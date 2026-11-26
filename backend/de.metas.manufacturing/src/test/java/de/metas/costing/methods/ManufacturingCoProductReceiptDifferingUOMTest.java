/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.costing.methods;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostElement;
import de.metas.costing.CostPrice;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.money.CurrencyId;
import de.metas.order.costs.OrderCostService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Cost;
import org.compiere.model.X_C_UOM;
import org.compiere.util.Env;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCostTrxType;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.impl.MockedProductCostingBL;
import org.eevolution.model.I_PP_Cost_Collector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Differing-UOM co-product receipt: a co-product made in a BOM-line UOM (kg) but stocked/costed in a different
 * stock UOM (Stück) must POST. The finished-good/co-product RECEIPT path snapshots the product's current cost
 * price in the STOCK UOM and hands it to {@code PPOrderCosts.updatePriceForCostSegmentAndElement}, whose row
 * keeps its {@code accumulatedQty} in the BOM-line UOM. Left un-converted, that price reaches
 * {@code PPOrderCost}'s constructor invariant and the receipt cost-update throws
 * {@code AdempiereException: "UOM not matching"} (so the GL posting fails).
 * <p>
 * The sibling {@code ManufacturingCoProductPartialReceiptTest} masks this by using a single UOM everywhere.
 * Here the co-product's M_Cost is in Stück, its {@code PP_Order_Cost} row is in kg, a product-specific
 * Stück&harr;kg conversion is registered, and the receipt qty is in kg - so the two UOMs actually diverge.
 */
@ExtendWith(AdempiereTestWatcher.class)
class ManufacturingCoProductReceiptDifferingUOMTest
{
	private static final Instant DATE = Instant.parse("2026-09-16T00:00:00Z");

	/** the co-product's current cost, priced per STOCK UOM (Stück) */
	private static final String COPRODUCT_COST_PRICE_PER_STUECK = "10";
	private static final String COPRODUCT_CURRENT_QTY = "0";

	/** the order's total inbound costs (e.g. a material issue already booked) - qty-independent */
	private static final String TOTAL_INBOUND_COSTS_AMOUNT = "450";

	/** product-specific conversion: 1 kg = 2 Stück */
	private static final BigDecimal KG_TO_STUECK_MULTIPLIER = new BigDecimal("2");

	/** receipt request qty, expressed in the BOM-line UOM (kg) */
	private static final BigDecimal RECEIPT_QTY_KG = new BigDecimal("3");

	/** current-cost(10/Stück) x received(3kg -> 6 Stück) = 60 - unchanged by the fix (only the stored price UOM changes) */
	private static final BigDecimal EXPECTED_RECEIPT_AMOUNT = new BigDecimal("60");
	/** the snapshot price after conversion into the row's kg UOM: 10 EUR/Stück x 2 Stück/kg = 20 EUR/kg */
	private static final BigDecimal EXPECTED_PRICE_PER_KG = new BigDecimal("20");

	//
	// Constants for the full close-path scenario (receipt -> updatePostCalculationAmounts -> distributor.createCostDetails).
	// Deliberately clean whole-number amounts, independent of the receipt-only scenario above, so the WIP=0 and the
	// capitalized carve are exact.
	//
	/** the order's total inbound costs for the close scenario (a material issue already booked) */
	private static final String CLOSE_TOTAL_INBOUND = "100";
	/** the co-product's current cost, priced per STOCK UOM (Stück); the receipt lifts on-hand from 0 to 6 Stück */
	private static final String CLOSE_COPRODUCT_PRICE_PER_STUECK = "2";
	/** the co-product's cost-distribution share of the total inbound costs */
	private static final Percent CLOSE_COPRODUCT_PERCENT = Percent.of(30);
	/** the co-product's distribution-% carve of the total inbound costs: 100 x 30% = 30 */
	private static final BigDecimal CLOSE_EXPECTED_COPRODUCT_CARVE = new BigDecimal("30");
	/** what the co-product's receipt books: current-cost(2/Stück) x received(3kg -> 6 Stück) = 12 */
	private static final BigDecimal CLOSE_EXPECTED_RECEIPT_AMOUNT = new BigDecimal("12");
	/** the co-product's still-open WIP after the receipt: carve(30) - booked receipt(12) = 18 */
	private static final BigDecimal CLOSE_EXPECTED_COPRODUCT_RESIDUAL_BEFORE_CLOSE = new BigDecimal("18");
	/** the co-product's current cost price after the in-stock carve capitalizes: (2 x 6 + 18) / 6 = 5 EUR/Stück */
	private static final BigDecimal CLOSE_EXPECTED_COPRODUCT_PRICE_PER_STUECK = new BigDecimal("5");

	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ANY;
	private final PPOrderId orderId = PPOrderId.ofRepoId(1);

	private CurrencyId currencyId;
	private I_C_UOM uomStueck;
	private I_C_UOM uomKg;
	private ProductId mainProductId;
	private ProductId rawProductId;
	private ProductId coProductId;

	private CostElementRepository costElementRepo;
	private CostingMethodHandlerUtils utils;
	private PPOrderCostDifferenceDistributor distributor;
	private IPPOrderCostBL ppOrderCostBL;

	// per-test, set up by setupOrderFor(..)
	private AcctSchemaId acctSchemaId;
	private CostElement costElement;
	private CostingMethodHandler handler;
	private PPCostCollectorId receiptCollectorId;

	/** the two in-scope handlers (AveragePO, MovingAverageInvoice); LastPO is covered by the same single-point fix but out of this test's scope. */
	private enum ManufacturingHandlerUnderTest
	{
		AveragePO(CostingMethod.AveragePO)
				{
					@Override
					CostingMethodHandler createHandler(final CostingMethodHandlerUtils utils, final PPOrderCostDifferenceDistributor distributor)
					{
						return new ManufacturingAveragePOCostingMethodHandler(
								utils,
								distributor,
								new AveragePOCostingMethodHandler(
										utils,
										MatchInvoiceService.newInstanceForUnitTesting(),
										OrderCostService.newInstanceForUnitTesting()));
					}
				},
		MovingAverageInvoice(CostingMethod.MovingAverageInvoice)
				{
					@Override
					CostingMethodHandler createHandler(final CostingMethodHandlerUtils utils, final PPOrderCostDifferenceDistributor distributor)
					{
						return new ManufacturingMovingAverageInvoiceCostingMethodHandler(
								utils,
								distributor,
								new MovingAverageInvoiceCostingMethodHandler(
										utils,
										MatchInvoiceService.newInstanceForUnitTesting(),
										OrderCostService.newInstanceForUnitTesting()));
					}
				},
		;

		final CostingMethod costingMethod;

		ManufacturingHandlerUnderTest(@NonNull final CostingMethod costingMethod) {this.costingMethod = costingMethod;}

		abstract CostingMethodHandler createHandler(CostingMethodHandlerUtils utils, PPOrderCostDifferenceDistributor distributor);
	}

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), clientId);

		ppOrderCostBL = Services.get(IPPOrderCostBL.class);

		uomStueck = BusinessTestHelper.createUOM("Stück", X_C_UOM.UOMTYPE_Other, 0);
		uomKg = BusinessTestHelper.createUOM("Kg", X_C_UOM.UOMTYPE_Weigth, 3);
		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		mainProductId = BusinessTestHelper.createProductId("main product", uomStueck);
		rawProductId = BusinessTestHelper.createProductId("raw product", uomStueck);
		// the co-product is a product whose BOM-line UOM is kg but whose stock/cost UOM is Stück
		coProductId = BusinessTestHelper.createProductId("co-product", uomStueck);

		// product-specific Stück<->kg conversion: 1 kg = 2 Stück (registered kg->Stück; the reciprocal is derived)
		Services.get(IUOMConversionDAO.class).createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(coProductId)
				.fromUomId(UomId.ofRepoId(uomKg.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomStueck.getC_UOM_ID()))
				.fromToMultiplier(KG_TO_STUECK_MULTIPLIER)
				.build());

		// the costing level is what the cost segment is built from; the costing method is only asked for products
		Services.registerService(IProductCostingBL.class, new MockedProductCostingBL(CostingLevel.Client, CostingMethod.AveragePO));

		costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		utils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				new CurrentCostsRepository(costElementRepo),
				new CostDetailService(new CostDetailRepository(), costElementRepo));
		distributor = new PPOrderCostDifferenceDistributor(costElementRepo, utils);
	}

	@ParameterizedTest
	@EnumSource(ManufacturingHandlerUnderTest.class)
	void coProductReceipt_withDifferingCostAndBomUom_convertsPriceAndPosts(final ManufacturingHandlerUnderTest handlerUnderTest)
	{
		setupOrderFor(handlerUnderTest);

		// Pre-fix this throws AdempiereException "UOM not matching": the current cost price is in Stück while the
		// co-product's PP_Order_Cost row keeps its accumulatedQty in kg, and updatePriceForCostSegmentAndElement
		// snapshots the un-converted Stück price onto the kg row.
		final CostDetailCreateResultsList result = handler.createOrUpdateCost(receiptRequest());
		final BigDecimal receiptAmount = result.getSingleResult().getAmt().getAmt(CostAmountType.MAIN).toBigDecimal();

		// The receipt books current-cost x received-qty regardless of the UOM split (10 EUR/Stück x 6 Stück).
		assertThat(receiptAmount).isEqualByComparingTo(EXPECTED_RECEIPT_AMOUNT);

		final PPOrderCost coProductCost = coProductOrderCost();
		// accumulated in the row's own kg UOM (6 Stück received -> 3 kg), value 60.
		assertThat(coProductCost.getAccumulatedAmount().toBigDecimal()).isEqualByComparingTo(EXPECTED_RECEIPT_AMOUNT);
		assertThat(coProductCost.getAccumulatedQty().getUomId()).isEqualTo(UomId.ofRepoId(uomKg.getC_UOM_ID()));
		assertThat(coProductCost.getAccumulatedQty().toBigDecimal()).isEqualByComparingTo(RECEIPT_QTY_KG);

		// the snapshot price was converted INTO the row's kg UOM (product-specific rate), not left in Stück.
		assertThat(coProductCost.getPrice().getUomId()).isEqualTo(UomId.ofRepoId(uomKg.getC_UOM_ID()));
		assertThat(coProductCost.getPrice().toBigDecimal()).isEqualByComparingTo(EXPECTED_PRICE_PER_KG);
	}

	/**
	 * WIP=0 end-to-end at the costing layer, under differing UOM: this drives the full order-close path the two
	 * manufacturing handlers run - the co-product RECEIPT (which internally runs
	 * {@code PPOrderCosts.updatePostCalculationAmounts}), then the CC-170 {@code CostDifferenceDistribution} leg that
	 * both {@link ManufacturingAveragePOCostingMethodHandler} and {@link ManufacturingMovingAverageInvoiceCostingMethodHandler}
	 * route to {@code costDifferenceDistributor.createCostDetails}. It proves, as a green end-to-end run rather than by
	 * reasoning, that with the co-product row in kg and its cost/stock in Stück:
	 * <ul>
	 *   <li>(a) every WIP residual (main product AND co-product) nets to EXACTLY zero after the close, and</li>
	 *   <li>(b) the co-product capitalizes its distribution-% carve ({@code total inbound x percent}) onto both its
	 *       {@code PP_Order_Cost} line and its current cost price.</li>
	 * </ul>
	 */
	@ParameterizedTest
	@EnumSource(ManufacturingHandlerUnderTest.class)
	void coProductReceiptThenClose_underDifferingUom_netsWipToZero_andCapitalizesCarve(final ManufacturingHandlerUnderTest handlerUnderTest)
	{
		setupOrderForClose(handlerUnderTest);

		//
		// (1) RECEIPT leg: the co-product MixVariance receipt values at current-cost x received-qty
		// (2 EUR/Stück x 6 Stück = 12) and - inside the handler - runs updatePostCalculationAmounts, which carves the
		// co-product's distribution share (100 total inbound x 30% = 30) and relieves the main product with the
		// remainder (70). The carve exceeds what the receipt booked, so the co-product still owes 18 to WIP.
		handler.createOrUpdateCost(receiptRequest());

		final PPOrderCost coProductAfterReceipt = coProductOrderCost();
		assertThat(coProductAfterReceipt.getAccumulatedAmount().toBigDecimal()).isEqualByComparingTo(CLOSE_EXPECTED_RECEIPT_AMOUNT);
		assertThat(coProductAfterReceipt.getPostCalculationAmount().toBigDecimal()).isEqualByComparingTo(CLOSE_EXPECTED_COPRODUCT_CARVE);
		assertThat(coProductAfterReceipt.getResidualCost().toBigDecimal()).isEqualByComparingTo(CLOSE_EXPECTED_COPRODUCT_RESIDUAL_BEFORE_CLOSE);

		//
		// (2) CLOSE leg: the CC-170 CostDifferenceDistribution collector - exactly what the manufacturing handlers
		// route to costDifferenceDistributor.createCostDetails - discharges every WIP residual (main product and each
		// co-product), capitalizing the in-stock share onto the current cost price.
		final PPCostCollectorId distributionCollectorId = createCostCollector(CostCollectorType.CostDifferenceDistribution, BigDecimal.ZERO);
		distributor.createCostDetails(closeRequest(distributionCollectorId), orderId);

		//
		// (a) WIP nets to EXACTLY zero: neither the main product nor the co-product carries a residual after the close.
		final PPOrderCosts afterClose = ppOrderCostBL.getByOrderId(orderId);
		assertThat(afterClose.getResidualCost(acctSchemaId, costElement.getId()).toBigDecimal())
				.isEqualByComparingTo(BigDecimal.ZERO);
		final PPOrderCost coProductAfterClose = coProductOrderCost();
		assertThat(coProductAfterClose.getResidualCost().toBigDecimal()).isEqualByComparingTo(BigDecimal.ZERO);

		//
		// (b) the co-product capitalized its distribution-% carve: its line now carries exactly 100 x 30% = 30, and the
		// whole in-stock share (all 6 Stück the receipt put on hand) lifted its current cost price from 2 to 5 EUR/Stück.
		assertThat(coProductAfterClose.getAccumulatedAmount().toBigDecimal()).isEqualByComparingTo(CLOSE_EXPECTED_COPRODUCT_CARVE);
		final BigDecimal coProductPriceAfterClose = utils
				.getCurrentCostForUpdate(utils.extractCostSegmentAndElement(receiptRequest()))
				.getCostPrice()
				.toBigDecimal();
		assertThat(coProductPriceAfterClose).isEqualByComparingTo(CLOSE_EXPECTED_COPRODUCT_PRICE_PER_STUECK);
	}

	//
	//
	// fixture
	//
	//

	private void setupOrderFor(@NonNull final ManufacturingHandlerUnderTest handlerUnderTest)
	{
		acctSchemaId = AcctSchemaTestHelper.newAcctSchema()
				.costingLevel(CostingLevel.Client)
				.costingMethod(handlerUnderTest.costingMethod)
				.currencyId(currencyId)
				.build();
		costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, handlerUnderTest.costingMethod);
		handler = handlerUnderTest.createHandler(utils, distributor);

		receiptCollectorId = createCostCollector(CostCollectorType.MixVariance, RECEIPT_QTY_KG);

		// the co-product's current M_Cost is priced per STOCK UOM (Stück)
		saveCurrentCost(coProductId, COPRODUCT_COST_PRICE_PER_STUECK, COPRODUCT_CURRENT_QTY);
		createOrderCosts();
	}

	private PPCostCollectorId createCostCollector(@NonNull final CostCollectorType type, @NonNull final BigDecimal movementQty)
	{
		final I_PP_Cost_Collector cc = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class);
		cc.setCostCollectorType(type.getCode());
		cc.setPP_Order_ID(orderId.getRepoId());
		cc.setMovementQty(movementQty);
		InterfaceWrapperHelper.saveRecord(cc);

		return PPCostCollectorId.ofRepoId(cc.getPP_Cost_Collector_ID());
	}

	private void saveCurrentCost(
			@NonNull final ProductId productId,
			@NonNull final String currentCostPrice,
			@NonNull final String currentQty)
	{
		final I_M_Cost cost = InterfaceWrapperHelper.newInstance(I_M_Cost.class);
		cost.setAD_Org_ID(orgId.getRepoId());
		cost.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		cost.setM_CostElement_ID(costElement.getId().getRepoId());
		cost.setM_CostType_ID(1);
		cost.setM_Product_ID(productId.getRepoId());
		cost.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
		// the cost is kept in the STOCK UOM (Stück), distinct from the BOM-line UOM (kg) of the PP_Order_Cost row
		cost.setC_UOM_ID(uomStueck.getC_UOM_ID());
		cost.setC_Currency_ID(currencyId.getRepoId());
		cost.setCurrentCostPrice(new BigDecimal(currentCostPrice));
		cost.setCurrentQty(new BigDecimal(currentQty));
		InterfaceWrapperHelper.saveRecord(cost);
	}

	/**
	 * The rows {@code CreatePPOrderCostsCommand} leaves behind for a freshly created order. All PP_Order_Cost rows
	 * are kept in the BOM-line UOM (kg) - the very thing that diverges from the co-product's Stück-priced M_Cost.
	 */
	private void createOrderCosts()
	{
		final PPOrderCost materialIssue = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MaterialIssue)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(receiptRequest().withProductId(rawProductId)))
				.price(costPriceKg("0"))
				.accumulatedAmount(CostAmount.of(new BigDecimal(TOTAL_INBOUND_COSTS_AMOUNT), currencyId))
				.accumulatedQty(Quantity.zero(uomKg))
				.build();

		final PPOrderCost mainProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(receiptRequest().withProductId(mainProductId)))
				.price(costPriceKg("0"))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomKg))
				.build();

		final PPOrderCost coProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.CoProduct)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(receiptRequest()))
				.price(costPriceKg("0"))
				.coProductCostDistributionPercent(Percent.of(BigDecimal.ONE, new BigDecimal("6"), 4))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomKg))
				.build();

		ppOrderCostBL.save(PPOrderCosts.builder()
				.orderId(orderId)
				.costs(ImmutableList.of(materialIssue, mainProduct, coProduct))
				.build());
	}

	//
	//
	// fixture - full close-path scenario
	//
	//

	private void setupOrderForClose(@NonNull final ManufacturingHandlerUnderTest handlerUnderTest)
	{
		acctSchemaId = AcctSchemaTestHelper.newAcctSchema()
				.costingLevel(CostingLevel.Client)
				.costingMethod(handlerUnderTest.costingMethod)
				.currencyId(currencyId)
				.build();
		costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, handlerUnderTest.costingMethod);
		handler = handlerUnderTest.createHandler(utils, distributor);

		receiptCollectorId = createCostCollector(CostCollectorType.MixVariance, RECEIPT_QTY_KG);

		// the co-product's current M_Cost is priced per STOCK UOM (Stück) and starts empty; the receipt adds on-hand
		saveCurrentCost(coProductId, CLOSE_COPRODUCT_PRICE_PER_STUECK, "0");
		// the main product needs a current M_Cost row too: the close reads it to split the main-product residual
		saveCurrentCost(mainProductId, "0", "0");
		createOrderCostsForClose();
	}

	/**
	 * The {@code PP_Order_Cost} rows for the close scenario: a booked material issue (the total inbound costs), an
	 * empty main-product line, and a co-product line carrying its cost-distribution percent. As with
	 * {@link #createOrderCosts()} every row keeps its {@code accumulatedQty} in the BOM-line UOM (kg).
	 */
	private void createOrderCostsForClose()
	{
		final PPOrderCost materialIssue = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MaterialIssue)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(receiptRequest().withProductId(rawProductId)))
				.price(costPriceKg("0"))
				.accumulatedAmount(CostAmount.of(new BigDecimal(CLOSE_TOTAL_INBOUND), currencyId))
				.accumulatedQty(Quantity.zero(uomKg))
				.build();

		final PPOrderCost mainProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(receiptRequest().withProductId(mainProductId)))
				.price(costPriceKg("0"))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomKg))
				.build();

		final PPOrderCost coProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.CoProduct)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(receiptRequest()))
				.price(costPriceKg("0"))
				.coProductCostDistributionPercent(CLOSE_COPRODUCT_PERCENT)
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomKg))
				.build();

		ppOrderCostBL.save(PPOrderCosts.builder()
				.orderId(orderId)
				.costs(ImmutableList.of(materialIssue, mainProduct, coProduct))
				.build());
	}

	/** what the framework hands the distributor for the main product's CC-170 CostDifferenceDistribution leg */
	private CostDetailCreateRequest closeRequest(@NonNull final PPCostCollectorId distributionCollectorId)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(clientId)
				.orgId(orgId)
				.productId(mainProductId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElement(costElement)
				.documentRef(CostingDocumentRef.ofCostCollectorId(distributionCollectorId))
				// value-only discharge: the qty is zeroed downstream, its UOM only needs to be the main product's stock UOM
				.qty(Quantity.zero(uomStueck))
				.amt(CostAmount.zero(currencyId))
				.date(DATE)
				.build();
	}

	/** a zero-ish cost price expressed in the BOM-line UOM (kg), to match the PP_Order_Cost rows' accumulatedQty UOM */
	private CostPrice costPriceKg(@NonNull final String ownCostPrice)
	{
		return CostPrice.builder()
				.ownCostPrice(CostAmount.of(new BigDecimal(ownCostPrice), currencyId))
				.componentsCostPrice(CostAmount.zero(currencyId))
				.uomId(UomId.ofRepoId(uomKg.getC_UOM_ID()))
				.build();
	}

	/** what {@code DocLine_CostCollector} hands the handler for the kg MixVariance co-product receipt collector */
	private CostDetailCreateRequest receiptRequest()
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(clientId)
				.orgId(orgId)
				.productId(coProductId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElement(costElement)
				.documentRef(CostingDocumentRef.ofCostCollectorId(receiptCollectorId))
				.qty(Quantity.of(RECEIPT_QTY_KG, uomKg)) // the receipt qty is in the BOM-line UOM (kg)
				.amt(CostAmount.zero(currencyId)) // N/A - the handler values the receipt at the co-product's current cost
				.date(DATE)
				.build();
	}

	private PPOrderCost coProductOrderCost()
	{
		return ppOrderCostBL
				.getByOrderId(orderId)
				.getByProductAndCostElements(coProductId, ImmutableSet.of(costElement.getId()))
				.stream()
				.filter(PPOrderCost::isCoProduct)
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("No co-product PP_Order_Cost row found"));
	}
}

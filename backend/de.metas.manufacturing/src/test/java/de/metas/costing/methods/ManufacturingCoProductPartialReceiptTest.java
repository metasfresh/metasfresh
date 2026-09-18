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
 * AC8/AC9 (D-MULTIRECEIPT landmine): a co-product received across TWO partial receipts must book
 * {@code current-cost x received-qty} on EACH receipt (proportional, mirroring the finished-good receipt path) -
 * NOT the full qty-independent carve share ({@code ShareInbound x percent}) on every single receipt. The old
 * full-share carve path over-relieved WIP by
 * {@code (N - 1) x (ShareInbound x p)} across N partial receipts; the single CC-170 true-up (the cost-difference
 * distributor, covered elsewhere) now carries the carve instead.
 */
@ExtendWith(AdempiereTestWatcher.class)
class ManufacturingCoProductPartialReceiptTest
{
	private static final Instant DATE = Instant.parse("2026-09-16T00:00:00Z");

	/** the co-product's current cost, i.e. the price EACH partial receipt must be valued at */
	private static final String COPRODUCT_COST_PRICE = "10";
	private static final String COPRODUCT_CURRENT_QTY = "0";

	/** the order's total inbound costs (e.g. a material issue already booked) - qty-independent, stays fixed across both receipts */
	private static final String TOTAL_INBOUND_COSTS_AMOUNT = "450";

	private static final BigDecimal FIRST_RECEIPT_QTY = new BigDecimal("3");
	private static final BigDecimal SECOND_RECEIPT_QTY = new BigDecimal("3");

	/** current-cost(10) x qty(3): what EACH partial receipt must book under the fix */
	private static final BigDecimal EXPECTED_AMOUNT_PER_RECEIPT = new BigDecimal("30");
	/** current-cost(10) x total received qty(6): what the two receipts must sum to */
	private static final BigDecimal EXPECTED_TOTAL_AMOUNT = new BigDecimal("60");

	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ANY;
	private final PPOrderId orderId = PPOrderId.ofRepoId(1);

	private CurrencyId currencyId;
	private I_C_UOM uomEach;
	private ProductId mainProductId;
	private ProductId issueProductId;
	private ProductId coProductId;

	private CostElementRepository costElementRepo;
	private CostingMethodHandlerUtils utils;
	private PPOrderCostDifferenceDistributor distributor;
	private IPPOrderCostBL ppOrderCostBL;

	// per-test, set up by setupOrderFor(..)
	private AcctSchemaId acctSchemaId;
	private CostElement costElement;
	private CostingMethodHandler handler;
	private PPCostCollectorId firstReceiptCollectorId;
	private PPCostCollectorId secondReceiptCollectorId;

	/** the two in-scope handlers per Task 10; the LastPO handler is explicitly out of scope. */
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

		uomEach = BusinessTestHelper.createUomEach();
		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		mainProductId = BusinessTestHelper.createProductId("main product", uomEach);
		issueProductId = BusinessTestHelper.createProductId("input material", uomEach);
		coProductId = BusinessTestHelper.createProductId("co-product", uomEach);

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
	void twoPartialReceipts_eachBooksCurrentCostTimesReceivedQty_notTheFullShare(final ManufacturingHandlerUnderTest handlerUnderTest)
	{
		setupOrderFor(handlerUnderTest);

		final CostDetailCreateResultsList firstResult = handler.createOrUpdateCost(firstReceiptRequest());
		final BigDecimal firstAmount = firstResult.getSingleResult().getAmt().getAmt(CostAmountType.MAIN).toBigDecimal();

		final CostDetailCreateResultsList secondResult = handler.createOrUpdateCost(secondReceiptRequest());
		final BigDecimal secondAmount = secondResult.getSingleResult().getAmt().getAmt(CostAmountType.MAIN).toBigDecimal();

		// EACH receipt books current-cost x its OWN received qty (3 x 10 = 30) - NOT the full, qty-independent
		// ShareInbound x percent carve share, which pre-fix is booked again in full on every receipt.
		assertThat(firstAmount).isEqualByComparingTo(EXPECTED_AMOUNT_PER_RECEIPT);
		assertThat(secondAmount).isEqualByComparingTo(EXPECTED_AMOUNT_PER_RECEIPT);
		assertThat(firstAmount.add(secondAmount)).isEqualByComparingTo(EXPECTED_TOTAL_AMOUNT);

		// and the co-product's PP_Order_Cost row accumulates exactly that sum - not (N x full share)
		final PPOrderCost coProductCost = coProductOrderCost();
		assertThat(coProductCost.getAccumulatedAmount().toBigDecimal()).isEqualByComparingTo(EXPECTED_TOTAL_AMOUNT);
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

		firstReceiptCollectorId = createCostCollector(CostCollectorType.MixVariance, FIRST_RECEIPT_QTY);
		secondReceiptCollectorId = createCostCollector(CostCollectorType.MixVariance, SECOND_RECEIPT_QTY);

		saveCurrentCost(coProductId, COPRODUCT_COST_PRICE, COPRODUCT_CURRENT_QTY);
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
		cost.setC_UOM_ID(uomEach.getC_UOM_ID());
		cost.setC_Currency_ID(currencyId.getRepoId());
		cost.setCurrentCostPrice(new BigDecimal(currentCostPrice));
		cost.setCurrentQty(new BigDecimal(currentQty));
		InterfaceWrapperHelper.saveRecord(cost);
	}

	/**
	 * The rows {@code CreatePPOrderCostsCommand} leaves behind for a freshly created order: a main-product row
	 * (mandatory - post-calculation requires exactly one per cost element), total inbound costs from a material issue (fixed,
	 * qty-independent - the very thing the old full-share path multiplied by a percent on every single receipt),
	 * and the co-product row itself, carrying the BOM's qty-distribution percent (1/6, as the co-product is
	 * eventually received across the two 3kg receipts below).
	 */
	private void createOrderCosts()
	{
		final PPOrderCost materialIssue = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MaterialIssue)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(firstReceiptRequest().withProductId(issueProductId)))
				.price(costPrice("0"))
				.accumulatedAmount(CostAmount.of(new BigDecimal(TOTAL_INBOUND_COSTS_AMOUNT), currencyId))
				.accumulatedQty(Quantity.zero(uomEach))
				.build();

		final PPOrderCost mainProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(firstReceiptRequest().withProductId(mainProductId)))
				.price(costPrice("0"))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomEach))
				.build();

		final PPOrderCost coProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.CoProduct)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(firstReceiptRequest()))
				.price(costPrice("0"))
				// the BOM's real 1/qty distribution share (Percent.of(1, totalCoQty=6, precision 4)) - irrelevant
				// under the fix (the blank-share path is no longer called), kept to mirror the real fixture and to
				// make the RED failure obvious: pre-fix, EVERY receipt books 450 x 1/6 = 75.0002, not 30.
				.coProductCostDistributionPercent(Percent.of(BigDecimal.ONE, new BigDecimal("6"), 4))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomEach))
				.build();

		ppOrderCostBL.save(PPOrderCosts.builder()
				.orderId(orderId)
				.costs(ImmutableList.of(materialIssue, mainProduct, coProduct))
				.build());
	}

	private CostPrice costPrice(@NonNull final String ownCostPrice)
	{
		return CostPrice.builder()
				.ownCostPrice(CostAmount.of(new BigDecimal(ownCostPrice), currencyId))
				.componentsCostPrice(CostAmount.zero(currencyId))
				.uomId(UomId.ofRepoId(uomEach.getC_UOM_ID()))
				.build();
	}

	/** what {@code DocLine_CostCollector} hands the handler for the FIRST 3kg MixVariance co-product receipt collector */
	private CostDetailCreateRequest firstReceiptRequest()
	{
		return requestBuilder(coProductId, firstReceiptCollectorId, FIRST_RECEIPT_QTY);
	}

	/** what {@code DocLine_CostCollector} hands the handler for the SECOND 3kg MixVariance co-product receipt collector */
	private CostDetailCreateRequest secondReceiptRequest()
	{
		return requestBuilder(coProductId, secondReceiptCollectorId, SECOND_RECEIPT_QTY);
	}

	private CostDetailCreateRequest requestBuilder(
			@NonNull final ProductId productId,
			@NonNull final PPCostCollectorId costCollectorId,
			@NonNull final BigDecimal qty)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(clientId)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElement(costElement)
				.documentRef(CostingDocumentRef.ofCostCollectorId(costCollectorId))
				.qty(Quantity.of(qty, uomEach))
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

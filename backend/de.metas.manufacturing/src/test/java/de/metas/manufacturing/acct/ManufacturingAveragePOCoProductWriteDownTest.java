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

package de.metas.manufacturing.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchemaId;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.costing.methods.AveragePOCostingMethodHandler;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.costing.methods.CostingMethodHandler;
import de.metas.costing.methods.CostingMethodHandlerUtils;
import de.metas.costing.methods.ManufacturingAveragePOCostingMethodHandler;
import de.metas.costing.methods.PPOrderCostDifferenceDistributor;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * End-to-end coverage of a co-product production run under the <b>AveragePO</b> costing method, driven through the
 * REAL {@link ManufacturingAveragePOCostingMethodHandler}. It exercises, in one flow through that handler, the path
 * no single JUnit test drove before: receipt -&gt; carve -&gt; CC-170 write-down (with the Dr/Cr SIGN) -&gt; whole-order WIP
 * relief. The cucumber scenario {@code coProductCostDistributionPercent.feature} proves the same numbers under
 * MovingAverageInvoice; this pins them under the AvgPO handler.
 * <p>
 * The case: a run issues 30 PCE of a 15-per-PCE raw component (Σ inbound 450) and yields a premium main product plus
 * a low-value co-product carrying {@code CoProductCostDistributionPercent = 10.666667%}. The co-product is carved
 * {@code p × 450 = 48}; the main product keeps the remainder (402). The co-product's OWN receipt books at its own
 * current cost ({@code 9.9 × 6 = 59.4}), which OVERVALUES it relative to its 48 carve, so the CC-170 true-up at order
 * close must write it DOWN by {@code 48 − 59.4 = −11.4} - and, with all 6 units still on hand, that write-down is
 * {@code Cr P_Asset / Dr P_WIP} (the single biggest sign risk).
 * <p>
 * Every expected figure below is an independent literal, never a value read back out of the code under test.
 */
@ExtendWith(AdempiereTestWatcher.class)
class ManufacturingAveragePOCoProductWriteDownTest
{
	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ANY;
	private final PPOrderId orderId = PPOrderId.ofRepoId(1);

	private CurrencyId currencyId;
	private I_C_UOM uomEach;
	private ProductId mainProductId;
	private ProductId rawProductId;
	private ProductId coProductId;

	private AcctSchemaId acctSchemaId;
	private CostElement costElement;
	private CostElementId costElementId;

	private CostElementRepository costElementRepo;
	private CostingMethodHandlerUtils utils;
	private PPOrderCostDifferenceDistributor distributor;
	private CostingMethodHandler handler;
	private IPPOrderCostBL ppOrderCostBL;

	private PPCostCollectorId issueCollectorId;
	private PPCostCollectorId mainReceiptCollectorId;
	private PPCostCollectorId coReceiptCollectorId;
	private PPCostCollectorId ccDistributionCollectorId;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), clientId);

		uomEach = BusinessTestHelper.createUomEach();
		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		mainProductId = BusinessTestHelper.createProductId("main product", uomEach);
		rawProductId = BusinessTestHelper.createProductId("raw product", uomEach);
		coProductId = BusinessTestHelper.createProductId("co-product", uomEach);

		// the costing level is what the cost segment is built from; the costing method is only asked for products
		Services.registerService(IProductCostingBL.class, new MockedProductCostingBL(CostingLevel.Client, CostingMethod.AveragePO));

		acctSchemaId = AcctSchemaTestHelper.newAcctSchema()
				.costingLevel(CostingLevel.Client)
				.costingMethod(CostingMethod.AveragePO)
				.currencyId(currencyId)
				.build();
		AcctSchemaTestHelper.registerAcctSchemaDAOWhichAlwaysProvides(acctSchemaId);

		// The CC-170 handler path resolves the order only through its PP_Cost_Collector rows and PP_Order_Cost
		// (never by loading I_PP_Order), so a bare orderId suffices - mirroring ManufacturingRepostCostCollectorTest.

		costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		utils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				new CurrentCostsRepository(costElementRepo),
				new CostDetailService(new CostDetailRepository(), costElementRepo));
		distributor = new PPOrderCostDifferenceDistributor(costElementRepo, utils);
		ppOrderCostBL = Services.get(IPPOrderCostBL.class);

		costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		costElementId = costElement.getId();

		// the REAL AveragePO manufacturing handler, wired exactly as production wires it
		handler = new ManufacturingAveragePOCostingMethodHandler(
				utils,
				distributor,
				new AveragePOCostingMethodHandler(
						utils,
						MatchInvoiceService.newInstanceForUnitTesting(),
						OrderCostService.newInstanceForUnitTesting()));

		issueCollectorId = createCostCollector(CostCollectorType.ComponentIssue);
		mainReceiptCollectorId = createCostCollector(CostCollectorType.MaterialReceipt);
		coReceiptCollectorId = createCostCollector(CostCollectorType.MixVariance); // the co/by-product receipt type
		ccDistributionCollectorId = createCostCollector(CostCollectorType.CostDifferenceDistribution);
	}

	@Test
	void coProductReceiptOvervaluesItsCarve_ccWritesItDownCreditAssetDebitWip_throughRealAveragePOHandler()
	{
		//
		// Given the current costs the receipts are valued at
		saveCurrentCost(rawProductId, "15", "30"); // raw component: 15 per PCE, 30 PCE on hand to issue
		saveCurrentCost(mainProductId, "0", "0");  // finished good: never priced before
		saveCurrentCost(coProductId, "9.9", "0");  // co-product's OWN current cost - overvalued vs. its 48 carve

		// and the up-front PP_Order_Cost rows CreatePPOrderCostsCommand leaves behind (accumulated 0 before any movement)
		seedOrderCosts();

		//
		// When the raw component is issued (30 PCE @ 15 = 450 into the order)
		handler.createOrUpdateCost(request(rawProductId, issueCollectorId, "-30"));

		// and the finished good is received (24 PCE, valued at its own 0 current cost)
		handler.createOrUpdateCost(request(mainProductId, mainReceiptCollectorId, "24"));

		// and the co-product is received (6 PCE, valued at ITS OWN current cost: 9.9 × 6 = 59.4)
		handler.createOrUpdateCost(request(coProductId, coReceiptCollectorId, "6"));

		//
		// Then the co-product booked its own current-cost value while post-calc carved it p × 450 = 48
		final CostAmount totalInboundCost = accumulatedOf(rawProductId);
		assertThat(totalInboundCost.toBigDecimal()).isEqualByComparingTo("450");

		final BigDecimal coBookedBD = accumulatedOf(coProductId).toBigDecimal();
		assertThat(coBookedBD).isEqualByComparingTo("59.4");                                // booked at own current cost
		assertThat(carveOf(coProductId).toBigDecimal()).isEqualByComparingTo("48");         // p × 450
		assertThat(residualOf(coProductId).toBigDecimal()).isEqualByComparingTo("-11.4");   // 48 − 59.4: a write-DOWN

		// the finished good keeps the remainder (450 − 48), booked at zero, so its whole 402 is still in WIP
		final CostAmount mainBooked = accumulatedOf(mainProductId);
		assertThat(mainBooked.toBigDecimal()).isEqualByComparingTo("0");
		assertThat(carveOf(mainProductId).toBigDecimal()).isEqualByComparingTo("402");
		assertThat(residualOf(mainProductId).toBigDecimal()).isEqualByComparingTo("402");

		//
		// When the order is closed and its residual distributed through the CC-170 collector (same real handler)
		handler.createOrUpdateCost(request(mainProductId, ccDistributionCollectorId, "0"));

		//
		// Then both the co-product's and the main product's residuals are fully discharged: nothing left in WIP
		assertThat(residualOf(coProductId).isZero()).isTrue();
		assertThat(residualOf(mainProductId).isZero()).isTrue();

		//
		// and the co-product's own CC-170 split is a pure, fully-on-hand write-down (nothing shipped)
		final CostAmountDetailed coSplit = splitOf(coProductId);
		assertThat(coSplit.getMainAmt().toBigDecimal()).isEqualByComparingTo("-11.4");
		assertThat(coSplit.getCostAdjustmentAmt().toBigDecimal()).isEqualByComparingTo("-11.4"); // all 6 units on hand
		assertThat(coSplit.getAlreadyShippedAmt().toBigDecimal()).isEqualByComparingTo("0");

		// THE headline assertion: the write-down credits the co-product's OWN P_Asset and debits P_WIP
		final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> coLegs =
				Doc_PPCostCollector.costDifferenceDistributionLegs(coSplit);
		assertThat(coLegs).hasSize(2); // fully on hand: no COGS leg

		final Doc_PPCostCollector.CostDifferenceDistributionLeg coAsset = legByAcctType(coLegs, ProductAcctType.P_Asset_Acct);
		assertThat(coAsset.isDebit()).isFalse(); // CREDIT - the write-down sign
		assertThat(coAsset.getAbsAmt().toBigDecimal()).isEqualByComparingTo("11.4");

		final Doc_PPCostCollector.CostDifferenceDistributionLeg coWip = legByAcctType(coLegs, ProductAcctType.P_WIP_Acct);
		assertThat(coWip.isDebit()).isTrue(); // DEBIT
		assertThat(coWip.getAbsAmt().toBigDecimal()).isEqualByComparingTo("11.4");

		//
		// and the finished good's own CC-170 split capitalizes its whole 402 (all 24 units on hand): Dr P_Asset / Cr P_WIP
		final CostAmountDetailed mainSplit = splitOf(mainProductId);
		assertThat(mainSplit.getMainAmt().toBigDecimal()).isEqualByComparingTo("402");
		assertThat(mainSplit.getCostAdjustmentAmt().toBigDecimal()).isEqualByComparingTo("402");
		assertThat(mainSplit.getAlreadyShippedAmt().toBigDecimal()).isEqualByComparingTo("0");

		final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> mainLegs =
				Doc_PPCostCollector.costDifferenceDistributionLegs(mainSplit);
		final Doc_PPCostCollector.CostDifferenceDistributionLeg mainAsset = legByAcctType(mainLegs, ProductAcctType.P_Asset_Acct);
		assertThat(mainAsset.isDebit()).isTrue(); // DEBIT
		assertThat(mainAsset.getAbsAmt().toBigDecimal()).isEqualByComparingTo("402");
		final Doc_PPCostCollector.CostDifferenceDistributionLeg mainWip = legByAcctType(mainLegs, ProductAcctType.P_WIP_Acct);
		assertThat(mainWip.isDebit()).isFalse(); // CREDIT
		assertThat(mainWip.getAbsAmt().toBigDecimal()).isEqualByComparingTo("402");

		//
		// The CC-170 collector itself is a balanced document: Σ Dr == Σ Cr over both products' legs.
		final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> allLegs =
				ImmutableList.<Doc_PPCostCollector.CostDifferenceDistributionLeg>builder()
						.addAll(coLegs)
						.addAll(mainLegs)
						.build();
		assertThat(sumDr(allLegs)).isEqualByComparingTo(sumCr(allLegs));

		//
		// Independent value-neutrality evidence, read FRESH from the co-product's current cost AFTER the CC-170
		// reprice - not recombined from the assertions above: the write-down moved its cost price to
		// (59.4 − 11.4) / 6 = 8, so its 6 on-hand units are now valued at exactly the 48 carve (matching the
		// cucumber sibling's post-distribution InventoryValueAcctAmt 48 / Acct_CostPrice 8).
		final CurrentCost coCurrentCostAfter = utils.getCurrentCostForUpdate(segmentOf(coProductId, coReceiptCollectorId, "6"));
		assertThat(coCurrentCostAfter.getCurrentQty().toBigDecimal()).isEqualByComparingTo("6");
		assertThat(coCurrentCostAfter.getCostPrice().toBigDecimal()).isEqualByComparingTo("8");

		//
		// Executable documentation of the whole-order WIP flow (a restated arithmetic identity, NOT extra coverage):
		// the 450 the component issue put INTO WIP is relieved by the two receipts (main 0 + co 59.4) and the CC-170
		// legs (main P_WIP Cr 402 minus co P_WIP Dr 11.4). It closes by construction once the terms above hold - the
		// carve / residual / write-down-sign and current-cost assertions are what actually catch a regression.
		final BigDecimal wholeOrderWip = totalInboundCost.toBigDecimal()          // component issue: Dr P_WIP 450
				.subtract(mainBooked.toBigDecimal())                              // main receipt:    Cr P_WIP 0
				.subtract(coBookedBD)                                             // co receipt:      Cr P_WIP 59.4
				.subtract(mainWip.getAbsAmt().toBigDecimal())                     // CC-170 main:     Cr P_WIP 402
				.add(coWip.getAbsAmt().toBigDecimal());                           // CC-170 co:       Dr P_WIP 11.4
		assertThat(wholeOrderWip).isEqualByComparingTo("0");
	}

	//
	//
	// fixture
	//
	//

	private PPCostCollectorId createCostCollector(final CostCollectorType type)
	{
		final I_PP_Cost_Collector cc = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class);
		cc.setCostCollectorType(type.getCode());
		cc.setPP_Order_ID(orderId.getRepoId());
		InterfaceWrapperHelper.saveRecord(cc);
		return PPCostCollectorId.ofRepoId(cc.getPP_Cost_Collector_ID());
	}

	private void saveCurrentCost(final ProductId productId, final String currentCostPrice, final String currentQty)
	{
		final I_M_Cost cost = InterfaceWrapperHelper.newInstance(I_M_Cost.class);
		cost.setAD_Org_ID(orgId.getRepoId());
		cost.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		cost.setM_CostElement_ID(costElementId.getRepoId());
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
	 * The PP_Order_Cost rows a freshly created order carries: one inbound material-issue line, the main-product line
	 * and the co-product line (its own current cost as price, carrying the manual distribution percent). All start
	 * with zero accumulated amount/qty - the handler-driven issue and receipts move them.
	 */
	private void seedOrderCosts()
	{
		final PPOrderCost materialIssue = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MaterialIssue)
				.costSegmentAndElement(segmentOf(rawProductId, issueCollectorId, "-30"))
				.price(costPrice("15"))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomEach))
				.build();

		final PPOrderCost mainProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegmentAndElement(segmentOf(mainProductId, mainReceiptCollectorId, "24"))
				.price(costPrice("0"))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomEach))
				.build();

		final PPOrderCost coProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.CoProduct)
				.costSegmentAndElement(segmentOf(coProductId, coReceiptCollectorId, "6"))
				.price(costPrice("9.9"))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomEach))
				.coProductCostDistributionPercent(Percent.of(new BigDecimal("10.666667")))
				.build();

		ppOrderCostBL.save(PPOrderCosts.builder()
				.orderId(orderId)
				.costs(ImmutableList.of(materialIssue, mainProduct, coProduct))
				.build());
	}

	private CostPrice costPrice(final String ownCostPrice)
	{
		return CostPrice.builder()
				.ownCostPrice(CostAmount.of(new BigDecimal(ownCostPrice), currencyId))
				.componentsCostPrice(CostAmount.zero(currencyId))
				.uomId(UomId.ofRepoId(uomEach.getC_UOM_ID()))
				.build();
	}

	private CostDetailCreateRequest request(final ProductId productId, final PPCostCollectorId costCollectorId, final String qty)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(clientId)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElement(costElement)
				.documentRef(CostingDocumentRef.ofCostCollectorId(costCollectorId))
				.qty(Quantity.of(new BigDecimal(qty), uomEach))
				.amt(CostAmount.zero(currencyId)) // N/A - the handler values the movement at the product's current cost
				.date(Instant.parse("2026-09-17T00:00:00Z"))
				.build();
	}

	/**
	 * Builds the exact {@link CostSegmentAndElement} the handler resolves for {@code productId}, so a seeded
	 * PP_Order_Cost row keys identically to what {@code accumulateInboundCostAmount}/{@code accumulateOutboundCostAmount}
	 * look up (they throw on a missing key).
	 */
	private CostSegmentAndElement segmentOf(final ProductId productId, final PPCostCollectorId costCollectorId, final String qty)
	{
		return utils.extractCostSegmentAndElement(request(productId, costCollectorId, qty));
	}

	private PPOrderCost orderCostOf(final ProductId productId, final PPCostCollectorId costCollectorId, final String qty)
	{
		return ppOrderCostBL.getByOrderId(orderId)
				.getByCostSegmentAndElement(segmentOf(productId, costCollectorId, qty))
				.orElseThrow(() -> new AssertionError("No PP_Order_Cost row for " + productId));
	}

	private PPOrderCost orderCostOf(final ProductId productId)
	{
		if (productId.equals(rawProductId))
		{
			return orderCostOf(rawProductId, issueCollectorId, "-30");
		}
		else if (productId.equals(coProductId))
		{
			return orderCostOf(coProductId, coReceiptCollectorId, "6");
		}
		else
		{
			return orderCostOf(mainProductId, mainReceiptCollectorId, "24");
		}
	}

	private CostAmount accumulatedOf(final ProductId productId)
	{
		return orderCostOf(productId).getAccumulatedAmount();
	}

	private CostAmount carveOf(final ProductId productId)
	{
		return orderCostOf(productId).getPostCalculationAmount();
	}

	private CostAmount residualOf(final ProductId productId)
	{
		return orderCostOf(productId).getResidualCost();
	}

	/** The product's OWN split, read back from ITS OWN persisted CostDetail rows of the CC-170 collector. */
	private CostAmountDetailed splitOf(final ProductId productId)
	{
		final List<CostDetail> costDetails = utils.getExistingCostDetails(CostDetailQuery.builder()
				.acctSchemaId(acctSchemaId)
				.costElementId(costElementId)
				.documentRef(CostingDocumentRef.ofCostCollectorId(ccDistributionCollectorId))
				.productId(productId)
				.build());
		return utils.toCostDetailCreateResultsList(costDetails).getTotalAmountToPost(utils.getAcctSchemaById(acctSchemaId));
	}

	private static Doc_PPCostCollector.CostDifferenceDistributionLeg legByAcctType(
			final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> legs,
			final ProductAcctType acctType)
	{
		return legs.stream()
				.filter(leg -> leg.getAcctType() == acctType)
				.findFirst()
				.orElseThrow(() -> new AssertionError("No leg found for " + acctType));
	}

	private static BigDecimal sumDr(final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> legs)
	{
		return legs.stream()
				.filter(Doc_PPCostCollector.CostDifferenceDistributionLeg::isDebit)
				.map(leg -> leg.getAbsAmt().toBigDecimal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal sumCr(final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> legs)
	{
		return legs.stream()
				.filter(leg -> !leg.isDebit())
				.map(leg -> leg.getAbsAmt().toBigDecimal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}

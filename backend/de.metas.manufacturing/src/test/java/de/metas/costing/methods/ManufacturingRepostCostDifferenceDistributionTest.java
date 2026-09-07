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
import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
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
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.money.CurrencyId;
import de.metas.order.costs.OrderCostService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
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
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A manufacturing {@code CostDifferenceDistribution} collector persists THREE cost-detail legs
 * (MAIN + ADJUSTMENT + ALREADY_SHIPPED — see {@link PPOrderCostDifferenceDistributor#createCostDetails})
 * for AveragePO, LastPOPrice and MovingAverageInvoice alike. Reposting it must recover the FULL posting:
 * {@code CostingMethodHandler.createOrUpdateCost} short-circuits an existing posting through
 * {@code CostingMethodHandlerUtils.getExistingCostDetails(request)} →
 * {@link CostDetailService#getExistingCostDetails(CostDetailCreateRequest)}, which must NOT filter by the
 * request's default {@code amtType = MAIN}.
 * <p>
 * Pre-fix that filter truncated the recovery to the MAIN leg, so the aggregated amount lost the ADJUSTMENT
 * and ALREADY_SHIPPED shares — the same defect {@code MatchInvRepostCostDetailTest} pins for the MAI MatchInv.
 * This test drives the real repost path end-to-end (post the collector, then repost it) and fails if the
 * amtType filter is reintroduced.
 */
@ExtendWith(AdempiereTestWatcher.class)
class ManufacturingRepostCostDifferenceDistributionTest
{
	private static final Instant DATE = Instant.parse("2026-08-29T00:00:00Z");

	// issued 100 (= price 10 x qty 10) - received 60 (= price 6 x qty 10) => residual 40.
	// 8 of the 10 manufactured are still in stock at cost price 30 => capitalize 40 x 8/10 = 32, spill 8 to COGS.
	private static final String ISSUED_PRICE = "10";
	private static final String ISSUED_QTY = "-10";
	private static final String RECEIVED_PRICE = "6";
	private static final String RECEIVED_QTY = "10";
	private static final String MAIN_CURRENT_QTY = "8";
	private static final String MAIN_CURRENT_COST_PRICE = "30";

	private static final String EXPECTED_RESIDUAL = "40";
	private static final String EXPECTED_CAPITALIZED = "32";
	private static final String EXPECTED_ALREADY_SHIPPED = "8";

	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ofRepoId(0);

	private CurrencyId currencyId;
	private I_C_UOM uomEach;
	private ProductId mainProductId;
	private ProductId componentProductId;

	private CostElementRepository costElementRepo;
	private CostingMethodHandlerUtils utils;
	private PPOrderCostDifferenceDistributor distributor;

	// per-test, set up by setupOrderFor(..)
	private AcctSchemaId acctSchemaId;
	private CostElement costElement;
	private CostingMethodHandler handler;
	private PPOrderId orderId;
	private PPCostCollectorId distributionCollectorId;

	/** The multi-leg producers: each accumulates into {@code PP_Order_Cost} and can emit a CostDifferenceDistribution. */
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
		LastPO(CostingMethod.LastPOPrice)
				{
					@Override
					CostingMethodHandler createHandler(final CostingMethodHandlerUtils utils, final PPOrderCostDifferenceDistributor distributor)
					{
						return new ManufacturingLastPOCostingMethodHandler(utils, distributor);
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

		uomEach = BusinessTestHelper.createUomEach();
		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		mainProductId = BusinessTestHelper.createProductId("main product", uomEach);
		componentProductId = BusinessTestHelper.createProductId("component", uomEach);

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
	void costDifferenceDistribution_repost_recoversAllThreeLegs_notJustMain(final ManufacturingHandlerUnderTest handlerUnderTest)
	{
		setupOrderFor(handlerUnderTest);

		final CostDetailCreateRequest request = distributionRequest();

		// first posting: the collector routes to the distributor, which persists MAIN + ADJUSTMENT + ALREADY_SHIPPED
		handler.createOrUpdateCost(request);

		// the three legs really are persisted for this collector (i.e. the scenario genuinely is multi-leg)
		assertThat(utils.getExistingCostDetails(request))
				.as("a CostDifferenceDistribution posting persists all three legs")
				.extracting(CostDetail::getAmtType)
				.containsExactlyInAnyOrder(
						CostAmountType.MAIN,
						CostAmountType.ADJUSTMENT,
						CostAmountType.ALREADY_SHIPPED);

		// the repost: the exact recovery path CostingMethodHandler.createOrUpdateCost takes when the details exist
		final CostDetailCreateResultsList repost = handler.createOrUpdateCost(request);

		final AcctSchema as = utils.getAcctSchemaById(acctSchemaId);
		final CostAmountDetailed recovered = repost.getTotalAmountToPost(as);

		// leg-complete: pre-fix the recovery truncated to MAIN, so ADJUSTMENT + ALREADY_SHIPPED came back zero
		assertThat(recovered.getMainAmt().toBigDecimal())
				.as("MAIN leg")
				.isEqualByComparingTo(EXPECTED_RESIDUAL);
		assertThat(recovered.getCostAdjustmentAmt().toBigDecimal())
				.as("ADJUSTMENT leg must survive the repost (not truncated to MAIN)")
				.isEqualByComparingTo(EXPECTED_CAPITALIZED);
		assertThat(recovered.getAlreadyShippedAmt().toBigDecimal())
				.as("ALREADY_SHIPPED leg must survive the repost (not truncated to MAIN)")
				.isEqualByComparingTo(EXPECTED_ALREADY_SHIPPED);
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

		orderId = createCompletedPPOrder();
		distributionCollectorId = createCostDifferenceDistributionCollector();

		seedOrderCostsWithResidual();
		saveMainProductCurrentCost();
	}

	private PPOrderId createCompletedPPOrder()
	{
		final I_PP_Order order = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		InterfaceWrapperHelper.setValue(order, I_PP_Order.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
		order.setAD_Org_ID(orgId.getRepoId());
		order.setM_Product_ID(mainProductId.getRepoId());
		order.setDocStatus(DocStatus.Completed.getCode());
		InterfaceWrapperHelper.saveRecord(order);
		return PPOrderId.ofRepoId(order.getPP_Order_ID());
	}

	private PPCostCollectorId createCostDifferenceDistributionCollector()
	{
		final I_PP_Cost_Collector cc = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class);
		cc.setCostCollectorType(CostCollectorType.CostDifferenceDistribution.getCode());
		cc.setPP_Order_ID(orderId.getRepoId());
		cc.setMovementQty(BigDecimal.ZERO);
		InterfaceWrapperHelper.saveRecord(cc);
		return PPCostCollectorId.ofRepoId(cc.getPP_Cost_Collector_ID());
	}

	/** The {@code PP_Order_Cost} rows a completed order carries: an issue and a main-product receipt, leaving a residual. */
	private void seedOrderCostsWithResidual()
	{
		final BigDecimal issuedQty = new BigDecimal(ISSUED_QTY);
		final BigDecimal receivedQty = new BigDecimal(RECEIVED_QTY);

		// A component issue keeps the stock-movement direction in its qty (negative) while accumulating the cost
		// that went INTO the order as a positive amount.
		final PPOrderCost materialIssue = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MaterialIssue)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(requestFor(componentProductId, issuedQty)))
				.price(costPrice(ISSUED_PRICE))
				.accumulatedQty(Quantity.of(issuedQty, uomEach))
				.accumulatedAmount(CostAmount.of(new BigDecimal(ISSUED_PRICE).multiply(issuedQty).negate(), currencyId))
				.build();

		final PPOrderCost mainProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(requestFor(mainProductId, receivedQty)))
				.price(costPrice(RECEIVED_PRICE))
				.accumulatedQty(Quantity.of(receivedQty, uomEach))
				.accumulatedAmount(CostAmount.of(new BigDecimal(RECEIVED_PRICE).multiply(receivedQty), currencyId))
				.build();

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(orderId)
				.costs(ImmutableList.of(materialIssue, mainProduct))
				.build();

		// what every costing-method handler does after an issue or a receipt
		orderCosts.updatePostCalculationAmounts(CurrencyPrecision.ofInt(2));

		Services.get(IPPOrderCostBL.class).save(orderCosts);
	}

	private void saveMainProductCurrentCost()
	{
		final I_M_Cost cost = InterfaceWrapperHelper.newInstance(I_M_Cost.class);
		cost.setAD_Org_ID(orgId.getRepoId());
		cost.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		cost.setM_CostElement_ID(costElement.getId().getRepoId());
		cost.setM_CostType_ID(1);
		cost.setM_Product_ID(mainProductId.getRepoId());
		cost.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
		cost.setC_UOM_ID(uomEach.getC_UOM_ID());
		cost.setC_Currency_ID(currencyId.getRepoId());
		cost.setCurrentCostPrice(new BigDecimal(MAIN_CURRENT_COST_PRICE));
		cost.setCurrentQty(new BigDecimal(MAIN_CURRENT_QTY));
		InterfaceWrapperHelper.saveRecord(cost);
	}

	private CostPrice costPrice(@NonNull final String ownCostPrice)
	{
		return CostPrice.builder()
				.ownCostPrice(CostAmount.of(new BigDecimal(ownCostPrice), currencyId))
				.componentsCostPrice(CostAmount.zero(currencyId))
				.uomId(UomId.ofRepoId(uomEach.getC_UOM_ID()))
				.build();
	}

	/** What the manufacturing handler is handed for the CostDifferenceDistribution collector: the main product, no amount. */
	private CostDetailCreateRequest distributionRequest()
	{
		return requestFor(mainProductId, new BigDecimal(RECEIVED_QTY));
	}

	private CostDetailCreateRequest requestFor(@NonNull final ProductId productId, @NonNull final BigDecimal qty)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(clientId)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElement(costElement)
				.documentRef(CostingDocumentRef.ofCostCollectorId(distributionCollectorId))
				.qty(Quantity.of(qty, uomEach))
				.amt(CostAmount.zero(currencyId))
				.date(DATE)
				.build();
	}
}

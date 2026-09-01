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
import de.metas.acct.api.AcctSchemaId;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostPrice;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
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
 * Reposting a {@code PP_Cost_Collector} shall be idempotent: the second posting of the very same collector
 * must reuse the {@code M_CostDetail} records the first posting created, and must NOT mutate {@code M_Cost}
 * a second time.
 * <p>
 * The guarantee is implemented by the {@code getExistingCostDetails} short-circuit at the top of every
 * {@code CostingMethodHandler#createOrUpdateCost}; this test pins it for all three manufacturing handlers,
 * on both cost-collector branches that touch a current cost.
 */
@ExtendWith(AdempiereTestWatcher.class)
class ManufacturingRepostCostCollectorTest
{
	private static final Instant DATE = Instant.parse("2026-08-29T00:00:00Z");

	/** the component's current cost, i.e. the price an issue is valued at */
	private static final String COMPONENT_COST_PRICE = "10";
	private static final String COMPONENT_CURRENT_QTY = "100";
	/** a component issue is booked like a sales transaction, so the issued qty reaches the handler negative */
	private static final BigDecimal ISSUED_QTY = new BigDecimal("-10");
	/** {@link #COMPONENT_CURRENT_QTY} + {@link #ISSUED_QTY}, i.e. the current qty after exactly ONE issue */
	private static final String COMPONENT_CURRENT_QTY_AFTER_ONE_ISSUE = "90";

	/** the main product's current cost, i.e. the price a receipt is valued at */
	private static final String MAIN_PRODUCT_COST_PRICE = "20";
	private static final String MAIN_PRODUCT_CURRENT_QTY = "100";
	private static final BigDecimal RECEIVED_QTY = new BigDecimal("5");
	/** {@link #MAIN_PRODUCT_CURRENT_QTY} + {@link #RECEIVED_QTY}, i.e. the current qty after exactly ONE receipt */
	private static final String MAIN_PRODUCT_CURRENT_QTY_AFTER_ONE_RECEIPT = "105";

	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ANY;
	private final PPOrderId orderId = PPOrderId.ofRepoId(1);

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
	private PPCostCollectorId issueCollectorId;
	private PPCostCollectorId receiptCollectorId;

	/** The three manufacturing handlers; each one has to short-circuit a repost. */
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
	void componentIssue_repostedTwice_createsOneCostDetailAndMovesCurrentCostOnce(final ManufacturingHandlerUnderTest handlerUnderTest)
	{
		setupOrderFor(handlerUnderTest);

		final CostDetailCreateRequest request = issueRequest();
		handler.createOrUpdateCost(request);
		handler.createOrUpdateCost(request); // the repost

		assertThat(utils.getExistingCostDetails(request)).hasSize(1);

		final CurrentCost componentCost = utils.getCurrentCostForUpdate(request);
		assertThat(componentCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo(COMPONENT_CURRENT_QTY_AFTER_ONE_ISSUE);
		assertThat(componentCost.getCumulatedQty().toBigDecimal()).isEqualByComparingTo(ISSUED_QTY);
	}

	@ParameterizedTest
	@EnumSource(ManufacturingHandlerUnderTest.class)
	void mainProductReceipt_repostedTwice_createsOneCostDetailAndMovesCurrentCostOnce(final ManufacturingHandlerUnderTest handlerUnderTest)
	{
		setupOrderFor(handlerUnderTest);

		final CostDetailCreateRequest request = receiptRequest();
		handler.createOrUpdateCost(request);
		handler.createOrUpdateCost(request); // the repost

		assertThat(utils.getExistingCostDetails(request)).hasSize(1);

		final CurrentCost mainProductCost = utils.getCurrentCostForUpdate(request);
		assertThat(mainProductCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo(MAIN_PRODUCT_CURRENT_QTY_AFTER_ONE_RECEIPT);
		assertThat(mainProductCost.getCumulatedQty().toBigDecimal()).isEqualByComparingTo(RECEIVED_QTY);
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

		issueCollectorId = createCostCollector(CostCollectorType.ComponentIssue, ISSUED_QTY.negate());
		receiptCollectorId = createCostCollector(CostCollectorType.MaterialReceipt, RECEIVED_QTY);

		saveCurrentCost(componentProductId, COMPONENT_COST_PRICE, COMPONENT_CURRENT_QTY);
		saveCurrentCost(mainProductId, MAIN_PRODUCT_COST_PRICE, MAIN_PRODUCT_CURRENT_QTY);
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
	 * The rows {@code CreatePPOrderCostsCommand} leaves behind for a freshly created order. The main-product row is
	 * mandatory: post-calculation, which every handler runs after each collector, requires exactly one per cost element.
	 */
	private void createOrderCosts()
	{
		final PPOrderCost materialIssue = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MaterialIssue)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(issueRequest()))
				.price(costPrice(COMPONENT_COST_PRICE))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomEach))
				.build();

		final PPOrderCost mainProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(receiptRequest()))
				.price(costPrice("0"))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomEach))
				.build();

		Services.get(IPPOrderCostBL.class).save(PPOrderCosts.builder()
				.orderId(orderId)
				.costs(ImmutableList.of(materialIssue, mainProduct))
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

	/** what {@code DocLine_CostCollector} hands the handler for a ComponentIssue collector: a qty, and no amount */
	private CostDetailCreateRequest issueRequest()
	{
		return requestBuilder(componentProductId, issueCollectorId, ISSUED_QTY);
	}

	/** what {@code DocLine_CostCollector} hands the handler for a MaterialReceipt collector: a qty, and no amount */
	private CostDetailCreateRequest receiptRequest()
	{
		return requestBuilder(mainProductId, receiptCollectorId, RECEIVED_QTY);
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
				.amt(CostAmount.zero(currencyId)) // N/A - the handler values the movement at the product's current cost
				.date(DATE)
				.build();
	}
}

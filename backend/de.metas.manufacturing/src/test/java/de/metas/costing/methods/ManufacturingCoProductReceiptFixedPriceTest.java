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
import org.compiere.model.I_M_Product;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Leg B of the co-product fixed-price valuation: when a co-product's OWN receipt is booked, it must be valued at
 * the co-product product's manual {@code M_Product.CoProductFixedCostPrice} (× received qty, rounded) instead of
 * the product's live {@code CurrentCostPrice}. Because leg B reuses the same {@code CoProductFixedCostPrices}
 * predicate as leg A (the post-calculation relief in {@code PPOrderCosts}), the two legs produce the IDENTICAL
 * co-product amount so cost is conserved.
 * <p>
 * The handler is driven through its real entry point {@code createOrUpdateCost}, with the co-product receipt qty
 * supplied POSITIVE exactly as {@code DocLine_CostCollector} hands it over (it already negates the collector's
 * negative co-product movement qty — {@code OrderBOMLineQuantities.adjustCoProductQty} stores it negated).
 * <p>
 * Only {@link CostingMethod#AveragePO} is covered here. The other in-scope method
 * ({@link CostingMethod#MovingAverageInvoice}) carries the same fixed-price edit, but its co-product-receipt
 * branch applies a further internal qty negation, so at the handler-entry level the faithful request sign cannot
 * be pinned without reproducing the full manufacturing workflow; its receipt valuation is covered end-to-end by
 * the manufacturing cost-collector Cucumber scenarios. {@link CostingMethod#LastPOPrice} keeps current-cost
 * valuation and is deliberately not covered.
 */
@ExtendWith(AdempiereTestWatcher.class)
class ManufacturingCoProductReceiptFixedPriceTest
{
	private static final Instant DATE = Instant.parse("2026-09-14T00:00:00Z");

	/** the input component: 30 @ 15 => a 450 input cost pool */
	private static final String INPUT_COST_PRICE = "15";
	private static final String INPUT_CURRENT_QTY = "1000";
	private static final BigDecimal ISSUED_QTY = new BigDecimal("-30");
	private static final String INPUT_COST_POOL = "450";

	/** the co-product: fixed 8 is what it MUST be valued at; 20 is its (discriminating) current cost */
	private static final String CO_PRODUCT_FIXED_PRICE = "8";
	private static final String CO_PRODUCT_CURRENT_PRICE = "20";
	private static final String CO_PRODUCT_CURRENT_QTY = "1000";
	private static final BigDecimal CO_PRODUCT_RECEIVED_QTY = new BigDecimal("6");
	/** {@link #CO_PRODUCT_FIXED_PRICE} × {@link #CO_PRODUCT_RECEIVED_QTY} = 48, NOT current 20 × 6 = 120 */
	private static final String CO_PRODUCT_FIXED_AMOUNT = "48";
	/** {@link #INPUT_COST_POOL} − {@link #CO_PRODUCT_FIXED_AMOUNT} = 402 (cost conservation) */
	private static final String MAIN_PRODUCT_REMAINDER = "402";

	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ANY;
	private final PPOrderId orderId = PPOrderId.ofRepoId(1);

	private CurrencyId currencyId;
	private I_C_UOM uomEach;
	private ProductId mainProductId;
	private ProductId componentProductId;
	private ProductId coProductId;

	private CostElementRepository costElementRepo;
	private CostingMethodHandlerUtils utils;
	private PPOrderCostDifferenceDistributor distributor;

	// per-test, set up by setupAveragePOOrder()
	private AcctSchemaId acctSchemaId;
	private CostElement costElement;
	private CostingMethodHandler handler;
	private PPCostCollectorId issueCollectorId;
	private PPCostCollectorId coProductReceiptCollectorId;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), clientId);

		uomEach = BusinessTestHelper.createUomEach();
		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		mainProductId = BusinessTestHelper.createProductId("main product", uomEach);
		componentProductId = BusinessTestHelper.createProductId("component", uomEach);
		coProductId = createCoProductWithFixedPrice();

		// the costing level is what the cost segment is built from; the costing method is only asked for products
		Services.registerService(IProductCostingBL.class, new MockedProductCostingBL(CostingLevel.Client, CostingMethod.AveragePO));

		costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		utils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				new CurrentCostsRepository(costElementRepo),
				new CostDetailService(new CostDetailRepository(), costElementRepo));
		distributor = new PPOrderCostDifferenceDistributor(costElementRepo, utils);
	}

	@Test
	void coProductReceipt_withFixedPrice_bookedAtFixedPriceAndConserved()
	{
		setupAveragePOOrder();

		// issue the input first (builds the 450 inbound pool), then receive the co-product
		handler.createOrUpdateCost(issueRequest());
		handler.createOrUpdateCost(coProductReceiptRequest());

		// leg B: the co-product's own receipt is booked at the FIXED price (48), never its current cost (120)
		final CurrentCost coProductCurrentCost = utils.getCurrentCostForUpdate(coProductReceiptRequest());
		assertThat(coProductCurrentCost.getCumulatedAmt().toBigDecimal())
				.isEqualByComparingTo(CO_PRODUCT_FIXED_AMOUNT);

		// leg A == leg B: the co-product post-calc amount equals the receipt valuation; the main product is
		// relieved by the remainder (cost conserved).
		final PPOrderCosts orderCosts = Services.get(IPPOrderCostBL.class).getByOrderId(orderId);
		assertThat(postCalcAmount(orderCosts, coProductReceiptRequest()))
				.isEqualByComparingTo(CO_PRODUCT_FIXED_AMOUNT);
		assertThat(postCalcAmount(orderCosts, mainReceiptRequest()))
				.isEqualByComparingTo(MAIN_PRODUCT_REMAINDER);
	}

	//
	//
	// fixture
	//
	//

	private BigDecimal postCalcAmount(@NonNull final PPOrderCosts orderCosts, @NonNull final CostDetailCreateRequest request)
	{
		final CostSegmentAndElement segment = utils.extractCostSegmentAndElement(request);
		return orderCosts.getByCostSegmentAndElement(segment)
				.orElseThrow(() -> new AssertionError("No PP_Order_Cost row for " + segment))
				.getPostCalculationAmount()
				.toBigDecimal();
	}

	private ProductId createCoProductWithFixedPrice()
	{
		final I_M_Product coProduct = BusinessTestHelper.createProduct("co-product output", uomEach);
		coProduct.setCoProductFixedCostPrice(new BigDecimal(CO_PRODUCT_FIXED_PRICE));
		InterfaceWrapperHelper.saveRecord(coProduct);
		return ProductId.ofRepoId(coProduct.getM_Product_ID());
	}

	private void setupAveragePOOrder()
	{
		acctSchemaId = AcctSchemaTestHelper.newAcctSchema()
				.costingLevel(CostingLevel.Client)
				.costingMethod(CostingMethod.AveragePO)
				.currencyId(currencyId)
				.build();
		costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		handler = new ManufacturingAveragePOCostingMethodHandler(
				utils,
				distributor,
				new AveragePOCostingMethodHandler(
						utils,
						MatchInvoiceService.newInstanceForUnitTesting(),
						OrderCostService.newInstanceForUnitTesting()));

		issueCollectorId = createCostCollector(CostCollectorType.ComponentIssue, ISSUED_QTY.negate());
		coProductReceiptCollectorId = createCostCollector(CostCollectorType.MixVariance, CO_PRODUCT_RECEIVED_QTY);

		saveCurrentCost(componentProductId, INPUT_COST_PRICE, INPUT_CURRENT_QTY);
		saveCurrentCost(mainProductId, "0", "0");
		saveCurrentCost(coProductId, CO_PRODUCT_CURRENT_PRICE, CO_PRODUCT_CURRENT_QTY);
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
	 * The order-cost rows {@code CreatePPOrderCostsCommand} leaves behind: the mandatory main-product row, the
	 * component (material-issue, inbound) row, and the co-product row that post-calculation reprices.
	 */
	private void createOrderCosts()
	{
		final PPOrderCost materialIssue = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MaterialIssue)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(issueRequest()))
				.price(costPrice(INPUT_COST_PRICE))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomEach))
				.build();

		final PPOrderCost mainProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(mainReceiptRequest()))
				.price(costPrice("0"))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomEach))
				.build();

		final PPOrderCost coProduct = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.CoProduct)
				.costSegmentAndElement(utils.extractCostSegmentAndElement(coProductReceiptRequest()))
				.price(costPrice(CO_PRODUCT_CURRENT_PRICE))
				.accumulatedAmount(CostAmount.zero(currencyId))
				.accumulatedQty(Quantity.zero(uomEach))
				.coProductCostDistributionPercent(Percent.of(100))
				.build();

		Services.get(IPPOrderCostBL.class).save(PPOrderCosts.builder()
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

	/** what {@code DocLine_CostCollector} hands the handler for a ComponentIssue collector: a negative qty, no amount */
	private CostDetailCreateRequest issueRequest()
	{
		return requestBuilder(componentProductId, issueCollectorId, ISSUED_QTY);
	}

	/**
	 * what {@code DocLine_CostCollector} hands the handler for a co-product (MixVariance) receipt: a POSITIVE
	 * "received" qty ({@code DocLine_CostCollector} already negates the collector's negative movement qty), no amount.
	 */
	private CostDetailCreateRequest coProductReceiptRequest()
	{
		return requestBuilder(coProductId, coProductReceiptCollectorId, CO_PRODUCT_RECEIVED_QTY);
	}

	/** segment-only helper for the main product (the receipt itself is not posted in this test) */
	private CostDetailCreateRequest mainReceiptRequest()
	{
		return requestBuilder(mainProductId, coProductReceiptCollectorId, BigDecimal.ONE);
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
				.amt(CostAmount.zero(currencyId)) // N/A - the handler values the movement itself
				.date(DATE)
				.build();
	}
}

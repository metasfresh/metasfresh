package org.eevolution.api;

import de.metas.acct.api.AcctSchemaId;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PPOrderCostsTest
{
	private final PPOrderId ppOrderId = PPOrderId.ofRepoId(1);

	private final CurrencyPrecision costingPrecision = CurrencyPrecision.ofInt(4);
	private final CurrencyId currencyId = CurrencyId.ofRepoId(1);

	private final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(1);
	private final CostTypeId costTypeId = CostTypeId.ofRepoId(1);
	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ofRepoId(1);
	private final CostElementId costElementId = CostElementId.ofRepoId(1);

	private final ProductId productId1 = ProductId.ofRepoId(1);
	private final ProductId productId2 = ProductId.ofRepoId(2);

	private I_C_UOM uom;
	private UomId uomId;
	private FixedCostPriceProvider fixedCostPriceProvider;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUomEach();
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());
		fixedCostPriceProvider = Services.get(IPPOrderCostBL.class);
	}

	@Test
	public void testPostCalculation_SimpleCase()
	{
		// The co-product AND by-product lines must reference real M_Product rows: the post-calc reads each co-product's
		// master live for the fixed-price relief, and each by-product's master live for the by-product fixed-price
		// reject-guard (both via IProductDAO.getById, fail-loud) — so a fabricated ProductId with no M_Product row would
		// throw. Blank fixed price -> today's qty-distribution behaviour is exercised.
		final ProductId coProductId3 = createProduct("coproduct_20pct", null);
		final ProductId coProductId4 = createProduct("coproduct_10pct", null);
		final ProductId byProductId = createProduct("byproduct", null);

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(productId1))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(productId2))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(100, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductId3))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductId4))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(10))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.ByProduct)
						.costSegmentAndElement(costSegmentAndElement(byProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		this.assertThatPostCalculationAmt(orderCosts, productId1).isEqualByComparingTo(new BigDecimal("70"));
		this.assertThatPostCalculationAmt(orderCosts, productId2).isEqualByComparingTo(new BigDecimal("100"));
		this.assertThatPostCalculationAmt(orderCosts, coProductId3).isEqualByComparingTo(new BigDecimal("20"));
		this.assertThatPostCalculationAmt(orderCosts, coProductId4).isEqualByComparingTo(new BigDecimal("10"));
		this.assertThatPostCalculationAmt(orderCosts, byProductId).isEqualByComparingTo(new BigDecimal("0"));
	}

	/**
	 * AC5 seam: a co-product whose product carries a (would-be) manual fixed cost price is STILL valued at
	 * {@code percent x pool} via {@code computeBlankCoProductAmount}, NOT at {@code fixedPrice x qty} — the
	 * {@code FixedCostPriceProvider} leg is no longer consulted by the post-calculation at all. Same pool/percent
	 * as the fixed-price scenario this replaces (450 pool, 20%, Randstücke 6 kg, field set to 8) would previously
	 * have produced 48 (fixedPrice x qty); the collapsed seam now produces 90 (percent x pool) regardless.
	 */
	@Test
	public void testCoProductWithFixedPriceFieldSet_valuedByPercentTimesPool_notFixedPriceTimesQty()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId coProductId = createProduct("Randstuecke", new BigDecimal("8"));

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		// percent(20%) x pool(450) = 90, NOT fixedPrice(8) x qty(6) = 48
		this.assertThatPostCalculationAmt(orderCosts, coProductId).isEqualByComparingTo(new BigDecimal("90"));
		// main relieved by the remainder 450 - 90 = 360
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("360"));
		// cost conserved: Sigma(outputs) = totalInbound
		final BigDecimal sumOutputs = getPostCalculationCostAmt(orderCosts, mainProductId).toBigDecimal()
				.add(getPostCalculationCostAmt(orderCosts, coProductId).toBigDecimal());
		assertThat(sumOutputs).isEqualByComparingTo(new BigDecimal("450"));
	}

	/**
	 * The bare conservation guard (Task 8 later refines it into the AC6 {@code Sigma p <= 100%} guard): when the
	 * co-products' percent-carved total exceeds the order's input cost pool, the main product would go negative —
	 * costing must reject BEFORE persisting a negative main-product amount. Here percent 120% x pool 450 = 540 > 450.
	 */
	@Test
	public void testConservationGuard_throwsWhenCoProductsExceedPool()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId coProductId = createProduct("overclaimed_coproduct", null);

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(120))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.build();

		// percent(120%) x pool(450) = 540 > totalInbound 450 -> main would go negative -> guard must reject
		assertThatThrownBy(() -> orderCosts.updatePostCalculationAmountsForCostElement(costingPrecision, costElementId, CostingMethod.AveragePO, fixedCostPriceProvider))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("540")
				.hasMessageContaining("450");

		// the guard must reject BEFORE persisting a negative main-product amount
		assertThat(getPostCalculationCostAmt(orderCosts, mainProductId).toBigDecimal())
				.isGreaterThanOrEqualTo(BigDecimal.ZERO);
	}

	/**
	 * By-products are always zeroed, unconditionally — even when the product carries a (would-be) fixed-price
	 * field, since the fixed-price leg is no longer consulted. No throw, no by-product-specific guard.
	 */
	@Test
	public void testByProduct_alwaysZeroed_regardlessOfFixedPriceField()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId byProductId = createProduct("whey_feed", new BigDecimal("8"));

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.ByProduct)
						.costSegmentAndElement(costSegmentAndElement(byProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.of(new BigDecimal("2"), uom))
						.build())
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		this.assertThatPostCalculationAmt(orderCosts, byProductId).isEqualByComparingTo(BigDecimal.ZERO);
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("450"));
	}

	/**
	 * AC6 no-regression / formula: a BLANK fixed-price field leaves the qty-distribution formula intact - the
	 * co-product is valued by {@code coProductCostDistributionPercent x pool} (450 * 20% = 90), main = 360.
	 */
	@Test
	public void testBlankFixedPriceField_percentDistributionFormulaApplies()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId coProductId = createProduct("Randstuecke", null); // BLANK fixed price field

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		this.assertThatPostCalculationAmt(orderCosts, coProductId).isEqualByComparingTo(new BigDecimal("90"));
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("360"));
		final BigDecimal sumOutputs = getPostCalculationCostAmt(orderCosts, mainProductId).toBigDecimal()
				.add(getPostCalculationCostAmt(orderCosts, coProductId).toBigDecimal());
		assertThat(sumOutputs).isEqualByComparingTo(new BigDecimal("450"));
	}

	/**
	 * Explicit 0% (not null/blank) must carve zero too - {@code computeBlankCoProductAmount}'s guard is
	 * {@code signum() <= 0}, so a co-product that explicitly distributes 0% claims nothing from the pool.
	 */
	@Test
	public void testExplicitZeroPercent_carvesZero()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId coProductId = createProduct("zero_pct_coproduct", null);

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(0)) // explicit 0%, not null
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		this.assertThatPostCalculationAmt(orderCosts, coProductId).isEqualByComparingTo(BigDecimal.ZERO);
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("450"));
	}

	/**
	 * AC12: adding a second co-product must leave the first co-product's carve unchanged - each co-product's
	 * amount is {@code percent x pool} independently, not a remainder-based split among co-products.
	 */
	@Test
	public void testSecondCoProduct_doesNotChangeFirstCoProductsCarve()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId coProductAId = createProduct("coproduct_a", null);
		final ProductId coProductBId = createProduct("coproduct_b", null);

		final PPOrderCosts orderCostsAOnly = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductAId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.build();
		orderCostsAOnly.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		final PPOrderCosts orderCostsAAndB = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductAId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductBId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(10))
						.accumulatedQty(Quantity.of(new BigDecimal("3"), uom))
						.build())
				.build();
		orderCostsAAndB.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		// A's carve (450 * 20% = 90) is IDENTICAL whether B is present or not
		this.assertThatPostCalculationAmt(orderCostsAOnly, coProductAId).isEqualByComparingTo(new BigDecimal("90"));
		this.assertThatPostCalculationAmt(orderCostsAAndB, coProductAId).isEqualByComparingTo(new BigDecimal("90"));
	}

	/**
	 * AC13 pool-cap: a co-product's carve is {@code percent x pool}, independent of its received qty
	 * ({@code accumulatedQty}) - a larger received quantity must NOT raise the co-product's total claim on the
	 * pool. Same percent (20%) and pool (450), qty 1 vs qty 100 -> identical 90 carve both times.
	 */
	@Test
	public void testReceivedQtyDoesNotRaiseTotalClaim()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId coProductSmallQtyId = createProduct("coproduct_smallqty", null);
		final ProductId coProductLargeQtyId = createProduct("coproduct_largeqty", null);

		final PPOrderCosts orderCostsSmallQty = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductSmallQtyId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.of(new BigDecimal("1"), uom))
						.build())
				.build();
		orderCostsSmallQty.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		final PPOrderCosts orderCostsLargeQty = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductLargeQtyId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.of(new BigDecimal("100"), uom))
						.build())
				.build();
		orderCostsLargeQty.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		this.assertThatPostCalculationAmt(orderCostsSmallQty, coProductSmallQtyId).isEqualByComparingTo(new BigDecimal("90"));
		this.assertThatPostCalculationAmt(orderCostsLargeQty, coProductLargeQtyId).isEqualByComparingTo(new BigDecimal("90"));
	}

	/**
	 * A blank-fixed-price co-product whose {@code coProductCostDistributionPercent} is NULL (the DAO leaves it
	 * nullable, especially under Moving Average Invoice) must NOT NPE in the post-calculation: a null / non-positive
	 * percent yields a ZERO co-product share, so the main product keeps the full input pool. Pre-fix this threw an
	 * NPE at {@code totalInbound.multiply(null, precision)}.
	 */
	@Test
	public void testBlankCoProduct_nullDistributionPercent_noNpe_zeroShare()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId coProductId = createProduct("Randstuecke", null); // BLANK fixed price

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductId))
						.price(CostPrice.zero(currencyId, uomId))
						// coProductCostDistributionPercent intentionally NOT set -> null
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		// null distribution percent -> zero co-product share (no NPE); main keeps the full pool
		this.assertThatPostCalculationAmt(orderCosts, coProductId).isEqualByComparingTo(BigDecimal.ZERO);
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("450"));
	}

	/**
	 * The blank-fixed-price invariant that makes the order's WIP clear: leg B (the co-product receipt valuation in
	 * the costing-method handlers, via {@link PPOrderCosts#getBlankCoProductReceiptAmount}) must book the IDENTICAL
	 * amount as leg A (the co-product's post-calculation relief). Here 450 pool x 1/6 = 75.0002 at precision 4.
	 */
	@Test
	public void getBlankCoProductReceiptAmount_matchesLegAPostCalculation()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId coProductId = createProduct("Randstuecke", null); // BLANK fixed price

		final CostSegmentAndElement coProductSegment = costSegmentAndElement(coProductId);
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(coProductSegment)
						.price(CostPrice.zero(currencyId, uomId))
						// the real 1/qty distribution the BOM assigns (Percent.of(1, coQty=6, precision 4))
						.coProductCostDistributionPercent(Percent.of(BigDecimal.ONE, new BigDecimal("6"), 4))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		final CostAmount legA_postCalculationAmount = getPostCalculationCostAmt(orderCosts, coProductId);
		final CostAmount legB_receiptAmount = orderCosts.getBlankCoProductReceiptAmount(coProductSegment, costingPrecision);

		// leg B books exactly what leg A relieved -> co-product residual is 0 -> the order's WIP clears
		assertThat(legB_receiptAmount).isEqualTo(legA_postCalculationAmount);
		// and it is the 1/qty share of the 450 pool (450 x 1/6 = 75.0002 at precision 4)
		assertThat(legB_receiptAmount.toBigDecimal()).isEqualByComparingTo(new BigDecimal("75.0002"));
	}

	@Test
	public void updatePriceForCostSegmentAndElement_setsPrice()
	{
		final CostSegmentAndElement costSegmentAndElement = costSegmentAndElement(productId1);

		final CostPrice initialPrice = CostPrice.ownCostPrice(CostAmount.of(10, currencyId), uomId);
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement)
						.price(initialPrice)
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.build();

		final CostPrice newPrice = CostPrice.ownCostPrice(CostAmount.of(25, currencyId), uomId);
		orderCosts.updatePriceForCostSegmentAndElement(costSegmentAndElement, newPrice);

		assertThat(orderCosts.getPriceByCostSegmentAndElement(costSegmentAndElement))
				.contains(newPrice);
	}

	/**
	 * The main product's post-calculation amount is the total INPUT cost of the order, so it must NOT count what
	 * the outputs already accumulated.
	 */
	@Test
	public void testPostCalculation_MainProductWithAccumulatedAmount()
	{
		// 340 issued into the order (10 PCE of a component at 34), 300 received (10 PCE of the output at 30)
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "30", "10", "300"))
				.cost(materialIssueCost(productId2, "34", "-10", "340"))
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		this.assertThatPostCalculationAmt(orderCosts, productId1).isEqualByComparingTo(new BigDecimal("340"));
		this.assertThatPostCalculationAmt(orderCosts, productId2).isEqualByComparingTo(new BigDecimal("340"));
	}

	@Test
	public void getResidualCost_isIssuedMinusReceived()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "30", "10", "300"))
				.cost(materialIssueCost(productId2, "34", "-10", "340"))
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, CostingMethod.AveragePO, fixedCostPriceProvider);

		// 340 issued - 300 received
		assertThat(orderCosts.getResidualCost(acctSchemaId, costElementId))
				.isEqualTo(CostAmount.of(40, currencyId));
	}

	@Test
	public void getResidualCost_isNullWhenThereIsNoMainProductRowForThatSchemaAndElement()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "30", "10", "300"))
				.cost(materialIssueCost(productId2, "34", "-10", "340"))
				.build();

		assertThat(orderCosts.getResidualCost(AcctSchemaId.ofRepoId(2), costElementId)).isNull();
		assertThat(orderCosts.getResidualCost(acctSchemaId, CostElementId.ofRepoId(2))).isNull();
	}

	/**
	 * A completed order that received value with NOTHING issued: every inbound row is zero, so the residual
	 * degenerates into minus the whole receipt and there is no cost difference to disposition.
	 */
	@Test
	public void hasNoInboundCosts_whenNothingWasIssued()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "10", "1", "100"))
				.cost(materialIssueCost(productId2, "10", "0", "0"))
				.build();

		assertThat(orderCosts.hasInboundCosts(acctSchemaId, costElementId)).isFalse();
	}

	@Test
	public void hasInboundCosts_whenAComponentWasIssued()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "10", "1", "100"))
				.cost(materialIssueCost(productId2, "10", "-5", "-50"))
				.build();

		assertThat(orderCosts.hasInboundCosts(acctSchemaId, costElementId)).isTrue();
	}

	/** Only the inbound rows count: a receipt on its own is not an issued cost. */
	@Test
	public void hasNoInboundCosts_whenTheOrderCarriesOnlyItsMainProductRow()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "10", "1", "100"))
				.build();

		assertThat(orderCosts.hasInboundCosts(acctSchemaId, costElementId)).isFalse();
	}

	private PPOrderCost mainProductCost(
			@NonNull final ProductId productId,
			@NonNull final String price,
			@NonNull final String accumulatedQty,
			@NonNull final String accumulatedAmount)
	{
		return orderCost(PPOrderCostTrxType.MainProduct, productId, price, accumulatedQty, accumulatedAmount);
	}

	private PPOrderCost materialIssueCost(
			@NonNull final ProductId productId,
			@NonNull final String price,
			@NonNull final String accumulatedQty,
			@NonNull final String accumulatedAmount)
	{
		return orderCost(PPOrderCostTrxType.MaterialIssue, productId, price, accumulatedQty, accumulatedAmount);
	}

	private PPOrderCost orderCost(
			@NonNull final PPOrderCostTrxType trxType,
			@NonNull final ProductId productId,
			@NonNull final String price,
			@NonNull final String accumulatedQty,
			@NonNull final String accumulatedAmount)
	{
		return PPOrderCost.builder()
				.trxType(trxType)
				.costSegmentAndElement(costSegmentAndElement(productId))
				.price(CostPrice.ownCostPrice(CostAmount.of(new BigDecimal(price), currencyId), uomId))
				.accumulatedQty(Quantity.of(new BigDecimal(accumulatedQty), uom))
				.accumulatedAmount(CostAmount.of(new BigDecimal(accumulatedAmount), currencyId))
				.build();
	}

	/**
	 * Creates a real {@code M_Product} record (so the fixed-price relief can read it live via the product's id) and
	 * returns its {@link ProductId}. Pass a non-null {@code coProductFixedCostPrice} to set the manual field.
	 */
	private ProductId createProduct(@NonNull final String name, @Nullable final BigDecimal coProductFixedCostPrice)
	{
		final I_M_Product product = BusinessTestHelper.createProduct(name, uom);
		if (coProductFixedCostPrice != null)
		{
			product.setCoProductFixedCostPrice(coProductFixedCostPrice);
			InterfaceWrapperHelper.save(product);
		}
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private CostSegmentAndElement costSegmentAndElement(@NonNull final ProductId productId)
	{
		return CostSegmentAndElement.builder()
				.costingLevel(CostingLevel.Organization)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeId)
				.clientId(clientId)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElementId(costElementId)
				.build();
	}

	private CostAmount getPostCalculationCostAmt(
			final PPOrderCosts orderCosts,
			final ProductId productId)
	{
		return orderCosts.getByCostSegmentAndElement(costSegmentAndElement(productId))
				.get()
				.getPostCalculationAmount();
	}

	private AbstractBigDecimalAssert<?> assertThatPostCalculationAmt(
			final PPOrderCosts orderCosts,
			final ProductId productId)
	{
		return assertThat(getPostCalculationCostAmt(orderCosts, productId).toBigDecimal());
	}
}

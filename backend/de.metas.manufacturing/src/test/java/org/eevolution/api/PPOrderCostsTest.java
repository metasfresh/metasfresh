package org.eevolution.api;

import de.metas.acct.api.AcctSchemaId;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUomEach();
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());
	}

	@Test
	public void testPostCalculation_SimpleCase()
	{
		// The co-product AND by-product lines must reference real M_Product rows: the post-calc resolves each
		// product's master live (via IProductDAO.getById, fail-loud) — so a fabricated ProductId with no
		// M_Product row would throw.
		final ProductId coProductId3 = createProduct("coproduct_20pct");
		final ProductId coProductId4 = createProduct("coproduct_10pct");
		final ProductId byProductId = createProduct("byproduct");

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

		orderCosts.updatePostCalculationAmounts(costingPrecision);

		this.assertThatPostCalculationAmt(orderCosts, productId1).isEqualByComparingTo(new BigDecimal("70"));
		this.assertThatPostCalculationAmt(orderCosts, productId2).isEqualByComparingTo(new BigDecimal("100"));
		this.assertThatPostCalculationAmt(orderCosts, coProductId3).isEqualByComparingTo(new BigDecimal("20"));
		this.assertThatPostCalculationAmt(orderCosts, coProductId4).isEqualByComparingTo(new BigDecimal("10"));
		this.assertThatPostCalculationAmt(orderCosts, byProductId).isEqualByComparingTo(new BigDecimal("0"));
	}

	/**
	 * A co-product carrying a manual cost price on its product is still valued by its cost distribution percent
	 * times the total inbound costs: 450 total inbound costs × 20% = 90 (Randstücke, 6 kg). The manual cost price is not consulted.
	 */
	@Test
	public void testCoProductWithDistributionPercentSet_valuedByPercentTimesTotalInboundCosts_notManualCostPriceTimesQty()
	{
		final ProductId mainProductId = createProduct("blocks_main");
		final ProductId issueProductId = createProduct("input_milk");
		final ProductId coProductId = createProduct("Randstuecke");

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

		orderCosts.updatePostCalculationAmounts(costingPrecision);

		// percent(20%) × total inbound costs(450) = 90
		this.assertThatPostCalculationAmt(orderCosts, coProductId).isEqualByComparingTo(new BigDecimal("90"));
		// main relieved by the remainder 450 - 90 = 360
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("360"));
		// cost conserved: Sigma(outputs) = totalInbound
		final BigDecimal sumOutputs = getPostCalculationCostAmt(orderCosts, mainProductId).toBigDecimal()
				.add(getPostCalculationCostAmt(orderCosts, coProductId).toBigDecimal());
		assertThat(sumOutputs).isEqualByComparingTo(new BigDecimal("450"));
	}

	/**
	 * AC6 {@code Sigma p <= 100%} guard, single-offender case: the co-products' distribution percent must not
	 * exceed 100% of the total inbound costs, in PERCENT-space — checked and rejected BEFORE any amount is carved from the total inbound costs
	 * (and therefore before the main product could ever be driven negative). Here a single co-product claims 120%.
	 */
	@Test
	public void testConservationGuard_throwsWhenCoProductsExceedTotalInboundCosts()
	{
		final ProductId mainProductId = createProduct("blocks_main");
		final ProductId issueProductId = createProduct("input_milk");
		final ProductId coProductId = createProduct("overclaimed_coproduct");

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

		// Sigma p = 120% > 100% -> guard must reject in percent-space, naming the offending product + the sum
		assertThatThrownBy(() -> orderCosts.updatePostCalculationAmountsForCostElement(costingPrecision, costElementId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("120%")
				.hasMessageContaining("overclaimed_coproduct");

		// the guard must reject BEFORE persisting a negative main-product amount
		assertThat(getPostCalculationCostAmt(orderCosts, mainProductId).toBigDecimal())
				.isGreaterThanOrEqualTo(BigDecimal.ZERO);
	}

	/**
	 * AC6 {@code Sigma p <= 100%} guard, two-co-product case: no single co-product exceeds 100% on its own, but
	 * their SUM does (60% + 50% = 110%) - the guard must still reject, naming BOTH offending products and the sum.
	 */
	@Test
	public void testConservationGuard_throwsWhenTwoCoProductsSumExceedsTotalInboundCosts()
	{
		final ProductId mainProductId = createProduct("blocks_main");
		final ProductId issueProductId = createProduct("input_milk");
		final ProductId coProductAId = createProduct("overclaimed_coproduct_a");
		final ProductId coProductBId = createProduct("overclaimed_coproduct_b");

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
						.costSegmentAndElement(costSegmentAndElement(coProductAId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(60))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductBId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(50))
						.accumulatedQty(Quantity.of(new BigDecimal("3"), uom))
						.build())
				.build();

		// Sigma p = 60% + 50% = 110% > 100% -> guard must reject, naming both products + the sum
		assertThatThrownBy(() -> orderCosts.updatePostCalculationAmountsForCostElement(costingPrecision, costElementId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("110%")
				.hasMessageContaining("overclaimed_coproduct_a")
				.hasMessageContaining("overclaimed_coproduct_b");

		// the guard must reject BEFORE persisting a negative main-product amount
		assertThat(getPostCalculationCostAmt(orderCosts, mainProductId).toBigDecimal())
				.isGreaterThanOrEqualTo(BigDecimal.ZERO);
	}

	/**
	 * A co-product-free order (a return / cost-correction) whose net inbound cost is NEGATIVE must set the main
	 * product's post-calculation amount to that negative value and NOT throw. The negative-main guard is scoped to
	 * orders that actually carry co-products (where {@code Sigma p <= 100%} makes main {@code >= 0} by construction);
	 * with no co-products there is nothing to conserve against, so the historical pass-through applies.
	 */
	@Test
	public void testNegativeMainProduct_noCoProducts_passesThroughWithoutThrow()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "0", "0", "0"))
				.cost(materialIssueCost(productId2, "0", "0", "-50"))
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision);

		this.assertThatPostCalculationAmt(orderCosts, productId1).isEqualByComparingTo(new BigDecimal("-50"));
	}

	/**
	 * Backstop absorb-band: with {@code Sigma p} at exactly 100% the percent guard passes, but the two co-product
	 * carves are rounded to the costing precision INDEPENDENTLY, so their rounded sum can overshoot the total
	 * inbound costs by up to one currency ulp per co-product - driving the main product a sub-precision amount
	 * negative. That is rounding noise, not a conservation breach: the backstop must ABSORB it (clamp the main
	 * product to zero and net the overshoot onto the largest co-product carve) so {@code Sigma(outputs)} equals
	 * the total inbound costs exactly and the order's WIP still nets to zero - it must NOT throw.
	 * <p>
	 * Here total inbound costs = 1, p1 = 50.005% -> carve rounds UP to 0.5001, p2 = 49.995% -> carve 0.5000, so the
	 * rounded co-products sum to 1.0001 and the raw main product is 1 - 1.0001 = -0.0001 (one ulp at precision 4).
	 */
	@Test
	public void testBackstop_absorbsSubPrecisionRoundingOvershoot_clampsMainAndNetsOntoLargestCoProduct()
	{
		final ProductId mainProductId = createProduct("blocks_main");
		final ProductId issueProductId = createProduct("input_milk");
		final ProductId coProductLargerId = createProduct("coproduct_50_005pct"); // carve rounds up to 0.5001
		final ProductId coProductSmallerId = createProduct("coproduct_49_995pct"); // carve 0.5000

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
						.accumulatedAmount(CostAmount.of(1, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductLargerId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of("50.005"))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductSmallerId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of("49.995"))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.build();

		// Sigma p = 100.000% -> percent guard passes; the raw main product is -0.0001 -> backstop must absorb, not throw.
		orderCosts.updatePostCalculationAmounts(costingPrecision);

		// main clamped to zero
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(BigDecimal.ZERO);
		// the overshoot is netted onto the LARGEST carve: 0.5001 - 0.0001 = 0.5000; the smaller carve is untouched
		this.assertThatPostCalculationAmt(orderCosts, coProductLargerId).isEqualByComparingTo(new BigDecimal("0.5000"));
		this.assertThatPostCalculationAmt(orderCosts, coProductSmallerId).isEqualByComparingTo(new BigDecimal("0.5000"));
		// cost conserved: Sigma(outputs) == total inbound costs exactly -> WIP nets to zero
		final BigDecimal sumOutputs = getPostCalculationCostAmt(orderCosts, mainProductId).toBigDecimal()
				.add(getPostCalculationCostAmt(orderCosts, coProductLargerId).toBigDecimal())
				.add(getPostCalculationCostAmt(orderCosts, coProductSmallerId).toBigDecimal());
		assertThat(sumOutputs).isEqualByComparingTo(BigDecimal.ONE);
	}

	/**
	 * By-products are always zeroed, unconditionally, regardless of any cost distribution percent or manual cost
	 * price on the product. No throw, no by-product-specific guard.
	 */
	@Test
	public void testByProduct_alwaysZeroed_regardlessOfDistributionPercent()
	{
		final ProductId mainProductId = createProduct("blocks_main");
		final ProductId issueProductId = createProduct("input_milk");
		final ProductId byProductId = createProduct("whey_feed");

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

		orderCosts.updatePostCalculationAmounts(costingPrecision);

		this.assertThatPostCalculationAmt(orderCosts, byProductId).isEqualByComparingTo(BigDecimal.ZERO);
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("450"));
	}

	/**
	 * A co-product with no manual cost price is valued by the distribution formula:
	 * {@code coProductCostDistributionPercent × total inbound costs} (450 × 20% = 90), main = 360.
	 */
	@Test
	public void testCoProductWithoutManualCostPrice_percentDistributionFormulaApplies()
	{
		final ProductId mainProductId = createProduct("blocks_main");
		final ProductId issueProductId = createProduct("input_milk");
		final ProductId coProductId = createProduct("Randstuecke"); // distribution percent set (20%); no manual cost price

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

		orderCosts.updatePostCalculationAmounts(costingPrecision);

		this.assertThatPostCalculationAmt(orderCosts, coProductId).isEqualByComparingTo(new BigDecimal("90"));
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("360"));
		final BigDecimal sumOutputs = getPostCalculationCostAmt(orderCosts, mainProductId).toBigDecimal()
				.add(getPostCalculationCostAmt(orderCosts, coProductId).toBigDecimal());
		assertThat(sumOutputs).isEqualByComparingTo(new BigDecimal("450"));
	}

	/**
	 * Explicit 0% (not null/blank) must carve zero too - {@code computeBlankCoProductAmount}'s guard is
	 * {@code signum() <= 0}, so a co-product that explicitly distributes 0% claims nothing from the total inbound costs.
	 */
	@Test
	public void testExplicitZeroPercent_carvesZero()
	{
		final ProductId mainProductId = createProduct("blocks_main");
		final ProductId issueProductId = createProduct("input_milk");
		final ProductId coProductId = createProduct("zero_pct_coproduct");

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

		orderCosts.updatePostCalculationAmounts(costingPrecision);

		this.assertThatPostCalculationAmt(orderCosts, coProductId).isEqualByComparingTo(BigDecimal.ZERO);
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("450"));
	}

	/**
	 * AC12: adding a second co-product must leave the first co-product's carve unchanged - each co-product's
	 * amount is {@code percent x total inbound costs} independently, not a remainder-based split among co-products.
	 */
	@Test
	public void testSecondCoProduct_doesNotChangeFirstCoProductsCarve()
	{
		final ProductId mainProductId = createProduct("blocks_main");
		final ProductId issueProductId = createProduct("input_milk");
		final ProductId coProductAId = createProduct("coproduct_a");
		final ProductId coProductBId = createProduct("coproduct_b");

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
		orderCostsAOnly.updatePostCalculationAmounts(costingPrecision);

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
		orderCostsAAndB.updatePostCalculationAmounts(costingPrecision);

		// A's carve (450 * 20% = 90) is IDENTICAL whether B is present or not
		this.assertThatPostCalculationAmt(orderCostsAOnly, coProductAId).isEqualByComparingTo(new BigDecimal("90"));
		this.assertThatPostCalculationAmt(orderCostsAAndB, coProductAId).isEqualByComparingTo(new BigDecimal("90"));
	}

	/**
	 * AC13 total-inbound-costs cap: a co-product's carve is {@code percent x total inbound costs}, independent of its received qty
	 * ({@code accumulatedQty}) - a larger received quantity must NOT raise the co-product's total claim on the
	 * total inbound costs. Same percent (20%) and total inbound costs (450), qty 1 vs qty 100 -> identical 90 carve both times.
	 */
	@Test
	public void testReceivedQtyDoesNotRaiseTotalClaim()
	{
		final ProductId mainProductId = createProduct("blocks_main");
		final ProductId issueProductId = createProduct("input_milk");
		final ProductId coProductSmallQtyId = createProduct("coproduct_smallqty");
		final ProductId coProductLargeQtyId = createProduct("coproduct_largeqty");

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
		orderCostsSmallQty.updatePostCalculationAmounts(costingPrecision);

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
		orderCostsLargeQty.updatePostCalculationAmounts(costingPrecision);

		this.assertThatPostCalculationAmt(orderCostsSmallQty, coProductSmallQtyId).isEqualByComparingTo(new BigDecimal("90"));
		this.assertThatPostCalculationAmt(orderCostsLargeQty, coProductLargeQtyId).isEqualByComparingTo(new BigDecimal("90"));
	}

	/**
	 * A co-product whose {@code coProductCostDistributionPercent} is NULL (the DAO leaves it nullable, especially
	 * under Moving Average Invoice) must not NPE in the post-calculation: a null / non-positive percent yields a
	 * ZERO co-product share, so the main product keeps the whole of the total inbound costs.
	 */
	@Test
	public void testBlankCoProduct_nullDistributionPercent_noNpe_zeroShare()
	{
		final ProductId mainProductId = createProduct("blocks_main");
		final ProductId issueProductId = createProduct("input_milk");
		final ProductId coProductId = createProduct("Randstuecke"); // BLANK distribution percent

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

		orderCosts.updatePostCalculationAmounts(costingPrecision);

		// null distribution percent -> zero co-product share (no NPE); main keeps the whole of the total inbound costs
		this.assertThatPostCalculationAmt(orderCosts, coProductId).isEqualByComparingTo(BigDecimal.ZERO);
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("450"));
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

		orderCosts.updatePostCalculationAmounts(costingPrecision);

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

		orderCosts.updatePostCalculationAmounts(costingPrecision);

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

	/**
	 * Only the inbound rows count: a receipt on its own is not an issued cost.
	 */
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
	 * Creates a real {@code M_Product} record (so the post-calculation's product-name lookup, used to name an
	 * offending co-product in the AC6 {@code Sigma p <= 100%} guard message, can read it live via the product's id)
	 * and returns its {@link ProductId}.
	 */
	private ProductId createProduct(@NonNull final String name)
	{
		final I_M_Product product = BusinessTestHelper.createProduct(name, uom);
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
		//noinspection OptionalGetWithoutIsPresent
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

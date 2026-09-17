package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.currency.CurrencyPrecision;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

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

@ToString
@EqualsAndHashCode
public final class PPOrderCosts
{
	@Getter
	private final PPOrderId orderId;
	private final HashMap<CostSegmentAndElement, PPOrderCost> costs;

	@Builder
	private PPOrderCosts(
			@NonNull final PPOrderId orderId,
			@NonNull @Singular final Collection<PPOrderCost> costs)
	{
		Check.assumeNotEmpty(costs, "costs shall not be empty for {}", orderId);

		this.orderId = orderId;

		this.costs = costs.stream()
				.collect(GuavaCollectors.toHashMapByKeyFailOnDuplicates(PPOrderCost::getCostSegmentAndElement));
	}

	public Collection<PPOrderCost> toCollection()
	{
		return costs.values();
	}

	public List<PPOrderCost> getByProductAndCostElements(
			@NonNull final ProductId productId,
			@NonNull final Set<CostElementId> costElementIds)
	{
		Check.assumeNotEmpty(costElementIds, "costElementIds is not empty");

		return filterAndList(PPOrderCostFilter.builder()
				.productId(productId)
				.costElementIds(costElementIds)
				.build());
	}

	public Optional<CostPrice> getPriceByCostSegmentAndElement(final CostSegmentAndElement costSegmentAndElement)
	{
		return getByCostSegmentAndElement(costSegmentAndElement)
				.map(PPOrderCost::getPrice);
	}

	public Optional<PPOrderCost> getByCostSegmentAndElement(final CostSegmentAndElement costSegmentAndElement)
	{
		final PPOrderCost cost = costs.get(costSegmentAndElement);
		if (cost == null)
		{
			return Optional.empty();
		}
		else
		{
			return Optional.of(cost);
		}
	}

	private List<PPOrderCost> filterAndList(@NonNull final Predicate<PPOrderCost> filter)
	{
		return costs.values()
				.stream()
				.filter(filter)
				.collect(ImmutableList.toImmutableList());
	}

	public void removeByProductsAndCostElements(
			final Set<ProductId> productIds,
			final Set<CostElementId> costElementIds)
	{
		costs.values().removeIf(cost -> productIds.contains(cost.getProductId()) && costElementIds.contains(cost.getCostElementId()));
	}

	public void addCosts(final Collection<PPOrderCost> costsToAdd)
	{
		for (final PPOrderCost costToAdd : costsToAdd)
		{
			costs.merge(costToAdd.getCostSegmentAndElement(), costToAdd, (oldCost, costToAdd2) -> {
				throw new AdempiereException("Cannot add " + costToAdd2 + " on " + costToAdd2.getCostSegmentAndElement() + " because we already have " + oldCost);
			});
		}
	}

	public void accumulateInboundCostAmount(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		changeExistingCost(costSegmentAndElement, cost -> cost.addingAccumulatedAmountAndQty(amt, qty, uomConverter));
	}

	/**
	 * Discharges {@code amt} onto the main-product line: the accumulated amount moves by {@code +amt} while
	 * the accumulated qty is left untouched, so the line records value leaving the order without recording a
	 * further movement of goods.
	 */
	public void dischargeOntoMainProduct(
			@NonNull final PPOrderCost mainProductCost,
			@NonNull final CostAmount amt,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		// accumulateOutboundCostAmount negates again, so passing -amt moves the accumulated amount by +amt.
		accumulateOutboundCostAmount(
				mainProductCost.getCostSegmentAndElement(),
				amt.negate(),
				mainProductCost.getAccumulatedQty().toZero(),
				uomConverter);
	}

	public void accumulateOutboundCostAmount(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		changeExistingCost(costSegmentAndElement, cost -> cost.subtractingAccumulatedAmountAndQty(amt, qty, uomConverter));
	}

	/**
	 * Sets the current-cost price snapshot on the existing cost row for the given segment.
	 * Callers must ensure {@code newPrice}'s UOM matches the existing row's {@code accumulatedQty} UOM; this
	 * is currently true because they derive from the product's stocking UOM at every current call site
	 * ({@code ManufacturingAveragePOCostingMethodHandler}, {@code ManufacturingMovingAverageInvoiceCostingMethodHandler}
	 * and {@code ManufacturingLastPOCostingMethodHandler}),
	 * but it is NOT structurally enforced by
	 * {@link CostSegmentAndElement}. The underlying {@code withPrice} rebuild throws {@code AdempiereException}
	 * if {@code newPrice}'s UOM diverges from the row's {@code accumulatedQty} UOM.
	 */
	public void updatePriceForCostSegmentAndElement(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final CostPrice newPrice)
	{
		changeExistingCost(costSegmentAndElement, cost -> cost.withPrice(newPrice));
	}

	private void changeExistingCost(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final UnaryOperator<PPOrderCost> mapper)
	{
		costs.compute(costSegmentAndElement, (k, cost) -> {
			if (cost == null)
			{
				throw new AdempiereException("No order costs found for " + costSegmentAndElement);
			}
			else
			{
				return mapper.apply(cost);
			}
		});
	}

	@Value
	@Builder
	private static class PPOrderCostFilter implements Predicate<PPOrderCost>
	{
		ProductId productId;

		@NonNull
		@Singular
		ImmutableSet<CostElementId> costElementIds;

		@Override
		public boolean test(final PPOrderCost cost)
		{
			return (productId == null || productId.equals(cost.getProductId()))
					&& (costElementIds.isEmpty() || costElementIds.contains(cost.getCostElementId()));
		}
	}

	public void updatePostCalculationAmounts(final CurrencyPrecision precision)
	{
		for (final CostElementId costElementId : getCostElementIds())
		{
			updatePostCalculationAmountsForCostElement(precision, costElementId);
		}
	}

	public void updatePostCalculationAmountsForCostElement(
			final CurrencyPrecision precision,
			final CostElementId costElementId)
	{
		final List<PPOrderCost> costs = filterAndList(PPOrderCostFilter.builder()
				.costElementId(costElementId)
				.build());

		final List<PPOrderCost> inboundCosts = costs.stream()
				.filter(PPOrderCost::isInboundCost)
				.collect(ImmutableList.toImmutableList());

		final PPOrderCost mainProductCost = costs.stream()
				.filter(PPOrderCost::isMainProduct)
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Single main product cost could not be found in " + costs)));

		final ImmutableList<PPOrderCost> coProductCosts = costs.stream()
				.filter(PPOrderCost::isCoProduct)
				.collect(ImmutableList.toImmutableList());
		assertValidTotalCoProductDistributionPercent(coProductCosts);

		//
		// Update inbound costs and calculate total inbound costs
		inboundCosts.forEach(PPOrderCost::setPostCalculationAmountAsAccumulatedAmt);
		final CostAmount totalInboundCostAmount = inboundCosts.stream()
				.map(PPOrderCost::getPostCalculationAmount)
				.reduce(CostAmount::add)
				.orElseThrow(() -> new AdempiereException("No inbound costs found in " + costs));

		//
		// Value each co-product through computeBlankCoProductAmount: its cost distribution percent times the
		// total inbound costs (CP_i = p_i × Σ inbound cost).
		coProductCosts.forEach(coProductCost ->
				coProductCost.setPostCalculationAmount(computeBlankCoProductAmount(totalInboundCostAmount, coProductCost, precision)));
		final CostAmount totalCoProductsCostAmount = coProductCosts.stream()
				.map(PPOrderCost::getPostCalculationAmount)
				.reduce(CostAmount::add)
				.orElseGet(totalInboundCostAmount::toZero);

		//
		// Backstop: the co-products valued together must not exceed the order's total inbound costs, else the
		// main product would go negative. The percent guard above already caps Sigma p at 100% in percent-space,
		// so the main product is >= 0 by construction; the ONLY residual way this subtraction turns negative is
		// sub-precision rounding when Sigma p is at (or just under) 100% - each co-product carve is rounded to the
		// costing precision independently (computeBlankCoProductAmount), so the rounded carves can overshoot the
		// total inbound costs by at most one currency ulp per co-product. That rounding noise is ABSORBED (below);
		// only a materially negative main - which can only come from a percent-guard bypass, a real bug - throws.
		CostAmount mainProductAmount = totalInboundCostAmount.subtract(totalCoProductsCostAmount);
		if (mainProductAmount.signum() < 0)
		{
			// Widest overshoot explainable by independent rounding: one currency ulp per co-product carve.
			final BigDecimal roundingTolerance = BigDecimal.ONE.movePointLeft(precision.toInt())
					.multiply(BigDecimal.valueOf(coProductCosts.size()));
			if (mainProductAmount.toBigDecimal().negate().compareTo(roundingTolerance) <= 0)
			{
				// Rounding noise, not a conservation breach: clamp the main product to zero and net the overshoot
				// onto the LARGEST co-product carve (largest-remainder), so Sigma(outputs) == total inbound costs
				// exactly and the order's WIP still nets to zero. No throw.
				final PPOrderCost largestCoProductCost = coProductCosts.stream()
						.max(Comparator
								.comparing((PPOrderCost cost) -> cost.getPostCalculationAmount().toBigDecimal())
								.thenComparingInt(cost -> cost.getProductId().getRepoId()))
						.orElseThrow(() -> new AdempiereException("No co-product cost to absorb the rounding overshoot onto in " + costs));
				// mainProductAmount is negative here, so adding it REDUCES the largest carve by the overshoot.
				largestCoProductCost.setPostCalculationAmount(largestCoProductCost.getPostCalculationAmount().add(mainProductAmount));
				mainProductAmount = mainProductAmount.toZero();
			}
			else
			{
				throw new AdempiereException("Co-products' total valuation " + totalCoProductsCostAmount
						+ " exceeds the production order's total inbound costs of " + totalInboundCostAmount
						+ " and would drive the main product negative");
			}
		}

		//
		// Clear by-product costs.
		costs.stream()
				.filter(PPOrderCost::isByProduct)
				.forEach(PPOrderCost::setPostCalculationAmountAsZero);

		//
		// Update main product cost
		mainProductCost.setPostCalculationAmount(mainProductAmount);
	}

	private static void assertValidTotalCoProductDistributionPercent(final Collection<PPOrderCost> coProductCosts)
	{
		final Percent totalCoProductDistributionPercent = coProductCosts.stream()
				.map(PPOrderCost::getCoProductCostDistributionPercent)
				.filter(percent -> percent != null && percent.signum() > 0)
				.reduce(Percent.ZERO, Percent::add);

		if (totalCoProductDistributionPercent.isOverOneHundred())
		{
			// Sort by product id for a deterministic message; the backing map iterates in unspecified order.
			final List<ProductId> offendingProductIds = coProductCosts.stream()
					.filter(coProductCost -> {
						final Percent percent = coProductCost.getCoProductCostDistributionPercent();
						return percent != null && percent.signum() > 0;
					})
					.map(PPOrderCost::getProductId)
					.sorted(Comparator.comparing(ProductId::getRepoId))
					.collect(ImmutableList.toImmutableList());
			throw new AdempiereException("Co-products' cost distribution percent sum of " + totalCoProductDistributionPercent
					+ " exceeds 100% for product(s): " + describeProducts(offendingProductIds));
		}
	}

	/**
	 * SUPERSEDED reference formula - NOT used by the shipped receipt valuation.
	 * <p>
	 * It computes the co-product's cost-distribution share of the order's total inbound costs (the same carve
	 * {@link #updatePostCalculationAmountsForCostElement} applies on leg A). It was intended for a costing-method
	 * handler to value the co-product receipt (leg B) at the identical amount, so both legs booked the same value.
	 * The shipped handlers instead value a co-product receipt at its CURRENT M_Cost × received-qty per receipt
	 * ({@code ManufacturingAveragePOCostingMethodHandler} / {@code ...MovingAverageInvoice...} /
	 * {@code ...LastPO...}), leaving the make-vs-average delta in WIP; the per-product percent carve is applied
	 * once at order close by the CC-170 cost-difference distributor, not per receipt. This method therefore has no
	 * production callers - only {@code PPOrderCostsTest} exercises it as a reference formula. Kept for reference
	 * (removal pending); contrast {@link #getByProductReceiptAmount}, which IS the live by-product receipt value.
	 */
	public CostAmount getBlankCoProductReceiptAmount(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final CurrencyPrecision precision)
	{
		final PPOrderCost coProductCost = getByCostSegmentAndElement(costSegmentAndElement)
				.orElseThrow(() -> new AdempiereException("No co-product cost row found for " + costSegmentAndElement));
		final CostAmount totalInboundCostAmount = getTotalInboundCostAmount(coProductCost.getCostElementId());
		return computeBlankCoProductAmount(totalInboundCostAmount, coProductCost, precision);
	}

	/**
	 * The amount a by-product receipt must capitalize to inventory: ZERO, regardless of the by-product's own
	 * current M_Cost - symmetric to the by-product's central post-calculation zeroing (leg A, above:
	 * {@code costs.stream().filter(PPOrderCost::isByProduct).forEach(PPOrderCost::setPostCalculationAmountAsZero)}).
	 * A costing-method handler values the by-product receipt (leg B) at this amount so a stray current cost on
	 * the by-product's own product cannot drive the AvgPO/MAI total inbound costs negative. Keyed on {@link PPOrderCost#isByProduct()}
	 * alone - not on any cost-distribution percent - unlike the co-product share in {@link #getBlankCoProductReceiptAmount}.
	 */
	public CostAmount getByProductReceiptAmount(@NonNull final CostSegmentAndElement costSegmentAndElement)
	{
		final PPOrderCost byProductCost = getByCostSegmentAndElement(costSegmentAndElement)
				.orElseThrow(() -> new AdempiereException("No by-product cost row found for " + costSegmentAndElement));
		if (!byProductCost.isByProduct())
		{
			throw new AdempiereException("Not a by-product cost row: " + byProductCost);
		}
		return byProductCost.getAccumulatedAmount().toZero();
	}

	private CostAmount getTotalInboundCostAmount(@NonNull final CostElementId costElementId)
	{
		final List<PPOrderCost> costsForElement = filterAndList(PPOrderCostFilter.builder()
				.costElementId(costElementId)
				.build());
		return costsForElement.stream()
				.filter(PPOrderCost::isInboundCost)
				.map(PPOrderCost::getAccumulatedAmount)
				.reduce(CostAmount::add)
				.orElseThrow(() -> new AdempiereException("No inbound costs found in " + costsForElement));
	}

	/**
	 * The co-product's cost-distribution share of the order's total inbound costs:
	 * {@code totalInbound × coProductCostDistributionPercent}. The distribution percent is nullable (the DAO
	 * leaves it unset, especially under Moving Average Invoice), so a null / non-positive percent yields a zero
	 * share - nothing to capitalise - rather than an NPE.
	 */
	private static CostAmount computeBlankCoProductAmount(
			@NonNull final CostAmount totalInboundCostAmount,
			@NonNull final PPOrderCost coProductCost,
			@NonNull final CurrencyPrecision precision)
	{
		final Percent distributionPercent = coProductCost.getCoProductCostDistributionPercent();
		if (distributionPercent == null || distributionPercent.signum() <= 0)
		{
			return totalInboundCostAmount.toZero();
		}
		return totalInboundCostAmount.multiply(distributionPercent, precision);
	}

	/**
	 * The order's not-yet-discharged WIP cost for the given accounting schema and cost element.
	 * Positive =&gt; more was issued into the order than was received out of it.
	 *
	 * @return {@code null} if there is no main-product cost row for that schema and cost element - the costing
	 * engine explodes the client's cost elements against the schema being posted, so a handler can be asked for
	 * a costing method this order has no rows for.
	 */
	@Nullable
	public CostAmount getResidualCost(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId)
	{
		return getMainProductCost(acctSchemaId, costElementId)
				.map(PPOrderCost::getResidualCost)
				.orElse(null);
	}

	/**
	 * When the inbound rows sum to zero the order received value out of no input cost, so the post-calculation
	 * amount stays zero and the residual degenerates into minus the whole receipt - not a cost difference.
	 */
	public boolean hasInboundCosts(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId)
	{
		return costs.values().stream()
				.filter(cost -> acctSchemaId.equals(cost.getAcctSchemaId()))
				.filter(cost -> costElementId.equals(cost.getCostElementId()))
				.filter(PPOrderCost::isInboundCost)
				.map(PPOrderCost::getAccumulatedAmount)
				.reduce(CostAmount::add)
				.map(totalInboundAmount -> !totalInboundAmount.isZero())
				.orElse(false);
	}

	/**
	 * @return the single main-product cost row for the given schema and cost element, if any.
	 */
	public Optional<PPOrderCost> getMainProductCost(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId)
	{
		final List<PPOrderCost> mainProductCosts = costs.values().stream()
				.filter(cost -> acctSchemaId.equals(cost.getAcctSchemaId()))
				.filter(cost -> costElementId.equals(cost.getCostElementId()))
				.filter(PPOrderCost::isMainProduct)
				.collect(ImmutableList.toImmutableList());

		if (mainProductCosts.size() > 1)
		{
			throw new AdempiereException("Expected at most one main-product PP_Order_Cost row for acctSchema=" + acctSchemaId
					+ ", costElement=" + costElementId + " in " + this);
		}

		return mainProductCosts.stream().findFirst();
	}

	/**
	 * @return the main- or co-product cost row for {@code productId}, if any. Only main/co-product rows
	 * match, never a component issue that happens to share the finished good's product.
	 */
	public Optional<PPOrderCost> getMainOrCoProductCost(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId,
			@NonNull final ProductId productId)
	{
		return costs.values().stream()
				.filter(cost -> cost.isMainProduct() || cost.isCoProduct())
				.filter(cost -> acctSchemaId.equals(cost.getAcctSchemaId()))
				.filter(cost -> costElementId.equals(cost.getCostElementId()))
				.filter(cost -> productId.equals(cost.getProductId()))
				.findFirst();
	}

	/**
	 * @return every co-product cost row for the given schema and cost element (possibly empty).
	 */
	public List<PPOrderCost> getCoProductCosts(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId)
	{
		return costs.values().stream()
				.filter(PPOrderCost::isCoProduct)
				.filter(cost -> acctSchemaId.equals(cost.getAcctSchemaId()))
				.filter(cost -> costElementId.equals(cost.getCostElementId()))
				.collect(ImmutableList.toImmutableList());
	}

	private Set<CostElementId> getCostElementIds()
	{
		return costs.keySet()
				.stream()
				.map(CostSegmentAndElement::getCostElementId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * @return the given products' names (comma-separated), for the negative-main guard message.
	 */
	private static String describeProducts(@NonNull final List<ProductId> productIds)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		return productIds.stream()
				.map(productBL::getProductName)
				.collect(Collectors.joining(", "));
	}

}

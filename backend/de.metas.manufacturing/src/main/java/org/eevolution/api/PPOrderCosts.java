package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingMethod;
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

	public void updatePostCalculationAmounts(
			final CurrencyPrecision precision,
			@NonNull final CostingMethod costingMethod)
	{
		for (final CostElementId costElementId : getCostElementIds())
		{
			updatePostCalculationAmountsForCostElement(precision, costElementId, costingMethod);
		}
	}

	public void updatePostCalculationAmountsForCostElement(
			final CurrencyPrecision precision,
			final CostElementId costElementId,
			@NonNull final CostingMethod costingMethod)
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

		//
		// AC6 guard: Sigma p_i <= 100%. The co-products' cost distribution percent must not exceed the whole pool.
		// This is the single consumption point of the percent (post-calc is one-shot; PP_Order closes afterward),
		// so it is checked here, in PERCENT-space, BEFORE any amount is carved from the pool - fail loud and name
		// the offending co-product(s) + the sum, rather than let it surface later as a negative main product.
		final Percent totalCoProductDistributionPercent = coProductCosts.stream()
				.map(PPOrderCost::getCoProductCostDistributionPercent)
				.filter(percent -> percent != null && percent.signum() > 0)
				.reduce(Percent.ZERO, Percent::add);
		if (totalCoProductDistributionPercent.isOverOneHundred())
		{
			// Sort ascending by product name so the message is deterministic; `costs`/`coProductCosts` are
			// backed by a HashMap and otherwise iterate in an unspecified (JVM-dependent) order.
			final List<ProductId> offendingProductIds = coProductCosts.stream()
					.filter(coProductCost -> {
						final Percent percent = coProductCost.getCoProductCostDistributionPercent();
						return percent != null && percent.signum() > 0;
					})
					.map(PPOrderCost::getProductId)
					.sorted(Comparator.comparing(Services.get(IProductBL.class)::getProductName))
					.collect(ImmutableList.toImmutableList());
			throw new AdempiereException("Co-products' cost distribution percent sum of " + totalCoProductDistributionPercent
					+ " exceeds 100% for product(s): " + describeProducts(offendingProductIds));
		}

		//
		// Update inbound costs and calculate total inbound costs
		inboundCosts.forEach(PPOrderCost::setPostCalculationAmountAsAccumulatedAmt);
		final CostAmount totalInboundCostAmount = inboundCosts.stream()
				.map(PPOrderCost::getPostCalculationAmount)
				.reduce(CostAmount::add)
				.orElseThrow(() -> new AdempiereException("No inbound costs found in " + costs));

		//
		// Update co-product costs and calculate total co-product costs: every co-product is valued through
		// computeBlankCoProductAmount — the AC5 seam (CP_i = p_i x SigmaInboundCost, realized as pool x percent).
		coProductCosts.forEach(coProductCost ->
				coProductCost.setPostCalculationAmount(computeBlankCoProductAmount(totalInboundCostAmount, coProductCost, precision)));
		final CostAmount totalCoProductsCostAmount = coProductCosts.stream()
				.map(PPOrderCost::getPostCalculationAmount)
				.reduce(CostAmount::add)
				.orElseGet(totalInboundCostAmount::toZero);

		//
		// Value-space BACKSTOP (kept behind the percent-space AC6 guard above): the co-products must not consume
		// more than the order's input cost pool, which would drive the main product's value negative. The percent
		// guard above rejects Sigma p > 100% before any amount is computed, so this only catches whatever that
		// check does not reach (e.g. rounding at the pool's precision). Reject here, BEFORE persisting the
		// negative main-product amount below.
		final CostAmount mainProductAmount = totalInboundCostAmount.subtract(totalCoProductsCostAmount);
		if (mainProductAmount.signum() < 0)
		{
			throw new AdempiereException("Co-products' total valuation " + totalCoProductsCostAmount
					+ " exceeds the production order's input cost pool of " + totalInboundCostAmount
					+ " and would drive the main product negative");
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

	/**
	 * The amount a blank-fixed-price co-product receipt must capitalize to inventory: the co-product's share of
	 * the order's inbound cost pool (qty-distribution) for its cost element - the IDENTICAL amount
	 * {@link #updatePostCalculationAmountsForCostElement} books as the co-product's post-calculation relief
	 * (leg A). A costing-method handler values the co-product receipt (leg B) at this amount so both legs book
	 * the same value, cost is conserved and the order's WIP clears.
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
	 * the by-product's own product cannot drive the AvgPO/MAI pool negative. Keyed on {@link PPOrderCost#isByProduct()}
	 * alone - no fixed-price/percent artefact - unlike the co-product share in {@link #getBlankCoProductReceiptAmount}.
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
	 * The blank-fixed-price co-product's qty-distribution share of the order's inbound cost pool:
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
		final PPOrderCost mainProductCost = getMainProductCostOrNull(acctSchemaId, costElementId);
		if (mainProductCost == null)
		{
			return null;
		}

		return mainProductCost.getResidualCost();
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

	/** @return the single main-product cost row for the given schema and cost element, or {@code null}. */
	@Nullable
	public PPOrderCost getMainProductCostOrNull(
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

		return mainProductCosts.isEmpty() ? null : mainProductCosts.get(0);
	}

	/**
	 * @return the main- or co-product cost row for {@code productId}, or {@code null}. Only main/co-product rows
	 * match, never a component issue that happens to share the finished good's product.
	 */
	@Nullable
	public PPOrderCost getMainOrCoProductCostOrNull(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId,
			@NonNull final ProductId productId)
	{
		return costs.values().stream()
				.filter(cost -> cost.isMainProduct() || cost.isCoProduct())
				.filter(cost -> acctSchemaId.equals(cost.getAcctSchemaId()))
				.filter(cost -> costElementId.equals(cost.getCostElementId()))
				.filter(cost -> productId.equals(cost.getProductId()))
				.findFirst()
				.orElse(null);
	}

	private Set<CostElementId> getCostElementIds()
	{
		return costs.keySet()
				.stream()
				.map(CostSegmentAndElement::getCostElementId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/** @return the given products' names (comma-separated), for the negative-main guard message. */
	private static String describeProducts(@NonNull final List<ProductId> productIds)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		return productIds.stream()
				.map(productBL::getProductName)
				.collect(Collectors.joining(", "));
	}

}

package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.currency.CurrencyPrecision;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

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

	public void accumulateOutboundCostAmount(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		changeExistingCost(costSegmentAndElement, cost -> cost.subtractingAccumulatedAmountAndQty(amt, qty, uomConverter));
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

		//
		// Update inbound costs and calculate total inbound costs
		inboundCosts.forEach(PPOrderCost::setPostCalculationAmountAsAccumulatedAmt);
		final CostAmount totalInboundCostAmount = inboundCosts.stream()
				.map(PPOrderCost::getPostCalculationAmount)
				.reduce(CostAmount::add)
				.orElseThrow(() -> new AdempiereException("No inbound costs found in " + costs));

		//
		// Update co-product costs and calculate total co-product costs
		coProductCosts.forEach(cost -> cost.setPostCalculationAmount(totalInboundCostAmount.multiply(cost.getCoProductCostDistributionPercent(), precision)));
		final CostAmount totalCoProductsCostAmount = coProductCosts.stream()
				.map(PPOrderCost::getPostCalculationAmount)
				.reduce(CostAmount::add)
				.orElseGet(totalInboundCostAmount::toZero);

		//
		// Update main product cost
		mainProductCost.setPostCalculationAmount(totalInboundCostAmount.subtract(totalCoProductsCostAmount));

		//
		// Clear by-product costs
		costs.stream()
				.filter(PPOrderCost::isByProduct)
				.forEach(PPOrderCost::setPostCalculationAmountAsZero);
	}

	private Set<CostElementId> getCostElementIds()
	{
		return costs.keySet()
				.stream()
				.map(CostSegmentAndElement::getCostElementId)
				.collect(ImmutableSet.toImmutableSet());
	}

}

package org.eevolution.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.costing.CostElementId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

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
public final class PPOrderCosts implements Iterable<PPOrderCost>
{
	@Getter
	private final PPOrderId orderId;
	private final ImmutableList<PPOrderCost> costs;

	@Builder
	private PPOrderCosts(
			@NonNull final PPOrderId orderId,
			@NonNull final Collection<PPOrderCost> costs)
	{
		this.orderId = orderId;
		this.costs = ImmutableList.copyOf(costs);

		// make sure we have only one entry for each cost segment+element
		Maps.uniqueIndex(costs, PPOrderCost::getCostSegmentAndElement);
	}

	public ImmutableList<PPOrderCost> toList()
	{
		return costs;
	}

	@Override
	public Iterator<PPOrderCost> iterator()
	{
		return toList().iterator();
	};

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

	private List<PPOrderCost> filterAndList(@NonNull final Predicate<PPOrderCost> filter)
	{
		return toList()
				.stream()
				.filter(filter)
				.collect(ImmutableList.toImmutableList());
	}

	public PPOrderCosts removingByProductsAndCostElements(final Set<ProductId> productIds, final Set<CostElementId> costElementIds)
	{
		final List<PPOrderCost> newCosts = toList()
				.stream()
				.filter(cost -> !productIds.contains(cost.getProductId())
						|| !costElementIds.contains(cost.getCostElementId()))
				.collect(ImmutableList.toImmutableList());
		return new PPOrderCosts(orderId, newCosts);
	}

	public PPOrderCosts addingCosts(final Collection<PPOrderCost> costsToAdd)
	{
		final List<PPOrderCost> newCosts = ImmutableList.<PPOrderCost> builder()
				.addAll(costs)
				.addAll(costsToAdd)
				.build();
		return new PPOrderCosts(orderId, newCosts);
	}

	@Value
	@Builder
	private static class PPOrderCostFilter implements Predicate<PPOrderCost>
	{
		final ProductId productId;
		@NonNull
		@Default
		final Set<CostElementId> costElementIds = ImmutableSet.of();

		@Override
		public boolean test(final PPOrderCost cost)
		{
			return (productId == null || productId.equals(cost.getProductId()))
					&& (costElementIds == null || costElementIds.isEmpty() || costElementIds.contains(cost.getCostElementId()));
		}
	}
}

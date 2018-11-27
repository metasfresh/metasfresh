package org.eevolution.api;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.costing.CostAmount;
import de.metas.costing.CostSegmentAndElement;
import de.metas.material.planning.pporder.PPOrderId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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
	private final ImmutableMap<CostSegmentAndElement, PPOrderCost> costs;

	@Builder
	private PPOrderCosts(
			@NonNull final PPOrderId orderId,
			@NonNull final Collection<PPOrderCost> costs)
	{
		this.orderId = orderId;
		this.costs = Maps.uniqueIndex(costs, PPOrderCost::getCostSegmentAndElement);
	}

	public Optional<CostAmount> getByCostSegmentAndElement(@NonNull final CostSegmentAndElement costSegmentAndElement)
	{
		final PPOrderCost cost = costs.get(costSegmentAndElement);
		if (cost == null)
		{
			return Optional.empty();
		}

		return Optional.of(cost.getPrice());
	}

	public void forEach(@NonNull final Consumer<PPOrderCost> action)
	{
		costs.values().forEach(action);
	}
}

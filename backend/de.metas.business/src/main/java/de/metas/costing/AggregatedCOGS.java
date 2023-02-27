/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.costing;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaCosting;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Value
public class AggregatedCOGS
{
	@Getter(AccessLevel.NONE)
	CostSegment costSegment;

	@Getter(AccessLevel.NONE)
	ImmutableMap<CostElement, CostAmountDetailed> amountsPerElement;

	@Builder
	private AggregatedCOGS(
			@NonNull final CostSegment costSegment,
			@NonNull @Singular final Map<CostElement, CostAmountDetailed> amounts)
	{
		Check.assumeNotEmpty(amounts, "amounts is not empty");

		this.costSegment = costSegment;
		amountsPerElement = ImmutableMap.copyOf(amounts);
	}

	public Set<CostElement> getCostElements()
	{
		return amountsPerElement.keySet();
	}

	public CostAmountDetailed getMovingAverageInvoiceAmtsForCostElement(final CostElement costElement)
	{
		final CostAmountDetailed amt = amountsPerElement.get(costElement);
		if (amt == null)
		{
			throw new AdempiereException("No cost amount for " + costElement + " in " + this);
		}
		return amt;
	}

	public AggregatedCOGS merge(@NonNull final AggregatedCOGS other)
	{
		if (!Objects.equals(costSegment, other.costSegment))
		{
			throw new AdempiereException("Cannot merge cost results when the cost segment is not matching: " + this + ", " + other);
		}

		// merge amounts maps; will fail in case of duplicate cost elements
		final ImmutableMap<CostElement, CostAmountDetailed> amountsNew = ImmutableMap.<CostElement, CostAmountDetailed>builder()
				.putAll(amountsPerElement)
				.putAll(other.amountsPerElement)
				.build();

		return new AggregatedCOGS(costSegment, amountsNew);
	}

	public AggregatedCOGS add(@NonNull final AggregatedCOGS other)
	{
		if (!Objects.equals(costSegment, other.costSegment))
		{
			throw new AdempiereException("Cannot add cost results when the cost segment is not matching: " + this + ", " + other);
		}

		final Map<CostElement, CostAmountDetailed> amountsNew = new HashMap<>(amountsPerElement);
		other.amountsPerElement.forEach((costElement, amtToAdd) -> {
			amountsNew.compute(costElement, (ce, amtOld) -> amtOld != null ? amtOld.add(amtToAdd) : amtToAdd);
		});

		return new AggregatedCOGS(costSegment, amountsNew);
	}


	public CostAmountDetailed getTotalAmountToPost(@NonNull final AcctSchema as)
	{
		final AcctSchemaCosting acctSchemaCosting = as.getCosting();
		return getTotalAmount(acctSchemaCosting.getCostingMethod(), acctSchemaCosting.getPostOnlyCostElementIds())
				.orElse(null);
	}

	@VisibleForTesting
	Optional<CostAmountDetailed> getTotalAmount(
			@NonNull final CostingMethod costingMethod,
			final Set<CostElementId> onlyCostElementIds)
	{
		return getCostElements()
				.stream()
				.filter(costElement -> isCostElementMatching(costElement, costingMethod, onlyCostElementIds))
				.map(this::getMovingAverageInvoiceAmtsForCostElement)
				.reduce(CostAmountDetailed::add);
		// .orElseThrow(() -> new AdempiereException("No costs found for " + costingMethod + ", onlyCostElementIds=" + onlyCostElementIds + " in " + this));
	}

	private static boolean isCostElementMatching(
			@NonNull final CostElement costElement,
			@NonNull final CostingMethod costingMethod,
			final Set<CostElementId> onlyCostElementIds)
	{
		if (!costingMethod.equals(costElement.getCostingMethod()))
		{
			return false;
		}

		if (onlyCostElementIds != null
				&& !onlyCostElementIds.isEmpty()
				&& !onlyCostElementIds.contains(costElement.getId()))
		{
			return false;
		}

		return true;
	}
}

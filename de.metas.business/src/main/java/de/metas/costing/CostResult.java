package de.metas.costing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import com.google.common.collect.ImmutableMap;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

@Value
public final class CostResult
{
	CostSegment costSegment;
	CostAmount totalAmount;
	@Getter(AccessLevel.NONE)
	ImmutableMap<CostElement, CostAmount> amounts;

	@Builder
	private CostResult(
			@NonNull final CostSegment costSegment,
			@NonNull final Map<CostElement, CostAmount> amounts)
	{
		Check.assumeNotEmpty(amounts, "amounts is not empty");

		this.costSegment = costSegment;
		this.amounts = ImmutableMap.copyOf(amounts);
		totalAmount = amounts.values()
				.stream()
				.reduce(CostAmount::add)
				.get();
	}

	public Set<CostElement> getCostElements()
	{
		return amounts.keySet();
	}

	public CostAmount getCostAmountForCostElement(final CostElement costElement)
	{
		final CostAmount amt = amounts.get(costElement);
		if (amt == null)
		{
			throw new AdempiereException("No cost amount for " + costElement + " in " + this);
		}
		return amt;
	}

	public CostResult merge(@NonNull final CostResult other)
	{
		if (!Objects.equals(costSegment, other.costSegment))
		{
			throw new AdempiereException("Cannot merge cost results when the cost segment is not matching: " + this + ", " + other);
		}

		// merge amounts maps; will fail in case of duplicate cost elements
		final ImmutableMap<CostElement, CostAmount> amountsNew = ImmutableMap.<CostElement, CostAmount> builder()
				.putAll(amounts)
				.putAll(other.amounts)
				.build();

		return new CostResult(costSegment, amountsNew);
	}

	public CostResult add(@NonNull final CostResult other)
	{
		if (!Objects.equals(costSegment, other.costSegment))
		{
			throw new AdempiereException("Cannot add cost results when the cost segment is not matching: " + this + ", " + other);
		}

		final Map<CostElement, CostAmount> amountsNew = new HashMap<>(amounts);
		other.amounts.forEach((costElement, amtToAdd) -> {
			amountsNew.compute(costElement, (ce, amtOld) -> amtOld != null ? amtOld.add(amtToAdd) : amtToAdd);
		});

		return new CostResult(costSegment, amountsNew);
	}

	public CostResult divide(@NonNull final BigDecimal divisor, final int precision, @NonNull final RoundingMode roundingMode)
	{
		Check.assume(divisor.signum() != 0, "divisor != 0");

		if (BigDecimal.ONE.equals(divisor))
		{
			return this;
		}

		final Map<CostElement, CostAmount> amountsNew = new HashMap<>(amounts.size());
		amounts.forEach((costElement, amt) -> amountsNew.put(costElement, amt.divide(divisor, precision, roundingMode)));

		return new CostResult(costSegment, amountsNew);
	}
}

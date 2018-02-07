package de.metas.costing;

import java.util.Map;

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
	Map<CostElement, CostAmount> amounts;

	@Builder
	private CostResult(
			@NonNull final CostSegment costSegment,
			@NonNull final Map<CostElement, CostAmount> amounts)
	{
		Check.assumeNotEmpty(amounts, "amounts is not empty");

		this.costSegment = costSegment;
		this.amounts = ImmutableMap.copyOf(amounts);
		this.totalAmount = amounts.values()
				.stream()
				.reduce(CostAmount::add)
				.get();
	}
}

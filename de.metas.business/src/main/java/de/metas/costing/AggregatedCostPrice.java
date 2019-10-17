package de.metas.costing;

import java.util.Map;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
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
public class AggregatedCostPrice
{
	CostPrice totalPrice;

	@Getter(AccessLevel.NONE)
	ImmutableMap<CostElement, CostPrice> pricesPerElement;

	@Builder
	private AggregatedCostPrice(
			@NonNull final Map<CostElement, CostPrice> prices)
	{
		Check.assumeNotEmpty(prices, "prices is not empty");

		pricesPerElement = ImmutableMap.copyOf(prices);
		totalPrice = prices.values()
				.stream()
				.reduce(CostPrice::add)
				.get();
	}

	public ImmutableSet<CostElement> getCostElements()
	{
		return pricesPerElement.keySet();
	}

	public CostPrice getCostPriceForCostElement(final CostElement costElement)
	{
		final CostPrice price = pricesPerElement.get(costElement);
		if (price == null)
		{
			throw new AdempiereException("No price found for " + costElement + " in " + this);
		}
		return price;
	}

}

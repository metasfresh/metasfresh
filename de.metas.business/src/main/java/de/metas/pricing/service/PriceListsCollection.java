package de.metas.pricing.service;

import java.util.Set;
import java.util.function.Predicate;

import org.adempiere.location.CountryId;
import org.compiere.model.I_M_PriceList;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.lang.SOTrx;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class PriceListsCollection
{
	private final PricingSystemId pricingSystemId;
	@Getter
	private final ImmutableList<I_M_PriceList> priceLists;

	public PriceListsCollection(
			@NonNull final PricingSystemId pricingSystemId,
			@NonNull final ImmutableList<I_M_PriceList> priceLists)
	{
		this.pricingSystemId = pricingSystemId;
		this.priceLists = priceLists;
	}

	public ImmutableSet<CountryId> getCountryIds()
	{
		return getPriceLists()
				.stream()
				.map(priceList -> CountryId.ofRepoIdOrNull(priceList.getC_Country_ID()))
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
	}

	public ImmutableList<I_M_PriceList> filterAndList(@NonNull final CountryId countryId, final SOTrx soTrx)
	{
		return getPriceLists()
				.stream()
				.filter(PriceListFilter.builder()
						.countryIds(ImmutableSet.of(countryId))
						.acceptNoCountry(true)
						.soTrx(soTrx)
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableSet<PriceListId> filterAndListIds(@NonNull final Set<CountryId> countryIds)
	{
		Check.assumeNotEmpty(countryIds, "countryIds is not empty");

		return getPriceLists()
				.stream()
				.filter(PriceListFilter.builder()
						.countryIds(ImmutableSet.copyOf(countryIds))
						.build())
				.map(priceList -> PriceListId.ofRepoId(priceList.getM_PriceList_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	@Value
	@Builder
	static class PriceListFilter implements Predicate<I_M_PriceList>
	{
		ImmutableSet<CountryId> countryIds;
		boolean acceptNoCountry;
		SOTrx soTrx;

		@Override
		public boolean test(final I_M_PriceList priceList)
		{
			return isCountryMatching(priceList)
					&& isSOTrxMatching(priceList);
		}

		private boolean isCountryMatching(final I_M_PriceList priceList)
		{
			if (countryIds == null)
			{
				return true;
			}

			final CountryId priceListCountryId = CountryId.ofRepoIdOrNull(priceList.getC_Country_ID());
			if (priceListCountryId == null && acceptNoCountry)
			{
				return true;
			}

			if (countryIds.isEmpty())
			{
				return priceListCountryId == null;
			}
			else
			{
				return countryIds.contains(priceListCountryId);
			}
		}

		private boolean isSOTrxMatching(final I_M_PriceList priceList)
		{
			return soTrx == null || soTrx.isSales() == priceList.isSOPriceList();
		}
	}

}

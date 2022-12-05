package de.metas.pricing.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAwares;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.compiere.model.I_M_PriceList;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
	@Getter(AccessLevel.PRIVATE)
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
				.map(PriceListsCollection::extractCountryIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	private static CountryId extractCountryIdOrNull(final I_M_PriceList priceList)
	{
		return CountryId.ofRepoIdOrNull(priceList.getC_Country_ID());
	}

	public Optional<PriceListId> getPriceListId(@NonNull final CountryId countryId, @NonNull final SOTrx soTrx)
	{
		return getPriceList(countryId, soTrx)
				.map(PriceListsCollection::extractPriceListId);
	}

	public Optional<I_M_PriceList> getPriceList(@NonNull final CountryId countryId, @NonNull final SOTrx soTrx)
	{
		return getPriceLists()
				.stream()
				.filter(PriceListFilter.builder()
						.countryIds(ImmutableSet.of(countryId))
						.acceptNoCountry(true)
						.soTrx(soTrx)
						.build())
				.sorted(RepoIdAwares.comparingNullsLast(PriceListsCollection::extractCountryIdOrNull))
				.findFirst();
	}

	public ImmutableList<I_M_PriceList> filterAndList(@NonNull final CountryId countryId, @Nullable final SOTrx soTrx)
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
		return filterAndStreamIds(countryIds)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Stream<PriceListId> filterAndStreamIds(@NonNull final Set<CountryId> countryIds)
	{
		Check.assumeNotEmpty(countryIds, "countryIds is not empty");

		return getPriceLists()
				.stream()
				.filter(PriceListFilter.builder()
						.countryIds(ImmutableSet.copyOf(countryIds))
						.build())
				.map(PriceListsCollection::extractPriceListId)
				.distinct();
	}

	@NonNull
	public ImmutableList<I_M_PriceList> getPriceList()
	{
		return getPriceLists();
	}

	private static PriceListId extractPriceListId(final I_M_PriceList priceList)
	{
		return PriceListId.ofRepoId(priceList.getM_PriceList_ID());
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

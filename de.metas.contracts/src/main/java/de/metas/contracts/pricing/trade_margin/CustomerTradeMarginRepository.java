/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.pricing.trade_margin;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.contracts.model.I_C_Customer_Trade_Margin;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerTradeMarginRepository
{

	private final CCache<CacheKey, ImmutableList<CustomerTradeMarginSettings>> cache = CCache.<CacheKey, ImmutableList<CustomerTradeMarginSettings>> builder()
			.cacheName("customerTradeMarginBySalesRepAndDate")
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.tableName(I_C_Customer_Trade_Margin.Table_Name)
			.build();

	public Optional<CustomerTradeMarginSettings> getBestMatchForCriteria(final CustomerTradeMarginSearchCriteria customerTradeMarginSearchCriteria)
	{
		final CacheKey cacheKey = CacheKey.of(customerTradeMarginSearchCriteria);

		final ImmutableList<CustomerTradeMarginSettings> customerTradeMarginList = cache.getOrLoad(cacheKey, this::loadRecordsForKey);

		Optional<CustomerTradeMarginSettings> bestMatch = Optional.empty();

		for (final CustomerTradeMarginSettings tradeMargin : customerTradeMarginList)
		{
			if ( tradeMargin.applicableOnlyTo( customerTradeMarginSearchCriteria.getCustomerId() )
					|| tradeMargin.applicableToAllCustomers() && !bestMatch.isPresent() )
			{
				bestMatch = Optional.of(tradeMargin);
			}
		}
		return bestMatch;
	}

	private ImmutableList<CustomerTradeMarginSettings> loadRecordsForKey(final CacheKey cacheKey)
	{
		final List<I_C_Customer_Trade_Margin> customerTradeMarginList =
				Optional.of(Services.get(IQueryBL.class)
						.createQueryBuilderOutOfTrx(I_C_Customer_Trade_Margin.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter( I_C_Customer_Trade_Margin.COLUMNNAME_C_BPartner_SalesRep_ID, cacheKey.getSalesRepId().getRepoId() )
						.addCompareFilter( I_C_Customer_Trade_Margin.COLUMN_ValidFrom, CompareQueryFilter.Operator.LESS_OR_EQUAL, cacheKey.getRequestedDate() )
						.create()
						.list())
						.orElseGet(ArrayList::new);

		return customerTradeMarginList.stream()
				.map(CustomerTradeMarginSettings::of)
				.collect(ImmutableList.toImmutableList());
	}

	@Builder
	@Value
	private static class CacheKey
	{
		@NonNull
		BPartnerId salesRepId;

		@NonNull
		LocalDate requestedDate;

		public static CacheKey of(final CustomerTradeMarginSearchCriteria customerTradeMarginSearchCriteria)
		{
			return CacheKey.builder()
					.requestedDate(customerTradeMarginSearchCriteria.getRequestedDate())
					.salesRepId(customerTradeMarginSearchCriteria.getSalesRepId())
					.build();
		}
	}
}

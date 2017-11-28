package de.metas.material.dispo.commons.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.logging.LogManager;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Stock_v;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
public class StockRepository
{
	private static final Logger logger = LogManager.getLogger(StockRepository.class);

	@NonNull
	public BigDecimal retrieveAvailableStockQtySum(@NonNull final MaterialQuery query)
	{
		final Timestamp latestDateOrNull = retrieveMaxDateLessOrEqual(query.getDate());
		if (latestDateOrNull == null)
		{
			return BigDecimal.ZERO;
		}
		final BigDecimal qty = createDBQueryForMaterialQuery(query.withDate(latestDateOrNull))
				.create()
				.aggregate(I_MD_Candidate.COLUMNNAME_Qty, IQuery.AGGREGATE_SUM, BigDecimal.class);

		return Util.coalesce(qty, BigDecimal.ZERO);
	}

	@NonNull
	public AvailableStockResult retrieveAvailableStock(@NonNull final MaterialQuery query)
	{
		final AvailableStockResult result = AvailableStockResult.createEmptyForQuery(query);

		final Timestamp latestDateOrNull = retrieveMaxDateLessOrEqual(query.getDate());
		if (latestDateOrNull == null)
		{
			return result;
		}

		final IQueryBuilder<I_MD_Candidate_Stock_v> dbQuery = createDBQueryForMaterialQuery(query.withDate(latestDateOrNull));
		final List<I_MD_Candidate_Stock_v> stockRecords = dbQuery
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
				.create()
				.list();

		applyStockRecordsToEmptyResult(result, stockRecords);
		return result;
	}

	private Timestamp retrieveMaxDateLessOrEqual(@NonNull final Date date)
	{
		// final Timestamp latestDateOrNull = Services.get(IQueryBL.class)
		// .createQueryBuilder(I_MD_Candidate_Stock_v.class)
		// .addCompareFilter(I_MD_Candidate_Stock_v.COLUMN_DateProjected, Operator.LESS_OR_EQUAL, new Timestamp(date.getTime()))
		// .orderBy().addColumnDescending(I_MD_Candidate_Stock_v.COLUMNNAME_DateProjected).endOrderBy()
		// .create()
		// TODO: this first implementation ignores Ordering!
		// .first(I_MD_Candidate_Stock_v.COLUMNNAME_DateProjected, Timestamp.class);
		final I_MD_Candidate_Stock_v stockRecordOrNull = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Candidate_Stock_v.class)
				.addCompareFilter(I_MD_Candidate_Stock_v.COLUMN_DateProjected, Operator.LESS_OR_EQUAL, new Timestamp(date.getTime()))
				.orderBy().addColumnDescending(I_MD_Candidate_Stock_v.COLUMNNAME_DateProjected).endOrderBy()
				.create()
				.first();
		return stockRecordOrNull == null ? null : stockRecordOrNull.getDateProjected();
	}

	@VisibleForTesting
	IQueryBuilder<I_MD_Candidate_Stock_v> createDBQueryForMaterialQuery(@NonNull final MaterialQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_MD_Candidate_Stock_v> queryBuilder = createInitialQueryBuilderForDateAndWarehouse(query);

		final ICompositeQueryFilter<I_MD_Candidate_Stock_v> orFilterForDifferentStorageAttributesKeys = queryBL
				.createCompositeQueryFilter(I_MD_Candidate_Stock_v.class)
				.setJoinOr();
		queryBuilder.filter(orFilterForDifferentStorageAttributesKeys);

		for (final AttributesKey storageAttributesKey : query.getStorageAttributesKeys())
		{
			final ICompositeQueryFilter<I_MD_Candidate_Stock_v> andFilterForCurrentStorageAttributesKey = createANDFilterForStorageAttributesKey(query, storageAttributesKey);
			orFilterForDifferentStorageAttributesKeys.addFilter(andFilterForCurrentStorageAttributesKey);
		}
		return queryBuilder;
	}

	private IQueryBuilder<I_MD_Candidate_Stock_v> createInitialQueryBuilderForDateAndWarehouse(@NonNull final MaterialQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Candidate_Stock_v> queryBuilder = queryBL
				.createQueryBuilder(I_MD_Candidate_Stock_v.class)
				.addEqualsFilter(I_MD_Candidate_Stock_v.COLUMN_DateProjected, query.getDate());

		if (!query.getWarehouseIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Candidate_Stock_v.COLUMN_M_Warehouse_ID, query.getWarehouseIds());
		}
		return queryBuilder;
	}

	private ICompositeQueryFilter<I_MD_Candidate_Stock_v> createANDFilterForStorageAttributesKey(
			@NonNull final MaterialQuery query,
			@NonNull final AttributesKey storageAttributesKey)
	{
		final ICompositeQueryFilter<I_MD_Candidate_Stock_v> filterForCurrentStorageAttributesKey = createInitialANDFilterForProductIds(query);

		if (Objects.equals(storageAttributesKey, ProductDescriptor.STORAGE_ATTRIBUTES_KEY_OTHER))
		{
			addNotLikeFiltersForAttributesKeys(filterForCurrentStorageAttributesKey, query.getStorageAttributesKeys());
		}
		else if (Objects.equals(storageAttributesKey, ProductDescriptor.STORAGE_ATTRIBUTES_KEY_ALL))
		{
			// nothing to add to the initial productIds filters
		}
		else
		{
			addLikeFilterForAttributesKey(storageAttributesKey, filterForCurrentStorageAttributesKey);
		}

		if (query.getBpartnerId() > 0)
		{
			// TODO: implement support for query.getBPartnerId(). see https://github.com/metasfresh/metasfresh/issues/3098
			logger.warn("Ignoring BPartnerId from query because it's not implemented yet: {}", query);
		}

		return filterForCurrentStorageAttributesKey;
	}

	private ICompositeQueryFilter<I_MD_Candidate_Stock_v> createInitialANDFilterForProductIds(@NonNull final MaterialQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createCompositeQueryFilter(I_MD_Candidate_Stock_v.class)
				.setJoinAnd()
				.addInArrayFilter(I_MD_Candidate_Stock_v.COLUMN_M_Product_ID, query.getProductIds());
	}

	private void addNotLikeFiltersForAttributesKeys(
			@NonNull final ICompositeQueryFilter<I_MD_Candidate_Stock_v> compositeFilter,
			@NonNull final List<AttributesKey> attributesKeys)
	{
		for (final AttributesKey storageAttributesKeyAgain : attributesKeys)
		{
			if (!Objects.equals(storageAttributesKeyAgain, ProductDescriptor.STORAGE_ATTRIBUTES_KEY_OTHER))
			{
				final String likeExpression = createLikeExpression(storageAttributesKeyAgain);
				compositeFilter.addStringNotLikeFilter(I_MD_Candidate_Stock_v.COLUMN_StorageAttributesKey, likeExpression, false);
			}
		}
	}

	private void addLikeFilterForAttributesKey(final AttributesKey storageAttributesKey, final ICompositeQueryFilter<I_MD_Candidate_Stock_v> andFilterForCurrentStorageAttributesKey)
	{
		final String likeExpression = createLikeExpression(storageAttributesKey);
		andFilterForCurrentStorageAttributesKey.addStringLikeFilter(I_MD_Candidate_Stock_v.COLUMN_StorageAttributesKey, likeExpression, false);
	}

	private static String createLikeExpression(@NonNull final AttributesKey storageAttributesKey)
	{
		final String storageAttributesKeyLikeExpression = storageAttributesKey.getSqlLikeString();
		return "%" + storageAttributesKeyLikeExpression + "%";
	}

	@VisibleForTesting
	void applyStockRecordsToEmptyResult(
			@NonNull final AvailableStockResult emptyResult,
			@NonNull final List<I_MD_Candidate_Stock_v> stockRecords)
	{
		for (final I_MD_Candidate_Stock_v stockRecord : stockRecords)
		{
			emptyResult.addQtyToMatchedGroups(stockRecord.getQty(), stockRecord.getM_Product_ID(), AttributesKey.ofString(stockRecord.getStorageAttributesKey()));
		}
	}

	@Value
	private static class ProductAndAttributeKey
	{
		int productId;
		String attributeKey;
	}
}

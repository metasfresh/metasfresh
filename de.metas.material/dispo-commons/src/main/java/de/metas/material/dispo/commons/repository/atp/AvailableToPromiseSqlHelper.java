package de.metas.material.dispo.commons.repository.atp;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.compiere.Adempiere;
import org.compiere.db.Database;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.dispo.model.I_MD_Candidate_ATP_QueryResult;
import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
/* package */ final class AvailableToPromiseSqlHelper
{
	@VisibleForTesting
	public IQuery<I_MD_Candidate_ATP_QueryResult> createDBQueryForStockQuery(@NonNull final AvailableToPromiseQuery query)
	{
		final IQueryBuilder<I_MD_Candidate_ATP_QueryResult> queryBuilder = createDBQueryForStockQueryBuilder(query);

		final IQuery<I_MD_Candidate_ATP_QueryResult> dbQuery = queryBuilder
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
				.create();

		if (isRealSqlQuery())
		{
			Check.assume(dbQuery instanceof TypedSqlQuery, "If we are not in unit test mode, then our query has to be an sql query; query={}", dbQuery);
			final TypedSqlQuery<I_MD_Candidate_ATP_QueryResult> sqlDbQuery = (TypedSqlQuery<I_MD_Candidate_ATP_QueryResult>)dbQuery;

			final String dateString = Database.TO_DATE(TimeUtil.asTimestamp(query.getDate()), false);
			sqlDbQuery.setSqlFrom("de_metas_material.retrieve_atp_at_date(" + dateString + ")");
		}

		//
		return dbQuery;
	}

	@VisibleForTesting
	IQueryBuilder<I_MD_Candidate_ATP_QueryResult> createDBQueryForStockQueryBuilder(@NonNull final AvailableToPromiseQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_MD_Candidate_ATP_QueryResult> queryBuilder = //
				queryBL.createQueryBuilder(I_MD_Candidate_ATP_QueryResult.class);

		if (!isRealSqlQuery())
		{
			// Date; this only makes sense in unit test's there the I_MD_Candidate_ATP_QueryResult records to return were hand-crafted for the respective test
			queryBuilder.addCompareFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_DateProjected, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(query.getDate()));
		}

		// Warehouse
		final Set<Integer> warehouseIds = query.getWarehouseIds();
		if (!warehouseIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_M_Warehouse_ID, warehouseIds);
		}

		// Product
		final List<Integer> productIds = query.getProductIds();
		if (!productIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_M_Product_ID, productIds);
		}

		// BPartner
		final int bpartnerId = query.getBpartnerId();
		if (bpartnerId == AvailableToPromiseQuery.BPARTNER_ID_ANY)
		{
			// nothing to filter
		}
		else if (bpartnerId == AvailableToPromiseQuery.BPARTNER_ID_NONE)
		{
			queryBuilder.addEqualsFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_C_BPartner_Customer_ID, null);
		}
		else
		{
			queryBuilder.addInArrayFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_C_BPartner_Customer_ID, bpartnerId, null);
		}

		//
		// Storage Attributes Key
		queryBuilder.filter(createANDFilterForStorageAttributesKeys(query));
		return queryBuilder;
	}

	private boolean isRealSqlQuery()
	{
		final boolean isRealSqlQuery = !Adempiere.isUnitTestMode();
		return isRealSqlQuery;
	}

	private static ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> createANDFilterForStorageAttributesKeys(@NonNull final AvailableToPromiseQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> orFilterForDifferentStorageAttributesKeys = queryBL
				.createCompositeQueryFilter(I_MD_Candidate_ATP_QueryResult.class)
				.setJoinOr();

		for (final AttributesKey attributesKey : query.getStorageAttributesKeys())
		{
			final IQueryFilter<I_MD_Candidate_ATP_QueryResult> andFilterForCurrentStorageAttributesKey = createANDFilterForStorageAttributesKey(query, attributesKey);
			orFilterForDifferentStorageAttributesKeys.addFilter(andFilterForCurrentStorageAttributesKey);
		}

		return orFilterForDifferentStorageAttributesKeys;
	}

	private static ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> createANDFilterForStorageAttributesKey(
			@NonNull final AvailableToPromiseQuery query,
			@NonNull final AttributesKey attributesKey)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> filterForCurrentStorageAttributesKey = queryBL.createCompositeQueryFilter(I_MD_Candidate_ATP_QueryResult.class)
				.setJoinAnd();

		if (Objects.equals(attributesKey, AttributesKey.OTHER))
		{
			addNotLikeFiltersForAttributesKeys(filterForCurrentStorageAttributesKey, query.getStorageAttributesKeys());
		}
		else if (Objects.equals(attributesKey, AttributesKey.ALL))
		{
			// nothing to add to the initial productIds filters
		}
		else
		{
			addLikeFilterForAttributesKey(attributesKey, filterForCurrentStorageAttributesKey);
		}

		return filterForCurrentStorageAttributesKey;
	}

	private static void addNotLikeFiltersForAttributesKeys(
			@NonNull final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> compositeFilter,
			@NonNull final List<AttributesKey> attributesKeys)
	{
		for (final AttributesKey storageAttributesKeyAgain : attributesKeys)
		{
			if (!Objects.equals(storageAttributesKeyAgain, AttributesKey.OTHER))
			{
				final String likeExpression = createLikeExpression(storageAttributesKeyAgain);
				compositeFilter.addStringNotLikeFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_StorageAttributesKey, likeExpression, false);
			}
		}
	}

	private static void addLikeFilterForAttributesKey(final AttributesKey attributesKey, final ICompositeQueryFilter<I_MD_Candidate_ATP_QueryResult> andFilterForCurrentStorageAttributesKey)
	{
		final String likeExpression = createLikeExpression(attributesKey);
		andFilterForCurrentStorageAttributesKey.addStringLikeFilter(I_MD_Candidate_ATP_QueryResult.COLUMN_StorageAttributesKey, likeExpression, false);
	}

	private static String createLikeExpression(@NonNull final AttributesKey attributesKey)
	{
		final String storageAttributesKeyLikeExpression = attributesKey.getSqlLikeString();
		return "%" + storageAttributesKeyLikeExpression + "%";
	}

}

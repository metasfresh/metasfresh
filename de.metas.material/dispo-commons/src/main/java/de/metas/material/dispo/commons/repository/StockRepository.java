package de.metas.material.dispo.commons.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.repository.AvailableStockResult.ResultGroupAddRequest;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Stock_v;
import de.metas.material.event.commons.StorageAttributesKey;
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
	@NonNull
	public BigDecimal retrieveAvailableStockQtySum(@NonNull final MaterialQuery query)
	{
		final Timestamp latestDateOrNull = retrieveMaxDateLessOrEqual(query.getDate());
		if (latestDateOrNull == null)
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal qty = StockRepositorySqlHelper.createDBQueryForMaterialQuery(query.withDate(latestDateOrNull))
				.create()
				.aggregate(I_MD_Candidate.COLUMNNAME_Qty, IQuery.AGGREGATE_SUM, BigDecimal.class);

		return Util.coalesce(qty, BigDecimal.ZERO);
	}

	@NonNull
	public AvailableStockResult retrieveAvailableStock(@NonNull MaterialMultiQuery multiQuery)
	{
		final AvailableStockResult result = multiQuery.isAddToPredefinedBuckets() ? AvailableStockResult.createEmptyWithPredefinedBuckets(multiQuery) : AvailableStockResult.createEmpty();

		final IQuery<I_MD_Candidate_Stock_v> dbQuery = createDBQueryForMaterialQueryOrNull(multiQuery);
		if (dbQuery == null)
		{
			return result;
		}

		final List<ResultGroupAddRequest> addRequests = dbQuery
				.stream()
				.map(stockRecord -> createResultGroupAddRequest(stockRecord))
				.collect(ImmutableList.toImmutableList());

		if (multiQuery.isAddToPredefinedBuckets())
		{
			addRequests.forEach(result::addQtyToAllMatchingGroups);
		}
		else
		{
			addRequests.forEach(result::addGroup);
		}
		return result;
	}

	public AvailableStockResult retrieveAvailableStock(@NonNull MaterialQuery query)
	{
		return retrieveAvailableStock(MaterialMultiQuery.of(query));
	}

	private IQuery<I_MD_Candidate_Stock_v> createDBQueryForMaterialQueryOrNull(@NonNull final MaterialMultiQuery multiQuery)
	{
		return multiQuery.getQueries()
				.stream()
				.map(query -> {
					final Timestamp latestDateOrNull = retrieveMaxDateLessOrEqual(query.getDate());
					if (latestDateOrNull == null)
					{
						return null;
					}
					return query.withDate(latestDateOrNull);
				})
				.filter(Predicates.notNull())
				.map(query -> StockRepositorySqlHelper.createDBQueryForMaterialQuery(query)
						.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
						.create())
				.reduce((previousDBQuery, dbQuery) -> {
					if (previousDBQuery == null)
					{
						return dbQuery;
					}
					else
					{
						previousDBQuery.addUnion(dbQuery, true);
						return previousDBQuery;
					}
				})
				.orElse(null);
	}

	private Timestamp retrieveMaxDateLessOrEqual(@NonNull final Date date)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Candidate_Stock_v.class)
				.addCompareFilter(I_MD_Candidate_Stock_v.COLUMN_DateProjected, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(date))
				.create()
				.aggregate(I_MD_Candidate_Stock_v.COLUMNNAME_DateProjected, IQuery.AGGREGATE_MAX, Timestamp.class);
	}

	@VisibleForTesting
	static ResultGroupAddRequest createResultGroupAddRequest(final I_MD_Candidate_Stock_v stockRecord)
	{
		return ResultGroupAddRequest.builder()
				.productId(stockRecord.getM_Product_ID())
				.bpartnerId(MaterialQuery.BPARTNER_ID_NONE) // TODO: https://github.com/metasfresh/metasfresh/issues/3098
				.warehouseId(stockRecord.getM_Warehouse_ID())
				.storageAttributesKey(StorageAttributesKey.ofString(stockRecord.getStorageAttributesKey()))
				.qty(stockRecord.getQty())
				.build();
	}

	@Value
	private static class ProductAndAttributeKey
	{
		int productId;
		String attributeKey;
	}
}

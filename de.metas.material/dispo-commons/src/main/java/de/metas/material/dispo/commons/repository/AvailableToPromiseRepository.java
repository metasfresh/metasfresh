package de.metas.material.dispo.commons.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Functions;
import org.adempiere.util.Functions.MemoizingFunction;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.material.dispo.commons.repository.AvailableToPromiseResult.AddToResultGroupRequest;
import de.metas.material.dispo.commons.repository.AvailableToPromiseResult.ResultGroup;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Stock_v;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.commons.AttributesKey;
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
public class AvailableToPromiseRepository
{
	private static final String SYSCONFIG_ATP_ATTRIBUTES_KEYS = "de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ATP.AttributesKeys";

	@NonNull
	public BigDecimal retrieveAvailableStockQtySum(@NonNull final AvailableToPromiseMultiQuery multiQuery)
	{
		return retrieveAvailableStock(multiQuery)
				.getResultGroups()
				.stream().map(ResultGroup::getQty).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@NonNull
	public BigDecimal retrieveAvailableStockQtySum(@NonNull final AvailableToPromiseQuery query)
	{
		return retrieveAvailableStockQtySum(AvailableToPromiseMultiQuery.of(query));
	}


	@NonNull
	public AvailableToPromiseResult retrieveAvailableStock(@NonNull AvailableToPromiseMultiQuery multiQuery)
	{
		final AvailableToPromiseResult result = multiQuery.isAddToPredefinedBuckets()
				? AvailableToPromiseResult.createEmptyWithPredefinedBuckets(multiQuery)
				: AvailableToPromiseResult.createEmpty();

		final IQuery<I_MD_Candidate_Stock_v> dbQuery = createDBQueryForMaterialQueryOrNull(multiQuery);
		if (dbQuery == null)
		{
			return result;
		}

		final List<AddToResultGroupRequest> addRequests = dbQuery
				.stream()
				.map(stockRecord -> createAddToResultGroupRequest(stockRecord))
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

	public AvailableToPromiseResult retrieveAvailableStock(@NonNull AvailableToPromiseQuery query)
	{
		return retrieveAvailableStock(AvailableToPromiseMultiQuery.of(query));
	}

	private IQuery<I_MD_Candidate_Stock_v> createDBQueryForMaterialQueryOrNull(
			@NonNull final AvailableToPromiseMultiQuery multiQuery)
	{
		final MemoizingFunction<LocalDateTime, Timestamp> maxDateLessOrEqualFunction //
				= Functions.memoizing(date -> retrieveMaxDateLessOrEqual(date));

		final UnaryOperator<AvailableToPromiseQuery> setStockQueryDateParameter = //
				stockQuery -> {
					final Timestamp latestDateOrNull = maxDateLessOrEqualFunction.apply(stockQuery.getDate());
					if (latestDateOrNull == null)
					{
						return null;
					}
					return stockQuery.withDate(latestDateOrNull);
				};

		final Function<AvailableToPromiseQuery, IQuery<I_MD_Candidate_Stock_v>> createDbQueryForSingleStockQuery = //
				stockQuery -> AvailableToPromiseSqlHelper
						.createDBQueryForStockQuery(stockQuery)
						.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
						.create();

		return multiQuery.getQueries()
				.stream()
				.map(setStockQueryDateParameter)
				.filter(Predicates.notNull())
				.map(createDbQueryForSingleStockQuery)
				.reduce(IQuery.unionDistict())
				.orElse(null);
	}

	private Timestamp retrieveMaxDateLessOrEqual(@NonNull final LocalDateTime date)
	{
		return Services.get(IQueryBL.class)

				// select from MD_Candidate, because the performance is much worse with MD_Candidate_Stock_v
				// also note that this method is supported by the DB index md_candidate_stock_latest_date_perf
				.createQueryBuilder(I_MD_Candidate.class)

				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Type, X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK)
				.addCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(date))

				.orderBy()
				.addColumn(I_MD_Candidate.COLUMN_DateProjected, IQueryOrderBy.Direction.Descending, IQueryOrderBy.Nulls.Last)
				.endOrderBy()

				.create()
				.first(I_MD_Candidate.COLUMNNAME_DateProjected, Timestamp.class);
	}

	@VisibleForTesting
	static AddToResultGroupRequest createAddToResultGroupRequest(final I_MD_Candidate_Stock_v stockRecord)
	{
		final int bPpartnerIdForRequest = stockRecord.getC_BPartner_ID() > 0
				? stockRecord.getC_BPartner_ID()
				: AvailableToPromiseQuery.BPARTNER_ID_NONE;

		return AddToResultGroupRequest.builder()
				.productId(stockRecord.getM_Product_ID())
				.bpartnerId(bPpartnerIdForRequest)
				.warehouseId(stockRecord.getM_Warehouse_ID())
				.storageAttributesKey(AttributesKey.ofString(stockRecord.getStorageAttributesKey()))
				.qty(stockRecord.getQty())
				.build();
	}

	public Set<AttributesKey> getPredefinedStorageAttributeKeys()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int clientId = Env.getAD_Client_ID(Env.getCtx());
		final int orgId = Env.getAD_Org_ID(Env.getCtx());

		final String storageAttributesKeys = sysConfigBL.getValue(
				SYSCONFIG_ATP_ATTRIBUTES_KEYS,
				AttributesKey.ALL.getAsString(),
				clientId, orgId);

		return Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(storageAttributesKeys)
				.stream()
				.map(attributesKeyStr -> toAttributesKey(attributesKeyStr))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static AttributesKey toAttributesKey(final String storageAttributesKey)
	{
		if ("<ALL_STORAGE_ATTRIBUTES_KEYS>".equals(storageAttributesKey))
		{
			return AttributesKey.ALL;
		}
		else if ("<OTHER_STORAGE_ATTRIBUTES_KEYS>".equals(storageAttributesKey))
		{
			return AttributesKey.OTHER;
		}
		else
		{
			return AttributesKey.ofString(storageAttributesKey);
		}
	}


	@Value
	private static class ProductAndAttributeKey
	{
		int productId;
		String attributeKey;
	}
}

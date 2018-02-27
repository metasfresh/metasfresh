package de.metas.ordercandidate.api;

import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.util.CCache;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ordercandidate.api.OLCandAggregationColumn.Granularity;
import de.metas.ordercandidate.model.I_C_OLCandAggAndOrder;
import de.metas.ordercandidate.model.X_C_OLCandAggAndOrder;

/*
 * #%L
 * de.metas.swat.base
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

@Repository
public class OLCandAggregationRepository
{
	private final CCache<Integer, OLCandAggregation> olCandAggregationsById = CCache.<Integer, OLCandAggregation> newCache(I_C_OLCandAggAndOrder.Table_Name + "#by#ID", 10, CCache.EXPIREMINUTES_Never);

	private static final Map<String, Granularity> granularityByADRefListValue = ImmutableMap.<String, Granularity> builder()
			.put(X_C_OLCandAggAndOrder.GRANULARITY_Tag, Granularity.Day)
			.put(X_C_OLCandAggAndOrder.GRANULARITY_Woche, Granularity.Week)
			.put(X_C_OLCandAggAndOrder.GRANULARITY_Monat, Granularity.Month)
			.build();

	public OLCandAggregation getById(final int olCandProcessorId)
	{
		return olCandAggregationsById.getOrLoad(olCandProcessorId, () -> retrieveOLCandAggregation(olCandProcessorId));
	}

	private OLCandAggregation retrieveOLCandAggregation(final int olCandProcessorId)
	{
		final List<OLCandAggregationColumn> columns = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_OLCandAggAndOrder.class)
				.addEqualsFilter(I_C_OLCandAggAndOrder.COLUMN_C_OLCandProcessor_ID, olCandProcessorId)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_C_OLCandAggAndOrder.COLUMN_OrderBySeqNo)
				.endOrderBy()
				.create()
				.stream(I_C_OLCandAggAndOrder.class)
				.map(this::createOLCandAggregationColumn)
				.collect(ImmutableList.toImmutableList());

		return OLCandAggregation.of(columns);
	}

	private OLCandAggregationColumn createOLCandAggregationColumn(final I_C_OLCandAggAndOrder olCandAgg)
	{
		final I_AD_Column adColumn = olCandAgg.getAD_Column_OLCand();

		return OLCandAggregationColumn.builder()
				.columnName(adColumn.getColumnName())
				.adColumnId(adColumn.getAD_Column_ID())
				.orderBySeqNo(olCandAgg.getOrderBySeqNo())
				.splitOrderDiscriminator(olCandAgg.isSplitOrder())
				.groupByColumn(olCandAgg.isGroupBy())
				.granularity(granularityByADRefListValue.get(olCandAgg.getGranularity()))
				.build();
	}
}

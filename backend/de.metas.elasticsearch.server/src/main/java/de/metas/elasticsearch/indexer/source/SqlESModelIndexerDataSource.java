/*
 * #%L
 * de.metas.elasticsearch.server
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.elasticsearch.indexer.source;

import de.metas.elasticsearch.trigger.IESModelIndexerTrigger;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.compiere.model.IQuery;

import java.util.List;
import java.util.stream.Stream;

@ToString(exclude = "queryBL")
public class SqlESModelIndexerDataSource implements ESModelIndexerDataSource
{
	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final String modelTableName;
	private final List<IESModelIndexerTrigger> triggers;
	private final String sqlWhereClause;
	private final String sqlOrderByClause;
	private final QueryLimit limit;

	@Builder
	private SqlESModelIndexerDataSource(
			@NonNull final String modelTableName,
			@NonNull final List<IESModelIndexerTrigger> triggers,
			final String sqlWhereClause,
			final String sqlOrderByClause,
			final int limit)
	{
		Check.assumeNotEmpty(triggers, "triggers is not empty");

		this.modelTableName = modelTableName;
		this.triggers = triggers;
		this.sqlWhereClause = sqlWhereClause;
		this.sqlOrderByClause = sqlOrderByClause;
		this.limit = QueryLimit.ofInt(limit);
	}

	@Override
	public Stream<ESModelToIndex> streamModelsToIndex()
	{
		final ICompositeQueryFilter<Object> triggerFilters = queryBL.createCompositeQueryFilter(modelTableName)
				.setDefaultAccept(true)
				.setJoinOr();
		for (final IESModelIndexerTrigger trigger : triggers)
		{
			final IQueryFilter<Object> filter = trigger.getMatchingModelsFilter();
			if (filter == null)
			{
				continue;
			}

			triggerFilters.addFilter(filter);
		}

		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(modelTableName)
				.addOnlyActiveRecordsFilter()
				.filter(triggerFilters);

		if (!Check.isEmpty(sqlWhereClause, true))
		{
			queryBuilder.filter(TypedSqlQueryFilter.of(sqlWhereClause));
		}

		if (limit.isLimited())
		{
			queryBuilder.setLimit(limit);
		}

		final IQuery<Object> query = queryBuilder.create();

		if (!Check.isBlank(sqlOrderByClause))
		{
			query.setOrderBy(queryBL.createSqlQueryOrderBy(sqlOrderByClause));
		}

		//
		// Execute query
		return query.stream().map(ESModelToIndex::ofObject);
	}
}

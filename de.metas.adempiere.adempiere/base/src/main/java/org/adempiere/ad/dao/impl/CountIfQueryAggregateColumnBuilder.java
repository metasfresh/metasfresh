package org.adempiere.ad.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryAggregateBuilder;
import org.adempiere.ad.dao.IQueryAggregateColumnBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.util.lang.IAggregator;

import de.metas.util.Check;

/**
 * COUNT-IF aggregation. Counts all source records which are matching the {@link #filter()}.
 * 
 * Created by {@link IQueryAggregateBuilder#countIf(ModelDynAttributeAccessor)}.
 *
 * @author tsa
 *
 * @param <SourceModelType>
 * @param <TargetModelType>
 */
/* package */class CountIfQueryAggregateColumnBuilder<SourceModelType, TargetModelType> implements IQueryAggregateColumnBuilder<SourceModelType, TargetModelType, Integer>
{
	private final ICompositeQueryFilter<SourceModelType> filters = new CompositeQueryFilter<>();
	private ModelDynAttributeAccessor<TargetModelType, Integer> dynAttribute;

	/* package */CountIfQueryAggregateColumnBuilder()
	{
		super();
	}

	@Override
	public ICompositeQueryFilter<SourceModelType> filter()
	{
		return this.filters;
	}

	@Override
	public String getSql(final Properties ctx, final List<Object> sqlParamsOut)
	{
		final List<IQueryFilter<SourceModelType>> nonSqlFilters = filters.getNonSqlFilters();
		Check.assume(nonSqlFilters == null || nonSqlFilters.isEmpty(), "Non-SQL filters are not supported: {}", nonSqlFilters);

		final String sqlWhereClause = filters.getSqlFiltersWhereClause();

		final StringBuilder sql = new StringBuilder()
				.append("SUM(CASE WHEN (").append(sqlWhereClause).append(") THEN +1 ELSE 0 END)");

		final List<Object> sqlWhereClauseParams = filters.getSqlFiltersParams(ctx);
		sqlParamsOut.addAll(sqlWhereClauseParams);

		return sql.toString();
	}

	@Override
	public CountIfQueryAggregateColumnBuilder<SourceModelType, TargetModelType> setDynAttribute(final ModelDynAttributeAccessor<TargetModelType, Integer> dynAttribute)
	{
		this.dynAttribute = dynAttribute;
		return this;
	}

	@Override
	public ModelDynAttributeAccessor<TargetModelType, Integer> getDynAttribute()
	{
		return dynAttribute;
	}

	@Override
	public IAggregator<Integer, SourceModelType> createAggregator(final TargetModelType targetModel)
	{
		Check.assumeNotNull(targetModel, "targetModel not null");

		return new IAggregator<Integer, SourceModelType>()
		{
			private final ICompositeQueryFilter<SourceModelType> filters = CountIfQueryAggregateColumnBuilder.this.filters.copy();
			private ModelDynAttributeAccessor<TargetModelType, Integer> dynAttribute = CountIfQueryAggregateColumnBuilder.this.dynAttribute;
			private int counter = 0;

			@Override
			public void add(final SourceModelType value)
			{
				if (filters.accept(value))
				{
					counter++;
				}

				// Update target model's dynamic attribute
				dynAttribute.setValue(targetModel, counter);
			}

			@Override
			public Integer getValue()
			{
				return counter;
			}
		};
	}
}

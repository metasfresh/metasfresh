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


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryAggregateColumnBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.lang.IAggregator;

import de.metas.util.Check;



public class SumQueryAggregateColumnBuilder<SourceModelType, TargetModelType> implements IQueryAggregateColumnBuilder<SourceModelType, TargetModelType, BigDecimal>
{
	private final ICompositeQueryFilter<SourceModelType> filters;
	private ModelDynAttributeAccessor<TargetModelType, BigDecimal> dynAttribute;
	private ModelColumn<SourceModelType, ?> _amountColumn;

	/* package */SumQueryAggregateColumnBuilder(final ModelColumn<SourceModelType, ?> amountColumn)
	{
		super();
		Check.assumeNotNull(amountColumn, "amountColumn not null");
		this._amountColumn = amountColumn;

		filters = new CompositeQueryFilter<>(amountColumn.getModelClass());
	}

	private final String getAmountColumnName()
	{
		return _amountColumn.getColumnName();
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
				.append("COALESCE(SUM(CASE WHEN (").append(sqlWhereClause).append(") THEN " + getAmountColumnName() + " ELSE 0 END), 0)");

		final List<Object> sqlWhereClauseParams = filters.getSqlFiltersParams(ctx);
		sqlParamsOut.addAll(sqlWhereClauseParams);

		return sql.toString();
	}

	@Override
	public SumQueryAggregateColumnBuilder<SourceModelType, TargetModelType> setDynAttribute(final ModelDynAttributeAccessor<TargetModelType, BigDecimal> dynAttribute)
	{
		this.dynAttribute = dynAttribute;
		return this;
	}

	@Override
	public ModelDynAttributeAccessor<TargetModelType, BigDecimal> getDynAttribute()
	{
		Check.assumeNotNull(dynAttribute, "dynAttribute not null");
		return dynAttribute;
	}

	@Override
	public IAggregator<BigDecimal, SourceModelType> createAggregator(final TargetModelType targetModel)
	{
		Check.assumeNotNull(targetModel, "targetModel not null");

		return new IAggregator<BigDecimal, SourceModelType>()
		{
			private final ICompositeQueryFilter<SourceModelType> filters = SumQueryAggregateColumnBuilder.this.filters.copy();
			private ModelDynAttributeAccessor<TargetModelType, BigDecimal> dynAttribute = SumQueryAggregateColumnBuilder.this.dynAttribute;
			private BigDecimal sum = BigDecimal.ZERO;

			@Override
			public void add(final SourceModelType model)
			{
				if (filters.accept(model))
				{
					final Optional<BigDecimal> value = InterfaceWrapperHelper.getValue(model, getAmountColumnName());
					if (value.isPresent())
					{
						sum = sum.add(value.get());
					}
				}

				// Update target model's dynamic attribute
				dynAttribute.setValue(targetModel, sum);
			}

			@Override
			public BigDecimal getValue()
			{
				return sum;
			}
		};
	}
}

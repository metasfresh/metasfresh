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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryBuilderOrderByClause;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.IQueryOrderByBuilder;
import org.adempiere.model.ModelColumn;

class QueryBuilderOrderByClause<ModelType> implements IQueryBuilderOrderByClause<ModelType>
{
	private final IQueryBuilder<ModelType> parent;
	private final IQueryOrderByBuilder<ModelType> orderByBuilder;

	public QueryBuilderOrderByClause(final IQueryBuilder<ModelType> parent)
	{
		super();

		this.parent = parent; // NOTE: we assume it's not null
		this.orderByBuilder = new QueryOrderByBuilder<>();
	}

	/** Copy constructor */
	private QueryBuilderOrderByClause(final QueryBuilderOrderByClause<ModelType> orderByClause)
	{
		super();
		this.parent = orderByClause.parent;
		this.orderByBuilder = orderByClause.orderByBuilder.copy();
	}

	@Override
	public IQueryBuilder<ModelType> endOrderBy()
	{
		return parent;
	}

	@Override
	public IQueryOrderBy createQueryOrderBy()
	{
		return orderByBuilder.createQueryOrderBy();
	}

	@Override
	public IQueryBuilderOrderByClause<ModelType> clear()
	{
		orderByBuilder.clear();
		return this;
	}

	@Override
	public QueryBuilderOrderByClause<ModelType> copy()
	{
		return new QueryBuilderOrderByClause<>(this);
	}

	@Override
	public IQueryBuilderOrderByClause<ModelType> addColumn(String columnName)
	{
		orderByBuilder.addColumn(columnName);
		return this;
	}

	@Override
	public IQueryBuilderOrderByClause<ModelType> addColumn(ModelColumn<ModelType, ?> column)
	{
		orderByBuilder.addColumn(column);
		return this;
	}

	@Override
	public IQueryBuilderOrderByClause<ModelType> addColumnAscending(String columnName)
	{
		orderByBuilder.addColumnAscending(columnName);
		return this;
	}

	@Override
	public IQueryBuilderOrderByClause<ModelType> addColumnDescending(String columnName)
	{
		orderByBuilder.addColumnDescending(columnName);
		return this;
	}
	
	@Override
	public IQueryBuilderOrderByClause<ModelType> addColumn(String columnName, Direction direction, Nulls nulls)
	{
		orderByBuilder.addColumn(columnName, direction, nulls);
		return this;
	}

	@Override
	public IQueryBuilderOrderByClause<ModelType> addColumn(ModelColumn<ModelType, ?> column, Direction direction, Nulls nulls)
	{
		orderByBuilder.addColumn(column, direction, nulls);
		return this;
	}
}

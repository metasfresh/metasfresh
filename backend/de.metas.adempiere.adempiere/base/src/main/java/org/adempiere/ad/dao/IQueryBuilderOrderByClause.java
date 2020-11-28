package org.adempiere.ad.dao;

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

import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.model.ModelColumn;

/**
 * {@link IQueryBuilder}'s ORDER BY clause.
 *
 * @param <ModelType>
 * @author tsa
 */
public interface IQueryBuilderOrderByClause<ModelType> extends IQueryOrderByBuilder<ModelType>
{
	/**
	 * Ends current ORDER BY clause and returns the {@link IQueryBuilder}.
	 * <p>
	 * This allows the developer to write fluently.
	 */
	IQueryBuilder<ModelType> endOrderBy();

	@Override
	IQueryBuilderOrderByClause<ModelType> clear();

	@Override
	IQueryOrderBy createQueryOrderBy();

	@Override
	IQueryBuilderOrderByClause<ModelType> copy();

	/**
	 * order ascending, with {@code NULLS LAST}
	 */
	@Override
	IQueryBuilderOrderByClause<ModelType> addColumn(String columnName);

	@Override
	IQueryBuilderOrderByClause<ModelType> addColumn(ModelColumn<ModelType, ?> column);

	/**
	 * @deprecated please use {@link #addColumnAscending(String)} and {@link #addColumnDescending(String)}.
	 */
	@Override
	@Deprecated
	default IQueryBuilderOrderByClause<ModelType> addColumn(String columnName, boolean asc)
	{
		return asc ? addColumnAscending(columnName) : addColumnDescending(columnName);
	}

	/**
	 * Note: ascending will by default have {@code NULLS LAST}
	 */
	@Override
	IQueryBuilderOrderByClause<ModelType> addColumnAscending(String columnName);

	/**
	 * Note: descending will by default have {@code NULLS FIRST}
	 */
	@Override
	IQueryBuilderOrderByClause<ModelType> addColumnDescending(String columnName);

	@Override
	IQueryBuilderOrderByClause<ModelType> addColumn(String columnName, Direction direction, Nulls nulls);

	@Override
	IQueryBuilderOrderByClause<ModelType> addColumn(ModelColumn<ModelType, ?> column, Direction direction, Nulls nulls);
}

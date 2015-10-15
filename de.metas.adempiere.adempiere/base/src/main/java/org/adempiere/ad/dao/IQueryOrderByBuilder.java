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

public interface IQueryOrderByBuilder<T>
{
	/** @return created {@link IQueryOrderBy} instance */
	IQueryOrderBy createQueryOrderBy();

	/**
	 * Clear current ordering
	 * 
	 * @return this
	 */
	IQueryOrderByBuilder<T> clear();

	IQueryOrderByBuilder<T> copy();

	IQueryOrderByBuilder<T> addColumn(String columnName);

	IQueryOrderByBuilder<T> addColumn(ModelColumn<T, ?> column);

	IQueryOrderByBuilder<T> addColumn(String columnName, boolean asc);

	IQueryOrderByBuilder<T> addColumn(String columnName, Direction direction, Nulls nulls);

	IQueryOrderByBuilder<T> addColumn(ModelColumn<T, ?> column, Direction direction, Nulls nulls);
}

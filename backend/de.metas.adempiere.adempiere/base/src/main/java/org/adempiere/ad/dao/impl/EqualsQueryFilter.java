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

import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.model.ModelColumn;

/**
 * Filter for equals. Also supports {@code NULL} values.
 * 
 * @param <T>
 */
public class EqualsQueryFilter<T> extends CompareQueryFilter<T>
{
	public static final <T> EqualsQueryFilter<T> isNull(final String columnName)
	{
		return new EqualsQueryFilter<>(columnName, null);
	}

	public static final <T> EqualsQueryFilter<T> of(final String columnName, final Object value)
	{
		return new EqualsQueryFilter<>(columnName, value);
	}

	public static final <T> EqualsQueryFilter<T> of(final ModelColumn<T, ?> column, final Object value)
	{
		return new EqualsQueryFilter<>(column, value);
	}

	public EqualsQueryFilter(final String columnName, final Object value, final IQueryFilterModifier modifier)
	{
		super(columnName, Operator.EQUAL, value, modifier);
	}

	public EqualsQueryFilter(final String columnName, final Object value)
	{
		this(columnName, value, NullQueryFilterModifier.instance);
	}

	public EqualsQueryFilter(final ModelColumn<T, ?> column, final Object value)
	{
		this(column.getColumnName(), value, NullQueryFilterModifier.instance);
	}
}

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


import java.util.List;

/**
 * Used in some {@link IQueryFilter}s as modifiers for column names and values.
 *
 * Use this interface if you want to implement modifiers like UPPER, TRUNC etc.
 *
 */
public interface IQueryFilterModifier
{
	/**
	 * Marker used to say that a value it's from a constant and not an actual column name
	 */
	String COLUMNNAME_Constant = null;

	/**
	 * Decorates given <code>columnSql</code>
	 *
	 * @param columnSql
	 * @return decorated (modified) column SQL
	 */
	String getColumnSql(String columnSql);

	/**
	 * Converts given <code>value</code> to SQL code and optionally adds the parameters to given <code>param</code>.
	 */
	String getValueSql(Object value, List<Object> params);

	/**
	 * Converts given <code>value</code> to normalized form (ready for comparation).
	 *
	 * Mainly this method is called in non-SQL mode, by {@link IQueryFilter#accept(Object)}.
	 *
	 * @param columnName column name or {@link #COLUMNNAME_Constant} (if it's a constant)
	 * @param value
	 * @model model model which is evaluated
	 * @return converted value
	 */
	Object convertValue(String columnName, Object value, Object model);

}

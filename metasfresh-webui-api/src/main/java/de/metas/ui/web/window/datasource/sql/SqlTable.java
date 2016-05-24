package de.metas.ui.web.window.datasource.sql;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.ui.web.vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
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


final class SqlTable
{
	public static final SqlTable of(final String tableName, final String tableAlias)
	{
		return new SqlTable(tableName, tableAlias);
	}

	private final String tableAlias;
	private final String tableName;
	private final String keyColumnName;

	private SqlTable(String tableName, String tableAlias)
	{
		super();
		Check.assumeNotEmpty(tableName, "tableName is not empty");
		Check.assumeNotEmpty(tableAlias, "tableAlias is not empty");

		this.tableAlias = tableAlias;
		this.tableName = tableName;
		this.keyColumnName = tableName + "_ID"; // TODO: hardcoded
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("tableName", tableName)
				.add("tableAlias", tableAlias)
				.toString();
	}

	public String getTableName()
	{
		return tableName;
	}

	public String getTableAlias()
	{
		return tableAlias;
	}

	public String getKeyColumnName()
	{
		return keyColumnName;
	}
}
package de.metas.ui.web.window.datasource.sql;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.descriptor.SqlLookupDescriptor;

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


final class SqlField
{
	public static final SqlField of(final SqlTable sqlTable, final PropertyDescriptor fieldDescriptor)
	{
		return new SqlField(sqlTable, fieldDescriptor);
	}

	private final PropertyName propertyName;

	private final SqlTable sqlTable;
	private final String columnName;
	private final String columnSql;

	private final boolean usingDisplayColumn;
	private final String displayColumnName;
	private final String displayColumnSql;
	private final Boolean numericKey;

	private final int displayType;
	private final boolean encrypted;

	private final boolean keyColumn;

	/** Builder constructor */
	private SqlField(final SqlTable sqlTable, final PropertyDescriptor fieldDescriptor)
	{
		super();
		this.sqlTable = sqlTable;
		propertyName = fieldDescriptor.getPropertyName();
		displayType = fieldDescriptor.getSqlDisplayType();
		encrypted = fieldDescriptor.isSqlEncrypted();

		columnName = fieldDescriptor.getSqlColumnName();
		Check.assumeNotEmpty(columnName, "columnName is not empty");

		String columnSql = fieldDescriptor.getSqlColumnSql();
		if (Check.isEmpty(columnSql, true))
		{
			columnSql = columnName;
		}
		this.columnSql = columnSql;

		//
		// Display column
		final SqlLookupDescriptor lookupDescriptor = fieldDescriptor.getSqlLookupDescriptor();
		if (lookupDescriptor != null)
		{
			usingDisplayColumn = true;
			displayColumnName = columnName + "$Display";
			displayColumnSql = lookupDescriptor.getSqlForFetchingDisplayNameById(sqlTable.getTableAlias() + "." + columnName);
			numericKey = lookupDescriptor.isNumericKey();
		}
		else
		{
			usingDisplayColumn = false;
			displayColumnName = null;
			displayColumnSql = null;
			numericKey = null;
		}

		this.keyColumn = columnName.equalsIgnoreCase(sqlTable.getKeyColumnName());
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("table", sqlTable)
				.add("columnName", columnName)
				.toString();
	}

	public PropertyName getPropertyName()
	{
		return propertyName;
	}
	
	public SqlTable getSqlTable()
	{
		return sqlTable;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public String getColumnSql()
	{
		return columnSql;
	}

	public boolean isUsingDisplayColumn()
	{
		return usingDisplayColumn;
	}

	public String getDisplayColumnName()
	{
		return displayColumnName;
	}

	public String getDisplayColumnSql()
	{
		return displayColumnSql;
	}

	public int getDisplayType()
	{
		return displayType;
	}

	public boolean isEncrypted()
	{
		return encrypted;
	}

	public boolean isNumericKey()
	{
		return numericKey;
	}

	public boolean isKeyColumn()
	{
		return keyColumn;
	}
}
package de.metas.ui.web.window.datasource.sql;

import java.util.LinkedHashMap;
import java.util.Map;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.datasource.IDataSourceFactory;
import de.metas.ui.web.window.datasource.ModelDataSource;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.descriptor.PropertyDescriptorType;

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


final class SqlsBuilder
{
	public static final SqlsBuilder newBuilder()
	{
		return new SqlsBuilder();
	}

	private final IDataSourceFactory dataSourceFactory = Services.get(IDataSourceFactory.class);

	private final Map<String, SqlTable> _tablesByAlias = new LinkedHashMap<>();
	private final Map<PropertyName, SqlField> sqlFields = new LinkedHashMap<>();

	private final Map<PropertyName, ModelDataSource> includedDataSources = new LinkedHashMap<>();
	private SqlField sqlField_KeyColumn;
	//
	private SqlField sqlField_ParentLinkColumn;
	private String sqlParentLinkColumnName;

	private static final String TABLEALIAS_Master = "master";

	private SqlsBuilder()
	{
		super();
	}

	/**
	 * @return SELECT ... FROM ....
	 */
	public String buildSqlSelect()
	{
		final String tableAlias = TABLEALIAS_Master;
		final String tableName = getTableNameForAlias(tableAlias);

		if (sqlFields.isEmpty())
		{
			throw new IllegalStateException("No SQL fields found");
		}

		final StringBuilder sqlSelectValues = new StringBuilder();
		final StringBuilder sqlSelectDisplayNames = new StringBuilder();
		for (final SqlField sqlField : sqlFields.values())
		{
			//
			// Value column
			final String columnSql = sqlField.getColumnSql();
			final String columnName = sqlField.getColumnName();
			//
			if (sqlSelectValues.length() > 0)
			{
				sqlSelectValues.append("\n, ");
			}
			sqlSelectValues.append(columnSql).append(" AS ").append(columnName);

			//
			// Display column, if any
			if (sqlField.isUsingDisplayColumn())
			{
				final String displayColumnName = sqlField.getDisplayColumnName();
				final String displayColumnSql = sqlField.getDisplayColumnSql();
				sqlSelectDisplayNames.append("\n, (").append(displayColumnSql).append(") AS ").append(displayColumnName);
			}
		}

		//
		if (sqlSelectDisplayNames.length() > 0)
		{
			return new StringBuilder()
					.append("SELECT ")
					.append("\n").append(tableAlias).append(".*") // Value fields
					.append("\n").append(sqlSelectDisplayNames) // DisplayName fields
					.append("\n FROM (SELECT ").append(sqlSelectValues).append(" FROM ").append(tableName).append(") ").append(tableAlias) // FROM
					.toString();
		}
		else
		{
			return new StringBuilder()
					.append("SELECT ")
					.append("\n").append(sqlSelectValues)
					.append("\n").append(" FROM ").append(tableName).append(" ").append(tableAlias)
					.toString();
		}
	}

	public Map<PropertyName, SqlField> buildSqlFieldsIndexedByPropertyName()
	{
		return ImmutableMap.copyOf(sqlFields);
	}

	public SqlsBuilder addRootProperty(final PropertyDescriptor rootPropertyDescriptor)
	{
		final String tableAlias = TABLEALIAS_Master;
		final String tableName = rootPropertyDescriptor.getSqlTableName();
		final SqlTable sqlTable = addTable(tableName, tableAlias);
		
		this.sqlParentLinkColumnName = rootPropertyDescriptor.getSqlParentLinkColumnName();
		if (Check.isEmpty(sqlParentLinkColumnName, true))
		{
			sqlParentLinkColumnName = null;
		}

		for (final PropertyDescriptor propertyDescriptor : rootPropertyDescriptor.getChildPropertyDescriptors())
		{
			addProperty(propertyDescriptor, sqlTable);
		}

		//
		// Create Key Column SQL Field
		if (sqlField_KeyColumn == null)
		{
			final String keyColumnName = sqlTable.getKeyColumnName();
			final PropertyDescriptor propertyDescriptor = PropertyDescriptor.builder()
					.setPropertyName(PropertyName.of(keyColumnName))
					.setSqlColumnName(keyColumnName)
					.setType(PropertyDescriptorType.Value)
					.setValueType(Integer.class)
					.build();
			addProperty(propertyDescriptor, sqlTable);
			
			Check.assumeNotNull(sqlField_KeyColumn, "Parameter sqlField_KeyColumn is not null");
		}
		
		//
		// Create Parent Link Column SQL Field
		if (sqlParentLinkColumnName != null && sqlField_ParentLinkColumn == null)
		{
			final PropertyDescriptor propertyDescriptor = PropertyDescriptor.builder()
					.setPropertyName(PropertyName.of(sqlParentLinkColumnName))
					.setSqlColumnName(sqlParentLinkColumnName)
					.setType(PropertyDescriptorType.Value)
					.setValueType(Integer.class)
					.build();
			addProperty(propertyDescriptor, sqlTable);
			
			Check.assumeNotNull(sqlField_ParentLinkColumn, "Parameter sqlField_ParentLinkColumn is not null");
		}

		return this;
	}

	private void addProperty(final PropertyDescriptor propertyDescriptor, final SqlTable sqlTable)
	{
		final PropertyDescriptorType type = propertyDescriptor.getType();

		if (type == PropertyDescriptorType.Group
				|| type == PropertyDescriptorType.ComposedValue)
		{
			addProperty_Group(propertyDescriptor, sqlTable);
		}
		else if (type == PropertyDescriptorType.Tabular)
		{
			addProperty_ChildTable(propertyDescriptor, sqlTable);
		}
		else
		{
			addProperty_SqlField(propertyDescriptor, sqlTable);
		}

	}

	private void addProperty_Group(final PropertyDescriptor groupDescriptor, final SqlTable sqlTable)
	{
		for (final PropertyDescriptor childPropertyDescriptor : groupDescriptor.getChildPropertyDescriptors())
		{
			addProperty(childPropertyDescriptor, sqlTable);
		}
	}

	private void addProperty_ChildTable(final PropertyDescriptor propertyDescriptor, final SqlTable parentSqlTable)
	{
		final String childTableName = propertyDescriptor.getSqlTableName();
		Check.assumeNotEmpty(childTableName, "childTableName is not empty");

		final String childParentLinkColumnName = propertyDescriptor.getSqlParentLinkColumnName();
		Check.assumeNotEmpty(childParentLinkColumnName, "childParentLinkColumnName is not empty");

		final PropertyName propertyName = propertyDescriptor.getPropertyName();
		final ModelDataSource childDataSource = dataSourceFactory.createDataSource(propertyDescriptor);
		final ModelDataSource childDataSourcePrev = includedDataSources.put(propertyName, childDataSource);
		if (childDataSourcePrev != null)
		{
			throw new IllegalStateException("More than one child data sources where registered for " + propertyName + ": " + childDataSourcePrev + ", " + childDataSource);
		}
	}

	private void addProperty_SqlField(final PropertyDescriptor fieldDescriptor, final SqlTable sqlTable)
	{
		final String columnName = fieldDescriptor.getSqlColumnName();
		final boolean isSqlColumn = !Check.isEmpty(columnName, true);
		if (!isSqlColumn)
		{
			return;
		}

		final SqlField sqlField = SqlField.of(sqlTable, fieldDescriptor);
		final PropertyName propertyName = sqlField.getPropertyName();
		final SqlField sqlFieldOld = sqlFields.put(propertyName, sqlField);
		if (sqlFieldOld != null)
		{
			throw new IllegalStateException("More than one SQL field defined for " + propertyName + ": " + sqlFieldOld + ", " + sqlField);
		}

		if (sqlField.isKeyColumn())
		{
			if (sqlField_KeyColumn != null)
			{
				throw new IllegalStateException("More then one key columns found for " + sqlTable + ": " + sqlField_KeyColumn + ", " + sqlField);
			}
			sqlField_KeyColumn = sqlField;
		}
		
		if (sqlParentLinkColumnName != null && sqlParentLinkColumnName.equals(sqlField.getColumnName()))
		{
			if (sqlField_ParentLinkColumn != null)
			{
				throw new IllegalStateException("More then one parent link columns found for " + sqlTable + ": " + sqlField_ParentLinkColumn + ", " + sqlField);
			}
			sqlField_ParentLinkColumn = sqlField;
		}
	}

	private SqlTable addTable(final String tableName, final String tableAlias)
	{
		final SqlTable table = SqlTable.of(tableName, tableAlias);

		final SqlTable tableOld = _tablesByAlias.get(tableAlias);
		if (tableOld != null)
		{
			throw new IllegalArgumentException("Table alias " + tableAlias + " was already allocated to table " + tableOld);
		}

		_tablesByAlias.put(tableAlias, table);
		
		return table;
	}
	
	public String getMainTableName()
	{
		return getTableNameForAlias(TABLEALIAS_Master);
	}

	private SqlTable getTableForAlias(final String tableAlias)
	{
		final SqlTable table = _tablesByAlias.get(tableAlias);
		Check.assumeNotNull(table, "table shall be defined for alias: {}", tableAlias);
		return table;
	}

	private String getTableNameForAlias(final String tableAlias)
	{
		return getTableForAlias(tableAlias).getTableName();
	}
	
	public SqlField getKeyColumn()
	{
		return sqlField_KeyColumn;
	}
	
	public SqlField getParentLinkColumn()
	{
		return sqlField_ParentLinkColumn;
	}
	
	public Map<PropertyName, ModelDataSource> getIncludedDataSources()
	{
		return includedDataSources;
	}
}
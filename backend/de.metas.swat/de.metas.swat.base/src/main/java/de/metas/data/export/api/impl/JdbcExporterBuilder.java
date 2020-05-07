package de.metas.data.export.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.base.Joiner;

import de.metas.data.export.api.IExportDataSource;
import de.metas.logging.LogManager;

/**
 * Helper class for building {@link JdbcExportDataSource}s.
 * 
 * @author tsa
 * 
 */
public class JdbcExporterBuilder
{
	private static final Logger logger = LogManager.getLogger(JdbcExporterBuilder.class);

	private final String tableName;

	/**
	 * Map: CSV Field Name to {@link Column}
	 */
	private final Map<String, Column> name2columns = new LinkedHashMap<>();

	private int nextInternalFieldIndex = 1;

	// private final List<String> sqlInternalDbColumnNames = new ArrayList<String>();
	private final StringBuilder sqlWhereClause = new StringBuilder();
	private final List<Object> sqlWhereClauseParams = new ArrayList<>();

	private final List<String> sqlOrderBys = new ArrayList<>();

	public JdbcExporterBuilder(final String tableName)
	{
		Check.assumeNotNull(tableName, "tableName not null");
		this.tableName = tableName;
	}

	public IExportDataSource createDataSource()
	{
		final List<String> csvFields = new ArrayList<>();
		final List<String> sqlFields = new ArrayList<>();
		for (final JdbcExporterBuilder.Column field : getFields())
		{
			if (!field.isExported())
			{
				logger.debug("Skip field {} from csv columns list because is not exported");
				continue;
			}

			csvFields.add(field.getName());
			sqlFields.add(field.getDbColumnName());
		}

		final String sqlWhereClause = getSqlWhereClause();

		// SQL Select
		final List<Object> sqlParams = new ArrayList<>();
		final String sqlSelect = getSqlSelect(sqlParams);

		// SQL Select Count
		final String sqlSelectCount = "SELECT COALESCE(COUNT(DISTINCT (" + getSqlSelectFields(false) + ")),0)"
				+ "\r\n FROM " + tableName
				+ "\r\n WHERE \r\n" + sqlWhereClause;

		final JdbcExportDataSource dataSource = new JdbcExportDataSource(csvFields, sqlFields, sqlSelect, sqlSelectCount, sqlWhereClause, sqlParams);
		return dataSource;
	}

	public String getTableName()
	{
		return tableName;
	}

	public Column getColumnByDbColumnName(String dbColumnName)
	{
		for (final Column column : name2columns.values())
		{
			if (dbColumnName.equals(column.getDbColumnName()))
			{
				return column;
			}
		}

		return null;
	}

	public Column addField(final String dbColumnName)
	{
		final String name = dbColumnName;
		return addField(name, dbColumnName);
	}

	public Column addField(final String name, final String dbColumnName)
	{
		Check.assumeNotNull(name, "name not null");
		final Column column = new Column(name, tableName, dbColumnName, null);
		name2columns.put(name, column);

		return column;
	}

	public Column addFieldFromSQL(final String name, final String dbColumnSQL)
	{
		Check.assumeNotNull(name, "name not null");
		Check.assumeNotNull(dbColumnSQL, "sql not null");
		Check.assume(!Check.isEmpty(dbColumnSQL, true), "sql not empty");

		final String dbColumnName = createInternalColumnName();
		final Column column = new Column(name, null, dbColumnName, dbColumnSQL);
		name2columns.put(name, column);

		return column;
	}

	public Column addFieldConstant(final String name, final String constantValue)
	{
		final String dbColumnSQL = DB.TO_STRING(constantValue)
				+ "::text" // TODO: HARDCODED: for Postgresql
		;
		return addFieldFromSQL(name, dbColumnSQL);
	}

	/**
	 * Add an internal selection db column. This kind of column won't be used in export but it will be included in internal SELECT and so it will affect counting and "SELECT DISTINCT".
	 * 
	 * @param dbColumnName
	 * @return this
	 */
	public Column addInternalField(final String dbColumnName)
	{
		final String name = createInternalColumnName();
		final Column column = addField(name, dbColumnName);
		column.setExported(false);

		return column;
	}

	private String createInternalColumnName()
	{
		final String dbColumnName = "internal" + nextInternalFieldIndex;
		nextInternalFieldIndex++;

		return dbColumnName;
	}

	public JdbcExporterBuilder addLikeWhereClause(String dbColumnName, Object value)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder whereClause = new StringBuilder();
		if (value != null)
		{
			String search = ((String)value).trim();
			if (!search.startsWith("%"))
				search = "%" + search;
			if (!search.endsWith("%"))
				search += "%";
			whereClause.append("UPPER(").append(dbColumnName).append(") LIKE UPPER(?)");
			sqlParams.add(search);
		}

		return addWhereClause(whereClause.toString(), sqlParams);
	}

	public JdbcExporterBuilder addEqualsWhereClause(String dbColumnName, Object value)
	{
		Check.assumeNotNull(dbColumnName, "dbColumnName not null");

		if (value == null)
		{
			return addWhereClause(dbColumnName + " IS NULL");
		}

		return addWhereClause(dbColumnName + "=?", value);
	}

	public JdbcExporterBuilder addNotEqualsWhereClause(String dbColumnName, Object value)
	{
		Check.assumeNotNull(dbColumnName, "dbColumnName not null");

		if (value == null)
		{
			return addWhereClause(dbColumnName + " IS NOT NULL");
		}

		return addWhereClause(dbColumnName + "<>?", value);
	}

	public JdbcExporterBuilder addBetweenWhereClause(String dbColumnName, Object valueFrom, Object valueTo)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder whereClause = new StringBuilder();
		if (valueFrom != null)
		{
			whereClause.append(dbColumnName).append(">=?");
			sqlParams.add(valueFrom);
		}
		if (valueTo != null)
		{
			if (whereClause.length() > 0)
			{
				whereClause.append(" AND ");
			}
			whereClause.append(dbColumnName).append("<=?");
			sqlParams.add(valueTo);
		}

		return addWhereClause(whereClause.toString(), sqlParams);
	}

	public JdbcExporterBuilder addWhereClause(String whereClause, Object... params)
	{
		final List<Object> paramsList = params == null || params.length == 0 ? Collections.emptyList() : Arrays.asList(params);
		return addWhereClause(whereClause, paramsList);
	}

	public JdbcExporterBuilder addWhereClause(String whereClause, List<Object> params)
	{
		if (!Check.isEmpty(whereClause, true))
		{
			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append("\r\n AND ");
			}
			sqlWhereClause.append("(").append(whereClause).append(")");
		}

		if (params != null && !params.isEmpty())
		{
			sqlWhereClauseParams.addAll(params);
		}

		return this;
	}

	private Collection<Column> getFields()
	{
		return name2columns.values();
	}

	public String getSqlWhereClause()
	{
		return sqlWhereClause.toString();
	}

	public List<Object> getSqlParams()
	{
		return sqlWhereClauseParams;
	}

	private String getSqlSelectFields(boolean addFieldAliases)
	{
		final StringBuilder sqlSelectColumns = new StringBuilder();

		for (final Column column : name2columns.values())
		{
			// final String dbColumnName = f.getValue();
			// final String dbColumnSQL = dbColumnName2sql.get(dbColumnName);

			final StringBuffer dbColumnName = new StringBuffer();
			if (Check.isEmpty(column.getDbColumnSQL()))
			{
				dbColumnName.append(column.getDbTableNameOrAlias()).append(".").append(column.getDbColumnName());
			}
			else
			{
				dbColumnName.append("(").append(column.getDbColumnSQL()).append(")");
			}

			if (addFieldAliases)
			{
				dbColumnName.append(" AS ").append(column.getDbColumnName());
			}

			if (sqlSelectColumns.length() > 0)
			{
				sqlSelectColumns.append("\r\n,");
			}
			sqlSelectColumns.append(dbColumnName);
		}

		if (sqlSelectColumns.length() <= 0)
		{
			throw new AdempiereException("No export fields where specified");
		}

		// Add the ORDER BYs to select columns because it's needed when we SELECT DISTINCT
		final List<String> sqlOrderBys = getSqlOrderBys();
		if (!sqlOrderBys.isEmpty())
		{
			if (sqlSelectColumns.length() > 0)
			{
				sqlSelectColumns.append("\r\n,");
			}
			sqlSelectColumns.append(Joiner.on("\r\n, ").join(sqlOrderBys));
		}

		return sqlSelectColumns.toString();
	}

	private String getSqlSelect(final List<Object> outSqlParams)
	{
		//
		// SQL Select Columns
		final String sqlSelectColumns = getSqlSelectFields(true);

		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT \r\n").append(sqlSelectColumns)
				.append("\r\n FROM ").append(tableName);

		final String whereClause = getSqlWhereClause();
		if (!Check.isEmpty(whereClause))
		{
			sql.append("\r\n WHERE \r\n").append(whereClause);
			outSqlParams.addAll(getSqlParams());
		}

		final List<String> sqlOrderBys = getSqlOrderBys();
		if (!sqlOrderBys.isEmpty())
		{
			sql.append("\r\n ORDER BY \r\n").append(Joiner.on(", ").join(sqlOrderBys));
		}

		return sql.toString();
	}

	public JdbcExporterBuilder addOrderBy(final String sqlOrderBy)
	{
		Check.assumeNotEmpty(sqlOrderBy, "sqlOrderBy is not empty");
		this.sqlOrderBys.add(sqlOrderBy);
		return this;
	}

	private List<String> getSqlOrderBys()
	{
		return sqlOrderBys;
	}

	public static class Column
	{
		private final String name;
		private final String dbTableNameOrAlias;
		private final String dbColumnName;
		private final String dbColumnSQL;
		private boolean exported;

		public Column(String name, String dbTableNameOrAlias, String dbColumnName, String dbColumnSQL)
		{
			super();
			this.name = name;
			this.dbTableNameOrAlias = dbTableNameOrAlias;
			this.dbColumnName = dbColumnName;
			this.dbColumnSQL = dbColumnSQL;
			this.exported = true;
		}

		@Override
		public String toString()
		{
			return "Column ["
					+ "name=" + name
					+ ", dbTableNameOrAlias=" + dbTableNameOrAlias
					+ ", dbColumnName=" + dbColumnName
					+ ", dbColumnSQL=" + dbColumnSQL
					+ ", exported=" + exported
					+ "]";
		}

		public String getName()
		{
			return name;
		}

		public String getDbTableNameOrAlias()
		{
			return dbTableNameOrAlias;
		}

		public String getDbColumnName()
		{
			return dbColumnName;
		}

		public String getDbColumnSQL()
		{
			return dbColumnSQL;
		}

		public boolean isExported()
		{
			return exported;
		}

		public void setExported(boolean exported)
		{
			this.exported = exported;
		}

	}
}

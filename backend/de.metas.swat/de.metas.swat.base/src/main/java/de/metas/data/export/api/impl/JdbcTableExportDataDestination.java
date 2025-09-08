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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

import org.compiere.util.DB;
import org.compiere.util.Trx;

import de.metas.data.export.api.IExportDataDestination;

public class JdbcTableExportDataDestination implements IExportDataDestination
{
	private static final Logger logger = LogManager.getLogger(JdbcTableExportDataDestination.class);

	private static class Column
	{
		private final String columnName;
		private final String sourceColumnName;
		private final Object constantValue;

		public Column(String columnName, String sourceColumnName, Object constantValue)
		{
			super();
			this.columnName = columnName;
			this.sourceColumnName = sourceColumnName;
			this.constantValue = constantValue;
		}

		public String getColumnName()
		{
			return columnName;
		}

		public String getSourceColumnName()
		{
			return sourceColumnName;
		}

		public Object getConstantValue()
		{
			return constantValue;
		}
	}

	private final String tableName;
	private final Map<String, Column> fields = new LinkedHashMap<String, Column>();

	public JdbcTableExportDataDestination(final String tableName)
	{
		super();
		this.tableName = tableName;
	}

	@Override
	public void appendLine(List<Object> values) throws IOException
	{
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void close() throws IOException
	{
		// nothing
	}

	public void append(final JdbcExportDataSource source)
	{
		final StringBuilder sqlInsert = new StringBuilder();

		final StringBuilder sqlSelect = new StringBuilder();
		final List<Object> sqlSelectParams = new ArrayList<Object>();
		for (final Column field : fields.values())
		{
			final String columnName = field.getColumnName();

			//
			// INSERT part
			if (sqlInsert.length() > 0)
			{
				sqlInsert.append(", ");
			}
			sqlInsert.append(columnName);

			if (sqlSelect.length() > 0)
			{
				sqlSelect.append("\n, ");
			}

			//
			// SELECT part
			final String sourceColumnName = field.getSourceColumnName();
			if (!Check.isEmpty(sourceColumnName))
			{
				sqlSelect.append(sourceColumnName).append(" AS ").append(columnName);
			}
			// Constant
			else
			{
				sqlSelect.append("? AS ").append(columnName);
				sqlSelectParams.add(field.getConstantValue());
			}
		}

		final String sql = new StringBuilder()
				.append("INSERT INTO ").append(tableName).append(" (").append(sqlInsert).append(")")
				.append("\nSELECT ").append(sqlSelect)
				.append("\nFROM (").append(source.getSqlSelect()).append(") t")
				.toString();

		final List<Object> sqlParams = new ArrayList<Object>();
		sqlParams.addAll(sqlSelectParams);
		sqlParams.addAll(source.getSqlParams());

		final String trxName = Trx.TRXNAME_None;
		final int count = DB.executeUpdateAndThrowExceptionOnFail(sql, sqlParams.toArray(), trxName);
		logger.info("Inserted {} records into {} from {}", new Object[] { count, tableName, source });
	}

	public void addField(final String fieldName, final String sourceFieldName)
	{
		final Column field = new Column(fieldName, sourceFieldName, null);
		fields.put(fieldName, field);
	}

	public void addConstant(final String fieldName, final Object value)
	{
		final Column field = new Column(fieldName, null, value);
		fields.put(fieldName, field);
	}
}

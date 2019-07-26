/**
 *
 */
package de.metas.adempiere.service.impl;

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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.adempiere.model.ITableColumnPath;
import de.metas.adempiere.model.ITableColumnPathElement;
import de.metas.adempiere.model.TableColumnPath;
import de.metas.adempiere.model.TableColumnPathElement;
import de.metas.adempiere.model.TableColumnPathException;
import de.metas.adempiere.service.IAppDictionaryBL;
import de.metas.adempiere.service.ITableColumnPathBL;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * @author tsa
 *
 */
public class TableColumnPathBL implements ITableColumnPathBL
{
	private static final Logger logger = LogManager.getLogger(TableColumnPathBL.class);

	@Override
	public Object getValueByPath(Properties ctx, String tableName, int id, String pathStr)
	{
		ITableColumnPath path = compilePath(ctx, tableName, id, pathStr);
		// System.out.println("Path: " + path + "\n------------------------------\n");
		String sql = getSQL(path);
		// System.out.println(pathStr + " - SQL: " + sql + "\n");
		Object value = getSQLValueObjectEx(null, sql.toString());

		// System.out.println("Value=" + value);

		return value;
	}

	private ITableColumnPath compilePath(Properties ctx, String tableName, int id, String pathStr)
	{
		final MTable table0 = MTable.get(ctx, tableName);
		final TableColumnPath path = new TableColumnPath(tableName, getKeyColumn(table0), id);

		String[] pathElements = pathStr.split("\\.");
		String valueColumnName = pathElements[pathElements.length - 1];

		MTable parentTable = table0;
		for (int i = 0; i < pathElements.length - 1; i++)
		{
			final String pe = pathElements[i];
			final MColumn column = parentTable.getColumn(pe);
			if (column == null)
			{
				throw new TableColumnPathException(pathStr, "Column " + pe + " not found in table " + parentTable.getTableName()); // TODO:
				// AD_Message
			}
			final MTable table = Services.get(IAppDictionaryBL.class).getReferencedTable(parentTable, pe);
			if (table == null)
			{
				throw new TableColumnPathException(pathStr, "No table found for column " + pe); // TODO: AD_Message
			}

			String columnSQL = null;
			
			if (column.isVirtualColumn())
			{
				columnSQL = column.getColumnSQL();
			}

			String keyColumn = getKeyColumn(table);
			path.addElement(new TableColumnPathElement(
					table.getTableName(),
					keyColumn,
					parentTable.getTableName(),
					column.getColumnName(),
					columnSQL));
			parentTable = table;
		}

		MColumn valueColumn = parentTable.getColumn(valueColumnName);
		if (valueColumn == null)
		{
			throw new TableColumnPathException(pathStr, "Value column " + valueColumnName + " not found in table " + parentTable.getTableName());
		}
		path.setValueColumnName(parentTable.getTableName(), valueColumn.getColumnName(), valueColumn.getAD_Reference_ID());

		return path;
	}

	public String getSQL(ITableColumnPath path)
	{
		StringBuffer sqlFROM = new StringBuffer();
		sqlFROM.append("FROM ").append(path.getKeyTableName());
		for (final ITableColumnPathElement e : path.getElements())
		{
			String parentColumnSQL = e.getParentColumnSQL();
			if (Check.isEmpty(parentColumnSQL))
			{
				parentColumnSQL = e.getParentTableName() + "." + e.getParentColumnName();
			}
			else
			{
				if (!parentColumnSQL.startsWith("("))
				{
					parentColumnSQL = "( " + parentColumnSQL + " )";
				}
			}

			sqlFROM.append("\nINNER JOIN ").append(e.getTableName()).append(" ON (")
					.append(e.getTableName()).append(".").append(e.getColumnName())
					.append("=").append(parentColumnSQL)
					.append(")");
		}

		// Where Clause
		sqlFROM.append("\nWHERE ").append(path.getKeyTableName()).append(".").append(path.getKeyColumnName())
				.append("=").append(path.getRecordId());

		StringBuffer sql = new StringBuffer("SELECT ")
				.append(path.getValueTableName()).append(".").append(path.getValueColumnName())
				.append(" ").append(sqlFROM);
		return sql.toString();
	}

	private String getKeyColumn(MTable table)
	{
		String[] keyColumns = table.getKeyColumns();
		if (keyColumns == null || keyColumns.length != 1)
		{
			throw new TableColumnPathException(null, "Table " + table.getTableName() + " should have one and only one key column");
		}
		String keyColumn = keyColumns[0];
		return keyColumn;
	}

	public static Object getSQLValueObjectEx(String trxName, String sql, Object... params)
	{
		Object retValue = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = rs.getObject(1);
			}
			else
			{
				logger.info("No Value " + sql);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return retValue;
	}

}

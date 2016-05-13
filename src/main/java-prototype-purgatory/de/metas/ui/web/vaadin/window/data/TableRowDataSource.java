package de.metas.ui.web.vaadin.window.data;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.KeyNamePair;
import org.compiere.util.SecureEngine;
import org.compiere.util.TrxRunnableAdapter;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.base.Strings;

import de.metas.logging.LogManager;
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
import de.metas.ui.web.vaadin.window.descriptor.DataFieldLookupDescriptor;
import de.metas.ui.web.vaadin.window.descriptor.DataFieldPropertyDescriptor;
import de.metas.ui.web.vaadin.window.descriptor.DataRowDescriptor;
import de.metas.ui.web.vaadin.window.model.DataRowContainer;
import de.metas.ui.web.vaadin.window.model.DataRowItem;

public class TableRowDataSource
{
	private static final Logger logger = LogManager.getLogger(TableRowDataSource.class);

	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private final DataRowDescriptor descriptor;

	/** SQL: SELECT ... FROM ... */
	private String sqlSelect;

	// private String sqlSelectCount;

	public TableRowDataSource(final DataRowDescriptor descriptor)
	{
		super();
		this.descriptor = descriptor;

		//
		// Init
		init();
	}

	private void init()
	{
		final String tableName = descriptor.getTableName();
		if (Strings.isNullOrEmpty(tableName))
		{
			throw new IllegalStateException("No table name in " + descriptor);
		}

		final Collection<DataFieldPropertyDescriptor> fieldDescriptors = descriptor.getFieldDescriptors();
		if (fieldDescriptors.isEmpty())
		{
			throw new IllegalStateException("No field descriptors in " + descriptor);
		}

		//
		// Create SELECT Part
		this.sqlSelect = buildSqlSelectPart();

		// sqlSelectCount = "SELECT COUNT(*) FROM " + tableName;

		// m_whereClauseFinal = createSelectWhereClauseSql();
		//
		// // RO/RW Access
		// m_SQL = m_SQL_Select + " WHERE " + m_whereClauseFinal.toString();
		// m_SQL_Count += " WHERE " + m_whereClauseFinal.toString();
		//
		// {
		// final IUserRolePermissions role = Env.getUserRolePermissions(m_ctx);
		// m_SQL = role.addAccessSQL(m_SQL, m_tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
		// m_SQL_Count = role.addAccessSQL(m_SQL_Count, m_tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
		// }
		//
		// // ORDER BY
		// if (!m_orderClause.equals(""))
		// {
		// m_SQL += " ORDER BY " + m_orderClause;
		// }
		//
		// // return m_SQL;
		//
	}

	private final String buildSql(final Query query)
	{
		final StringBuilder sql = new StringBuilder();

		sql.append(sqlSelect);

		final String whereClause = buildSqlWhereClause(query);
		if (!Strings.isNullOrEmpty(whereClause))
		{
			sql.append("\n WHERE ").append(whereClause);
		}

		final String orderBy = buildSqlOrderBy(query);
		if (!Strings.isNullOrEmpty(orderBy))
		{
			sql.append("\n ORDER BY ").append(orderBy);
		}

		final String limit = buildSqlLimit(query);
		if (!Strings.isNullOrEmpty(limit))
		{
			sql.append("\n LIMIT ").append(limit);
		}

		return sql.toString();
	}

	private final String buildSqlWhereClause(final Query query)
	{
		// TODO
		return null;
	}

	private String buildSqlOrderBy(Query query)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private String buildSqlLimit(Query query)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void refresh(final DataRowContainer container, final Query query)
	{
		trxManager.run(new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				refreshInTrx(container, query);
			}
		});
	}

	private final void refreshInTrx(final DataRowContainer container, final Query query)
	{
		final String sql = buildSql(query);

		final List<DataRowItem> rows = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final DataRowItem row = new DataRowItem(descriptor);
				loadRow(row, rs);
				rows.add(row);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		container.setRows(rows);
	}

	private void loadRow(final DataRowItem row, final ResultSet rs) throws SQLException
	{
		for (final DataFieldPropertyDescriptor fieldDescriptor : descriptor.getFieldDescriptors())
		{
			if (!fieldDescriptor.isSqlColumn())
			{
				continue;
			}

			final Object value = retrieveField(rs, fieldDescriptor);
			row.setPropertyValue(fieldDescriptor.getColumnName(), value);
		}
	}

	/** @return SELECT ... FROM .... */
	private String buildSqlSelectPart()
	{
		final String tableName = descriptor.getTableName();
		final String tableAlias = "master";

		final StringBuilder sqlSelectValues = new StringBuilder();
		final StringBuilder sqlSelectDisplayNames = new StringBuilder();
		for (final DataFieldPropertyDescriptor fieldDescriptor : descriptor.getFieldDescriptors())
		{
			if (!fieldDescriptor.isSqlColumn())
			{
				return null;
			}

			//
			// Value
			final String columnName = fieldDescriptor.getColumnName();
			final String columnSql = fieldDescriptor.getSqlColumnCode();
			if (sqlSelectValues.length() > 0)
			{
				sqlSelectValues.append("\n, ");
			}
			sqlSelectValues.append(columnSql).append(" AS ").append(columnName);

			//
			// DisplayName
			final DataFieldLookupDescriptor lookupDescriptor = fieldDescriptor.getLookupDescriptor();
			if (lookupDescriptor != null)
			{
				final String sqlDisplayName = lookupDescriptor.getSqlForFetchingDisplayNameById(tableAlias + "." + columnName);
				sqlSelectDisplayNames.append("\n, (").append(sqlDisplayName).append(") AS ").append(columnName + "_Display");
			}
		}

		//
		if (sqlSelectDisplayNames.length() > 0)
		{
			return new StringBuilder()
					.append("SELECT ")
					.append(tableAlias).append(".*") // Value fields
					.append(sqlSelectDisplayNames) // DisplayName fields
					.append("\n FROM (SELECT ").append(sqlSelectValues).append(" FROM ").append(tableName).append(") ").append(tableAlias) // FROM
					.toString();
		}
		else
		{
			return new StringBuilder()
					.append("SELECT ")
					.append(sqlSelectValues)
					.append(" FROM ").append(tableName)
					.toString();

		}
	}

	/*
	 * Based on org.compiere.model.GridTable.readData(ResultSet)
	 */
	private Object retrieveField(final ResultSet rs, final DataFieldPropertyDescriptor fieldDescriptor) throws SQLException
	{
		final String columnName = fieldDescriptor.getColumnName();
		final int displayType = fieldDescriptor.getDisplayType();
		final DataFieldLookupDescriptor lookupDescriptor = fieldDescriptor.getLookupDescriptor();
		boolean encrypted = fieldDescriptor.isEncrypted();

		final Object value;

		if (lookupDescriptor != null)
		{
			final String displayName = rs.getString(columnName + "_Display");
			if (lookupDescriptor.isNumericKey())
			{
				final int id = rs.getInt(columnName);
				value = rs.wasNull() ? null : new KeyNamePair(id, displayName);
			}
			else
			{
				final String key = rs.getString(columnName);
				value = rs.wasNull() ? null : new ValueNamePair(key, displayName);
			}
			encrypted = false;
		}
		// Integer, ID, Lookup (UpdatedBy is a numeric column)
		else if (displayType == DisplayType.Integer
				|| DisplayType.isID(displayType)
				&& (columnName.endsWith("_ID") || columnName.endsWith("_Acct")
						|| columnName.equals("AD_Key") || columnName.equals("AD_Display"))
				|| columnName.endsWith("atedBy")
				|| "Record_ID".equals(columnName) && DisplayType.Button == displayType // metas: Record_ID buttons are Integer IDs
		)
		{
			final int valueInt = rs.getInt(columnName);
			value = rs.wasNull() ? null : valueInt;
		}
		// Number
		else if (DisplayType.isNumeric(displayType))
		{
			value = rs.getBigDecimal(columnName);
		}
		// Date
		else if (DisplayType.isDate(displayType))
		{
			value = rs.getTimestamp(columnName);
		}
		// RowID or Key (and Selection)
		else if (displayType == DisplayType.RowID)
		{
			value = null;
		}
		// YesNo
		else if (displayType == DisplayType.YesNo)
		{
			String valueStr = rs.getString(columnName);
			if (encrypted)
			{
				valueStr = decrypt(valueStr).toString();
				encrypted = false;
			}

			value = DisplayType.toBoolean(valueStr, false);
		}
		// LOB
		else if (DisplayType.isLOB(displayType))
		{
			final Object valueObj = rs.getObject(columnName);
			if (rs.wasNull())
			{
				value = null;
			}
			else if (valueObj instanceof Clob)
			{
				final Clob lob = (Clob)valueObj;
				final long length = lob.length();
				value = lob.getSubString(1, (int)length);
			}
			else if (valueObj instanceof Blob)
			{
				final Blob lob = (Blob)valueObj;
				final long length = lob.length();
				value = lob.getBytes(1, (int)length);
			}
			else if (valueObj instanceof String)
			{
				value = valueObj;
			}
			else if (valueObj instanceof byte[])
			{
				value = valueObj;
			}
			else
			{
				logger.warn("Unknown LOB value '{}' for {}. Considering it null.", new Object[] { valueObj, fieldDescriptor });
				value = null;
			}
		}
		// String
		else
		{
			value = rs.getString(columnName);
		}

		// Decrypt if needed
		final Object valueDecrypted = encrypted ? decrypt(value) : value;

		return valueDecrypted;
	}

	private static final Object decrypt(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		return SecureEngine.decrypt(value);
	}	// decrypt

}

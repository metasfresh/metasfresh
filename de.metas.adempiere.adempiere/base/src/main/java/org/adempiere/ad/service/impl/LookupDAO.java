package org.adempiere.ad.service.impl;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.security.permissions.UIDisplayedEntityTypes;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.impl.CompositeValidationRule;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.adempiere.db.util.AbstractPreparedStatementBlindIterator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.ILookupDisplayColumn;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.LookupDisplayColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.MQuery;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.util.cache.annotations.CacheAllowMutable;
import de.metas.logging.LogManager;

public class LookupDAO implements ILookupDAO
{
	private static final transient Logger logger = LogManager.getLogger(LookupDAO.class);

	private final static String COLUMNNAME_Value = "Value";

	private static final ITableRefInfo tableRefInfo_Account = TableRefInfo.builder()
			.setName("Account")
			.setTableName(I_C_ValidCombination.Table_Name)
			.setKeyColumn(I_C_ValidCombination.COLUMNNAME_C_ValidCombination_ID)
			.setAutoComplete(true)
			.build();

	/* package */static class ColumnInfo implements IColumnInfo
	{
		private final String ColumnName;
		private final int AD_Reference_Value_ID;
		private final boolean parent;
		// String ValidationCode = "";
		private final int AD_Val_Rule_ID;

		public ColumnInfo(final String columnName, final int adReferenceValueId, final boolean isParent, final int adValRuleId)
		{
			super();
			ColumnName = columnName;
			AD_Reference_Value_ID = adReferenceValueId;
			parent = isParent;
			AD_Val_Rule_ID = adValRuleId;
		}

		@Override
		public String getColumnName()
		{
			return ColumnName;
		}

		@Override
		public int getAD_Reference_Value_ID()
		{
			return AD_Reference_Value_ID;
		}

		@Override
		public boolean isParent()
		{
			return parent;
		}

		@Override
		public int getAD_Val_Rule_ID()
		{
			return AD_Val_Rule_ID;
		}
	}

	@Immutable
	/* package */static final class TableRefInfo implements ITableRefInfo
	{
		public static TableRefInfoBuilder builder()
		{
			return new TableRefInfoBuilder();
		}

		// NOTE to developer:
		// * make sure all the fields are primitives or immutable.
		// * when adding new fields, please change "equals" and "hashCode" methods too

		@SuppressWarnings("unused")
		private final String name; // used only for debugging
		private final String tableName;
		private final String keyColumn;
		private final String displayColumn;
		private final boolean valueDisplayed;
		private final boolean translated;
		private final String whereClause;
		private final String orderByClause;
		private final int zoomWindow;
		private final int zoomWindowPO;
		private final String displayColumnSQL;
		private final int overrideZoomWindow;
		private final boolean autoComplete;

		private TableRefInfo(final TableRefInfoBuilder builder)
		{
			super();

			name = builder.name;

			Check.assumeNotEmpty(builder.tableName, "tableName not empty");
			tableName = builder.tableName;

			Check.assumeNotEmpty(builder.keyColumn, "keyColumn not empty");
			keyColumn = builder.keyColumn;

			if (!Check.isEmpty(builder.displayColumn, true))
			{
				displayColumn = builder.displayColumn;
			}
			else
			{
				displayColumn = null;
			}

			if (!Check.isEmpty(builder.displayColumnSQL, true))
			{
				displayColumnSQL = builder.displayColumnSQL;
			}
			else
			{
				displayColumnSQL = null;
			}

			valueDisplayed = builder.valueDisplayed;
			translated = builder.translated;

			if (!Check.isEmpty(builder.whereClause, true))
			{
				whereClause = builder.whereClause;
			}
			else
			{
				whereClause = null;
			}

			if (!Check.isEmpty(builder.orderByClause, true))
			{
				orderByClause = builder.orderByClause;
			}
			else
			{
				orderByClause = null;
			}

			zoomWindow = builder.zoomWindow <= 0 ? -1 : builder.zoomWindow;
			zoomWindowPO = builder.zoomWindowPO <= 0 ? -1 : builder.zoomWindowPO;
			overrideZoomWindow = builder.overrideZoomWindow <= 0 ? -1 : builder.overrideZoomWindow;

			autoComplete = builder.autoComplete;
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		@Override
		public int hashCode()
		{
			return new HashcodeBuilder()
					.append(tableName)
					.append(keyColumn)
					.append(displayColumn)
					.append(displayColumnSQL)
					.append(valueDisplayed)
					.append(whereClause)
					.append(orderByClause)
					.append(translated)
					.append(zoomWindow)
					.append(zoomWindowPO)
					.append(overrideZoomWindow)
					.append(autoComplete)
					.toHashcode();
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}

			final TableRefInfo other = EqualsBuilder.getOther(this, obj);
			if (other == null)
			{
				return false;
			}

			return new EqualsBuilder()
					.append(tableName, other.tableName)
					.append(keyColumn, other.keyColumn)
					.append(displayColumn, other.displayColumn)
					.append(displayColumnSQL, other.displayColumnSQL)
					.append(valueDisplayed, other.valueDisplayed)
					.append(whereClause, other.whereClause)
					.append(orderByClause, other.orderByClause)
					.append(translated, other.translated)
					.append(zoomWindow, other.zoomWindow)
					.append(zoomWindowPO, other.zoomWindowPO)
					.append(overrideZoomWindow, other.overrideZoomWindow)
					.append(autoComplete, other.autoComplete)
					.isEqual();
		}

		@Override
		public String getTableName()
		{
			return tableName;
		}

		@Override
		public String getKeyColumn()
		{
			return keyColumn;
		}

		@Override
		public String getDisplayColumn()
		{
			return displayColumn;
		}

		@Override
		public boolean isValueDisplayed()
		{
			return valueDisplayed;
		}

		@Override
		public boolean isTranslated()
		{
			return translated;
		}

		@Override
		public String getWhereClause()
		{
			return whereClause;
		}

		@Override
		public String getOrderByClause()
		{
			return orderByClause;
		}

		@Override
		public int getZoomWindow()
		{
			return zoomWindow;
		}

		@Override
		public int getZoomWindowPO()
		{
			return zoomWindowPO;
		}

		@Override
		public String getDisplayColumnSQL()
		{
			return displayColumnSQL;
		}

		@Override
		public int getOverrideZoomWindow()
		{
			return overrideZoomWindow;
		}

		@Override
		public boolean isAutoComplete()
		{
			return autoComplete;
		}

		@Override
		public boolean isNumericKey()
		{

			final boolean isNumeric = KeyColumn.endsWith("_ID");
			return isNumeric;

		}
	}

	static final class TableRefInfoBuilder
	{
		private String name; // used only for debugging
		private String tableName;
		private String keyColumn;
		private String displayColumn = null;
		private String displayColumnSQL = null;
		private boolean valueDisplayed = false;
		private boolean translated = false;
		private String whereClause = null;
		private String orderByClause = null;
		private int zoomWindow = -1;
		private int zoomWindowPO = -1;
		private int overrideZoomWindow = -1;
		private boolean autoComplete = true;

		private TableRefInfoBuilder()
		{
			super();
		}

		public TableRefInfo build()
		{
			return new TableRefInfo(this);
		}

		public TableRefInfoBuilder setName(final String name)
		{
			this.name = name;
			return this;
		}

		public TableRefInfoBuilder setTableName(final String tableName)
		{
			this.tableName = tableName;
			return this;
		}

		public TableRefInfoBuilder setKeyColumn(final String keyColumn)
		{
			this.keyColumn = keyColumn;
			return this;
		}

		public TableRefInfoBuilder setDisplayColumn(final String displayColumn)
		{
			this.displayColumn = displayColumn;
			return this;
		}

		public TableRefInfoBuilder setValueDisplayed(final boolean valueDisplayed)
		{
			this.valueDisplayed = valueDisplayed;
			return this;
		}

		public TableRefInfoBuilder setTranslated(final boolean translated)
		{
			this.translated = translated;
			return this;
		}

		public TableRefInfoBuilder setWhereClause(final String whereClause)
		{
			this.whereClause = whereClause;
			return this;
		}

		public TableRefInfoBuilder setOrderByClause(final String orderByClause)
		{
			this.orderByClause = orderByClause;
			return this;
		}

		public TableRefInfoBuilder setZoomWindow(final int zoomWindow)
		{
			this.zoomWindow = zoomWindow;
			return this;
		}

		public TableRefInfoBuilder setZoomWindowPO(final int zoomWindowPO)
		{
			this.zoomWindowPO = zoomWindowPO;
			return this;
		}

		public TableRefInfoBuilder setDisplayColumnSQL(final String displayColumnSQL)
		{
			this.displayColumnSQL = displayColumnSQL;
			return this;
		}

		public TableRefInfoBuilder setOverrideZoomWindow(final int overrideZoomWindow)
		{
			this.overrideZoomWindow = overrideZoomWindow;
			return this;
		}

		public TableRefInfoBuilder setAutoComplete(final boolean autoComplete)
		{
			this.autoComplete = autoComplete;
			return this;
		}
	}

	/* package */class LookupDisplayInfo implements ILookupDisplayInfo
	{
		private final List<ILookupDisplayColumn> lookupDisplayColumns;
		private final int zoomWindow;
		private final int zoomWindowPO;
		private final boolean translated;

		public LookupDisplayInfo(final List<ILookupDisplayColumn> lookupDisplayColumns,
				final int zoomWindow, final int zoomWindowPO,
				final boolean translated)
		{
			super();

			Check.assumeNotEmpty(lookupDisplayColumns, "lookupDisplayColumns not empty");
			this.lookupDisplayColumns = ImmutableList.copyOf(lookupDisplayColumns);

			this.zoomWindow = zoomWindow;
			this.zoomWindowPO = zoomWindowPO;
			this.translated = translated;
		}

		@Override
		public List<ILookupDisplayColumn> getLookupDisplayColumns()
		{
			return lookupDisplayColumns;
		}

		@Override
		public int getZoomWindow()
		{
			return zoomWindow;
		}

		@Override
		public int getZoomWindowPO()
		{
			return zoomWindowPO;
		}

		@Override
		public boolean isTranslated()
		{
			return translated;
		}
	}

	@Override
	@Cached
	public IColumnInfo retrieveColumnInfo(final int adColumnId)
	{
		if (adColumnId <= 0)
		{
			return null;
		}

		final String sql = "SELECT c.ColumnName, c.AD_Reference_Value_ID, c.IsParent, c.AD_Val_Rule_ID "
				+ " FROM AD_Column c"
				+ " WHERE c.AD_Column_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, adColumnId);
			//
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final String ColumnName = rs.getString(1);
				final int AD_Reference_Value_ID = rs.getInt(2);
				final boolean IsParent = "Y".equals(rs.getString(3));
				final int AD_Val_Rule_ID = rs.getInt(4);

				final IColumnInfo columnInfo = new ColumnInfo(ColumnName, AD_Reference_Value_ID, IsParent, AD_Val_Rule_ID);
				return columnInfo;
			}
			else
			{
				logger.error("Column Not Found - AD_Column_ID=" + adColumnId);
			}
		}
		catch (final SQLException ex)
		{
			logger.error("create", ex);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return null;
	}

	@Override
	public ITableRefInfo retrieveTableRefInfo(final int AD_Reference_ID)
	{
		final ITableRefInfo tableRefInfo = retrieveTableRefInfoOrNull(AD_Reference_ID);
		if (tableRefInfo == null)
		{
			logger.error("no table ref={}", AD_Reference_ID);
			return null;
		}

		return tableRefInfo;
	}

	@Override
	public boolean isTableReference(final int AD_Reference_Value_ID)
	{
		if (AD_Reference_Value_ID <= 0)
		{
			return false;
		}
		return retrieveTableRefInfoOrNull(AD_Reference_Value_ID) != null;
	}

	@Cached(cacheName = I_AD_Ref_Table.Table_Name + "#by#" + I_AD_Ref_Table.COLUMNNAME_AD_Reference_ID)
	@Override
	public ITableRefInfo retrieveTableRefInfoOrNull(final int AD_Reference_ID)
	{
		if (AD_Reference_ID <= 0)
		{
			logger.warn("retrieveTableRefInfoOrNull: Invalid AD_Reference_ID={}. Returning null", AD_Reference_ID);
			return null;
		}
		final Object[] sqlParams = new Object[] { AD_Reference_ID };
		final String sql = "SELECT t.TableName,ck.ColumnName AS KeyColumn,"				// 1..2
				+ "cd.ColumnName AS DisplayColumn,rt.IsValueDisplayed,cd.IsTranslated,"	// 3..5
				+ "rt.WhereClause,rt.OrderByClause,t.AD_Window_ID,t.PO_Window_ID, "		// 6..9
				+ "t.AD_Table_ID, cd.ColumnSQL as DisplayColumnSQL, "					// 10..11
				+ "rt.AD_Window_ID as RT_AD_Window_ID, " // 12
				+ "t." + I_AD_Table.COLUMNNAME_IsAutocomplete // 13
				+ ", r.Name as ReferenceName" // 14
				+ " FROM AD_Ref_Table rt"
				+ " INNER JOIN AD_Reference r on (r.AD_Reference_ID=rt.AD_Reference_ID)"
				+ " INNER JOIN AD_Table t ON (rt.AD_Table_ID=t.AD_Table_ID)"
				+ " INNER JOIN AD_Column ck ON (rt.AD_Key=ck.AD_Column_ID)"
				+ " LEFT OUTER JOIN AD_Column cd ON (rt.AD_Display=cd.AD_Column_ID) "
				+ " WHERE rt.AD_Reference_ID=?"
				+ " AND rt.IsActive='Y' AND t.IsActive='Y'";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			ITableRefInfo tableRefInfo = null;
			if (rs.next())
			{
				final String TableName = rs.getString(1);
				final String KeyColumn = rs.getString(2);
				final String DisplayColumn = rs.getString(3);
				final boolean isValueDisplayed = "Y".equals(rs.getString(4));
				final boolean IsTranslated = "Y".equals(rs.getString(5));
				final String WhereClause = rs.getString(6);
				final String OrderByClause = rs.getString(7);
				final int ZoomWindow = rs.getInt(8);
				final int ZoomWindowPO = rs.getInt(9);
				// AD_Table_ID = rs.getInt(10);
				final String displayColumnSQL = rs.getString(11);
				final int overrideZoomWindow = rs.getInt(12);
				final boolean autoComplete = "Y".equals(rs.getString(13));
				final String referenceName = rs.getString(14);

				tableRefInfo = TableRefInfo.builder()
						.setName(referenceName)
						.setTableName(TableName)
						.setKeyColumn(KeyColumn)
						.setDisplayColumn(DisplayColumn)
						.setValueDisplayed(isValueDisplayed)
						.setDisplayColumnSQL(displayColumnSQL)
						.setTranslated(IsTranslated)
						.setWhereClause(WhereClause)
						.setOrderByClause(OrderByClause)
						.setZoomWindow(ZoomWindow)
						.setZoomWindowPO(ZoomWindowPO)
						.setOverrideZoomWindow(overrideZoomWindow)
						.setAutoComplete(autoComplete)
						.build();
			}

			Check.assume(!rs.next(), "Only one row in result set was expected for: {} (AD_Reference_Value_ID={})", sql, AD_Reference_ID);

			return tableRefInfo;
		}
		catch (final SQLException e)
		{
			final DBException dbEx = new DBException(e, sql, sqlParams);
			logger.error("Failed retrieving TableRefInfo for AD_Reference_ID={}", AD_Reference_ID, dbEx);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	@Override
	@Cached
	public ITableRefInfo retrieveTableDirectRefInfo(final String columnName)
	{
		Check.assumeNotEmpty(columnName, "ColumnName not empty");

		if (!columnName.endsWith("_ID"))
		{
			throw new LookupException("Key does not end with '_ID': " + columnName);
		}

		String keyColumn = MQuery.getZoomColumnName(columnName);
		String tableName = MQuery.getZoomTableName(columnName);

		// Case: ColumnName is something like "hu_pip.M_HU_PI_Item_Product_ID". We need to get rid of table alias prefix
		if (Services.get(IADTableDAO.class).retrieveTableId(tableName) <= 0 // table name does not exist
				&& keyColumn.indexOf(".") > 0)
		{
			keyColumn = keyColumn.substring(keyColumn.indexOf(".") + 1);
			tableName = MQuery.getZoomTableName(keyColumn);
		}

		boolean autoComplete = false;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String sql = "SELECT "
				+ " " + I_AD_Table.COLUMNNAME_IsAutocomplete
				+ " FROM " + I_AD_Table.Table_Name + " t"
				+ " WHERE t." + I_AD_Table.COLUMNNAME_TableName + "=?";
		final List<Object> sqlParams = Arrays.<Object> asList(tableName);
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				autoComplete = DisplayType.toBoolean(rs.getString(I_AD_Table.COLUMNNAME_IsAutocomplete));
			}
			Check.assume(!rs.next(), "Only one row in result set was expected for: {} (TableName={})", sql, tableName);
		}
		catch (final SQLException e)
		{
			final DBException ex = new DBException(e, sql.toString(), sqlParams);
			logger.error(ex.getLocalizedMessage(), ex);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		final ITableRefInfo tableRefInfo = TableRefInfo.builder()
				.setName("Direct_" + tableName)
				.setTableName(tableName)
				.setKeyColumn(keyColumn)
				.setAutoComplete(autoComplete)
				.build();
		return tableRefInfo;
	}

	@Override
	public ITableRefInfo retrieveAccountTableRefInfo()
	{
		return tableRefInfo_Account;
	}

	@Override
	public ILookupDisplayInfo retrieveLookupDisplayInfo(final ITableRefInfo tableRefInfo)
	{
		Check.assumeNotNull(tableRefInfo, "tableRefInfo not null");

		final List<ILookupDisplayColumn> lookupDisplayColumns = new ArrayList<ILookupDisplayColumn>();
		boolean isTranslated = false;
		int ZoomWindow = 0;
		int ZoomWindowPO = 0;

		//
		// Column filter
		final StringBuilder sqlWhereClauseColumn = new StringBuilder();
		final List<Object> sqlWhereClauseColumnParams = new ArrayList<Object>();
		final StringBuilder sqlOrderBy = new StringBuilder();
		final List<Object> sqlOrderByParams = new ArrayList<Object>();
		//
		if (tableRefInfo.isValueDisplayed())
		{
			if (sqlWhereClauseColumn.length() > 0)
			{
				sqlWhereClauseColumn.append(" OR ");
			}
			sqlWhereClauseColumn.append("c.").append(I_AD_Column.COLUMNNAME_ColumnName).append("=?");
			sqlWhereClauseColumnParams.add(COLUMNNAME_Value);

			if (sqlOrderBy.length() > 0)
			{
				sqlOrderBy.append(", ");
			}
			sqlOrderBy.append("(CASE WHEN c.").append(I_AD_Column.COLUMNNAME_ColumnName).append("=? THEN 0 ELSE 1 END)");
			sqlOrderByParams.add(COLUMNNAME_Value);
		}
		//
		if (Check.isEmpty(tableRefInfo.getDisplayColumn(), true))
		{
			// Fetch IsIdentifier fields
			if (sqlWhereClauseColumn.length() > 0)
			{
				sqlWhereClauseColumn.append(" OR ");
			}
			sqlWhereClauseColumn.append("c.").append(I_AD_Column.COLUMNNAME_IsIdentifier).append("=?");
			sqlWhereClauseColumnParams.add(true);

			if (sqlOrderBy.length() > 0)
			{
				sqlOrderBy.append(", ");
			}
			sqlOrderBy.append("c.").append(I_AD_Column.COLUMNNAME_SeqNo);
		}
		else
		{
			if (sqlWhereClauseColumn.length() > 0)
			{
				sqlWhereClauseColumn.append(" OR ");
			}
			sqlWhereClauseColumn.append("c.").append(I_AD_Column.COLUMNNAME_ColumnName).append("=?");
			sqlWhereClauseColumnParams.add(tableRefInfo.getDisplayColumn());
		}

		final List<Object> sqlParams = new ArrayList<Object>();
		final StringBuilder sql = new StringBuilder("SELECT "
				+ " c." + I_AD_Column.COLUMNNAME_ColumnName
				+ ",c." + I_AD_Column.COLUMNNAME_IsTranslated
				+ ",c." + I_AD_Column.COLUMNNAME_AD_Reference_ID
				+ ",c." + I_AD_Column.COLUMNNAME_AD_Reference_Value_ID
				+ ",t." + I_AD_Table.COLUMNNAME_AD_Window_ID
				+ ",t." + I_AD_Table.COLUMNNAME_PO_Window_ID
				+ ",c." + I_AD_Column.COLUMNNAME_ColumnSQL  // 7
				+ ",c." + I_AD_Column.COLUMNNAME_FormatPattern  // 8
				+ " FROM " + I_AD_Table.Table_Name + " t"
				+ " INNER JOIN " + I_AD_Column.Table_Name + " c ON (t.AD_Table_ID=c.AD_Table_ID)");

		sql.append(" WHERE ");
		sql.append("t.").append(I_AD_Table.COLUMNNAME_TableName).append("=?");
		sqlParams.add(tableRefInfo.getTableName());

		if (sqlWhereClauseColumn.length() > 0)
		{
			sql.append(" AND (").append(sqlWhereClauseColumn).append(")");
			sqlParams.addAll(sqlWhereClauseColumnParams);
		}

		if (sqlOrderBy.length() > 0)
		{
			sql.append(" ORDER BY ").append(sqlOrderBy);
			sqlParams.addAll(sqlOrderByParams);
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final LookupDisplayColumn ldc = new LookupDisplayColumn(
						rs.getString(1) // columnName
						, rs.getString(7) // ColumnSQL
						, "Y".equals(rs.getString(2)) // isTranslated
						, rs.getInt(3) // AD_Reference_ID
						, rs.getInt(4) // AD_Reference_Value_ID
						, rs.getString(8) // FormatPattern
				);
				lookupDisplayColumns.add(ldc);
				// s_log.debug("getLookup_TableDir: " + ColumnName + " - " + ldc);
				//
				if (!isTranslated && ldc.isTranslated())
				{
					isTranslated = true;
				}
				ZoomWindow = rs.getInt(5);
				ZoomWindowPO = rs.getInt(6);
			}
		}
		catch (final SQLException e)
		{
			final DBException ex = new DBException(e, sql.toString(), sqlParams);
			logger.error(ex.getLocalizedMessage(), ex);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// Make sure we have some display columns defined.
		if (lookupDisplayColumns.isEmpty())
		{
			throw new AdempiereException("There are no lookup display columns defined for " + tableRefInfo.getTableName() + " table."
					+ "\n HINT: please go to Table and columns, and set IsIdentifier=Y on columns which you want to be part of record's display string.");
		}

		final ILookupDisplayInfo lookupDisplayInfo = new LookupDisplayInfo(lookupDisplayColumns,
				ZoomWindow, ZoomWindowPO,
				isTranslated);
		return lookupDisplayInfo;
	}

	@Override
	public boolean isReferenceOrderByValue(final int adReferenceId)
	{
		final boolean isOrderByValue = "Y".equals(DB.getSQLValueString(ITrx.TRXNAME_None, "SELECT IsOrderByValue FROM AD_Reference WHERE AD_Reference_ID = ? ", adReferenceId));
		return isOrderByValue;
	}

	public static class SQLNamePairIterator extends AbstractPreparedStatementBlindIterator<NamePair>implements INamePairIterator
	{
		private final String sql;
		private final boolean numericKey;
		private final int entityTypeColumnIndex;

		private boolean lastItemActive = false;

		public SQLNamePairIterator(final String sql, final boolean numericKey, final int entityTypeColumnIndex)
		{
			super();
			this.sql = sql;
			this.numericKey = numericKey;
			this.entityTypeColumnIndex = entityTypeColumnIndex;
		}

		/** Fetch and return all data from this iterator (from current's position until the end) */
		public List<NamePair> fetchAll()
		{
			final List<NamePair> result = new LinkedList<>();
			try (final INamePairIterator data = this)
			{
				if (!data.isValid())
				{
					return result;
				}

				for (NamePair itemModel = data.next(); itemModel != null; itemModel = data.next())
				{
					result.add(itemModel);
				}
			}

			return result;
		}

		@Override
		protected PreparedStatement createPreparedStatement() throws SQLException
		{
			return DB.prepareStatement(sql, ITrx.TRXNAME_None);
		}

		@Override
		protected NamePair fetch(final ResultSet rs) throws SQLException
		{
			final boolean isActive = isActive(rs) && isDisplayedInUI(rs);
			final String name = getDisplayName(rs, isActive);

			final NamePair item;
			if (numericKey)
			{
				final int key = rs.getInt(MLookupFactory.COLUMNINDEX_Key);
				item = new KeyNamePair(key, name);
			}
			else
			{
				final String value = rs.getString(MLookupFactory.COLUMNINDEX_Value);
				item = new ValueNamePair(value, name);
			}

			lastItemActive = isActive;

			return item;
		}

		private final boolean isActive(final ResultSet rs) throws SQLException
		{
			final boolean isActive = DisplayType.toBoolean(rs.getString(MLookupFactory.COLUMNINDEX_IsActive));
			return isActive;
		}

		private final boolean isDisplayedInUI(final ResultSet rs) throws SQLException
		{
			if (entityTypeColumnIndex <= 0)
			{
				return true;
			}

			final String entityType = rs.getString(entityTypeColumnIndex);
			if (Check.isEmpty(entityType, true))
			{
				return true;
			}

			final boolean displayed = UIDisplayedEntityTypes.isEntityTypeDisplayedInUIOrTrueIfNull(entityType);
			return displayed;
		}

		private final String getDisplayName(final ResultSet rs, final boolean isActive) throws SQLException
		{
			String name = rs.getString(MLookupFactory.COLUMNINDEX_DisplayName);
			if (!isActive)
			{
				name = MLookup.INACTIVE_S + name + MLookup.INACTIVE_E;
			}
			return name;
		}

		@Override
		protected void onSQLException(final SQLException e)
		{
			throw new DBException(e, sql);
		}

		@Override
		public boolean isValid()
		{
			return !Check.isEmpty(sql, true);
		}

		@Override
		public boolean wasActive()
		{
			return lastItemActive;
		}

		@Override
		public Object getValidationKey()
		{
			// actually we consider the SQL to be the validation key
			return sql;
		}

		@Override
		public boolean isNumericKey()
		{
			return numericKey;
		}
	}

	@Override
	public INamePairIterator retrieveLookupValues(final IValidationContext validationCtx, final MLookupInfo lookupInfo)
	{
		final IValidationRule additionalValidationRule = NullValidationRule.instance;
		return retrieveLookupValues(validationCtx, lookupInfo, additionalValidationRule);
	}

	@Override
	public INamePairIterator retrieveLookupValues(final IValidationContext validationCtx, final MLookupInfo lookupInfo, final IValidationRule additionalValidationRule)
	{
		final String sql = getSQL(validationCtx, lookupInfo, additionalValidationRule);
		final boolean numericKey = lookupInfo.isNumericKey();
		final int entityTypeColumnIndex = lookupInfo.isQueryHasEntityType() ? MLookupFactory.COLUMNINDEX_EntityType : -1;

		if (logger.isTraceEnabled())
		{
			logger.trace(lookupInfo.getKeyColumnFQ() + ": " + sql);
		}

		return new SQLNamePairIterator(sql, numericKey, entityTypeColumnIndex);
	}	// run

	@Override
	public Object createValidationKey(final IValidationContext validationCtx, final MLookupInfo lookupInfo)
	{
		final IValidationRule additionalValidationRule = NullValidationRule.instance;
		return getSQL(validationCtx, lookupInfo, additionalValidationRule);
	}

	private static String getSQL(final IValidationContext validationCtx, final MLookupInfo lookupInfo, final IValidationRule additionalValidationRule)
	{
		final IValidationRule lookupInfoValidationRule;
		if (validationCtx == IValidationContext.DISABLED)
		{
			// NOTE: if validation is disabled we shall not add any where clause
			lookupInfoValidationRule = NullValidationRule.instance;
		}
		else
		{
			lookupInfoValidationRule = lookupInfo.getValidationRule();
		}

		final IValidationRule validationRule = CompositeValidationRule.compose(lookupInfoValidationRule, additionalValidationRule);

		final IStringExpression sqlWhereClauseExpr = validationRule.getPrefilterWhereClause();
		final String sqlWhereClause;
		if (sqlWhereClauseExpr.isNullExpression())
		{
			sqlWhereClause = "";
		}
		else
		{
			sqlWhereClause = sqlWhereClauseExpr.evaluate(validationCtx, OnVariableNotFound.ReturnNoResult);
			if (sqlWhereClauseExpr.isNoResult(sqlWhereClause))
			{
				return null;
			}
		}

		final String sql = injectWhereClause(lookupInfo.getSqlQuery(), sqlWhereClause);
		return sql;
	}

	private static final String injectWhereClause(String sql, final String validation)
	{
		if (Check.isEmpty(validation, true))
		{
			return sql;
		}

		sql = processNewLines(sql); // Replaces all /n outside strings with spaces
		final int posFrom = sql.lastIndexOf(" FROM ");
		final boolean hasWhere = sql.indexOf(" WHERE ", posFrom) != -1;
		//
		final int posOrder = sql.lastIndexOf(" ORDER BY ");
		if (posOrder != -1)
		{
			sql = sql.substring(0, posOrder)
					+ (hasWhere ? " AND " : " WHERE ")
					+ " ( " + validation + " ) "
					+ sql.substring(posOrder);
		}
		else
		{
			sql += (hasWhere ? " AND " : " WHERE ")
					+ " ( " + validation + " ) ";
		}

		return sql;
	}

	// metas 030229 : Parser fix : changes all \n that are not inside strings to spaces
	private static String processNewLines(final String source)
	{
		final StringBuilder sb = new StringBuilder();
		boolean isInString = false;
		for (final char c : source.toCharArray())
		{
			isInString = isInString ^ '\'' == c; // toggles flag : true if we are inside a string.
			if (!isInString && c == '\n')
			{
				sb.append(' ');
			}
			else
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	@Override
	@Cached(
	// NOTE: short term caching because we are caching mutable values
	expireMinutes = 1)
	public NamePair retrieveLookupValue(
			@CacheAllowMutable final IValidationContext validationCtx,
			@CacheAllowMutable final MLookupInfo lookupInfo,
			@CacheAllowMutable final Object key)
	{
		// Nothing to query
		final String sqlQueryDirect = lookupInfo.getSqlQueryDirect();
		if (key == null || Check.isEmpty(sqlQueryDirect, true))
		{
			return null;
		}

		final boolean isNumber = lookupInfo.isNumericKey();

		// Case: key it's for a numeric ID but it's an empty string
		if (isNumber && Check.isEmpty(key.toString(), true))
		{
			return null;
		}

		// 04617: applying the validation rule's prefilter where clause, to make sure that what we return is valid
		String validation;
		if (validationCtx == IValidationContext.DISABLED)
		{
			// NOTE: if validation is disabled we shall not add any where clause
			validation = "";
		}
		else
		{
			final IStringExpression validationExpr = lookupInfo.getValidationRule().getPrefilterWhereClause();
			validation = validationExpr.evaluate(validationCtx, OnVariableNotFound.ReturnNoResult);
			if (validationExpr.isNoResult(validation))
			{
				validation = null;
			}
		}

		final String sql;
		if (validation == null)
		{
			sql = sqlQueryDirect;
		}
		else
		{
			sql = injectWhereClause(sqlQueryDirect, validation);
		}
		// 04617 end

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NamePair directValue = null;
		try
		{
			// SELECT Key, Value, Name FROM ...
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			if (isNumber)
			{
				pstmt.setInt(1, Integer.parseInt(key.toString()));
			}
			else
			{
				pstmt.setString(1, key.toString());
			}

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (directValue != null)
				{
					logger.error(lookupInfo.getKeyColumnFQ() + ": Not unique (first returned) for " + key + " SQL=" + sql);
					break;
				}

				final String name = rs.getString(MLookupFactory.COLUMNINDEX_DisplayName);
				final NamePair item;
				if (isNumber)
				{
					final int itemId = rs.getInt(MLookupFactory.COLUMNINDEX_Key);
					item = new KeyNamePair(itemId, name);
				}
				else
				{
					final String itemValue = rs.getString(MLookupFactory.COLUMNINDEX_Value);
					item = new ValueNamePair(itemValue, name);
				}

				// 04617: apply java validation rules
				final INamePairPredicate postQueryFilter = lookupInfo.getValidationRule().getPostQueryFilter();
				if (!postQueryFilter.accept(validationCtx, item))
				{
					continue;
				}

				directValue = item;
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, Arrays.asList(key));
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return directValue;
	}	// getDirect
}

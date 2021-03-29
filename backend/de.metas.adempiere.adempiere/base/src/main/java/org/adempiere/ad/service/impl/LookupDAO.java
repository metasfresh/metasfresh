/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.ad.service.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.adempiere.util.cache.annotations.CacheAllowMutable;
import de.metas.cache.CCache;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.reflist.ReferenceId;
import de.metas.security.permissions.UIDisplayedEntityTypes;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.TableRefInfo;
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
import org.adempiere.util.proxy.Cached;
import org.compiere.model.ILookupDisplayColumn;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.LookupDisplayColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.model.MQuery;
import org.compiere.model.X_AD_Column;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.compiere.util.ValueNamePairValidationInformation;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LookupDAO implements ILookupDAO
{
	private static final Logger logger = LogManager.getLogger(LookupDAO.class);

	private final static String COLUMNNAME_Value = "Value";

	private final CCache<Integer, TableRefInfoMap> tableRefInfoMapCache = CCache.<Integer, TableRefInfoMap>builder()
			.additionalTableNameToResetFor(I_AD_Reference.Table_Name)
			.additionalTableNameToResetFor(I_AD_Ref_Table.Table_Name)
			.build();

	/* package */static class ColumnInfo implements IColumnInfo
	{
		private final String ColumnName;
		private final String TableName;
		private final int AD_Reference_Value_ID;
		private final boolean parent;
		private final int AD_Val_Rule_ID;

		public ColumnInfo(final String tableName, final String columnName, final int adReferenceValueId, final boolean isParent, final int adValRuleId)
		{
			TableName = tableName;
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

		@Override
		public String getTableName()
		{
			return TableName;
		}
	}

	/* package */static class LookupDisplayInfo implements ILookupDisplayInfo
	{

		private final List<ILookupDisplayColumn> lookupDisplayColumns;
		private final AdWindowId zoomWindow;
		private final AdWindowId zoomWindowPO;
		private final boolean translated;

		public LookupDisplayInfo(
				final List<ILookupDisplayColumn> lookupDisplayColumns,
				final AdWindowId zoomWindow,
				final AdWindowId zoomWindowPO,
				final boolean translated)
		{
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
		public AdWindowId getZoomWindow()
		{
			return zoomWindow;
		}

		@Override
		public AdWindowId getZoomWindowPO()
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

		final String sql = "SELECT c.ColumnName, "
				+ "c.AD_Reference_Value_ID, c.IsParent, c.AD_Val_Rule_ID "
				+ ", c." + X_AD_Column.COLUMNNAME_AD_Table_ID
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
				final String columnName = rs.getString(1);
				final int AD_Reference_Value_ID = rs.getInt(2);
				final boolean IsParent = "Y".equals(rs.getString(3));
				final int AD_Val_Rule_ID = rs.getInt(4);

				final int tableID = rs.getInt(5);

				final String tableName = Services.get(IADTableDAO.class).retrieveTableName(tableID);

				return new ColumnInfo(tableName, columnName, AD_Reference_Value_ID, IsParent, AD_Val_Rule_ID);
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
	public ExplainedOptional<TableRefInfo> getTableRefInfo(final int referenceRepoId)
	{
		final ReferenceId referenceId = ReferenceId.ofRepoId(referenceRepoId);
		return getTableRefInfoMap().getById(referenceId);
	}

	@Override
	public TableRefInfo retrieveTableRefInfo(final int referenceRepoId)
	{
		final ReferenceId referenceId = ReferenceId.ofRepoId(referenceRepoId);
		final ExplainedOptional<TableRefInfo> tableRefInfo = getTableRefInfoMap().getById(referenceId);
		if (tableRefInfo.isPresent())
		{
			return tableRefInfo.get();
		}
		else
		{
			// NOTE: don't use logger.error because that call ErrorManager which will call POInfo which called this method.
			System.err.println("Cannot retrieve tableRefInfo for " + referenceId + " because " + tableRefInfo.getExplanation().getDefaultValue()
					+ ". Returning null.");
			// logger.error("Cannot retrieve tableRefInfo for {}. Returning null.", referenceId);
			return null;
		}
	}

	@Override
	public boolean isTableReference(final int referenceRepoId)
	{
		final ReferenceId referenceId = ReferenceId.ofRepoIdOrNull(referenceRepoId);
		return referenceId != null && getTableRefInfoMap().hasTableReference(referenceId);
	}

	private TableRefInfoMap getTableRefInfoMap()
	{
		return tableRefInfoMapCache.getOrLoad(0, this::retrieveTableRefInfoMap);
	}

	private TableRefInfoMap retrieveTableRefInfoMap()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		// NOTE: this method is called when we are loading POInfoColumn,
		// so it's very important to not use POs here but just plain SQL!

		final String sql = "SELECT t.TableName,ck.ColumnName AS KeyColumn,"                // 1..2
				+ "cd.ColumnName AS DisplayColumn,rt.IsValueDisplayed,cd.IsTranslated,"    // 3..5
				+ "rt.WhereClause," // 6
				+ "rt.OrderByClause," // 7
				+ "t.AD_Window_ID," // 8
				+ "t.PO_Window_ID, " // 9
				+ "t.AD_Table_ID, cd.ColumnSQL as DisplayColumnSQL, "                    // 10..11
				+ "rt.AD_Window_ID as RT_AD_Window_ID, " // 12
				+ "t." + I_AD_Table.COLUMNNAME_IsAutocomplete // 13
				+ ", rt." + I_AD_Ref_Table.COLUMNNAME_ShowInactiveValues // 14
				+ ", r.Name as ReferenceName"
				+ ", t.TooltipType as TooltipType "
				+ ", rt.AD_Reference_ID "
				+ ", r.IsActive as Ref_IsActive"
				+ ", rt.IsActive as RefTable_IsActive"
				+ ", t.IsActive as Table_IsActive"
				// #2340 Also collect information about the ref table being a reference target
				+ " FROM AD_Ref_Table rt"
				+ " INNER JOIN AD_Reference r on (r.AD_Reference_ID=rt.AD_Reference_ID)"
				+ " INNER JOIN AD_Table t ON (rt.AD_Table_ID=t.AD_Table_ID)"
				+ " INNER JOIN AD_Column ck ON (rt.AD_Key=ck.AD_Column_ID)" // key-column
				+ " LEFT OUTER JOIN AD_Column cd ON (rt.AD_Display=cd.AD_Column_ID) " // display-column
				+ " ORDER BY rt.AD_Reference_ID";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();

			final ImmutableMap.Builder<ReferenceId, ExplainedOptional<TableRefInfo>> map = ImmutableMap.builder();
			while (rs.next())
			{
				final ReferenceId referenceId = ReferenceId.ofRepoId(rs.getInt("AD_Reference_ID"));
				final ExplainedOptional<TableRefInfo> tableRefInfo = retrieveTableRefInfo(rs);
				map.put(referenceId, tableRefInfo);
			}

			final TableRefInfoMap result = new TableRefInfoMap(map.build());
			logger.info("Loaded {} in {}", result, stopwatch.stop());
			return result;
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private static ExplainedOptional<TableRefInfo> retrieveTableRefInfo(final ResultSet rs) throws SQLException
	{
		final ReferenceId referenceId = ReferenceId.ofRepoId(rs.getInt("AD_Reference_ID"));
		final String TableName = rs.getString(1);

		if (!StringUtils.toBoolean(rs.getString("Ref_IsActive")))
		{
			return ExplainedOptional.emptyBecause("AD_Reference with AD_Reference_ID=" + referenceId.getRepoId() + " is not active");
		}
		if (!StringUtils.toBoolean(rs.getString("RefTable_IsActive")))
		{
			return ExplainedOptional.emptyBecause("AD_Ref_Table with AD_Reference_ID=" + referenceId.getRepoId() + " is not active");
		}
		if (!StringUtils.toBoolean(rs.getString("Table_IsActive")))
		{
			return ExplainedOptional.emptyBecause("Table " + TableName + " is not active");
		}

		final String KeyColumn = rs.getString(2);
		final String DisplayColumn = rs.getString(3);
		final boolean isValueDisplayed = StringUtils.toBoolean(rs.getString(4));
		final boolean IsTranslated = StringUtils.toBoolean(rs.getString(5));
		final String WhereClause = rs.getString(6);
		final String OrderByClause = rs.getString(7);
		final AdWindowId zoomSO_Window_ID = AdWindowId.ofRepoIdOrNull(rs.getInt(8));
		final AdWindowId zoomPO_Window_ID = AdWindowId.ofRepoIdOrNull(rs.getInt(9));
		// AD_Table_ID = rs.getInt(10);
		final String displayColumnSQL = rs.getString(11);
		final AdWindowId zoomAD_Window_ID_Override = AdWindowId.ofRepoIdOrNull(rs.getInt(12));
		final boolean autoComplete = StringUtils.toBoolean(rs.getString(13));
		final boolean showInactiveValues = StringUtils.toBoolean(rs.getString(14));
		final String referenceName = rs.getString("ReferenceName");
		final TooltipType tooltipType = TooltipType.ofCode(rs.getString("TooltipType"));

		return ExplainedOptional.of(TableRefInfo.builder()
				.identifier("AD_Reference[ID=" + referenceId.getRepoId() + ",Name=" + referenceName + "]")
				.tableName(TableName)
				.keyColumn(KeyColumn)
				.displayColumn(DisplayColumn)
				.valueDisplayed(isValueDisplayed)
				.displayColumnSQL(displayColumnSQL)
				.translated(IsTranslated)
				.whereClause(WhereClause)
				.orderByClause(OrderByClause)
				.zoomSO_Window_ID(zoomSO_Window_ID)
				.zoomPO_Window_ID(zoomPO_Window_ID)
				.zoomAD_Window_ID_Override(zoomAD_Window_ID_Override)
				.autoComplete(autoComplete)
				.showInactiveValues(showInactiveValues)
				.tooltipType(tooltipType)
				.build());
	}

	@Override
	@Cached
	public TableRefInfo retrieveTableDirectRefInfo(final String columnName)
	{
		Check.assumeNotEmpty(columnName, "ColumnName not empty");

		if (!columnName.endsWith("_ID"))
		{
			throw new LookupException("Key does not end with '_ID': " + columnName);
		}

		String keyColumn = MQuery.getZoomColumnName(columnName);
		String tableName = MQuery.getZoomTableName(columnName);

		// Case: ColumnName is something like "hu_pip.M_HU_PI_Item_Product_ID". We need to get rid of table alias prefix
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		if (adTableDAO.retrieveTableId(tableName) <= 0 // table name does not exist
				&& keyColumn.indexOf(".") > 0)
		{
			keyColumn = keyColumn.substring(keyColumn.indexOf(".") + 1);
			tableName = MQuery.getZoomTableName(keyColumn);
		}

		final boolean autoComplete;
		final I_AD_Table table = adTableDAO.retrieveTableOrNull(tableName);
		if (table == null)
		{
			autoComplete = false;
		}
		else
		{
			autoComplete = table.isAutocomplete();
		}

		final TooltipType tooltipType = Services.get(IADTableDAO.class).getTooltipTypeByTableName(tableName);

		return TableRefInfo.builder()
				.identifier("Direct[FromColumn" + columnName + ",To=" + tableName + "." + columnName + "]")
				.tableName(tableName)
				.keyColumn(keyColumn)
				.autoComplete(autoComplete)
				.tooltipType(tooltipType)
				.build();
	}

	@Override
	public TableRefInfo retrieveAccountTableRefInfo()
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		return TableRefInfo.builder()
				.identifier("Account - C_ValidCombination_ID")
				.tableName(I_C_ValidCombination.Table_Name)
				.keyColumn(I_C_ValidCombination.COLUMNNAME_C_ValidCombination_ID)
				.autoComplete(true)
				.tooltipType(adTableDAO.getTooltipTypeByTableName(I_C_ValidCombination.Table_Name))
				.build();
	}

	@Override
	public ILookupDisplayInfo retrieveLookupDisplayInfo(@NonNull final TableRefInfo tableRefInfo)
	{
		final List<ILookupDisplayColumn> lookupDisplayColumns = new ArrayList<>();
		boolean isTranslated = false;
		AdWindowId ZoomWindow = null;
		AdWindowId ZoomWindowPO = null;

		//
		// Column filter
		final StringBuilder sqlWhereClauseColumn = new StringBuilder();
		final List<Object> sqlWhereClauseColumnParams = new ArrayList<>();
		final StringBuilder sqlOrderBy = new StringBuilder();
		final List<Object> sqlOrderByParams = new ArrayList<>();
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
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			if (I_AD_Table.Table_Name.equals(tableRefInfo.getTableName()))
			{
				if (sqlWhereClauseColumn.length() > 0)
				{
					sqlWhereClauseColumn.append(" OR ");
				}
				sqlWhereClauseColumn.append("c.").append(I_AD_Column.COLUMNNAME_ColumnName).append("=?");
				sqlWhereClauseColumnParams.add(I_AD_Table.COLUMNNAME_TableName);
			}
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

		final List<Object> sqlParams = new ArrayList<>();
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
						, DisplayType.toBoolean(rs.getString(2)) // isTranslated
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
				ZoomWindow = AdWindowId.ofRepoIdOrNull(rs.getInt(5));
				ZoomWindowPO = AdWindowId.ofRepoIdOrNull(rs.getInt(6));
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

		final ILookupDisplayInfo lookupDisplayInfo = new LookupDisplayInfo(
				lookupDisplayColumns,
				ZoomWindow,
				ZoomWindowPO,
				isTranslated);
		return lookupDisplayInfo;
	}

	@Override
	public boolean isReferenceOrderByValue(final int adReferenceId)
	{
		final boolean isOrderByValue = "Y".equals(DB.getSQLValueString(ITrx.TRXNAME_None, "SELECT IsOrderByValue FROM AD_Reference WHERE AD_Reference_ID = ? ", adReferenceId));
		return isOrderByValue;
	}

	public static class SQLNamePairIterator extends AbstractPreparedStatementBlindIterator<NamePair> implements INamePairIterator
	{
		private final String sql;
		private final boolean numericKey;
		private final int entityTypeColumnIndex;

		private boolean lastItemActive = false;

		public SQLNamePairIterator(final String sql, final boolean numericKey, final int entityTypeColumnIndex)
		{
			this.sql = sql;
			this.numericKey = numericKey;
			this.entityTypeColumnIndex = entityTypeColumnIndex;
		}

		/**
		 * Fetch and return all data from this iterator (from current's position until the end)
		 */
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
			final String description = rs.getString(MLookupFactory.COLUMNINDEX_Description);

			final NamePair item;
			if (numericKey)
			{
				final int key = rs.getInt(MLookupFactory.COLUMNINDEX_Key);
				item = KeyNamePair.of(key, name, description);
			}
			else
			{
				final String value = rs.getString(MLookupFactory.COLUMNINDEX_Value);
				final ValueNamePairValidationInformation validationInformation = getValidationInformation(rs);
				item = ValueNamePair.of(value, name, description, validationInformation);
			}

			lastItemActive = isActive;

			return item;
		}

		private boolean isActive(final ResultSet rs) throws SQLException
		{
			final boolean isActive = DisplayType.toBoolean(rs.getString(MLookupFactory.COLUMNINDEX_IsActive));
			return isActive;
		}

		private boolean isDisplayedInUI(final ResultSet rs) throws SQLException
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

			return Ini.isSwingClient()
					? UIDisplayedEntityTypes.isEntityTypeDisplayedInUIOrTrueIfNull(Env.getCtx(), entityType)
					: true;
		}

		private String getDisplayName(final ResultSet rs, final boolean isActive) throws SQLException
		{
			String name = rs.getString(MLookupFactory.COLUMNINDEX_DisplayName);
			if (!isActive)
			{
				name = MLookup.INACTIVE_S + name + MLookup.INACTIVE_E;
			}
			return name;
		}

		private ValueNamePairValidationInformation getValidationInformation(final ResultSet rs) throws SQLException
		{
			final ResultSetMetaData metaData = rs.getMetaData();
			if (metaData.getColumnCount() >= MLookupFactory.COLUMNINDEX_ValidationInformation)
			{
				final AdMessageKey question = AdMessageKey.ofNullable(rs.getString(MLookupFactory.COLUMNINDEX_ValidationInformation));
				if (question != null)
				{
					return ValueNamePairValidationInformation.builder()
							.question(question)
							.build();
				}
			}

			return null;
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
	}    // run

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

	private static String injectWhereClause(String sql, final String validation)
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
		final String sqlQueryDirect = lookupInfo.getSqlQueryDirect();
		if (key == null || Check.isEmpty(sqlQueryDirect, true))
		{
			return null; // Nothing to query
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
				final String description = rs.getString(MLookupFactory.COLUMNINDEX_Description);
				final NamePair item;
				if (isNumber)
				{
					final int itemId = rs.getInt(MLookupFactory.COLUMNINDEX_Key);
					item = KeyNamePair.of(itemId, name, description);
				}
				else
				{
					final String itemValue = rs.getString(MLookupFactory.COLUMNINDEX_Value);
					item = ValueNamePair.of(itemValue, name, description);
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
			throw DBException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("sql", sql)
					.setParameter("param", key);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return directValue;
	}    // getDirect

	private static class TableRefInfoMap
	{
		private final ImmutableMap<ReferenceId, ExplainedOptional<TableRefInfo>> map;

		public TableRefInfoMap(final Map<ReferenceId, ExplainedOptional<TableRefInfo>> map)
		{
			this.map = ImmutableMap.copyOf(map);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("size", map.size())
					.toString();
		}

		public boolean hasTableReference(@NonNull final ReferenceId referenceId)
		{
			final ExplainedOptional<TableRefInfo> result = map.get(referenceId);
			return result != null && result.isPresent();
		}

		public ExplainedOptional<TableRefInfo> getById(@NonNull final ReferenceId referenceId)
		{
			final ExplainedOptional<TableRefInfo> result = map.get(referenceId);
			return result != null
					? result
					: ExplainedOptional.emptyBecause("Unknown AD_Reference_ID=" + referenceId.getRepoId());
		}
	}
}

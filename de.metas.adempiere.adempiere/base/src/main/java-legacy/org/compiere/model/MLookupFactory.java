/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.IColumnInfo;
import org.adempiere.ad.service.ILookupDAO.ILookupDisplayInfo;
import org.adempiere.ad.service.ILookupDAO.ITableRefInfo;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;

import de.metas.i18n.Language;
import de.metas.i18n.TranslatableParameterizedString;
import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Create MLookups
 *
 * @author Jorg Janke
 * @version $Id: MLookupFactory.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1734394 ] MLookupFactory.getLookup_TableDirEmbed is not translated
 *         <li>BF [ 1714261 ] MLookupFactory: TableDirEmbed -> TableEmbed not supported
 *         <li>BF [ 1672820 ] Sorting should be language-sensitive
 *         <li>BF [ 1739530 ] getLookup_TableDirEmbed error when BaseColumn is sql query
 *         <li>BF [ 1739544 ] getLookup_TableEmbed error for self referencing references
 *         <li>BF [ 1817768 ] Isolate hardcoded table direct columns
 * @author Teo Sarca
 *         <li>BF [ 2933367 ] Virtual Column Identifiers are not working
 *         https://sourceforge.net/tracker/?func=detail&aid=2933367&group_id=176962&atid=879332
 * @author Carlos Ruiz, GlobalQSS
 *         <li>BF [ 2561593 ] Multi-tenant problem with webui
 */
public class MLookupFactory
{
	public static final int COLUMNINDEX_Key = 1;
	public static final int COLUMNINDEX_Value = 2;
	public static final int COLUMNINDEX_DisplayName = 3;
	public static final int COLUMNINDEX_IsActive = 4;
	public static final int COLUMNINDEX_EntityType = 5;

	/** Logging */
	private static final Logger s_log = LogManager.getLogger(MLookupFactory.class);
	/** Table Reference Cache */
	private static CCache<ArrayKey, MLookupInfo> s_cacheRefTable = new CCache<>("AD_Ref_Table", 30, 60);	// 1h

	/**
	 * Create MLookup
	 * 
	 * @throws AdempiereException if Lookup could not be created
	 */
	public static MLookup get(final Properties ctx, final int WindowNo, final int Column_ID, final int AD_Reference_ID, final String ctxTableName, final String ctxColumnName, final int AD_Reference_Value_ID,
			final boolean IsParent, final String ValidationCode)

	{
		MLookupInfo info = getLookupInfo(WindowNo, AD_Reference_ID, ctxTableName, ctxColumnName, AD_Reference_Value_ID, IsParent, ValidationCode);
		if (info == null)
			throw new AdempiereException("MLookup.create - no LookupInfo");
		return new MLookup(ctx, Column_ID, info, 0);
	}   // create

	public static MLookup get(final Properties ctx, final int WindowNo, final int Column_ID, final int AD_Reference_ID, final String ctxTableName, final String ctxColumnName, final int AD_Reference_Value_ID,
			final boolean IsParent, final int AD_Val_Rule_ID)

			throws AdempiereException
	{
		final MLookupInfo lookupInfo = getLookupInfo(WindowNo, AD_Reference_ID, ctxTableName, ctxColumnName, AD_Reference_Value_ID, IsParent, AD_Val_Rule_ID);
		return ofLookupInfo(ctx, lookupInfo, Column_ID);
	}   // create

	public static final MLookup ofLookupInfo(final Properties ctx, final MLookupInfo lookupInfo, final int AD_Column_ID)
	{
		Check.assumeNotNull(lookupInfo, "Parameter lookupInfo is not null");
		final int tabNo = 0;
		return new MLookup(ctx, AD_Column_ID, lookupInfo, tabNo);
	}

	public static MLookupInfo getLookupInfo(final int WindowNo, final int Column_ID, final int AD_Reference_ID)
	{
		final IColumnInfo columnInfo = Services.get(ILookupDAO.class).retrieveColumnInfo(Column_ID);
		if (columnInfo == null)
		{
			return null;
		}
		//
		final MLookupInfo info = getLookupInfo(
				WindowNo,
				AD_Reference_ID,
				columnInfo.getTableName(),
				columnInfo.getColumnName(),
				columnInfo.getAD_Reference_Value_ID(),
				columnInfo.isParent(),
				columnInfo.getAD_Val_Rule_ID());

		return info;
	}

	/**
	 * Create MLookup
	 *
	 * @param ctx context for access
	 * @param WindowNo window no
	 * @param TabNo TabNo
	 * @param Column_ID AD_Column_ID or AD_Process_Para_ID
	 * @param AD_Reference_ID display type
	 * @return MLookup
	 */
	public static MLookup get(final Properties ctx, final int WindowNo, final int TabNo, final int Column_ID, final int AD_Reference_ID)
	{
		//
		MLookupInfo info = getLookupInfo(WindowNo, Column_ID, AD_Reference_ID);
		return new MLookup(ctx, Column_ID, info, TabNo);
	}   // get

	/**************************************************************************
	 * Get Information for Lookups based on Column_ID for Table Columns or Process Parameters.
	 *
	 * The SQL returns three columns:
	 * 
	 * <pre>
	 *		Key, Value, Name, IsActive	(where either key or value is null)
	 * </pre>
	 * 
	 * @param ctx context for access
	 * @param WindowNo window no
	 * @param ColumnName key column name
	 * @param AD_Reference_ID display type
	 * @param AD_Reference_Value_ID AD_Reference (List, Table)
	 * @param IsParent parent (prevents query to directly access value)
	 * @param ValidationCode optional SQL validation
	 * @return lookup info structure
	 * @Deprecated
	 */
	static public MLookupInfo getLookupInfo(final int WindowNo,
			final int AD_Reference_ID,
			final String ctxTableName,
			final String ctxColumnName,
			final int AD_Reference_Value_ID,
			final boolean IsParent, final String ValidationCode)
	{
		final int adValRuleId = -1;
		final MLookupInfo info = getLookupInfo(WindowNo, AD_Reference_ID, ctxTableName,  ctxColumnName, AD_Reference_Value_ID, IsParent, adValRuleId);
		Check.assumeNotNull(info, "lookupInfo not null for TableName={}, ColumnName={}, AD_Reference_ID={}, AD_Reference_Value_ID={}", ctxTableName, ctxColumnName, AD_Reference_ID, AD_Reference_Value_ID);
		info.setValidationRule(Services.get(IValidationRuleFactory.class).createSQLValidationRule(ValidationCode));
		info.getValidationRule(); // make sure the effective validation rule is built here (optimization)
		return info;
	}

	static public MLookupInfo getLookupInfo(
			final int WindowNo, final int AD_Reference_ID, final String ctxTableName, final String ctxColumnName,final int AD_Reference_Value_ID, final boolean IsParent, final int AD_Val_Rule_ID)
	{
		final MLookupInfo info;
		// List
		if (AD_Reference_ID == DisplayType.List)
		{
			info = getLookup_List(AD_Reference_Value_ID);
		}
		//
		// Button with attached list or table
		else if (AD_Reference_ID == DisplayType.Button && AD_Reference_Value_ID > 0)
		{
			final boolean isTableReference = Services.get(ILookupDAO.class).isTableReference(AD_Reference_Value_ID);
			if (isTableReference)
			{
				info = getLookup_Table(WindowNo, AD_Reference_Value_ID);
			}
			else
			{
				info = getLookup_List(AD_Reference_Value_ID);
			}
		}
		// Table or Search with Reference_Value
		else if ((AD_Reference_ID == DisplayType.Table || AD_Reference_ID == DisplayType.Search) && AD_Reference_Value_ID > 0)
		{
			info = getLookup_Table(WindowNo, AD_Reference_Value_ID);
		}
		// Account
		else if (AD_Reference_ID == DisplayType.Account)
		{
			final ITableRefInfo accountTableRefInfo = Services.get(ILookupDAO.class).retrieveAccountTableRefInfo();
			info = getLookupInfo(WindowNo, accountTableRefInfo);
		}
		else if (AD_Reference_ID == DisplayType.Location)
		{
			info = getLookup_TableDir(WindowNo, DisplayType.getKeyColumnName(DisplayType.Location));
		}
		// TableDir, Search, ID, ...
		else
		{
			info = getLookup_TableDir(WindowNo, ctxColumnName);
		}
		// do we have basic info?
		if (info == null)
		{
			s_log.error("No SQL - {}", ctxColumnName);
			return null;
		}

		// remaining values
		// NOTE: because some of the previous getters can retrieve a clone of a cached lookup info, here we need to set the actual values again
		info.setWindowNo(WindowNo);
		info.setDisplayType(AD_Reference_ID);
		info.setAD_Reference_Value_ID(AD_Reference_Value_ID);
		info.setIsParent(IsParent);
		info.setValidationRule(Services.get(IValidationRuleFactory.class).create(info.getTableName(), AD_Val_Rule_ID, ctxTableName, ctxColumnName));
		info.getValidationRule(); // make sure the effective validation rule is built here (optimization)

		// Direct Query - NO Validation/Security
		info.setSqlQueryDirect(
				createQueryDirect(info, true) //
				, createQueryDirect(info, false) //
		);

		// Validation
		// NOTE (metas): we are not adding the validation here because it will be added on load time (from IValidationRule)

		// Add Security
		// NOTE (metas): not needed, atm MLookupInfo builds & cache them when needed

		//
		// s_log.trace("Query: " + info.Query);
		// s_log.trace("Direct: " + info.QueryDirect);
		return info;
	}	// createLookupInfo

	/**
	 * Creates Direct access SQL Query. Similar with regular query but without validation rules, no security and no ORDER BY.
	 * 
	 * @param info
	 * @return SELECT Key, Value, DisplayName, IsActive FROM TableName WHERE KeyColumn=?
	 */
	private static final String createQueryDirect(final MLookupInfo info, final boolean useBaseLanguage)
	{
		final String keyColumnFQ = info.getKeyColumnFQ();
		final String whereClauseSqlPart = info.getWhereClauseSqlPart();
		final String selectSqlPart = useBaseLanguage ? info.getSelectSqlPart_BaseLang() : info.getSelectSqlPart_Trl();

		final StringBuilder sqlWhereClause = new StringBuilder();
		if (!Check.isEmpty(whereClauseSqlPart, true))
		{
			sqlWhereClause.append("(").append(whereClauseSqlPart).append(")");
			sqlWhereClause.append(" AND ");
		}
		sqlWhereClause.append("(").append(keyColumnFQ).append("=?").append(")");

		final StringBuilder sql = new StringBuilder(selectSqlPart);
		sql.append(" WHERE ").append(sqlWhereClause);

		return sql.toString();
	}

	/**************************************************************************
	 * Get Lookup SQL for Lists
	 * 
	 * @param AD_Reference_Value_ID reference value
	 * @return SELECT NULL, Value, Name, IsActive FROM AD_Ref_List
	 */
	static public MLookupInfo getLookup_List(final int AD_Reference_Value_ID)
	{
		String displayColumnSQL_BaseLang = "AD_Ref_List.Name";
		String displayColumnSQL_Trl = "trl.Name";
		final String sqlFrom_BaseLang = "AD_Ref_List";
		final String sqlFrom_Trl = "AD_Ref_List INNER JOIN AD_Ref_List_Trl trl ON (AD_Ref_List.AD_Ref_List_ID=trl.AD_Ref_List_ID"
				+ " AND trl.AD_Language='" + MLookupInfo.CTXNAME_AD_Language.toStringWithMarkers() + "')";

		// metas: tsa: include reference value in reference name
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			displayColumnSQL_BaseLang = "AD_Ref_List.Value||'_'||" + displayColumnSQL_BaseLang;
			displayColumnSQL_Trl = "AD_Ref_List.Value||'_'||" + displayColumnSQL_Trl;
		}

		// SQL Select
		final StringBuilder sqlSelect_BaseLang = new StringBuilder("SELECT ") // Key, Value
				.append(" NULL") // Key
				.append(", AD_Ref_List.Value") // Value
				.append(",").append(displayColumnSQL_BaseLang) // DisplayName
				.append(",AD_Ref_List.IsActive") // IsActive
				.append(",AD_Ref_List.EntityType") // EntityType
				.append(" FROM ").append(sqlFrom_BaseLang);
		final StringBuilder sqlSelect_Trl = new StringBuilder("SELECT ") // Key, Value
				.append(" NULL") // Key
				.append(", AD_Ref_List.Value") // Value
				.append(",").append(displayColumnSQL_Trl) // DisplayName
				.append(",AD_Ref_List.IsActive") // IsActive
				.append(",AD_Ref_List.EntityType") // EntityType
				.append(" FROM ").append(sqlFrom_Trl);

		// SQL WHERE
		final StringBuilder sqlWhereClause = new StringBuilder()
				.append("AD_Ref_List.AD_Reference_ID=").append(AD_Reference_Value_ID);

		// SQL ORDER BY
		final boolean isOrderByValue = Services.get(ILookupDAO.class).isReferenceOrderByValue(AD_Reference_Value_ID);
		final String sqlOrderBy;
		if (isOrderByValue)
		{
			sqlOrderBy = Integer.toString(COLUMNINDEX_Value);
		}
		else
		{
			sqlOrderBy = Integer.toString(COLUMNINDEX_DisplayName); // sort by name/translated name - teo_sarca, [ 1672820 ]
		}

		final StringBuilder realSQL_BaseLang = new StringBuilder(sqlSelect_BaseLang)
				.append(" WHERE ").append(sqlWhereClause)
				.append(" ORDER BY ").append(sqlOrderBy);
		final StringBuilder realSQL_Trl = new StringBuilder(sqlSelect_Trl)
				.append(" WHERE ").append(sqlWhereClause)
				.append(" ORDER BY ").append(sqlOrderBy);

		//
		final int AD_REFERENCE_WINDOW_ID = 101;
		final MLookupInfo lookupInfo = new MLookupInfo(
				realSQL_BaseLang.toString(),   // Query_BaseLang
				realSQL_Trl.toString(),   // Query_Trl
				I_AD_Ref_List.Table_Name, // TableName
				I_AD_Ref_List.Table_Name + "." + I_AD_Ref_List.COLUMNNAME_Value,   // KeyColumn
				AD_REFERENCE_WINDOW_ID,   // zoomSO_Window_ID
				AD_REFERENCE_WINDOW_ID,   // zoomPO_Window_ID
				-1, // zoomAD_Window_ID_Override
				MQuery.getEqualQuery("AD_Reference_ID", AD_Reference_Value_ID) // Zoom Query
		);
		lookupInfo.setDisplayColumnSQL(displayColumnSQL_BaseLang, displayColumnSQL_Trl);
		lookupInfo.setSelectSqlPart(sqlSelect_BaseLang.toString(), sqlSelect_Trl.toString());
		lookupInfo.setFromSqlPart(sqlFrom_BaseLang, sqlFrom_Trl);
		lookupInfo.setWhereClauseSqlPart(sqlWhereClause.toString());
		lookupInfo.setOrderBySqlPart(sqlOrderBy);
		lookupInfo.setSecurityDisabled(true);
		lookupInfo.setQueryHasEntityType(true);

		return lookupInfo;
	}	// getLookup_List

	/**
	 * Get Lookup SQL for List
	 * 
	 * @param languageInfo report Language
	 * @param AD_Reference_Value_ID reference value
	 * @param linkColumnName link column name
	 * @return SELECT Name FROM AD_Ref_List WHERE AD_Reference_ID=x AND Value=linkColumn
	 */
	static public String getLookup_ListEmbed(final LanguageInfo languageInfo, final int AD_Reference_Value_ID, final String linkColumnName)
	{
		final MLookupInfo lookupInfo = getLookup_List(AD_Reference_Value_ID);
		if (lookupInfo == null)
		{
			return "";
		}

		final String baseTable = null;
		final String sql = getLookupEmbed(lookupInfo, languageInfo, baseTable, linkColumnName);
		return sql;
	}	// getLookup_ListEmbed

	private static final ArrayKey createCacheKey(final ITableRefInfo tableRef)
	{
		return new ArrayKey(tableRef);
	}

	/***************************************************************************
	 * Get Lookup SQL for Table Lookup
	 *
	 * @param ctx context for access and dynamic access
	 * @param languageInfo report language
	 * @param WindowNo window no
	 * @param AD_Reference_Value_ID reference value
	 * @return SELECT Key, NULL, Name, IsActive FROM Table - if KeyColumn end with _ID
	 *         otherwise SELECT NULL, Key, Name, IsActive FROM Table
	 */
	// NOTE: never make this method public because in case the lookup is cloned from a cached version we need to set the context and other relevant fields anyway
	static private MLookupInfo getLookup_Table(final int WindowNo, final int AD_Reference_Value_ID)
	{
		final ITableRefInfo tableRefInfo = Services.get(ILookupDAO.class).retrieveTableRefInfo(AD_Reference_Value_ID);
		if (tableRefInfo == null)
		{
			return null;
		}

		return getLookupInfo(WindowNo, tableRefInfo);
	}	// getLookup_Table

	/**
	 * Get Embedded Lookup SQL for Table Lookup
	 * 
	 * @param languageInfo report language
	 * @param BaseColumn base column name
	 * @param BaseTable base table name
	 * @param AD_Reference_Value_ID reference value
	 * @return SELECT Name FROM Table
	 */
	static public String getLookup_TableEmbed(final LanguageInfo languageInfo, final String BaseColumn, final String BaseTable, final int AD_Reference_Value_ID)
	{
		final MLookupInfo lookupInfo = getLookup_Table(Env.WINDOW_None, AD_Reference_Value_ID);
		if (lookupInfo == null)
		{
			return "";
		}

		final String sql = getLookupEmbed(lookupInfo, languageInfo, BaseTable, BaseColumn);
		return sql;
	}	// getLookup_TableEmbed

	/**************************************************************************
	 * Get Lookup SQL for direct Table Lookup
	 * 
	 * @param ctx context for access
	 * @param ColumnName column name
	 * @return SELECT Key, NULL, Name, IsActive from Table (fully qualified)
	 */
	// NOTE: never make this method public because in case the lookup is cloned from a cached version we need to set the context and other relevant fields anyway
	static private MLookupInfo getLookup_TableDir(final int WindowNo, final String ColumnName)
	{
		final ITableRefInfo tableRef = Services.get(ILookupDAO.class).retrieveTableDirectRefInfo(ColumnName);
		return getLookupInfo(WindowNo, tableRef);
	}

	// NOTE: never make this method public because in case the lookup is cloned from a cached version we need to set the context and other relevant fields anyway
	private static MLookupInfo getLookupInfo(final int windowNo, final ITableRefInfo tableRefInfo)
	{
		Check.assumeNotNull(tableRefInfo, "tableRefInfo not null");

		final ArrayKey cacheKey = createCacheKey(tableRefInfo);
		final MLookupInfo lookupInfo = s_cacheRefTable.getOrLoad(cacheKey, () -> buildLookupInfo(windowNo, tableRefInfo));
		return lookupInfo == null ? null : lookupInfo.cloneIt(windowNo);
	}

	private static final MLookupInfo buildLookupInfo(final int windowNo, final ITableRefInfo tableRefInfo)
	{
		final ILookupDisplayInfo lookupDisplayInfo = Services.get(ILookupDAO.class).retrieveLookupDisplayInfo(tableRefInfo);
		if (lookupDisplayInfo == null)
		{
			return null;
		}

		final List<ILookupDisplayColumn> displayColumns = lookupDisplayInfo.getLookupDisplayColumns();

		// Do we have columns ?
		if (displayColumns.isEmpty())
		{
			s_log.error("No Identifier records found for {}", tableRefInfo);
			return null;
		}

		final String TableName = tableRefInfo.getTableName();
		final String KeyColumn = tableRefInfo.getKeyColumn();

		final String keyColumnFQ = TableName + "." + KeyColumn;

		//
		// Display Column SQL
		final List<String> displayColumnSqlList_BaseLang = new ArrayList<>(displayColumns.size());
		final List<String> displayColumnSqlList_Trl = new ArrayList<>(displayColumns.size());
		for (final ILookupDisplayColumn ldc : displayColumns)
		{
			final String ldc_displayColumnSql_BaseLang = buildDisplayColumnSql(ldc, TableName, LanguageInfo.USE_BASE_LANGAUGE);
			if (!Check.isEmpty(ldc_displayColumnSql_BaseLang, true))
			{
				displayColumnSqlList_BaseLang.add(ldc_displayColumnSql_BaseLang);
			}

			final String ldc_displayColumnSql_Trl = buildDisplayColumnSql(ldc, TableName, LanguageInfo.USE_TRANSLATION_LANGUAGE);
			if (!Check.isEmpty(ldc_displayColumnSql_Trl, true))
			{
				displayColumnSqlList_Trl.add(ldc_displayColumnSql_Trl);
			}
		}
		//
		final String displayColumnSQL_BaseLang = joinDisplayColumnSqls(displayColumnSqlList_BaseLang, keyColumnFQ);
		final String displayColumnSQL_Trl = joinDisplayColumnSqls(displayColumnSqlList_Trl, keyColumnFQ);

		//
		// FROM SQL
		final StringBuilder sqlFrom_BaseLang = new StringBuilder(TableName);
		final StringBuilder sqlFrom_Trl = new StringBuilder(TableName);
		final boolean isTranslated = lookupDisplayInfo.isTranslated();
		if (isTranslated)
		{
			sqlFrom_Trl.append(" INNER JOIN ").append(TableName).append("_TRL ON (")
					.append(TableName).append(".").append(KeyColumn).append("=").append(TableName).append("_Trl.").append(KeyColumn)
					.append(" AND ").append(TableName).append("_Trl.AD_Language='").append(MLookupInfo.CTXNAME_AD_Language.toStringWithMarkers()).append("')");
		}

		final String sqlSelectKeyColumnSQL;
		final String sqlSelectValueColumnSQL;
		if (KeyColumn.endsWith("_ID"))
		{
			sqlSelectKeyColumnSQL = keyColumnFQ;
			sqlSelectValueColumnSQL = "NULL";
		}
		else
		{
			sqlSelectKeyColumnSQL = "NULL";
			sqlSelectValueColumnSQL = keyColumnFQ;
		}

		//
		// SELECT SQL
		final StringBuilder sqlSelect_BaseLang = new StringBuilder("SELECT ")
				.append(sqlSelectKeyColumnSQL) // Key
				.append(",").append(sqlSelectValueColumnSQL) // Value
				.append(",").append(displayColumnSQL_BaseLang)
				.append(",").append(TableName).append(".IsActive")
				.append(" FROM ").append(sqlFrom_BaseLang);
		final StringBuilder sqlSelect_Trl = new StringBuilder("SELECT ")
				.append(sqlSelectKeyColumnSQL) // Key
				.append(",").append(sqlSelectValueColumnSQL) // Value
				.append(",").append(displayColumnSQL_Trl)
				.append(",").append(TableName).append(".IsActive")
				.append(" FROM ").append(sqlFrom_Trl);

		//
		// Where Clause
		//
		// SQL Where Clause
		final String sqlWhereClauseStatic;
		final String sqlWhereClauseDynamic;
		if (!Check.isEmpty(tableRefInfo.getWhereClause(), true))
		{
			final String whereClause = tableRefInfo.getWhereClause();
			if (whereClause.indexOf('@') != -1)
			{
				sqlWhereClauseStatic = null;
				sqlWhereClauseDynamic = whereClause.trim();

				if (Services.get(IDeveloperModeBL.class).isEnabled())
				{
					s_log.warn("Please make sure AD_Ref_Table.WhereClause does not contain context variables."
							+ "\n Those have very bad performances and are not optimized well."
							+ "\n Consider using dynamic validation rules for that purpose."
							+ "\n See https://github.com/metasfresh/metasfresh/issues/384 "
							+ "\n tableRefInfo=" + tableRefInfo);
				}
			}
			else
			{
				if (whereClause.indexOf('.') == -1)
				{
					s_log.error("getLookupInfo: WHERE should be fully qualified: {} \n tableRefInfo={}", whereClause, tableRefInfo);
				}
				sqlWhereClauseStatic = whereClause;
				sqlWhereClauseDynamic = null;
			}
		}
		else
		{
			sqlWhereClauseStatic = null;
			sqlWhereClauseDynamic = null;
		}

		//
		// Zoom Query
		final MQuery zoomQuery;
		if (sqlWhereClauseStatic != null)
		{
			zoomQuery = new MQuery(TableName);
			zoomQuery.addRestriction(sqlWhereClauseStatic);
		}
		else
		{
			zoomQuery = null;
		}

		//
		// Order By qualified term or by Name
		final String sqlOrderBy;
		{
			final String OrderByClause = tableRefInfo.getOrderByClause();
			if (!Check.isEmpty(OrderByClause, true))
			{
				sqlOrderBy = OrderByClause;
				if (OrderByClause.indexOf('.') == -1)
				{
					s_log.error("getLookup_Table - " + TableName + ": ORDER BY must fully qualified: " + OrderByClause);
				}
			}
			else
			{
				sqlOrderBy = Integer.toString(COLUMNINDEX_DisplayName); // DisplayName
			}
		}

		//
		// Final SQL
		final StringBuilder sqlQueryFinal_BaseLang = new StringBuilder(sqlSelect_BaseLang);
		final StringBuilder sqlQueryFinal_Trl = new StringBuilder(sqlSelect_Trl);
		if (!Check.isEmpty(sqlWhereClauseStatic, true))
		{
			sqlQueryFinal_BaseLang.append(" WHERE ").append(sqlWhereClauseStatic);
			sqlQueryFinal_Trl.append(" WHERE ").append(sqlWhereClauseStatic);
		}
		sqlQueryFinal_BaseLang.append(" ORDER BY ").append(sqlOrderBy);
		sqlQueryFinal_Trl.append(" ORDER BY ").append(sqlOrderBy);

		//
		// Zoom AD_Window_IDs
		int zoomSO_Window_ID = tableRefInfo.getZoomSO_Window_ID();
		if (lookupDisplayInfo.getZoomWindow() > 0)
		{
			zoomSO_Window_ID = lookupDisplayInfo.getZoomWindow();
		}

		int zoomPO_Window_ID = tableRefInfo.getZoomPO_Window_ID();
		if (lookupDisplayInfo.getZoomWindowPO() > 0)
		{
			zoomPO_Window_ID = lookupDisplayInfo.getZoomWindowPO();
		}

		final int zoomAD_Window_ID_Override = tableRefInfo.getZoomAD_Window_ID_Override();
		if (zoomAD_Window_ID_Override > 0)
		{
			zoomSO_Window_ID = zoomAD_Window_ID_Override;
			zoomPO_Window_ID = 0;
		}

		//
		// Create MLookupInfo
		final MLookupInfo lookupInfo = new MLookupInfo(
				sqlQueryFinal_BaseLang.toString(), sqlQueryFinal_Trl.toString() //
				, TableName, keyColumnFQ //
				, zoomSO_Window_ID, zoomPO_Window_ID, zoomAD_Window_ID_Override, zoomQuery //
		);
		lookupInfo.setWindowNo(windowNo);
		lookupInfo.setDisplayColumns(displayColumns);
		lookupInfo.setDisplayColumnSQL(displayColumnSQL_BaseLang.toString(), displayColumnSQL_Trl.toString());
		lookupInfo.setSelectSqlPart(sqlSelect_BaseLang.toString(), sqlSelect_Trl.toString());
		lookupInfo.setFromSqlPart(sqlFrom_BaseLang.toString(), sqlFrom_Trl.toString());
		lookupInfo.setWhereClauseSqlPart(sqlWhereClauseStatic);
		lookupInfo.setWhereClauseDynamicSqlPart(sqlWhereClauseDynamic);
		lookupInfo.setOrderBySqlPart(sqlOrderBy);
		lookupInfo.setSecurityDisabled(false);
		lookupInfo.setAutoComplete(tableRefInfo.isAutoComplete());
		lookupInfo.setTranslated(isTranslated);

		return lookupInfo;
	}

	private static String joinDisplayColumnSqls(final List<String> displayColumnSqlList, final String keyColumnFQ)
	{
		if (displayColumnSqlList.isEmpty())
		{
			final String result = "<" + keyColumnFQ + ">";
			s_log.warn("No display columns found. Using: {}", result);
			return result;
		}
		else if (displayColumnSqlList.size() == 1)
		{
			return displayColumnSqlList.get(0);
		}
		else
		{
			final StringBuilder sb = new StringBuilder();
			sb.append("concat_ws('_', ");
			Joiner.on(", ").skipNulls().appendTo(sb, displayColumnSqlList);
			sb.append(")");
			return sb.toString();
		}
	}

	private static String buildDisplayColumnSql(final ILookupDisplayColumn ldc, final String TableName, final LanguageInfo languageInfo)
	{
		final String columnSQL = ldc.isVirtual() ? ldc.getColumnSQL() : TableName + "." + ldc.getColumnName();

		// translated
		if (ldc.isTranslated() && languageInfo.isUseTranslantionLanguage() && !ldc.isVirtual())
		{
			return TableName + "_Trl." + ldc.getColumnName();
		}
		// date
		else if (DisplayType.isDate(ldc.getDisplayType()))
		{
			if (ldc.getDisplayType() == DisplayType.Date)
			{
				// #1046
				// Make sure the date doesn't have time too
				final StringBuilder stringForDate = new StringBuilder()
						.append(columnSQL)
						.append("::")
						.append("date");
				return DB.TO_CHAR(stringForDate.toString(), ldc.getDisplayType());
			}

			// if the display type is not Date, let it work as before
			return DB.TO_CHAR(columnSQL, ldc.getDisplayType());
		}
		// TableDir
		else if ((ldc.getDisplayType() == DisplayType.TableDir
				|| (ldc.getDisplayType() == DisplayType.Search) && ldc.getAD_Reference_ID() <= 0)
				&& ldc.getColumnName().endsWith("_ID"))
		{
			final String embeddedSQL;
			if (ldc.isVirtual())
				embeddedSQL = getLookup_TableDirEmbed(languageInfo, ldc.getColumnName(), TableName, ldc.getColumnSQL());
			else
				embeddedSQL = getLookup_TableDirEmbed(languageInfo, ldc.getColumnName(), TableName);

			if (embeddedSQL != null)
			{
				return "(" + embeddedSQL + ")";
			}
			else
			{
				return null;
			}
		}
		// Table
		else if (ldc.getDisplayType() == DisplayType.Table
				|| (ldc.getDisplayType() == DisplayType.Search && ldc.getAD_Reference_ID() > 0))
		{
			final String embeddedSQL;
			if (ldc.isVirtual())
				embeddedSQL = getLookup_TableEmbed(languageInfo, ldc.getColumnSQL(), TableName, ldc.getAD_Reference_ID());
			else
				embeddedSQL = getLookup_TableEmbed(languageInfo, ldc.getColumnName(), TableName, ldc.getAD_Reference_ID());

			if (embeddedSQL != null)
			{
				return "(" + embeddedSQL + ")";
			}
			else
			{
				return null;
			}
		}
		// List
		else if (DisplayType.List == ldc.getDisplayType())
		{
			final String embeddedSQL = getLookup_ListEmbed(languageInfo, ldc.getAD_Reference_ID(), columnSQL);
			if (embeddedSQL != null)
			{
				return "(" + embeddedSQL + ")";
			}
			else
			{
				return null;
			}
		}
		// number
		else if (DisplayType.isNumeric(ldc.getDisplayType()))
		{
			final String adLanguage = null; // N/A
			return DB.TO_CHAR(columnSQL, ldc.getDisplayType(), adLanguage, ldc.getFormatPattern());
		}
		// ID
		else if (DisplayType.isID(ldc.getDisplayType()))
		{
			return DB.TO_CHAR(columnSQL, ldc.getDisplayType());
		}
		// Button
		else if (DisplayType.Button == ldc.getDisplayType() && ldc.getColumnName().endsWith("_ID"))
		{
			// metas: handle Record_ID buttons same as regular IDs
			return DB.TO_CHAR(columnSQL, ldc.getDisplayType());
		}
		// String
		else
		{
			return "NULLIF (TRIM(" + columnSQL + "), '')";
		}
	}

	/**
	 * Get embedded SQL for TableDir Lookup
	 *
	 * @param languageInfo report language
	 * @param ColumnName column name
	 * @param BaseTable base table
	 * @return SELECT Column FROM TableName WHERE BaseTable.ColumnName=TableName.ColumnName
	 * @see #getLookup_TableDirEmbed(LanguageInfo, String, String, String)
	 */
	static public String getLookup_TableDirEmbed(final LanguageInfo languageInfo, final String ColumnName, final String BaseTable)
	{
		return getLookup_TableDirEmbed(languageInfo, ColumnName, BaseTable, ColumnName);
	}   // getLookup_TableDirEmbed

	/**
	 * Get embedded SQL for TableDir Lookup
	 *
	 * @param languageInfo report language
	 * @param ColumnName column name
	 * @param BaseTable base table
	 * @param BaseColumn base column
	 * @return SELECT Column FROM TableName WHERE BaseTable.BaseColumn=TableName.ColumnName
	 */
	static public String getLookup_TableDirEmbed(final LanguageInfo languageInfo, final String ColumnName, final String BaseTable, final String BaseColumn)
	{
		final int windowNo = Env.WINDOW_None; // NOTE: for TableDir WindowNo, is not important
		final MLookupInfo lookupInfo = getLookup_TableDir(windowNo, ColumnName);
		if (lookupInfo == null)
		{
			return "";
		}

		final String sql = getLookupEmbed(lookupInfo, languageInfo, BaseTable, BaseColumn);
		return sql;
	}	// getLookup_TableDirEmbed

	// metas: begin
	/**
	 * Unified lookup embedded sql method. Based on AD_Reference_ID and AD_Reference_Value_ID parameters
	 * it will call corresponding getLookup_*Embed methods
	 */
	public static String getLookupEmbed(final LanguageInfo languageInfo, final String BaseColumn, final String BaseTable, final int AD_Reference_ID, final int AD_Reference_Value_ID)
	{
		if (DisplayType.List == AD_Reference_ID)
		{
			String linkColumnName = BaseColumn;
			return getLookup_ListEmbed(languageInfo, AD_Reference_Value_ID, linkColumnName);
		}
		else if (DisplayType.TableDir == AD_Reference_ID
				|| (DisplayType.Search == AD_Reference_ID && AD_Reference_Value_ID <= 0))
		{
			return getLookup_TableDirEmbed(languageInfo, BaseColumn, BaseTable, BaseColumn);
		}
		else if (DisplayType.Table == AD_Reference_ID
				|| (DisplayType.Search == AD_Reference_ID && AD_Reference_Value_ID > 0))
		{
			return getLookup_TableEmbed(languageInfo, BaseColumn, BaseTable, AD_Reference_Value_ID);
		}
		else
		{
			throw new IllegalArgumentException("DisplayType " + AD_Reference_ID + " not supported");
		}
	}

	// NOTE: this is an package level method because we want to JUnit test it
	/* package */ static String getLookupEmbed(final MLookupInfo lookupInfo, final LanguageInfo languageInfo, final String baseTable, final String baseColumn)
	{
		Check.assumeNotNull(baseColumn, "baseColumn not null");

		if (lookupInfo == null)
		{
			return "";
		}

		//
		// Extract needed info from MLookupInfo
		final String tableName = lookupInfo.getTableName();
		final String displayColumnSQL = lookupInfo.getDisplayColumnSQL(languageInfo);
		final String keyColumnFQ = lookupInfo.getKeyColumnFQ();
		final String fromSqlPart = lookupInfo.getFromSqlPart(languageInfo);
		final MQuery zoomQuery = lookupInfo.getZoomQuery();

		// Get BaseTable
		final boolean isVirtualLinkColumnName = baseColumn.trim().startsWith("(");
		final String linkTableName;
		// final String linkColumnName;
		final String linkColumnNameFQ;
		if (Check.isEmpty(baseTable, true))
		{
			final int idx = baseColumn.lastIndexOf(".");
			if (isVirtualLinkColumnName)
			{
				linkTableName = null; // not available
				// linkColumnName = BaseColumn0;
				linkColumnNameFQ = baseColumn;
			}
			else if (idx > 0)
			{
				linkTableName = baseColumn.substring(0, idx);
				// linkColumnName = BaseColumn0.substring(idx + 1);
				linkColumnNameFQ = baseColumn;
			}
			else
			{
				linkTableName = null;
				// linkColumnName = BaseColumn0;
				linkColumnNameFQ = baseColumn;
			}
		}
		else
		{
			linkTableName = baseTable;
			// linkColumnName = BaseColumn0;
			linkColumnNameFQ = baseTable + "." + baseColumn;
		}

		// NOTE: we are not adding lookup's where clause

		final StringBuilder sql = new StringBuilder();

		// If it's self referencing then use other alias - teo_sarca [ 1739544 ]
		if (tableName.equalsIgnoreCase(linkTableName))
		{
			final String tableNameAlias = "a" + System.currentTimeMillis();

			sql.append("select ").append(tableNameAlias).append(".V")
					.append(" from (")
					.append(" select ")
					.append(" ").append(displayColumnSQL).append(" AS V")
					.append(",").append(keyColumnFQ).append(" AS K")
					.append(" from ").append(fromSqlPart)
					.append(")").append(" ").append(tableNameAlias)
					.append(" where ")
					.append(tableNameAlias).append(".K=").append(linkColumnNameFQ);
		}
		else
		{
			sql.append(" select ")
					.append(" ").append(displayColumnSQL)
					.append(" from ").append(fromSqlPart)
					.append(" where ")
					.append(keyColumnFQ).append("=").append(linkColumnNameFQ);
		}

		// task 05076
		if (zoomQuery != null && !Check.isEmpty(zoomQuery.getWhereClause(), true))
		{
			sql.append(" AND ")
					.append(zoomQuery.getWhereClause());
		}
		// task 05076 end

		return sql.toString();
	}

	public static final class LanguageInfo
	{
		public static final LanguageInfo ofSpecificLanguage(@NonNull final Language language)
		{
			return new LanguageInfo(language.getAD_Language());
		}
		
		public static final LanguageInfo ofSpecificLanguage(final Properties ctx)
		{
			final String adLanguage = Env.getAD_Language(ctx);
			return new LanguageInfo(adLanguage);
		}
		
		public static final LanguageInfo ofSpecificLanguage(final String adLanguage)
		{
			return new LanguageInfo(adLanguage);
		}


		public static final LanguageInfo USE_BASE_LANGAUGE = new LanguageInfo(true);
		public static final LanguageInfo USE_TRANSLATION_LANGUAGE = new LanguageInfo(false);

		private final Boolean useBaseLanguage;
		private final String adLanguage;

		private LanguageInfo(final boolean useBaseLanguage)
		{
			super();
			this.useBaseLanguage = useBaseLanguage;
			this.adLanguage = null;
		}

		private LanguageInfo(@NonNull final String adLanguage)
		{
			this.useBaseLanguage = null; // N/A
			this.adLanguage = adLanguage;
		}


		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("useBaseLanguage", useBaseLanguage)
					.add("AD_Language", adLanguage)
					.toString();
		}

		public boolean isUseBaseLanguage()
		{
			return adLanguage == null && Boolean.TRUE.equals(useBaseLanguage);
		}

		public boolean isUseTranslantionLanguage()
		{
			return adLanguage == null && Boolean.FALSE.equals(useBaseLanguage);
		}

		public boolean isUseSpecificLanguage()
		{
			return adLanguage != null;
		}

		public String getAD_Language()
		{
			Check.assumeNotNull(adLanguage, "Parameter adLanguage is not null");
			return adLanguage;
		}

		public String extractString(final TranslatableParameterizedString trlString)
		{
			if (trlString == null)
			{
				return null;
			}
			else if (isUseBaseLanguage())
			{
				return trlString.getStringBaseLanguage();
			}
			else if (isUseTranslantionLanguage())
			{
				return trlString.getStringTrlPattern();
			}
			else if (isUseSpecificLanguage())
			{
				return trlString.translate(getAD_Language());
			}
			else
			{
				// internal error: shall never happen
				throw new IllegalStateException("Cannot extract string from " + trlString + " using " + this);
			}
		}
	}
}   // MLookupFactory

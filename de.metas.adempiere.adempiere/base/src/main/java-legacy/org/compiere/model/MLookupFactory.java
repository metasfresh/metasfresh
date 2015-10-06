/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.ad.security.IUserRolePermissions;
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
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.Util.ArrayKey;

/**
 *  Create MLookups
 *
 *  @author Jorg Janke
 *  @version  $Id: MLookupFactory.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *		<li>BF [ 1734394 ] MLookupFactory.getLookup_TableDirEmbed is not translated
 *		<li>BF [ 1714261 ] MLookupFactory: TableDirEmbed -> TableEmbed not supported
 *		<li>BF [ 1672820 ] Sorting should be language-sensitive
 *		<li>BF [ 1739530 ] getLookup_TableDirEmbed error when BaseColumn is sql query
 *		<li>BF [ 1739544 ] getLookup_TableEmbed error for self referencing references
 *		<li>BF [ 1817768 ] Isolate hardcoded table direct columns
 * @author Teo Sarca
 * 		<li>BF [ 2933367 ] Virtual Column Identifiers are not working
 * 			https://sourceforge.net/tracker/?func=detail&aid=2933367&group_id=176962&atid=879332
 * @author Carlos Ruiz, GlobalQSS
 *		<li>BF [ 2561593 ] Multi-tenant problem with webui
 */
public class MLookupFactory
{
	public static final int COLUMNINDEX_Key = 1;
	public static final int COLUMNINDEX_Value = 2;
	public static final int COLUMNINDEX_DisplayName = 3;
	public static final int COLUMNINDEX_IsActive = 4;
	
	/**	Logging								*/
	private static CLogger		s_log = CLogger.getCLogger(MLookupFactory.class);
	/** Table Reference Cache				*/
	private static CCache<ArrayKey,MLookupInfo> s_cacheRefTable = new CCache<ArrayKey,MLookupInfo>("AD_Ref_Table", 30, 60);	//	1h


	/**
	 *  Create MLookup
	 *
	 *  @param ctx context for access
	 *  @param WindowNo window no
	 * 	@param AD_Reference_ID display type
	 *  @param Column_ID AD_Column_ID or AD_Process_Para_ID
	 *  @param language report language
	 * 	@param ColumnName key column name
	 * 	@param AD_Reference_Value_ID AD_Reference (List, Table)
	 * 	@param IsParent parent (prevents query to directly access value)
	 * 	@param ValidationCode optional SQL validation
	 *  @throws AdempiereException if Lookup could not be created
	 *  @return MLookup
	 */
	public static MLookup get (Properties ctx, int WindowNo, int Column_ID, int AD_Reference_ID,
			Language language, String ColumnName, int AD_Reference_Value_ID,
			boolean IsParent, String ValidationCode)
		
	{
		MLookupInfo info = getLookupInfo (ctx, WindowNo, Column_ID, AD_Reference_ID,
			language, ColumnName, AD_Reference_Value_ID, IsParent, ValidationCode);
		if (info == null)
			throw new AdempiereException("MLookup.create - no LookupInfo");
		return new MLookup(info, 0);
	}   //  create
	
	
	public static MLookup get (Properties ctx, int WindowNo, int Column_ID, int AD_Reference_ID,
			Language language, String ColumnName, int AD_Reference_Value_ID,
			boolean IsParent, int AD_Val_Rule_ID)
		throws AdempiereException
	{
		MLookupInfo info = getLookupInfo (ctx, WindowNo, Column_ID, AD_Reference_ID,
			language, ColumnName, AD_Reference_Value_ID, IsParent, AD_Val_Rule_ID);
		if (info == null)
			throw new AdempiereException ("MLookup.create - no LookupInfo");
		return new MLookup(info, 0);
	}   //  create

	public static MLookupInfo getLookupInfo(final Properties ctx, final int WindowNo, final int Column_ID, final int AD_Reference_ID)
	{
		final IColumnInfo columnInfo = Services.get(ILookupDAO.class).retrieveColumnInfo(Column_ID);
		if (columnInfo == null)
		{
			return null;
		}
		//
		final MLookupInfo info = getLookupInfo (ctx,
				WindowNo,
				Column_ID,
				AD_Reference_ID,
				Env.getLanguage(ctx),
				columnInfo.getColumnName(),
				columnInfo.getAD_Reference_Value_ID(),
				columnInfo.isParent(),
				columnInfo.getAD_Val_Rule_ID()
		);

		return info;
	}

	/**
	 *  Create MLookup
	 *
	 *  @param ctx context for access
	 *  @param WindowNo window no
	 * 	@param TabNo TabNo
	 *  @param Column_ID AD_Column_ID or AD_Process_Para_ID
	 * 	@param AD_Reference_ID display type
	 *  @return MLookup
	 */
	public static MLookup get (Properties ctx, int WindowNo, int TabNo, int Column_ID, int AD_Reference_ID)
	{
		//
		MLookupInfo info = getLookupInfo (ctx, WindowNo, Column_ID, AD_Reference_ID);
		return new MLookup(info, TabNo);
	}   //  get


	/**************************************************************************
	 *  Get Information for Lookups based on Column_ID for Table Columns or Process Parameters.
	 *
	 *	The SQL returns three columns:
	 *  <pre>
	 *		Key, Value, Name, IsActive	(where either key or value is null)
	 *  </pre>
	 *  @param ctx context for access
	 *  @param language report language
	 *  @param WindowNo window no
	 *  @param Column_ID AD_Column_ID or AD_Process_Para_ID
	 * 	@param ColumnName key column name
	 * 	@param AD_Reference_ID display type
	 * 	@param AD_Reference_Value_ID AD_Reference (List, Table)
	 * 	@param IsParent parent (prevents query to directly access value)
	 * 	@param ValidationCode optional SQL validation
	 *  @return lookup info structure
	 *  @Deprecated
	 */
	static public MLookupInfo getLookupInfo (Properties ctx, int WindowNo,
		int Column_ID, int AD_Reference_ID,
		Language language, String ColumnName, int AD_Reference_Value_ID,
		boolean IsParent, String ValidationCode)
	{
		final MLookupInfo info = getLookupInfo(ctx, WindowNo, Column_ID, AD_Reference_ID, language, ColumnName, AD_Reference_Value_ID, IsParent, -1);
		info.setValidationRule(Services.get(IValidationRuleFactory.class).createSQLValidationRule(ValidationCode));
		return info;
	}
	
	static public MLookupInfo getLookupInfo (Properties ctx, int WindowNo,
			int Column_ID, int AD_Reference_ID,
			Language language, String ColumnName, int AD_Reference_Value_ID,
			boolean IsParent, int AD_Val_Rule_ID)
	{
		final MLookupInfo info;
		//	List
		if (AD_Reference_ID == DisplayType.List)	//	17
		{
			info = getLookup_List(language, AD_Reference_Value_ID);
		}
		//	Table or Search with Reference_Value
		else if ((AD_Reference_ID == DisplayType.Table || AD_Reference_ID == DisplayType.Search) && AD_Reference_Value_ID != 0)
		{
			info = getLookup_Table (ctx, language, WindowNo, AD_Reference_Value_ID);
		}
		//	TableDir, Search, ID, ...
		else
		{
			info = getLookup_TableDir (ctx, language, WindowNo, ColumnName);
		}
		//  do we have basic info?
		if (info == null)
		{
			s_log.severe ("No SQL - " + ColumnName);
			return null;
		}
		
		//	remaining values
		// NOTE: because some of the previous getters can retrieve a clone of a cached lookup info, here we need to set the actual values again
		info.setCtx(ctx);
		info.WindowNo = WindowNo;
		info.Column_ID = Column_ID;
		info.setDisplayType(AD_Reference_ID);
		info.AD_Reference_Value_ID = AD_Reference_Value_ID;
		info.IsParent = IsParent;
		info.setValidationRule(Services.get(IValidationRuleFactory.class).create(ctx, info.TableName, AD_Val_Rule_ID));

		//	Variables in SQL WHERE
		// NOTE(metas): there is no point to parse the where clause (even if just partially, for global variables) because it will be parsed anyway on valiadation time 
//		if (info.Query.indexOf('@') != -1)
//		{
//			final String newSQL = Env.parseContext(ctx,
//					0, // WindowNo
//					info.Query,
//					false,	// onlyWindow=false only global
//					true // ignoreUnparsable=true
//					);
//			if (newSQL.length() == 0)
//			{
//				s_log.severe ("SQL parse error: " + info.Query);
//				return null;
//			}
//			info.Query = newSQL;
//			s_log.fine("getLookupInfo, newSQL ="+newSQL); //jz
//		}

		//	Direct Query - NO Validation/Security
		info.QueryDirect = createQueryDirect(info);

		//	Validation
		// NOTE (metas): we are not adding the validation here because it will be added on load time (from IValidationRule)

		//	Add Security
		if (!info.isSecurityDisabled())
		{
			info.Query = Env.getUserRolePermissions(ctx).addAccessSQL(info.Query, info.TableName, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
		}
		
		//
	//	s_log.finest("Query:  " + info.Query);
	//	s_log.finest("Direct: " + info.QueryDirect);
		return info;
	}	//	createLookupInfo
	
	/**
	 * Creates Direct access SQL Query. Similar with regular query but without validation rules, no security and no ORDER BY.
	 * 
	 * @param info
	 * @return SELECT Key, Value, DisplayName, IsActive FROM TableName WHERE KeyColumn=?
	 */
	private static final String createQueryDirect(final MLookupInfo info)
	{
		final StringBuilder sqlWhereClause = new StringBuilder();
		if (!Check.isEmpty(info.getWhereClauseSqlPart(), true))
		{
			sqlWhereClause.append("(").append(info.getWhereClauseSqlPart()).append(")");
			sqlWhereClause.append(" AND ");
		}
		sqlWhereClause.append("(").append(info.getKeyColumnFQ()).append("=?").append(")");

		final StringBuilder sql = new StringBuilder(info.getSelectSqlPart());
		sql.append(" WHERE ").append(sqlWhereClause);
		
		return sql.toString();
	}


	/**************************************************************************
	 *	Get Lookup SQL for Lists
	 *  @param language report language
	 *  @param AD_Reference_Value_ID reference value
	 *	@return SELECT NULL, Value, Name, IsActive FROM AD_Ref_List
	 */
	static public MLookupInfo getLookup_List(final Language language, final int AD_Reference_Value_ID)
	{
		final boolean baseLanguage = Env.isBaseLanguage(language, "AD_Ref_List");
		
		String displayColumnSQL;
		final StringBuilder sqlFrom = new StringBuilder("AD_Ref_List");
		if (baseLanguage)
		{
			displayColumnSQL = "AD_Ref_List.Name";
		}
		else
		{
			displayColumnSQL = "trl.Name";
			sqlFrom.append(" INNER JOIN AD_Ref_List_Trl trl ON (AD_Ref_List.AD_Ref_List_ID=trl.AD_Ref_List_ID AND trl.AD_Language=").append(DB.TO_STRING(language.getAD_Language())).append(")");
		}
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			displayColumnSQL = "AD_Ref_List.Value||'_'||" + displayColumnSQL; // metas: tsa: include reference value in reference name
		}
		
		// SQL Select
		final StringBuilder sqlSelect = new StringBuilder("SELECT ") // Key, Value
				.append(" NULL") // Key
				.append(", AD_Ref_List.Value") // Value
				.append(",").append(displayColumnSQL) // DisplayName
				.append(",AD_Ref_List.IsActive") // IsActive
				.append(" FROM ").append(sqlFrom);
		
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
		
		final StringBuilder realSQL = new StringBuilder(sqlSelect)
				.append(" WHERE ").append(sqlWhereClause)
				.append(" ORDER BY ").append(sqlOrderBy);
		
		//
		final MLookupInfo lookupInfo = new MLookupInfo(
				realSQL.toString(), // Query
				"AD_Ref_List", // TableName
				"AD_Ref_List.Value", // KeyColumn
				101, // zoomWindow
				101, // zoomWindowPO
				MQuery.getEqualQuery("AD_Reference_ID", AD_Reference_Value_ID) // Zoom Query
		);
		lookupInfo.setDisplayColumnSQL(displayColumnSQL);
		lookupInfo.setSelectSqlPart(sqlSelect.toString());
		lookupInfo.setFromSqlPart(sqlFrom.toString());
		lookupInfo.setWhereClauseSqlPart(sqlWhereClause.toString());
		lookupInfo.setOrderBySqlPart(sqlOrderBy);
		lookupInfo.setSecurityDisabled(true);
		
		return lookupInfo;
	}	//	getLookup_List

	/**
	 * Get Lookup SQL for List
	 * @param language report Language
	 * @param AD_Reference_Value_ID reference value
	 * @param linkColumnName link column name
	 * @return SELECT Name FROM AD_Ref_List WHERE AD_Reference_ID=x AND Value=linkColumn
	 */
	static public String getLookup_ListEmbed(Language language, int AD_Reference_Value_ID, String linkColumnName)
	{
		final MLookupInfo lookupInfo = getLookup_List(language, AD_Reference_Value_ID);
		if (lookupInfo == null)
		{
			return "";
		}
		
		final String sql = getLookupEmbed(lookupInfo, null, linkColumnName); // baseTable=null
		return sql;
	}	//	getLookup_ListEmbed
	
	private static final ArrayKey createCacheKey(final Properties ctx, final Language language, final ITableRefInfo tableRef)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final String adLanguage = language == null ? "?" : language.getAD_Language();
		return new ArrayKey(adClientId, adLanguage, tableRef);
	}

	/***************************************************************************
	 *	Get Lookup SQL for Table Lookup
	 *
	 *  @param ctx context for access and dynamic access
	 *  @param language report language
	 *  @param WindowNo window no
	 *  @param AD_Reference_Value_ID reference value
	 *	@return	SELECT Key, NULL, Name, IsActive FROM Table - if KeyColumn end with _ID
	 *	  otherwise	SELECT NULL, Key, Name, IsActive FROM Table
	 */
	// NOTE: never make this method public because in case the lookup is cloned from a cached version we need to set the context and other relevant fields anyway
	static private MLookupInfo getLookup_Table (final Properties ctx, final Language language, final int WindowNo, final int AD_Reference_Value_ID)
	{
		final ITableRefInfo tableRefInfo = Services.get(ILookupDAO.class).retrieveTableRefInfo(AD_Reference_Value_ID);
		if (tableRefInfo == null)
		{
			return null;
		}
		
		return getLookupInfo(ctx, language, WindowNo, tableRefInfo);
	}	//	getLookup_Table

	/**
	 *	Get Embedded Lookup SQL for Table Lookup
	 *  @param language report language
	 * 	@param BaseColumn base column name
	 * 	@param BaseTable base table name
	 *  @param AD_Reference_Value_ID reference value
	 *	@return	SELECT Name FROM Table
	 */
	static public String getLookup_TableEmbed (final Language language, final String BaseColumn, final String BaseTable, final int AD_Reference_Value_ID)
	{
		final Properties ctx = Env.getCtx();
		final MLookupInfo lookupInfo = getLookup_Table(ctx, language, Env.WINDOW_None, AD_Reference_Value_ID);
		if (lookupInfo == null)
		{
			return "";
		}
		
		final String sql = getLookupEmbed(lookupInfo, BaseTable, BaseColumn);
		return sql;
	}	//	getLookup_TableEmbed


	/**************************************************************************
	 * Get Lookup SQL for direct Table Lookup
	 * @param ctx context for access
	 * @param language report language
	 * @param ColumnName column name
	 * @return SELECT Key, NULL, Name, IsActive from Table (fully qualified)
	 */
	// NOTE: never make this method public because in case the lookup is cloned from a cached version we need to set the context and other relevant fields anyway
	static private MLookupInfo getLookup_TableDir (final Properties ctx,
			final Language language,
			final int WindowNo,
			final String ColumnName)
	{
		final ITableRefInfo tableRef = Services.get(ILookupDAO.class).retrieveTableDirectRefInfo(ColumnName);
		return getLookupInfo(ctx, language, WindowNo, tableRef);
	}
	
	
	// NOTE: never make this method public because in case the lookup is cloned from a cached version we need to set the context and other relevant fields anyway
	private static MLookupInfo getLookupInfo(final Properties ctx,
			final Language language,
			final int windowNo,
			final ITableRefInfo tableRefInfo)
	{
		Check.assumeNotNull(tableRefInfo, "tableRefInfo not null");
		
		final String TableName = tableRefInfo.getTableName();
		final String KeyColumn = tableRefInfo.getKeyColumn();
		
		final String keyColumnFQ = TableName + "." + KeyColumn;
		//boolean isSOTrx = !"N".equals(Env.getContext(ctx, WindowNo, "IsSOTrx"));

		//try cache
		final ArrayKey cacheKey = createCacheKey(ctx, language, tableRefInfo);
		if (s_cacheRefTable.containsKey(cacheKey))
		{
			return s_cacheRefTable.get(cacheKey).cloneIt();
		}

		final ILookupDisplayInfo lookupDisplayInfo = Services.get(ILookupDAO.class)
				.retrieveLookupDisplayInfo(tableRefInfo);
		if (lookupDisplayInfo == null)
		{
			return null;
		}
		
		final List<ILookupDisplayColumn> displayColumns = lookupDisplayInfo.getLookupDisplayColumns();

		//  Do we have columns ?
		if (displayColumns.isEmpty())
		{
			s_log.log(Level.SEVERE, "No Identifier records found: " + tableRefInfo);
			return null;
		}

		//
		// Display Column SQL
		final StringBuilder displayColumnSQL = new StringBuilder();
		displayColumnSQL.append("concat_ws('_'");
		
		for (final ILookupDisplayColumn ldc : displayColumns)
		{
			displayColumnSQL.append(", ");
			final String columnSQL = ldc.isVirtual() ? ldc.getColumnSQL() : TableName + "." + ldc.getColumnName();

			//  translated
			if (ldc.isTranslated() && !Env.isBaseLanguage(language, TableName) && !ldc.isVirtual())
			{
				displayColumnSQL.append(TableName).append("_Trl.").append(ldc.getColumnName());
			}
			//  date
			else if (DisplayType.isDate(ldc.getDisplayType()))
			{
				displayColumnSQL.append(DB.TO_CHAR(columnSQL, ldc.getDisplayType(), language.getAD_Language()));
			}
			//  TableDir
			else if ((ldc.getDisplayType() == DisplayType.TableDir
						|| (ldc.getDisplayType() == DisplayType.Search) && ldc.getAD_Reference_ID() <= 0)
					&& ldc.getColumnName().endsWith("_ID"))
			{
				final String embeddedSQL;
				if (ldc.isVirtual())
					embeddedSQL = getLookup_TableDirEmbed(language, ldc.getColumnName(), TableName, ldc.getColumnSQL());
				else
					embeddedSQL = getLookup_TableDirEmbed(language, ldc.getColumnName(), TableName);
				if (embeddedSQL != null)
					displayColumnSQL.append("(").append(embeddedSQL).append(")");
			}
			//	Table
			else if (ldc.getDisplayType() == DisplayType.Table
					|| (ldc.getDisplayType() == DisplayType.Search && ldc.getAD_Reference_ID() > 0))
			{
				final String embeddedSQL;
				if (ldc.isVirtual())
					embeddedSQL = getLookup_TableEmbed (language, ldc.getColumnSQL(), TableName, ldc.getAD_Reference_ID());
				else
					embeddedSQL = getLookup_TableEmbed (language, ldc.getColumnName(), TableName, ldc.getAD_Reference_ID());
				if (embeddedSQL != null)
					displayColumnSQL.append("(").append(embeddedSQL).append(")");
			}
			//  number
			else if (DisplayType.isNumeric(ldc.getDisplayType()))
			{
				displayColumnSQL.append(DB.TO_CHAR(columnSQL, ldc.getDisplayType(), language.getAD_Language(), ldc.getFormatPattern()));
			}
			//	ID
			else if (DisplayType.isID(ldc.getDisplayType()))
			{
				displayColumnSQL.append(DB.TO_CHAR(columnSQL, ldc.getDisplayType(), language.getAD_Language()));
			}
			//	Button
			else if (DisplayType.Button == ldc.getDisplayType() && ldc.getColumnName().endsWith("_ID"))
			{
				// metas: handle Record_ID buttons same as regular IDs
				displayColumnSQL.append(DB.TO_CHAR(columnSQL, ldc.getDisplayType(), language.getAD_Language()));
			}
			//  String
			else
			{
				displayColumnSQL.append("NULLIF (TRIM(").append(columnSQL).append("), '')");
			}

		}
		displayColumnSQL.append(")");
		//
		//  FROM SQL
		final StringBuilder sqlFrom = new StringBuilder(TableName);
		final boolean isTranslated = lookupDisplayInfo.isTranslated();
		if (isTranslated && !Env.isBaseLanguage(language, TableName))
		{
			sqlFrom.append(" INNER JOIN ").append(TableName).append("_TRL ON (")
				.append(TableName).append(".").append(KeyColumn).append("=").append(TableName).append("_Trl.").append(KeyColumn)
				.append(" AND ").append(TableName).append("_Trl.AD_Language=").append(DB.TO_STRING(language.getAD_Language())).append(")");
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
		final StringBuilder sqlSelect = new StringBuilder("SELECT ")
				.append(sqlSelectKeyColumnSQL) // Key
				.append(",").append(sqlSelectValueColumnSQL) // Value
				.append(",").append(displayColumnSQL)
				.append(",").append(TableName).append(".IsActive")
				.append(" FROM ").append(sqlFrom);
		
		//
		// Where Clause
		//
		// SQL Where Clause
		final String sqlWhereClause;
		if (!Check.isEmpty(tableRefInfo.getWhereClause(), true))
		{
			final String whereClauseInitial = tableRefInfo.getWhereClause();
			String whereClauseParsed = whereClauseInitial;
			if (whereClauseParsed.indexOf('@') != -1)
			{
				whereClauseParsed = Env.parseContext(ctx, windowNo, whereClauseParsed, false);
			}
			
			if (Check.isEmpty(whereClauseParsed, true))
			{
				sqlWhereClause = null;
				s_log.severe ("Could not resolve: " + whereClauseInitial + ". WhereClause Ignored.");
			}
			else
			{
				if (whereClauseParsed.indexOf('.') == -1)
				{
					s_log.log(Level.SEVERE, "getLookup_Table - " + TableName + ": WHERE should be fully qualified: " + whereClauseInitial);
				}
				sqlWhereClause = whereClauseParsed;
			}
		}
		else
		{
			sqlWhereClause = null;
		}
		
		//
		// Zoom Query
		final MQuery zoomQuery;
		if (sqlWhereClause != null)
		{
			zoomQuery = new MQuery(TableName);
			zoomQuery.addRestriction(sqlWhereClause);
		}
		else
		{
			zoomQuery = null;
		}


		//
		//	Order By qualified term or by Name
		final String sqlOrderBy;
		{
			final String OrderByClause = tableRefInfo.getOrderByClause();
			if (!Check.isEmpty(OrderByClause, true))
			{
				sqlOrderBy = OrderByClause;
				if (OrderByClause.indexOf('.') == -1)
				{
					s_log.log(Level.SEVERE, "getLookup_Table - " + TableName + ": ORDER BY must fully qualified: " + OrderByClause);
				}
			}
			else
			{
				sqlOrderBy = Integer.toString(COLUMNINDEX_DisplayName); // DisplayName
			}
		}
		
		//
		// Final SQL
		final StringBuilder sqlQueryFinal = new StringBuilder(sqlSelect);
		if (!Check.isEmpty(sqlWhereClause, true))
		{
			sqlQueryFinal.append(" WHERE ").append(sqlWhereClause);
		}
		sqlQueryFinal.append(" ORDER BY ").append(sqlOrderBy);

		//
		// Zoom AD_Window_IDs
		int ZoomWindow = tableRefInfo.getZoomWindow();
		if (lookupDisplayInfo.getZoomWindow() > 0)
		{
			ZoomWindow = lookupDisplayInfo.getZoomWindow();
		}
		
		int ZoomWindowPO = tableRefInfo.getZoomWindowPO();
		if (lookupDisplayInfo.getZoomWindowPO() > 0)
		{
			ZoomWindowPO = lookupDisplayInfo.getZoomWindowPO();
		}
		
		final int overrideZoomWindow = tableRefInfo.getOverrideZoomWindow();
		if (overrideZoomWindow > 0)
		{
			ZoomWindow = overrideZoomWindow;
			ZoomWindowPO = 0;
		}

		//
		// Create MLookupInfo
		final MLookupInfo lookupInfo = new MLookupInfo(sqlQueryFinal.toString(),
				TableName, keyColumnFQ,
				ZoomWindow, ZoomWindowPO, zoomQuery);
		lookupInfo.WindowNo = windowNo;
		lookupInfo.setDisplayColumns(displayColumns);
		lookupInfo.setDisplayColumnSQL(displayColumnSQL.toString());
		lookupInfo.setSelectSqlPart(sqlSelect.toString());
		lookupInfo.setFromSqlPart(sqlFrom.toString());
		lookupInfo.setWhereClauseSqlPart(sqlWhereClause);
		lookupInfo.setOrderBySqlPart(sqlOrderBy);
		lookupInfo.setSecurityDisabled(false);
		lookupInfo.setAutoComplete(tableRefInfo.isAutoComplete());

		s_cacheRefTable.put(cacheKey, lookupInfo.cloneIt());
		
		return lookupInfo;
	}	//	getLookup_TableDir


	/**
	 *  Get embedded SQL for TableDir Lookup
	 *
	 *  @param language report language
	 *  @param ColumnName column name
	 *  @param BaseTable base table
	 *  @return SELECT Column FROM TableName WHERE BaseTable.ColumnName=TableName.ColumnName
	 *  @see #getLookup_TableDirEmbed(Language, String, String, String)
	 */
	static public String getLookup_TableDirEmbed (Language language, String ColumnName, String BaseTable)
	{
		return getLookup_TableDirEmbed (language, ColumnName, BaseTable, ColumnName);
	}   //  getLookup_TableDirEmbed

	/**
	 *  Get embedded SQL for TableDir Lookup
	 *
	 *  @param language report language
	 *  @param ColumnName column name
	 *  @param BaseTable base table
	 *  @param BaseColumn base column
	 *  @return SELECT Column FROM TableName WHERE BaseTable.BaseColumn=TableName.ColumnName
	 */
	static public String getLookup_TableDirEmbed (final Language language, final String ColumnName, final String BaseTable, final String BaseColumn)
	{
		final Properties ctx = Env.getCtx();
		final int windowNo = Env.WINDOW_None; // NOTE: for TableDir WindowNo, is not important 
		final MLookupInfo lookupInfo = getLookup_TableDir(ctx, language, windowNo, ColumnName);
		if (lookupInfo == null)
		{
			return "";
		}
		
		final String sql = getLookupEmbed(lookupInfo, BaseTable, BaseColumn);
		return sql;
	}	//  getLookup_TableDirEmbed

// metas: begin
	/**
	 * Unified lookup embedded sql method. Based on AD_Reference_ID and AD_Reference_Value_ID parameters
	 * it will call corresponding getLookup_*Embed methods
	 */
	public static String getLookupEmbed(Properties ctx,
			String BaseColumn, String BaseTable, int AD_Reference_ID, int AD_Reference_Value_ID)
	{
		final Language language = Env.getLanguage(ctx);
		
		if (DisplayType.List == AD_Reference_ID)
		{
			String linkColumnName = BaseColumn;
			return getLookup_ListEmbed(language, AD_Reference_Value_ID, linkColumnName);
		}
		else if (DisplayType.TableDir == AD_Reference_ID
				|| (DisplayType.Search == AD_Reference_ID && AD_Reference_Value_ID <= 0))
		{
			return getLookup_TableDirEmbed(language, BaseColumn, BaseTable, BaseColumn);
		}
		else if (DisplayType.Table == AD_Reference_ID
				|| (DisplayType.Search == AD_Reference_ID && AD_Reference_Value_ID > 0))
		{
			return getLookup_TableEmbed(language, BaseColumn, BaseTable, AD_Reference_Value_ID);
		}
		else
		{
			throw new IllegalArgumentException("DisplayType "+AD_Reference_ID+" not supported");
		}
	}
	
	// NOTE: this is an package level method because we want to JUnit test it
	/* package */ static String getLookupEmbed(final MLookupInfo lookupInfo, final String baseTable, final String baseColumn)
	{
		Check.assumeNotNull(baseColumn, "baseColumn not null");

		if (lookupInfo == null)
		{
			return "";
		}

		
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
		if (lookupInfo.getTableName().equalsIgnoreCase(linkTableName))
		{
			final String tableNameAlias = "a" + System.currentTimeMillis();

			sql.append("select ").append(tableNameAlias).append(".V")
					.append(" from (")
					.append(" select ")
					.append(" ").append(lookupInfo.getDisplayColumnSQL()).append(" AS V")
					.append(",").append(lookupInfo.getKeyColumnFQ()).append(" AS K")
					.append(" from ").append(lookupInfo.getFromSqlPart())
					.append(")").append(" ").append(tableNameAlias)
					.append(" where ")
					.append(tableNameAlias).append(".K=").append(linkColumnNameFQ);
		}
		else
		{
			sql.append(" select ")
					.append(" ").append(lookupInfo.getDisplayColumnSQL())
					.append(" from ").append(lookupInfo.getFromSqlPart())
					.append(" where ")
					.append(lookupInfo.getKeyColumnFQ()).append("=").append(linkColumnNameFQ);
		}

		// task 05076
		if (lookupInfo.ZoomQuery != null && !Check.isEmpty(lookupInfo.ZoomQuery.getWhereClause(), true))
		{
			sql.append(" AND ")
			.append(lookupInfo.ZoomQuery.getWhereClause());
		}
		// task 05076 end
		
		return sql.toString();
	}
}   //  MLookupFactory


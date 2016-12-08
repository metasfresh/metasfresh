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

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;

import org.adempiere.ad.security.TableAccessLevel;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;

import de.metas.logging.LogManager;

/**
 * Persistent Object Info. Provides structural information
 *
 * @author Jorg Janke
 * @version $Id: POInfo.java,v 1.2 2006/07/30 00:58:37 jjanke Exp $
 * @author Victor Perez, e-Evolution SC <li>[ 2195894 ] Improve performance in PO engine <li>http://sourceforge.net/tracker/index.php?func=detail&aid=2195894&group_id=176962&atid=879335
 */
public final class POInfo implements Serializable
{
	/** Used by Remote FinReport */
	static final long serialVersionUID = -5976719579744948419L;

	public static POInfo getPOInfo(final int AD_Table_ID)
	{
		final Properties ctx_NOTUSED = null;
		return getPOInfo(ctx_NOTUSED, AD_Table_ID, ITrx.TRXNAME_None);
	}

	/**
	 * Please consider using {@link #getPOInfo(int)}.
	 * 
	 * @param ctx_NOTUSED context
	 * @param AD_Table_ID AD_Table_ID
	 * @return POInfo
	 */
	public static POInfo getPOInfo(final Properties ctx_NOTUSED, final int AD_Table_ID)
	{
		return getPOInfo(ctx_NOTUSED, AD_Table_ID, ITrx.TRXNAME_None);
	}

	public static POInfo getPOInfo(final String tableName)
	{
		final Properties ctx = null; // null because it's not used
		final String trxName = ITrx.TRXNAME_None;
		return getPOInfo(ctx, tableName, trxName);
	}

	/**
	 * Please consider using {@link #getPOInfo(String)}.
	 * 
	 * @param ctx_NOTUSED
	 * @param tableName
	 * @return POInfo
	 */
	public static POInfo getPOInfo(final Properties ctx_NOTUSED, final String tableName)
	{
		final String trxName = ITrx.TRXNAME_None;
		return getPOInfo(ctx_NOTUSED, tableName, trxName);
	}

	/**
	 * Please consider using {@link #getPOInfo(int)}.
	 * 
	 * @param ctx_NOTUSED context
	 * @param AD_Table_ID AD_Table_ID
	 * @param trxName Transaction name
	 * @return POInfo
	 */
	public static POInfo getPOInfo(final Properties ctx_NOTUSED, final int AD_Table_ID, final String trxName)
	{
		return s_cache.get(AD_Table_ID, new Callable<Optional<POInfo>>()
		{
			@Override
			public Optional<POInfo> call()
			{
				final POInfo poInfo = new POInfo(AD_Table_ID, trxName);
				final boolean valid = poInfo.getColumnCount() > 0;
				if (!valid)
				{
					return Optional.absent();
				}
				final Optional<POInfo> poInfoOptional = Optional.of(poInfo);

				// Update the cache by tablename
				s_cacheByTableNameUC.put(poInfo.getTableNameUC(), poInfoOptional);

				return poInfoOptional;
			}
		}).orNull();
	}   // getPOInfo

	/**
	 * Please consider using {@link #getPOInfo(String)}.
	 * 
	 * @param ctx_NOTUSED
	 * @param tableName
	 * @param trxName
	 * @return
	 */
	public static POInfo getPOInfo(final Properties ctx_NOTUSED, final String tableName, final String trxName)
	{
		if (tableName == null)
		{
			// TODO: shall we throw exception here?
			return null;
		}

		final String tableNameUC = tableName.toUpperCase();
		return s_cacheByTableNameUC.get(tableNameUC, new Callable<Optional<POInfo>>()
		{

			@Override
			public Optional<POInfo> call()
			{
				final POInfo poInfo = new POInfo(tableName, trxName);
				final boolean valid = poInfo.getColumnCount() > 0;
				if (!valid)
				{
					return Optional.absent();
				}
				final Optional<POInfo> poInfoOptional = Optional.of(poInfo);

				// Update the cache by AD_Table_ID
				s_cache.put(poInfo.getAD_Table_ID(), poInfoOptional);

				return poInfoOptional;
			}
		}).orNull();
	}   // getPOInfo

	private static final transient Logger logger = LogManager.getLogger(POInfo.class);

	/** Cache of POInfo */
	private static final CCache<Integer, Optional<POInfo>> s_cache = new CCache<>("POInfo", 200);
	private static final CCache<String, Optional<POInfo>> s_cacheByTableNameUC = new CCache<>("POInfo_ByTableName", 200);

	private POInfo(final String tableName, final String trxName)
	{
		super();

		m_TableName = tableName;
		loadInfo(trxName);
	}   // PInfo

	/**************************************************************************
	 * Create Persistent Info
	 * 
	 * @param AD_Table_ID AD_ Table_ID
	 * @param trxName transaction name
	 */
	private POInfo(final int AD_Table_ID, final String trxName)
	{
		super();

		m_AD_Table_ID = AD_Table_ID;
		loadInfo(trxName);
	}   // PInfo

	/** Table_ID */
	private int m_AD_Table_ID = 0;
	/** Table Name */
	private String m_TableName = null;
	/** Access Level */
	private TableAccessLevel m_AccessLevel = TableAccessLevel.Organization;
	private Boolean m_isView = null;
	/** Columns */
	private POInfoColumn[] m_columns;

	private Map<String, Integer> columnName2columnIndex;
	private Map<Integer, Integer> adColumnId2columnIndex;

	/**
	 * Key Column Names
	 */
	private List<String> m_keyColumnNames;
	/**
	 * Single Primary Key.
	 * 
	 * If table has composed primary key, this variable will be set to null
	 */
	private String m_keyColumnName = null;

	/** Table needs keep log */
	private boolean m_IsChangeLog = false;

	private boolean m_HasStaleableColumns = false;

	private String sqlWhereClauseByKeys;
	private String sqlSelectByKeys;
	private String sqlSelectColumns;
	private String sqlSelect;

	//
	// Translation info
	public static final String COLUMNNAME_AD_Language = "AD_Language";
	/**
	 * True if at least one column is translated
	 */
	private boolean translated = false;
	private ImmutableList<String> translatedColumnNames = null; // built on demand
	private Optional<String> sqlSelectTrlByIdAndLanguage;  // built on demand
	private Optional<String> sqlSelectTrlById;  // built on demand

	/**
	 * Load Table/Column Info into this instance. If the select returns no result, nothing is loaded and no error is raised.
	 * 
	 * @param trxName
	 */
	private final void loadInfo(final String trxName)
	{
		final List<POInfoColumn> list = new ArrayList<>(20);
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT t.TableName, c.ColumnName,c.AD_Reference_ID,"		// 1..3
				+ "c.IsMandatory,c.IsUpdateable,c.DefaultValue, "				// 4..6
				+ "e.Name, "													// 7
				+ "e.Description, "												// 8
				+ "c.AD_Column_ID, "											// 9
				+ "c.IsKey,c.IsParent, "										// 10..11
				+ "c.AD_Reference_Value_ID, "									// 12
				+ "vr.Code, "													// 13
				+ "c.FieldLength, c.ValueMin, c.ValueMax, c.IsTranslated"		// 14..17
				+ ",t.AccessLevel"												// 18
				+ ",c.ColumnSQL"												// 19
				+ ",c.IsEncrypted "												// 20
				+ ",c.IsAllowLogging"											// 21
				+ ",t.IsChangeLog "												// 22
				+ ",c.IsLazyLoading "											// 23
				+ ",c.IsCalculated "											// 24 // metas
				+ ",c.AD_Val_Rule_ID "											// 25 // metas
				+ ",t.AD_Table_ID "												// 26 // metas
				+ ",c." + I_AD_Column.COLUMNNAME_IsUseDocSequence				// 27 // metas: 05133
				+ ",c." + I_AD_Column.COLUMNNAME_IsStaleable					// 28 // metas: 01537
				+ ",c." + I_AD_Column.COLUMNNAME_IsSelectionColumn				// 29 // metas
				+ ",t." + I_AD_Table.COLUMNNAME_IsView							// 30 // metas
		);
		sql.append(" FROM AD_Table t "
				+ " INNER JOIN AD_Column c ON (t.AD_Table_ID=c.AD_Table_ID) "
				+ " LEFT OUTER JOIN AD_Val_Rule vr ON (c.AD_Val_Rule_ID=vr.AD_Val_Rule_ID) "
				+ " INNER JOIN AD_Element e ON (c.AD_Element_ID=e.AD_Element_ID) ");
		sql.append(" WHERE "
				+ (m_AD_Table_ID <= 0 ? "UPPER(t.TableName)=UPPER(?)" : "t.AD_Table_ID=?")
				+ " AND c.IsActive='Y'");
		//
		final List<String> keyColumnNames = new ArrayList<String>();
		final List<String> parentColumnNames = new ArrayList<String>();
		boolean tableInfoLoaded = false;
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), trxName);
			if (m_AD_Table_ID <= 0)
				pstmt.setString(1, m_TableName);
			else
				pstmt.setInt(1, m_AD_Table_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (!tableInfoLoaded)
				{
					m_TableName = rs.getString(I_AD_Table.COLUMNNAME_TableName);
					m_AD_Table_ID = rs.getInt(I_AD_Table.COLUMNNAME_AD_Table_ID);
					m_isView = "Y".equals(rs.getString(I_AD_Table.COLUMNNAME_IsView));
					m_AccessLevel = TableAccessLevel.forAccessLevel(rs.getString(I_AD_Table.COLUMNNAME_AccessLevel));
					m_IsChangeLog = "Y".equals(rs.getString(I_AD_Table.COLUMNNAME_IsChangeLog));
					//
					tableInfoLoaded = true;
				}

				String ColumnName = rs.getString(2);
				int AD_Reference_ID = rs.getInt(3);
				boolean IsMandatory = "Y".equals(rs.getString(4));
				boolean IsUpdateable = "Y".equals(rs.getString(5));
				String DefaultLogic = rs.getString(6);
				String Name = rs.getString(7);
				String Description = rs.getString(8);
				int AD_Column_ID = rs.getInt(9);

				final boolean IsKey = "Y".equals(rs.getString(10));
				if (IsKey)
				{
					keyColumnNames.add(ColumnName);
				}

				final boolean IsParent = "Y".equals(rs.getString(11));
				if (IsParent)
				{
					parentColumnNames.add(ColumnName);
				}

				int AD_Reference_Value_ID = rs.getInt(12);
				// String ValidationCode = rs.getString(13);
				int FieldLength = rs.getInt(14);
				String ValueMin = rs.getString(15);
				String ValueMax = rs.getString(16);
				boolean IsTranslated = "Y".equals(rs.getString(17));
				//
				final String ColumnSQL = rs.getString(19);
				boolean IsEncrypted = "Y".equals(rs.getString(20));
				boolean IsAllowLogging = "Y".equals(rs.getString(21));
				boolean IsLazyLoading = "Y".equals(rs.getString(23)); // metas
				boolean IsCalculated = "Y".equals(rs.getString(24)); // metas
				int AD_Val_Rule_ID = rs.getInt(25); // metas
				final boolean isUseDocumentSequence = "Y".equals(rs.getString(I_AD_Column.COLUMNNAME_IsUseDocSequence)); // metas: 05133

				final boolean isStaleable = "Y".equals(rs.getString(I_AD_Column.COLUMNNAME_IsStaleable)); // metas: 01537
				if (isStaleable)
				{
					m_HasStaleableColumns = true;
				}

				final boolean isSelectionColumn = "Y".equals(rs.getString(I_AD_Column.COLUMNNAME_IsSelectionColumn));

				final POInfoColumn col = new POInfoColumn(
						AD_Column_ID, ColumnName, ColumnSQL, AD_Reference_ID,
						IsMandatory, IsUpdateable,
						DefaultLogic,
						Name, // ColumnLabel
						Description, // ColumnDescription
						IsKey, IsParent,
						AD_Reference_Value_ID, AD_Val_Rule_ID,
						FieldLength, ValueMin, ValueMax,
						IsTranslated, IsEncrypted,
						IsAllowLogging);
				col.IsLazyLoading = IsLazyLoading; // metas
				col.IsCalculated = IsCalculated; // metas
				col.IsUseDocumentSequence = isUseDocumentSequence; // metas: _05133
				col.IsStaleable = isStaleable; // metas: 01537
				col.IsSelectionColumn = isSelectionColumn;
				list.add(col);
			}
		}
		catch (SQLException e)
		{
			logger.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// convert to array
		m_columns = list.toArray(new POInfoColumn[list.size()]);

		//
		// Iterate columns and build pre-calculated values and indexes
		final int columnsCount = m_columns.length;
		// NOTE: we would like to have the columnNames searched case insensive
		// because role's addAccessSQL parsers are using POInfo for checking column availability,
		// and ofc in SQL queries could be with ANY case...
		final ImmutableSortedMap.Builder<String, Integer> columnName2columnIndexBuilder = ImmutableSortedMap.orderedBy(String.CASE_INSENSITIVE_ORDER);
		final ImmutableMap.Builder<Integer, Integer> adColumnId2columnIndexBuilder = ImmutableMap.<Integer, Integer> builder();
		this.translated = false;
		for (int columnIndex = 0; columnIndex < columnsCount; columnIndex++)
		{
			final POInfoColumn columnInfo = m_columns[columnIndex];
			final String columnName = columnInfo.getColumnName();
			final int adColumnId = columnInfo.getAD_Column_ID();

			columnName2columnIndexBuilder.put(columnName, columnIndex);
			adColumnId2columnIndexBuilder.put(adColumnId, columnIndex);

			if (!this.translated && columnInfo.isTranslated())
			{
				this.translated = true;
			}
		}
		this.columnName2columnIndex = columnName2columnIndexBuilder.build();
		this.adColumnId2columnIndex = adColumnId2columnIndexBuilder.build();

		//
		// Set Key Columns Info
		// First: check key columns
		if (!keyColumnNames.isEmpty())
		{
			m_keyColumnNames = ImmutableList.copyOf(keyColumnNames);

			// Set m_keyColumnName only if we have a single primary key
			if (keyColumnNames.size() == 1)
			{
				m_keyColumnName = keyColumnNames.get(0);
			}
			else
			{
				// FIXME: this case shall be forbidden, but i am letting out now because of "EDI_C_BPartner_Product_v" which is breaking this case
				m_keyColumnName = null;
			}
		}
		// Second: check parent columns
		else if (!parentColumnNames.isEmpty())
		{
			m_keyColumnNames = ImmutableList.copyOf(parentColumnNames);
			m_keyColumnName = null;
		}
		// Fallback: there are no keys nor parents
		else
		{
			m_keyColumnNames = Collections.emptyList();
			m_keyColumnName = null;
		}

		//
		// Setup some pre-built SQLs which are frequently used
		sqlSelectColumns = buildSqlSelectColumns();
		sqlSelect = buildSqlSelect();
		sqlWhereClauseByKeys = buildSqlWhereClauseByKeys();
		sqlSelectByKeys = buildSqlSelectByKeys();
	}   // loadInfo

	/**
	 * String representation
	 * 
	 * @return String Representation
	 */
	@Override
	public String toString()
	{
		return "POInfo[" + getTableName() + ",AD_Table_ID=" + getAD_Table_ID() + "]";
	}   // toString

	/**
	 * String representation for index
	 * 
	 * @param index column index
	 * @return String Representation
	 */
	public String toString(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return "POInfo[" + getTableName() + "-(InvalidColumnIndex=" + index + ")]";
		return "POInfo[" + getTableName() + "-" + m_columns[index].toString() + "]";
	}   // toString

	/**
	 * Get Table Name
	 * 
	 * @return Table Name
	 */
	public String getTableName()
	{
		return m_TableName;
	}   // getTableName

	/** @return table name (upper case) */
	public String getTableNameUC()
	{
		return m_TableName == null ? null : m_TableName.toUpperCase();
	}   // getTableName

	/**
	 * Get AD_Table_ID
	 * 
	 * @return AD_Table_ID
	 */
	public int getAD_Table_ID()
	{
		return m_AD_Table_ID;
	}   // getAD_Table_ID

	public boolean isView()
	{
		return m_isView;
	}

	/**
	 * Table has single primary key
	 *
	 * @return true if table has a single primary key
	 */
	public boolean hasKeyColumn()
	{
		return m_keyColumnName != null;
	}	// hasKeyColumn

	/**
	 * 
	 * @return single primary key or null
	 */
	public String getKeyColumnName()
	{
		return m_keyColumnName;
	}

	/**
	 * 
	 * @return list column names which compose the primary key or empty list; never return null
	 */
	public List<String> getKeyColumnNames()
	{
		return m_keyColumnNames;
	}

	/**
	 * Creates a new array copy of {@link #getKeyColumnNames()}
	 * 
	 * @return array of key column names
	 */
	public String[] getKeyColumnNamesAsArray()
	{
		return m_keyColumnNames.toArray(new String[m_keyColumnNames.size()]);
	}

	/**
	 * Get Table Access Level
	 *
	 * @return {@link TableAccessLevel}; never returns <code>null</code>
	 */
	public TableAccessLevel getAccessLevel()
	{
		return m_AccessLevel;
	}	// getAccessLevel

	/**
	 * Gets Table Access Level as integer.
	 * 
	 * @return {@link TableAccessLevel#getAccessLevelInt()}
	 * @deprecated Please use {@link #getAccessLevel()}
	 */
	@Deprecated
	int getAccessLevelInt()
	{
		return m_AccessLevel.getAccessLevelInt();
	}

	/**************************************************************************
	 * Get ColumnCount
	 * 
	 * @return column count
	 */
	public int getColumnCount()
	{
		return m_columns.length;
	}   // getColumnCount

	/**
	 * 
	 * @param columnName
	 * @return true if given column name exists
	 */
	public boolean hasColumnName(final String columnName)
	{
		final int idx = getColumnIndex(columnName);
		return idx >= 0;
	}

	/**
	 * Get Column Index
	 * 
	 * @param ColumnName column name
	 * @return index of column with ColumnName or -1 if not found
	 */
	// TODO: handle column names ignoring the case
	public int getColumnIndex(final String ColumnName)
	{
		final Integer columnIndex = columnName2columnIndex.get(ColumnName);
		if (columnIndex != null)
		{
			return columnIndex;
		}

		return -1;
	}   // getColumnIndex

	/**
	 * Get Column Index
	 * 
	 * @param AD_Column_ID column
	 * @return index of column with ColumnName or -1 if not found
	 */
	public int getColumnIndex(int AD_Column_ID)
	{
		if (AD_Column_ID <= 0)
		{
			return -1;
		}

		final Integer columnIndex = adColumnId2columnIndex.get(AD_Column_ID);
		if (columnIndex != null)
		{
			return columnIndex;
		}

		//
		// Fallback: for some reason column index was not found
		// => iterate columns and try to get it
		logger.warn("ColumnIndex was not found for AD_Column_ID={} on '{}'. Searching one by one.", new Object[] { AD_Column_ID, this });
		for (int i = 0; i < m_columns.length; i++)
		{
			if (AD_Column_ID == m_columns[i].AD_Column_ID)
				return i;
		}
		return -1;
	}   // getColumnIndex

	/**
	 * @param columnName
	 * @return AD_Column_ID if found, -1 if not found
	 */
	public int getAD_Column_ID(String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return -1;
		}

		return m_columns[columnIndex].getAD_Column_ID();
	}

	/**
	 * Get Column
	 * 
	 * @param index index
	 * @return column
	 */
	// metas: making getColumn public to enable easier testing (was protected)
	public POInfoColumn getColumn(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return null;
		return m_columns[index];
	}   // getColumn

	/**
	 * @return immutable set of all column names
	 */
	public Set<String> getColumnNames()
	{
		return columnName2columnIndex.keySet();
	}

	/**
	 * Get Column Name
	 * 
	 * @param index index
	 * @return ColumnName column name
	 */
	public String getColumnName(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return null;
		return m_columns[index].getColumnName();
	}   // getColumnName

	/**
	 * Get Column SQL or Column Name, including the "AS ColumnName"
	 * 
	 * @param index column index
	 */
	public String getColumnSqlForSelect(final int index)
	{
		if (index < 0 || index >= m_columns.length)
		{
			return null;
		}

		final POInfoColumn columnInfo = m_columns[index];
		return columnInfo.getColumnSqlForSelect();
	}

	/**
	 * Get Column SQL or Column Name, including the "AS ColumnName".
	 * 
	 * @param columnName
	 */
	public String getColumnSqlForSelect(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			throw new IllegalArgumentException("Column name " + columnName + " not found in " + this);
		}
		return getColumnSqlForSelect(columnIndex);
	}

	/**
	 * Get Column SQL or Column Name, without the "AS ColumnName"
	 * 
	 * @param index
	 * @see #getColumnSqlForSelect(int)
	 */
	public String getColumnSql(final int index)
	{
		if (index < 0 || index >= m_columns.length)
		{
			return null;
		}

		final POInfoColumn columnInfo = m_columns[index];
		return columnInfo.isVirtualColumn() ? columnInfo.getColumnSQL() : columnInfo.getColumnName();
	}

	/**
	 * Get Column SQL or Column Name, without the "AS ColumnName"
	 * 
	 * @param columnName
	 * @see #getColumnSqlForSelect(String)
	 */
	public String getColumnSql(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			throw new IllegalArgumentException("Column name " + columnName + " not found in " + this);
		}
		return getColumnSql(columnIndex);
	}

	/**
	 * Is Column Virtual?
	 * 
	 * @param index index
	 * @return true if column is virtual
	 */
	public boolean isVirtualColumn(int index)
	{
		if (index < 0 || index >= m_columns.length)
		{
			// If index is not valid, we consider this a virtual column
			return true;
		}
		return m_columns[index].isVirtualColumn();
	}   // isVirtualColumn

	public boolean isVirtualColumn(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return false;
		}
		return isVirtualColumn(columnIndex);
	}

	/**
	 * Get Column Label
	 * 
	 * @param index index
	 * @return column label
	 */
	public String getColumnLabel(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return null;
		return m_columns[index].getColumnLabel();
	}   // getColumnLabel

	/**
	 * Get Column Description
	 * 
	 * @param index index
	 * @return column description
	 */
	public String getColumnDescription(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return null;
		return m_columns[index].getColumnDescription();
	}   // getColumnDescription

	/**
	 * Get Column Class
	 * 
	 * @param index index
	 * @return Class
	 */
	public Class<?> getColumnClass(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return null;
		return m_columns[index].ColumnClass;
	}   // getColumnClass
	
	/**
	 * Get Column Class
	 * 
	 * @param columnName
	 * @return Class
	 */
	public Class<?> getColumnClass(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return null;
		}
		return getColumnClass(columnIndex);
	}


	/**
	 * Get Column Display Type
	 * 
	 * @param index index
	 * @return DisplayType
	 */
	public int getColumnDisplayType(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return DisplayType.String;
		return m_columns[index].DisplayType;
	}   // getColumnDisplayType

	public int getColumnDisplayType(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			throw new IllegalArgumentException("Column name " + columnName + " not found in " + this);
		}
		return getColumnDisplayType(columnIndex);
	}

	/**
	 * Get Column Default Logic
	 * 
	 * @param index index
	 * @return Default Logic
	 */
	public String getDefaultLogic(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return null;
		return m_columns[index].DefaultLogic;
	}   // getDefaultLogic

	/**
	 * Is Column Mandatory
	 * 
	 * @param index index
	 * @return true if column mandatory
	 */
	public boolean isColumnMandatory(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return false;
		return m_columns[index].IsMandatory;
	}   // isMandatory

	// metas-03035 begin
	// method has been added to find out which table a given column references
	//
	/**
	 * Returns the columns <code>AD_Reference_Value_ID</code>.
	 * 
	 * @param index index
	 * @return the column's AD_Reference_Value_ID or -1 if the given index is not valid
	 */
	public int getColumnReferenceValueId(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return -1;
		return m_columns[index].AD_Reference_Value_ID;
	}

	/**
	 * Returns the columns <code>AD_Reference_Value_ID</code>.
	 * 
	 * @param columnName
	 * @return the column's AD_Reference_Value_ID or -1 if the given index is not valid
	 */
	public int getColumnReferenceValueId(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		return getColumnReferenceValueId(columnIndex);
	}

	//
	// metas-03035 end

	/**
	 * Is Column Updateable
	 * 
	 * @param index index
	 * @return true if column updateable
	 */
	public boolean isColumnUpdateable(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return false;
		return m_columns[index].IsUpdateable;
	}   // isUpdateable

	/**
	 * Is Column Updateable
	 * 
	 * @param columnName
	 * @return true if column updateable
	 */
	public boolean isColumnUpdateable(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		return isColumnUpdateable(columnIndex);
	}   // isUpdateable

	/**
	 * Set all columns updateable
	 * 
	 * @param updateable updateable
	 * 
	 * @deprecated This method will be deleted in future because our {@link POInfo} has to be immutable.
	 */
	@Deprecated
	public void setUpdateable(boolean updateable)
	{
		for (int i = 0; i < m_columns.length; i++)
		{
			m_columns[i].IsUpdateable = updateable;
		}
	}	// setUpdateable
	
	public String getReferencedTableNameOrNull(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		final POInfoColumn poInfoColumn = m_columns[columnIndex];
		return poInfoColumn.getReferencedTableNameOrNull();
	}

	/**
	 * Get Lookup
	 *
	 * @param ctx
	 * @param columnIndex index
	 * @return Lookup
	 */
	public Lookup getColumnLookup(final Properties ctx, final int columnIndex)
	{
		return m_columns[columnIndex].getLookup(ctx, Env.WINDOW_None);
	}

	public Lookup getColumnLookup(final Properties ctx, final int windowNo, final int columnIndex)
	{
		return m_columns[columnIndex].getLookup(ctx, windowNo);
	}

	/**
	 * Is Column Key
	 * 
	 * @param index index
	 * @return true if column is the key
	 */
	public boolean isKey(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return false;
		return m_columns[index].IsKey;
	}   // isKey

	/**
	 * 
	 * @param columnName
	 * @return true if column is the key
	 */
	public boolean isKey(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return false;
		}
		return isKey(columnIndex);
	}   // isKey

	/**
	 * Is Column Parent
	 * 
	 * @param index index
	 * @return true if column is a Parent
	 */
	public boolean isColumnParent(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return false;
		return m_columns[index].IsParent;
	}   // isColumnParent
	
	public boolean isColumnParent(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		return isColumnParent(columnIndex);
	}

	/**
	 * Is Column Translated
	 * 
	 * @param index index
	 * @return true if column is translated
	 */
	public boolean isColumnTranslated(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return false;
		return m_columns[index].isTranslated();
	}   // isColumnTranslated

	/**
	 * Is Table Translated
	 * 
	 * @return true if table is translated
	 */
	public boolean isTranslated()
	{
		return this.translated;
	}   // isTranslated

	/**
	 * Is Column (data) Encrypted
	 * 
	 * @param index index
	 * @return true if column is encrypted
	 */
	public boolean isEncrypted(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return false;
		return m_columns[index].IsEncrypted;
	}   // isEncrypted

	/**
	 * Is allowed logging on this column
	 * 
	 * @param index index
	 * @return true if column is allowed to be logged
	 */
	public boolean isAllowLogging(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return false;
		return m_columns[index].IsAllowLogging;
	} // isAllowLogging

	/**
	 * Get Column FieldLength
	 * 
	 * @param index index
	 * @return field length
	 */
	public int getFieldLength(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return 0;
		return m_columns[index].FieldLength;
	}   // getFieldLength

	/**
	 * Get Column FieldLength
	 * 
	 * @param columnName Column Name
	 * @return field length or 0
	 */
	public int getFieldLength(String columnName)
	{
		int index = getColumnIndex(columnName);
		if (index >= 0)
		{
			return getFieldLength(index);
		}
		return 0;
	}

	/**
	 * Validate Content
	 * 
	 * @param index index
	 * @param value new Value
	 * @return null if all valid otherwise error message
	 */
	public String validate(int index, Object value)
	{
		if (index < 0 || index >= m_columns.length)
			return "RangeError";
		// Mandatory (i.e. not null
		if (m_columns[index].IsMandatory && value == null)
		{
			return "IsMandatory";
		}
		if (value == null)
			return null;

		// Length ignored

		//
		if (m_columns[index].ValueMin != null)
		{
			BigDecimal value_BD = null;
			try
			{
				if (m_columns[index].ValueMin_BD != null)
					value_BD = new BigDecimal(value.toString());
			}
			catch (Exception ex)
			{
			}
			// Both are Numeric
			if (m_columns[index].ValueMin_BD != null && value_BD != null)
			{	// error: 1 - 0 => 1 - OK: 1 - 1 => 0 & 1 - 10 => -1
				int comp = m_columns[index].ValueMin_BD.compareTo(value_BD);
				if (comp > 0)
				{
					return "MinValue=" + m_columns[index].ValueMin_BD
							+ "(" + m_columns[index].ValueMin + ")"
							+ " - compared with Numeric Value=" + value_BD + "(" + value + ")"
							+ " - results in " + comp;
				}
			}
			else
			// String
			{
				int comp = m_columns[index].ValueMin.compareTo(value.toString());
				if (comp > 0)
				{
					return "MinValue=" + m_columns[index].ValueMin
							+ " - compared with String Value=" + value
							+ " - results in " + comp;
				}
			}
		}
		if (m_columns[index].ValueMax != null)
		{
			BigDecimal value_BD = null;
			try
			{
				if (m_columns[index].ValueMax_BD != null)
					value_BD = new BigDecimal(value.toString());
			}
			catch (Exception ex)
			{
			}
			// Both are Numeric
			if (m_columns[index].ValueMax_BD != null && value_BD != null)
			{	// error 12 - 20 => -1 - OK: 12 - 12 => 0 & 12 - 10 => 1
				int comp = m_columns[index].ValueMax_BD.compareTo(value_BD);
				if (comp < 0)
				{
					return "MaxValue=" + m_columns[index].ValueMax_BD + "(" + m_columns[index].ValueMax + ")"
							+ " - compared with Numeric Value=" + value_BD + "(" + value + ")"
							+ " - results in " + comp;
				}
			}
			else
			// String
			{
				int comp = m_columns[index].ValueMax.compareTo(value.toString());
				if (comp < 0)
				{
					return "MaxValue=" + m_columns[index].ValueMax
							+ " - compared with String Value=" + value
							+ " - results in " + comp;
				}
			}
		}
		return null;
	}   // validate

	public boolean isLazyLoading(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return true;
		return isVirtualColumn(index) && m_columns[index].IsLazyLoading;
	}

	/**
	 * Build select clause (i.e. SELECT column names FROM TableName)
	 * 
	 * @return string builder
	 */
	public StringBuilder buildSelect()
	{
		final StringBuilder sql = new StringBuilder(sqlSelect);
		return sql;
	}

	private final String buildSqlSelect()
	{
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ")
				.append(getSqlSelectColumns())
				.append(" FROM ").append(getTableName());
		return sql.toString();
	}

	private final String buildSqlSelectColumns()
	{
		final StringBuilder sql = new StringBuilder();
		final int size = getColumnCount();
		for (int i = 0; i < size; i++)
		{
			if (isLazyLoading(i))
				continue;
			if (sql.length() > 0)
				sql.append(",");
			sql.append(getColumnSqlForSelect(i)); // Normal and Virtual Column
		}

		return sql.toString();
	}

	/**
	 * @return all columns to select in SQL format (i.e. ColumnName1, ColumnName2 ....)
	 */
	public final String getSqlSelectColumns()
	{
		return sqlSelectColumns;
	}

	/**
	 * 
	 * @return if table save log
	 */
	public boolean isChangeLog()
	{
		return m_IsChangeLog;
	}

	// metas: us215
	public boolean isCalculated(int index)
	{
		if (index < 0 || index >= m_columns.length)
			return false;
		return m_columns[index].IsCalculated;
	}

	public boolean isCalculated(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return false;
		}
		return isCalculated(columnIndex);
	}

	// metas-03035 begin
	//
	/**
	 * Makes sure that a POInfo with the given <code>tableName</code> is not cached (anymore). This method can be used to make sure that a subsequent call of one of any <code>getPOInfo(...)</code>
	 * method causes a reload from DB.
	 * 
	 * @param tableName if there is a cached POInfo instance whose {@link #getTableName()} method returns this string, it is removed from cache
	 */
	public static void removeFromCache(final String tableName)
	{
		if (tableName == null)
		{
			return;
		}

		final String tableNameUC = tableName.toUpperCase();
		final POInfo cachedValue = s_cacheByTableNameUC.remove(tableNameUC).orNull();
		if (cachedValue == null)
		{
			return; // nothing to do
		}
		s_cache.remove(cachedValue.getAD_Table_ID());
	}

	/**
	 * Makes sure that a POInfo with the given <code>AD_Table_ID</code> is not cached (anymore). This method can be used to make sure that a subsequent call of one of any <code>getPOInfo(...)</code>
	 * method causes a reload from DB.
	 * 
	 * @param AD_Table_ID if there is a cached POInfo instance whose {@link #getAD_Table_ID()} method returns this int, it is removed from cache
	 */
	public static void removeFromCache(final int AD_Table_ID)
	{
		final POInfo cachedValue = s_cache.remove(AD_Table_ID).orNull();
		if (cachedValue == null)
		{
			return; // nothing to do
		}
		s_cache.remove(AD_Table_ID);
		s_cacheByTableNameUC.remove(cachedValue.getTableNameUC());
	}

	//
	// metas-03035 end

	/**
	 * Shall we generate auto-sequence for given String column
	 * 
	 * @param index
	 * @return true if we shall generate auto-sequence
	 */
	// metas 05133
	public boolean isUseDocSequence(final int index)
	{
		if (index < 0 || index >= m_columns.length)
			return false;
		return m_columns[index].IsUseDocumentSequence;
	}

	/**
	 * 
	 * @return true if we shall re-load the model after save
	 */
	public boolean isLoadAfterSave()
	{
		// TODO: implement a better logic
		return false;
	}

	/**
	 * Gets SQL to be used when loading from model table
	 * 
	 * The returned SQL will look like: <br>
	 * SELECT columnName1, columnName2, (SELECT ...) as VirtualColumn3 FROM TableName
	 * 
	 * @return SELECT ... FROM TableName
	 */
	/* package */String getSqlSelect()
	{
		return sqlSelect;
	}

	/**
	 * Gets SQL to be used when loading a record
	 * <p>
	 * e.g. SELECT columnNames FROM TableName WHERE KeyColumn1=? AND KeyColumn2=?
	 * 
	 * @return SELECT SQL for loading
	 */
	/* package */String getSqlSelectByKeys()
	{
		return sqlSelectByKeys;
	}

	private final String buildSqlSelectByKeys()
	{
		final StringBuilder sql = new StringBuilder();
		sql.append(getSqlSelect());
		sql.append(" WHERE ")
				.append(getSqlWhereClauseByKeys());

		return sql.toString();
	}

	/**
	 * Gets SQL Where Clause by KeyColumns.
	 * 
	 * NOTE: values are parameterized
	 * 
	 * @return SQL where clause (e.g. KeyColumn_ID=?)
	 */
	/* package */String getSqlWhereClauseByKeys()
	{
		return sqlWhereClauseByKeys;
	}

	private final String buildSqlWhereClauseByKeys()
	{
		final StringBuilder sb = new StringBuilder();
		for (final String keyColumnName : m_keyColumnNames)
		{
			if (sb.length() > 0)
			{
				sb.append(" AND ");
			}

			sb.append(keyColumnName).append("=?");
		}
		return sb.toString();
	}

	/**
	 * Checks if given column can be stale
	 * 
	 * @param columnIndex
	 * @return false if the given column can NEVER get staled after a save
	 */
	/* package */boolean isColumnStaleable(final int columnIndex)
	{
		if (columnIndex < 0 || columnIndex >= m_columns.length)
		{
			return true;
		}

		return m_columns[columnIndex].IsStaleable;
	}

	/**
	 * 
	 * @return true if there is at least one column which is staleable
	 */
	/* package */boolean hasStaleableColumns()
	{
		return m_HasStaleableColumns;
	}
	
	/**
	 * @return list of column names which are translated
	 */
	public List<String> getTranslatedColumnNames()
	{
		if (translatedColumnNames == null)
		{
			if (isTranslated())
			{
				final ImmutableList.Builder<String> columnNames = ImmutableList.builder();
				for (final POInfoColumn columnInfo : this.m_columns)
				{
					if (!columnInfo.isTranslated())
					{
						continue;
					}

					columnNames.add(columnInfo.getColumnName());
				}

				translatedColumnNames = columnNames.build();
			}
			else
			{
				translatedColumnNames = ImmutableList.of();
			}
		}
		return translatedColumnNames;
	}

	/**
	 * @return <code>SELECT * FROM TableName_Trl WHERE KeyColumnName=? AND AD_Language=?</code> or <code>null</code>
	 */
	public String getSqlSelectTrlByIdAndLanguage()
	{
		if (sqlSelectTrlByIdAndLanguage == null)
		{
			sqlSelectTrlByIdAndLanguage = buildSqlSelectTrl(true); // byLanguageToo=true
		}
		return sqlSelectTrlByIdAndLanguage.orNull();
	}

	/**
	 * @return <code>SELECT * FROM TableName_Trl WHERE KeyColumnName=?</code> or <code>null</code>
	 */
	public String getSqlSelectTrlById()
	{
		if (sqlSelectTrlById == null)
		{
			sqlSelectTrlById = buildSqlSelectTrl(false); // byLanguageToo=false
		}
		return sqlSelectTrlById.orNull();
	}

	/**
	 * Builds the SQL to select from translation table (i.e. <code>SELECT * FROM TableName_Trl WHERE KeyColumnName=? [AND AD_Language=?]</code>)
	 * 
	 * @param byLanguageToo if <code>true</code> the returned SQL shall also filter by "AD_Language"
	 * @return
	 * 		<ul>
	 *         <li>SQL select
	 *         <li>or {@link Optional#absent()} if table is not translatable or there are no translation columns
	 *         </ul>
	 */
	private Optional<String> buildSqlSelectTrl(final boolean byLanguageToo)
	{
		final String keyColumnName = getKeyColumnName(); // assume not null
		if (keyColumnName == null)
		{
			return Optional.absent();
		}

		final List<String> columnNames = getTranslatedColumnNames(); // assume not empty
		if (columnNames.isEmpty())
		{
			return Optional.absent();
		}

		final String sqlColumns = Joiner.on(",").join(columnNames);
		StringBuilder sql = new StringBuilder("SELECT ")
				.append(sqlColumns)
				.append(", ").append(COLUMNNAME_AD_Language)
				.append(" FROM ").append(getTableName()).append("_Trl")
				.append(" WHERE ")
				.append(keyColumnName).append("=?");

		if (byLanguageToo)
		{
			sql.append(" AND ").append(COLUMNNAME_AD_Language).append("=?");
		}

		return Optional.of(sql.toString());
	}
}   // POInfo

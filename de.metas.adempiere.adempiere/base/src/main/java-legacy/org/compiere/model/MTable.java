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
 * Contributor(s): Carlos Ruiz - globalqss *
 *****************************************************************************/
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal;
import org.adempiere.ad.persistence.TableModelClassLoader;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Persistent Table Model
 * <p>
 * Change log:
 * <ul>
 * <li>2007-02-01 - teo_sarca - [ 1648850 ] MTable.getClass works incorrect for table "Fact_Acct"
 * </ul>
 * <ul>
 * <li>2007-08-30 - vpj-cd - [ 1784588 ] Use ModelPackage of EntityType to Find Model Class
 * </ul>
 *
 * @author Jorg Janke
 *         <li>BF [ 3133032 ] Adempiere is not loading classes from org.compiere.report
 *         https://sourceforge.net/tracker/?func=detail&aid=3133032&group_id=176962&atid=879332
 * @version $Id: MTable.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class MTable extends X_AD_Table
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2367316254623142732L;

	/**
	 * Get Table from Cache
	 *
	 * @param ctx context
	 * @param AD_Table_ID id
	 * @return MTable
	 */
	public static MTable get(final Properties ctx, final int AD_Table_ID)
	{
		try
		{
			s_cacheLock.lock();

			MTable retValue = s_cache.get(AD_Table_ID);
			if (retValue != null && Env.isSame(retValue.getCtx(), ctx))
			{
				return retValue;
			}
			retValue = new MTable(ctx, AD_Table_ID, ITrx.TRXNAME_None);
			if (retValue.get_ID() > 0)
			{
				final String tableName = retValue.getTableName();

				s_cache.put(AD_Table_ID, retValue);
				s_cacheTableNameUC2Table.put(tableName.toUpperCase(), retValue);

				// metas
				if (s_cacheTableName2Id != null)
				{
					s_cacheTableName2Id.put(tableName, retValue.getAD_Table_ID());
				}
			}
			return retValue;
		}
		finally
		{
			s_cacheLock.unlock();
		}
	}	// get

	/**
	 * Get Table from Cache
	 *
	 * @param ctx context
	 * @param tableName case insensitive table name
	 * @return Table
	 */
	public static MTable get(final Properties ctx, final String tableName)
	{
		if (tableName == null)
		{
			return null;
		}

		try
		{
			s_cacheLock.lock();

			final String tableNameUC = tableName.toUpperCase();

			//
			// Check cache
			MTable retValue = s_cacheTableNameUC2Table.get(tableNameUC);
			if (retValue != null && retValue.getCtx() == ctx)
			{
				return retValue;
			}

			//
			// Load from database
			final String sql = "SELECT * FROM AD_Table WHERE UPPER(TableName)=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				pstmt.setString(1, tableNameUC);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					retValue = new MTable(ctx, rs, ITrx.TRXNAME_None);
				}
			}
			catch (Exception e)
			{
				s_log.error(sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

			if (retValue != null)
			{
				final int adTableId = retValue.getAD_Table_ID();
				s_cache.put(adTableId, retValue);
				s_cacheTableNameUC2Table.put(tableNameUC, retValue);

				// metas
				if (s_cacheTableName2Id != null)
				{
					s_cacheTableName2Id.put(retValue.getTableName(), adTableId);
				}
			}
			return retValue;
		}
		finally
		{
			s_cacheLock.unlock();
		}
	}	// get

	/**
	 * Get Table Name
	 *
	 * @param ctx context
	 * @param AD_Table_ID table
	 * @return table name
	 * @deprecated Please use {@link IADTableDAO#retrieveTableName(int)}
	 */
	@Deprecated
	public static String getTableName(Properties ctx, int AD_Table_ID)
	{
		if (org.compiere.Adempiere.isUnitTestMode())
		{
			for (final Map.Entry<String, Integer> e : staticTableIds.entrySet())
			{
				if (e.getValue() == AD_Table_ID)
				{
					return e.getKey();
				}
			}
			throw new AdempiereException("No TableName found for AD_Table_ID=" + AD_Table_ID);
		}

		return MTable.get(ctx, AD_Table_ID).getTableName();
	}	// getTableName

	/** Cache */
	private static final CCache<Integer, MTable> s_cache = new CCache<>("AD_Table", 500, 0);
	private static final CCache<String, MTable> s_cacheTableNameUC2Table = new CCache<>("AD_Table", 500, 0);
	private static final ReentrantLock s_cacheLock = new ReentrantLock();

	/** Static Logger */
	private static Logger s_log = LogManager.getLogger(MTable.class);

	/** EntityTypes */
	// metas: tsa: load entity types only when they are needed, else database decoupled testing is not possible. See EntityTypeNames class.
	// private static MEntityType[] entityTypes = null;

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param AD_Table_ID id
	 * @param trxName transaction
	 */
	public MTable(Properties ctx, int AD_Table_ID, String trxName)
	{
		super(ctx, AD_Table_ID, trxName);
		if (AD_Table_ID == 0)
		{
			// setName (null);
			// setTableName (null);
			setAccessLevel(ACCESSLEVEL_SystemOnly);	// 4
			setEntityType(ENTITYTYPE_UserMaintained);	// U
			setIsChangeLog(false);
			setIsDeleteable(false);
			setIsHighVolume(false);
			setIsSecurityEnabled(false);
			setIsView(false);	// N
			setReplicationType(REPLICATIONTYPE_Local);
		}
	}	// MTable

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MTable(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MTable

	/** Columns */
	private MColumn[] m_columns = null;

	private final transient ReentrantLock columnsLoadLock = new ReentrantLock();

	/**
	 * Get Columns
	 *
	 * @param requery requery
	 * @return array of columns
	 */
	public MColumn[] getColumns(boolean requery)
	{
		if (m_columns != null && !requery)
		{
			return m_columns;
		}

		columnsLoadLock.lock();
		try
		{
			final String sql = "SELECT * FROM AD_Column WHERE AD_Table_ID=? ORDER BY ColumnName";
			final Object[] params = new Object[] { getAD_Table_ID() };
			List<MColumn> list = new ArrayList<MColumn>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, get_TrxName());
				DB.setParameters(pstmt, params);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					final MColumn column = new MColumn(getCtx(), rs, get_TrxName());
					list.add(column);
				}
			}
			catch (Exception e)
			{
				throw new DBException(e, sql, params);
			}
			finally
			{
				DB.close(rs, pstmt);
			}
			//
			m_columns = new MColumn[list.size()];
			list.toArray(m_columns);
		}
		finally
		{
			columnsLoadLock.unlock();
		}
		return m_columns;
	}	// getColumns

	/**
	 * Get Column
	 *
	 * @param columnName (case insensitive)
	 * @return column if found; null if column was not found
	 * @deprecated Please use {@link IADTableDAO#retrieveColumnOrNull(String, String)}
	 */
	@Deprecated
	public MColumn getColumn(String columnName)
	{
		final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

		final I_AD_Column column = tableDAO.retrieveColumnOrNull(getTableName(), columnName);
		return LegacyAdapters.convertToPO(column);
	}	// getColumn

	/**
	 * Get Key Columns of Table
	 *
	 * @return key columns
	 */
	public String[] getKeyColumns()
	{
		final List<String> list = new ArrayList<String>();
		//
		for (final MColumn column : getColumns(false))
		{
			if (column.isKey())
			{
				return new String[] { column.getColumnName() };
			}
			if (column.isParent())
			{
				list.add(column.getColumnName());
			}
		}
		final String[] retValue = list.toArray(new String[list.size()]);
		return retValue;
	}	// getKeyColumns

	/**************************************************************************
	 * Get PO Class Instance
	 *
	 * @param Record_ID record
	 * @param trxName
	 * @return PO for Record or null
	 * @deprecated Please consider using {@link TableModelLoader#getPO(Properties, String, int, String)} or {@link TableRecordCacheLocal#getReferencedValue(Object, Class)}.
	 */
	@Deprecated
	public PO getPO(final int Record_ID, final String trxName)
	{
		final Properties ctx = getCtx();
		final String tableName = getTableName();
		return TableModelLoader.instance.getPO(ctx, tableName, Record_ID, trxName);
	}

	/**
	 *
	 * @param tableName
	 * @return tableName's model class
	 * @deprecated Please use {@link TableModelClassLoader#getClass(String)}.
	 */
	@Deprecated
	public static final Class<?> getClass(String tableName)
	{
		return TableModelClassLoader.instance.getClass(tableName);
	}

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (isView() && isDeleteable())
			setIsDeleteable(false);
		//
		return true;
	}	// beforeSave

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success success
	 * @return success
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		//
		// Create/Update table sequences
		// NOTE: we shall do this only if it's a new table, else we will change the sequence's next value
		// which could be OK on our local development database,
		// but when the migration script will be executed on target customer database, their sequences will be wrongly changed (08607)
		if (success && newRecord)
		{
			Services.get(ISequenceDAO.class).createTableSequenceChecker(getCtx())
					.setFailOnFirstError(true)
					.setSequenceRangeCheck(false)
					.setTable(this)
					.setTrxName(get_TrxName())
					.run();
		}

		if (!newRecord && is_ValueChanged(COLUMNNAME_TableName))
		{
			Services.get(IADTableDAO.class).onTableNameRename(this);
		}

		return success;
	}	// afterSave

	/**
	 * Get SQL Create
	 *
	 * @return create table DDL
	 */
	public String getSQLCreate()
	{
		final StringBuffer sb = new StringBuffer("CREATE TABLE ")
				.append(getTableName())
				.append(" (");
		//
		boolean hasPK = false;
		boolean hasParents = false;
		final StringBuffer constraints = new StringBuffer();

		getColumns(true);

		for (int i = 0; i < m_columns.length; i++)
		{
			final MColumn column = m_columns[i];
			final String colSQL = column.getSQLDDL();
			if (!Check.isEmpty(colSQL, true))
			{
				if (i > 0)
				{
					sb.append(", ");
				}
				sb.append(column.getSQLDDL());
			}
			else
			{
				continue; // virtual column
			}

			if (column.isKey())
			{
				hasPK = true;
			}
			if (column.isParent())
			{
				hasParents = true;
			}

			final String constraint = column.getConstraint(getTableName());
			if (!Check.isEmpty(constraint, true))
			{
				constraints.append(", ").append(constraint);
			}
		}
		// Multi Column PK
		if (!hasPK && hasParents)
		{
			final StringBuffer cols = new StringBuffer();
			for (int i = 0; i < m_columns.length; i++)
			{
				final MColumn column = m_columns[i];
				if (!column.isParent())
				{
					continue;
				}
				if (cols.length() > 0)
				{
					cols.append(", ");
				}
				cols.append(column.getColumnName());
			}

			sb.append(", CONSTRAINT ")
					.append(getTableName()).append("_Key PRIMARY KEY (")
					.append(cols).append(")");
		}

		sb
				.append(constraints)
				.append(")");

		return sb.toString();
	}	// getSQLCreate

	// globalqss
	/**
	 * Grant independence to GenerateModel from AD_Table_ID
	 *
	 * @param String tableName
	 * @return int retValue
	 * @deprecated Please use {@link IADTableDAO#retrieveTableId(String)}
	 */
	@Deprecated
	public static int getTable_ID(String tableName)
	{
		if (Check.isEmpty(tableName, true))
		{
			return -1;
		}

		// metas-ts: adding a unit testing mode, where a table id is returned without DB access.
		// Note: this method is called from every model interface generated by the model generator class.
		if (org.compiere.Adempiere.isUnitTestMode())
		{
			// ignoring case, because in DLM we also deal with all-lowercase table names, and it should not matter anyways
			// also note that we don't store the upper or lowercase tableName because what we put into the map is returned by the getTableName(int) method
			final Optional<Entry<String, Integer>> entry = staticTableIds.entrySet().stream()
					.filter(e -> e.getKey().equalsIgnoreCase(tableName))
					.findFirst();
			if (entry.isPresent())
			{
				return entry.get().getValue();
			}

			final int returnValue = ++nextTableId;
			setStaticTableId(tableName, returnValue);
			return returnValue;
		}
		// metas end

		Integer retValue = 0;
		if (s_cacheTableName2Id != null)
		{
			// Can happen to have s_cacheTableName2Id null when for example we load I_AD_Table interface
			retValue = s_cacheTableName2Id.get(tableName.toLowerCase());
		}
		if (retValue != null && retValue > 0)
		{
			return retValue;
		}

		final String SQL = "SELECT AD_Table_ID FROM AD_Table WHERE lower(TableName) = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(SQL, null);
			pstmt.setString(1, tableName.toLowerCase());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			s_log.error(SQL, e);
			retValue = -1;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (retValue != null && retValue > 0)
		{
			if (s_cacheTableName2Id != null)
			{
				s_cacheTableName2Id.put(tableName.toLowerCase(), retValue);
			}
			return retValue;
		}
		else
		{
			return -1;
		}
	}

	/**
	 * Create query to retrieve one or more PO.
	 *
	 * @param whereClause
	 * @param trxName
	 * @return Query
	 */
	public Query createQuery(String whereClause, String trxName)
	{
		return new Query(this.getCtx(), this, whereClause, trxName);
	}

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MTable[");
		sb.append(get_ID()).append("-").append(getTableName()).append("]");
		return sb.toString();
	}	// toString

	//
	// metas-ts
	private static final Map<String, Integer> staticTableIds = new HashMap<String, Integer>();
	private static int nextTableId = 0;
	private static final CCache<String, Integer> s_cacheTableName2Id = new CCache<String, Integer>(Table_Name + "#TableName2ID", 200, 0); // metas

	public static void setStaticTableId(final String name, final int id)
	{
		staticTableIds.put(name, id);
	}
	// metas end

}	// MTable

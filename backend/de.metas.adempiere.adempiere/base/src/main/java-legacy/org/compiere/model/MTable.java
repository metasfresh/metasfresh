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

import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.persistence.TableModelClassLoader;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.LegacyAdapters;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

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
	 * @deprecated Please use {@link IADTableDAO#retrieveTable(AdTableId)}
	 */
	@Deprecated
	public static MTable get(final Properties ignoredCtx, final int AD_Table_ID)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final I_AD_Table table = adTableDAO.retrieveTable(AD_Table_ID);
		return LegacyAdapters.convertToPO(table);
	}

	/**
	 * @deprecated Please use {@link IADTableDAO#retrieveTable(String)}
	 */
	@Deprecated
	public static MTable get(final Properties ignoredCtx, final String tableName)
	{
		if (tableName == null)
		{
			return null;
		}
		
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final I_AD_Table table = adTableDAO.retrieveTable(tableName);
		return LegacyAdapters.convertToPO(table);
	}

	/**
	 * @deprecated Please use {@link IADTableDAO#retrieveTableName(int)}
	 */
	@Deprecated
	@NonNull
	public static String getTableName(final Properties ignoredCtx, @NonNull final AdTableId adTableId)
	{
		return TableIdsCache.instance.getTableName(adTableId);
	}

	/**
	 * @deprecated Please use {@link IADTableDAO#retrieveTableId(String)}
	 */
	@Deprecated
	public static int getTable_ID(String tableName)
	{
		if (Check.isBlank(tableName))
		{
			return -1;
		}

		return TableIdsCache.instance.getTableId(tableName)
				.map(AdTableId::getRepoId)
				.orElse(-1);
	}

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
			List<MColumn> list = new ArrayList<>();
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
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final I_AD_Column column = adTableDAO.retrieveColumnOrNull(getTableName(), columnName);
		return LegacyAdapters.convertToPO(column);
	}	// getColumn

	/**
	 * Get Key Columns of Table
	 *
	 * @return key columns
	 */
	public String[] getKeyColumns()
	{
		final List<String> list = new ArrayList<>();
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
		return list.toArray(new String[list.size()]);
	}	// getKeyColumns

	/**************************************************************************
	 * Get PO Class Instance
	 *
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
	 * @return tableName's model class
	 * @deprecated Please use {@link TableModelClassLoader#getClass(String)}.
	 */
	@Deprecated
	public static Class<?> getClass(String tableName)
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
		{
			setIsDeleteable(false);
		}
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
			final ISequenceDAO sequenceDAO = Services.get(ISequenceDAO.class);
			sequenceDAO.createTableSequenceChecker(getCtx())
					.setFailOnFirstError(true)
					.setSequenceRangeCheck(false)
					.setTable(this)
					.setTrxName(get_TrxName())
					.run();
		}

		if (!newRecord && is_ValueChanged(COLUMNNAME_TableName))
		{
			final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
			adTableDAO.onTableNameRename(this);
		}

		return success;
	}	// afterSave

	public Query createQuery(String whereClause, String trxName)
	{
		return new Query(this.getCtx(), this, whereClause, trxName);
	}

	@Override
	public String toString()
	{
		return "MTable[" + get_ID() + "-" + getTableName() + "]";
	}
}

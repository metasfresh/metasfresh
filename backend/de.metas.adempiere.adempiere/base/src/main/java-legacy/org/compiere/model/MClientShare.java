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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Services;

/**
 * Client Share Info
 * 
 * @author Jorg Janke
 * @version $Id: MClientShare.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MClientShare extends X_AD_ClientShare
{
	private static final long serialVersionUID = -8104352403537353753L;

	private static final CCache<String, Boolean> s_shares = new CCache<>(Table_Name, 10, 120);	// 2h
	private static final transient Logger s_log = LogManager.getLogger(MClientShare.class);

	/**
	 * @return true if client level only (default false)
	 */
	public static boolean isClientLevelOnly(int AD_Client_ID, int AD_Table_ID)
	{
		Boolean share = isShared(AD_Client_ID, AD_Table_ID);
		if (share != null)
		{
			return share.booleanValue();
		}
		return false;
	}

	/**
	 * @return true if Org level only (default false)
	 */
	public static boolean isOrgLevelOnly(int AD_Client_ID, int AD_Table_ID)
	{
		Boolean share = isShared(AD_Client_ID, AD_Table_ID);
		if (share != null)
		{
			return !share.booleanValue();
		}
		return false;
	}

	/**
	 * Is Table Shared for Client
	 * 
	 * @param AD_Client_ID client
	 * @param AD_Table_ID table
	 * @return info or null
	 */
	private static Boolean isShared(int AD_Client_ID, int AD_Table_ID)
	{
		// Load
		if (s_shares.isEmpty())
		{
			String sql = "SELECT AD_Client_ID, AD_Table_ID, ShareType "
					+ "FROM AD_ClientShare WHERE ShareType<>'x' AND IsActive='Y'";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					int Client_ID = rs.getInt(1);
					int table_ID = rs.getInt(2);
					String key = Client_ID + "_" + table_ID;
					String ShareType = rs.getString(3);
					if (ShareType.equals(SHARETYPE_ClientAllShared))
					{
						s_shares.put(key, Boolean.TRUE);
					}
					else if (ShareType.equals(SHARETYPE_OrgNotShared))
					{
						s_shares.put(key, Boolean.FALSE);
					}
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

			if (s_shares.isEmpty())
			{
				s_shares.put("0_0", Boolean.TRUE);
			}
		}	// load

		String key = AD_Client_ID + "_" + AD_Table_ID;
		return s_shares.get(key);
	}	// load

	public MClientShare(Properties ctx, int AD_ClientShare_ID, String trxName)
	{
		super(ctx, AD_ClientShare_ID, trxName);
	}

	public MClientShare(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * @return true if client level only (shared)
	 */
	private boolean isClientLevelOnly()
	{
		return getShareType().equals(SHARETYPE_ClientAllShared);
	}

	/**
	 * Is Org Level Only
	 * 
	 * @return true if org level only (not shared)
	 */
	private boolean isOrgLevelOnly()
	{
		return getShareType().equals(SHARETYPE_OrgNotShared);
	}

	private String getTableName()
	{
		return Services.get(IADTableDAO.class).retrieveTableName(getAD_Table_ID());
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (isActive())
		{
			setDataToLevel();
			listChildRecords();
		}
		return true;
	}

	private String setDataToLevel()
	{
		String info = "-";
		if (isClientLevelOnly())
		{
			StringBuilder sql = new StringBuilder("UPDATE ")
					.append(getTableName())
					.append(" SET AD_Org_ID=0 WHERE AD_Org_ID<>0 AND AD_Client_ID=?");
			int no = DB.executeUpdateAndSaveErrorOnFail(sql.toString(), getAD_Client_ID(), get_TrxName());
			info = getTableName() + " set to Shared #" + no;
			log.info(info);
		}
		else if (isOrgLevelOnly())
		{
			StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ")
					.append(getTableName())
					.append(" WHERE AD_Org_ID=0 AND AD_Client_ID=?");
			int no = DB.getSQLValue(get_TrxName(), sql.toString(), getAD_Client_ID());
			info = getTableName() + " Shared records #" + no;
			log.info(info);
		}
		return info;
	}	// setDataToLevel

	private String listChildRecords()
	{
		final StringBuilder info = new StringBuilder();
		String sql = "SELECT AD_Table_ID, TableName "
				+ "FROM AD_Table t "
				+ "WHERE AccessLevel='3' AND IsView='N'"
				+ " AND EXISTS (SELECT * FROM AD_Column c "
				+ "WHERE t.AD_Table_ID=c.AD_Table_ID"
				+ " AND c.IsParent='Y'"
				+ " AND c.ColumnName IN (SELECT ColumnName FROM AD_Column cc "
				+ "WHERE cc.IsKey='Y' AND cc.AD_Table_ID=?))";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, getAD_Table_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String TableName = rs.getString(2);
				if (info.length() != 0)
				{
					info.append(", ");
				}
				info.append(TableName);
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		return info.toString();
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getAD_Org_ID() != 0)
		{
			setAD_Org_ID(0);
		}
		return true;
	}

}

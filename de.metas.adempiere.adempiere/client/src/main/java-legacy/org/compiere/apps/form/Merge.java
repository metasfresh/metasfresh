/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin *
 * Copyright (C) 2009 Idalica Corporation *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package org.compiere.apps.form;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.X_M_Cost;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class Merge
{
	private static final long serialVersionUID = 149783846292562740L;
	/** Window No */
	public int m_WindowNo = 0;
	/** Total Count */
	public int m_totalCount = 0;
	/** Error Log */
	public StringBuffer m_errorLog = new StringBuffer();
	/** Connection */
	// private Connection m_con = null;
	private Trx m_trx = null;
	/** Logger */
	public static Logger log = LogManager.getLogger(Merge.class);

	public static String AD_ORG_ID = "AD_Org_ID";
	public static String C_BPARTNER_ID = "C_BPartner_ID";
	public static String AD_USER_ID = "AD_User_ID";
	public static String M_PRODUCT_ID = "M_Product_ID";

	/** Tables to delete (not update) for AD_Org */
	public static String[] s_delete_Org = new String[] { "AD_OrgInfo", "AD_Role_OrgAccess" };
	/** Tables to delete (not update) for AD_User */
	public static String[] s_delete_User = new String[] { "AD_User_Roles" };
	/** Tables to delete (not update) for C_BPartner */
	public static String[] s_delete_BPartner = new String[] { "C_BP_Employee_Acct", "C_BP_Vendor_Acct", "C_BP_Customer_Acct",
			"T_Aging" };
	/** Tables to delete (not update) for M_Product */
	public static String[] s_delete_Product = new String[] { "M_Product_PO", "M_Replenish", "T_Replenish",
			"M_ProductPrice",
			"M_Cost",       // teo_sarca [ 1704554 ]
			"M_Product_Trl", "M_Product_Acct" };		// M_Storage

	public String[] m_columnName = null;
	public String[] m_deleteTables = null;

	public void updateDeleteTable(String columnName)
	{
		// ** Update **
		if (columnName.equals(AD_ORG_ID))
			m_deleteTables = s_delete_Org;
		else if (columnName.equals(AD_USER_ID))
			m_deleteTables = s_delete_User;
		else if (columnName.equals(C_BPARTNER_ID))
			m_deleteTables = s_delete_BPartner;
		else if (columnName.equals(M_PRODUCT_ID))
			m_deleteTables = s_delete_Product;
	}

	/**
	 * Merge.
	 *
	 * @param ColumnName column
	 * @param from_ID from
	 * @param to_ID to
	 * @return true if merged
	 */
	public boolean merge(String ColumnName, int from_ID, int to_ID)
	{
		String TableName = ColumnName.substring(0, ColumnName.length() - 3);
		log.info(ColumnName
				+ " - From=" + from_ID + ",To=" + to_ID);

		boolean success = true;
		m_totalCount = 0;
		m_errorLog = new StringBuffer();
		String sql = "SELECT t.TableName, c.ColumnName "
				+ "FROM AD_Table t"
				+ " INNER JOIN AD_Column c ON (t.AD_Table_ID=c.AD_Table_ID) "
				+ "WHERE t.IsView='N'"
				+ " AND t.TableName NOT IN ('C_TaxDeclarationAcct')"
				+ " AND ("
				+ "(c.ColumnName=? AND c.IsKey='N')"		// #1 - direct
				+ " OR "
				+ "c.AD_Reference_Value_ID IN "				// Table Reference
				+ "(SELECT rt.AD_Reference_ID FROM AD_Ref_Table rt"
				+ " INNER JOIN AD_Column cc ON (rt.AD_Table_ID=cc.AD_Table_ID AND rt.AD_Key=cc.AD_Column_ID) "
				+ "WHERE cc.IsKey='Y' AND cc.ColumnName=?)"	// #2
				+ ") AND c.ColumnSQL IS NULL"
				+ "ORDER BY t.LoadSeq DESC";
		PreparedStatement pstmt = null;

		try
		{

			m_trx = Trx.get(Trx.createTrxName("merge"), true);
			//
			pstmt = DB.prepareStatement(sql, Trx.createTrxName());
			pstmt.setString(1, ColumnName);
			pstmt.setString(2, ColumnName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				String tName = rs.getString(1);
				String cName = rs.getString(2);
				if (!TableName.equals(tName))      	// to be sure - sql should prevent it
				{
					int count = mergeTable(tName, cName, from_ID, to_ID);
					if (count < 0)
						success = false;
					else
						m_totalCount += count;
				}
			}
			rs.close();
			pstmt.close();
			pstmt = null;
			//
			log.info("Success=" + success
					+ " - " + ColumnName + " - From=" + from_ID + ",To=" + to_ID);
			if (success)
			{
				sql = "DELETE FROM " + TableName + " WHERE " + ColumnName + "=" + from_ID;

				if (DB.executeUpdate(sql, m_trx.getTrxName()) < 0)
				{
					m_errorLog.append(Env.NL).append("DELETE FROM ").append(TableName)
							.append(" - ");
					success = false;
					log.info(m_errorLog.toString());
					m_trx.rollback();
					return false;
				}

			}
			//
			if (success)
				m_trx.commit();
			else
				m_trx.rollback();

			m_trx.close();

		}
		catch (Exception ex)
		{
			log.error(ColumnName, ex);
		}
		// Cleanup
		try
		{
			if (pstmt != null)
				pstmt.close();

		}
		catch (Exception ex)
		{
		}
		pstmt = null;
		return success;
	}	// merge

	/**
	 * Merge Table
	 *
	 * @param TableName table
	 * @param ColumnName column
	 * @param from_ID from
	 * @param to_ID to
	 * @return -1 for error or number of changes
	 */
	public int mergeTable(String TableName, String ColumnName, int from_ID, int to_ID)
	{
		log.debug(TableName + "." + ColumnName + " - From=" + from_ID + ",To=" + to_ID);
		String sql = "UPDATE " + TableName
				+ " SET " + ColumnName + "=" + to_ID
				+ " WHERE " + ColumnName + "=" + from_ID;
		boolean delete = false;
		for (int i = 0; i < m_deleteTables.length; i++)
		{
			if (m_deleteTables[i].equals(TableName))
			{
				delete = true;
				sql = "DELETE FROM " + TableName + " WHERE " + ColumnName + "=" + from_ID;
			}
		}
		// Delete newly created MCost records - teo_sarca [ 1704554 ]
		if (delete && X_M_Cost.Table_Name.equals(TableName) && M_PRODUCT_ID.equals(ColumnName))
		{
			sql += " AND " + X_M_Cost.COLUMNNAME_CurrentCostPrice + "=0"
					+ " AND " + X_M_Cost.COLUMNNAME_CurrentQty + "=0"
					+ " AND " + X_M_Cost.COLUMNNAME_CumulatedAmt + "=0"
					+ " AND " + X_M_Cost.COLUMNNAME_CumulatedQty + "=0";
		}

		int count = DB.executeUpdate(sql, m_trx.getTrxName());

		if (count < 0)
		{

			count = -1;
			m_errorLog.append(Env.NL)
					.append(delete ? "DELETE FROM " : "UPDATE ")
					.append(TableName).append(" - ")
					.append(" - ").append(sql);
			log.info(m_errorLog.toString());
			m_trx.rollback();

		}
		log.debug(count
				+ (delete ? " -Delete- " : " -Update- ") + TableName);

		return count;
	}	// mergeTable

	/**
	 * Post Merge
	 *
	 * @param ColumnName column name
	 * @param to_ID ID
	 */
	public void postMerge(String ColumnName, int to_ID)
	{
		if (ColumnName.equals(AD_ORG_ID))
		{

		}
		else if (ColumnName.equals(AD_USER_ID))
		{

		}
		else if (ColumnName.equals(C_BPARTNER_ID))
		{
			// task FRESH-152 : Refactor

			final I_C_BPartner partner = InterfaceWrapperHelper.create(Env.getCtx(), to_ID, I_C_BPartner.class, ITrx.TRXNAME_None);

			if (partner != null)
			{
				MPayment[] payments = MPayment.getOfBPartner(Env.getCtx(), partner.getC_BPartner_ID(), null);
				for (int i = 0; i < payments.length; i++)
				{
					MPayment payment = payments[i];
					if (payment.testAllocation())
						payment.save();
				}
				MInvoice[] invoices = MInvoice.getOfBPartner(Env.getCtx(), partner.getC_BPartner_ID(), null);
				for (int i = 0; i < invoices.length; i++)
				{
					MInvoice invoice = invoices[i];
					if (invoice.testAllocation())
						invoice.save();
				}

				// task FRESH-152. Update bpartner stats
				Services.get(IBPartnerStatisticsUpdater.class)
						.updateBPartnerStatistics(Env.getCtx(), Collections.singleton(partner.getC_BPartner_ID()), ITrx.TRXNAME_None);
			}
		}
		else if (ColumnName.equals(M_PRODUCT_ID))
		{

		}
	}	// postMerge
}

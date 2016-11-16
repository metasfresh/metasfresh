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
package org.compiere.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.exceptions.DBException;
import org.compiere.model.MAging;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 *	Invoice Aging Report.
 *	Based on RV_Aging.
 *  @author Jorg Janke
 *  @author victor.perez@e-evolution.com  FR 1933937  Is necessary a new Aging to Date
 *  @see http://sourceforge.net/tracker/index.php?func=detail&aid=1933937&group_id=176962&atid=879335 
 *  @author Carlos Ruiz - globalqss  BF 2655587  Multi-org not supported in Aging
 *  @see https://sourceforge.net/tracker2/?func=detail&aid=2655587&group_id=176962&atid=879332 
 *  @version $Id: Aging.java,v 1.5 2006/10/07 00:58:44 jjanke Exp $
 */
public class Aging extends SvrProcess
{
	/** The date to calculate the days due from			*/
	private Timestamp	p_StatementDate = null;
	//FR 1933937
	private boolean		p_DateAcct = false;
	private boolean 	p_IsSOTrx = false;
	private int			p_C_Currency_ID = 0;
	private int			p_AD_Org_ID = 0;
	private int			p_C_BP_Group_ID = 0;
	private int			p_C_BPartner_ID = 0;
	private boolean		p_IsListInvoices = false;
	/** Number of days between today and statement date	*/
	private int			m_statementOffset = 0;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("StatementDate"))
				p_StatementDate = (Timestamp)para[i].getParameter();
			else if (name.equals("DateAcct"))
				p_DateAcct = "Y".equals(para[i].getParameter());
			else if (name.equals("IsSOTrx"))
				p_IsSOTrx = "Y".equals(para[i].getParameter());
			else if (name.equals("C_Currency_ID"))
				p_C_Currency_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_BP_Group_ID"))
				p_C_BP_Group_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("IsListInvoices"))
				p_IsListInvoices = "Y".equals(para[i].getParameter());
			else
				log.error("Unknown Parameter: " + name);
		}
		if (p_StatementDate == null)
			p_StatementDate = new Timestamp (System.currentTimeMillis());
		else
			m_statementOffset = TimeUtil.getDaysBetween( 
				new Timestamp(System.currentTimeMillis()), p_StatementDate);
	}	//	prepare

	/**
	 * 	DoIt
	 *	@return Message
	 *	@throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("StatementDate=" + p_StatementDate + ", IsSOTrx=" + p_IsSOTrx
			+ ", C_Currency_ID=" + p_C_Currency_ID + ", AD_Org_ID=" + p_AD_Org_ID
			+ ", C_BP_Group_ID=" + p_C_BP_Group_ID + ", C_BPartner_ID=" + p_C_BPartner_ID
			+ ", IsListInvoices=" + p_IsListInvoices);
		//FR 1933937
		String dateacct = DB.TO_DATE(p_StatementDate);  
		 
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT bp.C_BP_Group_ID, oi.C_BPartner_ID,oi.C_Invoice_ID,oi.C_InvoicePaySchedule_ID, "  // 1..4 
			+ "oi.C_Currency_ID, oi.IsSOTrx, "								//	5..6
			+ "oi.DateInvoiced, oi.NetDays,oi.DueDate,oi.DaysDue, ");		//	7..10
		if (p_C_Currency_ID == 0)
		{
			if (!p_DateAcct)//FR 1933937
			{
				sql.append(" oi.GrandTotal, oi.PaidAmt, oi.OpenAmt ");			//	11..13
			}
			else
			{
				sql.append(" oi.GrandTotal, invoicePaidToDate(oi.C_Invoice_ID, oi.C_Currency_ID, 1,"+dateacct+") AS PaidAmt, invoiceOpenToDate(oi.C_Invoice_ID,oi.C_InvoicePaySchedule_ID,"+dateacct+") AS OpenAmt ");			//	11..13
			}
		}
		else
		{
			String s = ",oi.C_Currency_ID," + p_C_Currency_ID + ",oi.DateAcct,oi.C_ConversionType_ID,oi.AD_Client_ID,oi.AD_Org_ID)";
			sql.append("currencyConvert(oi.GrandTotal").append(s);		//	11
			if (!p_DateAcct)
			{
				sql.append(", currencyConvert(oi.PaidAmt").append(s)  // 12
				.append(", currencyConvert(oi.OpenAmt").append(s);  // 13
			}
			else
			{
				sql.append(", currencyConvert(invoicePaidToDate(oi.C_Invoice_ID, oi.C_Currency_ID, 1,"+dateacct+")").append(s) // 12
				.append(", currencyConvert(invoiceOpenToDate(oi.C_Invoice_ID,oi.C_InvoicePaySchedule_ID,"+dateacct+")").append(s);  // 13
			}
		}
		sql.append(",oi.C_Activity_ID,oi.C_Campaign_ID,oi.C_Project_ID,oi.AD_Org_ID ");	//	14..17
		if (!p_DateAcct)//FR 1933937
		{
			sql.append(" FROM RV_OpenItem oi");
		}
		else
		{
			sql.append(" FROM RV_OpenItemToDate oi");
		}
		
		sql.append(" INNER JOIN C_BPartner bp ON (oi.C_BPartner_ID=bp.C_BPartner_ID) "
			+ "WHERE oi.ISSoTrx=").append(p_IsSOTrx ? "'Y'" : "'N'");
		if (p_C_BPartner_ID > 0)
		{
			sql.append(" AND oi.C_BPartner_ID=").append(p_C_BPartner_ID);
		}
		else if (p_C_BP_Group_ID > 0)
		{
			sql.append(" AND bp.C_BP_Group_ID=").append(p_C_BP_Group_ID);
		}
		if (p_AD_Org_ID > 0) // BF 2655587
		{
			sql.append(" AND oi.AD_Org_ID=").append(p_AD_Org_ID);
		}
		
		if (p_DateAcct)//FR 1933937
		{
			sql.append(" AND invoiceOpenToDate(oi.C_Invoice_ID,oi.C_InvoicePaySchedule_ID,"+dateacct+") <> 0 ");
		}
		
		sql.append(" ORDER BY oi.C_BPartner_ID, oi.C_Currency_ID, oi.C_Invoice_ID");
		
		log.trace(sql.toString());
		String finalSql = Env.getUserRolePermissions(getCtx()).addAccessSQL(
			sql.toString(), "oi", IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);	
		log.trace(finalSql);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//
		MAging aging = null;
		int counter = 0;
		int rows = 0;
		int AD_PInstance_ID = getAD_PInstance_ID();
		//
		try
		{
			pstmt = DB.prepareStatement(finalSql, get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int C_BP_Group_ID = rs.getInt(1);
				int C_BPartner_ID = rs.getInt(2);
				int C_Invoice_ID = p_IsListInvoices ? rs.getInt(3) : 0;
				int C_InvoicePaySchedule_ID = p_IsListInvoices ? rs.getInt(4) : 0;
				int C_Currency_ID = rs.getInt(5);
				boolean IsSOTrx = "Y".equals(rs.getString(6));
				//
				Timestamp DateInvoiced = rs.getTimestamp(7);
				int NetDays = rs.getInt(8);
				Timestamp DueDate = rs.getTimestamp(9);
				//	Days Due
				int DaysDue = rs.getInt(10)		//	based on today
					+ m_statementOffset;
				//
				BigDecimal GrandTotal = rs.getBigDecimal(11);
				BigDecimal PaidAmt = rs.getBigDecimal(12);
				BigDecimal OpenAmt = rs.getBigDecimal(13);
				//
				int C_Activity_ID = p_IsListInvoices ? rs.getInt(14) : 0;
				int C_Campaign_ID = p_IsListInvoices ? rs.getInt(15) : 0;
				int C_Project_ID = p_IsListInvoices ? rs.getInt(16) : 0;
				int AD_Org_ID = rs.getInt(17);
				
				rows++;
				//	New Aging Row
				if (aging == null 		//	Key
					|| AD_PInstance_ID != aging.getAD_PInstance_ID()
					|| C_BPartner_ID != aging.getC_BPartner_ID()
					|| C_Currency_ID != aging.getC_Currency_ID()
					|| C_Invoice_ID != aging.getC_Invoice_ID()
					|| C_InvoicePaySchedule_ID != aging.getC_InvoicePaySchedule_ID())
				{
					if (aging != null)
					{
						aging.saveEx();
						log.debug("#" + ++counter + " - " + aging);
					}
					aging = new MAging (getCtx(), AD_PInstance_ID, p_StatementDate, 
						C_BPartner_ID, C_Currency_ID, 
						C_Invoice_ID, C_InvoicePaySchedule_ID, 
						C_BP_Group_ID, AD_Org_ID, DueDate, IsSOTrx, get_TrxName());
					aging.setC_Activity_ID(C_Activity_ID);
					aging.setC_Campaign_ID(C_Campaign_ID);
					aging.setC_Project_ID(C_Project_ID);
					aging.setDateAcct(p_DateAcct);
				}
				//	Fill Buckets
				aging.add (DueDate, DaysDue, GrandTotal, OpenAmt);
			}
			if (aging != null)
			{
				aging.saveEx();
				counter++;
				log.debug("#" + counter + " - " + aging);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, finalSql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//	
		log.info("#" + counter + " - rows=" + rows);
		return "";
	}	//	doIt

}	//	Aging

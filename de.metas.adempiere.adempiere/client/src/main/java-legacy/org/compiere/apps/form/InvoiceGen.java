/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.apps.form;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.apps.IStatusBar;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.I_C_Order;
import org.compiere.model.MOrder;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MPrivateAccess;
import org.compiere.model.MRMA;
import org.compiere.print.ReportEngine;
import org.compiere.process.ProcessInfo;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

/**
 * Generate Invoice (manual) controller class
 * 
 */
public class InvoiceGen extends GenForm
{
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(InvoiceGen.class);
	//
	
	public Object 			m_AD_Org_ID = null;
	public Object 			m_C_BPartner_ID = null;
	
	@Override
	public void dynInit() throws Exception
	{
		setTitle("InvGenerateInfo");
		setReportEngineType(ReportEngine.INVOICE);
		setAskPrintMsg("PrintInvoices");
	}
	
	@Override
	public void configureMiniTable(IMiniTable miniTable)
	{
		//  create Columns
		miniTable.addColumn("C_Order_ID");
		miniTable.addColumn("AD_Org_ID");
		miniTable.addColumn("C_DocType_ID");
		miniTable.addColumn("DocumentNo");
		miniTable.addColumn("C_BPartner_ID");
		miniTable.addColumn("DateOrdered");
		miniTable.addColumn("TotalLines");
		//
		miniTable.setMultiSelection(true);
		//  set details
		miniTable.setColumnClass(0, IDColumn.class, false, " ");
		miniTable.setColumnClass(1, String.class, true, Msg.translate(Env.getCtx(), "AD_Org_ID"));
		miniTable.setColumnClass(2, String.class, true, Msg.translate(Env.getCtx(), "C_DocType_ID"));
		miniTable.setColumnClass(3, String.class, true, Msg.translate(Env.getCtx(), "DocumentNo"));
		miniTable.setColumnClass(4, String.class, true, Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		miniTable.setColumnClass(5, Timestamp.class, true, Msg.translate(Env.getCtx(), "DateOrdered"));
		miniTable.setColumnClass(6, BigDecimal.class, true, Msg.translate(Env.getCtx(), "TotalLines"));
		//
		miniTable.autoSize();
	}
	
	/**
	 * Get SQL for Orders that needs to be shipped
	 * @return sql
	 */
	private String getOrderSQL()
	{
	// metas: fully changed
		final StringBuilder sql = new StringBuilder(
				"SELECT ic.C_Order_ID, o.Name, dt.Name, ic.DocumentNo, bp.Name, ic.DateOrdered, ic.TotalLines \n"
						+ "FROM C_Order, "  // adding C_Order to the SQL as the first table in order to make MRolle.addAccessSql work
						+ "  C_Invoice_Candidate_v ic, AD_Org o, C_BPartner bp, C_DocType dt \n" //
						+ "WHERE ic.AD_Org_ID=o.AD_Org_ID\n"
						+ " AND ic.C_BPartner_ID=bp.C_BPartner_ID\n"
						+ " AND ic.C_DocType_ID=dt.C_DocType_ID\n"
						+ " AND ic.C_Order_ID=C_Order.C_Order_ID\n"
						+ " AND ic.AD_Client_ID=?\n"
						// looking for an order line whose QtyOrdered is not fully covered
						// by the lines of invoices with status "in progress", "completed" or "closed".
						// Note: ol.QtyInvoiced can't be used, because it ignores invoices that are in progress
						+ " AND EXISTS (\n"
						+ "      select 1\n"
						+ "      from C_OrderLine ol\n"
						+ "      where true\n " 
						+ "        AND ol.C_Order_ID=ic.C_Order_ID\n" 
						// metas: QtyOrdered *OR* QtyDelivered can be greater than QtyInvoiced
						+ "        AND CASE WHEN ol.QtyOrdered>=ol.QtyDelivered THEN ol.QtyOrdered ELSE ol.QtyDelivered END >COALESCE((\n"
						// metas end
						+ "      	 select\n"
						+ "       		 sum(il.QtyInvoiced)\n"
						+ "       	 from\n"
						+ "       		C_InvoiceLine il\n"
						+ "      		JOIN C_Invoice i ON i.C_Invoice_ID=il.C_Invoice_ID\n"
						+ "      	 where\n"
						+ "      		il.C_OrderLine_ID=ol.C_OrderLine_ID\n"
						+ "      		AND i.DocStatus IN ('IP','CO','CL')\n"
						+ "      ),0)\n"
						+ " )");

		if (m_AD_Org_ID != null)
			sql.append(" AND ic.AD_Org_ID=").append(m_AD_Org_ID);
		if (m_C_BPartner_ID != null)
			sql.append(" AND ic.C_BPartner_ID=").append(m_C_BPartner_ID);

		sql.append(" ORDER BY o.Name,bp.Name,DateOrdered");
		
		//
		// Appending role access sql
		// Note: this obsoletes the former solution to '[ 1713337 ] "Generate Invoices (manual)" show locked records.'
		final IUserRolePermissions role = Env.getUserRolePermissions();
		return role.addAccessSQL(sql.toString(), I_C_Order.Table_Name, true, false);
	}
	
	/**
	 * Get SQL for Customer RMA that need to be invoiced
	 * @return sql
	 */
	private String getRMASql()
	{
		StringBuffer sql = new StringBuffer();
	    sql.append("SELECT rma.M_RMA_ID, org.Name, dt.Name, rma.DocumentNo, bp.Name, rma.Created, rma.Amt ");
        sql.append("FROM M_RMA rma INNER JOIN AD_Org org ON rma.AD_Org_ID=org.AD_Org_ID ");
        sql.append("INNER JOIN C_DocType dt ON rma.C_DocType_ID=dt.C_DocType_ID ");
        sql.append("INNER JOIN C_BPartner bp ON rma.C_BPartner_ID=bp.C_BPartner_ID ");
        sql.append("INNER JOIN M_InOut io ON rma.InOut_ID=io.M_InOut_ID ");
        sql.append("WHERE rma.DocStatus='CO' ");
        sql.append("AND dt.DocBaseType = 'SOO' ");
        // sql.append("AND NOT EXISTS (SELECT * FROM C_Invoice i ");
        // sql.append("WHERE i.M_RMA_ID=rma.M_RMA_ID AND i.DocStatus IN ('IP', 'CO', 'CL')) ");
        // sql.append("AND EXISTS (SELECT * FROM C_InvoiceLine il INNER JOIN M_InOutLine iol ");
        // sql.append("ON il.M_InOutLine_ID=iol.M_InOutLine_ID INNER JOIN C_Invoice i ");
        // sql.append("ON i.C_Invoice_ID=il.C_Invoice_ID WHERE i.DocStatus IN ('CO', 'CL') ");
        // sql.append("AND iol.M_InOutLine_ID IN ");
        // sql.append("(SELECT M_InOutLine_ID FROM M_RMALine rl WHERE rl.M_RMA_ID=rma.M_RMA_ID ");
        // sql.append("AND rl.M_InOutLine_ID IS NOT NULL)) ");
        sql.append("AND rma.AD_Client_ID=?");
        
        if (m_AD_Org_ID != null)
            sql.append(" AND rma.AD_Org_ID=").append(m_AD_Org_ID);
        if (m_C_BPartner_ID != null)
            sql.append(" AND bp.C_BPartner_ID=").append(m_C_BPartner_ID);
        
        int AD_User_ID = Env.getContextAsInt(Env.getCtx(), "#AD_User_ID");
        String lockedIDs = MPrivateAccess.getLockedRecordWhere(MRMA.Table_ID, AD_User_ID);
        if (lockedIDs != null)
        {
            sql.append(" AND rma.M_RMA_ID").append(lockedIDs);
        }
        
        sql.append(" ORDER BY org.Name, bp.Name, rma.Created ");
        
        return sql.toString();
	}
	
	/**
	 *  Query Info
	 */
	public void executeQuery(KeyNamePair docTypeKNPair, IMiniTable miniTable)
	{
		log.info("");
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		//  Create SQL
		
		String sql = "";
        
        if (docTypeKNPair.getKey() == MOrder.Table_ID)
        {
            sql = getOrderSQL();
        }
        else
        {
            sql = getRMASql();
        }

		//  reset table
		int row = 0;
		miniTable.setRowCount(row);
		//  Execute
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, AD_Client_ID);
			ResultSet rs = pstmt.executeQuery();
			//
			while (rs.next())
			{
				//  extend table
				miniTable.setRowCount(row+1);
				//  set values
				miniTable.setValueAt(new IDColumn(rs.getInt(1)), row, 0);   //  C_Order_ID
				miniTable.setValueAt(rs.getString(2), row, 1);              //  Org
				miniTable.setValueAt(rs.getString(3), row, 2);              //  DocType
				miniTable.setValueAt(rs.getString(4), row, 3);              //  Doc No
				miniTable.setValueAt(rs.getString(5), row, 4);              //  BPartner
				miniTable.setValueAt(rs.getTimestamp(6), row, 5);           //  DateOrdered
				miniTable.setValueAt(rs.getBigDecimal(7), row, 6);          //  TotalLines
				//  prepare next
				row++;
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql.toString(), e);
		}
		//
		miniTable.autoSize();
	//	statusBar.setStatusDB(String.valueOf(miniTable.getRowCount()));
	}   //  executeQuery
	
	/**
	 *	Save Selection & return selecion Query or ""
	 *  @return where clause like C_Order_ID IN (...)
	 */
	@Override
	public void saveSelection(IMiniTable miniTable)
	{
		log.info("");
		//  Array of Integers
		ArrayList<Integer> results = new ArrayList<Integer>();
		setSelection(null);

		//	Get selected entries
		int rows = miniTable.getRowCount();
		for (int i = 0; i < rows; i++)
		{
			IDColumn id = (IDColumn)miniTable.getValueAt(i, 0);     //  ID in column 0
		//	log.debug( "Row=" + i + " - " + id);
			if (id != null && id.isSelected())
				results.add(id.getRecord_ID());
		}

		if (results.size() == 0)
			return;
		log.info("Selected #" + results.size());
		setSelection(results);
	}	//	saveSelection

	
	/**************************************************************************
	 *	Generate Invoices
	 */
	public String generate(IStatusBar statusBar, KeyNamePair docTypeKNPair, String docActionSelected)
	{
		String info = "";
		String trxName = Trx.createTrxName("IVG");
		Trx trx = Trx.get(trxName, true);	//trx needs to be committed too
		
		setSelectionActive(false);  //  prevents from being called twice
		statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), "InvGenerateGen"));
		statusBar.setStatusDB(String.valueOf(getSelection().size()));

		//	Prepare Process
		int AD_Process_ID = 0;
        
        if (docTypeKNPair.getKey() == MRMA.Table_ID)
        {
            AD_Process_ID = 52002; // C_Invoice_GenerateRMA - org.adempiere.process.InvoiceGenerateRMA
        }
        else
        {
            AD_Process_ID = 134;  // HARDCODED    C_InvoiceCreate
        }
		MPInstance instance = new MPInstance(Env.getCtx(), AD_Process_ID, 0, 0);
		if (!instance.save())
		{
			info = Msg.getMsg(Env.getCtx(), "ProcessNoInstance");
			return info;
		}
		
		//insert selection
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO T_SELECTION(AD_PINSTANCE_ID, T_SELECTION_ID) ");
		int counter = 0;
		for(Integer selectedId : getSelection())
		{
			counter++;
			if (counter > 1)
				insert.append(" UNION ");
			insert.append("SELECT ");
			insert.append(instance.getAD_PInstance_ID());
			insert.append(", ");
			insert.append(selectedId);
			insert.append(" FROM DUAL ");
			
			if (counter == 1000) 
			{
				if ( DB.executeUpdate(insert.toString(), trxName) < 0 )
				{
					String msg = "No Invoices";     //  not translated!
					info = msg;
					log.info(msg);
					trx.rollback();
					return info;
				}
				insert = new StringBuffer();
				insert.append("INSERT INTO T_SELECTION(AD_PINSTANCE_ID, T_SELECTION_ID) ");
				counter = 0;
			}
		}
		if (counter > 0)
		{
			if ( DB.executeUpdate(insert.toString(), trxName) < 0 )
			{
				String msg = "No Invoices";     //  not translated!
				info = msg;
				log.info(msg);
				trx.rollback();
				return info;
			}
		}
		
		ProcessInfo pi = new ProcessInfo ("", AD_Process_ID);
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());

		//	Add Parameters
		MPInstancePara para = new MPInstancePara(instance, 10);
		para.setParameter("Selection", "Y");
		if (!para.save())
		{
			String msg = "No Selection Parameter added";  //  not translated
			info = msg;
			log.error(msg);
			return info;
		}
		
		para = new MPInstancePara(instance, 20);
		para.setParameter("DocAction", docActionSelected);
		
		if (!para.save())
		{
			String msg = "No DocAction Parameter added";  //  not translated
			info = msg;
			log.error(msg);
			return info;
		}
		
		setTrx(trx);
		setProcessInfo(pi);
		
		return info;
	}	//	generateInvoices
}

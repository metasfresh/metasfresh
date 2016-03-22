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

import org.compiere.apps.IStatusBar;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
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
 * Generate Shipment (manual) controller class
 * 
 */
public class InOutGen extends GenForm
{
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(InOutGen.class);
	//
	
	public Object 			m_M_Warehouse_ID = null;
	public Object 			m_C_BPartner_ID = null;
	
	public void dynInit() throws Exception
	{
		setTitle("InOutGenerateInfo");
		setReportEngineType(ReportEngine.SHIPMENT);
		setAskPrintMsg("PrintShipments");
	}
	
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
	//  Create SQL
        StringBuffer sql = new StringBuffer(
            "SELECT C_Order_ID, o.Name, dt.Name, DocumentNo, bp.Name, DateOrdered, TotalLines "
            + "FROM M_InOut_Candidate_v ic, AD_Org o, C_BPartner bp, C_DocType dt "
            + "WHERE ic.AD_Org_ID=o.AD_Org_ID"
            + " AND ic.C_BPartner_ID=bp.C_BPartner_ID"
            + " AND ic.C_DocType_ID=dt.C_DocType_ID"
            + " AND ic.AD_Client_ID=?");

        if (m_M_Warehouse_ID != null)
            sql.append(" AND ic.M_Warehouse_ID=").append(m_M_Warehouse_ID);
        if (m_C_BPartner_ID != null)
            sql.append(" AND ic.C_BPartner_ID=").append(m_C_BPartner_ID);
        
        // bug - [ 1713317 ] Generate Shipments (manual) show locked records
        /* begin - Exclude locked records; @Trifon */
        int AD_User_ID = Env.getContextAsInt(Env.getCtx(), "#AD_User_ID");
        String lockedIDs = MPrivateAccess.getLockedRecordWhere(MOrder.Table_ID, AD_User_ID);
        if (lockedIDs != null)
        {
            if (sql.length() > 0)
                sql.append(" AND ");
            sql.append("C_Order_ID").append(lockedIDs);
        }
        /* eng - Exclude locked records; @Trifon */
          
        //
        sql.append(" ORDER BY o.Name,bp.Name,DateOrdered");
        
        return sql.toString();
	}
	
	/**
	 * Get SQL for Vendor RMA that need to be shipped
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
	    sql.append("AND dt.DocBaseType = 'POO' ");
	    sql.append("AND EXISTS (SELECT * FROM M_RMA r INNER JOIN M_RMALine rl ");
	    sql.append("ON r.M_RMA_ID=rl.M_RMA_ID WHERE r.M_RMA_ID=rma.M_RMA_ID ");
	    sql.append("AND rl.IsActive='Y' AND rl.M_InOutLine_ID > 0 AND rl.QtyDelivered < rl.Qty) ");
	    sql.append("AND NOT EXISTS (SELECT * FROM M_InOut oio WHERE oio.M_RMA_ID=rma.M_RMA_ID ");
	    sql.append("AND oio.DocStatus IN ('IP', 'CO', 'CL')) " );
	    sql.append("AND rma.AD_Client_ID=?");
	    
	    if (m_M_Warehouse_ID != null)
            sql.append(" AND io.M_Warehouse_ID=").append(m_M_Warehouse_ID);
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
		
		String sql = "";
		
		if (docTypeKNPair.getKey() == MRMA.Table_ID)
		{
		    sql = getRMASql();
		}
		else
		{
		    sql = getOrderSQL();
		}

		log.debug(sql);
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
	 *	Save Selection & return selection Query or ""
	 *  @return where clause like C_Order_ID IN (...)
	 */
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
	 *	Generate Shipments
	 */
	public String generate(IStatusBar statusBar, KeyNamePair docTypeKNPair, String docActionSelected)
	{
		String info = "";
		log.info("M_Warehouse_ID=" + m_M_Warehouse_ID);
		String trxName = Trx.createTrxName("IOG");	
		Trx trx = Trx.get(trxName, true);	//trx needs to be committed too
		
		setSelectionActive(false);  //  prevents from being called twice
		statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), "InOutGenerateGen"));
		statusBar.setStatusDB(String.valueOf(getSelection().size()));

		//	Prepare Process
		int AD_Process_ID = 0;	  
        
        if (docTypeKNPair.getKey() == MRMA.Table_ID)
        {
            AD_Process_ID = 52001; // M_InOut_GenerateRMA - org.adempiere.process.InOutGenerateRMA
        }
        else
        {
            AD_Process_ID = 199;      // M_InOut_Generate - org.compiere.process.InOutGenerate
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
					String msg = "No Shipments";     //  not translated!
					log.info(msg);
					info = msg;
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
				String msg = "No Shipments";     //  not translated!
				log.info(msg);
				info = msg;
				trx.rollback();
				return info;
			}
		}
		
		//call process
		ProcessInfo pi = new ProcessInfo ("VInOutGen", AD_Process_ID);
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());

		//	Add Parameter - Selection=Y
		MPInstancePara ip = new MPInstancePara(instance, 10);
		ip.setParameter("Selection","Y");
		if (!ip.save())
		{
			String msg = "No Parameter added";  //  not translated
			info = msg;
			log.error(msg);
			return info;
		}
		//Add Document action parameter
		ip = new MPInstancePara(instance, 20);
//		String docActionSelected = (String)docAction.getValue();
		ip.setParameter("DocAction", docActionSelected);
		if(!ip.save())
		{
			String msg = "No DocACtion Parameter added";
			info = msg;
			log.error(msg);
			return info;
		}
		//	Add Parameter - M_Warehouse_ID=x
		ip = new MPInstancePara(instance, 30);
		ip.setParameter("M_Warehouse_ID", Integer.parseInt(m_M_Warehouse_ID.toString()));
		if(!ip.save())
		{
			String msg = "No Parameter added";  //  not translated
			info = msg;
			log.error(msg);
			return info;
		}
		
		setTrx(trx);
		setProcessInfo(pi);
		
		return info;
	}	//	generateShipments
	
	public void setM_Warehouse_ID(Object value)
	{
		this.m_M_Warehouse_ID = value;
	}
	
	public int getM_Warehouse_ID()
	{
		if (m_M_Warehouse_ID == null)
			return -1;
		return ((Integer)m_M_Warehouse_ID);
	}
}

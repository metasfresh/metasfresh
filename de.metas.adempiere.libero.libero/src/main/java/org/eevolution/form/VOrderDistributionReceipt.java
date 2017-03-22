/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): victor.perez@e-evolution.com			                          *
 *****************************************************************************/
package org.eevolution.form;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.ADialog;
import org.compiere.apps.ADialogDialog;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.PrintInfo;
import org.compiere.plaf.CompiereColor;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.print.Viewer;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTabbedPane;
import org.compiere.swing.CTextPane;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.MDDOrder;
import org.eevolution.model.MDDOrderLine;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 *	Create Movement for Material Receipt from Distribution Order
 *
 *  @author victor.perez@www.e-evolution.com 
 *  @version $Id: VOrderDistributionReceipt,v 1.0 
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class VOrderDistributionReceipt extends CPanel
	implements FormPanel, ActionListener, VetoableChangeListener, 
		ChangeListener, TableModelListener
{
	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame frame
	 */
	@Override
	public void init (int WindowNo, FormFrame frame)
	{
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", "Y");
		try
		{
			fillPicks();
			jbInit();
			dynInit();
			frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
			frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		}
		catch(Exception ex)
		{
			log.error("init", ex);
		}
	}	//	init

	/**	Window No			*/
	private int         	m_WindowNo = 0;
	/**	FormFrame			*/
	private FormFrame 		m_frame;

	private boolean			m_selectionActive = true;
	private Object 			m_DD_Order_ID = null;
	private Object 			m_MovementDate = null;

	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VOrderDistributionReceipt.class);
	//
	private CTabbedPane tabbedPane = new CTabbedPane();
	private CPanel selPanel = new CPanel();
	private CPanel selNorthPanel = new CPanel();
	private BorderLayout selPanelLayout = new BorderLayout();
	private CLabel lOrder = new CLabel();
	private VLookup fOrder;
	private VDate fMovementDate = new VDate("MovementDate", true, false, true, DisplayType.Date, "MovementDate");
	private CLabel lMovementDate = new CLabel(Msg.translate(Env.getCtx(),"MovementDate"));
	private FlowLayout northPanelLayout = new FlowLayout();
	private ConfirmPanel confirmPanelSel = ConfirmPanel.newWithOKAndCancel();
	private ConfirmPanel confirmPanelGen = ConfirmPanel.builder().withRefreshButton(true).build();
	private StatusBar statusBar = new StatusBar();
	private CPanel genPanel = new CPanel();
	private BorderLayout genLayout = new BorderLayout();
	private CTextPane info = new CTextPane();
	private JScrollPane scrollPane = new JScrollPane();
	private MiniTable miniTable = new MiniTable();

	/** User selection */
	private ArrayList<Integer> selection = null;
	
	/**
	 *	Static Init.
	 *  <pre>
	 *  selPanel (tabbed)
	 *      fOrg, fBPartner
	 *      scrollPane & miniTable
	 *  genPanel
	 *      info
	 *  </pre>
	 *  @throws Exception
	 */
	void jbInit() throws Exception
	{
		CompiereColor.setBackground(this);
		//
		selPanel.setLayout(selPanelLayout);
		lOrder.setLabelFor(fOrder);

		selNorthPanel.setLayout(northPanelLayout);
		northPanelLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.add(selPanel, Msg.getMsg(Env.getCtx(), "Select"));
		selPanel.add(selNorthPanel, BorderLayout.NORTH);
		selNorthPanel.add(lOrder, null);
		selNorthPanel.add(fOrder, null);
		selNorthPanel.add(lMovementDate, null);
		selNorthPanel.add(fMovementDate, null);
		selPanel.setName("selPanel");
		selPanel.add(confirmPanelSel, BorderLayout.SOUTH);
		selPanel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getViewport().add(miniTable, null);
		confirmPanelSel.setActionListener(this);
		//
		tabbedPane.add(genPanel, Msg.getMsg(Env.getCtx(), "Generate"));
		genPanel.setLayout(genLayout);
		genPanel.add(info, BorderLayout.CENTER);
		genPanel.setEnabled(false);
		info.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		info.setEditable(false);
		genPanel.add(confirmPanelGen, BorderLayout.SOUTH);
		confirmPanelGen.setActionListener(this);
	}	//	jbInit

	/**
	 *	Fill Picks.
	 *		Column_ID from C_Order
	 *  @throws Exception if Lookups cannot be initialized
	 */
	private void fillPicks() throws Exception
	{
		MLookup orderL = 	MLookupFactory.get(Env.getCtx(), m_WindowNo, MColumn.getColumn_ID(MDDOrder.Table_Name,MDDOrder.COLUMNNAME_DD_Order_ID) , DisplayType.Search , MDDOrder.COLUMNNAME_DD_Order_ID , 0 , false, "DocStatus='CO'");
		fOrder = new VLookup (MDDOrder.COLUMNNAME_DD_Order_ID, true, false, true, orderL);
		lOrder.setText(Msg.translate(Env.getCtx(), MDDOrder.COLUMNNAME_DD_Order_ID));
		fOrder.addVetoableChangeListener(this);
		Timestamp today = new Timestamp (System.currentTimeMillis());
		m_MovementDate = today;
		fMovementDate.setValue(today);
		fMovementDate.addVetoableChangeListener(this);
	}	//	fillPicks

	/**
	 *	Dynamic Init.
	 *	- Create GridController & Panel
	 *	- AD_Column_ID from C_Order
	 */
	private void dynInit()
	{
		//  create Columns
		miniTable.addColumn("DD_Order_ID");
		miniTable.addColumn("QtyInTransit");
		miniTable.addColumn("C_UOM_ID");
		miniTable.addColumn("Value");
		miniTable.addColumn("M_Product_ID");
		miniTable.addColumn("M_WarehouseSource_ID");
		//miniTable.addColumn("TotalLines");
		//
		miniTable.setMultiSelection(true);
		miniTable.setRowSelectionAllowed(true);
		//  set details
		miniTable.setColumnClass(0, IDColumn.class, false, " ");
		miniTable.setColumnClass(1, BigDecimal.class, false, Msg.translate(Env.getCtx(), "QtyInTransit")); //Qty
		miniTable.setColumnClass(2, String.class, true, Msg.translate(Env.getCtx(), "C_UOM_ID")); //UM 
		miniTable.setColumnClass(3, String.class, true, Msg.translate(Env.getCtx(), "M_Product_ID")); // Product 
		miniTable.setColumnClass(4, String.class, true, Msg.translate(Env.getCtx(), "Value")); // Line
		miniTable.setColumnClass(5, String.class, true, Msg.translate(Env.getCtx(), "WarehouseSource"));
		//miniTable.setColumnClass(6, BigDecimal.class, true, Msg.translate(Env.getCtx(), "TotalLines"));
		//
		miniTable.autoSize();
		miniTable.getModel().addTableModelListener(this);
		//	Info
		statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), "InOutGenerateSel"));//@@
		statusBar.setStatusDB(" ");
		//	Tabbed Pane Listener
		tabbedPane.addChangeListener(this);
	}	//	dynInit

	/**
	 * Get SQL for Orders that needs to be shipped
	 * @return sql
	 */
	private String getOrderSQL()
	{
	//  Create SQL
        StringBuffer sql = new StringBuffer(
            "SELECT ol.DD_OrderLine_ID, ol.QtyInTransit , uom.Name , p.Value ,p.Name  , w.Name "
            + "FROM DD_OrderLine ol INNER JOIN DD_Order o ON (o.DD_Order_ID=ol.DD_Order_ID) INNER JOIN M_Product p ON (p.M_Product_ID=ol.M_Product_ID) "
            + " INNER JOIN C_UOM uom  ON (uom.C_UOM_ID=ol.C_UOM_ID)"
            + " INNER JOIN M_Locator  l ON (l.M_Locator_ID = ol.M_Locator_ID)"
            + " INNER JOIN M_Warehouse  w ON (w.M_Warehouse_ID = l.M_Warehouse_ID)"  
            + " WHERE o.DocStatus= 'CO' AND  ol.QtyInTransit > 0  AND  o.DD_Order_ID = ? ");
        
        return sql.toString();
	}

	/**
	 *  Query Info
	 */
	private void executeQuery()
	{
		log.info("");	
		String sql = "";
		
		if (m_DD_Order_ID == null)
			return;
		
		    sql = getOrderSQL();

		log.debug(sql);
		//  reset table
		int row = 0;
		miniTable.setRowCount(row);
			
		//  Execute
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, Integer.parseInt(m_DD_Order_ID.toString()));
			ResultSet rs = pstmt.executeQuery();
			//
			while (rs.next())
			{
				//  extend table
				miniTable.setRowCount(row+1);
				//  set values
				miniTable.setValueAt(new IDColumn(rs.getInt(1)), row, 0);   //  DD_Order_ID
				miniTable.setValueAt(rs.getBigDecimal(2), row, 1);              //  QtyInTransit
				miniTable.setValueAt(rs.getString(3), row, 2);              //  C_UOM_ID
				miniTable.setValueAt(rs.getString(4), row, 4);              //  Value
				miniTable.setValueAt(rs.getString(5), row, 3);              //  M_Product_ID
				miniTable.setValueAt(rs.getString(6), row, 5);           	//  WarehouseSource
				//miniTable.setValueAt(rs.getBigDecimal(7), row, 6);          //  QtyBackOrder
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
	 * 	Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	}	//	dispose

	/**
	 *	Action Listener
	 *  @param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		log.info("Cmd=" + e.getActionCommand());
		//
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
			return;
		}

		//
		saveSelection();
		if (selection != null
			&& selection.size() > 0
			&& m_selectionActive	//	on selection tab
			&& m_DD_Order_ID != null && m_MovementDate != null)
			generateMovements ();
		else
			dispose();
	}	//	actionPerformed

	/**
	 *	Vetoable Change Listener - requery
	 *  @param e event
	 */
	@Override
	public void vetoableChange(PropertyChangeEvent e)
	{
		log.info(e.getPropertyName() + "=" + e.getNewValue());
		if (e.getPropertyName().equals("DD_Order_ID"))
			m_DD_Order_ID = e.getNewValue();
		
		if(e.getPropertyName().equals("MovementDate"))
			m_MovementDate = e.getNewValue();
		
		executeQuery();
	}	//	vetoableChange

	/**
	 *	Change Listener (Tab changed)
	 *  @param e event
	 */
	@Override
	public void stateChanged (ChangeEvent e)
	{
		int index = tabbedPane.getSelectedIndex();
		m_selectionActive = (index == 0);
	}	//	stateChanged

	/**
	 *  Table Model Listener
	 *  @param e event
	 */
	@Override
	public void tableChanged(TableModelEvent e)
	{
		int rowsSelected = 0;
		int rows = miniTable.getRowCount();
		for (int i = 0; i < rows; i++)
		{
			IDColumn id = (IDColumn)miniTable.getValueAt(i, 0);     //  ID in column 0
			if (id != null && id.isSelected())
				rowsSelected++;
		}
		statusBar.setStatusDB(" " + rowsSelected + " ");
	}   //  tableChanged

	/**
	 *	Save Selection & return selecion Query or ""
	 *  @return where clause like C_Order_ID IN (...)
	 */
	private void saveSelection()
	{
		log.info("");
		//  ID selection may be pending
		miniTable.editingStopped(new ChangeEvent(this));
		//  Array of Integers
		ArrayList<Integer> results = new ArrayList<Integer>();
		selection = null;

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
		selection = results;
		
	}	//	saveSelection

	
	/**************************************************************************
	 *	Generate Shipments
	 */
	private void generateMovements ()
	{

		log.info("DD_Order_ID=" + m_DD_Order_ID);
		log.info("MovementDate" + m_MovementDate);
		String trxName = Trx.createTrxName("IOG");	
		Trx trx = Trx.get(trxName, true);	//trx needs to be committed too

		m_selectionActive = false;  //  prevents from being called twice
		statusBar.setStatusLine(Msg.translate(Env.getCtx(), "M_Movement_ID"));
		statusBar.setStatusDB(String.valueOf(selection.size()));
		
		if (selection.size() <= 0)
			return;
		Properties m_ctx = Env.getCtx();
		
		Timestamp MovementDate = (Timestamp) m_MovementDate;
		MDDOrder order = new MDDOrder(m_ctx , Integer.parseInt(m_DD_Order_ID.toString()), trxName);
		MMovement movement = new MMovement(m_ctx , 0 , trxName);
		
		movement.setDD_Order_ID(order.getDD_Order_ID());
		movement.setAD_User_ID(order.getAD_User_ID());
		movement.setPOReference(order.getPOReference());
		movement.setReversal_ID(0);
		movement.setM_Shipper_ID(order.getM_Shipper_ID());
		movement.setDescription(order.getDescription());
		movement.setC_BPartner_ID(order.getC_BPartner_ID());
		movement.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
		movement.setAD_Org_ID(order.getAD_Org_ID());
		movement.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		movement.setAD_User_ID(order.getAD_User_ID());
		// 07689: this setting is harmless, even though the column is currently hidden
		movement.setC_Activity_ID(order.getC_Activity_ID());
		movement.setC_Campaign_ID(order.getC_Campaign_ID());
		movement.setC_Project_ID(order.getC_Project_ID());
		movement.setMovementDate(MovementDate);
		movement.setDeliveryRule(order.getDeliveryRule());
		movement.setDeliveryViaRule(order.getDeliveryViaRule());
		movement.setDocAction(MMovement.ACTION_Prepare);
		movement.setDocStatus(MMovement.DOCSTATUS_Drafted);
		
		if (!movement.save())
			 throw new LiberoException("Can not save Inventory Move");
		
		
		for (int i = 0 ; i < selection.size() ; i++ )
		{
			int DD_OrderLine_ID = selection.get(i);
			MDDOrderLine oline = new MDDOrderLine(m_ctx, DD_OrderLine_ID, trxName);
			MMovementLine line = new MMovementLine(movement);
			line.setM_Product_ID(oline.getM_Product_ID());
			BigDecimal QtyDeliver = (BigDecimal) miniTable.getValueAt(i, 1);
			if(QtyDeliver == null | QtyDeliver.compareTo(oline.getQtyInTransit()) > 0)
				 throw new LiberoException("Error in Qty");
			
			line.setOrderLine(oline, QtyDeliver, true);
			line.saveEx();
		}
		
		movement.completeIt();
		movement.setDocAction(MMovement.ACTION_Complete);
		movement.setDocStatus(MMovement.DOCACTION_Close);
		movement.save();
		
		
		trx.commit();
		generateMovements_complete(movement);
		

		//
	}	//	generateMovements

	

	/**
	 *  Complete generating movements.
	 * @param movement
	 */
	private void generateMovements_complete (MMovement movement)
	{
		//  Switch Tabs
		tabbedPane.setSelectedIndex(1);
		StringBuffer iText = new StringBuffer();
		iText.append("<b>").append("")
			.append("</b><br>")
			.append(Msg.translate(Env.getCtx(), "DocumentNo") +" : " +movement.getDocumentNo())
			//  Shipments are generated depending on the Delivery Rule selection in the Order
			.append("<br>")
			.append("");
		info.setText(iText.toString());


		confirmPanelGen.getOKButton().setEnabled(false);
		//	OK to print shipments
		if (ADialog.ask(m_WindowNo, this, "PrintShipments"))
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			int retValue = ADialogDialog.A_CANCEL;	//	see also ProcessDialog.printShipments/Invoices
			do
			{

					 MPrintFormat format = MPrintFormat.get(Env.getCtx(), MPrintFormat.getPrintFormat_ID("Inventory Move Hdr (Example)", MMovement.Table_ID,  0), false);
					 MQuery query = new MQuery(MMovement.Table_Name);
					 query.addRestriction(MMovement.COLUMNNAME_M_Movement_ID, Operator.EQUAL, movement.getM_Movement_ID());
		                                
					//	Engine
		             PrintInfo info = new PrintInfo(MMovement.Table_Name,MMovement.Table_ID, movement.getM_Movement_ID());               
		             ReportEngine re = new ReportEngine(Env.getCtx(), format, query, info);
		             re.print();
                     new Viewer(re);

				
				ADialogDialog d = new ADialogDialog (m_frame,
					Env.getHeader(Env.getCtx(), m_WindowNo),
					Msg.getMsg(Env.getCtx(), "PrintoutOK?"),
					JOptionPane.QUESTION_MESSAGE);
				retValue = d.getReturnCode();
			}
			while (retValue == ADialogDialog.A_CANCEL);
			setCursor(Cursor.getDefaultCursor());
		}	//	OK to print shipments

		//
		confirmPanelGen.getOKButton().setEnabled(true);
	}   //  generateMovement_complete

}	//	VOrderDistributionReceipt

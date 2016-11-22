/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
 * Contributor(s): Victor Perez www.e-evolution.com                           *
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
import org.compiere.apps.ProcessCtl;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.grid.ed.VComboBox;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.MColumn;
import org.compiere.model.MLocator;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MMovement;
import org.compiere.model.MOrder;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MPrivateAccess;
import org.compiere.model.MProcess;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.PrintInfo;
import org.compiere.plaf.CompiereColor;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.print.Viewer;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoUtil;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTabbedPane;
import org.compiere.swing.CTextPane;
import org.compiere.util.ASyncProcess;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.eevolution.model.MDDOrder;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *	Create Movement from Distribution Order
 *
 *  @author victor.perez@www.e-evolution.com
 *  @version $Id: VOrderDistribution,v 1.0
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class VOrderDistribution extends CPanel
	implements FormPanel, ActionListener, VetoableChangeListener,
				ChangeListener, TableModelListener, ASyncProcess
{
	private static final long serialVersionUID = 1L;

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
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", "N");
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
	private Object 			m_M_Locator_ID = null;
	private Object 			m_M_LocatorTo_ID = null;
	private Object 			m_C_BPartner_ID = null;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VOrderDistribution.class);
	//
	private CTabbedPane tabbedPane = new CTabbedPane();
	private CPanel selPanel = new CPanel();
	private CPanel selNorthPanel = new CPanel();
	private BorderLayout selPanelLayout = new BorderLayout();
	private CLabel lOrder = new CLabel();
	private VLookup fOrder;
	private CLabel lLocator = new CLabel();
	private VLookup fLocator;
	private CLabel lLocatorTo = new CLabel();
	private VLookup fLocatorTo;
	private CLabel lBPartner = new CLabel();
	private VLookup fBPartner;
	private FlowLayout northPanelLayout = new FlowLayout();
	private ConfirmPanel confirmPanelSel = ConfirmPanel.newWithOKAndCancel();
	private ConfirmPanel confirmPanelGen = ConfirmPanel.builder().withRefreshButton(true).build();
	private StatusBar statusBar = new StatusBar();
	private CPanel genPanel = new CPanel();
	private BorderLayout genLayout = new BorderLayout();
	private CTextPane info = new CTextPane();
	private JScrollPane scrollPane = new JScrollPane();
	private MiniTable miniTable = new MiniTable();

	private CLabel     lDocType = new CLabel();
	private VComboBox  cmbDocType = new VComboBox();

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
		lLocator.setLabelFor(fLocator);
		lLocatorTo.setLabelFor(fLocatorTo);
		lBPartner.setLabelFor(fBPartner);
		lBPartner.setText("BPartner");
		selNorthPanel.setLayout(northPanelLayout);
		northPanelLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.add(selPanel, Msg.getMsg(Env.getCtx(), "Select"));
		selPanel.add(selNorthPanel, BorderLayout.NORTH);
		selNorthPanel.add(lOrder, null);
		selNorthPanel.add(fOrder, null);
		selNorthPanel.add(lLocator, null);
		selNorthPanel.add(fLocator, null);
		selNorthPanel.add(lLocatorTo, null);
		selNorthPanel.add(fLocatorTo, null);
		//selNorthPanel.add(lWarehouse, null);
		//selNorthPanel.add(fWarehouse, null);
		selNorthPanel.add(lBPartner, null);
		selNorthPanel.add(fBPartner, null);
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
		// Order Distribution
		MLookup orderL = MLookupFactory.get (Env.getCtx(), m_WindowNo, 0, MColumn.getColumn_ID(MDDOrder.Table_Name, MDDOrder.COLUMNNAME_DD_Order_ID), DisplayType.Search);
		fOrder = new VLookup (MDDOrder.COLUMNNAME_DD_Order_ID, true, false, true, orderL);
		lOrder.setText(Msg.translate(Env.getCtx(), MDDOrder.COLUMNNAME_DD_Order_ID));
		fOrder.addVetoableChangeListener(this);
		lOrder.setVisible(false);
		fOrder.setVisible(false);

		MLookup llocator= MLookupFactory.get (Env.getCtx(), m_WindowNo, 0, 53950, DisplayType.TableDir);
		fLocator = new VLookup (MLocator.COLUMNNAME_M_Locator_ID, true, false, true, llocator);
		lLocator.setText(Msg.translate(Env.getCtx(), "M_Locator_ID"));
		fLocator.addVetoableChangeListener(this);
		m_M_Locator_ID = fLocator.getValue();

		MLookup llocatorto = MLookupFactory.get (Env.getCtx(), m_WindowNo, 0, 53949, DisplayType.TableDir);
		fLocatorTo = new VLookup ("M_LocatorTo_ID", false, false, true, llocatorto);
		lLocatorTo.setText(Msg.translate(Env.getCtx(), "M_LocatorTo_ID"));
		fLocatorTo.addVetoableChangeListener(this);
		m_M_LocatorTo_ID = fLocatorTo.getValue();

		//	C_Order.C_BPartner_ID
		MLookup bpL = MLookupFactory.get (Env.getCtx(), m_WindowNo, 0, 2762, DisplayType.Search);
		fBPartner = new VLookup ("C_BPartner_ID", false, false, true, bpL);
		lBPartner.setText(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		fBPartner.addVetoableChangeListener(this);
		//Document Type Sales Order/Vendor RMA
		//lDocType.setText(Msg.translate(Env.getCtx(), "C_DocType_ID"));
		//cmbDocType.addItem(new KeyNamePair(MOrder.Table_ID, Msg.translate(Env.getCtx(), "Order")));
		//cmbDocType.addItem(new KeyNamePair(MRMA.Table_ID, Msg.translate(Env.getCtx(), "VendorRMA")));
		//cmbDocType.addActionListener(this);
	}	//	fillPicks

	/**
	 *	Dynamic Init.
	 *	- Create GridController & Panel
	 *	- AD_Column_ID from C_Order
	 */
	private void dynInit()
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
		miniTable.setRowSelectionAllowed(true);
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
				"SELECT DD_Order_ID, o.Name, dt.Name, DocumentNo, bp.Name, DateOrdered "
				+ "FROM M_Movement_Candidate_v ic, AD_Org o, C_BPartner bp, C_DocType dt "
				+ "WHERE ic.AD_Org_ID=o.AD_Org_ID"
				+ " AND ic.C_BPartner_ID=bp.C_BPartner_ID"
				+ " AND ic.C_DocType_ID=dt.C_DocType_ID"
				+ " AND ic.AD_Client_ID=?");

		if(m_M_Locator_ID != null)
			sql.append(" AND ic.M_Locator_ID=").append(m_M_Locator_ID);
		/*if (m_M_Warehouse_ID != null)
            sql.append(" AND ic.M_Warehouse_ID=").append(m_M_Warehouse_ID);*/
		if(m_M_LocatorTo_ID != null)
			sql.append(" AND ic.M_LocatorTo_ID=").append(m_M_LocatorTo_ID);
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
			sql.append("DD_Order_ID").append(lockedIDs);
		}
		/* eng - Exclude locked records; @Trifon */

		//
		sql.append(" ORDER BY o.Name,bp.Name,DateOrdered");

		return sql.toString();
	}

	/**
	 *  Query Info
	 */
	private void executeQuery()
	{
		log.info("");
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());

		String sql = "";

		KeyNamePair docTypeKNPair = (KeyNamePair)cmbDocType.getSelectedItem();

		sql = getOrderSQL();

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
		if (cmbDocType.equals(e.getSource()))
		{
			executeQuery();
			return;
		}
		//
		saveSelection();
		if (selection != null
				&& selection.size() > 0
				&& m_selectionActive	//	on selection tab
				&& m_M_Locator_ID != null)
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
		//if (e.getPropertyName().equals("M_Warehouse_ID"))
		//m_M_Warehouse_ID = e.getNewValue();
		if (e.getPropertyName().equals("M_Locator_ID"))
			m_M_Locator_ID = e.getNewValue();
		if (e.getPropertyName().equals("M_LocatorTo_ID"))
			m_M_LocatorTo_ID = e.getNewValue();
		if (e.getPropertyName().equals("C_BPartner_ID"))
		{
			m_C_BPartner_ID = e.getNewValue();
			fBPartner.setValue(m_C_BPartner_ID);	//	display value
		}
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
		ArrayList<Integer> results = new ArrayList<>();
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
		//log.info("M_Warehouse_ID=" + m_M_Warehouse_ID);
		log.info("M_Locator_ID=" + m_M_Locator_ID);
		String trxName = Trx.createTrxName("IOG");
		Trx trx = Trx.get(trxName, true);	//trx needs to be committed too
		//String trxName = null;
		//Trx trx = null;

		m_selectionActive = false;  //  prevents from being called twice
		statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), "M_Movement_ID"));
		statusBar.setStatusDB(String.valueOf(selection.size()));

		//	Prepare Process
		int AD_Process_ID = MProcess.getProcess_ID("M_Generate Movement", trxName);
		KeyNamePair docTypeKNPair = (KeyNamePair)cmbDocType.getSelectedItem();

		MPInstance instance = new MPInstance(Env.getCtx(), AD_Process_ID, 0, 0);
		if (!instance.save())
		{
			info.setText(Msg.getMsg(Env.getCtx(), "ProcessNoInstance"));
			return;
		}

		//insert selection
		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO T_SELECTION(AD_PINSTANCE_ID, T_SELECTION_ID) ");
		int counter = 0;
		for(Integer selectedId : selection)
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
					info.setText(msg);
					trx.rollback();
					return;
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
				String msg = "No Movements";     //  not translated!
				log.info(msg);
				info.setText(msg);
				trx.rollback();
				return;
			}
		}

		//call process
		ProcessInfo pi = new ProcessInfo ("VOrderDistribution", AD_Process_ID);
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());

		//	Add Parameter - Selection=Y
		MPInstancePara ip = new MPInstancePara(instance, 10);
		ip.setParameter("Selection","Y");
		if (!ip.save())
		{
			String msg = "No Parameter added";  //  not translated
			info.setText(msg);
			log.error(msg);
			return;
		}
		MLocator locator = MLocator.get(Env.getCtx(), Integer.parseInt(m_M_Locator_ID.toString()));
		//	Add Parameter - M_Warehouse_ID=x
		ip = new MPInstancePara(instance, 20);
		ip.setParameter("M_Warehouse_ID", locator.getM_Warehouse_ID());
		if (!ip.save())
		{
			String msg = "No Parameter added";  //  not translated
			info.setText(msg);
			log.error(msg);
			return;
		}

		//	Execute Process
		ProcessCtl worker = new ProcessCtl(this, Env.getWindowNo(this), pi, trx);
		worker.start();     //  complete tasks in unlockUI / generateShipments_complete
		//
	}	//	generateShipments

	/**
	 *  Complete generating shipments.
	 *  Called from Unlock UI
	 *  @param pi process info
	 */
	private void generateMovements_complete (ProcessInfo pi)
	{
		//  Switch Tabs
		tabbedPane.setSelectedIndex(1);
		//
		ProcessInfoUtil.setLogFromDB(pi);
		StringBuffer iText = new StringBuffer();
		iText.append("<b>").append(pi.getSummary())
		.append("</b><br>(")
		.append(Msg.getMsg(Env.getCtx(), "InOutGenerateInfo"))
		//  Shipments are generated depending on the Delivery Rule selection in the Order
		.append(")<br>")
		.append(pi.getLogInfo(true));
		info.setText(iText.toString());

		//	Reset Selection

		//String sql = "UPDATE DD_Order SET IsSelected='N' WHERE " + m_whereClause;
		//int no = DB.executeUpdate(sql, null);
		//log.info("Reset=" + no);

		//	Get results
		int[] ids = null;
		if (ids == null || ids.length == 0)
			return;
		log.info("PrintItems=" + ids.length);

		confirmPanelGen.getOKButton().setEnabled(false);
		//	OK to print shipments
		if (ADialog.ask(m_WindowNo, this, "PrintShipments"))
		{
			//	info.append("\n\n" + Msg.getMsg(Env.getCtx(), "PrintShipments"));
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			int retValue = ADialogDialog.A_CANCEL;	//	see also ProcessDialog.printShipments/Invoices
			do
			{
				//	Loop through all items
				for (int i = 0; i < ids.length; i++)
				{
					int M_Movement_ID = ids[i];
					MPrintFormat format = MPrintFormat.get(Env.getCtx(), MPrintFormat.getPrintFormat_ID("Inventory Move Hdr (Example)", MMovement.Table_ID,  0), false);
					MQuery query = new MQuery(MMovement.Table_Name);
					query.addRestriction(MMovement.COLUMNNAME_M_Movement_ID, Operator.EQUAL, M_Movement_ID);

					//	Engine
					PrintInfo info = new PrintInfo(MMovement.Table_Name,MMovement.Table_ID, M_Movement_ID);
					ReportEngine re = new ReportEngine(Env.getCtx(), format, query, info);
					re.print();
					new Viewer(re);
				}
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
	}   //  generateShipments_complete


	/**************************************************************************
	 *  Lock User Interface.
	 *  Called from the Worker before processing
	 *  @param pi process info
	 */
	@Override
	public void lockUI (ProcessInfo pi)
	{
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.setEnabled(false);
	}   //  lockUI

	/**
	 *  Unlock User Interface.
	 *  Called from the Worker when processing is done
	 *  @param pi result of execute ASync call
	 */
	@Override
	public void unlockUI (ProcessInfo pi)
	{
		this.setEnabled(true);
		this.setCursor(Cursor.getDefaultCursor());
		//
		generateMovements_complete(pi);
	}   //  unlockUI

	/**
	 *  Is the UI locked (Internal method)
	 *  @return true, if UI is locked
	 */
	@Override
	public boolean isUILocked()
	{
		return this.isEnabled();
	}   //  isUILocked

	/**
	 *  Method to be executed async.
	 *  Called from the Worker
	 *  @param pi ProcessInfo
	 */
	@Override
	public void executeASync (ProcessInfo pi)
	{
	}   //  executeASync

}	//	VOrderDistribution

/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
package org.adempiere.inout.form;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.inout.process.InOutGenerate;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
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
import org.compiere.model.MClient;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.model.MRefList;
import org.compiere.model.MTable;
import org.compiere.plaf.CompiereColor;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoUtil;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTabbedPane;
import org.compiere.swing.CTextPane;
import org.compiere.util.ASyncProcess;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Login;
import org.compiere.util.Msg;
import org.compiere.util.TrxRunnable;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;

/**
 * Manual Shipment Selection
 * 
 * @author Jorg Janke
 * @version $Id: VInOutGen.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VInOutGen extends CPanel implements FormPanel, ActionListener,
		VetoableChangeListener, ChangeListener, TableModelListener,
		ASyncProcess {

	public static final String MSG_SELECT_ALL = "Button.SelectAll";

	public static final String MSG_UNSELECT_ALL = "Button.UnselectAll";

	private static final Logger logger = LogManager.getLogger(VInOutGen.class);

	private static final long serialVersionUID = 4788331195927773242L;

	public static final String MSG_COMPUTING_DATA = "VInOutGen.ComputingData";

	public static final String MSG_CONSIDER_UNCONFIRMED = "VInOutGen.ConsiderUnconfirmed";

	public static final String ORG = "AD_Org_ID";

	/**
	 * Initialize Panel
	 * 
	 * @param WindowNo
	 *            window
	 * @param frame
	 *            frame
	 */
	@Override
	public void init(int WindowNo, FormFrame frame) {
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", "Y");
		try {
			fillPicks();
			jbInit();
			dynInit();
			frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
			frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		} catch (Exception ex) {
			log.error("init", ex);
		}
	} // init

	/** Window No */
	private int m_WindowNo = 0;
	/** FormFrame */
	private FormFrame m_frame;

	private boolean m_selectionActive = true;
	private transient Object m_M_Warehouse_ID = null;
	private transient int selectedBPartner = 0;
	/** Logger */
	private static Logger log = LogManager.getLogger(VInOutGen.class);
	//
	private CTabbedPane tabbedPane = new CTabbedPane();
	private CPanel selPanel = new CPanel();
	private CPanel selNorthPanel = new CPanel();
	private BorderLayout selPanelLayout = new BorderLayout();

	private VLookup fWarehouse;
	private VLookup fBPartner;

	private ConfirmPanel confirmPanelSel = ConfirmPanel.builder()
			.withCancelButton(true)
			.withRefreshButton(true)
			.build();
	private ConfirmPanel confirmPanelGen = ConfirmPanel.builder()
			.withCancelButton(false)
			.withRefreshButton(true)
			.build();
	private StatusBar statusBar = new StatusBar();
	private CPanel genPanel = new CPanel();
	private BorderLayout genLayout = new BorderLayout();
	private CTextPane info = new CTextPane();
	private JScrollPane scrollPane = new JScrollPane();
	private MiniTable miniTable = new MiniTable();

	private VComboBox cmbDocAction = new VComboBox();

	private transient final Map<Integer, Collection<Integer>> bpId2OrderId = new HashMap<Integer, Collection<Integer>>();

	private final VComboBox cOrgFilter = new VComboBox();
	private final CCheckBox cIgnorePortage = new CCheckBox();

	private final CCheckBox considerUnconfirmed = new CCheckBox();

	/** User selection */
	private List<Integer> selection = null;

	/**
	 * Static Init.
	 * 
	 * <pre>
	 *  selPanel (tabbed)
	 *      fOrg, fBPartner
	 *      scrollPane &amp; miniTable
	 *  genPanel
	 *      info
	 * </pre>
	 * 
	 * @throws Exception
	 */
	void jbInit() throws Exception {

		CompiereColor.setBackground(this);

		selPanel.setLayout(selPanelLayout);

		final CLabel lWarehouse = new CLabel();
		lWarehouse.setText(Msg.translate(Env.getCtx(), "M_Warehouse_ID"));
		lWarehouse.setLabelFor(fWarehouse);

		final CLabel lBPartner = new CLabel();
		lBPartner.setText(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		lBPartner.setLabelFor(fBPartner);

		// org filter
		final JLabel lOrgFilter = new JLabel();
		lOrgFilter.setText(Msg.translate(Env.getCtx(), ORG));
		lOrgFilter.setLabelFor(cOrgFilter);

		// docAction after creation of the shipments
		final CLabel lDocAction = new CLabel();
		lDocAction.setText(Msg.translate(Env.getCtx(), "DocAction"));
		lDocAction.setLabelFor(cmbDocAction);

		final CLabel lIgnorePortage = new CLabel();
		lIgnorePortage.setText(Msg.translate(Env.getCtx(), InOutGenerate.PARAM_IgnorePostageFreeAmount));
		lIgnorePortage.setLabelFor(cIgnorePortage);
		cIgnorePortage.setValue("N");

		final CLabel lConsiderUnconfirmed = new CLabel();
		lConsiderUnconfirmed.setText(Msg.translate(Env.getCtx(),
				MSG_CONSIDER_UNCONFIRMED));
		lConsiderUnconfirmed.setLabelFor(considerUnconfirmed);
		considerUnconfirmed.setValue("Y");
		// we want the user to see that unconfirmed shipments are considered,
		// but we don't want them to change it.
		considerUnconfirmed.setEnabled(false);

		final JButton selectAll = new JButton(Msg.getMsg(Env.getCtx(),
				MSG_SELECT_ALL));
		selectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setAllSelected(true);
			}
		});
		confirmPanelSel.addButton(selectAll);

		final JButton unselectAll = new JButton(Msg.getMsg(Env.getCtx(),
				MSG_UNSELECT_ALL));
		unselectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setAllSelected(false);
			}
		});
		confirmPanelSel.addButton(unselectAll);

		selNorthPanel.setLayout(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		selNorthPanel.add(lOrgFilter, c);
		c.gridx = 1;
		selNorthPanel.add(cOrgFilter, c);
		c.gridx = 2;
		selNorthPanel.add(lWarehouse, c);
		c.gridx = 3;
		selNorthPanel.add(fWarehouse, c);
		c.gridx = 4;
		selNorthPanel.add(lBPartner, c);
		c.gridx = 5;
		selNorthPanel.add(fBPartner, c);

		c.gridx = 0;
		c.gridy = 1;
		selNorthPanel.add(lDocAction, c);
		c.gridx = 1;
		selNorthPanel.add(cmbDocAction, c);
		c.gridx = 2;
		selNorthPanel.add(lIgnorePortage, c);
		c.gridx = 3;
		selNorthPanel.add(cIgnorePortage, c);
		c.gridx = 4;
		selNorthPanel.add(lConsiderUnconfirmed, c);
		c.gridx = 5;
		selNorthPanel.add(considerUnconfirmed, c);

		tabbedPane.add(selPanel, Msg.getMsg(Env.getCtx(), "Select"));
		selPanel.add(selNorthPanel, BorderLayout.NORTH);

		selPanel.setName("selPanel");
		selPanel.add(confirmPanelSel, BorderLayout.SOUTH);
		selPanel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getViewport().add(miniTable, null);

		confirmPanelSel.setActionListener(this);
		confirmPanelSel.getRefreshButton().setEnabled(false);
		confirmPanelSel.getOKButton().setEnabled(false);

		//
		tabbedPane.add(genPanel, Msg.getMsg(Env.getCtx(), "Generate"));
		genPanel.setLayout(genLayout);
		genPanel.add(info, BorderLayout.CENTER);
		genPanel.setEnabled(false);
		info.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		info.setEditable(false);
		genPanel.add(confirmPanelGen, BorderLayout.SOUTH);
		confirmPanelGen.setActionListener(this);

	} // jbInit

	/**
	 * Fill Picks. Column_ID from C_Order
	 * 
	 * @throws Exception
	 *             if Lookups cannot be initialized
	 */
	private void fillPicks() throws Exception {
		// C_OrderLine.M_Warehouse_ID
		final MLookup orgL = MLookupFactory.get(Env.getCtx(), m_WindowNo, 0, 
//				2223, // AD_Column_ID of C_OrderLine.M_Warehouse_ID
//				MTable.get(Env.getCtx(), I_M_InOut.Table_Name).getColumn(I_M_InOut.COLUMNNAME_M_Warehouse_ID).getAD_Column_ID(),
				MTable.get(Env.getCtx(), I_M_ShipmentSchedule.Table_Name).getColumn(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID).getAD_Column_ID(),
				DisplayType.TableDir);
//		orgL.getLookupInfo().IsIgnoredValidationCodeFail = true; // metas: tsa: 01753: ignore validation rule if fails
		fWarehouse = new VLookup("M_Warehouse_ID", true, false, true, orgL);
		fWarehouse.addVetoableChangeListener(this);

		m_M_Warehouse_ID = fWarehouse.getValue();
		// C_Order.C_BPartner_ID
		MLookup bpL = MLookupFactory.get(Env.getCtx(), m_WindowNo, 0, 2762,
				DisplayType.Search);
//		bpL.getLookupInfo().IsIgnoredValidationCodeFail = true; // metas: tsa: 01753: ignore validation rule if fails
		fBPartner = new VLookup("C_BPartner_ID", false, false, true, bpL);
		fBPartner.addVetoableChangeListener(this);

		// metas: new GUI elements

		final ValueNamePair[] allDocActions = MRefList.getList(Env.getCtx(),
				Constants.AD_REFERENCE_ID_DOCUMENT_ACTION, false);
		String namePrepare = null;
		String nameComplete = null;
		for (ValueNamePair currentAction : allDocActions) {
			if (DocAction.ACTION_Prepare.equals(currentAction.getValue())) {
				namePrepare = currentAction.getName();
			} else if (DocAction.ACTION_Complete.equals(currentAction
					.getValue())) {
				nameComplete = currentAction.getName();
			}
		}

		cmbDocAction.addItem(new ValueNamePair(DocAction.ACTION_Prepare,
				namePrepare));
		cmbDocAction.addItem(new ValueNamePair(DocAction.ACTION_Complete,
				nameComplete));

		// find out the orgs that are available to the user for selection
		final MClient client = MClient.get(Env.getCtx());
		final KeyNamePair clientKeyNamePair = new KeyNamePair(client.get_ID(),
				client.getName());
		final Login login = new Login(Env.getCtx());
		final Set<KeyNamePair> orgs = login.setClientAndGetOrgs(clientKeyNamePair);

		for (final KeyNamePair org : orgs) {
			cOrgFilter.addItem(org);
		}

		// metas end

	} // fillPicks

	/**
	 * Dynamic Init. - Create GridController & Panel - AD_Column_ID from C_Order
	 */
	private void dynInit() {
		// create Columns
		miniTable.addColumn("C_BPartner_ID");
		miniTable.addColumn("Value");
		miniTable.addColumn("Name");
		miniTable.addColumn("TotalLines");
		miniTable.addColumn("TotalDeliverable");
		miniTable.addColumn("PostageFreeAmt");

		//
		miniTable.setMultiSelection(true);
		miniTable.setRowSelectionAllowed(true);
		// set details
		miniTable.setColumnClass(0, IDColumn.class, false, " ");
		miniTable.setColumnClass(1, String.class, true, Msg.translate(Env
				.getCtx(), "Value"));
		miniTable.setColumnClass(2, String.class, true, Msg.translate(Env
				.getCtx(), "VInOutGen.Name"));
		miniTable.setColumnClass(3, BigDecimal.class, true, Msg.translate(Env
				.getCtx(), "TotalLines"));
		miniTable.setColumnClass(4, BigDecimal.class, true, Msg.translate(Env
				.getCtx(), "TotalDeliverable"));
		miniTable.setColumnClass(5, BigDecimal.class, true, Msg.translate(Env
				.getCtx(), "PostageFreeAmt"));

		//
		miniTable.autoSize();
		miniTable.getModel().addTableModelListener(this);
		// Info
		// statusBar.setStatusLine(Msg.getMsg(Env.getCtx(),
		// "InOutGenerateSel"));// @@
		statusBar.setStatusLine(" ");
		statusBar.setStatusDB(" ");
		// Tabbed Pane Listener
		tabbedPane.addChangeListener(this);
	} // dynInit

	private void setAllSelected(final boolean selected) {

		final int rowCount = miniTable.getRowCount();
		for (int i = 0; i < rowCount; i++) {

			final IDColumn id = (IDColumn) miniTable.getValueAt(i, 0);
			if (id != null) {
				id.setSelected(selected);
				miniTable.setValueAt(id, i, 0);
			}
		}
	}

	/**
	 * Dispose
	 */
	@Override
	public void dispose() {
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	} // dispose

	/**
	 * Action Listener
	 * 
	 * @param e
	 *            event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		log.info("Cmd=" + e.getActionCommand());
		//
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
			return;
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_REFRESH))
		{
			final Runnable computeValues = new Runnable() {
				@Override
				public void run() {

					statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), MSG_COMPUTING_DATA));
					enableGuiElements(false);
					
					try
					{
						computeValues();
					}
					finally
					{
						miniTable.autoSize();
						enableGuiElements(true);
						statusBar.setStatusLine(" ");
					}
				}
			};
			final Thread t = new Thread(computeValues);
			t.setDaemon(true);
			t.setName("VINOutGen.computeValues");
			t.start();
			return;
		}
		
		
		saveSelection();
		if (selection != null && !selection.isEmpty()
				&& m_selectionActive // on selection tab
				&& m_M_Warehouse_ID != null)
		{
			generateShipments();
		}
		else
		{
			dispose();
		}
	} // actionPerformed

	/**
	 * Vetoable Change Listener - requery
	 * 
	 * @param e
	 *            event
	 */
	@Override
	public void vetoableChange(PropertyChangeEvent e) {
		log.info(e.getPropertyName() + "=" + e.getNewValue());

		if (e.getPropertyName().equals("M_Warehouse_ID")) {
			m_M_Warehouse_ID = e.getNewValue();
			confirmPanelSel.getRefreshButton().setEnabled(
					m_M_Warehouse_ID != null && (Integer) m_M_Warehouse_ID > 0);
		}
		if (e.getPropertyName().equals("C_BPartner_ID")) {

			final Object objBPartnerId = e.getNewValue();
			fBPartner.setValue(objBPartnerId);// display value

			if (objBPartnerId != null && (Integer) objBPartnerId > 0) {
				selectedBPartner = (Integer) objBPartnerId;
			} else {
				selectedBPartner = 0;
			}
		}

	} // vetoableChange

	private void computeValues()
	{
		final List<Integer> schedIds = new ArrayList<Integer>();

		final Map<Integer, Collection<Integer>> bp2scheds = new HashMap<Integer, Collection<Integer>>();
		final Map<Integer, String> bp2value = new HashMap<Integer, String>();
		final Map<Integer, String> bp2name = new HashMap<Integer, String>();
		final Map<Integer, BigDecimal> bp2PostageFreeAmt = new HashMap<Integer, BigDecimal>();

		final Map<Integer, BigDecimal> sched2LineNetAmt = new HashMap<Integer, BigDecimal>();
		final Map<Integer, BigDecimal> sched2PriceActual = new HashMap<Integer, BigDecimal>();
		final Map<Integer, BigDecimal> sched2QtyUnconfirmed = new HashMap<Integer, BigDecimal>();
		final Map<Integer, BigDecimal> sched2QtyToDeliver = new HashMap<Integer, BigDecimal>();

		//
		// get the orders and their respective schedule entries
		bpId2OrderId.clear();

		final KeyNamePair selectedOrg = (KeyNamePair) cOrgFilter.getSelectedItem();
		final int selectedOrgId = selectedOrg.getKey();

		final List<Object> sqlParams = new ArrayList<Object>();
		final String sql = createSQL(selectedOrgId, selectedBPartner, sqlParams);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				insertResults(rs, schedIds, sched2LineNetAmt,
						sched2PriceActual, sched2QtyUnconfirmed,
						sched2QtyToDeliver, bp2name, bp2value,
						bp2PostageFreeAmt, bp2scheds);
			}

		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		updateMiniTable(bp2scheds, bp2value, bp2name, bp2PostageFreeAmt,
				sched2LineNetAmt, sched2PriceActual, sched2QtyUnconfirmed,
				sched2QtyToDeliver);
		
		confirmPanelSel.getOKButton().setEnabled(miniTable.getRowCount() > 0);
	}

	/**
	 * Constructs the SQL query for the miniTable.
	 * 
	 * @param selectedOrgId
	 *            the AD_Org_ID selected in the GUI or 0.
	 * @param bPartnerId
	 *            the BPartner selected in the GUI or 0.
	 * @return a SQL query whose result set can be processed in
	 *         {@link #insertResults(ResultSet, Collection, Map, Map, Map, Map, Map, Map, Map)}.
	 */
	private String createSQL(final int selectedOrgId, final int bPartnerId, final List<Object> sqlParams)
	{
		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("    s.M_ShipmentSchedule_ID, bp.C_BPartner_ID, bp.Value, ");
		sql.append("    bp.Name, bp.PostageFreeAmt, ");
		//sql.append("    o.C_Order_ID, ");
		sql.append("    ol.C_OrderLine_ID, ");
		sql.append("    ((ol.QtyOrdered-ol.QtyDelivered)*ol.PriceActual), ");
		sql.append("    ol.PriceActual, ");
		sql.append("    coalesce(i.qtyUnConfirmed,0) as qtyUnConfirmed, ");
		sql.append("    coalesce(s.QtyToDeliver,0) as QtyToDeliver ");
		sql.append("FROM M_ShipmentSchedule s ");

		// joining C_OrderLine and C_Order
		sql.append("  LEFT JOIN C_OrderLine ol ");
		sql.append("    ON ol.C_OrderLine_ID=s.C_OrderLine_ID ");
		sql.append("  LEFT JOIN C_Order o ON o.C_Order_ID=ol.C_Order_ID ");

		// joining the quantities of unconfirmed shipments for each
		// shipmentSchedule
		sql.append("  LEFT JOIN (");
		sql
				.append("    SELECT il.C_OrderLine_ID, sum(il.MovementQty) as qtyUnConfirmed ");
		sql.append("    FROM M_InOutLine il ");
		sql.append("      LEFT JOIN M_InOut i ON i.M_InOut_ID=il.M_InOut_ID ");
		sql.append("    WHERE i.DocStatus in ('DR','IP') ");
		sql.append("    GROUP BY il.C_OrderLine_ID ");
		sql.append("  ) i ON i.C_OrderLine_ID=s.C_OrderLine_ID ");

		// joining the customer
		sql.append("  LEFT JOIN C_BPartner bp ");
		sql.append("    ON bp.C_BPartner_ID=ol.C_BPartner_ID ");

		sql.append("WHERE s.AD_Client_ID=? ");
		sqlParams.add(Env.getAD_Client_ID(Env.getCtx()));

		if (bPartnerId > 0)
		{
			sql.append(" AND bp.C_BPartner_ID=? ");
			sqlParams.add(bPartnerId);
		}
		if (selectedOrgId > 0)
		{
			sql.append(" AND s.AD_Org_ID=? ");
			sqlParams.add(selectedOrgId);
		}
		return sql.toString();
	}

	private void insertResults(final ResultSet rs,
			final Collection<Integer> schedIds,
			final Map<Integer, BigDecimal> sched2LineNetAmt,
			final Map<Integer, BigDecimal> sched2PriceActual,
			final Map<Integer, BigDecimal> sched2QtyUnconfirmed,
			final Map<Integer, BigDecimal> sched2QtyToDeliver,
			final Map<Integer, String> bp2name,
			final Map<Integer, String> bp2value,
			final Map<Integer, BigDecimal> bp2PostageFreeAmt,
			final Map<Integer, Collection<Integer>> bp2scheds)
			throws SQLException {

		final int schedId = rs.getInt(1);
		final int bpId = rs.getInt(2);
		final String bpValue = rs.getString(3);
		final String bpName = rs.getString(4);
		final BigDecimal postageFreeAmt = rs.getBigDecimal(5);
		final int orderId = rs.getInt(6);
		final BigDecimal linetNetAmt = rs.getBigDecimal(7);
		final BigDecimal priceActual = rs.getBigDecimal(8);
		final BigDecimal qtyUnconfirmed = rs.getBigDecimal(9);
		final BigDecimal qtyToDeliver = rs.getBigDecimal(10);

		if (linetNetAmt == null) {
			logger
					.warn("LineNetAmt of the order line for m_ShipmentSchedule_ID "
							+ schedId
							+ " is null. Does the ol exist? Skipping schedule entry");
			return;
		}

		schedIds.add(schedId);
		sched2LineNetAmt.put(schedId, linetNetAmt);
		sched2PriceActual.put(schedId, priceActual);
		sched2QtyUnconfirmed.put(schedId, qtyUnconfirmed);
		sched2QtyToDeliver.put(schedId, qtyToDeliver);

		bp2name.put(bpId, bpName);
		bp2value.put(bpId, bpValue);
		bp2PostageFreeAmt.put(bpId, postageFreeAmt);

		Collection<Integer> bPartnerEntries = bp2scheds.get(bpId);
		if (bPartnerEntries == null) {
			bPartnerEntries = new ArrayList<Integer>();
			bp2scheds.put(bpId, bPartnerEntries);
		}
		bPartnerEntries.add(schedId);

		Collection<Integer> bPartnerOrderIds = bpId2OrderId.get(bpId);
		if (bPartnerOrderIds == null) {
			bPartnerOrderIds = new ArrayList<Integer>();
			bpId2OrderId.put(bpId, bPartnerOrderIds);
		}
		bPartnerOrderIds.add(orderId);
	}

	private void updateMiniTable(
			final Map<Integer, Collection<Integer>> bpId2scheds,
			final Map<Integer, String> bpId2value,
			final Map<Integer, String> bpId2name,
			final Map<Integer, BigDecimal> bpId2PostageFreeAmt,
			final Map<Integer, BigDecimal> schedId2LineNetAmt,
			final Map<Integer, BigDecimal> schedId2PriceActual,
			final Map<Integer, BigDecimal> sched2QtyUnconfirmed,
			final Map<Integer, BigDecimal> sched2QtyToDeliver) {

		miniTable.setRowCount(0);

		int row = 0;
		for (final int bpId : bpId2scheds.keySet()) {

			BigDecimal totalOrderedPrice = BigDecimal.ZERO;
			BigDecimal totalDeliverablePrice = BigDecimal.ZERO;
			BigDecimal totalQtyToDeliver = BigDecimal.ZERO;

			for (final int schedId : bpId2scheds.get(bpId)) {

				totalOrderedPrice = totalOrderedPrice.add(schedId2LineNetAmt
						.get(schedId));

				BigDecimal qtyToDeliver = sched2QtyToDeliver.get(schedId);
				if (qtyToDeliver == null) {
					// this could happen if a new schedule line hasn't been updated
					// yet. However, it will definitely be updated before
					// shipment generation.
					qtyToDeliver = BigDecimal.ZERO;
				}

				if (considerUnconfirmed.isSelected()) {

					final BigDecimal qtyUnconfirmed = sched2QtyUnconfirmed
							.get(schedId);

					if (qtyUnconfirmed != null) {
						qtyToDeliver = qtyToDeliver
								.subtract(qtyUnconfirmed);
						if (qtyToDeliver.signum() < 0) {
							qtyToDeliver = BigDecimal.ZERO;
						}
					}
				}

				BigDecimal priceActual = schedId2PriceActual.get(schedId);
				if (priceActual == null) {
					priceActual = BigDecimal.ZERO;
				}

				totalDeliverablePrice = totalDeliverablePrice.add(priceActual
						.multiply(qtyToDeliver));
				totalQtyToDeliver = totalQtyToDeliver.add(qtyToDeliver);
			}

			if (totalQtyToDeliver.signum() <= 0) 
			{
				continue;
			}
			miniTable.setRowCount(row + 1);
			// set values
			miniTable.setValueAt(new IDColumn(bpId), row, 0);
			miniTable.setValueAt(bpId2value.get(bpId), row, 1);
			miniTable.setValueAt(bpId2name.get(bpId), row, 2);
			miniTable.setValueAt(totalOrderedPrice, row, 3);
			miniTable.setValueAt(totalDeliverablePrice, row, 4);
			miniTable.setValueAt(bpId2PostageFreeAmt.get(bpId), row, 5);

			row++;
		}
	}

	/**
	 * Change Listener (Tab changed)
	 * 
	 * @param e
	 *            event
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		int index = tabbedPane.getSelectedIndex();
		m_selectionActive = (index == 0);
	} // stateChanged

	/**
	 * Table Model Listener
	 * 
	 * @param e
	 *            event
	 */
	@Override
	public void tableChanged(TableModelEvent e) {

		int rowsSelected = 0;
		int rows = miniTable.getRowCount();
		for (int i = 0; i < rows; i++) {
			IDColumn id = (IDColumn) miniTable.getValueAt(i, 0); // ID in
			// column 0
			if (id != null && id.isSelected())
				rowsSelected++;
		}
		statusBar.setStatusDB(" " + rowsSelected + " ");
	} // tableChanged

	/**
	 * Save Selection & return selecion Query or ""
	 * 
	 * @return where clause like C_Order_ID IN (...)
	 */
	private void saveSelection()
	{
		log.info("");
		// ID selection may be pending
		miniTable.editingStopped(new ChangeEvent(this));
		// Array of Integers
		final List<Integer> results = new ArrayList<Integer>();
		selection = null;

		// Get selected entries
		final int rows = miniTable.getRowCount();
		for (int i = 0; i < rows; i++)
		{
			final IDColumn id = (IDColumn) miniTable.getValueAt(i, 0); // ID in column 0
			// log.debug( "Row=" + i + " - " + id);
			if (id != null && id.isSelected())
			{
				results.add(id.getRecord_ID());
			}
		}

		if (results.isEmpty())
		{
			return;
		}
		
		log.info("Selected #" + results.size());
		selection = results;

	} // saveSelection

	/***************************************************************************
	 * Generate Shipments
	 */
	private void generateShipments()
	{
		log.info("M_Warehouse_ID=" + m_M_Warehouse_ID);

		m_selectionActive = false; // prevents from being called twice
		statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), "InOutGenerateGen"));
		statusBar.setStatusDB(String.valueOf(selection.size()));

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.run(new TrxRunnable()
		{
			
			@Override
			public void run(String localTrxName) throws Exception
			{
				final ITrx trx = trxManager.get(localTrxName, false);
				generateShipments0(trx);
			}
		});
	}
	
	private void generateShipments0(final ITrx trx)
	{
		final String trxName = trx.getTrxName();
		
		final int AD_Process_ID = 199; // M_InOut_Generate - org.compiere.process.InOutGenerate

		final MPInstance instance = new MPInstance(Env.getCtx(), AD_Process_ID, 0, 0);
		if (!instance.save())
		{
			info.setText(Msg.getMsg(Env.getCtx(), "ProcessNoInstance"));
			return;
		}

		final List<Integer> selectedOrderIds = new ArrayList<Integer>();
		for (final Integer selectedBpId : selection)
		{
			selectedOrderIds.addAll(bpId2OrderId.get(selectedBpId));
		}

		// insert selection
		DB.createT_Selection(instance.getAD_PInstance_ID(), selectedOrderIds, trxName);

		// call process
		final ProcessInfo pi = new ProcessInfo("VInOutGen", AD_Process_ID);
		pi.setAD_PInstance_ID(instance.getAD_PInstance_ID());

		// Add Parameter - Selection=Y
		MPInstancePara ip = new MPInstancePara(instance, 10);
		setupInstancePara(ip, "Selection", "Y");

		// Add Parameter - M_Warehouse_ID=x
		ip = new MPInstancePara(instance, 20);
		setupInstancePara(ip, InOutGenerate.PARAM_M_Warehouse_ID, Integer.parseInt(m_M_Warehouse_ID.toString()));

		// metas: Add Parameter - DocAction
		ip = new MPInstancePara(instance, 30);
		final String docAction = ((ValueNamePair) cmbDocAction.getSelectedItem()).getValue();
		setupInstancePara(ip, InOutGenerate.PARAM_DocAction, docAction);

		ip = new MPInstancePara(instance, 40);
		setupInstancePara(ip, InOutGenerate.PARAM_IgnorePostageFreeAmount, (String) cIgnorePortage.getValue());

		ip = new MPInstancePara(instance, 50);
		setupInstancePara(ip, InOutGenerate.PARAM_IsUnconfirmedInOut, (String) considerUnconfirmed.getValue());

		// Execute Process
		final ProcessCtl worker = new ProcessCtl(this, Env.getWindowNo(this), pi, trx);
		worker.run(); // complete tasks in unlockUI / generateShipments_complete
	} // generateShipments
	
	private void setupInstancePara(final MPInstancePara ip, final String name, final String value)
	{
		ip.setParameter(name, value);
		if (!ip.save()) {
			String msg = "No Parameter added"; // not translated
			info.setText(msg);
			log.error(msg);
			return;
		}
	}

	private void setupInstancePara(final MPInstancePara ip, final String name, final int value)
	{
		ip.setParameter(name, value);
		if (!ip.save()) {
			String msg = "No Parameter added"; // not translated
			info.setText(msg);
			log.error(msg);
			return;
		}
	}

	/**
	 * Complete generating shipments. Called from Unlock UI
	 * 
	 * @param pi
	 *            process info
	 */
	private void generateShipments_complete(final ProcessInfo pi)
	{
		// Switch Tabs
		tabbedPane.setSelectedIndex(1);
		//
		ProcessInfoUtil.setLogFromDB(pi);
		StringBuffer iText = new StringBuffer();
		iText.append("<b>").append(pi.getSummary()).append("</b><br>(").append(
				Msg.getMsg(Env.getCtx(), "InOutGenerateInfo"))
		// Shipments are generated depending on the Delivery Rule selection in
				// the Order
				.append(")<br>").append(pi.getLogInfo(true));
		info.setText(iText.toString());

		// Reset Selection
		/*
		 * String sql = "UPDATE C_Order SET IsSelected='N' WHERE " +
		 * m_whereClause; int no = DB.executeUpdate(sql, null);
		 * log.info("Reset=" + no);
		 */

		// Get results
		final int[] inoutIds = InOutGenerate.getM_InOut_IDs(pi);
		if (inoutIds == null || inoutIds.length == 0)
		{
			return;
		}
		log.info("PrintItems=" + inoutIds.length);

		//
		// OK to print shipments
		if (ADialog.ask(m_WindowNo, this, "PrintShipments"))
		{
			executeSync(new Runnable()
			{
				@Override
				public void run()
				{
					int retValue = ADialogDialog.A_CANCEL; // see also ProcessDialog.printShipments/Invoices
					do
					{
						// Loop through all items
						for (final int inoutId : inoutIds)
						{
							ReportCtl.startDocumentPrint(ReportEngine.SHIPMENT,
									inoutId,
									VInOutGen.this,
									Env.getWindowNo(VInOutGen.this),
									true);
						}
						
						final ADialogDialog d = new ADialogDialog(m_frame,
								Env.getHeader(Env.getCtx(), m_WindowNo),
								Msg.getMsg(Env.getCtx(), "PrintoutOK?"),
								JOptionPane.QUESTION_MESSAGE);
						retValue = d.getReturnCode();
					} while (retValue == ADialogDialog.A_CANCEL);
				}
			});
		} // OK to print shipments

		//
		confirmPanelGen.getOKButton().setEnabled(true);
	} // generateShipments_complete
	
	private void executeSync(final Runnable runnable)
	{
		final boolean okButtonEnabledOld = confirmPanelGen.getOKButton().isEnabled();
		confirmPanelGen.getOKButton().setEnabled(false);
		
		final Cursor cursorOld = getCursor();
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		try
		{
			runnable.run();
		}
		finally
		{
			setCursor(cursorOld);
			confirmPanelGen.getOKButton().setEnabled(okButtonEnabledOld);
		}
		
	}
	
	/***************************************************************************
	 * Lock User Interface. Called from the Worker before processing
	 * 
	 * @param pi
	 *            process info
	 */
	@Override
	public void lockUI(ProcessInfo pi)
	{
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.setEnabled(false);

		enableGuiElements(false);
	} // lockUI

	/**
	 * Unlock User Interface. Called from the Worker when processing is done
	 * 
	 * @param pi
	 *            result of execute ASync call
	 */
	@Override
	public void unlockUI(ProcessInfo pi)
	{
		this.setEnabled(true);
		this.setCursor(Cursor.getDefaultCursor());
		//
		enableGuiElements(true);

		generateShipments_complete(pi);
	} // unlockUI

	private void enableGuiElements(boolean enabled)
	{
		confirmPanelSel.getRefreshButton().setEnabled(enabled);
		confirmPanelSel.getOKButton().setEnabled(enabled);

		cmbDocAction.setEnabled(enabled);
		cIgnorePortage.setEnabled(enabled);
		cOrgFilter.setEnabled(enabled);
	}

	/**
	 * Is the UI locked (Internal method)
	 * 
	 * @return true, if UI is locked
	 */
	@Override
	public boolean isUILocked()
	{
		return this.isEnabled();
	} // isUILocked

	/**
	 * Method to be executed async. Called from the Worker
	 * 
	 * @param pi
	 *            ProcessInfo
	 */
	@Override
	public void executeASync(ProcessInfo pi)
	{
	} // executeASync

} // VInOutGen

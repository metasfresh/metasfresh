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
package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.StatusBar;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VNumber;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.MMatchPO;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

/**
 *  Manual Matching
 *
 *  @author     Jorg Janke
 *  @version    $Id: VMatch.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VMatch extends Match
	implements FormPanel, ActionListener, TableModelListener, ListSelectionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6381583335194660502L;
	
	private CPanel panel = new CPanel();

	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame frame
	 */
	@Override
	public void init (int WindowNo, FormFrame frame)
	{
		m_WindowNo = WindowNo;
		m_frame = frame;
		log.info("WinNo=" + m_WindowNo
			+ " - AD_Client_ID=" + m_AD_Client_ID + ", AD_Org_ID=" + m_AD_Org_ID + ", By=" + m_by);
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", "N");

		try
		{
			//	UI
			onlyVendor = VLookup.createBPartner(m_WindowNo); 
			onlyProduct = VLookup.createProduct(m_WindowNo);
			jbInit();
			//
			dynInit();
			frame.getContentPane().add(panel, BorderLayout.CENTER);
			frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
			//
			new Thread()
			{
				@Override
				public void run()
				{
					log.info("Starting ...");
					MMatchPO.consolidate(Env.getCtx());
					log.info("... Done");
				}
			}.start();
		}
		catch(Exception e)
		{
			log.error("", e);
		}
	}	//	init

	/**	Window No			*/
	private int         	m_WindowNo = 0;
	/**	FormFrame			*/
	private FormFrame 		m_frame;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VMatch.class);

	private int     m_AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
	private int     m_AD_Org_ID = Env.getAD_Org_ID(Env.getCtx());
	private int     m_by = Env.getAD_User_ID(Env.getCtx());

	/** Match Options           */
	private String[] m_matchOptions = new String[] {
		Msg.getElement(Env.getCtx(), "C_Invoice_ID", false),
		Msg.getElement(Env.getCtx(), "M_InOut_ID", false),
		Msg.getElement(Env.getCtx(), "C_Order_ID", false) };

	/** Match Mode              	*/
	private String[] m_matchMode = new String[] {
		Msg.translate(Env.getCtx(), "NotMatched"),
		Msg.translate(Env.getCtx(), "Matched")};
	private static final int		MODE_NOTMATCHED = 0;
	private static final int		MODE_MATCHED = 1;

	/**	Indexes in Table			*/
	private static final int		I_QTY = 6;
	private static final int		I_MATCHED = 7;
	
	private BigDecimal      m_xMatched = Env.ZERO;
	private BigDecimal      m_xMatchedTo = Env.ZERO;

	//
	private StatusBar statusBar = new StatusBar();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel northPanel = new CPanel();
	private GridBagLayout northLayout = new GridBagLayout();
	private CLabel matchFromLabel = new CLabel();
	private CComboBox matchFrom = new CComboBox(m_matchOptions);
	private CLabel matchToLabel = new CLabel();
	private CComboBox matchTo = new CComboBox();
	private CLabel matchModeLabel = new CLabel();
	private CComboBox matchMode = new CComboBox(m_matchMode);
	private VLookup onlyVendor = null; 
	private VLookup onlyProduct = null;
	private CLabel onlyVendorLabel = new CLabel();
	private CLabel onlyProductLabel = new CLabel();
	private CLabel dateFromLabel = new CLabel();
	private CLabel dateToLabel = new CLabel();
	private VDate dateFrom = new VDate("DateFrom", false, false, true, DisplayType.Date, "DateFrom");
	private VDate dateTo = new VDate("DateTo", false, false, true, DisplayType.Date, "DateTo");
	private CButton bSearch = new CButton();
	private CPanel southPanel = new CPanel();
	private GridBagLayout southLayout = new GridBagLayout();
	private CLabel xMatchedLabel = new CLabel();
	private CLabel xMatchedToLabel = new CLabel();
	private CLabel differenceLabel = new CLabel();
	private VNumber xMatched = new VNumber("xMatched", false, true, false, DisplayType.Quantity, "xMatched");
	private VNumber xMatchedTo = new VNumber("xMatchedTo", false, true, false, DisplayType.Quantity, "xMatchedTo");
	private VNumber difference = new VNumber("Difference", false, true, false, DisplayType.Quantity, "Difference");
	private CButton bProcess = new CButton();
	private CPanel centerPanel = new CPanel();
	private BorderLayout centerLayout = new BorderLayout(5,5);
	private JScrollPane xMatchedScrollPane = new JScrollPane();
	private TitledBorder xMatchedBorder = new TitledBorder("xMatched");
	private MiniTable xMatchedTable = new MiniTable();
	private JScrollPane xMatchedToScrollPane = new JScrollPane();
	private TitledBorder xMatchedToBorder = new TitledBorder("xMatchedTo");
	private MiniTable xMatchedToTable = new MiniTable();
	private CPanel xPanel = new CPanel();
	private JCheckBox sameProduct = new JCheckBox();
	private JCheckBox sameBPartner = new JCheckBox();
	private JCheckBox sameQty = new JCheckBox();
	private FlowLayout xLayout = new FlowLayout(FlowLayout.CENTER, 10, 0);

	/**
	 *  Static Init.
	 *  <pre>
	 *  mainPanel
	 *      northPanel
	 *      centerPanel
	 *          xMatched
	 *          xPanel
	 *          xMathedTo
	 *      southPanel
	 *  </pre>
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		panel.setLayout(mainLayout);
		northPanel.setLayout(northLayout);
		matchFromLabel.setText(Msg.translate(Env.getCtx(), "MatchFrom"));
		matchToLabel.setText(Msg.translate(Env.getCtx(), "MatchTo"));
		matchModeLabel.setText(Msg.translate(Env.getCtx(), "MatchMode"));
		onlyVendorLabel.setText(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		onlyProductLabel.setText(Msg.translate(Env.getCtx(), "M_Product_ID"));
		dateFromLabel.setText(Msg.translate(Env.getCtx(), "DateFrom"));
		dateToLabel.setText(Msg.translate(Env.getCtx(), "DateTo"));
		bSearch.setText(Msg.translate(Env.getCtx(), "Search"));
		southPanel.setLayout(southLayout);
		xMatchedLabel.setText(Msg.translate(Env.getCtx(), "ToBeMatched"));
		xMatchedToLabel.setText(Msg.translate(Env.getCtx(), "Matching"));
		differenceLabel.setText(Msg.translate(Env.getCtx(), "Difference"));
		bProcess.setText(Msg.translate(Env.getCtx(), "Process"));
		centerPanel.setLayout(centerLayout);
		xMatchedScrollPane.setBorder(xMatchedBorder);
		xMatchedScrollPane.setPreferredSize(new Dimension(450, 200));
		xMatchedToScrollPane.setBorder(xMatchedToBorder);
		xMatchedToScrollPane.setPreferredSize(new Dimension(450, 200));
		sameProduct.setSelected(true);
		sameProduct.setText(Msg.translate(Env.getCtx(), "SameProduct"));
		sameBPartner.setSelected(true);
		sameBPartner.setText(Msg.translate(Env.getCtx(), "SameBPartner"));
		sameQty.setSelected(false);
		sameQty.setText(Msg.translate(Env.getCtx(), "SameQty"));
		xPanel.setLayout(xLayout);
		panel.add(northPanel,  BorderLayout.NORTH);
		northPanel.add(matchFromLabel,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(12, 12, 5, 5), 0, 0));
		northPanel.add(matchFrom,        new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 0, 5, 0), 0, 0));
		northPanel.add(matchToLabel,      new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(12, 5, 5, 5), 0, 0));
		northPanel.add(matchTo,        new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 0, 5, 0), 0, 0));
		northPanel.add(matchModeLabel,    new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		northPanel.add(matchMode,     new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
		northPanel.add(onlyVendor,     new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 0, 0));
		northPanel.add(onlyProduct,       new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 0, 0));
		northPanel.add(onlyVendorLabel,     new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		northPanel.add(onlyProductLabel,      new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(dateFromLabel,    new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		northPanel.add(dateToLabel,      new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		northPanel.add(dateFrom,      new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
		northPanel.add(dateTo,       new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
		northPanel.add(bSearch,   new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 12), 0, 0));
		panel.add(southPanel,  BorderLayout.SOUTH);
		southPanel.add(xMatchedLabel,        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		southPanel.add(xMatched,         new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 0, 0));
		southPanel.add(xMatchedToLabel,          new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
		southPanel.add(bProcess,        new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 12), 0, 0));
		southPanel.add(differenceLabel,      new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
		panel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.add(xMatchedScrollPane,  BorderLayout.NORTH);
		xMatchedScrollPane.getViewport().add(xMatchedTable, null);
		centerPanel.add(xMatchedToScrollPane,  BorderLayout.SOUTH);
		centerPanel.add(xPanel, BorderLayout.CENTER);
		xPanel.add(sameBPartner, null);
		xPanel.add(sameProduct, null);
		xPanel.add(sameQty, null);
		xMatchedToScrollPane.getViewport().add(xMatchedToTable, null);
		southPanel.add(difference,   new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 0, 0));
		southPanel.add(xMatchedTo, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 0, 0));
	}   //  jbInit

	/**
	 *  Dynamic Init.
	 *  Table Layout, Visual, Listener
	 */
	private void dynInit()
	{
		ColumnInfo[] layout = new ColumnInfo[] {
			new ColumnInfo(" ",                                         ".", IDColumn.class, false, false, ""),
			new ColumnInfo(Msg.translate(Env.getCtx(), "DocumentNo"),   ".", String.class),             //  1
			new ColumnInfo(Msg.translate(Env.getCtx(), "Date"),         ".", Timestamp.class),
			new ColumnInfo(Msg.translate(Env.getCtx(), "C_BPartner_ID"),".", KeyNamePair.class, "."),   //  3
			new ColumnInfo(Msg.translate(Env.getCtx(), "Line"),         ".", KeyNamePair.class, "."),
			new ColumnInfo(Msg.translate(Env.getCtx(), "M_Product_ID"), ".", KeyNamePair.class, "."),   //  5
			new ColumnInfo(Msg.translate(Env.getCtx(), "Qty"),          ".", Double.class),
			new ColumnInfo(Msg.translate(Env.getCtx(), "Matched"),      ".", Double.class),
			new ColumnInfo(Msg.translate(Env.getCtx(), "AD_Org_ID"),    ".", KeyNamePair.class, ".") //JAVIER
		};

		xMatchedTable.prepareTable(layout, "", "", false, "");
		xMatchedToTable.prepareTable(layout, "", "", true, "");

		//  Visual
		AdempierePLAF.setDefaultBackground(panel);

		//  Listener
		matchFrom.addActionListener(this);
		matchTo.addActionListener(this);
		bSearch.addActionListener(this);
		xMatchedTable.getSelectionModel().addListSelectionListener(this);
		xMatchedToTable.getModel().addTableModelListener(this);
		bProcess.addActionListener(this);
		sameBPartner.addActionListener(this);
		sameProduct.addActionListener(this);
		sameQty.addActionListener(this);
		//  Init
		matchTo.setModel(new DefaultComboBoxModel(cmd_matchFrom((String)matchFrom.getSelectedItem())));
		//  Set Title
		xMatchedBorder.setTitle((String)matchFrom.getSelectedItem());
		xMatchedScrollPane.repaint();
		//  Reset Table
		xMatchedTable.setRowCount(0);
		
		cmd_matchTo();
		statusBar.setStatusLine("");
		statusBar.setStatusDB(0);
	}   //  dynInit

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

	
	/**************************************************************************
	 *  Action Listener
	 *  @param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		Integer product = onlyProduct.getValue()!=null?(Integer)onlyProduct.getValue():null;
		Integer vendor = onlyVendor.getValue()!=null?(Integer)onlyVendor.getValue():null;
		Timestamp from = dateFrom.getValue()!=null?(Timestamp)dateFrom.getValue():null;
		Timestamp to = dateTo.getValue()!=null?(Timestamp)dateTo.getValue():null;
		if (e.getSource() == matchFrom) {
			String selection = (String)matchFrom.getSelectedItem();
			matchTo.setModel(new DefaultComboBoxModel(cmd_matchFrom(selection)));
			//  Set Title
			xMatchedBorder.setTitle(selection);
			xMatchedScrollPane.repaint();
			//  Reset Table
			xMatchedTable.setRowCount(0);
			//  sync To
			cmd_matchTo();
			
		}
		else if (e.getSource() == matchTo)
			cmd_matchTo();
		else if (e.getSource() == bSearch) {
			xMatchedTable = (MiniTable) cmd_search(xMatchedTable, matchFrom.getSelectedIndex(), (String)matchTo.getSelectedItem(), product, vendor, from, to, matchMode.getSelectedIndex() == MODE_MATCHED);
			xMatched.setValue(Env.ZERO);
			//  Status Info
			statusBar.setStatusLine(matchFrom.getSelectedItem().toString()
				+ "# = " + xMatchedTable.getRowCount(),
				xMatchedTable.getRowCount() == 0);
			statusBar.setStatusDB(0);
		}
		else if (e.getSource() == bProcess) {
			cmd_process(xMatchedTable, xMatchedToTable, matchMode.getSelectedIndex(), matchFrom.getSelectedIndex(), matchTo.getSelectedItem(), m_xMatched);
			xMatchedTable = (MiniTable) cmd_search(xMatchedTable, matchFrom.getSelectedIndex(), (String)matchTo.getSelectedItem(), product, vendor, from, to, matchMode.getSelectedIndex() == MODE_MATCHED);
			xMatched.setValue(Env.ZERO);
			//  Status Info
			statusBar.setStatusLine(matchFrom.getSelectedItem().toString()
				+ "# = " + xMatchedTable.getRowCount(),
				xMatchedTable.getRowCount() == 0);
			statusBar.setStatusDB(0);
		}
		else if (e.getSource() == sameBPartner
			|| e.getSource() == sameProduct
			|| e.getSource() == sameQty)
			xMatchedTable = (MiniTable) cmd_search(xMatchedTable, matchFrom.getSelectedIndex(), (String)matchTo.getSelectedItem(), product, vendor, from, to, matchMode.getSelectedIndex() == MODE_MATCHED);
		panel.setCursor(Cursor.getDefaultCursor());
	}   //  actionPerformed

	
	/**
	 *  Match To Changed - set Title
	 */
	private void cmd_matchTo()
	{
	//	log.debug( "VMatch.cmd_matchTo");
		String selection = (String)matchTo.getSelectedItem();
		xMatchedToBorder.setTitle(selection);
		xMatchedToScrollPane.repaint();
		//  Reset Table
		xMatchedToTable.setRowCount(0);
	}   //  cmd_matchTo
	

	/**************************************************************************
	 *  List Selection Listener - get Info and fill xMatchedTo
	 *  @param e event
	 */
	@Override
	public void valueChanged (ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting())
			return;
	//	log.info( "VMatch.valueChanged");
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		cmd_searchTo();
		panel.setCursor(Cursor.getDefaultCursor());
	}   //  valueChanged

	/**
	 *  Fill xMatchedTo
	 */
	private void cmd_searchTo()
	{
		int row = xMatchedTable.getSelectedRow();
		log.info("Row=" + row);

		double qty = 0.0;
		if (row < 0)
		{
			xMatchedToTable.setRowCount(0);
		}
		else
		{
			//  ** Create SQL **
			String displayString = (String)matchTo.getSelectedItem();
			int matchToType = matchFrom.getSelectedIndex();
			double docQty = ((Double)xMatchedTable.getValueAt(row, I_QTY)).doubleValue();
			double matchedQty = ((Double)xMatchedTable.getValueAt(row, I_MATCHED)).doubleValue();
			qty = docQty - matchedQty;
			xMatchedToTable = (MiniTable)cmd_searchTo(xMatchedTable, xMatchedToTable, displayString, matchToType, sameBPartner.isSelected(), sameProduct.isSelected(), sameQty.isSelected(), matchMode.getSelectedIndex() == MODE_MATCHED);

		}
		//  Display To be Matched Qty
		m_xMatched = new BigDecimal (qty);
		xMatched.setValue(m_xMatched);
		xMatchedTo.setValue(Env.ZERO);
		difference.setValue(m_xMatched);
		//  Status Info
		statusBar.setStatusLine(matchFrom.getSelectedItem().toString()
			+ "# = " + xMatchedTable.getRowCount() + " - "
			+ matchTo.getSelectedItem().toString()
			+  "# = " + xMatchedToTable.getRowCount(),
			xMatchedToTable.getRowCount() == 0);
		statusBar.setStatusDB(0);
	}   //  cmd_seachTo
	
	/***************************************************************************
	 *  Table Model Listener - calculate matchd Qty
	 *  @param e event
	 */
	@Override
	public void tableChanged (TableModelEvent e)
	{
		if (e.getColumn() != 0)
			return;
		log.info("Row=" + e.getFirstRow() + "-" + e.getLastRow() + ", Col=" + e.getColumn()
			+ ", Type=" + e.getType());
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		//  Matched From
		int matchedRow = xMatchedTable.getSelectedRow();
		KeyNamePair Product = (KeyNamePair)xMatchedTable.getValueAt(matchedRow, 5);

		//  Matched To
		double qty = 0.0;
		int noRows = 0;
		for (int row = 0; row < xMatchedToTable.getRowCount(); row++)
		{
			IDColumn id = (IDColumn)xMatchedToTable.getValueAt(row, 0);
			if (id != null && id.isSelected())
			{
				KeyNamePair ProductCompare = (KeyNamePair)xMatchedToTable.getValueAt(row, 5);
				if (Product.getKey() != ProductCompare.getKey())
				{
					id.setSelected(false);
				}
				else
				{
					if (matchMode.getSelectedIndex() == MODE_NOTMATCHED)
						qty += ((Double)xMatchedToTable.getValueAt(row, I_QTY)).doubleValue();  //  doc
					qty -= ((Double)xMatchedToTable.getValueAt(row, I_MATCHED)).doubleValue();  //  matched
					noRows++;
				}
			}
		}
		//  update qualtities
		m_xMatchedTo = new BigDecimal(qty);
		xMatchedTo.setValue(m_xMatchedTo);
		difference.setValue(m_xMatched.subtract(m_xMatchedTo));
		bProcess.setEnabled(noRows != 0);
		panel.setCursor(Cursor.getDefaultCursor());
		//  Status
		statusBar.setStatusDB(noRows);
	}   //  tableChanged

}   //  VMatch

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
package org.adempiere.webui.apps.form;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.SimpleListModel;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.panel.StatusBarPanel;
import org.adempiere.webui.session.SessionManager;
import org.compiere.apps.form.Match;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MMatchPO;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;

/**
 *  Manual Matching
 *
 *  @author     Jorg Janke
 *  @version    $Id: VMatch.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class WMatch extends Match
	implements IFormController, EventListener, WTableModelListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6383121932802974801L;
	private CustomForm form = new CustomForm();

	/**
	 *	Initialize Panel
	 */
	public WMatch()
	{
		log.info("WinNo=" + m_WindowNo
			+ " - AD_Client_ID=" + m_AD_Client_ID + ", AD_Org_ID=" + m_AD_Org_ID + ", By=" + m_by);
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", "N");

		try
		{
			//	UI
			onlyVendor = WSearchEditor.createBPartner(m_WindowNo); 
			onlyProduct = WSearchEditor.createProduct(m_WindowNo);
			zkInit();
			dynInit();

			southPanel.appendChild(new Separator());
			southPanel.appendChild(statusBar);
			LayoutUtils.addSclass("status-border", statusBar);
			//
			
			MMatchPO.consolidate(Env.getCtx());
			cmd_matchTo();
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
	}	//	init

	/**	Window No			*/
	private int         	m_WindowNo = 0;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WMatch.class);

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
	private Panel mainPanel = new Panel();
	private StatusBarPanel statusBar = new StatusBarPanel();
	private Borderlayout mainLayout = new Borderlayout();
	private Panel northPanel = new Panel();
	private Grid northLayout = GridFactory.newGridLayout();
	private Label matchFromLabel = new Label();
	private Listbox matchFrom = ListboxFactory.newDropdownListbox(m_matchOptions);
	private Label matchToLabel = new Label();
	private Listbox matchTo = ListboxFactory.newDropdownListbox();
	private Label matchModeLabel = new Label();
	private Listbox matchMode = ListboxFactory.newDropdownListbox(m_matchMode);
	private WSearchEditor onlyVendor = null; 
	private WSearchEditor onlyProduct = null;
	private Label onlyVendorLabel = new Label();
	private Label onlyProductLabel = new Label();
	private Label dateFromLabel = new Label();
	private Label dateToLabel = new Label();
	private WDateEditor dateFrom = new WDateEditor("DateFrom", false, false, true, "DateFrom");
	private WDateEditor dateTo = new WDateEditor("DateTo", false, false, true, "DateTo");
	private Button bSearch = new Button();
	private Panel southPanel = new Panel();
	private Grid southLayout = GridFactory.newGridLayout();
	private Label xMatchedLabel = new Label();
	private Label xMatchedToLabel = new Label();
	private Label differenceLabel = new Label();
	private WNumberEditor xMatched = new WNumberEditor("xMatched", false, true, false, DisplayType.Quantity, "xMatched");
	private WNumberEditor xMatchedTo = new WNumberEditor("xMatchedTo", false, true, false, DisplayType.Quantity, "xMatchedTo");
	private WNumberEditor difference = new WNumberEditor("Difference", false, true, false, DisplayType.Quantity, "Difference");
	private Button bProcess = new Button();
	private Panel centerPanel = new Panel();
	private Borderlayout centerLayout = new Borderlayout();
	private Label xMatchedBorder = new Label("xMatched");
	private WListbox xMatchedTable = ListboxFactory.newDataTable();
	private Label xMatchedToBorder = new Label("xMatchedTo");
	private WListbox xMatchedToTable = ListboxFactory.newDataTable();
	private Panel xPanel = new Panel();
	private Checkbox sameProduct = new Checkbox();
	private Checkbox sameBPartner = new Checkbox();
	private Checkbox sameQty = new Checkbox();

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
	private void zkInit() throws Exception
	{
		form.appendChild(mainPanel);
		mainPanel.setStyle("width: 99%; height: 100%; padding: 0; margin: 0");
		mainPanel.appendChild(mainLayout);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		northPanel.appendChild(northLayout);
		matchFromLabel.setText(Msg.translate(Env.getCtx(), "MatchFrom"));
		matchToLabel.setText(Msg.translate(Env.getCtx(), "MatchTo"));
		matchModeLabel.setText(Msg.translate(Env.getCtx(), "MatchMode"));
		onlyVendorLabel.setText(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		onlyProductLabel.setText(Msg.translate(Env.getCtx(), "M_Product_ID"));
		dateFromLabel.setText(Msg.translate(Env.getCtx(), "DateFrom"));
		dateToLabel.setText(Msg.translate(Env.getCtx(), "DateTo"));
		bSearch.setLabel(Msg.translate(Env.getCtx(), "Search"));
		southPanel.appendChild(southLayout);
		xMatchedLabel.setText(Msg.translate(Env.getCtx(), "ToBeMatched"));
		xMatchedToLabel.setText(Msg.translate(Env.getCtx(), "Matching"));
		differenceLabel.setText(Msg.translate(Env.getCtx(), "Difference"));
		bProcess.setLabel(Msg.translate(Env.getCtx(), "Process"));
		centerPanel.appendChild(centerLayout);
		sameProduct.setSelected(true);
		sameProduct.setText(Msg.translate(Env.getCtx(), "SameProduct"));
		sameBPartner.setSelected(true);
		sameBPartner.setText(Msg.translate(Env.getCtx(), "SameBPartner"));
		sameQty.setSelected(false);
		sameQty.setText(Msg.translate(Env.getCtx(), "SameQty"));
		
		North north = new North();
		mainLayout.appendChild(north);
		north.appendChild(northPanel);
		
		Rows rows = northLayout.newRows();
		Row row = rows.newRow();
		row.appendChild(matchFromLabel.rightAlign());
		row.appendChild(matchFrom);
		row.appendChild(matchToLabel.rightAlign());
		row.appendChild(matchTo);
		row.appendChild(new Space());
		
		row = rows.newRow();
		row.setSpans("1,1,3");
		row.appendChild(matchModeLabel.rightAlign());
		row.appendChild(matchMode);
		row.appendChild(new Space());
		
		row = rows.newRow();
		row.appendChild(onlyVendorLabel.rightAlign());
		row.appendChild(onlyVendor.getComponent());
		row.appendChild(onlyProductLabel.rightAlign());
		row.appendChild(onlyProduct.getComponent());
		row.appendChild(new Space());
		
		row = rows.newRow();
		row.appendChild(dateFromLabel.rightAlign());		
		row.appendChild(dateFrom.getComponent());
		row.appendChild(dateToLabel.rightAlign());
		row.appendChild(dateTo.getComponent());
		row.appendChild(bSearch);
		
		South south = new South();
		mainLayout.appendChild(south);
		south.appendChild(southPanel);
		
		rows = southLayout.newRows();
		
		row = rows.newRow();
		row.appendChild(xMatchedLabel.rightAlign());
		row.appendChild(xMatched.getComponent());
		row.appendChild(xMatchedToLabel.rightAlign());
		row.appendChild(xMatchedTo.getComponent());
		row.appendChild(differenceLabel.rightAlign());
		row.appendChild(difference.getComponent());
		row.appendChild(bProcess);
		
		Center center = new Center();
		mainLayout.appendChild(center);
		center.appendChild(centerPanel);
		center.setFlex(true);
		centerLayout.setWidth("100%");
		centerLayout.setHeight("100%");
		north = new North();
		centerLayout.appendChild(north);
		north.setStyle("border: none");
		Panel p = new Panel();
		p.appendChild(xMatchedBorder);
		p.appendChild(xMatchedTable);
		xMatchedTable.setWidth("99%");
		xMatchedTable.setHeight("85%");
		p.setStyle("width: 100%; height: 100%; padding: 0; margin: 0");
		north.appendChild(p);
		north.setHeight("44%");
		
		south = new South();
		centerLayout.appendChild(south);
		south.setStyle("border: none");
		xMatchedToTable.setWidth("99%");
		xMatchedToTable.setHeight("99%");
		south.appendChild(xMatchedToTable);
		south.setHeight("44%");
		
		center = new Center();
		centerLayout.appendChild(center);
		center.setStyle("border: none");
		center.setFlex(false);
//		center.setHeight("6%");
		center.appendChild(xPanel);
		xPanel.appendChild(sameBPartner);
		xPanel.appendChild(new Space());
		xPanel.appendChild(sameProduct);
		xPanel.appendChild(new Space());
		xPanel.appendChild(sameQty);
		xPanel.setHeight("50px");
		xPanel.appendChild(new Separator());
		xPanel.appendChild(xMatchedToBorder);
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
			new ColumnInfo(Msg.translate(Env.getCtx(), "Matched"),      ".", Double.class)
		};

		xMatchedTable.prepareTable(layout, "", "", false, "");
		xMatchedToTable.prepareTable(layout, "", "", true, "");

		matchFrom.setSelectedIndex(0);
		//  Listener
		matchFrom.addActionListener(this);
		matchTo.addActionListener(this);
		bSearch.addActionListener(this);
		xMatchedTable.addEventListener(Events.ON_SELECT, this);
		xMatchedToTable.getModel().addTableModelListener(this);
		bProcess.addActionListener(this);
		sameBPartner.addActionListener(this);
		sameProduct.addActionListener(this);
		sameQty.addActionListener(this);
		
		//  Init Yvonne
		String selection = (String)matchFrom.getSelectedItem().getValue();
		SimpleListModel model = new SimpleListModel(cmd_matchFrom((String)matchFrom.getSelectedItem().getLabel()));
		matchTo.setItemRenderer(model);
		matchTo.setModel(model);		
		//  Set Title
		xMatchedBorder.setValue(selection);
		//  Reset Table
		xMatchedTable.setRowCount(0);
		//  sync To
		matchTo.setSelectedIndex(0);
		cmd_matchTo();

		statusBar.setStatusLine("");
		statusBar.setStatusDB("0");
	}   //  dynInit

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		SessionManager.getAppDesktop().closeActiveWindow();
	}	//	dispose

	
	/**************************************************************************
	 *  Action Listener
	 *  @param e event
	 */
	public void onEvent (Event e)
	{
		Integer product = onlyProduct.getValue()!=null?(Integer)onlyProduct.getValue():null;
		Integer vendor = onlyVendor.getValue()!=null?(Integer)onlyVendor.getValue():null;
		Timestamp from = dateFrom.getValue()!=null?(Timestamp)dateFrom.getValue():null;
		Timestamp to = dateTo.getValue()!=null?(Timestamp)dateTo.getValue():null;
		
		if (e.getTarget() == matchFrom) {
			//cmd_matchFrom((String)matchFrom.getSelectedItem().getLabel());
			String selection = (String)matchFrom.getSelectedItem().getValue();
			SimpleListModel model = new SimpleListModel(cmd_matchFrom((String)matchFrom.getSelectedItem().getLabel()));
			matchTo.setItemRenderer(model);
			matchTo.setModel(model);		
			//  Set Title
			xMatchedBorder.setValue(selection);
			//  Reset Table
			xMatchedTable.setRowCount(0);
			//  sync To
			matchTo.setSelectedIndex(0);
			cmd_matchTo();
			
		}
		else if (e.getTarget() == matchTo)
			cmd_matchTo();
		else if (e.getTarget() == bSearch)
		{
			//cmd_search();
			xMatchedTable = (WListbox)cmd_search(xMatchedTable, matchFrom.getSelectedIndex(), (String)matchTo.getSelectedItem().getLabel(), product, vendor, from, to, matchMode.getSelectedIndex() == MODE_MATCHED);

			xMatched.setValue(Env.ZERO);
			//  Status Info
			statusBar.setStatusLine(matchFrom.getSelectedItem().getLabel()
				+ "# = " + xMatchedTable.getRowCount(),
				xMatchedTable.getRowCount() == 0);
			statusBar.setStatusDB("0");
			cmd_searchTo();
		}
		else if (e.getTarget() == bProcess)
		{
			//cmd_process();
			cmd_process(xMatchedTable, xMatchedToTable, matchMode.getSelectedIndex(), matchFrom.getSelectedIndex(), matchTo.getSelectedItem(), m_xMatched);
			xMatchedTable = (WListbox) cmd_search(xMatchedTable, matchFrom.getSelectedIndex(), (String)matchTo.getSelectedItem().getLabel(), product, vendor, from, to, matchMode.getSelectedIndex() == MODE_MATCHED);
			xMatched.setValue(Env.ZERO);
			//  Status Info
			statusBar.setStatusLine(matchFrom.getSelectedItem().getLabel()
				+ "# = " + xMatchedTable.getRowCount(),
				xMatchedTable.getRowCount() == 0);
			statusBar.setStatusDB("0");
			cmd_searchTo();
		}
		else if (e.getTarget() == sameBPartner
			|| e.getTarget() == sameProduct
			|| e.getTarget() == sameQty)
			cmd_searchTo();
		else if (AEnv.contains(xMatchedTable, e.getTarget()))
			cmd_searchTo();
	}   //  actionPerformed

	
	/**
	 *  Match To Changed - set Title
	 */
	private void cmd_matchTo()
	{
	//	log.fine( "VMatch.cmd_matchTo");
		int index = matchTo.getSelectedIndex();
		String selection = (String)matchTo.getModel().getElementAt(index);
		xMatchedToBorder.setValue(selection);
		//  Reset Table
		xMatchedToTable.setRowCount(0);
	}   //  cmd_matchTo
	

	/**
	 *  Fill xMatchedTo
	 */
	private void cmd_searchTo()
	{
		int row = xMatchedTable.getSelectedRow();
		log.config("Row=" + row);

		double qty = 0.0;
		if (row < 0)
		{
			xMatchedToTable.setRowCount(0);
		}
		else
		{
			//  ** Create SQL **
			String displayString = (String)matchTo.getSelectedItem().getLabel();
			int matchToType = matchFrom.getSelectedIndex();
			double docQty = ((Double)xMatchedTable.getValueAt(row, I_QTY)).doubleValue();
			double matchedQty = ((Double)xMatchedTable.getValueAt(row, I_MATCHED)).doubleValue();
			qty = docQty - matchedQty;
			xMatchedToTable = (WListbox) cmd_searchTo(xMatchedTable, xMatchedToTable, displayString, matchToType, sameBPartner.isSelected(), sameProduct.isSelected(), sameQty.isSelected(), matchMode.getSelectedIndex() == MODE_MATCHED);
		}
		//  Display To be Matched Qty
		m_xMatched = new BigDecimal (qty);
		xMatched.setValue(m_xMatched);
		xMatchedTo.setValue(Env.ZERO);
		difference.setValue(m_xMatched);
		//  Status Info
		statusBar.setStatusLine(matchFrom.getSelectedItem().getLabel()
			+ "# = " + xMatchedTable.getRowCount() + " - "
			+ getMatchToLabel()
			+  "# = " + xMatchedToTable.getRowCount(),
			xMatchedToTable.getRowCount() == 0);
		statusBar.setStatusDB("0");
	}   //  cmd_seachTo
	
	private String getMatchToLabel() {
		int index = matchTo.getSelectedIndex();
		return matchTo.getModel().getElementAt(index).toString();
	}

	/***************************************************************************
	 *  Table Model Listener - calculate matchd Qty
	 *  @param e event
	 */
	public void tableChanged (WTableModelEvent e)
	{
		if (e.getColumn() != 0)
			return;
		log.config("Row=" + e.getFirstRow() + "-" + e.getLastRow() + ", Col=" + e.getColumn()
			+ ", Type=" + e.getType());

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
		//  Status
		statusBar.setStatusDB(noRows + "");
	}   //  tableChanged


	public ADForm getForm() {
		return form;
	}
	
}
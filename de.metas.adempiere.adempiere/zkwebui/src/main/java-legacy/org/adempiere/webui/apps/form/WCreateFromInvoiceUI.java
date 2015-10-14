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
package org.adempiere.webui.apps.form;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.grid.CreateFromInvoice;
import org.compiere.model.GridTab;
import org.compiere.model.MDocType;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zul.Space;

public class WCreateFromInvoiceUI extends CreateFromInvoice implements EventListener, ValueChangeListener
{
	private static final long serialVersionUID = 1L;
	
	private WCreateFromWindow window;
	
	public WCreateFromInvoiceUI(GridTab tab) 
	{
		super(tab);
		log.info(getGridTab().toString());
		
		window = new WCreateFromWindow(this, getGridTab().getWindowNo());
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
			zkInit();
			setInitOK(true);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			setInitOK(false);
		}
		AEnv.showWindow(window);
	}
	
	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
		
	protected Label bPartnerLabel = new Label();
	protected WEditor bPartnerField;
	
	protected Label orderLabel = new Label();
	protected Listbox orderField = ListboxFactory.newDropdownListbox();
	
	protected Label shipmentLabel = new Label();
	protected Listbox shipmentField = ListboxFactory.newDropdownListbox();
    
    /** Label for the rma selection */
    protected Label rmaLabel = new Label();
    /** Combo box for selecting RMA document */
    protected Listbox rmaField = ListboxFactory.newDropdownListbox();
	
	/**
	 *  Dynamic Init
	 *  @throws Exception if Lookups cannot be initialized
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception
	{
		log.config("");
		
		super.dynInit();
		
		window.setTitle(getTitle());

		// RMA Selection option should only be available for AP Credit Memo
		Integer docTypeId = (Integer)getGridTab().getValue("C_DocTypeTarget_ID");
		MDocType docType = MDocType.get(Env.getCtx(), docTypeId);
		if (!MDocType.DOCBASETYPE_APCreditMemo.equals(docType.getDocBaseType()))
		{
			rmaLabel.setVisible(false);
		    rmaField.setVisible(false);
		}
		
		initBPartner(true);
		bPartnerField.addValueChangeListener(this);
		
		return true;
	}   //  dynInit
	
	protected void zkInit() throws Exception
	{
		bPartnerLabel.setText(Msg.getElement(Env.getCtx(), "C_BPartner_ID"));
		orderLabel.setText(Msg.getElement(Env.getCtx(), "C_Order_ID", false));
		shipmentLabel.setText(Msg.getElement(Env.getCtx(), "M_InOut_ID", false));
        rmaLabel.setText(Msg.translate(Env.getCtx(), "M_RMA_ID"));
        
		Borderlayout parameterLayout = new Borderlayout();
		parameterLayout.setHeight("110px");
		parameterLayout.setWidth("100%");
    	Panel parameterPanel = window.getParameterPanel();
		parameterPanel.appendChild(parameterLayout);
		
		Grid parameterStdLayout = GridFactory.newGridLayout();
    	Panel parameterStdPanel = new Panel();
		parameterStdPanel.appendChild(parameterStdLayout);

		Center center = new Center();
		parameterLayout.appendChild(center);
		center.appendChild(parameterStdPanel);
		
		Rows rows = (Rows) parameterStdLayout.newRows();
		Row row = rows.newRow();
		row.appendChild(bPartnerLabel.rightAlign());
		if (bPartnerField != null)
			row.appendChild(bPartnerField.getComponent());
		row.appendChild(orderLabel.rightAlign());
		row.appendChild(orderField);
		
		row = rows.newRow();
		row.appendChild(new Space());
		row.appendChild(new Space());
		row.appendChild(shipmentLabel.rightAlign());
		row.appendChild(shipmentField);				
        
        // Add RMA document selection to panel
		row = rows.newRow();
		row.appendChild(new Space());
		row.appendChild(new Space());
        row.appendChild(rmaLabel.rightAlign());
        row.appendChild(rmaField);
	}

	private boolean 	m_actionActive = false;
	
	/**
	 *  Action Listener
	 *  @param e event
	 * @throws Exception 
	 */
	public void onEvent(Event e) throws Exception
	{
		if (m_actionActive)
			return;
		m_actionActive = true;
		
		//  Order
		if (e.getTarget().equals(orderField))
		{
			ListItem li = orderField.getSelectedItem();
			int C_Order_ID = 0;
			if (li != null && li.getValue() != null)
				C_Order_ID = ((Integer) li.getValue()).intValue();
			//  set Invoice, RMA and Shipment to Null
			rmaField.setSelectedIndex(-1);
			shipmentField.setSelectedIndex(-1);
			loadOrder(C_Order_ID, true);
		}
		//  Shipment
		else if (e.getTarget().equals(shipmentField))
		{
			ListItem li = shipmentField.getSelectedItem();
			int M_InOut_ID = 0;
			if (li != null && li.getValue() != null)
				M_InOut_ID = ((Integer) li.getValue()).intValue();
			//  set Order, RMA and Invoice to Null
			orderField.setSelectedIndex(-1);
			rmaField.setSelectedIndex(-1);
			loadShipment(M_InOut_ID);
		}
		//  RMA
		else if (e.getTarget().equals(rmaField))
		{
			ListItem li = rmaField.getSelectedItem();
		    int M_RMA_ID = 0;
		    if (li != null && li.getValue() != null)
		        M_RMA_ID = ((Integer) li.getValue()).intValue();
		    //  set Order and Invoice to Null
		    orderField.setSelectedIndex(-1);
		    shipmentField.setSelectedIndex(-1);
		    loadRMA(M_RMA_ID);
		}
		m_actionActive = false;
	}
	
	/**
	 *  Change Listener
	 *  @param e event
	 */
	public void valueChange (ValueChangeEvent e)
	{
		log.config(e.getPropertyName() + "=" + e.getNewValue());

		//  BPartner - load Order/Invoice/Shipment
		if (e.getPropertyName().equals("C_BPartner_ID"))
		{
			int C_BPartner_ID = ((Integer)e.getNewValue()).intValue();
			initBPOrderDetails (C_BPartner_ID, true);
		}
		window.tableChanged(null);
	}   //  vetoableChange
	
	/**************************************************************************
	 *  Load BPartner Field
	 *  @param forInvoice true if Invoices are to be created, false receipts
	 *  @throws Exception if Lookups cannot be initialized
	 */
	protected void initBPartner (boolean forInvoice) throws Exception
	{
		//  load BPartner
		int AD_Column_ID = 3499;        //  C_Invoice.C_BPartner_ID
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		bPartnerField = new WSearchEditor ("C_BPartner_ID", true, false, true, lookup);
		//
		int C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_BPartner_ID");
		bPartnerField.setValue(new Integer(C_BPartner_ID));

		//  initial loading
		initBPOrderDetails(C_BPartner_ID, forInvoice);
	}   //  initBPartner

	/**
	 *  Load PBartner dependent Order/Invoice/Shipment Field.
	 *  @param C_BPartner_ID BPartner
	 *  @param forInvoice for invoice
	 */
	protected void initBPOrderDetails (int C_BPartner_ID, boolean forInvoice)
	{
		log.config("C_BPartner_ID=" + C_BPartner_ID);
		KeyNamePair pp = new KeyNamePair(0,"");
		//  load PO Orders - Closed, Completed
		orderField.removeActionListener(this);
		orderField.removeAllItems();
		orderField.addItem(pp);
		
		ArrayList<KeyNamePair> list = loadOrderData(C_BPartner_ID, forInvoice, false);
		for(KeyNamePair knp : list)
			orderField.addItem(knp);
		
		orderField.setSelectedIndex(0);
		orderField.addActionListener(this);

		initBPDetails(C_BPartner_ID);
	}   //  initBPartnerOIS
	
	public void initBPDetails(int C_BPartner_ID) 
	{
		initBPShipmentDetails(C_BPartner_ID);
		initBPRMADetails(C_BPartner_ID);
	}

	/**
	 * Load PBartner dependent Order/Invoice/Shipment Field.
	 * @param C_BPartner_ID
	 */
	private void initBPShipmentDetails(int C_BPartner_ID)
	{
		log.config("C_BPartner_ID" + C_BPartner_ID);

		//  load Shipments (Receipts) - Completed, Closed
		shipmentField.removeActionListener(this);
		shipmentField.removeAllItems();
		//	None
		KeyNamePair pp = new KeyNamePair(0,"");
		shipmentField.addItem(pp);
		
		ArrayList<KeyNamePair> list = loadShipmentData(C_BPartner_ID);
		for(KeyNamePair knp : list)
			shipmentField.addItem(knp);
		
		shipmentField.setSelectedIndex(0);
		shipmentField.addActionListener(this);
	}
	
	/**
	 * Load RMA that are candidates for shipment
	 * @param C_BPartner_ID BPartner
	 */
	private void initBPRMADetails(int C_BPartner_ID)
	{
	    rmaField.removeActionListener(this);
	    rmaField.removeAllItems();
	    //  None
	    KeyNamePair pp = new KeyNamePair(0,"");
	    rmaField.addItem(pp);
	    
	    ArrayList<KeyNamePair> list = loadRMAData(C_BPartner_ID);
		for(KeyNamePair knp : list)
			rmaField.addItem(knp);
		
	    rmaField.setSelectedIndex(0);
	    rmaField.addActionListener(this);
	}

	/**
	 *  Load Data - Order
	 *  @param C_Order_ID Order
	 *  @param forInvoice true if for invoice vs. delivery qty
	 */
	protected void loadOrder (int C_Order_ID, boolean forInvoice)
	{
		loadTableOIS(getOrderData(C_Order_ID, forInvoice));
	}   //  LoadOrder
	
	protected void loadRMA (int M_RMA_ID)
	{
		loadTableOIS(getRMAData(M_RMA_ID));
	}
	
	protected void loadShipment (int M_InOut_ID)
	{
		loadTableOIS(getShipmentData(M_InOut_ID));
	}
	
	/**
	 *  Load Order/Invoice/Shipment data into Table
	 *  @param data data
	 */
	protected void loadTableOIS (Vector<?> data)
	{
		window.getWListbox().clear();
		
		//  Remove previous listeners
		window.getWListbox().getModel().removeTableModelListener(window);
		//  Set Model
		ListModelTable model = new ListModelTable(data);
		model.addTableModelListener(window);
		window.getWListbox().setData(model, getOISColumnNames());
		//
		
		configureMiniTable(window.getWListbox());
	}   //  loadOrder
	
	public void showWindow()
	{
		window.setVisible(true);
	}
	
	public void closeWindow()
	{
		window.dispose();
	}
}

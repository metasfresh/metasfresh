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

import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.grid.CreateFromRMA;
import org.compiere.model.GridTab;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;

public class WCreateFromRMAUI extends CreateFromRMA implements ValueChangeListener
{
	private static final long serialVersionUID = 1L;
	
	private WCreateFromWindow window;
	
	public WCreateFromRMAUI(GridTab tab) 
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
		
		initBPartner(true);
		bPartnerField.addValueChangeListener(this);
		
		loadRMA();
		
		return true;
	}   //  dynInit
	
	protected void zkInit() throws Exception
	{
		bPartnerLabel.setText(Msg.getElement(Env.getCtx(), "C_BPartner_ID"));
        
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
			loadRMA();
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
	}   //  initBPartner
	
	protected void loadRMA()
	{
		loadTableOIS(getRMAData());
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

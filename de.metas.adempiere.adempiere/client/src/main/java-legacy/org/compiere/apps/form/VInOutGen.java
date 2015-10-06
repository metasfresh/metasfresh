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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.logging.Level;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.apps.ADialog;
import org.compiere.grid.ed.VComboBox;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MOrder;
import org.compiere.model.MRMA;
import org.compiere.swing.CLabel;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

/**
 * Generate Shipment (manual) view class
 * 
 */
public class VInOutGen extends InOutGen implements FormPanel, ActionListener, VetoableChangeListener
{
	private VGenPanel panel;
	
	/**	Window No			*/
	private int         	m_WindowNo = 0;
	/**	FormFrame			*/
	private FormFrame 		m_frame;

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(VInOutGen.class);
	//

	private CLabel lWarehouse = new CLabel();
	private VLookup fWarehouse;
	private CLabel lBPartner = new CLabel();
	private VLookup fBPartner;	
	private CLabel     lDocType = new CLabel();
	private VComboBox  cmbDocType = new VComboBox();
	private CLabel     lDocAction = new CLabel();
	private VLookup    docAction;
	
	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame frame
	 */
	public void init (int WindowNo, FormFrame frame)
	{
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", "Y");

		panel = new VGenPanel(this, WindowNo, frame);

		try
		{
			super.dynInit();
			dynInit();
			jbInit();
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, "init", ex);
		}
	}	//	init
	
	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	}	//	dispose
	
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
		lWarehouse.setLabelFor(fWarehouse);
		lBPartner.setLabelFor(fBPartner);
		lBPartner.setText(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		lDocAction.setLabelFor(docAction);
		lDocAction.setText(Msg.translate(Env.getCtx(), "DocAction"));
		lDocType.setLabelFor(cmbDocType);
		
		panel.getParameterPanel().add(lWarehouse, null);
		panel.getParameterPanel().add(fWarehouse, null);
		panel.getParameterPanel().add(lBPartner, null);
		panel.getParameterPanel().add(fBPartner, null);
		panel.getParameterPanel().add(lDocType, null);
		panel.getParameterPanel().add(cmbDocType, null);
		panel.getParameterPanel().add(lDocAction, null);
		panel.getParameterPanel().add(docAction, null);
	}	//	jbInit
	
	/**
	 *	Fill Picks.
	 *		Column_ID from C_Order
	 *  @throws Exception if Lookups cannot be initialized
	 */
	public void dynInit() throws Exception
	{
		//	C_OrderLine.M_Warehouse_ID
		MLookup orgL = MLookupFactory.get (Env.getCtx(), m_WindowNo, 0, 2223, DisplayType.TableDir);
		fWarehouse = new VLookup ("M_Warehouse_ID", true, false, true, orgL);
		lWarehouse.setText(Msg.translate(Env.getCtx(), "M_Warehouse_ID"));
		fWarehouse.addVetoableChangeListener(this);
		setM_Warehouse_ID(fWarehouse.getValue());
		//   Document Action Prepared/ Completed
		MLookup docActionL = MLookupFactory.get(Env.getCtx(), m_WindowNo, 4324 /* M_InOut.DocStatus */, 
				DisplayType.List, Env.getLanguage(Env.getCtx()), "DocAction", 135 /* _Document Action */,
				false, "AD_Ref_List.Value IN ('CO','PR')");
		docAction = new VLookup("DocAction", true, false, true,docActionL);
		docAction.addVetoableChangeListener(this);
		//		C_Order.C_BPartner_ID
		MLookup bpL = MLookupFactory.get (Env.getCtx(), m_WindowNo, 0, 2762, DisplayType.Search);
		fBPartner = new VLookup ("C_BPartner_ID", false, false, true, bpL);
		lBPartner.setText(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		fBPartner.addVetoableChangeListener(this);
		//Document Type Sales Order/Vendor RMA
		lDocType.setText(Msg.translate(Env.getCtx(), "C_DocType_ID"));
		cmbDocType.addItem(new KeyNamePair(MOrder.Table_ID, Msg.translate(Env.getCtx(), "Order")));
		cmbDocType.addItem(new KeyNamePair(MRMA.Table_ID, Msg.translate(Env.getCtx(), "VendorRMA")));
		cmbDocType.addActionListener(this);
		
		panel.getStatusBar().setStatusLine(Msg.getMsg(Env.getCtx(), "InOutGenerateSel"));//@@
	}	//	fillPicks
	
	public void executeQuery()
	{
		KeyNamePair docTypeKNPair = (KeyNamePair)cmbDocType.getSelectedItem();
		executeQuery(docTypeKNPair, panel.getMiniTable());
	}   //  executeQuery
	
	/**
	 *	Action Listener
	 *  @param e event
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (cmbDocType.equals(e.getSource()))
		{
		   executeQuery();
		    return;
		}
		
		try
		{
			validate();
		}
		catch(Exception ex)
		{
			ADialog.error(m_WindowNo, this.panel, "Error", ex.getLocalizedMessage());
		}
	}	//	actionPerformed
	
	public void validate()
	{
		panel.saveSelection();
		
		if (getM_Warehouse_ID() <= 0)
		{
			throw new FillMandatoryException("M_Warehouse_ID");
		}
		
		ArrayList<Integer> selection = getSelection();
		if (selection != null
			&& selection.size() > 0
			&& isSelectionActive())	//	on selection tab
		{
			panel.generate();
		}
		else
		{
			panel.dispose();
		}
	}

	/**
	 *	Vetoable Change Listener - requery
	 *  @param e event
	 */
	public void vetoableChange(PropertyChangeEvent e)
	{
		log.info(e.getPropertyName() + "=" + e.getNewValue());
		if (e.getPropertyName().equals("M_Warehouse_ID"))
			setM_Warehouse_ID(e.getNewValue());
		if (e.getPropertyName().equals("C_BPartner_ID"))
		{
			m_C_BPartner_ID = e.getNewValue();
			fBPartner.setValue(m_C_BPartner_ID);	//	display value
		}
		executeQuery();
	}	//	vetoableChange
	
	/**************************************************************************
	 *	Generate Shipments
	 */
	public String generate()
	{
		KeyNamePair docTypeKNPair = (KeyNamePair)cmbDocType.getSelectedItem();
		String docActionSelected = (String)docAction.getValue();	
		return generate(panel.getStatusBar(), docTypeKNPair, docActionSelected);
	}	//	generateShipments
}

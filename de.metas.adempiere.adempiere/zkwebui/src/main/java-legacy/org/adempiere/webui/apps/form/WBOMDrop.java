/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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

/**
 * 2007, Modified by Posterita Ltd.
 */

package org.adempiere.webui.apps.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;
import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;



public class WBOMDrop extends ADForm implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5065364554398280623L;

	/**	Product to create BOMs from	*/
	private MProduct m_product;
	
	/** BOM Qty						*/
	private BigDecimal m_qty = Env.ONE;
	
	/**	Line Counter				*/
	private int m_bomLine = 0;
	
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(WBOMDrop.class);
	
	/**	List of all selectors		*/
	private ArrayList<org.zkoss.zul.Checkbox> m_selectionList = new ArrayList<org.zkoss.zul.Checkbox>();
	
	/**	List of all quantities		*/
	private ArrayList<Decimalbox> m_qtyList = new ArrayList<Decimalbox>();
	
	/**	List of all products		*/
	private ArrayList<Integer> m_productList = new ArrayList<Integer>();
	
	/** Alternative Group Lists		*/
	private HashMap<String, Radiogroup> m_buttonGroups = new HashMap<String,Radiogroup>();

	private static final int WINDOW_WIDTH = 600;	//	width of the window
	
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	private Grid selectionPanel = GridFactory.newGridLayout();
	private Listbox productField = new Listbox();
	private Decimalbox productQty = new Decimalbox();
	private Listbox orderField = new Listbox();
	private Listbox invoiceField = new Listbox();
	private Listbox projectField = new Listbox();
	
	private Groupbox grpSelectionPanel = new Groupbox();
	
	private Groupbox grpSelectProd = new Groupbox();
	
	public WBOMDrop()
	{}
	
	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame parent frame
	 */
	protected void initForm()
	{
		log.info("");

		try
		{
			 confirmPanel = new ConfirmPanel(true);
			 
			//	Top Selection Panel
			createSelectionPanel(true, true, true);

			//	Center
			createMainPanel();

			confirmPanel.addActionListener(Events.ON_CLICK, this);
		}
		catch(Exception e)
		{
			log.error("", e);
		}
		//sizeIt();
	}	//	init

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		if (selectionPanel != null)
			selectionPanel.getChildren().clear();
		
		selectionPanel = null;
		
		if (m_selectionList != null)
			m_selectionList.clear();
		
		m_selectionList = null;
		
		if (m_productList != null)
			m_productList.clear();
		
		m_productList = null;
		
		if (m_qtyList != null)
			m_qtyList.clear();
		
		m_qtyList = null;
		
		if (m_buttonGroups != null)
			m_buttonGroups.clear();
		m_buttonGroups = null;
	}	//	dispose
	
	/**************************************************************************
	 * 	Create Selection Panel
	 *	@param order
	 *	@param invoice
	 *	@param project
	 */
	
	private void createSelectionPanel (boolean order, boolean invoice, boolean project)
	{
		Caption caption = new Caption(Msg.translate(Env.getCtx(), "Selection"));

//		grpSelectionPanel.setWidth("100%");
		grpSelectionPanel.appendChild(caption);
		grpSelectionPanel.appendChild(selectionPanel);
		
		productField.setRows(1);
		productField.setMold("select");
		
		KeyNamePair[] keyNamePair = getProducts();
		
		for (int i = 0; i < keyNamePair.length; i++)
		{
			productField.addItem(keyNamePair[i]);
		}
		
		Rows rows = selectionPanel.newRows();
		Row boxProductQty = rows.newRow();
		
		Label lblProduct = new Label(Msg.translate(Env.getCtx(), "M_Product_ID"));
		Label lblQty = new Label(Msg.translate(Env.getCtx(), "Qty"));
		productQty.setValue(new BigDecimal(1));
		productField.addEventListener(Events.ON_SELECT, this);
		productQty.addEventListener(Events.ON_CHANGE, this);
		
		productField.setWidth("99%");
		boxProductQty.appendChild(lblProduct.rightAlign());
		boxProductQty.appendChild(productField);
		boxProductQty.appendChild(lblQty.rightAlign());
		boxProductQty.appendChild(productQty);
		
		if (order)
		{
			keyNamePair = getOrders();
			
			orderField.setRows(1);
			orderField.setMold("select");
			orderField.setWidth("99%");
			
			for (int i = 0; i < keyNamePair.length; i++)
			{
				orderField.addItem(keyNamePair[i]);
			}

			Label lblOrder = new Label(Msg.translate(Env.getCtx(), "C_Order_ID"));

			Row boxOrder = rows.newRow();
			
			orderField.addEventListener(Events.ON_CLICK, this);
			
			boxOrder.appendChild(lblOrder.rightAlign());
			boxOrder.appendChild(orderField);
			boxOrder.appendChild(new Space());
			boxOrder.appendChild(new Space());
		}

		if (invoice)
		{
			invoiceField.setRows(1);
			invoiceField.setMold("select");
			invoiceField.setWidth("99%");
			
			keyNamePair = getInvoices();
			
			for (int i = 0; i < keyNamePair.length; i++)
			{
				invoiceField.addItem(keyNamePair[i]);
			}
			
			Label lblInvoice = new Label(Msg.translate(Env.getCtx(), "C_Invoice_ID"));
			
			Row boxInvoices = rows.newRow();
			
			invoiceField.addEventListener(Events.ON_SELECT, this);
			
			boxInvoices.appendChild(lblInvoice.rightAlign());
			boxInvoices.appendChild(invoiceField);
			boxInvoices.appendChild(new Space());
			boxInvoices.appendChild(new Space());
		}
		
		if (project)
		{
			projectField.setRows(1);
			projectField.setMold("select");
			projectField.setWidth("99%");
			
			keyNamePair = getProjects();
			
			for (int i = 0; i < keyNamePair.length; i++)
			{
				projectField.addItem(keyNamePair[i]);
			}
			
			Label lblProject = new Label(Msg.translate(Env.getCtx(), "C_Project_ID"));
			
			Row boxProject = rows.newRow();
			
			projectField.addEventListener(Events.ON_SELECT, this);
			
			boxProject.appendChild(lblProject.rightAlign());
			boxProject.appendChild(projectField);
			boxProject.appendChild(new Space());
			boxProject.appendChild(new Space());			
		}
		
		//	Enabled in ActionPerformed
		confirmPanel.setEnabled("Ok", false);
	}	//	createSelectionPanel

	/**
	 * 	Get Array of BOM Products
	 *	@return products
	 */
	
	private KeyNamePair[] getProducts()
	{
		String sql = "SELECT M_Product_ID, Name "
			+ "FROM M_Product "
			+ "WHERE IsBOM='Y' AND IsVerified='Y' AND IsActive='Y' "
			+ "ORDER BY Name";
	
		return DB.getKeyNamePairs(Env.getUserRolePermissions().addAccessSQL(
			sql, "M_Product", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO), true);
	}	//	getProducts
	
	/**
	 * 	Get Array of open Orders
	 *	@return orders
	 */
	
	private KeyNamePair[] getOrders()
	{
		String sql = "SELECT C_Order_ID, DocumentNo || '_' || GrandTotal "
			+ "FROM C_Order "
			+ "WHERE Processed='N' AND DocStatus='DR' "
			+ "ORDER BY DocumentNo";
	
		return DB.getKeyNamePairs(Env.getUserRolePermissions().addAccessSQL(
			sql, "C_Order", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO), true);
	}	//	getOrders

	/**
	 * 	Get Array of open non service Projects
	 *	@return orders
	 */
	
	private KeyNamePair[] getProjects()
	{
		String sql = "SELECT C_Project_ID, Name "
			+ "FROM C_Project "
			+ "WHERE Processed='N' AND IsSummary='N' AND IsActive='Y'"
			+ " AND ProjectCategory<>'S' "
			+ "ORDER BY Name";
	
		return DB.getKeyNamePairs(Env.getUserRolePermissions().addAccessSQL(
			sql, "C_Project", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO), true);
	}	//	getProjects
	
	/**
	 * 	Get Array of open Invoices
	 *	@return invoices
	 */
	
	private KeyNamePair[] getInvoices()
	{
		String sql = "SELECT C_Invoice_ID, DocumentNo || '_' || GrandTotal "
			+ "FROM C_Invoice "
			+ "WHERE Processed='N' AND DocStatus='DR' "
			+ "ORDER BY DocumentNo";
	
		return DB.getKeyNamePairs(Env.getUserRolePermissions().addAccessSQL(
			sql, "C_Invoice", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO), true);
	}	//	getInvoices

	/**************************************************************************
	 * 	Create Main Panel.
	 * 	Called when changing Product
	 */
	
	private void createMainPanel ()
	{
		log.info(": " + m_product);
		this.getChildren().clear();
		//this.invalidate();
		//this.setBorder(null);
		
		m_selectionList.clear();
		m_productList.clear();
		m_qtyList.clear();
		m_buttonGroups.clear();
		
		this.appendChild(new Separator());
		this.appendChild(grpSelectionPanel);
		this.appendChild(new Separator());
		this.appendChild(grpSelectProd);
		this.appendChild(new Separator());
		this.appendChild(confirmPanel);
		this.appendChild(new Separator());
		this.setBorder("normal");
		
		Caption title = new Caption(Msg.getMsg(Env.getCtx(), "SelectProduct"));

		grpSelectProd.getChildren().clear();
		grpSelectProd.appendChild(title);
		
		if (m_product != null && m_product.get_ID() > 0)
		{
			title.setLabel(m_product.getName());
			
			if (m_product.getDescription() != null && m_product.getDescription().length() > 0)
				;//this.setsetToolTipText(m_product.getDescription());
			
			m_bomLine = 0;
			addBOMLines(m_product, m_qty);
		}
	}	//	createMainPanel

	/**
	 * 	Add BOM Lines to this.
	 * 	Called recursively
	 * 	@param product product
	 * 	@param qty quantity
	 */
	
	private void addBOMLines (MProduct product, BigDecimal qty)
	{
		MProductBOM[] bomLines = MProductBOM.getBOMLines(product);
		
		for (int i = 0; i < bomLines.length; i++)
		{
			grpSelectProd.appendChild(new Separator());
			addBOMLine (bomLines[i], qty);
			grpSelectProd.appendChild(new Separator());
		}
		
		log.debug("#" + bomLines.length);
	}	//	addBOMLines

	/**
	 * 	Add BOM Line to this.
	 * 	Calls addBOMLines if added product is a BOM
	 * 	@param line BOM Line
	 * 	@param qty quantity
	 */
	
	private void addBOMLine (MProductBOM line, BigDecimal qty)
	{
		log.debug(line.toString());
		String bomType = line.getBOMType();
		
		if (bomType == null)
			bomType = MProductBOM.BOMTYPE_StandardPart;
		//
		BigDecimal lineQty = line.getBOMQty().multiply(qty);
		MProduct product = line.getProduct();
		
		if (product == null)
			return;
		
		if (product.isBOM() && product.isVerified())
			addBOMLines (product, lineQty);		//	recursive
		else
			addDisplay (line.getM_Product_ID(),
				product.getM_Product_ID(), bomType, product.getName(), lineQty);
	}	//	addBOMLine

	/**
	 * 	Add Line to Display
	 *	@param parentM_Product_ID parent product
	 *	@param M_Product_ID product
	 *	@param bomType bom type
	 *	@param name name
	 *	@param lineQty qty
	 */
	
	private void addDisplay (int parentM_Product_ID,
		int M_Product_ID, String bomType, String name, BigDecimal lineQty)
	{
		log.debug("M_Product_ID=" + M_Product_ID + ",Type=" + bomType + ",Name=" + name + ",Qty=" + lineQty);
		
		boolean selected = true;
		
		Hbox boxQty = new Hbox();
		boxQty.setWidth("100%");
		boxQty.setWidths("10%, 40%, 50%");
		
		if (MProductBOM.BOMTYPE_StandardPart.equals(bomType))
		{
			String title = "";
			Checkbox cb = new Checkbox();
			cb.setLabel(title);
			cb.setChecked(true);
			cb.setEnabled(false);

			m_selectionList.add(cb);
			boxQty.appendChild(cb);
		}
		else if (MProductBOM.BOMTYPE_OptionalPart.equals(bomType))
		{
			String title = Msg.getMsg(Env.getCtx(), "Optional");
			Checkbox cb = new Checkbox();
			cb.setLabel(title);
			cb.setChecked(false);
			selected = false;
			cb.addEventListener(Events.ON_CHECK, this);
			
			m_selectionList.add(cb);
			boxQty.appendChild(cb);
		}
		else	//	Alternative
		{
			String title = Msg.getMsg(Env.getCtx(), "Alternative") + " " + bomType;
			Radio b = new Radio();
			b.setLabel(title);
			String groupName = String.valueOf(parentM_Product_ID) + "_" + bomType;
			Radiogroup group = m_buttonGroups.get(groupName);
			
			if (group == null)
			{
				log.debug("ButtonGroup=" + groupName);
				group = new Radiogroup();
				m_buttonGroups.put(groupName, group);
				group.appendChild(b);
				b.setSelected(true);		//	select first one
			}
			else
			{
				group.appendChild(b);
				b.setSelected(false);
				selected = false;
			}
			b.addEventListener(Events.ON_CLICK, this);
			m_selectionList.add(b);
			boxQty.appendChild(b);
		}
		
		//	Add to List & display
		m_productList.add (new Integer(M_Product_ID));
		Decimalbox qty = new Decimalbox();
		qty.setValue(lineQty);
		qty.setReadonly(!selected);
		m_qtyList.add(qty);
		
		Label label = new Label(name);
		HtmlBasedComponent c = (HtmlBasedComponent) label.rightAlign();
		c.setStyle(c.getStyle() + ";margin-right: 5px");
		boxQty.appendChild(c);
		boxQty.appendChild(qty);

		grpSelectProd.appendChild(boxQty);
	}	//	addDisplay

	/**************************************************************************
	 *	Action Listener
	 *  @param e event
	 */
	public void onEvent (Event e) throws Exception
	{
		log.info(e.getName());
		
		Object source = e.getTarget();

		//	Toggle Qty Enabled
		if (source instanceof Checkbox || source instanceof Radio)
		{
			cmd_selection (source);
			//	need to de-select the others in group	
			if (source instanceof Radio)
			{
				//	find Button Group
				Iterator<Radiogroup> it = m_buttonGroups.values().iterator();
				
				while (it.hasNext())
				{
					Radiogroup group = it.next();
					Enumeration en = (Enumeration) group.getChildren();
				
					while (en.hasMoreElements())
					{
						//	We found the group
						if (source == en.nextElement())
						{
							Enumeration info = (Enumeration) group.getChildren();
							
							while (info.hasMoreElements())
							{
								Object infoObj = info.nextElement();
								if (source != infoObj)
									cmd_selection(infoObj);
							}
						}
					}
				}
			}
		}	//	JCheckBox or JRadioButton
			
		//	Product / Qty
		else if (source == productField || source == productQty)
		{
			m_qty = productQty.getValue();
			
			ListItem listitem = productField.getSelectedItem();
			
			KeyNamePair pp = null;
			
			if (listitem != null)
				pp = listitem.toKeyNamePair();
			
			m_product = MProduct.get (Env.getCtx(), pp.getKey());
			createMainPanel();
			//sizeIt();
		}
		
		//	Order
		else if (source == orderField)
		{
			ListItem listitem = orderField.getSelectedItem();
			
			KeyNamePair pp = null;
			
			if (listitem != null)
				pp = listitem.toKeyNamePair();
			
			boolean valid = (pp != null && pp.getKey() > 0);
			
			if (invoiceField != null)
				invoiceField.setEnabled(!valid);
			if (projectField != null)
				projectField.setEnabled(!valid);
		}
		//	Invoice
		else if (source == invoiceField)
		{
			ListItem listitem = invoiceField.getSelectedItem();
			
			KeyNamePair pp = null;
						
			if (listitem != null)
				pp = listitem.toKeyNamePair();
			
			boolean valid = (pp != null && pp.getKey() > 0);
			
			if (orderField != null)
				orderField.setEnabled(!valid);
			if (projectField != null)
				projectField.setEnabled(!valid);
		}
		//	Project
		else if (source == projectField)
		{
			ListItem listitem = projectField.getSelectedItem();
			
			KeyNamePair pp = null;
			
			if (listitem != null)
				pp = listitem.toKeyNamePair();
			
			boolean valid = (pp != null && pp.getKey() > 0);
			//
			if (orderField != null)
				orderField.setEnabled(!valid);
			if (invoiceField != null)
				invoiceField.setEnabled(!valid);
		}
		//	OK
		else if (confirmPanel.getButton("Ok").equals(e.getTarget()))
		{
			if (cmd_save())
				SessionManager.getAppDesktop().closeActiveWindow();
		}
		else if (confirmPanel.getButton("Cancel").equals(e.getTarget()))
			SessionManager.getAppDesktop().closeActiveWindow();
			
		//	Enable OK
		boolean OK = m_product != null;
		
		if (OK)
		{
			KeyNamePair pp = null;
			
			if (orderField != null)
			{
				ListItem listitem = orderField.getSelectedItem();
				
				if (listitem != null)
					pp = listitem.toKeyNamePair();
			}
			
			if ((pp == null || pp.getKey() <= 0) && invoiceField != null)
			{
				ListItem listitem = invoiceField.getSelectedItem();
				
				if (listitem != null)
					pp = listitem.toKeyNamePair();
			}
			
			if ((pp == null || pp.getKey() <= 0) && projectField != null)
			{
				ListItem listitem = projectField.getSelectedItem();
				
				if (listitem != null)
					pp = listitem.toKeyNamePair();
			}
			OK = (pp != null && pp.getKey() > 0);
		}
		
		confirmPanel.setEnabled("Ok", OK);
	}	//	actionPerformed

	/**
	 * 	Enable/disable qty based on selection
	 *	@param source JCheckBox or JRadioButton
	 */
	
	private void cmd_selection (Object source)
	{
		for (int i = 0; i < m_selectionList.size(); i++)
		{
			if (source == m_selectionList.get(i))
			{
				boolean selected = isSelectionSelected(source);
				Decimalbox qty = m_qtyList.get(i);
				qty.setReadonly(!selected);
				return;
			}
		}
		log.error("not found - " + source);
	}	//	cmd_selection

	/**
	 * 	Is Selection Selected
	 *	@param source CheckBox or RadioButton
	 *	@return true if selected
	 */
	
	private boolean isSelectionSelected (Object source)
	{
		boolean retValue = false;
		
		if (source instanceof Checkbox)
			retValue = ((Checkbox)source).isChecked();
		else if (source instanceof Radio)
			retValue = ((Radio)source).isChecked();
		else
			log.error("Not valid - " + source);
		
		return retValue;
	}	//	isSelected

	/**************************************************************************
	 * 	Save Selection
	 * 	@return true if saved
	 */
	
	private boolean cmd_save()
	{
		ListItem listitem = orderField.getSelectedItem();
		
		KeyNamePair pp = null;
		
		if (listitem != null)
			pp = listitem.toKeyNamePair();
		
		if (pp != null && pp.getKey() > 0)
			return cmd_saveOrder (pp.getKey());
		
		listitem = invoiceField.getSelectedItem();
		
		pp = null;
		
		if (listitem != null)
			pp = listitem.toKeyNamePair();
		
		if (pp != null && pp.getKey() > 0)
			return cmd_saveInvoice (pp.getKey());
		
		listitem = projectField.getSelectedItem();
		
		pp = null;
		
		if (listitem != null)
			pp = listitem.toKeyNamePair();
		
		if (pp != null && pp.getKey() > 0)
			return cmd_saveProject (pp.getKey());
		
		log.error("Nothing selected");
		return false;
	}	//	cmd_save

	/**
	 * 	Save to Order
	 *	@param C_Order_ID id
	 *	@return true if saved
	 */
	
	private boolean cmd_saveOrder (int C_Order_ID)
	{
		log.info("C_Order_ID=" + C_Order_ID);
		MOrder order = new MOrder (Env.getCtx(), C_Order_ID, null);
		
		if (order.get_ID() == 0)
		{
			log.error("Not found - C_Order_ID=" + C_Order_ID);
			return false;
		}
		
		int lineCount = 0;
		
		//	for all bom lines
		for (int i = 0; i < m_selectionList.size(); i++)
		{
			if (isSelectionSelected(m_selectionList.get(i)))
			{
				BigDecimal qty = m_qtyList.get(i).getValue();
				int M_Product_ID = m_productList.get(i).intValue();
				//	Create Line
				MOrderLine ol = new MOrderLine (order);
				ol.setM_Product_ID(M_Product_ID, true);
				ol.setQty(qty);
				ol.setPrice();
				ol.setTax();
				if (ol.save())
					lineCount++;
				else
					log.error("Line not saved");
			}	//	line selected
		}	//	for all bom lines
		
		FDialog.info(-1, this, order.getDocumentInfo() + " " + Msg.translate(Env.getCtx(), "Inserted") + "=" + lineCount);
		log.info("#" + lineCount);
		return true;
	}	//	cmd_saveOrder

	/**
	 * 	Save to Invoice
	 *	@param C_Invoice_ID id
	 *	@return true if saved
	 */
	
	private boolean cmd_saveInvoice (int C_Invoice_ID)
	{
		log.info("C_Invoice_ID=" + C_Invoice_ID);
		MInvoice invoice = new MInvoice (Env.getCtx(), C_Invoice_ID, null);
		if (invoice.get_ID() == 0)
		{
			log.error("Not found - C_Invoice_ID=" + C_Invoice_ID);
			return false;
		}
		int lineCount = 0;
		
		//	for all bom lines
		for (int i = 0; i < m_selectionList.size(); i++)
		{
			if (isSelectionSelected(m_selectionList.get(i)))
			{
				BigDecimal qty = m_qtyList.get(i).getValue();
				int M_Product_ID = m_productList.get(i).intValue();
				//	Create Line
				MInvoiceLine il = new MInvoiceLine (invoice);
				il.setM_Product_ID(M_Product_ID, true);
				il.setQty(qty);
				il.setPrice();
				il.setTax();
				if (il.save())
					lineCount++;
				else
					log.error("Line not saved");
			}	//	line selected
		}	//	for all bom lines
		
		FDialog.info(-1, this, invoice.getDocumentInfo() +  " " + Msg.translate(Env.getCtx(), "Inserted") + "=" + lineCount);
		log.info("#" + lineCount);
		return true;
	}	//	cmd_saveInvoice

	/**
	 * 	Save to Project
	 *	@param C_Project_ID id
	 *	@return true if saved
	 */
	private boolean cmd_saveProject (int C_Project_ID)
	{
		log.info("C_Project_ID=" + C_Project_ID);
		MProject project = new MProject (Env.getCtx(), C_Project_ID, null);
		if (project.get_ID() == 0)
		{
			log.error("Not found - C_Project_ID=" + C_Project_ID);
			return false;
		}
		int lineCount = 0;
		
		//	for all bom lines
		for (int i = 0; i < m_selectionList.size(); i++)
		{
			if (isSelectionSelected(m_selectionList.get(i)))
			{
				BigDecimal qty = m_qtyList.get(i).getValue();
				int M_Product_ID = m_productList.get(i).intValue();
				//	Create Line
				MProjectLine pl = new MProjectLine (project);
				pl.setM_Product_ID(M_Product_ID);
				pl.setPlannedQty(qty);

				if (pl.save())
					lineCount++;
				else
					log.error("Line not saved");
			}	//	line selected
		}	//	for all bom lines
		
		FDialog.info(-1, this, project.getName() + " " + Msg.translate(Env.getCtx(), "Inserted") + "=" + lineCount);
		log.info("#" + lineCount);
		return true;
	}	//	cmd_saveProject
}

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
package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VNumber;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;
import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/**
 *	Drop BOM
 *	
 *  @author Jorg Janke
 *  @version $Id: VBOMDrop.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class VBOMDrop extends CPanel 
	implements FormPanel, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3135475369002895149L;


	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame parent frame
	 */
	@Override
	public void init (int WindowNo, FormFrame frame)
	{
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		try
		{
			//	Top Selection Panel
			createSelectionPanel(true, true, true);
			m_frame.getContentPane().add(selectionPanel, BorderLayout.NORTH);
			//	Center
			createMainPanel();
			CScrollPane scroll = new CScrollPane (this);
			m_frame.getContentPane().add(scroll, BorderLayout.CENTER);
			confirmPanel.setActionListener(this);
			//	South
			m_frame.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		}
		catch(Exception e)
		{
			log.error("", e);
		}
		sizeIt();
	}	//	init

	/**
	 * 	Size Window
	 */
	private void sizeIt()
	{
		//	Frame
		m_frame.pack();
		Dimension size = m_frame.getPreferredSize();
		size.width = WINDOW_WIDTH;
		m_frame.setSize(size);
	}	//	size
	
	/**
	 * 	Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
		removeAll();
		if (selectionPanel != null)
			selectionPanel.removeAll();
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

	/**	Window No					*/
	private int         m_WindowNo = 0;
	/**	FormFrame					*/
	private FormFrame 	m_frame;
	/**	Product to create BOMs from	*/
	private MProduct 	m_product;
	/** BOM Qty						*/
	private BigDecimal	m_qty = Env.ONE;
	/**	Line Counter				*/
	private int			m_bomLine = 0;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VBOMDrop.class);
	
	/**	List of all selectors		*/
	private ArrayList<JToggleButton>	m_selectionList = new ArrayList<JToggleButton>();
	/**	List of all quantities		*/
	private ArrayList<VNumber>		m_qtyList = new ArrayList<VNumber>();
	/**	List of all products		*/
	private ArrayList<Integer>		m_productList = new ArrayList<Integer>();
	/** Alternative Group Lists		*/
	private HashMap<String,ButtonGroup>	m_buttonGroups = new HashMap<String,ButtonGroup>();

	private static final int	WINDOW_WIDTH = 600;	//	width of the window
	//
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	private CPanel selectionPanel = new CPanel(new ALayout());
	private CComboBox productField;
	private VNumber productQty = new VNumber("Qty", true, false, true, DisplayType.Quantity, Msg.translate(Env.getCtx(), "Qty"));
	private CComboBox orderField;
	private CComboBox invoiceField;
	private CComboBox projectField;

	
	/**************************************************************************
	 * 	Create Selection Panel
	 *	@param order
	 *	@param invoice
	 *	@param project
	 */
	private void createSelectionPanel (boolean order, boolean invoice, boolean project)
	{
		int row = 0;
		selectionPanel.setBorder(new TitledBorder(Msg.translate(Env.getCtx(), "Selection")));
		productField = new CComboBox(getProducts());
		CLabel label = new CLabel(Msg.translate(Env.getCtx(), "M_Product_ID"));
		label.setLabelFor(productField);
		selectionPanel.add(label, new ALayoutConstraint(row++, 0));
		selectionPanel.add(productField);
		productField.addActionListener(this);
		//	Qty
		label = new CLabel (productQty.getTitle());
		label.setLabelFor(productQty);		
		selectionPanel.add(label);
		selectionPanel.add(productQty);
		productQty.setValue(Env.ONE);
		productQty.addActionListener(this);
		
		if (order)
		{
			orderField = new CComboBox(getOrders());
			label = new CLabel (Msg.translate(Env.getCtx(), "C_Order_ID"));
			label.setLabelFor(orderField);
			selectionPanel.add(label, new ALayoutConstraint(row++, 0));
			selectionPanel.add(orderField);
			orderField.addActionListener(this);
		}
		if (invoice)
		{
			invoiceField = new CComboBox(getInvoices());
			label = new CLabel (Msg.translate(Env.getCtx(), "C_Invoice_ID"));
			label.setLabelFor(invoiceField);
			selectionPanel.add(label, new ALayoutConstraint(row++, 0));
			selectionPanel.add(invoiceField);
			invoiceField.addActionListener(this);
		}
		if (project)
		{
			projectField = new CComboBox(getProjects());
			label = new CLabel (Msg.translate(Env.getCtx(), "C_Project_ID"));
			label.setLabelFor(projectField);
			selectionPanel.add(label, new ALayoutConstraint(row++, 0));
			selectionPanel.add(projectField);
			projectField.addActionListener(this);
		}
		//	Enabled in ActionPerformed
		confirmPanel.getOKButton().setEnabled(false);
		//	Size
		Dimension size = selectionPanel.getPreferredSize();
		size.width = WINDOW_WIDTH;
		selectionPanel.setPreferredSize(size);
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
		this.removeAll();
		this.setPreferredSize(null);
		this.invalidate();
		this.setBorder(null);
		//
		m_selectionList.clear();
		m_productList.clear();
		m_qtyList.clear();
		m_buttonGroups.clear();
		//
		this.setLayout (new ALayout());
		String title = Msg.getMsg(Env.getCtx(), "SelectProduct");
		if (m_product != null && m_product.get_ID() > 0)
		{
			title = m_product.getName();
			if (m_product.getDescription() != null && m_product.getDescription().length() > 0)
				this.setToolTipText(m_product.getDescription());
			m_bomLine = 0;
			addBOMLines(m_product, m_qty);
		}
		this.setBorder (new TitledBorder(title));
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
			addBOMLine (bomLines[i], qty);
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
		//
		boolean selected = true;
		if (MProductBOM.BOMTYPE_StandardPart.equals(bomType))
		{
			String title = "";
			JCheckBox cb = new JCheckBox(title);
			cb.setSelected(true);
			cb.setEnabled(false);
		//	cb.addActionListener(this);		//	will not change
			m_selectionList.add(cb);
			this.add(cb, new ALayoutConstraint(m_bomLine++, 0));
		}
		else if (MProductBOM.BOMTYPE_OptionalPart.equals(bomType))
		{
			String title = Msg.getMsg(Env.getCtx(), "Optional");
			JCheckBox cb = new JCheckBox(title);
			cb.setSelected(false);
			selected = false;
			cb.addActionListener(this);
			m_selectionList.add(cb);
			this.add(cb, new ALayoutConstraint(m_bomLine++, 0));
		}
		else	//	Alternative
		{
			String title = Msg.getMsg(Env.getCtx(), "Alternative") + " " + bomType;
			JRadioButton b = new JRadioButton(title);
			String groupName = String.valueOf(parentM_Product_ID) + "_" + bomType;
			ButtonGroup group = m_buttonGroups.get(groupName);
			if (group == null)
			{
				log.debug("ButtonGroup=" + groupName);
				group = new ButtonGroup();
				m_buttonGroups.put(groupName, group);
				group.add(b);
				b.setSelected(true);		//	select first one
			}
			else
			{
				group.add(b);
				b.setSelected(false);
				selected = false;
			}
			b.addActionListener(this);
			m_selectionList.add(b);
			this.add(b, new ALayoutConstraint(m_bomLine++, 0));
		}
		//	Add to List & display
		m_productList.add (new Integer(M_Product_ID));
		VNumber qty = new VNumber ("Qty", true, false, true, DisplayType.Quantity, name);
		qty.setValue(lineQty);
		qty.setReadWrite(selected);
		m_qtyList.add(qty);
		CLabel label = new CLabel (name);
		label.setLabelFor(qty);
		this.add(label);
		this.add(qty);	
	}	//	addDisplay

	
	/**
	 * 	Get Preferred Size
	 *	@return size
	 */
	@Override
	public Dimension getPreferredSize ()
	{
		Dimension size = super.getPreferredSize ();
		if (size.width > WINDOW_WIDTH)
			size.width = WINDOW_WIDTH - 30;
		return size;
	}	//	getPreferredSize

	
	/**************************************************************************
	 *	Action Listener
	 *  @param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		log.info(e.getActionCommand());
		
		Object source = e.getSource();

		//	Toggle Qty Enabled
		if (source instanceof JCheckBox || source instanceof JRadioButton)
		{
			cmd_selection (source);
			//	need to de-select the others in group	
			if (source instanceof JRadioButton)
			{
				//	find Button Group
				Iterator it = m_buttonGroups.values().iterator();
				while (it.hasNext())
				{
					ButtonGroup group = (ButtonGroup)it.next();
					Enumeration en = group.getElements();
					while (en.hasMoreElements())
					{
						//	We found the group
						if (source == en.nextElement())
						{
							Enumeration info = group.getElements();
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
			m_qty = (BigDecimal)productQty.getValue();
			KeyNamePair pp = (KeyNamePair)productField.getSelectedItem();
			m_product = MProduct.get (Env.getCtx(), pp.getKey());
			createMainPanel();
			sizeIt();
		}
		
		//	Order
		else if (source == orderField)
		{
			KeyNamePair pp = (KeyNamePair)orderField.getSelectedItem();
			boolean valid = (pp != null && pp.getKey() > 0);
			//
			if (invoiceField != null)
				invoiceField.setReadWrite(!valid);
			if (projectField != null)
				projectField.setReadWrite(!valid);
		}
		//	Invoice
		else if (source == invoiceField)
		{
			KeyNamePair pp = (KeyNamePair)invoiceField.getSelectedItem();
			boolean valid = (pp != null && pp.getKey() > 0);
			//
			if (orderField != null)
				orderField.setReadWrite(!valid);
			if (projectField != null)
				projectField.setReadWrite(!valid);
		}
		//	Project
		else if (source == projectField)
		{
			KeyNamePair pp = (KeyNamePair)projectField.getSelectedItem();
			boolean valid = (pp != null && pp.getKey() > 0);
			//
			if (orderField != null)
				orderField.setReadWrite(!valid);
			if (invoiceField != null)
				invoiceField.setReadWrite(!valid);
		}
		
		//	OK
		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			if (cmd_save())
				dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
			dispose();
			
		//	Enable OK
		boolean OK = m_product != null;
		if (OK)
		{
			KeyNamePair pp = null;
			if (orderField != null)
				pp = (KeyNamePair)orderField.getSelectedItem();
			if ((pp == null || pp.getKey() <= 0) && invoiceField != null)
				pp = (KeyNamePair)invoiceField.getSelectedItem();
			if ((pp == null || pp.getKey() <= 0) && projectField != null)
				pp = (KeyNamePair)projectField.getSelectedItem();
			OK = (pp != null && pp.getKey() > 0);
		}
		confirmPanel.getOKButton().setEnabled(OK);
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
				VNumber qty = m_qtyList.get(i);
				qty.setReadWrite(selected);
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
		if (source instanceof JCheckBox)
			retValue = ((JCheckBox)source).isSelected();
		else if (source instanceof JRadioButton)
			retValue = ((JRadioButton)source).isSelected();
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
		KeyNamePair pp = (KeyNamePair)orderField.getSelectedItem();
		if (pp != null && pp.getKey() > 0)
			return cmd_saveOrder (pp.getKey());
		//
		pp = (KeyNamePair)invoiceField.getSelectedItem();
		if (pp != null && pp.getKey() > 0)
			return cmd_saveInvoice (pp.getKey());
		//
		pp = (KeyNamePair)projectField.getSelectedItem();
		if (pp != null && pp.getKey() > 0)
			return cmd_saveProject (pp.getKey());
		//
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
				BigDecimal qty = (BigDecimal)m_qtyList.get(i).getValue();
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
				BigDecimal qty = (BigDecimal)m_qtyList.get(i).getValue();
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
				BigDecimal qty = (BigDecimal)m_qtyList.get(i).getValue();
				int M_Product_ID = m_productList.get(i).intValue();
				//	Create Line
				MProjectLine pl = new MProjectLine (project);
				pl.setM_Product_ID(M_Product_ID);
				pl.setPlannedQty(qty);
			//	pl.setPlannedPrice();
				if (pl.save())
					lineCount++;
				else
					log.error("Line not saved");
			}	//	line selected
		}	//	for all bom lines
		
		log.info("#" + lineCount);
		return true;
	}	//	cmd_saveProject

}	//	VBOMDrop

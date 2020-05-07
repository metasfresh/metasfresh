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
package org.eevolution.form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.apps.form.VBOMDrop;
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
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.eevolution.form.bom.RadioButtonTreeCellRenderer;
import org.eevolution.form.bom.nodeUserObject;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

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


import it.cnr.imaa.essi.lablib.gui.checkboxtree.CheckboxTree;

/**
 *	Drop BOM
 *	
 *  @author Jorg Janke
 *  @version $Id: VBOMDrop.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class VProductConfigurationBOM extends CPanel 
	implements FormPanel, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5246895661277027914L;


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
			//Tree stuff
			DefaultMutableTreeNode parent = new DefaultMutableTreeNode(Msg.translate(Env.getCtx(), "No Product Chosen"));

                        this.m_RadioButtonTreeCellRenderer = new RadioButtonTreeCellRenderer();

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
	//private JTree                   m_tree;
	private CheckboxTree                   m_tree;

	/**	Product to create BOMs from	*/
	private MProduct 	m_product = null;
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
	/**	List of all bom line ids		*/
	private ArrayList<Integer>		m_bomLineIDList = new ArrayList<Integer>();
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

        private RadioButtonTreeCellRenderer m_RadioButtonTreeCellRenderer = null;

	
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
			+ "WHERE IsBOM='Y' AND IsVerified='Y' AND IsActive='Y' and M_Product_ID in (SELECT M_Product_ID FROM PP_Product_bom WHERE bomtype = 'C' AND bomuse = 'A')"
			+ " ORDER BY Name";
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
		m_bomLineIDList.clear();
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

            m_tree = new CheckboxTree(this.m_RadioButtonTreeCellRenderer.action_loadBOM(m_product, true));
            m_tree.getCheckingModel().setCheckingMode(it.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingModel.CheckingMode.SIMPLE);
            m_tree.getCheckingModel().clearChecking();
            m_tree.setCellRenderer(this.m_RadioButtonTreeCellRenderer);
            m_tree.setScrollsOnExpand(true);
            JScrollPane treeView = new JScrollPane(m_tree);
			this.add(treeView);
                        
		} 
		else 
		{
            DefaultMutableTreeNode nodeHolder = new DefaultMutableTreeNode(Msg.translate(Env.getCtx(), "No Product Chosen"));
            m_tree = new CheckboxTree(nodeHolder);
            JScrollPane treeView = new JScrollPane(m_tree);
			this.add(treeView);
        }
		this.setBorder (new TitledBorder(title));
	}	//	createMainPanel

	/**
	 * 	Get last product of a BOM
	 *	@return MProduct
	 */
	private MProduct getProductFromMPPProductBOM(int PP_Product_BOM_ID) {
	MProduct m_product = null;

		try{
                      	StringBuffer sql1 = new StringBuffer("select m_product_id from pp_product_bom where pp_product_bom_id = ?");
                      	PreparedStatement pstmt = DB.prepareStatement(sql1.toString(), null);
                        pstmt.setInt(1,PP_Product_BOM_ID);


                        ResultSet rs = pstmt.executeQuery();

                        //
                        if (rs.next())
                        {
                                int m_product_id = rs.getInt(1);
                                m_product = new MProduct(Env.getCtx(), m_product_id, null);

                        }
                        rs.close();
                        pstmt.close();
           }
           catch(SQLException s)
           {
                      log.error("ERROR:", s);
           }

	return m_product;

	}

	/**
	 * 	Get last componet of a BOM
	 *	@return MProduct
	 */
	private MProduct getProductFromMPPProductBOMLine(MPPProductBOMLine bomLine) {
	MProduct m_product = null;

		try{
             StringBuffer sql1 = new StringBuffer("select m_product_id from pp_product_bomline where pp_product_bomline_id = ?");
             PreparedStatement pstmt = DB.prepareStatement(sql1.toString(), null);
             pstmt.setInt(1,bomLine.getPP_Product_BOMLine_ID());
             ResultSet rs = pstmt.executeQuery();
             
             if (rs.next())
             {
                int m_product_id = rs.getInt(1);
				m_product = new MProduct(Env.getCtx(), m_product_id, null);

             }
                        
             rs.close();
             pstmt.close();
             }
             catch(SQLException s)
             {
                      log.error("ERROR:", s);
             }

	return m_product;

	}

	/**
	 * 	Get Array of BOM Lines
	 *	@return MProduct
	 */
	private MPPProductBOMLine[] getBOMLines(MProduct m_product) 
	{
		Collection<MPPProductBOMLine> col = new ArrayList<MPPProductBOMLine>();

		try{                      
				StringBuffer sql1 = new StringBuffer("select pp_product_bom_id from pp_product_bom where m_product_id = ?");
	            PreparedStatement pstmt = DB.prepareStatement(sql1.toString(), null);
	            pstmt.setInt(1,m_product.get_ID());
	            
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next())
	            {
	                int m_pp_product_bom_id = rs.getInt(1);
					MPPProductBOM m_MPPProductBOM = new MPPProductBOM(Env.getCtx(), m_pp_product_bom_id, null);
					MPPProductBOMLine[] bomLines = m_MPPProductBOM.getLines();
	        		Collections.addAll(col, bomLines); //...or pass an explicit array
	            }
	                rs.close();
	                pstmt.close();
            }
            catch(SQLException s)
            {
                      log.error("ERROR:", s);
            }
            
		return col.toArray(new MPPProductBOMLine[col.size()]);
	}

	/**
	 * 	Add BOM Lines to this.
	 * 	Called recursively
	 * 	@param product product
	 * 	@param qty quantity
	 */
	private void addBOMLines (MProduct product, BigDecimal qty)
	{
		MPPProductBOMLine[] bomLines = getBOMLines(product);
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
	private void addBOMLine (MPPProductBOMLine line, BigDecimal qty)
	{
		log.debug("In addBOMLine");
		log.debug(line.toString());
		//FIXME:  add a bomtype accessor here
		String bomType = line.getComponentType();
		if (bomType == null)
			bomType = MProductBOM.BOMTYPE_StandardPart;
		BigDecimal lineQty = new BigDecimal(0);
		MProduct product = getProductFromMPPProductBOMLine(line);
		if (product == null)
			return;
		
		addDisplay (line.getM_Product_ID(),
		product.getM_Product_ID(), bomType, product.getName(), lineQty, line.getPP_Product_BOM_ID(), line.getFeature(), line.get_ID());
	}	//	addBOMLine

	/**
	 * 	Add Line to Display
	 *	@param parentM_Product_ID parent product
	 *	@param M_Product_ID product
	 *	@param bomType bom type
	 *	@param name name
	 *	@param lineQty qty
	 */
	private void addDisplay (int parentM_Product_ID,int M_Product_ID, String bomType, String name, BigDecimal lineQty, int PP_Product_BOM_ID, String M_Feature, int PP_Product_BOMLine_ID)
	{
		log.debug("M_Product_ID=" + M_Product_ID + ",Type=" + bomType + ",Name=" + name + ",Qty=" + lineQty);
		//
		boolean selected = true;
	        	
		if (MPPProductBOMLine.COMPONENTTYPE_Component.equals(bomType))
		{
			String title = "";
			JCheckBox cb = new JCheckBox(title);
			cb.setSelected(true);
			cb.setEnabled(false);
			m_selectionList.add(cb);
			this.add(cb, new ALayoutConstraint(m_bomLine++, 0));
		}
		
		//FIXME:  add different types for libero
		else if (MPPProductBOMLine.COMPONENTTYPE_Variant.equals(bomType))
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
			//String title = Msg.getMsg(Env.getCtx(), "Alternative") + " " + bomType;
			String title = M_Feature;
			JRadioButton b = new JRadioButton(title);
			//String groupName = String.valueOf(parentM_Product_ID) + "_" + bomType;
			String groupName = String.valueOf(getProductFromMPPProductBOM(PP_Product_BOM_ID).get_ID()) + "_" + bomType;
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
			m_bomLineIDList.add(new Integer(PP_Product_BOMLine_ID));
		/*VNumber qty = new VNumber ("Qty", true, false, true, DisplayType.Quantity, name);
		qty.setValue(lineQty);
		qty.setReadWrite(selected);
		m_qtyList.add(qty);
		*/
		CLabel label = new CLabel (name);
		//label.setLabelFor(qty);
		this.add(label);
		//this.add(qty);	
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

	private ArrayList<DefaultMutableTreeNode> getProductInstances(int root_m_product_id) {
		log.debug("In getProductInstances root_m_product_id: " + root_m_product_id);

		ArrayList<DefaultMutableTreeNode> productInstancesList = new ArrayList<DefaultMutableTreeNode>();
		try
                  {
                      StringBuffer sql1 = new StringBuffer("select a.m_product_id from pp_product_bom a, pp_product_bom b where a.name = b.name || ' Instance' and b.m_product_id = ? and a.bomtype = 'C' and a.bomuse = 'M'");
		log.debug("sql1: " + sql1);
                PreparedStatement pstmt = DB.prepareStatement(sql1.toString(), null);
			
			log.debug("root_m_product_id: " + root_m_product_id);
                        pstmt.setInt(1,root_m_product_id);


                        ResultSet rs = pstmt.executeQuery();

                        //
                        while (rs.next())
                        {
                                int m_product_id = rs.getInt(1);
				m_product = new MProduct(Env.getCtx(), m_product_id, null);
				if(m_product.isVerified()) {
				    log.debug("Adding product: " + m_product.get_ID());
				    productInstancesList.add(this.m_RadioButtonTreeCellRenderer.action_loadBOM(m_product, false));
				}

                        }
                        rs.close();
                        pstmt.close();
                  }
                  catch(SQLException s)
{
                      log.error("ERROR:", s);
                  }

		log.debug("# of product instances found: " + productInstancesList.size());
		return productInstancesList;

        }

	private ArrayList<DefaultMutableTreeNode> getProductInstances() {
		log.debug("In getProductInstances");

		ArrayList<DefaultMutableTreeNode> productInstancesList = new ArrayList<DefaultMutableTreeNode>();
		try
                  {
                      StringBuffer sql1 = new StringBuffer("select a.m_product_id from pp_product_bom a, pp_product_bom b where a.name = b.name || ' Instance' and b.m_product_id = ? and a.bomtype = 'C' and a.bomuse = 'M'");
		log.debug("sql1: " + sql1);
                PreparedStatement pstmt = DB.prepareStatement(sql1.toString(), null);
			
			KeyNamePair pp = (KeyNamePair)productField.getSelectedItem();
			MProduct m_productConfig = MProduct.get (Env.getCtx(), pp.getKey());
			log.debug("m_productConfig.get_ID " + m_productConfig.get_ID());
                        pstmt.setInt(1,m_productConfig.get_ID());


                        ResultSet rs = pstmt.executeQuery();

                        //
                        while (rs.next())
                        {
                                int m_product_id = rs.getInt(1);
				m_product = new MProduct(Env.getCtx(), m_product_id, null);
				if(m_product.isVerified()) {
				    log.debug("Adding product: " + m_product.get_ID());
                        //this.m_RadioButtonTreeCellRenderer = new RadioButtonTreeCellRenderer();
				    productInstancesList.add(this.m_RadioButtonTreeCellRenderer.action_loadBOM(m_product, false));
				}

                        }
                        rs.close();
                        pstmt.close();
                  }
                  catch(SQLException s)
{
                      log.error("ERROR:", s);
                  }

		log.debug("# of product instances found: " + productInstancesList.size());
		return productInstancesList;

        }

   private HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>> buildConfigBOMIDToBOMLevelToLinesMap(MProduct m_product) {
      log.debug("In buildConfigBOMIDToBOMLevelToLinesMap");

      HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>>	m_ConfigBOMIDToBOMLevelToLinesMap = new HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>>();

      HashMap<Integer, ArrayList<MPPProductBOMLine>> m_BOMLevelToLinesMap = new HashMap<Integer, ArrayList<MPPProductBOMLine>>();

      MPPProductBOMLine[] bomLines = getBOMLines(m_product);
      int bomLevel = 0;

      ArrayList<MPPProductBOMLine> col = new ArrayList<MPPProductBOMLine>();
      for(int i = 0; i < bomLines.length; i++) {
        log.debug("bom line from product with product_id: " + getProductFromMPPProductBOMLine(bomLines[i]).get_ID());
        col.add(bomLines[i]);

      }

      //Collection<MPPProductBOMLine> col = new ArrayList<MPPProductBOMLine>();
      //Collections.addAll(col, bomLines);

      m_BOMLevelToLinesMap.put(new Integer(bomLevel), col);
      MPPProductBOMLine m_MPPProductBOMLine = bomLines[0];
      int PP_Product_BOM_ID = m_MPPProductBOMLine.getPP_Product_BOM_ID();
      m_ConfigBOMIDToBOMLevelToLinesMap.put(new Integer(PP_Product_BOM_ID), m_BOMLevelToLinesMap);


      return m_ConfigBOMIDToBOMLevelToLinesMap;

   }

   private int getBomLevelBetweenPPProductBOMIDs(int top_bom_id, int lower_bom_id) {
      //int retValue = -1;
      int retValue = 0;


      return retValue;
   }

   private HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>> buildConfigBOMIDToBOMLevelToLinesMapFromSelectionList() {
     log.debug("In buildConfigBOMIDToBOMLevelToLinesMapFromSelectionList");

     KeyNamePair pp = (KeyNamePair)productField.getSelectedItem();
     MProduct m_productConfig = MProduct.get (Env.getCtx(), pp.getKey());
     MPPProductBOMLine[] configBOMLines = getBOMLines(m_productConfig);
     int PP_ConfigProduct_BOM_ID = configBOMLines[0].getPP_Product_BOM_ID();

      HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>>	m_ConfigBOMIDToBOMLevelToLinesMapFromSelectionList = new HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>>();

      HashMap<Integer, ArrayList<MPPProductBOMLine>> m_BOMLevelToLinesMap = new HashMap<Integer, ArrayList<MPPProductBOMLine>>();

      HashMap<Integer, ArrayList<MPPProductBOMLine>> m_BOMLevelToLinesMapFromKey = null;

      //	for all bom lines
      for (int i = 0; i < m_selectionList.size(); i++)
      {
         if (isSelectionSelected(m_selectionList.get(i)))
		{
		   int PP_Product_BOMLine_ID = m_bomLineIDList.get(i).intValue();
		   log.debug("PP_Product_BOMLine_ID: " + PP_Product_BOMLine_ID);
		   MPPProductBOMLine m_MPPProductBOMLine = new MPPProductBOMLine(Env.getCtx(), PP_Product_BOMLine_ID, null);
		
			m_BOMLevelToLinesMapFromKey = m_ConfigBOMIDToBOMLevelToLinesMapFromSelectionList.get(new Integer(m_MPPProductBOMLine.getPP_Product_BOM_ID()));

			if (m_BOMLevelToLinesMapFromKey == null)
			{
				m_BOMLevelToLinesMapFromKey = new HashMap<Integer, ArrayList<MPPProductBOMLine>>();
				m_ConfigBOMIDToBOMLevelToLinesMapFromSelectionList.put(new Integer(m_MPPProductBOMLine.getPP_Product_BOM_ID()), m_BOMLevelToLinesMapFromKey);
				//ArrayList<MPPProductBOMLine> bomLines = (ArrayList<MPPProductBOMLine>)m_BOMLevelToLinesMapFromKey.get(getBomLevelBetweenPPProductBOMIDs(PP_ConfigProduct_BOM_ID, m_MPPProductBOMLine.getPP_Product_BOM_ID()));
				ArrayList<MPPProductBOMLine> bomLines = new ArrayList<MPPProductBOMLine>();
				bomLines.add(m_MPPProductBOMLine);
			        m_BOMLevelToLinesMapFromKey.put(getBomLevelBetweenPPProductBOMIDs(PP_ConfigProduct_BOM_ID, m_MPPProductBOMLine.getPP_Product_BOM_ID()), bomLines);
			}
			else
			{
				ArrayList<MPPProductBOMLine> bomLines = m_BOMLevelToLinesMapFromKey.get(getBomLevelBetweenPPProductBOMIDs(PP_ConfigProduct_BOM_ID, m_MPPProductBOMLine.getPP_Product_BOM_ID()));
				bomLines.add(m_MPPProductBOMLine);
			}
		}	//	line selected
      }	//	for all bom lines

      return m_ConfigBOMIDToBOMLevelToLinesMapFromSelectionList;

   }

   private void printBOMLevelToLinesMap(HashMap<Integer, ArrayList<MPPProductBOMLine>> m_BOMLevelToLinesMap) {

      Set set = m_BOMLevelToLinesMap.entrySet();
    
      for (Iterator i = set.iterator(); i.hasNext();) {
         Map.Entry me = (Map.Entry)i.next();

         log.debug("BOM level: " + me.getKey().toString());
         ArrayList<MPPProductBOMLine> bomLines = (ArrayList<MPPProductBOMLine>)me.getValue();
         
         log.debug("Total BOM line's: " + bomLines.size());
         for(int j = 0; j < bomLines.size(); j++) {
            MPPProductBOMLine m_MPPProductBOMLine = bomLines.get(j);
            log.debug("bom line #: " + j + " product: " + m_MPPProductBOMLine.getDescription());

         }
         

      }


   }

   private void printConfigBOMIDToBOMLinesMapForProduct(MProduct m_product, HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>>   m_ConfigBOMInstIDToBOMLinesMap) {
      log.debug("In printConfigBOMIDToBOMLinesMapForProduct");
      log.debug("Analyzing product bom id's and corresponding bomlines and levels for those product bom id's for a particular product");
      
      log.debug("Product: " + m_product.getName() + " has:");
      log.debug("Total BOM's: " + m_ConfigBOMInstIDToBOMLinesMap.size());

      Set set = m_ConfigBOMInstIDToBOMLinesMap.entrySet();
    
      for (Iterator i = set.iterator(); i.hasNext();) {
         Map.Entry me = (Map.Entry)i.next();

         HashMap<Integer, ArrayList<MPPProductBOMLine>> m_BOMLevelToLinesMap = new HashMap<Integer, ArrayList<MPPProductBOMLine>>();

         log.debug("PP_Product_BOM_ID: " + me.getKey().toString() + " has: ");
         m_BOMLevelToLinesMap = (HashMap<Integer, ArrayList<MPPProductBOMLine>>)me.getValue();
      Set setLines = m_BOMLevelToLinesMap.entrySet();
    
      for (Iterator k = setLines.iterator(); k.hasNext();) {
         Map.Entry me2 = (Map.Entry)k.next();

         ArrayList<MPPProductBOMLine> bomLines = (ArrayList<MPPProductBOMLine>)me2.getValue();
         
            log.debug("Total bom lines: " + bomLines.size());
            for(int count = 0; count < bomLines.size(); count++) {
              MPPProductBOMLine m_MPPProductBOMLine = bomLines.get(count);
              log.debug("bom line #: " + count + " product: " + m_MPPProductBOMLine.getDescription());


            }

         }

      }

   }

   private int getTotalBOMLines(HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>>   m_ConfigBOMInstIDToBOMLinesMap) 
   {
      log.debug("In getTotalBOMLines");
     int retVal = 0;
      
      Set set = m_ConfigBOMInstIDToBOMLinesMap.entrySet();
    
      for (Iterator i = set.iterator(); i.hasNext();) {
         Map.Entry me = (Map.Entry)i.next();

         HashMap<Integer, ArrayList<MPPProductBOMLine>> m_BOMLevelToLinesMap = new HashMap<Integer, ArrayList<MPPProductBOMLine>>();

         m_BOMLevelToLinesMap = (HashMap<Integer, ArrayList<MPPProductBOMLine>>)me.getValue();
	 
         Set set2 = m_BOMLevelToLinesMap.entrySet();
    
         for (Iterator i2 = set2.iterator(); i2.hasNext();) {
            Map.Entry me2 = (Map.Entry)i2.next();

            ArrayList<MPPProductBOMLine> bomLines = (ArrayList<MPPProductBOMLine>)me2.getValue();
         
            retVal += bomLines.size();
         

         }
      }
     
     log.debug("Total bom lines: " + retVal);
     return retVal;

   }
      private int getTotalBOMLevels(HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>> m_ConfigBOMInstIDToBOMLinesMap) {
      log.debug("In getTotalBOMLevels");
      int maxBOMLevel = 0;

      Set set = m_ConfigBOMInstIDToBOMLinesMap.entrySet();
    
      for (Iterator i = set.iterator(); i.hasNext();) {
         Map.Entry me = (Map.Entry)i.next();

         HashMap<Integer, ArrayList<MPPProductBOMLine>> m_BOMLevelToLinesMap = new HashMap<Integer, ArrayList<MPPProductBOMLine>>();

         log.debug("PP_Product_BOM_ID: " + me.getKey().toString() + " has: ");
         m_BOMLevelToLinesMap = (HashMap<Integer, ArrayList<MPPProductBOMLine>>)me.getValue();
         Set setLines = m_BOMLevelToLinesMap.entrySet();
    
         for (Iterator k = setLines.iterator(); k.hasNext();) {
           Map.Entry me2 = (Map.Entry)k.next();

            if(((Integer)me2.getKey()).intValue() > maxBOMLevel)
               maxBOMLevel = ((Integer)me2.getKey()).intValue();
         }
      }

         log.debug("maxBOMLevel found: " + maxBOMLevel);

         return maxBOMLevel;
      }

   private boolean isSameProductsInProductInstance(int bomLevel, ArrayList<MPPProductBOMLine> configBomLines,  HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>> m_ConfigBOMInstIDToBOMLinesMap) { 
      log.info("In isSameProductsInProductInstance");

      boolean retVal = true;

      Set set = m_ConfigBOMInstIDToBOMLinesMap.entrySet();
    
      for (Iterator i = set.iterator(); i.hasNext();) {
         Map.Entry me = (Map.Entry)i.next();

         HashMap<Integer, ArrayList<MPPProductBOMLine>> m_BOMLevelToLinesMap = new HashMap<Integer, ArrayList<MPPProductBOMLine>>();

         log.debug("PP_Product_BOM_ID: " + me.getKey().toString() + " has: ");
         m_BOMLevelToLinesMap = (HashMap<Integer, ArrayList<MPPProductBOMLine>>)me.getValue();
         Set setLines = m_BOMLevelToLinesMap.entrySet();
    
         for (Iterator k = setLines.iterator(); k.hasNext();) {
           Map.Entry me2 = (Map.Entry)k.next();

               if(((Integer)me2.getKey()).intValue() == bomLevel) {
                  ArrayList<MPPProductBOMLine> configInstanceBomLines = (ArrayList<MPPProductBOMLine>)me2.getValue();
                  log.debug("configBomLines.size: " + configBomLines.size());
                  log.debug("configInstanceBomLines.size: " + configInstanceBomLines.size());
		  for(int l = 0; l < configBomLines.size(); l++) {
                     boolean matchedProduct = false;
		     for(int m = 0; m < configInstanceBomLines.size(); m++) {
                        int chosenProduct_ID = getProductFromMPPProductBOMLine(configBomLines.get(l)).get_ID();
                        int instanceProduct_ID = getProductFromMPPProductBOMLine(configBomLines.get(m)).get_ID();
                        log.debug("bomLevel: " + bomLevel);
	                log.debug("chosenProduct_ID: " + chosenProduct_ID);
	                log.debug("instanceProduct_ID: " + instanceProduct_ID);

                        if(chosenProduct_ID == instanceProduct_ID)
                           matchedProduct = true;
                     }
		     if(!matchedProduct)
	                return false;
                  }

                  }
               }
         }
      return retVal;
   }

   private boolean isSameProductsAtBOMLevel(int bomLevel, HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>> m_ConfigBOMIDToBOMLinesMap, HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>> m_ConfigBOMInstIDToBOMLinesMap) {
      log.debug("In isSameProductsAtBOMLevel");
      boolean retVal = true;
      
      Set set = m_ConfigBOMIDToBOMLinesMap.entrySet();
    
      for (Iterator i = set.iterator(); i.hasNext();) {
         Map.Entry me = (Map.Entry)i.next();

         HashMap<Integer, ArrayList<MPPProductBOMLine>> m_BOMLevelToLinesMap = new HashMap<Integer, ArrayList<MPPProductBOMLine>>();

         log.debug("PP_Product_BOM_ID: " + me.getKey().toString() + " has: ");
         m_BOMLevelToLinesMap = (HashMap<Integer, ArrayList<MPPProductBOMLine>>)me.getValue();
         Set setLines = m_BOMLevelToLinesMap.entrySet();
    
         for (Iterator k = setLines.iterator(); k.hasNext();) {
           Map.Entry me2 = (Map.Entry)k.next();

               if(((Integer)me2.getKey()).intValue() == bomLevel) {
                  ArrayList<MPPProductBOMLine> bomLines = (ArrayList<MPPProductBOMLine>)me2.getValue();
		  if(isSameProductsInProductInstance(bomLevel, bomLines,  m_ConfigBOMInstIDToBOMLinesMap)) { 

                  } else {
		     retVal = false;
                  }
               }
         }
      }

      return retVal;

   }

   private boolean isSameBOMStructure(HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>> m_ConfigBOMIDToBOMLinesMap, HashMap<Integer, HashMap<Integer, ArrayList<MPPProductBOMLine>>> m_ConfigBOMInstIDToBOMLinesMap) {
      log.debug("In isSameBOMStructure");

      boolean retVal = true;

      int totalProductConfigBOMLevels = getTotalBOMLevels(m_ConfigBOMIDToBOMLinesMap);
      int totalProductConfigInstBOMLevels = getTotalBOMLevels(m_ConfigBOMInstIDToBOMLinesMap);

      if(totalProductConfigBOMLevels == totalProductConfigInstBOMLevels) {
         for(int i = 0; i < totalProductConfigBOMLevels+1; i++) {
            if(isSameProductsAtBOMLevel(i, m_ConfigBOMIDToBOMLinesMap, m_ConfigBOMInstIDToBOMLinesMap)) {

            } else {
               retVal = false;
	       break;
            } 

         }


      } else {
         retVal = false;
      }  

      return retVal; 
   }

   private boolean pruneProductConfig() {
     log.debug("In pruneProductConfig");
     boolean retVal = false;
     DefaultMutableTreeNode rootProductConfig = this.m_RadioButtonTreeCellRenderer.root;
        Enumeration children = rootProductConfig.breadthFirstEnumeration();
        log.debug("About to prune");
        if (children != null) {
           while (children.hasMoreElements()) {

              DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
              log.debug("Analyzing: " + child);
              log.debug("level: " + child.getLevel());
              nodeUserObject m_nodeUserObject = (nodeUserObject)child.getUserObject();
              log.debug("isMandatory: " + m_nodeUserObject.isMandatory);
              log.debug("isChosen: " + m_nodeUserObject.isChosen);

              if(!(child.isRoot() || m_nodeUserObject.isChosen || m_nodeUserObject.isMandatory)) {
                 log.debug("Removing: " + child);
                 child.removeFromParent();
                 retVal = true;
              }
           }
        }

        log.debug("Exiting pruneConfig");
        return retVal;
   }

   private int getNumNodesFromRoot(DefaultMutableTreeNode root) {
        Enumeration children = root.breadthFirstEnumeration();
        ArrayList m_ArrayList = Collections.list(children);

        return  m_ArrayList.size();

   }
   private boolean isProductContainedAtLevelInProductInstance(int m_product_id, int level, DefaultMutableTreeNode rootProductInstance) {
        log.debug("In isProductContainedAtLevelInProductInstance");
        log.debug("looking for m_product_id: " + m_product_id + " at level: " + level);
     log.debug("rootProductInstance.getDepth: " + rootProductInstance.getDepth());
     log.debug("rootProductInstance.getNumNodesFromRoot: " + getNumNodesFromRoot(rootProductInstance));

   boolean retValue = false;
        

        Enumeration children = rootProductInstance.breadthFirstEnumeration();
        if (children != null) {
           while (children.hasMoreElements()) {

              DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
              nodeUserObject m_nodeUserObject = (nodeUserObject)child.getUserObject();
              log.debug("node: " + child + " product instance m_product_id: " + m_nodeUserObject.M_Product.get_ID() + " at level: " + child.getLevel());
              if(child.getLevel() == level &&  m_nodeUserObject.M_Product.get_ID() == m_product_id) {
                    retValue = true;
                    return retValue;
              }
           }
        }


   return retValue;
   }

   private void printTree(DefaultMutableTreeNode root) {
     log.debug("In printTree");
     log.debug("root.getDepth: " + root.getDepth());
     log.debug("root.getNumNodesFromRoot: " + getNumNodesFromRoot(root));

        Enumeration children = root.breadthFirstEnumeration();
        if (children != null) {
           while (children.hasMoreElements()) {

              DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
              nodeUserObject m_nodeUserObject = (nodeUserObject)child.getUserObject();
              log.debug("node: " + child + " product instance m_product_id: " + m_nodeUserObject.M_Product.get_ID() + " at level: " + child.getLevel());
              log.debug("bom id: " + m_nodeUserObject.bom.get_ID());
              log.debug("isMandatory: " + m_nodeUserObject.isMandatory);
              log.debug("isChosen: " + m_nodeUserObject.isChosen);
           }
        }

   }

   private boolean isConfigEqualToProductInstance(DefaultMutableTreeNode rootProductInstance, DefaultMutableTreeNode rootProductConfig)
   {
     log.debug("In isConfigEqualToProductInstance");

     boolean retValue = false;

     boolean done = false;
     log.debug("rootProductConfig.getDepth: " + rootProductConfig.getDepth());
     log.debug("rootProductInstance.getDepth: " + rootProductInstance.getDepth());
     log.debug("rootProductConfig.getNumNodesFromRoot: " + getNumNodesFromRoot(rootProductConfig));
     log.debug("rootProductInstance.getNumNodesFromRoot: " + getNumNodesFromRoot(rootProductInstance));

     if(getNumNodesFromRoot(rootProductConfig) == (getNumNodesFromRoot(rootProductInstance))) {

     if(rootProductConfig.getDepth() == rootProductInstance.getDepth()) {
        Enumeration children = rootProductConfig.breadthFirstEnumeration();
        if (children != null) {
           while (children.hasMoreElements()) {

              DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
              nodeUserObject m_nodeUserObject = (nodeUserObject)child.getUserObject();
              if(m_nodeUserObject.isChosen || m_nodeUserObject.isMandatory) {
                 if( (rootProductConfig.getLevel() - child.getLevel() + 1) > 1 && m_nodeUserObject.bomLine != null) { 
                   if(!isProductContainedAtLevelInProductInstance(m_nodeUserObject.M_Product.get_ID(), rootProductConfig.getLevel() - child.getLevel() + 1, rootProductInstance))
                     return retValue;
                 }
              }
           }
           retValue = true;
           return retValue;
        }
      }

      }

     return retValue;
   }

   private boolean isConfigEqualToProductInstance(DefaultMutableTreeNode rootProductInstance)
   {
     log.debug("In isConfigEqualToProductInstance");

     boolean retValue = false;

     boolean done = false;
     DefaultMutableTreeNode rootProductConfig = this.m_RadioButtonTreeCellRenderer.root;
     log.debug("rootProductConfig.getDepth: " + rootProductConfig.getDepth());
     log.debug("rootProductInstance.getDepth: " + rootProductInstance.getDepth());
     log.debug("rootProductConfig.getNumNodesFromRoot: " + getNumNodesFromRoot(rootProductConfig));
     log.debug("rootProductInstance.getNumNodesFromRoot: " + getNumNodesFromRoot(rootProductInstance));

     if(getNumNodesFromRoot(rootProductConfig) == (getNumNodesFromRoot(rootProductInstance))) {

     if(rootProductConfig.getDepth() == rootProductInstance.getDepth()) {
        Enumeration children = rootProductConfig.breadthFirstEnumeration();
        if (children != null) {
           while (children.hasMoreElements()) {

              DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
              nodeUserObject m_nodeUserObject = (nodeUserObject)child.getUserObject();
              if(m_nodeUserObject.isChosen || m_nodeUserObject.isMandatory) {
                 if(child.getLevel() > 1) {
                   if(!isProductContainedAtLevelInProductInstance(m_nodeUserObject.M_Product.get_ID(), child.getLevel(), rootProductInstance))
                      return retValue;
                 }
              }
           }
           retValue = true;
           return retValue;
        }
      }

      }

     return retValue;
   }

		private DefaultMutableTreeNode findProductInstance(DefaultMutableTreeNode rootProductConfig) 
        {
                log.debug("In findProductInstance");
        	DefaultMutableTreeNode retNode = null;

		nodeUserObject m_rootNodeUserObject = (nodeUserObject)rootProductConfig.getUserObject();

		ArrayList<DefaultMutableTreeNode> productInstances = getProductInstances( m_rootNodeUserObject.M_Product.get_ID());


		if(productInstances.size() > 0) {
			for(int i = 0; i < productInstances.size(); i++) {
				if(isConfigEqualToProductInstance(productInstances.get(i), rootProductConfig)) {
			   		retNode = productInstances.get(i);
				}


			
			}

		}
        return retNode;
        }

		private int findProductInstance() 
        {
                log.debug("In findProductInstance");
        	int M_Product_ID = -1;


                 DefaultMutableTreeNode rootProductConfig = this.m_RadioButtonTreeCellRenderer.root;

		ArrayList<DefaultMutableTreeNode> productInstances = getProductInstances();


		if(productInstances.size() > 0) 
		{
			for(int i = 0; i < productInstances.size(); i++) 
			{
				if(isConfigEqualToProductInstance(productInstances.get(i))) 
				{
			   		nodeUserObject m_nodeUserObject = (nodeUserObject)productInstances.get(i).getUserObject();
			   		M_Product_ID = m_nodeUserObject.M_Product.get_ID();
				}
			}
		}
        	return M_Product_ID;
        }

        private DefaultMutableTreeNode getLowestConfigurableBOMTreeNode() {
           DefaultMutableTreeNode retNode = null;
           log.debug("In getLowestConfigurableBOMTreeNode");

        DefaultMutableTreeNode rootProductConfig = this.m_RadioButtonTreeCellRenderer.root;
        Enumeration children = rootProductConfig.breadthFirstEnumeration();
        if (children != null) {
           while (children.hasMoreElements()) {

              DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
              nodeUserObject m_nodeUserObject = (nodeUserObject)child.getUserObject();
              log.debug("child level: " + child.getLevel());
              log.debug("child: " + child);
              if(child.getLevel() > 1) {
                 if(m_nodeUserObject.bom.getBOMType().equals(m_nodeUserObject.bom.BOMTYPE_ProductConfigure) && m_nodeUserObject.bom.getBOMUse().equals(m_nodeUserObject.bom.BOMUSE_Master) ) {
                    if(m_nodeUserObject.bomLine == null) {
                       if(retNode == null) 
                          retNode = child;
                       else if(retNode.getLevel() > child.getLevel()) 
                          retNode = child;
                   
                    } /*else if(m_nodeUserObject.bomLine.getComponentType().equals(m_nodeUserObject.bomLine.COMPONENTTYPE_Option) || m_nodeUserObject.bomLine.getComponentType().equals(m_nodeUserObject.bomLine.COMPONENTTYPE_Variant)) {
                       if(retNode == null) 
                          retNode = child;
                       else if(retNode.getLevel() > child.getLevel()) 
                          retNode = child;
                    } */
                        
                 }
              }
           }
        }

        return retNode;
        }


        private boolean replaceProductConfigBOMwithProductFromChoices() {
           boolean retValue = true;
           boolean done = false;
           log.debug("In replaceProductConfigBOMwithProductFromChoices");
        
           while(!done) {
             DefaultMutableTreeNode lowestProductConfigBOMNode = getLowestConfigurableBOMTreeNode();
             log.debug("lowestProductConfigBOMNode: " + lowestProductConfigBOMNode);
             if(lowestProductConfigBOMNode == null) {
                done = true;

             } else {
	        DefaultMutableTreeNode foundProductInstanceNode = findProductInstance(lowestProductConfigBOMNode);
                log.debug("foundProductInstanceNode: " + foundProductInstanceNode);
                if(foundProductInstanceNode == null) {
                   done = true;
                   retValue = false;
                } else {
                   DefaultMutableTreeNode m_parent = (DefaultMutableTreeNode)lowestProductConfigBOMNode.getParent();
                   nodeUserObject m_nodeUserObject = (nodeUserObject)foundProductInstanceNode.getUserObject();
                   m_nodeUserObject.isChosen = true;
                   m_nodeUserObject.isMandatory = true;
                   m_parent.add(foundProductInstanceNode);
                   //foundProductInstanceNode.removeAllChildren();
                   lowestProductConfigBOMNode.removeFromParent();
                }
             }
           }


        return retValue;
        }

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

		//FIXME:  Change to search for correct product, but because
		//we only have the one product to use, that is automatically
		//chosen.

		m_qty = (BigDecimal)productQty.getValue();
                log.debug("printing product config tree");
                printTree(this.m_RadioButtonTreeCellRenderer.root);
                while(pruneProductConfig());
		int M_Product_ID = -1;
                boolean replaceResult = replaceProductConfigBOMwithProductFromChoices();
                log.debug("replaceResult: " + replaceResult);

                if(replaceResult) {
                   log.debug("After replacement product config tree");
                   printTree(this.m_RadioButtonTreeCellRenderer.root);
		   M_Product_ID = findProductInstance();
                }

                log.debug("M_Product_ID: " + M_Product_ID);
		
                   if(M_Product_ID < 0 || !replaceResult) {
					log.debug("No product instance found for the configuration chosen");
					 String warningMsg = "No product instance found for the configuration chosen, create one?";
                                String warningTitle = "Warning";
                                int response = JOptionPane.showConfirmDialog(null, warningMsg,
                                                warningTitle, JOptionPane.YES_NO_OPTION);
                                if (response == JOptionPane.YES_OPTION) {
					log.debug("create product instance");

				}
		}

		MOrderLine ol = new MOrderLine (order);
		ol.setM_Product_ID(m_product.get_ID(), true);
		ol.setQty(m_qty);
		ol.setPrice();
		ol.setTax();
		if (ol.save())
			log.debug("order line saved");
		else
			log.error("Line not saved");
		int lineCount = 0;
		
		//	for all bom lines
		/* FIXME: This needs to be changed to search for a product
                   that is a production configuration BOM instance of all
                   the choices made.  This product configuration BOM instance
		   would then be associated with one product which would be
		   added to the sales order.
		for (int i = 0; i < m_selectionList.size(); i++)
		{
			if (isSelectionSelected(m_selectionList.get(i)))
			{
				BigDecimal qty = (BigDecimal)((VNumber)m_qtyList.get(i)).getValue();
		//	for all bom lines
		for (int i = 0; i < m_selectionList.size(); i++)
		{
			if (isSelectionSelected(m_selectionList.get(i)))
			{
				int M_Product_ID = ((Integer)m_productList.get(i)).intValue();
			}	//	line selected
		}	//	for all bom lines

				int M_Product_ID = ((Integer)m_productList.get(i)).intValue();
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
		*/
		
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

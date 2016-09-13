/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.model.Query;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * BOM Tree Maintenance
 *	
 *  @author Victor Perez,Sergio Ramazzinag
 *  @version $Id: VTreeMaintenance.java,v 1.1 2004/03/20 04:35:51 jjanke Exp $
 * 
 *	4Layers - MODIFICATIONS --------------------------------------------------------
 * 	 2005/04/12	Various improvements to the standard form (Sergio Ramazzina)
 *	4Layers -------------------------------------------------------------------- end
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class VTreeBOM extends CPanel implements FormPanel, ActionListener,
		ListSelectionListener, PropertyChangeListener, VetoableChangeListener,
		TreeSelectionListener, TableModelListener
{
	private static final long serialVersionUID = 1L;

	/**
	 *	Tree Maintenance 
	 */
	public VTreeBOM ()
	{
	}	//	VTreeMaintenance

	/**	Window No				*/
	private int         	m_WindowNo = 0;
	/**	FormFrame				*/
	private FormFrame 		m_frame;
	/**	Active Tree				*/
	private JTree		 	m_tree;

	private static Logger log = LogManager.getLogger(VTreeBOM.class);

	private BorderLayout	mainLayout	= new BorderLayout ();
	private CPanel 			northPanel	= new CPanel ();

	private FlowLayout		northLayout	= new FlowLayout ();
	private CLabel			labelProduct	= new CLabel ();
	private VLookup fieldProduct;
	//private CButton			bAddAll		= new CButton (Env.getImageIcon("FastBack24.gif"));
	//private CButton			bAdd		= new CButton (Env.getImageIcon("StepBack24.gif"));
	//private CButton			bDelete		= new CButton (Env.getImageIcon("StepForward24.gif"));
	//private CButton			bDeleteAll	= new CButton (Env.getImageIcon("FastForward24.gif"));
	private CCheckBox		implosion	= new CCheckBox ();
	private CLabel			treeInfo	= new CLabel ();
	//
	private JSplitPane		splitPane	= new JSplitPane ();
	//private VTreePanel		centerTree;
	//private JList			centerList	= new JList ();
	private JScrollPane dataPane = new JScrollPane();
	private JScrollPane treePane = new JScrollPane();
	//private CLabel           labelUOM = new CLabel();
	//private CTextField       fieldUOM = new CTextField(10);
	//private CLabel     labelDocument  = new CLabel();
	//private CTextField  fieldDocument = new CTextField(10);
	//private CLabel     labelRevision  = new CLabel();
	//private CTextField  fieldRevision = new CTextField(8);
	//private CLabel          labelECN  = new CLabel();
	//private CTextField       fieldECN = new CTextField(10);
	//private VDate            dateFrom = new VDate ("DateFrom", false, false, true, DisplayType.Date, Msg.translate(getCtx(), "DateFrom"));
	//private VDate              dateTo = new VDate ("DateTo", false, false, true, DisplayType.Date, Msg.translate(getCtx(), "DateTo"));
	private CPanel southPanel = new CPanel();
	private BorderLayout southLayout = new BorderLayout();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	protected StatusBar statusBar = new StatusBar();

	private MiniTable tableBOM = new MiniTable();
	private Vector<Vector<Object>> dataBOM = new Vector<Vector<Object>>();
	private Vector<String> columnNames;
	//private VDate fieldGuaranteeDate = 
	//4Layers - Set divider location variable
	private final int DIVIDER_LOCATION = 240;
	// 4Layers - end

	public Properties getCtx() {
		return Env.getCtx();
	}
	
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
		log.info( "VMerge.init - WinNo=" + m_WindowNo);
		try
		{
			preInit();
			jbInit ();

			frame.getContentPane().add(this, BorderLayout.CENTER);
			//	frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
			//action_loadBOM();
		}
		catch (Exception ex)
		{
			log.error("VTreeMaintenance.init", ex);
		}
	}	//	init

	/**
	 * 	Fill Tree Combo
	 */
	private void preInit() throws Exception
	{
		Properties ctx = getCtx();
		MLookup m_fieldProduct = MLookupFactory.get(ctx, m_WindowNo,
				MColumn.getColumn_ID(MProduct.Table_Name, "M_Product_ID"),
				DisplayType.Search, MProduct.COLUMNNAME_M_Product_ID, 0, false,
				" M_Product.IsSummary = 'N'");
		fieldProduct = new VLookup ("M_Product_ID", false, false, true,  m_fieldProduct) {
			private static final long serialVersionUID = 1L;
			@Override
			public void setValue(Object value) {
				super.setValue(value);
				action_loadBOM();
			}
		};
		
		implosion.addActionListener(this);
		splitPane.add (dataPane, JSplitPane.RIGHT);
		splitPane.add (treePane, JSplitPane.LEFT);
	}	//	preInit

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
	private void loadTableBOM()
	{
		//  Header Info
		columnNames = new Vector<String>(18);

		columnNames.add(Msg.translate(getCtx(), "Select"));	        // 0		
		columnNames.add(Msg.translate(getCtx(), "IsActive"));       // 1
		columnNames.add(Msg.translate(getCtx(), "Line"));           // 2
		columnNames.add(Msg.translate(getCtx(), "ValidFrom"));      // 3
		columnNames.add(Msg.translate(getCtx(), "ValidTo"));        // 4
		columnNames.add(Msg.translate(getCtx(), "M_Product_ID"));   // 5
		columnNames.add(Msg.translate(getCtx(), "C_UOM_ID"));       // 6
		columnNames.add(Msg.translate(getCtx(), "IsQtyPercentage"));  // 7                       
		columnNames.add(Msg.translate(getCtx(), "QtyBatch"));   // 8
		columnNames.add(Msg.translate(getCtx(), "QtyBOM"));   		// 9
		columnNames.add(Msg.translate(getCtx(), "IsCritical"));     // 10
		columnNames.add(Msg.translate(getCtx(), "LeadTimeOffset"));       // 11
		columnNames.add(Msg.translate(getCtx(), "Assay"));          // 12
		columnNames.add(Msg.translate(getCtx(), "Scrap"));          // 13
		columnNames.add(Msg.translate(getCtx(), "IssueMethod"));    // 14
		columnNames.add(Msg.translate(getCtx(), "BackflushGroup")); // 15
		columnNames.add(Msg.translate(getCtx(), "Forecast"));       // 16

		//  Remove previous listeners
		tableBOM.getModel().removeTableModelListener(this);
		//  Remove previous listeners
		tableBOM.getModel().removeTableModelListener(this);
		//  Set Model
		DefaultTableModel model = new DefaultTableModel(dataBOM, columnNames);
		model.addTableModelListener(this);
		tableBOM.setModel(model);

		tableBOM.setColumnClass( 0, Boolean.class, false);     //  0 Select
		tableBOM.setColumnClass( 1, Boolean.class, false);     //  1 IsActive
		tableBOM.setColumnClass( 2, Integer.class,false);      //  2 Line
		tableBOM.setColumnClass( 3, Timestamp.class,false);    //  3 ValidFrom
		tableBOM.setColumnClass( 4, Timestamp.class,false);    //  4 ValidTo
		tableBOM.setColumnClass( 5, KeyNamePair.class,false);  //  5 M_Product_ID
		tableBOM.setColumnClass( 6, KeyNamePair.class,false);  //  6 C_UOM_ID
		tableBOM.setColumnClass( 7, Boolean.class,false);      //  7 QtyPorcentage                        
		tableBOM.setColumnClass( 8, BigDecimal.class,false);   //  8 BatchPercent
		tableBOM.setColumnClass( 9, BigDecimal.class,false);   //  9 QtyBOM
		tableBOM.setColumnClass( 10, Boolean.class,false);      // 10 IsCritical                                           
		tableBOM.setColumnClass( 11, BigDecimal.class,false);   // 11 LTOffSet
		tableBOM.setColumnClass( 12, BigDecimal.class,false);   // 12 Assay
		tableBOM.setColumnClass( 13, Integer.class,false);      // 13 Scrap
		tableBOM.setColumnClass( 14, String.class,false);       // 14 IssueMethod
		tableBOM.setColumnClass( 15, String.class,false);       // 15 BackflushGroup
		tableBOM.setColumnClass( 16, BigDecimal.class,false);   // 16 Forecast
		tableBOM.autoSize();

		//tableBOM.prepareTable(layout, "", "", false, "");

		//  Visual
		//CompiereColor.setBackground (this);

		//tableBOM.getSelectionModel().addListSelectionListener(this);
	}   //  dynInit


	/**
	 * 	Static init
	 *	@throws Exception
	 */
	private void jbInit () 
	{
		this.setLayout (mainLayout);

		// 4Layers - Set initial window dimension 
		this.setPreferredSize(new Dimension(640, 480));

		//labelUOM.setText (Msg.getElement(getCtx(), "C_UOM_ID"));
		//fieldUOM.setEditable(false);
		//labelDocument.setText (Msg.translate(getCtx(), "Document"));
		//labelRevision.setText (Msg.translate(getCtx(), "Revision"));
		//labelECN.setText (Msg.translate(getCtx(), "ECN"));

		labelProduct.setText (Msg.getElement(getCtx(), "M_Product_ID"));
		//implosion.setEnabled (false);
		implosion.setText (Msg.getElement(getCtx(), "Implosion"));
		//treeInfo.setText (" ");
		//bAdd.setToolTipText("Add to Tree");
		//bAddAll.setToolTipText("Add ALL to Tree");
		//bDelete.setToolTipText("Delete from Tree");
		//bDeleteAll.setToolTipText("Delete ALL from Tree");
		//bAdd.addActionListener(this);
		//bAddAll.addActionListener(this);
		//bDelete.addActionListener(this);
		//bDeleteAll.addActionListener(this);
		northPanel.setLayout (northLayout);
		northLayout.setAlignment (FlowLayout.LEFT);
		//
		this.add (northPanel, BorderLayout.NORTH);

		northPanel.add (labelProduct, null);
		northPanel.add (fieldProduct, null);
		northPanel.add (implosion, null);
		//northPanel.add (cbAllNodes, null);
		northPanel.add (treeInfo, null);

		//northPanel.add (labelUOM, null);
		//northPanel.add (fieldUOM, null);
		//northPanel.add (labelDocument, null);
		//northPanel.add (fieldDocument, null);
		//northPanel.add (labelRevision, null);
		//northPanel.add (fieldRevision, null);
		//northPanel.add (fieldECN, null);

		//northPanel.add (bAddAll, null);
		//northPanel.add (bAdd, null);
		//northPanel.add (bDelete, null);
		//northPanel.add (bDeleteAll, null);
		//

		this.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(southLayout);
		confirmPanel.setActionListener(this);
		southPanel.add(confirmPanel, BorderLayout.SOUTH);
		//southPanel.add(statusBar, BorderLayout.SOUTH);
		this.add (splitPane, BorderLayout.CENTER);

		// 4Layers - Set divider location
		splitPane.setDividerLocation(DIVIDER_LOCATION);

		//centerList.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
		//centerList.addListSelectionListener(this);

	}	//	jbInit

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

	@Override
	public void vetoableChange (PropertyChangeEvent e)
	{
		String name = e.getPropertyName();
		Object value = e.getNewValue();
		log.info( "VAllocation.vetoableChange - " + name + "=" + value);
		if (value == null)
			return;

		//  BPartner
		if (name.equals("M_Product_ID"))
		{
			if (fieldProduct != null)
				action_loadBOM();	
		}		
	}   //  vetoableChange

	/**
	 * 	Action Listener
	 *	@param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == implosion) 
		{
			action_loadBOM();
		}
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			action_loadBOM();
		}
		// 4Layers - Close window when cancel is pressed
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL)) 
		{
			dispose();
		}
		// 4Layers - End

		/*else if (e.getSource() == bAddAll)
			action_treeAddAll();
		else if (e.getSource() == bAdd)
			action_treeAdd((ListItem)centerList.getSelectedValue());
		else if (e.getSource() == bDelete)
			action_treeDelete((ListItem)centerList.getSelectedValue());
		else if (e.getSource() == bDeleteAll)
			action_treeDeleteAll();*/
		//super.actionPerformed(e);
	}	//	actionPerformed


	/**
	 * 	Action: Fill Tree with all nodes
	 */
	private void action_loadBOM()
	{
		int M_Product_ID = getM_Product_ID(); 
		if (M_Product_ID == 0)
			return;
		MProduct M_Product = MProduct.get(getCtx(), M_Product_ID);
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode(productSummary(M_Product, false));

		dataBOM.clear();

		if (isImplosion())
		{
			for (MPPProductBOMLine bomline : getBOMLines(M_Product_ID))
			{
				parent.add(parent(bomline));
			}     
			m_tree = new JTree(parent);
		}
		else
		{
			for (MPPProductBOM bom : getBOMs(M_Product_ID, true))
			{
				parent.add(parent(bom));                    
			}      
			m_tree = new JTree(parent);
		}

		m_tree.addTreeSelectionListener(this);

		treePane.getViewport().add (m_tree, null);

		loadTableBOM();
		dataPane.getViewport().add(tableBOM, null);
		// 4Layers - Set divider location
		splitPane.setDividerLocation(DIVIDER_LOCATION);
		// 4Layers - end
		
	}	//	action_fillTree

	public DefaultMutableTreeNode parent(MPPProductBOMLine bomline) 
	{

		//System.out.println("-------------------------Parent:" + bom.getName());
		MProduct M_Product = MProduct.get(getCtx(), bomline.getM_Product_ID());
		MPPProductBOM bomproduct = new MPPProductBOM(getCtx(), bomline.getPP_Product_BOM_ID(), null);
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode(productSummary(M_Product, false));

		Vector<Object> line = new Vector<Object>(17);
		line.add( new Boolean(false));  //  0 Select
		line.add( new Boolean(true));   //  1 IsActive
		line.add( new Integer(bomline.getLine())); // 2 Line                
		line.add( bomline.getValidFrom()); //  3 ValidDrom
		line.add( bomline.getValidTo()); //  4 ValidTo
		KeyNamePair pp = new KeyNamePair(M_Product.getM_Product_ID(),M_Product.getName());
		line.add(pp); //  5 M_Product_ID
		KeyNamePair uom = new KeyNamePair(bomline.getC_UOM_ID(),"");
		line.add(uom); //  6 C_UOM_ID
		line.add(new Boolean(bomline.isQtyPercentage())); //  7 IsQtyPorcentage
		line.add(bomline.getQtyBatch());  //  8 BatchPercent
		line.add((bomline.getQtyBOM()!=null) ? bomline.getQtyBOM() : new BigDecimal(0));  //  9 QtyBOM
		line.add(new Boolean(bomline.isCritical())); //  10 IsCritical                  
		line.add( bomline.getLeadTimeOffset()); // 11 LTOffSet
		line.add( bomline.getAssay()); // 12 Assay
		line.add( (bomline.getScrap())); // 13 Scrap
		line.add( bomline.getIssueMethod()); // 14 IssueMethod
		line.add( bomline.getBackflushGroup());  // 15 BackflushGroup
		line.add( bomline.getForecast()); // 16 Forecast
		dataBOM.add(line);

		for (MPPProductBOM bom : getBOMs(bomproduct.getM_Product_ID(), false))
		{
			MProduct component = MProduct.get(getCtx(), bom.getM_Product_ID());
			return component(component);
		}
		return parent;
	}

	public DefaultMutableTreeNode parent(MPPProductBOM bom) 
	{

		// System.out.println("Parent:" + bom.getName());
		// X_M_Product product = new X_M_Product(getCtx(), bom.getM_Product_ID(),"M_Product");

		//vparent.setValue(m_product_id);
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode(productSummary(bom)); 

		for (MPPProductBOMLine bomline : bom.getLines())
		{
			MProduct component = MProduct.get(getCtx(), bomline.getM_Product_ID());
			//System.out.println("Componente :" + component.getValue() + "[" + component.getName() + "]");
			//component(component);
			Vector<Object> line = new Vector<Object>(17);
			line.add( new Boolean(false));  //  0 Select
			line.add( new Boolean(true));   //  1 IsActive
			line.add( new Integer(bomline.getLine())); // 2 Line                
			line.add( bomline.getValidFrom()); //  3 ValidDrom
			line.add( bomline.getValidTo()); //  4 ValidTo
			KeyNamePair pp = new KeyNamePair(component.getM_Product_ID(),component.getName());
			line.add(pp); //  5 M_Product_ID
			KeyNamePair uom = new KeyNamePair(bomline.getC_UOM_ID(),"");
			line.add(uom); //  6 C_UOM_ID
			line.add(new Boolean(bomline.isQtyPercentage())); //  7 IsQtyPercentage
			line.add(bomline.getQtyBatch());  //  8 BatchPercent
			line.add(bomline.getQtyBOM());  //  9 QtyBom
			line.add(new Boolean(bomline.isCritical())); //  10 IsCritical       
			line.add( bomline.getLeadTimeOffset()); // 11 LTOffSet
			line.add( bomline.getAssay()); // 12 Assay
			line.add( (bomline.getScrap())); // 13 Scrap
			line.add( bomline.getIssueMethod()); // 14 IssueMethod
			line.add( bomline.getBackflushGroup());  // 15 BackflushGroup
			line.add( bomline.getForecast()); // 16 Forecast
			//line.add(this.);
			dataBOM.add(line);
			parent.add(component(component));

		}
		return parent;
	}

	public DefaultMutableTreeNode component(MProduct M_Product) 
	{   

		if (isImplosion())
		{
			DefaultMutableTreeNode parent = new DefaultMutableTreeNode(productSummary(M_Product, false));
			for (MPPProductBOMLine bomline : getBOMLines(M_Product.getM_Product_ID()))
			{
				parent.add(parent(bomline));
			}  
			return parent;  
		}
		else
		{
			for (MPPProductBOM bom : getBOMs(M_Product.getValue()))
			{
				return parent(bom);
			}  
			return new DefaultMutableTreeNode(productSummary(M_Product, true));
		}
	}


	@Override
	public void valueChanged(TreeSelectionEvent event) 
	{
		//currentSelectionField.setText("Current Selection: " +  tree.getLastSelectedPathComponent().toString());
	}

	/**
	 * 	List Selection Listener
	 *	@param e event
	 */
	@Override
	public void valueChanged (ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting())
			return;
	}	//	valueChanged

	/**
	 * 	VTreePanel Changed
	 *	@param e event
	 */
	@Override
	public void propertyChange (PropertyChangeEvent e)
	{
		//MTreeNode tn = (MTreeNode)e.getNewValue();
		//log.info( "VTreeMaintenance.propertyChanged", tn);
		//if (tn == null)
		//	return;
		/*ListModel model = centerList.getModel();
		int size = model.getSize();
		int index = -1;
		for (index = 0; index < size; index++)
		{
			ListItem item = (ListItem)model.getElementAt(index);
			if (item.id == tn.getNode_ID())
				break;
		}
		centerList.setSelectedIndex(index);*/
	}	//	propertyChange

	/**
	 * 	Action: Add Node to Tree
	 */ 
	private void action_treeAdd(ListItem item)
	{
		log.info( "VTreeMaintenance.action_treeAdd " + item);
		if (item != null)
		{
			//centerTree.nodeChanged(true, item.id, item.name, 
			//	item.description, item.isSummary, item.imageIndicator);
			/*			if (m_tree.isProduct())
			{
				MTree_NodePR node = new MTree_NodePR (m_tree, item.id);
				node.save();
			}
			else if (m_tree.isBPartner())
			{
				MTree_NodeBP node = new MTree_NodeBP (m_tree, item.id);
				node.save();
			}
			else if (m_tree.isMenu())
			{
				MTree_NodeMM node = new MTree_NodeMM (m_tree, item.id);
				node.save();
			}
			else
			{
				MTree_Node node = new MTree_Node (m_tree, item.id);
				node.save();
			}*/
		}
	}	//	action_treeAdd

	/**
	 * 	Action: Delete Node from Tree
	 */
	private void action_treeDelete(ListItem item)
	{
		log.info( "VTreeMaintenance.action_treeDelete" + item);
		if (item != null)
		{
			//centerTree.nodeChanged(false, item.id, item.name, 
			//	item.description, item.isSummary, item.imageIndicator);
			/*if (m_tree.isProduct())
			{
				MTree_NodePR node = MTree_NodePR.get (m_tree, item.id);
				if (node != null)
					node.delete();
			}
			else if (m_tree.isBPartner())
			{
				MTree_NodeBP node = MTree_NodeBP.get (m_tree, item.id);
				if (node != null)
					node.delete();
			}
			else if (m_tree.isMenu())
			{
				MTree_NodeMM node = MTree_NodeMM.get (m_tree, item.id);
				if (node != null)
					node.delete();
			}
			else
			{
				MTree_Node node = MTree_Node.get (m_tree, item.id);
				if (node != null)
					node.delete();
			}*/
		}
	}	//	action_treeDelete


	/**
	 * 	Action: Add All Nodes to Tree
	 */ 
	private void action_treeAddAll()
	{
		log.info( "VTreeMaintenance.action_treeAddAll");
		/*ListModel model = centerList.getModel();
		int size = model.getSize();
		int index = -1;
		for (index = 0; index < size; index++)
		{
			ListItem item = (ListItem)model.getElementAt(index);
			action_treeAdd(item);
		}*/
	}	//	action_treeAddAll

	/**
	 * 	Action: Delete All Nodes from Tree
	 */
	private void action_treeDeleteAll()
	{
		log.info( "VTreeMaintenance.action_treeDeleteAll");
		/*ListModel model = centerList.getModel();
		int size = model.getSize();
		int index = -1;
		for (index = 0; index < size; index++)
		{
			ListItem item = (ListItem)model.getElementAt(index);
			action_treeDelete(item);
		}*/
	}

	@Override
	public void tableChanged(TableModelEvent e) {
	}        


	//	action_treeDeleteAll

	/**************************************************************************
	 * 	Tree Maintenance List Item
	 */
	class ListItem
	{
		public ListItem (int id, String name, String description, boolean isSummary, String imageIndicator)
		{
			this.id = id;
			this.name = name;
			this.description = description;
			this.isSummary = isSummary;
			this.imageIndicator = imageIndicator;
		}	//	ListItem

		public int id;
		public String name;
		public String description;
		public boolean isSummary;
		public String imageIndicator;  //  Menu - Action

		/**
		 * 	To String
		 *	@return	String Representation
		 */
		@Override
		public String toString ()
		{
			String retValue = name;
			if (description != null && description.length() > 0)
				retValue += " (" + description + ")";
			return retValue;
		}	//	toString

	}	//	ListItem
	
	private String productSummary(MProduct product, boolean isLeaf) {
		MUOM uom = MUOM.get(getCtx(), product.getC_UOM_ID());
		String value = product.getValue();
		String name = product.get_Translation(MProduct.COLUMNNAME_Name);
		//
		StringBuffer sb = new StringBuffer(value);
		if (name != null && !value.equals(name))
			sb.append("_").append(product.getName());
		sb.append(" [").append(uom.get_Translation(MUOM.COLUMNNAME_UOMSymbol)).append("]");
		//
		return sb.toString();
	}
	
	private String productSummary(MPPProductBOM bom) {
		String value = bom.getValue();
		String name = bom.get_Translation(MPPProductBOM.COLUMNNAME_Name);
		//
		StringBuffer sb = new StringBuffer(value);
		if (name != null && !name.equals(value))
			sb.append("_").append(name);
		//
		return sb.toString();
	}
	
	private boolean isImplosion() {
		return implosion.isSelected();
	}
	
	private int getM_Product_ID() {
		Integer Product = (Integer)fieldProduct.getValue();
		if (Product == null)
			return 0;
		return Product.intValue(); 
	}
	
	private List<MPPProductBOM> getBOMs(String productValue)
	{
		int ad_client_id = Env.getAD_Client_ID(getCtx()); 
		String filter = MPPProductBOM.COLUMNNAME_Value+"=? AND AD_Client_ID=?";
		return new Query (getCtx(), MPPProductBOM.Table_Name, filter, null)
					.setParameters(new Object[]{productValue, ad_client_id})
					.list();
		
	}
	private List<MPPProductBOM> getBOMs(int M_Product_ID, boolean onlyActiveRecords)
	{
		String filter = MPPProductBOM.COLUMNNAME_M_Product_ID+"=?"
						+(onlyActiveRecords ? " AND IsActive='Y'" : "");
		return new Query(getCtx(), MPPProductBOM.Table_Name, filter, null)
					.setParameters(new Object[]{M_Product_ID})
					.list();
	}
	
	private List<MPPProductBOMLine> getBOMLines(int M_Product_ID) 
	{
		String filter = MPPProductBOMLine.COLUMNNAME_M_Product_ID+"=?";
		return new Query(getCtx(), MPPProductBOMLine.Table_Name, filter, null)
						.setParameters(new Object[]{M_Product_ID})
						.list();
	}
}	//	VTreeMaintenance

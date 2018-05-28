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
 * Copyright (C) 2003-2008 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
//RadioButtonTreeCellRenderer.java
package org.eevolution.form.bom;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
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
import it.cnr.imaa.essi.lablib.gui.checkboxtree.CheckboxTreeCellRenderer;
import it.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingEvent;
import it.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingListener;
import it.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingModel;



@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class RadioButtonTreeCellRenderer implements CheckboxTreeCellRenderer {

	JRadioButton button = new JRadioButton();
	JCheckBox checkBox = new JCheckBox();
	JPanel panel = new JPanel();
	JLabel label = new JLabel();
	//ButtonGroup group = new ButtonGroup();
	boolean toggle = false;
	private Vector<Vector<Comparable<?>>> dataBOM = new Vector<>();
	public DefaultMutableTreeNode root = null;

	public HashSet<TreePath> checkedPathsSet =  new HashSet<>();

	public HashSet<TreePath> greyedPathsSet =  new HashSet<>();

	public HashSet<TreePath> disabledPathsSet =  new HashSet<>();

	public HashSet<TreePath> checkBoxPathsSet =  new HashSet<>();
	private static Logger log = LogManager.getLogger(RadioButtonTreeCellRenderer.class);


	public DefaultMutableTreeNode getTreeNodeForNodeUserObject(nodeUserObject m_nodeUserObject) {
		log.debug("In getTreeNodeForNodeUserObject");
		DefaultMutableTreeNode foundChild = null;

		Enumeration<?> children = this.root.breadthFirstEnumeration();
		if (children != null) {
			while (children.hasMoreElements()) {

				DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
				if(m_nodeUserObject == (nodeUserObject)child.getUserObject()) {
					log.debug("nodeUserObjectFound");
					foundChild = child;
				}
			}
		}
		return foundChild;
	}


	public static void printDescendents(TreeNode root) {
		log.debug(root.toString());
		Enumeration<?> children = root.children();
		if (children != null) {
			while (children.hasMoreElements()) {
				printDescendents((TreeNode) children.nextElement());
			}
		}
	}


	/**
	 * 	Action: Fill Tree with all nodes
	 */
	public DefaultMutableTreeNode action_loadBOM(MProduct Product, boolean setRoot)
	{
		int M_Product_ID = Product.get_ID(); 
		MProduct M_Product =  MProduct.get(Env.getCtx(), M_Product_ID);
		MUOM UOM = new MUOM(Env.getCtx() , M_Product.getC_UOM_ID(), null);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new nodeUserObject(Msg.translate(Env.getCtx(), "M_Product_ID") + Msg.translate(Env.getCtx(), "Value") + ": " + M_Product.getValue() + " " + Msg.translate(Env.getCtx(), "Name") + ": "  +M_Product.getName() + " " +  Msg.translate(Env.getCtx(), "C_UOM_ID") + ": " + UOM.getName(), M_Product, null, null));
		if(setRoot) {
			this.root = root;
		}
		dataBOM.clear();
		if (false)
		{
			String whereClause = "M_Product_ID=?";
			List<MPPProductBOMLine> bomlines = new Query(Env.getCtx(),MPPProductBOMLine.Table_Name,whereClause, null)
			.setParameters(new Object[]{M_Product_ID})
			.list(MPPProductBOMLine.class);
			for (MPPProductBOMLine bomline : bomlines)
			{
				 root.add(parent(bomline)); 
			}
		}
		else
		{
			String whereClause = "M_Product_ID=?";
			List<MPPProductBOM> boms = new Query(Env.getCtx(),MPPProductBOM.Table_Name,whereClause, null)
			.setParameters(new Object[]{M_Product_ID})
			.setOnlyActiveRecords(true)
			.list(MPPProductBOM.class);
			for (MPPProductBOM bom : boms)
			{
				DefaultMutableTreeNode child = parent(bom);
				root.add(child);  
			}
    
		}
		log.debug("root.getChildCount: " + root.getChildCount());
		if(root.getChildCount() > 0) {
			root = (DefaultMutableTreeNode)root.getFirstChild();
		}

		if(setRoot)
			this.root = root;

		return root;

	}	//	action_fillTree

	public DefaultMutableTreeNode  parent(MPPProductBOMLine bomline) 
	{
		log.debug("In parent with X_PP_Product_BOMLine");

		MProduct M_Product = MProduct.get(Env.getCtx(), bomline.getM_Product_ID());
		MUOM UOM = new MUOM(Env.getCtx() , M_Product.getC_UOM_ID(),null); 

		MPPProductBOM bomproduct = new MPPProductBOM(Env.getCtx(),bomline.getPP_Product_BOM_ID(),null);
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode(new nodeUserObject(Msg.translate(Env.getCtx(), "M_Product_ID") + Msg.translate(Env.getCtx(), "key") + ": " + M_Product.getValue() + " " + Msg.translate(Env.getCtx(), "Name") + ": "  +M_Product.getName() + " " +  Msg.translate(Env.getCtx(), "C_UOM_ID") + ": " + UOM.getName(), M_Product, bomproduct, bomline));


		Vector<Comparable<?>> line = new Vector<>(17);
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

		String whereClause = "M_Product_ID=?";
		List<MPPProductBOM> boms = new Query(Env.getCtx(),MPPProductBOM.Table_Name,whereClause, null)
		.setParameters(new Object[]{bomproduct.getM_Product_ID()})
		.setOnlyActiveRecords(true)
		.list(MPPProductBOM.class);
		for (MPPProductBOM bom : boms)
		{        
			MProduct component = MProduct.get(Env.getCtx(), bom.getM_Product_ID());
			return component(component, bom, bomline); 
		}
		return parent;
	}

	public DefaultMutableTreeNode  parent(MPPProductBOM bom) 
	{

		log.debug("Parent:" + bom.getName());
		MProduct product = MProduct.get(Env.getCtx(), bom.getM_Product_ID());

		//vparent.setValue(m_product_id);
		String data = Msg.translate(Env.getCtx(), "PP_Product_BOM_ID") + " " + Msg.translate(Env.getCtx(), "Value") + ":"+ bom.getValue()+ " " + Msg.translate(Env.getCtx(), "Name")  +  ": " + bom.getName(); 
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode(new nodeUserObject(data, product, bom, null)); 

		String whereClause = "PP_Product_BOM_ID=?";
		List<MPPProductBOMLine> bomlines = new Query(Env.getCtx(),MPPProductBOMLine.Table_Name,whereClause, null)
		.setParameters(new Object[]{bom.getPP_Product_BOM_ID()})
		.list(MPPProductBOMLine.class);
		for (MPPProductBOMLine bomline : bomlines)
		{            
			MProduct component = MProduct.get(Env.getCtx(), bomline.getM_Product_ID());
			//System.out.println("Componente :" + component.getValue() + "[" + component.getName() + "]");
			//component(component);
			Vector<Comparable<?>> line = new Vector<>(17);
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
			parent.add(component(component, bom, bomline));
		}
		return parent;
	}




	public DefaultMutableTreeNode component(MProduct M_Product, MPPProductBOM bomPassed, MPPProductBOMLine bomlinePassed) 
	{   

		MUOM UOM = new MUOM(Env.getCtx() , M_Product.getC_UOM_ID(),null);
		String whereClause = "Value=?";
		List<MPPProductBOM> boms = new Query(Env.getCtx(),MPPProductBOM.Table_Name,whereClause, null)
		.setParameters(new Object[]{M_Product.getValue()})
		.setOnlyActiveRecords(true)
		.list(MPPProductBOM.class);
		for (MPPProductBOM bom : boms)
		{
			return parent(bom);  
		}
		return new DefaultMutableTreeNode(new nodeUserObject(Msg.translate(Env.getCtx(), "Value") + ": " + M_Product.getValue() + " " + Msg.translate(Env.getCtx(), "Name") + ": "  +M_Product.getName() + " " +  Msg.translate(Env.getCtx(), "C_UOM_ID") + ": " + UOM.getName(), M_Product, bomPassed, bomlinePassed));
	}

	@Override
	public boolean isOnHotspot(int x, int y) {
		return (button.getBounds().contains(x, y));
	}


	protected TreePath[] getChildrenPath(TreePath path, TreeModel model) {
		Object node = path.getLastPathComponent();
		int childrenNumber = model.getChildCount(node);
		TreePath[] childrenPath = new TreePath[childrenNumber];
		for (int childIndex = 0; childIndex < childrenNumber; childIndex++) {
			childrenPath[childIndex] = path.pathByAddingChild(model.getChild(node, childIndex));
		}
		return childrenPath;
	}

	public TreePath getPath(TreeNode node) {
		java.util.List<TreeNode> list = new ArrayList<>();

		// Add all nodes to list
		while (node != null) {
			list.add(node);
			node = node.getParent();
		}
		Collections.reverse(list);

		// Convert array of nodes to TreePath
		return new TreePath(list.toArray());
	}


	public void printTree(TreePath path, TreeModel model, TreeCheckingModel checkingModel ) {
		log.debug("In printTree");
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		log.debug("Node: " + node);
		log.debug("isPathCheckbox: " + checkingModel.isPathCheckBox(getPath(node)));
		for (TreePath childPath : getChildrenPath(path, model)) {
			printTree(childPath, model, checkingModel);
		}
		return;


	}

	public String getComponentTypeUsingBOMParent(int bom_id, int m_product_id) {
		String retVal = "";

		String sql = "select componenttype from pp_product_bomline where pp_product_bom_id = ? and m_product_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, "test");
			pstmt.setInt(1, bom_id);
			pstmt.setInt(2, m_product_id);
			rs = pstmt.executeQuery();


			while (rs.next()) {
				retVal = rs.getString(1);
			}

			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			log.debug("Execption; sql = "+sql+"; e.getMessage() = " +e.getMessage());
		} 

		return retVal;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object  
			value, boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		log.debug("row: " + row);
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)value;

		log.debug("treeNode.getLevel: " + treeNode.getLevel());
		nodeUserObject m_nodeUserObject = (nodeUserObject)treeNode.getUserObject();
		log.debug("m_nodeUserObject.toString: " + m_nodeUserObject.toString());
		log.debug("m_nodeUserObject.M_Product.getName: " + m_nodeUserObject.M_Product.getName());
		log.debug("value.toString: " + value.toString());
		label.setText(value.toString());
		TreeCheckingModel checkingModel = ((CheckboxTree)tree).getCheckingModel();
		//printTree(new TreePath(tree.getModel().getRoot()), tree.getModel(), checkingModel);
		TreePath path = tree.getPathForRow(row);
		boolean enabled = checkingModel.isPathEnabled(path);
		boolean checked = checkingModel.isPathChecked(path);
		//	    	boolean checkBoxed = checkingModel.isPathCheckBox(path);

		checked = checkingModel.isPathChecked(path);

		//	    	boolean grayed = checkingModel.isPathGreyed(path);
		button.setEnabled(true);

		button.setSelected(checked);
		m_nodeUserObject.isChosen = checked;
		log.debug("m_nodeUserObject.isChosen" + m_nodeUserObject.isChosen);

		if(m_nodeUserObject.isCheckbox || treeNode.isRoot() ) {
			panel.add(checkBox);
			panel.remove(button);
			log.debug("checked: " + checked);
			log.debug("enabled: " + enabled);
			checkBox.setEnabled(enabled);
			checkBox.setSelected(checked);
			if(treeNode.isRoot()) {
				checkBox.setSelected(true);
				checkBox.setEnabled(false);
				m_nodeUserObject.isMandatory = true;
			}
			if(m_nodeUserObject.isMandatory) {
				checkBox.setSelected(true);
				checkBox.setEnabled(false);
			}
		}  else {
			panel.remove(checkBox);
			panel.add(button);

		}

		panel.add(label);

		m_nodeUserObject.isChosen = checked;
		log.debug("m_nodeUserObject.isChosen: " + m_nodeUserObject.isChosen);
		if( m_nodeUserObject.bom!= null) { 
			log.debug("m_nodeUserObject.bom not null");
			log.debug("m_nodeUserObject.bom product_id: " + m_nodeUserObject.bom.getM_Product_ID());
			if( m_nodeUserObject.bomLine == null) {
				log.debug("m_nodeUserObject.bomLine is null");
				DefaultMutableTreeNode m_treeNode = getTreeNodeForNodeUserObject(m_nodeUserObject);
				if(!m_treeNode.isRoot()) {
					DefaultMutableTreeNode m_treeNodeParent = (DefaultMutableTreeNode)m_treeNode.getParent();
					if(m_treeNodeParent.isRoot()) {
						m_nodeUserObject.isMandatory = true;
					}
					nodeUserObject m_nodeUserObjectParent = (nodeUserObject)m_treeNodeParent.getUserObject();
					if(m_nodeUserObjectParent.bom != null) {
						log.debug("m_nodeUserObjectParent.bom is not null");

						log.debug("m_nodeUserObjectParent.bom.pp_product_bom_id: " + m_nodeUserObjectParent.bom.get_ID());
						log.debug("m_nodeUserObject.M_Product.get_ID: " + m_nodeUserObject.M_Product.get_ID());
						if(getComponentTypeUsingBOMParent(m_nodeUserObjectParent.bom.get_ID(), m_nodeUserObject.M_Product.get_ID()).equals(MPPProductBOMLine.COMPONENTTYPE_Variant) || getComponentTypeUsingBOMParent(m_nodeUserObjectParent.bom.get_ID(), m_nodeUserObject.M_Product.get_ID()).equals(MPPProductBOMLine.COMPONENTTYPE_Component)) {
							log.debug("Type is checkbox");
							if(!m_nodeUserObject.isCheckbox) {
								m_nodeUserObject.isCheckbox = true;
								panel.remove(label);
								panel.add(checkBox);
								panel.add(label);
								panel.remove(button);
								if(!m_nodeUserObject.isChosen) {
									checkBox.setSelected(false);
									m_nodeUserObject.isChosen = false;
								} else {
									checkBox.setSelected(true);
									m_nodeUserObject.isMandatory = true;
									m_nodeUserObject.isChosen = true;
								}

							}

						}

					} else {
						log.debug("Type is checkbox");
						if(!m_nodeUserObject.isCheckbox) {
							panel.remove(label);
							panel.add(checkBox);
							panel.add(label);
							panel.remove(button);

						}


					}
				}
			} else {
				log.debug("m_nodeUserObject.bomLine is not null");
				log.debug("m_nodeUserObject.M_Product.get_ID: " + m_nodeUserObject.M_Product.get_ID());
				log.debug("m_nodeUserObject.bomLine.getM_Product_ID: " + m_nodeUserObject.bomLine.getM_Product_ID());
				log.debug("m_nodeUserObject.isCheckbox: " + m_nodeUserObject.isCheckbox);
			}

		}
		panel.setBackground(Color.white);
		log.debug("m_nodeUserObject.isChosen: " + m_nodeUserObject.isChosen);
		return panel;
	} 


	public static void main(String[] args) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		DefaultMutableTreeNode one = new DefaultMutableTreeNode("one");
		DefaultMutableTreeNode two = new DefaultMutableTreeNode("two");
		DefaultMutableTreeNode three = new DefaultMutableTreeNode("three");
		root.add(one);
		root.add(two);
		root.add(three);

		RadioButtonTreeCellRenderer m_RadioButtonTreeCellRenderer = new RadioButtonTreeCellRenderer();

		final CheckboxTree tree = new CheckboxTree(m_RadioButtonTreeCellRenderer.action_loadBOM(null, false));
		tree.getCheckingModel().setCheckingMode(it.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingModel.CheckingMode.SIMPLE);
		tree.getCheckingModel().clearChecking();
		tree.setCellRenderer(m_RadioButtonTreeCellRenderer);
		tree.addTreeCheckingListener(new TreeCheckingListener() {
			@Override
			public void valueChanged(TreeCheckingEvent e) {
				log.debug("Checked paths changed: user clicked on " + (e.getLeadingPath().getLastPathComponent()));
				//       		TreeModel tm = tree.getModel();
				//          	TreePath selected = tree.getSelectionPath();
				//       		TreeCheckingModel checkingModel = ((CheckboxTree)tree).getCheckingModel();

			}
		});

		JFrame frame = new JFrame("RadioButton tree");
		frame.add(tree);
		tree.expandAll();
		frame.pack();
		frame.setVisible(true);
	} 
}

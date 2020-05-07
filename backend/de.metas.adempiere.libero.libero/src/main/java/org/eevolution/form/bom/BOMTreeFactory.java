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

package org.eevolution.form.bom;

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

import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.compiere.model.MProduct;
import org.compiere.model.MResource;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.eevolution.model.MPPOrder;
import org.eevolution.model.reasoner.StorageReasoner;
import org.eevolution.model.wrapper.BOMLineWrapper;
import org.eevolution.model.wrapper.BOMWrapper;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public abstract class BOMTreeFactory implements BOMTreeModel {

	protected JTree tree;
	protected HashMap mapping;
	protected BOMMessenger msg;
	
	private StorageReasoner reasoner;
	
	protected abstract String type();
	
	public static BOMTreeModel get(String bomType, PO po, StorageReasoner reasoner) {
		
		final StorageReasoner r = reasoner;
		final String type = bomType;
		BOMTreeFactory factory = new BOMTreeFactory() {
		
			protected String type() {
				
				return type;
			};
		};
        
		factory.buildTree(po, reasoner);
		
		return factory;
	}

	private StorageReasoner getStorageReasoner() {
		
		return reasoner;
	}
	
	protected void buildTree(PO po, StorageReasoner reasoner) {
		
		 this.reasoner = reasoner;
		
		 tree = new JTree(buildStructure(po, reasoner)) {
			 
			 public String getToolTipText(MouseEvent event) {
				 
				 return msg.getToolTipText(event);
			 };
		 };	
		 tree.setCellRenderer(new BOMTreeCellRenderer(getNodeMapping()));

		 msg = new BOMMessenger(tree);
	}
	
	protected DefaultMutableTreeNode buildStructure(PO po, StorageReasoner reasoner) {
		
		mapping = new HashMap();

		DefaultMutableTreeNode root = new DefaultMutableTreeNode(po);
        mapping.put(root, getTreeNodeRepresentation(root));
        
        BOMWrapper bom = null;
        
        String restriction = null;
        if(po instanceof MProduct) {
        	
        	restriction = MProduct.Table_Name+"_ID";
        }
        else if(po instanceof MPPOrder) {
        	
        	restriction = MPPOrder.Table_Name+"_ID";
        }
       
        int[] ids = reasoner.getPOIDs(BOMWrapper.tableName(type()), "IsActive = 'Y' AND "+restriction+" = " + po.get_ID(), null);
        for(int i = 0; i < ids.length; i++) {
        	
        	bom = new BOMWrapper(Env.getCtx(), ids[i], null, type());                                     
            root.add(getNode(bom, null, mapping));                    
        }      
	
        return root;
	}
	
    protected DefaultMutableTreeNode getNode(BOMWrapper bom, BigDecimal qty, HashMap map) {
    	
        MProduct product = new MProduct(Env.getCtx(), bom.getM_Product_ID(),MProduct.Table_Name);
        
        DefaultMutableTreeNode parent = new DefaultMutableTreeNode(bom);;
        map.put(parent, getTreeNodeRepresentation(parent));
        
        DefaultMutableTreeNode node = null;
        DefaultMutableTreeNode leaf = null;
        
        int[] ids = getStorageReasoner().getPOIDs(BOMLineWrapper.tableName(type()), BOMWrapper.idColumn(type())+" = "+bom.getID(), null);
        
        BOMLineWrapper bomline = null;
        MProduct p = null;
        for(int i = 0; i < ids.length; i++) {
        	
            bomline =  new BOMLineWrapper(Env.getCtx(), ids[i], null, type());             
            bomline.setQtyBOM(qty != null ? qty.multiply(bomline.getQtyBOM()) : bomline.getQtyBOM());
            
            p = new MProduct(Env.getCtx(), bomline.getM_Product_ID(), null);

            node = addLeafs(p, qty, map);
            
            leaf = new DefaultMutableTreeNode(bomline);
            map.put(leaf, getTreeNodeRepresentation(leaf));
            
            parent.add( (node==null) ? leaf : node);
        }

        return parent;
    }
        
    protected DefaultMutableTreeNode addLeafs(MProduct M_Product, BigDecimal qty, HashMap map) {   
  
        int[] ids = getStorageReasoner().getPOIDs(BOMWrapper.tableName(type()), "Value = '"+M_Product.getValue()+"'", null);

        BOMWrapper bom = null;
        for(int i = 0; i < ids.length; i++) {

            bom = new BOMWrapper(Env.getCtx(), ids[i], null, type());
            return getNode(bom, qty, map);
        }        
        
        return null;
    }
    
 	protected String getTreeNodeRepresentation(DefaultMutableTreeNode node) {
	
        String name = null;
        if(node.getUserObject() instanceof MProduct) {
        	
        	MProduct p = (MProduct)node.getUserObject();
        	
        	name = p.getName()+" ("+p.getValue()+")";
        }
        if(node.getUserObject() instanceof MPPOrder) {
        	
        	MPPOrder o = (MPPOrder)node.getUserObject();
        	MResource r = MResource.get(Env.getCtx(), o.getS_Resource_ID());
        	
        	name = o.getDocumentNo()+" ("+r.getName()+")";
        }
        else if(node.getUserObject() instanceof BOMWrapper) {
        	
        	BOMWrapper pb = (BOMWrapper)node.getUserObject();
        	MProduct p = new MProduct(Env.getCtx(), pb.getM_Product_ID(), null);

        	name = pb.getName();
        }
        else if(node.getUserObject() instanceof BOMLineWrapper) {
        	
        	BOMLineWrapper mpbl = (BOMLineWrapper)node.getUserObject();
        	MProduct p = new MProduct(Env.getCtx(), mpbl.getM_Product_ID(), null);
        	
        	name = p.getName();
        }
    	
        return name;
	}
 	
 	public JTree getTree() {
 		
 		return this.tree;
 	}

 	public HashMap getNodeMapping() {
 	 	
 		return this.mapping;
 	}

 	public BOMMessenger getBOMMessenger() {
 	 	
 		return this.msg;
 	}
}

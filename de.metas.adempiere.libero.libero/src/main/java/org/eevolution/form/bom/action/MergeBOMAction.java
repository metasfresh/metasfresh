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

package org.eevolution.form.bom.action;

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

import java.awt.event.ActionEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.compiere.util.Env;
import org.eevolution.form.action.PopupAction;
import org.eevolution.model.MPPProductBOM;
import org.eevolution.model.MPPProductBOMLine;
import org.eevolution.model.wrapper.BOMLineWrapper;
import org.eevolution.model.wrapper.BOMWrapper;

/**
 * @author Victor Perez, e-Evolution, S.C.
 * Merge Bill of Material
 * 
 * Merges a multi level bill of material to a single level bill of material by tree selection.
 * 
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public class MergeBOMAction extends PopupAction {
	
	public static final String COMMAND = "mergeBOM";
	
	protected JTree tree;
	protected boolean actionResult;
	/**
	 * Constructs a new Instance.
	 */
	public MergeBOMAction(JTree tree) {
		
		super(COMMAND);
		setActionCommand(COMMAND);
		
		this.tree = tree;
	}

	protected String getCommand() {

		return COMMAND;
	}

	protected boolean successful() {
		
		return actionResult;
	}

	protected String validateAction() {

		return null;
	}

	protected void doAction(ActionEvent e) {
	
		if(tree != null) {
			
			mergeBOMFrom((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent());
		}
	}
	
    private void mergeBOMFrom(DefaultMutableTreeNode node) {
    	
    	BOMWrapper sourceBOM = (BOMWrapper)node.getUserObject();
    	MPPProductBOM targetBOM = new MPPProductBOM(Env.getCtx(), 0, null);

    	targetBOM.setBOMType(sourceBOM.getBOMType());
        targetBOM.setDescription(sourceBOM.getDescription());
        targetBOM.setM_AttributeSetInstance_ID(sourceBOM.getM_AttributeSetInstance_ID());
        targetBOM.setM_Product_ID(sourceBOM.getM_Product_ID());
        targetBOM.setName(sourceBOM.getName());
        targetBOM.setRevision(sourceBOM.getRevision());
        targetBOM.setValidFrom(sourceBOM.getValidFrom());
        targetBOM.setValidTo(sourceBOM.getValidTo());
        targetBOM.setValue(sourceBOM.getValue());
        targetBOM.setDocumentNo(sourceBOM.getDocumentNo());
        targetBOM.setC_UOM_ID(sourceBOM.getC_UOM_ID());
        
        actionResult = targetBOM.save();    
        
        if(successful()) {
        	
        	mergeInDepth(targetBOM.get_ID(), node);
        }
    }
	
    private void mergeInDepth(int bomId, DefaultMutableTreeNode parent) {
    	
    	DefaultMutableTreeNode node = null;
    	for(int i = 0; i < parent.getChildCount(); i++) {
    		
    		node = (DefaultMutableTreeNode)parent.getChildAt(i);
    		
    		if(node.isLeaf()) {
    			
    			createBOMLine(bomId, node);
    		}
    		else {
    			
    			mergeInDepth(bomId, node);
    		}
    		
    		if(!successful()) {
    			
    			break;
    		}
    	}
    }
    
    private void createBOMLine(int bomId, DefaultMutableTreeNode node) {
    	
    	BOMLineWrapper sourceLine = (BOMLineWrapper)node.getUserObject();
    	MPPProductBOMLine targetLine = new MPPProductBOMLine(Env.getCtx(),0,null);
        
    	targetLine.setPP_Product_BOM_ID(bomId);
        targetLine.setHelp(sourceLine.getHelp());
        targetLine.setM_ChangeNotice_ID(sourceLine.getM_ChangeNotice_ID());
        targetLine.setAssay(sourceLine.getAssay());
        targetLine.setQtyBatch(sourceLine.getQtyBatch());
        targetLine.setQtyBOM(sourceLine.getQtyBOM());
        targetLine.setIsQtyPercentage(sourceLine.isQtyPercentage());
        targetLine.setComponentType(sourceLine.getComponentType());          
        targetLine.setC_UOM_ID(sourceLine.getC_UOM_ID());
        targetLine.setForecast(sourceLine.getForecast());
        targetLine.setIsCritical(sourceLine.isCritical());
        targetLine.setIssueMethod(sourceLine.getIssueMethod());
        targetLine.setLine(sourceLine.getLine());
        targetLine.setLeadTimeOffset(sourceLine.getLeadTimeOffset());
        targetLine.setM_AttributeSetInstance_ID(sourceLine.getM_AttributeSetInstance_ID());
        targetLine.setM_Product_ID(sourceLine.getM_Product_ID());
        targetLine.setScrap(sourceLine.getScrap());
        targetLine.setValidFrom(sourceLine.getValidFrom());
        targetLine.setValidTo(sourceLine.getValidTo());
        
        actionResult = targetLine.save();
    }
}

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

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.util.Services;
import org.compiere.apps.search.PAttributeInstance;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MProduct;
import org.compiere.util.Env;
import org.eevolution.form.action.ProcessPopupAction;
import org.eevolution.model.wrapper.BOMLineWrapper;

import de.metas.i18n.IMsgBL;


/**
 * @author Victor Perez, e-Evolution, S.C.
 * Change Attribute Set Instance
 * 
 * Allows by tree selection the change of the product's attribute set instance by 
 * choosing an existing other from a list for this product.
 * 
 * !NOTICE! : Hardcoded process id 1000102 expected.
 * 
 * AD_Process: 
 * INSERT INTO ad_process VALUES (1000102, 0, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100, '10000007', 'Change ASI', NULL, NULL, '3', 'U', NULL, 'N', 'N', NULL, 'com.compiere.form.bom.action.ChangeASIAction', 0, 0, NULL, NULL, NULL, 'N');
 * 
 * AD_Process_Para:
 * INSERT INTO ad_process_para VALUES (1000249, 0, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100, 'M_Warehouse_ID', NULL, NULL, 1000102, 10, 18, 197, NULL, 'M_Warehouse_ID', 'Y', 0, 'N', 'N', NULL, NULL, NULL, NULL, NULL, 459, 'U');
 *
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
public class ChangeASIAction extends ProcessPopupAction {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 5683707111093783365L;

	public static final String COMMAND = "changeASI";

	// Hard coded process id. Expected process is 'Create RfQ'.
	final public static int PROCESS_ID = 1000102;
	
	protected JTree tree;
	
	/**
	 * Constructs a new Instance.
	 */
	public ChangeASIAction(JTree tree, JFrame window) {
		
		super(COMMAND, window);
		
		this.tree = tree;
	}

	@Override
	protected String getCommand() {

		return COMMAND;
	}

	@Override
	protected int getProcessID() {
	
		return PROCESS_ID;
	}
	
	@Override
	protected String validateAction() {

		String validate = null;
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
		BOMLineWrapper line = null;
		if(!(node.getUserObject() instanceof BOMLineWrapper)) {
			
			validate = "'"+node.getUserObject().getClass().getName()+"' isn't a type of 'BOMLineWrapper'(ClassCastException)";
		}
		else {
			
			line = (BOMLineWrapper)node.getUserObject();
	    	MProduct p = new MProduct(Env.getCtx(), line.getM_Product_ID(), null);
	    	
	    	if(p.getM_AttributeSet_ID() == 0) {
	    		
	    		validate = Services.get(IMsgBL.class).getMsg(Env.getCtx(), "PAttributeNoAttributeSet");
	    	}
		}
		
		return validate;
	}

	@Override
	protected void doProcess() {
	
		if(tree != null) {
			
			changeASI((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent());
		}
	}
	
	private void changeBOMLine(BOMLineWrapper line, MProduct p, MAttributeSetInstance asi) {
		
		//BigDecimal maxLength = messenger.getLength(p, asi);
		//BigDecimal neededLength = line.getLength();
		//BigDecimal onehundred = new BigDecimal(100);		

		//line.setQtyBatch(neededLength.divide(maxLength, 2, BigDecimal.ROUND_HALF_UP).multiply(onehundred));
		//line.setScrap(onehundred.subtract(line.getQtyBatch()).intValue());
    	line.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
    	
    	savePO(line.get());
	}
	
    private void changeASI(DefaultMutableTreeNode node) {
    	
    	BOMLineWrapper line = (BOMLineWrapper)node.getUserObject();
    	
    	int selectedASI = selectASIID(line);
    	if(selectedASI == -1) {
    		
    		setIgnoreChange(true);
    		return;
    	}

    	MAttributeSetInstance asi = new MAttributeSetInstance(Env.getCtx(), selectedASI, null);
    	MProduct p = new MProduct(Env.getCtx(), line.getM_Product_ID(), null);

    	changeBOMLine(line, p, asi);
    }

    private int selectASIID(BOMLineWrapper line) {

    	if (line.getM_Product_ID() <= 0)
			return -1;
    	
    	MProduct p = new MProduct(Env.getCtx(), line.getM_Product_ID(), null);
		PAttributeInstance pai = 
			new PAttributeInstance ((JFrame)null, p.getName(), getParameterValueAsInt("M_Warehouse_ID"), 0, line.getM_Product_ID(), 0);
		
		return pai.getM_AttributeSetInstance_ID();
    }
}

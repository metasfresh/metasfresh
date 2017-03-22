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
package org.eevolution.form.action;

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
import java.lang.reflect.Field;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.eevolution.model.wrapper.AbstractPOWrapper;


/**
 * Zoom Menu Action
 * 
 * Zooms directly to a static destination window referred from action's properties or to a dynamic destination,
 * dependent on action's instantiation w/o or with a target component.
 * 
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
public class ZoomMenuAction extends PopupAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3025550937253896044L;

	public static final String COMMAND = "zoom";
	
	protected Object target;
	protected int tableID;
	protected String tableName;
	
	/**
	 * Constructs a new Instance with static zoom target, determined by . The properties are used to determine the
	 * zoom target. 
	 */
	public ZoomMenuAction(int tableID, String tableName) {
		
		super(COMMAND);
		setActionCommand(COMMAND);
		
		this.tableID = tableID;
		this.tableName = tableName;
	}
	
	/**
	 * Constructs a new Instance with a overgiven target. E.g. use your JTree as target object. The nodes 
	 * of your tree owns their compiere model objects (PO) as user objects. 
	 * Supported classes are: JTree.  
	 * 
	 * @param target the target object.
	 * @throws an exception, if the target class isn't supported.
	 */
	public ZoomMenuAction(Object target) throws Exception {

		super(COMMAND);
		setActionCommand(COMMAND);
	
		if(target != null && !(target instanceof JTree)) {
		
			throw new Exception("Unsupported target component: "+ target.getClass().getName());
		}
		
		this.target = target;
	}
	
	@Override
	protected String getCommand() {

		return COMMAND;
	}

	@Override
	protected String validateAction() {

		return null;
	}
	
	@Override
	protected void doAction(ActionEvent e) {

		if(target != null) {
			
			zoom(target);
		}
		else {
			
			zoom();
		}
	}

	@Override
	protected boolean successful() {
		
		return true;
	}
	
	public Object getTarget() {
		
		return target;
	}
	
	private void zoom(Object obj) {

		if(obj instanceof JTree) {

		    JTree tree = (JTree)obj;
			
			Object node = tree.getSelectionPath().getLastPathComponent();
			
			int tableId = 0;
			int recordId = 0;
			try {

				tableId = getTableID((DefaultMutableTreeNode)node);
				recordId = getRecordID((DefaultMutableTreeNode)node);
			}
			catch(Exception e) {
				
				e.printStackTrace();
			}
			
			AEnv.zoom(tableId, recordId);
		}
	}
	
	private int getTableID(DefaultMutableTreeNode tn) throws Exception {
		
		PO po = null;
		if(tn.getUserObject() instanceof PO) {
			
			po = (PO)tn.getUserObject();
		}
		else if(tn.getUserObject() instanceof AbstractPOWrapper) {
			
			po = ((AbstractPOWrapper)tn.getUserObject()).get();
		}
		else {
			
			return -1;
		}

		Field f = po.getClass().getField("Table_ID");
		
		return f.getInt(null);
	}

	private int getRecordID(DefaultMutableTreeNode tn) {
		
		PO po = null;
		if(tn.getUserObject() instanceof PO) {
			
			po = (PO)tn.getUserObject();
		}
		else if(tn.getUserObject() instanceof AbstractPOWrapper) {
			
			po = ((AbstractPOWrapper)tn.getUserObject()).get();
		}
		
		return po == null ? -1 : po.get_ID();
	}
	
	private void zoom() {
		
		String tablename = tableName;
		int tableid = tableID;
		
		MQuery query = new MQuery(tablename);

		AWindow window = new AWindow();
		if (window.initWindow(tableid, query)) {
		
			AEnv.showCenterScreen(window);
		}

		window = null;
	}
}


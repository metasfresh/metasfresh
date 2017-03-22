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

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import javax.swing.JFrame;

import org.compiere.apps.ADialog;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.eevolution.tools.swing.SwingTool;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public abstract class ProcessPopupAction extends PopupAction {
	
	protected JFrame window;
	
	protected abstract void doProcess();
	
	protected abstract int getProcessID();
	
	public ProcessPopupAction(String property, JFrame window) {
		
		super(property);
		
		this.window = window;
	}
	
	@Override
	protected void beforeAction() {
		
		SwingTool.setCursorsFromParent(window.getContentPane(), true);
		super.beforeAction();

		throw new UnsupportedOperationException(); // TODO implement
	}		

	@Override
	protected void doAction(ActionEvent e) {

		if(successful()) {

			doProcess();
		}
	}
	
	@Override
	protected void afterAction() {

		super.afterAction();
	
		if(isIgnoreChange()) {
			
			SwingTool.setCursorsFromParent(window, false);
			return;
		}
		
		if(successful()) {
			
			ADialog.info(Env.getWindowNo(getWindow()), getWindow(), Msg.translate(Env.getCtx(), "Success"), getSuccessMsg());
		}
		else {
			
			ADialog.error(Env.getWindowNo(getWindow()), getWindow(), Msg.translate(Env.getCtx(), "Error"), getErrorMsg());
		}
		
		SwingTool.setCursorsFromParent(window, false);
	}
	
	protected int getParameterValueAsInt(String name) {
		
		Object o = getParameterValue(name);
		
		int value = -1;
		if(o instanceof Integer) {

			value = ((Integer)o).intValue();
		}
		else if(o instanceof BigDecimal) {
			
			value = ((BigDecimal)o).intValue();
		}
		else {
			
			value = Integer.parseInt(o.toString());
		}
		
		return value;
	}

	public JFrame getWindow() {
		
		return window;
	}
	
	protected Object getParameterValue(String name) {
		throw new UnsupportedOperationException(); // TODO implement
	}
}

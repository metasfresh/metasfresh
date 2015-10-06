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
package org.adempiere.webui.apps.form;

import org.adempiere.webui.component.Window;
import org.adempiere.webui.editor.WButtonEditor;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *	Display (and process) Payment Options.
 */
public class WPayment extends Window
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5886626149128018585L;
	private final WPaymentPanel panel;

	/**
	 *	Constructor
	 *
	 *	@param WindowNo	owning window
	 *  @param mTab     owning tab
	 *	@param button	button with access information
	 */
	public WPayment (int WindowNo, GridTab mTab, WButtonEditor button)
	{
		super();
		this.setTitle(Msg.getMsg(Env.getCtx(), "Payment"));
		this.setAttribute("mode", "modal");
		this.panel = new WPaymentPanel(WindowNo, mTab, button);
		panel.setWidth("100%");
		panel.setHeight("100%");
		this.appendChild(panel);
		//
		this.setHeight("400px");
		this.setWidth("500px");
		this.setBorder("normal");
	}	//	VPayment
	
	/**
	 *	Init OK to be able to make changes?
	 *  @return true if init OK
	 */
	public boolean isInitOK()
	{
		return panel.isInitOK();
	}	//	isInitOK
	
	public boolean needSave()
	{
		return panel.needSave();
	}	//	needSave
}	//	VPayment

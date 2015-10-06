/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package org.compiere.pos;

import net.miginfocom.swing.MigLayout;

import org.compiere.apps.ADialog;
import org.compiere.model.I_C_POSKey;
import org.compiere.util.CLogger;


/**
 *	Function Key Sub Panel
 *	
 *  @author Comunidad de Desarrollo OpenXpertya 
 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
 *         *Copyright (c) Jorg Janke
 *  @version $Id: SubFunctionKeys.java,v 1.1 2004/07/12 04:10:04 jjanke Exp $
 */
public class SubFunctionKeys extends PosSubPanel implements PosKeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2131406504920855582L;

	/**
	 * 	Constructor
	 *	@param posPanel POS Panel
	 */
	public SubFunctionKeys (PosBasePanel posPanel)
	{
		super (posPanel);
	}	//	PosSubFunctionKeys
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(SubFunctionKeys.class);
	
	/**
	 * 	Initialize
	 */
	public void init()
	{
		int C_POSKeyLayout_ID = p_pos.getC_POSKeyLayout_ID();
		if (C_POSKeyLayout_ID == 0)
			return;
		
		PosKeyPanel panel = new PosKeyPanel(C_POSKeyLayout_ID, this);
		this.setLayout(new MigLayout("fill, ins 0"));
		add(panel, "growx, growy");

	}	//	init
	
	/**
	 * 	Dispose - Free Resources
	 */
	public void dispose()
	{
		super.dispose();
	}	//	dispose

	/**
	 * Call back from key panel
	 */
	public void keyReturned(I_C_POSKey key) {
		// processed order
		if ( p_posPanel.m_order != null && p_posPanel.m_order.isProcessed() )
			return;
		
		// new line
		p_posPanel.f_curLine.setM_Product_ID(key.getM_Product_ID());
		p_posPanel.f_curLine.setPrice();
		p_posPanel.f_curLine.setQty(key.getQty());
		if ( !p_posPanel.f_curLine.saveLine() )
		{
			ADialog.error(0, this, "Could not save order line");
		}
		p_posPanel.updateInfo();
		return;
	}
	
}	//	PosSubFunctionKeys

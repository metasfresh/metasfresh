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
package org.compiere.process;

import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.model.MInOut;
import org.compiere.model.MInOutConfirm;
 
/**
 *	Create Confirmation From Shipment
 *	
 *  @author Jorg Janke
 *  @version $Id: InOutCreateConfirm.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class InOutCreateConfirm extends SvrProcess
{
	/**	Shipment				*/
	private int 	p_M_InOut_ID = 0;
	/**	Confirmation Type		*/
	private String		p_ConfirmType = null;

	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("ConfirmType"))
				p_ConfirmType = (String)para[i].getParameter();
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
		p_M_InOut_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Create Confirmation
	 *	@return document no
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		log.info("M_InOut_ID=" + p_M_InOut_ID + ", Type=" + p_ConfirmType);
		MInOut shipment = new MInOut (getCtx(), p_M_InOut_ID, get_TrxName());
		if (shipment.get_ID() == 0)
			throw new IllegalArgumentException("Not found M_InOut_ID=" + p_M_InOut_ID);
		//
		MInOutConfirm confirm = MInOutConfirm.create (shipment, p_ConfirmType, true);
		if (confirm == null)
			throw new Exception ("Cannot create Confirmation for " + shipment.getDocumentNo());
		//
		return confirm.getDocumentNo();
	}	//	doIt
	
}	//	InOutCreateConfirm

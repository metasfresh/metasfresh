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

import java.util.logging.Level;

import org.compiere.model.MRfQResponse;


/**
 *	RfQ Response - Invite.
 *	Send Inites/Reminder to Vendor to respond to RfQ.
 *	
 *  @author Jorg Janke
 *  @version $Id: RfQResponseInvite.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class RfQResponseInvite extends SvrProcess
{
	/**	RfQ Response				*/
	private int		p_C_RfQResponse_ID = 0;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_C_RfQResponse_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		MRfQResponse response = new MRfQResponse (getCtx(), p_C_RfQResponse_ID, get_TrxName());
		log.info("doIt - " + response);
		String error = response.getRfQ().checkQuoteTotalAmtOnly();
		if (error != null && error.length() > 0)
			throw new Exception (error);
		//	Send it
		if (response.sendRfQ())
			return "OK";
		//
		return "@Error@";
	}	//	doIt
	
}	//	RfQResponseInvite

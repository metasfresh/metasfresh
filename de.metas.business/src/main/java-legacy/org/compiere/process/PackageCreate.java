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

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.MInOut;
import org.compiere.model.MPackage;
 
/**
 *	Create Package from Shipment for Shipper
 *	
 *  @author Jorg Janke
 *  @version $Id: PackageCreate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class PackageCreate extends SvrProcess
{
	/**	Shipper				*/
	private int		p_M_Shipper_ID = 0;
	/** Parent				*/
	private int		p_M_InOut_ID = 0;
	

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
			else if (name.equals("M_Shipper_ID"))
				p_M_Shipper_ID = para[i].getParameterAsInt();
			else if (name.equals("M_InOut_ID")) // BF [ 1754889 ] Create Package error
				p_M_InOut_ID = para[i].getParameterAsInt();
			else
				log.error("prepare - Unknown Parameter: " + name);
		}

		// Bug [ 1754889 ] Create Package error
		// Commenting these lines because this process is called also from window "Ship/Receipt Confirm"
		// if (p_M_InOut_ID == 0)
			// p_M_InOut_ID = getRecord_ID();

	}	//	prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		log.info("doIt - M_InOut_ID=" + p_M_InOut_ID + ", M_Shipper_ID=" + p_M_Shipper_ID);
		if (p_M_InOut_ID == 0)
			throw new IllegalArgumentException("No Shipment");
		if (p_M_Shipper_ID == 0)
			throw new IllegalArgumentException("No Shipper");
		MInOut shipment = new MInOut (getCtx(), p_M_InOut_ID, null);
		if (shipment.get_ID() != p_M_InOut_ID)
			throw new IllegalArgumentException("Cannot find Shipment ID=" + p_M_InOut_ID);
		
		final I_M_Shipper shipper = InterfaceWrapperHelper.create(getCtx(), p_M_Shipper_ID, I_M_Shipper.class, get_TrxName());
		if (shipper.getM_Shipper_ID() != p_M_Shipper_ID)
			throw new IllegalArgumentException("Cannot find Shipper ID=" + p_M_InOut_ID);
		//
		
		MPackage pack = MPackage.create (shipment, shipper, null);
		
		return pack.getDocumentNo();
	}	//	doIt
	
}	//	PackageCreate

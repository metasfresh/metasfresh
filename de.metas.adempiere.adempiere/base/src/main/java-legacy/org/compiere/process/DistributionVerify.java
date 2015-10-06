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

import org.compiere.model.MDistribution;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.AdempiereUserError;


/**
 *	Verify GL Distribution
 *	
 *  @author Jorg Janke
 *  @version $Id: DistributionVerify.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class DistributionVerify extends SvrProcess
{

	/**
	 * 	Prepare
	 */
	protected void prepare ()
	{
	}	//	prepare

	/**
	 * 	Process
	 *	@return message
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		log.info("doIt - GL_Distribution_ID=" + getRecord_ID());
		MDistribution distribution = new MDistribution (getCtx(), getRecord_ID(), get_TrxName());
		if (distribution.get_ID() == 0)
			throw new AdempiereUserError("Not found GL_Distribution_ID=" + getRecord_ID());

		String error = distribution.validate();
		boolean saved = distribution.save();
		if (error != null)
			throw new AdempiereUserError(error);
		if (!saved)
			throw new AdempiereSystemError("@NotSaved@");
		
		return "@OK@";
	}	//	doIt

}	//	DistributionVerify

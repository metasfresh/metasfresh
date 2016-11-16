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

import java.math.BigDecimal;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

import org.compiere.model.MOrder;

/**
 *  Copy Order Lines
 *
 *	@author Jorg Janke
 *	@version $Id: CopyFromOrder.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class CopyFromOrder extends SvrProcess
{
	/**	The Order				*/
	private int		p_C_Order_ID = 0;

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
			else if (name.equals("C_Order_ID"))
				p_C_Order_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		int To_C_Order_ID = getRecord_ID();
		log.info("From C_Order_ID=" + p_C_Order_ID + " to " + To_C_Order_ID);
		if (To_C_Order_ID == 0)
			throw new IllegalArgumentException("Target C_Order_ID == 0");
		if (p_C_Order_ID == 0)
			throw new IllegalArgumentException("Source C_Order_ID == 0");
		MOrder from = new MOrder (getCtx(), p_C_Order_ID, get_TrxName());
		MOrder to = new MOrder (getCtx(), To_C_Order_ID, get_TrxName());
		//
		int no = to.copyLinesFrom (from, false, false);		//	no Attributes
		//
		return "@Copied@=" + no;
	}	//	doIt

}	//	CopyFromOrder

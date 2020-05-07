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

//import org.compiere.process.*;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

import org.compiere.model.I_C_Payment;
import org.compiere.model.MPayment;
import org.compiere.util.Util;

/**
 * Online Payment Process
 * 
 * @author Jorg Janke
 * @version $Id: PaymentOnline.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class PaymentOnline extends JavaProcess
{
	private String creditCardNumber;
	private String creditCardVV;

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
			// metas-01300
			else if (name.equals(I_C_Payment.COLUMNNAME_CreditCardNumber))
			{
				creditCardNumber = (String)para[i].getParameter();
			}
			else if (name.equals(I_C_Payment.COLUMNNAME_CreditCardVV))
			{
				creditCardVV = (String)para[i].getParameter();
			}
			// metas end
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws Exception
	{
		log.info("Record_ID=" + getRecord_ID());
		// get Payment
		final MPayment pp = new MPayment(getCtx(), getRecord_ID(), get_TrxName());

		// metas-01300
		if (!Util.isEmpty(creditCardNumber))
		{
			pp.setCreditCardNumber(creditCardNumber);
		}
		if (!Util.isEmpty(creditCardVV))
		{
			pp.setCreditCardVV(creditCardVV);
		}
		// metas end
		
		// Process it
		boolean ok = pp.processOnline();
		pp.saveEx();
		if (!ok)
			throw new Exception(pp.getErrorMessage());
		return "OK";
	} // doIt

} // PaymentOnline

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

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoicePaySchedule;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *	Validate Invoice Payment Schedule
 *	
 *  @author Jorg Janke
 *  @version $Id: InvoicePayScheduleValidate.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class InvoicePayScheduleValidate extends SvrProcess
{
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
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		log.info("C_InvoicePaySchedule_ID=" + getRecord_ID());
		MInvoicePaySchedule[] schedule = MInvoicePaySchedule.getInvoicePaySchedule
			(getCtx(), 0, getRecord_ID(), null);
		if (schedule.length == 0)
			throw new IllegalArgumentException("InvoicePayScheduleValidate - No Schedule");
		//	Get Invoice
		MInvoice invoice = new MInvoice (getCtx(), schedule[0].getC_Invoice_ID(), null);
		if (invoice.get_ID() == 0)
			throw new IllegalArgumentException("InvoicePayScheduleValidate - No Invoice");
		//
		BigDecimal total = Env.ZERO;
		for (int i = 0; i < schedule.length; i++)
		{
			BigDecimal due = schedule[i].getDueAmt();
			if (due != null)
				total = total.add(due);
		}
		boolean valid = invoice.getGrandTotal().compareTo(total) == 0;
		invoice.setIsPayScheduleValid(valid);
		invoice.save();
		//	Schedule
		for (int i = 0; i < schedule.length; i++)
		{
			if (schedule[i].isValid() != valid)
			{
				schedule[i].setIsValid(valid);
				schedule[i].save();				
			}
		}
		String msg = "@OK@";
		if (!valid)
			msg = "@GrandTotal@ = " + invoice.getGrandTotal() 
				+ " <> @Total@ = " + total 
				+ "  - @Difference@ = " + invoice.getGrandTotal().subtract(total); 
		return Msg.parseTranslation(getCtx(), msg);
	}	//	doIt

}	//	InvoicePayScheduleValidate

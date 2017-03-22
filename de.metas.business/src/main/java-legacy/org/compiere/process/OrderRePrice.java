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
import de.metas.process.JavaProcess;

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.util.Env;

/**
 *	Re-Price Order or Invoice
 *	
 *  @author Jorg Janke
 *  @version $Id: OrderRePrice.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class OrderRePrice extends JavaProcess
{
	/**	Order to re-price		*/
	private int 	p_C_Order_ID = 0;
	/** Invoice to re-price		*/
	private int 	p_C_Invoice_ID = 0;
	
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
			else if (name.equals("C_Invoice_ID"))
				p_C_Invoice_ID = ((BigDecimal)para[i].getParameter()).intValue();
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
		log.info("C_Order_ID=" + p_C_Order_ID + ", C_Invoice_ID=" + p_C_Invoice_ID);
		if (p_C_Order_ID == 0 && p_C_Invoice_ID == 0)
			throw new IllegalArgumentException("Nothing to do");

		String retValue = "";
		if (p_C_Order_ID != 0)
		{
			MOrder order = new MOrder (getCtx(), p_C_Order_ID, get_TrxName());
			BigDecimal oldPrice = order.getGrandTotal();
			MOrderLine[] lines = order.getLines();
			for (int i = 0; i < lines.length; i++)
			{
				lines[i].setPrice(order.getM_PriceList_ID());
				lines[i].save();
			}
			order = new MOrder (getCtx(), p_C_Order_ID, get_TrxName());
			BigDecimal newPrice = order.getGrandTotal();
			retValue = order.getDocumentNo() + ":  " + oldPrice + " -> " + newPrice;
		}
		if (p_C_Invoice_ID != 0)
		{
			MInvoice invoice = new MInvoice (getCtx(), p_C_Invoice_ID, null);
			BigDecimal oldPrice = invoice.getGrandTotal();
			MInvoiceLine[] lines = invoice.getLines(false);
			for (int i = 0; i < lines.length; i++)
			{
				lines[i].setPrice(invoice.getM_PriceList_ID(), invoice.getC_BPartner_ID());
				lines[i].save();
			}
			invoice = new MInvoice (getCtx(), p_C_Invoice_ID, null);
			BigDecimal newPrice = invoice.getGrandTotal();
			if (retValue.length() > 0)
				retValue += Env.NL;
			retValue += invoice.getDocumentNo() + ":  " + oldPrice + " -> " + newPrice;
		}
		//
		return retValue;
	}	//	doIt

}	//	OrderRePrice

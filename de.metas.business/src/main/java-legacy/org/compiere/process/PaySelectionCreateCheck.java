/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.process;

import java.util.ArrayList;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.MPaySelection;
import org.compiere.model.MPaySelectionCheck;
import org.compiere.model.MPaySelectionLine;
import org.compiere.model.X_C_Order;
import org.compiere.util.AdempiereUserError;

/**
 * Create Checks from Payment Selection Line
 * 
 * @author Jorg Janke
 * @version $Id: PaySelectionCreateCheck.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class PaySelectionCreateCheck extends SvrProcess
{
	/** Target Payment Rule */
	private String p_PaymentRule = null;
	/** Payment Selection */
	private int p_C_PaySelection_ID = 0;
	/** The checks */
	private ArrayList<MPaySelectionCheck> m_list = new ArrayList<MPaySelectionCheck>();

	public static String ERR_PaySelection_NoBPBankAccount = "ERR_PaySelection_NoBPBankAccount";

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("PaymentRule"))
				p_PaymentRule = (String)para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}
		p_C_PaySelection_ID = getRecord_ID();
		if (p_PaymentRule != null && p_PaymentRule.equals(X_C_Order.PAYMENTRULE_DirectDebit))
			p_PaymentRule = null;
	}	// prepare

	/**
	 * Perform process.
	 * 
	 * @return Message (clear text)
	 * @throws Exception if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("C_PaySelection_ID=" + p_C_PaySelection_ID
				+ ", PaymentRule=" + p_PaymentRule);

		MPaySelection psel = new MPaySelection(getCtx(), p_C_PaySelection_ID, get_TrxName());
		if (psel.get_ID() == 0)
		{
			throw new IllegalArgumentException("Not found C_PaySelection_ID=" + p_C_PaySelection_ID);
		}
		if (psel.isProcessed())
		{
			throw new IllegalArgumentException("@Processed@");
		}
		//
		MPaySelectionLine[] lines = psel.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MPaySelectionLine line = lines[i];
			if (!line.isActive() || line.isProcessed())
				continue;
			createCheck(line);
		}
		//
		psel.setProcessed(true);
		psel.save();

		return "@C_PaySelectionCheck_ID@ - #" + m_list.size();
	}	// doIt

	/**
	 * Create Check from line
	 * 
	 * @param line
	 * @throws Exception for invalid bank accounts
	 */
	private void createCheck(MPaySelectionLine line) throws Exception
	{
		// Try to find one
		for (int i = 0; i < m_list.size(); i++)
		{
			MPaySelectionCheck check = m_list.get(i);
			// Add to existing
			if (check.getC_BPartner_ID() == line.getInvoice().getC_BPartner_ID())
			{
				check.addLine(line);
				if (!check.save())
				{
					throw new IllegalStateException("Cannot save MPaySelectionCheck");
				}
				line.setC_PaySelectionCheck_ID(check.getC_PaySelectionCheck_ID());
				line.setProcessed(true);
				if (!line.save())
				{
					throw new IllegalStateException("Cannot save MPaySelectionLine");
				}
				return;
			}
		}
		// Create new
		String PaymentRule = line.getPaymentRule();
		if (p_PaymentRule != null)
		{
			if (!X_C_Order.PAYMENTRULE_DirectDebit.equals(PaymentRule))
				PaymentRule = p_PaymentRule;
		}
		MPaySelectionCheck check = new MPaySelectionCheck(line, PaymentRule);
		if (!check.isValid())
		{
			String msg = Services.get(IMsgBL.class).getMsg(getCtx(), ERR_PaySelection_NoBPBankAccount);
			throw new AdempiereUserError(msg);
		}
		if (!check.save())
			throw new IllegalStateException("Cannot save MPaySelectionCheck");
		line.setC_PaySelectionCheck_ID(check.getC_PaySelectionCheck_ID());
		line.setProcessed(true);
		if (!line.save())
			throw new IllegalStateException("Cannot save MPaySelectionLine");
		m_list.add(check);
	}	// createCheck

}	// PaySelectionCreateCheck

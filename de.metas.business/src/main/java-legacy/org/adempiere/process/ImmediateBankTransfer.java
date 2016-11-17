/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Alejandro Falcone                                     *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Alejandro Falcone (afalcone@users.sourceforge.net)                *
*                      http://www.openbiz.com.ar                      *
*                                                                     * 
* Sponsors:                                                           *
* - Idalica Inc. (http://www.idalica.com)                             *
**********************************************************************/
package org.adempiere.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.MCash;
import org.compiere.model.MCashBook;
import org.compiere.model.MCashLine;
import org.compiere.process.DocAction;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;
 
/**
 *  Bank Transfer. Generate a CashJournal entry with 2 lines (Bank Transfer)
 *  
 *  For Bank Transfer From Bank Account "A" 
 *                    To   Bank Account "B"
 *                    
 *  - Generate a Cash Journal entry with 2 lines (Cash Type = Transfer):
 *  		From Bank Account "A"  To Cash Journal
 *  		From Cash Journal      To Bank Account "B"
 *  
 *	@author Alejandro Falcone
 *	
 **/
public class ImmediateBankTransfer extends JavaProcess
{
	 /** DocAction          */
    private String      p_docAction = DocAction.ACTION_Complete;
    /** Created #           */
    private int         m_created = 0;

	private int m_C_Currency_ID;			// Currency
    
    private String p_Name = "";					// Name
	private String p_Description= "";			// Description
	private int p_C_CashBook_ID = 0;   			// CashBook to be used as bridge
	private BigDecimal p_Amount = new BigDecimal(0);  			// Amount to be transfered between the accounts
	private int p_From_C_BP_BankAccount_ID = 0;	// Bank Account From
	private int p_To_C_BP_BankAccount_ID= 0;		// Bank Account To
	private Timestamp	p_StatementDate = null;  // Date Statement
	private Timestamp	p_DateAcct = null;  // Date Account
   
    
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("From_C_BP_BankAccount_ID"))
			{
				p_From_C_BP_BankAccount_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals("To_C_BP_BankAccount_ID"))
			{
				p_To_C_BP_BankAccount_ID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals("C_CashBook_ID"))
				p_C_CashBook_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("Amount"))
				p_Amount = ((BigDecimal)para[i].getParameter());
			else if (name.equals("Name"))
				p_Name = (String)para[i].getParameter();
			else if (name.equals("Description"))
				p_Description = (String)para[i].getParameter();
			else if (name.equals("StatementDate"))
				p_StatementDate = (Timestamp)para[i].getParameter();
			else if (name.equals("DateAcct"))
				p_DateAcct = (Timestamp)para[i].getParameter();
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (translated text)
	 *  @throws Exception if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("From Bank="+p_From_C_BP_BankAccount_ID+" - To Bank="+p_To_C_BP_BankAccount_ID
				+ " - C_CashBook_ID="+p_C_CashBook_ID+" - Amount="+p_Amount+" - Name="+p_Name
				+ " - Description="+p_Description+ " - Statement Date="+p_StatementDate+
				" - Date Account="+p_DateAcct);

		if (p_To_C_BP_BankAccount_ID == 0 || p_From_C_BP_BankAccount_ID == 0)
			throw new IllegalArgumentException("Banks required");

		if (p_Name == null || p_Name.length() == 0)
			throw new IllegalArgumentException("Name required");

		// To_C_BP_BankAccount_ID MUST be different than From_C_BP_BankAccount_ID
		if (p_To_C_BP_BankAccount_ID == p_From_C_BP_BankAccount_ID)
			throw new AdempiereUserError ("Banks From and To must be different");

		// Banks and CashBook must have same currency
		if (!isSameCurrency())
			throw new AdempiereUserError ("Banks and CashBook must have same currency");		

		if (p_Amount.compareTo(new BigDecimal(0)) == 0)
			throw new AdempiereUserError ("Amount required");

		//	Login Date
		if (p_StatementDate == null)
			p_StatementDate = Env.getContextAsDate(getCtx(), "#Date");
		if (p_StatementDate == null)
			p_StatementDate = new Timestamp(System.currentTimeMillis());			

		if (p_DateAcct == null)
			p_DateAcct = p_StatementDate;

		generateBankTransfer();

		return "@Created@ = " + m_created;
	}	//	doIt

	/**
	 * To check CashBook and Banks have the same currency 
	 * @return
	 */
	private boolean isSameCurrency()
	{

		MCashBook mcash = new MCashBook	(getCtx(),p_C_CashBook_ID, get_TrxName());
		I_C_BP_BankAccount mBankFrom = InterfaceWrapperHelper.create(getCtx(), p_From_C_BP_BankAccount_ID, I_C_BP_BankAccount.class, getTrxName());
		I_C_BP_BankAccount mBankTo = InterfaceWrapperHelper.create(getCtx(), p_To_C_BP_BankAccount_ID, I_C_BP_BankAccount.class, getTrxName());

		if ((mcash.getC_Currency_ID() != mBankFrom.getC_Currency_ID()) || 
				(mcash.getC_Currency_ID() != mBankTo.getC_Currency_ID()))
			return false ;

		m_C_Currency_ID = mcash.getC_Currency_ID();

		return true;
	}  // isSameCurrency
	
	private MCash createCash()
	{

		MCash cash = new MCash (getCtx(), 0, get_TrxName());

		cash.setName(p_Name);
		cash.setDescription(p_Description);
		cash.setDateAcct(p_DateAcct);
		cash.setStatementDate(p_StatementDate);
		cash.setC_CashBook_ID(p_C_CashBook_ID);
		if (!cash.save())
		{
			throw new IllegalStateException("Could not create Cash");
		}
		return cash;
	}  //  createCash
	
	private MCashLine[] createCashLines(MCash cash)
	{

		ArrayList<MCashLine> cashLineList = new ArrayList<MCashLine>();

		// From Bank (From) to CashLine
		MCashLine cashLine = new MCashLine (cash);
		cashLine.setAmount(p_Amount);
		cashLine.setC_BP_BankAccount_ID(p_From_C_BP_BankAccount_ID);
		cashLine.setC_Currency_ID(m_C_Currency_ID);

		if (p_Description != null)
			cashLine.setDescription(p_Description);
		else
			cashLine.setDescription(p_Name);

		cashLine.setCashType("T"); // Transfer

		if (!cashLine.save())
		{
			throw new IllegalStateException("Could not create Cash line (From Bank)");
		}
		cashLineList.add(cashLine);

		// From CashLine to Bank (To)
		cashLine = new MCashLine (cash);
		cashLine.setAmount(p_Amount.negate());
		cashLine.setC_BP_BankAccount_ID(p_To_C_BP_BankAccount_ID);
		cashLine.setC_Currency_ID(m_C_Currency_ID);
		if (p_Description != null)
			cashLine.setDescription(p_Description);
		else
			cashLine.setDescription(p_Name);

		cashLine.setCashType("T"); // Transfer

		if (!cashLine.save())
		{
			throw new IllegalStateException("Could not create Cash line (To Bank)");
		}
		cashLineList.add(cashLine);

		MCashLine cashLines[] = new MCashLine[cashLineList.size()];
		cashLineList.toArray(cashLines);
		return cashLines;

	}  //  createCashLines
	
	
	/**
	 * Generate CashJournal
	 *
	 */
	private void generateBankTransfer()
	{
	
		//	Create Cash & CashLines
		MCash cash = createCash();
		MCashLine cashLines[]= createCashLines(cash);
		
		StringBuffer processMsg = new StringBuffer(cash.getDocumentNo());
	
		cash.setDocAction(p_docAction);
		if (!cash.processIt(p_docAction))
	    {
	        processMsg.append(" (NOT Processed)");
	        log.warn("Cash Processing failed: " + cash + " - " + cash.getProcessMsg());
	        addLog(cash.getC_Cash_ID(), cash.getStatementDate(), null,
					"Cash Processing failed: " + cash + " - "
							+ cash.getProcessMsg()
							+ " / please complete it manually");
	    }
		if (!cash.save())
		{
           throw new IllegalStateException("Could not create Cash");
		}
		 
		 // Add processing information to process log
        addLog(cash.getC_Cash_ID(), cash.getStatementDate(), null, processMsg.toString());
        m_created++;
	}  //  generateBankTransfer
	
	
}	//	ImmediateBankTransfer

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
import java.sql.Timestamp;

import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_I_BankStatement;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.X_I_BankStatement;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

/**
 *	Create Payment from Bank Statement Info
 *	
 *  @author Jorg Janke
 *  @version $Id: BankStatementPayment.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class BankStatementPayment extends SvrProcess
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
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		final String tableName = getTableName();
		final int Record_ID = getRecord_ID();
		log.info("Table_Name=" + tableName + ", Record_ID=" + Record_ID);
		
		if (I_I_BankStatement.Table_Name.equals(tableName))
			return createPayment (new X_I_BankStatement(getCtx(), Record_ID, get_TrxName()));
		else if (I_C_BankStatementLine.Table_Name.equals(tableName))
			return createPayment (new MBankStatementLine(getCtx(), Record_ID, get_TrxName()));
		
		throw new AdempiereSystemError("??");
	}	//	doIt

	/**
	 * 	Create Payment for Import
	 *	@param ibs import bank statement
	 *	@return Message
	 *  @throws Exception if not successful
	 */
	private String createPayment (X_I_BankStatement ibs) throws Exception
	{
		if (ibs == null || ibs.getC_Payment_ID() != 0)
			return "--";
		log.debug(ibs.toString());
		if (ibs.getC_Invoice_ID() == 0 && ibs.getC_BPartner_ID() == 0)
			throw new AdempiereUserError ("@NotFound@ @C_Invoice_ID@ / @C_BPartner_ID@");
		if (ibs.getC_BP_BankAccount_ID() == 0)
			throw new AdempiereUserError ("@NotFound@ @C_BP_BankAccount_ID@");
		//
		MPayment payment = createPayment (ibs.getC_Invoice_ID(), ibs.getC_BPartner_ID(),
			ibs.getC_Currency_ID(), ibs.getStmtAmt(), ibs.getTrxAmt(), 
			ibs.getC_BP_BankAccount_ID(), ibs.getStatementLineDate() == null ? ibs.getStatementDate() : ibs.getStatementLineDate(), 
			ibs.getDateAcct(), ibs.getDescription(), ibs.getAD_Org_ID());
		if (payment == null)
			throw new AdempiereSystemError("Could not create Payment");
		
		ibs.setC_Payment_ID(payment.getC_Payment_ID());
		ibs.setC_Currency_ID (payment.getC_Currency_ID());
		ibs.setTrxAmt(payment.getPayAmt());
		ibs.save();
		//
		String retString = "@C_Payment_ID@ = " + payment.getDocumentNo();
		if (payment.getOverUnderAmt().signum() != 0)
			retString += " - @OverUnderAmt@=" + payment.getOverUnderAmt();
		return retString;
	}	//	createPayment - Import
	
	/**
	 * 	Create Payment for BankStatement
	 *	@param bsl bank statement Line
	 *	@return Message
	 *  @throws Exception if not successful
	 */
	private String createPayment (MBankStatementLine bsl) throws Exception
	{
		if (bsl == null || bsl.getC_Payment_ID() != 0)
			return "--";
		log.debug(bsl.toString());
		if (bsl.getC_Invoice_ID() == 0 && bsl.getC_BPartner_ID() == 0)
			throw new AdempiereUserError ("@NotFound@ @C_Invoice_ID@ / @C_BPartner_ID@");
		//
		MBankStatement bs = new MBankStatement (getCtx(), bsl.getC_BankStatement_ID(), get_TrxName());
		//
		MPayment payment = createPayment (bsl.getC_Invoice_ID(), bsl.getC_BPartner_ID(),
			bsl.getC_Currency_ID(), bsl.getStmtAmt(), bsl.getTrxAmt(), 
			bs.getC_BP_BankAccount_ID(), bsl.getStatementLineDate(), bsl.getDateAcct(),
			bsl.getDescription(), bsl.getAD_Org_ID());
		if (payment == null)
			throw new AdempiereSystemError("Could not create Payment");
		//	update statement
		bsl.setPayment(payment);
		bsl.save();
		//
		String retString = "@C_Payment_ID@ = " + payment.getDocumentNo();
		if (payment.getOverUnderAmt().signum() != 0)
			retString += " - @OverUnderAmt@=" + payment.getOverUnderAmt();
		return retString;
	}	//	createPayment


	/**
	 * 	Create actual Payment
	 *	@param C_Invoice_ID invoice
	 *	@param C_BPartner_ID partner ignored when invoice exists
	 *	@param C_Currency_ID currency
	 *	@param StmtAmt statement amount
	 *	@param TrxAmt transaction amt
	 *	@param C_BP_BankAccount_ID bank account
	 *	@param DateTrx transaction date
	 *	@param DateAcct	accounting date
	 *	@param Description description
	 *	@param AD_Org_ID org
	 *	@return payment
	 */
	private MPayment createPayment (int C_Invoice_ID, int C_BPartner_ID, 
		int C_Currency_ID, BigDecimal StmtAmt, BigDecimal TrxAmt,
		int C_BP_BankAccount_ID, Timestamp DateTrx, Timestamp DateAcct, 
		String Description, int AD_Org_ID)
	{
		//	Trx Amount = Payment overwrites Statement Amount if defined
		BigDecimal PayAmt = TrxAmt;
		if (PayAmt == null || Env.ZERO.compareTo(PayAmt) == 0)
			PayAmt = StmtAmt;
		if (C_Invoice_ID == 0
			&& (PayAmt == null || Env.ZERO.compareTo(PayAmt) == 0))
			throw new IllegalStateException ("@PayAmt@ = 0");
		if (PayAmt == null)
			PayAmt = Env.ZERO;
		//
		MPayment payment = new MPayment (getCtx(), 0, get_TrxName());
		payment.setAD_Org_ID(AD_Org_ID);
		payment.setC_BP_BankAccount_ID(C_BP_BankAccount_ID);
		payment.setTenderType(MPayment.TENDERTYPE_Check);
		if (DateTrx != null)
			payment.setDateTrx(DateTrx);
		else if (DateAcct != null)
			payment.setDateTrx(DateAcct);
		if (DateAcct != null)
			payment.setDateAcct(DateAcct);
		else
			payment.setDateAcct(payment.getDateTrx());
		payment.setDescription(Description);
		//
		if (C_Invoice_ID != 0)
		{
			MInvoice invoice = new MInvoice (getCtx(), C_Invoice_ID, null);
			payment.setC_DocType_ID(invoice.isSOTrx());		//	Receipt
			payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
			payment.setC_BPartner_ID (invoice.getC_BPartner_ID());
			if (PayAmt.signum() != 0)	//	explicit Amount
			{
				payment.setC_Currency_ID(C_Currency_ID);
				if (invoice.isSOTrx())
					payment.setPayAmt(PayAmt);
				else	//	payment is likely to be negative
					payment.setPayAmt(PayAmt.negate());
				payment.setOverUnderAmt(invoice.getGrandTotal(true).subtract(payment.getPayAmt()));
			}
			else	// set Pay Amout from Invoice
			{
				payment.setC_Currency_ID(invoice.getC_Currency_ID());
				payment.setPayAmt(invoice.getGrandTotal(true));
			}
		}
		else if (C_BPartner_ID != 0)
		{
			payment.setC_BPartner_ID(C_BPartner_ID);
			payment.setC_Currency_ID(C_Currency_ID);
			if (PayAmt.signum() < 0)	//	Payment
			{
				payment.setPayAmt(PayAmt.abs());
				payment.setC_DocType_ID(false);
			}
			else	//	Receipt
			{
				payment.setPayAmt(PayAmt);
				payment.setC_DocType_ID(true);
			}
		}
		else
			return null;
		payment.save();
		//
		payment.processIt(MPayment.DOCACTION_Complete);
		payment.save();
		return payment;		
	}	//	createPayment

}	//	BankStatementPayment

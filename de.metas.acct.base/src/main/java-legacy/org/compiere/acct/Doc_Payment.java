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
package org.compiere.acct;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCharge;

/**
 * Post Payment Documents.
 * 
 * <pre>
 *  Table:              C_Payment (335)
 *  Document Types      ARP, APP
 * </pre>
 * 
 * @author Jorg Janke
 * @version $Id: Doc_Payment.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Payment extends Doc<DocLine<Doc_Payment>>
{
	// services
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	/**
	 * Constructor
	 * 
	 * @param ass accounting schemata
	 * @param rs record
	 * @param trxName trx
	 */
	public Doc_Payment(final IDocBuilder docBuilder)
	{
		super(docBuilder);
	}	// Doc_Payment

	/** Tender Type */
	private String m_TenderType = null;
	/** Prepayment */
	private boolean m_Prepayment = false;

	@Override
	protected void loadDocumentDetails()
	{
		final I_C_Payment pay = getModel(I_C_Payment.class);
		setDateDoc(pay.getDateTrx());
		setC_BP_BankAccount_ID(pay.getC_BP_BankAccount_ID());
		m_TenderType = pay.getTenderType();
		m_Prepayment = pay.isPrepayment();

		// Amount
		setAmount(Doc.AMTTYPE_Gross, pay.getPayAmt());
	}

	/**************************************************************************
	 * Get Source Currency Balance - always zero
	 * 
	 * @return Zero (always balanced)
	 */
	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}   // getBalance

	/**
	 * Create Facts (the accounting logic) for
	 * ARP, APP.
	 * 
	 * <pre>
	 *  ARP
	 *      BankInTransit   DR
	 *      UnallocatedCash         CR
	 *      or Charge/C_Prepayment
	 *  APP
	 *      PaymentSelect   DR
	 *      or Charge/V_Prepayment
	 *      BankInTransit           CR
	 *  CashBankTransfer
	 *      -
	 * </pre>
	 * 
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts(final MAcctSchema as)
	{
		// create Fact Header
		final Fact fact = new Fact(this, as, Fact.POST_Actual);
		final int AD_Org_ID = getBank_Org_ID();		// Bank Account Org

		// Cash Transfer
		if ("X".equals(getTenderType()) && !isCashAsPayment())
		{
			final ArrayList<Fact> facts = new ArrayList<>();
			facts.add(fact);
			return facts;
		}

		final String documentType = getDocumentType();
		if (DOCTYPE_ARReceipt.equals(documentType))
		{
			// Asset (DR)
			FactLine fl = fact.createLine(null, getBankAccount(as),
					getC_Currency_ID(), getAmount(), null);
			if (fl != null && AD_Org_ID != 0)
				fl.setAD_Org_ID(AD_Org_ID);
			//
			MAccount acct = null;
			if (getC_Charge_ID() != 0)
				acct = MCharge.getAccount(getC_Charge_ID(), as, getAmount());
			else if (isPrepayment())
				acct = getAccount(Doc.ACCTTYPE_C_Prepayment, as);
			else
				acct = getAccount(Doc.ACCTTYPE_UnallocatedCash, as);
			fl = fact.createLine(null, acct,
					getC_Currency_ID(), null, getAmount());
			if (fl != null && AD_Org_ID != 0
					&& getC_Charge_ID() == 0)		// don't overwrite charge
				fl.setAD_Org_ID(AD_Org_ID);
		}
		// APP
		else if (DOCTYPE_APPayment.equals(documentType))
		{
			MAccount acct = null;
			if (getC_Charge_ID() != 0)
				acct = MCharge.getAccount(getC_Charge_ID(), as, getAmount());
			else if (isPrepayment())
				acct = getAccount(Doc.ACCTTYPE_V_Prepayment, as);
			else
				acct = getAccount(Doc.ACCTTYPE_PaymentSelect, as);
			FactLine fl = fact.createLine(null, acct,
					getC_Currency_ID(), getAmount(), null);
			if (fl != null && AD_Org_ID != 0
					&& getC_Charge_ID() == 0)		// don't overwrite charge
				fl.setAD_Org_ID(AD_Org_ID);

			// Asset (CR)
			fl = fact.createLine(null, getBankAccount(as),
					getC_Currency_ID(), null, getAmount());
			if (fl != null && AD_Org_ID != 0)
				fl.setAD_Org_ID(AD_Org_ID);
		}
		else
		{
			throw newPostingException()
					.setC_AcctSchema(as)
					.setFact(fact)
					.setPostingStatus(PostingStatus.Error)
					.setDetailMessage("DocumentType unknown: " + documentType);

		}

		//
		final ArrayList<Fact> facts = new ArrayList<>();
		facts.add(fact);
		return facts;
	}   // createFact

	/**
	 * Get AD_Org_ID from Bank Account
	 * 
	 * @return AD_Org_ID or 0
	 */
	private final int getBank_Org_ID()
	{
		final I_C_BP_BankAccount bpBankAccount = getC_BP_BankAccount();
		if (bpBankAccount == null)
		{
			return 0;
		}
		return bpBankAccount.getAD_Org_ID();
	}

	private final boolean isCashAsPayment()
	{
		final boolean defaultValue = true;
		return sysConfigBL.getBooleanValue("CASH_AS_PAYMENT", defaultValue);
	}

	private String getTenderType()
	{
		return m_TenderType;
	}

	private final boolean isPrepayment()
	{
		return m_Prepayment;
	}

	/**
	 * Gets the Bank Account to be used.
	 * 
	 * @param as accounting schema
	 * @return bank in transit account ({@link Doc#ACCTTYPE_BankInTransit})
	 */
	private MAccount getBankAccount(final I_C_AcctSchema as)
	{
		return getAccount(Doc.ACCTTYPE_BankInTransit, as);
	}
}   // Doc_Payment

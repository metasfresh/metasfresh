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
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAccount;
import org.compiere.model.MCharge;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.organization.OrgId;
import de.metas.payment.TenderType;
import de.metas.util.Services;

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

	public Doc_Payment(final AcctDocContext ctx)
	{
		super(ctx);
	}

	/** Tender Type */
	private TenderType _tenderType;
	/** Prepayment */
	private boolean m_Prepayment = false;

	@Override
	protected void loadDocumentDetails()
	{
		final I_C_Payment pay = getModel(I_C_Payment.class);
		setDateDoc(pay.getDateTrx());
		setBPBankAccountId(BankAccountId.ofRepoIdOrNull(pay.getC_BP_BankAccount_ID()));
		_tenderType = TenderType.ofCode(pay.getTenderType());
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
	public ArrayList<Fact> createFacts(final AcctSchema as)
	{
		// create Fact Header
		final Fact fact = new Fact(this, as, PostingType.Actual);
		final OrgId AD_Org_ID = getBankOrgId();		// Bank Account Org

		// Cash Transfer
		if (getTenderType().isCash() && !isCashAsPayment())
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
					getCurrencyId(), getAmount(), null);
			if (fl != null && AD_Org_ID.isRegular())
			{
				fl.setAD_Org_ID(AD_Org_ID);
			}
			//
			MAccount acct = null;
			if (getC_Charge_ID() != 0)
			{
				acct = MCharge.getAccount(getC_Charge_ID(), as.getId(), getAmount());
			}
			else if (isPrepayment())
			{
				acct = getAccount(AccountType.C_Prepayment, as);
			}
			else
			{
				acct = getAccount(AccountType.UnallocatedCash, as);
			}
			fl = fact.createLine(null, acct,
					getCurrencyId(), null, getAmount());
			if (fl != null && AD_Org_ID.isRegular()
					&& getC_Charge_ID() == 0)
			{
				fl.setAD_Org_ID(AD_Org_ID);
			}
		}
		// APP
		else if (DOCTYPE_APPayment.equals(documentType))
		{
			MAccount acct = null;
			if (getC_Charge_ID() != 0)
			{
				acct = MCharge.getAccount(getC_Charge_ID(), as.getId(), getAmount());
			}
			else if (isPrepayment())
			{
				acct = getAccount(AccountType.V_Prepayment, as);
			}
			else
			{
				acct = getAccount(AccountType.PaymentSelect, as);
			}
			FactLine fl = fact.createLine(null, acct,
					getCurrencyId(), getAmount(), null);
			if (fl != null && AD_Org_ID.isRegular()
					&& getC_Charge_ID() == 0)
			{
				fl.setAD_Org_ID(AD_Org_ID);
			}

			// Asset (CR)
			fl = fact.createLine(null, getBankAccount(as),
					getCurrencyId(), null, getAmount());
			if (fl != null && AD_Org_ID.isRegular())
			{
				fl.setAD_Org_ID(AD_Org_ID);
			}
		}
		else
		{
			throw newPostingException()
					.setAcctSchema(as)
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
	 * @return bank account's Org or {@link OrgId#ANY}
	 */
	private final OrgId getBankOrgId()
	{
		final BankAccount bankAccount = getBankAccount();
		return bankAccount != null ? bankAccount.getOrgId() : OrgId.ANY;
	}

	private final boolean isCashAsPayment()
	{
		final boolean defaultValue = true;
		return sysConfigBL.getBooleanValue("CASH_AS_PAYMENT", defaultValue);
	}

	private TenderType getTenderType()
	{
		return _tenderType;
	}

	private final boolean isPrepayment()
	{
		return m_Prepayment;
	}

	/**
	 * Gets the Bank Account to be used.
	 * 
	 * @param as accounting schema
	 * @return bank in transit account ({@link AccountType#BankInTransit})
	 */
	private MAccount getBankAccount(final AcctSchema as)
	{
		return getAccount(AccountType.BankInTransit, as);
	}
}   // Doc_Payment

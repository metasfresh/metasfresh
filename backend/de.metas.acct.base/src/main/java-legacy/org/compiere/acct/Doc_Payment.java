package org.compiere.acct;

import com.google.common.collect.ImmutableList;
<<<<<<< HEAD
=======
import de.metas.acct.Account;
import de.metas.acct.accounts.BPartnerCustomerAccountType;
import de.metas.acct.accounts.BPartnerVendorAccountType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
<<<<<<< HEAD
import de.metas.currency.CurrencyConversionContext;
import de.metas.organization.OrgId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAccount;
import org.compiere.model.MCharge;
=======
import de.metas.banking.accounting.BankAccountAcctType;
import de.metas.costing.ChargeId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.document.DocBaseType;
import de.metas.organization.OrgId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Payment;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Post Payment Documents.
 *
 * <pre>
 *  Table:              C_Payment (335)
 *  Document Types      ARP, APP
 * </pre>
 *
 * @author Jorg Janke
<<<<<<< HEAD
 * @version $Id: Doc_Payment.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 */
public class Doc_Payment extends Doc<DocLine<Doc_Payment>>
{
	// services
<<<<<<< HEAD
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	private TenderType _tenderType;
=======
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private boolean m_Prepayment = false;
	@Nullable private CurrencyConversionContext _currencyConversionContext; // lazy

	public Doc_Payment(final AcctDocContext ctx)
	{
		super(ctx);
	}

	@Override
	protected void loadDocumentDetails()
	{
		final I_C_Payment payment = getModel(I_C_Payment.class);
		setDateDoc(payment.getDateTrx());
		setBPBankAccountId(BankAccountId.ofRepoIdOrNull(payment.getC_BP_BankAccount_ID()));
<<<<<<< HEAD
		_tenderType = TenderType.ofCode(payment.getTenderType());
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		m_Prepayment = payment.isPrepayment();

		// Amount
		setAmount(Doc.AMTTYPE_Gross, payment.getPayAmt());
	}

	@Override
<<<<<<< HEAD
	public CurrencyConversionContext getCurrencyConversionContext()
	{
		if(_currencyConversionContext == null)
=======
	public CurrencyConversionContext getCurrencyConversionContext(final AcctSchema acctSchema)
	{
		if (_currencyConversionContext == null)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			final I_C_Payment payment = getModel(I_C_Payment.class);
			_currencyConversionContext = paymentBL.extractCurrencyConversionContext(payment);
		}
		return _currencyConversionContext;
	}

	/**
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
<<<<<<< HEAD
	 *  ARP
	 *      BankInTransit   DR
	 *      UnallocatedCash         CR
	 *      or Charge/C_Prepayment
	 *  APP
=======
	 *  ARR - Account Receivables Receipt
	 *      BankInTransit   DR
	 *      UnallocatedCash         CR
	 *      or Charge/C_Prepayment
	 *  APP - Account Payable Payment
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
	public List<Fact> createFacts(final AcctSchema as)
	{
		// create Fact Header
		final Fact fact = new Fact(this, as, PostingType.Actual);
<<<<<<< HEAD
		final OrgId AD_Org_ID = getBankOrgId();        // Bank Account Org

		// Cash Transfer
		if (getTenderType().isCash() && !isCashAsPayment())
		{
			return ImmutableList.of(fact);
		}

		final String documentType = getDocumentType();
		if (DOCTYPE_ARReceipt.equals(documentType))
		{
			// Asset (DR)
			final FactLine fl_DR = fact.createLine()
					.setAccount(getBankAccount(as))
					.setAmtSource(getCurrencyId(), getAmount(), null)
					.setCurrencyConversionCtx(getCurrencyConversionContext())
					.buildAndAdd();
			if (fl_DR != null && AD_Org_ID.isRegular())
			{
				fl_DR.setAD_Org_ID(AD_Org_ID);
			}

			// Prepayment/UnallocatedCash (CR)
			final MAccount acct;
			if (getC_Charge_ID() > 0)
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
=======
		final OrgId bankOrgId = getBankOrgId();        // Bank Account Org

		final DocBaseType docBaseType = getDocBaseType();
		if (DocBaseType.ARReceipt.equals(docBaseType))
		{
			// Asset (DR)
			final FactLine fl_DR = fact.createLine()
					.setAccount(getBankInTransitAcct(as))
					.setAmtSource(getCurrencyId(), getAmount(), null)
					.setCurrencyConversionCtx(getCurrencyConversionContext(as))
					.buildAndAdd();
			if (fl_DR != null && bankOrgId.isRegular())
			{
				fl_DR.setAD_Org_ID(bankOrgId);
			}

			// Prepayment/UnallocatedCash (CR)
			final Account acct;
			final ChargeId chargeId = getC_Charge_ID().orElse(null);
			if (chargeId != null)
			{
				acct = getAccountProvider().getChargeAccount(chargeId, as.getId(), getAmount());
			}
			else if (isPrepayment())
			{
				acct = getCustomerAccount(BPartnerCustomerAccountType.C_Prepayment, as);
			}
			else
			{
				acct = getBankAccountAccount(BankAccountAcctType.B_UnallocatedCash_Acct, as);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			}
			final FactLine fl_CR = fact.createLine()
					.setAccount(acct)
					.setAmtSource(getCurrencyId(), null, getAmount())
<<<<<<< HEAD
					.setCurrencyConversionCtx(getCurrencyConversionContext())
					.buildAndAdd();
			if (fl_CR != null && AD_Org_ID.isRegular() && getC_Charge_ID() <= 0)
			{
				fl_CR.setAD_Org_ID(AD_Org_ID);
			}
		}
		// APP
		else if (DOCTYPE_APPayment.equals(documentType))
		{
			// Prepayment/PaymentSelect (DR)
			final MAccount acct;
			if (getC_Charge_ID() > 0)
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
=======
					.setCurrencyConversionCtx(getCurrencyConversionContext(as))
					.buildAndAdd();
			if (fl_CR != null && bankOrgId.isRegular() && chargeId == null)
			{
				fl_CR.setAD_Org_ID(bankOrgId);
			}
		}
		// APP
		else if (DocBaseType.PurchasePayment.equals(docBaseType))
		{
			// Prepayment/PaymentSelect (DR)
			final Account acct;
			final ChargeId chargeId = getC_Charge_ID().orElse(null);
			if (chargeId != null)
			{
				acct = getAccountProvider().getChargeAccount(chargeId, as.getId(), getAmount());
			}
			else if (isPrepayment())
			{
				acct = getVendorAccount(BPartnerVendorAccountType.V_Prepayment, as);
			}
			else
			{
				acct = getBankAccountAccount(BankAccountAcctType.B_PaymentSelect_Acct, as);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			}
			final FactLine fl_DR = fact.createLine()
					.setAccount(acct)
					.setAmtSource(getCurrencyId(), getAmount(), null)
<<<<<<< HEAD
					.setCurrencyConversionCtx(getCurrencyConversionContext())
					.buildAndAdd();
			if (fl_DR != null && AD_Org_ID.isRegular() && getC_Charge_ID() <= 0)
			{
				fl_DR.setAD_Org_ID(AD_Org_ID);
=======
					.setCurrencyConversionCtx(getCurrencyConversionContext(as))
					.buildAndAdd();
			if (fl_DR != null && bankOrgId.isRegular() && chargeId == null)
			{
				fl_DR.setAD_Org_ID(bankOrgId);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			}

			// Asset (CR)
			final FactLine fl_CR = fact.createLine()
<<<<<<< HEAD
					.setAccount(getBankAccount(as))
					.setAmtSource(getCurrencyId(), null, getAmount())
					.setCurrencyConversionCtx(getCurrencyConversionContext())
					.buildAndAdd();
			if (fl_CR != null && AD_Org_ID.isRegular())
			{
				fl_CR.setAD_Org_ID(AD_Org_ID);
=======
					.setAccount(getBankInTransitAcct(as))
					.setAmtSource(getCurrencyId(), null, getAmount())
					.setCurrencyConversionCtx(getCurrencyConversionContext(as))
					.buildAndAdd();
			if (fl_CR != null && bankOrgId.isRegular())
			{
				fl_CR.setAD_Org_ID(bankOrgId);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			}
		}
		else
		{
			throw newPostingException()
					.setAcctSchema(as)
					.setFact(fact)
					.setPostingStatus(PostingStatus.Error)
<<<<<<< HEAD
					.setDetailMessage("DocumentType unknown: " + documentType);
=======
					.setDetailMessage("DocBaseType unknown: " + docBaseType);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		}

		return ImmutableList.of(fact);
	}

	/**
	 * @return bank account's Org or {@link OrgId#ANY}
	 */
	private OrgId getBankOrgId()
	{
		final BankAccount bankAccount = getBankAccount();
		return bankAccount != null ? bankAccount.getOrgId() : OrgId.ANY;
	}

<<<<<<< HEAD
	private boolean isCashAsPayment()
	{
		final boolean defaultValue = true;
		return sysConfigBL.getBooleanValue("CASH_AS_PAYMENT", defaultValue);
	}

	private TenderType getTenderType()
	{
		return _tenderType;
	}

	private boolean isPrepayment()
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
=======
	private boolean isPrepayment() {return m_Prepayment;}

	@NonNull
	private Account getBankInTransitAcct(final AcctSchema as) {return getBankAccountAccount(BankAccountAcctType.B_InTransit_Acct, as);}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}   // Doc_Payment

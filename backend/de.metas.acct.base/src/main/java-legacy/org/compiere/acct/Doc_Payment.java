package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.Account;
import de.metas.acct.accounts.BPartnerCustomerAccountType;
import de.metas.acct.accounts.BPartnerVendorAccountType;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.accounting.BankAccountAcctType;
import de.metas.costing.ChargeId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.document.DocBaseType;
import de.metas.organization.OrgId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Payment;

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
 */
public class Doc_Payment extends Doc<DocLine<Doc_Payment>>
{
	// services
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

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
		m_Prepayment = payment.isPrepayment();

		// Amount
		setAmount(Doc.AMTTYPE_Gross, payment.getPayAmt());
	}

	@Override
	public CurrencyConversionContext getCurrencyConversionContext(final AcctSchema acctSchema)
	{
		if (_currencyConversionContext == null)
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
	public List<Fact> createFacts(final AcctSchema as)
	{
		// create Fact Header
		final Fact fact = new Fact(this, as, PostingType.Actual);
		final OrgId AD_Org_ID = getBankOrgId();        // Bank Account Org

		final DocBaseType docBaseType = getDocBaseType();
		if (DocBaseType.ARReceipt.equals(docBaseType))
		{
			// Asset (DR)
			final FactLine fl_DR = fact.createLine()
					.setAccount(getBankAccount(as))
					.setAmtSource(getCurrencyId(), getAmount(), null)
					.setCurrencyConversionCtx(getCurrencyConversionContext(as))
					.buildAndAdd();
			if (fl_DR != null && AD_Org_ID.isRegular())
			{
				fl_DR.setAD_Org_ID(AD_Org_ID);
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
			}
			final FactLine fl_CR = fact.createLine()
					.setAccount(acct)
					.setAmtSource(getCurrencyId(), null, getAmount())
					.setCurrencyConversionCtx(getCurrencyConversionContext(as))
					.buildAndAdd();
			if (fl_CR != null && AD_Org_ID.isRegular() && chargeId == null)
			{
				fl_CR.setAD_Org_ID(AD_Org_ID);
			}
		}
		// APP
		else if (DocBaseType.APPayment.equals(docBaseType))
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
			}
			final FactLine fl_DR = fact.createLine()
					.setAccount(acct)
					.setAmtSource(getCurrencyId(), getAmount(), null)
					.setCurrencyConversionCtx(getCurrencyConversionContext(as))
					.buildAndAdd();
			if (fl_DR != null && AD_Org_ID.isRegular() && chargeId == null)
			{
				fl_DR.setAD_Org_ID(AD_Org_ID);
			}

			// Asset (CR)
			final FactLine fl_CR = fact.createLine()
					.setAccount(getBankAccount(as))
					.setAmtSource(getCurrencyId(), null, getAmount())
					.setCurrencyConversionCtx(getCurrencyConversionContext(as))
					.buildAndAdd();
			if (fl_CR != null && AD_Org_ID.isRegular())
			{
				fl_CR.setAD_Org_ID(AD_Org_ID);
			}
		}
		else
		{
			throw newPostingException()
					.setAcctSchema(as)
					.setFact(fact)
					.setPostingStatus(PostingStatus.Error)
					.setDetailMessage("DocBaseType unknown: " + docBaseType);

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

	private boolean isPrepayment()
	{
		return m_Prepayment;
	}

	/**
	 * Gets the Bank Account to be used.
	 *
	 * @param as accounting schema
	 * @return bank in transit account ({@link BankAccountAcctType#B_InTransit_Acct})
	 */
	@NonNull
	private Account getBankAccount(final AcctSchema as)
	{
		return getBankAccountAccount(BankAccountAcctType.B_InTransit_Acct, as);
	}
}   // Doc_Payment

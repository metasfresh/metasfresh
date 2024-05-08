package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.organization.OrgId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAccount;
import org.compiere.model.MCharge;

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
 * @version $Id: Doc_Payment.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Payment extends Doc<DocLine<Doc_Payment>>
{
	// services
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	private TenderType _tenderType;
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
		_tenderType = TenderType.ofCode(payment.getTenderType());
		m_Prepayment = payment.isPrepayment();

		// Amount
		setAmount(Doc.AMTTYPE_Gross, payment.getPayAmt());
	}

	@Override
	public CurrencyConversionContext getCurrencyConversionContext()
	{
		if(_currencyConversionContext == null)
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
			}
			final FactLine fl_CR = fact.createLine()
					.setAccount(acct)
					.setAmtSource(getCurrencyId(), null, getAmount())
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
			}
			final FactLine fl_DR = fact.createLine()
					.setAccount(acct)
					.setAmtSource(getCurrencyId(), getAmount(), null)
					.setCurrencyConversionCtx(getCurrencyConversionContext())
					.buildAndAdd();
			if (fl_DR != null && AD_Org_ID.isRegular() && getC_Charge_ID() <= 0)
			{
				fl_DR.setAD_Org_ID(AD_Org_ID);
			}

			// Asset (CR)
			final FactLine fl_CR = fact.createLine()
					.setAccount(getBankAccount(as))
					.setAmtSource(getCurrencyId(), null, getAmount())
					.setCurrencyConversionCtx(getCurrencyConversionContext())
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
					.setDetailMessage("DocumentType unknown: " + documentType);

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
}   // Doc_Payment

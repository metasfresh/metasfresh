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

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.ICurrencyBL;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Cash;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAccount;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Allocation Line
 *
 * @author Jorg Janke
 * @version $Id: DocLine_Allocation.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
class DocLine_Allocation extends DocLine<Doc_AllocationHdr>
{
	public DocLine_Allocation(final I_C_AllocationLine line, final Doc_AllocationHdr doc)
	{
		super(InterfaceWrapperHelper.getPO(line), doc);

		//
		// Invoice
		m_C_Invoice_ID = line.getC_Invoice_ID();
		invoice = m_C_Invoice_ID > 0 ? line.getC_Invoice() : null;
		if (invoice != null)
		{
			final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
			creditMemoInvoice = invoiceBL.isCreditMemo(invoice);
			soTrxInvoice = invoice.isSOTrx();
		}
		else
		{
			creditMemoInvoice = false;
			soTrxInvoice = null;
		}

		this.m_Counter_AllocationLine_ID = line.getCounter_AllocationLine_ID();

		//
		// Payment: via Cashbook (legacy)
		m_C_CashLine_ID = line.getC_CashLine_ID();
		cashLine = m_C_CashLine_ID > 0 ? line.getC_CashLine() : null;

		//
		// Payment
		this._paymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
		this._payment = this._paymentId != null
				? Services.get(IPaymentBL.class).getById(this._paymentId)
				: null;
		if (this._payment != null)
		{
			final int C_ConversionType_ID = this._payment.getC_ConversionType_ID();
			setC_ConversionType_ID(C_ConversionType_ID);
			paymentReceipt = this._payment.isReceipt();
		}
		else
		{
			paymentReceipt = null;
		}

		//
		// Amounts
		m_AllocatedAmt = line.getAmount();
		setAmount(m_AllocatedAmt);
		m_DiscountAmt = Money.of(line.getDiscountAmt(), doc.getCurrencyId());
		m_WriteOffAmt = Money.of(line.getWriteOffAmt(), doc.getCurrencyId());
		m_OverUnderAmt = line.getOverUnderAmt();
		m_PaymentWriteOffAmt = line.getPaymentWriteOffAmt();
	}    // DocLine_Allocation

	private final int m_C_Invoice_ID;
	private final I_C_Invoice invoice;
	private CurrencyConversionContext invoiceCurrencyConversionCtx;
	private final boolean creditMemoInvoice;
	private final Boolean soTrxInvoice;

	private final int m_Counter_AllocationLine_ID;
	private DocLine_Allocation counterDocLine;
	private final Set<AcctSchemaId> salesPurchaseInvoiceAlreadyCompensated_AcctSchemaIds = new HashSet<>();

	private final PaymentId _paymentId;
	private final I_C_Payment _payment;
	private CurrencyConversionContext paymentCurrencyConversionCtx;
	private final Boolean paymentReceipt;

	private final int m_C_CashLine_ID;
	private final I_C_CashLine cashLine;

	private final BigDecimal m_AllocatedAmt;
	private final Money m_DiscountAmt;
	private final Money m_WriteOffAmt;
	private final BigDecimal m_OverUnderAmt;
	private final BigDecimal m_PaymentWriteOffAmt;

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("DocLine_Allocation[");
		sb.append(get_ID())
				.append(",Amt=").append(getAllocatedAmt())
				.append(",Discount=").append(getDiscountAmt().toBigDecimal())
				.append(",WriteOff=").append(getWriteOffAmt())
				.append(",OverUnderAmt=").append(getOverUnderAmt())
				.append(" - C_Payment_ID=").append(_payment)
				.append(",C_CashLine_ID=").append(cashLine)
				.append(",C_Invoice_ID=").append(invoice)
				.append("]");
		return sb.toString();
	}    // toString

	/**
	 * @return allocated amount
	 */
	public BigDecimal getAllocatedAmt()
	{
		return m_AllocatedAmt;
	}

	public BigDecimal getAllocatedAmt_CMAdjusted()
	{
		BigDecimal allocatedAmt = getAllocatedAmt();
		if (isCreditMemoInvoice())
		{
			allocatedAmt = allocatedAmt.negate();
		}
		return allocatedAmt;
	}

	/**
	 * @return Returns the discountAmt.
	 */
	public Money getDiscountAmt()
	{
		return m_DiscountAmt;
	}

	public Money getDiscountAmt_CMAdjusted()
	{
		Money discountAmt = getDiscountAmt();
		if (isCreditMemoInvoice())
		{
			discountAmt = discountAmt.negate();
		}
		return discountAmt;
	}

	/**
	 * @return Returns the overUnderAmt.
	 */
	public BigDecimal getOverUnderAmt()
	{
		return m_OverUnderAmt;
	}

	/**
	 * @return Returns the writeOffAmt.
	 */
	public Money getWriteOffAmt()
	{
		return m_WriteOffAmt;
	}

	public Money getWriteOffAmt_CMAdjusted()
	{
		Money writeOffAmt = getWriteOffAmt();
		if (isCreditMemoInvoice())
		{
			writeOffAmt = writeOffAmt.negate();
		}
		return writeOffAmt;
	}

	/**
	 * @return Returns the PaymentwriteOffAmt.
	 */
	public BigDecimal getPaymentWriteOffAmt()
	{
		return m_PaymentWriteOffAmt;
	}

	/**
	 * @return Returns the c_CashLine_ID.
	 */
	public int getC_CashLine_ID()
	{
		return m_C_CashLine_ID;
	}

	public I_C_CashLine getC_CashLine()
	{
		return cashLine;
	}

	/**
	 * @return Returns the c_Invoice_ID.
	 */
	public int getC_Invoice_ID()
	{
		return m_C_Invoice_ID;
	}

	public I_C_Invoice getC_Invoice()
	{
		return invoice;
	}

	public int getCounter_AllocationLine_ID()
	{
		return m_Counter_AllocationLine_ID;
	}

	public DocLine_Allocation getCounterDocLine()
	{
		return counterDocLine;
	}

	void setCounterDocLine(final DocLine_Allocation counterDocLine)
	{
		Check.assumeNotNull(counterDocLine, "counterDocLine not null");
		final int counterDocLineId = getCounter_AllocationLine_ID();
		Check.assume(counterDocLine.get_ID() == counterDocLineId, "Counter docline shall have ID={}: {}", counterDocLineId, counterDocLine);
		this.counterDocLine = counterDocLine;
	}

	/**
	 * @return true if this is a sales/purchase compensation line which was not already compensated
	 */
	public boolean isSalesPurchaseInvoiceToCompensate(final AcctSchemaId acctSchemaId)
	{
		// Check if it was already compensated
		if (salesPurchaseInvoiceAlreadyCompensated_AcctSchemaIds.contains(acctSchemaId))
		{
			return false;
		}

		// Shall have an invoice set
		if (!hasInvoiceDocument())
		{
			return false;
		}
		// The invoice shall not be a credit memo
		if (isCreditMemoInvoice())
		{
			return false;
		}

		// Shall have a counter line set
		final DocLine_Allocation counterLine = getCounterDocLine();
		if (counterLine == null)
		{
			return false;
		}

		// The counter line shall have a invoice set
		if (!counterLine.hasInvoiceDocument())
		{
			return false;
		}

		// The counter line's invoice shall not be a credit memo
		if (counterLine.isCreditMemoInvoice())
		{
			return false;
		}

		// The invoice of this line and the invoice of counter line shall be of opposite transactions (sales-purchase)
		if (isSOTrxInvoice() == counterLine.isSOTrxInvoice())
		{
			return false;
		}

		return true;
	}

	public void markAsSalesPurchaseInvoiceCompensated(final AcctSchema as)
	{
		final boolean added = salesPurchaseInvoiceAlreadyCompensated_AcctSchemaIds.add(as.getId());
		Check.assume(added, "Line should not be already compensated: {}", this);
	}

	public boolean hasInvoiceDocument()
	{
		return getC_Invoice() != null;
	}

	@Nullable
	public PaymentId getPaymentId()
	{
		return _paymentId;
	}

	@Nullable
	public I_C_Payment getC_Payment()
	{
		return _payment;
	}

	public final boolean isSOTrxInvoice()
	{
		Check.assumeNotNull(soTrxInvoice, "soTrxInvoice not null");
		return soTrxInvoice;
	}

	/**
	 * @deprecated Please use {@link #isSOTrxInvoice()}
	 */
	@Deprecated
	@Override
	public final boolean isSOTrx()
	{
		return isSOTrxInvoice();
	}

	public final boolean isCreditMemoInvoice()
	{
		return creditMemoInvoice;
	}

	@NonNull
	public CurrencyId getInvoiceCurrencyId()
	{
		final I_C_Invoice invoice = Check.assumeNotNull(getC_Invoice(), "invoice not null");
		return CurrencyId.ofRepoId(invoice.getC_Currency_ID());
	}

	public OrgId getInvoiceOrgId()
	{
		final I_C_Invoice invoice = getC_Invoice();
		if (invoice != null)
		{
			return OrgId.ofRepoId(invoice.getAD_Org_ID());
		}

		return getOrgId();
	}

	public BPartnerId getInvoiceBPartnerId()
	{
		final I_C_Invoice invoice = getC_Invoice();
		if (invoice != null)
		{
			return BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		}

		return getBPartnerId();
	}

	public MAccount getPaymentAcct(final AcctSchema as)
	{
		final PaymentId paymentId = getPaymentId();
		if (paymentId != null)
		{
			final MAccount paymentAcct = getPaymentAcct(as, paymentId);
			Check.assumeNotNull(paymentAcct, "paymentAcct not null");
			return paymentAcct;
		}

		final I_C_CashLine cashLine = getC_CashLine();
		if (cashLine != null)
		{
			final MAccount cashbookAcct = getCashAcct(as, cashLine.getC_CashLine_ID());
			Check.assumeNotNull(cashbookAcct, "cashbookAcct not null");
			return cashbookAcct;
		}

		throw getDoc().newPostingException()
				.setDocLine(this)
				.setAcctSchema(as)
				.setDetailMessage("No payment account found because there is not payment or cash line");
	}

	/**
	 * Get Payment (Unallocated Payment or Payment Selection) Acct of Bank Account
	 */
	private MAccount getPaymentAcct(@NonNull final AcctSchema as, @NonNull final PaymentId paymentId)
	{
		final Doc_AllocationHdr doc = getDoc();
		doc.setBPBankAccountId(null);
		// AccountType.UnallocatedCash (AR) or C_Prepayment
		// or AccountType.PaymentSelect (AP) or V_Prepayment
		AccountType accountType = AccountType.UnallocatedCash;
		//
		final String sql = "SELECT p.C_BP_BankAccount_ID, d.DocBaseType, p.IsReceipt, p.IsPrepayment "
				+ "FROM C_Payment p INNER JOIN C_DocType d ON (p.C_DocType_ID=d.C_DocType_ID) "
				+ "WHERE C_Payment_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setInt(1, paymentId.getRepoId());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				doc.setBPBankAccountId(BankAccountId.ofRepoIdOrNull(rs.getInt(1)));

				final String docBaseType = rs.getString(2);
				if (Doc.DOCTYPE_APPayment.equals(docBaseType))
				{
					accountType = AccountType.PaymentSelect;
				}

				// Prepayment
				final boolean isPrepayment = StringUtils.toBoolean(rs.getString(4));
				if (isPrepayment)        // Prepayment
				{
					final boolean isReceipt = StringUtils.toBoolean(rs.getString(3));
					if (isReceipt)
					{
						accountType = AccountType.C_Prepayment;
					}
					else
					{
						accountType = AccountType.V_Prepayment;
					}
				}
			}
		}
		catch (final Exception e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//
		if (doc.getBPBankAccountId() == null)
		{
			// log.error("NONE for C_Payment_ID=" + C_Payment_ID);
			throw doc.newPostingException()
					.setDocLine(this)
					.setAcctSchema(as)
					.setDetailMessage("No payment account found for " + paymentId);
		}
		return doc.getAccount(accountType, as);
	}    // getPaymentAcct

	/**
	 * Get Cash (Transfer) Acct of CashBook
	 */
	private MAccount getCashAcct(final AcctSchema as, final int C_CashLine_ID)
	{
		final Doc_AllocationHdr doc = getDoc();
		final String sql = "SELECT c.C_CashBook_ID "
				+ "FROM C_Cash c, C_CashLine cl "
				+ "WHERE c.C_Cash_ID=cl.C_Cash_ID AND cl.C_CashLine_ID=?";
		doc.setC_CashBook_ID(DB.getSQLValue(ITrx.TRXNAME_ThreadInherited, sql, C_CashLine_ID));
		if (doc.getC_CashBook_ID() <= 0)
		{
			throw doc.newPostingException()
					.setDocLine(this)
					.setAcctSchema(as)
					.setDetailMessage("No cashbook account found for C_CashLine_ID=" + C_CashLine_ID);
		}
		return doc.getAccount(AccountType.CashTransfer, as);
	}    // getCashAcct

	public Optional<MAccount> getPaymentWriteOffAccount(final AcctSchemaId acctSchemaId)
	{
		final I_C_Payment payment = getC_Payment();
		if (payment == null)
		{
			return Optional.empty();
		}

		final BankAccountId bankAccountId = BankAccountId.ofRepoIdOrNull(payment.getC_BP_BankAccount_ID());
		if (bankAccountId == null)
		{
			return Optional.empty();
		}

		return services.getBankAccountAcct(bankAccountId, acctSchemaId)
				.getPaymentWriteOffAcct()
				.map(services::getAccountById);
	}

	public final CurrencyConversionContext getInvoiceCurrencyConversionCtx()
	{
		if (invoiceCurrencyConversionCtx == null)
		{
			final I_C_Invoice invoice = getC_Invoice();
			Check.assumeNotNull(invoice, "invoice not null");
			final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
			invoiceCurrencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
					TimeUtil.asLocalDate(invoice.getDateAcct()),
					CurrencyConversionTypeId.ofRepoIdOrNull(invoice.getC_ConversionType_ID()),
					ClientId.ofRepoId(invoice.getAD_Client_ID()),
					OrgId.ofRepoId(invoice.getAD_Org_ID()));
		}
		return invoiceCurrencyConversionCtx;
	}

	public final CurrencyConversionContext getPaymentCurrencyConversionCtx()
	{
		if (paymentCurrencyConversionCtx == null)
		{
			final I_C_Payment payment = getC_Payment();
			final I_C_CashLine cashLine = getC_CashLine();
			if (payment != null)
			{
				final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
				paymentCurrencyConversionCtx = paymentBL.extractCurrencyConversionContext(payment);
			}
			else if (cashLine != null)
			{
				final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
				final I_C_Cash cashJournal = cashLine.getC_Cash();
				paymentCurrencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
						TimeUtil.asLocalDate(cashJournal.getDateAcct()),
						(CurrencyConversionTypeId)null, // C_ConversionType_ID - default
						ClientId.ofRepoId(cashLine.getAD_Client_ID()),
						OrgId.ofRepoId(cashLine.getAD_Org_ID()));
			}
			else
			{
				throw new IllegalStateException("Allocation line does not have a payment or a cash line set: " + this);
			}
		}

		return paymentCurrencyConversionCtx;
	}

	public final boolean hasPaymentDocument()
	{
		return getC_Payment() != null || getC_CashLine() != null;
	}

	public final OrgId getPaymentOrgId()
	{
		final I_C_Payment payment = getC_Payment();
		if (payment != null)
		{
			return OrgId.ofRepoId(payment.getAD_Org_ID());
		}

		final I_C_CashLine cashLine = getC_CashLine();
		if (cashLine != null)
		{
			return OrgId.ofRepoId(cashLine.getAD_Org_ID());
		}

		return getOrgId();
	}

	public final BPartnerId getPaymentBPartnerId()
	{
		final I_C_Payment payment = getC_Payment();
		if (payment != null)
		{
			return BPartnerId.ofRepoId(payment.getC_BPartner_ID());
		}

		return getBPartnerId();
	}

	public boolean isPaymentReceipt()
	{
		Check.assumeNotNull(paymentReceipt, "payment document exists");
		return paymentReceipt;
	}

}    // DocLine_Allocation

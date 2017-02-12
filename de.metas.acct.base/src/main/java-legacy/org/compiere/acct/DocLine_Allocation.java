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
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Cash;
import org.compiere.model.I_C_CashLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAllocationLine;
import org.compiere.util.DB;

import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyConversionContext;

/**
 * Allocation Line
 *
 * @author Jorg Janke
 * @version $Id: DocLine_Allocation.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
class DocLine_Allocation extends DocLine
{

	/**
	 * DocLine_Allocation
	 *
	 * @param line allocation line
	 * @param doc header
	 */
	public DocLine_Allocation(final MAllocationLine line, final Doc doc)
	{
		super(line, doc);

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
		// Order (used for prepayments)
		m_C_Order_ID = line.getC_Order_ID();

		//
		// Payment: via Cashbook (legacy)
		m_C_CashLine_ID = line.getC_CashLine_ID();
		cashLine = m_C_CashLine_ID > 0 ? line.getC_CashLine() : null;

		//
		// Payment
		m_C_Payment_ID = line.getC_Payment_ID();
		payment = m_C_Payment_ID > 0 ? line.getC_Payment() : null;
		if (payment != null)
		{
			final int C_ConversionType_ID = payment.getC_ConversionType_ID();
			setC_ConversionType_ID(C_ConversionType_ID);
			paymentReceipt = payment.isReceipt();
		}
		else
		{
			paymentReceipt = null;
		}

		//
		// Amounts
		m_AllocatedAmt = line.getAmount();
		setAmount(m_AllocatedAmt);
		m_DiscountAmt = line.getDiscountAmt();
		m_WriteOffAmt = line.getWriteOffAmt();
		m_OverUnderAmt = line.getOverUnderAmt();
		m_PaymentWriteOffAmt = line.getPaymentWriteOffAmt();
	}	// DocLine_Allocation

	private final int m_C_Invoice_ID;
	private final I_C_Invoice invoice;
	private ICurrencyConversionContext invoiceCurrencyConversionCtx;
	private final boolean creditMemoInvoice;
	private final Boolean soTrxInvoice;

	private final int m_Counter_AllocationLine_ID;
	private DocLine_Allocation counterDocLine;
	private final Set<Integer> salesPurchaseInvoiceAlreadyCompensated_AcctSchemaIds = new HashSet<>();
	
	private final int m_C_Payment_ID;
	private final I_C_Payment payment;
	private ICurrencyConversionContext paymentCurrencyConversionCtx;
	private final Boolean paymentReceipt;

	private final int m_C_CashLine_ID;
	private final I_C_CashLine cashLine;

	private final int m_C_Order_ID;

	private final BigDecimal m_AllocatedAmt;
	private final BigDecimal m_DiscountAmt;
	private final BigDecimal m_WriteOffAmt;
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
				.append(",Discount=").append(getDiscountAmt())
				.append(",WriteOff=").append(getWriteOffAmt())
				.append(",OverUnderAmt=").append(getOverUnderAmt())
				.append(" - C_Payment_ID=").append(payment)
				.append(",C_CashLine_ID=").append(cashLine)
				.append(",C_Invoice_ID=").append(invoice)
				.append("]");
		return sb.toString();
	}	// toString

	/** @return allocated amount */
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
	public BigDecimal getDiscountAmt()
	{
		return m_DiscountAmt;
	}

	public BigDecimal getDiscountAmt_CMAdjusted()
	{
		BigDecimal discountAmt = getDiscountAmt();
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
	public BigDecimal getWriteOffAmt()
	{
		return m_WriteOffAmt;
	}

	public BigDecimal getWriteOffAmt_CMAdjusted()
	{
		BigDecimal writeOffAmt = getWriteOffAmt();
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
	
	/** @return true if this is a sales/purchase compensation line which was not already compensated */
	public boolean isSalesPurchaseInvoiceToCompensate(final MAcctSchema as)
	{
		// Check if it was already compensated
		if (salesPurchaseInvoiceAlreadyCompensated_AcctSchemaIds.contains(as.getC_AcctSchema_ID()))
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
	
	public void markAsSalesPurchaseInvoiceCompensated(final MAcctSchema as)
	{
		final boolean added = salesPurchaseInvoiceAlreadyCompensated_AcctSchemaIds.add(as.getC_AcctSchema_ID());
		Check.assume(added, "Line should not be already compensated: {}", this);
	}

	public boolean hasInvoiceDocument()
	{
		return getC_Invoice() != null;
	}

	/**
	 * @return Returns the c_Payment_ID.
	 */
	public int getC_Payment_ID()
	{
		return m_C_Payment_ID;
	}

	public I_C_Payment getC_Payment()
	{
		return payment;
	}

	/**
	 * @return Returns the c_Order_ID.
	 */
	public int getC_Order_ID()
	{
		return m_C_Order_ID;
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

	/**
	 * Get Invoice C_Currency_ID
	 *
	 * @return 0 if no invoice -1 if not found
	 */
	public int getInvoiceC_Currency_ID()
	{
		final I_C_Invoice invoice = getC_Invoice();
		return invoice == null ? -1 : invoice.getC_Currency_ID();
	}	// getInvoiceC_Currency_ID

	public int getInvoiceOrg_ID()
	{
		final I_C_Invoice invoice = getC_Invoice();
		if (invoice != null)
		{
			return invoice.getAD_Org_ID();
		}

		return getAD_Org_ID();
	}

	public int getInvoiceBPartner_ID()
	{
		final I_C_Invoice invoice = getC_Invoice();
		if (invoice != null)
		{
			return invoice.getC_BPartner_ID();
		}

		return getC_BPartner_ID();
	}

	public MAccount getPaymentAcct(final MAcctSchema as)
	{
		final I_C_Payment payment = getC_Payment();
		if (payment != null)
		{
			final MAccount paymentAcct = getPaymentAcct(as, payment.getC_Payment_ID());
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
				.setC_AcctSchema(as)
				.setDetailMessage("No payment account found because there is not payment or cash line");
	}

	/**
	 * Get Payment (Unallocated Payment or Payment Selection) Acct of Bank Account
	 *
	 * @param as accounting schema
	 * @param C_Payment_ID payment
	 * @return acct
	 */
	private MAccount getPaymentAcct(final MAcctSchema as, final int C_Payment_ID)
	{
		final Doc doc = getDoc();
		doc.setC_BP_BankAccount_ID(0);
		// Doc.ACCTTYPE_UnallocatedCash (AR) or C_Prepayment
		// or Doc.ACCTTYPE_PaymentSelect (AP) or V_Prepayment
		int accountType = Doc.ACCTTYPE_UnallocatedCash;
		//
		final String sql = "SELECT p.C_BP_BankAccount_ID, d.DocBaseType, p.IsReceipt, p.IsPrepayment "
				+ "FROM C_Payment p INNER JOIN C_DocType d ON (p.C_DocType_ID=d.C_DocType_ID) "
				+ "WHERE C_Payment_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setInt(1, C_Payment_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				doc.setC_BP_BankAccount_ID(rs.getInt(1));
				if (Doc.DOCTYPE_APPayment.equals(rs.getString(2)))
				{
					accountType = Doc.ACCTTYPE_PaymentSelect;
				}
				// Prepayment
				if ("Y".equals(rs.getString(4)))		// Prepayment
				{
					if ("Y".equals(rs.getString(3)))
					{
						accountType = Doc.ACCTTYPE_C_Prepayment;
					}
					else
					{
						accountType = Doc.ACCTTYPE_V_Prepayment;
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
		if (doc.getC_BP_BankAccount_ID() <= 0)
		{
			// log.error("NONE for C_Payment_ID=" + C_Payment_ID);
			throw doc.newPostingException()
					.setDocLine(this)
					.setC_AcctSchema(as)
					.setDetailMessage("No payment account found for " + C_Payment_ID);
		}
		return doc.getAccount(accountType, as);
	}	// getPaymentAcct

	/**
	 * Get Cash (Transfer) Acct of CashBook
	 *
	 * @param as accounting schema
	 * @param C_CashLine_ID
	 * @return acct
	 */
	private final MAccount getCashAcct(final MAcctSchema as, final int C_CashLine_ID)
	{
		final Doc doc = getDoc();
		final String sql = "SELECT c.C_CashBook_ID "
				+ "FROM C_Cash c, C_CashLine cl "
				+ "WHERE c.C_Cash_ID=cl.C_Cash_ID AND cl.C_CashLine_ID=?";
		doc.setC_CashBook_ID(DB.getSQLValue(ITrx.TRXNAME_ThreadInherited, sql, C_CashLine_ID));
		if (doc.getC_CashBook_ID() <= 0)
		{
			log.error("NONE for C_CashLine_ID=" + C_CashLine_ID);
			throw doc.newPostingException()
					.setDocLine(this)
					.setC_AcctSchema(as)
					.setDetailMessage("No cashbook account found for C_CashLine_ID=" + C_CashLine_ID);
		}
		return doc.getAccount(Doc.ACCTTYPE_CashTransfer, as);
	}	// getCashAcct

	public final ICurrencyConversionContext getInvoiceCurrencyConversionCtx()
	{
		if (invoiceCurrencyConversionCtx == null)
		{
			final I_C_Invoice invoice = getC_Invoice();
			Check.assumeNotNull(invoice, "invoice not null");
			final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
			invoiceCurrencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
					invoice.getDateAcct(),
					invoice.getC_ConversionType_ID(),
					invoice.getAD_Client_ID(),
					invoice.getAD_Org_ID());
		}
		return invoiceCurrencyConversionCtx;
	}

	public final ICurrencyConversionContext getPaymentCurrencyConversionCtx()
	{
		if (paymentCurrencyConversionCtx == null)
		{
			final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);

			final I_C_Payment payment = getC_Payment();
			final I_C_CashLine cashLine = getC_CashLine();
			if (payment != null)
			{
				paymentCurrencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
						payment.getDateAcct(),
						payment.getC_ConversionType_ID(),
						payment.getAD_Client_ID(),
						payment.getAD_Org_ID());
			}
			else if (cashLine != null)
			{
				final I_C_Cash cashJournal = cashLine.getC_Cash();
				paymentCurrencyConversionCtx = currencyConversionBL.createCurrencyConversionContext(
						cashJournal.getDateAcct(),
						-1, // C_ConversionType_ID - default
						cashLine.getAD_Client_ID(),
						cashLine.getAD_Org_ID());
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

	public final int getPaymentOrg_ID()
	{
		final I_C_Payment payment = getC_Payment();
		if (payment != null)
		{
			return payment.getAD_Org_ID();
		}

		final I_C_CashLine cashLine = getC_CashLine();
		if (cashLine != null)
		{
			return cashLine.getAD_Org_ID();
		}

		return getAD_Org_ID();
	}

	public final int getPaymentBPartner_ID()
	{
		final I_C_Payment payment = getC_Payment();
		if (payment != null)
		{
			return payment.getC_BPartner_ID();
		}

		return getC_BPartner_ID();
	}
	
	public boolean isPaymentReceipt()
	{
		Check.assumeNotNull(paymentReceipt, "payment document exists");
		return paymentReceipt;
	}

}	// DocLine_Allocation

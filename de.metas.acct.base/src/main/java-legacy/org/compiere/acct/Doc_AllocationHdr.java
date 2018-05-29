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

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.acct.Fact.FactLineBuilder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MTax;
import org.slf4j.Logger;

import de.metas.currency.ICurrencyConversionContext;
import de.metas.logging.LogManager;

/**
 * Post Allocation Documents.
 *
 * <pre>
 *  Table:              C_AllocationHdr
 *  Document Types:     CMA
 * </pre>
 *
 * @author Jorg Janke
 * @version $Id: Doc_Allocation.java,v 1.6 2006/07/30 00:53:33 jjanke Exp $
 *
 *          FR [ 1840016 ] Avoid usage of clearing accounts - subject to C_AcctSchema.IsPostIfClearingEqual Avoid posting if Receipt and both accounts Unallocated Cash and Receivable are equal Avoid
 *          posting if Payment and both accounts Payment Select and Liability are equal
 *
 * @author phib BF [ 2019262 ] Allocation posting currency gain/loss omits line reference
 *
 */
public class Doc_AllocationHdr extends Doc
{
	/**
	 * Constructor
	 *
	 * @param ass accounting schemata
	 * @param rs record
	 * @param trxName trx
	 */
	public Doc_AllocationHdr(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_Allocation);
	}   // Doc_Allocation

	/**
	 * Load Specific Document Details
	 *
	 * @return error message or null
	 */
	@Override
	protected String loadDocumentDetails()
	{
		MAllocationHdr alloc = (MAllocationHdr)getPO();
		setDateDoc(alloc.getDateTrx());
		// Contained Objects
		p_lines = loadLines(alloc);
		return null;
	}   // loadDocumentDetails

	/**
	 * Load Invoice Line
	 *
	 * @param alloc header
	 * @return DocLine Array
	 */
	private DocLine[] loadLines(final MAllocationHdr alloc)
	{
		final List<DocLine_Allocation> docLines = new ArrayList<>();
		final Map<Integer, DocLine_Allocation> id2docLine = new HashMap<>();
		for (final MAllocationLine line : alloc.getLines(false))
		{
			final DocLine_Allocation docLine = new DocLine_Allocation(line, this);
			log.debug("Line: {}", docLine);

			docLines.add(docLine);
			id2docLine.put(docLine.get_ID(), docLine);
		}

		// Cross-link counter allocation lines
		for (final DocLine_Allocation docLine : docLines)
		{
			final int counterAllocationLineId = docLine.getCounter_AllocationLine_ID();
			if (counterAllocationLineId <= 0)
			{
				continue;
			}
			final DocLine_Allocation counterDocLine = id2docLine.get(counterAllocationLineId);
			Check.assumeNotNull(counterDocLine, "counterDocLine shall exist for ID={} in {}", counterAllocationLineId, docLines);
			docLine.setCounterDocLine(counterDocLine);
		}

		// Return Array
		return docLines.toArray(new DocLine[docLines.size()]);
	}	// loadLines

	/**************************************************************************
	 * Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 *
	 * @return positive amount, if total invoice is bigger than lines
	 */
	@Override
	public BigDecimal getBalance()
	{
		BigDecimal retValue = ZERO;
		return retValue;
	}   // getBalance

	/**
	 * Create Facts (the accounting logic) for CMA.
	 *
	 * <pre>
	 *  AR_Invoice_Payment
	 *      UnAllocatedCash DR
	 *      or C_Prepayment
	 *      DiscountExp     DR
	 *      WriteOff        DR
	 *      Receivables             CR
	 *  AR_Invoice_Cash
	 *      CashTransfer    DR
	 *      DiscountExp     DR
	 *      WriteOff        DR
	 *      Receivables             CR
	 *
	 *  AP_Invoice_Payment
	 *      Liability       DR
	 *      DiscountRev             CR
	 *      WriteOff                CR
	 *      PaymentSelect           CR
	 *      or V_Prepayment
	 *  AP_Invoice_Cash
	 *      Liability       DR
	 *      DiscountRev             CR
	 *      WriteOff                CR
	 *      CashTransfer            CR
	 *  CashBankTransfer
	 *      -
	 *  ==============================
	 *  Realized Gain & Loss
	 * 		AR/AP			DR		CR
	 * 		Realized G/L	DR		CR
	 *
	 *
	 * </pre>
	 *
	 * Tax needs to be corrected for discount & write-off; Currency gain & loss is realized here.
	 *
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public ArrayList<Fact> createFacts(final MAcctSchema as)
	{
		final ArrayList<Fact> facts = new ArrayList<>();

		// create Fact Header
		final Fact fact = createEmptyFact(as);
		facts.add(fact);

		int countPayments = 0;
		int countInvoices = 0;
		for (DocLine p_line : p_lines)
		{
			final DocLine_Allocation line = (DocLine_Allocation)p_line;
			if (line.hasInvoiceDocument())
			{
				countInvoices++;
			}
			if (line.hasPaymentDocument())
			{
				countPayments++;
			}
		}

		//
		//
		if (countPayments > 0 && countInvoices == 0)
		{
			createFactLines_PaymentAllocation(fact);
			return facts;
		}

		for (DocLine p_line : p_lines)
		{
			final DocLine_Allocation line = (DocLine_Allocation)p_line;
			setC_BPartner_ID(line.getC_BPartner_ID());

			// CashBankTransfer - all references null and Discount/WriteOff = 0
			if (line.getC_Payment_ID() > 0
					&& line.getC_Invoice_ID() <= 0
					&& line.getC_CashLine_ID() <= 0 && line.getC_BPartner_ID() <= 0
					&& ZERO.compareTo(line.getDiscountAmt()) == 0
					&& ZERO.compareTo(line.getWriteOffAmt()) == 0)
			{
				continue;
			}

			//
			// No Invoice
			if (!line.hasInvoiceDocument())
			{
				final boolean hasPaymentDocument = line.hasPaymentDocument();
				// Payment only
				if (hasPaymentDocument)
				{
					createPaymentWriteOffAmtFacts(fact, line);
				}
				else
				{
					// metas: this situation (neither payment nor invoice) can be legal ok,
					// e.g. if a payed metas-prepay order is closed
					// p_Error = "Cannot determine SO/PO";
					// log.error(p_Error);
					// return null;
					assert line.getC_OrderLine_ID() > 0 : line;
					return facts;
					// metas end
				}
			}
			//
			// Invoice
			else
			{
				final AmountSourceAndAcct.Builder invoiceTotalAllocatedAmtSourceAndAcctCollector = AmountSourceAndAcct.builder();

				//
				// Payment DR/CR
				{
					final AmountSourceAndAcct paymentSourceAndAcctAmt = createPaymentFacts(fact, line);
					invoiceTotalAllocatedAmtSourceAndAcctCollector.add(paymentSourceAndAcctAmt);
				}

				//
				// Discount DR/CR
				{
					final AmountSourceAndAcct discountAmt = createInvoiceDiscountFacts(fact, line);
					invoiceTotalAllocatedAmtSourceAndAcctCollector.add(discountAmt);
				}

				//
				// Write off DR/CR
				{
					final AmountSourceAndAcct writeOffAmt = createInvoiceWriteOffFacts(fact, line);
					invoiceTotalAllocatedAmtSourceAndAcctCollector.add(writeOffAmt);
				}

				//
				// Sales invoice - purchase invoice
				{
					final AmountSourceAndAcct compensationAmt = createPurchaseSalesInvoiceFacts(fact, line);
					invoiceTotalAllocatedAmtSourceAndAcctCollector.add(compensationAmt);
				}

				//
				// AR/AP Invoice Amount CR/DR
				{
					final AmountSourceAndAcct invoiceTotalAllocatedAmtSourceAndAcct = invoiceTotalAllocatedAmtSourceAndAcctCollector.build();
					createInvoiceFacts(fact, line, invoiceTotalAllocatedAmtSourceAndAcct);
				}
			}

			//
			// VAT Tax Correction
			// https://github.com/metasfresh/metasfresh/issues/3988 - if tax correction is needed, it has to be a dedicated fact;
			// otherwise, FactTrxLinesType.extractType will fail
			final Optional<Fact> taxCorrectionFact = createTaxCorrection(fact.getAcctSchema(), line);
			taxCorrectionFact.ifPresent(facts::add);
		}            	// for all lines

		// reset line info
		setC_BPartner_ID(0);
		return facts;
	}   // createFact

	private Fact createEmptyFact(final MAcctSchema as)
	{
		return new Fact(this, as, Fact.POST_Actual);
	}

	/**
	 * Create facts for payments in the case when no invoice was involved.
	 * The pay Amt will go to Credit for outgoing payments and to Debit for Incoming payments
	 *
	 * @param fact
	 */
	private void createFactLines_PaymentAllocation(final Fact fact)
	{
		final MAcctSchema as = fact.getAcctSchema();

		for (DocLine p_line : p_lines)
		{
			final DocLine_Allocation line = (DocLine_Allocation)p_line;

			// FRESH-523: Make sure the partner of the payment is set in the Doc. It will be needed when selecting the correct Account
			setC_BPartner_ID(line.getPaymentBPartner_ID());

			// In case there is a line with a writeoff amount, throw an exception. This is not supported (yet).

			if (line.getPaymentWriteOffAmt().signum() != 0)
			{
				// In case the allocations are for writeoff lines, also create the fact acct lines for writeoff.
				createPaymentWriteOffAmtFacts(fact, line);
			}
			else
			{

				final MAccount paymentAcct = line.getPaymentAcct(as);

				if (!line.hasPaymentDocument())
				{
					continue;
				}

				final FactLine fl_Payment;

				final BigDecimal allocatedAmt = line.getAllocatedAmt();

				// Incoming payment
				if (line.isPaymentReceipt())
				{
					// Originally on Credit. The amount must be moved to Debit
					fl_Payment = fact.createLine(line, paymentAcct, getC_Currency_ID(), allocatedAmt, null);
				}
				// Outgoing payment
				else
				{
					// Originally on Debit. The amount must be moved to Credit, with different sign
					fl_Payment = fact.createLine(line, paymentAcct, getC_Currency_ID(), null, allocatedAmt.negate());
				}

				// Make sure the fact line was created
				Check.assumeNotNull(fl_Payment, "fl_Payment not null");

				fl_Payment.setAD_Org_ID(line.getPaymentOrg_ID());
				fl_Payment.setC_BPartner_ID(line.getPaymentBPartner_ID());
			}
		}
	}

	/**
	 * Creates facts related to {@link DocLine_Allocation#getPaymentWriteOffAmt()}.
	 *
	 * @param fact
	 * @param line
	 * @see "Task #09441 for more informations"
	 */
	private final void createPaymentWriteOffAmtFacts(final Fact fact, final DocLine_Allocation line)
	{
		if (line.getPaymentWriteOffAmt().signum() == 0)
		{
			return;
		}

		final I_C_Payment payment = line.getC_Payment();
		Check.assumeNotNull(payment, "payment not null for {}", line); // shall not happen

		final MAcctSchema as = fact.getAcctSchema();
		final MAccount paymentAcct = line.getPaymentAcct(as);
		final FactLine fl_Payment;
		final FactLine fl_Discount;

		//
		// Case: Customer Overpayment
		//
		// PaymentAcct DR
		// DiscountExpense CR
		if (payment.isReceipt())
		{
			final MAccount discountAcct = getAccount(Doc.ACCTTYPE_DiscountExp, as);
			final BigDecimal paymentWriteOffAmt = line.getPaymentWriteOffAmt();

			fl_Payment = fact.createLine(line, paymentAcct, getC_Currency_ID(), paymentWriteOffAmt, null);
			fl_Discount = fact.createLine(line, discountAcct, getC_Currency_ID(), null, paymentWriteOffAmt);
		}
		//
		// Case: Vendor Overpayment
		//
		// PaymentAcct CR
		// DiscountRevenue DR
		else
		{
			final MAccount discountAcct = getAccount(Doc.ACCTTYPE_DiscountRev, as);
			final BigDecimal paymentWriteOffAmt = line.getPaymentWriteOffAmt().negate();

			fl_Payment = fact.createLine(line, paymentAcct, getC_Currency_ID(), null, paymentWriteOffAmt);
			fl_Discount = fact.createLine(line, discountAcct, getC_Currency_ID(), paymentWriteOffAmt, null);
		}

		//
		// Update fact line dimensions
		Check.assumeNotNull(fl_Payment, "fl_Payment not null");
		fl_Payment.setAD_Org_ID(payment.getAD_Org_ID());
		fl_Payment.setC_BPartner_ID(payment.getC_BPartner_ID());
		//
		Check.assumeNotNull(fl_Discount, "fl_Discount not null");
		fl_Discount.setAD_Org_ID(payment.getAD_Org_ID());
		fl_Discount.setC_BPartner_ID(payment.getC_BPartner_ID());
	}

	/**
	 * Creates the {@link FactLine} to book the payment document.
	 *
	 * @param fact
	 * @param line
	 * @return source and accounted amount
	 */
	private AmountSourceAndAcct createPaymentFacts(final Fact fact, final DocLine_Allocation line)
	{
		final MAcctSchema as = fact.getAcctSchema();

		final BigDecimal paymentAllocatedAmt = line.getAllocatedAmt_CMAdjusted();
		if (paymentAllocatedAmt.signum() == 0)
		{
			// no amount to be allocated on payment
			return AmountSourceAndAcct.ZERO;
		}

		if (!line.hasPaymentDocument())
		{
			// no payment document
			return AmountSourceAndAcct.ZERO;
		}

		final FactLineBuilder factLineBuilder = fact.createLine()
				.setDocLine(line)
				.setAccount(line.getPaymentAcct(as))
				.setC_Currency_ID(getC_Currency_ID())
				.setCurrencyConversionCtx(line.getPaymentCurrencyConversionCtx())
				.setAD_Org_ID(line.getPaymentOrg_ID())
				.setC_BPartner_ID(line.getPaymentBPartner_ID());

		if (line.isSOTrxInvoice())
		{
			factLineBuilder.setAmtSource(paymentAllocatedAmt, null);
		}
		else
		{
			factLineBuilder.setAmtSource(null, paymentAllocatedAmt.negate());
		}

		final FactLine factLine = factLineBuilder.buildAndAdd();
		factLine.invertDrAndCrAmountsIfTrue(line.isCreditMemoInvoice());

		return factLine.getAmtSourceAndAcctDrOrCr();
	}

	/**
	 * Create accounting facts for early payment discounts. Separate for each tax.
	 *
	 * @param fact
	 * @param line
	 * @param discount amount booked
	 */
	private AmountSourceAndAcct createInvoiceDiscountFacts(final Fact fact, final DocLine_Allocation line)
	{
		final BigDecimal discountAmt_Abs = line.getDiscountAmt();
		if (discountAmt_Abs.signum() == 0)
		{
			return AmountSourceAndAcct.ZERO;
		}

		final BigDecimal discountAmt_CMAdjusted = line.getDiscountAmt_CMAdjusted();

		final MAcctSchema as = fact.getAcctSchema();
		final I_C_Payment payment = line.getC_Payment();
		final I_C_Invoice invoice = line.getC_Invoice();
		final boolean isCreditMemoInvoice = line.isCreditMemoInvoice();

		boolean isDiscountExpense = line.isSOTrxInvoice();
		if (isCreditMemoInvoice)
		{
			isDiscountExpense = !isDiscountExpense;
		}

		final AmountSourceAndAcct.Builder discountAmtSourceAndAcct = AmountSourceAndAcct.builder();

		final MInvoice invoicePO = LegacyAdapters.convertToPO(invoice);
		final MInvoiceTax[] taxes = invoicePO.getTaxes(true);
		if (taxes == null || taxes.length == 0)
		{
			// old behavior
			final FactLine fl;
			if (isDiscountExpense)
			{
				fl = fact.createLine(line, getAccount(Doc.ACCTTYPE_DiscountExp, as), getC_Currency_ID(), discountAmt_CMAdjusted, null);
				discountAmtSourceAndAcct.addAmtSource(fl.getAmtSourceDr()).addAmtAcct(fl.getAmtAcctDr());
			}
			else
			{
				fl = fact.createLine(line, getAccount(Doc.ACCTTYPE_DiscountRev, as), getC_Currency_ID(), null, discountAmt_CMAdjusted.negate());
				discountAmtSourceAndAcct.addAmtSource(fl.getAmtSourceCr()).addAmtAcct(fl.getAmtAcctCr());
			}
			if (payment != null)
			{
				fl.setAD_Org_ID(payment.getAD_Org_ID());
				fl.setC_BPartner_ID(payment.getC_BPartner_ID());
			}

		}
		else
		{
			final BigDecimal discountFactor = calculateDiscountFactor(discountAmt_Abs, invoice);

			// split discount for different taxes
			BigDecimal discountSum = ZERO;
			for (int i = 0; i < taxes.length; i++)
			{
				// TaxDiscountAmt = TaxBaseAmt * Skonto * (1+TaxRate)
				// but we are calculating differently to avoid division by zero when calculating TaxRate=TaxAmt/TaxBaseAmt
				final BigDecimal taxAmt = taxes[i].getTaxAmt();
				final BigDecimal taxBaseAmt = taxes[i].getTaxBaseAmt();
				final BigDecimal baseAndTaxAmt = taxBaseAmt.add(taxAmt);
				BigDecimal taxDiscountAmt = baseAndTaxAmt.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
				if (taxAmt.signum() == 0)
				{
					taxDiscountAmt = ZERO;
				}

				discountSum = discountSum.add(taxDiscountAmt);
				if (i == taxes.length - 1)
				{
					// last tax, check amounts
					if (discountSum.compareTo(discountAmt_CMAdjusted) != 0)
					{
						taxDiscountAmt = taxDiscountAmt.add(discountAmt_CMAdjusted.subtract(discountSum));
					}
				}

				//
				// Get tax specific discount account
				final int taxId = taxes[i].getC_Tax_ID();
				MAccount account = getTaxDiscountAccount(taxId, isDiscountExpense, as);
				if (account == null)
				{
					// no taxDiscountAcct found, use standard account...
					account = getAccount(isDiscountExpense ? Doc.ACCTTYPE_DiscountExp : Doc.ACCTTYPE_DiscountRev, as);
				}

				//
				// Create discount fact line
				final BigDecimal taxDiscountAmt_CMAdjusted = isCreditMemoInvoice ? taxDiscountAmt.negate() : taxDiscountAmt;
				FactLine fl = null;
				if (isDiscountExpense)
				{
					fl = fact.createLine(line, account, getC_Currency_ID(), taxDiscountAmt_CMAdjusted, null);
				}
				else
				{
					fl = fact.createLine(line, account, getC_Currency_ID(), null, taxDiscountAmt_CMAdjusted.negate());
				}
				if (fl != null)
				{
					fl.setC_Tax_ID(taxId);
					if (payment != null)
					{
						fl.setAD_Org_ID(payment.getAD_Org_ID());
						fl.setC_BPartner_ID(payment.getC_BPartner_ID());
					}
					discountAmtSourceAndAcct.add(fl.getAmtSourceAndAcctDrOrCr());
				}
			}
		}

		return discountAmtSourceAndAcct.build();
	}

	/**
	 * Calculates the discount factor (percentage of discountAmt from invoice's grand total)
	 *
	 * @param discountAmt discount amount (absolute amount)
	 * @param invoice
	 * @return discount factor, percentage between 0...1, high precision
	 */
	private final BigDecimal calculateDiscountFactor(final BigDecimal discountAmt, final I_C_Invoice invoice)
	{
		if (discountAmt.signum() == 0)
		{
			return ZERO;
		}

		final MInvoice invoicePO = LegacyAdapters.convertToPO(invoice);
		BigDecimal invoiceGrandTotalAbs = invoicePO.getGrandTotal(true); // creditMemoAdjusted=true
		if (!invoice.isSOTrx())
		{
			invoiceGrandTotalAbs = invoiceGrandTotalAbs.negate();
		}

		if (invoiceGrandTotalAbs.signum() == 0)
		{
			final PostingException ex = newPostingException()
					.addDetailMessage("Cannot calculate the discount factor when invoice grand total is ZERO")
					.setParameter("DiscountAmt", discountAmt)
					.setParameter("C_Invoice_ID", invoice);
			log.warn("Cannot calculate the discount factor when invoice grand total is ZERO. Considering ZERO", ex);
			return ZERO;
		}

		final BigDecimal discountFactor = discountAmt.divide(invoiceGrandTotalAbs, 8, RoundingMode.HALF_UP);
		return discountFactor;
	}

	/**
	 * Returns early payment discount account for given tax.
	 *
	 * @param taxId
	 * @return
	 */
	private final MAccount getTaxDiscountAccount(final int taxId, final boolean isDiscountExpense, final MAcctSchema as)
	{
		if (taxId <= 0)
		{
			return null;
		}
		return MTax.getDiscountAccount(taxId, isDiscountExpense, as);
	}

	/**
	 * Creates the {@link FactLine} to book the invoice write off.
	 *
	 * @param fact
	 * @param line
	 * @return WriteOff amount booked
	 */
	private final AmountSourceAndAcct createInvoiceWriteOffFacts(final Fact fact, final DocLine_Allocation line)
	{
		final BigDecimal writeOffAmt = line.getWriteOffAmt_CMAdjusted();
		if (writeOffAmt.signum() == 0)
		{
			return AmountSourceAndAcct.ZERO;
		}

		final MAcctSchema as = fact.getAcctSchema();
		final FactLineBuilder factLineBuilder = fact.createLine()
				.setDocLine(line)
				.setAccount(getAccount(Doc.ACCTTYPE_WriteOff, as))
				.setC_Currency_ID(getC_Currency_ID())
				.setAD_Org_ID(line.getPaymentOrg_ID())
				.setC_BPartner_ID(line.getPaymentBPartner_ID());

		if (line.isSOTrxInvoice())
		{
			factLineBuilder.setAmtSource(writeOffAmt, null);
		}
		else
		{
			factLineBuilder.setAmtSource(null, writeOffAmt.negate());
		}

		final FactLine factLine = factLineBuilder.buildAndAdd();
		factLine.invertDrAndCrAmountsIfTrue(line.isCreditMemoInvoice());

		return factLine.getAmtSourceAndAcctDrOrCr();
	}

	private void createInvoiceFacts(final Fact fact, final DocLine_Allocation line, final AmountSourceAndAcct invoiceTotalAllocatedAmtSourceAndAcct)
	{
		final BigDecimal allocationSource = invoiceTotalAllocatedAmtSourceAndAcct.getAmtSource();
		if (allocationSource.signum() == 0)
		{
			// nothing to book
			return;
		}

		final MAcctSchema as = fact.getAcctSchema();
		if (!as.isAccrual())
		{
			throw newPostingException()
					.setDetailMessage("Cash based accounting is not supported")
					.appendParametersToMessage()
					.setParameter("fact", fact)
					.setParameter("line", line)
					.setParameter("allocationSource", allocationSource);
		}

		//
		// Determine which currency conversion we shall use
		final ICurrencyConversionContext invoiceCurrencyConversionCtx;
		if (line.getInvoiceC_Currency_ID() == as.getC_Currency_ID())
		{
			// use default context because the invoice is in accounting currency, so we shall have no currency gain/loss
			invoiceCurrencyConversionCtx = null;
		}
		else
		{
			invoiceCurrencyConversionCtx = line.getInvoiceCurrencyConversionCtx();
		}

		final FactLineBuilder factLineBuilder = fact.createLine()
				.setDocLine(line)
				.setC_Currency_ID(getC_Currency_ID())
				.setCurrencyConversionCtx(invoiceCurrencyConversionCtx)
				.setAD_Org_ID(line.getInvoiceOrg_ID())
				.setC_BPartner_ID(line.getInvoiceBPartner_ID());

		if (line.isSOTrxInvoice())
		{
			factLineBuilder.setAccount(getAccount(Doc.ACCTTYPE_C_Receivable, as));
			factLineBuilder.setAmtSource(null, allocationSource);
		}
		else
		{
			factLineBuilder.setAccount(getAccount(Doc.ACCTTYPE_V_Liability, as));
			factLineBuilder.setAmtSource(allocationSource, null);
		}

		final FactLine factLine = factLineBuilder.buildAndAdd();
		factLine.invertDrAndCrAmountsIfTrue(line.isCreditMemoInvoice());

		final BigDecimal allocationAcctOnPaymentDate = invoiceTotalAllocatedAmtSourceAndAcct.getAmtAcct();
		createRealizedGainLossFactLine(line, fact, factLine, allocationAcctOnPaymentDate);
	}

	/**
	 * Creates the {@link FactLine} to book the purchase - sales invoice compensation
	 *
	 * @param fact
	 * @param line
	 * @return
	 * @return
	 */
	private AmountSourceAndAcct createPurchaseSalesInvoiceFacts(final Fact fact, final DocLine_Allocation line)
	{
		final MAcctSchema as = fact.getAcctSchema();

		// Do nothing if this is not a compensation line or it was already compensated
		if (!line.isSalesPurchaseInvoiceToCompensate(as))
		{
			return AmountSourceAndAcct.ZERO;
		}

		//
		// Make sure the line is valid compensation line
		Check.assume(!line.hasPaymentDocument(), "A sales-purchase compensation line shall not have a payment document set: {}", line);

		final BigDecimal compensationAmtSource = line.getAllocatedAmt();
		if (compensationAmtSource.signum() == 0)
		{
			return AmountSourceAndAcct.ZERO;
		}

		//
		// Make sure the invoices are NOT in foreign currency because that case is not supported,
		// i.e. we did not implement the currency gain/loss calculation for this case
		final DocLine_Allocation counterLine = line.getCounterDocLine();
		Check.assumeNotNull(counterLine, "counterLine not null");
		if (line.getInvoiceC_Currency_ID() != as.getC_Currency_ID())
		{
			throw newPostingException()
					.setFact(fact)
					.setDocLine(line)
					.setDetailMessage("Booking sales-purchase invoice compansation in foreign currency is not supported");
		}
		if (counterLine.getInvoiceC_Currency_ID() != as.getC_Currency_ID())
		{
			throw newPostingException()
					.setFact(fact)
					.setDocLine(counterLine)
					.setDetailMessage("Booking sales-purchase invoice compansation in foreign currency is not supported");
		}

		//
		// Make sure the counter line is valid compensation line
		Check.assume(!counterLine.hasPaymentDocument(), "A sales-purchase compensation line shall not have a payment document set: {}", counterLine);

		//
		// Make sure it's not cash based accounting => we are not supporting that case.
		if (!as.isAccrual())
		{
			throw newPostingException()
					.setFact(fact)
					.setDocLine(line)
					.setDetailMessage("Booking the counter invoice using cash based accounting method is not supported");
		}

		//
		// Make sure the compensation amount of this line and of it's counter part are matching
		final BigDecimal counterLine_compensationAmtSource = counterLine.getAllocatedAmt();
		if (compensationAmtSource.compareTo(counterLine_compensationAmtSource.negate()) != 0)
		{
			throw newPostingException()
					.setFact(fact)
					.setDocLine(line)
					.setDetailMessage("Counter invoice shall have the same allocated amount: " + counterLine);
		}

		//
		// Create the fact line to book the counter invoice
		final FactLineBuilder factLineBuilder = fact.createLine()
				.setDocLine(counterLine)
				.setC_Currency_ID(getC_Currency_ID())
				.setAD_Org_ID(counterLine.getInvoiceOrg_ID())
				.setC_BPartner_ID(counterLine.getInvoiceBPartner_ID());
		if (counterLine.isSOTrxInvoice())
		{
			factLineBuilder.setAccount(getAccount(Doc.ACCTTYPE_C_Receivable, as));
			factLineBuilder.setAmtSource(null, compensationAmtSource.negate());
		}
		else
		{
			factLineBuilder.setAccount(getAccount(Doc.ACCTTYPE_V_Liability, as));
			factLineBuilder.setAmtSource(compensationAmtSource, null);
		}
		final FactLine factLine = factLineBuilder.buildAndAdd();

		//
		// Mark the lines as compensated
		line.markAsSalesPurchaseInvoiceCompensated(as);
		counterLine.markAsSalesPurchaseInvoiceCompensated(as);

		//
		// Return how much was booked here.
		return factLine.getAmtSourceAndAcctDrOrCr();
	}

	/**
	 * Create the {@link FactLine} which is about booking the currency gain/loss between invoice and payment.
	 *
	 * It is also creating a new FactLine where the currency gain/loss is booked.
	 *
	 * @param line
	 * @param fact
	 * @param invoiceFactLine invoice related booking (on Invoice date)
	 * @param allocationAcctOnPaymentDate
	 */
	private void createRealizedGainLossFactLine(final DocLine_Allocation line, final Fact fact, final FactLine invoiceFactLine, final BigDecimal allocationAcctOnPaymentDate)
	{
		final MAcctSchema as = fact.getAcctSchema();

		//
		// Get how much was booked for invoice, on allocation's date
		final boolean isDR = invoiceFactLine.getAmtAcctDr().signum() != 0;
		final BigDecimal allocationAcctOnInvoiceDate = isDR ? invoiceFactLine.getAmtAcctDr() : invoiceFactLine.getAmtAcctCr();

		//
		// Calculate our currency gain/loss by subtracting how much was booked at invoice time and how much shall be booked at payment time.
		final BigDecimal invoicedMinusPaidAcctAmt = allocationAcctOnInvoiceDate.subtract(allocationAcctOnPaymentDate);
		if (invoicedMinusPaidAcctAmt.signum() == 0)
		{
			// no currency difference => no gain/loss
			return;
		}

		//
		// Flag this document as multi-currency to prevent source amounts balancing.
		// Our source amounts won't be source balanced anymore because the Invoice/Discount/WriteOff/PaymentSelect are booked in allocation's currency
		// and the currency gain/loss is booked in accounting currency.
		setIsMultiCurrency(true);

		// Build up the description for the new line
		final StringBuilder description = new StringBuilder();
		description.append("Amt(PaymentDate)=").append(allocationAcctOnPaymentDate);
		description.append(", Amt(InvoiceDate)=").append(allocationAcctOnInvoiceDate);

		//
		// Check the "invoice minus paid" amount and decide if it's a gain or loss and book it.
		final FactLine fl;
		// Sales invoice (i.e. the invoice was booked on C_Receivable, on Credit)
		if (!isDR)
		{
			// We got paid less than what we invoiced on our customer => loss
			if (invoicedMinusPaidAcctAmt.signum() > 0)
			{
				final BigDecimal lossAmt = invoicedMinusPaidAcctAmt;
				final MAccount lossAcct = getRealizedLossAcct(as);
				fl = fact.createLine(line, lossAcct, as.getC_Currency_ID(), lossAmt, null);
			}
			// We got paid more than what we invoiced on our customer => gain
			else
			{
				final BigDecimal gainAmt = invoicedMinusPaidAcctAmt.negate();
				final MAccount gainAcct = getRealizedGainAcct(as);
				fl = fact.createLine(line, gainAcct, as.getC_Currency_ID(), null, gainAmt);
			}
		}
		// Purchase invoice (i.e. the invoice was booked on V_Liability, on Debit)
		else
		{
			// We are paying less than what vendor invoiced us => gain
			if (invoicedMinusPaidAcctAmt.signum() > 0)
			{
				final BigDecimal gainAmt = invoicedMinusPaidAcctAmt;
				final MAccount gainAcct = getRealizedGainAcct(as);
				fl = fact.createLine(line, gainAcct, as.getC_Currency_ID(), null, gainAmt);
			}
			// We are paying more than what vendor invoiced us => loss
			else
			{
				final BigDecimal lossAmt = invoicedMinusPaidAcctAmt.negate();
				final MAccount lossAcct = getRealizedLossAcct(as);
				fl = fact.createLine(line, lossAcct, as.getC_Currency_ID(), lossAmt, null);
			}
		}

		// Update created gain/loss fact line (description, dimensions etc)
		fl.setAD_Org_ID(invoiceFactLine.getAD_Org_ID());
		fl.setC_BPartner_ID(invoiceFactLine.getC_BPartner_ID());
		fl.addDescription(description.toString());

	}

	/**************************************************************************
	 * Create Tax Correction if required by accounting schema
	 *
	 * Requirement: Adjust the tax amount, if you did not receive the full amount of the invoice (payment discount, write-off).
	 *
	 * Applies to many countries with VAT.
	 *
	 * Example:
	 *
	 * <pre>
	 * Invoice: Net $100 + Tax1 $15 + Tax2 $5 = Total $120
	 * Payment: $115 (i.e. $5 underpayment)
	 * Tax Adjustment = Tax1 = 0.63 (15/120*5)
	 * Tax2 = 0.21 (5/120/5)
	 * </pre>
	 *
	 */
	private Optional<Fact> createTaxCorrection(final MAcctSchema as, final DocLine_Allocation line)
	{
		// Make sure accounting schema requires tax correction bookings
		if (!as.isTaxCorrection())
		{
			return Optional.empty();
		}

		//
		// Get discount and write off amounts to be corrected
		final BigDecimal discountAmt = as.isTaxCorrectionDiscount() ? line.getDiscountAmt() : ZERO;
		final BigDecimal writeOffAmt = as.isTaxCorrectionWriteOff() ? line.getWriteOffAmt() : ZERO;
		if (discountAmt.signum() == 0 && writeOffAmt.signum() == 0)
		{
			// no amounts => nothing to do
			return Optional.empty();
		}

		//
		// Get the invoice
		final I_C_Invoice invoice = line.getC_Invoice();
		if (invoice == null)
		{
			throw newPostingException()
					.setC_AcctSchema(as)
					.setDocLine(line)
					.setDetailMessage("No invoice found even though we have DiscountAmt or WriteOffAmt");
		}

		boolean isDiscountExpense = invoice.isSOTrx();
		if (line.isCreditMemoInvoice())
		{
			isDiscountExpense = !isDiscountExpense;
		}

		final MAccount discountAccount = getAccount(isDiscountExpense ? Doc.ACCTTYPE_DiscountExp : Doc.ACCTTYPE_DiscountRev, as);
		final MAccount writeOffAccount = getAccount(Doc.ACCTTYPE_WriteOff, as);
		final Doc_AllocationTax taxCorrection = new Doc_AllocationTax(this, discountAccount, discountAmt, writeOffAccount, writeOffAmt, isDiscountExpense);

		final Fact fact = createEmptyFact(as);

		// FIXME: metas-tsa: fix how we retrieve the tax bookings of the invoice, i.e.
		// * here we retrieve all Fact_Acct records which are not on line level.
		// * the code is assuming that it will get the Tax bookings and the invoice gross amount booking
		// * later on org.compiere.acct.Doc_AllocationTax.createEntries(MAcctSchema, Fact, DocLine_Allocation), we skip the gross amount Fact_Acct line
		// Get Source Amounts with account
		final List<I_Fact_Acct> invoiceFactLines = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class, getCtx(), fact.get_TrxName())
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, 318) // C_Invoice
				.addEqualsFilter(I_Fact_Acct.COLUMN_Record_ID, line.getC_Invoice_ID())
				.addEqualsFilter(I_Fact_Acct.COLUMN_C_AcctSchema_ID, as.getC_AcctSchema_ID())
				.addEqualsFilter(I_Fact_Acct.COLUMN_Line_ID, null) // header lines like tax or total
				.addEqualsFilter(I_Fact_Acct.COLUMN_PostingType, fact.getPostingType()) // i.e. POST_Actual
				.orderBy()
				.addColumn(I_Fact_Acct.COLUMN_Fact_Acct_ID)
				.endOrderBy()
				.create()
				.list(I_Fact_Acct.class);
		// Invoice Not posted
		if (invoiceFactLines.isEmpty())
		{
			throw newPostingException()
					.setC_AcctSchema(as)
					.setFact(fact)
					.setDocLine(line)
					.setDetailMessage("Invoice not posted yet - " + line);
		}
		taxCorrection.addInvoiceFacts(invoiceFactLines);

		taxCorrection.createEntries(fact, line);

		return Optional.of(fact);
	}	// createTaxCorrection
}   // Doc_Allocation

/**
 * Allocation Document Tax Handing
 *
 * @author Jorg Janke
 * @version $Id: Doc_Allocation.java,v 1.6 2006/07/30 00:53:33 jjanke Exp $
 */
/* package */class Doc_AllocationTax
{
	/**
	 * Allocation Tax Adjustment
	 *
	 * @param doc_AllocationHdr
	 *
	 * @param DiscountAccount discount acct
	 * @param DiscountAmt discount amt
	 * @param WriteOffAccount write off acct
	 * @param WriteOffAmt write off amt
	 */
	public Doc_AllocationTax(final Doc_AllocationHdr doc,
			final MAccount DiscountAccount, final BigDecimal DiscountAmt,
			final MAccount WriteOffAccount, final BigDecimal WriteOffAmt,
			final boolean isDiscountExpense)
	{
		super();
		this.doc = doc;
		m_StandardDiscountAccount = DiscountAccount;
		m_DiscountAmt = DiscountAmt;
		m_WriteOffAccount = WriteOffAccount;
		m_WriteOffAmt = WriteOffAmt;
		this.isDiscountExpense = isDiscountExpense;
	}	// Doc_AllocationTax

	// services
	private static final Logger log = LogManager.getLogger(Doc_AllocationTax.class);
	private final transient IFactAcctBL factAcctBL = Services.get(IFactAcctBL.class);

	private final Doc_AllocationHdr doc;
	private final MAccount m_StandardDiscountAccount;
	private final BigDecimal m_DiscountAmt;
	private final MAccount m_WriteOffAccount;
	private final BigDecimal m_WriteOffAmt;
	private final boolean isDiscountExpense;
	//
	private I_Fact_Acct _invoiceGrandTotalFact;
	private final List<I_Fact_Acct> _invoiceTaxFacts = new ArrayList<>();

	private final PostingException newPostingException()
	{
		return doc.newPostingException();
	}

	/**
	 * Add Invoice Fact Lines
	 *
	 * @param facts invoice fact lines
	 */
	public void addInvoiceFacts(final Iterable<I_Fact_Acct> facts)
	{
		for (final I_Fact_Acct fact : facts)
		{
			addInvoiceFact(fact);
		}
	}

	/**
	 * Add Invoice Fact Line
	 *
	 * @param fact fact line
	 */
	private void addInvoiceFact(final I_Fact_Acct fact)
	{
		if (fact.getC_Tax_ID() > 0)
		{
			_invoiceTaxFacts.add(fact);
		}
		else
		{
			Check.assumeNull(_invoiceGrandTotalFact, "only one invoice grand total fact line set");
			_invoiceGrandTotalFact = fact;
		}
	}

	private I_Fact_Acct getInvoiceGrandTotalFact()
	{
		Check.assumeNotNull(_invoiceGrandTotalFact, "_invoiceGrandTotalFact not null");
		return _invoiceGrandTotalFact;
	}

	private BigDecimal getInvoiceGrandTotalAmt()
	{
		final I_Fact_Acct invoiceGrandTotalFact = getInvoiceGrandTotalFact();
		final BigDecimal amtSourceDr = invoiceGrandTotalFact.getAmtAcctDr();
		if (amtSourceDr.signum() != 0)
		{
			return amtSourceDr;
		}

		final BigDecimal amtSourceCr = invoiceGrandTotalFact.getAmtAcctCr();
		if (amtSourceCr.signum() != 0)
		{
			return amtSourceCr;
		}

		return ZERO;
	}

	private List<I_Fact_Acct> getInvoiceTaxFacts()
	{
		return _invoiceTaxFacts;
	}

	public boolean hasInvoiceTaxFacts()
	{
		return !_invoiceTaxFacts.isEmpty();
	}

	private final MAccount getTaxDiscountAcct(final MAcctSchema as, final int taxId)
	{
		final MAccount discountAccount = MTax.getDiscountAccount(taxId, isDiscountExpense, as);
		if (discountAccount != null)
		{
			return discountAccount;
		}
		return m_StandardDiscountAccount;
	}

	/**
	 * Create Accounting Entries
	 *
	 * @param as account schema
	 * @param fact fact to add lines
	 * @param line line
	 * @return true if created
	 */
	public void createEntries(final Fact fact, final DocLine_Allocation line)
	{
		// If there are no tax facts, there is no need to do tax correction
		if (!hasInvoiceTaxFacts())
		{
			return;
		}

		final MAcctSchema as = fact.getAcctSchema();

		//
		// Get total index (the Receivables/Liabilities line)
		final BigDecimal invoiceGrandTotalAmt = getInvoiceGrandTotalAmt();

		//
		// Iterate the invoice tax facts
		final int precision = as.getStdPrecision();
		for (final I_Fact_Acct taxFactAcct : getInvoiceTaxFacts())
		{
			//
			// Get the C_Tax_ID
			final int taxId = taxFactAcct.getC_Tax_ID();
			if (taxId <= 0)
			{
				// shall not happen
				newPostingException()
						.setC_AcctSchema(as)
						.setFact(fact)
						.setFactLine(taxFactAcct)
						.setDocLine(line)
						.setDetailMessage("No tax found");
			}

			//
			// Get the Tax Account
			final MAccount taxAcct = factAcctBL.getAccount(taxFactAcct);
			if (taxAcct == null || taxAcct.getC_ValidCombination_ID() <= 0)
			{
				throw newPostingException()
						.setC_AcctSchema(as)
						.setFact(fact)
						.setFactLine(taxFactAcct)
						.setDocLine(line)
						.setDetailMessage("Tax Account not found/created");
			}

			//
			// Discount Amount
			if (m_DiscountAmt.signum() != 0)
			{
				final MAccount discountAcct = getTaxDiscountAcct(as, taxId);

				// Original Tax is DR - need to correct it CR
				if (taxFactAcct.getAmtSourceDr().signum() != 0)
				{
					final BigDecimal invoiceTaxAmt = taxFactAcct.getAmtSourceDr();
					final BigDecimal amount = calcAmount(invoiceTaxAmt, invoiceGrandTotalAmt, m_DiscountAmt, precision);
					if (amount.signum() != 0)
					{
						final String description = "Invoice TaxAmt/GrandTotal=" + invoiceTaxAmt + "/" + invoiceGrandTotalAmt + ", Alloc Discount=" + m_DiscountAmt;

						// Discount expense
						if (isDiscountExpense)
						{
							final FactLine flDR = fact.createLine(line, discountAcct, as.getC_Currency_ID(), amount, null);
							updateFactLine(flDR, taxId, description);
							final FactLine flCR = fact.createLine(line, taxAcct, as.getC_Currency_ID(), null, amount);
							updateFactLine(flCR, taxId, description);
						}
						// Discount revenue
						else
						{
							final FactLine flDR = fact.createLine(line, discountAcct, as.getC_Currency_ID(), amount.negate(), null);
							updateFactLine(flDR, taxId, description);
							final FactLine flCR = fact.createLine(line, taxAcct, as.getC_Currency_ID(), null, amount.negate());
							updateFactLine(flCR, taxId, description);
						}

					}
				}
				// Original Tax is CR - need to correct it DR
				else
				{
					final BigDecimal invoiceTaxAmt = taxFactAcct.getAmtSourceCr();
					final BigDecimal amount = calcAmount(invoiceTaxAmt, invoiceGrandTotalAmt, m_DiscountAmt, precision);
					if (amount.signum() != 0)
					{
						final String description = "Invoice TaxAmt/GrandTotal=" + invoiceTaxAmt + "/" + invoiceGrandTotalAmt + ", Alloc Discount=" + m_DiscountAmt;

						// Discount expense
						if (isDiscountExpense)
						{
							final FactLine flDR = fact.createLine(line, taxAcct, as.getC_Currency_ID(), amount, null);
							updateFactLine(flDR, taxId, description);
							final FactLine flCR = fact.createLine(line, discountAcct, as.getC_Currency_ID(), null, amount);
							updateFactLine(flCR, taxId, description);
						}
						// Discount revenue
						else
						{
							final FactLine flDR = fact.createLine(line, taxAcct, as.getC_Currency_ID(), amount.negate(), null);
							updateFactLine(flDR, taxId, description);
							final FactLine flCR = fact.createLine(line, discountAcct, as.getC_Currency_ID(), null, amount.negate());
							updateFactLine(flCR, taxId, description);
						}
					}
				}
			}            	// Discount

			//
			// WriteOff Amount
			if (m_WriteOffAmt.signum() != 0)
			{
				// Original Tax is DR - need to correct it CR
				if (taxFactAcct.getAmtSourceDr().signum() != 0)
				{
					final BigDecimal invoiceTaxAmt = taxFactAcct.getAmtSourceDr();
					final BigDecimal amount = calcAmount(invoiceTaxAmt, invoiceGrandTotalAmt, m_WriteOffAmt, precision);
					if (amount.signum() != 0)
					{
						final String description = "Invoice TaxAmt/GrandTotal=" + invoiceTaxAmt + "/" + invoiceGrandTotalAmt + ", Alloc WriteOff=" + m_WriteOffAmt;

						final FactLine flDR = fact.createLine(line, m_WriteOffAccount, as.getC_Currency_ID(), amount, null);
						updateFactLine(flDR, taxId, description);
						final FactLine flCR = fact.createLine(line, taxAcct, as.getC_Currency_ID(), null, amount);
						updateFactLine(flCR, taxId, description);
					}
				}
				// Original Tax is CR - need to correct it DR
				else
				{
					final BigDecimal invoiceTaxAmt = taxFactAcct.getAmtSourceCr();
					final BigDecimal amount = calcAmount(invoiceTaxAmt, invoiceGrandTotalAmt, m_WriteOffAmt, precision);
					if (amount.signum() != 0)
					{
						final String description = "Invoice TaxAmt/GrandTotal=" + invoiceTaxAmt + "/" + invoiceGrandTotalAmt + ", Alloc WriteOff=" + m_WriteOffAmt;

						final FactLine flDR = fact.createLine(line, taxAcct, as.getC_Currency_ID(), amount, null);
						updateFactLine(flDR, taxId, description);
						final FactLine flCR = fact.createLine(line, m_WriteOffAccount, as.getC_Currency_ID(), null, amount);
						updateFactLine(flCR, taxId, description);
					}
				}
			}            	// WriteOff
		}            	// for all lines
	}	// createEntries

	/**
	 * Calculate the tax amount part.
	 *
	 * @param taxAmt tax
	 * @param invoiceGrandTotalAmt total
	 * @param discountAmt reduction amt
	 * @param precision precision
	 * @return (taxAmt / invoice grand total amount) * taxAmt
	 */
	private static final BigDecimal calcAmount(final BigDecimal taxAmt, final BigDecimal invoiceGrandTotalAmt, final BigDecimal discountAmt, final int precision)
	{
		if (log.isDebugEnabled())
		{
			log.debug("DiscountAmt=" + discountAmt + " - Invoice Total=" + invoiceGrandTotalAmt + ", TaxAmt=" + taxAmt);
		}

		if (taxAmt.signum() == 0
				|| invoiceGrandTotalAmt.signum() == 0
				|| discountAmt.signum() == 0)
		{
			return ZERO;
		}

		//
		final BigDecimal multiplier = taxAmt.divide(invoiceGrandTotalAmt, 10, BigDecimal.ROUND_HALF_UP);
		BigDecimal taxAmtPart = multiplier.multiply(discountAmt);
		if (taxAmtPart.scale() > precision)
		{
			taxAmtPart = taxAmtPart.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}

		if (log.isDebugEnabled())
		{
			log.debug(taxAmtPart + " (Mult=" + multiplier + "(Prec=" + precision + ")");
		}
		return taxAmtPart;
	}	// calcAmount

	/**
	 * Convenient method to update {@link FactLine}'s infos if the line is not null.
	 *
	 * @param fl
	 * @param taxId
	 * @param description description to add
	 */
	private static final void updateFactLine(final FactLine fl, final int taxId, final String description)
	{
		if (fl == null)
		{
			return;
		}

		fl.setC_Tax_ID(taxId);
		if (!Check.isEmpty(description, true))
		{
			fl.addDescription(description);
		}
	}
}	// Doc_AllocationTax

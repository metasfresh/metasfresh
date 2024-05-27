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

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.acct.doc.PostingException;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.LegacyAdapters;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MAccount;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceTax;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

/**
 * Post Allocation Documents.
 *
 * <pre>
 *  Table:              C_AllocationHdr
 *  Document Types:     CMA
 * </pre>
 *
 * @author Jorg Janke
 * @author phib BF [ 2019262 ] Allocation posting currency gain/loss omits line reference
 * @version $Id: Doc_Allocation.java,v 1.6 2006/07/30 00:53:33 jjanke Exp $
 * <p>
 * FR [ 1840016 ] Avoid usage of clearing accounts - subject to C_AcctSchema.IsPostIfClearingEqual Avoid posting if Receipt and both accounts Unallocated Cash and Receivable are equal Avoid
 * posting if Payment and both accounts Payment Select and Liability are equal
 */
public class Doc_AllocationHdr extends Doc<DocLine_Allocation>
{
	private static final Logger logger = LogManager.getLogger(Doc_AllocationHdr.class);
	private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

	public Doc_AllocationHdr(final AcctDocContext ctx)
	{
		super(ctx, DOCTYPE_Allocation);
	}   // Doc_Allocation

	@Override
	protected void loadDocumentDetails()
	{
		final I_C_AllocationHdr alloc = getModel(I_C_AllocationHdr.class);
		setDateDoc(alloc.getDateTrx());
		setDocLines(loadLines(alloc));
	}

	private List<DocLine_Allocation> loadLines(final I_C_AllocationHdr alloc)
	{
		final List<DocLine_Allocation> docLines = new ArrayList<>();
		final Map<Integer, DocLine_Allocation> id2docLine = new HashMap<>();
		for (final I_C_AllocationLine line : allocationDAO.retrieveAllLines(alloc))
		{
			final DocLine_Allocation docLine = new DocLine_Allocation(line, this);

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

		return docLines;
	}    // loadLines

	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}

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
	 * <p>
	 * Tax needs to be corrected for discount & write-off; Currency gain & loss is realized here.
	 *
	 * @param as accounting schema
	 * @return Fact
	 */
	@Override
	public List<Fact> createFacts(final AcctSchema as)
	{
		final ArrayList<Fact> facts = new ArrayList<>();

		// create Fact Header
		final Fact fact = createEmptyFact(as);
		facts.add(fact);

		int countPayments = 0;
		int countInvoices = 0;
		for (final DocLine_Allocation line : getDocLines())
		{
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
			if (!isReversedPaymentAllocation())
			{
				// in case of reversed payment allocation, nothing is to do
				// because both line have the same account, dimension etc. in one line we add, in the other line we subtract again;
				createFactLines_PaymentAllocation(fact);
			}
			return facts;
		}
		else if (countPayments == 0 && countInvoices > 0)
		{
			if (isReversedInvoiceAllocation())
			{
				return facts; // nothing to do, analog to isReversedPaymentAllocation()
			}
			else
			{
				// because we have just one fact line per allocation line
				fact.setFactTrxLinesStrategy(PerDocumentFactTrxStrategy.instance);
			}
		}

		for (final DocLine_Allocation line : getDocLines())
		{
			setBPartnerId(line.getBPartnerId());

			// CashBankTransfer - all references null and Discount/WriteOff = 0
			if (line.getPaymentId() != null
					&& line.getC_Invoice_ID() <= 0
					&& line.getC_CashLine_ID() <= 0 && line.getBPartnerId() == null
					&& line.getDiscountAmt().isZero()
					&& line.getWriteOffAmt().isZero())
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
					assert line.getOrderLineId() != null : line;
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
			final List<Fact> taxCorrectionFacts = createTaxCorrection(fact.getAcctSchema(), line);
			facts.addAll(taxCorrectionFacts);
		}                // for all lines

		// reset line info
		setBPartnerId(null);

		return facts;
	}   // createFact

	private Fact createEmptyFact(final AcctSchema as)
	{
		return new Fact(this, as, PostingType.Actual);
	}

	/**
	 * Create facts for payments in the case when no invoice was involved.
	 * The pay Amt will go to Credit for outgoing payments and to Debit for Incoming payments
	 *
	 * @param fact
	 */
	private void createFactLines_PaymentAllocation(final Fact fact)
	{
		fact.setFactTrxLinesStrategy(PerDocumentFactTrxStrategy.instance); // because we have just one fact line per allocation line

		final AcctSchema as = fact.getAcctSchema();

		for (final DocLine_Allocation line : getDocLines())
		{
			// FRESH-523: Make sure the partner of the payment is set in the Doc. It will be needed when selecting the correct Account
			setBPartnerId(line.getPaymentBPartnerId());

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
					fl_Payment = fact.createLine()
							.setDocLine(line)
							.setAccount(paymentAcct)
							.setCurrencyId(getCurrencyId())
							.setAmtSourceDrOrCr(allocatedAmt)
							.alsoAddZeroLine()
							.buildAndAdd();
				}
				// Outgoing payment
				else
				{
					// Originally on Debit. The amount must be moved to Credit, with different sign
					fl_Payment = fact.createLine()
							.setDocLine(line)
							.setAccount(paymentAcct)
							.setAmtSource(getCurrencyId(), null, allocatedAmt.negate())
							.alsoAddZeroLine()
							.buildAndAdd();

				}

				// Make sure the fact line was created
				Check.assumeNotNull(fl_Payment, "fl_Payment not null");

				fl_Payment.setAD_Org_ID(line.getPaymentOrgId().getRepoId());
				fl_Payment.setC_BPartner_ID(line.getPaymentBPartnerId());
			}
		}
	}

	private boolean isReversedPaymentAllocation()
	{
		if (!mightBeReversedAllocation())
		{
			return false;
		}

		final List<DocLine_Allocation> lines = getDocLines();

		// note: the p_lines are not each others' counter doc lines, i.e. DocLine_Allocation.getCounterDocLine() == null and getCounter_AllocationLine_ID == 0
		final I_C_Payment firstPayment = lines.get(0).getC_Payment();
		final I_C_Payment secondPayment = lines.get(1).getC_Payment();

		boolean firstPaymentIsReversalOfSecond = firstPayment.getReversal_ID() == secondPayment.getC_Payment_ID();
		boolean secondPaymentIsReversalOfFirst = secondPayment.getReversal_ID() == firstPayment.getC_Payment_ID();
		return firstPaymentIsReversalOfSecond || secondPaymentIsReversalOfFirst;
	}

	private boolean isReversedInvoiceAllocation()
	{
		if (!mightBeReversedAllocation())
		{
			return false;
		}

		// note: the p_lines are not each others' counter doc lines, i.e. DocLine_Allocation.getCounterDocLine() == null and getCounter_AllocationLine_ID == 0
		final List<DocLine_Allocation> lines = getDocLines();
		final I_C_Invoice firstInvoice = lines.get(0).getC_Invoice();
		final I_C_Invoice secondInvoice = lines.get(1).getC_Invoice();

		boolean firstInvoiceIsReversalOfSecond = firstInvoice.getReversal_ID() == secondInvoice.getC_Invoice_ID();
		boolean secondInvoiceIsReversalOfFirst = secondInvoice.getReversal_ID() == firstInvoice.getC_Invoice_ID();
		return firstInvoiceIsReversalOfSecond || secondInvoiceIsReversalOfFirst;
	}

	private boolean mightBeReversedAllocation()
	{
		final List<DocLine_Allocation> lines = getDocLines();
		if (lines == null || lines.size() != 2)
		{
			return false;
		}

		// note: in the code this calls this method, we checked that 0 lines refer to an invoice and > 0 lines refer to a payment
		final DocLine_Allocation firstAllocationDocLine = lines.get(0);
		final DocLine_Allocation secondAllocationDocLine = lines.get(1);
		// note: they are not each others' counter doc lines, i.e. DocLine_Allocation.getCounterDocLine() == null and getCounter_AllocationLine_ID == 0

		final boolean amountsAreMatching = firstAllocationDocLine.getAllocatedAmt().negate().compareTo(secondAllocationDocLine.getAllocatedAmt()) == 0;
		return amountsAreMatching;
	}

	/**
	 * Creates facts related to {@link DocLine_Allocation#getPaymentWriteOffAmt()}.
	 *
	 * @see "Task #09441 for more informations"
	 */
	private void createPaymentWriteOffAmtFacts(final Fact fact, final DocLine_Allocation line)
	{
		if (line.getPaymentWriteOffAmt().signum() == 0)
		{
			return;
		}

		final I_C_Payment payment = line.getC_Payment();
		Check.assumeNotNull(payment, "payment not null for {}", line); // shall not happen

		final AcctSchema as = fact.getAcctSchema();
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
			final MAccount discountAcct = line.getPaymentWriteOffAccount(as.getId())
					.orElseGet(() -> getAccount(AccountType.DiscountExp, as));
			final BigDecimal paymentWriteOffAmt = line.getPaymentWriteOffAmt();

			fl_Payment = fact.createLine(line, paymentAcct, getCurrencyId(), paymentWriteOffAmt, null);
			fl_Discount = fact.createLine(line, discountAcct, getCurrencyId(), null, paymentWriteOffAmt);
		}
		//
		// Case: Vendor Overpayment
		//
		// PaymentAcct CR
		// DiscountRevenue DR
		else
		{
			final MAccount discountAcct = line.getPaymentWriteOffAccount(as.getId())
					.orElseGet(() -> getAccount(AccountType.DiscountRev, as));
			final BigDecimal paymentWriteOffAmt = line.getPaymentWriteOffAmt().negate();

			fl_Payment = fact.createLine(line, paymentAcct, getCurrencyId(), null, paymentWriteOffAmt);
			fl_Discount = fact.createLine(line, discountAcct, getCurrencyId(), paymentWriteOffAmt, null);
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
	 * @return source and accounted amount
	 */
	private AmountSourceAndAcct createPaymentFacts(final Fact fact, final DocLine_Allocation line)
	{
		final AcctSchema as = fact.getAcctSchema();

		final BigDecimal paymentAllocatedAmt = line.getAllocatedAmt();
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
				.setCurrencyId(getCurrencyId())
				.setCurrencyConversionCtx(line.getPaymentCurrencyConversionCtx())
				.orgId(line.getPaymentOrgId())
				.bpartnerId(line.getPaymentBPartnerId())
				.alsoAddZeroLine();

		if (line.isSOTrxInvoice())
		{
			// ARC:
			if (line.isCreditMemoInvoice())
			{
				factLineBuilder.setAmtSource(null, paymentAllocatedAmt.negate());
			}
			// ARI:
			else
			{
				factLineBuilder.setAmtSource(paymentAllocatedAmt, null);
			}
		}
		else
		{
			// APC
			if (line.isCreditMemoInvoice())
			{
				factLineBuilder.setAmtSource(paymentAllocatedAmt, null);
			}
			// API
			else
			{
				factLineBuilder.setAmtSource(null, paymentAllocatedAmt.negate());
			}
		}

		final FactLine factLine = factLineBuilder.buildAndAdd();

		return factLine.getAmtSourceAndAcctDrOrCr();
	}

	/**
	 * Create accounting facts for early payment discounts. Separate for each tax.
	 */
	private AmountSourceAndAcct createInvoiceDiscountFacts(final Fact fact, final DocLine_Allocation line)
	{
		final Money discountAmt_Abs = line.getDiscountAmt();
		if (discountAmt_Abs.signum() == 0)
		{
			return AmountSourceAndAcct.ZERO;
		}

		final Money discountAmt_CMAdjusted = line.getDiscountAmt_CMAdjusted();

		final AcctSchema as = fact.getAcctSchema();
		final I_C_Payment payment = line.getC_Payment();
		final I_C_Invoice invoice = line.getC_Invoice();
		final boolean isCreditMemoInvoice = line.isCreditMemoInvoice();

		boolean isDiscountExpense = line.isSOTrxInvoice();
		if (isCreditMemoInvoice)
		{
			isDiscountExpense = !isDiscountExpense;
		}

		//
		// Determine which currency conversion we shall use
		final CurrencyConversionContext invoiceCurrencyConversionCtx;
		if (fact.isAccountingCurrency(line.getInvoiceCurrencyId()))
		{
			// use default context because the invoice is in accounting currency, so we shall have no currency gain/loss
			invoiceCurrencyConversionCtx = null;
		}
		else
		{
			invoiceCurrencyConversionCtx = line.getInvoiceCurrencyConversionCtx();
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
				fl = fact.createLine()
						.setDocLine(line)
						.setAccount(getAccount(AccountType.DiscountExp, as))
						.setAmtSource(discountAmt_CMAdjusted.toBigDecimal(), null)
						.setCurrencyConversionCtx(invoiceCurrencyConversionCtx)
						.buildAndAdd();
				discountAmtSourceAndAcct.addAmtSource(fl.getAmtSourceDr()).addAmtAcct(fl.getAmtAcctDr());
			}
			else
			{
				fl = fact.createLine()
						.setDocLine(line)
						.setAccount(getAccount(AccountType.DiscountRev, as))
						.setAmtSource(null, discountAmt_CMAdjusted.negate().toBigDecimal())
						.setCurrencyConversionCtx(invoiceCurrencyConversionCtx)
						.buildAndAdd();
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
			final CurrencyId currencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());

			Money discountSum = Money.zero(currencyId);
			for (int i = 0; i < taxes.length; i++)
			{
				// TaxDiscountAmt = TaxBaseAmt * Skonto * (1+TaxRate)
				// but we are calculating differently to avoid division by zero when calculating TaxRate=TaxAmt/TaxBaseAmt
				final BigDecimal taxAmt = taxes[i].getTaxAmt();
				final BigDecimal taxBaseAmt = taxes[i].getTaxBaseAmt();
				final BigDecimal baseAndTaxAmt = taxBaseAmt.add(taxAmt);
				Money taxDiscountAmt = Money.of(baseAndTaxAmt.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP), currencyId);
				if (taxAmt.signum() == 0)
				{
					taxDiscountAmt = Money.zero(currencyId);
				}

				discountSum = discountSum.add(taxDiscountAmt);
				if (i == taxes.length - 1)
				{
					// last tax, check amounts
					if (!discountSum.isEqualByComparingTo(discountAmt_CMAdjusted))
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
					account = getAccount(isDiscountExpense ? AccountType.DiscountExp : AccountType.DiscountRev, as);
				}

				//
				// Create discount fact line
				final Money taxDiscountAmt_CMAdjusted = isCreditMemoInvoice ? taxDiscountAmt.negate() : taxDiscountAmt;
				final FactLine fl;
				if (isDiscountExpense)
				{
					fl = fact.createLine()
							.setDocLine(line)
							.setAccount(account)
							.setAmtSource(taxDiscountAmt_CMAdjusted, null)
							.setCurrencyConversionCtx(invoiceCurrencyConversionCtx)
							.buildAndAdd();
				}
				else
				{
					fl = fact.createLine()
							.setDocLine(line)
							.setAccount(account)
							.setAmtSource(null, taxDiscountAmt_CMAdjusted.negate())
							.setCurrencyConversionCtx(invoiceCurrencyConversionCtx)
							.buildAndAdd();
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
	private final BigDecimal calculateDiscountFactor(final Money discountAmt, final I_C_Invoice invoice)
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
			logger.warn("Cannot calculate the discount factor when invoice grand total is ZERO. Considering ZERO", ex);
			return BigDecimal.ZERO;
		}

		final BigDecimal discountFactor = discountAmt.toBigDecimal().divide(invoiceGrandTotalAbs, 8, RoundingMode.HALF_UP);
		return discountFactor;
	}

	/**
	 * Returns early payment discount account for given tax.
	 *
	 * @param taxId
	 * @return
	 */
	private static final MAccount getTaxDiscountAccount(final int taxId, final boolean isDiscountExpense, final AcctSchema as)
	{
		return getTaxDiscountAccount(taxId, isDiscountExpense, as.getId());
	}

	static MAccount getTaxDiscountAccount(final int taxId, final boolean isDiscountExpense, final AcctSchemaId acctSchemaId)
	{
		if (taxId <= 0)
		{
			return null;
		}

		String sql = "SELECT T_PayDiscount_Exp_Acct FROM C_Tax_Acct WHERE C_Tax_ID=? AND C_AcctSchema_ID=?";
		if (!isDiscountExpense)
		{
			sql = "SELECT T_PayDiscount_Rev_Acct FROM C_Tax_Acct WHERE C_Tax_ID=? AND C_AcctSchema_ID=?";
		}

		final int Account_ID = DB.getSQLValueEx(ITrx.TRXNAME_None, sql, taxId, acctSchemaId);
		// No account
		if (Account_ID <= 0)
		{
			logger.error("NO account for C_Tax_ID=" + taxId);
			return null;
		}

		// Return Account
		final MAccount acct = Services.get(IAccountDAO.class).getById(Account_ID);
		return acct;
	}

	/**
	 * Creates the {@link FactLine} to book the invoice write off.
	 *
	 * @return WriteOff amount booked
	 */
	private final AmountSourceAndAcct createInvoiceWriteOffFacts(final Fact fact, final DocLine_Allocation line)
	{
		final Money writeOffAmt = line.getWriteOffAmt();
		if (writeOffAmt.signum() == 0)
		{
			return AmountSourceAndAcct.ZERO;
		}

		final AcctSchema as = fact.getAcctSchema();
		final FactLineBuilder factLineBuilder = fact.createLine()
				.setDocLine(line)
				.setAccount(getAccount(AccountType.WriteOff, as))
				.setCurrencyId(getCurrencyId())
				.orgId(line.getPaymentOrgId())
				.bpartnerId(line.getPaymentBPartnerId())
				.alsoAddZeroLine();

		if (line.isSOTrxInvoice())
		{
			if (line.isCreditMemoInvoice())
			{
				factLineBuilder.setAmtSource(null, writeOffAmt.negate());
			}
			else
			{
				factLineBuilder.setAmtSource(writeOffAmt, null);
			}
		}
		else
		{
			if (line.isCreditMemoInvoice())
			{
				factLineBuilder.setAmtSource(writeOffAmt, null);
			}
			else
			{
				factLineBuilder.setAmtSource(null, writeOffAmt.negate());
			}
		}

		// IMPORTANT: we shall write off using the same currency rate as the invoice
		if (!fact.isAccountingCurrency(line.getInvoiceCurrencyId()))
		{
			factLineBuilder.setCurrencyConversionCtx(line.getInvoiceCurrencyConversionCtx());
		}

		final FactLine factLine = factLineBuilder.buildAndAdd();

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

		final AcctSchema as = fact.getAcctSchema();
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
		final CurrencyConversionContext invoiceCurrencyConversionCtx;
		if (fact.isAccountingCurrency(line.getInvoiceCurrencyId()))
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
				.setCurrencyId(getCurrencyId())
				.setCurrencyConversionCtx(invoiceCurrencyConversionCtx)
				.orgId(line.getInvoiceOrgId())
				.bpartnerId(line.getInvoiceBPartnerId())
				.alsoAddZeroLine();

		if (line.isSOTrxInvoice())
		{
			factLineBuilder.setAccount(getAccount(AccountType.C_Receivable, as));

			// ARC
			if (line.isCreditMemoInvoice())
			{
				factLineBuilder.setAmtSource(allocationSource, null);
			}
			// ARI
			else
			{
				factLineBuilder.setAmtSource(null, allocationSource);
			}
		}
		else
		{
			factLineBuilder.setAccount(getAccount(AccountType.V_Liability, as));

			// APC
			if (line.isCreditMemoInvoice())
			{
				factLineBuilder.setAmtSource(null, allocationSource);
			}
			// API
			else
			{
				factLineBuilder.setAmtSource(allocationSource, null);
			}
		}

		final FactLine factLine = factLineBuilder.buildAndAdd();

		final Money allocationAcctOnPaymentDate = Money.of(invoiceTotalAllocatedAmtSourceAndAcct.getAmtAcct(), factLine.getAcctSchema().getCurrencyId());
		createRealizedGainLossFactLine(line, fact, factLine, allocationAcctOnPaymentDate);
	}

	/**
	 * Creates the {@link FactLine} to book the purchase - sales invoice compensation
	 *
	 * @param fact
	 * @param line
	 * @return
	 */
	private AmountSourceAndAcct createPurchaseSalesInvoiceFacts(final Fact fact, final DocLine_Allocation line)
	{
		final AcctSchema as = fact.getAcctSchema();

		// Do nothing if this is not a compensation line or it was already compensated
		if (!line.isSalesPurchaseInvoiceToCompensate(as.getId()))
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

		final DocLine_Allocation counterLine = line.getCounterDocLine();
		Check.assumeNotNull(counterLine, "counterLine not null");

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
				.setCurrencyId(getCurrencyId())
				.orgId(counterLine.getInvoiceOrgId())
				.bpartnerId(counterLine.getInvoiceBPartnerId())
				.alsoAddZeroLine();
		if (counterLine.isSOTrxInvoice())
		{
			factLineBuilder.setAccount(getAccount(AccountType.C_Receivable, as));
			factLineBuilder.setAmtSource(null, compensationAmtSource.negate());
		}
		else
		{
			factLineBuilder.setAccount(getAccount(AccountType.V_Liability, as));
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
	 * <p>
	 * It is also creating a new FactLine where the currency gain/loss is booked.
	 *
	 * @param invoiceFactLine invoice related booking (on Invoice date)
	 */
	private void createRealizedGainLossFactLine(final DocLine_Allocation line, final Fact fact, final FactLine invoiceFactLine, final Money allocationAcctOnPaymentDate)
	{
		final AcctSchema as = fact.getAcctSchema();

		//
		// Get how much was booked for invoice, on allocation's date
		final boolean isDR = invoiceFactLine.getAmtAcctDr().signum() != 0;
		final Money allocationAcctOnInvoiceDate = isDR
				? Money.of(invoiceFactLine.getAmtAcctDr(), as.getCurrencyId())
				: Money.of(invoiceFactLine.getAmtAcctCr(), as.getCurrencyId());

		//
		// Calculate our currency gain/loss by subtracting how much was booked at invoice time and how much shall be booked at payment time.
		final Money invoicedMinusPaidAcctAmt = allocationAcctOnInvoiceDate.subtract(allocationAcctOnPaymentDate);
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

		// Disable trx lines strategy in order to tolerate cases with multiple DR and CR lines
		// We have to do this because in case AcctSchema.isAllowMultiDebitAndCredit is false, for some cases we will get multiple DR/CR lines.
		fact.setFactTrxLinesStrategy(null);

		// Build up the description for the new line
		final String description = "Amt(PaymentDate)=" + allocationAcctOnPaymentDate
				+ ", Amt(InvoiceDate)=" + allocationAcctOnInvoiceDate;

		//
		// Check the "invoice minus paid" amount and decide if it's a gain or loss and book it.
		final FactLine fl;
		// Sales invoice (i.e. the invoice was booked on C_Receivable, on Credit)
		if (!isDR)
		{
			// We got paid less than what we invoiced on our customer => loss
			if (invoicedMinusPaidAcctAmt.signum() > 0)
			{
				final Money lossAmt = invoicedMinusPaidAcctAmt;
				final MAccount lossAcct = getRealizedLossAcct(as);
				fl = fact.createLine(line, lossAcct, lossAmt.getCurrencyId(), lossAmt.toBigDecimal(), null);
			}
			// We got paid more than what we invoiced on our customer => gain
			else
			{
				final Money gainAmt = invoicedMinusPaidAcctAmt.negate();
				final MAccount gainAcct = getRealizedGainAcct(as);
				fl = fact.createLine(line, gainAcct, gainAmt.getCurrencyId(), null, gainAmt.toBigDecimal());
			}
		}
		// Purchase invoice (i.e. the invoice was booked on V_Liability, on Debit)
		else
		{
			// We are paying less than what vendor invoiced us => gain
			if (invoicedMinusPaidAcctAmt.signum() > 0)
			{
				final Money gainAmt = invoicedMinusPaidAcctAmt;
				final MAccount gainAcct = getRealizedGainAcct(as);
				fl = fact.createLine(line, gainAcct, gainAmt.getCurrencyId(), null, gainAmt.toBigDecimal());
			}
			// We are paying more than what vendor invoiced us => loss
			else
			{
				final Money lossAmt = invoicedMinusPaidAcctAmt.negate();
				final MAccount lossAcct = getRealizedLossAcct(as);
				fl = fact.createLine(line, lossAcct, lossAmt.getCurrencyId(), lossAmt.toBigDecimal(), null);
			}
		}

		// Update created gain/loss fact line (description, dimensions etc)
		fl.setAD_Org_ID(invoiceFactLine.getAD_Org_ID());
		fl.setC_BPartner_ID(invoiceFactLine.getC_BPartner_ID());
		fl.addDescription(description);

	}

	/**************************************************************************
	 * Create Tax Correction facts if required by accounting schema
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
	private List<Fact> createTaxCorrection(final AcctSchema as, final DocLine_Allocation line)
	{
		// Make sure accounting schema requires tax correction bookings
		final TaxCorrectionType taxCorrectionType = as.getTaxCorrectionType();
		if (taxCorrectionType.isNone())
		{
			return ImmutableList.of();
		}

		//
		// Get discount and write off amounts to be corrected
		final Money discountAmt = taxCorrectionType.isDiscount() ? line.getDiscountAmt() : line.getDiscountAmt().toZero();
		final Money writeOffAmt = taxCorrectionType.isWriteOff() ? line.getWriteOffAmt() : line.getWriteOffAmt().toZero();
		if (discountAmt.signum() == 0 && writeOffAmt.signum() == 0)
		{
			// no amounts => nothing to do
			return ImmutableList.of();
		}

		//
		// Get the invoice
		final I_C_Invoice invoice = line.getC_Invoice();
		if (invoice == null)
		{
			throw newPostingException()
					.setAcctSchema(as)
					.setDocLine(line)
					.setDetailMessage("No invoice found even though we have DiscountAmt or WriteOffAmt");
		}

		boolean isDiscountExpense = invoice.isSOTrx();
		if (line.isCreditMemoInvoice())
		{
			isDiscountExpense = !isDiscountExpense;
		}

		final MAccount discountAccount = getAccount(isDiscountExpense ? AccountType.DiscountExp : AccountType.DiscountRev, as);
		final MAccount writeOffAccount = getAccount(AccountType.WriteOff, as);
		final Doc_AllocationTax taxCorrection = new Doc_AllocationTax(this, discountAccount, discountAmt, writeOffAccount, writeOffAmt, isDiscountExpense);

		// FIXME: metas-tsa: fix how we retrieve the tax bookings of the invoice, i.e.
		// * here we retrieve all Fact_Acct records which are not on line level.
		// * the code is assuming that it will get the Tax bookings and the invoice gross amount booking
		// * later on org.compiere.acct.Doc_AllocationTax.createEntries(AcctSchema, Fact, DocLine_Allocation), we skip the gross amount Fact_Acct line
		// Get Source Amounts with account
		final IQueryBuilder<I_Fact_Acct> invoiceFactLinesQueryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, 318) // C_Invoice
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, line.getC_Invoice_ID())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_AcctSchema_ID, as.getId())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Line_ID, null) // header lines like tax or total
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_PostingType, PostingType.Actual.getCode())
				.orderBy(I_Fact_Acct.COLUMN_Fact_Acct_ID);

		//
		// Skip any suspense balancing booking because those are not relevant for tax correction calculation
		final AccountId suspenseBalancingAcctId = as.getGeneralLedger().getSuspenseBalancingAcctId();
		if (suspenseBalancingAcctId != null)
		{
			final MAccount suspenseBalancingAcct = services.getAccountById(suspenseBalancingAcctId);
			invoiceFactLinesQueryBuilder.addNotEqualsFilter(I_Fact_Acct.COLUMNNAME_Account_ID, suspenseBalancingAcct.getAccount_ID());
		}
		//
		// Skip any currency balancing booking because those are not relevant for tax correction calculation
		final AccountId currencyBalancingAcctId = as.getGeneralLedger().getCurrencyBalancingAcctId();
		if (currencyBalancingAcctId != null)
		{
			final MAccount currencyBalancingAcct = services.getAccountById(currencyBalancingAcctId);
			invoiceFactLinesQueryBuilder.addNotEqualsFilter(I_Fact_Acct.COLUMNNAME_Account_ID, currencyBalancingAcct.getAccount_ID());
		}

		final List<I_Fact_Acct> invoiceFactLines = invoiceFactLinesQueryBuilder
				.create()
				.list(I_Fact_Acct.class);
		// Invoice Not posted
		if (invoiceFactLines.isEmpty())
		{
			throw newPostingException()
					.setAcctSchema(as)
					.setDocLine(line)
					.setDetailMessage("Invoice not posted yet - " + line);
		}
		taxCorrection.addInvoiceFacts(invoiceFactLines);

		return taxCorrection.createEntries(as, line);
	}    // createTaxCorrection
}   // Doc_Allocation

/* package */class Doc_AllocationTax
{
	private final AcctDocRequiredServicesFacade services;

	private final Doc_AllocationHdr doc;
	private final MAccount m_StandardDiscountAccount;
	private final Money m_DiscountAmt;
	private final MAccount m_WriteOffAccount;
	private final Money m_WriteOffAmt;
	private final boolean isDiscountExpense;
	//
	private I_Fact_Acct _invoiceGrandTotalFact;
	private final ArrayList<I_Fact_Acct> _invoiceTaxFacts = new ArrayList<>();

	public Doc_AllocationTax(final Doc_AllocationHdr doc,
							 final MAccount DiscountAccount, final Money DiscountAmt,
							 final MAccount WriteOffAccount, final Money WriteOffAmt,
							 final boolean isDiscountExpense)
	{
		Money.assertSameCurrency(DiscountAmt, WriteOffAmt);
		this.doc = doc;
		this.services = doc.getServices();
		m_StandardDiscountAccount = DiscountAccount;
		m_DiscountAmt = DiscountAmt;
		m_WriteOffAccount = WriteOffAccount;
		m_WriteOffAmt = WriteOffAmt;
		this.isDiscountExpense = isDiscountExpense;
	}

	private PostingException newPostingException()
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

	private MoneySourceAndAcct getInvoiceGrandTotalAmt(final AcctSchema as)
	{
		final I_Fact_Acct invoiceGrandTotalFact = getInvoiceGrandTotalFact();
		final BigDecimal amtSourceDr = invoiceGrandTotalFact.getAmtAcctDr();
		if (amtSourceDr.signum() != 0)
		{
			return extractMoneySourceAndAcctDebit(invoiceGrandTotalFact, as);
		}
		else
		{
			return extractMoneySourceAndAcctCredit(invoiceGrandTotalFact, as);
		}
	}

	private List<I_Fact_Acct> getInvoiceTaxFacts()
	{
		return _invoiceTaxFacts;
	}

	public boolean hasInvoiceTaxFacts()
	{
		return !_invoiceTaxFacts.isEmpty();
	}

	private final MAccount getTaxDiscountAcct(final AcctSchema as, final int taxId)
	{
		final MAccount discountAccount = Doc_AllocationHdr.getTaxDiscountAccount(taxId, isDiscountExpense, as.getId());
		if (discountAccount != null)
		{
			return discountAccount;
		}
		return m_StandardDiscountAccount;
	}

	/**
	 * Create Accounting Entries
	 */
	public List<Fact> createEntries(
			final AcctSchema as,
			final DocLine_Allocation line)
	{
		// If there are no tax facts, there is no need to do tax correction
		if (!hasInvoiceTaxFacts())
		{
			return ImmutableList.of();
		}

		//
		// Get total index (the Receivables/Liabilities line)
		final MoneySourceAndAcct invoiceGrandTotalAmt = getInvoiceGrandTotalAmt(as);

		final ImmutableList.Builder<Fact> result = ImmutableList.builder();

		//
		// Iterate the invoice tax facts
		final CurrencyPrecision acctPrecision = as.getStandardPrecision();
		for (final I_Fact_Acct taxFactAcct : getInvoiceTaxFacts())
		{
			//
			// Get the C_Tax_ID
			final int taxId = taxFactAcct.getC_Tax_ID();
			if (taxId <= 0)
			{
				// shall not happen
				throw newPostingException()
						.setAcctSchema(as)
						.setFactLine(taxFactAcct)
						.setDocLine(line)
						.setDetailMessage("No tax found");
			}

			//
			// Get the Tax Account
			final MAccount taxAcct = services.getAccount(taxFactAcct);
			if (taxAcct == null || taxAcct.getC_ValidCombination_ID() <= 0)
			{
				throw newPostingException()
						.setAcctSchema(as)
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
					final MoneySourceAndAcct invoiceTaxAmt = extractMoneySourceAndAcctDebit(taxFactAcct, as);
					final MoneySourceAndAcct amountCMAdjusted = calcAmount(
							invoiceTaxAmt,
							invoiceGrandTotalAmt,
							m_DiscountAmt,
							acctPrecision,
							line.isCreditMemoInvoice());
					if (amountCMAdjusted.signum() != 0)
					{
						final String description = "Invoice TaxAmt=" + invoiceTaxAmt + ", GrandTotal=" + invoiceGrandTotalAmt + ", Alloc Discount=" + m_DiscountAmt;

						final Fact fact = createEmptyFact(as);
						result.add(fact);

						// Discount expense
						if (isDiscountExpense)
						{
							// DR
							fact.createLine()
									.setDocLine(line)
									.setAccount(discountAcct)
									.setAmt(amountCMAdjusted, null)
									.setC_Tax_ID(taxId)
									.additionalDescription(description)
									.buildAndAdd();

							// CR
							fact.createLine()
									.setDocLine(line)
									.setAccount(taxAcct)
									.setAmt(null, amountCMAdjusted)
									.setC_Tax_ID(taxId)
									.alsoAddZeroLine()
									.additionalDescription(description)
									.buildAndAdd();
						}
						// Discount revenue
						else
						{
							// DR
							fact.createLine()
									.setDocLine(line)
									.setAccount(discountAcct)
									.setAmt(amountCMAdjusted.negate(), null)
									.setC_Tax_ID(taxId)
									.additionalDescription(description)
									.buildAndAdd();

							// CR
							fact.createLine()
									.setDocLine(line)
									.setAccount(taxAcct)
									.setAmt(null, amountCMAdjusted.negate())
									.setC_Tax_ID(taxId)
									.alsoAddZeroLine()
									.additionalDescription(description)
									.buildAndAdd();

						}

					}
				}
				// Original Tax is CR - need to correct it DR
				else
				{
					final MoneySourceAndAcct invoiceTaxAmt = extractMoneySourceAndAcctCredit(taxFactAcct, as);
					final MoneySourceAndAcct amountCMAdjusted = calcAmount(
							invoiceTaxAmt,
							invoiceGrandTotalAmt,
							m_DiscountAmt,
							acctPrecision,
							line.isCreditMemoInvoice());
					if (amountCMAdjusted.signum() != 0)
					{
						final String description = "Invoice TaxAmt=" + invoiceTaxAmt + ", GrandTotal=" + invoiceGrandTotalAmt + ", Alloc Discount=" + m_DiscountAmt;

						final Fact fact = createEmptyFact(as);
						result.add(fact);

						// Discount expense
						if (isDiscountExpense)
						{
							// DR
							fact.createLine()
									.setDocLine(line)
									.setAccount(taxAcct)
									.setAmt(amountCMAdjusted, null)
									.setC_Tax_ID(taxId)
									.alsoAddZeroLine()
									.additionalDescription(description)
									.buildAndAdd();

							// CR
							fact.createLine()
									.setDocLine(line)
									.setAccount(discountAcct)
									.setAmt(null, amountCMAdjusted)
									.setC_Tax_ID(taxId)
									.additionalDescription(description)
									.buildAndAdd();

						}
						// Discount revenue
						else
						{
							//DR
							fact.createLine()
									.setDocLine(line)
									.setAccount(taxAcct)
									.setAmt(amountCMAdjusted.negate(), null)
									.setC_Tax_ID(taxId)
									.alsoAddZeroLine()
									.additionalDescription(description)
									.buildAndAdd();

							// CR
							fact.createLine()
									.setDocLine(line)
									.setAccount(discountAcct)
									.setAmt(null, amountCMAdjusted.negate())
									.setC_Tax_ID(taxId)
									.additionalDescription(description)
									.buildAndAdd();

						}
					}
				}
			}                // Discount

			//
			// WriteOff Amount
			if (m_WriteOffAmt.signum() != 0)
			{
				// Original Tax is DR - need to correct it CR
				if (taxFactAcct.getAmtSourceDr().signum() != 0)
				{
					final MoneySourceAndAcct invoiceTaxAmt = extractMoneySourceAndAcctDebit(taxFactAcct, as);
					final MoneySourceAndAcct amountCMAdjusted = calcAmount(invoiceTaxAmt, invoiceGrandTotalAmt, m_WriteOffAmt, acctPrecision, line.isCreditMemoInvoice());
					if (amountCMAdjusted.signum() != 0)
					{
						final String description = "Invoice TaxAmt=" + invoiceTaxAmt + ", GrandTotal=" + invoiceGrandTotalAmt + ", Alloc WriteOff=" + m_WriteOffAmt;

						final Fact fact = createEmptyFact(as);
						result.add(fact);

						//DR
						fact.createLine()
								.setDocLine(line)
								.setAccount(m_WriteOffAccount)
								.setAmt(amountCMAdjusted, null)
								.setC_Tax_ID(taxId)
								.additionalDescription(description)
								.buildAndAdd();

						// CR
						fact.createLine()
								.setDocLine(line)
								.setAccount(taxAcct)
								.setAmt(null, amountCMAdjusted)
								.setC_Tax_ID(taxId)
								.alsoAddZeroLine()
								.additionalDescription(description)
								.buildAndAdd();
					}
				}
				// Original Tax is CR - need to correct it DR
				else
				{
					final MoneySourceAndAcct invoiceTaxAmt = extractMoneySourceAndAcctCredit(taxFactAcct, as);
					final MoneySourceAndAcct amountCMAdjusted = calcAmount(invoiceTaxAmt, invoiceGrandTotalAmt, m_WriteOffAmt, acctPrecision, line.isCreditMemoInvoice());
					if (amountCMAdjusted.signum() != 0)
					{
						final String description = "Invoice TaxAmt=" + invoiceTaxAmt + ", GrandTotal=" + invoiceGrandTotalAmt + ", Alloc WriteOff=" + m_WriteOffAmt;

						final Fact fact = createEmptyFact(as);
						result.add(fact);

						// DR
						fact.createLine()
								.setDocLine(line)
								.setAccount(taxAcct)
								.setAmt(amountCMAdjusted, null)
								.setC_Tax_ID(taxId)
								.alsoAddZeroLine()
								.additionalDescription(description)
								.buildAndAdd();

						// CR
						fact.createLine()
								.setDocLine(line)
								.setAccount(m_WriteOffAccount)
								.setAmt(null, amountCMAdjusted)
								.setC_Tax_ID(taxId)
								.additionalDescription(description)
								.buildAndAdd();
					}
				}
			}                // WriteOff
		}                // for all lines

		return result.build();
	}    // createEntries

	private Fact createEmptyFact(final AcctSchema as)
	{
		return new Fact(doc, as, PostingType.Actual);
	}

	/**
	 * Calculate the tax amount part.
	 *
	 * @return (taxAmt / invoice grand total amount) * taxAmt
	 */
	private MoneySourceAndAcct calcAmount(
			final MoneySourceAndAcct taxAmt,
			final MoneySourceAndAcct invoiceGrandTotalAmt,
			final Money discountAmt,
			final CurrencyPrecision acctPrecision,
			final boolean isCreditMemoInvoice)
	{
		if (taxAmt.signum() == 0
				|| invoiceGrandTotalAmt.signum() == 0
				|| discountAmt.signum() == 0)
		{
			return taxAmt.toZero();
		}

		final CurrencyPrecision sourcePrecision = services.getCurrencyStandardPrecision(taxAmt.getSourceCurrencyId());

		return taxAmt.divide(invoiceGrandTotalAmt, CurrencyPrecision.TEN)
				.multiply(discountAmt.toBigDecimal())
				.roundIfNeeded(sourcePrecision, acctPrecision)
				.negateIf(isCreditMemoInvoice);
	}

	private static MoneySourceAndAcct extractMoneySourceAndAcctDebit(final I_Fact_Acct factAcct, final AcctSchema as)
	{
		return extractMoneySourceAndAcct(factAcct, as, true);
	}

	private static MoneySourceAndAcct extractMoneySourceAndAcctCredit(final I_Fact_Acct factAcct, final AcctSchema as)
	{
		return extractMoneySourceAndAcct(factAcct, as, false);
	}

	private static MoneySourceAndAcct extractMoneySourceAndAcct(final I_Fact_Acct factAcct, final AcctSchema as, boolean isDebit)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(factAcct.getC_AcctSchema_ID());
		Check.assumeEquals(acctSchemaId, as.getId(), "acctSchema");

		final CurrencyId sourceCurrencyId = CurrencyId.ofRepoId(factAcct.getC_Currency_ID());
		final CurrencyId acctCurrencyId = as.getCurrencyId();
		final Money source;
		final Money acct;
		if (isDebit)
		{
			source = Money.of(factAcct.getAmtSourceDr(), sourceCurrencyId);
			acct = Money.of(factAcct.getAmtAcctDr(), acctCurrencyId);
		}
		else
		{
			source = Money.of(factAcct.getAmtSourceCr(), sourceCurrencyId);
			acct = Money.of(factAcct.getAmtAcctCr(), acctCurrencyId);
		}

		return MoneySourceAndAcct.ofSourceAndAcct(source, acct);
	}
}    // Doc_AllocationTax

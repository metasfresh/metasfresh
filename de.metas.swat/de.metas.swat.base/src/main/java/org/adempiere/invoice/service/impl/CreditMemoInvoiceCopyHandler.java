package org.adempiere.invoice.service.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceCreditContext;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.process.DocAction;
import org.compiere.util.Env;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.document.IDocCopyHandler;
import de.metas.document.engine.IDocActionBL;

/**
 *
 * Note: This class is currently instantiated and called directly from BLs in this package.<br>
 * Please move this class to <code>org.adempiere.invoice.spi.impl</code> as soon as it is registered at and invoked via {@link de.metas.document.ICopyHandlerBL}.
 * <p>
 * Also see the javadoc of {@link IInvoiceBL#creditInvoice(de.metas.adempiere.model.I_C_Invoice, IInvoiceCreditContext)}.
 */
public class CreditMemoInvoiceCopyHandler implements IDocCopyHandler<I_C_Invoice, I_C_InvoiceLine>
{
	// private final Properties ctx;
	private final IInvoiceCreditContext creditCtx;
	private final BigDecimal openAmt;
	private final String trxName;

	public CreditMemoInvoiceCopyHandler(final Properties ctx,
			final IInvoiceCreditContext creditCtx,
			final BigDecimal openAmt,
			final String trxName)
	{
		super();

		// this.ctx = ctx;
		this.creditCtx = creditCtx;
		this.openAmt = openAmt;
		this.trxName = trxName;
	}

	@Override
	public void copyPreliminaryValues(final I_C_Invoice from, final I_C_Invoice to)
	{
		// do nothing. this is already done by the default copy handler
	}

	@Override
	public void copyValues(final I_C_Invoice from, final I_C_Invoice to)
	{
		final de.metas.adempiere.model.I_C_Invoice invoice = InterfaceWrapperHelper.create(from, de.metas.adempiere.model.I_C_Invoice.class);
		final de.metas.adempiere.model.I_C_Invoice creditMemo = InterfaceWrapperHelper.create(to, de.metas.adempiere.model.I_C_Invoice.class);

		if (creditCtx.isReferenceInvoice())
		{
			creditMemo.setRef_Invoice_ID(invoice.getC_Invoice_ID());
		}

		creditMemo.setIsCreditedInvoiceReinvoicable(creditCtx.isCreditedInvoiceReinvoicable()); // task 08927

		handlePartialRequests(invoice, creditMemo);

		completeAndAllocateCreditMemo(invoice, creditMemo);
	}

	@Override
	public CreditMemoInvoiceLineCopyHandler getDocLineCopyHandler()
	{
		return CreditMemoInvoiceLineCopyHandler.getInstance();
	}

	private void handlePartialRequests(final de.metas.adempiere.model.I_C_Invoice invoice,
			final de.metas.adempiere.model.I_C_Invoice creditMemo)
	{
		final BigDecimal allocatedAmt = Services.get(IAllocationDAO.class).retrieveAllocatedAmt(invoice);
		if (allocatedAmt == null || allocatedAmt.signum() == 0)
		{
			// skip non-partial lines (a line is partially allocated if the allocated amount is not null or 0)
			return;
		}

		// task 08927: we won't be able to re-invoice partial credit memos because we will set the invoiceLines' QtyInvoiced to 1,
		// so we won't know the exact qtys to re-invoice.
		creditMemo.setIsCreditedInvoiceReinvoicable(false);

		// CreditMemo lines shall be including tax. This makes our life easier further below
		creditMemo.setIsTaxIncluded(true);

		// get our currency precision and the smallest possible amount (in most currencies this is 0.01)
		final int precision = invoice.getC_Currency().getStdPrecision();
		final BigDecimal smallestAmtInCurrency = BigDecimal.ONE.setScale(precision, BigDecimal.ROUND_HALF_UP).divide(BigDecimal.TEN.pow(precision));

		// Compute the factor we can use to get the credit memo amounts from their respective invoice amounts.
		// Note that by rounding to "floor", the rounded value won't ever be greater than the "correct" value
		final BigDecimal openfraction = openAmt
				// gh #448: make sure to choose openfraction's scale such that it's length after the decimal point (it is generally <=1) is in the same order as the size of the numbers which we divide.
				// this is important to guarantee that the rounding error is not bigger than 'smallestAmtInCurrency'
				.setScale(openAmt.precision() + invoice.getGrandTotal().precision(), BigDecimal.ROUND_HALF_UP)
				.divide(invoice.getGrandTotal(), RoundingMode.FLOOR);

		final IInvoiceDAO invoicePA = Services.get(IInvoiceDAO.class);

		//
		// Get the C_InvoiceTax records of 'invoice' taxes, ordered by tax rate, with the biggest tax rate first
		final List<I_C_InvoiceTax> invoiceTaxes = invoicePA.retrieveTaxes(invoice);
		Collections.sort(invoiceTaxes, new Comparator<I_C_InvoiceTax>()
		{
			@Override
			public int compare(final I_C_InvoiceTax o1, final I_C_InvoiceTax o2)
			{
				return o1.getC_Tax().getRate().compareTo(o2.getC_Tax().getRate()) * -1;
			}
		});

		BigDecimal creditMemoGrandTotal = BigDecimal.ZERO;
		final Map<I_C_InvoiceTax, BigDecimal> newTaxAmounts = new IdentityHashMap<I_C_InvoiceTax, BigDecimal>();

		//
		// For every I_C_InvoiceTax of 'invoice', we get the fraction of the tax amount that shall be credited
		final Map<Integer, I_C_InvoiceTax> taxId2Invoicetax = new HashMap<Integer, I_C_InvoiceTax>();
		for (final I_C_InvoiceTax invoiceTax : invoiceTaxes)
		{
			final BigDecimal taxGrossAmt = invoiceTax.getTaxBaseAmt().add(invoiceTax.getTaxAmt());

			// Note that once again, we round to "floor", so the rounded value won't be greater than the "correct" value
			// Also note that if 'creditTaxGrossAmt' is less than the "correct" value, the difference is never bigger than than 'smallestAmtInCurrency'.
			final BigDecimal creditTaxGrossAmt = taxGrossAmt.multiply(openfraction).setScale(precision, RoundingMode.FLOOR);
			creditMemoGrandTotal = creditMemoGrandTotal.add(creditTaxGrossAmt);

			newTaxAmounts.put(invoiceTax, creditTaxGrossAmt);
			taxId2Invoicetax.put(invoiceTax.getC_Tax_ID(), invoiceTax);
		}

		//
		// Now we check for rounding errors and correct the amounts if necessary.
		// Note that our amounts can't be too big but only too small (by one 'smallestAmt' for each I_C_InvoiceTax).
		// Also note that due to the ordering of 'invoiceTaxes' we correct the invoiceTax with the biggest rate first
		for (final I_C_InvoiceTax invoiceTax : invoiceTaxes)
		{
			if (creditMemoGrandTotal.compareTo(openAmt) == 0)
			{
				break;
			}
			if (creditMemoGrandTotal.compareTo(openAmt) < 0)
			{
				newTaxAmounts.put(invoiceTax,
						newTaxAmounts.get(invoiceTax).add(smallestAmtInCurrency));
				creditMemoGrandTotal = creditMemoGrandTotal.add(smallestAmtInCurrency);
			}
			else
			{
				Check.errorIf(true, "On evaluating {}, we have creditMemoGrandTotal={} and openAmt={}", invoiceTax, creditMemoGrandTotal, openAmt);
			}
		}

		// gh #448: in the past this error occurred when openfraction's scale was two low.
		Check.errorIf(creditMemoGrandTotal.compareTo(openAmt) != 0, "creditMemoGrandTotal={} is different from openAmt={}; openfraction={}", creditMemoGrandTotal, openAmt, openfraction);

		//
		// Now that we have computed the tax values where we need to end up with our credit memo,
		// We do basically the same on invoice line level. The credit memo lines were copied from the invoice lines, so they still contain the original amounts which we can use
		final Map<I_C_InvoiceTax, List<I_C_InvoiceLine>> tax2lines = new IdentityHashMap<I_C_InvoiceTax, List<I_C_InvoiceLine>>();

		final List<de.metas.adempiere.model.I_C_InvoiceLine> lines = invoicePA.retrieveLines(creditMemo, trxName);
		for (final I_C_InvoiceLine creditMemoLine : lines)
		{
			if (creditMemoLine.isDescription())
			{
				continue; // not changing description lines
			}
			final I_C_InvoiceTax invoiceTax = taxId2Invoicetax.get(creditMemoLine.getC_Tax_ID());
			List<I_C_InvoiceLine> linesForInvoiceTax = tax2lines.get(invoiceTax);
			if (linesForInvoiceTax == null)
			{
				linesForInvoiceTax = new ArrayList<I_C_InvoiceLine>();
				tax2lines.put(invoiceTax, linesForInvoiceTax);

			}
			linesForInvoiceTax.add(creditMemoLine);
		}

		final Map<I_C_InvoiceLine, BigDecimal> line2newLineGrossAmt = new IdentityHashMap<I_C_InvoiceLine, BigDecimal>();
		for (final I_C_InvoiceTax invoiceTax : invoiceTaxes)
		{
			BigDecimal sumPerTax = BigDecimal.ZERO;
			for (final I_C_InvoiceLine creditMemoLine : tax2lines.get(invoiceTax))
			{
				final BigDecimal newLineNetAmt = creditMemoLine.getLineNetAmt().multiply(openfraction);
				final BigDecimal newLineGrossAmt;
				if (invoiceTax.isTaxIncluded())
				{
					newLineGrossAmt = newLineNetAmt.setScale(precision, RoundingMode.FLOOR);
				}
				else
				{
					newLineGrossAmt = newLineNetAmt
							.multiply(invoiceTax.getC_Tax().getRate()
									.add(Env.ONEHUNDRED)
									.divide(Env.ONEHUNDRED))
							.setScale(precision, RoundingMode.FLOOR);
				}
				line2newLineGrossAmt.put(creditMemoLine, newLineGrossAmt);
				sumPerTax = sumPerTax.add(newLineGrossAmt);
			}

			for (final I_C_InvoiceLine creditMemoLine : tax2lines.get(invoiceTax))
			{
				final BigDecimal targetSum = newTaxAmounts.get(invoiceTax);
				if (sumPerTax.compareTo(targetSum) == 0)
				{
					break;
				}
				if (sumPerTax.compareTo(targetSum) < 0)
				{
					line2newLineGrossAmt.put(creditMemoLine, line2newLineGrossAmt.get(creditMemoLine).add(smallestAmtInCurrency));
					sumPerTax = sumPerTax.add(smallestAmtInCurrency);
				}
				else
				{
					Check.errorIf(true, "invoiceTax {} has has targetSum={}, but the credit memo lines for this tax have sumPerTax={} (difference may not be more than {})",
							invoiceTax, targetSum, sumPerTax, smallestAmtInCurrency);
				}
			}
		}

		for (final I_C_InvoiceLine creditMemoLine : lines)
		{
			creditMemoLine.setQtyEntered(BigDecimal.ONE);
			creditMemoLine.setQtyInvoiced(BigDecimal.ONE);
			creditMemoLine.setPriceEntered(line2newLineGrossAmt.get(creditMemoLine));
			creditMemoLine.setPriceActual(line2newLineGrossAmt.get(creditMemoLine));

			// 07090: setting priceUOM to 0, because we set the Qty to 1, and the priceUOM only makes sense in the context of the original Qty (but this is a partial-ammount credit memo)
			InterfaceWrapperHelper.create(creditMemoLine, de.metas.adempiere.model.I_C_InvoiceLine.class).setPrice_UOM_ID(0);

			InterfaceWrapperHelper.save(creditMemoLine);
		}
	}

	private void completeAndAllocateCreditMemo(final de.metas.adempiere.model.I_C_Invoice invoice, final de.metas.adempiere.model.I_C_Invoice creditMemo)
	{
		// task 08906: link the credit memo with the parent invoice
		if (creditCtx.isReferenceInvoice())
		{
			invoice.setRef_CreditMemo_ID(creditMemo.getC_Invoice_ID());
			InterfaceWrapperHelper.save(invoice);
		}

		if (!creditCtx.completeAndAllocate())
		{
			Services.get(IDocActionBL.class).processEx(creditMemo, DocAction.ACTION_Prepare, DocAction.STATUS_InProgress);
			Check.assume(creditMemo.getGrandTotal().compareTo(openAmt) == 0, "{} has GrandTotal={}", creditMemo, openAmt);

			// nothing left to do
			return;
		}

		// make sure the grand total of the credit memo equals the open AMT of the invoice
		Check.assume(creditMemo.getGrandTotal().compareTo(openAmt) == 0, "{} has GrandTotal={}", creditMemo, openAmt);

		Services.get(IDocActionBL.class).processEx(creditMemo, DocAction.ACTION_Complete, DocAction.STATUS_Completed);

		// allocate invoice against credit memo will be done in the model validator of creditmemo complete
	}

	/**
	 * Returns the header item class, i.e. <code>I_C_Invoice</code>.
	 */
	@Override
	public Class<I_C_Invoice> getSupportedItemsClass()
	{
		return I_C_Invoice.class;
	}
}

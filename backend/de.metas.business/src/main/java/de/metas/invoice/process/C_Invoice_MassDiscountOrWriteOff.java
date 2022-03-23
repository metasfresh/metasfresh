package de.metas.invoice.process;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.base.Stopwatch;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationBL.InvoiceDiscountAndWriteOffRequest;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.model.IQuery;
import org.compiere.util.TrxRunnableAdapter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Process to batch-create discount allocations for invoices that only have a very small remaining open amount left.<br>
 * This process is meant to be used in AD_Scheduler.
 *
 * @implNote See task 09135.
 */
public class C_Invoice_MassDiscountOrWriteOff extends JavaProcess
{
	public static final AdMessageKey MSG_AllocationLinesCreated = AdMessageKey.of("MSG_AllocationLinesCreated");

	private static final String PARAM_OpenAmt = "OpenAmt";
	private static final String PARAM_DateInvoiced = I_C_Invoice.COLUMNNAME_DateInvoiced;
	private static final String PARAM_IsSOTrx = I_C_Invoice.COLUMNNAME_IsSOTrx;

	// services
	private final transient IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	private final transient IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	// parameters
	private BigDecimal p_OpenAmt = null;
	private Timestamp p_DateInvoicedFrom = null;
	private Timestamp p_DateInvoicedTo = null;
	private SOTrx p_SOTrx = null;

	// status
	private final AtomicInteger counterProcessed = new AtomicInteger(0);
	private final AtomicInteger counterSkipped = new AtomicInteger(0);

	@Override
	protected void prepare()
	{
		final IRangeAwareParams params = getParameterAsIParams();
		p_OpenAmt = params.getParameterAsBigDecimal(PARAM_OpenAmt);
		p_DateInvoicedFrom = params.getParameterAsTimestamp(PARAM_DateInvoiced);
		p_DateInvoicedTo = params.getParameter_ToAsTimestamp(PARAM_DateInvoiced);
		p_SOTrx = SOTrx.ofNullableBoolean(params.getParameterAsBoolean(PARAM_IsSOTrx, null));
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		if (p_OpenAmt == null)
		{
			throw new FillMandatoryException(PARAM_OpenAmt);
		}

		final Iterator<I_C_Invoice> iterator = retrieveInvoices();
		while (iterator.hasNext())
		{
			final I_C_Invoice invoice = iterator.next();
			invoiceDiscount(invoice);
		}

		addLog("Discounted " + counterProcessed.get() + " invoices, Skipped " + counterSkipped.intValue() + " invoices. Took " + stopwatch);
		return msgBL.getMsg(getCtx(), MSG_AllocationLinesCreated, new Object[] { counterProcessed.intValue() });
	}

	private void invoiceDiscount(@NonNull final I_C_Invoice invoice)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());
		final Money invoiceOpenAmt = Money.of(allocationDAO.retrieveOpenAmt(invoice, true), currencyId);
		if (invoiceOpenAmt.signum() == 0)
		{
			addLog("Skip C_Invoice_ID=" + invoice.getC_Invoice_ID() + ": " + "Has OpenAmt=0 but IsPaid=N.");
			counterSkipped.incrementAndGet();
			return;
		}

		// skip the invoice if there is nothing allocated yet! We only want to complete *partial* allocations
		final BigDecimal allocatedAmt = allocationDAO.retrieveAllocatedAmt(invoice);
		if (allocatedAmt == null || allocatedAmt.signum() == 0)
		{
			addLog("Skip C_Invoice_ID=" + invoice.getC_Invoice_ID() + ": " + "Has allocatedAmt=0.");
			counterSkipped.incrementAndGet();
			return;
		}

		if (invoiceOpenAmt.toBigDecimal().abs().compareTo(p_OpenAmt.abs()) > 0)
		{
			counterSkipped.incrementAndGet();
			return;
		}

		final Money discountAmt = invoice.isSOTrx()
				? invoiceOpenAmt
				: invoiceOpenAmt.negate();

		trxManager.assertThreadInheritedTrxNotExists();
		trxManager.runInThreadInheritedTrx(new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName_NOTUSED)
			{
				allocationBL.invoiceDiscountAndWriteOff(
						InvoiceDiscountAndWriteOffRequest.builder()
								.invoice(invoice)
								.useInvoiceDate(true)
								.discountAmt(discountAmt)
								.description(getProcessInfo().getTitle())
								.build());

				// Make sure it was fully allocated
				InterfaceWrapperHelper.refresh(invoice);
				Check.errorIf(!invoice.isPaid(), "C_Invoice {} still has IsPaid='N' after having allocating discountAmt={}", invoice, discountAmt);

				// Log the success and increase the counter
				addLog("@Processed@: @C_Invoice_ID@ " + invoice.getDocumentNo() + "; @DiscountAmt@=" + discountAmt);
				counterProcessed.incrementAndGet();
			}

			@Override
			public boolean doCatch(final Throwable e)
			{
				final String errmsg = "@Error@: @C_Invoice_ID@ " + invoice.getDocumentNo() + ": " + e.getLocalizedMessage();
				addLog(errmsg);
				log.error(errmsg, e);
				return true; // do rollback
			}
		});
	}

	private Iterator<I_C_Invoice> retrieveInvoices()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		//
		// Create the selection which we might need to update
		// note that selecting all unpaid and then skipping all whose open amount is > p_OpenAmt is acceptable performance-wise,
		// at least when we worked with 14.000 invoices and the client was running remote, over an internet connection
		final IQueryBuilder<I_C_Invoice> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_AD_Client_ID, getClientId())
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_IsPaid, false); // not already fully allocated

		if (!getProcessInfo().isInvokedByScheduler())
		{
			// user selection..if any. if none, then process all
			final IQueryFilter<I_C_Invoice> userSelectionFilter = getProcessInfo().getQueryFilterOrElseTrue();
			queryBuilder.filter(userSelectionFilter);
		}

		if (p_SOTrx != null)
		{
			queryBuilder.addEqualsFilter(PARAM_IsSOTrx, p_SOTrx.toBoolean());
		}
		if (p_DateInvoicedFrom != null)
		{
			queryBuilder.addCompareFilter(PARAM_DateInvoiced, Operator.GREATER_OR_EQUAL, p_DateInvoicedFrom);
		}
		if (p_DateInvoicedTo != null)
		{
			queryBuilder.addCompareFilter(PARAM_DateInvoiced, Operator.LESS_OR_EQUAL, p_DateInvoicedTo);
		}

		final IQuery<I_C_Invoice> query = queryBuilder
				.orderBy(I_C_Invoice.COLUMNNAME_C_Invoice_ID)
				.create();
		addLog("Using query: " + query);

		final int count = query.count();
		if (count > 0)
		{
			final Iterator<I_C_Invoice> iterator = query.iterate(I_C_Invoice.class);
			addLog("Found " + count + " invoices to evaluate. Took " + stopwatch);
			return iterator;
		}
		else
		{
			addLog("No invoices found. Took " + stopwatch);
			return Collections.emptyIterator();
		}
	}
}

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.process.JavaProcess;

/**
 * 
 * Process to batch-create discount allocations for invoices that only have a very small remaining open amount left.<br>
 * See task 09135.
 */
public class C_Invoice_DiscountAllocation_Process extends JavaProcess
{
	public static final String MSG_AllocationLinesCreated = "MSG_AllocationLinesCreated";

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
	private Boolean p_isSOTrx = null;

	@Override
	protected void prepare()
	{
		final IRangeAwareParams params = getParameterAsIParams();
		p_OpenAmt = params.getParameterAsBigDecimal(PARAM_OpenAmt);
		p_DateInvoicedFrom = params.getParameterAsTimestamp(PARAM_DateInvoiced);
		p_DateInvoicedTo = params.getParameter_ToAsTimestamp(PARAM_DateInvoiced);
		p_isSOTrx = params.getParameterAsBool(PARAM_IsSOTrx);
	}

	@Override
	protected String doIt() throws Exception
	{

		final Iterator<I_C_Invoice> iterator = createIterator();

		if (!iterator.hasNext())
		{
			addLog("@NoSelection@");
		}

		@SuppressWarnings("unused")
		int counterSkipped = 0;
		int counterProcessed = 0;

		while (iterator.hasNext())
		{
			final I_C_Invoice invoice = iterator.next();

			final BigDecimal invoiceOpenAmt = allocationDAO.retrieveOpenAmt(invoice,
					true); // creditMemoAdjusted = false

			if (invoiceOpenAmt.signum() <= 0)
			{
				counterSkipped++;
				continue;
			}

			if (invoiceOpenAmt.compareTo(p_OpenAmt) > 0)
			{
				counterSkipped++;
				continue;
			}

			final BigDecimal discountAmount = invoice.isSOTrx()
					? invoiceOpenAmt
					: invoiceOpenAmt.negate();

			trxManager.run(new TrxRunnableAdapter()
			{
				@Override
				public void run(final String localTrxName) throws Exception
				{
					final boolean ignoreIfNotHandled = true;
					InterfaceWrapperHelper.setTrxName(invoice, localTrxName, ignoreIfNotHandled);

					//@formatter:off
					final I_C_AllocationHdr allocationHdr = allocationBL.newBuilder(new PlainContextAware(getCtx(), localTrxName))
							.setAD_Org_ID(invoice.getAD_Org_ID())
							.setC_Currency_ID(invoice.getC_Currency_ID())
							.setDateAcct(invoice.getDateAcct())
							.setDateTrx(invoice.getDateInvoiced())
							.addLine()
								.setAD_Org_ID(invoice.getAD_Org_ID())
								.setC_BPartner_ID(invoice.getC_BPartner_ID())
								.setC_Invoice_ID(invoice.getC_Invoice_ID())
								.setDiscountAmt(discountAmount)
								.setWriteOffAmt(BigDecimal.ZERO)
							.lineDone()
							.create(true); // complete=true
					//@formatter:on

					InterfaceWrapperHelper.refresh(invoice);
					Check.errorIf(!invoice.isPaid(), "C_Invoice {} still has IsPaid='N' after having created {} with discountAmt={}", invoice, allocationHdr, discountAmount);
				}

				@Override
				public boolean doCatch(final Throwable e) throws Throwable
				{
					addLog("@Error@: @C_Invoice_ID@ " + invoice.getDocumentNo() + ": " + e.getMessage());
					return true; // do rollback
				}

			});

			addLog("@Processed@: @C_Invoice_ID@ " + invoice.getDocumentNo() + "; @DiscountAmt@=" + discountAmount);
			counterProcessed++;
		}

		final String message = msgBL.getMsg(getCtx(), MSG_AllocationLinesCreated, new Object[] { counterProcessed });
		return message;
	}

	private Iterator<I_C_Invoice> createIterator()
	{
	// user selection..if any
		final IQueryFilter<I_C_Invoice> userSelectionFilter = getProcessInfo().getQueryFilter();

		//
		// Create the selection which we might need to update
		// note that selecting all unpaid and then skipping all whose open amount is > p_OpenAmt is acceptable performance-wise, 
		// at least when we worked with 14.000 invoices and the client was running remote, over an internet connection
		final IQueryBuilder<I_C_Invoice> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice.class, this)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_IsPaid, false); // this is a no-brainer

		queryBuilder.filter(userSelectionFilter);

		if (p_isSOTrx != null)
		{
			queryBuilder.addEqualsFilter(PARAM_IsSOTrx, p_isSOTrx);
		}
		if (p_DateInvoicedFrom != null)
		{
			queryBuilder.addCompareFilter(PARAM_DateInvoiced, Operator.GREATER_OR_EQUAL, p_DateInvoicedFrom);
		}
		if (p_DateInvoicedTo != null)
		{
			queryBuilder.addCompareFilter(PARAM_DateInvoiced, Operator.LESS_OR_EQUAL, p_DateInvoicedTo);
		}

		return queryBuilder
				.orderBy().addColumn(I_C_Invoice.COLUMNNAME_C_Invoice_ID)
				.endOrderBy()
				.create()
				.iterate(I_C_Invoice.class);
	}
}

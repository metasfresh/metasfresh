package de.metas.fresh.invoice.migrateMatchInv.process;

/*
 * #%L
 * de.metas.fresh.base
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.IProcessor;
import org.adempiere.util.Services;
import org.adempiere.util.lang.MutableBigDecimal;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.Query;
import org.compiere.process.DocAction;

import de.metas.printing.esb.base.util.Check;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

public class M_MatchInv_CreateMissing extends JavaProcess
{
	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	// Parameters
	private static final String PARAM_WhereClause = "WhereClause";
	private String p_WhereClause = null;

	// Status
	private final MatchInvHelper matchInvHelper = new MatchInvHelper();
	private static final String COUNTER_MATCHED = "Counter-Matched";
	private static final String COUNTER_NOTHING_TO_BE_DONE = "Counter-NothingToBeDone";
	private static final String COUNTER_NOTHING_TO_BE_DONE_NoIOL = COUNTER_NOTHING_TO_BE_DONE + "-NoInOutLine";
	private static final String COUNTER_NOTHING_TO_BE_DONE_NoIOLWithUnmatchedQty = COUNTER_NOTHING_TO_BE_DONE + "-NoInOutLineWithUnmatchedQty";

	// private final Map<String, Integer> counters = new LinkedHashMap<>();

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (PARAM_WhereClause.equals(name))
			{
				p_WhereClause = para.getParameterAsString();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		// theses are the invoice lines we will deal with
		final Iterator<I_C_InvoiceLine> invoiceLines = retrieveAllInvoiceLines();

		return matchInvHelper.process(getCtx(), invoiceLines, new IProcessor<I_C_InvoiceLine>()
		{

			@Override
			public void process(I_C_InvoiceLine il)
			{
				createMissingMatchInvs(il);
			}
		});
	}

	private Iterator<I_C_InvoiceLine> retrieveAllInvoiceLines()
	{
		final IQueryBuilder<I_C_InvoiceLine> queryBuilder = queryBL.createQueryBuilder(I_C_InvoiceLine.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter();

		// Filter only relevant invoices
		{
			final IQuery<I_C_Invoice> invoiceQuery = queryBL.createQueryBuilder(I_C_Invoice.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_C_Invoice.COLUMN_DocStatus, DocAction.STATUS_Completed, DocAction.STATUS_Closed)
					.create();

			queryBuilder.addInSubQueryFilter(I_C_InvoiceLine.COLUMN_C_Invoice_ID,
					I_C_Invoice.COLUMN_C_Invoice_ID,
					invoiceQuery);
		}

		// Skip invoices that already have M_MatchInv records
		{
			final IQuery<I_M_MatchInv> matchInvQuery = queryBL.createQueryBuilder(I_M_MatchInv.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
					.addOnlyActiveRecordsFilter()
					.create();

			queryBuilder.addNotInSubQueryFilter(I_C_InvoiceLine.COLUMN_C_InvoiceLine_ID,
					I_M_MatchInv.COLUMN_C_InvoiceLine_ID,
					matchInvQuery);
		}

		// Skip description lines
		queryBuilder.addEqualsFilter(I_C_InvoiceLine.COLUMN_IsDescription, false);

		// Add custom WHERE clause if any
		if (!Check.isEmpty(p_WhereClause, true))
		{
			queryBuilder.filter(new TypedSqlQueryFilter<I_C_InvoiceLine>(p_WhereClause));
		}

		//
		// ORDER BY (to have a predictable result)
		queryBuilder.orderBy()
				.addColumn(I_C_InvoiceLine.COLUMN_C_Invoice_ID)
				.addColumn(I_C_InvoiceLine.COLUMN_Line)
				.addColumn(I_C_InvoiceLine.COLUMN_C_InvoiceLine_ID);

		final Iterator<I_C_InvoiceLine> invoiceLines = queryBuilder
				.create()
				.setOption(Query.OPTION_IteratorBufferSize, 1000)
				.iterate(I_C_InvoiceLine.class);
		
		return matchInvHelper.cacheCurrentInvoiceIterator(invoiceLines);
	}

	private final int incrementCounterAndGet(final String counterName)
	{
		return matchInvHelper.incrementCounterAndGet(counterName);
	}

	private void createMissingMatchInvs(final I_C_InvoiceLine il)
	{
		// skip description lines
		if (il.isDescription())
		{
			incrementCounterAndGet(COUNTER_NOTHING_TO_BE_DONE);
			return;
		}

		// If the invoice line already has at least one matchInv record, we assume that it's all fine.
		// NOTE: commented out because the checking is done when invoice lines are fetched
		// final boolean hasExistingMatchInvs = matchInvDAO.retrieveForInvoiceLineQuery(il).create().match();
		// if (hasExistingMatchInvs)
		// {
		// incrementCounterAndGet(COUNTER_NOTHING_TO_BE_DONE);
		// return;
		// }

		//
		// Consider only Completed/Closed invoices
		// NOTE: commented out because the checking is done when invoice lines are fetched
		// final I_C_Invoice currentInvoice = il.getC_Invoice();
		// if (!docActionBL.isStatusCompletedOrClosed(currentInvoice))
		// {
		// incrementCounterAndGet(COUNTER_NOTHING_TO_BE_DONE);
		// // TODO consider still trying to add MatchInv, but i'm not sure if there is a point
		// return;
		// }

		//
		// Retrieve how much we need to match on this invoice line.
		final MutableBigDecimal qtyInvoicedNotMatched = new MutableBigDecimal(retrieveQtyNotMatched(il));
		if (qtyInvoicedNotMatched.signum() == 0)
		{
			incrementCounterAndGet(COUNTER_NOTHING_TO_BE_DONE);
			return;
		}

		//
		// Iterate each M_InOutLine and try to match as much as possible
		final Map<Integer, MutableBigDecimal> inoutLineId2qtyNotMatched = new HashMap<>();
		int countMatchInvCreated = 0;
		int countInOutLinesChecked = 0;
		for (final I_M_InOutLine inoutLine : retrieveInOutLines(il))
		{
			countInOutLinesChecked++;

			// Get M_InOutLine's MovementQty that was not already matched
			final int inoutLineId = inoutLine.getM_InOutLine_ID();
			MutableBigDecimal qtyMovedNotMatched = inoutLineId2qtyNotMatched.get(inoutLineId);
			if (qtyMovedNotMatched == null)
			{
				qtyMovedNotMatched = new MutableBigDecimal(retrieveQtyNotMatched(inoutLine));
				inoutLineId2qtyNotMatched.put(inoutLineId, qtyMovedNotMatched);
			}

			final BigDecimal qtyToMatch = qtyInvoicedNotMatched.min(qtyMovedNotMatched).getValue();
			if (qtyToMatch.signum() == 0)
			{
				continue;
			}

			createNewMatchInvoiceRecord(il, inoutLine, qtyToMatch);
			countMatchInvCreated++;

			// Update quantities
			qtyInvoicedNotMatched.subtract(qtyToMatch);
			qtyMovedNotMatched.subtract(qtyToMatch);
		}

		//
		// Update counters
		if (countMatchInvCreated > 0)
		{
			incrementCounterAndGet(COUNTER_MATCHED);
		}
		else if (countInOutLinesChecked > 0)
		{
			incrementCounterAndGet(COUNTER_NOTHING_TO_BE_DONE_NoIOLWithUnmatchedQty);
		}
		else
		{
			incrementCounterAndGet(COUNTER_NOTHING_TO_BE_DONE_NoIOL);
		}
	}

	private List<I_M_InOutLine> retrieveInOutLines(final I_C_InvoiceLine il)
	{
		return matchInvHelper.retrieveInOutLines(il);
	}

	private BigDecimal retrieveQtyNotMatched(final I_C_InvoiceLine il)
	{
		return matchInvHelper.retrieveQtyNotMatched(il);
	}

	private BigDecimal retrieveQtyNotMatched(final I_M_InOutLine iol)
	{
		return matchInvHelper.retrieveQtyNotMatched(iol);
	}

	private I_M_MatchInv createNewMatchInvoiceRecord(final I_C_InvoiceLine il, final I_M_InOutLine iol, final BigDecimal qtyMatched)
	{
		return matchInvHelper.createMatchInv(il, iol, qtyMatched);
	}
}

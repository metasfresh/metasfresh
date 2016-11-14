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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.IProcessor;
import org.adempiere.util.Services;
import org.adempiere.util.lang.MutableBigDecimal;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.document.engine.IDocActionBL;
import de.metas.printing.esb.base.util.Check;

public class M_MatchInv_RecreateForInvoiceLine extends SvrProcess
{
	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	// Parameters
	private static final String PARAM_WhereClause = "WhereClause";
	private String p_WhereClause = null;
	private static final String PARAM_IsSOTrx = "IsSOTrx";
	private Boolean p_IsSOTrx = null;

	// Status
	private final MatchInvHelper matchInvHelper = new MatchInvHelper();

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
			else if (PARAM_IsSOTrx.equals(name))
			{
				p_IsSOTrx = para.getParameterAsBooleanOrNull();
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
			public void process(final I_C_InvoiceLine il)
			{
				rebuildMatchInvs(il);
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
			final IQueryBuilder<I_C_Invoice> invoiceQueryBuilder = queryBL.createQueryBuilder(I_C_Invoice.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
					.addOnlyActiveRecordsFilter();

			// Relevant DocStatus
			invoiceQueryBuilder.addInArrayFilter(I_C_Invoice.COLUMN_DocStatus, DocAction.STATUS_Completed, DocAction.STATUS_Closed);

			// IsSOTrx
			if (p_IsSOTrx != null)
			{
				invoiceQueryBuilder.addEqualsFilter(I_C_Invoice.COLUMN_IsSOTrx, p_IsSOTrx);
			}

			// Filter C_InvoiceLine by releavant C_Invoices
			final IQuery<I_C_Invoice> invoiceQuery = invoiceQueryBuilder.create();
			queryBuilder.addInSubQueryFilter(I_C_InvoiceLine.COLUMN_C_Invoice_ID,
					I_C_Invoice.COLUMN_C_Invoice_ID,
					invoiceQuery);
		}

		// Skip description lines
		queryBuilder.addEqualsFilter(I_C_InvoiceLine.COLUMN_IsDescription, false);

		// Skip invoice lines which have QtyInvoiced=0
		queryBuilder.addNotEqualsFilter(I_C_InvoiceLine.COLUMN_QtyInvoiced, BigDecimal.ZERO);

		// Skip invoice lines that are already fully matched
		{
			final String wc = "C_InvoiceLine.QtyInvoiced<>(SELECT COALESCE(SUM(mi.Qty), 0) FROM M_MatchInv mi  WHERE mi.C_InvoiceLine_ID=C_InvoiceLine.C_InvoiceLine_ID)";
			queryBuilder.filter(new TypedSqlQueryFilter<I_C_InvoiceLine>(wc));
		}

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
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterate(I_C_InvoiceLine.class);

		return matchInvHelper.cacheCurrentInvoiceIterator(invoiceLines);
	}

	private final void rebuildMatchInvs(final I_C_InvoiceLine invoiceLine)
	{
		final BigDecimal qtyInvoicedNotMatchedInitial = matchInvHelper.retrieveQtyNotMatched(invoiceLine);
		final MutableBigDecimal qtyInvoicedNotMatched = new MutableBigDecimal(qtyInvoicedNotMatchedInitial);

		//
		// "M_InOutLine_ID to QtyMovedNotMatched" map
		// NOTE: linked HashMap because we want to preserve the order
		final Map<Integer, MutableBigDecimal> inoutLineId2qtyNotMatchedMap = new LinkedHashMap<>();
		final Map<Integer, I_M_InOutLine> inoutLines = new LinkedHashMap<>();

		//
		// Add inout lines from existing M_MatchInvs to "M_InOutLine_ID to QtyMovedNotMatched" map
		final List<I_M_MatchInv> existingMatchInvs = matchInvHelper.retrieveMatchInvs(invoiceLine);
		for (final I_M_MatchInv matchInv : existingMatchInvs)
		{
			final int inoutLineId = matchInv.getM_InOutLine_ID();
			MutableBigDecimal qtyMovedNotMatched = inoutLineId2qtyNotMatchedMap.get(inoutLineId);
			if (qtyMovedNotMatched == null)
			{
				qtyMovedNotMatched = new MutableBigDecimal(matchInvHelper.retrieveQtyNotMatched(matchInv.getM_InOutLine()));
				inoutLineId2qtyNotMatchedMap.put(inoutLineId, qtyMovedNotMatched);
			}

			inoutLines.put(inoutLineId, matchInv.getM_InOutLine());
		}

		//
		// Add other inout lines associated with this invoice line to "M_InOutLine_ID to QtyMovedNotMatched" map
		for (final I_M_InOutLine inoutLine : matchInvHelper.retrieveInOutLines(invoiceLine))
		{
			// Get M_InOutLine's MovementQty that was not already matched
			final int inoutLineId = inoutLine.getM_InOutLine_ID();
			MutableBigDecimal qtyMovedNotMatched = inoutLineId2qtyNotMatchedMap.get(inoutLineId);
			if (qtyMovedNotMatched == null)
			{
				qtyMovedNotMatched = new MutableBigDecimal(matchInvHelper.retrieveQtyNotMatched(inoutLine));
				inoutLineId2qtyNotMatchedMap.put(inoutLineId, qtyMovedNotMatched);
			}

			inoutLines.put(inoutLineId, inoutLine);
		}

		//
		// Set all M_MatchInv to ZERO
		for (final I_M_MatchInv matchInv : existingMatchInvs)
		{
			final BigDecimal qty = matchInv.getQty();
			matchInv.setQty(BigDecimal.ZERO);

			// Increase the qtyInvoiced not matched
			qtyInvoicedNotMatched.add(qty);

			// Increase the qtyMoved not matched
			final int inoutLineId = matchInv.getM_InOutLine_ID();
			final MutableBigDecimal qtyMovedNotMatched = inoutLineId2qtyNotMatchedMap.get(inoutLineId);
			qtyMovedNotMatched.add(qty);
		}

		//
		// Start rebuilding the M_MatchInvs
		for (final I_M_MatchInv matchInv : existingMatchInvs)
		{
			final int inoutLineId = matchInv.getM_InOutLine_ID();
			final MutableBigDecimal qtyMovedNotMatched = inoutLineId2qtyNotMatchedMap.get(inoutLineId);

			final BigDecimal qtyMatched = calculateQtyMatched(qtyInvoicedNotMatched, qtyMovedNotMatched);
			if (qtyMatched.signum() == 0)
			{
				matchInv.setProcessed(false);
				InterfaceWrapperHelper.delete(matchInv);
			}
			else
			{
				matchInv.setQty(qtyMatched);
				InterfaceWrapperHelper.save(matchInv);
			}

			qtyInvoicedNotMatched.subtract(qtyMatched);
			qtyMovedNotMatched.subtract(qtyMatched);
		}

		//
		// If we still have QtyInvoiced which was not matched, check remaining M_InOutLines
		if (qtyInvoicedNotMatched.signum() != 0)
		{
			for (final Map.Entry<Integer, MutableBigDecimal> e : inoutLineId2qtyNotMatchedMap.entrySet())
			{
				if (qtyInvoicedNotMatched.signum() == 0)
				{
					break;
				}

				final MutableBigDecimal qtyMovedNotMatched = e.getValue();
				if (qtyMovedNotMatched.signum() == 0)
				{
					continue;
				}

				final BigDecimal qtyMatched = calculateQtyMatched(qtyInvoicedNotMatched, qtyMovedNotMatched);
				if (qtyMatched.signum() == 0)
				{
					continue;
				}

				final int inoutLineId = e.getKey();
				final I_M_InOutLine inoutLine = inoutLines.get(inoutLineId);
				final I_M_InOut inout = inoutLine.getM_InOut();

				// Skip inouts which are not COmpleted or CLosed
				if (!Services.get(IDocActionBL.class).isStatusCompletedOrClosed(inout))
				{
					continue;
				}

				// Create the matching
				matchInvHelper.createMatchInv(invoiceLine, inoutLine, qtyMatched);

				// Update not matched quantities
				qtyInvoicedNotMatched.subtract(qtyMatched);
				qtyMovedNotMatched.subtract(qtyMatched);
			}
		}

		//
		// Update counters
		if (qtyInvoicedNotMatched.signum() == 0)
		{
			matchInvHelper.incrementCounterAndGet(MatchInvHelper.COUNTER_FullyMatched);
		}
		else if (qtyInvoicedNotMatchedInitial.compareTo(qtyInvoicedNotMatched.getValue()) == 0)
		{
			matchInvHelper.incrementCounterAndGet(MatchInvHelper.COUNTER_NotMatched);
		}
		else
		{
			matchInvHelper.incrementCounterAndGet(MatchInvHelper.COUNTER_PartiallyMatched);
		}
	}

	/**
	 * Calculate how much we can match
	 * 
	 * @param qtyInvoicedNotMatched
	 * @param qtyMovedNotMatched
	 * @return quantity that can be matched.
	 */
	private BigDecimal calculateQtyMatched(final MutableBigDecimal qtyInvoicedNotMatched, final MutableBigDecimal qtyMovedNotMatched)
	{
		final int qtyInvoicedNotMatchedSignum = qtyInvoicedNotMatched.signum();
		if (qtyInvoicedNotMatchedSignum == 0)
		{
			// NOTE: usually shall not happen because it was checked before
			return BigDecimal.ZERO;
		}

		final int qtyMovedNotMatchedSignum = qtyMovedNotMatched.signum();
		if (qtyMovedNotMatchedSignum == 0)
		{
			return BigDecimal.ZERO;
		}

		if (qtyInvoicedNotMatchedSignum > 0)
		{
			if (qtyMovedNotMatchedSignum > 0)
			{
				return qtyInvoicedNotMatched.min(qtyMovedNotMatched).getValue();
			}
			else
			// qtyMovedNotMatchedSignum < 0
			{
				return BigDecimal.ZERO;
			}
		}
		else
		// qtyInvoicedNotMatchedSignum < 0
		{
			if (qtyMovedNotMatchedSignum > 0)
			{
				return BigDecimal.ZERO;
			}
			else
			// qtyMovedNotMatchedSignum < 0
			{
				return qtyInvoicedNotMatched.max(qtyMovedNotMatched).getValue();
			}
		}

	}
}

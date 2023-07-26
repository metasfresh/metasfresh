package de.metas.fresh.invoice.migrateMatchInv.process;

import static java.math.BigDecimal.ZERO;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
import org.compiere.model.IQuery;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;

import de.metas.document.engine.IDocument;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.IProcessor;
import de.metas.util.Services;

public class M_MatchInv_RecreateForInOutLine extends JavaProcess
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
		final Iterator<I_M_InOutLine> inoutLines = retrieveAllInOutLines();

		return matchInvHelper.process(getCtx(), inoutLines, new IProcessor<I_M_InOutLine>()
		{

			@Override
			public void process(final I_M_InOutLine inoutLine)
			{
				rebuildMatchInvs(inoutLine);
			}
		});
	}

	private Iterator<I_M_InOutLine> retrieveAllInOutLines()
	{
		final IQueryBuilder<I_M_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter();

		// Filter only relevant inouts
		{
			final IQueryBuilder<I_M_InOut> inoutQueryBuilder = queryBL.createQueryBuilder(I_M_InOut.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
					.addOnlyActiveRecordsFilter();

			// Relevant DocStatus
			inoutQueryBuilder.addInArrayOrAllFilter(I_M_InOut.COLUMN_DocStatus, IDocument.STATUS_Completed, IDocument.STATUS_Closed);

			// IsSOTrx
			if (p_IsSOTrx != null)
			{
				inoutQueryBuilder.addEqualsFilter(I_M_InOut.COLUMN_IsSOTrx, p_IsSOTrx);
			}

			final IQuery<I_M_InOut> inoutQuery = inoutQueryBuilder.create();
			queryBuilder.addInSubQueryFilter(I_M_InOutLine.COLUMN_M_InOut_ID,
					I_M_InOut.COLUMN_M_InOut_ID,
					inoutQuery);
		}

		// Skip description lines
		queryBuilder.addEqualsFilter(I_M_InOutLine.COLUMN_IsDescription, false);

		// Skip invoice lines which have MovementQty=0
		queryBuilder.addNotEqualsFilter(I_M_InOutLine.COLUMN_MovementQty, BigDecimal.ZERO);

		// Skip inout lines that are already fully matched
		{
			final String wc = "M_InOutLine.MovementQty<>(SELECT COALESCE(SUM(mi.Qty), 0) FROM M_MatchInv mi  WHERE mi.M_InOutLine_ID=M_InOutLine.M_InOutLine_ID)";
			queryBuilder.filter(TypedSqlQueryFilter.of(wc));
		}

		// Add custom WHERE clause if any
		if (!Check.isEmpty(p_WhereClause, true))
		{
			queryBuilder.filter(TypedSqlQueryFilter.of(p_WhereClause));
		}

		//
		// ORDER BY (to have a predictable result)
		queryBuilder.orderBy()
				.addColumn(I_M_InOutLine.COLUMN_M_InOut_ID)
				.addColumn(I_M_InOutLine.COLUMN_Line)
				.addColumn(I_M_InOutLine.COLUMN_M_InOutLine_ID);

		final Iterator<I_M_InOutLine> inoutLines = queryBuilder
				.create()
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterate(I_M_InOutLine.class);

		return matchInvHelper.cacheCurrentInOutIterator(inoutLines);
	}

	private void rebuildMatchInvs(final I_M_InOutLine inoutLine)
	{
		final ProductId productId = ProductId.ofRepoId(inoutLine.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(inoutLine.getC_UOM_ID());

		final StockQtyAndUOMQty qtyMovedNotMatchedInitial = matchInvHelper.retrieveQtyNotMatched(inoutLine);
		StockQtyAndUOMQty qtyMovedNotMatched = StockQtyAndUOMQtys.createZero(productId, uomId);

		//
		// "C_InvoiceLine_ID to QtyMovedNotMatched" map
		// NOTE: linked HashMap because we want to preserve the order
		final Map<Integer, StockQtyAndUOMQty> invoiceLineId2qtyNotMatchedMap = new LinkedHashMap<>();
		final Map<Integer, I_C_InvoiceLine> invoiceLines = new LinkedHashMap<>();

		//
		// Add invoice lines from existing M_MatchInvs to "C_InvoiceLine_ID to QtyMovedNotMatched" map
		final List<I_M_MatchInv> existingMatchInvs = matchInvHelper.retrieveMatchInvs(inoutLine);
		for (final I_M_MatchInv matchInv : existingMatchInvs)
		{
			final int invoiceLineId = matchInv.getC_InvoiceLine_ID();
			final I_C_InvoiceLine invoiceLine = matchInv.getC_InvoiceLine();
			StockQtyAndUOMQty qtyInvoicedNotMatched = invoiceLineId2qtyNotMatchedMap.get(invoiceLineId);
			if (qtyInvoicedNotMatched == null)
			{
				qtyInvoicedNotMatched = matchInvHelper.retrieveQtyNotMatched(invoiceLine);
				invoiceLineId2qtyNotMatchedMap.put(invoiceLineId, qtyInvoicedNotMatched);
			}

			invoiceLines.put(invoiceLineId, invoiceLine);
		}

		//
		// Add other invoice lines associated with this invoice line to "C_InvoiceLine_ID to QtyMovedNotMatched" map
		for (final I_C_InvoiceLine invoiceLine : matchInvHelper.retrieveInvoiceLines(inoutLine))
		{
			// Get M_InOutLine's MovementQty that was not already matched
			final int invoiceLineId = invoiceLine.getC_InvoiceLine_ID();
			StockQtyAndUOMQty qtyInvoicedNotMatched = invoiceLineId2qtyNotMatchedMap.get(invoiceLineId);
			if (qtyInvoicedNotMatched == null)
			{
				qtyInvoicedNotMatched = matchInvHelper.retrieveQtyNotMatched(invoiceLine);
				invoiceLineId2qtyNotMatchedMap.put(invoiceLineId, qtyInvoicedNotMatched);
			}

			invoiceLines.put(invoiceLineId, invoiceLine);
		}

		//
		// Set all M_MatchInv to ZERO
		for (final I_M_MatchInv matchInv : existingMatchInvs)
		{
			final StockQtyAndUOMQty qty = StockQtyAndUOMQtys.create(matchInv.getQty(), productId, matchInv.getQtyInUOM(), uomId);

			matchInv.setQty(ZERO);
			matchInv.setQtyInUOM(ZERO);

			// Increase the qtyMoved not matched
			qtyMovedNotMatched = StockQtyAndUOMQtys.add(qtyMovedNotMatched, qty);

			// Increase the qtyInvoiced not matched
			final int invoiceLineId = matchInv.getC_InvoiceLine_ID();
			final StockQtyAndUOMQty qtyInvoicedNotMatched = invoiceLineId2qtyNotMatchedMap.get(invoiceLineId);
			invoiceLineId2qtyNotMatchedMap.put(invoiceLineId, StockQtyAndUOMQtys.add(qtyInvoicedNotMatched, qty));
		}

		//
		// Start rebuilding the M_MatchInvs
		for (final I_M_MatchInv matchInv : existingMatchInvs)
		{
			final int invoiceLineId = matchInv.getC_InvoiceLine_ID();
			final StockQtyAndUOMQty qtyInvoicedNotMatched = invoiceLineId2qtyNotMatchedMap.get(invoiceLineId);

			final StockQtyAndUOMQty qtyMatched = StockQtyAndUOMQtys.minUomQty(qtyInvoicedNotMatched, qtyMovedNotMatched);
			if (qtyMatched.signum() == 0)
			{
				matchInv.setProcessed(false);
				InterfaceWrapperHelper.delete(matchInv);
			}
			else
			{
				matchInv.setQty(qtyMatched.getStockQty().toBigDecimal());
				matchInv.setQtyInUOM(qtyMatched.getUOMQtyNotNull().toBigDecimal());
				matchInv.setC_UOM_ID(qtyMatched.getUOMQtyNotNull().getUomId().getRepoId());
				InterfaceWrapperHelper.save(matchInv);
			}

			invoiceLineId2qtyNotMatchedMap.put(invoiceLineId, StockQtyAndUOMQtys.subtract(qtyInvoicedNotMatched, qtyMatched));
			qtyMovedNotMatched = StockQtyAndUOMQtys.subtract(qtyMovedNotMatched, qtyMatched);
		}

		//
		// If we still have QtyMoved which was not matched, check remaining C_InvoiceLines
		if (qtyMovedNotMatched.signum() != 0)
		{
			for (final int invoiceLineId : invoiceLineId2qtyNotMatchedMap.keySet())
			{
				if (qtyMovedNotMatched.signum() == 0)
				{
					break;
				}

				final StockQtyAndUOMQty qtyInvoicedNotMatched = invoiceLineId2qtyNotMatchedMap.get(invoiceLineId);
				if (qtyInvoicedNotMatched.signum() == 0)
				{
					continue;
				}

				final StockQtyAndUOMQty qtyMatched = StockQtyAndUOMQtys.minUomQty(qtyInvoicedNotMatched, qtyMovedNotMatched);
				if (qtyMatched.signum() == 0)
				{
					continue;
				}

				final I_C_InvoiceLine invoiceLine = invoiceLines.get(invoiceLineId);
				matchInvHelper.createMatchInv(invoiceLine, inoutLine, qtyMatched);

				invoiceLineId2qtyNotMatchedMap.put(invoiceLineId, StockQtyAndUOMQtys.subtract(qtyInvoicedNotMatched, qtyMatched));
				qtyMovedNotMatched = StockQtyAndUOMQtys.subtract(qtyMovedNotMatched, qtyMatched);
			}
		}

		//
		// Update counters
		if (qtyMovedNotMatched.signum() == 0)
		{
			matchInvHelper.incrementCounterAndGet(MatchInvHelper.COUNTER_FullyMatched);
		}
		else if (StockQtyAndUOMQtys.compareUomQty(qtyMovedNotMatchedInitial, qtyMovedNotMatched) == 0)
		{
			matchInvHelper.incrementCounterAndGet(MatchInvHelper.COUNTER_NotMatched);
		}
		else
		{
			matchInvHelper.incrementCounterAndGet(MatchInvHelper.COUNTER_PartiallyMatched);
		}
	}
}

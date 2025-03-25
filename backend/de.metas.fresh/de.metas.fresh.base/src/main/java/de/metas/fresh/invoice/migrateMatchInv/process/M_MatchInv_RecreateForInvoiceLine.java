package de.metas.fresh.invoice.migrateMatchInv.process;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.IProcessor;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.math.BigDecimal.ZERO;

public class M_MatchInv_RecreateForInvoiceLine extends JavaProcess
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
			invoiceQueryBuilder.addInArrayOrAllFilter(I_C_Invoice.COLUMN_DocStatus, IDocument.STATUS_Completed, IDocument.STATUS_Closed);

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
		final StockQtyAndUOMQty qtyInvoicedNotMatchedInitial = matchInvHelper.retrieveQtyNotMatched(invoiceLine);
		StockQtyAndUOMQty qtyInvoicedNotMatched = qtyInvoicedNotMatchedInitial;

		//
		// "M_InOutLine_ID to QtyMovedNotMatched" map
		// NOTE: linked HashMap because we want to preserve the order
		final Map<Integer, StockQtyAndUOMQty> inoutLineId2qtyNotMatchedMap = new LinkedHashMap<>();
		final Map<Integer, I_M_InOutLine> inoutLines = new LinkedHashMap<>();

		//
		// Add inout lines from existing M_MatchInvs to "M_InOutLine_ID to QtyMovedNotMatched" map
		final List<I_M_MatchInv> existingMatchInvs = matchInvHelper.retrieveMatchInvs(invoiceLine);
		for (final I_M_MatchInv matchInv : existingMatchInvs)
		{
			final int inoutLineId = matchInv.getM_InOutLine_ID();
			StockQtyAndUOMQty qtyMovedNotMatched = inoutLineId2qtyNotMatchedMap.get(inoutLineId);
			if (qtyMovedNotMatched == null)
			{
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
			StockQtyAndUOMQty qtyMovedNotMatched = inoutLineId2qtyNotMatchedMap.get(inoutLineId);
			if (qtyMovedNotMatched == null)
			{
				inoutLineId2qtyNotMatchedMap.put(inoutLineId, qtyMovedNotMatched);
			}
			inoutLines.put(inoutLineId, inoutLine);
		}

		//
		// Set all M_MatchInv to ZERO
		for (final I_M_MatchInv matchInv : existingMatchInvs)
		{
			final StockQtyAndUOMQty qty = StockQtyAndUOMQtys.create(
					matchInv.getQty(), ProductId.ofRepoId(matchInv.getM_Product_ID()),
					matchInv.getQtyInUOM(), UomId.ofRepoId(matchInv.getC_UOM_ID()));

			matchInv.setQty(BigDecimal.ZERO);
			matchInv.setQtyInUOM(ZERO);

			// Increase the qtyInvoiced not matched
			qtyInvoicedNotMatched = StockQtyAndUOMQtys.add(qtyInvoicedNotMatched, qty);

			// Increase the qtyMoved not matched
			final int inoutLineId = matchInv.getM_InOutLine_ID();
			final StockQtyAndUOMQty qtyMovedNotMatched = inoutLineId2qtyNotMatchedMap.get(inoutLineId);
			inoutLineId2qtyNotMatchedMap.put(inoutLineId,
					StockQtyAndUOMQtys.add(qtyMovedNotMatched, qty));

		}

		//
		// Start rebuilding the M_MatchInvs
		for (final I_M_MatchInv matchInv : existingMatchInvs)
		{
			final int inoutLineId = matchInv.getM_InOutLine_ID();
			final StockQtyAndUOMQty qtyMovedNotMatched = inoutLineId2qtyNotMatchedMap.get(inoutLineId);

			final StockQtyAndUOMQty qtyMatched = calculateQtyMatched(qtyInvoicedNotMatched, qtyMovedNotMatched);
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

			qtyInvoicedNotMatched = StockQtyAndUOMQtys.subtract(qtyInvoicedNotMatched, qtyMatched);
			inoutLineId2qtyNotMatchedMap.put(inoutLineId, StockQtyAndUOMQtys.subtract(qtyMovedNotMatched, qtyMatched));
		}

		//
		// If we still have QtyInvoiced which was not matched, check remaining M_InOutLines
		if (qtyInvoicedNotMatched.signum() != 0)
		{
			final Set<Integer> inoutLineIds = inoutLineId2qtyNotMatchedMap.keySet();
			for (final int inoutLineId : inoutLineIds)
			// for (final Map.Entry<Integer, StockQtyAndUOMQty> e : inoutLineId2qtyNotMatchedMap.entrySet())
			{
				if (qtyInvoicedNotMatched.signum() == 0)
				{
					break;
				}

				final StockQtyAndUOMQty qtyMovedNotMatched = inoutLineId2qtyNotMatchedMap.get(inoutLineId);
				if (qtyMovedNotMatched.signum() == 0)
				{
					continue;
				}

				final StockQtyAndUOMQty qtyMatched = calculateQtyMatched(qtyInvoicedNotMatched, qtyMovedNotMatched);
				if (qtyMatched.signum() == 0)
				{
					continue;
				}

				final I_M_InOutLine inoutLine = inoutLines.get(inoutLineId);
				final I_M_InOut inout = inoutLine.getM_InOut();

				// Skip inouts which are not COmpleted or CLosed
				if (!Services.get(IDocumentBL.class).isDocumentCompletedOrClosed(inout))
				{
					continue;
				}

				// Create the matching
				matchInvHelper.createMatchInv(invoiceLine, inoutLine, qtyMatched);

				// Update not matched quantities
				qtyInvoicedNotMatched = StockQtyAndUOMQtys.subtract(qtyInvoicedNotMatched, qtyMatched);
				inoutLineId2qtyNotMatchedMap.put(inoutLineId, StockQtyAndUOMQtys.subtract(qtyMovedNotMatched, qtyMatched));
			}
		}

		//
		// Update counters
		if (qtyInvoicedNotMatched.signum() == 0)
		{
			matchInvHelper.incrementCounterAndGet(MatchInvHelper.COUNTER_FullyMatched);
		}
		else if (StockQtyAndUOMQtys.compareUomQty(qtyInvoicedNotMatchedInitial, qtyInvoicedNotMatched) == 0)
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
	private StockQtyAndUOMQty calculateQtyMatched(final StockQtyAndUOMQty qtyInvoicedNotMatched, final StockQtyAndUOMQty qtyMovedNotMatched)
	{
		final int qtyInvoicedNotMatchedSignum = qtyInvoicedNotMatched.signum();
		if (qtyInvoicedNotMatchedSignum == 0)
		{
			// NOTE: usually shall not happen because it was checked before
			return StockQtyAndUOMQtys.createZero(qtyInvoicedNotMatched.getProductId(), qtyInvoicedNotMatched.getUOMQtyNotNull().getUomId());
		}

		final int qtyMovedNotMatchedSignum = qtyMovedNotMatched.signum();
		if (qtyMovedNotMatchedSignum == 0)
		{
			return StockQtyAndUOMQtys.createZero(qtyInvoicedNotMatched.getProductId(), qtyInvoicedNotMatched.getUOMQtyNotNull().getUomId());
		}

		if (qtyInvoicedNotMatchedSignum > 0)
		{
			if (qtyMovedNotMatchedSignum > 0)
			{
				return StockQtyAndUOMQtys.maxUomQty(qtyInvoicedNotMatched, qtyMovedNotMatched);
			}
			else
			// qtyMovedNotMatchedSignum < 0
			{
				return StockQtyAndUOMQtys.createZero(qtyInvoicedNotMatched.getProductId(), qtyInvoicedNotMatched.getUOMQtyNotNull().getUomId());
			}
		}
		else
		// qtyInvoicedNotMatchedSignum < 0
		{
			if (qtyMovedNotMatchedSignum > 0)
			{
				return StockQtyAndUOMQtys.createZero(qtyInvoicedNotMatched.getProductId(), qtyInvoicedNotMatched.getUOMQtyNotNull().getUomId());
			}
			else
			// qtyMovedNotMatchedSignum < 0
			{
				return StockQtyAndUOMQtys.maxUomQty(qtyInvoicedNotMatched, qtyMovedNotMatched);
			}
		}

	}
}

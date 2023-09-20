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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IMatchInvDAO;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.IProcessor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.X_M_InOut;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Helper class used by the processes which are creating/adjusting {@link I_M_MatchInv} records.
 *
 * @author tsa
 *
 */
/* package */class MatchInvHelper
{
	// services
	private final transient Logger logger = LogManager.getLogger(getClass());
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IMatchInvDAO matchInvDAO = Services.get(IMatchInvDAO.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	/** Migration AD_User_ID used to create the new M_MatchInv records */
	public static final int AD_USER_ID_Migration = 99;

	// status
	private final Map<String, Integer> counters = new LinkedHashMap<>();
	public static final String COUNTER_ALL = "Counter-All";
	public static final String COUNTER_FullyMatched = "Counter-FullyMatched";
	public static final String COUNTER_PartiallyMatched = "Counter-PartiallyMatched";
	public static final String COUNTER_NotMatched = "Counter-NotMatched";

	public final int incrementCounterAndGet(final String counterName)
	{
		Integer cnt = counters.get(counterName);
		if (cnt == null)
		{
			cnt = 0;
		}

		cnt++;
		counters.put(counterName, cnt);

		return cnt;
	}

	public final int getCounter(final String counterName)
	{
		final Integer cnt = counters.get(counterName);
		return cnt == null ? 0 : cnt;
	}

	public String getCountersAsString()
	{
		final StringBuilder sb = new StringBuilder();
		for (final Map.Entry<String, Integer> e : counters.entrySet())
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append(e.getKey()).append("=").append(e.getValue());
		}
		if (sb.length() <= 0)
		{
			return "-";
		}

		return sb.toString();
	}

	public List<I_M_MatchInv> retrieveMatchInvs(final I_C_InvoiceLine invoiceLine)
	{
		return matchInvDAO.retrieveForInvoiceLine(invoiceLine);
	}

	public List<I_M_MatchInv> retrieveMatchInvs(final I_M_InOutLine inoutLine)
	{
		return matchInvDAO.retrieveForInOutLine(inoutLine);
	}

	public List<I_M_InOutLine> retrieveInOutLines(final I_C_InvoiceLine il)
	{
		//
		// Try C_InvoiceLine->C_Invoice_Line_Alloc->C_Invoice_Candidate->C_InvoiceCandidate_InOutLine->M_InOutLine
		final List<I_M_InOutLine> inoutLines = queryBL.createQueryBuilder(I_C_Invoice_Line_Alloc.class, il)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID, il.getC_InvoiceLine_ID())
				//
				// Collect C_Invoice_Candidates
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID)
				.addOnlyActiveRecordsFilter()
				//
				// Collect C_InvoiceCandidate_InOutLines
				.andCollectChildren(I_C_InvoiceCandidate_InOutLine.COLUMN_C_Invoice_Candidate_ID, I_C_InvoiceCandidate_InOutLine.class)
				//
				// Collect M_InOutLines
				.andCollect(I_C_InvoiceCandidate_InOutLine.COLUMN_M_InOutLine_ID)
				//
				// Order M_InOutLines
				.orderBy()
				.addColumn(I_M_InOutLine.COLUMN_M_InOutLine_ID)
				.endOrderBy()
				//
				// Execute the query
				.create()
				.list(I_M_InOutLine.class);

		//
		// If direct link C_InvoiceLine.M_InOutLine_ID is set,
		// Add it to existing M_InOutLines list, if not already added
		{
			final int directInOutLineId = il.getM_InOutLine_ID();
			if (directInOutLineId > 0)
			{
				//
				// Check if already exists
				boolean found = false;
				for (int i = 0; i < inoutLines.size(); i++)
				{
					final I_M_InOutLine inoutLine = inoutLines.get(i);
					if (inoutLine.getM_InOutLine_ID() != directInOutLineId)
					{
						continue;
					}

					// Make sure the direct link it's the first record to be checked
					if (i > 0)
					{
						inoutLines.remove(i);
						inoutLines.add(0, inoutLine);
					}

					found = true;
					break;
				}

				// Add the direct M_InOutLine if not already exists
				if (!found)
				{
					final I_M_InOutLine directInoutLine = il.getM_InOutLine();
					inoutLines.add(0, directInoutLine);
					logger.warn("Direct link was not found in IC-IOL associations. Adding now."
							+ "\n C_InvoiceLine: " + il
							+ "\n M_InOutLine: " + directInoutLine);
				}
			}
		}

		return inoutLines;
	}

	public List<I_C_InvoiceLine> retrieveInvoiceLines(final I_M_InOutLine inoutLine)
	{
		//
		// Try M_InOutLine_ID->C_InvoiceCandidate_InOutLine->C_Invoice_Candidate->C_Invoice_Line_Alloc->C_InvoiceLine
		return queryBL.createQueryBuilder(I_C_InvoiceCandidate_InOutLine.class, inoutLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_InvoiceCandidate_InOutLine.COLUMN_M_InOutLine_ID, inoutLine.getM_InOutLine_ID())
				//
				// Collect C_Invoice_Candidates
				.andCollect(I_C_InvoiceCandidate_InOutLine.COLUMN_C_Invoice_Candidate_ID)
				.addOnlyActiveRecordsFilter()
				//
				// Collect C_Invoice_Line_Allocs
				.andCollectChildren(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID, I_C_Invoice_Line_Alloc.class)
				.addOnlyActiveRecordsFilter()
				//
				// Collect C_InvoiceLines
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID)
				//
				// Order C_InvoiceLines
				.orderBy()
				.addColumn(I_C_InvoiceLine.COLUMN_C_InvoiceLine_ID)
				.endOrderBy()
				//
				// Execute the query
				.create()
				.list(I_C_InvoiceLine.class);
	}

	public StockQtyAndUOMQty retrieveQtyNotMatched(@NonNull final I_C_InvoiceLine il)
	{
		StockQtyAndUOMQty qtyInvoiced = StockQtyAndUOMQtys
				.create(
						il.getQtyInvoiced(), ProductId.ofRepoId(il.getM_Product_ID()),
						il.getQtyEntered(), UomId.ofRepoId(il.getC_UOM_ID()));

		// Negate the qtyInvoiced if this is an CreditMemo
		final I_C_Invoice invoice = il.getC_Invoice();
		if (Services.get(IInvoiceBL.class).isCreditMemo(invoice))
		{
			qtyInvoiced = qtyInvoiced.negate();
		}

		final StockQtyAndUOMQty qtyMatched = matchInvDAO.retrieveQtyMatched(il);
		final StockQtyAndUOMQty qtyNotMatched = qtyInvoiced.subtract(qtyMatched);
		return qtyNotMatched;
	}

	public StockQtyAndUOMQty retrieveQtyNotMatched(@NonNull final I_M_InOutLine iol)
	{
		UomId catchUOMId;
		if (iol.getQtyDeliveredCatch().signum() != 0)
		{
			catchUOMId = UomId.ofRepoIdOrNull(iol.getCatch_UOM_ID());
		}
		else
		{
			catchUOMId = null;
		}
		StockQtyAndUOMQty qtyReceived = StockQtyAndUOMQtys
				.create(
						iol.getMovementQty(), ProductId.ofRepoId(iol.getM_Product_ID()),
						iol.getQtyDeliveredCatch(), catchUOMId);

		// Negate the qtyReceived if this is an material return,
		// because we want to have the qtyReceived as an absolute value.
		final I_M_InOut inout = iol.getM_InOut();
		final String movementType = inout.getMovementType();
		if (X_M_InOut.MOVEMENTTYPE_CustomerReturns.equals(movementType)
				|| X_M_InOut.MOVEMENTTYPE_VendorReturns.equals(movementType))
		{
			qtyReceived = qtyReceived.negate();
		}

		final StockQtyAndUOMQty qtyMatched = matchInvDAO.retrieveQtysInvoiced(
				iol,
				qtyReceived.toZero()/* initialValue */);
		final StockQtyAndUOMQty qtyNotMatched = StockQtyAndUOMQtys.subtract(qtyReceived, qtyMatched);
		return qtyNotMatched;
	}

	public I_M_MatchInv createMatchInv(
			@NonNull final I_C_InvoiceLine il,
			@NonNull final I_M_InOutLine iol,
			@NonNull final StockQtyAndUOMQty qtysMatched)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(il);
		final I_M_MatchInv matchInv = InterfaceWrapperHelper.create(ctx, I_M_MatchInv.class, ITrx.TRXNAME_ThreadInherited);

		matchInv.setAD_Org_ID(il.getAD_Org_ID());
		matchInv.setC_Invoice_ID(il.getC_Invoice_ID());
		matchInv.setC_InvoiceLine(il);
		matchInv.setM_InOut_ID(iol.getM_InOut_ID());
		matchInv.setM_InOutLine(iol);

		matchInv.setQty(qtysMatched.getStockQty().toBigDecimal());
		matchInv.setQtyInUOM(qtysMatched.getUOMQtyOpt().get().toBigDecimal());
		matchInv.setC_UOM_ID(qtysMatched.getUOMQtyOpt().get().getUomId().getRepoId());

		matchInv.setM_Product_ID(iol.getM_Product_ID());
		matchInv.setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());

		final I_C_Invoice invoice = il.getC_Invoice();
		matchInv.setDateTrx(invoice.getDateInvoiced());

		final I_M_InOut inOut = iol.getM_InOut();
		matchInv.setIsSOTrx(inOut.isSOTrx());
		matchInv.setDocumentNo(inOut.getDocumentNo()); // FIXME: why using inout's DocumentNo?

		matchInv.setProcessed(true);

		InterfaceWrapperHelper.save(matchInv);
		return matchInv;
	}

	/**
	 * Process given <code>lines</code> using <code>processor</code>.
	 *
	 * NOTE:
	 * <ul>
	 * <li>user {@link #AD_USER_ID_Migration} will be used to save/update records.
	 * </ul>
	 *
	 * @param ctx
	 * @param lines document lines
	 * @param processor processor used to process one document line
	 * @return processing summary (i.e. the counters)
	 * @throws Exception
	 */
	public <T> String process(final Properties ctx,
			final Iterator<T> lines,
			final IProcessor<T> processor)
	{
		// we do everything as AD_User_ID 99 ("migration"), so we can later on identify the records that were created.
		final String userIdBkp = Env.getContext(ctx, Env.CTXNAME_AD_User_ID);
		try
		{
			Env.setContext(ctx, Env.CTXNAME_AD_User_ID, AD_USER_ID_Migration);
			return process0(lines, processor);
		}
		catch (final InterruptedException e)
		{
			return "Interrupted: " + getCountersAsString();
		}
		finally
		{
			Env.setContext(ctx, Env.CTXNAME_AD_User_ID, userIdBkp);
		}
	}

	private <T> String process0(final Iterator<T> lines, final IProcessor<T> processor) throws InterruptedException
	{
		for (final T line : IteratorUtils.asIterable(lines))
		{
			// Check if the thread was interrupted
			if (Thread.currentThread().isInterrupted())
			{
				throw new InterruptedException();
			}

			// Report current status
			{
				final int countAll = getCounter(COUNTER_ALL);
				if (countAll > 0 && countAll % 100 == 0)
				{
					logger.warn("STATUS: " + getCountersAsString());
				}
			}

			incrementCounterAndGet(COUNTER_ALL);

			// now do the actual work, in a dedicated transaction
			trxManager.runInNewTrx(new TrxRunnable()
			{
				@Override
				public void run(final String localTrxName) throws Exception
				{
					processor.process(line);
				}
			});
		}

		return "@Success@: " + getCountersAsString();
	}

	public final Iterator<I_C_InvoiceLine> cacheCurrentInvoiceIterator(final Iterator<I_C_InvoiceLine> invoiceLines)
	{
		return new Iterator<I_C_InvoiceLine>()
		{
			I_C_Invoice currentInvoice = null;

			@Override
			public boolean hasNext()
			{
				return invoiceLines.hasNext();
			}

			@Override
			public I_C_InvoiceLine next()
			{
				final I_C_InvoiceLine invoiceLine = invoiceLines.next();
				loadCurrentInvoice(invoiceLine);
				return invoiceLine;
			}

			private final void loadCurrentInvoice(final I_C_InvoiceLine invoiceLine)
			{
				if (currentInvoice != null && currentInvoice.getC_Invoice_ID() != invoiceLine.getC_Invoice_ID())
				{
					currentInvoice = null;
				}
				if (currentInvoice == null)
				{
					currentInvoice = invoiceLine.getC_Invoice();
				}
				else
				{
					invoiceLine.setC_Invoice(currentInvoice);
				}
			}

			@Override
			public void remove()
			{
				invoiceLines.remove();
			}
		};
	}

	public Iterator<I_M_InOutLine> cacheCurrentInOutIterator(final Iterator<I_M_InOutLine> inoutLines)
	{
		return new Iterator<I_M_InOutLine>()
		{
			I_M_InOut currentInOut = null;

			@Override
			public boolean hasNext()
			{
				return inoutLines.hasNext();
			}

			@Override
			public I_M_InOutLine next()
			{
				final I_M_InOutLine inoutLine = inoutLines.next();
				loadCurrentInOut(inoutLine);
				return inoutLine;
			}

			private final void loadCurrentInOut(final I_M_InOutLine inoutLine)
			{
				if (currentInOut != null && currentInOut.getM_InOut_ID() != inoutLine.getM_InOut_ID())
				{
					currentInOut = null;
				}
				if (currentInOut == null)
				{
					currentInOut = inoutLine.getM_InOut();
				}
				else
				{
					inoutLine.setM_InOut(currentInOut);
				}
			}

			@Override
			public void remove()
			{
				inoutLines.remove();
			}
		};
	}
}

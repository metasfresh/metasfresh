package org.eevolution.process;

/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.document.engine.DocStatus;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;
import org.compiere.util.TrxRunnable;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.util.Iterator;

/**
 * Closes the completed manufacturing orders of the current selection.
 *
 * <p>The cost-monitor window lists completed-but-not-closed orders. The post-calculation action closes the
 * ones whose residual it posts, but an order that is already balanced has nothing left to post - closing it
 * is the only remaining action, and the tab carries no DocAction field to do it with. This is that action,
 * in bulk.
 */
public class PP_Order_CloseSelection extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

	private int countClosed = 0;
	private int countFailed = 0;
	@Nullable private String firstFailureMessage = null;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		if (createSelection() <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}
	}

	private int createSelection()
	{
		final IQueryBuilder<I_PP_Order> queryBuilder = createCompletedOrdersQueryBuilder();

		final PInstanceId adPInstanceId = getPinstanceId();

		Check.assumeNotNull(adPInstanceId, "adPInstanceId is not null");

		DB.deleteT_Selection(adPInstanceId, ITrx.TRXNAME_ThreadInherited);

		return queryBuilder
				.create()
				.createSelection(adPInstanceId);
	}

	@NonNull
	private IQueryBuilder<I_PP_Order> createCompletedOrdersQueryBuilder()
	{
		final IQueryFilter<I_PP_Order> userSelectionFilter = getProcessInfo().getQueryFilterOrElse(null);

		if (userSelectionFilter == null)
		{
			throw new AdempiereException("@NoSelection@");
		}

		return queryBL
				.createQueryBuilder(I_PP_Order.class, getCtx(), ITrx.TRXNAME_None)
				// The DocStatus guard belongs HERE, in the query that materializes the selection - not in the
				// close loop. The user's selection comes from a view the client may have rendered a while ago,
				// so a Drafted / Voided / already-Closed order can still be in it.
				.addEqualsFilter(I_PP_Order.COLUMNNAME_DocStatus, DocStatus.Completed)
				.filter(userSelectionFilter)
				.addOnlyActiveRecordsFilter();
	}

	/**
	 * Runs out of transaction so each order can be closed in a transaction of its own: one order that refuses
	 * to close must neither abort the batch nor roll back the orders already closed.
	 */
	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final Iterator<I_PP_Order> ppOrders = queryBL
				.createQueryBuilder(I_PP_Order.class)
				.setOnlySelection(getPinstanceId())
				.orderBy(I_PP_Order.COLUMNNAME_PP_Order_ID) // predictable order, so "the first failure" is reproducible
				.create()
				.iterate(I_PP_Order.class);

		while (ppOrders.hasNext())
		{
			close(ppOrders.next());
		}

		final String summary = "@Processed@ (OK=#" + countClosed + ", Error=#" + countFailed + ")";
		if (countFailed <= 0)
		{
			return summary;
		}

		final String summaryWithFirstFailure = summary + ": " + firstFailureMessage;
		if (countClosed > 0)
		{
			// Partial success is reported as a success carrying the counts and the first failure's message:
			// the orders that did close are committed, so failing the process here would misreport them.
			// Every failure is additionally in the process log, one line per order.
			return summaryWithFirstFailure;
		}

		// Nothing closed at all - the run achieved nothing, so it must not come back as a green tick.
		// Note that returning MSG_Error would NOT do it: JavaProcess only treats the result as an error when
		// it equals "@Error@" exactly, so a message-carrying result has to be thrown.
		throw new AdempiereException(summaryWithFirstFailure);
	}

	private void close(@NonNull final I_PP_Order ppOrder)
	{
		try
		{
			trxManager.runInNewTrx((TrxRunnable)localTrxName -> {
				InterfaceWrapperHelper.refresh(ppOrder, localTrxName);
				ppOrderBL.closeOrder(ppOrder);

				// closeOrder() runs the document action without asserting the outcome, so a close that
				// silently did not take effect would otherwise be counted as a success.
				final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(ppOrder.getDocStatus());
				if (!docStatus.isClosed())
				{
					throw new AdempiereException("@Invalid@ @DocStatus@: " + docStatus);
				}
			});

			countClosed++;
			addLog("PP_Order {}: closed", ppOrder.getDocumentNo());
		}
		catch (final RuntimeException e)
		{
			countFailed++;

			final String failureMessage = AdempiereException.extractMessage(e);
			if (firstFailureMessage == null)
			{
				firstFailureMessage = ppOrder.getDocumentNo() + ": " + failureMessage;
			}

			addLog("PP_Order {}: failed - {}", ppOrder.getDocumentNo(), failureMessage);
			log.warn("Failed closing PP_Order {}", ppOrder.getPP_Order_ID(), e);
		}
	}
}

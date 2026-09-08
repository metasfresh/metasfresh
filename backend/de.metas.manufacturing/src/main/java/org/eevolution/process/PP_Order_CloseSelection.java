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
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderCloseResult;
import org.eevolution.model.I_PP_Order;

/**
 * Closes the completed manufacturing orders of the current selection, each one independently of the others.
 */
public class PP_Order_CloseSelection extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

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
				// The DocStatus guard belongs in the query that materializes the selection, not in the close
				// loop: the user's selection comes from a client-side view that may be stale.
				.addEqualsFilter(I_PP_Order.COLUMNNAME_DocStatus, DocStatus.Completed)
				.filter(userSelectionFilter)
				.addOnlyActiveRecordsFilter();
	}

	/**
	 * Runs out of transaction because the orders are closed one transaction at a time.
	 */
	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final PPOrderCloseResult result = ppOrderBL.closeOrdersInSelection(getPinstanceId());

		final String summary = "@Processed@ (OK=#" + result.getCountClosed() + ", Error=#" + result.getCountFailed() + ")";
		if (result.getCountFailed() <= 0)
		{
			return summary;
		}

		final String summaryWithFirstFailure = summary + ": " + result.getFirstFailureMessage();
		if (result.getCountClosed() > 0)
		{
			// Partial success stays a success: the orders that did close are committed already.
			return summaryWithFirstFailure;
		}

		// Thrown, not returned: a returned result counts as an error only when it is exactly "@Error@",
		// which would drop the message.
		throw new AdempiereException(summaryWithFirstFailure);
	}
}

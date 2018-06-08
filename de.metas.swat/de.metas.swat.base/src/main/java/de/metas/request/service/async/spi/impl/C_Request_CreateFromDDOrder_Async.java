package de.metas.request.service.async.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_OrderLine;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.request.api.IRequestDAO;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class C_Request_CreateFromDDOrder_Async extends WorkpackageProcessorAdapter
{

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{// Services
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IRequestDAO requestDAO = Services.get(IRequestDAO.class);

		// retrieve the items (DDOrder lines) that were enqueued and put them in a list
		final List<I_DD_OrderLine> lines = queueDAO.retrieveItems(workPackage, I_DD_OrderLine.class, localTrxName);

		// for each line that was enqueued, create a R_Request containing the information from the DDOrder line and DDOrder
		for (final I_DD_OrderLine line : lines)
		{
			requestDAO.createRequestFromDDOrderLine(line);
		}

		return Result.SUCCESS;
	}

	public static void createWorkpackage(final List<Integer> ddOrderLineIds)
	{
		if (Check.isEmpty(ddOrderLineIds))
		{
			// no lines to process
			return;
		}

		for (final Integer ddOrderLineId : ddOrderLineIds)
		{
			if (ddOrderLineId == null || ddOrderLineId <= 0)
			{
				// should not happen
				continue;
			}

			final I_DD_OrderLine ddOrderLine = load(ddOrderLineId, I_DD_OrderLine.class);

			// Schedule the request creation based on the given DDOrderline id
			SCHEDULER.schedule(ddOrderLine);
		}

	}

	private static final WorkpackagesOnCommitSchedulerTemplate<I_DD_OrderLine> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<I_DD_OrderLine>(C_Request_CreateFromDDOrder_Async.class)
	{
		@Override
		protected boolean isEligibleForScheduling(final I_DD_OrderLine model)
		{
			return model != null && model.getDD_OrderLine_ID() > 0;
		};

		@Override
		protected Properties extractCtxFromItem(final I_DD_OrderLine item)
		{
			return Env.getCtx();
		}

		@Override
		protected String extractTrxNameFromItem(final I_DD_OrderLine item)
		{
			return ITrx.TRXNAME_ThreadInherited;
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final I_DD_OrderLine item)
		{
			return new TableRecordReference(I_DD_OrderLine.Table_Name, item.getDD_OrderLine_ID());
		}
	};

}

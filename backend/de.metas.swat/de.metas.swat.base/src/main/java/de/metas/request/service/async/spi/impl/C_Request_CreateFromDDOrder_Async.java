/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.request.service.async.spi.impl;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.request.api.IRequestBL;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_OrderLine;

import java.util.List;
import java.util.Properties;

public class C_Request_CreateFromDDOrder_Async extends WorkpackageProcessorAdapter
{

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{// Services
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IRequestBL requestBL = Services.get(IRequestBL.class);

		// retrieve the items (DDOrder lines) that were enqueued and put them in a list
		final List<I_DD_OrderLine> lines = queueDAO.retrieveAllItems(workPackage, I_DD_OrderLine.class);

		// for each line that was enqueued, create a R_Request containing the information from the DDOrder line and DDOrder
		for (final I_DD_OrderLine line : lines)
		{
			requestBL.createRequestFromDDOrderLine(line);
		}

		return Result.SUCCESS;
	}

	public static void createWorkpackage(final List<DDOrderLineId> ddOrderLineIds)
	{
		SCHEDULER.scheduleAll(ddOrderLineIds);
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<DDOrderLineId> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<DDOrderLineId>(C_Request_CreateFromDDOrder_Async.class)
	{
		@Override
		protected boolean isEligibleForScheduling(final DDOrderLineId ddOrderLineId)
		{
			return ddOrderLineId != null;
		}

		@Override
		protected Properties extractCtxFromItem(final DDOrderLineId ddOrderLineId)
		{
			return Env.getCtx();
		}

		@Override
		protected String extractTrxNameFromItem(final DDOrderLineId ddOrderLineId)
		{
			return ITrx.TRXNAME_ThreadInherited;
		}

		@Override
		protected TableRecordReference extractModelToEnqueueFromItem(final Collector collector, final DDOrderLineId ddOrderLineId)
		{
			return TableRecordReference.of(I_DD_OrderLine.Table_Name, ddOrderLineId);
		}
	};

}

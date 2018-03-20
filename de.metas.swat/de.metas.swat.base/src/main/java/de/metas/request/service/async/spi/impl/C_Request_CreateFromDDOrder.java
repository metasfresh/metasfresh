package de.metas.request.service.async.spi.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_DD_OrderLine;

import com.google.common.base.MoreObjects;

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

public class C_Request_CreateFromDDOrder extends WorkpackageProcessorAdapter
{

	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workPackage, String localTrxName)
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

	public static void createWorkpackage(Properties ctx, List<Integer> ddOrderLineIds, String trxName)
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

			// Schedule the request creation based on the given DDOrderline ids
			SCHEDULER.schedule(DDOrderLineToBlock.of(ctx, ddOrderLineId, trxName));
		}
		
	}
	
	private static final WorkpackagesOnCommitSchedulerTemplate<DDOrderLineToBlock> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<DDOrderLineToBlock>(C_Request_CreateFromDDOrder.class)
	{
		@Override
		protected boolean isEligibleForScheduling(final DDOrderLineToBlock model)
		{
			return model != null && model.getDD_OrderLine_ID() > 0;
		};

		@Override
		protected Properties extractCtxFromItem(final DDOrderLineToBlock item)
		{
			return item.getCtx();
		}

		@Override
		protected String extractTrxNameFromItem(final DDOrderLineToBlock item)
		{
			return item.getTrxName();
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final DDOrderLineToBlock item)
		{
			return new TableRecordReference(I_DD_OrderLine.Table_Name, item.getDD_OrderLine_ID());
		}
	};
	
	private static final class DDOrderLineToBlock
	{
		public static DDOrderLineToBlock of(Properties ctx, int ddOrderLineId, String trxName)
		{
			return new DDOrderLineToBlock(ctx, ddOrderLineId, trxName);
		}

		private final Properties ctx;
		private final String trxName;
		private final int ddOrderLineId;

		private DDOrderLineToBlock(Properties ctx, int ddOrderLineId, String trxName)
		{
			super();
			this.ctx = ctx;
			this.ddOrderLineId = ddOrderLineId;
			this.trxName = trxName;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("DDOrderLineId", ddOrderLineId)
					.toString();
		}

		public Properties getCtx()
		{
			return ctx;
		}

		public String getTrxName()
		{
			return trxName;
		}

		public int getDD_OrderLine_ID()
		{
			return ddOrderLineId;
		}
	}

}

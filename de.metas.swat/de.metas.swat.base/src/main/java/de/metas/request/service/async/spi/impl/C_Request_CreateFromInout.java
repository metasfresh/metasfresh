package de.metas.request.service.async.spi.impl;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.base.MoreObjects;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.request.api.IRequestDAO;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class C_Request_CreateFromInout extends WorkpackageProcessorAdapter
{
	public static void createWorkpackage(final Properties ctx, final Set<Integer> inOutLineIds, final String trxName)
	{
		if (inOutLineIds == null || inOutLineIds.isEmpty())
		{
			return;
		}

		for (final Integer inOutLineId : inOutLineIds)
		{
			if (inOutLineId == null || inOutLineId <= 0)
			{
				continue;
			}

			SCHEDULER.schedule(InOutLineWithQualityNotice.of(ctx, inOutLineId, trxName));
		}
	}

	private static final class InOutLineWithQualityNotice
	{
		public static InOutLineWithQualityNotice of(Properties ctx, int inOutLineId, String trxName)
		{
			return new InOutLineWithQualityNotice(ctx, inOutLineId, trxName);
		}

		private final Properties ctx;
		private final String trxName;
		private final int inOutLineId;

		private InOutLineWithQualityNotice(Properties ctx, int inOutLineId, String trxName)
		{
			super();
			this.ctx = ctx;
			this.inOutLineId = inOutLineId;
			this.trxName = trxName;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("inOutLineId", inOutLineId)
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

		public int getM_InOutLine_ID()
		{
			return inOutLineId;
		}
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<InOutLineWithQualityNotice> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<InOutLineWithQualityNotice>(C_Request_CreateFromInout.class)
	{
		@Override
		protected boolean isEligibleForScheduling(final InOutLineWithQualityNotice model)
		{
			return model != null && model.getM_InOutLine_ID() > 0;
		};

		@Override
		protected Properties extractCtxFromItem(final InOutLineWithQualityNotice item)
		{
			return item.getCtx();
		}

		@Override
		protected String extractTrxNameFromItem(final InOutLineWithQualityNotice item)
		{
			return item.getTrxName();
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final InOutLineWithQualityNotice item)
		{
			return new TableRecordReference(I_M_InOutLine.Table_Name, item.getM_InOutLine_ID());
		}
	};

	
	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workPackage, String localTrxName)
	{
		// Services
				final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
				final IRequestDAO requestDAO = Services.get(IRequestDAO.class);

				final List<I_M_InOutLine> lines = queueDAO.retrieveItems(workPackage, I_M_InOutLine.class, localTrxName);

				for (final I_M_InOutLine line : lines)
				{
					requestDAO.createRequestFromInOutLine(line);
				}

				return Result.SUCCESS;
	}

}

package de.metas.request.service.async.spi.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class C_Request_CreateFromInout_Async extends WorkpackageProcessorAdapter
{
	/**
	 * 
	 * Schedule the request creation based on the given inoutline ids
	 * 
	 * These requests will contain information taken from the lines and from their inout headers:
	 * <li>inout
	 * <li>product
	 * <li>partner
	 * <li>dateDelivered
	 * <li>qualityNotice
	 * <li>org
	 * <li>linked salesrep of the org, etc.
	 */
	public static void createWorkpackage(final List<Integer> inOutLineIds)
	{
		if (inOutLineIds == null || inOutLineIds.isEmpty())
		{
			// no lines to process
			return;
		}

		for (final Integer inOutLineId : inOutLineIds)
		{
			if (inOutLineId == null || inOutLineId <= 0)
			{
				// should not happen
				continue;
			}

			// Schedule the request creation based on the given inoutline ids
			SCHEDULER.schedule(InOutLineWithQualityIssues.of(inOutLineId));
		}
	}

	/**
	 * Class to keep information about the inout lines with quality issues (Quality Discount Percent).
	 * This model will be used ion the
	 * 
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	private static final class InOutLineWithQualityIssues
	{
		public static InOutLineWithQualityIssues of(int inOutLineId)
		{
			return new InOutLineWithQualityIssues(inOutLineId);
		}

		private final int inOutLineId;

		private InOutLineWithQualityIssues(int inOutLineId)
		{
			super();
			this.inOutLineId = inOutLineId;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("inOutLineId", inOutLineId)
					.toString();
		}

		public int getM_InOutLine_ID()
		{
			return inOutLineId;
		}
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<InOutLineWithQualityIssues> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<InOutLineWithQualityIssues>(C_Request_CreateFromInout_Async.class)
	{
		@Override
		protected boolean isEligibleForScheduling(final InOutLineWithQualityIssues model)
		{
			return model != null && model.getM_InOutLine_ID() > 0;
		};

		@Override
		protected Properties extractCtxFromItem(final InOutLineWithQualityIssues item)
		{
			return Env.getCtx();
		}

		@Override
		protected String extractTrxNameFromItem(final InOutLineWithQualityIssues item)
		{
			return ITrx.TRXNAME_ThreadInherited;
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final InOutLineWithQualityIssues item)
		{
			return new TableRecordReference(I_M_InOutLine.Table_Name, item.getM_InOutLine_ID());
		}
	};

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		// Services
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IRequestDAO requestDAO = Services.get(IRequestDAO.class);

		// retrieve the items (inout lines) that were enqueued and put them in a list
		final List<I_M_InOutLine> lines = queueDAO.retrieveItems(workPackage, I_M_InOutLine.class, localTrxName);

		// for each line that was enqueued, create a R_Request containing the information from the inout line and inout
		for (final I_M_InOutLine line : lines)
		{
			requestDAO.createRequestFromInOutLine(line);
		}

		return Result.SUCCESS;
	}

}

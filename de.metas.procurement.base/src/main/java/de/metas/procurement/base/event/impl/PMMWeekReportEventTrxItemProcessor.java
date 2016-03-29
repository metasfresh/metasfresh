package de.metas.procurement.base.event.impl;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;

import de.metas.lock.api.ILockManager;
import de.metas.procurement.base.IPMMWeekDAO;
import de.metas.procurement.base.model.I_PMM_Week;
import de.metas.procurement.base.model.I_PMM_WeekReport_Event;

/*
 * #%L
 * de.metas.procurement.base
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

class PMMWeekReportEventTrxItemProcessor extends TrxItemProcessorAdapter<I_PMM_WeekReport_Event, Void>
{
	// services
	private final transient IPMMWeekDAO purchaseCandidateDAO = Services.get(IPMMWeekDAO.class);
	private final transient ILockManager lockManager = Services.get(ILockManager.class);

	private static final String ERRORMSG_None = null;

	private final AtomicInteger countProcessed = new AtomicInteger(0);
	private final AtomicInteger countSkipped = new AtomicInteger(0);

	@Override
	public void process(final I_PMM_WeekReport_Event event)
	{
		//
		// Get aggregate
		final Timestamp weekDate = TimeUtil.trunc(event.getWeekDate(), TimeUtil.TRUNC_WEEK);
		I_PMM_Week aggregate = purchaseCandidateDAO.retrieveFor(event.getC_BPartner_ID(), event.getM_Product_ID(), event.getM_HU_PI_Item_Product_ID(), weekDate);

		//
		// If candidate is currently locked, skip processing this event for now
		if (aggregate != null && lockManager.isLocked(aggregate))
		{
			final String errorMsg = "Skip processing event because candidate is currently locked: " + aggregate;
			markSkipped(event, aggregate, errorMsg);
			return;
		}

		if (aggregate != null && isProcessedByFutureEvent(aggregate, event))
		{
			final String errorMsg = "Skipped because candidate " + aggregate + " was already processed by future event: " + aggregate.getLast_WeekReport_Event_ID();
			markProcessed(event, aggregate, errorMsg);
			return;
		}

		//
		// If no candidate found, create a new candidate
		if (aggregate == null)
		{
			aggregate = InterfaceWrapperHelper.newInstance(I_PMM_Week.class);
			aggregate.setC_BPartner_ID(event.getC_BPartner_ID());
			aggregate.setM_Product_ID(event.getM_Product_ID());
			aggregate.setM_HU_PI_Item_Product_ID(event.getM_HU_PI_Item_Product_ID());
			aggregate.setWeekDate(weekDate);

			aggregate.setAD_Org_ID(event.getAD_Org_ID());
		}

		//
		// Update the candidate with the values from event
		aggregate.setPMM_Trend(event.getPMM_Trend());
		InterfaceWrapperHelper.save(aggregate);

		//
		// Mark the event as processed
		markProcessed(event, aggregate, ERRORMSG_None);
	}

	private final void markProcessed(final I_PMM_WeekReport_Event event, final I_PMM_Week aggregate, final String errorMsg)
	{
		event.setPMM_Week(aggregate);
		event.setProcessed(true);
		event.setErrorMsg(errorMsg);
		InterfaceWrapperHelper.save(event);

		if (errorMsg != null)
		{
			getLoggable().addLog("Event " + event + " marked as processed with warnings: " + errorMsg);
		}

		countProcessed.incrementAndGet();
	}

	private final void markSkipped(final I_PMM_WeekReport_Event event, final I_PMM_Week aggregate, final String errorMsg)
	{
		event.setErrorMsg(errorMsg);
		InterfaceWrapperHelper.save(event);

		if (errorMsg != null)
		{
			getLoggable().addLog("Event " + event + " skipped: " + errorMsg);
		}

		countSkipped.incrementAndGet();

	}

	private boolean isProcessedByFutureEvent(final I_PMM_Week aggregate, final I_PMM_WeekReport_Event currentEvent)
	{
		if (aggregate == null)
		{
			return false;
		}

		final int lastEventId = aggregate.getLast_WeekReport_Event_ID();
		if (lastEventId > currentEvent.getPMM_WeekReport_Event_ID())
		{
			return true;
		}

		return false;
	}

	private final ILoggable getLoggable()
	{
		return ILoggable.THREADLOCAL.getLoggable();
	}

	public String getProcessSummary()
	{
		return "@Processed@ #" + countProcessed.get() + ", @Skipped@ #" + countSkipped.get();
	}
}

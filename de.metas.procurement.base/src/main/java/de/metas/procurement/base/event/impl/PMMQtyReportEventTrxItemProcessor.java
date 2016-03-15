package de.metas.procurement.base.event.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;

import de.metas.lock.api.ILockManager;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;
import de.metas.procurement.base.order.IPMMPurchaseCandidateBL;
import de.metas.procurement.base.order.IPMMPurchaseCandidateDAO;

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

class PMMQtyReportEventTrxItemProcessor extends TrxItemProcessorAdapter<I_PMM_QtyReport_Event, Void>
{
	// services
	private final transient IPMMPurchaseCandidateDAO purchaseCandidateDAO = Services.get(IPMMPurchaseCandidateDAO.class);
	private final transient IPMMPurchaseCandidateBL purchaseCandidateBL = Services.get(IPMMPurchaseCandidateBL.class);
	private final transient ILockManager lockManager = Services.get(ILockManager.class);

	private static final String ERRORMSG_None = null;

	private final AtomicInteger countProcessed = new AtomicInteger(0);
	private final AtomicInteger countSkipped = new AtomicInteger(0);

	@Override
	public void process(final I_PMM_QtyReport_Event event)
	{
		//
		// Get candidate
		I_PMM_PurchaseCandidate candidate = purchaseCandidateDAO.retrieveFor(event.getC_BPartner_ID(), event.getM_Product_ID(), event.getDatePromised());

		//
		// If candidate is currently locked, skip processing this event for now
		if (candidate != null && lockManager.isLocked(candidate))
		{
			final String errorMsg = "Skip processing event because candidate is currently locked: " + candidate;
			markSkipped(event, candidate, errorMsg);
			return;
		}

		if (candidate != null && isProcessedByFutureEvent(candidate, event))
		{
			final String errorMsg = "Skipped because candidate " + candidate + " was already processed by future event: " + candidate.getLast_QtyReport_Event_ID();
			markProcessed(event, candidate, errorMsg);
			return;
		}

		try
		{
			//
			// If no candidate found, create a new candidate
			if (candidate == null)
			{
				candidate = InterfaceWrapperHelper.newInstance(I_PMM_PurchaseCandidate.class);
				candidate.setC_BPartner_ID(event.getC_BPartner_ID());
				candidate.setM_Product_ID(event.getM_Product_ID());
				candidate.setC_UOM_ID(event.getC_UOM_ID());
				candidate.setM_HU_PI_Item_Product_ID(event.getM_HU_PI_Item_Product_ID());
				candidate.setDatePromised(event.getDatePromised());

				candidate.setAD_Org_ID(event.getAD_Org_ID());
				if (event.getM_Warehouse_ID() > 0)
				{
					candidate.setM_Warehouse_ID(event.getM_Warehouse_ID());
				}
				candidate.setM_PricingSystem_ID(event.getM_PricingSystem_ID());
				candidate.setM_PriceList_ID(event.getM_PriceList_ID());
				candidate.setC_Currency_ID(event.getC_Currency_ID());
				candidate.setPrice(event.getPrice());
			}

			//
			// Update the candidate with the values from event
			candidate.setQtyPromised(event.getQtyPromised());
			candidate.setQtyToOrder(purchaseCandidateBL.calculateQtyToOrder(candidate));
			InterfaceWrapperHelper.save(candidate);
		}
		catch (Exception e)
		{
			markProcessed(event, candidate, e.getLocalizedMessage());
			throw e;
		}
		//
		// Mark the event as processed
		markProcessed(event, candidate, ERRORMSG_None);
	}

	private final void markProcessed(final I_PMM_QtyReport_Event event, final I_PMM_PurchaseCandidate candidate, final String errorMsg)
	{
		event.setPMM_PurchaseCandidate(candidate);
		
		final boolean isError = !Check.equals(ERRORMSG_None, errorMsg);
		event.setIsError(isError);
		event.setProcessed(!isError);
		event.setErrorMsg(errorMsg);
		InterfaceWrapperHelper.save(event);

		if (errorMsg != null)
		{
			getLoggable().addLog("Event " + event + " marked as processed with warnings: " + errorMsg);
		}

		countProcessed.incrementAndGet();
	}

	private final void markSkipped(final I_PMM_QtyReport_Event event, final I_PMM_PurchaseCandidate candidate, final String errorMsg)
	{
		event.setErrorMsg(errorMsg);
		InterfaceWrapperHelper.save(event);

		if (errorMsg != null)
		{
			getLoggable().addLog("Event " + event + " skipped: " + errorMsg);
		}
		countSkipped.incrementAndGet();

	}

	private boolean isProcessedByFutureEvent(final I_PMM_PurchaseCandidate candidate, final I_PMM_QtyReport_Event currentEvent)
	{
		if (candidate == null)
		{
			return false;
		}

		final int lastQtyReportEventId = candidate.getLast_QtyReport_Event_ID();
		if (lastQtyReportEventId > currentEvent.getPMM_QtyReport_Event_ID())
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

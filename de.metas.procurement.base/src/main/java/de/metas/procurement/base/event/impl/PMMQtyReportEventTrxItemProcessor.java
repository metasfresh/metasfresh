package de.metas.procurement.base.event.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.lock.api.ILockManager;
import de.metas.logging.LogManager;
import de.metas.procurement.base.IPMMContractsBL;
import de.metas.procurement.base.IPMMContractsDAO;
import de.metas.procurement.base.IPMMPricingAware;
import de.metas.procurement.base.IPMMPricingBL;
import de.metas.procurement.base.balance.IPMMBalanceChangeEventProcessor;
import de.metas.procurement.base.balance.PMMBalanceChangeEvent;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;
import de.metas.procurement.base.order.IPMMPurchaseCandidateBL;
import de.metas.procurement.base.order.IPMMPurchaseCandidateDAO;
import de.metas.procurement.base.order.PMMPurchaseCandidateSegment;

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

/**
 * Processes {@link I_PMM_QtyReport_Event}s and creates/updates {@link I_PMM_PurchaseCandidate}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class PMMQtyReportEventTrxItemProcessor extends TrxItemProcessorAdapter<I_PMM_QtyReport_Event, Void>
{
	// services
	private static final Logger logger = LogManager.getLogger(PMMQtyReportEventTrxItemProcessor.class);
	private final transient ILockManager lockManager = Services.get(ILockManager.class);
	private final transient IPMMPurchaseCandidateDAO purchaseCandidateDAO = Services.get(IPMMPurchaseCandidateDAO.class);
	private final transient IPMMPurchaseCandidateBL purchaseCandidateBL = Services.get(IPMMPurchaseCandidateBL.class);
	private final transient IPMMBalanceChangeEventProcessor pmmBalanceEventProcessor = Services.get(IPMMBalanceChangeEventProcessor.class);
	private final transient IPMMPricingBL pmmPricingBL = Services.get(IPMMPricingBL.class);
	private final transient IPMMContractsDAO pmmContractsDAO = Services.get(IPMMContractsDAO.class);
	private final transient IPMMContractsBL pmmContractsBL = Services.get(IPMMContractsBL.class);

	private final AtomicInteger countProcessed = new AtomicInteger(0);
	private final AtomicInteger countErrors = new AtomicInteger(0);
	private final AtomicInteger countSkipped = new AtomicInteger(0);

	/**
	 * Creates/Updates a {@link I_PMM_PurchaseCandidate} for the given <code>event</code> <b>and also</b> updates the given <code>event</code>'s pricing.
	 */
	@Override
	public void process(final I_PMM_QtyReport_Event event)
	{
		updateC_Flatrate_DataEntry(event);

		//
		// Create the aggregation segment
		final PMMPurchaseCandidateSegment pmmSegment = PMMPurchaseCandidateSegment.builder()
				.setC_BPartner_ID(event.getC_BPartner_ID())
				.setM_Product_ID(event.getM_Product_ID())
				.setM_HU_PI_Item_Product_ID(event.getM_HU_PI_Item_Product_ID())
				.setM_AttributeSetInstance_ID(event.getM_AttributeSetInstance_ID())
				.setC_Flatrate_DataEntry_ID(event.getC_Flatrate_DataEntry_ID())
				.build();

		final Timestamp datePromised = event.getDatePromised();
		I_PMM_PurchaseCandidate candidate;

		//
		// Weekly planning
		if (event.isPlanning())
		{
			candidate = null;
			if (purchaseCandidateDAO.hasRecordsForWeek(pmmSegment, datePromised))
			{
				markProcessed(event, candidate);
				return;
			}
		}
		//
		// Concrete QtyReport
		else
		{
			candidate = purchaseCandidateDAO.retrieveFor(pmmSegment, datePromised);
		}

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

				// Segment values
				candidate.setC_BPartner_ID(pmmSegment.getC_BPartner_ID());
				candidate.setM_Product_ID(pmmSegment.getM_Product_ID());
				if (pmmSegment.getM_AttributeSetInstance_ID() > 0)
				{
					candidate.setM_AttributeSetInstance_ID(pmmSegment.getM_AttributeSetInstance_ID());
				}
				candidate.setM_HU_PI_Item_Product_ID(pmmSegment.getM_HU_PI_Item_Product_ID());
				if (pmmSegment.getC_Flatrate_DataEntry_ID() > 0)
				{
					candidate.setC_Flatrate_DataEntry_ID(pmmSegment.getC_Flatrate_DataEntry_ID());
				}

				candidate.setC_UOM_ID(event.getC_UOM_ID());
				candidate.setDatePromised(datePromised);

				candidate.setAD_Org_ID(event.getAD_Org_ID());

				if (event.getM_Warehouse_ID() > 0)
				{
					candidate.setM_Warehouse_ID(event.getM_Warehouse_ID());
				}

				//
				// Pricing
				updatePricing(event);
				candidate.setM_PricingSystem_ID(event.getM_PricingSystem_ID());
				candidate.setM_PriceList_ID(event.getM_PriceList_ID());
				candidate.setC_Currency_ID(event.getC_Currency_ID());
				candidate.setPrice(event.getPrice());
			}

			//
			// Update event and capture the old candidate values
			// NOTE: these fields are critical for PMM_Balance
			final BigDecimal candidate_QtyPromised = candidate.getQtyPromised();
			final BigDecimal candidate_QtyPromisedTU = candidate.getQtyPromised_TU();
			event.setQtyPromised_Old(candidate_QtyPromised);
			event.setQtyPromised_TU_Old(candidate_QtyPromisedTU);

			//
			// Update the candidate with the values from event
			purchaseCandidateBL.setQtyPromised(candidate, event.getQtyPromised(), event.getQtyPromised_TU());
			// NOTE: as per FRESH-141 we shall not touch the QtyToOrder fields
			InterfaceWrapperHelper.save(candidate);

			//
			// Update PMM_Balance
			pmmBalanceEventProcessor.addEvent(createPMMBalanceChangeEvent(event));

			//
			// Mark the event as processed
			markProcessed(event, candidate);
		}
		catch (final Exception e)
		{
			markError(event, e);
		}
	}

	private final void updatePricing(final I_PMM_QtyReport_Event qtyReportEvent)
	{
		// Non-contract product: fetch price from pricing system
		final IPMMPricingAware pricingAware = PMMPricingAware_QtyReportEvent.of(qtyReportEvent);
		pmmPricingBL.updatePricing(pricingAware);
	}

	private void updateC_Flatrate_DataEntry(final I_PMM_QtyReport_Event qtyReportEvent)
	{
		final I_C_Flatrate_Term flatrateTerm = qtyReportEvent.getC_Flatrate_Term();
		if (flatrateTerm == null)
		{
			// we are called, because qtyReportEvent has a contractLine_uuid. So if there is no term then something is wrong
			return;
		}

		final Timestamp datePromised = qtyReportEvent.getDatePromised();

		final I_C_Flatrate_DataEntry dataEntryForProduct = pmmContractsDAO.retrieveFlatrateDataEntry(flatrateTerm, datePromised);
		if (dataEntryForProduct == null)
		{
			return;
		}

		// Skip setting the data entry if it does not have the price or the qty set (FRESH-568)
		if (!pmmContractsBL.hasPriceOrQty(dataEntryForProduct))
		{
			logger.debug("Skip setting {} to {} because the data entry does not have a price or qty set", dataEntryForProduct, qtyReportEvent);
			return;
		}

		qtyReportEvent.setC_Flatrate_DataEntry(dataEntryForProduct);
	}

	private final void markProcessed(final I_PMM_QtyReport_Event event, final I_PMM_PurchaseCandidate candidate)
	{
		final String errorMsg = null; // no error message
		markProcessed(event, candidate, errorMsg);
	}

	private final void markProcessed(final I_PMM_QtyReport_Event event, final I_PMM_PurchaseCandidate candidate, final String errorMsg)
	{
		event.setPMM_PurchaseCandidate(candidate);

		event.setProcessed(true);
		event.setIsError(!Check.isEmpty(errorMsg, true));
		event.setErrorMsg(errorMsg);
		InterfaceWrapperHelper.save(event);

		countProcessed.incrementAndGet();
	}

	private final void markError(final I_PMM_QtyReport_Event event, final Throwable ex)
	{
		final String errorMsg = ex.getLocalizedMessage();

		InterfaceWrapperHelper.refresh(event, true);
		event.setIsError(true);
		event.setErrorMsg(errorMsg);
		event.setProcessed(false);
		InterfaceWrapperHelper.save(event);

		Loggables.get().addLog("Event " + event + " marked as processed with warnings: " + errorMsg);

		countErrors.incrementAndGet();
	}

	private final void markSkipped(final I_PMM_QtyReport_Event event, final I_PMM_PurchaseCandidate candidate, final String errorMsg)
	{
		event.setErrorMsg(errorMsg);
		InterfaceWrapperHelper.save(event);

		if (errorMsg != null)
		{
			Loggables.get().addLog("Event " + event + " skipped: " + errorMsg);
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

	public String getProcessSummary()
	{
		return "@Processed@ #" + countProcessed.get()
				+ ", @IsError@ #" + countErrors.get()
				+ ", @Skipped@ #" + countSkipped.get();
	}

	@VisibleForTesting
	static PMMBalanceChangeEvent createPMMBalanceChangeEvent(final I_PMM_QtyReport_Event qtyReportEvent)
	{
		final BigDecimal qtyPromisedDiff = qtyReportEvent.getQtyPromised().subtract(qtyReportEvent.getQtyPromised_Old());
		final BigDecimal qtyPromisedTUDiff = qtyReportEvent.getQtyPromised_TU().subtract(qtyReportEvent.getQtyPromised_TU_Old());
		final PMMBalanceChangeEvent event = PMMBalanceChangeEvent.builder()
				.setC_BPartner_ID(qtyReportEvent.getC_BPartner_ID())
				.setM_Product_ID(qtyReportEvent.getM_Product_ID())
				.setM_AttributeSetInstance_ID(qtyReportEvent.getM_AttributeSetInstance_ID())
				.setM_HU_PI_Item_Product_ID(qtyReportEvent.getM_HU_PI_Item_Product_ID())
				.setC_Flatrate_DataEntry_ID(qtyReportEvent.getC_Flatrate_DataEntry_ID())
				//
				.setDate(qtyReportEvent.getDatePromised())
				//
				.setQtyPromised(qtyPromisedDiff, qtyPromisedTUDiff)
				//
				.build();
		logger.trace("Created event {} from {}", event, qtyReportEvent);

		return event;
	}
}

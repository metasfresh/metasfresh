package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;

public class ShipmentScheduleUpdater implements IShipmentScheduleUpdater
{
	/**
	 * Flag which is set to true when shipment schedule updater is running.
	 *
	 * This information is stored on thread level.
	 */
	private final InheritableThreadLocal<Boolean> running = new InheritableThreadLocal<>();

	private static final Logger logger = LogManager.getLogger(ShipmentScheduleUpdater.class);

	@Override
	public int updateShipmentSchedule(final Properties ctx, final int adClientId, final int adUserId, final int adPInstanceId, final String trxName)
	{
		final boolean updateOnlyLocked = false;
		return updateShipmentSchedule(ctx, adClientId, adUserId, adPInstanceId, updateOnlyLocked, trxName);
	}

	@Override
	public int updateShipmentSchedule(
			final Properties ctx,
			final int adClientId,
			final int adUserId,
			final int adPInstanceId,
			final boolean updateOnlyLocked,
			final String trxName)
	{
		// services
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		final Boolean running = this.running.get();
		Check.assume(running == null || running == false, "updateShipmentSchedule is not already running");
		this.running.set(true);

		try
		{
			shipmentSchedulePA.deleteSchedulesWithOutOl(trxName);

			if (!updateOnlyLocked)
			{
				//
				// Create and invalidate missing shipment schedules
				final List<I_M_ShipmentSchedule> shipmentSchedulesNew = Services.get(IShipmentScheduleHandlerBL.class).createMissingCandidates(ctx, trxName);
				shipmentSchedulePA.invalidate(shipmentSchedulesNew, trxName);
			}

			final List<Integer> processedShipmentRunIds = shipmentSchedulePA.retrieveProcessedShipmentRunIds(trxName);

			final List<OlAndSched> collectResult = retrieveOlsAndSchedsToProcess(ctx, adClientId, adPInstanceId, updateOnlyLocked, trxName);

			logger.debug("Invoking shipmentScheduleBL to update {} shipment schedule entries.", collectResult.size());
			shipmentScheduleBL.updateSchedules(ctx, collectResult, trxName);

			// cleanup the marker/pointer tables
			shipmentSchedulePA.deleteRecomputeMarkers(adPInstanceId, trxName); // if updateOnlyLocked, then there is nothing to delete..but still making this call, it should finish rather quickly
			shipmentSchedulePA.deleteProcessedShipmentRunIds(processedShipmentRunIds, trxName);

			logger.debug("Done");
			return collectResult.size();
		}
		finally
		{
			try
			{
				// Make sure the recompute tag is released, just in case any error occurs.
				// Usually zero records will be deleted because, if everything goes fine, the tagged recompute records were already deleted.
				shipmentSchedulePA.releaseRecomputeMarker(adPInstanceId, trxName);
			}
			finally
			{
				this.running.set(false);
			}
		}
	}

	@Override
	public boolean isRunning()
	{
		final Boolean running = this.running.get();
		return running != null && running == true;
	}

	private final List<OlAndSched> retrieveOlsAndSchedsToProcess(
			final Properties ctx,
			final int adClientId,
			final int adPinstanceId,
			final boolean retrieveOnlyLocked,
			final String trxName)
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final List<OlAndSched> olsAndScheds = shipmentSchedulePA.retrieveInvalid(adClientId, adPinstanceId, retrieveOnlyLocked, trxName);

		if (olsAndScheds.isEmpty())
		{
			logger.debug("There are no shipment schedule entries to update");
			return Collections.emptyList();
		}
		logger.debug("Found {} invalid shipment schedule entries", olsAndScheds.size());

		final List<OlAndSched> collectResult = new ArrayList<>();
		collectResult.addAll(olsAndScheds);

		logger.debug("Found additional {} schedule entries to update", (collectResult.size() - olsAndScheds.size()));
		return collectResult;
	}
}

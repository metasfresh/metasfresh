package de.metas.handlingunits.shipmentschedule.async;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import com.google.common.collect.ImmutableSet;

import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.ILatchStragegy;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;

/**
 * Makes sure that if a workpackage is locked that references shipment schedules with a certain header aggregation key, then no further workpackages which reference scheds with the same key can be
 * processed in parallel.
 * <p>
 * Goal: avoid shipment schedules with equal header aggregation keys from ending up in multiple shipments, just because the WP-processors were working in parallel and didn't know about each other.
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/09216_Async_-_Need_SPI_to_decide_if_packets_can_be_processed_in_parallel_of_not_%28106397206117%29
 */
public final class CreateShipmentLatch implements ILatchStragegy
{
	public static final CreateShipmentLatch INSTANCE = new CreateShipmentLatch();

	private static final transient Logger logger = LogManager.getLogger(CreateShipmentLatch.class);

	private CreateShipmentLatch()
	{
	}

	/**
	 * The workpackage processors of the WPs this latch cares about
	 */
	private static final Set<String> CLASSNAMES = ImmutableSet.<String> of(
			GenerateInOutFromShipmentSchedules.class.getName(),
			GenerateInOutFromHU.class.getName()
			);

	/**
	 * Notes:
	 * <ul>
	 * <li>only validates work packages that shall be processed *before* <code>currentWorkPackage</code> (higher prio or same prio and lower/earlier ID). That way, if two WPs' latches are validated at
	 * the same time (and there is not a 3rd one already in processing), not both of them are postponed, but the one with higher prio is actually processed.
	 * <li>If <code>currentWorkPackage</code> needs to be postponed, a {@link WorkpackageSkipRequestException} with a random timeout is thrown, to avoid one pair of WPs from repeatedly being postoned
	 * in tandem over and over again.
	 * </ul>
	 */
	@Override
	public void postponeIfNeeded(final I_C_Queue_WorkPackage currentWorkPackage,
			final IQueryBuilder<I_C_Queue_WorkPackage> currentlyLockedWorpackagesQueryBuilder)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final String trxName = InterfaceWrapperHelper.getTrxName(currentWorkPackage);

		// before doing anything else, we check if there are any locked work packages to validate at all
		final List<I_C_Queue_WorkPackage> lockedWPs = currentlyLockedWorpackagesQueryBuilder
				.create()
				.setOrderBy(queueDAO.getQueueOrderBy())
				.list(I_C_Queue_WorkPackage.class);
		if (!lockedWPs.isEmpty())
		{
			// as of now, this shall not happen (at least currentWorkPackage should be included),
			// but *if* the framework implementation is changed, that shall not be this implementors bother.
			logger.debug("no locked C_Queue_WorkPackages; returning (currentWorkPackage={})", currentWorkPackage);
			return; // nothing to do
		}
		final boolean currentWpIsHighestPrio = lockedWPs.get(0).getC_Queue_WorkPackage_ID() == currentWorkPackage.getC_Queue_WorkPackage_ID();
		if (currentWpIsHighestPrio)
		{
			// we only want to look for work packages that shall be processed *before* the current one (higher prio or same prio and lower/earlier ID).
			// that way, if two WPs' latches are validated at the same time (and there is not a 3rd one already in processing), not both of them are postponed.
			logger.debug("none of the locked C_Queue_WorkPackages' prio is higher than that of the current WP; returning (currentWorkPackage={})", currentWorkPackage);
			return; // nothing to do
		}

		// get the HeaderAggregationKeys of the shipment-scheds that we have in 'currentWorkPackage'
		final List<Object> distinctAggregationKeys;
		{
			final List<Map<String, Object>> distinctAggregationKeysFromQuery =
					queueDAO
							.createElementsQueryBuilder(currentWorkPackage,
									I_M_ShipmentSchedule.class,
									trxName)
							.create()
							.listDistinct(I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey);
			distinctAggregationKeys = new ArrayList<Object>(distinctAggregationKeysFromQuery.size());
			for (final Map<String, Object> record : distinctAggregationKeysFromQuery)
			{
				distinctAggregationKeys.add(record.get(I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey));
			}
			logger.debug("currentWorkPackage={} has M_ShipmentSchedules with these header aggregation keys: {})",
					new Object[] { currentWorkPackage, distinctAggregationKeys });
		}

		// iterate the locked work packages and check if any of them have a shipment schedule which has the same aggregation key that we have
		for (final I_C_Queue_WorkPackage lockedWP : lockedWPs)
		{
			if (lockedWP.getC_Queue_WorkPackage_ID() == currentWorkPackage.getC_Queue_WorkPackage_ID())
			{
				// we checked all WPs whose prio is higher than that of 'currentWorkPackage'
				logger.debug("All WPs with a prio higher than currentWorkPackage={} were checked; returning", currentWorkPackage);
				break;
			}

			final String lockedWpClassname = lockedWP.getC_Queue_Block().getC_Queue_PackageProcessor().getClassname();
			if (!CLASSNAMES.contains(lockedWpClassname))
			{
				logger.debug("currently-locked WP {} belongs to the WP-processor {}; continuing.", new Object[] { lockedWP, lockedWpClassname });
				continue; // this latch only cares for packages that belong to one of the inout-generating processors.
			}

			final boolean lockedWpHasElementWithSameKey = queueDAO
					.createElementsQueryBuilder(lockedWP,
							I_M_ShipmentSchedule.class,
							trxName)
					.addInArrayOrAllFilter(I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey, distinctAggregationKeys)
					.create()
					.match();

			if (lockedWpHasElementWithSameKey)
			{
				final String message = "Skipping because the currently-locked WP " + lockedWP + " also references a " + I_M_ShipmentSchedule.Table_Name
						+ " with one of the following " + I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey + "s:" + distinctAggregationKeys;

				final WorkpackageSkipRequestException skipRequestException = WorkpackageSkipRequestException.createWithRandomTimeout(message);

				logger.debug(message + "; throwing {}", skipRequestException);
				throw skipRequestException;
			}
		}
	}
}

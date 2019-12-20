package de.metas.location.geocoding.process;

import java.util.Arrays;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Location;
import org.compiere.model.X_C_Location;

import com.google.common.collect.ImmutableSet;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.location.LocationId;
import de.metas.location.geocoding.asynchandler.LocationGeocodeEventRequest;
import de.metas.location.geocoding.interceptor.C_Location;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class C_Location_Geocoding_ScheduleUpdate extends JavaProcess
{
	private static final int FETCH_SIZE = 100;

	private IEventBus eventBus;

	private int countScheduled = 0;
	private LocationId minLocationIdScheduled = null;

	@Override
	protected void prepare()
	{
		final IEventBusFactory eventBusFactory = SpringContextHolder.instance.getBean(IEventBusFactory.class);
		eventBus = eventBusFactory.getEventBus(C_Location.EVENTS_TOPIC);
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		 TODO tbp: we should send batches of 100 per request, instead of just 1 single location.
		this has to be tested in C_Location_Geocoding_ScheduleUpdateTest

		Set<LocationId> locationIds = retrieveLocationIdsToUpdate(FETCH_SIZE);
		while (!locationIds.isEmpty())
		{
			scheduleUpdates(locationIds);
			locationIds = retrieveLocationIdsToUpdate(FETCH_SIZE);
		}

		return "Scheduled " + countScheduled + " C_Location(s)";
	}

	private ImmutableSet<LocationId> retrieveLocationIdsToUpdate(final int limit)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_Location.class)
				//
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_C_Location.COLUMN_GeocodingStatus, Arrays.asList(X_C_Location.GEOCODINGSTATUS_Error, X_C_Location.GEOCODINGSTATUS_NotChecked))
				//
				.addCompareFilter(I_C_Location.COLUMN_C_Location_ID, Operator.GREATER, LocationId.toRepoIdOr(minLocationIdScheduled, 0))
				.orderByDescending(I_C_Location.COLUMN_C_Location_ID)
				//
				.setLimit(limit)
				//
				.create()
				.listIds(LocationId::ofRepoId);
	}

	private void scheduleUpdates(@NonNull final Set<LocationId> locationIds)
	{
		locationIds.forEach(this::scheduleUpdate);
	}

	private void scheduleUpdate(@NonNull final LocationId locationId)
	{
		eventBus.postObject(LocationGeocodeEventRequest.of(locationId));

		countScheduled++;
		minLocationIdScheduled = min(minLocationIdScheduled, locationId);
	}

	private static LocationId min(final LocationId locationId1, final LocationId locationId2)
	{
		if (locationId1 == null)
		{
			return locationId2;
		}
		else if (locationId2 == null)
		{
			return locationId1;
		}
		else
		{
			return locationId1.getRepoId() <= locationId2.getRepoId() ? locationId1 : locationId2;
		}
	}
}

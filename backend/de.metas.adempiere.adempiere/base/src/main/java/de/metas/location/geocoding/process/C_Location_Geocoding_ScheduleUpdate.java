package de.metas.location.geocoding.process;

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
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Location;
import org.compiere.model.X_C_Location;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

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
	private LocationId maxLocationIdScheduled = null;

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
				.addCompareFilter(I_C_Location.COLUMN_C_Location_ID, Operator.GREATER, LocationId.toRepoIdOr(maxLocationIdScheduled, 0))
				.orderBy(I_C_Location.COLUMN_C_Location_ID)
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
		eventBus.enqueueObject(LocationGeocodeEventRequest.of(locationId));

		countScheduled++;

		if (maxLocationIdScheduled == null)
		{
			maxLocationIdScheduled = locationId;
		}
		else
		{
			maxLocationIdScheduled = Collections.max(Arrays.asList(maxLocationIdScheduled, locationId));
		}
	}
}

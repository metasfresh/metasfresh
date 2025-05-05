package de.metas.location.geocoding.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.location.LocationId;
import de.metas.location.geocoding.GeocodingService;
import de.metas.location.geocoding.asynchandler.LocationGeocodeEventRequest;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Location;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * #%L
 * metasfresh-pharma
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

@Component
@Interceptor(I_C_Location.class)
@RequiredArgsConstructor
public class C_Location
{
	public static final Topic EVENTS_TOPIC = Topic.distributed("de.metas.location.geocoding.events");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IEventBusFactory eventBusFactory;
	@NonNull private final GeocodingService geocodingService;

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void onNewLocation(final I_C_Location locationRecord)
	{
		if (geocodingService.isProviderConfigured())
		{
			trxManager.accumulateAndProcessAfterCommit(
					"LocationGeocodeEventRequest",
					ImmutableList.of(LocationGeocodeEventRequest.of(LocationId.ofRepoId(locationRecord.getC_Location_ID()))),
					this::fireLocationGeocodeRequests
			);
		}
	}

	private void fireLocationGeocodeRequests(final List<LocationGeocodeEventRequest> requests)
	{
		eventBusFactory.getEventBus(EVENTS_TOPIC).enqueueObjectsCollection(requests);
	}
}

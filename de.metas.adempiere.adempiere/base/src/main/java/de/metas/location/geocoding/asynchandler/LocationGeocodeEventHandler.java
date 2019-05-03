package de.metas.location.geocoding.asynchandler;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.compiere.model.I_C_Location;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

import de.metas.Profiles;
import de.metas.event.IEventBusFactory;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.geocoding.GeoCoordinatesProvider;
import de.metas.location.geocoding.GeoCoordinatesRequest;
import de.metas.location.geocoding.GeographicalCoordinates;
import de.metas.location.geocoding.interceptor.C_Location;
import de.metas.util.Services;
import lombok.NonNull;

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
@Profile(Profiles.PROFILE_App)
class LocationGeocodeEventHandler
{
	private final ILocationDAO locationsRepo = Services.get(ILocationDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final GeoCoordinatesProvider geo;
	private final IEventBusFactory eventBusFactory;

	public LocationGeocodeEventHandler(
			final IEventBusFactory eventBusFactory,
			final GeoCoordinatesProvider geo)
	{
		this.eventBusFactory = eventBusFactory;
		this.geo = geo;
	}

	@PostConstruct
	private void postConstruct()
	{
		eventBusFactory
				.getEventBus(C_Location.EVENTS_TOPIC)
				.subscribeOn(LocationGeocodeEventRequest.class, this::handleEvent);
	}

	private void handleEvent(@NonNull final LocationGeocodeEventRequest request)
	{
		final I_C_Location locationRecord = locationsRepo.getById(request.getLocationId());

		final GeoCoordinatesRequest coordinatesRequest = createGeoCoordinatesRequest(locationRecord);

		final Optional<GeographicalCoordinates> xoy = geo.findBestCoordinates(coordinatesRequest);
		if (xoy.isPresent())
		{
			locationRecord.setLatitude(xoy.get().getLatitude());
			locationRecord.setLongitude(xoy.get().getLongitude());
			locationsRepo.save(locationRecord);
		}
	}

	private GeoCoordinatesRequest createGeoCoordinatesRequest(final I_C_Location locationRecord)
	{
		final String countryCode2 = countryDAO.retrieveCountryCode2ByCountryId(CountryId.ofRepoId(locationRecord.getC_Country_ID()));

		final String address = Joiner.on(" ")
				.skipNulls()
				.join(locationRecord.getAddress1(), locationRecord.getAddress2(), locationRecord.getAddress3(), locationRecord.getAddress4());

		final String postal = locationRecord.getPostal();

		final GeoCoordinatesRequest coordinatesRequest = GeoCoordinatesRequest.builder()
				.countryCode2(countryCode2)
				.address(address)
				.postal(postal)
				.build();
		return coordinatesRequest;
	}
}

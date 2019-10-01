package de.metas.location.geocoding.asynchandler;

import java.util.Optional;

import javax.annotation.PostConstruct;

import com.google.common.annotations.VisibleForTesting;
import org.compiere.model.I_C_Location;
import org.compiere.model.X_C_Location;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

import de.metas.Profiles;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.event.IEventBusFactory;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.geocoding.GeoCoordinatesRequest;
import de.metas.location.geocoding.GeocodingService;
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
	private final GeocodingService geocodingService;
	private final IEventBusFactory eventBusFactory;

	public LocationGeocodeEventHandler(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final GeocodingService geocodingService)
	{
		this.eventBusFactory = eventBusFactory;
		this.geocodingService = geocodingService;
	}

	@PostConstruct
	private void postConstruct()
	{
		eventBusFactory
				.getEventBus(C_Location.EVENTS_TOPIC)
				.subscribeOn(LocationGeocodeEventRequest.class, this::handleEvent);
	}

	@SuppressWarnings("WeakerAccess")
	@VisibleForTesting
	void handleEvent(@NonNull final LocationGeocodeEventRequest request)
	{
		final I_C_Location locationRecord = locationsRepo.getById(request.getLocationId());
		final GeoCoordinatesRequest coordinatesRequest = createGeoCoordinatesRequest(locationRecord);

		try
		{
			final Optional<GeographicalCoordinates> xoy = geocodingService.findBestCoordinates(coordinatesRequest);
			if (xoy.isPresent())
			{
				locationRecord.setLatitude(xoy.get().getLatitude());
				locationRecord.setLongitude(xoy.get().getLongitude());
				locationRecord.setGeocodingStatus(X_C_Location.GEOCODINGSTATUS_Resolved);
				locationRecord.setGeocoding_Issue_ID(-1);
			}
			else
			{
				locationRecord.setLatitude(null);
				locationRecord.setLongitude(null);
				locationRecord.setGeocodingStatus(X_C_Location.GEOCODINGSTATUS_NotResolved);
				locationRecord.setGeocoding_Issue_ID(-1);
			}
		}
		catch (final Exception ex)
		{
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(ex);

			locationRecord.setGeocodingStatus(X_C_Location.GEOCODINGSTATUS_Error);
			locationRecord.setGeocoding_Issue_ID(issueId.getRepoId());
		}
		finally
		{
			locationsRepo.save(locationRecord);
		}
	}

	private GeoCoordinatesRequest createGeoCoordinatesRequest(@NonNull final I_C_Location locationRecord)
	{
		final String countryCode2 = countryDAO.retrieveCountryCode2ByCountryId(CountryId.ofRepoId(locationRecord.getC_Country_ID()));

		final String address = Joiner.on(" ")
				.skipNulls()
				.join(locationRecord.getAddress1(), locationRecord.getAddress2(), locationRecord.getAddress3(), locationRecord.getAddress4());

		final String postal = locationRecord.getPostal();

		final String city = locationRecord.getCity();

		return GeoCoordinatesRequest.builder()
				.countryCode2(countryCode2)
				.address(address)
				.postal(postal)
				.city(city)
				.build();
	}
}

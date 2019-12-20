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

package de.metas.location.geocoding.process;

import de.metas.location.CountryId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationCreateRequest;
import de.metas.location.LocationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.X_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.junit.jupiter.api.Assertions.assertEquals;

class C_Location_Geocoding_ScheduleUpdateTest
{
	CountryId countryId;
	private final C_Location_Geocoding_ScheduleUpdate process = new C_Location_Geocoding_ScheduleUpdate();

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		countryId = createCountry();

		// must be called
		process.prepare();
	}

	@Test
	void testTheNumberOfEnqueuedLocations()
	{
		 TODO tbp: fix this test!
		final int totalNumberOfLocationsToEnque = 1234;

		for (int i = 0; i < totalNumberOfLocationsToEnque; i++)
		{
			createLocationToBeEnqueued(countryId, i);
		}

		// create another location which won't be enqueued
		createLocationNotToBeEnqueued(countryId);

		// test the number of enqueued locations
		final String processResponse = process.doIt();

		final String expectedResponse = "Scheduled " + totalNumberOfLocationsToEnque + " C_Location(s)";
		assertEquals(expectedResponse, processResponse);
	}

	@NonNull
	private I_C_Location createLocationNotToBeEnqueued(final CountryId countryId)
	{
		final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
		final LocationCreateRequest locationCreateRequest = LocationCreateRequest.builder()
				.address1("not enqueued location")
				.countryId(countryId)
				.build();
		final LocationId locationId = locationDAO.createLocation(locationCreateRequest);

		final I_C_Location location = locationDAO.getById(locationId);
		location.setGeocodingStatus(X_C_Location.GEOCODINGSTATUS_NotResolved);
		InterfaceWrapperHelper.save(location);

		return location;
	}

	@NonNull
	private I_C_Location createLocationToBeEnqueued(final CountryId countryId, int nameAppend)
	{
		final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
		final LocationCreateRequest locationCreateRequest = LocationCreateRequest.builder()
				.address1("location " + nameAppend)
				.countryId(countryId)
				.build();
		final LocationId locationId = locationDAO.createLocation(locationCreateRequest);

		final I_C_Location location = locationDAO.getById(locationId);
		location.setGeocodingStatus(X_C_Location.GEOCODINGSTATUS_NotChecked);
		InterfaceWrapperHelper.save(location);

		return location;
	}

	@NonNull
	private CountryId createCountry()
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setName("Saint Kitts and Nevis");
		saveRecord(country);
		return CountryId.ofRepoId(country.getC_Country_ID());
	}

}

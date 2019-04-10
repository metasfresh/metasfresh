package org.adempiere.location.geocoding.interceptor;

import de.metas.adempiere.model.I_C_Location;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.location.geocoding.GeographicalCoordinates;
import org.adempiere.location.geocoding.GeographicalCoordinatesProvider;
import org.adempiere.location.geocoding.GeographicalCoordinatesRequest;
import org.compiere.model.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

@Component("org.adempiere.location.geocoding.interceptor.C_Location")
@Interceptor(I_C_Location.class)
public class C_Location
{
	@Autowired
	GeographicalCoordinatesProvider geo;

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE})
	public void onNewLocation(final  I_C_Location locationRecord)
	{
		final String address = locationRecord.getAddress1() +locationRecord.getAddress2() + locationRecord.getAddress3() + locationRecord.getAddress4();
		GeographicalCoordinatesRequest coordinatesRequest = GeographicalCoordinatesRequest.builder()
				.countryCode(locationRecord.getC_Country().getCountryCode())
				.address(address)
				.postal(locationRecord.getPostal())
				.build();

		final Optional<GeographicalCoordinates> xoy = geo.findBestCoordinates(coordinatesRequest);
		System.out.printf("\n\n\n\n\n\n\n\n\n\n\\n\n\n%s\n\n\n\n\\n\n\n\n\n\n\n\n", xoy);

		if (xoy.isPresent())
		{
			locationRecord.setLatitude(xoy.get().getLatitude());
			locationRecord.setLongitude(xoy.get().getLongitude());
		}
	}
}

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

package de.metas.location.geocoding.provider.googlemaps;

import com.google.maps.GeoApiContext;
import de.metas.location.geocoding.GeocodingConfigRepository;

public class GoogleMapsGeoApiContext
{
	// this should be a singleton as per he docs https://github.com/googlemaps/google-maps-services-java#usage
	private static volatile GeoApiContext instance;

	private GoogleMapsGeoApiContext()
	{
	}

	public static GeoApiContext getInstance()
	{
		if (instance != null)
		{
			return instance;
		}

		synchronized (GoogleMapsGeoApiContext.class)
		{
			if (instance == null)
			{
				final String apiKey = GeocodingConfigRepository.readGeocodingConfig().getgmaps_ApiKey();
				instance = new GeoApiContext.Builder()
						.apiKey(apiKey)
						.build();
			}
		}

		return instance;
	}
}

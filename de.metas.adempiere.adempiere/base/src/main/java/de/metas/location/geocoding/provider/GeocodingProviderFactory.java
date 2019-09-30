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

package de.metas.location.geocoding.provider;

import com.google.maps.GeoApiContext;
import de.metas.location.geocoding.GeocodingConfigRepository;
import de.metas.location.geocoding.GeocodingProvider;
import de.metas.location.geocoding.provider.googlemaps.GoogleMapsGeoApiContext;
import de.metas.location.geocoding.provider.googlemaps.GoogleMapsGeocodingProviderImpl;
import de.metas.location.geocoding.provider.openstreetmap.NominatimOSMGeocodingProviderImpl;
import de.metas.logging.LogManager;
import org.compiere.model.I_GeocodingConfig;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class GeocodingProviderFactory
{
	private static final Logger logger = LogManager.getLogger(GeocodingProviderFactory.class);

	private static volatile GeocodingProvider instance;

	/**
	 * fixme: this is broken because no live changing of the provider is possible
	 */
	@Nullable
	public static GeocodingProvider buildActiveGeocodingProviderOrNull()
	{
		if (instance != null)
		{
			return instance;
		}

		synchronized (GeocodingProviderFactory.class)
		{
			if (instance != null)
			{
				return instance;
			}

			logger.debug("Creating a new instance of GeoCoordinatesProvider");

			final GeocodingProviderName providerName = GeocodingConfigRepository.getActiveGeocodingProviderNameOrNull();
			if (providerName == null)
			{
				logger.debug("No GeoCoordinatesProvider is set in settings! Returning null.");
				return null;
			}

			if (GeocodingProviderName.GOOGLE_MAPS.equals(providerName))
			{
				createGoogleMapsProvider();
			}

			if (GeocodingProviderName.OPEN_STREET_MAPS.equals(providerName))
			{
				createOSMProvider();
			}
		}
		return instance;
	}

	private static void createOSMProvider()
	{
		logger.debug("Creating a new instance of NominatimOSMGeocodingProviderImpl");

		final I_GeocodingConfig geocodingConfig = GeocodingConfigRepository.readGeocodingConfig();
		final int cacheCapacity = geocodingConfig.getcacheCapacity();
		final String baseURL = geocodingConfig.getosm_baseURL();
		final long millisBetweenRequests = geocodingConfig.getosm_millisBetweenRequests();

		instance = new NominatimOSMGeocodingProviderImpl(baseURL, millisBetweenRequests, cacheCapacity);
	}

	private static void createGoogleMapsProvider()
	{
		logger.debug("Creating a new instance of GoogleMapsGeocodingProviderImpl");

		final I_GeocodingConfig geocodingConfig = GeocodingConfigRepository.readGeocodingConfig();
		final int cacheCapacity = geocodingConfig.getcacheCapacity();

		final GeoApiContext context = GoogleMapsGeoApiContext.getInstance();

		instance = new GoogleMapsGeocodingProviderImpl(context, cacheCapacity);
	}
}

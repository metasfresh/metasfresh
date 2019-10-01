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

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;

import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.location.geocoding.GeocodingConfig;
import de.metas.location.geocoding.GeocodingConfig.GoogleMapsConfig;
import de.metas.location.geocoding.GeocodingConfig.OpenStreetMapsConfig;
import de.metas.location.geocoding.GeocodingConfigRepository;
import de.metas.location.geocoding.GeocodingProvider;
import de.metas.location.geocoding.provider.googlemaps.GoogleMapsGeocodingProviderImpl;
import de.metas.location.geocoding.provider.openstreetmap.NominatimOSMGeocodingProviderImpl;
import lombok.NonNull;

@Service
public class GeocodingProviderFactory
{
	private final GeocodingConfigRepository configRepository;

	private final CCache<GeocodingConfig, GeocodingProvider> providers = CCache.<GeocodingConfig, GeocodingProvider> builder()
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(10)
			.build();

	public GeocodingProviderFactory(
			@NonNull final GeocodingConfigRepository configRepository)
	{
		this.configRepository = configRepository;
	}

	public Optional<GeocodingProvider> getProvider()
	{
		final GeocodingConfig config = configRepository.getGeocodingConfig().orElse(null);
		if (config == null)
		{
			return Optional.empty();
		}

		final GeocodingProvider provider = providers.getOrLoad(config, this::createProvider);
		return Optional.of(provider);
	}

	private GeocodingProvider createProvider(@NonNull final GeocodingConfig config)
	{
		final GeocodingProviderName providerName = config.getProviderName();
		if (GeocodingProviderName.GOOGLE_MAPS.equals(providerName))
		{
			return createGoogleMapsProvider(config.getGoogleMapsConfig());
		}
		else if (GeocodingProviderName.OPEN_STREET_MAPS.equals(providerName))
		{
			return createOSMProvider(config.getOpenStreetMapsConfig());
		}
		else
		{
			throw new AdempiereException("Unknown provider: " + providerName);
		}
	}

	private GoogleMapsGeocodingProviderImpl createGoogleMapsProvider(final GoogleMapsConfig config)
	{
		final String apiKey = config.getApiKey();
		final int cacheCapacity = config.getCacheCapacity();

		final GeoApiContext context = new GeoApiContext.Builder()
				.apiKey(apiKey)
				.build();

		return new GoogleMapsGeocodingProviderImpl(context, cacheCapacity);
	}

	private NominatimOSMGeocodingProviderImpl createOSMProvider(final OpenStreetMapsConfig config)
	{
		final String baseURL = config.getBaseURL();
		final int cacheCapacity = config.getCacheCapacity();
		final long millisBetweenRequests = config.getMillisBetweenRequests();
		return new NominatimOSMGeocodingProviderImpl(baseURL, millisBetweenRequests, cacheCapacity);
	}
}

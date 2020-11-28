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

import com.google.common.collect.ImmutableList;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import de.metas.cache.CCache;
import de.metas.location.geocoding.GeoCoordinatesRequest;
import de.metas.location.geocoding.GeocodingProvider;
import de.metas.location.geocoding.GeographicalCoordinates;
import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GoogleMapsGeocodingProviderImpl implements GeocodingProvider
{
	private static final Logger logger = LogManager.getLogger(GoogleMapsGeocodingProviderImpl.class);

	private final CCache<GeoCoordinatesRequest, ImmutableList<GeographicalCoordinates>> coordinatesCache;
	private final GeoApiContext context;

	public GoogleMapsGeocodingProviderImpl(final GeoApiContext context, final int cacheCapacity)
	{
		logger.info("context={}; cacheCapacity={}", context, cacheCapacity);

		this.context = context;

		coordinatesCache = CCache.<GeoCoordinatesRequest, ImmutableList<GeographicalCoordinates>>builder()
				.cacheMapType(CCache.CacheMapType.LRU)
				.initialCapacity(cacheCapacity)
				.build();
	}

	@Override
	public Optional<GeographicalCoordinates> findBestCoordinates(final GeoCoordinatesRequest request)
	{
		final List<GeographicalCoordinates> coords = findAllCoordinates(request);

		if (coords.isEmpty())
		{
			return Optional.empty();
		}
		return Optional.of(coords.get(0));
	}

	@NonNull
	private ImmutableList<GeographicalCoordinates> findAllCoordinates(final @NonNull GeoCoordinatesRequest request)
	{
		final ImmutableList<GeographicalCoordinates> response = coordinatesCache.get(request);
		if (response != null)
		{
			return response;
		}

		return coordinatesCache.getOrLoad(request, this::queryAllCoordinates);
	}

	@NonNull
	private ImmutableList<GeographicalCoordinates> queryAllCoordinates(@NonNull final GeoCoordinatesRequest request)
	{
		final String formattedAddress = String.format("%s, %s %s, %s",
				CoalesceUtil.coalesce(request.getAddress(), ""),
				CoalesceUtil.coalesce(request.getPostal(), ""),
				CoalesceUtil.coalesce(request.getCity(), ""),
				CoalesceUtil.coalesce(request.getCountryCode2(), ""));

		logger.trace("Formatted address: {}", formattedAddress);

		final GeocodingResult[] results = GeocodingApi
				.geocode(context, formattedAddress)
				.awaitIgnoreError();

		//noinspection ConfusingArgumentToVarargsMethod
		logger.trace("Geocoding response from google: {}", results);

		if (results == null)
		{
			return ImmutableList.of();
		}

		return Arrays.stream(results)
				.map(GoogleMapsGeocodingProviderImpl::toGeographicalCoordinates)
				.collect(GuavaCollectors.toImmutableList());
	}

	private static GeographicalCoordinates toGeographicalCoordinates(@NonNull final GeocodingResult result)
	{
		final LatLng ll = result.geometry.location;
		return GeographicalCoordinates.builder()
				.latitude(BigDecimal.valueOf(ll.lat))
				.longitude(BigDecimal.valueOf(ll.lng))
				.build();
	}
}

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

package de.metas.location.geocoding.provider.openstreetmap;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.cache.CCache;
import de.metas.location.geocoding.GeoCoordinatesRequest;
import de.metas.location.geocoding.GeocodingProvider;
import de.metas.location.geocoding.GeographicalCoordinates;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class NominatimOSMGeocodingProviderImpl implements GeocodingProvider
{
	private static final Logger logger = LogManager.getLogger(NominatimOSMGeocodingProviderImpl.class);

	private final static String DEFAULT_BASE_URL = "https://nominatim.openstreetmap.org/search";
	@SuppressWarnings("SpellCheckingInspection")
	private static final String QUERY_STRING = "?street={numberAndStreet}&city={city}&postalcode={postalcode}&countrycodes={countrycodes}&format={format}&dedupe={dedupe}&email={email}&polygon_geojson={polygon_geojson}&polygon_kml={polygon_kml}&polygon_svg={polygon_svg}&polygon_text={polygon_text}";

	private final RestTemplate restTemplate = new RestTemplateBuilder()
			.messageConverters(new MappingJackson2HttpMessageConverter(JsonObjectMapperHolder.sharedJsonObjectMapper()))
			.build();

	private final CCache<GeoCoordinatesRequest, ImmutableList<GeographicalCoordinates>> coordinatesCache;

	private final String baseUrl;

	private final long millisBetweenRequests;
	private Instant lastRequestTime;

	public NominatimOSMGeocodingProviderImpl(
			final String baseUrl,
			final long millisBetweenRequests,
			final int cacheCapacity)
	{
		if (!Check.isEmpty(baseUrl, true))
		{
			this.baseUrl = baseUrl;
		}
		else
		{
			this.baseUrl = DEFAULT_BASE_URL;
		}
		logger.info("baseUrl={}", this.baseUrl);

		this.millisBetweenRequests = millisBetweenRequests;
		logger.info("millisBetweenRequests={}", millisBetweenRequests);

		lastRequestTime = Instant.now().minusMillis(this.millisBetweenRequests);

		logger.info("cacheCapacity={}", cacheCapacity);
		coordinatesCache = CCache.<GeoCoordinatesRequest, ImmutableList<GeographicalCoordinates>>builder()
				.cacheMapType(CCache.CacheMapType.LRU)
				.initialCapacity(cacheCapacity)
				.build();
	}

	@NonNull
	@Override
	public Optional<GeographicalCoordinates> findBestCoordinates(final @NonNull GeoCoordinatesRequest request)
	{
		final List<GeographicalCoordinates> coords = findAllCoordinates(request);

		if (coords.isEmpty())
		{
			return Optional.empty();
		}
		return Optional.of(coords.get(0));
	}

	@SuppressWarnings("WeakerAccess")
	@NonNull
	@VisibleForTesting
	ImmutableList<GeographicalCoordinates> findAllCoordinates(final @NonNull GeoCoordinatesRequest request)
	{
		final ImmutableList<GeographicalCoordinates> response = coordinatesCache.get(request);
		if (response != null)
		{
			return response;
		}
		rateLimitAsNeeded();

		return coordinatesCache.getOrLoad(request, this::queryAllCoordinates);
	}

	private synchronized void rateLimitAsNeeded()
	{
		final Instant shouldBeBeforeNow = lastRequestTime.plusMillis(millisBetweenRequests);
		if (shouldBeBeforeNow.isAfter(Instant.now()))
		{
			final long timeToSleep = Duration.between(Instant.now(), shouldBeBeforeNow).toMillis();
			sleepMillis(timeToSleep);
		}
	}

	@NonNull
	private ImmutableList<GeographicalCoordinates> queryAllCoordinates(final @NonNull GeoCoordinatesRequest request)
	{
		final Map<String, String> parameterList = prepareParameterList(request);

		//@formatter:off
		final ParameterizedTypeReference<List<NominatimOSMGeographicalCoordinatesJSON>> returnType = new ParameterizedTypeReference<List<NominatimOSMGeographicalCoordinatesJSON>>(){};
		//@formatter:on

		final HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.USER_AGENT, "openstreetmapbot@metasfresh.com");
		final HttpEntity<String> entity = new HttpEntity<>(headers);

		final ResponseEntity<List<NominatimOSMGeographicalCoordinatesJSON>> exchange = restTemplate.exchange(
				baseUrl + QUERY_STRING,
				HttpMethod.GET,
				entity,
				returnType,
				parameterList);

		final List<NominatimOSMGeographicalCoordinatesJSON> coords = exchange.getBody();

		lastRequestTime = Instant.now();

		final ImmutableList<GeographicalCoordinates> result = coords.stream()
				.map(NominatimOSMGeocodingProviderImpl::toGeographicalCoordinates)
				.collect(GuavaCollectors.toImmutableList());

		logger.debug("Got result for {}: {}", request, result);

		return result;
	}

	@SuppressWarnings("SpellCheckingInspection")
	@NonNull
	private Map<String, String> prepareParameterList(final @NonNull GeoCoordinatesRequest request)
	{
		final String defaultEmptyValue = "";

		final Map<String, String> m = new HashMap<>();
		m.put("numberAndStreet", CoalesceUtil.coalesce(request.getAddress(), defaultEmptyValue));
		m.put("postalcode", CoalesceUtil.coalesce(request.getPostal(), defaultEmptyValue));
		m.put("city", CoalesceUtil.coalesce(request.getCity(), defaultEmptyValue));
		m.put("countrycodes", request.getCountryCode2());
		m.put("format", "json");
		m.put("dedupe", "1");
		m.put("email", "openstreetmapbot@metasfresh.com");
		m.put("polygon_geojson", "0");
		m.put("polygon_kml", "0");
		m.put("polygon_svg", "0");
		m.put("polygon_text", "0");
		return m;
	}

	private static GeographicalCoordinates toGeographicalCoordinates(final NominatimOSMGeographicalCoordinatesJSON json)
	{
		return GeographicalCoordinates.builder()
				.latitude(new BigDecimal(json.getLat()))
				.longitude(new BigDecimal(json.getLon()))
				.build();
	}

	private void sleepMillis(final long timeToSleep)
	{
		if (timeToSleep <= 0)
		{
			return;
		}

		try
		{
			logger.trace("Sleeping {}ms (rate limit)", timeToSleep);
			TimeUnit.MILLISECONDS.sleep(timeToSleep);
		}
		catch (final InterruptedException e)
		{
			// nothing to do here
		}
	}
}

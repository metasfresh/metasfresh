package de.metas.location.geocoding.openstreetmap;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import de.metas.location.geocoding.GeoCoordinatesProvider;
import de.metas.location.geocoding.GeoCoordinatesRequest;
import de.metas.location.geocoding.GeographicalCoordinates;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
public class NominatimOSMGeoCoordinatesProviderImpl implements GeoCoordinatesProvider
{
	private static final Logger logger = LogManager.getLogger(NominatimOSMGeoCoordinatesProviderImpl.class);

	/**
	 * Please observe the 2 URLs.
	 * Issue with WRONG_BASE_URL is that when using <pre>search?q={address}&postalcode={postalcode}</pre>
	 * Nominatim tries to search for BOTH a postal and an address. <br/>
	 * <p>
	 * In some cases the address may be wrong so we get no response. <br>
	 * <p>
	 * With the second QUERY_STRING Nominatim ignores the address and just searches for postal.
	 * <p>
	 * Country is taken into account in both cases.
	 */
	//	private static final String WRONG_URL = "https://nominatim.openstreetmap.org/search?q={address}&postalcode={postalcode}&countrycodes={countrycodes}&format={format}&dedupe={dedupe}&email={email}&polygon_geojson={polygon_geojson}&polygon_kml={polygon_kml}&polygon_svg={polygon_svg}&polygon_text={polygon_text}";
	private final static String DEFAULT_BASE_URL = "https://nominatim.openstreetmap.org/search/";
	@SuppressWarnings("SpellCheckingInspection") private static final String QUERY_STRING = "{address}?postalcode={postalcode}&countrycodes={countrycodes}&format={format}&dedupe={dedupe}&email={email}&polygon_geojson={polygon_geojson}&polygon_kml={polygon_kml}&polygon_svg={polygon_svg}&polygon_text={polygon_text}";

	private final RestTemplate restTemplate = new RestTemplateBuilder().build();

	private final CCache<GeoCoordinatesRequest, ImmutableList<GeographicalCoordinates>> coordinatesCache;

	private final String BASE_URL;

	private final long millisBetweenRequests;
	private Instant lastRequestTime;

	NominatimOSMGeoCoordinatesProviderImpl(
			@Value("${de.metas.location.geocoding.openstreetmap.baseUrl:}") final String baseUrl,
			@Value("${de.metas.location.geocoding.openstreetmap.millisBetweenRequests:2000}") final long millisBetweenRequests,
			@Value("${de.metas.location.geocoding.openstreetmap.cacheCapacity:200}") final int cacheCapacity
	)
	{
		if (!Check.isEmpty(baseUrl, true))
		{
			BASE_URL = baseUrl;
		}
		else
		{
			BASE_URL = DEFAULT_BASE_URL;
		}
		logger.info("baseUrl={}", BASE_URL);

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
				BASE_URL + QUERY_STRING,
				HttpMethod.GET,
				entity,
				returnType,
				parameterList);

		final List<NominatimOSMGeographicalCoordinatesJSON> coords = exchange.getBody();

		lastRequestTime = Instant.now();

		return coords.stream()
				.map(it -> new GeographicalCoordinates(it.getLat(), it.getLon()))
				.collect(GuavaCollectors.toImmutableList());
	}

	@SuppressWarnings("SpellCheckingInspection")
	@NonNull private Map<String, String> prepareParameterList(final @Nonnull GeoCoordinatesRequest request)
	{
		String nonNullPostalCode = request.getPostal();
		if (nonNullPostalCode == null)
		{
			nonNullPostalCode = "";
		}
		String nonNullAddress = request.getAddress();
		if (nonNullAddress == null)
		{
			nonNullAddress = "";
		}

		final Map<String, String> m = new HashMap<>();
		m.put("address", nonNullAddress);
		m.put("postalcode", nonNullPostalCode);
		m.put("countrycodes", request.getCountryCode());
		m.put("format", "json");
		m.put("dedupe", "1");
		m.put("email", "openstreetmapbot@metasfresh.com");
		m.put("polygon_geojson", "0");
		m.put("polygon_kml", "0");
		m.put("polygon_svg", "0");
		m.put("polygon_text", "0");
		return m;
	}

	private void sleepMillis(final long timeToSleep)
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(timeToSleep);
		}
		catch (final InterruptedException e)
		{
			// nothing to do here
		}
	}
}

package org.adempiere.location.geocoding.openstreetmap;

import com.google.common.annotations.VisibleForTesting;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.location.geocoding.GeoCoordinatesProvider;
import org.adempiere.location.geocoding.GeographicalCoordinates;
import org.adempiere.location.geocoding.GeoCoordinatesRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Component
public class NominatimOSMGeoCoordinatesProviderImpl implements GeoCoordinatesProvider
{

	/**
	 * Please observe the 2 URLs.
	 * Issue with WRONG_BASE_URL is that when using <pre>search?q={address}&postalcode={postalcode}</pre>
	 * Nominatim tries to search for BOTH a postal and an address. <br/>
	 *
	 * In some cases the address may be wrong so we get no response. <br>
	 *
	 * With the second BASE_URL Nominatim ignores the address and just searches for postal.
	 *
	 * Country is taken into account in both cases.
	 */
	//	private static final String WRONG_BASE_URL = "https://nominatim.openstreetmap.org/search?q={address}&postalcode={postalcode}&countrycodes={countrycodes}&format={format}&dedupe={dedupe}&email={email}&polygon_geojson={polygon_geojson}&polygon_kml={polygon_kml}&polygon_svg={polygon_svg}&polygon_text={polygon_text}";
	@SuppressWarnings("SpellCheckingInspection")
	private static final String BASE_URL = "https://nominatim.openstreetmap.org/search/{address}?postalcode={postalcode}&countrycodes={countrycodes}&format={format}&dedupe={dedupe}&email={email}&polygon_geojson={polygon_geojson}&polygon_kml={polygon_kml}&polygon_svg={polygon_svg}&polygon_text={polygon_text}";

	private final RestTemplate restTemplate = new RestTemplateBuilder().build();

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
	List<GeographicalCoordinates> findAllCoordinates(final @NonNull GeoCoordinatesRequest request)
	{
		final Map<String, String> parameterList = prepareParameterList(request);

		//@formatter:off
		final ParameterizedTypeReference<List<NominatimOSMGeographicalCoordinatesJSON>> returnType = new ParameterizedTypeReference<List<NominatimOSMGeographicalCoordinatesJSON>>(){};
		//@formatter:on

		final ResponseEntity<List<NominatimOSMGeographicalCoordinatesJSON>> exchange = restTemplate.exchange(
				BASE_URL,
				HttpMethod.GET,
				null,
				returnType,
				parameterList);

		final List<NominatimOSMGeographicalCoordinatesJSON> coords = exchange.getBody();
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

}

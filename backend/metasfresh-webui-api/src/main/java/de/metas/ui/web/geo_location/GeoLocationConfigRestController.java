/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.geo_location;

import de.metas.location.geocoding.GeocodingConfig;
import de.metas.location.geocoding.GeocodingConfigRepository;
import de.metas.ui.web.config.WebConfig;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(GeoLocationConfigRestController.ENDPOINT)
public class GeoLocationConfigRestController
{
	static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/geolocation/config";

	private final GeocodingConfigRepository geocodingConfigRepository;

	public GeoLocationConfigRestController(@NonNull final GeocodingConfigRepository geocodingConfigRepository)
	{
		this.geocodingConfigRepository = geocodingConfigRepository;
	}

	@GetMapping
	public JsonGeoLocationConfig getConfig()
	{
		if (!geocodingConfigRepository.getGeocodingConfig().isPresent())
		{
			return JsonGeoLocationConfig.builder().build();
		}

		final GeocodingConfig geocodingConfig = geocodingConfigRepository.getGeocodingConfig().get();
		switch (geocodingConfig.getProviderName())
		{
			case GOOGLE_MAPS:
				return getGoogleMapsConfig(geocodingConfig);
			case OPEN_STREET_MAPS:
				return getOpenStreetMapsConfig();
			default:
				return JsonGeoLocationConfig.builder().build();
		}
	}

	@NonNull
	private JsonGeoLocationConfig getOpenStreetMapsConfig()
	{
		return JsonGeoLocationConfig.builder()
				.provider(JsonGeoLocationProvider.OpenStreetMaps)
				.build();
	}

	@NonNull
	private JsonGeoLocationConfig getGoogleMapsConfig(@NonNull final GeocodingConfig geocodingConfig)
	{
		final GeocodingConfig.GoogleMapsConfig googleMapsConfig = geocodingConfig.getGoogleMapsConfig();

		return JsonGeoLocationConfig.builder()
				.provider(JsonGeoLocationProvider.GoogleMaps)
				.googleMapsApiKey(googleMapsConfig.getApiKey())
				.build();
	}
}

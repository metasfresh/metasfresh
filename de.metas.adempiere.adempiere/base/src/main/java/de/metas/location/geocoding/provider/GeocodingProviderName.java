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

import com.google.common.collect.ImmutableMap;
import org.compiere.model.X_GeocodingConfig;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public enum GeocodingProviderName
{
	GOOGLE_MAPS(X_GeocodingConfig.GEOCODINGPROVIDER_GoogleMaps),
	OPEN_STREET_MAPS(X_GeocodingConfig.GEOCODINGPROVIDER_OpenStreetMaps);

	private final String providerName;

	@SuppressWarnings("UnstableApiUsage")
	private static final Map<String, GeocodingProviderName> map = Stream.of(values()).collect(ImmutableMap.toImmutableMap(m -> m.providerName, Function.identity()));

	GeocodingProviderName(final String providerName)
	{
		this.providerName = providerName;
	}

	@Nullable
	public static GeocodingProviderName ofProviderName(final String providerName)
	{
		return map.get(providerName);
	}
}

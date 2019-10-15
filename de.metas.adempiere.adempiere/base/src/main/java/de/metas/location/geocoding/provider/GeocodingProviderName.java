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

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_GeocodingConfig;

import com.google.common.collect.ImmutableMap;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

public enum GeocodingProviderName implements ReferenceListAwareEnum
{
	GOOGLE_MAPS(X_GeocodingConfig.GEOCODINGPROVIDER_GoogleMaps), OPEN_STREET_MAPS(X_GeocodingConfig.GEOCODINGPROVIDER_OpenStreetMaps);

	@Getter
	private final String code;

	GeocodingProviderName(final String providerName)
	{
		this.code = providerName;
	}

	@NonNull public static GeocodingProviderName ofCode(@NonNull final String code)
	{
		final GeocodingProviderName type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + GeocodingProviderName.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static GeocodingProviderName ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	private static final ImmutableMap<String, GeocodingProviderName> typesByCode = ReferenceListAwareEnums.indexByCode(values());
}

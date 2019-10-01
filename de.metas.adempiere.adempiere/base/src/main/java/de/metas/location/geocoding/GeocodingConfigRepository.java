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

package de.metas.location.geocoding;

import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_GeocodingConfig;
import org.springframework.stereotype.Repository;

import de.metas.cache.CCache;
import de.metas.location.geocoding.GeocodingConfig.GoogleMapsConfig;
import de.metas.location.geocoding.GeocodingConfig.OpenStreetMapsConfig;
import de.metas.location.geocoding.provider.GeocodingProviderName;
import de.metas.util.Services;

@Repository
public class GeocodingConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, Optional<GeocodingConfig>> cache = CCache.<Integer, Optional<GeocodingConfig>> builder()
			.tableName(I_GeocodingConfig.Table_Name)
			.build();

	public Optional<GeocodingConfig> getGeocodingConfig()
	{
		return cache.getOrLoad(0, this::retrieveGeocodingConfig);
	}

	private Optional<GeocodingConfig> retrieveGeocodingConfig()
	{
		final I_GeocodingConfig record = queryBL.createQueryBuilderOutOfTrx(I_GeocodingConfig.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_GeocodingConfig.class);

		return record != null
				? Optional.of(toGeocodingConfig(record))
				: Optional.empty();
	}

	private static GeocodingConfig toGeocodingConfig(final I_GeocodingConfig record)
	{
		final GeocodingProviderName providerName = GeocodingProviderName.ofCode(record.getGeocodingProvider());

		final GoogleMapsConfig googleMapsConfig;
		final OpenStreetMapsConfig openStreetMapsConfig;
		if (GeocodingProviderName.GOOGLE_MAPS.equals(providerName))
		{
			googleMapsConfig = GoogleMapsConfig.builder()
					.apiKey(record.getgmaps_ApiKey())
					.cacheCapacity(record.getcacheCapacity())
					.build();
			openStreetMapsConfig = null;

		}
		else if (GeocodingProviderName.OPEN_STREET_MAPS.equals(providerName))
		{
			googleMapsConfig = null;
			openStreetMapsConfig = OpenStreetMapsConfig.builder()
					.baseURL(record.getosm_baseURL())
					.millisBetweenRequests(record.getosm_millisBetweenRequests())
					.cacheCapacity(record.getcacheCapacity())
					.build();
		}
		else
		{
			throw new AdempiereException("Unknown provider: " + providerName);
		}

		return GeocodingConfig.builder()
				.providerName(providerName)
				.googleMapsConfig(googleMapsConfig)
				.openStreetMapsConfig(openStreetMapsConfig)
				.build();
	}
}

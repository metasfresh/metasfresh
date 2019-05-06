package de.metas.location.geocoding;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import lombok.NonNull;

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

@Service
public class GeoCoordinatesService
{
	private static final Logger logger = LogManager.getLogger(GeoCoordinatesService.class);

	private final ImmutableList<GeoCoordinatesProvider> providers;

	public GeoCoordinatesService(final Optional<List<GeoCoordinatesProvider>> providers)
	{
		this.providers = providers
				.map(ImmutableList::copyOf)
				.orElseGet(ImmutableList::of);

		logger.info("Providers: {}", this.providers);
	}

	private GeoCoordinatesProvider getProviderOrNull()
	{
		return providers != null ? providers.get(0) : null;
	}

	public Optional<GeographicalCoordinates> findBestCoordinates(@NonNull final GeoCoordinatesRequest request)
	{
		final GeoCoordinatesProvider provider = getProviderOrNull();
		if (provider == null)
		{
			return Optional.empty();
		}

		return provider.findBestCoordinates(request);
	}

}

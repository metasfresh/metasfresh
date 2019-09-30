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

import de.metas.location.geocoding.provider.GeocodingProviderName;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_GeocodingConfig;

import javax.annotation.Nullable;

public class GeocodingConfigRepository
{
	public static I_GeocodingConfig readGeocodingConfig()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_GeocodingConfig.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.first();
	}

	@Nullable
	public static GeocodingProviderName getActiveGeocodingProviderNameOrNull()
	{
		final I_GeocodingConfig geocodingConfig = Services.get(IQueryBL.class).createQueryBuilder(I_GeocodingConfig.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.first();

		final String geocodingProviderStr = geocodingConfig.getGeocodingProvider();
		return GeocodingProviderName.ofProviderName(geocodingProviderStr);
	}
}

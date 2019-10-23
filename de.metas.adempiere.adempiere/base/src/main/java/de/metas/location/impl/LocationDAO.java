package de.metas.location.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Location;
import org.compiere.model.X_C_Location;
import org.slf4j.Logger;

import com.google.common.base.Predicates;

import de.metas.location.CountryId;
import de.metas.location.GeographicalCoordinatesWithLocationId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationCreateRequest;
import de.metas.location.LocationId;
import de.metas.location.geocoding.GeographicalCoordinates;
import de.metas.logging.LogManager;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class LocationDAO implements ILocationDAO
{
	private static final Logger logger = LogManager.getLogger(LocationDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_C_Location getById(@NonNull final LocationId id)
	{
		return loadOutOfTrx(id, I_C_Location.class);
	}

	@Override
	public List<I_C_Location> getByIds(@NonNull final Set<LocationId> ids)
	{
		return loadByRepoIdAwaresOutOfTrx(ids, I_C_Location.class);
	}

	@Override
	public void save(final I_C_Location location)
	{
		InterfaceWrapperHelper.save(location);
	}

	@Override
	public CountryId getCountryIdByLocationId(@NonNull final LocationId id)
	{
		final I_C_Location location = getById(id);
		return CountryId.ofRepoId(location.getC_Country_ID());
	}

	@Override
	public LocationId createLocation(@NonNull final LocationCreateRequest request)
	{
		// NOTE: C_Location table might be heavily used, so it's better to create the address OOT to not lock it.
		final I_C_Location locationRecord = newInstanceOutOfTrx(I_C_Location.class);
		locationRecord.setAddress1(request.getAddress1());
		locationRecord.setAddress2(request.getAddress2());
		locationRecord.setAddress3(request.getAddress3());
		locationRecord.setAddress4(request.getAddress4());
		locationRecord.setPostal(request.getPostal());
		locationRecord.setPostal_Add(request.getPostalAdd());
		locationRecord.setCity(request.getCity());
		locationRecord.setC_Region_ID(request.getRegionId());
		locationRecord.setC_Country_ID(request.getCountryId().getRepoId());
		locationRecord.setPOBox(request.getPoBox());

		save(locationRecord);

		return LocationId.ofRepoId(locationRecord.getC_Location_ID());
	}

	@Override
	public Stream<GeographicalCoordinatesWithLocationId> streamGeoCoordinatesByIds(@NonNull final Set<LocationId> locationIds)
	{
		if (locationIds.isEmpty())
		{
			return Stream.empty();
		}

		return queryBL.createQueryBuilder(I_C_Location.class)
				.addInArrayFilter(I_C_Location.COLUMNNAME_C_Location_ID, locationIds)
				.addEqualsFilter(I_C_Location.COLUMNNAME_GeocodingStatus, X_C_Location.GEOCODINGSTATUS_Resolved)
				.create()
				.listColumns(I_C_Location.COLUMNNAME_C_Location_ID, I_C_Location.COLUMNNAME_Latitude, I_C_Location.COLUMNNAME_Longitude)
				.stream()
				.map(row -> toGeographicalCoordinatesWithLocationIdOrNull(row))
				.filter(Predicates.notNull());
	}

	@Nullable
	private static GeographicalCoordinatesWithLocationId toGeographicalCoordinatesWithLocationIdOrNull(final Map<String, Object> row)
	{
		try
		{
			final LocationId locationId = LocationId.ofRepoId(NumberUtils.asInt(row.get(I_C_Location.COLUMNNAME_C_Location_ID), -1));

			final BigDecimal latitude = NumberUtils.asBigDecimal(row.get(I_C_Location.COLUMNNAME_Latitude), null);
			final BigDecimal longitude = NumberUtils.asBigDecimal(row.get(I_C_Location.COLUMNNAME_Longitude), null);
			final GeographicalCoordinates coordinate = GeographicalCoordinates.builder()
					.latitude(latitude)
					.longitude(longitude)
					.build();

			return GeographicalCoordinatesWithLocationId.of(locationId, coordinate);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed converting {} to LocationId/GeoCoordinate. Ignored.", row, ex);
			return null;
		}
	}

}

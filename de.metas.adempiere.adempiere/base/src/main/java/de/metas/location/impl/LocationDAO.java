package de.metas.location.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.model.X_C_Location;
import org.slf4j.Logger;

import java.util.Objects;

import de.metas.location.CountryId;
import de.metas.location.GeographicalCoordinatesWithLocationId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationCreateRequest;
import de.metas.location.LocationId;
import de.metas.location.geocoding.GeographicalCoordinates;
import de.metas.logging.LogManager;
import de.metas.util.Check;
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
		// Don't load records out-of-trx unless you really know what's going on and also know the possible contexts in which a method might be called.
		// * this method is (also) called as part of a full bpartner-creation workflow and we need to be able to roll it back, without leaving back this dangling C_Location.
		// * since the c_location is created in-trx, we also need to (re-)load it in-trx later when we try to create its product-price
		return load(id, I_C_Location.class);
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
		// NOTE2: Don't create records OOT unless you really know what's going on and also know the possible contexts in which a method might be called.
		// * this method is (also) called as part of a full bpartner-creation workflow we need to be able to roll it back, without leaving back this dangling C_Location.
		// * more, as of writing this the getPostalId(..) method which is called below doesn't care about trx, so (by default) it's running within the thread-inherited trx
		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setAddress1(request.getAddress1());
		locationRecord.setAddress2(request.getAddress2());
		locationRecord.setAddress3(request.getAddress3());
		locationRecord.setAddress4(request.getAddress4());

		final String postalValue = request.getPostal();
		locationRecord.setPostal(postalValue);

		locationRecord.setPostal_Add(request.getPostalAdd());
		locationRecord.setCity(request.getCity());
		locationRecord.setC_Region_ID(request.getRegionId());
		locationRecord.setC_Country_ID(request.getCountryId().getRepoId());
		locationRecord.setPOBox(request.getPoBox());

		int postalId = getPostalId(request);

		locationRecord.setC_Postal_ID(postalId);

		save(locationRecord);

		return LocationId.ofRepoId(locationRecord.getC_Location_ID());
	}

	private int getPostalId(final LocationCreateRequest request)
	{
		final String postalValue = request.getPostal();

		if (Check.isEmpty(postalValue))
		{
			return -1;
		}

		final IQueryBuilder<I_C_Postal> postalQuery = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Postal.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Postal.COLUMNNAME_C_Country_ID, request.getCountryId());

		if (request.getRegionId() > 0)
		{
			postalQuery.addEqualsFilter(I_C_Postal.COLUMNNAME_C_Region_ID, request.getRegionId());
		}

		if (!Check.isEmpty(request.getCity()))
		{
			postalQuery.addEqualsFilter(I_C_Postal.COLUMNNAME_City, request.getCity());
		}

		final int postalId = postalQuery.filter(PostalQueryFilter.of(postalValue))
				.create()
				.firstIdOnly();

		return postalId;
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
				.filter(Objects::nonNull);
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

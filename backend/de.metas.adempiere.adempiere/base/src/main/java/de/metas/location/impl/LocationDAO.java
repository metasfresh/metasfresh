package de.metas.location.impl;

import de.metas.location.CountryId;
import de.metas.location.GeographicalCoordinatesWithLocationId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationCreateRequest;
import de.metas.location.LocationId;
import de.metas.location.PostalId;
import de.metas.location.geocoding.GeographicalCoordinates;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.model.X_C_Location;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

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
	public I_C_Postal getPostalById(@NonNull final PostalId postalId)
	{
		return loadOutOfTrx(postalId, I_C_Postal.class);
	}

	@Override
	public List<I_C_Postal> getPostalByIds(@NonNull final Set<PostalId> postalIds)
	{
		return loadByRepoIdAwaresOutOfTrx(postalIds, I_C_Postal.class);
	}

	@Override
	public List<I_C_Location> getByIds(@NonNull final Set<LocationId> ids)
	{
		// Don't load records out-of-trx unless you really know what's going on and also know the possible contexts in which a method might be called.
		// * this method is (also) called as part of a full bpartner-creation workflow and we need to be able to roll it back, without leaving back this dangling C_Location.
		// * since the c_location is created in-trx, we also need to (re-)load it in-trx later when we try to create its product-price
		return loadByRepoIdAwares(ids, I_C_Location.class);
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
	public LocationId createOrReuseLocation(@NonNull final LocationCreateRequest request0)
	{
		LocationCreateRequest requestEffective = request0;

		//
		// Resolve request's postalId if missing
		if (requestEffective.getPostalId() == null)
		{
			final PostalId postalId = findPostalId(requestEffective.getPostal(), requestEffective.getCountryId(), requestEffective.getRegionId(), requestEffective.getCity()).orElse(null);
			requestEffective = requestEffective.withPostalId(postalId);
		}

		//
		// Check if the given request will produce any change in database
		if (requestEffective.getExistingLocationId() != null)
		{
			final I_C_Location existingRecord = getById(requestEffective.getExistingLocationId());
			if (existingRecord != null && LocationCreateRequest.equals(requestEffective, toLocationCreateRequest(existingRecord)))
			{
				// the request won't produce any change, so we can re-use existing ID 
				return requestEffective.getExistingLocationId();
			}
		}

		// NOTE: C_Location table might be heavily used, so it's better to create the address OOT to not lock it.
		// NOTE2: Don't create records OOT unless you really know what's going on and also know the possible contexts in which a method might be called.
		// * this method is (also) called as part of a full bpartner-creation workflow we need to be able to roll it back, without leaving back this dangling C_Location.
		// * more, as of writing this the getPostalId(..) method which is called below doesn't care about trx, so (by default) it's running within the thread-inherited trx
		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		updateRecord(locationRecord, requestEffective);
		save(locationRecord);

		return LocationId.ofRepoId(locationRecord.getC_Location_ID());
	}

	private static void updateRecord(final I_C_Location record, final @NotNull LocationCreateRequest from)
	{
		record.setAddress1(from.getAddress1());
		record.setAddress2(from.getAddress2());
		record.setAddress3(from.getAddress3());
		record.setAddress4(from.getAddress4());

		record.setC_Postal_ID(PostalId.toRepoId(from.getPostalId()));
		record.setPostal(from.getPostal());
		record.setPostal_Add(from.getPostalAdd());

		record.setCity(from.getCity());
		record.setC_Region_ID(from.getRegionId());
		record.setRegionName(from.getRegionName());
		record.setC_Country_ID(from.getCountryId().getRepoId());

		record.setPOBox(from.getPoBox());
	}

	private static LocationCreateRequest toLocationCreateRequest(final I_C_Location record)
	{
		return LocationCreateRequest.builder()
				.existingLocationId(LocationId.ofRepoIdOrNull(record.getC_Location_ID()))
				.address1(record.getAddress1())
				.address2(record.getAddress2())
				.address3(record.getAddress3())
				.address4(record.getAddress4())
				//
				.postalId(PostalId.ofRepoIdOrNull(record.getC_Postal_ID()))
				.postal(record.getPostal())
				.postalAdd(record.getPostal_Add())
				//
				.city(record.getCity())
				.regionId(record.getC_Region_ID())
				.regionName(record.getRegionName())
				.countryId(CountryId.ofRepoId(record.getC_Country_ID()))
				//
				.poBox(record.getPOBox())
				//
				.build();
	}

	private Optional<PostalId> findPostalId(
			@Nullable final String postal,
			@NonNull final CountryId countryId,
			final int regionId,
			@Nullable final String city)
	{
		final String postalNorm = StringUtils.trimBlankToNull(postal);
		if (postalNorm == null)
		{
			return Optional.empty();
		}

		final IQueryBuilder<I_C_Postal> queryBuilder = queryBL.createQueryBuilder(I_C_Postal.class)
				.addOnlyActiveRecordsFilter()
				.filter(PostalQueryFilter.of(postal))
				.addEqualsFilter(I_C_Postal.COLUMNNAME_C_Country_ID, countryId);
		if (regionId > 0)
		{
			queryBuilder.addEqualsFilter(I_C_Postal.COLUMNNAME_C_Region_ID, regionId);
		}
		if (!Check.isEmpty(city))
		{
			queryBuilder.addEqualsFilter(I_C_Postal.COLUMNNAME_City, city);
		}

		return queryBuilder.create().firstIdOnlyOptional(PostalId::ofRepoIdOrNull);
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
				.map(LocationDAO::toGeographicalCoordinatesWithLocationIdOrNull)
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

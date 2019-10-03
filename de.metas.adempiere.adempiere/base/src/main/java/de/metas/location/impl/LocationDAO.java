package de.metas.location.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

import java.util.List;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Location;

import de.metas.location.CountryId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationCreateRequest;
import de.metas.location.LocationId;
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
}

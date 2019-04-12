package de.metas.adempiere.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import de.metas.location.LocationId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_City;
import org.compiere.model.I_C_Location;

import de.metas.adempiere.service.ILocationDAO;
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
	public IQueryBuilder<I_C_City> retrieveCitiesByCountryOrRegionQuery(final Properties ctx, final int countryId, final int regionId)
	{
		final IQueryBuilder<I_C_City> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_City.class, ctx, ITrx.TRXNAME_None)
				.addOnlyContextClientOrSystem()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_City.COLUMNNAME_C_Country_ID, countryId)
				//
				.orderBy()
				.addColumn(I_C_City.COLUMNNAME_Name)
				.addColumn(I_C_City.COLUMNNAME_C_City_ID)
				.endOrderBy();

		if (regionId > 0)
		{
			return queryBuilder.addEqualsFilter(I_C_City.COLUMNNAME_C_Region_ID, regionId);
		}

		return queryBuilder;
	}
}

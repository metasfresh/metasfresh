package de.metas.adempiere.service.impl;

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.I_C_City;

import de.metas.adempiere.service.ILocationDAO;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class LocationDAO implements ILocationDAO
{
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

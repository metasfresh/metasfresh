package org.eevolution.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MProduct;
import org.compiere.model.X_S_Resource;
import org.eevolution.api.IResourceDAO;

import de.metas.adempiere.util.CacheCtx;

public class ResourceDAO implements IResourceDAO
{
	@Override
	@Cached(cacheName = I_S_Resource.Table_Name + "#by"
			+ "#" + I_S_Resource.COLUMNNAME_AD_Client_ID
			+ "#" + I_S_Resource.COLUMNNAME_IsManufacturingResource)
	public List<I_S_Resource> retrievePlants(final @CacheCtx Properties ctx)
	{
		final IQueryBuilder<I_S_Resource> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_S_Resource.class, ctx, ITrx.TRXNAME_None);

		final ICompositeQueryFilter<I_S_Resource> filters = queryBuilder.getFilters();

		//
		// Only manufacturing resources
		filters.addEqualsFilter(I_S_Resource.COLUMNNAME_IsManufacturingResource, true);

		//
		// Only Plant resources
		filters.addEqualsFilter(I_S_Resource.COLUMNNAME_ManufacturingResourceType, X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant);

		//
		// Only for current AD_Client_ID
		filters.addOnlyContextClient(ctx);

		//
		// Only active ones
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_S_Resource.COLUMNNAME_S_Resource_ID);

		return queryBuilder
				.create()
				.list(I_S_Resource.class);
	}

	@Override
	public I_S_Resource retrievePlant(final Properties ctx, final int resourceId)
	{
		for (final I_S_Resource plant : retrievePlants(ctx))
		{
			if (plant.getS_Resource_ID() == resourceId)
			{
				return plant;
			}
		}

		return null;
	}
}

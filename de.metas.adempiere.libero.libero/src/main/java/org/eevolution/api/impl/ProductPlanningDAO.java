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
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.IQueryOrderByBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.api.IProductPlanningDAO;
import org.eevolution.exceptions.NoPlantForWarehouseException;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.adempiere.util.CacheCtx;

public class ProductPlanningDAO implements IProductPlanningDAO
{
	private static final int ANY_S_Resource_ID = -100;

	@Override
	public I_PP_Product_Planning find(final Properties ctx,
			final int AD_Org_ID,
			final int M_Warehouse_ID,
			final int S_Resource_ID,
			final int M_Product_ID,
			final String trxName)
	{
		final IQueryBuilder<I_PP_Product_Planning> queryBuilder = createQueryBuilder(ctx, AD_Org_ID, M_Warehouse_ID, S_Resource_ID, M_Product_ID, trxName);

		//
		// Fetch first matching product planning data
		final I_PP_Product_Planning productPlanningData = queryBuilder.create().first();
		return productPlanningData;
	}

	private IQueryBuilder<I_PP_Product_Planning> createQueryBuilder(final Properties ctx,
			final int AD_Org_ID,
			final int M_Warehouse_ID,
			final int S_Resource_ID,
			final int M_Product_ID,
			final String trxName)
	{
		final IQueryBuilder<I_PP_Product_Planning> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Product_Planning.class, ctx, trxName);

		final ICompositeQueryFilter<I_PP_Product_Planning> filters = queryBuilder.getFilters();
		final IQueryOrderByBuilder<I_PP_Product_Planning> orderBy = queryBuilder.orderBy();

		//
		// Filter by context #AD_Client_ID
		filters.addOnlyContextClient(ctx);

		//
		// Filter by AD_Org_ID: given AD_Org_ID or 0/null
		filters.addInArrayOrAllFilter(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, AD_Org_ID, 0, null);
		orderBy.addColumn(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last);

		//
		// Filter by Warehouse: given M_Warehouse_ID or 0/null
		filters.addInArrayOrAllFilter(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID, 0, null);
		orderBy.addColumn(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, Direction.Descending, Nulls.Last);

		//
		// Filter by Plant: given S_Resource_ID or 0/null
		if (S_Resource_ID != ANY_S_Resource_ID)
		{
			filters.addInArrayOrAllFilter(I_PP_Product_Planning.COLUMNNAME_S_Resource_ID, S_Resource_ID, 0, null);
		}
		orderBy.addColumn(I_PP_Product_Planning.COLUMNNAME_S_Resource_ID, Direction.Descending, Nulls.Last);

		//
		// Filter by Product
		filters.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, M_Product_ID);

		//
		// Only active records
		filters.addOnlyActiveRecordsFilter();
		return queryBuilder;
	}

	@Override
	public I_S_Resource findPlant(final Properties ctx,
			final int adOrgId,
			final I_M_Warehouse warehouse,
			final int productId)
	{
		//
		// First: get the plant directly from Warehouse
		if (warehouse != null)
		{
			final I_S_Resource plant = warehouse.getPP_Plant();
			if (plant != null && plant.getS_Resource_ID() > 0)
			{
				return plant;
			}
		}

		//
		// Try searching for a product planning file and get the warehouse from there
		final int warehouseId = warehouse == null ? -1 : warehouse.getM_Warehouse_ID();
		final List<I_PP_Product_Planning> productPlannings = createQueryBuilder(ctx, adOrgId, warehouseId, ANY_S_Resource_ID, productId, ITrx.TRXNAME_None)
				.create()
				.list();

		int foundPlantId = -1;
		I_S_Resource foundPlant = null;
		for (final I_PP_Product_Planning pp : productPlannings)
		{
			final int plantId = pp.getS_Resource_ID();
			if (plantId <= 0)
			{
				continue;
			}

			//
			// We found more then one Plant => consider it as no plant was found
			if (foundPlantId > 0 && foundPlantId != plantId)
			{
				throw new NoPlantForWarehouseException(adOrgId, warehouseId, productId);
			}

			foundPlant = pp.getS_Resource();
		}

		if (foundPlant == null)
		{
			throw new NoPlantForWarehouseException(adOrgId, warehouseId, productId);
		}

		return foundPlant;
	}

	@Override
	public final List<I_M_Warehouse> retrieveWarehousesForPlant(final Properties ctx, final I_AD_Org org, final I_S_Resource plant)
	{
		Check.assumeNotNull(org, "org not null");
		Check.assumeNotNull(plant, "plant not null");

		final int adOrgId = org.getAD_Org_ID();
		final int ppPlantId = plant.getS_Resource_ID();
		return retrieveWarehousesForPlant(ctx, adOrgId, ppPlantId);
	}

	/**
	 * Retrieve all warehouses which are directly to our Org and Plant.
	 *
	 * @param ctx
	 * @param adOrgId
	 * @param ppPlantId
	 * @return M_Warehouse_ID to {@link I_M_Warehouse} map
	 */
	@Cached(cacheName = I_M_Warehouse.Table_Name + "#by#AD_Org_ID#PP_Plant_ID")
	List<I_M_Warehouse> retrieveWarehousesForPlant(@CacheCtx final Properties ctx, final int adOrgId, final int ppPlantId)
	{
		// services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		//
		// Retrive warehouses which which are directly assigned to Org and Plant
		return queryBL.createQueryBuilder(I_M_Warehouse.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Warehouse.COLUMN_AD_Org_ID, adOrgId)
				.addInArrayOrAllFilter(I_M_Warehouse.COLUMN_PP_Plant_ID, null, ppPlantId)
				.addEqualsFilter(I_M_Warehouse.COLUMN_IsInTransit, false) // skip in transit warehouses
				.create()
				.list();
	}
}

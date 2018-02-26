package de.metas.material.planning.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_Product_Planning;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.util.CacheCtx;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import lombok.NonNull;

public class ProductPlanningDAO implements IProductPlanningDAO
{
	private static final int ANY_S_Resource_ID = -100;

	@Override
	public I_PP_Product_Planning find(
			final int orgId,
			final int warehouseId,
			final int resourceId,
			final int productId,
			final int attributeSetInstanceId)
	{
		final IQueryBuilder<I_PP_Product_Planning> queryBuilder = createQueryBuilder(
				orgId,
				warehouseId,
				resourceId,
				productId,
				attributeSetInstanceId);

		//
		// Fetch first matching product planning data
		final I_PP_Product_Planning productPlanningData = queryBuilder.create().first();
		return productPlanningData;
	}

	@Override
	public I_S_Resource findPlant(
			final int orgId,
			final I_M_Warehouse warehouse,
			final int productId,
			final int attributeSetInstanceId)
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
		{
			final int warehouseId = warehouse == null ? -1 : warehouse.getM_Warehouse_ID();
			final List<Integer> plantIds = createQueryBuilder(orgId, warehouseId, ANY_S_Resource_ID, productId, attributeSetInstanceId)
					.create()
					.stream(I_PP_Product_Planning.class)
					.map(I_PP_Product_Planning::getS_Resource_ID) // get plant
					.filter(plantId -> plantId > 0)
					.distinct()
					.collect(ImmutableList.toImmutableList());
			if (plantIds.isEmpty())
			{
				throw new NoPlantForWarehouseException(orgId, warehouseId, productId);
			}
			else if (plantIds.size() > 1)
			{
				// we found more then one Plant => consider it as no plant was found
				throw new NoPlantForWarehouseException(orgId, warehouseId, productId);
			}
			//
			final I_S_Resource foundPlant = load(plantIds.get(0), I_S_Resource.class);
			if (foundPlant == null)
			{
				throw new NoPlantForWarehouseException(orgId, warehouseId, productId);
			}

			return foundPlant;
		}
	}

	private IQueryBuilder<I_PP_Product_Planning> createQueryBuilder(
			final int orgId,
			final int warehouseId,
			final int resourceId,
			final int productId,
			final int attributeSetInstanceId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_PP_Product_Planning> queryBuilder = queryBL
				.createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter();

		// Filter by context #AD_Client_ID
		queryBuilder.addOnlyContextClient();

		// Filter by AD_Org_ID: given AD_Org_ID or 0/null
		queryBuilder.addInArrayOrAllFilter(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, orgId, 0, null);

		// Filter by Warehouse: given M_Warehouse_ID or 0/null
		queryBuilder.addInArrayOrAllFilter(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, warehouseId, 0, null);

		// Filter by Plant: given S_Resource_ID or 0/null
		if (resourceId != ANY_S_Resource_ID)
		{
			queryBuilder.addInArrayOrAllFilter(I_PP_Product_Planning.COLUMNNAME_S_Resource_ID, resourceId, 0, null);
		}

		// Filter by Product
		queryBuilder.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, productId);

		final ICompositeQueryFilter<I_PP_Product_Planning> attributesFilter = createAttributesFilter(attributeSetInstanceId);

		queryBuilder.filter(attributesFilter);

		return queryBuilder.orderBy()
				.addColumn(I_PP_Product_Planning.COLUMN_SeqNo, Direction.Ascending, Nulls.First)
				.addColumnDescending(I_PP_Product_Planning.COLUMNNAME_IsAttributeDependant) // prefer results with IsAttributeDependant='Y'
				.addColumn(I_PP_Product_Planning.COLUMN_AD_Org_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_PP_Product_Planning.COLUMN_M_Warehouse_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_PP_Product_Planning.COLUMN_S_Resource_ID, Direction.Descending, Nulls.Last)
				.endOrderBy();
	}

	private static ICompositeQueryFilter<I_PP_Product_Planning> createAttributesFilter(final int attributeSetInstanceId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final AttributesKey attributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(attributeSetInstanceId)
				.orElse(AttributesKey.ALL);

		final ICompositeQueryFilter<I_PP_Product_Planning> matchingAsiFilter = queryBL
				.createCompositeQueryFilter(I_PP_Product_Planning.class)
				.setJoinAnd()
				.addEqualsFilter(I_PP_Product_Planning.COLUMN_IsAttributeDependant, true)
				.addStringLikeFilter(I_PP_Product_Planning.COLUMN_StorageAttributesKey, attributesKey.getSqlLikeString(), false);

		final ICompositeQueryFilter<I_PP_Product_Planning> attributesFilter = queryBL
				.createCompositeQueryFilter(I_PP_Product_Planning.class)
				.setJoinOr()
				.addEqualsFilter(I_PP_Product_Planning.COLUMN_IsAttributeDependant, false)
				.addFilter(matchingAsiFilter);

		return attributesFilter;
	}

	@Override
	public final List<I_M_Warehouse> retrieveWarehousesForPlant(final Properties ctx,
			@NonNull final I_AD_Org org,
			@NonNull final I_S_Resource plant)
	{
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

		// Retrieve warehouses which which are directly assigned to Org and Plant
		return queryBL.createQueryBuilder(I_M_Warehouse.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Warehouse.COLUMN_AD_Org_ID, adOrgId)
				.addInArrayOrAllFilter(I_M_Warehouse.COLUMN_PP_Plant_ID, null, ppPlantId)
				.addEqualsFilter(I_M_Warehouse.COLUMN_IsInTransit, false) // skip in transit warehouses
				.create()
				.list();
	}
}

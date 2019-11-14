package de.metas.material.planning.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.util.proxy.Cached;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_Planning;

import com.google.common.collect.ImmutableList;

import de.metas.cache.annotation.CacheCtx;
import de.metas.material.commons.attributes.AttributesKeyPatterns;
import de.metas.material.commons.attributes.AttributesKeyQueryHelper;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IResourceDAO;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;

public class ProductPlanningDAO implements IProductPlanningDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IResourceDAO resourcesRepo = Services.get(IResourceDAO.class);

	@Override
	public I_PP_Product_Planning getById(@NonNull final ProductPlanningId ppProductPlanningId)
	{
		return loadOutOfTrx(ppProductPlanningId, I_PP_Product_Planning.class);
	}

	@Override
	public Optional<I_PP_Product_Planning> find(@NonNull final ProductPlanningQuery productPlanningQuery)
	{
		final IQueryBuilder<I_PP_Product_Planning> queryBuilder = createQueryBuilder(
				productPlanningQuery.getOrgId(),
				productPlanningQuery.getWarehouseId(),
				productPlanningQuery.getPlantId(),
				productPlanningQuery.getProductId(),
				productPlanningQuery.getAttributeSetInstanceId());

		//
		// Fetch first matching product planning data
		final I_PP_Product_Planning productPlanningData = queryBuilder.create().first();
		return Optional.ofNullable(productPlanningData);
	}

	@Override
	public I_S_Resource findPlant(
			final int orgRepoId,
			final I_M_Warehouse warehouse,
			final int productRepoId,
			final int attributeSetInstanceRepoId)
	{
		//
		// First: get the plant directly from Warehouse
		if (warehouse != null)
		{
			final ResourceId plantId = ResourceId.ofRepoIdOrNull(warehouse.getPP_Plant_ID());
			if (plantId != null)
			{
				return resourcesRepo.getById(plantId);
			}
		}

		//
		// Try searching for a product planning file and get the warehouse from there
		{
			final OrgId orgId = OrgId.ofRepoIdOrAny(orgRepoId);
			final WarehouseId warehouseId = warehouse != null ? WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()) : null;
			final ProductId productId = ProductId.ofRepoId(productRepoId);
			final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(attributeSetInstanceRepoId);
			final IQueryBuilder<I_PP_Product_Planning> queryBuilder = createQueryBuilder(
					orgId,
					warehouseId,
					(ResourceId)null,  // any plant
					productId,
					attributeSetInstanceId);

			final List<ResourceId> plantIds = queryBuilder
					.create()
					.stream(I_PP_Product_Planning.class)
					.map(I_PP_Product_Planning::getS_Resource_ID) // get plant
					.filter(plantId -> plantId > 0)
					.distinct()
					.map(ResourceId::ofRepoId)
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
			else
			{
				return resourcesRepo.getById(plantIds.get(0));
			}
		}
	}

	private IQueryBuilder<I_PP_Product_Planning> createQueryBuilder(
			final OrgId orgId,
			final WarehouseId warehouseId,
			final ResourceId resourceId,
			final ProductId productId,
			final AttributeSetInstanceId attributeSetInstanceId)
	{
		final IQueryBuilder<I_PP_Product_Planning> queryBuilder = queryBL
				.createQueryBuilder(I_PP_Product_Planning.class)
				.addOnlyActiveRecordsFilter();

		// Filter by context #AD_Client_ID
		queryBuilder.addOnlyContextClient();

		// Filter by AD_Org_ID: given AD_Org_ID or 0/null
		queryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY, null);

		// Filter by Warehouse: given M_Warehouse_ID or 0/null
		queryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, warehouseId, null);

		// Filter by Plant: given S_Resource_ID or 0/null
		if (resourceId != null)
		{
			queryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_S_Resource_ID, resourceId, null);
		}

		// Filter by Product if provided

		queryBuilder.addInArrayFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, productId, null);

		// Filter by ASI
		final ICompositeQueryFilter<I_PP_Product_Planning> attributesFilter = createAttributesFilter(attributeSetInstanceId);
		queryBuilder.filter(attributesFilter);

		return queryBuilder.orderBy()
				.addColumn(I_PP_Product_Planning.COLUMN_SeqNo, Direction.Ascending, Nulls.First)
				.addColumnDescending(I_PP_Product_Planning.COLUMNNAME_IsAttributeDependant) // prefer results with IsAttributeDependant='Y'
				.addColumn(I_PP_Product_Planning.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_PP_Product_Planning.COLUMNNAME_M_Warehouse_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_PP_Product_Planning.COLUMN_S_Resource_ID, Direction.Descending, Nulls.Last)
				.endOrderBy();
	}

	private ICompositeQueryFilter<I_PP_Product_Planning> createAttributesFilter(final AttributeSetInstanceId attributeSetInstanceId)
	{
		final ICompositeQueryFilter<I_PP_Product_Planning> matchingAsiFilter = queryBL
				.createCompositeQueryFilter(I_PP_Product_Planning.class)
				.setJoinAnd()
				.addEqualsFilter(I_PP_Product_Planning.COLUMN_IsAttributeDependant, true)
				.addFilter(createStorageAttributeKeyFilter(attributeSetInstanceId));

		return queryBL.createCompositeQueryFilter(I_PP_Product_Planning.class)
				.setJoinOr()
				.addEqualsFilter(I_PP_Product_Planning.COLUMN_IsAttributeDependant, false)
				.addFilter(matchingAsiFilter);
	}

	private static IQueryFilter<I_PP_Product_Planning> createStorageAttributeKeyFilter(final AttributeSetInstanceId attributeSetInstanceId)
	{
		final AttributesKey attributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(attributeSetInstanceId)
				.orElse(AttributesKey.ALL);

		return AttributesKeyQueryHelper
				.createFor(I_PP_Product_Planning.COLUMN_StorageAttributesKey)
				.createFilter(AttributesKeyPatterns.ofAttributeKey(attributesKey));
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
		// Retrieve warehouses which which are directly assigned to Org and Plant
		return queryBL.createQueryBuilder(I_M_Warehouse.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Warehouse.COLUMN_AD_Org_ID, adOrgId)
				.addInArrayOrAllFilter(I_M_Warehouse.COLUMN_PP_Plant_ID, null, ppPlantId)
				.addEqualsFilter(I_M_Warehouse.COLUMN_IsInTransit, false) // skip in transit warehouses
				.create()
				.list();
	}

	@Override
	public void save(final I_PP_Product_Planning productPlanningRecord)
	{
		saveRecord(productPlanningRecord);
	}

	@Override
	public void setProductBOMIdIfAbsent(
			@NonNull final ProductId productId,
			@NonNull final ProductBOMId bomId)
	{
		final List<I_PP_Product_Planning> productPlanningRecords = queryBL
				.createQueryBuilder(I_PP_Product_Planning.class)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_PP_Product_BOM_ID, null)
				.create()
				.list();

		for (final I_PP_Product_Planning productPlanningRecord : productPlanningRecords)
		{
			productPlanningRecord.setPP_Product_BOM_ID(bomId.getRepoId());
			save(productPlanningRecord);
		}
	}
}

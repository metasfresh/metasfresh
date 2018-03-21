package org.adempiere.warehouse.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.model.WarehousePickingGroup;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_M_Warehouse_PickingGroup;
import org.compiere.util.Env;
import org.eevolution.model.I_M_Warehouse_Routing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

public class WarehouseDAO implements IWarehouseDAO
{

	@Override
	public boolean hasAvailableDocTypes(final Properties ctx, final int warehouseId, final String trxName)
	{
		final List<I_M_Warehouse_Routing> warehouseRoutings = retrieveWarehouseRoutings(ctx, warehouseId, trxName);
		return !warehouseRoutings.isEmpty();
	}

	@Cached(cacheName = I_M_Warehouse_Routing.Table_Name + "#By#M_Warehouse_ID")
	public List<I_M_Warehouse_Routing> retrieveWarehouseRoutings(final @CacheCtx Properties ctx, final int warehouseId, final @CacheTrx String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Warehouse_Routing.class, ctx, trxName)
				.filter(new EqualsQueryFilter<I_M_Warehouse_Routing>(I_M_Warehouse_Routing.COLUMNNAME_M_Warehouse_ID, warehouseId))
				.filter(ActiveRecordQueryFilter.getInstance(I_M_Warehouse_Routing.class))
				.create()
				.list(I_M_Warehouse_Routing.class);
	}

	@Override
	public boolean isDocTypeAllowed(final Properties ctx, final int warehouseId, final I_C_DocType docType, final String trxName)
	{
		final List<I_M_Warehouse_Routing> validRoutings = retrieveWarehouseRoutings(ctx, warehouseId, trxName);

		if (validRoutings.isEmpty())
		{
			// The warehouse is not type specific. Allowed for all doc types.
			return true;
		}

		final String docBaseType = docType.getDocBaseType();

		for (final I_M_Warehouse_Routing routing : validRoutings)
		{
			if (docBaseType.equalsIgnoreCase(routing.getDocBaseType()))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public List<I_M_Locator> retrieveLocators(final I_M_Warehouse warehouse)
	{
		Check.assumeNotNull(warehouse, "warehouse not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(warehouse);
		final String trxName = InterfaceWrapperHelper.getTrxName(warehouse);
		final int warehouseId = warehouse.getM_Warehouse_ID();

		return retrieveLocators(ctx, warehouseId, trxName);
	}

	@Cached(cacheName = I_M_Locator.Table_Name + "#By#M_Warehouse_ID")
	/* package */List<I_M_Locator> retrieveLocators(@CacheCtx final Properties ctx, final int warehouseId, @CacheTrx final String trxName)
	{
		final IQueryOrderBy orderBy = Services.get(IQueryBL.class).createQueryOrderByBuilder(I_M_Locator.class)
				.addColumn(I_M_Locator.COLUMNNAME_X)
				.addColumn(I_M_Locator.COLUMNNAME_Y)
				.addColumn(I_M_Locator.COLUMNNAME_Z)
				.createQueryOrderBy();

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Locator.class, ctx, trxName)
				.filter(new EqualsQueryFilter<I_M_Locator>(I_M_Locator.COLUMNNAME_M_Warehouse_ID, warehouseId))
				.create()
				.setOrderBy(orderBy)
				.list(I_M_Locator.class);
	}

	@Override
	public List<I_M_Warehouse> retrieveWarehouses(final Properties ctx, final String docBaseType)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Warehouse_Routing> queryWarehouseRoutingForDocBaseType = queryBL.createQueryBuilder(I_M_Warehouse_Routing.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Warehouse_Routing.COLUMNNAME_DocBaseType, docBaseType)
				.create();

		final IQuery<I_M_Warehouse_Routing> queryWarehouseRoutingAllActive = queryBL
				.createQueryBuilder(I_M_Warehouse_Routing.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.create();

		final ICompositeQueryFilter<I_M_Warehouse> filterWarehouseRouting = queryBL.createCompositeQueryFilter(I_M_Warehouse.class)
				.setJoinOr()
				// warehouse shall have a routing to given DocBaseType
				.addInSubQueryFilter(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, I_M_Warehouse_Routing.COLUMNNAME_M_Warehouse_ID, queryWarehouseRoutingForDocBaseType)
				// or warehouse shall have no routing at all
				.addNotInSubQueryFilter(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, I_M_Warehouse_Routing.COLUMNNAME_M_Warehouse_ID, queryWarehouseRoutingAllActive)
		//
		;

		return queryBL.createQueryBuilder(I_M_Warehouse.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.filter(filterWarehouseRouting)
				//
				.orderBy()
				.addColumn(I_M_Warehouse.COLUMNNAME_Name)
				.endOrderBy()
				//
				.create()
				.list(I_M_Warehouse.class);
	}

	@Override
	public I_M_Warehouse retrieveOrgWarehouse(final Properties ctx, final int adOrgId)
	{
		final I_AD_OrgInfo orgInfo = Services.get(IOrgDAO.class).retrieveOrgInfo(ctx, adOrgId, ITrx.TRXNAME_None);
		// Check.assumeNotNull(orgInfo, "OrgInfo not null"); // NOTE: commented out because it fails some JUnit test in case there is not OrgInfo

		if (orgInfo == null)
		{
			return null;
		}

		if (orgInfo.getM_Warehouse_ID() <= 0)
		{
			return null;
		}

		return orgInfo.getM_Warehouse();
	}
	
	@Override
	public int retrieveOrgWarehousePOId(final int adOrgId)
	{
		final I_AD_OrgInfo orgInfo = Services.get(IOrgDAO.class).retrieveOrgInfo(Env.getCtx(), adOrgId, ITrx.TRXNAME_None);
		// Check.assumeNotNull(orgInfo, "OrgInfo not null"); // NOTE: commented out because it fails some JUnit test in case there is not OrgInfo
		if (orgInfo == null)
		{
			return -1;
		}

		return orgInfo.getM_WarehousePO_ID();
	}


	@Override
	@Cached(cacheName = I_M_Warehouse.Table_Name + "#InTransitForOrg")
	public List<I_M_Warehouse> retrieveWarehousesInTransitForOrg(@CacheCtx final Properties ctx, final int adOrgId)
	{
		final IQueryBuilder<I_M_Warehouse> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Warehouse.class, ctx, ITrx.TRXNAME_None);

		queryBuilder.getCompositeFilter()
				.addEqualsFilter(I_M_Warehouse.COLUMNNAME_AD_Org_ID, adOrgId)
				.addEqualsFilter(I_M_Warehouse.COLUMNNAME_IsInTransit, true)
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID);

		return queryBuilder.create()
				.list(I_M_Warehouse.class);
	}

	@Override
	public I_M_Warehouse retrieveWarehouseInTransitForOrg(final Properties ctx, final int adOrgId)
	{
		final List<I_M_Warehouse> warehouses = retrieveWarehousesInTransitForOrg(ctx, adOrgId);
		if (warehouses == null || warehouses.isEmpty())
		{
			return null;
		}
		else
		{
			return warehouses.get(0);
		}
	}

	@Override
	public List<I_M_Warehouse> retrieveForOrg(final Properties ctx, final int AD_Org_ID)
	{
		final IQueryBuilder<I_M_Warehouse> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Warehouse.class, ctx, ITrx.TRXNAME_None);

		queryBuilder.getCompositeFilter()
				.addEqualsFilter(I_M_Warehouse.COLUMNNAME_AD_Org_ID, AD_Org_ID)
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID);

		return queryBuilder.create()
				.list(I_M_Warehouse.class);
	}

	@Override
	public List<I_M_Warehouse> retrieveWarehousesForCtx(final Properties ctx)
	{
		final List<I_M_Warehouse> warehouses = Services.get(IQueryBL.class).createQueryBuilder(I_M_Warehouse.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_Warehouse.class);

		return warehouses;
	}

	// TODO: implement a way to reset a cache when I_M_Warehouse_PickingGroup is changed
	@Cached(cacheName = I_M_Warehouse.Table_Name)
	public ImmutableList<WarehousePickingGroup> retrieveWarehouseGroups()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ImmutableSetMultimap<Integer, Integer> warehouseIdsByPickingGroupId = queryBL.createQueryBuilder(I_M_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_M_Warehouse.COLUMNNAME_M_Warehouse_PickingGroup_ID)
				.create()
				.listDistinct(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, I_M_Warehouse.COLUMNNAME_M_Warehouse_PickingGroup_ID)
				.stream()
				.map(record -> {
					final int warehouseId = (int)record.get(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID);
					final int warehousePickingGroupId = (int)record.get(I_M_Warehouse.COLUMNNAME_M_Warehouse_PickingGroup_ID);
					return GuavaCollectors.entry(warehousePickingGroupId, warehouseId);
				})
				.collect(GuavaCollectors.toImmutableSetMultimap());

		return queryBL.createQueryBuilder(I_M_Warehouse_PickingGroup.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(warehousePickingGroupPO -> WarehousePickingGroup.builder()
						.id(warehousePickingGroupPO.getM_Warehouse_PickingGroup_ID())
						.name(warehousePickingGroupPO.getName())
						.description(warehousePickingGroupPO.getDescription())
						.warehouseIds(warehouseIdsByPickingGroupId.get(warehousePickingGroupPO.getM_Warehouse_PickingGroup_ID()))
						.build())
				.collect(ImmutableList.toImmutableList());

	}

	@Override
	public WarehousePickingGroup getWarehousePickingGroupContainingWarehouseId(final int warehouseId)
	{
		return retrieveWarehouseGroups()
				.stream()
				.filter(warehousePickingGroup -> warehousePickingGroup.containsWarehouseId(warehouseId))
				.findFirst().orElse(null);
	}

	@Override
	public int retrieveLocatorIdByBarcode(final String barcode)
	{
		if (Check.isEmpty(barcode, true))
		{
			throw new AdempiereException("Invalid locator barcode: " + barcode);
		}

		try
		{
			final int locatorId = Integer.parseInt(barcode);
			Check.assume(locatorId > 0, "locatorId > 0");
			return locatorId;
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid locator barcode: " + barcode, ex);
		}
	}
}

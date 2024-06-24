package de.metas.material.planning.ddorder.impl;

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

import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.ddorder.IDistributionNetworkDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class DistributionNetworkDAO implements IDistributionNetworkDAO
{
	@Override
	public I_DD_NetworkDistribution getById(@NonNull final DistributionNetworkId id)
	{
		return InterfaceWrapperHelper.load(id, I_DD_NetworkDistribution.class);
	}

	@Override
	public List<I_DD_NetworkDistributionLine> retrieveAllNetworkLines(@NonNull final I_DD_NetworkDistribution distributionNetwork)
	{
		final DistributionNetworkId distributionNetworkId = DistributionNetworkId.ofRepoId(distributionNetwork.getDD_NetworkDistribution_ID());
		final Properties ctx = InterfaceWrapperHelper.getCtx(distributionNetwork);
		final String trxName = InterfaceWrapperHelper.getTrxName(distributionNetwork);

		return retrieveAllNetworkLines(ctx, distributionNetworkId, trxName);
	}

	@Cached(cacheName = I_DD_NetworkDistributionLine.Table_Name + "#by#" + I_DD_NetworkDistributionLine.COLUMNNAME_DD_NetworkDistribution_ID)
		/* package */List<I_DD_NetworkDistributionLine> retrieveAllNetworkLines(
			@CacheCtx final Properties ctx,
			@NonNull final DistributionNetworkId distributionNetworkId,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_DD_NetworkDistributionLine> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DD_NetworkDistributionLine.class, ctx, trxName);

		final ICompositeQueryFilter<I_DD_NetworkDistributionLine> filters = queryBuilder.getCompositeFilter();
		filters.addEqualsFilter(I_DD_NetworkDistributionLine.COLUMNNAME_DD_NetworkDistribution_ID, distributionNetworkId);

		queryBuilder.orderBy()
				.addColumn(I_DD_NetworkDistributionLine.COLUMNNAME_PriorityNo)
				.addColumn(I_DD_NetworkDistributionLine.COLUMNNAME_M_Shipper_ID);

		return queryBuilder.create()
				.list();
	}

	@Override
	public List<I_DD_NetworkDistributionLine> retrieveNetworkLinesByTargetWarehouse(final I_DD_NetworkDistribution distributionNetwork, final WarehouseId targetWarehouseId)
	{
		final List<I_DD_NetworkDistributionLine> result = new ArrayList<>();
		for (final I_DD_NetworkDistributionLine line : retrieveAllNetworkLines(distributionNetwork))
		{
			// Skip inactive lines
			if (!line.isActive())
			{
				continue;
			}

			final WarehouseId lineTargetWarehouseId = WarehouseId.ofRepoId(line.getM_Warehouse_ID());
			if (WarehouseId.equals(lineTargetWarehouseId, targetWarehouseId))
			{
				result.add(line);
			}
		}
		return result;
	}

	@Override
	public List<I_DD_NetworkDistributionLine> retrieveNetworkLinesBySourceWarehouse(final I_DD_NetworkDistribution distributionNetwork, final int sourceWarehouseId)
	{
		final List<I_DD_NetworkDistributionLine> result = new ArrayList<>();
		for (final I_DD_NetworkDistributionLine line : retrieveAllNetworkLines(distributionNetwork))
		{
			// Skip inactive lines
			if (!line.isActive())
			{
				continue;
			}

			if (line.getM_WarehouseSource_ID() == sourceWarehouseId)
			{
				result.add(line);
			}
		}
		return result;
	}

	@Override
	public List<I_M_Warehouse> retrieveWarehousesInSamePlantAs(final Properties ctx, final int targetWarehouseId)
	{
		final Map<Integer, List<I_M_Warehouse>> targetWarehouseId2sourceWarehouses = retriveTargetWarehouse2SourceWarehousesWithKeepTargetPlant(ctx);
		final List<I_M_Warehouse> sourceWarehouses = targetWarehouseId2sourceWarehouses.get(targetWarehouseId);
		if (sourceWarehouses == null || sourceWarehouses.isEmpty())
		{
			return Collections.emptyList();
		}
		return new ArrayList<>(sourceWarehouses);
	}

	/**
	 * @param ctx
	 * @return target M_Warehouse_ID to source warehouses (from network lines which have IsKeepTargetPlant set)
	 */
	@Cached(cacheName = I_DD_NetworkDistributionLine.Table_Name + "#targetWarehouseId2SourceWarehouses")
	Map<Integer, List<I_M_Warehouse>> retriveTargetWarehouse2SourceWarehousesWithKeepTargetPlant(@CacheCtx final Properties ctx)
	{
		// services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Set<ArrayKey> target2sourceWarehouseIdLinks = new HashSet<>();
		final Map<Integer, List<I_M_Warehouse>> targetWarehouseId2sourceWarehouses = new HashMap<>();

		//
		// Retrieve Source warehouses from distribution network, which:
		// * has our Plant and Org warehouses as target
		// * are configured to keep the target warehouse's Plant
		final List<I_DD_NetworkDistributionLine> networkLinesWithKeepTargetPlant = queryBL
				.createQueryBuilder(I_DD_NetworkDistributionLine.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_NetworkDistributionLine.COLUMNNAME_IsKeepTargetPlant, true)
				//
				.create()
				.list();

		for (final I_DD_NetworkDistributionLine networkLine : networkLinesWithKeepTargetPlant)
		{
			final int targetWarehouseId = networkLine.getM_Warehouse_ID();
			final int sourceWarehouseId = networkLine.getM_WarehouseSource_ID();

			// Make sure we are not adding the same link more then once
			if (!target2sourceWarehouseIdLinks.add(Util.mkKey(targetWarehouseId, sourceWarehouseId)))
			{
				continue;
			}

			List<I_M_Warehouse> sourceWarehouses = targetWarehouseId2sourceWarehouses.get(targetWarehouseId);
			if (sourceWarehouses == null)
			{
				sourceWarehouses = new ArrayList<>();
				targetWarehouseId2sourceWarehouses.put(targetWarehouseId, sourceWarehouses);
			}

			final I_M_Warehouse sourceWarehouse = networkLine.getM_WarehouseSource();
			sourceWarehouses.add(sourceWarehouse);
		}

		return new HashMap<>(targetWarehouseId2sourceWarehouses);
	}
}

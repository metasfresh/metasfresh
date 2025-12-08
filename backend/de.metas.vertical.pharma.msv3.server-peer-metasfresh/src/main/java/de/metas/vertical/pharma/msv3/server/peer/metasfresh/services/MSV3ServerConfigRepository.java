package de.metas.vertical.pharma.msv3.server.peer.metasfresh.services;

import de.metas.cache.CCache;
import de.metas.product.ProductCategoryId;
import de.metas.util.Services;
import de.metas.vertical.pharma.msv3.server.model.I_MSV3_Server;
import de.metas.vertical.pharma.msv3.server.model.I_MSV3_Server_Product_Category;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.model.MSV3ServerConfig;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.model.MSV3ServerConfig.MSV3ServerConfigBuilder;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.groups.picking.WarehousePickingGroupId;
import org.springframework.stereotype.Repository;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class MSV3ServerConfigRepository
{
	private final CCache<Integer, MSV3ServerConfig> serverConfigCache = CCache.<Integer, MSV3ServerConfig> builder()
			.tableName(I_MSV3_Server.Table_Name)
			.initialCapacity(1)
			.additionalTableNameToResetFor(I_MSV3_Server_Product_Category.Table_Name)
			.build();

	public MSV3ServerConfig getServerConfig()
	{
		return serverConfigCache.getOrLoad(0, this::retrieveServerConfig);
	}

	private MSV3ServerConfig retrieveServerConfig()
	{
		final MSV3ServerConfigBuilder serverConfigBuilder = MSV3ServerConfig.builder();

		final I_MSV3_Server serverConfigRecord = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_MSV3_Server.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_MSV3_Server.class);
		if (serverConfigRecord != null)
		{
			final int fixedQtyAvailableToPromise = serverConfigRecord.getFixedQtyAvailableToPromise();
			serverConfigBuilder.fixedQtyAvailableToPromise(fixedQtyAvailableToPromise);

			final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
			final WarehousePickingGroupId warehousePickingGroupId = WarehousePickingGroupId.ofRepoId(serverConfigRecord.getM_Warehouse_PickingGroup_ID());

			// note that the DAO method invoked by this supplier is cached
			serverConfigBuilder.warehousePickingGroupSupplier(() -> warehouseDAO.getWarehousePickingGroupById(warehousePickingGroupId));
		}

		Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_MSV3_Server_Product_Category.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_MSV3_Server_Product_Category.class)
				.forEach(productCategoryRecord -> serverConfigBuilder.productCategoryId(ProductCategoryId.ofRepoId(productCategoryRecord.getM_Product_Category_ID())));

		return serverConfigBuilder.build();
	}
}

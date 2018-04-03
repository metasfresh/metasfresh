package de.metas.vertical.pharma.msv3.server.peer.metasfresh.services;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.CCache;
import org.springframework.stereotype.Repository;

import de.metas.vertical.pharma.msv3.server.model.I_MSV3_Server;
import de.metas.vertical.pharma.msv3.server.model.I_MSV3_Server_Product_Category;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.model.MSV3ServerConfig;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.model.MSV3ServerConfig.MSV3ServerConfigBuilder;

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
	private final CCache<Integer, MSV3ServerConfig> serverConfigCache = CCache.<Integer, MSV3ServerConfig> newCache(I_MSV3_Server.Table_Name, 1, CCache.EXPIREMINUTES_Never)
			.addResetForTableName(I_MSV3_Server_Product_Category.Table_Name);

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
			serverConfigBuilder.qtyAvailableToPromiseMin(serverConfigRecord.getQty_AvailableToPromise_Min().intValueExact());

			final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
			final int warehousePickingGroupId = serverConfigRecord.getM_Warehouse_PickingGroup_ID();
			serverConfigBuilder.warehousePickingGroupSupplier(() -> warehouseDAO.getWarehousePickingGroupById(warehousePickingGroupId));
		}

		Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_MSV3_Server_Product_Category.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_MSV3_Server_Product_Category.class)
				.forEach(productCategoryRecord -> serverConfigBuilder.productCategoryId(productCategoryRecord.getM_Product_Category_ID()));

		return serverConfigBuilder.build();
	}
}

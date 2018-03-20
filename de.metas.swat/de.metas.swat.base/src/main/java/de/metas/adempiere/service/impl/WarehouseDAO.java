package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.swat.base
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

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.adempiere.warehouse.model.I_M_Warehouse;

import de.metas.adempiere.service.IWarehouseDAO;
import de.metas.adempiere.util.CacheCtx;

public class WarehouseDAO implements IWarehouseDAO
{

	@Override
	@Cached(cacheName = I_M_Warehouse.Table_Name + "#" + I_M_Warehouse.COLUMNNAME_IsIssueWarehouse)
	public I_M_Warehouse retrieveWarehouseForIssuesOrNull(@CacheCtx final Properties ctx)
	{
		final I_M_Warehouse warehouse = Services.get(IQueryBL.class).createQueryBuilder(I_M_Warehouse.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_Warehouse.COLUMNNAME_IsIssueWarehouse, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_Warehouse.class);
		return warehouse;
	}

	@Override
	public final I_M_Warehouse retrieveWarehouseForIssues(Properties ctx)
	{
		final I_M_Warehouse warehouse = retrieveWarehouseForIssuesOrNull(ctx);
		if (warehouse == null)
		{
			throw new AdempiereException("@NotFound@ @M_Warehouse_ID@ (@IsIssueWarehouse@=@Y@)");
		}
		return warehouse;
	}

	@Override
	public I_M_Warehouse retrieveQuarantineWarehouseOrNull()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Warehouse.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Warehouse.COLUMNNAME_IsQuarantineWarehouse, true)
				.orderBy(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID)
				.create()
				.first();
	}

}

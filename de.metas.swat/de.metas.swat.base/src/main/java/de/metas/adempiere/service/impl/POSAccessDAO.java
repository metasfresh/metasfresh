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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.I_C_POS_Profile;
import org.adempiere.model.I_C_POS_Profile_Warehouse;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;

import de.metas.adempiere.service.IPOSAccessDAO;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

public class POSAccessDAO implements IPOSAccessDAO
{

	@Override
	@Cached(cacheName = I_C_POS_Profile.Table_Name + "#by#" + I_C_POS_Profile.COLUMNNAME_AD_Role_ID)
	public I_C_POS_Profile retrieveProfileByRole(@CacheCtx final Properties ctx, final int adRoleId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_POS_Profile.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_POS_Profile.COLUMNNAME_AD_Role_ID, adRoleId)
				.create()
				.firstOnly(I_C_POS_Profile.class);
	}

	@Override
	@Cached
	public List<I_C_POS_Profile_Warehouse> retrieveWarehouseProfiles(@CacheCtx final Properties ctx, final int posProfileId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_POS_Profile_Warehouse.class, ctx, trxName)
				.filter(new EqualsQueryFilter<I_C_POS_Profile_Warehouse>(I_C_POS_Profile_Warehouse.COLUMNNAME_C_POS_Profile_ID, posProfileId))
				.create()
				.setOnlyActiveRecords(true)
				.list(I_C_POS_Profile_Warehouse.class);

	}

}

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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.I_C_POS_Profile;
import org.adempiere.model.I_C_POS_Profile_Warehouse;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;

import de.metas.adempiere.service.IPOSAccessBL;
import de.metas.adempiere.service.IPOSAccessDAO;
import de.metas.security.RoleId;
import de.metas.util.Services;

public class POSAccessBL implements IPOSAccessBL
{

	@Override
	public List<I_M_Warehouse> getAvailableWarehouses(Properties ctx)
	{
		final RoleId roleId = Env.getLoggedRoleId(ctx);
		final I_C_POS_Profile profile = Services.get(IPOSAccessDAO.class).retrieveProfileByRole(ctx, roleId.getRepoId(), ITrx.TRXNAME_None);

		if (null == profile)
		{
			// No profile defined for this role. Assume no warehouses.
			return Collections.emptyList();
		}

		final List<I_C_POS_Profile_Warehouse> profileWarehouses = Services.get(IPOSAccessDAO.class).retrieveWarehouseProfiles(ctx, profile.getC_POS_Profile_ID(), ITrx.TRXNAME_None);

		if ((null == profileWarehouses) || (profileWarehouses.isEmpty()))
		{
			// No warehouses defined for this profile.
			return Collections.emptyList();
		}

		final List<I_M_Warehouse> warehouseList = new ArrayList<I_M_Warehouse>();

		for (I_C_POS_Profile_Warehouse profileWarehouse : profileWarehouses)
		{
			// We don't check for exists and null values because M_Warehouse is mandatory in I_C_POS_Profile_Warehouse and there's an index on it.
			warehouseList.add(profileWarehouse.getM_Warehouse());
		}

		return warehouseList;
	}

	@Override
	public List<I_M_Warehouse> filterWarehousesByProfile(Properties ctx, List<I_M_Warehouse> initialList)
	{
		if (null == initialList || initialList.isEmpty())
		{
			// We don't have any warehouses defined for this POS. Return empty list.
			return Collections.emptyList();
		}
		
		final List<I_M_Warehouse> result = new ArrayList<I_M_Warehouse>(initialList.size());
		final List<I_M_Warehouse> profileWarehouses = getAvailableWarehouses(ctx);
		
		for (final I_M_Warehouse warehouse : initialList)
		{
			if (profileWarehouses.contains(warehouse))
			{
				result.add(warehouse);
			}
		}
		
		return result;
	}

}

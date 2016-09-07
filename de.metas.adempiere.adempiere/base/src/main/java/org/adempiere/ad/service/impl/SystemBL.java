package org.adempiere.ad.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.I_AD_System;
import org.compiere.model.MSystem;
import org.compiere.util.Ini;

import de.metas.adempiere.util.CacheCtx;

public class SystemBL implements ISystemBL
{
	@Override
	@Cached(cacheName = I_AD_System.Table_Name)
	public MSystem get(@CacheCtx final Properties ctx)
	{
		// Retrieve AD_System record
		final I_AD_System system = Services.get(IQueryBL.class).createQueryBuilder(I_AD_System.class, ctx, ITrx.TRXNAME_None)
				.create()
				.firstOnly(I_AD_System.class);

		// guard agaist null (shall not happen)
		if (system == null)
		{
			return null;
		}

		final MSystem systemPO = LegacyAdapters.convertToPO(system);

		//
		// If running on server side, update the system info
		// TODO i think we shall move this logic somewhere else where is obvious what we are doing and not hidden here.
		if (Ini.getRunMode() == RunMode.BACKEND)
		{
			if (systemPO.setInfo())
			{
				InterfaceWrapperHelper.save(systemPO);
			}
		}

		return systemPO;
	}
}

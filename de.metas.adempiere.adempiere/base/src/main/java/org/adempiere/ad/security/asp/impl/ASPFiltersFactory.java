package org.adempiere.ad.security.asp.impl;

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

import org.adempiere.ad.security.asp.IASPFilters;
import org.adempiere.ad.security.asp.IASPFiltersFactory;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;

import de.metas.util.Services;

public class ASPFiltersFactory implements IASPFiltersFactory
{
	@Override
	@Cached(cacheName = I_AD_Client.Table_Name + "#ASPFilters")
	public IASPFilters getASPFiltersForClient(final int adClientId)
	{
		final Properties ctx = Env.getCtx();
		final I_AD_Client adClient = Services.get(IClientDAO.class).retriveClient(ctx, adClientId);
		if (adClient == null)
		{
			return NullASPFilters.instance;
		}
		if (!adClient.isUseASP())
		{
			return NullASPFilters.instance;
		}

		return new ASPFilters(adClientId);
	}

	@Override
	public IASPFilters getASPFiltersForClient(final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		return getASPFiltersForClient(adClientId);
	}
}

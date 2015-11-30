package org.adempiere.ad.window.api.impl;

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
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Window;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;

public class ADWindowDAO implements IADWindowDAO
{

	@Override
	public String retrieveWindowName(final int adWindowId)
	{
		final Properties ctx = Env.getCtx();
		final I_AD_Window window = retrieveWindow(ctx, adWindowId); // using a simple DB call would be faster, but this way it's less coupled and after all we have caching
		return window.getName();
	}

	@Override
	@Cached(cacheName = I_AD_Window.Table_Name + "#By#" + I_AD_Window.COLUMNNAME_AD_Window_ID)
	public I_AD_Window retrieveWindow(
			@CacheCtx final Properties ctx,
			final int adWindowId)
	{
		final I_AD_Window window = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Window.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Window.COLUMNNAME_AD_Window_ID, adWindowId)
				.create()
				.firstOnly(I_AD_Window.class);

		return window;
	}

}

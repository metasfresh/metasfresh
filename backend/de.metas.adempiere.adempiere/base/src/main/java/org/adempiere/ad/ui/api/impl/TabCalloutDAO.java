package org.adempiere.ad.ui.api.impl;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.ui.api.ITabCalloutDAO;
import org.adempiere.model.I_AD_Tab_Callout;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;

import de.metas.adempiere.util.CacheCtx;

public class TabCalloutDAO implements ITabCalloutDAO
{
	@Override
	@Cached(cacheName = I_AD_Tab_Callout.Table_Name + "#by#" + I_AD_Tab_Callout.COLUMNNAME_AD_Tab_ID)
	public List<I_AD_Tab_Callout> retrieveAllCalloutsDefinition(@CacheCtx Properties ctx, int adTabId)
	{
		final IQueryBuilder<I_AD_Tab_Callout> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Tab_Callout.class, ctx, ITrx.TRXNAME_None)
				// .addOnlyActiveRecordsFilter() // NOTE: we are retrieving all callouts, even if they are active or not
				.addEqualsFilter(I_AD_Tab_Callout.COLUMNNAME_AD_Tab_ID, adTabId);

		queryBuilder.orderBy()
				.addColumn(I_AD_Tab_Callout.COLUMNNAME_SeqNo);

		return queryBuilder.create().list(I_AD_Tab_Callout.class);
	}

}

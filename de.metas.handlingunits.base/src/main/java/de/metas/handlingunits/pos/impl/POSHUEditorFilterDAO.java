package de.metas.handlingunits.pos.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.proxy.Cached;

import de.metas.adempiere.util.CacheCtx;
import de.metas.handlingunits.model.I_C_POS_HUEditor_Filter;
import de.metas.handlingunits.pos.IPOSHUEditorFilterDAO;

/**
 * POS Filter configuration retrieval
 *
 * @author al
 */
public class POSHUEditorFilterDAO implements IPOSHUEditorFilterDAO
{
	@Override
	public List<I_C_POS_HUEditor_Filter> retrieveFilters(final IContextAware contextProvider)
	{
		Check.assumeNotNull(contextProvider, "contextProvider not null");
		// NOTE: we are retrieving master data, so there is NO point to use the transaction name
		return retrieveFilters(contextProvider.getCtx());
	}

	@Cached(cacheName = I_C_POS_HUEditor_Filter.Table_Name + "#All")
	List<I_C_POS_HUEditor_Filter> retrieveFilters(@CacheCtx final Properties ctx)
	{
		Check.assumeNotNull(ctx, "ctx not null");

		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_POS_HUEditor_Filter> queryBuilder = queryBL.createQueryBuilder(I_C_POS_HUEditor_Filter.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem();

		queryBuilder.orderBy()
				.addColumn(I_C_POS_HUEditor_Filter.COLUMNNAME_C_POS_HUEditor_Filter_ID); // just to have a predictable order

		return queryBuilder.create()
				.list();
	}
}

package org.adempiere.pos.impl;

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
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pos.IPOSKeyKayoutDAO;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_POSKey;
import org.compiere.model.I_C_POSKeyLayout;

import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.util.Check;
import de.metas.util.Services;

public class POSKeyKayoutDAO implements IPOSKeyKayoutDAO
{
	@Override
	public List<I_C_POSKey> retrievePOSKeys(final I_C_POSKeyLayout keyLayout)
	{
		Check.assumeNotNull(keyLayout, "keyLayout not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(keyLayout);
		final String trxName = InterfaceWrapperHelper.getTrxName(keyLayout);
		final int posKeyLayoutId = keyLayout.getC_POSKeyLayout_ID();

		return retrievePOSKeys(ctx, posKeyLayoutId, trxName);
	}

	@Cached(cacheName = I_C_POSKey.Table_Name + "#by#" + I_C_POSKey.COLUMNNAME_C_POSKeyLayout_ID)
	List<I_C_POSKey> retrievePOSKeys(@CacheCtx Properties ctx, int posKeyLayoutId, @CacheTrx String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_POSKey> queryBuilder = queryBL.createQueryBuilder(I_C_POSKey.class, ctx, trxName)
				.addEqualsFilter(I_C_POSKey.COLUMNNAME_C_POSKeyLayout_ID, posKeyLayoutId)
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_C_POSKey.COLUMNNAME_SeqNo);

		return queryBuilder
				.create()
				.list(I_C_POSKey.class);
	}
}

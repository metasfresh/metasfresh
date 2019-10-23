package de.metas.adempiere.docline.sort.api.impl;

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
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.I_C_DocLine_Sort_Item;

import de.metas.adempiere.docline.sort.api.IDocLineSortDAO;
import de.metas.adempiere.docline.sort.api.IDocLineSortItemFinder;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.util.Check;
import de.metas.util.Services;

public class DocLineSortDAO implements IDocLineSortDAO
{
	@Override
	public List<I_C_DocLine_Sort_Item> retrieveItems(final I_C_DocLine_Sort docLineSort)
	{
		Check.assumeNotNull(docLineSort, "docLineSort not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(docLineSort);
		final int docLineSortId = docLineSort.getC_DocLine_Sort_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(docLineSort);
		return retrieveItems(ctx, docLineSortId, trxName);
	}

	@Cached(cacheName = I_C_DocLine_Sort_Item.Table_Name + "#by#" + I_C_DocLine_Sort_Item.COLUMNNAME_C_DocLine_Sort_ID)
	public List<I_C_DocLine_Sort_Item> retrieveItems(final @CacheCtx Properties ctx, final int docLineSortId, final @CacheTrx String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_DocLine_Sort_Item> queryBuilder = queryBL.createQueryBuilder(I_C_DocLine_Sort_Item.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_DocLine_Sort_Item.COLUMN_C_DocLine_Sort_ID, docLineSortId);
		return queryBuilder.create()
				.list();
	}

	@Override
	public IDocLineSortItemFinder findDocLineSort()
	{
		return new DocLineSortItemFinder();
	}
}

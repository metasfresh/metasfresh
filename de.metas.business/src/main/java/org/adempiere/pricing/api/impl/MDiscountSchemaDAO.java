package org.adempiere.pricing.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IMDiscountSchemaDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

public class MDiscountSchemaDAO implements IMDiscountSchemaDAO
{
	@Override
	public I_M_DiscountSchema getById(final int discountSchemaId)
	{
		if (discountSchemaId <= 0)
		{
			return null;
		}

		return loadOutOfTrx(discountSchemaId, I_M_DiscountSchema.class);
	}

	@Override
	public List<I_M_DiscountSchemaBreak> retrieveBreaks(final int discountSchemaId)
	{
		return retrieveBreaks(Env.getCtx(), discountSchemaId, ITrx.TRXNAME_None);
	}

	@Override
	public List<I_M_DiscountSchemaBreak> retrieveBreaks(final I_M_DiscountSchema schema)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(schema);
		final String trxName = InterfaceWrapperHelper.getTrxName(schema);

		return retrieveBreaks(ctx, schema.getM_DiscountSchema_ID(), trxName);
	}

	@Cached(cacheName = I_M_DiscountSchemaBreak.Table_Name + "#by#" + I_M_DiscountSchemaBreak.COLUMNNAME_M_DiscountSchema_ID)
	/* package */ List<I_M_DiscountSchemaBreak> retrieveBreaks(@CacheCtx final Properties ctx, int discountSchemaId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DiscountSchemaBreak.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_DiscountSchemaBreak.COLUMNNAME_M_DiscountSchema_ID, discountSchemaId)
				.orderBy(I_M_DiscountSchemaBreak.COLUMNNAME_SeqNo)
				.orderBy(I_M_DiscountSchemaBreak.COLUMNNAME_M_DiscountSchemaBreak_ID)
				.create()
				.listImmutable(I_M_DiscountSchemaBreak.class);
	}

	@Override
	public List<I_M_DiscountSchemaLine> retrieveLines(final I_M_DiscountSchema schema)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(schema);
		final String trxName = InterfaceWrapperHelper.getTrxName(schema);

		return retrieveLines(ctx, schema.getM_DiscountSchema_ID(), trxName);
	}

	@Cached(cacheName = I_M_DiscountSchemaLine.Table_Name + "#by#" + I_M_DiscountSchemaLine.COLUMNNAME_M_DiscountSchema_ID)
	/* package */ List<I_M_DiscountSchemaLine> retrieveLines(@CacheCtx final Properties ctx, final int discountSchemaId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DiscountSchemaLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_DiscountSchemaLine.COLUMNNAME_M_DiscountSchema_ID, discountSchemaId)
				.orderBy(I_M_DiscountSchemaLine.COLUMNNAME_SeqNo)
				.orderBy(I_M_DiscountSchemaLine.COLUMNNAME_M_DiscountSchemaLine_ID)
				.create()
				.listImmutable(I_M_DiscountSchemaLine.class);
	}
}

package org.adempiere.uom.api.impl;

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
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;

public class UOMDAO implements IUOMDAO
{

	@Override
	public I_C_UOM retrieveByX12DE355(@CacheCtx final Properties ctx, final String x12de355)
	{
		return retrieveByX12DE355(ctx, x12de355, true);
	}

	@Cached(cacheName = I_C_UOM.Table_Name
			+ "#by"
			+ "#" + I_C_UOM.COLUMNNAME_X12DE355
			+ "#" + I_C_UOM.COLUMNNAME_IsDefault
			+ "#" + I_C_UOM.COLUMNNAME_AD_Client_ID)
	@Override
	public I_C_UOM retrieveByX12DE355(@CacheCtx final Properties ctx, final String x12de355, final boolean throwExIfNull)
	{
		final IQueryBuilder<I_C_UOM> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_UOM.class, ctx, ITrx.TRXNAME_None)
				.addInArrayOrAllFilter(I_C_UOM.COLUMNNAME_AD_Client_ID, Env.CTXVALUE_AD_Client_ID_System, Env.getAD_Client_ID(ctx))
				.addEqualsFilter(I_C_UOM.COLUMNNAME_X12DE355, x12de355)
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_C_UOM.COLUMNNAME_AD_Client_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_C_UOM.COLUMNNAME_IsDefault, Direction.Descending, Nulls.Last);

		final I_C_UOM uom = queryBuilder.create().first(I_C_UOM.class);
		if (uom == null && throwExIfNull)
		{
			throw new AdempiereException("@NotFound@ @C_UOM_ID@ (@X12DE355@: " + x12de355 + ")");
		}
		return uom;
	}

	@Override
	public I_C_UOM retrieveEachUOM(final Properties ctx)
	{
		return retrieveByX12DE355(ctx, X12DE355_Each);
	}
}

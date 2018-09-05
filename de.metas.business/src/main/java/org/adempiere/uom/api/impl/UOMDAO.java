package org.adempiere.uom.api.impl;

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

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.uom.UomId;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;
import lombok.NonNull;

public class UOMDAO implements IUOMDAO
{
	@Override
	public I_C_UOM getById(final int uomId)
	{
		return loadOutOfTrx(uomId, I_C_UOM.class); // assume it's cached on table level
	}

	@Override
	public I_C_UOM getById(final UomId uomId)
	{
		return loadOutOfTrx(uomId, I_C_UOM.class); // assume it's cached on table level
	}

	@Override
	public UomId getUomIdByX12DE355(final String x12de355)
	{
		final boolean throwExIfNull = true;
		return retrieveUomIdByX12DE355(Env.getCtx(), x12de355, throwExIfNull);
	}

	@Override
	public I_C_UOM retrieveByX12DE355(final Properties ctx, final String x12de355)
	{
		final boolean throwExIfNull = true;
		return retrieveByX12DE355(ctx, x12de355, throwExIfNull);
	}

	@Override
	public I_C_UOM retrieveByX12DE355(@CacheCtx final Properties ctx, final String x12de355, final boolean throwExIfNull)
	{
		UomId uomId = retrieveUomIdByX12DE355(ctx, x12de355, throwExIfNull);
		if (uomId == null)
		{
			return null;
		}
		else
		{
			return getById(uomId);
		}
	}

	@Cached(cacheName = I_C_UOM.Table_Name
			+ "#by"
			+ "#" + I_C_UOM.COLUMNNAME_X12DE355
			+ "#" + I_C_UOM.COLUMNNAME_IsDefault
			+ "#" + I_C_UOM.COLUMNNAME_AD_Client_ID)
	public UomId retrieveUomIdByX12DE355(
			@CacheCtx final Properties ctx,
			@NonNull final String x12de355,
			final boolean throwExIfNull)
	{
		final int uomId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_UOM.class, ctx, ITrx.TRXNAME_None)
				.addOnlyContextClientOrSystem()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_UOM.COLUMNNAME_X12DE355, x12de355)
				.orderBy()
				.addColumn(I_C_UOM.COLUMNNAME_AD_Client_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_C_UOM.COLUMNNAME_IsDefault, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.firstId();

		if (uomId <= 0 && throwExIfNull)
		{
			throw new AdempiereException("@NotFound@ @C_UOM_ID@ (@X12DE355@: " + x12de355 + ")");
		}

		return UomId.ofRepoIdOrNull(uomId);
	}

	@Override
	public I_C_UOM retrieveEachUOM(final Properties ctx)
	{
		return retrieveByX12DE355(ctx, X12DE355_Each);
	}
}

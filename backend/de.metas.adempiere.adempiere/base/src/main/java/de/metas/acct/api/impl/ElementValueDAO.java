/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.acct.api.impl;

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.RPadQueryFilterModifier;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_ElementValue;

import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.IElementValueDAO;
import de.metas.cache.annotation.CacheCtx;
import de.metas.util.Services;
import lombok.NonNull;

public class ElementValueDAO implements IElementValueDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Cached(cacheName = I_C_ElementValue.Table_Name, expireMinutes = Cached.EXPIREMINUTES_Never)
	@Override
	public I_C_ElementValue retrieveById(@CacheCtx final Properties ctx, final int elementValueId)
	{
		if (elementValueId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(ctx, elementValueId, I_C_ElementValue.class, ITrx.TRXNAME_None);
	}

	@Override
	public @NonNull ImmutableSet<ElementValueId> getElementValueIdsBetween(@NonNull final String valueFrom, @NonNull final String valueTo)
	{
		final RPadQueryFilterModifier rpad = new RPadQueryFilterModifier(20, "0");

		final I_C_ElementValue from = queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_ElementValue.COLUMNNAME_Value, CompareQueryFilter.Operator.STRING_LIKE_IGNORECASE, valueFrom + "%")
				.setLimit(QueryLimit.ONE)
				.orderBy(I_C_ElementValue.COLUMNNAME_Value)
				.create()
				.first();

		final I_C_ElementValue to = queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_ElementValue.COLUMNNAME_Value, CompareQueryFilter.Operator.STRING_LIKE_IGNORECASE, valueTo + "%")
				.setLimit(QueryLimit.ONE)
				.orderByDescending(I_C_ElementValue.COLUMNNAME_Value)
				.create()
				.first();

		return queryBL.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.addBetweenFilter(I_C_ElementValue.COLUMNNAME_Value, from.getValue(), to.getValue(), rpad)
				.create()
				.listIds(ElementValueId::ofRepoId);
	}
}

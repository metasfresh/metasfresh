package de.metas.calendar.impl;

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


import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_PeriodControl;

import de.metas.cache.annotation.CacheCtx;
import de.metas.calendar.IPeriodDAO;
import de.metas.util.Services;

public class PeriodDAO implements IPeriodDAO
{
	@Override
	@Cached(cacheName = I_C_PeriodControl.Table_Name + "#by#" + I_C_PeriodControl.COLUMNNAME_C_Period_ID, expireMinutes = 120)
	public Map<String, I_C_PeriodControl> retrievePeriodControlsByDocBaseType(final @CacheCtx Properties ctx, final int periodId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PeriodControl.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_PeriodControl.COLUMN_C_Period_ID, periodId)
				.addOnlyActiveRecordsFilter()
				.create()
				.map(I_C_PeriodControl.class, I_C_PeriodControl.COLUMN_DocBaseType.<String> asValueFunction());
	}
}

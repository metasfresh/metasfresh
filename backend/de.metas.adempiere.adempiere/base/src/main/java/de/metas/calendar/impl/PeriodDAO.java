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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

<<<<<<< HEAD

import java.util.Map;
import java.util.Properties;

=======
import com.google.common.collect.ImmutableMap;
import de.metas.cache.annotation.CacheCtx;
import de.metas.calendar.IPeriodDAO;
import de.metas.document.DocBaseType;
import de.metas.util.Services;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_PeriodControl;

<<<<<<< HEAD
import de.metas.cache.annotation.CacheCtx;
import de.metas.calendar.IPeriodDAO;
import de.metas.util.Services;
=======
import java.util.Map;
import java.util.Properties;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

public class PeriodDAO implements IPeriodDAO
{
	@Override
	@Cached(cacheName = I_C_PeriodControl.Table_Name + "#by#" + I_C_PeriodControl.COLUMNNAME_C_Period_ID, expireMinutes = 120)
<<<<<<< HEAD
	public Map<String, I_C_PeriodControl> retrievePeriodControlsByDocBaseType(final @CacheCtx Properties ctx, final int periodId)
=======
	public Map<DocBaseType, I_C_PeriodControl> retrievePeriodControlsByDocBaseType(final @CacheCtx Properties ctx, final int periodId)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PeriodControl.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_PeriodControl.COLUMN_C_Period_ID, periodId)
				.addOnlyActiveRecordsFilter()
<<<<<<< HEAD
				.create()
				.map(I_C_PeriodControl.class, I_C_PeriodControl.COLUMN_DocBaseType.<String> asValueFunction());
=======
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> DocBaseType.ofCode(record.getDocBaseType()),
						record -> record
				));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}

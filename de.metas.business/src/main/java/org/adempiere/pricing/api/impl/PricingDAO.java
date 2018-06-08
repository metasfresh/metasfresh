package org.adempiere.pricing.api.impl;

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
import org.adempiere.pricing.api.IPricingDAO;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;

import de.metas.adempiere.util.CacheCtx;

public class PricingDAO implements IPricingDAO
{
	@Override
	@Cached(cacheName = I_C_PricingRule.Table_Name + "#All")
	public List<I_C_PricingRule> retrievePricingRules(@CacheCtx final Properties ctx)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_PricingRule.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				//
				.orderBy()
				.addColumn(I_C_PricingRule.COLUMNNAME_SeqNo)
				.addColumn(I_C_PricingRule.COLUMNNAME_C_PricingRule_ID)
				.endOrderBy()
				//
				.create()
				.listImmutable(I_C_PricingRule.class);
	}
}

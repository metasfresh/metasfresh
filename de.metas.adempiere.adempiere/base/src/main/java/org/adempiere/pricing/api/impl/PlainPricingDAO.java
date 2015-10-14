package org.adempiere.pricing.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.pricing.api.IPricingDAO;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.util.TypedAccessor;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;

public class PlainPricingDAO implements IPricingDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}

	@Override
	public List<I_C_PricingRule> retrievePricingRules(Properties ctx)
	{

		final List<I_C_PricingRule> priceRules = db.getRecords(I_C_PricingRule.class, new IQueryFilter<I_C_PricingRule>()
		{

			@Override
			public boolean accept(I_C_PricingRule pojo)
			{
				if (!pojo.isActive())
				{
					return false;
				}
				return true;
			}

		});
		Collections.sort(priceRules, new AccessorComparator<I_C_PricingRule, Integer>(
				new ComparableComparator<Integer>(),
				new TypedAccessor<Integer>()
				{

					@Override
					public Integer getValue(Object o)
					{
						return ((I_C_PricingRule)o).getSeqNo();
					}
				}));

		return priceRules;
	}
}

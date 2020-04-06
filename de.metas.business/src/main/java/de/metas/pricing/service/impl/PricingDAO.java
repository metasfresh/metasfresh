package de.metas.pricing.service.impl;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.util.reflect.ClassReference;
import org.slf4j.Logger;

import java.util.Objects;
import com.google.common.collect.ImmutableList;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.pricing.service.IPricingDAO;
import de.metas.pricing.service.PricingRuleDescriptor;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;

public class PricingDAO implements IPricingDAO
{
	private static final Logger logger = LogManager.getLogger(PricingDAO.class);

	private final CCache<Integer, ImmutableList<PricingRuleDescriptor>> //
	pricingRuleDescriptorsCache = CCache.<Integer, ImmutableList<PricingRuleDescriptor>> builder()
			.cacheName("pricingRuleDescriptorsCache")
			.additionalTableNameToResetFor(I_C_PricingRule.Table_Name)
			.initialCapacity(1)
			.build();

	@Override
	public List<PricingRuleDescriptor> getPricingRules()
	{
		return pricingRuleDescriptorsCache.getOrLoad(0, this::retrievePricingRules);
	}

	private ImmutableList<PricingRuleDescriptor> retrievePricingRules()
	{
		return Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_C_PricingRule.class)
				.addOnlyActiveRecordsFilter()
				//
				.orderBy(I_C_PricingRule.COLUMNNAME_SeqNo)
				.orderBy(I_C_PricingRule.COLUMNNAME_C_PricingRule_ID)
				//
				.create()
				.stream()
				.map(this::toPricingRuleDescriptorNoFail)
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.distinctBy(PricingRuleDescriptor::getPricingRuleClass))
				.collect(ImmutableList.toImmutableList());
	}

	private PricingRuleDescriptor toPricingRuleDescriptorNoFail(final I_C_PricingRule record)
	{
		try
		{
			return toPricingRuleDescriptor(record);
		}
		catch (final Exception ex)
		{
			logger.warn("Skipping invalid pricing rule definition: {}", record, ex);
			return null;
		}
	}

	private PricingRuleDescriptor toPricingRuleDescriptor(final I_C_PricingRule record)
	{
		return PricingRuleDescriptor.builder()
				.name(record.getName())
				.pricingRuleClass(ClassReference.ofClassname(record.getClassname()))
				.build();
	}
}

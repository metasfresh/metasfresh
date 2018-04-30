package de.metas.pricing.conditions;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.compiere.model.I_M_AttributeInstance;

import com.google.common.base.Predicates;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder
public class PricingConditions
{
	private static final Comparator<PricingConditionsBreak> REVERSED_BREAKS_COMPARATOR = Comparator.<PricingConditionsBreak, BigDecimal> comparing(b -> b.getMatchCriteria().getBreakValue())
			.reversed();

	int discountSchemaId;
	
	PricingConditionsDiscountType discountType;

	boolean bpartnerFlatDiscount;
	BigDecimal flatDiscount;

	boolean quantityBased;

	List<PricingConditionsBreak> breaks;


	/**
	 * Pick the first break that applies based on product, category and attribute instance
	 * 
	 * @return schema break or null
	 */
	public PricingConditionsBreak pickApplyingBreak(final @NonNull PricingConditionsBreakQuery query)
	{
		final List<I_M_AttributeInstance> attributeInstances = query.getAttributeInstances();
		if (hasNoAttributeValues(attributeInstances))
		{
			return pickApplyingBreak(query, /* attributeValueId */ -1);
		}
		else
		{
			return attributeInstances.stream()
					.filter(attributeInstance -> hasAttributeValue(attributeInstance))
					.map(attributeInstance -> pickApplyingBreak(query, attributeInstance.getM_AttributeValue_ID()))
					.filter(Predicates.notNull())
					.findFirst()
					.orElse(null);
		}
	}

	private PricingConditionsBreak pickApplyingBreak(final PricingConditionsBreakQuery query, final int attributeValueId)
	{
		return breaks.stream()
				.filter(schemaBreak -> schemaBreakMatches(schemaBreak, query, attributeValueId))
				.sorted(REVERSED_BREAKS_COMPARATOR)
				.findFirst()
				.orElse(null);
	}

	private static boolean hasAttributeValue(final I_M_AttributeInstance instance)
	{
		return instance.getM_AttributeValue_ID() > 0;
	}

	/**
	 * Check if the instances are empty ( have "" value)
	 *
	 * @param instances
	 * @return true if all the instances are empty, false if at least one is not
	 */
	private static boolean hasNoAttributeValues(final Collection<I_M_AttributeInstance> instances)
	{
		if (instances == null || instances.isEmpty())
		{
			return true;
		}

		return instances.stream().noneMatch(instance -> hasAttributeValue(instance));
	}

	private boolean schemaBreakMatches(
			final PricingConditionsBreak schemaBreak,
			final PricingConditionsBreakQuery query,
			final int attributeValueId)
	{
		final BigDecimal breakValue = quantityBased ? query.getQty() : query.getAmt();

		final PricingConditionsBreakMatchCriteria matchCriteria = schemaBreak.getMatchCriteria();
		return matchCriteria.breakValueMatches(breakValue)
				&& matchCriteria.productMatches(query.getProductId(), query.getProductCategoryId())
				&& matchCriteria.attributeMatches(attributeValueId);
	}

}

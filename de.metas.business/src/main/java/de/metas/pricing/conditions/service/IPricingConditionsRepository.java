package de.metas.pricing.conditions.service;

import java.util.Collection;

import org.adempiere.util.ISingletonService;

import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsId;

public interface IPricingConditionsRepository extends ISingletonService
{
	PricingConditions getPricingConditionsById(PricingConditionsId pricingConditionsId);

	PricingConditions getPricingConditionsById(int discountSchemaId);

	Collection<PricingConditions> getPricingConditionsByIds(Collection<Integer> discountSchemaIds);

	PricingConditionsBreakId changePricingConditionsBreak(PricingConditionsBreakChangeRequest request);

	/**
	 * Re-sequence the lines and breaks of the given schema.
	 * This means setting their sequences from 10 to 10.
	 *
	 * @return number of things (lined + breaks) that were modified
	 */
	int resequence(int discountSchemaId);

}

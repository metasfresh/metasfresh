package org.adempiere.pricing.api;

import java.util.Collection;

import org.adempiere.util.ISingletonService;

public interface IMDiscountSchemaDAO extends ISingletonService
{
	PricingConditions getPricingConditionsById(int discountSchemaId);

	Collection<PricingConditions> getPricingConditionsByIds(Collection<Integer> discountSchemaIds);

	/** @return discountSchemaBreakId */
	int changePricingConditionsBreak(PricingConditionsBreakChangeRequest request);

	/**
	 * Re-sequence the lines and breaks of the given schema.
	 * This means setting their sequences from 10 to 10.
	 *
	 * @return number of things (lined + breaks) that were modified
	 */
	int resequence(int discountSchemaId);

}

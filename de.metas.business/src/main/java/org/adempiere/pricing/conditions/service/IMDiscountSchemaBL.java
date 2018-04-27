package org.adempiere.pricing.conditions.service;

import org.adempiere.util.ISingletonService;

public interface IMDiscountSchemaBL extends ISingletonService
{
	/**
	 * Calculate Discount Percentage
	 *
	 */
	DiscountResult calculateDiscount(CalculateDiscountRequest request);
}

package org.adempiere.pricing.api;

import org.adempiere.util.ISingletonService;

public interface IMDiscountSchemaBL extends ISingletonService
{
	/**
	 * Calculate Discount Percentage
	 *
	 */
	DiscountResult calculateDiscount(CalculateDiscountRequest request);
}

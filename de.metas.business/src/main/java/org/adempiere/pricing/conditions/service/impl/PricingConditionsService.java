
package org.adempiere.pricing.conditions.service.impl;

import org.adempiere.pricing.conditions.service.CalculateDiscountRequest;
import org.adempiere.pricing.conditions.service.DiscountResult;
import org.adempiere.pricing.conditions.service.IPricingConditionsService;

public class PricingConditionsService implements IPricingConditionsService
{

	@Override
	public DiscountResult calculateDiscount(final CalculateDiscountRequest request)
	{
		final CalculateDiscountCommand command = new CalculateDiscountCommand(request);
		return command.calculateDiscount();
	}
}

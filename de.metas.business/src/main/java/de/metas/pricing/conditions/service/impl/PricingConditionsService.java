
package de.metas.pricing.conditions.service.impl;

import de.metas.pricing.conditions.service.CalculateDiscountRequest;
import de.metas.pricing.conditions.service.DiscountResult;
import de.metas.pricing.conditions.service.IPricingConditionsService;

public class PricingConditionsService implements IPricingConditionsService
{

	@Override
	public DiscountResult calculateDiscount(final CalculateDiscountRequest request)
	{
		final CalculateDiscountCommand command = new CalculateDiscountCommand(request);
		return command.calculateDiscount();
	}
}


package de.metas.pricing.conditions.service.impl;

import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.pricing.conditions.service.IPricingConditionsService;

public class PricingConditionsService implements IPricingConditionsService
{

	@Override
	public PricingConditionsResult calculatePricingConditions(final CalculatePricingConditionsRequest request)
	{
		final CalculatePricingConditionsCommand command = new CalculatePricingConditionsCommand(request);
		return command.calculate();
	}
}

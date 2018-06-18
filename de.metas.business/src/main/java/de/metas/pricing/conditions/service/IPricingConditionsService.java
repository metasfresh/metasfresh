package de.metas.pricing.conditions.service;

import org.adempiere.util.ISingletonService;

public interface IPricingConditionsService extends ISingletonService
{
	PricingConditionsResult calculatePricingConditions(CalculatePricingConditionsRequest request);
}

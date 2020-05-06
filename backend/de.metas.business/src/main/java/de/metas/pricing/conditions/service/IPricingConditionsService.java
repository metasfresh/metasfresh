package de.metas.pricing.conditions.service;

import java.util.Optional;

import de.metas.util.ISingletonService;

public interface IPricingConditionsService extends ISingletonService
{
	Optional<PricingConditionsResult> calculatePricingConditions(CalculatePricingConditionsRequest request);
}

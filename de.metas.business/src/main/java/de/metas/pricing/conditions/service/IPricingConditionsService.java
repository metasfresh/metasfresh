package de.metas.pricing.conditions.service;

import java.util.Optional;

import org.adempiere.util.ISingletonService;

public interface IPricingConditionsService extends ISingletonService
{
	Optional<PricingConditionsResult> calculatePricingConditions(CalculatePricingConditionsRequest request);
}

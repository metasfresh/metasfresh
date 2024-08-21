
package de.metas.pricing.conditions.service.impl;

import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.conditions.service.IPricingConditionsService;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.Optional;

public class PricingConditionsService implements IPricingConditionsService
{
	final IPricingConditionsRepository pricingConditionsRepository = Services.get(IPricingConditionsRepository.class);

	@Override
	public PricingConditions getPricingConditionsById(@NonNull final PricingConditionsId pricingConditionsId)
	{
		return pricingConditionsRepository.getPricingConditionsById(pricingConditionsId);
	}

	@Override
	public Optional<PricingConditionsResult> calculatePricingConditions(@NonNull final CalculatePricingConditionsRequest request)
	{
		final CalculatePricingConditionsCommand command = new CalculatePricingConditionsCommand(request);
		return command.calculate();
	}
}

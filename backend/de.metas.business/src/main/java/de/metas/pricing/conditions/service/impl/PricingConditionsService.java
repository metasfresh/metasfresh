
package de.metas.pricing.conditions.service.impl;

import java.util.Optional;

import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.conditions.service.IPricingConditionsService;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_DiscountSchema;

public class PricingConditionsService implements IPricingConditionsService
{
	final IPricingConditionsRepository pricingConditionsRepository = Services.get(IPricingConditionsRepository.class);
	@Override
	public Optional<PricingConditionsResult> calculatePricingConditions(@NonNull final CalculatePricingConditionsRequest request)
	{
		final CalculatePricingConditionsCommand command = new CalculatePricingConditionsCommand(request);
		return command.calculate();
	}

	@Override
	public I_M_DiscountSchema getDiscountSchemaById(final int discountSchemaRecordId)
	{
		return pricingConditionsRepository.getDiscountSchemaById(discountSchemaRecordId);
	}
}

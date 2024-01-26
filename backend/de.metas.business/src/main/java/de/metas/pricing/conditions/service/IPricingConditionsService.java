package de.metas.pricing.conditions.service;

import java.util.Optional;

import de.metas.util.ISingletonService;
import org.compiere.model.I_M_DiscountSchema;

public interface IPricingConditionsService extends ISingletonService
{
	Optional<PricingConditionsResult> calculatePricingConditions(CalculatePricingConditionsRequest request);

	I_M_DiscountSchema getDiscountSchemaById(final int discountSchemaRecordId);
}

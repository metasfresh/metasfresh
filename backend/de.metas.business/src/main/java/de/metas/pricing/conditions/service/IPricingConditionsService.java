package de.metas.pricing.conditions.service;

import java.util.Optional;

import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_DiscountSchema;

public interface IPricingConditionsService extends ISingletonService
{
	PricingConditions getPricingConditionsById(@NonNull final PricingConditionsId pricingConditionsId);

	Optional<PricingConditionsResult> calculatePricingConditions(CalculatePricingConditionsRequest request);
}

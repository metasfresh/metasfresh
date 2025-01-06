package de.metas.pricing;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class PricingSystemAndListId
{
	@NonNull PricingSystemId pricingSystemId;
	@NonNull PriceListId priceListId;

	public static PricingSystemAndListId ofRepoIds(final int pricingSystemRepoId, final int priceListRepoId)
	{
		return of(PricingSystemId.ofRepoId(pricingSystemRepoId), PriceListId.ofRepoId(priceListRepoId));
	}
}

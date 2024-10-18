package de.metas.pos.product;

import de.metas.currency.Currency;
import de.metas.pricing.PriceListId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class POSProductsSearchRequest
{
	@NonNull PriceListId priceListId;
	@NonNull Currency currency;
	@NonNull Instant evalDate;
	@Nullable String queryString;
}

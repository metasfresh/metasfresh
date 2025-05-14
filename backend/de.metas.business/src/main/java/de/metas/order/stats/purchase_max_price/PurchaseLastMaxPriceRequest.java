package de.metas.order.stats.purchase_max_price;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@Value
@Builder
public class PurchaseLastMaxPriceRequest
{
	@NonNull ProductId productId;
	@NonNull LocalDate evalDate;

	public static ImmutableSet<PurchaseLastMaxPriceRequest> ofProductIds(
			@NonNull final Set<ProductId> productIds,
			@NonNull final LocalDate date)
	{
		if (productIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final PurchaseLastMaxPriceRequestBuilder template = builder().evalDate(date);

		return productIds.stream()
				.map((productId) -> template.productId(productId).build())
				.collect(ImmutableSet.toImmutableSet());
	}

}

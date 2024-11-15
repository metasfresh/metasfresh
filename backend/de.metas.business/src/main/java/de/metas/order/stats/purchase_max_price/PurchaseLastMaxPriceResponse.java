package de.metas.order.stats.purchase_max_price;

import de.metas.money.Money;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PurchaseLastMaxPriceResponse
{
	public static final PurchaseLastMaxPriceResponse NONE = builder().build();

	@Nullable Money maxPurchasePrice;
}

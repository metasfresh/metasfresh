package de.metas.contracts.modular.computing.purchasecontract.subtractedvalue.interim;

import de.metas.product.ProductPrice;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class ProductPriceWithFlags
{
	@NonNull ProductPrice price;
	@With boolean isCost;
	@With boolean isSubtractedValue;

	public static ProductPriceWithFlags of(final ProductPrice price)
	{
		return ProductPriceWithFlags.builder().price(price).build();
	}

	public ProductPrice toProductPrice()
	{
		return price
				.negateIf(isCost)
				.negateIf(isSubtractedValue);
	}
}

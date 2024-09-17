package de.metas.pos;

import de.metas.currency.CurrencyPrecision;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class POSOrderLine
{
	@NonNull String externalId;
	int localId;

	@NonNull ProductId productId;
	@NonNull String productName;
	@NonNull Quantity qty;
	@NonNull BigDecimal price;

	@NonNull BigDecimal amount;

	private POSOrderLine(
			@NonNull final String externalId,
			final int localId,
			@NonNull final ProductId productId,
			@NonNull final String productName,
			@NonNull final Quantity qty,
			@NonNull final BigDecimal price,
			@Nullable final BigDecimal amount)
	{
		this.externalId = externalId;
		this.localId = localId;
		this.productId = productId;
		this.productName = productName;
		this.qty = qty;
		this.price = price;
		this.amount = amount != null
				? amount
				: CurrencyPrecision.TWO.round(qty.toBigDecimal().multiply(price));
	}
}

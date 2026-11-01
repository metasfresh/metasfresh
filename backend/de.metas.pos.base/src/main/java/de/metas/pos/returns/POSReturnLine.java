package de.metas.pos.returns;

import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * One product line of a {@link POSReturnRequest}: the goods handed back at the till, priced at the till's
 * current price for that product.
 */
@Value
@Builder
public class POSReturnLine
{
	@NonNull ProductId productId;
	@NonNull Quantity qty;

	/** Unit price, per {@link #priceUomId} — the till's current price for the product. */
	@NonNull Money price;
	@NonNull UomId priceUomId;
}

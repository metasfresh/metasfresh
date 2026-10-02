package de.metas.pos.returns;

import de.metas.pos.POSProductsService;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

/**
 * One product+qty line as the REST client sends it: no price, no UOM. The client never sends a price — the
 * till's current price (and its price UOM) is resolved server-side, from {@link POSProductsService}, by
 * {@link POSReturnService#createReturnFromTillPrices}. Kept separate from {@link POSReturnLine}, which DOES
 * carry a price+UOM (the already-priced line {@link POSReturnService#createReturn} works with).
 */
@Value
@Builder
public class POSReturnRequestedLine
{
	@NonNull ProductId productId;
	@NonNull BigDecimal qty;
}

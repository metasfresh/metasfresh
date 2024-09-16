package de.metas.pos;

import de.metas.currency.Amount;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class POSOrderLine
{
	@NonNull ProductId productId;
	@NonNull String productName;
	@NonNull Quantity qty;
	@NonNull Amount price;
	@NonNull TaxId taxId;
	@NonNull Amount netAmt;
	@NonNull Amount taxAmt;
	@NonNull Amount totalAmt;
}

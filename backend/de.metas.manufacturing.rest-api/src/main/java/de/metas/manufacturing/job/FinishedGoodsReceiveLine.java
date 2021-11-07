package de.metas.manufacturing.job;

import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class FinishedGoodsReceiveLine
{
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyToReceive;
	@NonNull Quantity qtyReceived;
	boolean isByOrCoProduct;
}

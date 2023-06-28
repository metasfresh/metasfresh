package de.metas.gplr.source.model;

import de.metas.currency.Amount;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class SourceOrderLine
{
	@NonNull OrderLineId id;
	int lineNo;
	@NonNull ProductId productId;
	@NonNull String productCode;
	@NonNull String productName;
	@NonNull Quantity qtyEntered;
	@NonNull Amount priceFC;
	@Nullable Amount cogsLC;
	@Nullable String batchNo;
	@NonNull Amount lineNetAmtFC;
	@NonNull Amount taxAmtFC;
	@NonNull SourceTaxInfo tax;
}

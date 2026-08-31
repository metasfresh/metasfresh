package de.metas.deliveryplanning.webui.process;

import de.metas.handlingunits.HuId;
import de.metas.inout.InOutId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
class DeliveryPlanningGenerateReceiptResult
{
	@NonNull InOutId receiptId;
	@NonNull HuId receivedVHUId;
	@NonNull ProductId productId;
	@NonNull Quantity qty;
}

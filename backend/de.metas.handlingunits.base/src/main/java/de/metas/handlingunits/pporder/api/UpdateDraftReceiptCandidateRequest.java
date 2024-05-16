package de.metas.handlingunits.pporder.api;

import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;

@Value
@Builder
public class UpdateDraftReceiptCandidateRequest
{
	@NonNull PPOrderId pickingOrderId;
	@NonNull HuId huID;
	@NonNull Quantity qtyReceived;
}

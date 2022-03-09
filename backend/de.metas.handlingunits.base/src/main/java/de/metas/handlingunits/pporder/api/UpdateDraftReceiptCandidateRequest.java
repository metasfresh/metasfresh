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
	@NonNull private final PPOrderId pickingOrderId;
	@NonNull private final HuId huID;
	@NonNull private final Quantity qtyReceived;
}

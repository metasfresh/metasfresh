package de.metas.distribution.ddorder.movement.schedule;

import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import org.adempiere.warehouse.LocatorId;

@Value
@Builder
public class DDOrderMoveScheduleCreateRequest
{
	@NonNull DDOrderId ddOrderId;
	@NonNull DDOrderLineId ddOrderLineId;

	@NonNull ProductId productId;

	//
	// Pick From
	@NonNull LocatorId pickFromLocatorId;
	@NonNull HuId pickFromHUId;
	@NonNull Quantity qtyToPick;
	boolean isPickWholeHU;

	//
	// Drop To
	@NonNull LocatorId dropToLocatorId;
}

package de.metas.ddorder.movement.schedule;

import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.eevolution.api.DDOrderId;
import org.eevolution.api.DDOrderLineId;

import javax.annotation.Nullable;

@Data
@Builder
public class DDOrderMoveSchedule
{
	@NonNull private final DDOrderMoveScheduleId id;
	@NonNull private final DDOrderId ddOrderId;
	@NonNull private final DDOrderLineId ddOrderLineId;

	//
	// Pick From
	@NonNull private final HuId pickFromHUId;
	@Nullable HuId actualHUIdPicked;
	@NonNull private final Quantity qtyToPick;
	private final boolean isPickWholeHU;
}

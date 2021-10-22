package de.metas.handlingunits.ddorder.picking;

import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.DDOrderId;
import org.eevolution.api.DDOrderLineId;

import javax.annotation.Nullable;

@Data
@Builder
public class DDOrderPickSchedule
{
	@NonNull private final DDOrderPickScheduleId id;

	@NonNull private final DDOrderId ddOrderId;
	@NonNull private final DDOrderLineId ddOrderLineId;

	@NonNull private final HuId pickFromHUId;
	@Nullable HuId actualHUIdPicked;

	@NonNull private final Quantity qtyToPick;
	private final boolean isPickWholeHU;
}

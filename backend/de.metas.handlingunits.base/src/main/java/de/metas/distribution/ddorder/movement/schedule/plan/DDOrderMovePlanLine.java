package de.metas.distribution.ddorder.movement.schedule.plan;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DDOrderMovePlanLine
{
	@NonNull I_M_HU pickFromHU;
	@NonNull Quantity qtyToPick;
	boolean isPickWholeHU;

	public HuId getPickFromHUId() {return HuId.ofRepoId(pickFromHU.getM_HU_ID());}
}

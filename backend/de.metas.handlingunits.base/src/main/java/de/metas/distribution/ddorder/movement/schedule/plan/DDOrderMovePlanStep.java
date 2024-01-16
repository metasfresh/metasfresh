package de.metas.distribution.ddorder.movement.schedule.plan;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

@Value
@Builder
public class DDOrderMovePlanStep
{
	@NonNull ProductId productId;

	//
	// Pick From
	@NonNull LocatorId pickFromLocatorId;
	@NonNull I_M_HU pickFromHU;
	@NonNull Quantity qtyToPick;
	boolean isPickWholeHU;

	//
	// Drop To
	@NonNull LocatorId dropToLocatorId;

	public HuId getPickFromHUId() {return HuId.ofRepoId(pickFromHU.getM_HU_ID());}
}

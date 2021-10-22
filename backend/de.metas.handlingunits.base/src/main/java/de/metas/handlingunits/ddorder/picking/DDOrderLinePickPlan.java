package de.metas.handlingunits.ddorder.picking;

import com.google.common.collect.ImmutableList;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.DDOrderLineId;

@Value
@Builder
public class DDOrderLinePickPlan
{
	@NonNull DDOrderLineId ddOrderLineId;
	@NonNull Quantity qtyToPickTarget;
	@NonNull ImmutableList<DDOrderLineStepPlan> steps;

	public boolean isFullyAllocated()
	{
		return qtyToPickTarget.subtract(getQtyToPick()).isZero();
	}

	public Quantity getQtyToPick()
	{
		return steps.stream()
				.map(DDOrderLineStepPlan::getQtyToPick)
				.reduce(Quantity::add)
				.orElseGet(qtyToPickTarget::toZero);
	}
}

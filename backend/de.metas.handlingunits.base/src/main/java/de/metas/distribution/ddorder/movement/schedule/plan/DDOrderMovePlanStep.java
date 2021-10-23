package de.metas.distribution.ddorder.movement.schedule.plan;

import com.google.common.collect.ImmutableList;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import de.metas.distribution.ddorder.DDOrderLineId;

@Value
@Builder
public class DDOrderMovePlanStep
{
	@NonNull DDOrderLineId ddOrderLineId;
	@NonNull Quantity qtyToPickTarget;
	@NonNull ImmutableList<DDOrderMovePlanLine> steps;

	public boolean isFullyAllocated()
	{
		return qtyToPickTarget.subtract(getQtyToPick()).isZero();
	}

	public Quantity getQtyToPick()
	{
		return steps.stream()
				.map(DDOrderMovePlanLine::getQtyToPick)
				.reduce(Quantity::add)
				.orElseGet(qtyToPickTarget::toZero);
	}
}

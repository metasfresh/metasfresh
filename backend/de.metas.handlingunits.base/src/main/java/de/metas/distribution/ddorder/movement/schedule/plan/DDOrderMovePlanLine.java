package de.metas.distribution.ddorder.movement.schedule.plan;

import com.google.common.collect.ImmutableList;
import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import de.metas.distribution.ddorder.DDOrderLineId;

@Value
@Builder
public class DDOrderMovePlanLine
{
	@NonNull DDOrderLineId ddOrderLineId;

	@Getter(AccessLevel.NONE)
	@NonNull Quantity qtyToPickTarget;

	@NonNull ImmutableList<DDOrderMovePlanStep> steps;

	public boolean isFullyAllocated()
	{
		return qtyToPickTarget.subtract(getQtyToPick()).signum() <= 0;
	}

	public Quantity getQtyToPick()
	{
		return steps.stream()
				.map(DDOrderMovePlanStep::getQtyToPick)
				.reduce(Quantity::add)
				.orElseGet(qtyToPickTarget::toZero);
	}
}

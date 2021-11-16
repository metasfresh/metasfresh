package de.metas.distribution.workflows_api;

import de.metas.handlingunits.HuId;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class DistributionJobStep
{
	@NonNull DDOrderMoveScheduleId id;
	@NonNull Quantity qtyToMoveTarget;

	//
	// Pick From
	@NonNull HuId pickFromHUId;
	@Nullable HuId actualHUPicked;
	@NonNull Quantity qtyPicked;
	@Nullable QtyRejectedReasonCode qtyNotPickedReasonCode;

	//
	// Drop To
	boolean droppedToLocator;

	@Builder
	private DistributionJobStep(
			@NonNull final DDOrderMoveScheduleId id,
			@NonNull final Quantity qtyToMoveTarget,
			//
			@NonNull final HuId pickFromHUId,
			@Nullable final HuId actualHUPicked,
			@NonNull final Quantity qtyPicked,
			@Nullable final QtyRejectedReasonCode qtyNotPickedReasonCode,
			//
			final boolean droppedToLocator)
	{
		Quantity.assertSameUOM(qtyToMoveTarget, qtyPicked);

		this.id = id;
		this.qtyToMoveTarget = qtyToMoveTarget;
		this.pickFromHUId = pickFromHUId;
		this.actualHUPicked = actualHUPicked;
		this.qtyPicked = qtyPicked;
		this.qtyNotPickedReasonCode = qtyNotPickedReasonCode;
		this.droppedToLocator = droppedToLocator;
	}
}

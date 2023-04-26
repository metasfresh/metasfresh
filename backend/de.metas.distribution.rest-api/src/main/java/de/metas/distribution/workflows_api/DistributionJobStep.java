package de.metas.distribution.workflows_api;

import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.quantity.Quantity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
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
	@NonNull HUInfo pickFromHU;
	@NonNull Quantity qtyPicked;
	@Nullable QtyRejectedReasonCode qtyNotPickedReasonCode;
	boolean isPickedFromLocator;

	//
	// Drop To
	boolean isDroppedToLocator;

	@NonNull WFActivityStatus status;

	@Builder
	private DistributionJobStep(
			@NonNull final DDOrderMoveScheduleId id,
			@NonNull final Quantity qtyToMoveTarget,
			//
			@NonNull final HUInfo pickFromHU,
			@NonNull final Quantity qtyPicked,
			@Nullable final QtyRejectedReasonCode qtyNotPickedReasonCode,
			final boolean isPickedFromLocator,
			//
			final boolean isDroppedToLocator)
	{
		Quantity.assertSameUOM(qtyToMoveTarget, qtyPicked);

		this.id = id;
		this.qtyToMoveTarget = qtyToMoveTarget;
		this.pickFromHU = pickFromHU;
		this.qtyPicked = qtyPicked;
		this.qtyNotPickedReasonCode = qtyNotPickedReasonCode;
		this.isPickedFromLocator = isPickedFromLocator;
		this.isDroppedToLocator = isDroppedToLocator;

		this.status = computeStatus(this.isPickedFromLocator, this.isDroppedToLocator);
	}

	private static WFActivityStatus computeStatus(final boolean isPickedFromLocator, final boolean isDroppedToLocator)
	{
		if (isPickedFromLocator)
		{
			return isDroppedToLocator ? WFActivityStatus.COMPLETED : WFActivityStatus.IN_PROGRESS;
		}
		else
		{
			return WFActivityStatus.NOT_STARTED;
		}
	}
}

package de.metas.distribution.ddorder.movement.schedule;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.MovementAndLineId;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class DDOrderMoveSchedule
{
	@NonNull private final DDOrderMoveScheduleId id;
	@NonNull private final DDOrderId ddOrderId;
	@NonNull private final DDOrderLineId ddOrderLineId;

	@NonNull private DDOrderMoveScheduleStatus status;
	@NonNull private final ProductId productId;

	//
	// Pick From
	@NonNull private final LocatorId pickFromLocatorId;
	@NonNull private final HuId pickFromHUId;
	@NonNull private final Quantity qtyToPick;
	// Pick From Status
	@Nullable private HuId actualHUIdPicked;
	private boolean isPickWholeHU;
	@NonNull private Quantity qtyPicked;
	@Nullable private QtyRejectedReasonCode qtyNotPickedReason;
	@Nullable private MovementAndLineId pickFromMovementLineId;
	@Nullable private LocatorId inTransitLocatorId;

	//
	// Drop To
	@NonNull private final LocatorId dropToLocatorId;
	// Drop To Status
	@Nullable private MovementAndLineId dropToMovementLineId;

	public I_C_UOM getUOM() {return getQtyToPick().getUOM();}

	public boolean isPickedFrom()
	{
		return pickFromMovementLineId != null;
	}

	public void assertNotPickedFrom()
	{
		if (isPickedFrom()) {throw new AdempiereException("Already Picked From");}
	}

	private void assertPickedFrom()
	{
		if (!isPickedFrom()) {throw new AdempiereException("Pick from required first");}
	}

	public void markAsPickedFrom(
			@NonNull final Quantity qtyPicked,
			@Nullable final QtyRejectedReasonCode qtyNotPickedReason,
			@NonNull final HuId actualHuIdPicked,
			@NonNull final MovementAndLineId pickFromMovementLineId,
			@Nullable final LocatorId inTransitLocatorId)
	{
		assertNotPickedFrom();

		Quantity.assertSameUOM(this.qtyToPick, qtyPicked);
		if (qtyPicked.signum() <= 0)
		{
			throw new AdempiereException("QtyPicked must be greater than zero");
		}
		this.qtyPicked = qtyPicked;

		if (!this.qtyToPick.qtyAndUomCompareToEquals(qtyPicked))
		{
			if (qtyNotPickedReason == null)
			{
				throw new AdempiereException("Reason must be provided when not picking the whole scheduled qty");
			}
			this.qtyNotPickedReason = qtyNotPickedReason;
		}
		else
		{
			this.qtyNotPickedReason = null;
		}

		this.actualHUIdPicked = actualHuIdPicked;
		this.isPickWholeHU = HuId.equals(this.pickFromHUId, actualHUIdPicked);

		this.pickFromMovementLineId = pickFromMovementLineId;
		this.inTransitLocatorId = inTransitLocatorId;

		updateStatus();
	}

	public boolean isDropTo()
	{
		return dropToMovementLineId != null;
	}

	public void assertNotDroppedTo()
	{
		if (isDropTo())
		{
			throw new AdempiereException("Already Dropped To");
		}
	}

	public void markAsDroppedTo(@NonNull final MovementAndLineId dropToMovementLineId)
	{
		assertPickedFrom();
		assertNotDroppedTo();

		this.dropToMovementLineId = dropToMovementLineId;

		updateStatus();
	}

	private void updateStatus()
	{
		this.status = computeStatus();
	}

	private DDOrderMoveScheduleStatus computeStatus()
	{
		if (isDropTo())
		{
			return DDOrderMoveScheduleStatus.COMPLETED;
		}
		else if (isPickedFrom())
		{
			return DDOrderMoveScheduleStatus.IN_PROGRESS;
		}
		else
		{
			return DDOrderMoveScheduleStatus.NOT_STARTED;
		}
	}
}

package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.Check;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.i18n.ExplainedOptional;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.MovementId;
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
	// Pick From Specs
	@NonNull private final LocatorId pickFromLocatorId;
	@NonNull private final HuId pickFromHUId;
	@NonNull private final Quantity qtyToPick;
	private final boolean isPickWholeHU;

	//
	// Drop To Specs
	@NonNull private final LocatorId dropToLocatorId;

	//
	// State
	@Nullable private QtyRejectedReasonCode qtyNotPickedReason;
	@Nullable private DDOrderMoveSchedulePickedHUs pickedHUs;

	public I_C_UOM getUOM() {return getQtyToPick().getUOM();}

	public Quantity getQtyPicked() {return pickedHUs != null ? pickedHUs.getQtyPicked() : getQtyToPick().toZero();}

	public boolean isPickedFrom() {return pickedHUs != null;}

	public void assertNotPickedFrom()
	{
		if (isPickedFrom()) {throw new AdempiereException("Already Picked From");}
	}

	public void assertNotStarted()
	{
		Check.assume(status == DDOrderMoveScheduleStatus.NOT_STARTED, "Expected status NOT_STARTED but was {}", status);
	}

	public void assertPickedFrom()
	{
		if (!isPickedFrom()) {throw new AdempiereException("Pick from required first");}
	}

	public void assertInTransit()
	{
		assertNotDroppedTo();
		assertPickedFrom();
	}

	public void removePickedHUs()
	{
		this.pickedHUs = null;
		this.qtyNotPickedReason = null;
		updateStatus();
	}

	public void markAsPickedFrom(
			@Nullable final QtyRejectedReasonCode qtyNotPickedReason,
			@NonNull final DDOrderMoveSchedulePickedHUs pickedHUs)
	{
		assertNotPickedFrom();

		final Quantity qtyPicked = pickedHUs.getQtyPicked();
		Quantity.assertSameUOM(this.qtyToPick, qtyPicked);
		if (qtyPicked.signum() <= 0)
		{
			throw new AdempiereException("QtyPicked must be greater than zero");
		}

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

		this.pickedHUs = pickedHUs;

		updateStatus();
	}

	public boolean isDropTo()
	{
		return pickedHUs != null && pickedHUs.isDroppedTo();
	}

	public void assertNotDroppedTo()
	{
		if (isDropTo())
		{
			throw new AdempiereException("Already Dropped To");
		}
	}

	public void markAsDroppedTo(@NonNull LocatorId dropToLocatorId, @NonNull final MovementId dropToMovementId)
	{
		assertInTransit();

		final DDOrderMoveSchedulePickedHUs pickedHUs = Check.assumeNotNull(this.pickedHUs, "Expected to be already picked: {}", this);
		pickedHUs.setDroppedTo(dropToLocatorId, dropToMovementId);

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

	@NonNull
	public ExplainedOptional<LocatorId> getInTransitLocatorId()
	{
		if (pickedHUs == null)
		{
			return ExplainedOptional.emptyBecause("Schedule is not picked yet");
		}

		return pickedHUs.getInTransitLocatorId();
	}

	@NonNull
	public ImmutableSet<HuId> getPickedHUIds()
	{
		final DDOrderMoveSchedulePickedHUs pickedHUs = Check.assumeNotNull(this.pickedHUs, "Expected to be already picked: {}", this);
		return pickedHUs.getActualHUIdsPicked();
	}

}

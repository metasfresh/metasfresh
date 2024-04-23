package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PickingJobStepPickFrom
{
	@NonNull PickingJobStepPickFromKey pickFromKey;
	@NonNull LocatorInfo pickFromLocator;
	@NonNull HUInfo pickFromHU;

	@Nullable PickingJobStepPickedTo pickedTo;

	public LocatorId getPickFromLocatorId() {return getPickFromLocator().getId();}

	public WarehouseId getPickFromWarehouseId() {return getPickFromLocatorId().getWarehouseId();}

	public Optional<Quantity> getQtyPicked()
	{
		return Optional.ofNullable(pickedTo != null ? pickedTo.getQtyPicked() : null);
	}

	public Optional<Quantity> getQtyRejected()
	{
		if (pickedTo == null)
		{
			return Optional.empty();
		}

		final QtyRejectedWithReason qtyRejected = pickedTo.getQtyRejected();
		if (qtyRejected == null)
		{
			return Optional.empty();
		}

		return Optional.of(qtyRejected.toQuantity());
	}

	public HuId getPickFromHUId() {return getPickFromHU().getId();}

	public boolean isPicked() {return pickedTo != null;}

	public boolean isNotPicked() {return pickedTo == null;}

	public void assertPicked()
	{
		if (!isPicked())
		{
			throw new AdempiereException("PickFrom was not picked: " + this);
		}
	}

	public void assertNotPicked()
	{
		if (isPicked())
		{
			throw new AdempiereException("PickFrom already picked: " + this);
		}
	}

	public PickingJobStepPickFrom withPickedEvent(@NonNull final PickingJobStepPickedTo pickedTo)
	{
		return toBuilder()
				.pickedTo(pickedTo)
				.build();
	}

	public PickingJobStepPickFrom withUnPickedEvent(@NonNull PickingJobStepUnpickInfo ignoredUnpicked)
	{
		return toBuilder().pickedTo(null).build();
	}
}

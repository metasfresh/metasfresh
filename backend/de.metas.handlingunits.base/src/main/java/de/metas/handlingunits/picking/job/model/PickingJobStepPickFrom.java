package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PickingJobStepPickFrom
{
	@NonNull PickingJobStepPickFromKey pickFromKey;
	@NonNull LocatorInfo pickFromLocator;
	@NonNull HUInfo pickFromHU;

	@Nullable PickingJobStepPickedTo pickedTo;

	public boolean isPicked() {return pickedTo != null;}

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

	public PickingJobStepPickFrom withUnPickedEvent(@NonNull PickingJobStepUnpickInfo unpicked)
	{
		return toBuilder().pickedTo(null).build();
	}
}

package de.metas.cucumber.stepdefs.picking;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.model.I_M_PickingSlot;

public class PickingSlot_StepDefData extends StepDefData<I_M_PickingSlot>
{
	public PickingSlot_StepDefData() {super(I_M_PickingSlot.class);}

	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(final String identifier)
	{
		return getPickingSlotIdAndCaption(StepDefDataIdentifier.ofString(identifier));
	}

	public PickingSlotIdAndCaption getPickingSlotIdAndCaption(final StepDefDataIdentifier identifier)
	{
		final I_M_PickingSlot pickingSlot = get(identifier);
		return PickingSlotIdAndCaption.of(PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID()), pickingSlot.getPickingSlot());
	}
}

package de.metas.handlingunits.picking.job.service.commands;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class LUPackingInstructions
{
	@NonNull HuPackingInstructionsId luPackingInstructionsId;
	@NonNull I_M_HU_PI_Item luPIItem;

	@NonNull HUPIItemProductId tuPackingInstructionId;
}

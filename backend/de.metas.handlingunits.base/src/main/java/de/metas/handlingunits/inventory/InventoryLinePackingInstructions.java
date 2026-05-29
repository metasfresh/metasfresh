package de.metas.handlingunits.inventory;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class InventoryLinePackingInstructions
{
	public static final InventoryLinePackingInstructions VHU = builder().tuPIItemProductId(HUPIItemProductId.VIRTUAL_HU).build();

	@NonNull HUPIItemProductId tuPIItemProductId;
	@Nullable HuPackingInstructionsId luPIId;
	
	public boolean isVHU() { return tuPIItemProductId.isVirtualHU() && luPIId == null;}
}

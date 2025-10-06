package de.metas.inventory.mobileui.job.qrcode;

import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

@Value
@Builder
public class ResolveHURequest
{
	@NonNull ScannedCode scannedCode;

	//
	// Context:
	@NonNull InventoryJob job;
	@Nullable InventoryLineId lineId;
	@NonNull LocatorId locatorId;
}

package de.metas.inventory.mobileui.rest_api.json;

import de.metas.inventory.InventoryLineId;
import de.metas.scannable_code.ScannedCode;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonScannedCodeResolveRequest
{
	@NonNull ScannedCode scannedCode;

	//
	// Context:
	@NonNull WFProcessId wfProcessId;
	@Nullable InventoryLineId lineId;
}

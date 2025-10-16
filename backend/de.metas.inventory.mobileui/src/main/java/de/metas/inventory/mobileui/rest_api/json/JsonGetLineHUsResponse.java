package de.metas.inventory.mobileui.rest_api.json;

import de.metas.inventory.InventoryLineId;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonGetLineHUsResponse
{
	@NonNull WFProcessId wfProcessId;
	@NonNull InventoryLineId lineId;
	@NonNull List<JsonInventoryLineHU> lineHUs;
}

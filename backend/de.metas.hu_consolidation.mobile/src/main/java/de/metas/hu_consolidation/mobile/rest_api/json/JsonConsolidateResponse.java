package de.metas.hu_consolidation.mobile.rest_api.json;

import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonConsolidateResponse
{
	@NonNull JsonWFProcess wfProcess;
	@NonNull JsonHUConsolidationJobPickingSlotContent pickingSlotContent;
}

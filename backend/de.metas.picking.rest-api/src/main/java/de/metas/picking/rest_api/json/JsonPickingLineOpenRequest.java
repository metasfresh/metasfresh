package de.metas.picking.rest_api.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Jacksonized
public class JsonPickingLineOpenRequest
{
	@NonNull WFProcessId wfProcessId;
	@NonNull PickingJobLineId pickingLineId;
}

package de.metas.picking.rest_api.json;

import de.metas.handlingunits.picking.job.model.PickingJobLineId;
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
public class JsonGetNextEligibleLineRequest
{
	@NonNull WFProcessId wfProcessId;
	@NonNull ScannedCode huScannedCode;
	@Nullable PickingJobLineId excludeLineId;
}

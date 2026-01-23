package de.metas.distribution.mobileui.rest_api.json;

import de.metas.distribution.mobileui.job.model.DistributionJobLineId;
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
public class JsonGetNextEligiblePickFromLineRequest
{
	@NonNull WFProcessId wfProcessId;
	@NonNull ScannedCode huQRCode;
	@Nullable ScannedCode productScannedCode;
	@Nullable DistributionJobLineId lineId;
}

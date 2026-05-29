package de.metas.distribution.mobileui.rest_api.json;

import de.metas.distribution.mobileui.job.model.DistributionJobLineId;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonGetNextEligiblePickFromLineResponse
{
	@Nullable DistributionJobLineId lineId;
}

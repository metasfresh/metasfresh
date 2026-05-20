package de.metas.workflow.rest_api.controller.v2.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = JsonTrolleyPendingWorkContribution.JsonTrolleyPendingWorkContributionBuilder.class)
public class JsonTrolleyPendingWorkContribution
{
	@NonNull String applicationId;
	int openJobsCount;
}

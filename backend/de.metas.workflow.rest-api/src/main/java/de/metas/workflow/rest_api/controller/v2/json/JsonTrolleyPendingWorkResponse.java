package de.metas.workflow.rest_api.controller.v2.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonDeserialize(builder = JsonTrolleyPendingWorkResponse.JsonTrolleyPendingWorkResponseBuilder.class)
public class JsonTrolleyPendingWorkResponse
{
	@NonNull @Singular ImmutableList<JsonTrolleyPendingWorkContribution> contributions;
	int totalOpenJobs;

	public static JsonTrolleyPendingWorkResponse aggregate(@NonNull final List<JsonTrolleyPendingWorkContribution> items)
	{
		final int total = items.stream().mapToInt(JsonTrolleyPendingWorkContribution::getOpenJobsCount).sum();
		return JsonTrolleyPendingWorkResponse.builder()
				.contributions(ImmutableList.copyOf(items))
				.totalOpenJobs(total)
				.build();
	}
}

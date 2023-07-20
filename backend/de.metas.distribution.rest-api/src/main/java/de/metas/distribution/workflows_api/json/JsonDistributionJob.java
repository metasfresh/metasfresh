package de.metas.distribution.workflows_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.workflows_api.DistributionJob;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonDistributionJob
{
	@NonNull List<JsonDistributionJobLine> lines;

	public static JsonDistributionJob of(
			@NonNull final DistributionJob job,
			@NonNull final JsonOpts jsonOpts)
	{
		return builder()
				.lines(job.getLines()
						.stream()
						.map(line -> JsonDistributionJobLine.of(line, jsonOpts))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}

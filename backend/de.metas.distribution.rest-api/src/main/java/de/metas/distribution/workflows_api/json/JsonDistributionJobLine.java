package de.metas.distribution.workflows_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.workflows_api.DistributionJobLine;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonDistributionJobLine
{
	@NonNull String caption;
	@NonNull List<JsonDistributionJobStep> steps;

	public static JsonDistributionJobLine of(
			@NonNull final DistributionJobLine line,
			@NonNull final JsonOpts jsonOpts)
	{
		final String adLanguage = jsonOpts.getAdLanguage();

		return builder()
				.caption(line.getProduct().getCaption().translate(adLanguage))
				.steps(line.getSteps()
						.stream()
						.map(step -> JsonDistributionJobStep.of(step, line, jsonOpts))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}
}

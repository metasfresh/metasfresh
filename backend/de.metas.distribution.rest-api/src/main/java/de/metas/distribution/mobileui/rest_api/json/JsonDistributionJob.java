package de.metas.distribution.mobileui.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.mobileui.job.model.DistributionJob;
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
	boolean requireScanningProductCode;
	boolean completeJobAutomatically;
	@NonNull JsonRejectReasonsList qtyRejectedReasons;

	public static JsonDistributionJob.JsonDistributionJobBuilder builderFrom(
			@NonNull final DistributionJob job,
			@NonNull final JsonOpts jsonOpts)
	{
		return builder()
				.lines(job.getLines()
						.stream()
						.map(line -> JsonDistributionJobLine.of(line, job, jsonOpts))
						.collect(ImmutableList.toImmutableList()));
	}
}

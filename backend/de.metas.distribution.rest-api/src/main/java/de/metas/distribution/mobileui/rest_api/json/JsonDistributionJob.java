package de.metas.distribution.mobileui.rest_api.json;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonDistributionJob
{
	@Nullable String pickingInstruction;
	@NonNull List<JsonDistributionJobLine> lines;
	boolean requireScanningProductCode;
	boolean completeJobAutomatically;
	boolean navigateToJobsListAfterPickFromComplete;
	@NonNull JsonRejectReasonsList qtyRejectedReasons;

	public static JsonDistributionJob.JsonDistributionJobBuilder builderFrom(
			@NonNull final DistributionJob job,
			@NonNull final JsonOpts jsonOpts)
	{
		return builder()
				.pickingInstruction(job.getPickingInstruction().translate(jsonOpts.getAdLanguage()))
				.lines(job.getLines()
						.stream()
						.map(line -> JsonDistributionJobLine.of(line, job, jsonOpts))
						.collect(ImmutableList.toImmutableList()));
	}
}

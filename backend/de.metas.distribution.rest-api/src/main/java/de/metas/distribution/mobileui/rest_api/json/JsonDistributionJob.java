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
	boolean canSwitchPickFromLocator;
	@NonNull JsonRejectReasonsList qtyRejectedReasons;

	/**
	 * What is still to be moved, as "&lt;qty&gt; &lt;uom&gt; &lt;product&gt;" per line, or {@code null} when every
	 * line's planned quantity was moved. Non-null is what tells the mobile UI that Complete will be refused and that
	 * the give-up-the-remainder action applies — and it spells out exactly which quantity would be abandoned.
	 */
	@Nullable String qtyOutstanding;

	public static JsonDistributionJob.JsonDistributionJobBuilder builderFrom(
			@NonNull final DistributionJob job,
			@NonNull final JsonOpts jsonOpts)
	{
		return builder()
				.pickingInstruction(job.getPickingInstruction().translate(jsonOpts.getAdLanguage()))
				.canSwitchPickFromLocator(job.canSwitchPickFromLocator())
				.qtyOutstanding(job.hasQtyOutstanding() ? job.getQtyOutstandingDescription().translate(jsonOpts.getAdLanguage()) : null)
				.lines(job.getLines()
						.stream()
						.map(line -> JsonDistributionJobLine.of(line, job, jsonOpts))
						.collect(ImmutableList.toImmutableList()));
	}
}

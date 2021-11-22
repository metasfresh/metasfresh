package de.metas.manufacturing.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.util.collections.CollectionUtils;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Objects;
import java.util.function.UnaryOperator;

@Value
public class RawMaterialsIssue
{
	@NonNull ImmutableList<RawMaterialsIssueLine> lines;

	@NonNull WFActivityStatus status; // computed

	@Builder(toBuilder = true)
	private RawMaterialsIssue(@NonNull final ImmutableList<RawMaterialsIssueLine> lines)
	{
		this.lines = lines;

		this.status = WFActivityStatus.computeStatusFromLines(this.lines, RawMaterialsIssueLine::getStatus);
	}

	public boolean containsRawMaterialsIssueStep(final PPOrderIssueScheduleId issueScheduleId)
	{
		return lines.stream().anyMatch(line -> line.containsRawMaterialsIssueStep(issueScheduleId));
	}

	public RawMaterialsIssue withChangedRawMaterialsIssueStep(
			@NonNull final PPOrderIssueScheduleId issueScheduleId,
			@NonNull UnaryOperator<RawMaterialsIssueStep> mapper)
	{
		final ImmutableList<RawMaterialsIssueLine> linesNew = CollectionUtils.map(lines, line -> line.withChangedRawMaterialsIssueStep(issueScheduleId, mapper));
		return withLines(linesNew);
	}

	private RawMaterialsIssue withLines(final ImmutableList<RawMaterialsIssueLine> linesNew)
	{
		return !Objects.equals(this.lines, linesNew) ? toBuilder().lines(linesNew).build() : this;
	}
}

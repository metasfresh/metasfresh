package de.metas.manufacturing.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.util.function.UnaryOperator;

@Value
@Builder
public class RawMaterialsIssue
{
	@With
	@NonNull ImmutableList<RawMaterialsIssueLine> lines;

	public RawMaterialsIssue withChangedRawMaterialsIssueStep(
			@NonNull final PPOrderIssueScheduleId issueScheduleId,
			@NonNull UnaryOperator<RawMaterialsIssueStep> mapper)
	{
		final ImmutableList<RawMaterialsIssueLine> linesNew = CollectionUtils.map(lines, line -> line.withChangedRawMaterialsIssueStep(issueScheduleId, mapper));
		return withLines(linesNew);
	}
}

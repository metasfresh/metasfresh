package de.metas.handlingunits.pporder.api;

import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class IssueCandidateGeneratedBy
{
	@Nullable PickingCandidateId pickingCandidateId;
	@Nullable PPOrderIssueScheduleId issueScheduleId;

	public static IssueCandidateGeneratedBy ofPickingCandidateId(@NonNull final PickingCandidateId pickingCandidateId)
	{
		return new IssueCandidateGeneratedBy(pickingCandidateId);
	}

	public static IssueCandidateGeneratedBy ofIssueScheduleId(@NonNull final PPOrderIssueScheduleId issueScheduleId)
	{
		return new IssueCandidateGeneratedBy(issueScheduleId);
	}

	private IssueCandidateGeneratedBy(@NonNull final PickingCandidateId pickingCandidateId)
	{
		this.pickingCandidateId = pickingCandidateId;
		this.issueScheduleId = null;
	}

	private IssueCandidateGeneratedBy(@NonNull final PPOrderIssueScheduleId issueScheduleId)
	{
		this.pickingCandidateId = null;
		this.issueScheduleId = issueScheduleId;
	}
}

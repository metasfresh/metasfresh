package de.metas.workflow.execution.approval.strategy.type_handlers;

import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyLine;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyService;
import de.metas.workflow.execution.approval.strategy.UsersToApproveList;
import lombok.NonNull;

public class ProjectManagerHandler implements DocApprovalStrategyTypeHandler
{
	@Override
	public UsersToApproveList getUsersToApprove(
			@NonNull final DocApprovalStrategyService.GetUsersToApproveRequest request,
			@NonNull final DocApprovalStrategyLine strategyLine_NOTUSED)
	{
		return UsersToApproveList.ofNullable(request.getProjectManagerId());
	}
}

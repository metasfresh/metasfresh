package de.metas.workflow.execution.approval.strategy.type_handlers;

import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyLine;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyService;
import de.metas.workflow.execution.approval.strategy.UsersToApproveList;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import lombok.NonNull;

public class JobHandler implements DocApprovalStrategyTypeHandler
{
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);

	@Override
	public UsersToApproveList getUsersToApprove(
			@NonNull final DocApprovalStrategyService.GetUsersToApproveRequest request_NOTUSED,
			@NonNull final DocApprovalStrategyLine strategyLine)
	{
		return UsersToApproveList.ofCollection(userBL.getUserIdsByJobId(strategyLine.getJobIdNotNull()));
	}
}

package de.metas.workflow.execution.approval.strategy.type_handlers;

import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyLine;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyService;
import de.metas.workflow.execution.approval.strategy.UsersToApproveList;
import lombok.NonNull;

public interface DocApprovalStrategyTypeHandler
{
	UsersToApproveList getUsersToApprove(@NonNull DocApprovalStrategyService.GetUsersToApproveRequest request, final DocApprovalStrategyLine strategyLine);
}

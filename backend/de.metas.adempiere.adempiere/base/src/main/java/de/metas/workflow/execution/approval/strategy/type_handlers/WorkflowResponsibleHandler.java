package de.metas.workflow.execution.approval.strategy.type_handlers;

import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyLine;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyService;
import de.metas.workflow.execution.approval.strategy.UsersToApproveList;
import de.metas.security.IRoleDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.WFResponsible;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Set;

class WorkflowResponsibleHandler implements DocApprovalStrategyTypeHandler
{
	@NonNull private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

	@Override
	public UsersToApproveList getUsersToApprove(
			@NonNull final DocApprovalStrategyService.GetUsersToApproveRequest request,
			@NonNull final DocApprovalStrategyLine strategyLine_NOTUSED)
	{
		final UserId workflowInvokerId = request.getWorkflowInvokerId();
		final WFResponsible responsible = request.getWorkflowResponsible();
		if (responsible.isInvoker())
		{
			return UsersToApproveList.of(workflowInvokerId);
		}
		else if (responsible.isHuman())
		{
			return UsersToApproveList.of(responsible.getUserIdNotNull());
		}
		else if (responsible.isRole())
		{
			final @NonNull RoleId roleId = responsible.getRoleIdNotNull();
			final Set<UserId> userIdsOfRole = roleDAO.retrieveUserIdsForRoleId(roleId);
			if (userIdsOfRole.isEmpty())
			{
				throw new AdempiereException("No users found to approve for role `" + roleDAO.getRoleName(roleId) + "`");
			}
			if (userIdsOfRole.contains(workflowInvokerId))
			{
				return UsersToApproveList.of(workflowInvokerId);
			}
			else
			{
				// NOTE: atm we cannot forward to all of those users, nor we can have an "any of the following users list" rule,
				// so we just pick the first one
				final UserId firstUserIdOfRole = userIdsOfRole.iterator().next();
				return UsersToApproveList.of(firstUserIdOfRole);
			}
		}
		else if (responsible.isOrganization())
		{
			throw new AdempiereException("@NotSupported@ " + responsible);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ " + responsible);
		}
	}
}

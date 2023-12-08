package de.metas.workflow.execution.approval.strategy.impl;

import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.security.IUserRolePermissions;
import de.metas.security.RoleId;
import de.metas.security.permissions.DocumentApprovalConstraint;
import de.metas.user.UserId;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.execution.WorkflowExecutionSupportingServicesFacade;
import de.metas.workflow.execution.approval.strategy.WFApprovalStrategyHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StandardApprovalStrategyHandler implements WFApprovalStrategyHandler
{
	private static final Logger log = LogManager.getLogger(StandardApprovalStrategyHandler.class);
	@NonNull private final WorkflowExecutionSupportingServicesFacade services;

	@Override
	public Response approve(@NonNull WFApprovalStrategyHandler.Request request)
	{
		//
		// Find who shall approve the document
		final UserId workflowInvokerId = request.getWorkflowInvokerId();
		final WFResponsible responsible = request.getResponsible();
		final boolean autoApproval;
		final UserId approverId;
		if (responsible.isInvoker())
		{
			approverId = getApproverId(request).orElse(null);
			autoApproval = UserId.equals(workflowInvokerId, approverId);
		}
		else if (responsible.isHuman())
		{
			approverId = responsible.getUserId();
			autoApproval = UserId.equals(workflowInvokerId, approverId);
		}
		else if (responsible.isRole())
		{
			final @NonNull RoleId roleId = responsible.getRoleIdNotNull();
			final Set<UserId> allRoleUserIds = services.getUserIdsByRoleId(roleId);
			autoApproval = allRoleUserIds.contains(workflowInvokerId);
			if (autoApproval)
			{
				approverId = workflowInvokerId;
			}
			else
			{
				// NOTE: atm we cannot forward to all of those users, so we just pick the first one
				approverId = allRoleUserIds.stream().findFirst().orElse(null);
			}
		}
		else if (responsible.isOrganization())
		{
			throw new AdempiereException("Support not implemented for " + responsible);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ " + responsible);
		}

		//
		//
		if (autoApproval)
		{
			return Response.APPROVED;
		}
		else
		{
			if (approverId == null)
			{
				throw new AdempiereException("No user to approve found!"); // TODO: trl
			}

			return Response.forwardTo(approverId);
		}
	}

	private Optional<UserId> getApproverId(@NonNull final WFApprovalStrategyHandler.Request request)
	{
		// Nothing to approve
		if (request.getAmountToApprove() == null || request.getAmountToApprove().signum() == 0)
		{
			return Optional.of(request.getWorkflowInvokerId());
		}

		UserId currentUserId = request.getWorkflowInvokerId();
		final HashSet<UserId> alreadyCheckedUserIds = new HashSet<>();
		while (currentUserId != null)
		{
			if (!alreadyCheckedUserIds.add(currentUserId))
			{
				log.debug("Loop - {}", alreadyCheckedUserIds);
				return Optional.empty();
			}

			if (isUserAllowedToApproveDocument(currentUserId, request))
			{
				return Optional.of(currentUserId);
			}

			//
			// Gets user's supervisor
			currentUserId = services.getSupervisorId(currentUserId, request.getOrgId()).orElse(null);
		}

		log.debug("No user found");
		return Optional.empty();
	}

	private boolean isUserAllowedToApproveDocument(
			@NonNull final UserId userId,
			@NonNull final WFApprovalStrategyHandler.Request request)
	{
		for (final IUserRolePermissions role : services.getUserRolesPermissionsForUserWithOrgAccess(userId, request.getClientAndOrgId(), request.getEvaluationTimeAsLocalDate()))
		{
			if (isRoleAllowedToApproveDocument(role, userId, request))
			{
				return true;
			}
		}

		return false;
	}

	private boolean isRoleAllowedToApproveDocument(
			@NonNull final IUserRolePermissions role,
			@NonNull final UserId userId,
			@NonNull final WFApprovalStrategyHandler.Request request)
	{
		final DocumentApprovalConstraint docApprovalConstraints = role.getConstraint(DocumentApprovalConstraint.class).orElse(DocumentApprovalConstraint.DEFAULT);
		final boolean ownDocument = UserId.equals(request.getDocumentOwnerId(), userId);
		if (ownDocument && !docApprovalConstraints.canApproveOwnDoc())
		{
			return false;
		}

		final Money amountToApprove = request.getAmountToApprove();
		if (amountToApprove == null)
		{
			return true;
		}

		Money maxAllowedAmount = docApprovalConstraints.getAmtApproval(amountToApprove.getCurrencyId());
		if (maxAllowedAmount.signum() <= 0)
		{
			return false;
		}

		maxAllowedAmount = services.convertMoney(maxAllowedAmount, amountToApprove.getCurrencyId(), request.getEvaluationTimeAsLocalDate(), request.getClientAndOrgId());

		return amountToApprove.isLessThanOrEqualTo(maxAllowedAmount);
	}
}

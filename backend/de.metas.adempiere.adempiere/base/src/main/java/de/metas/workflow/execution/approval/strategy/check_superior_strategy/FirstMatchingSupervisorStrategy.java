package de.metas.workflow.execution.approval.strategy.check_superior_strategy;

import com.google.common.collect.ImmutableList;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.permissions.DocumentApprovalConstraint;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

class FirstMatchingSupervisorStrategy implements CheckSupervisorStrategy
{
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	@Override
	public List<UserId> getSupervisors(@NonNull final GetSupervisorsRequest request)
	{
		final UserId supervisorId = getFirstMatchingSupervisorId(request).orElse(null);
		return supervisorId != null ? ImmutableList.of(supervisorId) : ImmutableList.of();
	}

	private Optional<UserId> getFirstMatchingSupervisorId(@NonNull final GetSupervisorsRequest request)
	{
		// If there is no amount to approve, don't search for a supervisor
		if (request.getAmountToApprove() == null || request.getAmountToApprove().signum() == 0)
		{
			return Optional.empty();
		}

		// User is allowed to approve the document, no need to search for supervisors
		if (isUserAllowedToApproveDocument(request.getUserId(), request))
		{
			return Optional.empty();
		}

		//
		// Search for a supervisor which can approve the document
		final OrgId orgId = request.getClientAndOrgId().getOrgId();
		UserId supervisorId = userBL.getSupervisorId(request.getUserId(), orgId).orElse(null);
		final HashSet<UserId> seenSupervisorIds = new HashSet<>();
		while (supervisorId != null)
		{
			if (!seenSupervisorIds.add(supervisorId))
			{
				throw new AdempiereException("Cycle detected in supervisors hierarchy: " + seenSupervisorIds);
			}

			if (isUserAllowedToApproveDocument(supervisorId, request))
			{
				return Optional.of(supervisorId);
			}

			//
			// Gets user's supervisor
			supervisorId = userBL.getSupervisorId(supervisorId, orgId).orElse(null);
		}

		throw new AdempiereException("No supervisor that can approve the document found");
	}

	private boolean isUserAllowedToApproveDocument(
			@NonNull final UserId userId,
			@NonNull final GetSupervisorsRequest context)
	{
		final boolean isUserTheDocumentOwner = UserId.equals(context.getDocumentOwnerId(), userId);

		for (final IUserRolePermissions role : getRolesOfUser(userId, context))
		{
			if (isRoleAllowedToApproveDocument(role, isUserTheDocumentOwner, context))
			{
				return true;
			}
		}

		return false;
	}

	private List<IUserRolePermissions> getRolesOfUser(final @NotNull UserId userId, final @NotNull GetSupervisorsRequest context)
	{
		return userRolePermissionsDAO.retrieveUserRolesPermissionsForUserWithOrgAccess(
				context.getClientAndOrgId().getClientId(),
				context.getClientAndOrgId().getOrgId(),
				userId,
				context.getEvaluationDate());
	}

	private boolean isRoleAllowedToApproveDocument(
			@NonNull final IUserRolePermissions role,
			final boolean isUserTheDocumentOwner,
			@NonNull final GetSupervisorsRequest context)
	{
		if (isUserTheDocumentOwner && !isAllowedToApproveOwnDocuments(role))
		{
			return false;
		}

		final Money amountToApprove = context.getAmountToApprove();
		if (amountToApprove == null)
		{
			return true;
		}

		final Money maxAmountAllowedToApprove = getMaxAmountAllowedToApprove(amountToApprove.getCurrencyId(), role, context);
		if (maxAmountAllowedToApprove.signum() <= 0)
		{
			return false;
		}

		return amountToApprove.isLessThanOrEqualTo(maxAmountAllowedToApprove);
	}

	private boolean isAllowedToApproveOwnDocuments(@NonNull final IUserRolePermissions role)
	{
		return getDocumentApprovalConstraint(role).canApproveOwnDoc();
	}

	private Money getMaxAmountAllowedToApprove(
			@NonNull final CurrencyId currencyId,
			@NonNull final IUserRolePermissions role,
			@NonNull final GetSupervisorsRequest context)
	{
		final Money maxAmountAllowedToApprove = getDocumentApprovalConstraint(role).getAmtApproval(currencyId);
		return currencyBL.convert(maxAmountAllowedToApprove, currencyId, context.getEvaluationDate(), context.getClientAndOrgId());
	}

	@NotNull
	private static DocumentApprovalConstraint getDocumentApprovalConstraint(final @NotNull IUserRolePermissions role)
	{
		return role.getConstraint(DocumentApprovalConstraint.class).orElse(DocumentApprovalConstraint.DEFAULT);
	}
}

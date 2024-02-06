package de.metas.workflow.execution.approval.strategy.check_superior_strategy;

import com.google.common.collect.ImmutableList;
import de.metas.currency.ICurrencyBL;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.permissions.DocumentApprovalConstraint;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import de.metas.workflow.execution.approval.strategy.UsersToApproveList;
import lombok.NonNull;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

class AllMatchingSupervisorsStrategy implements CheckSupervisorStrategy
{
	@NonNull private static final Logger logger = LogManager.getLogger(AllMatchingSupervisorsStrategy.class);
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	@Override
	public List<UserId> getSupervisors(@NonNull final GetSupervisorsRequest request)
	{
		final OrgId orgId = request.getClientAndOrgId().getOrgId();

		final UsersToApproveList userIdsToApprove = UsersToApproveList.empty();

		if (!isRoleApprovalRequired(request, request.getUserId()))
		{
			return ImmutableList.of();
		}
		
		UserId supervisorId = userBL.getSupervisorId(request.getUserId(), orgId).orElse(null);
		final HashSet<UserId> seenSupervisorIds = new HashSet<>();
		while (supervisorId != null)
		{
			if (!seenSupervisorIds.add(supervisorId))
			{
				logger.warn("Cycle detected in supervisors hierarchy: {}", seenSupervisorIds);
				break;
			}

			userIdsToApprove.add(supervisorId);

			if (isRoleApprovalRequired(request, supervisorId))
			{
				supervisorId = userBL.getSupervisorId(supervisorId, orgId).orElse(null);
			}
		}

		return userIdsToApprove.toList();
	}

	private boolean isRoleApprovalRequired(@NonNull final GetSupervisorsRequest request, @NonNull final UserId userId)
	{
		final boolean ownDocument = UserId.equals(request.getDocumentOwnerId(), userId);
		if (ownDocument)
		{
			return false;
		}

		final ClientAndOrgId clientAndOrgId = request.getClientAndOrgId();
		final LocalDate evalDate = request.getEvaluationDate();
		final List<IUserRolePermissions> roles = userRolePermissionsDAO.retrieveUserRolesPermissionsForUserWithOrgAccess(
				clientAndOrgId.getClientId(),
				clientAndOrgId.getOrgId(),
				userId,
				evalDate);
		if (roles.isEmpty())
		{
			return false;
		}

		final Money amountToApprove = request.getAmountToApprove();
		if (amountToApprove == null)
		{
			return true;
		}

		for (final IUserRolePermissions role : roles)
		{
			final Money thresholdAmount = getRoleThresholdAmount(role, amountToApprove.getCurrencyId(), evalDate, clientAndOrgId);
			if (amountToApprove.isGreaterThanOrEqualTo(thresholdAmount))
			{
				return true;
			}
		}

		return false;
	}

	private Money getRoleThresholdAmount(
			@NonNull final IUserRolePermissions role,
			@NonNull final CurrencyId currencyId,
			@NonNull final LocalDate conversionDate,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final DocumentApprovalConstraint docApprovalConstraints = role.getConstraint(DocumentApprovalConstraint.class)
				.orElse(DocumentApprovalConstraint.DEFAULT);

		Money thresholdAmount = docApprovalConstraints.getAmtApproval(currencyId);
		return currencyBL.convert(thresholdAmount, currencyId, conversionDate, clientAndOrgId);
	}

}

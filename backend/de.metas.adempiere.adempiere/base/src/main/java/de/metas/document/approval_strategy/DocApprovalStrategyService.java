package de.metas.document.approval_strategy;

import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.ICurrencyBL;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.permissions.DocumentApprovalConstraint;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocApprovalStrategyService
{
	@NonNull private static final Logger logger = LogManager.getLogger(DocApprovalStrategyService.class);
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);

	@NonNull private final DocApprovalStrategyRepository repository;

	@Value
	@Builder
	public static class GetUsersToApproveRequest
	{
		@NonNull DocApprovalStrategyId docApprovalStrategyId;
		@Nullable Money amountToApprove;
		@NonNull LocalDate evaluationDate;
		@NonNull ClientAndOrgId clientAndOrgId;
		@NonNull UserId documentOwnerId;
		@Nullable UserId requestorId;
		@Nullable UserId projectManagerId;
	}

	public List<UserId> getUsersToApprove(final @NotNull GetUsersToApproveRequest request)
	{
		final DocApprovalStrategy strategy = repository.getById(request.getDocApprovalStrategyId());
		final UsersToApproveList usersToApproveList = UsersToApproveList.empty();
		for (final DocApprovalStrategyLine strategyLine : strategy.getLinesInOrder())
		{
			usersToApproveList.addAll(getUsersToApprove(request, strategyLine));
		}

		return usersToApproveList.toList();
	}

	private UsersToApproveList getUsersToApprove(@NonNull final GetUsersToApproveRequest request, final DocApprovalStrategyLine strategyLine)
	{
		if (!isMatching(request, strategyLine))
		{
			return UsersToApproveList.empty();
		}

		return switch (strategyLine.getType())
		{
			case RequestorSupervisorsHierarchy -> getUsersToApprove_RequestorSupervisorsHierarchy(request);
			case ProjectManager -> UsersToApproveList.ofNullable(request.getProjectManagerId());
			case Job -> UsersToApproveList.ofCollection(userBL.getUserIdsByJobId(strategyLine.getJobIdNotNull()));
		};
	}

	private boolean isMatching(@NonNull final GetUsersToApproveRequest request, final DocApprovalStrategyLine strategyLine)
	{
		if (strategyLine.getIsProjectManagerSet().isPresent())
		{
			final boolean isProjectedManagerExpectedToBeSet = strategyLine.getIsProjectManagerSet().isTrue();
			final boolean isProjectManagerSet = request.getProjectManagerId() != null;
			if (isProjectManagerSet != isProjectedManagerExpectedToBeSet)
			{
				return false;
			}
		}

		final Money minimumAmountThatRequiresApproval = strategyLine.getMinimumAmountThatRequiresApproval();
		if (minimumAmountThatRequiresApproval != null && minimumAmountThatRequiresApproval.signum() > 0)
		{
			final Money amountToApprove = request.getAmountToApprove();
			if (amountToApprove == null)
			{
				return false;
			}

			final Money amountToApproveConv = convertMoney(amountToApprove, minimumAmountThatRequiresApproval.getCurrencyId(), request.getEvaluationDate(), request.getClientAndOrgId());
			//noinspection RedundantIfStatement
			if (amountToApproveConv.isLessThan(minimumAmountThatRequiresApproval))
			{
				return false;
			}
		}

		return true;
	}

	private Money convertMoney(
			@NonNull final Money amount,
			@NonNull final CurrencyId toCurrencyId,
			@NonNull final LocalDate conversionDate,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		if (CurrencyId.equals(amount.getCurrencyId(), toCurrencyId))
		{
			return amount;
		}

		final CurrencyConversionContext conversionCtx = currencyBL.createCurrencyConversionContext(
				LocalDateAndOrgId.ofLocalDate(conversionDate, clientAndOrgId.getOrgId()),
				(CurrencyConversionTypeId)null,
				clientAndOrgId.getClientId());

		final CurrencyConversionResult conversionResult = currencyBL.convert(
				conversionCtx,
				amount.toBigDecimal(),
				amount.getCurrencyId(),
				toCurrencyId);

		return Money.of(conversionResult.getAmount(), toCurrencyId);
	}

	private UsersToApproveList getUsersToApprove_RequestorSupervisorsHierarchy(@NonNull final GetUsersToApproveRequest request)
	{
		final UserId requestorId = request.getRequestorId();
		if (requestorId == null)
		{
			return UsersToApproveList.empty();
		}

		final UsersToApproveList userIdsToApprove = UsersToApproveList.empty();
		userIdsToApprove.add(requestorId);

		final OrgId orgId = request.getClientAndOrgId().getOrgId();
		UserId supervisorId = userBL.getSupervisorId(requestorId, orgId).orElse(null);
		final HashSet<UserId> seenSupervisorIds = new HashSet<>();
		while (supervisorId != null)
		{
			if (!seenSupervisorIds.add(supervisorId))
			{
				logger.warn("Cycle detected in supervisors hierarchy: {}", seenSupervisorIds);
				break;
			}

			if (isRoleApprovalRequired(request, supervisorId))
			{
				userIdsToApprove.add(supervisorId);
			}

			supervisorId = userBL.getSupervisorId(supervisorId, orgId).orElse(null);
		}

		return userIdsToApprove;
	}

	private boolean isRoleApprovalRequired(@NonNull final GetUsersToApproveRequest request, @NonNull final UserId userId)
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
		return convertMoney(thresholdAmount, currencyId, conversionDate, clientAndOrgId);
	}
}

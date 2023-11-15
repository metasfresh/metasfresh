package de.metas.workflow.execution.approval.strategy.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.ICurrencyBL;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.permissions.DocumentApprovalConstraint;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.execution.approval.strategy.WFApprovalStrategy;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class StandardApprovalStrategy implements WFApprovalStrategy
{
	private static final Logger log = LogManager.getLogger(StandardApprovalStrategy.class);
	private final IUserBL userBL = Services.get(IUserBL.class);
	private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	@Override
	public Response approve(@NonNull WFApprovalStrategy.Request request)
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
			final Set<UserId> allRoleUserIds = getUserIdsByRoleId(responsible.getRoleIdNotNull());
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

	private Optional<UserId> getApproverId(@NonNull final WFApprovalStrategy.Request document)
	{
		// Nothing to approve
		if (document.getAmountToApprove() == null || document.getAmountToApprove().signum() == 0)
		{
			return Optional.of(document.getWorkflowInvokerId());
		}

		UserId currentUserId = document.getWorkflowInvokerId();
		final HashSet<UserId> alreadyCheckedUserIds = new HashSet<>();
		while (currentUserId != null)
		{
			if (!alreadyCheckedUserIds.add(currentUserId))
			{
				log.debug("Loop - {}", alreadyCheckedUserIds);
				return Optional.empty();
			}

			if (isUserAllowedToApproveDocument(currentUserId, document))
			{
				return Optional.of(currentUserId);
			}

			//
			// Gets user's supervisor
			currentUserId = userBL.getSupervisorId(currentUserId, document.getClientAndOrgId().getOrgId()).orElse(null);
		}

		log.debug("No user found");
		return Optional.empty();
	}

	private boolean isUserAllowedToApproveDocument(
			@NonNull final UserId userId,
			@NonNull final WFApprovalStrategy.Request request)
	{

		final @NonNull ClientAndOrgId clientAndOrgId = request.getClientAndOrgId();
		final List<IUserRolePermissions> roles = userRolePermissionsDAO.retrieveUserRolesPermissionsForUserWithOrgAccess(
				clientAndOrgId.getClientId(),
				clientAndOrgId.getOrgId(),
				userId,
				Env.getLocalDate());
		for (final IUserRolePermissions role : roles)
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
			@NonNull final WFApprovalStrategy.Request request)
	{
		final DocumentApprovalConstraint docApprovalConstraints = role.getConstraint(DocumentApprovalConstraint.class)
				.orElse(DocumentApprovalConstraint.DEFAULT);

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

		maxAllowedAmount = convertMoney(maxAllowedAmount, amountToApprove.getCurrencyId(), request.getClientAndOrgId());

		return amountToApprove.isLessThanOrEqualTo(maxAllowedAmount);
	}

	private Set<UserId> getUserIdsByRoleId(@NonNull final RoleId roleId)
	{
		return roleDAO.retrieveUserIdsForRoleId(roleId);
	}

	private Money convertMoney(
			@NonNull final Money amount,
			@NonNull final CurrencyId toCurrencyId,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		if (CurrencyId.equals(amount.getCurrencyId(), toCurrencyId))
		{
			return amount;
		}

		final CurrencyConversionContext conversionCtx = currencyBL.createCurrencyConversionContext(
				LocalDateAndOrgId.ofLocalDate(SystemTime.asLocalDate(), clientAndOrgId.getOrgId()),
				(CurrencyConversionTypeId)null,
				clientAndOrgId.getClientId());

		final CurrencyConversionResult conversionResult = currencyBL.convert(
				conversionCtx,
				amount.toBigDecimal(),
				amount.getCurrencyId(),
				toCurrencyId);

		return Money.of(conversionResult.getAmount(), toCurrencyId);
	}
}

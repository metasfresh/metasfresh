package de.metas.workflow.execution.approval.strategy.impl;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_Order;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.ICurrencyBL;
import de.metas.document.engine.IDocument;
import de.metas.job.Job;
import de.metas.job.JobService;
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
import de.metas.util.lang.RepoIdAwares;
import de.metas.util.lang.SeqNoProvider;
import de.metas.workflow.execution.approval.WFApprovalRequest;
import de.metas.workflow.execution.approval.WFApprovalRequestRepository;
import de.metas.workflow.execution.approval.WFApprovalRequestStatus;
import de.metas.workflow.execution.approval.strategy.WFApprovalStrategy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestorHierarcyProjectManagerPlusCTO_ApprovalStrategy implements WFApprovalStrategy
{
	private static final Logger logger = LogManager.getLogger(RequestorHierarcyProjectManagerPlusCTO_ApprovalStrategy.class);
	@NonNull private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final JobService jobService;
	@NonNull private final WFApprovalRequestRepository wfApprovalRequestRepository;

	@Override
	public Response approve(@NonNull final WFApprovalStrategy.Request request)
	{
		final List<WFApprovalRequest> approvalRequests = getOrCreateApprovalRequestsOrderedBySeqNo(request);
		if (approvalRequests.isEmpty())
		{
			return Response.APPROVED;
		}

		int countApproved = 0;
		for (WFApprovalRequest approvalRequest : approvalRequests)
		{
			switch (approvalRequest.getStatus())
			{
				case Approved -> countApproved++;
				case Rejected ->
				{
					return Response.REJECTED;
				}
				case Pending ->
				{
					return Response.forwardTo(approvalRequest.getUserId(), approvalRequest.toTableRecordReference());
				}
			}
		}

		return approvalRequests.size() == countApproved ? Response.APPROVED : Response.PENDING;
	}

	private List<WFApprovalRequest> getOrCreateApprovalRequestsOrderedBySeqNo(@NonNull final WFApprovalStrategy.Request request)
	{
		List<WFApprovalRequest> approvalRequests = wfApprovalRequestRepository.getByDocumentRef(request.getDocumentRef());
		if (approvalRequests.isEmpty())
		{
			approvalRequests = createApprovalRequests(request);
		}
		if (approvalRequests.isEmpty())
		{
			return ImmutableList.of();
		}

		return approvalRequests.stream()
				.sorted(Comparator.comparing(WFApprovalRequest::getSeqNo))
				.collect(ImmutableList.toImmutableList());
	}

	private List<WFApprovalRequest> createApprovalRequests(@NonNull final WFApprovalStrategy.Request request)
	{
		final ArrayList<WFApprovalRequest> approvalRequests = new ArrayList<>();
		final SeqNoProvider seqNoProvider = SeqNoProvider.ofInt(10);
		for (final UserId approverId : findUsersToApprove(request))
		{
			approvalRequests.add(
					WFApprovalRequest.builder()
							.documentRef(request.getDocumentRef())
							.documentNo(request.getDocumentNo())
							.docBaseType(request.getDocBaseType())
							//
							.seqNo(seqNoProvider.getAndIncrement())
							.userId(approverId)
							.status(WFApprovalRequestStatus.Pending)
							//
							.requestDate(SystemTime.asInstant())
							.responseDate(null)
							//
							.wfProcessId(request.getWfProcessId())
							.wfActivityId(request.getWfActivityId())
							//
							.build()
			);
		}

		wfApprovalRequestRepository.saveForDocument(request.getDocumentRef(), approvalRequests);

		return approvalRequests;
	}

	private List<UserId> findUsersToApprove(@NonNull final WFApprovalStrategy.Request request)
	{
		final ArrayList<UserId> userIdsToApprove = new ArrayList<>();

		final IDocument document = request.getDocument();
		final ClientId clientId = ClientId.ofRepoId(document.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoId(document.getAD_Org_ID());
		final UserId requestorId = document.getValue(I_C_Order.COLUMNNAME_Requestor_ID)
				.map(valueObj -> RepoIdAwares.ofObject(valueObj, UserId.class))
				.orElse(null);
		final UserId projectManagerId = document.getValue(I_C_Order.COLUMNNAME_ProjectManager_ID)
				.map(valueObj -> RepoIdAwares.ofObject(valueObj, UserId.class))
				.orElse(null);

		addUserToApprove(userIdsToApprove, requestorId);

		if (projectManagerId != null)
		{
			addUserToApprove(userIdsToApprove, projectManagerId);
		}
		else if (requestorId != null)
		{
			UserId supervisorId = userBL.getSupervisorId(requestorId, orgId).orElse(null);
			final HashSet<UserId> seenSupervisorIds = new HashSet<>();
			while (supervisorId != null)
			{
				if(!seenSupervisorIds.add(supervisorId))
				{
					logger.warn("Cycle detected in supervisors hierarchy: {}", seenSupervisorIds);
					break;
				}
				if (isApprovalRequired(request, supervisorId))
				{
					addUserToApprove(userIdsToApprove, supervisorId);
				}

				supervisorId = userBL.getSupervisorId(supervisorId, orgId).orElse(null);
			}

		}

		final Job ctoJob = jobService.getCTO(clientId).orElse(null);
		if (ctoJob != null)
		{
			final Set<UserId> ctoUserIds = userBL.getUserIdsByJobId(ctoJob.getId());
			ctoUserIds.forEach(ctoId -> addUserToApprove(userIdsToApprove, ctoId));
		}

		return userIdsToApprove;
	}

	private static void addUserToApprove(@NonNull final ArrayList<UserId> list, @Nullable final UserId userId)
	{
		if (userId == null)
		{
			return;
		}

		list.remove(userId);
		list.add(userId);
	}

	private boolean isApprovalRequired(@NonNull final WFApprovalStrategy.Request request, @NonNull final UserId userId)
	{
		final boolean ownDocument = UserId.equals(request.getDocumentOwnerId(), userId);
		if (ownDocument)
		{
			return false;
		}

		final @NonNull ClientAndOrgId clientAndOrgId = request.getClientAndOrgId();
		final List<IUserRolePermissions> roles = userRolePermissionsDAO.retrieveUserRolesPermissionsForUserWithOrgAccess(
				clientAndOrgId.getClientId(),
				clientAndOrgId.getOrgId(),
				userId,
				Env.getLocalDate());

		for (final IUserRolePermissions role : roles)
		{
			if (isApprovalRequired(request, role))
			{
				return true;
			}
		}

		return false;
	}

	private boolean isApprovalRequired(@NonNull final Request request, @NonNull final IUserRolePermissions role)
	{
		final Money amountToApprove = request.getAmountToApprove();
		if (amountToApprove == null)
		{
			return true;
		}

		final Money thresholdAmount = getThresoldAmount(role, amountToApprove.getCurrencyId(), request.getClientAndOrgId());

		return amountToApprove.isGreaterThanOrEqualTo(thresholdAmount);
	}

	private Money getThresoldAmount(
			@NonNull final IUserRolePermissions role,
			@NonNull final CurrencyId currencyId,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final DocumentApprovalConstraint docApprovalConstraints = role.getConstraint(DocumentApprovalConstraint.class)
				.orElse(DocumentApprovalConstraint.DEFAULT);

		Money thresholdAmount = docApprovalConstraints.getAmtApproval(currencyId);
		return convertMoney(thresholdAmount, currencyId, clientAndOrgId);
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

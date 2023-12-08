package de.metas.workflow.execution.approval.strategy.impl;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_Order;
import de.metas.common.util.time.SystemTime;
import de.metas.document.approval_strategy.DocApprovalStrategyId;
import de.metas.document.approval_strategy.DocApprovalStrategyService;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.lang.RepoIdAwares;
import de.metas.util.lang.SeqNoProvider;
import de.metas.workflow.execution.approval.WFApprovalRequest;
import de.metas.workflow.execution.approval.WFApprovalRequestRepository;
import de.metas.workflow.execution.approval.WFApprovalRequestStatus;
import de.metas.workflow.execution.approval.strategy.WFApprovalStrategyHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DocApprovalStrategy_WFApprovalStrategyHandler implements WFApprovalStrategyHandler
{
	@NonNull private final WFApprovalRequestRepository wfApprovalRequestRepository;
	@NonNull private final DocApprovalStrategyService docApprovalStrategyService;

	private static final AdWindowId WINDOW_ID_MyApprovals = AdWindowId.ofRepoId(541736);

	@Override
	public Response approve(@NonNull final Request request)
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
					return Response.forwardTo(approvalRequest.getUserId(), UserNotificationRequest.TargetViewAction.openNewView(WINDOW_ID_MyApprovals));
				}
			}
		}

		return approvalRequests.size() == countApproved ? Response.APPROVED : Response.PENDING;
	}

	private List<WFApprovalRequest> getOrCreateApprovalRequestsOrderedBySeqNo(@NonNull final WFApprovalStrategyHandler.Request request)
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

	private List<WFApprovalRequest> createApprovalRequests(@NonNull final WFApprovalStrategyHandler.Request request)
	{
		final DocApprovalStrategyId docApprovalStrategyId = request.getDocApprovalStrategyId();
		if (docApprovalStrategyId == null)
		{
			return ImmutableList.of();
		}

		final List<UserId> usersToApproveList = docApprovalStrategyService.getUsersToApprove(DocApprovalStrategyService.GetUsersToApproveRequest.builder()
				.docApprovalStrategyId(docApprovalStrategyId)
				.amountToApprove(request.getAmountToApprove())
				.evaluationDate(request.getEvaluationTimeAsLocalDate())
				.clientAndOrgId(request.getClientAndOrgId())
				.documentOwnerId(request.getDocumentOwnerId())
				.requestorId(getRequestorId(request).orElse(null))
				.projectManagerId(getProjectManagerId(request).orElse(null))
				.build());
		if (usersToApproveList.isEmpty())
		{
			return ImmutableList.of();
		}

		final WFApprovalRequest.WFApprovalRequestBuilder requestTemplate = WFApprovalRequest.builder()
				.documentRef(request.getDocumentRef())
				.documentInfo(request.getDocumentInfo())
				//
				//.seqNo(...)
				//.userId(...)
				.status(WFApprovalRequestStatus.Pending)
				//
				.requestDate(SystemTime.asInstant())
				.responseDate(null)
				//
				.wfProcessId(request.getWfProcessId())
				.wfActivityId(request.getWfActivityId());

		final SeqNoProvider seqNoProvider = SeqNoProvider.ofInt(10);
		final ArrayList<WFApprovalRequest> approvalRequests = new ArrayList<>();
		for (final UserId approverId : usersToApproveList)
		{
			approvalRequests.add(requestTemplate.seqNo(seqNoProvider.getAndIncrement()).userId(approverId).build());
		}

		wfApprovalRequestRepository.saveForDocument(request.getDocumentRef(), approvalRequests);

		return approvalRequests;
	}

	private static Optional<UserId> getRequestorId(@NonNull final WFApprovalStrategyHandler.Request request)
	{
		return request.getDocument()
				.getValue(I_C_Order.COLUMNNAME_Requestor_ID)
				.map(valueObj -> RepoIdAwares.ofObject(valueObj, UserId.class));
	}

	private static Optional<UserId> getProjectManagerId(@NonNull final WFApprovalStrategyHandler.Request request)
	{
		return request.getDocument()
				.getValue(I_C_Order.COLUMNNAME_ProjectManager_ID)
				.map(valueObj -> RepoIdAwares.ofObject(valueObj, UserId.class));
	}
}

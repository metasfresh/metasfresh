package de.metas.workflow.execution.approval.strategy.impl;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.user.UserId;
import de.metas.util.lang.SeqNoProvider;
import de.metas.workflow.execution.approval.WFApprovalRequest;
import de.metas.workflow.execution.approval.WFApprovalRequestRepository;
import de.metas.workflow.execution.approval.WFApprovalRequestStatus;
import de.metas.workflow.execution.approval.strategy.WFApprovalStrategy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RequestorHierarcyProjectManagerPlusCTO_ApprovalStrategy implements WFApprovalStrategy
{
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
					return Response.forwardTo(approvalRequest.getUserId());
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
							// TODO .docBaseType()
							// TODO .documentNo()
							//
							.seqNo(seqNoProvider.getAndIncrement())
							.userId(approverId)
							.status(WFApprovalRequestStatus.Pending)
							//
							.requestDate(SystemTime.asInstant())
							.responseDate(null)
							//
							// TODO wfProcessId;
							// TODO wfActivityId;
							.build()
			);
		}

		wfApprovalRequestRepository.saveForDocument(request.getDocumentRef(), approvalRequests);

		return approvalRequests;
	}

	public List<UserId> findUsersToApprove(@NonNull final WFApprovalStrategy.Request request)
	{
		// FIXME HARDCODED
		return ImmutableList.of(
				UserId.ofRepoId(2188224),
				UserId.ofRepoId(2188225),
				UserId.ofRepoId(2188226));
	}
}

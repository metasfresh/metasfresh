package de.metas.workflow.execution.approval;

import com.google.common.collect.ImmutableList;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.user.UserId;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.execution.WFActivityId;
import de.metas.workflow.execution.WFProcessId;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyId;
import de.metas.workflow.execution.approval.strategy.DocApprovalStrategyService;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class WFActivityApprovalCommand
{
	@NonNull private final WFApprovalRequestRepository wfApprovalRequestRepository;
	@NonNull private final DocApprovalStrategyService docApprovalStrategyService;

	@NonNull private final TableRecordReference documentRef;
	@NonNull private final WFApprovalRequest.AdditionalDocumentInfo additionalDocumentInfo;
	@NonNull private final WFProcessId wfProcessId;
	@NonNull private final WFActivityId wfActivityId;
	private final DocApprovalStrategyService.GetUsersToApproveRequest getUsersToApproveRequest;

	@Builder
	private WFActivityApprovalCommand(
			@NonNull final WFApprovalRequestRepository wfApprovalRequestRepository,
			@NonNull final DocApprovalStrategyService docApprovalStrategyService,
			//
			@NonNull final LocalDate evaluationDate,
			@NonNull final TableRecordReference documentRef,
			@NonNull final WFApprovalRequest.AdditionalDocumentInfo additionalDocumentInfo,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final DocApprovalStrategyId docApprovalStrategyId,
			//
			@NonNull final UserId documentOwnerId,
			@Nullable final UserId requestorId,
			@Nullable final UserId projectManagerId,
			//
			@Nullable final Money amountToApprove,
			//
			@NonNull final UserId wfInvokerId,
			@NonNull final WFResponsible wfResponsible,
			@NonNull final WFProcessId wfProcessId,
			@NonNull final WFActivityId wfActivityId
	)
	{
		this.wfApprovalRequestRepository = wfApprovalRequestRepository;
		this.docApprovalStrategyService = docApprovalStrategyService;

		this.documentRef = documentRef;
		this.additionalDocumentInfo = additionalDocumentInfo;
		this.wfProcessId = wfProcessId;
		this.wfActivityId = wfActivityId;

		this.getUsersToApproveRequest = DocApprovalStrategyService.GetUsersToApproveRequest.builder()
				.evaluationDate(evaluationDate)
				.docApprovalStrategyId(docApprovalStrategyId)
				.clientAndOrgId(clientAndOrgId)
				.amountToApprove(amountToApprove)
				.workflowInvokerId(wfInvokerId)
				.workflowResponsible(wfResponsible)
				.documentOwnerId(documentOwnerId)
				.requestorId(requestorId)
				.projectManagerId(projectManagerId)
				.build();

	}

	@SuppressWarnings("unused")
	public static class WFActivityApprovalCommandBuilder
	{
		public WFActivityApprovalResponse execute() {return build().execute();}

		public <R> R executeThenMapResponse(@NonNull final WFActivityApprovalResponse.CaseMapper<R> mapper) {return execute().map(mapper);}
	}

	public WFActivityApprovalResponse execute()
	{
		final List<WFApprovalRequest> approvalRequests = getOrCreateDocumentApprovals();
		return toApprovalResponse(approvalRequests);
	}

	@NotNull
	private static WFActivityApprovalResponse toApprovalResponse(final List<WFApprovalRequest> approvalRequests)
	{
		if (approvalRequests.isEmpty())
		{
			return WFActivityApprovalResponse.APPROVED;
		}

		int countApproved = 0;
		for (WFApprovalRequest approvalRequest : approvalRequests)
		{
			switch (approvalRequest.getStatus())
			{
				case Approved -> countApproved++;
				case Rejected ->
				{
					return WFActivityApprovalResponse.REJECTED;
				}
				case Pending ->
				{
					return WFActivityApprovalResponse.forwardTo(approvalRequest.getUserId());
				}
			}
		}

		return approvalRequests.size() == countApproved ? WFActivityApprovalResponse.APPROVED : WFActivityApprovalResponse.PENDING;
	}

	private List<WFApprovalRequest> getOrCreateDocumentApprovals()
	{
		List<WFApprovalRequest> approvalRequests = wfApprovalRequestRepository.getByDocumentRef(documentRef);
		if (approvalRequests.isEmpty())
		{
			approvalRequests = createDocumentApprovals();
		}

		return approvalRequests.stream()
				.sorted(Comparator.comparing(WFApprovalRequest::getSeqNo))
				.collect(ImmutableList.toImmutableList());
	}

	private List<WFApprovalRequest> createDocumentApprovals()
	{
		final List<UserId> usersToApproveList = docApprovalStrategyService.getUsersToApprove(getUsersToApproveRequest);

		final List<WFApprovalRequest> result = WFApprovalRequest.builder()
				.documentRef(documentRef)
				.additionalDocumentInfo(additionalDocumentInfo)
				.status(WFApprovalRequestStatus.Pending)
				.wfProcessId(wfProcessId)
				.wfActivityId(wfActivityId)
				.buildForEachUser(usersToApproveList);

		wfApprovalRequestRepository.saveForDocument(documentRef, result);

		return result;
	}
}

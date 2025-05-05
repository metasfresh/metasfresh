package de.metas.workflow.execution.approval;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.user.UserId;
import de.metas.workflow.execution.WorkflowExecutor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WFApprovalRequestService
{
	@NonNull private final WFApprovalRequestRepository repository;

	public boolean canBeApprovedBy(@NonNull final WFApprovalRequestId requestId, @NonNull final UserId approverId)
	{
		return repository.getById(requestId).canBeApprovedBy(approverId);
	}

	public boolean canBeApprovedBy(@NonNull final Set<WFApprovalRequestId> requestIds, @NonNull final UserId approverId)
	{
		if (requestIds.isEmpty())
		{
			return false;
		}

		return repository.streamByIds(requestIds).allMatch(request -> request.canBeApprovedBy(approverId));
	}

	public void approve(@NonNull final WFApprovalRequestId requestId, @NonNull final UserId approverId)
	{
		approve(ImmutableSet.of(requestId), approverId);
	}

	public void approve(@NonNull final Set<WFApprovalRequestId> requestIds, @NonNull final UserId approverId)
	{
		final Instant date = SystemTime.asInstant();

		final HashSet<TableRecordReference> documentRefs = new HashSet<>();
		repository.updateByIds(requestIds, request -> {
			request.approve(approverId, date);
			documentRefs.add(request.getDocumentRef());
		});

		notifyApproved(documentRefs, approverId);
	}

	private void notifyApproved(@NonNull final Set<TableRecordReference> documentRefs, @NonNull final UserId approverId)
	{
		documentRefs.forEach(documentRef -> notifyApproved(documentRef, approverId));
	}

	private void notifyApproved(@NonNull final TableRecordReference documentRef, @NonNull final UserId approverId)
	{
		WorkflowExecutor.builder()
				.documentRef(documentRef)
				.userId(approverId)
				.build()
				.resume();
	}

}

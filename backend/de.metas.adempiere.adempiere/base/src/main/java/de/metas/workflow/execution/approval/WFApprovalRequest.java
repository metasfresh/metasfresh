package de.metas.workflow.execution.approval;

import de.metas.document.DocBaseType;
import de.metas.user.UserId;
import de.metas.util.lang.SeqNo;
import de.metas.workflow.execution.WFActivityId;
import de.metas.workflow.execution.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class WFApprovalRequest
{
	@NonNull TableRecordReference documentRef;
	@Nullable DocBaseType docBaseType;
	@Nullable String documentNo;

	@NonNull SeqNo seqNo;
	@NonNull UserId userId;
	@NonNull WFApprovalRequestStatus status;

	@NonNull Instant requestDate;
	@Nullable Instant responseDate;

	@Nullable WFProcessId wfProcessId;
	@Nullable WFActivityId wfActivityId;
}

package de.metas.workflow.execution.approval;

import de.metas.document.DocBaseType;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.lang.SeqNo;
import de.metas.workflow.execution.WFActivityId;
import de.metas.workflow.execution.WFProcessId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_WF_Approval_Request;

import javax.annotation.Nullable;
import java.time.Instant;

@Builder
@EqualsAndHashCode
@ToString
@Getter
public class WFApprovalRequest
{
	@Nullable private WFApprovalRequestId id;

	@NonNull private final TableRecordReference documentRef;
	@Nullable private final String documentNo;
	@Nullable private final DocBaseType docBaseType;

	@NonNull private final SeqNo seqNo;
	@NonNull private final UserId userId;
	@NonNull private WFApprovalRequestStatus status;

	@NonNull private final Instant requestDate;
	@Nullable private Instant responseDate;

	@Nullable private final WFProcessId wfProcessId;
	@Nullable private final WFActivityId wfActivityId;

	@NonNull
	public WFApprovalRequestId getIdNotNull() {return Check.assumeNotNull(id, "request is saved: {}", this);}

	void setId(@NonNull final WFApprovalRequestId id) {this.id = id;}

	public TableRecordReference toTableRecordReference() {return TableRecordReference.of(I_AD_WF_Approval_Request.Table_Name, getIdNotNull());}

	public boolean canBeApprovedBy(@NonNull final UserId approverId)
	{
		return status.isPending() && UserId.equals(this.userId, approverId);
	}

	public void approve(@NonNull final UserId approverId, @NonNull Instant date)
	{
		if (!canBeApprovedBy(approverId))
		{
			throw new AdempiereException("Request cannot be approved by " + approverId);
		}

		this.status = WFApprovalRequestStatus.Approved;
		this.responseDate = date;
	}
}

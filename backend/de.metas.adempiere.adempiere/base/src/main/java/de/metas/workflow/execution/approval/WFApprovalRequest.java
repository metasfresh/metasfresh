package de.metas.workflow.execution.approval;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocBaseType;
import de.metas.money.Money;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.lang.SeqNo;
import de.metas.util.lang.SeqNoProvider;
import de.metas.workflow.execution.WFActivityId;
import de.metas.workflow.execution.WFProcessId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_WF_Approval_Request;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@EqualsAndHashCode
@ToString
@Getter
public class WFApprovalRequest
{
	@Nullable private WFApprovalRequestId id;

	@NonNull private final TableRecordReference documentRef;
	@Nullable private final AdditionalDocumentInfo additionalDocumentInfo;

	@NonNull private final SeqNo seqNo;
	@NonNull private final UserId userId;
	@NonNull private WFApprovalRequestStatus status;

	@NonNull @Builder.Default private final Instant requestDate = SystemTime.asInstant();
	@Nullable private Instant responseDate;

	@Nullable private final WFProcessId wfProcessId;
	@Nullable private final WFActivityId wfActivityId;

	@SuppressWarnings("unused")
	public static class WFApprovalRequestBuilder
	{
		List<WFApprovalRequest> buildForEachUser(@NonNull final List<UserId> userIds)
		{
			if (userIds.isEmpty())
			{
				return ImmutableList.of();
			}

			final SeqNoProvider seqNoProvider = SeqNoProvider.ofInt(10);
			final ArrayList<WFApprovalRequest> result = new ArrayList<>(userIds.size());
			for (final UserId userId : userIds)
			{
				result.add(seqNo(seqNoProvider.getAndIncrement()).userId(userId).build());
			}

			return result;
		}
	}

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

	//
	//
	//

	@Value
	@Builder
	public static class AdditionalDocumentInfo
	{
		@Nullable String documentNo;
		@Nullable DocBaseType docBaseType;
		@Nullable BPartnerId bpartnerId;
		@Nullable ActivityId activityId;
		@Nullable ProjectId projectId;
		@Nullable Money totalAmt;
	}
}

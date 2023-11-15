package de.metas.workflow.execution.approval;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_WF_Approval_Request;

@AllArgsConstructor
@Getter
public enum WFApprovalRequestStatus implements ReferenceListAwareEnum
{
	Pending(X_AD_WF_Approval_Request.STATUS_Pending),
	Approved(X_AD_WF_Approval_Request.STATUS_Approved),
	Rejected(X_AD_WF_Approval_Request.STATUS_Rejected),
	;

	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<WFApprovalRequestStatus> index = ReferenceListAwareEnums.index(values());

	public static WFApprovalRequestStatus ofCode(@NonNull String code)
	{
		return index.ofCode(code);
	}

	public boolean isPending() {return Pending.equals(this);}
}


package de.metas.workflow.process;

import com.google.common.collect.ImmutableSet;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.workflow.execution.approval.WFApprovalRequestId;
import de.metas.workflow.execution.approval.WFApprovalRequestService;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Objects;

public class AD_WF_Approval_Request_ApproveSelection extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final WFApprovalRequestService wfApprovalRequestService = SpringContextHolder.instance.getBean(WFApprovalRequestService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ImmutableSet<WFApprovalRequestId> requestIds = getSelectedRequestIds();
		if (requestIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return wfApprovalRequestService.canBeApprovedBy(requestIds, Env.getLoggedUserId())
				? ProcessPreconditionsResolution.accept()
				: ProcessPreconditionsResolution.rejectWithInternalReason("Not all rows can be approved by current user");
	}

	@Override
	protected String doIt()
	{
		wfApprovalRequestService.approve(getSelectedRequestIds(), getUserId());
		return MSG_OK;
	}

	private ImmutableSet<WFApprovalRequestId> getSelectedRequestIds()
	{
		return streamSelectedRows()
				.map(AD_WF_Approval_Request_ApproveSelection::extractWFApprovalRequestId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	private static WFApprovalRequestId extractWFApprovalRequestId(final IViewRow row)
	{
		return WFApprovalRequestId.ofRepoIdOrNull(row.getId().toIntOr(-1));
	}

}

package de.metas.workflow.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.workflow.execution.approval.WFApprovalRequestId;
import de.metas.workflow.execution.approval.WFApprovalRequestService;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

public class AD_WF_Approval_Request_Approve extends JavaProcess implements IProcessPrecondition
{
	private final WFApprovalRequestService wfApprovalRequestService = SpringContextHolder.instance.getBean(WFApprovalRequestService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final WFApprovalRequestId requestId = WFApprovalRequestId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (requestId == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return wfApprovalRequestService.canBeApprovedBy(requestId, Env.getLoggedUserId())
				? ProcessPreconditionsResolution.accept()
				: ProcessPreconditionsResolution.rejectWithInternalReason("Cannot be approved");
	}

	@Override
	protected String doIt()
	{
		final WFApprovalRequestId requestId = WFApprovalRequestId.ofRepoId(getRecord_ID());
		wfApprovalRequestService.approve(requestId, getUserId());
		return MSG_OK;
	}
}

package de.metas.acct.acct_simulation;

import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.view.ViewId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class SAP_GLJournal_AcctSimulation_Launcher extends JavaProcess implements IProcessPrecondition
{
	private final AcctSimulationViewFactory viewsFactory = SpringContextHolder.instance.getBean(AcctSimulationViewFactory.class);
	private final SAPGLJournalService glJournalService = SpringContextHolder.instance.getBean(SAPGLJournalService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not a single row selection");
		}

		final SAPGLJournalId journalId = context.getSingleSelectedRecordId(SAPGLJournalId.class);
		final SAPGLJournal glJournal = glJournalService.getById(journalId);
		if (!glJournal.getDocStatus().isDraftedOrInProgress())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not draft");
		}

		if (!glJournal.isBalanced())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not balanced");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ViewId viewId = viewsFactory.createView(getRecordRef(), true).getViewId();
		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
				.build());
		return MSG_OK;
	}
}

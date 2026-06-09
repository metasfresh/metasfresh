package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class SAP_GLJournal_OpenItems_Launcher extends JavaProcess implements IProcessPrecondition
{
	private final SAPGLJournalService sapglJournalService = SpringContextHolder.instance.getBean(SAPGLJournalService.class);
	private final IViewsRepository viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final SAPGLJournalId sapglJournalId = context.getSingleSelectedRecordId(SAPGLJournalId.class);
		if (!sapglJournalService.getDocStatus(sapglJournalId).isDraftedOrInProgress())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not drafted");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final SAPGLJournalId sapglJournalId = getSelectedSAPGLJournalId();

		final ViewId viewId = viewsFactory.createView(OIViewFactory.createViewRequest(sapglJournalId))
				.getViewId();

		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
				.build());

		return MSG_OK;
	}

	private SAPGLJournalId getSelectedSAPGLJournalId()
	{
		return getRecordIdAssumingTableName(I_SAP_GLJournal.Table_Name, SAPGLJournalId::ofRepoId);
	}
}

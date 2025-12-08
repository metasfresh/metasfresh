package de.metas.acct.gljournal_sap.process;

import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.document.engine.DocStatus;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class SAP_GLJournal_GenerateTaxLines extends JavaProcess implements IProcessPrecondition
{
	private final SAPGLJournalService glJournalService = SpringContextHolder.instance.getBean(SAPGLJournalService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(context.getSingleSelectedRecordId());
		final DocStatus docStatus = glJournalService.getDocStatus(glJournalId);
		if (!docStatus.isDraftedOrInProgress())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not drafted");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(getRecord_ID());
		glJournalService.regenerateTaxLines(glJournalId);

		return MSG_OK;
	}
}

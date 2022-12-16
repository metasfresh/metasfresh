package de.metas.acct.gljournal_sap.process;

import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.process.JavaProcess;
import org.compiere.SpringContextHolder;

public class SAP_GLJournal_GenerateTaxLines extends JavaProcess
{
	private final SAPGLJournalService glJournalService = SpringContextHolder.instance.getBean(SAPGLJournalService.class);

	@Override
	protected String doIt()
	{
		final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(getRecord_ID());
		glJournalService.regenerateTaxLines(glJournalId);

		return MSG_OK;
	}
}

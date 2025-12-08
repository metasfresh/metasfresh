package de.metas.acct.gljournal_sap.document;

import de.metas.acct.gljournal_sap.service.SAPGLJournalService;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentHandlerProvider;
import org.springframework.stereotype.Component;

@Component
public class SAPGLJournalDocumentHandlerProvider implements DocumentHandlerProvider
{
	private final SAPGLJournalService glJournalService;

	public SAPGLJournalDocumentHandlerProvider(final SAPGLJournalService glJournalService) {this.glJournalService = glJournalService;}

	@Override
	public String getHandledTableName()
	{
		return I_SAP_GLJournal.Table_Name;
	}

	@Override
	public DocumentHandler provideForDocument(final Object model)
	{
		return new SAPGLJournalDocumentHandler(glJournalService);
	}
}

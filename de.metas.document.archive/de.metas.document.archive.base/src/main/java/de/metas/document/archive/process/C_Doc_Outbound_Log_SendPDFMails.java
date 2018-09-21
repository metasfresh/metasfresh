package de.metas.document.archive.process;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import lombok.NonNull;

public class C_Doc_Outbound_Log_SendPDFMails
		extends AbstractSendDocumentsForSelection
		implements IProcessPrecondition
{

	private static final String MSG_EMPTY_MailTo = "SendMailsForSelection.EMPTY_MailTo";

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No records selected");
		}

		if (context.getSelectionSize() > 500)
		{
			// Checking is too expensive; just assume that some selected records have an email address
			return ProcessPreconditionsResolution.accept();
		}

		final boolean atLeastOneRecordHasEmail = context
				.getSelectedModels(I_C_Doc_Outbound_Log.class)
				.stream()
				.anyMatch(record -> !Check.isEmpty(record.getCurrentEMailAddress(), true));
		if (!atLeastOneRecordHasEmail)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_EMPTY_MailTo));
		}

		return ProcessPreconditionsResolution.accept();
	}
}

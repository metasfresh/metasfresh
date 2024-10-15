package de.metas.document.archive.mailrecipient.process;

import de.metas.common.util.Check;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import lombok.NonNull;

public class C_Doc_Outbound_Log_SendPDFMails
		extends AbstractMailDocumentsForSelection
		implements IProcessPrecondition
{
	private static final AdMessageKey MSG_EMPTY_MailTo = AdMessageKey.of("SendMailsForSelection.EMPTY_MailTo");

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final SelectionSize selectionSize = context.getSelectionSize();
		if (selectionSize.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (selectionSize.isAllSelected() || selectionSize.getSize() > 500)
		{
			// Checking is too expensive; just assume that some selected records have an email address
			return ProcessPreconditionsResolution.accept();
		}

		final boolean atLeastOneRecordHasEmail = context
				.streamSelectedModels(I_C_Doc_Outbound_Log.class)
				.anyMatch(record -> !Check.isEmpty(record.getCurrentEMailAddress(), true));
		if (!atLeastOneRecordHasEmail)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_EMPTY_MailTo));
		}

		return ProcessPreconditionsResolution.accept();
	}
}

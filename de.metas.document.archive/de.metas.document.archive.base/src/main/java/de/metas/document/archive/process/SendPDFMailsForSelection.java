package de.metas.document.archive.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;

/**
 * Send PDF mails for selected entries.
 *
 * @author al
 */
public class SendPDFMailsForSelection
		extends AbstractSendDocumentsForSelection
		implements IProcessPrecondition
{

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		if(context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No records selected");
		}
		return ProcessPreconditionsResolution.accept();
	}
}

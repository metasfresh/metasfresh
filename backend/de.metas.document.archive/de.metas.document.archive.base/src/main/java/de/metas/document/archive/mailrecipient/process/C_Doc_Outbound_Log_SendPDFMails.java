package de.metas.document.archive.mailrecipient.process;

import com.google.common.collect.ImmutableList;
import de.metas.async.Async_Constants;
import de.metas.common.util.Check;
import de.metas.document.archive.api.impl.DocOutboundService;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.process.SelectionSize;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

public class C_Doc_Outbound_Log_SendPDFMails
	extends JavaProcess
		implements IProcessPrecondition
{
	private static final AdMessageKey MSG_EMPTY_MailTo = AdMessageKey.of("SendMailsForSelection.EMPTY_MailTo");
	private static final AdMessageKey MSG_No_DocOutboundLog_Selection = AdMessageKey.of("AbstractMailDocumentsForSelection.No_DocOutboundLog_Selection");
	private static final String PARA_OnlyNotSentMails = "OnlyNotSentMails";

	@NonNull private final transient DocOutboundService docOutboundService = SpringContextHolder.instance.getBean(DocOutboundService.class);

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

	@Param(parameterName = PARA_OnlyNotSentMails, mandatory=true)
	private boolean p_OnlyNotSentMails = false;

	@Override
	protected final void prepare()
	{
		if (docOutboundService.retrieveLogs(getFilter(), true).isEmpty())
		{
			throw new AdempiereException(MSG_No_DocOutboundLog_Selection);
		}
	}

	protected IQueryFilter<I_C_Doc_Outbound_Log> getFilter()
	{
		final ProcessInfo pi = getProcessInfo();
		return pi.getQueryFilterOrElse(ConstantQueryFilter.of(false));
	}

	@Override
	@RunOutOfTrx
	protected final String doIt() throws Exception
	{
		final PInstanceId pinstanceId = getPinstanceId();
		final int counter = docOutboundService.sendMails( getFilter(), pinstanceId, p_OnlyNotSentMails);

		return msgBL.getMsg(Async_Constants.MSG_WORKPACKAGES_CREATED, ImmutableList.of(counter));
	}
}

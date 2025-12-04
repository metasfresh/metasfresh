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
import de.metas.process.SelectionSize;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.MutableInt;
import org.compiere.SpringContextHolder;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class C_Doc_Outbound_Log_SendPDFMails
	extends JavaProcess
		implements IProcessPrecondition
{
	private static final AdMessageKey MSG_EMPTY_MailTo = AdMessageKey.of("SendMailsForSelection.EMPTY_MailTo");
	private static final AdMessageKey MSG_No_DocOutboundLog_Selection = AdMessageKey.of("AbstractMailDocumentsForSelection.No_DocOutboundLog_Selection");
	private static final String PARA_OnlyNotSentMails = "OnlyNotSentMails";

	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient DocOutboundService docOutboundService = SpringContextHolder.instance.getBean(DocOutboundService.class);

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
		final IQueryFilter<I_C_Doc_Outbound_Log> filter = getFilter();
		final PInstanceId pinstanceId = getPinstanceId();

		//
		// Create selection for PInstance and make sure we're enqueuing something
		final int selectionCount = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class, this)
				.addOnlyActiveRecordsFilter()
				.filter(filter)
				.create()
				.createSelection(pinstanceId);

		if (selectionCount == 0)
		{
			throw new AdempiereException(MSG_No_DocOutboundLog_Selection);
		}
	}

	protected IQueryFilter<I_C_Doc_Outbound_Log> getFilter()
	{
		final ProcessInfo pi = getProcessInfo();
		final IQueryFilter<I_C_Doc_Outbound_Log> selectedRecordsFilter = pi.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		return queryBL
				.createCompositeQueryFilter(I_C_Doc_Outbound_Log.class)
				.addNotNull(I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddress)
				.addFilter(selectedRecordsFilter);
	}

	@Override
	protected final String doIt() throws Exception
	{
		final PInstanceId pinstanceId = getPinstanceId();
		final MutableInt counter = docOutboundService.sendMail( retrieveSelectedDocOutboundLogs(pinstanceId), pinstanceId, false, p_OnlyNotSentMails);

		return msgBL.getMsg(Async_Constants.MSG_WORKPACKAGES_CREATED, ImmutableList.of(counter.getValue()));
	}

	private Stream<I_C_Doc_Outbound_Log> retrieveSelectedDocOutboundLogs(final PInstanceId pinstanceId)
	{
		final Iterator<I_C_Doc_Outbound_Log> iterator = queryBL
				.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.setOnlySelection(pinstanceId)
				.create()
				.iterate(I_C_Doc_Outbound_Log.class);

		final Iterable<I_C_Doc_Outbound_Log> iterable = () -> iterator;
		return StreamSupport.stream(iterable.spliterator(), false);
	}
}

package de.metas.document.archive.process;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.SqlQueryFilter;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_User;
import org.compiere.model.Query;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.interfaces.I_C_BPartner;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Contains basic utility BL needed to create processes which send mails for given selection.
 *
 * @author al
 */
public abstract class AbstractSendDocumentsForSelection extends JavaProcess
{
	private static final String MSG_No_DocOutboundLog_Selection = "C_Doc_Outbound_Log.No_DocOutboundLog_Selection";

	private static final String MSG_EMPTY_C_Doc_Outbound_Log_Line_ID = "SendMailsForSelection.EMPTY_C_Doc_Outbound_Log_Line_ID";
	private static final String MSG_EMPTY_AD_Archive_ID = "SendMailsForSelection.EMPTY_AD_Archive_ID";
	private static final String MSG_EMPTY_C_BPartner_ID = "SendMailsForSelection.EMPTY_C_BPartner_ID";
	private static final String MSG_EMPTY_AD_User_To_ID = "SendMailsForSelection.EMPTY_AD_User_To_ID";
	private static final String MSG_EMPTY_MailTo = "SendMailsForSelection.EMPTY_MailTo";

	private static final String PARA_OnlyNotSentMails = "OnlyNotSentMails";
	private boolean p_OnlyNotSentMails = false;

	@Override
	protected final void prepare()
	{
		//
		// Init parameters first
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				// do nothing
			}
			else if (PARA_OnlyNotSentMails.equals(name))
			{
				p_OnlyNotSentMails = "Y".equals(para.getParameter());
			}
		}

		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		final String tableName = I_C_Doc_Outbound_Log.Table_Name;

		final ProcessInfo pi = getProcessInfo();
		final SqlQueryFilter piQueryFilter = (SqlQueryFilter)pi.getQueryFilter();
		final String whereClause = piQueryFilter.getSql();

		final int pInstanceId = getAD_PInstance_ID();

		//
		// Create selection for PInstance and make sure we're enqueuing something
		final int selectionCount = new Query(ctx, tableName, whereClause, trxName)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.createSelection(pInstanceId);
		if (selectionCount == 0)
		{
			throw new AdempiereException(Services.get(IMsgBL.class).getMsg(ctx, MSG_No_DocOutboundLog_Selection));
		}
	}

	@Override
	protected final String doIt() throws Exception
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, getWorkpackageProcessor());

		//
		// Enqueue selected archives as workpackages
		final int pInstanceId = getAD_PInstance_ID();
		final List<I_C_Doc_Outbound_Log_Line> docOutboundLines = getPDFArchiveDocOutboundLinesForSelection(ctx, pInstanceId, trxName);
		for (final I_C_Doc_Outbound_Log_Line docOutboundLine : docOutboundLines)
		{
			final I_C_Queue_Block block = queue.enqueueBlock(ctx);
			final I_C_Queue_WorkPackage workpackage = queue.enqueueWorkPackage(block, IWorkPackageQueue.PRIORITY_AUTO);
			workpackage.setAD_PInstance_ID(pInstanceId);
			queue.enqueueElement(workpackage, docOutboundLine);

			queue.markReadyForProcessingAfterTrxCommit(workpackage, trxName);
		}
		return "OK";
	}

	protected abstract Class<? extends IWorkpackageProcessor> getWorkpackageProcessor();

	private final List<I_C_Doc_Outbound_Log_Line> getPDFArchiveDocOutboundLinesForSelection(final Properties ctx, final int pInstanceId, final String trxName)
	{
		final List<I_C_Doc_Outbound_Log> logs = retrieveSelectedDocOutboundLogs(ctx, pInstanceId, trxName);
		final ProblemCollector collector = new ProblemCollector();

		final List<I_C_Doc_Outbound_Log_Line> logLines = new ArrayList<>();
		for (final I_C_Doc_Outbound_Log log : logs)
		{
			final I_C_Doc_Outbound_Log_Line logLine = retrieveDocumentLogLine(log);
			if (!isEmailSendable(ctx, log, logLine, collector, trxName))
			{
				continue;
			}
			logLines.add(logLine);
		}

		final Exception exception = collector.getCollectedExceptionsOrNull(ctx);
		if (exception != null)
		{
			log.warn(exception.getLocalizedMessage());
		}
		return logLines;
	}

	private final List<I_C_Doc_Outbound_Log> retrieveSelectedDocOutboundLogs(final Properties ctx, final int pInstanceId, final String trxName)
	{
		final IQueryBuilder<I_C_Doc_Outbound_Log> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Doc_Outbound_Log.class, ctx, trxName)
				.setOnlySelection(pInstanceId);
		final List<I_C_Doc_Outbound_Log> logs = queryBuilder.create()
				.list(I_C_Doc_Outbound_Log.class);
		return logs;
	}

	/**
	 * To be overridden where necessary
	 *
	 * @param log
	 * @return document log line
	 */
	protected I_C_Doc_Outbound_Log_Line retrieveDocumentLogLine(final I_C_Doc_Outbound_Log log)
	{
		final I_C_Doc_Outbound_Log_Line logLine = Services.get(IDocOutboundDAO.class).retrieveCurrentPDFArchiveLogLineOrNull(log);
		return logLine;
	}

	private final boolean isEmailSendable(final Properties ctx,
			final I_C_Doc_Outbound_Log log,
			final I_C_Doc_Outbound_Log_Line logLine,
			final ProblemCollector collector,
			final String trxName)
	{
		//
		// Note that the problems here come in cascade, that's why we're returning instead of keeping collecting
		//

		//
		// No document archive log line found; skipping
		if (logLine == null)
		{
			collector.collectException(MSG_EMPTY_C_Doc_Outbound_Log_Line_ID, log.getDocumentNo());
			return false;
		}

		//
		// Log line must have an archive to be viable
		if (logLine.getAD_Archive_ID() <= 0)
		{
			collector.collectException(MSG_EMPTY_AD_Archive_ID, log.getDocumentNo());
			return false;
		}

		//
		// Do not enqueue if the log was sent at least once
		if (p_OnlyNotSentMails && isSentAtLeastOnce(logLine))
		{
			return false; // don't collect this, just silently skip it
		}

		//
		// Log must have a C_BPartner to send the mail to
		final I_C_BPartner partner = InterfaceWrapperHelper.create(log.getC_BPartner(), I_C_BPartner.class);
		if (partner == null)
		{
			collector.collectException(MSG_EMPTY_C_BPartner_ID, log.getDocumentNo());
			return false;
		}

		//
		// The partner specified in the log must have a billing contact to which we send the email to
		final I_AD_User userTo = Services.get(IBPartnerBL.class).retrieveBillContact(ctx, partner.getC_BPartner_ID(), trxName);
		if (userTo == null)
		{
			collector.collectException(MSG_EMPTY_AD_User_To_ID, partner.getName());
			return false;
		}

		//
		// The billing contact must have a mail address
		final String mailTo = userTo.getEMail();
		if (Check.isEmpty(mailTo))
		{
			collector.collectException(MSG_EMPTY_MailTo, userTo.getName());
			return false;
		}

		final Map<String, List<Object>> assertionResult = assertSendable(ctx, log, logLine, trxName);
		for (final Entry<String, List<Object>> result : assertionResult.entrySet())
		{
			final List<Object> values = result.getValue();
			collector.collectException(result.getKey(), values.toArray(new Object[values.size()]));
		}

		return true;
	}

	protected abstract boolean isSentAtLeastOnce(final I_C_Doc_Outbound_Log_Line docOutboundLine);

	protected Map<String, List<Object>> assertSendable(final Properties ctx,
			final I_C_Doc_Outbound_Log log,
			final I_C_Doc_Outbound_Log_Line logLine,
			final String trxName)
	{
		// no additional assertions at this level
		return Collections.emptyMap(); 
	}
}

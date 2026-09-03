/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.document.archive.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.async.spi.impl.MailWorkpackageProcessor;
import de.metas.document.archive.config.DocOutboundConfig;
import de.metas.document.archive.config.DocOutboundConfigQuery;
import de.metas.document.archive.config.DocOutboundConfigRepository;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.archive.ArchiveId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.load;

@Service
@RequiredArgsConstructor
public class DocOutboundService
{
	private static final AdMessageKey MSG_EMPTY_AD_Archive_ID = AdMessageKey.of("SendMailsForSelection.EMPTY_AD_Archive_ID");

	private final IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final DocOutboundConfigRepository docOutboundConfigRepository;

	public static DocOutboundService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new DocOutboundService(DocOutboundConfigRepository.newInstanceForUnitTesting());
	}

	@Nullable
	public String getDocumentEmail(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final TableRecordReference recordRef = TableRecordReference.of(docOutboundLogRecord.getAD_Table_ID(), docOutboundLogRecord.getRecord_ID());

		if (recordRef.getAD_Table_ID() == getTableId(I_C_Invoice.class))
		{
			final I_C_Invoice invoice = load(InvoiceId.ofRepoId(recordRef.getRecord_ID()), I_C_Invoice.class);

			return invoice.getEMail();
		}

		if (recordRef.getAD_Table_ID() == getTableId(I_C_Order.class))
		{
			final I_C_Order order = load(OrderId.ofRepoId(recordRef.getRecord_ID()), I_C_Order.class);

			return order.getEMail();
		}

		if (recordRef.getAD_Table_ID() == getTableId(I_M_InOut.class))
		{
			final I_M_InOut inout = load(InOutId.ofRepoId(recordRef.getRecord_ID()), I_M_InOut.class);
			return inout.getEMail();
		}

		return null;
	}

	@Nullable
	public String getLocationEmail(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final TableRecordReference recordRef = TableRecordReference.of(docOutboundLogRecord.getAD_Table_ID(), docOutboundLogRecord.getRecord_ID());

		if (recordRef.getAD_Table_ID() == getTableId(I_C_Invoice.class))
		{
			return invoiceBL.getLocationEmail(InvoiceId.ofRepoId(recordRef.getRecord_ID()));
		}

		if (recordRef.getAD_Table_ID() == getTableId(I_C_Order.class))
		{
			return orderBL.getLocationEmail(OrderId.ofRepoId(recordRef.getRecord_ID()));
		}

		if (recordRef.getAD_Table_ID() == getTableId(I_M_InOut.class))
		{
			return inoutBL.getLocationEmail(InOutId.ofRepoId(recordRef.getRecord_ID()));
		}

		return null;
	}

	public void updatePOReferenceIfExists(
			@NonNull final TableRecordReference recordReference,
			@Nullable final String poReference)
	{
		docOutboundDAO.updatePOReferenceIfExists(recordReference, poReference);
	}

	public ImmutableList<I_C_Doc_Outbound_Log> retrieveLogs(@NonNull final IQueryFilter<I_C_Doc_Outbound_Log> filter, final boolean isFilterCurrentMailSet)
	{
		return docOutboundDAO.retrieveLogs(filter, isFilterCurrentMailSet);
	}

	public void sendMailAutomaticallyIfActive(@NonNull final I_C_Doc_Outbound_Log docOutboundLog)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(docOutboundLog.getC_DocType_ID());
		final DocBaseType docBaseType = docTypeId != null ? docTypeDAO.getDocBaseTypeById(docTypeId) : null;

		final DocOutboundConfig config = docOutboundConfigRepository.getByQuery(DocOutboundConfigQuery.builder()
						.tableId(AdTableId.ofRepoId(docOutboundLog.getAD_Table_ID()))
						.docBaseType(docBaseType)
						.orgId(OrgId.ofRepoId(docOutboundLog.getAD_Org_ID()))
				.build());
		if (config != null && config.isAutoSendDocument() && StringUtils.trimBlankToNull(docOutboundLog.getCurrentEMailAddress()) != null)
		{
			final ImmutableList<IDocOutboundDAO.LogWithLines> logsWithLines = docOutboundDAO.retrieveLogsWithLines(ImmutableList.of(docOutboundLog));
			final Stream<I_C_Doc_Outbound_Log_Line> lines = getPDFArchiveDocOutboundLines(logsWithLines, true);
			sendMails(lines, null);
		}
	}

	public int sendMails(@NonNull final IQueryFilter<I_C_Doc_Outbound_Log> filter,
						@Nullable final PInstanceId pInstanceId,
						final boolean onlyNotSendMails)
	{
		final ImmutableList<IDocOutboundDAO.LogWithLines> logsWithLines = docOutboundDAO.retrieveLogsWithLines(docOutboundDAO.retrieveLogs(filter, true));
		final Stream<I_C_Doc_Outbound_Log_Line> lines = getPDFArchiveDocOutboundLines(logsWithLines, onlyNotSendMails);
		return sendMails(lines, pInstanceId);
	}

	private Stream<I_C_Doc_Outbound_Log_Line> getPDFArchiveDocOutboundLines(@NonNull final ImmutableList<IDocOutboundDAO.LogWithLines> logsWithLines, final boolean onlyNotSendMails)
	{
		return logsWithLines.stream()
				.map(logWithLines -> logWithLines.findCurrentPDFArchiveLogLine()
						.filter(currentLine -> isEmailSendable(logWithLines, currentLine, onlyNotSendMails))
						.orElse(null))
				.filter(Objects::nonNull);
	}

	private boolean isEmailSendable(@NonNull final IDocOutboundDAO.LogWithLines logWithLines, @NonNull final I_C_Doc_Outbound_Log_Line currentLogLine, final boolean onlyNotSendMails)
	{
		if (ArchiveId.ofRepoIdOrNull(currentLogLine.getAD_Archive_ID()) == null)
		{
			final I_C_Doc_Outbound_Log log = logWithLines.getLog();
			Loggables.addLog(msgBL.getMsg( MSG_EMPTY_AD_Archive_ID, ImmutableList.of(StringUtils.nullToEmpty(log.getDocumentNo()))));
			return false;
		}

		return !onlyNotSendMails || !logWithLines.wasEmailSentAtLeastOnce();
	}


	private int sendMails(@NonNull final Stream<I_C_Doc_Outbound_Log_Line> lines,
						@Nullable final PInstanceId pInstanceId)
	{
		final AtomicInteger counter = new AtomicInteger();

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(MailWorkpackageProcessor.class);

		lines.forEach(docOutboundLogLine -> {
			final IWorkPackageBuilder builder = queue.newWorkPackage()
					.addElement(docOutboundLogLine)
					.bindToThreadInheritedTrx();

			if (pInstanceId != null)
			{
				builder.setAD_PInstance_ID(pInstanceId);
			}

			builder.buildAndEnqueue();

			counter.getAndIncrement();
		});
		return counter.get();
	}
}

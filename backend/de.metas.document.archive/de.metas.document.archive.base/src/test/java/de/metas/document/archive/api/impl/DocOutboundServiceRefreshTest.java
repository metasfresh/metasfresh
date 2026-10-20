/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRegistry;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.engine.DocStatus;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_Test;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link DocOutboundService#refreshLogFromNewDocumentArchive(I_AD_Archive)}: the outbound log header follows each new document PDF.
 */
class DocOutboundServiceRefreshTest
{
	private static final Timestamp OLD_DATE_DOC = TimeUtil.asTimestamp(LocalDate.parse("2026-03-26"));
	private static final LocalDate NEW_DATE_ORDERED = LocalDate.parse("2026-03-27");
	private static final Timestamp DATE_LAST_PRINT = TimeUtil.asTimestamp(LocalDate.parse("2026-03-27"));
	private static final Timestamp DATE_LAST_EMAIL = TimeUtil.asTimestamp(LocalDate.parse("2026-03-25"));
	private static final int ASYNC_BATCH_ID = 4711;
	private static final int LOG_ORG_ID = 1000001;

	private DocOutboundService docOutboundService;

	private I_C_BPartner partnerA;
	private I_C_BPartner partnerB;
	private I_C_DocType newDocType;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		docOutboundService = new DocOutboundService(new DocOutboundLogHeaderPopulator(new DocOutboundLogMailRecipientRegistry(Optional.empty())));

		partnerA = partner("A");
		partnerB = partner("B");
		newDocType = newInstance(I_C_DocType.class);
		saveRecord(newDocType);
	}

	@Test
	void noLog_noOp()
	{
		final I_Test record = newInstance(I_Test.class);
		saveRecord(record);

		docOutboundService.refreshLogFromNewDocumentArchive(archive(record, partnerB.getC_BPartner_ID()));

		assertThat(allLogs()).isEmpty();
	}

	@Test
	void noLogForThisRecord_otherRecordsLogUntouched()
	{
		final I_C_Order orderWithLog = order(DocStatus.Completed);
		final I_C_Doc_Outbound_Log otherLog = existingLog(orderWithLog, DocStatus.InProgress);
		final I_C_Order orderWithoutLog = order(DocStatus.Completed);

		docOutboundService.refreshLogFromNewDocumentArchive(archive(orderWithoutLog, partnerB.getC_BPartner_ID()));

		assertThat(allLogs()).hasSize(1);
		final I_C_Doc_Outbound_Log reloaded = reload(otherLog);
		assertThat(reloaded.getC_BPartner_ID()).isEqualTo(partnerA.getC_BPartner_ID());
		assertThat(reloaded.getDocStatus()).isEqualTo(DocStatus.InProgress.getCode());
		assertThat(reloaded.getPOReference()).isEqualTo("oldPOReference");
	}

	@Test
	void completedDocument_partnerStatusAndDocumentFieldsRefreshed()
	{
		final I_C_Order order = order(DocStatus.Completed);
		final I_C_Doc_Outbound_Log log = existingLog(order, DocStatus.InProgress);

		docOutboundService.refreshLogFromNewDocumentArchive(archive(order, partnerB.getC_BPartner_ID()));

		final I_C_Doc_Outbound_Log reloaded = reload(log);
		assertThat(reloaded.getC_BPartner_ID()).isEqualTo(partnerB.getC_BPartner_ID());
		assertThat(reloaded.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
		assertThat(reloaded.getPOReference()).isEqualTo("newPOReference");
		assertThat(reloaded.getDocumentNo()).isEqualTo("newDocumentNo");
		assertThat(reloaded.getFileName()).isEqualTo("newFileName.pdf");
		assertThat(reloaded.getC_DocType_ID()).isEqualTo(newDocType.getC_DocType_ID());
		assertThat(TimeUtil.asLocalDate(reloaded.getDateDoc())).isEqualTo(NEW_DATE_ORDERED);
	}

	@Test
	void waitingPaymentDocument_statusWP()
	{
		final I_C_Order order = order(DocStatus.WaitingPayment);
		final I_C_Doc_Outbound_Log log = existingLog(order, DocStatus.InProgress);

		docOutboundService.refreshLogFromNewDocumentArchive(archive(order, partnerB.getC_BPartner_ID()));

		assertThat(reload(log).getDocStatus()).isEqualTo(DocStatus.WaitingPayment.getCode());
	}

	@Test
	void closedDocument_headerStatusKept_documentFieldsRefreshed()
	{
		final I_C_Order order = order(DocStatus.Closed);
		final I_C_Doc_Outbound_Log log = existingLog(order, DocStatus.Completed);

		docOutboundService.refreshLogFromNewDocumentArchive(archive(order, partnerA.getC_BPartner_ID()));

		final I_C_Doc_Outbound_Log reloaded = reload(log);
		assertThat(reloaded.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
		assertThat(reloaded.getC_BPartner_ID()).isEqualTo(partnerA.getC_BPartner_ID());
		assertThat(reloaded.getPOReference()).isEqualTo("newPOReference");
	}

	@Test
	void inProgressDocument_headerStatusKept()
	{
		final I_C_Order order = order(DocStatus.InProgress);
		final I_C_Doc_Outbound_Log log = existingLog(order, DocStatus.InProgress);

		docOutboundService.refreshLogFromNewDocumentArchive(archive(order, partnerB.getC_BPartner_ID()));

		final I_C_Doc_Outbound_Log reloaded = reload(log);
		assertThat(reloaded.getDocStatus()).isEqualTo(DocStatus.InProgress.getCode());
		assertThat(reloaded.getPOReference()).isEqualTo("newPOReference");
	}

	@Test
	void partnerlessArchive_partnerKept_statusCO_newDateDoc()
	{
		final I_C_Order order = order(DocStatus.Completed);
		final I_C_Doc_Outbound_Log log = existingLog(order, DocStatus.InProgress);

		docOutboundService.refreshLogFromNewDocumentArchive(archive(order, -1));

		final I_C_Doc_Outbound_Log reloaded = reload(log);
		assertThat(reloaded.getC_BPartner_ID()).isEqualTo(partnerA.getC_BPartner_ID());
		assertThat(reloaded.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
		assertThat(TimeUtil.asLocalDate(reloaded.getDateDoc())).isEqualTo(NEW_DATE_ORDERED);
	}

	@Test
	void printAndMailDates_asyncBatch_org_kept()
	{
		final I_C_Order order = order(DocStatus.Completed);
		final I_C_Doc_Outbound_Log log = existingLog(order, DocStatus.InProgress);

		docOutboundService.refreshLogFromNewDocumentArchive(archive(order, partnerB.getC_BPartner_ID()));

		final I_C_Doc_Outbound_Log reloaded = reload(log);
		assertThat(reloaded.getC_BPartner_ID()).isEqualTo(partnerB.getC_BPartner_ID()); // the refresh did run
		assertThat(reloaded.getDateLastPrint()).isEqualTo(DATE_LAST_PRINT);
		assertThat(reloaded.getDateLastEMail()).isEqualTo(DATE_LAST_EMAIL);
		assertThat(reloaded.getC_Async_Batch_ID()).isEqualTo(ASYNC_BATCH_ID);
		assertThat(reloaded.getAD_Org_ID()).isEqualTo(LOG_ORG_ID);
	}

	/**
	 * A mass print writes {@code DateLastPrint} on its own instance of the log row after the row was created;
	 * the refresh must work on the current row, not on an instance that predates that write.
	 */
	@Test
	void refreshKeepsDateLastPrint()
	{
		final I_C_Order order = order(DocStatus.Completed);
		final I_C_Doc_Outbound_Log log = existingLog(order, DocStatus.InProgress);

		final I_C_Doc_Outbound_Log massPrintInstance = reload(log);
		final Timestamp massPrintDate = TimeUtil.asTimestamp(LocalDate.parse("2026-03-28"));
		massPrintInstance.setDateLastPrint(massPrintDate);
		saveRecord(massPrintInstance);

		docOutboundService.refreshLogFromNewDocumentArchive(archive(order, partnerB.getC_BPartner_ID()));

		final I_C_Doc_Outbound_Log reloaded = reload(log);
		assertThat(reloaded.getDateLastPrint()).isEqualTo(massPrintDate);
		assertThat(reloaded.getC_BPartner_ID()).isEqualTo(partnerB.getC_BPartner_ID());
		assertThat(reloaded.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());
	}

	private I_C_BPartner partner(@NonNull final String name)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName(name);
		saveRecord(partner);
		return partner;
	}

	private I_C_Order order(@NonNull final DocStatus docStatus)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(partnerB.getC_BPartner_ID());
		order.setDocStatus(docStatus.getCode());
		order.setC_DocType_ID(newDocType.getC_DocType_ID());
		order.setDateOrdered(TimeUtil.asTimestamp(NEW_DATE_ORDERED));
		order.setPOReference("newPOReference");
		// the unit-test IDocumentBL (PlainDocumentBL) views the record as an IDocument proxy whose getDocumentDate() reads a "DocumentDate" value;
		// in production MOrder.getDocumentDate() returns DateOrdered
		POJOWrapper.getWrapper(order).setValue("DocumentDate", NEW_DATE_ORDERED);
		saveRecord(order);
		return order;
	}

	/**
	 * A log as written when the document's first PDF was archived (old partner, reference, ...), already printed and mailed.
	 */
	private I_C_Doc_Outbound_Log existingLog(@NonNull final Object document, @NonNull final DocStatus headerDocStatus)
	{
		final I_C_Doc_Outbound_Log log = newInstance(I_C_Doc_Outbound_Log.class);
		log.setAD_Org_ID(LOG_ORG_ID);
		log.setAD_Table_ID(InterfaceWrapperHelper.getModelTableId(document));
		log.setRecord_ID(InterfaceWrapperHelper.getId(document));
		log.setC_BPartner_ID(partnerA.getC_BPartner_ID());
		log.setDocStatus(headerDocStatus.getCode());
		log.setPOReference("oldPOReference");
		log.setDocumentNo("oldDocumentNo");
		log.setFileName("oldFileName.pdf");
		log.setC_DocType_ID(-1);
		log.setDateDoc(OLD_DATE_DOC);
		log.setDateLastPrint(DATE_LAST_PRINT);
		log.setDateLastEMail(DATE_LAST_EMAIL);
		log.setC_Async_Batch_ID(ASYNC_BATCH_ID);
		saveRecord(log);
		return log;
	}

	private I_AD_Archive archive(@NonNull final Object document, final int bpartnerId)
	{
		final I_AD_Archive archive = newInstance(I_AD_Archive.class);
		archive.setAD_Table_ID(InterfaceWrapperHelper.getModelTableId(document));
		archive.setRecord_ID(InterfaceWrapperHelper.getId(document));
		archive.setC_BPartner_ID(bpartnerId);
		archive.setPOReference("newPOReference");
		archive.setDocumentNo("newDocumentNo");
		archive.setName("newFileName.pdf");
		archive.setIsReport(false);
		saveRecord(archive);
		return archive;
	}

	private static I_C_Doc_Outbound_Log reload(@NonNull final I_C_Doc_Outbound_Log log)
	{
		return load(log.getC_Doc_Outbound_Log_ID(), I_C_Doc_Outbound_Log.class);
	}

	private static List<I_C_Doc_Outbound_Log> allLogs()
	{
		return POJOLookupMap.get().getRecords(I_C_Doc_Outbound_Log.class);
	}
}

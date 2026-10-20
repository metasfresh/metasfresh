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

import com.google.common.annotations.VisibleForTesting;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipients;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRegistry;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRequest;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.engine.IDocumentBL;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Properties;

/**
 * Populates the header fields of a {@link I_C_Doc_Outbound_Log} that are derived from its document and from a document PDF ({@link I_AD_Archive}).
 * <p>
 * Used when the outbound log is created from the document's first PDF, and when the header is refreshed from a later document PDF,
 * so that a refreshed header carries the same values as a newly created one.
 * <p>
 * None of the methods saves the given log record.
 */
@Component
public class DocOutboundLogHeaderPopulator
{
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final DocOutboundLogMailRecipientRegistry docOutboundLogMailRecipientRegistry;

	@VisibleForTesting
	public static DocOutboundLogHeaderPopulator newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				DocOutboundLogHeaderPopulator.class,
				() -> new DocOutboundLogHeaderPopulator(DocOutboundLogMailRecipientRegistry.newInstanceForUnitTesting()));
	}

	public DocOutboundLogHeaderPopulator(@NonNull final DocOutboundLogMailRecipientRegistry docOutboundLogMailRecipientRegistry)
	{
		this.docOutboundLogMailRecipientRegistry = docOutboundLogMailRecipientRegistry;
	}

	/**
	 * Sets the document fields of the given log from the given document PDF and from the document it references:
	 * {@code POReference}, {@code C_DocType_ID}, {@code DocumentNo}, {@code FileName} and {@code DateDoc}.
	 * <p>
	 * {@code DateDoc} is the document's date; if the document has none, it falls back to the log's {@code Created} (which is not yet set on a new, unsaved log).
	 * <p>
	 * Does not touch the business partner, the status, the recipients, the print / mail dates, the org, the async batch or the record reference.
	 *
	 * @param docOutboundLogRecord the log to populate; not saved
	 * @param archiveRecord        the document PDF; its {@code AD_Table_ID} / {@code Record_ID} identify the document
	 */
	public void populateDocumentFields(
			@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord,
			@NonNull final I_AD_Archive archiveRecord)
	{
		final TableRecordReference reference = TableRecordReference.ofReferenced(archiveRecord);
		final int adTableId = reference.getAD_Table_ID();
		final int recordId = reference.getRecord_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(archiveRecord);

		docOutboundLogRecord.setPOReference(archiveRecord.getPOReference());

		final int doctypeID = documentBL.getC_DocType_ID(ctx, adTableId, recordId);
		docOutboundLogRecord.setC_DocType_ID(doctypeID);

		docOutboundLogRecord.setDocumentNo(archiveRecord.getDocumentNo());
		docOutboundLogRecord.setFileName(archiveRecord.getName());

		final LocalDate documentDate = CoalesceUtil.coalesce(
				documentBL.getDocumentDate(ctx, adTableId, recordId),
				TimeUtil.asLocalDate(docOutboundLogRecord.getCreated()));

		docOutboundLogRecord.setDateDoc(TimeUtil.asTimestamp(documentDate)); // task 08905: Also set the the documentDate
	}

	/**
	 * Sets the e-mail recipient fields of the given log ({@code CurrentEMailRecipient_ID}, {@code CurrentEMailAddress},
	 * {@code CurrentEMailCCRecipient_ID}, {@code CurrentEMailAddressCC}) and {@code IsInvoiceEmailEnabled}
	 * from the recipient that the registered {@link de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientProvider} yields for the log's document.
	 * <p>
	 * Requires the log's {@code AD_Table_ID}, {@code Record_ID}, {@code AD_Client_ID}, {@code AD_Org_ID} and {@code C_DocType_ID} to be set.
	 * If the provider yields no recipient, the log is left unchanged.
	 *
	 * @param docOutboundLogRecord the log to populate; not saved
	 */
	public void populateRecipients(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final DocOutboundLogMailRecipientRequest recipientRequest = DocOutboundLogMailRecipientRequest.builder()
				.recordRef(TableRecordReference.ofOrNull(docOutboundLogRecord.getAD_Table_ID(), docOutboundLogRecord.getRecord_ID()))
				.clientId(ClientId.ofRepoId(docOutboundLogRecord.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(docOutboundLogRecord.getAD_Org_ID()))
				.docTypeId(DocTypeId.ofRepoIdOrNull(docOutboundLogRecord.getC_DocType_ID()))
				.build();

		docOutboundLogMailRecipientRegistry.getRecipient(recipientRequest)
				.ifPresent(recipients -> updateRecordWithRecipient(docOutboundLogRecord, recipients));
	}

	private void updateRecordWithRecipient(
			@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord,
			@NonNull final DocOutBoundRecipients recipients)
	{
		final String tableName = TableRecordReference.ofReferenced(docOutboundLogRecord).getTableName();
		final boolean isInvoiceEmailEnabled = I_C_Invoice.Table_Name.equals(tableName) && recipients.isInvoiceAsEmail();
		docOutboundLogRecord.setIsInvoiceEmailEnabled(isInvoiceEmailEnabled);

		if (recipients.getTo() != null)
		{
			// an email-only recipient (e.g. the bill-to location fallback) has no AD_User => toRepoId returns -1
			docOutboundLogRecord.setCurrentEMailRecipient_ID(DocOutBoundRecipientId.toRepoId(recipients.getTo().getId()));
			docOutboundLogRecord.setCurrentEMailAddress(recipients.getTo().getEmailAddress());
		}

		if (recipients.getCc() != null)
		{
			docOutboundLogRecord.setCurrentEMailCCRecipient_ID(recipients.getCc().getId().getRepoId());
			docOutboundLogRecord.setCurrentEMailAddressCC(recipients.getCc().getEmailAddress());
		}
	}
}

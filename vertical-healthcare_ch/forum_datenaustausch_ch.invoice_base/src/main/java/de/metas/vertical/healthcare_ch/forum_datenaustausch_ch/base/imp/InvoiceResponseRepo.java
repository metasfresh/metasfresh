package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Repository;

import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.invoice_gateway.spi.model.InvoiceId;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class InvoiceResponseRepo
{
	private static final String MSG_INVOICE_NOT_FOUND_2P = "de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.InvoiceResponseRepo_Invoice_Not_Found";
	private final AttachmentEntryService attachmentEntryService;

	public InvoiceResponseRepo(@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
	}

	public InvoiceId save(@NonNull final ImportedInvoiceResponse importedInvoiceResponse)
	{
		final I_C_Invoice invoiceRecord;
		if (importedInvoiceResponse.getId() == null)
		{
			invoiceRecord = retrieveInvoiceRecord(importedInvoiceResponse);
		}
		else
		{
			invoiceRecord = load(importedInvoiceResponse.getId(), I_C_Invoice.class);
		}

		updateInvoiceRecord(importedInvoiceResponse, invoiceRecord);

		attachFileToInvoiceRecord(importedInvoiceResponse, invoiceRecord);

		return InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());
	}

	private I_C_Invoice retrieveInvoiceRecord(@NonNull final ImportedInvoiceResponse response)
	{
		final I_C_Invoice invoiceRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice.COLUMN_DocumentNo, response.getDocumentNumber())
				.addEqualsFilter(I_C_Invoice.COLUMN_Created, response.getInvoiceCreated())
				.create()
				.setApplyAccessFilterRW(true)
				.firstOnly(I_C_Invoice.class);

		if (invoiceRecord != null)
		{
			return invoiceRecord;
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final ITranslatableString message = msgBL
				.getTranslatableMsgText(MSG_INVOICE_NOT_FOUND_2P,
						response.getDocumentNumber(),
						response.getInvoiceCreated().getEpochSecond());

		throw new InvoiceResponseRepoException(message)
				.markAsUserValidationError();

	}

	private void updateInvoiceRecord(@NonNull final ImportedInvoiceResponse response,
			@NonNull final I_C_Invoice invoiceRecord)
	{
		if (ImportedInvoiceResponse.Status.REJECTED.equals(response.getStatus()))
		{
			invoiceRecord.setIsInDispute(true);
		}
		else
		{
			invoiceRecord.setIsInDispute(false);
		}
		saveRecord(invoiceRecord);
	}

	private void attachFileToInvoiceRecord(
			@NonNull final ImportedInvoiceResponse response,
			@NonNull final I_C_Invoice invoiceRecord)
	{
		final AttachmentEntryCreateRequest attachmentEntryCreateRequest = AttachmentEntryCreateRequest
				.builderFromByteArray(response.getRequest().getFileName(), response.getRequest().getData())
				.tags(response.getAdditionalTags())
				.build();

		attachmentEntryService.createNewAttachment(invoiceRecord, attachmentEntryCreateRequest);
	}

	public static final class InvoiceResponseRepoException extends AdempiereException
	{
		private static final long serialVersionUID = -4024895067979792864L;

		public InvoiceResponseRepoException(@NonNull final ITranslatableString message)
		{
			super(message);
		}
	}
}

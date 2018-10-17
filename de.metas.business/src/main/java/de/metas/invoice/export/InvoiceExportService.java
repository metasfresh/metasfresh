package de.metas.invoice.export;

import lombok.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.attachments.AttachmentConstants;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.invoice_gateway.api.InvoiceExportServiceRegistry;
import de.metas.invoice_gateway.spi.InvoiceExportClient;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.invoice_gateway.spi.model.InvoiceExportResult;
import de.metas.invoice_gateway.spi.model.InvoiceId;
import de.metas.invoice_gateway.spi.model.InvoiceToExport;
import de.metas.util.StringUtils;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class InvoiceExportService
{
	private final InvoiceToExportFactory exportInvoiceRepository;

	private final InvoiceExportServiceRegistry invoiceExportServiceRegistry;

	private final AttachmentEntryService attachmentEntryService;

	private InvoiceExportService(
			@NonNull final InvoiceToExportFactory exportInvoiceRepository,
			@NonNull final InvoiceExportServiceRegistry invoiceExportServiceRegistry,
			@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.exportInvoiceRepository = exportInvoiceRepository;
		this.invoiceExportServiceRegistry = invoiceExportServiceRegistry;
		this.attachmentEntryService = attachmentEntryService;
	}

	public void exportInvoices(@NonNull final ImmutableList<InvoiceId> invoiceIdsToExport)
	{
		for (final InvoiceId invoiceIdToExport : invoiceIdsToExport)
		{
			final InvoiceToExport invoiceToExport = exportInvoiceRepository.getById(invoiceIdToExport);
			exportInvoice(invoiceToExport);
		}
	}

	private void exportInvoice(@NonNull final InvoiceToExport invoiceToExport)
	{
		final List<InvoiceExportClient> exportClients = invoiceExportServiceRegistry.createExportClients(invoiceToExport);

		final List<AttachmentEntryCreateRequest> attachmentEntryCreateRequests = new ArrayList<>();
		for (final InvoiceExportClient exportClient : exportClients)
		{
			final List<InvoiceExportResult> exportResults = exportClient.export(invoiceToExport);
			for (final InvoiceExportResult exportResult : exportResults)
			{
				attachmentEntryCreateRequests.add(createAttachmentRequest(exportResult));
			}
		}

		for (final AttachmentEntryCreateRequest attachmentEntryCreateRequest : attachmentEntryCreateRequests)
		{
			attachmentEntryService.createNewAttachment(
					TableRecordReference.of(I_C_Invoice.Table_Name, invoiceToExport.getId()),
					attachmentEntryCreateRequest);
		}
	}

	private AttachmentEntryCreateRequest createAttachmentRequest(@NonNull final InvoiceExportResult exportResult)
	{
		byte[] byteArrayData;
		try
		{
			byteArrayData = ByteStreams.toByteArray(exportResult.getData());
		}
		catch (IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		final AttachmentEntryCreateRequest attachmentEntryCreateRequest = AttachmentEntryCreateRequest
				.builderFromByteArray(
						exportResult.getFileName(),
						byteArrayData)
				.tag(AttachmentConstants.TAGNAME_IS_DOCUMENT, StringUtils.ofBoolean(true))
				.tag(AttachmentConstants.TAGNAME_BPARTNER_RECIPIENT_ID, Integer.toString(exportResult.getRecipientId().getRepoId()))
				.tag(InvoiceExportClientFactory.ATTATCHMENT_TAGNAME_EXPORT_PROVIDER, exportResult.getInvoiceExportProviderId())
				.build();
		return attachmentEntryCreateRequest;
	}
}

package de.metas.invoice.export;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.invoice_gateway.api.InvoiceExportServiceRegistry;
import de.metas.invoice_gateway.spi.InvoiceExportClient;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.invoice_gateway.spi.model.InvoiceExportResult;
import de.metas.invoice_gateway.spi.model.InvoiceId;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.StringUtils;
import lombok.NonNull;

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
	private static final Logger logger = LogManager.getLogger(InvoiceExportService.class);

	private final InvoiceToExportFactory invoiceToExportFactory;

	private final InvoiceExportServiceRegistry invoiceExportServiceRegistry;

	private final AttachmentEntryService attachmentEntryService;

	private InvoiceExportService(
			@NonNull final InvoiceToExportFactory invoiceToExportFactory,
			@NonNull final InvoiceExportServiceRegistry invoiceExportServiceRegistry,
			@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.invoiceToExportFactory = invoiceToExportFactory;
		this.invoiceExportServiceRegistry = invoiceExportServiceRegistry;
		this.attachmentEntryService = attachmentEntryService;
	}

	public void exportInvoices(@NonNull final ImmutableList<InvoiceId> invoiceIdsToExport)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		for (final InvoiceId invoiceIdToExport : invoiceIdsToExport)
		{
			try (final MDCCloseable invoiceMDC = TableRecordMDC.putTableRecordReference(I_C_Invoice.Table_Name, invoiceIdToExport))
			{
				final Optional<InvoiceToExport> invoiceToExport = invoiceToExportFactory.getCreateForId(invoiceIdToExport);
				if (!invoiceToExport.isPresent())
				{
					loggable.addLog("InvoiceExportService - invoiceToExportFactory did not create an exportable representation for the invoice with InvoiceId={}; skipping.", invoiceIdToExport);
					continue;
				}
				exportInvoice(invoiceToExport.get());
			}
		}
	}

	private void exportInvoice(@NonNull final InvoiceToExport invoiceToExport)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final List<InvoiceExportClient> exportClients = invoiceExportServiceRegistry.createExportClients(invoiceToExport);
		if (exportClients.isEmpty())
		{
			loggable.addLog("InvoiceExportService - Found no InvoiceExportClient implementors for invoiceId={}; invoiceToExport={}", invoiceToExport.getId(), invoiceToExport);
			return; // nothing more to do
		}

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
			loggable.addLog("InvoiceExportService - Attached export data to invoiceId={}; attachment={}", invoiceToExport.getId(), attachmentEntryCreateRequest);
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
		final AttachmentTags attachmentTag = AttachmentTags.builder()
				.tag(AttachmentTags.TAGNAME_IS_DOCUMENT, StringUtils.ofBoolean(true)) // other than the "input" xml with was more or less just a template, this is a document
				.tag(AttachmentTags.TAGNAME_BPARTNER_RECIPIENT_ID, Integer.toString(exportResult.getRecipientId().getRepoId()))
				.tag(InvoiceExportClientFactory.ATTACHMENT_TAGNAME_EXPORT_PROVIDER, exportResult.getInvoiceExportProviderId())
				.build();
		final AttachmentEntryCreateRequest attachmentEntryCreateRequest = AttachmentEntryCreateRequest
				.builderFromByteArray(
						exportResult.getFileName(),
						byteArrayData)
				.tags(attachmentTag)
				.build();
		return attachmentEntryCreateRequest;
	}
}

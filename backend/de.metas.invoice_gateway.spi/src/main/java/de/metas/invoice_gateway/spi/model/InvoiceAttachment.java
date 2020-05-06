package de.metas.invoice_gateway.spi.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;

/*
 * #%L
 * metasfresh-invoice_gateway.spi
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

@Value
public class InvoiceAttachment
{
	String fileName;

	String mimeType;

	byte[] data;

	/**
	 * String that identifies the export client factory in charge for this attachment.
	 * See {@link InvoiceExportClientFactory#getInvoiceExportProviderId()}.
	 *
	 * Note: *currently*, only invoices with an attachment that has a providerId can be exported,
	 * but that's just due to the very first ExportClient implementation.
	 */
	String invoiceExportProviderId;

	boolean primaryAttachment;

	public InputStream getDataAsInputStream()
	{
		return new ByteArrayInputStream(getData());
	}

	@Builder
	private InvoiceAttachment(
			@NonNull String fileName,
			@NonNull String mimeType,
			@NonNull byte[] data,
			@Nullable String invoiceExportProviderId,
			@NonNull Boolean primaryAttachment)
	{
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.data = data;
		this.invoiceExportProviderId = invoiceExportProviderId;
		this.primaryAttachment = primaryAttachment;
	}

}

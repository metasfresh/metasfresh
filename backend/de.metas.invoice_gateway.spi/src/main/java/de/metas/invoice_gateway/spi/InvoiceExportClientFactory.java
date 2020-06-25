package de.metas.invoice_gateway.spi;

import java.util.Optional;

import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;

/*
 * #%L
 * metasfresh-invoice.gateway.commons
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

/** SPI to extend for different formats and protocols. */
public interface InvoiceExportClientFactory
{
	String ATTACHMENT_TAGNAME_EXPORT_PROVIDER = "InvoiceExportProviderId";

	String ATTACHMENT_TAGNAME_EXTERNAL_REFERENCE = "ExternalReference";

	String ATTACHMENT_TAGNAME_BELONGS_TO_EXTERNAL_REFERENCE = "BelongsToExternalReference";

	String getInvoiceExportProviderId();

	/** @return empty if the given factory can't provide an export client */
	Optional<InvoiceExportClient> newClientForInvoice(InvoiceToExport invoice);
}

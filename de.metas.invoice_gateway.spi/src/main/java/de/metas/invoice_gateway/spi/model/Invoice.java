package de.metas.invoice_gateway.spi.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import de.metas.invoice_gateway.spi.CustomInvoicePayload;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

@Value
@Builder
public class Invoice
{
	@NonNull
	LocalDate invoiceDate;

	@NonNull
	Instant invoiceTimestamp;

	@NonNull
	String documentNumber;

	/** partner from whom the invoice originates (who shall get the money). */
	@NonNull
	BPartner biller;

	@NonNull
	BPartner recipient;

	boolean isReversal;

	/** {@code true} is this invoice is not send for the first time. */
	boolean copy;

	List<CustomInvoicePayload> customInvoicePayloads;

	@NonNull
	Money amount;

	@NonNull
	Money alreadyPaidAmount;

	@Singular
	List<InvoiceTax> invoiceTaxes;

	@Singular
	List<InvoiceLine> invoiceLines;

	@Singular
	List<InvoiceAttachment> invoiceAttachments;

}

package de.metas.invoice_gateway.spi.model.imp;

import de.metas.invoice_gateway.spi.model.InvoiceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/*
 * #%L
 * metasfresh-invoice_gateway.spi
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

/**
 * Contains the response from a 3rd party that reacted to an invoice with we send from metasfresh.
 */
@Value
@Builder(toBuilder = true)
public class ImportedInvoiceResponse
{
	public enum Status
	{
		ACCEPTET, PENDING, REJECTED
	}

	@Nullable
	InvoiceId invoiceId;

	@NonNull
	String documentNumber; 		// invoiceNumber

	@NonNull
	Instant invoiceCreated;

	Status status;

	ImportInvoiceResponseRequest request;

	@Singular
	Map<String, String> additionalTags;

	String client;

	String invoiceRecipient;

	List<RejectedError> reason;

	String explanation;

	String responsiblePerson;

	String phone;

	String email;

	String billerEan;

	int billerOrg;

	@Value
	public static class RejectedError
	{
		@NonNull
		String code;

		@NonNull
		String text;

		@Override public String toString()
		{
			return code + ": " + text + ";";
		}
	}
}

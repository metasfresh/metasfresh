package de.metas.invoice_gateway.spi.esr.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import de.metas.invoice_gateway.spi.CustomInvoicePayload;
import de.metas.invoice_gateway.spi.model.AddressInfo;
import de.metas.invoice_gateway.spi.model.PersonInfo;

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
@Builder
public class ESRPaymentInfo implements CustomInvoicePayload
{
	@NonNull
	String participantNumber;

	@NonNull
	String referenceNumber;

	@NonNull
	String codingLine;

	@Nullable
	String companyName;

	@Nullable
	PersonInfo personInfo;

	@Nullable
	AddressInfo addressInfo;

	private ESRPaymentInfo(
			@NonNull final String participantNumber,
			@NonNull final String referenceNumber,
			@NonNull final String codingLine,
			@Nullable final String companyName,
			@Nullable final PersonInfo personInfo,
			@Nullable final AddressInfo addressInfo)
	{
		this.participantNumber = participantNumber;
		this.referenceNumber = referenceNumber;
		this.codingLine = codingLine;
		this.companyName = companyName;
		this.personInfo = personInfo;
		this.addressInfo = addressInfo;

// take whatever infos we can get, and let the consumers decide what to do with it
//		assume(isEmpty(companyName) || addressInfo != null,
//				"If a companyName is given, then an address needs to be given as well; this={}", this);
//		assume(isEmpty(personInfo) || addressInfo != null,
//				"If a personInfo is given, then an address needs to be given as well; this={}", this);
	}

}

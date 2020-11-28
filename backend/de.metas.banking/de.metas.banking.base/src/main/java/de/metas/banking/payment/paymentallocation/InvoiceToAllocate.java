package de.metas.banking.payment.paymentallocation;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.banking.base
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

@Value
@Builder
public class InvoiceToAllocate
{
	// invoiceId or preparyOrderId shall non null
	@Nullable
	InvoiceId invoiceId;
	@Nullable
	OrderId prepayOrderId;

	@NonNull
	ClientAndOrgId clientAndOrgId;

	@NonNull
	String documentNo;

	@NonNull
	BPartnerId bpartnerId;
	@NonNull
	String bpartnerName;

	@NonNull
	LocalDate dateInvoiced;
	@NonNull
	LocalDate dateAcct;

	@NonNull
	CurrencyCode documentCurrencyCode;

	/** Date used to calculate the currency conversion and discount */
	@NonNull
	ZonedDateTime evaluationDate;

	@NonNull
	Amount grandTotal;
	@NonNull
	Amount grandTotalConverted;
	@NonNull
	Amount openAmountConverted;
	@NonNull
	Amount discountAmountConverted;

	@NonNull
	DocTypeId docTypeId;
	@NonNull
	InvoiceDocBaseType docBaseType;

	@Nullable
	String poReference;

	@Nullable
	CurrencyConversionTypeId currencyConversionTypeId;
}

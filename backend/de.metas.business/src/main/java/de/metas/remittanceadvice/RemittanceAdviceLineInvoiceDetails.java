/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.remittanceadvice;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
public class RemittanceAdviceLineInvoiceDetails
{
	@NonNull
	InvoiceId invoiceId;

	@NonNull
	BPartnerId billBPartnerId;

	@NonNull
	BigDecimal invoiceAmt;

	@NonNull
	CurrencyId invoiceCurrencyId;

	@NonNull
	Amount invoiceAmtInREMADVCurrency;

	@NonNull
	Amount overUnderAmtInREMADVCurrency;

	@NonNull
	String invoiceDocType;

	@NonNull
	Instant invoiceDate;

	@Builder
	public RemittanceAdviceLineInvoiceDetails(@NonNull final InvoiceId invoiceId,
			@NonNull final BPartnerId billBPartnerId,
			@NonNull final BigDecimal invoiceAmt,
			@NonNull final CurrencyId invoiceCurrencyId,
			@NonNull final Amount invoiceAmtInREMADVCurrency,
			@NonNull final Amount overUnderAmtInREMADVCurrency,
			@NonNull final String invoiceDocType,
			@NonNull final Instant invoiceDate)
	{
		Amount.assertSameCurrency(invoiceAmtInREMADVCurrency, overUnderAmtInREMADVCurrency);

		this.invoiceId = invoiceId;
		this.billBPartnerId = billBPartnerId;
		this.invoiceAmt = invoiceAmt;
		this.invoiceCurrencyId = invoiceCurrencyId;
		this.invoiceAmtInREMADVCurrency = invoiceAmtInREMADVCurrency;
		this.overUnderAmtInREMADVCurrency = overUnderAmtInREMADVCurrency;
		this.invoiceDocType = invoiceDocType;
		this.invoiceDate = invoiceDate;
	}
}

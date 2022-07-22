/*
 * #%L
 * de.metas.banking.base
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

package de.metas.banking.payment.paymentallocation;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.Amount;
import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.invoice.InvoiceId;
import de.metas.lang.SOTrx;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;

@Value
public class PaymentAllocationPayableItem
{
	InvoiceAmtMultiplier amtMultiplier;
	Amount openAmt;
	Amount payAmt;
	Amount discountAmt;
	Amount serviceFeeAmt;
	ClientAndOrgId clientAndOrgId;
	Instant paymentDate;

	/**
	 * Payment-BPartner
	 */
	BPartnerId bPartnerId;

	InvoiceId invoiceId;
	BPartnerId invoiceBPartnerId;
	
	boolean invoiceIsCreditMemo;
	
	String documentNo;

	/**
	 * This property is not about the invoice, but basically about the payment.
	 */
	SOTrx soTrx;
	
	LocalDate dateInvoiced;

	@Builder
	private PaymentAllocationPayableItem(
			@NonNull final InvoiceAmtMultiplier amtMultiplier,
			@NonNull final Amount openAmt,
			@NonNull final Amount payAmt,
			@NonNull final Amount discountAmt,
			@Nullable final Amount serviceFeeAmt,
			@NonNull final OrgId orgId,
			@NonNull final ClientId clientId,
			@Nullable final Instant paymentDate,
			@NonNull final BPartnerId bPartnerId,
			@NonNull final InvoiceId invoiceId,
			@NonNull final BPartnerId invoiceBPartnerId,
			final boolean invoiceIsCreditMemo, 
			@NonNull final String documentNo,
			@NonNull final SOTrx soTrx,
			@NonNull final LocalDate dateInvoiced)
	{
		this.amtMultiplier = amtMultiplier;
		this.openAmt = openAmt;
		this.payAmt = payAmt;
		this.discountAmt = discountAmt;
		this.serviceFeeAmt = serviceFeeAmt;
		this.invoiceIsCreditMemo = invoiceIsCreditMemo;
		this.clientAndOrgId = ClientAndOrgId.ofClientAndOrg(clientId, orgId);
		this.paymentDate = CoalesceUtil.coalesce(paymentDate, Instant::now);
		this.bPartnerId = bPartnerId;
		this.invoiceId = invoiceId;
		this.invoiceBPartnerId = invoiceBPartnerId;
		this.documentNo = documentNo;
		this.soTrx = soTrx;
		this.dateInvoiced = dateInvoiced;
	}
}

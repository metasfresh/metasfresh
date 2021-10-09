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
import de.metas.currency.Amount;
import de.metas.invoice.InvoiceId;
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
	Amount openAmt;
	Amount payAmt;
	Amount discountAmt;
	Amount serviceFeeAmt;
	ClientAndOrgId clientAndOrgId;
	Instant paymentDate;
	
	/** Payment-BPartner */
	BPartnerId bPartnerId;
	
	InvoiceId invoiceId;
	BPartnerId invoiceBPartnerId;
	
	String documentNo;
	boolean isSOTrx;
	LocalDate dateInvoiced;

	@Builder
	private PaymentAllocationPayableItem(
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
			@NonNull final String documentNo,
			final boolean isSOTrx,
			@NonNull final LocalDate dateInvoiced)
	{
		this.openAmt = openAmt;
		this.payAmt = payAmt;
		this.discountAmt = discountAmt;
		this.serviceFeeAmt = serviceFeeAmt;
		this.clientAndOrgId = ClientAndOrgId.ofClientAndOrg(clientId, orgId);
		this.paymentDate = paymentDate == null ? Instant.now() : paymentDate;
		this.bPartnerId = bPartnerId;
		this.invoiceId = invoiceId;
		this.invoiceBPartnerId = invoiceBPartnerId;
		this.documentNo = documentNo;
		this.isSOTrx = isSOTrx;
		this.dateInvoiced = dateInvoiced;
	}
}

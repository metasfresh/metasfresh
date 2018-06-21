package de.metas.contracts.refund;

import java.time.LocalDate;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
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
public class AssignableInvoiceCandidate implements InvoiceCandidate
{
	public static AssignableInvoiceCandidate cast(@NonNull final Object invoiceCandidate)
	{
		return (AssignableInvoiceCandidate)invoiceCandidate;
	}

	InvoiceCandidateId id;
	BPartnerId bpartnerId;
	ProductId productId;
	LocalDate invoiceableFrom;
	Money money;

	AssignmentToRefundCandidate assignmentToRefundCandidate;

	@Builder(toBuilder = true)
	private AssignableInvoiceCandidate(
			@NonNull final InvoiceCandidateId id,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final ProductId productId,
			@NonNull final LocalDate invoiceableFrom,
			@NonNull final Money money,
			@Nullable final AssignmentToRefundCandidate assignmentToRefundCandidate)
	{
		this.id = id;
		this.bpartnerId = bpartnerId;
		this.productId = productId;
		this.invoiceableFrom = invoiceableFrom;
		this.money = money;

		this.assignmentToRefundCandidate = assignmentToRefundCandidate;
	}

	public AssignableInvoiceCandidate withoutRefundInvoiceCandidate()
	{
		return toBuilder()
				.assignmentToRefundCandidate(null)
				.build();
	}

	public boolean isAssigned()
	{
		return assignmentToRefundCandidate != null;
	}
}

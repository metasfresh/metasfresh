package de.metas.contracts.refund;

import java.time.LocalDate;

import org.adempiere.bpartner.BPartnerId;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.Money;
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
@Builder(toBuilder = true)
public class RefundInvoiceCandidate implements InvoiceCandidate
{
	public static RefundInvoiceCandidate cast(@NonNull final InvoiceCandidate refundInvoiceCandidate)
	{
		return (RefundInvoiceCandidate)refundInvoiceCandidate;
	}

	@NonNull
	InvoiceCandidateId id;

	@NonNull
	BPartnerId bpartnerId;

	@NonNull
	LocalDate invoiceableFrom;

	@NonNull
	RefundContract refundContract;

	@NonNull
	Money money;

	public AssignmentToRefundCandidate withAddedMoneyAmount(
			@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate)
	{
		final Money augend = assignableInvoiceCandidate
				.getMoney()
				.percentage(refundContract.getRefundConfig().getPercent());

		final RefundInvoiceCandidate updatedRefundCandidate = toBuilder()
				.money(money.add(augend))
				.build();

		return new AssignmentToRefundCandidate(updatedRefundCandidate, augend);
	}
}

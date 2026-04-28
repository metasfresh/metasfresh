/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.paymentschedule;

import de.metas.currency.CurrencyPrecision;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.ReferenceDateType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

@Builder
@Getter
public class OrderSchedulingContext
{
	@NonNull OrderId orderId;
	@Nullable LocalDate orderDate;
	@Nullable LocalDate letterOfCreditDate;
	@Nullable LocalDate billOfLadingDate;
	@Nullable LocalDate ETADate;
	@Nullable LocalDate invoiceDate;
	@NonNull Money grandTotal;
	@NonNull CurrencyPrecision precision;
	@NonNull PaymentTerm paymentTerm;

	public DueDateAndStatus computeDueDate(@NonNull final PaymentTermBreak termBreak)
	{
		final LocalDate referenceDate = getAvailableReferenceDate(termBreak.getReferenceDateType());
		if (referenceDate != null)
		{
			final LocalDate dueDate = referenceDate.plusDays(termBreak.getOffsetDays());
			return DueDateAndStatus.awaitingPayment(dueDate);
		}
		else
		{
			return DueDateAndStatus.pending();
		}
	}

	@Nullable
	private LocalDate getAvailableReferenceDate(@NonNull final ReferenceDateType referenceDateType)
	{
		switch (referenceDateType)
		{
			case OrderDate:
				return getOrderDate();
			case LetterOfCreditDate:
				return getLetterOfCreditDate();
			case BillOfLadingDate:
				return getBillOfLadingDate();
			case ETADate:
				return getETADate();
			case InvoiceDate:
				return getInvoiceDate();
			default:
				return null;
		}
	}
}

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

package de.metas.order.paymentschedule.core;

import com.google.common.collect.ImmutableMap;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

@Getter
public class OrderSchedulingContext
{
	@NonNull private final OrderId orderId;
	@Nullable private final LocalDate orderDate;
	@Nullable private final LocalDate letterOfCreditDate;
	@Nullable private final LocalDate billOfLadingDate;
	@Nullable private final LocalDate ETADate;
	@Nullable private final LocalDate invoiceDate;
	@NonNull private final Money grandTotal;
	@NonNull private final CurrencyPrecision precision;
	@NonNull private final PaymentTerm paymentTerm;

	@Nullable @Getter(AccessLevel.NONE) private ImmutableMap<PaymentTermBreakId, Money> _grandTotalByBreakId;

	@Builder
	private OrderSchedulingContext(
			@NonNull final OrderId orderId,
			@Nullable final LocalDate orderDate,
			@Nullable final LocalDate letterOfCreditDate,
			@Nullable final LocalDate billOfLadingDate,
			@Nullable final LocalDate ETADate,
			@Nullable final LocalDate invoiceDate,
			@NonNull final Money grandTotal,
			@NonNull final CurrencyPrecision precision,
			@NonNull final PaymentTerm paymentTerm)
	{
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.letterOfCreditDate = letterOfCreditDate;
		this.billOfLadingDate = billOfLadingDate;
		this.ETADate = ETADate;
		this.invoiceDate = invoiceDate;
		this.grandTotal = grandTotal;
		this.precision = precision;
		this.paymentTerm = paymentTerm;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isComplexPaymentTerm() {return paymentTerm.isComplex();}

	public CurrencyId getCurrencyId() {return grandTotal.getCurrencyId();}

	public List<PaymentTermBreak> getBreaksBy(final Predicate<PaymentTermBreak> predicate)
	{
		return paymentTerm.getBreaksBy(predicate);
	}

	public OrderPayScheduleLineContext computeLineContext(@NonNull final PaymentTermBreak termBreak)
	{
		final LocalDate referenceDate = getAvailableReferenceDate(termBreak.getReferenceDateType());
		if (referenceDate != null)
		{
			final LocalDate dueDate = referenceDate.plusDays(termBreak.getOffsetDays());
			return OrderPayScheduleLineContext.awaitingPayment()
					.referenceDate(referenceDate)
					.dueDate(dueDate)
					.build();
		}
		else
		{
			return OrderPayScheduleLineContext.pending();
		}
	}

	@Nullable
	public LocalDate getAvailableReferenceDate(@NonNull final ReferenceDateType referenceDateType)
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

	public Money getGrandTotalByBreakId(final PaymentTermBreakId breakId)
	{
		final Money grandTotalPart = getGrandTotalByBreaks().get(breakId);
		if (grandTotalPart == null)
		{
			throw new AdempiereException("No payment term break found for ID: " + breakId);
		}
		return grandTotalPart;
	}

	private ImmutableMap<PaymentTermBreakId, Money> getGrandTotalByBreaks()
	{
		if (_grandTotalByBreakId == null)
		{
			_grandTotalByBreakId = paymentTerm.spreadByBreaks(grandTotal, precision);
		}
		return _grandTotalByBreakId;
	}
}

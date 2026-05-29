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

package de.metas.payment.paymentterm;

import de.metas.currency.CurrencyPrecision;
import de.metas.money.Money;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Value
@Builder
public class PaySchedule
{
	@NonNull PayScheduleId id;
	@NonNull Percent percentage;
	@NonNull Percent discount;

	@Nullable DayOfWeek netDay;
	int netDays;
	int graceDays;
	int discountDays;

	public Money calculateDueAmt(@NonNull final Money grandTotal, @NonNull final CurrencyPrecision precision)
	{
		return grandTotal.multiply(percentage, precision);
	}

	public LocalDate calculateDueDate(@NonNull final LocalDate dateInvoiced)
	{
		return dateInvoiced.plusDays(netDays);
	}

	public Money calculateDiscountAmt(@NonNull final Money dueAmt, @NonNull final CurrencyPrecision precision)
	{
		return dueAmt.multiply(percentage, precision);
	}

	public LocalDate calculateDiscountDate(@NonNull final LocalDate dateInvoiced)
	{
		return dateInvoiced.plusDays(discountDays);
	}
}

package de.metas.invoice;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class InvoiceSchedule
{
	public enum Frequency
	{
		DAILY,

		WEEKLY,

		MONTLY,

		TWICE_MONTHLY;
	}

	InvoiceScheduleId id;

	Frequency frequency;

	/** ignored unless frequency=weekly */
	DayOfWeek invoiceDayOfWeek;

	/** ignored unless frequency=monthly; if bigger than the respective month's last day, then the last day is used instead */
	int invoiceDayOfMonth;

	int invoiceDistance;

	@Builder
	public InvoiceSchedule(
			@NonNull final InvoiceScheduleId id,
			@NonNull final Frequency frequency,
			final int invoiceDistance,
			final DayOfWeek invoiceDayOfWeek,
			final int invoiceDayOfMonth)
	{
		this.id = id;
		this.frequency = frequency;

		if (Frequency.WEEKLY.equals(getFrequency()))
		{
			this.invoiceDayOfWeek = Check.assumeNotNull(invoiceDayOfWeek, "If the invoicing freqency is 'weekly', then invoiceWeekDay may not be null");
		}
		else
		{
			this.invoiceDayOfWeek = null;
		}

		if (Frequency.MONTLY.equals(getFrequency()))
		{
			this.invoiceDayOfMonth = Check.assumeGreaterThanZero(invoiceDayOfMonth, "If the invoicing freqency is 'monthly', then invoiceDayOfMonth needs to be >=1");
		}
		else
		{
			this.invoiceDayOfMonth = -1;
		}

		this.invoiceDistance = invoiceDistance;
	}

	public LocalDate calculateNextDateToInvoice(@NonNull final LocalDate deliveryDate)
	{
		final int offset = Integer.max(getInvoiceDistance() - 1, 0);

		switch (getFrequency())
		{
			case DAILY:
				return computeNextDailyInvoiceDate(deliveryDate, offset);
			case WEEKLY:
				return computeNextWeeklyInvoiceDate(deliveryDate, offset);
			case TWICE_MONTHLY:
				return computeNextTwiceMonthlyInvoiceDate(deliveryDate);
			case MONTLY:
				return computeNextMonthlyInvoiceDate(deliveryDate, offset);
			default:
				throw new AdempiereException(this + " has unsupported frequency '" + getFrequency() + "'");
		}
	}

	private LocalDate computeNextDailyInvoiceDate(@NonNull final LocalDate deliveryDate, final int offset)
	{
		return deliveryDate.plus(offset, ChronoUnit.DAYS);
	}

	private LocalDate computeNextWeeklyInvoiceDate(@NonNull final LocalDate deliveryDate, final int offset)
	{
		final LocalDate dateToInvoice;
		final LocalDate nextDateWithInvoiceWeekDay = deliveryDate.with(nextOrSame(getInvoiceDayOfWeek()));

		if (!nextDateWithInvoiceWeekDay.isAfter(deliveryDate))
		{
			dateToInvoice = nextDateWithInvoiceWeekDay.plus(1 + offset, ChronoUnit.WEEKS);
		}
		else
		{
			dateToInvoice = nextDateWithInvoiceWeekDay.plus(offset, ChronoUnit.WEEKS);
		}
		return dateToInvoice;
	}

	private LocalDate computeNextTwiceMonthlyInvoiceDate(@NonNull final LocalDate deliveryDate)
	{
		final LocalDate dateToInvoice;
		final LocalDate middleDayOfMonth = deliveryDate.withDayOfMonth(15);

		// tasks 08484, 08869
		if (deliveryDate.isAfter(middleDayOfMonth))
		{
			dateToInvoice = deliveryDate.with(lastDayOfMonth());
		}
		else
		{
			dateToInvoice = middleDayOfMonth;
		}
		return dateToInvoice;
	}

	private LocalDate computeNextMonthlyInvoiceDate(@NonNull final LocalDate deliveryDate, final int offset)
	{
		final LocalDate dateToInvoice;
		final int invoiceDayOfMonthToUse = Integer.min(deliveryDate.lengthOfMonth(), getInvoiceDayOfMonth());
		final LocalDate deliveryDateWithDayOfMonth = deliveryDate.withDayOfMonth(invoiceDayOfMonthToUse);

		if (!deliveryDateWithDayOfMonth.isAfter(deliveryDate))
		{
			dateToInvoice = deliveryDateWithDayOfMonth.plus(1 + offset, ChronoUnit.MONTHS);
		}
		else
		{
			dateToInvoice = deliveryDateWithDayOfMonth.plus(offset, ChronoUnit.MONTHS);
		}
		return dateToInvoice;
	}
}

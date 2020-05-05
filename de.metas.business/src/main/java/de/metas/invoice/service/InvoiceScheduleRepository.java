package de.metas.invoice.service;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.DayOfWeek;

import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.X_C_InvoiceSchedule;
import org.springframework.stereotype.Repository;

import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceScheduleId;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.util.Check;
import lombok.NonNull;

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

@Repository
public class InvoiceScheduleRepository
{
	InvoiceSchedule getById(@NonNull final InvoiceScheduleId id)
	{
		final I_C_InvoiceSchedule invoiceScheduleRecord = loadOutOfTrx(id.getRepoId(), I_C_InvoiceSchedule.class);
		return ofRecord(invoiceScheduleRecord);
	}

	public InvoiceSchedule ofRecord(@NonNull final I_C_InvoiceSchedule invoiceScheduleRecord)
	{
		return InvoiceSchedule
				.builder()
				.id(InvoiceScheduleId.ofRepoId(invoiceScheduleRecord.getC_InvoiceSchedule_ID()))
				.frequency(extractFrequency(invoiceScheduleRecord))
				.invoiceDistance(invoiceScheduleRecord.getInvoiceDistance())
				.invoiceDayOfWeek(extractInvoiceWeekDayOrNull(invoiceScheduleRecord))
				.invoiceDayOfMonth(invoiceScheduleRecord.getInvoiceDay())
				.build();
	}

	private Frequency extractFrequency(@NonNull final I_C_InvoiceSchedule invoiceScheduleRecord)
	{
		final String invoiceFrequency = invoiceScheduleRecord.getInvoiceFrequency();

		final Frequency frequency;
		if (X_C_InvoiceSchedule.INVOICEFREQUENCY_Daily.equals(invoiceFrequency))
		{
			frequency = Frequency.DAILY;
		}
		else if (X_C_InvoiceSchedule.INVOICEFREQUENCY_Weekly.equals(invoiceFrequency))
		{
			frequency = Frequency.WEEKLY;
		}
		else if (X_C_InvoiceSchedule.INVOICEFREQUENCY_TwiceMonthly.equals(invoiceFrequency))
		{
			frequency = Frequency.TWICE_MONTHLY;
		}
		else if (X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly.equals(invoiceFrequency))
		{
			frequency = Frequency.MONTLY;
		}
		else
		{
			Check.fail(
					"The given C_InvoiceSchedule record has an supported InvoiceFrequency value; InvoiceFrequency={}; C_InvoiceSchedule={}",
					invoiceFrequency, invoiceScheduleRecord);
			frequency = null;
		}
		return frequency;
	}

	private static DayOfWeek extractInvoiceWeekDayOrNull(@NonNull final I_C_InvoiceSchedule invoiceScheduleRecord)
	{
		final String invoiceWeekDay = invoiceScheduleRecord.getInvoiceWeekDay();
		if (Check.isEmpty(invoiceWeekDay, true))
		{
			return null;
		}

		if (X_C_InvoiceSchedule.INVOICEWEEKDAY_Friday.equals(invoiceWeekDay))
		{
			return DayOfWeek.FRIDAY;
		}
		if (X_C_InvoiceSchedule.INVOICEWEEKDAY_Saturday.equals(invoiceWeekDay))
		{
			return DayOfWeek.SATURDAY;
		}
		if (X_C_InvoiceSchedule.INVOICEWEEKDAY_Sunday.equals(invoiceWeekDay))
		{
			return DayOfWeek.SUNDAY;
		}
		if (X_C_InvoiceSchedule.INVOICEWEEKDAY_Monday.equals(invoiceWeekDay))
		{
			return DayOfWeek.MONDAY;
		}
		if (X_C_InvoiceSchedule.INVOICEWEEKDAY_Tuesday.equals(invoiceWeekDay))
		{
			return DayOfWeek.TUESDAY;
		}
		if (X_C_InvoiceSchedule.INVOICEWEEKDAY_Wednesday.equals(invoiceWeekDay))
		{
			return DayOfWeek.WEDNESDAY;
		}
		if (X_C_InvoiceSchedule.INVOICEWEEKDAY_Thursday.equals(invoiceWeekDay))
		{
			return DayOfWeek.THURSDAY;
		}
		else
		{
			Check.fail(
					"The given C_InvoiceSchedule record has an unsupported WeekDay value; WeekDay={}; C_InvoiceSchedule={}",
					invoiceWeekDay, invoiceScheduleRecord);
			return null;
		}
	}	// getCalendarDay

	public InvoiceSchedule save(@NonNull final InvoiceSchedule invoiceSchedule)
	{
		final I_C_InvoiceSchedule invoiceScheduleRecord;
		if (invoiceSchedule.getId() == null)
		{
			invoiceScheduleRecord = newInstance(I_C_InvoiceSchedule.class);
		}
		else
		{
			invoiceScheduleRecord = load(invoiceSchedule.getId().getRepoId(), I_C_InvoiceSchedule.class);
		}

		switch (invoiceSchedule.getFrequency())
		{
			case DAILY:
				invoiceScheduleRecord.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Daily);
				break;
			case WEEKLY:
				invoiceScheduleRecord.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Weekly);
				setInvoiceWeekDayOfRecord(invoiceSchedule, invoiceScheduleRecord);
				break;
			case MONTLY:
				invoiceScheduleRecord.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
				invoiceScheduleRecord.setInvoiceDay(invoiceSchedule.getInvoiceDayOfMonth());
				break;
			case TWICE_MONTHLY:
				invoiceScheduleRecord.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_TwiceMonthly);
				break;
			default:
				Check.fail("Unsupported frequency={}", invoiceSchedule.getFrequency());
				break;
		}

		invoiceScheduleRecord.setInvoiceDistance(invoiceSchedule.getInvoiceDistance());

		saveRecord(invoiceScheduleRecord);

		return invoiceSchedule
				.toBuilder()
				.id(InvoiceScheduleId.ofRepoId(invoiceScheduleRecord.getC_InvoiceSchedule_ID()))
				.build();
	}

	private void setInvoiceWeekDayOfRecord(
			@NonNull final InvoiceSchedule invoiceSchedule,
			@NonNull final I_C_InvoiceSchedule invoiceScheduleRecord)
	{
		switch (invoiceSchedule.getInvoiceDayOfWeek())
		{
			case MONDAY:
				invoiceScheduleRecord.setInvoiceWeekDay(X_C_InvoiceSchedule.INVOICEWEEKDAY_Monday);
				break;
			case TUESDAY:
				invoiceScheduleRecord.setInvoiceWeekDay(X_C_InvoiceSchedule.INVOICEWEEKDAY_Tuesday);
				break;
			case WEDNESDAY:
				invoiceScheduleRecord.setInvoiceWeekDay(X_C_InvoiceSchedule.INVOICEWEEKDAY_Wednesday);
				break;
			case THURSDAY:
				invoiceScheduleRecord.setInvoiceWeekDay(X_C_InvoiceSchedule.INVOICEWEEKDAY_Thursday);
				break;
			case FRIDAY:
				invoiceScheduleRecord.setInvoiceWeekDay(X_C_InvoiceSchedule.INVOICEWEEKDAY_Friday);
				break;
			case SATURDAY:
				invoiceScheduleRecord.setInvoiceWeekDay(X_C_InvoiceSchedule.INVOICEWEEKDAY_Saturday);
				break;
			case SUNDAY:
				invoiceScheduleRecord.setInvoiceWeekDay(X_C_InvoiceSchedule.INVOICEWEEKDAY_Sunday);
				break;
			default:
				Check.fail("Unexpected day of week");
				break;
		}
	}
}

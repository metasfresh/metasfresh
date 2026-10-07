package de.metas.contracts.refund;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.invoice.InvoiceScheduleId;
import de.metas.util.lang.Percent;

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

public class RefundContractTest
{
	private static final LocalDate NOW = LocalDate.now();

	private final static BigDecimal FOUR = new BigDecimal("4");
	private final static BigDecimal FIVE = new BigDecimal("5");

	private RefundContract refundContract;

	private RefundConfig refundConfig1;

	private RefundConfig refundConfig2;

	private I_C_UOM uomRecord;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final InvoiceSchedule invoiceSchedule = InvoiceSchedule.builder()
				.id(InvoiceScheduleId.ofRepoId(5))
				.frequency(Frequency.MONTLY)
				.invoiceDayOfMonth(1)
				.build();

		refundConfig1 = RefundConfig.builder()
				.conditionsId(ConditionsId.ofRepoId(20))
				.invoiceSchedule(invoiceSchedule)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.minQty(ZERO)
				.percent(Percent.of(20))
				.build();

		refundConfig2 = refundConfig1.toBuilder()
				.minQty(FIVE)
				.percent(Percent.of(30))
				.build();

		refundContract = RefundContract.builder()
				.id(FlatrateTermId.ofRepoId(100))
				.bPartnerId(BPartnerId.ofRepoId(200))
				.startDate(NOW)
				.endDate(NOW.plusDays(10))
				.refundConfig(refundConfig2)
				.refundConfig(refundConfig1)
				.build();

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
	}

	@Test
	public void getRefundConfig_qty_four()
	{
		// invoke the method under test
		final RefundConfig result = refundContract.getRefundConfig(FOUR);

		assertThat(result).isEqualTo(refundConfig1);
	}

	@Test
	public void getRefundConfig_qty_five()
	{
		// invoke the method under test
		final RefundConfig result = refundContract.getRefundConfig(FIVE);

		assertThat(result).isEqualTo(refundConfig2);
	}

	/**
	 * The invoice day 31 means the end of the month, also after a shorter month: June 30 must not shift every following period end to the 30th.
	 */
	@Test
	public void computeNextInvoiceDate_endOfMonthScheduleDoesNotDrift()
	{
		final RefundContract contract = contractWithSchedule(1, LocalDate.of(2026, 6, 1));

		assertThat(contract.computeNextInvoiceDate(LocalDate.of(2026, 6, 10)).getDateToInvoice()).isEqualTo(LocalDate.of(2026, 6, 30));
		assertThat(contract.computeNextInvoiceDate(LocalDate.of(2026, 7, 5)).getDateToInvoice()).isEqualTo(LocalDate.of(2026, 7, 31));
		assertThat(contract.computeNextInvoiceDate(LocalDate.of(2026, 8, 5)).getDateToInvoice()).isEqualTo(LocalDate.of(2026, 8, 31));
		assertThat(contract.computeNextInvoiceDate(LocalDate.of(2026, 10, 5)).getDateToInvoice()).isEqualTo(LocalDate.of(2026, 10, 31));
	}

	@Test
	public void computeNextInvoiceDate_quarterlyEndOfMonthScheduleDoesNotDrift()
	{
		final RefundContract contract = contractWithSchedule(3, LocalDate.of(2026, 7, 1));

		assertThat(contract.computeNextInvoiceDate(LocalDate.of(2026, 7, 15)).getDateToInvoice()).isEqualTo(LocalDate.of(2026, 9, 30));
		assertThat(contract.computeNextInvoiceDate(LocalDate.of(2026, 10, 15)).getDateToInvoice()).isEqualTo(LocalDate.of(2026, 12, 31));
		assertThat(contract.computeNextInvoiceDate(LocalDate.of(2027, 1, 15)).getDateToInvoice()).isEqualTo(LocalDate.of(2027, 3, 31));
	}

	/** monthly-based refund periods are calendar periods: quarters, half-years and years end on 31.03, 30.06, 30.09, 31.12 / 30.06, 31.12 / 31.12 */
	@Test
	public void computeNextInvoiceDate_multiMonthSchedule_startingInAugust()
	{
		assertPeriodEnds(31, 3, "2026-08-01", "2026-09-30", "2026-12-31", "2027-03-31", "2027-06-30");
		assertPeriodEnds(31, 6, "2026-08-01", "2026-12-31", "2027-06-30", "2027-12-31");
		assertPeriodEnds(31, 12, "2026-08-01", "2026-12-31", "2027-12-31");
	}

	@Test
	public void computeNextInvoiceDate_multiMonthSchedule_startingInNovember()
	{
		assertPeriodEnds(31, 3, "2026-11-01", "2026-12-31", "2027-03-31", "2027-06-30");
		assertPeriodEnds(31, 6, "2026-11-01", "2026-12-31", "2027-06-30");
		assertPeriodEnds(31, 12, "2026-11-01", "2026-12-31", "2027-12-31");
	}

	@Test
	public void computeNextInvoiceDate_periodEndInALeapYearFebruary()
	{
		assertPeriodEnds(31, 1, "2028-02-01", "2028-02-29", "2028-03-31");
		assertPeriodEnds(31, 3, "2027-12-01", "2027-12-31", "2028-03-31");
		assertPeriodEnds(31, 12, "2027-03-01", "2027-12-31", "2028-12-31");
	}

	/** the invoice day of the schedule does not move the end of a refund period: it is the calendar period's last day */
	@Test
	public void computeNextInvoiceDate_invoiceDayIsIgnored()
	{
		assertPeriodEnds(15, 1, "2026-08-01", "2026-08-31", "2026-09-30", "2026-10-31");
		assertPeriodEnds(15, 3, "2026-08-01", "2026-09-30", "2026-12-31", "2027-03-31");
	}

	/** a contract starting mid-quarter has a short first period up to the quarter's end, then calendar quarters */
	@Test
	public void calendarPeriods_quarterly_startingMidQuarter()
	{
		assertPeriods(31, 3, "2026-03-15",
				"2026-03-15/2026-03-31", "2026-04-01/2026-06-30", "2026-07-01/2026-09-30", "2026-10-01/2026-12-31", "2027-01-01/2027-03-31");
	}

	@Test
	public void calendarPeriods_halfYearly_startingMidQuarter()
	{
		assertPeriods(31, 6, "2026-03-15", "2026-03-15/2026-06-30", "2026-07-01/2026-12-31", "2027-01-01/2027-06-30");
	}

	@Test
	public void calendarPeriods_yearly_startingMidQuarter()
	{
		assertPeriods(31, 12, "2026-03-15", "2026-03-15/2026-12-31", "2027-01-01/2027-12-31");
	}

	@Test
	public void calendarPeriods_monthly_startingMidMonth()
	{
		assertPeriods(31, 1, "2026-01-20", "2026-01-20/2026-01-31", "2026-02-01/2026-02-28", "2026-03-01/2026-03-31");
	}

	/**
	 * A sale on the last day of a month belongs to the period that ends that day, also with a schedule whose invoice day is the 30th
	 * (like the seeded schedules "quartalsweise", "halbjährlich", "jährlich").
	 */
	@Test
	public void computeNextInvoiceDate_saleOnTheLastDayOfTheCalendarPeriod()
	{
		final RefundContract quarterly = contractWithSchedule(30, 3, LocalDate.of(2026, 1, 1));
		assertThat(quarterly.computeNextInvoiceDate(LocalDate.of(2026, 3, 31)).getDateToInvoice()).isEqualTo(LocalDate.of(2026, 3, 31));
		assertThat(quarterly.computeNextInvoiceDate(LocalDate.of(2026, 12, 31)).getDateToInvoice()).isEqualTo(LocalDate.of(2026, 12, 31));
		assertThat(quarterly.computeNextInvoiceDate(LocalDate.of(2027, 1, 1)).getDateToInvoice()).isEqualTo(LocalDate.of(2027, 3, 31));

		final RefundContract yearly = contractWithSchedule(30, 12, LocalDate.of(2026, 1, 1));
		assertThat(yearly.computeNextInvoiceDate(LocalDate.of(2026, 12, 31)).getDateToInvoice()).isEqualTo(LocalDate.of(2026, 12, 31));
	}

	/** before the contract's start, the first period is the one asked for: it starts with the contract */
	@Test
	public void calendarPeriods_dateBeforeTheStart()
	{
		final RefundContract contract = contractWithSchedule(31, 3, LocalDate.of(2026, 3, 15));
		assertThat(contract.computeNextInvoiceDate(LocalDate.of(2026, 1, 10)).getDateToInvoice()).isEqualTo(LocalDate.of(2026, 3, 31));
		assertThat(contract.computeCurrentPeriodStart(LocalDate.of(2026, 1, 10))).isEqualTo(LocalDate.of(2026, 3, 15));
	}

	/** asserts each period ("start/end") through both its start and its end, and through a day in between */
	private void assertPeriods(final int invoiceDayOfMonth, final int invoiceDistance, final String startDate, final String... expectedPeriods)
	{
		final RefundContract contract = contractWithSchedule(invoiceDayOfMonth, invoiceDistance, LocalDate.parse(startDate));
		for (final String expectedPeriod : expectedPeriods)
		{
			final LocalDate periodStart = LocalDate.parse(expectedPeriod.split("/")[0]);
			final LocalDate periodEnd = LocalDate.parse(expectedPeriod.split("/")[1]);
			for (final LocalDate day : new LocalDate[] { periodStart, periodStart.plusDays((periodEnd.toEpochDay() - periodStart.toEpochDay()) / 2), periodEnd })
			{
				assertThat(contract.computeNextInvoiceDate(day).getDateToInvoice()).as("end of the period containing %s", day).isEqualTo(periodEnd);
				assertThat(contract.computeCurrentPeriodStart(day)).as("start of the period containing %s", day).isEqualTo(periodStart);
			}
		}
	}

	private void assertPeriodEnds(final int invoiceDayOfMonth, final int invoiceDistance, final String startDate, final String... expectedPeriodEnds)
	{
		final RefundContract contract = contractWithSchedule(invoiceDayOfMonth, invoiceDistance, LocalDate.parse(startDate));
		LocalDate dayInPeriod = LocalDate.parse(startDate);
		for (final String expectedPeriodEnd : expectedPeriodEnds)
		{
			final LocalDate periodEnd = LocalDate.parse(expectedPeriodEnd);
			assertThat(contract.computeNextInvoiceDate(dayInPeriod).getDateToInvoice()).as("period containing %s", dayInPeriod).isEqualTo(periodEnd);
			assertThat(contract.computeNextInvoiceDate(periodEnd).getDateToInvoice()).as("the period end itself belongs to its period").isEqualTo(periodEnd);
			dayInPeriod = periodEnd.plusDays(1);
		}
	}

	@Test
	public void computeNextInvoiceDate_dailySchedule()
	{
		final RefundContract contract = contractWithSchedule(Frequency.DAILY, 1, null, LocalDate.of(2026, 8, 1));
		assertThat(contract.computeNextInvoiceDate(LocalDate.of(2026, 8, 5)).getDateToInvoice()).isEqualTo(LocalDate.of(2026, 8, 5));

		final RefundContract everyThirdDay = contractWithSchedule(Frequency.DAILY, 3, null, LocalDate.of(2026, 8, 1));
		final LocalDate periodEnd = everyThirdDay.computeNextInvoiceDate(LocalDate.of(2026, 8, 10)).getDateToInvoice();
		assertThat(periodEnd).isAfterOrEqualTo(LocalDate.of(2026, 8, 10)).isBeforeOrEqualTo(LocalDate.of(2026, 8, 12));
	}

	@Test
	public void computeNextInvoiceDate_twiceMonthlyAndWeeklySchedule()
	{
		final RefundContract twiceMonthly = contractWithSchedule(Frequency.TWICE_MONTHLY, 1, null, LocalDate.of(2026, 8, 1));
		final LocalDate twiceMonthlyEnd = twiceMonthly.computeNextInvoiceDate(LocalDate.of(2026, 9, 20)).getDateToInvoice();
		assertThat(twiceMonthlyEnd).isAfterOrEqualTo(LocalDate.of(2026, 9, 20)).isBeforeOrEqualTo(LocalDate.of(2026, 10, 1));

		final RefundContract weekly = contractWithSchedule(Frequency.WEEKLY, 1, java.time.DayOfWeek.FRIDAY, LocalDate.of(2026, 8, 1));
		final LocalDate weeklyEnd = weekly.computeNextInvoiceDate(LocalDate.of(2026, 8, 20)).getDateToInvoice();
		assertThat(weeklyEnd).isAfterOrEqualTo(LocalDate.of(2026, 8, 20)).isBeforeOrEqualTo(LocalDate.of(2026, 8, 27));
		assertThat(weeklyEnd.getDayOfWeek()).isEqualTo(java.time.DayOfWeek.FRIDAY);
	}

	private RefundContract contractWithSchedule(final int invoiceDistance, final LocalDate startDate)
	{
		return contractWithSchedule(31, invoiceDistance, startDate);
	}

	private RefundContract contractWithSchedule(final int invoiceDayOfMonth, final int invoiceDistance, final LocalDate startDate)
	{
		return contractWithSchedule(Frequency.MONTLY, invoiceDistance, null, invoiceDayOfMonth, startDate);
	}

	private RefundContract contractWithSchedule(final Frequency frequency, final int invoiceDistance, final java.time.DayOfWeek dayOfWeek, final LocalDate startDate)
	{
		return contractWithSchedule(frequency, invoiceDistance, dayOfWeek, 31, startDate);
	}

	private RefundContract contractWithSchedule(final Frequency frequency, final int invoiceDistance, final java.time.DayOfWeek dayOfWeek, final int invoiceDayOfMonth, final LocalDate startDate)
	{
		final InvoiceSchedule schedule = InvoiceSchedule.builder()
				.id(InvoiceScheduleId.ofRepoId(6))
				.frequency(frequency)
				.invoiceDayOfWeek(dayOfWeek)
				.invoiceDayOfMonth(invoiceDayOfMonth)
				.invoiceDistance(invoiceDistance)
				.build();

		return RefundContract.builder()
				.id(FlatrateTermId.ofRepoId(101))
				.bPartnerId(BPartnerId.ofRepoId(200))
				.startDate(startDate)
				.endDate(startDate.plusYears(3))
				.refundConfig(refundConfig1.toBuilder().invoiceSchedule(schedule).build())
				.build();
	}
}

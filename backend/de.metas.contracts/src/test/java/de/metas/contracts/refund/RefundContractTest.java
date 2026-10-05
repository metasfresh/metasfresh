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

	/** the period ends of a monthly schedule with invoice day 31 start from the anchor; the short month only clamps its own end */
	@Test
	public void computeNextInvoiceDate_multiMonthEndOfMonthSchedule_startingInAugust()
	{
		assertPeriodEnds(31, 3, "2026-08-01", "2026-10-31", "2027-01-31", "2027-04-30", "2027-07-31");
		assertPeriodEnds(31, 6, "2026-08-01", "2027-01-31", "2027-07-31", "2028-01-31");
		assertPeriodEnds(31, 12, "2026-08-01", "2027-07-31", "2028-07-31");
	}

	@Test
	public void computeNextInvoiceDate_multiMonthEndOfMonthSchedule_startingInNovember()
	{
		assertPeriodEnds(31, 3, "2026-11-01", "2027-01-31", "2027-04-30", "2027-07-31");
		assertPeriodEnds(31, 6, "2026-11-01", "2027-04-30", "2027-10-31");
		assertPeriodEnds(31, 12, "2026-11-01", "2027-10-31", "2028-10-31");
	}

	@Test
	public void computeNextInvoiceDate_periodEndInALeapYearFebruary()
	{
		assertPeriodEnds(31, 3, "2027-12-01", "2028-02-29", "2028-05-31");
		assertPeriodEnds(31, 12, "2027-03-01", "2028-02-29", "2029-02-28");
	}

	@Test
	public void computeNextInvoiceDate_midMonthInvoiceDay()
	{
		assertPeriodEnds(15, 1, "2026-08-01", "2026-08-15", "2026-09-15", "2026-10-15");
		assertPeriodEnds(15, 3, "2026-08-01", "2026-10-15", "2027-01-15", "2027-04-15");
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

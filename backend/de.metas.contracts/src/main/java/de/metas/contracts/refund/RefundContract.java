package de.metas.contracts.refund;

import static de.metas.util.collections.CollectionUtils.extractSingleElement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
public class RefundContract
{
	/** may be {@code null} if the contract is not persisted. */
	FlatrateTermId id;

	/** contains the refund configs, ordered by minQty descending. */
	List<RefundConfig> refundConfigs;

	LocalDate startDate;

	LocalDate endDate;

	BPartnerId bPartnerId;

	@Builder(toBuilder = true)
	private RefundContract(
			@Nullable final FlatrateTermId id,
			@NonNull final BPartnerId bPartnerId,
			@Singular final List<RefundConfig> refundConfigs,
			@NonNull final LocalDate startDate,
			@NonNull final LocalDate endDate)
	{
		this.id = id;
		this.bPartnerId = bPartnerId;
		this.startDate = startDate;
		this.endDate = endDate;

		RefundConfigs.assertValid(refundConfigs);

		this.refundConfigs = RefundConfigs.sortByMinQtyDesc(refundConfigs);
	}

	public RefundConfig getRefundConfig(@NonNull final BigDecimal qtyInStockUom)
	{

		return refundConfigs
				.stream()
				.filter(config -> config.getMinQty().compareTo(qtyInStockUom) <= 0)
				.findFirst()
				.orElse(null);
	}

	public List<RefundConfig> getRefundConfigsToApplyForQuantity(@NonNull final BigDecimal qty)
	{
		final Predicate<RefundConfig> minQtyLessOrEqual = config -> config.getMinQty().compareTo(qty) <= 0;

		return refundConfigs
				.stream()
				.filter(minQtyLessOrEqual)
				.collect(ImmutableList.toImmutableList());
	}

	public RefundConfig getRefundConfigById(@NonNull final RefundConfigId refundConfigId)
	{
		for (RefundConfig refundConfig : refundConfigs)
		{
			if (refundConfig.getId().equals(refundConfigId))
			{
				return refundConfig;
			}
		}

		Check.fail("This contract has no config with id={}; this={}", refundConfigId, this);
		return null;
	}

	public ConditionsId getConditionsId()
	{
		return CollectionUtils.extractSingleElement(refundConfigs, RefundConfig::getConditionsId);
	}

	public BonusRecipient extractBonusRecipient()
	{
		return RefundConfigs.extractBonusRecipient(refundConfigs);
	}

	public RefundMode extractRefundMode()
	{
		return RefundConfigs.extractRefundMode(refundConfigs);
	}

	public Optional<RefundConfig> getRefundConfigToUseProfitCalculation()
	{
		return getRefundConfigs()
				.stream()
				.filter(RefundConfig::isUseInProfitCalculation)
				.findFirst();
	}

	/**
	 * @return the end of the refund period that contains the given date (or of the first period, if the date is before this contract's start).
	 * For a monthly schedule the periods are calendar periods of the schedule's distance in months (month, quarter, half-year, year;
	 * the year is January to December); the schedule's invoice day does not matter. The first period starts with the contract.
	 * Other schedules count their periods from the contract's start date.
	 */
	public NextInvoiceDate computeNextInvoiceDate(@NonNull final LocalDate currentDate)
	{
		final InvoiceSchedule invoiceSchedule = extractSingleElement(refundConfigs, RefundConfig::getInvoiceSchedule);

		if (Frequency.MONTLY.equals(invoiceSchedule.getFrequency()))
		{
			final YearMonth lastMonth = computeCalendarPeriodFirstMonth(invoiceSchedule, latestOf(currentDate, startDate))
					.plusMonths(invoiceSchedule.getInvoiceDistance() - 1);
			return new NextInvoiceDate(invoiceSchedule, lastMonth.atEndOfMonth());
		}

		LocalDate date = invoiceSchedule.calculateNextDateToInvoice(startDate);
		while (date.isBefore(currentDate))
		{
			// ask from the day after the period end, because from the end itself, the schedule might return the same date again
			final LocalDate nextDate = invoiceSchedule.calculateNextDateToInvoice(date.plusDays(1));

			Check.assume(nextDate.isAfter(date), // make sure not to get stuck in an endless loop
					"For the given date={}, invoiceSchedule.calculateNextDateToInvoice needs to return a nextDate that is later; nextDate={}",
					date, nextDate);

			date = nextDate;
		}
		return new NextInvoiceDate(invoiceSchedule, date);
	}

	/**
	 * @return the first day of the period that contains the given date; the contract's start date if that is the first period
	 */
	public LocalDate computeCurrentPeriodStart(@NonNull final LocalDate date)
	{
		final InvoiceSchedule invoiceSchedule = extractSingleElement(refundConfigs, RefundConfig::getInvoiceSchedule);
		if (Frequency.MONTLY.equals(invoiceSchedule.getFrequency()))
		{
			return latestOf(computeCalendarPeriodFirstMonth(invoiceSchedule, date).atDay(1), startDate);
		}

		LocalDate periodStart = startDate;
		LocalDate periodEnd = computeNextInvoiceDate(periodStart).getDateToInvoice();
		while (periodEnd.isBefore(date))
		{
			periodStart = periodEnd.plusDays(1);
			periodEnd = computeNextInvoiceDate(periodStart).getDateToInvoice();
		}
		return periodStart;
	}

	/**
	 * @return the first month of the calendar period of {@code invoiceDistance} months that contains the given date
	 * @throws org.adempiere.exceptions.AdempiereException if the distance does not divide 12 (see {@link RefundConfigs#assertInvoiceDistanceDividesTheYear(InvoiceSchedule)})
	 */
	private static YearMonth computeCalendarPeriodFirstMonth(@NonNull final InvoiceSchedule invoiceSchedule, @NonNull final LocalDate date)
	{
		RefundConfigs.assertInvoiceDistanceDividesTheYear(invoiceSchedule);

		final int months = invoiceSchedule.getInvoiceDistance();
		final int periodIndex = (date.getMonthValue() - 1) / months;
		return YearMonth.of(date.getYear(), periodIndex * months + 1);
	}

	private static LocalDate latestOf(@NonNull final LocalDate date1, @NonNull final LocalDate date2)
	{
		return date1.isAfter(date2) ? date1 : date2;
	}

	@Value
	public static class NextInvoiceDate
	{
		InvoiceSchedule invoiceSchedule;
		LocalDate dateToInvoice;
	}

}

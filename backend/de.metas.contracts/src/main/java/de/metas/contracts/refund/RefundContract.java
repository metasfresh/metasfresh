package de.metas.contracts.refund;

import static de.metas.util.collections.CollectionUtils.extractSingleElement;

import java.math.BigDecimal;
import java.time.LocalDate;
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
	 * With this instance's {@code StartDate} as basis, the method returns the first date that is
	 * after or at the given {@code currentDate} and that is aligned with this instance's invoice schedule.
	 */
	public NextInvoiceDate computeNextInvoiceDate(@NonNull final LocalDate currentDate)
	{
		final InvoiceSchedule invoiceSchedule = extractSingleElement(refundConfigs, RefundConfig::getInvoiceSchedule);

		final LocalDate firstPeriodEnd = invoiceSchedule.calculateNextDateToInvoice(startDate);

		if (Frequency.MONTLY.equals(invoiceSchedule.getFrequency()))
		{
			return new NextInvoiceDate(invoiceSchedule, computeMonthlyPeriodEnd(invoiceSchedule, firstPeriodEnd, currentDate));
		}

		LocalDate date = firstPeriodEnd;
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
	 * Every period end is computed from the first one: its month plus a multiple of the schedule's distance, with the invoice day clamped to that month's length.
	 * Stepping from one end to the next would carry a clamped day (e.g. the 30th after a short month) into the following periods.
	 */
	private static LocalDate computeMonthlyPeriodEnd(
			@NonNull final InvoiceSchedule invoiceSchedule,
			@NonNull final LocalDate firstPeriodEnd,
			@NonNull final LocalDate currentDate)
	{
		final LocalDate firstPeriodEndMonth = firstPeriodEnd.withDayOfMonth(1);
		for (int periodIndex = 0;; periodIndex++)
		{
			final LocalDate month = firstPeriodEndMonth.plusMonths((long)periodIndex * invoiceSchedule.getInvoiceDistance());
			final LocalDate periodEnd = month.withDayOfMonth(Math.min(invoiceSchedule.getInvoiceDayOfMonth(), month.lengthOfMonth()));
			if (!periodEnd.isBefore(currentDate))
			{
				return periodEnd;
			}
		}
	}

	@Value
	public static class NextInvoiceDate
	{
		InvoiceSchedule invoiceSchedule;
		LocalDate dateToInvoice;
	}

}

package de.metas.purchasecandidate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.generator.Frequency;
import org.adempiere.util.time.generator.FrequencyType;
import org.compiere.util.CCache;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.purchasecandidate.BPPurchaseSchedule.BPPurchaseScheduleBuilder;
import de.metas.purchasecandidate.model.I_C_BP_PurchaseSchedule;
import de.metas.purchasecandidate.model.X_C_BP_PurchaseSchedule;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.purchasecandidate.base
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
public class BPPurchaseScheduleRepository
{
	private final CCache<Integer, AllBPPurchaseSchedule> //
	schedulesByBpartnerId = CCache.<Integer, AllBPPurchaseSchedule> newCache(I_C_BP_PurchaseSchedule.Table_Name, 10, CCache.EXPIREMINUTES_Never);

	public Optional<BPPurchaseSchedule> getByBPartnerIdAndValidFrom(final int bpartnerId, final Date date)
	{
		final LocalDate dateToMatch = TimeUtil.asLocalDate(date);

		return schedulesByBpartnerId.getOrLoad(bpartnerId, () -> retrieveAllBPPurchaseSchedulesByBPartnerId(bpartnerId))
				.findByDate(dateToMatch);
	}

	private AllBPPurchaseSchedule retrieveAllBPPurchaseSchedulesByBPartnerId(final int bpartnerId)
	{
		final List<BPPurchaseSchedule> bpPurchaseSchedules = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_PurchaseSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_PurchaseSchedule.COLUMN_C_BPartner_ID, bpartnerId)
				.create()
				.stream()
				.map(scheduleRecord -> toBPPurchaseSchedule(scheduleRecord))
				.collect(ImmutableList.toImmutableList());

		return AllBPPurchaseSchedule.builder()
				.bpartnerId(bpartnerId)
				.schedules(bpPurchaseSchedules)
				.build();
	}

	private static BPPurchaseSchedule toBPPurchaseSchedule(final I_C_BP_PurchaseSchedule scheduleRecord)
	{
		final ImmutableSet<DayOfWeek> daysOfWeek = extractDaysOfWeek(scheduleRecord);

		final Frequency frequency;
		final FrequencyType frequencyType = toFrequencyType(scheduleRecord.getFrequencyType());
		if (frequencyType == FrequencyType.Weekly)
		{
			frequency = Frequency.builder()
					.type(FrequencyType.Weekly)
					.everyNthWeek(scheduleRecord.getFrequency())
					.onlyDaysOfWeek(daysOfWeek)
					.build();
		}
		else if (frequencyType == FrequencyType.Monthly)
		{
			frequency = Frequency.builder()
					.type(FrequencyType.Monthly)
					.everyNthMonth(scheduleRecord.getFrequency())
					.onlyDayOfMonth(scheduleRecord.getMonthDay())
					.onlyDaysOfWeek(daysOfWeek)
					.build();
		}
		else
		{
			throw new AdempiereException("Unknown " + FrequencyType.class + ": " + frequencyType);
		}

		final BPPurchaseScheduleBuilder builder = BPPurchaseSchedule.builder()
				.validFrom(TimeUtil.asLocalDate(scheduleRecord.getValidFrom()))
				.frequency(frequency);

		for (final DayOfWeek dayOfWeek : daysOfWeek)
		{
			final LocalTime preparationTime = extractPreparationTime(scheduleRecord, dayOfWeek);
			if (preparationTime == null)
			{
				continue;
			}
			builder.dailyPreparationTime(dayOfWeek, preparationTime);
		}

		return builder.build();
	}

	private static FrequencyType toFrequencyType(final String frequencyType)
	{
		if (X_C_BP_PurchaseSchedule.FREQUENCYTYPE_Weekly.equals(frequencyType))
		{
			return FrequencyType.Weekly;
		}
		else if (X_C_BP_PurchaseSchedule.FREQUENCYTYPE_Monthly.equals(frequencyType))
		{
			return FrequencyType.Monthly;
		}
		else
		{
			throw new AdempiereException("Unknown " + FrequencyType.class + ": " + frequencyType);
		}
	}

	private static ImmutableSet<DayOfWeek> extractDaysOfWeek(final I_C_BP_PurchaseSchedule schedule)
	{
		final ImmutableSet.Builder<DayOfWeek> daysOfWeek = ImmutableSet.<DayOfWeek> builder();

		if (schedule.isOnMonday())
		{
			daysOfWeek.add(DayOfWeek.MONDAY);
		}
		if (schedule.isOnTuesday())
		{
			daysOfWeek.add(DayOfWeek.TUESDAY);
		}
		if (schedule.isOnWednesday())
		{
			daysOfWeek.add(DayOfWeek.WEDNESDAY);
		}
		if (schedule.isOnThursday())
		{
			daysOfWeek.add(DayOfWeek.THURSDAY);
		}
		if (schedule.isOnFriday())
		{
			daysOfWeek.add(DayOfWeek.FRIDAY);
		}
		if (schedule.isOnSaturday())
		{
			daysOfWeek.add(DayOfWeek.SATURDAY);
		}
		if (schedule.isOnSunday())
		{
			daysOfWeek.add(DayOfWeek.SUNDAY);
		}

		return daysOfWeek.build();
	}

	private static LocalTime extractPreparationTime(final I_C_BP_PurchaseSchedule schedule, final DayOfWeek dayOfWeek)
	{
		if (dayOfWeek == DayOfWeek.MONDAY)
		{
			return TimeUtil.asLocalTime(schedule.getPreparationTime_1());
		}
		else if (dayOfWeek == DayOfWeek.TUESDAY)
		{
			return TimeUtil.asLocalTime(schedule.getPreparationTime_2());
		}
		else if (dayOfWeek == DayOfWeek.WEDNESDAY)
		{
			return TimeUtil.asLocalTime(schedule.getPreparationTime_3());
		}
		else if (dayOfWeek == DayOfWeek.THURSDAY)
		{
			return TimeUtil.asLocalTime(schedule.getPreparationTime_4());
		}
		else if (dayOfWeek == DayOfWeek.FRIDAY)
		{
			return TimeUtil.asLocalTime(schedule.getPreparationTime_5());
		}
		else if (dayOfWeek == DayOfWeek.SATURDAY)
		{
			return TimeUtil.asLocalTime(schedule.getPreparationTime_6());
		}
		else if (dayOfWeek == DayOfWeek.SUNDAY)
		{
			return TimeUtil.asLocalTime(schedule.getPreparationTime_7());
		}
		else
		{
			throw new IllegalArgumentException("Invalid day of week: " + dayOfWeek);
		}
	}

	@ToString
	private static final class AllBPPurchaseSchedule
	{
		private static final Comparator<BPPurchaseSchedule> ORDERING_VALID_FROM_DESC = Comparator.comparing(BPPurchaseSchedule::getValidFrom).reversed();

		int bpartnerId;
		List<BPPurchaseSchedule> schedules;

		@Builder
		private AllBPPurchaseSchedule(final int bpartnerId, final List<BPPurchaseSchedule> schedules)
		{
			Check.assumeGreaterThanZero(bpartnerId, "bpartnerId");
			Check.assumeNotEmpty(schedules, "schedules is not empty");

			this.bpartnerId = bpartnerId;
			this.schedules = schedules.stream()
					.sorted(ORDERING_VALID_FROM_DESC)
					.collect(ImmutableList.toImmutableList());
		}

		public Optional<BPPurchaseSchedule> findByDate(@NonNull final LocalDate date)
		{
			return schedules.stream()
					.filter(schedule -> schedule.getValidFrom().compareTo(date) <= 0)
					.findFirst();
		}
	}
}

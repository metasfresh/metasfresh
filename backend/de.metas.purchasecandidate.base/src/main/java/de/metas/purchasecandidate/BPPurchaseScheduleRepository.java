package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.calendar.CalendarId;
import de.metas.purchasecandidate.model.I_C_BP_PurchaseSchedule;
import de.metas.purchasecandidate.model.X_C_BP_PurchaseSchedule;
import de.metas.util.Services;
import de.metas.util.time.generator.Frequency;
import de.metas.util.time.generator.FrequencyType;
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
	private final CCache<BPartnerId, AllBPPurchaseSchedule> //
	schedulesByBpartnerId = CCache.<BPartnerId, AllBPPurchaseSchedule> newCache(I_C_BP_PurchaseSchedule.Table_Name, 10, CCache.EXPIREMINUTES_Never);

	public Optional<BPPurchaseSchedule> getByBPartnerIdAndValidFrom(final BPartnerId bpartnerId, final LocalDate date)
	{
		return schedulesByBpartnerId.getOrLoad(bpartnerId, this::retrieveAllBPPurchaseSchedulesByBPartnerId)
				.findByDate(date);
	}

	private AllBPPurchaseSchedule retrieveAllBPPurchaseSchedulesByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		final List<BPPurchaseSchedule> bpPurchaseSchedules = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_PurchaseSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_PurchaseSchedule.COLUMNNAME_C_BPartner_ID, bpartnerId.getRepoId())
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

		return BPPurchaseSchedule.builder()
				.bpPurchaseScheduleId(BPPurchaseScheduleId.ofRepoId(scheduleRecord.getC_BP_PurchaseSchedule_ID()))
				.validFrom(TimeUtil.asLocalDate(scheduleRecord.getValidFrom()))
				.frequency(frequency)
				.dailyPreparationTimes(extractPreparationTimes(daysOfWeek, scheduleRecord))
				.reminderTime(Duration.ofMinutes(scheduleRecord.getReminderTimeInMin()))
				.leadTimeOffset(Duration.ofDays(scheduleRecord.getLeadTimeOffset()))
				.bpartnerId(BPartnerId.ofRepoId(scheduleRecord.getC_BPartner_ID()))
				.nonBusinessDaysCalendarId(CalendarId.ofRepoIdOrNull(scheduleRecord.getC_Calendar_ID()))
				.build();
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

	private static String toFrequencyTypeString(final FrequencyType frequencyType)
	{
		if (FrequencyType.Weekly.equals(frequencyType))
		{
			return X_C_BP_PurchaseSchedule.FREQUENCYTYPE_Weekly;
		}
		else if (FrequencyType.Monthly.equals(frequencyType))
		{
			return X_C_BP_PurchaseSchedule.FREQUENCYTYPE_Monthly;
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

	private static Map<DayOfWeek, LocalTime> extractPreparationTimes(final Set<DayOfWeek> daysOfWeek, final I_C_BP_PurchaseSchedule scheduleRecord)
	{
		final ImmutableMap.Builder<DayOfWeek, LocalTime> preparationTimes = ImmutableMap.builder();

		for (final DayOfWeek dayOfWeek : daysOfWeek)
		{
			final LocalTime preparationTime = extractPreparationTime(scheduleRecord, dayOfWeek);
			if (preparationTime == null)
			{
				continue;
			}
			preparationTimes.put(dayOfWeek, preparationTime);
		}

		return preparationTimes.build();
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

		BPartnerId bpartnerId;
		List<BPPurchaseSchedule> schedules;

		@Builder
		private AllBPPurchaseSchedule(@NonNull final BPartnerId bpartnerId, @NonNull final List<BPPurchaseSchedule> schedules)
		{
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

	public BPPurchaseSchedule changeLeadTimeOffset(final BPPurchaseSchedule bpPurchaseSchedule, Duration leadTimeOffset)
	{
		bpPurchaseSchedule.toBuilder()
				.leadTimeOffset(leadTimeOffset)
				.build();

		save(bpPurchaseSchedule);

		return bpPurchaseSchedule;
	}

	public BPPurchaseSchedule save(@NonNull final BPPurchaseSchedule schedule)
	{
		final I_C_BP_PurchaseSchedule scheduleRecord = createOrUpdateRecord(schedule);
		saveRecord(scheduleRecord);

		return schedule.toBuilder()
				.bpPurchaseScheduleId(BPPurchaseScheduleId.ofRepoId(scheduleRecord.getC_BP_PurchaseSchedule_ID()))
				.build();
	}

	private I_C_BP_PurchaseSchedule createOrUpdateRecord(@NonNull final BPPurchaseSchedule schedule)
	{
		final I_C_BP_PurchaseSchedule scheduleRecord;
		if (schedule.getBpPurchaseScheduleId() != null)
		{
			final int repoId = schedule.getBpPurchaseScheduleId().getRepoId();
			scheduleRecord = load(repoId, I_C_BP_PurchaseSchedule.class);
		}
		else
		{
			scheduleRecord = newInstance(I_C_BP_PurchaseSchedule.class);
		}

		scheduleRecord.setC_BPartner_ID(BPartnerId.toRepoIdOr(schedule.getBpartnerId(), 0));
		scheduleRecord.setValidFrom(TimeUtil.asTimestamp(schedule.getValidFrom()));
		scheduleRecord.setLeadTimeOffset((int)schedule.getLeadTimeOffset().toDays());
		scheduleRecord.setReminderTimeInMin((int)schedule.getReminderTime().toMinutes());
		final Frequency frequency = schedule.getFrequency();
		scheduleRecord.setFrequencyType(toFrequencyTypeString(frequency.getType()));
		if (frequency.getType() == FrequencyType.Weekly)
		{
			scheduleRecord.setFrequency(frequency.getEveryNthWeek());
		}
		else if (frequency.getType() == FrequencyType.Monthly)
		{
			scheduleRecord.setFrequency(frequency.getEveryNthMonth());
			scheduleRecord.setMonthDay(frequency.getOnlyDaysOfMonth()
					.stream()
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No month of the day " + Frequency.class + ": " + frequency)));
		}

		final ImmutableSet<DayOfWeek> daysOfWeek = frequency.getOnlyDaysOfWeek();
		setDaysOfWeek(scheduleRecord, daysOfWeek);

		return scheduleRecord;
	}

	private static void setDaysOfWeek(@NonNull final I_C_BP_PurchaseSchedule scheduleRecord, @NonNull final ImmutableSet<DayOfWeek> daysOfWeek)
	{
		if (daysOfWeek.contains(DayOfWeek.MONDAY))
		{
			scheduleRecord.setOnMonday(true);
		}
		if (daysOfWeek.contains(DayOfWeek.TUESDAY))
		{
			scheduleRecord.setOnTuesday(true);
		}
		if (daysOfWeek.contains(DayOfWeek.WEDNESDAY))
		{
			scheduleRecord.setOnWednesday(true);
		}
		if (daysOfWeek.contains(DayOfWeek.THURSDAY))
		{
			scheduleRecord.setOnThursday(true);
		}
		if (daysOfWeek.contains(DayOfWeek.FRIDAY))
		{
			scheduleRecord.setOnFriday(true);
		}
		if (daysOfWeek.contains(DayOfWeek.SATURDAY))
		{
			scheduleRecord.setOnSaturday(true);
		}
		if (daysOfWeek.contains(DayOfWeek.SUNDAY))
		{
			scheduleRecord.setOnSunday(true);
		}
	}
}

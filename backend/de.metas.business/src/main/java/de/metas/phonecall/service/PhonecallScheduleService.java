package de.metas.phonecall.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Phonecall_Schedule;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.calendar.ICalendarBL;
import de.metas.phonecall.PhonecallSchedule;
import de.metas.phonecall.PhonecallSchema;
import de.metas.phonecall.PhonecallSchemaVersion;
import de.metas.phonecall.PhonecallSchemaVersionId;
import de.metas.phonecall.PhonecallSchemaVersionLine;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.calendar.IBusinessDayMatcher;
import de.metas.util.time.generator.BusinessDayShifter;
import de.metas.util.time.generator.BusinessDayShifter.OnNonBussinessDay;
import de.metas.util.time.generator.DateSequenceGenerator;
import de.metas.util.time.generator.Frequency;
import de.metas.util.time.generator.IDateShifter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class PhonecallScheduleService
{
	private final PhonecallScheduleRepository schedulesRepo;
	private final PhonecallSchemaRepository schemaRepo;

	public PhonecallScheduleService(
			@NonNull final PhonecallScheduleRepository schedulesRepo,
			@NonNull final PhonecallSchemaRepository schemaRepo)
	{
		this.schedulesRepo = schedulesRepo;
		this.schemaRepo = schemaRepo;
	}

	public void generatePhonecallSchedulesForSchema(final PhonecallSchema schema, final LocalDate startDate, final LocalDate endDate)
	{
		final List<PhonecallSchemaVersionRange> schemaVersionRanges = retrievePhonecallSchemaVersionRanges(schema, startDate, endDate);

		for (final PhonecallSchemaVersionRange schemaVersionRange : schemaVersionRanges)
		{

			schemaRepo.deletePhonecallDaysInRange(schemaVersionRange);

			final PhonecallSchemaVersion schemaVersion = schemaVersionRange.getPhonecallSchemaVersion();
			final List<PhonecallSchemaVersionLine> schemaVersionLines = schemaVersion.getLines();
			if (schemaVersionLines.isEmpty())
			{
				continue;
			}
			for (final PhonecallSchemaVersionLine schemaVersionLine : schemaVersionLines)
			{
				createPhonecallSchedulesForLine(schemaVersionRange, schemaVersionLine);
			}
		}
	}

	public void createPhonecallSchedulesForLine(final PhonecallSchemaVersionRange schemaVersionRange, final PhonecallSchemaVersionLine schemaVersionLine)
	{
		final Set<LocalDate> phonecallDates = schemaVersionRange.generatePhonecallDates();
		if (phonecallDates.isEmpty())
		{

			return;
		}

		for (final LocalDate currentPhonecallDate : phonecallDates)
		{
			if (!phonecallScheduleAlreadyExists(currentPhonecallDate, schemaVersionLine))
			{
				createPhonecallSchedule(schemaVersionLine, currentPhonecallDate);
			}
		}

	}

	private boolean phonecallScheduleAlreadyExists(final LocalDate currentPhonecallDate, final PhonecallSchemaVersionLine schemaVersionLine)
	{
		final BPartnerLocationId bpartnerAndLocationId = schemaVersionLine.getBpartnerAndLocationId();
		final UserId contactId = schemaVersionLine.getContactId();
		final PhonecallSchemaVersionId phonecallSchemaVersionId = schemaVersionLine.getVersionId();

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Phonecall_Schedule.class)
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_C_BPartner_ID, bpartnerAndLocationId.getBpartnerId().getRepoId())
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_C_BPartner_Location_ID, bpartnerAndLocationId.getRepoId())
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_C_BP_Contact_ID, contactId.getRepoId())
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_PhonecallTimeMin, TimeUtil.asTimestamp(schemaVersionLine.getStartTime()))
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_PhonecallTimeMax, TimeUtil.asTimestamp(schemaVersionLine.getEndTime()))
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_PhonecallDate, TimeUtil.asTimestamp(currentPhonecallDate))
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_C_Phonecall_Schema_ID, phonecallSchemaVersionId.getPhonecallSchemaId())
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_C_Phonecall_Schema_Version_ID, phonecallSchemaVersionId)
				.create()
				.anyMatch();
	}

	private void createPhonecallSchedule(final PhonecallSchemaVersionLine schemaVersionLine, final LocalDate currentPhonecallDate)
	{

		final BPartnerLocationId bpartnerAndLocationId = schemaVersionLine.getBpartnerAndLocationId();
		final BPartnerId bpartnerId = bpartnerAndLocationId.getBpartnerId();

		final UserId salesRepId = Services.get(IBPartnerBL.class).getSalesRepIdOrNull(bpartnerId);

		final PhonecallSchedule schedule = PhonecallSchedule.builder()
				.orgId(schemaVersionLine.getOrgId())
				.bpartnerAndLocationId(bpartnerAndLocationId)
				.contactId(schemaVersionLine.getContactId())
				.schemaVersionLineId(schemaVersionLine.getId())
				.date(currentPhonecallDate)
				.startTime(schemaVersionLine.getStartTime())
				.endTime(schemaVersionLine.getEndTime())
				.salesRepId(salesRepId)
				.description(schemaVersionLine.getDescription())
				.build();

		schedulesRepo.save(schedule);
	}

	/**
	 * Issue https://github.com/metasfresh/metasfresh/issues/4951
	 * I made this method similar with de.metas.tourplanning.api.impl.TourDAO.retrieveTourVersionRanges(I_M_Tour, LocalDate, LocalDate) and I kept the comments
	 * Maybe in the future these shall be somehow brought together in a single structure so we don't have similar code in 2 places.
	 *
	 * @param schema
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PhonecallSchemaVersionRange> retrievePhonecallSchemaVersionRanges(
			@NonNull final PhonecallSchema schema,
			@NonNull final LocalDate dateFrom,
			@NonNull final LocalDate dateTo)
	{
		Check.assume(dateFrom.compareTo(dateTo) <= 0, "startDate({}) <= endDate({})", dateFrom, dateTo);

		final List<PhonecallSchemaVersion> schemaVersions = schema.getChronologicallyOrderedPhonecallSchemaVersions(dateTo);
		if (schemaVersions.isEmpty())
		{
			return Collections.emptyList();
		}

		//
		// Continue iterating the phonecall schema versions and create phonecall schema version ranges
		List<PhonecallSchemaVersionRange> schemaVersionRanges = new ArrayList<>();
		boolean previousVersionValid = false;
		PhonecallSchemaVersion previousSchemaVersion = null;
		LocalDate previousSchemaVersionValidFrom = null;

		final Iterator<PhonecallSchemaVersion> schemaVersionsIterator = schemaVersions.iterator();
		while (schemaVersionsIterator.hasNext())
		{
			final PhonecallSchemaVersion schemaVersion = schemaVersionsIterator.next();

			final LocalDate schemaVersionValidFrom = schemaVersion.getValidFrom();

			Check.assumeNotNull(schemaVersionValidFrom, "phonecallSchemaVersionValidFrom not null");

			//
			// Guard: phonecall schema version's ValidFrom shall be before "dateTo"
			if (schemaVersionValidFrom.compareTo(dateTo) > 0)
			{
				// shall not happen because we retrieved until dateTo, but just to make sure
				break;
			}

			//
			// Case: We are still searching for first phonecall schema version to consider
			if (!previousVersionValid)
			{
				// Case: our current phonecall schema version is before given dateFrom
				if (schemaVersionValidFrom.compareTo(dateFrom) < 0)
				{
					if (!schemaVersionsIterator.hasNext())
					{
						// there is no other next, so we need to consider this one
						previousSchemaVersion = schemaVersion;
						previousSchemaVersionValidFrom = dateFrom;
						previousVersionValid = true;
						continue;
					}
				}
				// Case: our current phonecall schema version starts exactly on our given dateFrom
				else if (schemaVersionValidFrom.compareTo(dateFrom) == 0)
				{
					previousSchemaVersion = schemaVersion;
					previousSchemaVersionValidFrom = dateFrom;
					previousVersionValid = true;
					continue;
				}
				// Case: our current phonecall schema version start after our given dateFrom
				else
				{
					// Check if we have a previous phonecall schema version, because if we have, that shall be the first phonecall schema version to consider
					if (previousSchemaVersion != null)
					{
						// NOTE: we consider dateFrom as first date because phonecall schema version's ValidFrom is before dateFrom
						previousSchemaVersionValidFrom = dateFrom;
						previousVersionValid = true;
						// don't continue: we got it right now
						// continue;
					}
					// ... else it seems this is the first phonecall schema version which actually starts after our dateFrom
					else
					{
						previousSchemaVersion = schemaVersion;
						previousSchemaVersionValidFrom = schemaVersionValidFrom;
						previousVersionValid = true;
						continue;
					}
				}
			}

			//
			// Case: we do have a previous valid phonecall schema version to consider so we can generate phonecall schema ranges
			if (previousVersionValid)
			{
				final LocalDate previousSchemaVersionValidTo = schemaVersionValidFrom.minusDays(1);
				final PhonecallSchemaVersionRange previousSchemaVersionRange = createPhonecallSchemaVersionRange(
						previousSchemaVersion,
						previousSchemaVersionValidFrom,
						previousSchemaVersionValidTo);

				schemaVersionRanges.add(previousSchemaVersionRange);
			}

			//
			// Set current as previous and move on
			previousSchemaVersion = schemaVersion;
			previousSchemaVersionValidFrom = schemaVersionValidFrom;
		}

		//
		// Create phonecall schema Version Range for last version
		if (previousVersionValid)
		{
			final PhonecallSchemaVersionRange lastSchemaVersionRange = createPhonecallSchemaVersionRange(
					previousSchemaVersion,
					previousSchemaVersionValidFrom,
					dateTo);
			schemaVersionRanges.add(lastSchemaVersionRange);
		}

		return schemaVersionRanges;
	}

	private PhonecallSchemaVersionRange createPhonecallSchemaVersionRange(
			final PhonecallSchemaVersion schemaVersion,
			final LocalDate startDate,
			final LocalDate endDate)
	{
		return PhonecallSchemaVersionRange.builder()
				.phonecallSchemaVersion(schemaVersion)
				.startDate(startDate)
				.endDate(endDate)
				.dateSequenceGenerator(createDateSequenceGenerator(schemaVersion, startDate, endDate))
				.build();
	}

	public DateSequenceGenerator createDateSequenceGenerator(
			@NonNull final PhonecallSchemaVersion schemaVersion,
			@NonNull final LocalDate validFrom,
			@NonNull final LocalDate validTo)
	{
		final Frequency frequency = schemaVersion.getFrequency();
		if (frequency == null)
		{
			return null;
		}

		final OnNonBussinessDay onNonBusinessDay = schemaRepo.extractOnNonBussinessDayOrNull(schemaVersion);

		return DateSequenceGenerator.builder()
				.dateFrom(validFrom)
				.dateTo(validTo)
				.shifter(createDateShifter(frequency, onNonBusinessDay))
				.frequency(frequency)
				.build();
	}

	private static IDateShifter createDateShifter(final Frequency frequency, final OnNonBussinessDay onNonBusinessDay)
	{
		final IBusinessDayMatcher businessDayMatcher = createBusinessDayMatcher(frequency, onNonBusinessDay);

		return BusinessDayShifter.builder()
				.businessDayMatcher(businessDayMatcher)
				.onNonBussinessDay(onNonBusinessDay != null ? onNonBusinessDay : OnNonBussinessDay.Cancel)
				.build();
	}

	public static IBusinessDayMatcher createBusinessDayMatcher(final Frequency frequency, final OnNonBussinessDay onNonBusinessDay)
	{
		final ICalendarBL calendarBL = Services.get(ICalendarBL.class);

		//
		// If user explicitly asked for a set of week days, don't consider them non-business days by default
		if (frequency.isWeekly()
				&& frequency.isOnlySomeDaysOfTheWeek()
				&& onNonBusinessDay == null)
		{
			return calendarBL.createBusinessDayMatcherExcluding(frequency.getOnlyDaysOfWeek());
		}
		else
		{
			return calendarBL.createBusinessDayMatcherExcluding(ImmutableSet.of());
		}
	}
}

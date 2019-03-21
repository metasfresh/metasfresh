package de.metas.phonecall.service;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.user.UserId;
import org.compiere.model.I_C_Phonecall_Schedule;
import org.compiere.model.I_C_Phonecall_Schema;
import org.compiere.model.I_C_Phonecall_Schema_Version;
import org.compiere.model.I_C_Phonecall_Schema_Version_Line;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.cache.CCache;
import de.metas.phonecall.PhonecallSchema;
import de.metas.phonecall.PhonecallSchemaId;
import de.metas.phonecall.PhonecallSchemaVersion;
import de.metas.phonecall.PhonecallSchemaVersionId;
import de.metas.phonecall.PhonecallSchemaVersionLine;
import de.metas.phonecall.PhonecallSchemaVersionLineId;
import de.metas.util.Services;
import de.metas.util.time.generator.BusinessDayShifter.OnNonBussinessDay;
import de.metas.util.time.generator.Frequency;
import de.metas.util.time.generator.FrequencyType;
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

@Repository
public class PhonecallSchemaRepository
{
	private final CCache<PhonecallSchemaId, PhonecallSchema> schemas = CCache.<PhonecallSchemaId, PhonecallSchema> builder()
			.tableName(I_C_Phonecall_Schema.Table_Name)
			.build();

	public PhonecallSchema getById(@NonNull final PhonecallSchemaId id)
	{
		return schemas.getOrLoad(id, this::retrieveById);
	}

	private PhonecallSchema retrieveById(@NonNull final PhonecallSchemaId id)
	{
		final I_C_Phonecall_Schema schemaRecord = loadOutOfTrx(id, I_C_Phonecall_Schema.class);

		return PhonecallSchema.builder()
				.id(id)
				.name(schemaRecord.getName())
				.versions(retrievePhonecallSchemaVersions(id))
				.build();
	}

	private PhonecallSchemaVersion retrievePhonecallSchemaVersionById(@NonNull PhonecallSchemaVersionId phonecallSchemaVersionId)
	{
		final I_C_Phonecall_Schema_Version phonecallSchemaVersionRecord = loadOutOfTrx(phonecallSchemaVersionId, I_C_Phonecall_Schema_Version.class);

		return PhonecallSchemaVersion.builder()
				.id(phonecallSchemaVersionId)
				.phonecallSchemaId(phonecallSchemaVersionId.getPhonecallSchemaId())
				.lines(retrievePhonecallSchemaVersionLines(phonecallSchemaVersionId))
				.validFrom(TimeUtil.asLocalDate(phonecallSchemaVersionRecord.getValidFrom()))
				.name(phonecallSchemaVersionRecord.getName())
				.build();
	}

	private ImmutableList<PhonecallSchemaVersionLine> retrievePhonecallSchemaVersionLines(final PhonecallSchemaVersionId phonecallSchemaVersionId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Phonecall_Schema_Version_Line.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Phonecall_Schema_Version_Line.COLUMNNAME_C_Phonecall_Schema_Version_ID, phonecallSchemaVersionId.getRepoId())
				.create()
				.list(I_C_Phonecall_Schema_Version_Line.class)
				.stream()
				.map(
						line -> retrievePhonecallSchemaVersionLineById(
								PhonecallSchemaVersionLineId.ofRepoId(
										line.getC_Phonecall_Schema_ID(),
										line.getC_Phonecall_Schema_Version_ID(),
										line.getC_Phonecall_Schema_Version_Line_ID())))
				.collect(ImmutableList.toImmutableList());
	}

	private PhonecallSchemaVersionLine retrievePhonecallSchemaVersionLineById(final PhonecallSchemaVersionLineId phonecallSchemaVersionLineId)
	{
		final I_C_Phonecall_Schema_Version_Line phonecallSchemaVersionLineRecord = loadOutOfTrx(phonecallSchemaVersionLineId, I_C_Phonecall_Schema_Version_Line.class);

		return PhonecallSchemaVersionLine.builder()
				.id(phonecallSchemaVersionLineId)
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoId(phonecallSchemaVersionLineRecord.getC_BPartner_ID(), phonecallSchemaVersionLineRecord.getC_BPartner_Location_ID()))
				.contactId(UserId.ofRepoId(phonecallSchemaVersionLineRecord.getC_BP_Contact_ID()))
				.phonecallSchemaVersionId(phonecallSchemaVersionLineId.getPhonecallSchemaVersionId())
				.startTime(TimeUtil.asZonedDateTime(phonecallSchemaVersionLineRecord.getPhonecallTimeMin()))
				.endTime(TimeUtil.asZonedDateTime(phonecallSchemaVersionLineRecord.getPhonecallTimeMax()))
				.build();
	}

	private List<PhonecallSchemaVersion> retrievePhonecallSchemaVersions(final PhonecallSchemaId phonecallSchemaId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Phonecall_Schema_Version.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Phonecall_Schema_Version.COLUMNNAME_C_Phonecall_Schema_ID, phonecallSchemaId.getRepoId())
				.create()
				.listIds()
				.stream()
				.map(id -> retrievePhonecallSchemaVersionById(PhonecallSchemaVersionId.ofRepoIdOrNull(phonecallSchemaId, id)))
				.collect(ImmutableList.toImmutableList());
	}

	public Frequency extractFrequency(final PhonecallSchemaVersion phonecallSchemaVersion)
	{
		final PhonecallSchemaVersionId phonecallSchemaVersionId = phonecallSchemaVersion.getId();

		final I_C_Phonecall_Schema_Version phonecallSchemaVersionRecord = loadOutOfTrx(phonecallSchemaVersionId, I_C_Phonecall_Schema_Version.class);

		boolean isWeekly = phonecallSchemaVersionRecord.isWeekly();
		int everyWeek = phonecallSchemaVersionRecord.getEveryWeek();

		if (isWeekly)
		{
			everyWeek = 1;
		}
		else if (!isWeekly && everyWeek > 0)
		{
			isWeekly = true;
		}

		boolean isMonthly = phonecallSchemaVersionRecord.isMonthly();
		int everyMonth = phonecallSchemaVersionRecord.getEveryMonth();
		final int monthDay = phonecallSchemaVersionRecord.getMonthDay();
		if (isMonthly)
		{
			everyMonth = 1;
		}
		else if (!isMonthly && everyMonth > 0)
		{
			isMonthly = true;
		}

		if (isWeekly)
		{
			return Frequency.builder()
					.type(FrequencyType.Weekly)
					.everyNthWeek(everyWeek)
					.onlyDaysOfWeek(extractWeekDays(phonecallSchemaVersion))
					.build();
		}
		else if (isMonthly)
		{
			return Frequency.builder()
					.type(FrequencyType.Monthly)
					.everyNthMonth(1)
					.onlyDayOfMonth(monthDay)
					.build();
		}
		else
		{
			return null;
		}
	}

	private static Set<DayOfWeek> extractWeekDays(@NonNull PhonecallSchemaVersion phonecallSchemaVersion)
	{
		final PhonecallSchemaVersionId phonecallSchemaVersionId = phonecallSchemaVersion.getId();

		final I_C_Phonecall_Schema_Version phonecallSchemaVersionRecord = loadOutOfTrx(phonecallSchemaVersionId, I_C_Phonecall_Schema_Version.class);

		final Set<DayOfWeek> weekDays = new HashSet<>();
		if (phonecallSchemaVersionRecord.isOnSunday())
		{
			weekDays.add(DayOfWeek.SUNDAY);
		}
		if (phonecallSchemaVersionRecord.isOnMonday())
		{
			weekDays.add(DayOfWeek.MONDAY);
		}
		if (phonecallSchemaVersionRecord.isOnTuesday())
		{
			weekDays.add(DayOfWeek.TUESDAY);
		}
		if (phonecallSchemaVersionRecord.isOnWednesday())
		{
			weekDays.add(DayOfWeek.WEDNESDAY);
		}
		if (phonecallSchemaVersionRecord.isOnThursday())
		{
			weekDays.add(DayOfWeek.THURSDAY);
		}
		if (phonecallSchemaVersionRecord.isOnFriday())
		{
			weekDays.add(DayOfWeek.FRIDAY);
		}
		if (phonecallSchemaVersionRecord.isOnSaturday())
		{
			weekDays.add(DayOfWeek.SATURDAY);
		}

		return weekDays;
	}

	public void deletePhonecallDaysInRange(final PhonecallSchemaVersionRange phonecallSchemaVersionRange)
	{
		final PhonecallSchemaVersion phonecallSchemaVersion = phonecallSchemaVersionRange.getPhonecallSchemaVersion();

		final DateTruncQueryFilterModifier dateTruncModifier = DateTruncQueryFilterModifier.forTruncString(TimeUtil.TRUNC_DAY);

		final IQueryBuilder<I_C_Phonecall_Schedule> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Phonecall_Schedule.class)
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_IsCalled, false)
				.addCompareFilter(I_C_Phonecall_Schedule.COLUMN_PhonecallDate, Operator.GREATER_OR_EQUAL, phonecallSchemaVersionRange.getStartDate(), dateTruncModifier)
				.addCompareFilter(I_C_Phonecall_Schedule.COLUMN_PhonecallDate, Operator.LESS_OR_EQUAL, phonecallSchemaVersionRange.getEndDate(), dateTruncModifier)
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_C_Phonecall_Schema_ID, phonecallSchemaVersion.getPhonecallSchemaId().getRepoId());

		queryBuilder.orderBy(I_C_Phonecall_Schedule.COLUMN_PhonecallDate);

		queryBuilder.create().delete();
	}

	public OnNonBussinessDay extractOnNonBussinessDayOrNull(final PhonecallSchemaVersion phonecallSchemaVersion)
	{
		final PhonecallSchemaVersionId phonecallSchemaVersionId = phonecallSchemaVersion.getId();
		final I_C_Phonecall_Schema_Version phonecallSchemaVersionRecord = loadOutOfTrx(phonecallSchemaVersionId, I_C_Phonecall_Schema_Version.class);

		if (phonecallSchemaVersionRecord.isCancelPhonecallDay())
		{
			return OnNonBussinessDay.Cancel;
		}
		else if (phonecallSchemaVersionRecord.isMovePhonecallDay())
		{
			return OnNonBussinessDay.MoveToClosestBusinessDay;
		}
		else
		{
			return null;
		}
	}
}

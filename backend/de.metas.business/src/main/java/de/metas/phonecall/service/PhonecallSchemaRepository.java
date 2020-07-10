package de.metas.phonecall.service;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Phonecall_Schedule;
import org.compiere.model.I_C_Phonecall_Schema;
import org.compiere.model.I_C_Phonecall_Schema_Version;
import org.compiere.model.I_C_Phonecall_Schema_Version_Line;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.phonecall.PhonecallSchema;
import de.metas.phonecall.PhonecallSchemaId;
import de.metas.phonecall.PhonecallSchemaVersion;
import de.metas.phonecall.PhonecallSchemaVersionId;
import de.metas.phonecall.PhonecallSchemaVersionLine;
import de.metas.phonecall.PhonecallSchemaVersionLineId;
import de.metas.user.UserId;
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
			.additionalTableNameToResetFor(I_C_Phonecall_Schema_Version.Table_Name)
			.additionalTableNameToResetFor(I_C_Phonecall_Schema_Version_Line.Table_Name)
			.build();

	public PhonecallSchema getById(@NonNull final PhonecallSchemaId id)
	{
		return schemas.getOrLoad(id, this::retrieveById);
	}

	private PhonecallSchema retrieveById(@NonNull final PhonecallSchemaId id)
	{
		final I_C_Phonecall_Schema schemaRecord = load(id, I_C_Phonecall_Schema.class);

		final List<I_C_Phonecall_Schema_Version> versionRecords = retrieveVersionRecords(id);

		final ImmutableSet<PhonecallSchemaVersionId> versionIds = versionRecords.stream()
				.map(record -> extractPhonecallSchemaVersionId(record))
				.collect(ImmutableSet.toImmutableSet());

		final ListMultimap<PhonecallSchemaVersionId, PhonecallSchemaVersionLine> lines = retrieveLines(versionIds);

		ImmutableList<PhonecallSchemaVersion> versions = versionRecords.stream()
				.map(record -> toPhonecallSchemaVersion(record, lines.get(extractPhonecallSchemaVersionId(record))))
				.collect(ImmutableList.toImmutableList());

		return PhonecallSchema.builder()
				.id(id)
				.name(schemaRecord.getName())
				.versions(versions)
				.build();
	}

	private List<I_C_Phonecall_Schema_Version> retrieveVersionRecords(@NonNull final PhonecallSchemaId id)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Phonecall_Schema_Version.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Phonecall_Schema_Version.COLUMNNAME_C_Phonecall_Schema_ID, id)
				.create()
				.list();
	}

	private ListMultimap<PhonecallSchemaVersionId, PhonecallSchemaVersionLine> retrieveLines(@NonNull final Collection<PhonecallSchemaVersionId> versionIds)
	{
		if (versionIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Phonecall_Schema_Version_Line.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Phonecall_Schema_Version_Line.COLUMN_C_Phonecall_Schema_Version_ID, versionIds)
				.create()
				.stream()
				.map(record -> toPhonecallSchemaVersionLine(record))
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						PhonecallSchemaVersionLine::getVersionId,
						Function.identity()));
	}

	private static PhonecallSchemaVersionId extractPhonecallSchemaVersionId(final I_C_Phonecall_Schema_Version record)
	{
		final PhonecallSchemaId schemaId = PhonecallSchemaId.ofRepoId(record.getC_Phonecall_Schema_ID());
		return PhonecallSchemaVersionId.ofRepoId(schemaId, record.getC_Phonecall_Schema_Version_ID());
	}

	private static PhonecallSchemaVersionId extractPhonecallSchemaVersionId(final I_C_Phonecall_Schema_Version_Line record)
	{
		final PhonecallSchemaId schemaId = PhonecallSchemaId.ofRepoId(record.getC_Phonecall_Schema_ID());
		return PhonecallSchemaVersionId.ofRepoId(schemaId, record.getC_Phonecall_Schema_Version_ID());
	}

	private static PhonecallSchemaVersionLine toPhonecallSchemaVersionLine(final I_C_Phonecall_Schema_Version_Line record)
	{
		final PhonecallSchemaVersionId versionId = extractPhonecallSchemaVersionId(record);
		final PhonecallSchemaVersionLineId id = PhonecallSchemaVersionLineId.ofRepoId(versionId, record.getC_Phonecall_Schema_Version_Line_ID());

		return PhonecallSchemaVersionLine.builder()
				.id(id)
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.versionId(versionId)
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID()))
				.contactId(UserId.ofRepoId(record.getC_BP_Contact_ID()))
				.startTime(TimeUtil.asZonedDateTime(record.getPhonecallTimeMin()))
				.endTime(TimeUtil.asZonedDateTime(record.getPhonecallTimeMax()))
				.description(record.getDescription())
				.build();
	}

	private static PhonecallSchemaVersion toPhonecallSchemaVersion(
			@NonNull final I_C_Phonecall_Schema_Version record,
			@NonNull final List<PhonecallSchemaVersionLine> lines)
	{
		final PhonecallSchemaVersionId id = extractPhonecallSchemaVersionId(record);
		return PhonecallSchemaVersion.builder()
				.id(id)
				.phonecallSchemaId(id.getPhonecallSchemaId())
				.validFrom(TimeUtil.asLocalDate(record.getValidFrom()))
				.name(record.getName())
				.frequency(extractFrequencyOrNull(record))
				.lines(ImmutableList.copyOf(lines))
				.build();
	}

	private static Frequency extractFrequencyOrNull(final I_C_Phonecall_Schema_Version record)
	{
		boolean isWeekly = record.isWeekly();
		int everyWeek = record.getEveryWeek();

		if (isWeekly)
		{
			everyWeek = 1;
		}
		else if (!isWeekly && everyWeek > 0)
		{
			isWeekly = true;
		}

		boolean isMonthly = record.isMonthly();
		int everyMonth = record.getEveryMonth();
		final int monthDay = record.getMonthDay();
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
					.onlyDaysOfWeek(extractWeekDays(record))
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
			return null; // No frequency was defined yet
		}
	}

	private static Set<DayOfWeek> extractWeekDays(@NonNull final I_C_Phonecall_Schema_Version phonecallSchemaVersionRecord)
	{
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

	public void deletePhonecallDaysInRange(final PhonecallSchemaVersionRange schemaVersionRange)
	{
		final PhonecallSchemaVersion schemaVersion = schemaVersionRange.getPhonecallSchemaVersion();

		final DateTruncQueryFilterModifier dateTruncModifier = DateTruncQueryFilterModifier.forTruncString(TimeUtil.TRUNC_DAY);

		final IQueryBuilder<I_C_Phonecall_Schedule> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Phonecall_Schedule.class)
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_IsCalled, false)
				.addCompareFilter(I_C_Phonecall_Schedule.COLUMN_PhonecallDate, Operator.GREATER_OR_EQUAL, schemaVersionRange.getStartDate(), dateTruncModifier)
				.addCompareFilter(I_C_Phonecall_Schedule.COLUMN_PhonecallDate, Operator.LESS_OR_EQUAL, schemaVersionRange.getEndDate(), dateTruncModifier)
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_C_Phonecall_Schema_ID, schemaVersion.getPhonecallSchemaId().getRepoId());

		queryBuilder.orderBy(I_C_Phonecall_Schedule.COLUMN_PhonecallDate);

		queryBuilder.create().delete();
	}

	public OnNonBussinessDay extractOnNonBussinessDayOrNull(final PhonecallSchemaVersion schemaVersion)
	{
		final PhonecallSchemaVersionId schemaVersionId = schemaVersion.getId();
		final I_C_Phonecall_Schema_Version schemaVersionRecord = load(schemaVersionId, I_C_Phonecall_Schema_Version.class);

		if (schemaVersionRecord.isCancelPhonecallDay())
		{
			return OnNonBussinessDay.Cancel;
		}
		else if (schemaVersionRecord.isMovePhonecallDay())
		{
			return OnNonBussinessDay.MoveToClosestBusinessDay;
		}
		else
		{
			return null;
		}
	}

	public void updateLinesOnSchemaChanged(@NonNull final PhonecallSchemaVersionId phonecallSchemaVersionId)
	{
		final int phonecallSchemaTableId = getTableId(I_C_Phonecall_Schema.class);

		final PhonecallSchemaId phonecallSchemaId = phonecallSchemaVersionId.getPhonecallSchemaId();
		final List<I_C_Phonecall_Schema_Version_Line> linesWithDifferentSchema = retrieveLinesWithDifferentSchemas(phonecallSchemaVersionId);

		final ImmutableSet<Integer> schemasToInvalidate = linesWithDifferentSchema.stream()
				.map(line -> line.getC_Phonecall_Schema_ID())
				.collect(ImmutableSet.toImmutableSet());

		for (final I_C_Phonecall_Schema_Version_Line line : linesWithDifferentSchema)
		{
			line.setC_Phonecall_Schema_ID(phonecallSchemaId.getRepoId());
			saveRecord(line);
		}

		schemasToInvalidate.stream()
				.forEach(schemaId -> schemas.resetForRecordId(TableRecordReference.of(phonecallSchemaTableId, schemaId)));

		schemas.resetForRecordId(TableRecordReference.of(phonecallSchemaTableId, phonecallSchemaId.getRepoId()));

	}

	private List<I_C_Phonecall_Schema_Version_Line> retrieveLinesWithDifferentSchemas(@NonNull final PhonecallSchemaVersionId phonecallSchemaVersionId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Phonecall_Schema_Version_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Phonecall_Schema_Version_Line.COLUMNNAME_C_Phonecall_Schema_Version_ID, phonecallSchemaVersionId)
				.addNotEqualsFilter(I_C_Phonecall_Schema_Version_Line.COLUMNNAME_C_Phonecall_Schema_ID, phonecallSchemaVersionId.getPhonecallSchemaId())
				.create()
				.list();
	}

	public void updateSchedulesOnSchemaChanged(@NonNull final PhonecallSchemaVersionId phonecallSchemaVersionId)
	{

		final PhonecallSchemaId phonecallSchemaId = phonecallSchemaVersionId.getPhonecallSchemaId();
		final List<I_C_Phonecall_Schedule> schedulesWithDifferentSchema = retrieveSchedulesWithDifferentSchemas(phonecallSchemaVersionId);

		for (final I_C_Phonecall_Schedule schedule : schedulesWithDifferentSchema)
		{
			schedule.setC_Phonecall_Schema_ID(phonecallSchemaId.getRepoId());
			saveRecord(schedule);
		}

	}

	private List<I_C_Phonecall_Schedule> retrieveSchedulesWithDifferentSchemas(@NonNull final PhonecallSchemaVersionId phonecallSchemaVersionId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Phonecall_Schedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_C_Phonecall_Schema_Version_ID, phonecallSchemaVersionId)
				.addNotEqualsFilter(I_C_Phonecall_Schedule.COLUMNNAME_C_Phonecall_Schema_ID, phonecallSchemaVersionId.getPhonecallSchemaId())
				.create()
				.list();
	}
}

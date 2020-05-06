package de.metas.phonecall.service;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Phonecall_Schedule;
import org.compiere.model.I_C_Phonecall_Schema;
import org.compiere.model.I_C_Phonecall_Schema_Version;
import org.compiere.model.I_C_Phonecall_Schema_Version_Line;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.phonecall.PhonecallSchedule;
import de.metas.phonecall.PhonecallSchema;
import de.metas.phonecall.PhonecallSchemaId;
import de.metas.phonecall.PhonecallSchemaVersionId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

@ExtendWith(AdempiereTestWatcher.class)
public class PhonecallScheduleServiceTest
{
	private PhonecallSchemaRepository schemaRepo;
	private PhonecallScheduleRepository schedulesRepo;
	private PhonecallScheduleService phonecallScheduleService;

	private BPartnerId bpartnerId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		schedulesRepo = new PhonecallScheduleRepository();
		schemaRepo = new PhonecallSchemaRepository();
		phonecallScheduleService = new PhonecallScheduleService(schedulesRepo, schemaRepo);

		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

		//
		// Masterdata:
		bpartnerId = createBPartner();
	}

	private BPartnerId createBPartner()
	{
		I_C_BPartner record = newInstance(I_C_BPartner.class);
		saveRecord(record);
		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}

	private PhonecallSchemaId createPhonecallSchema()
	{
		final I_C_Phonecall_Schema schema = newInstance(I_C_Phonecall_Schema.class);
		schema.setName("test");
		saveRecord(schema);
		return PhonecallSchemaId.ofRepoId(schema.getC_Phonecall_Schema_ID());
	}

	@Builder(builderMethodName = "preparePhonecallSchemaVersion", builderClassName = "PhonecallSchemaVersionBuilder")
	private PhonecallSchemaVersionId createPhonecallSchemaVersion(
			final boolean weekly,
			final int everyWeek,
			final Set<DayOfWeek> dayOfWeek)
	{
		final PhonecallSchemaId schemaId = createPhonecallSchema();
		final I_C_Phonecall_Schema_Version schemaVersion = newInstance(I_C_Phonecall_Schema_Version.class);
		schemaVersion.setC_Phonecall_Schema_ID(schemaId.getRepoId());
		schemaVersion.setName("test");
		schemaVersion.setValidFrom(TimeUtil.asTimestamp(LocalDate.of(1970, Month.JANUARY, 1)));

		schemaVersion.setIsWeekly(weekly);
		schemaVersion.setEveryWeek(everyWeek);
		schemaVersion.setOnMonday(dayOfWeek != null && dayOfWeek.contains(DayOfWeek.MONDAY));
		schemaVersion.setOnTuesday(dayOfWeek != null && dayOfWeek.contains(DayOfWeek.TUESDAY));
		schemaVersion.setOnWednesday(dayOfWeek != null && dayOfWeek.contains(DayOfWeek.WEDNESDAY));
		schemaVersion.setOnThursday(dayOfWeek != null && dayOfWeek.contains(DayOfWeek.THURSDAY));
		schemaVersion.setOnFriday(dayOfWeek != null && dayOfWeek.contains(DayOfWeek.FRIDAY));
		schemaVersion.setOnSaturday(dayOfWeek != null && dayOfWeek.contains(DayOfWeek.SATURDAY));
		schemaVersion.setOnSunday(dayOfWeek != null && dayOfWeek.contains(DayOfWeek.SUNDAY));

		saveRecord(schemaVersion);
		return PhonecallSchemaVersionId.ofRepoId(schemaId, schemaVersion.getC_Phonecall_Schema_Version_ID());
	}

	@Builder(builderMethodName = "preparePhonecallSchemaLine", builderClassName = "PhonecallSchemaLineBuilder")
	private void createPhonecallSchemaLine(
			@NonNull final PhonecallSchemaVersionId schemaVersionId,
			@NonNull final BPartnerLocationId bpartnerAndLocationId,
			@NonNull final LocalTime timeMin,
			@NonNull final LocalTime timeMax,
			final String description)
	{
		final I_C_Phonecall_Schema_Version_Line record = newInstance(I_C_Phonecall_Schema_Version_Line.class);
		record.setC_Phonecall_Schema_ID(schemaVersionId.getPhonecallSchemaId().getRepoId());
		record.setC_Phonecall_Schema_Version_ID(schemaVersionId.getRepoId());

		record.setC_BPartner_ID(bpartnerAndLocationId.getBpartnerId().getRepoId());
		record.setC_BPartner_Location_ID(bpartnerAndLocationId.getRepoId());

		record.setPhonecallTimeMin(TimeUtil.asTimestamp(timeMin));
		record.setPhonecallTimeMax(TimeUtil.asTimestamp(timeMax));

		record.setDescription(description);

		saveRecord(record);
	}

	public List<PhonecallSchedule> getAllPhonecallSchedules()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Phonecall_Schedule.class)
				.orderBy(I_C_Phonecall_Schedule.COLUMNNAME_C_Phonecall_Schedule_ID)
				.create()
				.stream()
				.map(PhonecallScheduleRepository::toPhonecallSchedule)
				.collect(ImmutableList.toImmutableList());
	}

	@Test
	public void check_description_from_schemaLine_to_schedule()
	{
		final BPartnerLocationId bpartnerAndLocationId = BPartnerLocationId.ofRepoId(bpartnerId, 1);

		final PhonecallSchemaVersionId schemaVersionId = preparePhonecallSchemaVersion()
				.weekly(true)
				.everyWeek(1)
				.dayOfWeek(ImmutableSet.copyOf(DayOfWeek.values()))
				.build();
		preparePhonecallSchemaLine()
				.schemaVersionId(schemaVersionId)
				.bpartnerAndLocationId(bpartnerAndLocationId)
				.timeMin(LocalTime.of(10, 23))
				.timeMax(LocalTime.of(11, 24))
				.description("schema line description")
				.build();

		final PhonecallSchema schema = schemaRepo.getById(schemaVersionId.getPhonecallSchemaId());

		phonecallScheduleService.generatePhonecallSchedulesForSchema(schema,
				LocalDate.of(2019, Month.DECEMBER, 9),
				LocalDate.of(2019, Month.DECEMBER, 9));

		final List<PhonecallSchedule> schedules = getAllPhonecallSchedules();
		assertThat(schedules).hasSize(1);

		final PhonecallSchedule schedule = schedules.get(0);
		assertThat(schedule.getDescription()).isEqualTo("schema line description");
	}
}

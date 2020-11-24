package de.metas.phonecall.service;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_C_Phonecall_Schedule;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.organization.OrgId;
import de.metas.phonecall.PhonecallSchedule;
import de.metas.phonecall.PhonecallScheduleId;
import de.metas.phonecall.PhonecallSchemaVersionId;
import de.metas.phonecall.PhonecallSchemaVersionLineId;
import de.metas.user.UserId;
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
public class PhonecallScheduleRepository
{
	public void save(@NonNull final PhonecallSchedule schedule)
	{
		final I_C_Phonecall_Schedule scheduleRecord;

		if (schedule.getId() == null)
		{
			scheduleRecord = newInstance(I_C_Phonecall_Schedule.class);
		}
		else
		{
			scheduleRecord = load(schedule.getId().getRepoId(), I_C_Phonecall_Schedule.class);
		}

		scheduleRecord.setC_BPartner_ID(schedule.getBpartnerAndLocationId().getBpartnerId().getRepoId());
		scheduleRecord.setC_BPartner_Location_ID(schedule.getBpartnerAndLocationId().getRepoId());
		scheduleRecord.setC_BP_Contact_ID(schedule.getContactId().getRepoId());

		scheduleRecord.setC_Phonecall_Schema_ID(schedule.getPhonecallSchemaId().getRepoId());
		scheduleRecord.setC_Phonecall_Schema_Version_ID(schedule.getPhonecallSchemaVersionId().getRepoId());
		scheduleRecord.setC_Phonecall_Schema_Version_Line_ID(schedule.getSchemaVersionLineId().getRepoId());

		scheduleRecord.setPhonecallDate(TimeUtil.asTimestamp(schedule.getDate()));
		scheduleRecord.setPhonecallTimeMin(TimeUtil.asTimestamp(schedule.getStartTime()));
		scheduleRecord.setPhonecallTimeMax(TimeUtil.asTimestamp(schedule.getEndTime()));

		scheduleRecord.setIsCalled(schedule.isCalled());
		scheduleRecord.setIsOrdered(schedule.isOrdered());

		scheduleRecord.setSalesRep_ID(UserId.toRepoId(schedule.getSalesRepId()));

		scheduleRecord.setDescription(schedule.getDescription());

		saveRecord(scheduleRecord);
	}

	public PhonecallSchedule getById(@NonNull final PhonecallScheduleId scheduleId)
	{
		final I_C_Phonecall_Schedule scheduleRecord = load(scheduleId, I_C_Phonecall_Schedule.class);

		return toPhonecallSchedule(scheduleRecord);
	}

	@VisibleForTesting
	static PhonecallSchedule toPhonecallSchedule(final I_C_Phonecall_Schedule record)
	{
		final PhonecallSchemaVersionId schemaVersionId = PhonecallSchemaVersionId.ofRepoId(
				record.getC_Phonecall_Schema_ID(),
				record.getC_Phonecall_Schema_Version_ID());

		return PhonecallSchedule.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID()))
				.contactId(UserId.ofRepoId(record.getC_BP_Contact_ID()))
				.date(TimeUtil.asLocalDate(record.getPhonecallDate()))
				.startTime(TimeUtil.asZonedDateTime(record.getPhonecallTimeMin()))
				.endTime(TimeUtil.asZonedDateTime(record.getPhonecallTimeMax()))
				.id(PhonecallScheduleId.ofRepoId(record.getC_Phonecall_Schedule_ID()))
				.schemaVersionLineId(PhonecallSchemaVersionLineId.ofRepoId(
						schemaVersionId,
						record.getC_Phonecall_Schema_Version_Line_ID()))
				.isOrdered(record.isOrdered())
				.isCalled(record.isCalled())
				.salesRepId(UserId.ofRepoIdOrNull(record.getSalesRep_ID()))
				.description(record.getDescription())
				.build();
	}

	public void markAsOrdered(@NonNull final PhonecallSchedule schedule)
	{
		final I_C_Phonecall_Schedule scheduleRecord = load(schedule.getId(), I_C_Phonecall_Schedule.class);

		scheduleRecord.setIsCalled(true);
		scheduleRecord.setIsOrdered(true);

		saveRecord(scheduleRecord);
	}

	public void markAsCalled(@NonNull final PhonecallSchedule schedule)
	{
		final I_C_Phonecall_Schedule scheduleRecord = load(schedule.getId(), I_C_Phonecall_Schedule.class);

		scheduleRecord.setIsCalled(true);

		saveRecord(scheduleRecord);
	}

}

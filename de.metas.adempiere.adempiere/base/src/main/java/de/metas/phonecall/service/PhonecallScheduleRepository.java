package de.metas.phonecall.service;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_C_Phonecall_Schedule;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.phonecall.PhonecallSchedule;
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
		final I_C_Phonecall_Schedule phonecallScheduleRecord;

		if (schedule.getId() == null)
		{
			phonecallScheduleRecord = newInstance(I_C_Phonecall_Schedule.class);
		}
		else
		{
			phonecallScheduleRecord = load(schedule.getId().getRepoId(), I_C_Phonecall_Schedule.class);
		}

		phonecallScheduleRecord.setC_BPartner_ID(schedule.getBpartnerAndLocationId().getBpartnerId().getRepoId());
		phonecallScheduleRecord.setC_BPartner_Location_ID(schedule.getBpartnerAndLocationId().getRepoId());
		phonecallScheduleRecord.setAD_User_ID(schedule.getContactId().getRepoId());

		phonecallScheduleRecord.setC_Phonecall_Schema_ID(schedule.getPhonecallSchemaId().getRepoId());
		phonecallScheduleRecord.setC_Phonecall_Schema_Version_ID(schedule.getPhonecallSchemaVersionId().getRepoId());
		phonecallScheduleRecord.setC_Phonecall_Schema_Version_Line_ID(schedule.getSchemaVersionLineId().getRepoId());

		phonecallScheduleRecord.setPhonecallDate(TimeUtil.asTimestamp(schedule.getDate()));
		phonecallScheduleRecord.setPhonecallTimeMin(TimeUtil.asTimestamp(schedule.getStartTime()));
		phonecallScheduleRecord.setPhonecallTimeMax(TimeUtil.asTimestamp(schedule.getEndTime()));

		saveRecord(phonecallScheduleRecord);
	}
}

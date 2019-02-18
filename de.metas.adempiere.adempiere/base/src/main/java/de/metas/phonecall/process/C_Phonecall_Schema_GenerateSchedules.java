package de.metas.phonecall.process;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.compiere.Adempiere;
import org.compiere.util.TimeUtil;

import de.metas.phonecall.PhonecallSchemaId;
import de.metas.phonecall.service.PhonecallScheduleService;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

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

public class C_Phonecall_Schema_GenerateSchedules extends JavaProcess
{
	private final PhonecallScheduleService phonecallScheduleService = Adempiere.getBean(PhonecallScheduleService.class);

	@Param(parameterName = "Date")
	private Timestamp p_Date;

	@Override
	protected String doIt()
	{
		final PhonecallSchemaId phonecallSchemaId = PhonecallSchemaId.ofRepoId(getRecord_ID());
		final LocalDate date = TimeUtil.asLocalDate(p_Date);

		phonecallScheduleService.generatePhonecallSchedule(phonecallSchemaId, date);

		return MSG_OK;
	}
}

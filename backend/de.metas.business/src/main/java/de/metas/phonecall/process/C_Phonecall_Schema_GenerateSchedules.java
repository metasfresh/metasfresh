package de.metas.phonecall.process;

import java.time.LocalDate;

import org.compiere.SpringContextHolder;

import de.metas.phonecall.PhonecallSchema;
import de.metas.phonecall.PhonecallSchemaId;
import de.metas.phonecall.service.PhonecallScheduleService;
import de.metas.phonecall.service.PhonecallSchemaRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;

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

public class C_Phonecall_Schema_GenerateSchedules extends JavaProcess implements IProcessPrecondition
{
	private final PhonecallScheduleService phonecallScheduleService = SpringContextHolder.instance.getBean(PhonecallScheduleService.class);
	private final PhonecallSchemaRepository schemaRepo = SpringContextHolder.instance.getBean(PhonecallSchemaRepository.class);

	@Param(parameterName = "DateFrom")
	private LocalDate p_StartDate;

	@Param(parameterName = "DateTo")
	private LocalDate p_EndDate;

	@Override
	protected String doIt()
	{
		final PhonecallSchema phonecallSchema = schemaRepo.getById(PhonecallSchemaId.ofRepoId(getRecord_ID()));

		phonecallScheduleService.generatePhonecallSchedulesForSchema(phonecallSchema, p_StartDate, p_EndDate);

		return MSG_OK;
	}


	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if(!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}
}

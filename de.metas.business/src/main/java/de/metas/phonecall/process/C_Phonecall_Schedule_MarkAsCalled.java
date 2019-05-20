package de.metas.phonecall.process;

import org.compiere.Adempiere;

import de.metas.phonecall.PhonecallSchedule;
import de.metas.phonecall.PhonecallScheduleId;
import de.metas.phonecall.service.PhonecallScheduleRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

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

public class C_Phonecall_Schedule_MarkAsCalled extends JavaProcess implements IProcessPrecondition
{
	private final PhonecallScheduleRepository phonecallSchedueRepo = Adempiere.getBean(PhonecallScheduleRepository.class);

	@Override
	protected String doIt()
	{
		final PhonecallSchedule phonecallSchedule = phonecallSchedueRepo.getById(PhonecallScheduleId.ofRepoId(getRecord_ID()));
		phonecallSchedueRepo.markAsCalled(phonecallSchedule);

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

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.adempiere.process;

import de.metas.adempiere.scheduler.SchedulerServiceImpl;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Scheduler;

public class EnableDisableScheduler extends JavaProcess
{
	public static final String AD_SCHEDULER_ID = "AD_Scheduler_ID";
	@Param(parameterName = AD_SCHEDULER_ID)
	protected int adSchedulerId;

	@Override
	protected String doIt() throws Exception
	{

		final I_AD_Scheduler adScheduler = InterfaceWrapperHelper.load(adSchedulerId, I_AD_Scheduler.class);

		if (adScheduler.isActive())
		{
			SpringContextHolder.instance.getBean(SchedulerServiceImpl.class).disableScheduler(adScheduler);
			return MSG_OK;
		}

		SpringContextHolder.instance.getBean(SchedulerServiceImpl.class).activateScheduler(adScheduler);

		return MSG_OK;
	}
}

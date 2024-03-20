/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.scheduler;

import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.scheduler.SchedulerAction;
import de.metas.scheduler.SchedulerSearchKey;
import de.metas.scheduler.eventbus.ManageSchedulerRequest;
import de.metas.scheduler.eventbus.SchedulerEventBusService;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

public class AD_Scheduler_StepDef
{
	private final SchedulerEventBusService schedulerEventBusService = SpringContextHolder.instance.getBean(SchedulerEventBusService.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	@And("AD_Scheduler for classname {string} is disabled")
	public void disable_AD_Scheduler_for_className(@NonNull final String className)
	{
		final AdProcessId targetProcessId = adProcessDAO.retrieveProcessIdByClassIfUnique(className);

		Check.assumeNotNull(targetProcessId, "There should always be an AD_Process record for classname:" + className);

		schedulerEventBusService.postRequest(ManageSchedulerRequest.builder()
													 .schedulerSearchKey(SchedulerSearchKey.of(targetProcessId))
													 .clientId(Env.getClientId())
													 .schedulerAction(SchedulerAction.DISABLE)
													 .supervisorAction(ManageSchedulerRequest.SupervisorAction.DISABLE)
													 .build());
	}

	@And("AD_Scheduler for classname {string} is enabled")
	public void enable_AD_Scheduler_for_className(@NonNull final String className)
	{
		final AdProcessId targetProcessId = adProcessDAO.retrieveProcessIdByClassIfUnique(className);

		Check.assumeNotNull(targetProcessId, "There should always be an AD_Process record for classname:" + className);

		schedulerEventBusService.postRequest(ManageSchedulerRequest.builder()
													 .schedulerSearchKey(SchedulerSearchKey.of(targetProcessId))
													 .clientId(Env.getClientId())
													 .schedulerAction(SchedulerAction.ENABLE)
													 .supervisorAction(ManageSchedulerRequest.SupervisorAction.ENABLE)
													 .build());
	}

	@And("AD_Scheduler for classname {string} is ran once")
	public void runOnceNow_AD_Scheduler_for_className(@NonNull final String className)
	{
		final AdProcessId targetProcessId = adProcessDAO.retrieveProcessIdByClassIfUnique(className);

		Check.assumeNotNull(targetProcessId, "There should always be an AD_Process record for classname:" + className);

		schedulerEventBusService.postRequest(ManageSchedulerRequest.builder()
				.schedulerSearchKey(SchedulerSearchKey.of(targetProcessId))
				.clientId(Env.getClientId())
				.schedulerAction(SchedulerAction.RESTART)
				.build());

		schedulerEventBusService.postRequest(ManageSchedulerRequest.builder()
				.schedulerSearchKey(SchedulerSearchKey.of(targetProcessId))
				.clientId(Env.getClientId())
				.schedulerAction(SchedulerAction.RUN_ONCE)
				.build());
	}
}
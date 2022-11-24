/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.scheduler.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.scheduler.AdSchedulerId;
import de.metas.scheduler.SchedulerAction;
import de.metas.scheduler.SchedulerSearchKey;
import de.metas.scheduler.eventbus.ManageSchedulerRequest;
import de.metas.scheduler.eventbus.SchedulerEventBusService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import javax.annotation.Nullable;

public class AD_Scheduler_Controller extends JavaProcess implements IProcessPrecondition
{
	final SchedulerEventBusService schedulerEventBusService = SpringContextHolder.instance.getBean(SchedulerEventBusService.class);

	@Param(parameterName = "Action", mandatory = true)
	private String p_Action;

	@Param(parameterName = "DeactivateSupervisor")
	private boolean deactivateSupervisor;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final AdSchedulerId adSchedulerId = AdSchedulerId.ofRepoId(getRecord_ID());

		switch (SchedulerAction.ofCode(p_Action))
		{
			case ENABLE:
				sendManageSchedulerRequest(adSchedulerId, SchedulerAction.ENABLE, ManageSchedulerRequest.SupervisorAction.ENABLE);
				break;
			case DISABLE:
				final ManageSchedulerRequest.SupervisorAction supervisorAction = deactivateSupervisor ? ManageSchedulerRequest.SupervisorAction.DISABLE : null;
				sendManageSchedulerRequest(adSchedulerId, SchedulerAction.DISABLE, supervisorAction);
				break;
			case RUN_ONCE:
				sendManageSchedulerRequest(adSchedulerId, SchedulerAction.RUN_ONCE, null);
				break;
			case RESTART:
				sendManageSchedulerRequest(adSchedulerId, SchedulerAction.RESTART, ManageSchedulerRequest.SupervisorAction.ENABLE);
				break;
			default:
				throw new AdempiereException("Unsupported action!")
						.appendParametersToMessage()
						.setParameter("action", p_Action);
		}

		return MSG_OK;
	}

	private void sendManageSchedulerRequest(
			@NonNull final AdSchedulerId adSchedulerId,
			@NonNull final SchedulerAction schedulerAction,
			@Nullable final ManageSchedulerRequest.SupervisorAction supervisorAction)
	{
		schedulerEventBusService.postRequest(ManageSchedulerRequest.builder()
													 .schedulerSearchKey(SchedulerSearchKey.of(adSchedulerId))
													 .clientId(Env.getClientId())
													 .schedulerAction(schedulerAction)
													 .supervisorAction(supervisorAction)
													 .build());
	}
}
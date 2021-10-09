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

package de.metas.adempiere.scheduler;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.scheduler.eventbus.ManageSchedulerRequest;
import de.metas.scheduler.eventbus.ManageSchedulerRequestHandler;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.scheduler.SchedulerDao;
import org.compiere.model.I_AD_Scheduler;
import org.compiere.model.I_AD_User;
import org.compiere.model.MScheduler;
import org.compiere.server.AdempiereServer;
import org.compiere.server.AdempiereServerGroup;
import org.compiere.server.AdempiereServerMgr;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchedulerService implements ManageSchedulerRequestHandler
{
	private static final Logger logger = LogManager.getLogger(SchedulerService.class);

	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	private final SchedulerDao schedulerDao;

	public SchedulerService(@NonNull final SchedulerDao schedulerDao)
	{
		this.schedulerDao = schedulerDao;
	}

	@Override
	public void handleRequest(final ManageSchedulerRequest request)
	{
		final I_AD_Scheduler scheduler = schedulerDao.getSchedulerByProcessIdIfUnique(request.getAdProcessId())
				.orElseThrow(() -> new AdempiereException("No scheduler found for process")
						.appendParametersToMessage()
						.setParameter("adProcessId", request.getAdProcessId()));

		Optional.ofNullable(request.getSupervisorAdvice())
				.ifPresent(supervisorAdvice -> handleSupervisor(scheduler, supervisorAdvice));

		if (ManageSchedulerRequest.Advice.ENABLE.equals(request.getSchedulerAdvice()))
		{
			activateScheduler(scheduler);
			startScheduler(scheduler);
		}
		else
		{
			deactivateScheduler(scheduler);
			stopScheduler(scheduler);
		}
	}

	private void handleSupervisor(@NonNull final I_AD_Scheduler scheduler, @NonNull final ManageSchedulerRequest.Advice supervisorAdvice)
	{
		final UserId supervisorId = UserId.ofRepoIdOrNull(scheduler.getSupervisor_ID());

		if (supervisorId == null)
		{
			return;
		}

		final I_AD_User user = userDAO.getById(supervisorId);
		user.setIsActive(ManageSchedulerRequest.Advice.ENABLE.equals(supervisorAdvice));

		userDAO.save(user);
	}

	private void activateScheduler(@NonNull final I_AD_Scheduler scheduler)
	{
		scheduler.setIsActive(true);
		schedulerDao.save(scheduler);
	}

	private void deactivateScheduler(@NonNull final I_AD_Scheduler scheduler)
	{
		scheduler.setIsActive(false);
		schedulerDao.save(scheduler);
	}

	private void startScheduler(@NonNull final I_AD_Scheduler adScheduler)
	{
		final MScheduler schedulerModel = new MScheduler(Env.getCtx(), adScheduler.getAD_Scheduler_ID(), null);

		final AdempiereServerMgr adempiereServerMgr = AdempiereServerMgr.get();

		final AdempiereServer scheduler = adempiereServerMgr.getServer(schedulerModel.getServerID());
		if (scheduler == null)
		{
			adempiereServerMgr.add(schedulerModel);
			Loggables.withLogger(logger, Level.DEBUG).addLog("Created server scheduler for " + schedulerModel.getName());
		}

		adempiereServerMgr.start(schedulerModel.getServerID());
		Loggables.withLogger(logger, Level.DEBUG).addLog("Started " + schedulerModel.getName());
	}

	private void stopScheduler(@NonNull final I_AD_Scheduler adScheduler)
	{
		final MScheduler schedulerModel = new MScheduler(Env.getCtx(), adScheduler.getAD_Scheduler_ID(), null);

		final AdempiereServerMgr adempiereServerMgr = AdempiereServerMgr.get();

		adempiereServerMgr.stop(schedulerModel.getServerID());

		adempiereServerMgr.removeServerWithId(schedulerModel.getServerID());

		AdempiereServerGroup.get().dump();
	}
}

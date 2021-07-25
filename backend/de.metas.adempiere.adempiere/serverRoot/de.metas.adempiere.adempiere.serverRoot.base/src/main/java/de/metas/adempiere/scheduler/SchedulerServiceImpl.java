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
import de.metas.process.AdProcessId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
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
public class SchedulerServiceImpl
{
	private static final Logger logger = LogManager.getLogger(SchedulerServiceImpl.class);

	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	private final SchedulerDao schedulerDao;

	public SchedulerServiceImpl(@NonNull final SchedulerDao schedulerDao)
	{
		this.schedulerDao = schedulerDao;
	}

	public void enableScheduler(@NonNull final I_AD_Scheduler scheduler)
	{
		activateScheduler(scheduler);

		final UserId supervisorId = UserId.ofRepoIdOrNull(scheduler.getSupervisor_ID());

		if (supervisorId != null)
		{
			activateSupervisor(supervisorId);
		}

		startScheduler(scheduler);
	}

	public void disableScheduler(@NonNull final I_AD_Scheduler scheduler)
	{
		deactivateScheduler(scheduler);

		final UserId supervisorId = UserId.ofRepoIdOrNull(scheduler.getSupervisor_ID());

		if (supervisorId != null)
		{
			deactivateSupervisor(supervisorId);
		}

		stopScheduler(scheduler);
	}

	public Optional<I_AD_Scheduler> getSchedulerByProcessIdIfUnique(@NonNull final AdProcessId processId)
	{
		return schedulerDao.getSchedulerByProcessIdIfUnique(processId);
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

	private void activateSupervisor(@NonNull final UserId userId)
	{
		final I_AD_User user = userDAO.getById(userId);
		user.setIsActive(true);
		userDAO.save(user);
	}

	private void deactivateSupervisor(@NonNull final UserId userId)
	{
		final I_AD_User user = userDAO.getById(userId);
		user.setIsActive(false);
		userDAO.save(user);
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

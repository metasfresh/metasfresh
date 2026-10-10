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

import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.scheduler.AdSchedulerId;
import de.metas.scheduler.SchedulerAction;
import de.metas.scheduler.SchedulerDao;
import de.metas.scheduler.SchedulerSearchKey;
import de.metas.scheduler.eventbus.ManageSchedulerRequest;
import de.metas.scheduler.eventbus.SchedulerEventBusService;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import it.sauronsoftware.cron4j.SchedulingPattern;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Scheduler;
import org.compiere.model.MScheduler;
import org.compiere.model.X_AD_Scheduler;
import org.compiere.server.AdempiereServer;
import org.compiere.server.AdempiereServerMgr;
import org.compiere.util.Env;

import javax.annotation.Nullable;

public class AD_Scheduler_StepDef
{
	private static final int RESTART_SETTLE_TIMEOUT_SECONDS = 60;

	private final SchedulerEventBusService schedulerEventBusService = SpringContextHolder.instance.getBean(SchedulerEventBusService.class);
	private final SchedulerDao schedulerDao = SpringContextHolder.instance.getBean(SchedulerDao.class);
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

	/**
	 * Restarts the scheduler of the given process and then runs it once.
	 * <p>
	 * Waits for the restart to settle before requesting the run. A restarted cron scheduler's server thread saves
	 * its {@code AD_Scheduler} record ({@code setDateNextRun}) right after it starts. RUN_ONCE saves the same,
	 * shared record object ({@code setSchedulerStatus}) on the event-bus thread. If the two saves overlap,
	 * RUN_ONCE fails with "trxName shall not be null" and the process never runs.
	 */
	@And("AD_Scheduler for classname {string} is ran once")
	public void runOnceNow_AD_Scheduler_for_className(@NonNull final String className) throws InterruptedException
	{
		final AdProcessId targetProcessId = adProcessDAO.retrieveProcessIdByClassIfUnique(className);

		Check.assumeNotNull(targetProcessId, "There should always be an AD_Process record for classname:" + className);

		final I_AD_Scheduler adScheduler = schedulerDao.getSchedulerByProcessIdIfUnique(targetProcessId)
				.orElseThrow(() -> new AdempiereException("No unique AD_Scheduler found for classname:" + className));
		final String serverId = MScheduler.computeServerID(AdSchedulerId.ofRepoId(adScheduler.getAD_Scheduler_ID()));
		// same condition as Scheduler.run(): only a valid cron pattern makes the server thread save at start-up
		final boolean cronScheduler = X_AD_Scheduler.SCHEDULETYPE_CronSchedulingPattern.equals(adScheduler.getScheduleType())
				&& Check.isNotBlank(adScheduler.getCronPattern())
				&& SchedulingPattern.validate(adScheduler.getCronPattern());
		final AdempiereServer serverBeforeRestart = AdempiereServerMgr.get().getServer(serverId);

		schedulerEventBusService.postRequest(ManageSchedulerRequest.builder()
				.schedulerSearchKey(SchedulerSearchKey.of(targetProcessId))
				.clientId(Env.getClientId())
				.schedulerAction(SchedulerAction.RESTART)
				.build());

		StepDefUtil.tryAndWait(
				RESTART_SETTLE_TIMEOUT_SECONDS,
				100,
				() -> isRestartSettled(serverId, serverBeforeRestart, cronScheduler));

		schedulerEventBusService.postRequest(ManageSchedulerRequest.builder()
				.schedulerSearchKey(SchedulerSearchKey.of(targetProcessId))
				.clientId(Env.getClientId())
				.schedulerAction(SchedulerAction.RUN_ONCE)
				.build());
	}

	/**
	 * @return true when the server that RESTART started is running and, for a cron scheduler, has finished its
	 * start-up save and gone to sleep ({@code Scheduler.run()} sets the next run date before its first sleep).
	 */
	private static boolean isRestartSettled(
			@NonNull final String serverId,
			@Nullable final AdempiereServer serverBeforeRestart,
			final boolean cronScheduler)
	{
		final AdempiereServer server;
		try
		{
			server = AdempiereServerMgr.get().getServer(serverId);
		}
		catch (final IndexOutOfBoundsException e)
		{
			// getServer() iterates an unsynchronized list by index that RESTART modifies concurrently; just poll again
			return false;
		}
		if (server == null || server == serverBeforeRestart || !server.isAlive())
		{
			return false;
		}
		return !cronScheduler || server.isSleeping();
	}
}

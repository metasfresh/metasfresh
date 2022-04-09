package org.adempiere.ad.housekeeping;

import ch.qos.logback.classic.Level;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

@Service
public class HouseKeepingService
{
	private static final Logger logger = LogManager.getLogger(HouseKeepingService.class);

	public static final String SYSCONFIG_SKIP_HOUSE_KEEPING = "de.metas.housekeeping.SkipHouseKeeping";

	private final ImmutableList<IStartupHouseKeepingTask> startupTasks;

	public HouseKeepingService(final Optional<List<IStartupHouseKeepingTask>> startupTasks)
	{
		this.startupTasks = startupTasks.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
		logger.info("Startup tasks: {}", this.startupTasks);
	}

	public void runStartupHouseKeepingTasks()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		// first check if we shall run at all
		final boolean skipHouseKeeping = sysConfigBL.getBooleanValue(SYSCONFIG_SKIP_HOUSE_KEEPING, false);
		if (skipHouseKeeping)
		{
			logger.warn("SysConfig {} = {} => skipping execution of the housekeeping tasks", new Object[] { SYSCONFIG_SKIP_HOUSE_KEEPING, skipHouseKeeping });
			return;
		}

		logger.info("Executing the registered house keeping tasks; disable with SysConfig {}=true", SYSCONFIG_SKIP_HOUSE_KEEPING);
		final Stopwatch allTasksWatch = Stopwatch.createStarted();

		final ILoggable loggable = Loggables.logback(logger, Level.INFO);
		try (final IAutoCloseable temporaryLoggable = Loggables.temporarySetLoggable(loggable);)
		{
			for (final IStartupHouseKeepingTask task : startupTasks)
			{
				//
				final String taskName = task.getClass().getName();
				final String sysConfigSkipTaskName = SYSCONFIG_SKIP_HOUSE_KEEPING + "." + taskName;
				final boolean skipTask = sysConfigBL.getBooleanValue(sysConfigSkipTaskName, false);

				if (skipTask)
				{
					logger.warn("SysConfig {}={} => skipping execution of task {}", sysConfigSkipTaskName, skipTask, taskName);
					continue;
				}

				logger.info("Executing task {}; disable with SysConfig {}=true", taskName, sysConfigSkipTaskName);

				final Stopwatch currentTaskWatch = Stopwatch.createStarted();
				try
				{
					task.executeTask();
					logger.info("Finished executing task {}; elapsed time={}", taskName, currentTaskWatch.stop());
				}
				catch (Exception e)
				{
					logger.warn("Failed to execute task {}; skipped; elapsed time={}", taskName, e, currentTaskWatch.stop());
				}
			}
		}
		final String elapsedTime = allTasksWatch.stop().toString();
		logger.info("Finished executing the house keeping tasks; overall elapsed time={}", elapsedTime);
	}
}

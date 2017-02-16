package org.adempiere.ad.housekeeping.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.ad.housekeeping.IHouseKeepingBL;
import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.LoggerLoggable;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;

public class HouseKeepingBL implements IHouseKeepingBL
{
	private static final Logger logger = LogManager.getLogger(HouseKeepingBL.class);

	final CopyOnWriteArrayList<IStartupHouseKeepingTask> startupTasks = new CopyOnWriteArrayList<>();

	@Override
	public void registerStartupHouseKeepingTask(final IStartupHouseKeepingTask task)
	{
		Check.assumeNotNull(task, "task not null");
		startupTasks.addIfAbsent(task);
	}

	@Override
	public void runStartupHouseKeepingTasks()
	{
		logger.info("Executing the registered house keeping tasks");

		final ILoggable loggable = LoggerLoggable.of(logger, Level.INFO);

		try (final IAutoCloseable temporaryLoggable = Loggables.temporarySetLoggable(loggable);)
		{
			for (final IStartupHouseKeepingTask task : startupTasks)
			{
				logger.info("Executing task " + task.getClass().getName());
				try
				{
					task.executeTask();
				}
				catch (Exception e)
				{
					logger.warn("Failed to execute task {}. Skipped", task, e);
				}
			}
		}

		logger.info("Finished executing the house keeping tasks");
	}

}

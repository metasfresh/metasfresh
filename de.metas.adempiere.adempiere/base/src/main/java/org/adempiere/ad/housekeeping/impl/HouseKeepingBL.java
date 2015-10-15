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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.adempiere.ad.housekeeping.IHouseKeepingBL;
import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.adempiere.util.CLoggerLoggable;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.compiere.util.CLogger;

public class HouseKeepingBL implements IHouseKeepingBL
{
	private static final CLogger logger = CLogger.getCLogger(HouseKeepingBL.class);

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

		final ILoggable loggable = new CLoggerLoggable(logger, Level.INFO);
		for (final IStartupHouseKeepingTask task : startupTasks)
		{
			logger.info("Executing task " + task.getClass().getName());

			try
			{
				task.executeTask(loggable);
			}
			catch (Exception e)
			{
				logger.log(Level.WARNING, "Failed to execute task " + task + ". Skipped", e);
			}
		}
	}

}

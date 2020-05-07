package de.metas.lock.housekeeping.spi.impl;

/*
 * #%L
 * de.metas.async
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

import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;

import de.metas.lock.api.ILockManager;

/**
 * Deletes lock records which were flagged with "auto-cleanup".
 *
 * @author ts
 * @author tsa
 * @task 06295
 */
public class ClearLocks implements IStartupHouseKeepingTask
{
	@Override
	public void executeTask()
	{
		final int countLocksReleased = Services.get(ILockManager.class).removeAutoCleanupLocks();
		Loggables.get().addLog("Deleted " + countLocksReleased + " stale records");
	}

}

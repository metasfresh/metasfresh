package org.adempiere.ad.housekeeping;

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


import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;

import de.metas.util.ISingletonService;

public interface IHouseKeepingBL extends ISingletonService
{
	/**
	 * Register a new task to be executed during adempiere server startup
	 * @param task
	 */
	void registerStartupHouseKeepingTask(IStartupHouseKeepingTask task);

	/**
	 * Will be called during ADempiere-startup, when running in {@link org.compiere.Adempiere.RunMode#BACKEND} mode, after the model validators have been initialized
	 */
	void runStartupHouseKeepingTasks();
}

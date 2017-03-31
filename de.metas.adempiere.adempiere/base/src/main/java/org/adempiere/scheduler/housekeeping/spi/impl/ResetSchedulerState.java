package org.adempiere.scheduler.housekeeping.spi.impl;

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
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Loggables;
import org.compiere.model.I_AD_Scheduler;
import org.compiere.model.X_AD_Scheduler;
import org.compiere.util.DB;

/**
 * Updates <code>AD_Scheduler</code> records from status "Running" to "Started".
 * 
 * @author ts
 * 
 */
// task 06295
public class ResetSchedulerState implements IStartupHouseKeepingTask
{
	@Override
	public void executeTask()
	{
		final int no = DB.executeUpdateEx("UPDATE " + I_AD_Scheduler.Table_Name
				+ " SET " + I_AD_Scheduler.COLUMNNAME_Status + "='" + X_AD_Scheduler.STATUS_Started + "'"
				+ " WHERE " + I_AD_Scheduler.COLUMNNAME_Status + "='" + X_AD_Scheduler.STATUS_Running + "'", ITrx.TRXNAME_None);

		Loggables.get().addLog("Updated " + no + " AD_Scheduler records from status '" + X_AD_Scheduler.STATUS_Running + "' to '" + X_AD_Scheduler.STATUS_Started + "'");
	}
}

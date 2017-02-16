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
import org.compiere.util.DB;

import de.metas.lock.model.I_T_Lock;

/**
 * Deletes {@link I_T_Lock} records which have {@link I_T_Lock#COLUMNNAME_IsAutoCleanup} set to <code>true</code>.
 * Those records become stale if the server shuts down in mid-processing.
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
		final String sql = "DELETE FROM " + I_T_Lock.Table_Name + " WHERE " + I_T_Lock.COLUMNNAME_IsAutoCleanup + "=?";
		final Object[] sqlParams = new Object[] { true };
		final int countLocksReleased = DB.executeUpdateEx(sql, sqlParams, ITrx.TRXNAME_None);
		Loggables.get().addLog("Deleted " + countLocksReleased + " stale " + I_T_Lock.Table_Name + " records");
	}

}

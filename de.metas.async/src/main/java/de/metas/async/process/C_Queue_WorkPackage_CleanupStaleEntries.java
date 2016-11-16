package de.metas.async.process;

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


import org.adempiere.exceptions.AdempiereException;

import de.metas.async.api.impl.WorkpackageCleanupStaleEntries;
import de.metas.process.SvrProcess;

/**
 * Identifies and deactivates stale workpackages.
 * 
 * @author tsa
 * @see WorkpackageCleanupStaleEntries
 */
public class C_Queue_WorkPackage_CleanupStaleEntries extends SvrProcess
{
	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final int staleWorkpackagesCount = new WorkpackageCleanupStaleEntries()
				.setContext(getCtx())
				.setLogger(this)
				.setAD_PInstance_ID(getAD_PInstance_ID())
				.runAndGetUpdatedCount();

		//
		// If there were stale workpackages found, throw an exception
		// We do this because this process is designed to be executed from AD_Scheduler which is monitored by zabbix.
		// If the process fails, there will be a log entry in AD_Scheduler system which will be flagged as "IsError"
		// and it will be monitored by zabbix which will inform the support guys.
		if (staleWorkpackagesCount != 0)
		{
			throw new AdempiereException("We found and deactivated " + staleWorkpackagesCount + " workpackages");
		}

		return MSG_OK;
	}
}

package de.metas.server.housekeep;

import de.metas.cache.CacheMgt;
import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Component
public class SignDatabaseBuildHouseKeepingTask implements IStartupHouseKeepingTask
{
	private static final transient Logger logger = LogManager.getLogger(SignDatabaseBuildHouseKeepingTask.class);

	@Override
	public void executeTask()
	{
		if (!DB.isConnected())
		{
			throw new DBException("No database connection");
		}

		final String lastBuildInfo = Adempiere.getImplementationVersion();
		if (lastBuildInfo!= null && lastBuildInfo.endsWith(Adempiere.CLIENT_VERSION_LOCAL_BUILD))
		{
			Loggables.withLogger(logger, Level.WARN).addLog("Not signing the database build with our version={}, because it makes no sense", lastBuildInfo);
			return;
		}

		try
		{
			final String sql = "UPDATE AD_System SET LastBuildInfo = ?";
			DB.executeUpdateAndThrowExceptionOnFail(sql, new Object[] { lastBuildInfo }, ITrx.TRXNAME_None);
			CacheMgt.get().reset("AD_System");

			logger.info("Set AD_System.LastBuildInfo={}", lastBuildInfo);
		}
		catch (final Exception ex)
		{
			Loggables.withLogger(logger, Level.ERROR).addLog("Failed updating the LastBuildInfo", ex);
		}

		Loggables.withLogger(logger, Level.INFO).addLog("Signed the database build with version: {}", lastBuildInfo);
	}

}

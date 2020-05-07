package de.metas.logging.model.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.util.Check;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.logging.ILoggerCustomizer;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class LoggingModuleInterceptor extends AbstractModuleInterceptor
{
	private final static transient Logger logger = LogManager.getLogger(LoggingModuleInterceptor.class);

	public static final LoggingModuleInterceptor INSTANCE = new LoggingModuleInterceptor();

	private LoggingModuleInterceptor()
	{
	}

	/**
	 * Logs the output of {@link ILoggerCustomizer#dumpConfig()}.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/288
	 */
	@Override
	public void onUserLogin(final int AD_Org_ID_IGNORED, final int AD_Role_ID_IGNORED, final int AD_User_ID_IGNORED)
	{
		logCustomizerConfig();
	}

	private void logCustomizerConfig()
	{
		final String customizerConfig = LogManager.dumpCustomizerConfig();

		// try to get the current log level for this interceptor's logger
		final String logLevelBkp = LogManager.getLoggerLevelName(logger);

		if (Check.isEmpty(logLevelBkp))
		{
			System.err.println("Unable to log the customizer config to logger=" + logger);
			System.err.println("Writing the customizer config to std-err instead");

			// there is a problem, but still try to output the information.
			System.err.println(customizerConfig);
			return;
		}

		// this is the normal case. Make sure that we are loglevel info, log the information and set the logger back to its former level.
		try
		{
			LogManager.setLoggerLevel(logger, Level.INFO);
			logger.info(customizerConfig);
		}
		finally
		{
			LogManager.setLoggerLevel(logger, logLevelBkp);
		}
	}
}

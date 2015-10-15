package org.compiere.util;

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


import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.net.NetUtils;
import org.compiere.Adempiere;

/**
 * Sets log level which is configured in {@link ISysConfigBL}.
 * 
 * @author tsa
 *
 */
public class SysConfigLoggerCustomizer implements ILoggerCustomizer
{
	public static final transient SysConfigLoggerCustomizer instance = new SysConfigLoggerCustomizer();

	private static final String CFG_C_LOGGER_LEVEL_PREFIX = "CLogger.Level.";
	private static final ThreadLocal<Boolean> withinCustomizeLogLevel = new ThreadLocal<Boolean>();

	/**
	 * Holds the hostname which the logger uses. The value is set during the first invocations of {@link #customizeLogLevel(CLogger)}.<br>
	 * Note that the hostname won't change during runtime and that different threads will ultimately retrieve the same value if they run <code>customizeLogLevel()</code> concurrently, so we don't need
	 * to take any precautions for thread-safety.
	 */
	private String _hostNameWithDot = "";

	private SysConfigLoggerCustomizer()
	{
		super();
	}

	@Override
	public void customize(final CLogger logger)
	{
		if (logger == null)
		{
			return; // nothing to customize
		}

		if (withinCustomizeLogLevel.get() != null && withinCustomizeLogLevel.get())
		{
			return;
		}
		withinCustomizeLogLevel.set(true);
		try
		{
			if (!Env.isCtxAvailable())
			{
				// if not even the Ctx is there yet, we are too early in the startup do do any stuff with services etc.
				return;
			}
			if (Adempiere.isUnitTestMode())
			{
				// mainly we don't customize log level in unit test mode, because it might be trouble and currently we don't need to.
				return;
			}

			if (Env.getContextAsInt(Env.getCtx(), Env.CTXNAME_AD_Session_ID) <= 0)
			{
				// Likewise, if there is no session (yet), we are not interested in trying to get any services.
				// It might not be impossible, but we didn't yet need to customize any of those early loggers
				// if the need arises, we might want to customize them via system properties
				return;
			}

			final String hostNameWithDot = getHostNameWithDot();

			// string representation of the loglevel; we will try to retrieve the value from the SysConfig
			final String logLevelStr;

			String sysConfigName = CFG_C_LOGGER_LEVEL_PREFIX + hostNameWithDot + logger.getName();

			final String logLevelStrForHostName = getSysConfigValue(sysConfigName);
			if (!Check.isEmpty(logLevelStrForHostName))
			{
				logLevelStr = logLevelStrForHostName;
			}
			else
			{
				// fallback: maybe there is a log setting that applied for all hosts
				sysConfigName = CFG_C_LOGGER_LEVEL_PREFIX + logger.getName();
				logLevelStr = getSysConfigValue(sysConfigName);
			}

			// if there was a loglevel specified and if it does not equal the logger's current level, then pars and set it.
			if (!Check.isEmpty(logLevelStr) && !logLevelStr.equals(logger.getLevel().getName()))
			{
				try
				{
					final Level level = Level.parse(logLevelStr);
					logger.setMaxLevel(level); // we want to make sure nobody is highering the log level (e.g. change from INFO to WARNING)
					logger.setLevel(level);
				}
				catch (final IllegalArgumentException iae)
				{
					logger.log(Level.WARNING, "SysConfig with name {0} contained unparsable loglevel {1} for logger {2}", new Object[] { sysConfigName, logLevelStr, logger.getName() });
				}
			}
		}
		finally
		{
			withinCustomizeLogLevel.set(false);
		}
	}

	private final String getHostNameWithDot()
	{
		if (Check.isEmpty(_hostNameWithDot, true))
		{
			_hostNameWithDot = NetUtils.getLocalHost().getHostName() + ".";
		}
		return _hostNameWithDot;
	}

	private final String getSysConfigValue(final String sysConfigName)
	{
		final Map<String, String> properties = getPropertiesMap();
		if (properties == null) // shall not happen
		{
			return null;
		}

		final String value = properties.get(sysConfigName);
		return value;
	}

	private Map<String, String> getPropertiesMap()
	{
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		final boolean removePrefix = false; // keep the prefix, our BL is relying on that

		// Load the properties map from underlying database
		// NOTE: we assume it's cached on that level
		return Services.get(ISysConfigBL.class).getValuesForPrefix(CFG_C_LOGGER_LEVEL_PREFIX, removePrefix, adClientId, adOrgId);
	}

}

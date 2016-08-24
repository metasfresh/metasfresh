package de.metas.logging;

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

import java.util.Map;
import java.util.Properties;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.net.NetUtils;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;

/**
 * Sets log level which is configured in {@link ISysConfigBL}.
 *
 * @author tsa
 *
 */
class SysConfigLoggerCustomizer implements ILoggerCustomizer
{
	public static final transient SysConfigLoggerCustomizer instance = new SysConfigLoggerCustomizer();

	private static final String CFG_C_LOGGER_LEVEL_PREFIX = "CLogger.Level.";

	/**
	 * Thread-local boolean to make sure that only one thread is within the {@link #customize(Logger)} method at a time
	 */
	private static final ThreadLocal<Boolean> withinCustomizeLogLevel = new ThreadLocal<Boolean>();

	/**
	 * Holds the hostname which the logger uses. The value is set during before first use.<br>
	 * Note that the hostname won't change during runtime and that different threads will ultimately retrieve the same value if they run {@link #getHostNameWithDot()} concurrently, so we don't need
	 * to take any precautions for thread-safety.
	 */
	private String _hostNameWithDot = "";

	private SysConfigLoggerCustomizer()
	{
		super();
	}

	@Override
	public void customize(final Logger logger)
	{
		if (logger == null)
		{
			return; // nothing to customize
		}

		if (withinCustomizeLogLevel.get() != null && withinCustomizeLogLevel.get())
		{
			return; // was already handled
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

			if (Check.isEmpty(logLevelStr, true))
			{
				return;
			}

			final Level level = LogManager.asLogbackLevel(logLevelStr);
			if (!LogManager.setLoggerLevel(logger, level))
			{
				logger.warn("SysConfig with name {} contained unparsable loglevel {} for logger {}", sysConfigName, logLevelStr, logger.getName());
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
		if (properties == null)            // shall not happen
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

	@Override
	public String dumpConfig()
	{
		final Map<String, String> propertiesMap = getPropertiesMap();

		final StringBuilder out = new StringBuilder();
		out.append("Customizer class: " + getClass().getName() + "\n");
		out.append("Hostname (with dot): " + getHostNameWithDot() + "\n");
		out.append("Logger settings from SysConfig (count=" + propertiesMap.size() + "):" + "\n");

		for (final String key : propertiesMap.keySet())
		{
			out.append("\t" + key + " = " + propertiesMap.get(key) + "\n");
		}

		return out.toString();
	}

}

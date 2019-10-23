package de.metas.logging;

import java.io.File;
import java.util.List;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.RollingPolicy;
import ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicy;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MetasfreshFileLoggerHelper
{
	public static final MetasfreshFileLoggerHelper get()
	{
		final ILoggerFactory loggerFactory = LoggerFactory.getILoggerFactory();
		if (loggerFactory instanceof LoggerContext)
		{
			final LoggerContext loggerContext = (LoggerContext)loggerFactory;
			return get(loggerContext);
		}

		return NULL;
	}

	public static final MetasfreshFileLoggerHelper get(final LoggerContext context)
	{
		final Logger rootLogger = context.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
		final Appender<ILoggingEvent> appender = rootLogger.getAppender(FILEAPPENDER_NAME);
		if (!(appender instanceof RollingFileAppender))
		{
			return NULL;
		}

		final RollingFileAppender<?> rollingFileAppender = (RollingFileAppender<?>)appender;
		final RollingPolicy rollingPolicy = rollingFileAppender.getRollingPolicy();
		if (!(rollingPolicy instanceof MetasfreshTimeBasedRollingPolicy))
		{
			return NULL;
		}

		final MetasfreshTimeBasedRollingPolicy<?> metasfreshRollingPolicy = (MetasfreshTimeBasedRollingPolicy<?>)rollingPolicy;

		return new MetasfreshFileLoggerHelper(rollingFileAppender, metasfreshRollingPolicy);
	}

	private static final String FILEAPPENDER_NAME = "FILE";

	private static final MetasfreshFileLoggerHelper NULL = new MetasfreshFileLoggerHelper();

	private final RollingFileAppender<?> rollingFileAppender;
	private final MetasfreshTimeBasedRollingPolicy<?> metasfreshRollingPolicy;

	private MetasfreshFileLoggerHelper(RollingFileAppender<?> rollingFileAppender, final MetasfreshTimeBasedRollingPolicy<?> metasfreshRollingPolicy)
	{
		super();
		this.rollingFileAppender = rollingFileAppender;
		this.metasfreshRollingPolicy = metasfreshRollingPolicy;
	}

	/** Null constructor */
	private MetasfreshFileLoggerHelper()
	{
		super();
		rollingFileAppender = null;
		metasfreshRollingPolicy = null;
	}

	public File getActiveFileOrNull()
	{
		if (metasfreshRollingPolicy == null)
		{
			return null;
		}
		return metasfreshRollingPolicy.getActiveFileOrNull();
	}

	public List<File> getLogFiles()
	{
		if (metasfreshRollingPolicy == null)
		{
			return ImmutableList.of();
		}
		return metasfreshRollingPolicy.getLogFiles();
	}

	public boolean isActiveLogFile(final File file)
	{
		if (file == null)
		{
			return false;
		}

		final File activeLogFile = getActiveFileOrNull();
		if (activeLogFile == null)
		{
			return false;
		}

		return activeLogFile.equals(file);
	}

	public void flushActiveLogFile()
	{
		// TODO Auto-generated method stub
	}

	public void rotateLogFile()
	{
		if (metasfreshRollingPolicy != null)
		{
			final TimeBasedFileNamingAndTriggeringPolicy<?> triggeringPolicy = metasfreshRollingPolicy.getTimeBasedFileNamingAndTriggeringPolicy();
			if (triggeringPolicy instanceof MetasfreshTimeBasedFileNamingAndTriggeringPolicy)
			{
				final MetasfreshTimeBasedFileNamingAndTriggeringPolicy<?> metasfreshTriggeringPolicy = (MetasfreshTimeBasedFileNamingAndTriggeringPolicy<?>)triggeringPolicy;
				metasfreshTriggeringPolicy.setForceRollover();
			}
		}

		if (rollingFileAppender != null)
		{
			rollingFileAppender.rollover();
		}
	}
	
	public void setDisabled(final boolean disabled)
	{
		if (rollingFileAppender instanceof MetasfreshFileAppender)
		{
			((MetasfreshFileAppender<?>)rollingFileAppender).setDisabled(disabled);
		}
	}
	
	public boolean isDisabled()
	{
		if (rollingFileAppender instanceof MetasfreshFileAppender)
		{
			return ((MetasfreshFileAppender<?>)rollingFileAppender).isDisabled();
		}
		
		return false;
		
	}

}

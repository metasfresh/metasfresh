package org.adempiere.util.logging;

import java.util.Arrays;

import org.slf4j.Logger;

import ch.qos.logback.classic.Level;

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

public final class LoggingHelper
{
	public static void log(final Logger logger, final Level level, final String msg, final Throwable t)
	{
		if (logger == null)
		{
			System.err.println(msg);
			if (t != null)
			{
				t.printStackTrace(System.err);
			}
		}
		else if (level == Level.ERROR)
		{
			logger.error(msg, t);
		}
		else if (level == Level.WARN)
		{
			logger.warn(msg, t);
		}
		else if (level == Level.INFO)
		{
			logger.info(msg, t);
		}
		else if (level == Level.DEBUG)
		{
			logger.debug(msg, t);
		}
		else
		{
			logger.trace(msg, t);
		}
	}

	public static void log(final Logger logger, final Level level, final String msg, final Object... msgParameters)
	{
		if (logger == null)
		{
			System.err.println(msg + " -- " + (msgParameters == null ? "" : Arrays.asList(msgParameters)));
		}
		else if (level == Level.ERROR)
		{
			logger.error(msg, msgParameters);
		}
		else if (level == Level.WARN)
		{
			logger.warn(msg, msgParameters);
		}
		else if (level == Level.INFO)
		{
			logger.info(msg, msgParameters);
		}
		else if (level == Level.DEBUG)
		{
			logger.debug(msg, msgParameters);
		}
		else
		{
			logger.trace(msg, msgParameters);
		}
	}

	private LoggingHelper()
	{
	}
}

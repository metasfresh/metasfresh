package org.adempiere.util;

import org.slf4j.Logger;

import ch.qos.logback.classic.Level;

/*
 * #%L
 * de.metas.util
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

/**
 * Interface implementations can be passed to business logic to perform high-level logging. Use {@link Loggables} to get an instance.
 *
 * NOTE: The signature of this interface {@link #addLog(String, Object...)} method is chosen so that all classes like de.metas.process.JavaProcess subclasses can implement it without further code changes.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface ILoggable
{
	/**
	 * Add a log message.
	 *
	 * @param msg
	 * @param msgParameters
	 */
	public void addLog(String msg, Object... msgParameters);

	/**
	 * Create a new {@link ILoggable} instance that delegates {@link #addLog(String, Object...)} invokations to this instance and in addition logs to the given logger.
	 *
	 * @param logger
	 * @param level
	 * @return
	 */
	default ILoggable withLogger(final Logger logger, final Level level)
	{
		return new LoggableWithLogger(this, logger, level);
	}
}

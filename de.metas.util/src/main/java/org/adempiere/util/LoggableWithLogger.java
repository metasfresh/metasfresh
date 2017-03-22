package org.adempiere.util;

import org.adempiere.util.logging.LoggingHelper;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;

/*
 * #%L
 * de.metas.util
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
 * To be created by {@link ILoggable#withLogger(Logger, Level)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class LoggableWithLogger implements ILoggable
{
	private final ILoggable parent;
	private final Logger logger;
	private final Level level;

	/* package */ LoggableWithLogger(ILoggable parent, Logger logger, Level level)
	{
		this.parent = parent;
		this.logger = logger;
		this.level = level;
	}

	@Override
	public void addLog(String msg, Object... msgParameters)
	{
		parent.addLog(msg, msgParameters);
		LoggingHelper.log(logger, level, msg, msgParameters);
	}
}

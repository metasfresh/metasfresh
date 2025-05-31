package de.metas.logging;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import ch.qos.logback.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

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

@NoAutoStart
public class MetasfreshTimeBasedFileNamingAndTriggeringPolicy<E> extends DefaultTimeBasedFileNamingAndTriggeringPolicy<E>
{
	private final AtomicBoolean forceRollover = new AtomicBoolean(true);

	public MetasfreshTimeBasedFileNamingAndTriggeringPolicy()
	{
	}

	@Override
	public void start()
	{
		super.start();
	}

	@Override
	public boolean isTriggeringEvent(final File activeFile, final E event)
	{
		// Make sure we are starting a new log file on each boot
		if (forceRollover.getAndSet(false))
		{
			// Force rollover by returning true directly
			// This ensures a new log file is created on application startup
			return true;

		}

		return super.isTriggeringEvent(activeFile, event);
	}

	public void setForceRollover()
	{
		forceRollover.set(true);
	}
}

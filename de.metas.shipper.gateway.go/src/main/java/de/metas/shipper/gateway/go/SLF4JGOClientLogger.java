package de.metas.shipper.gateway.go;

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.go
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class SLF4JGOClientLogger implements GOClientLogger
{
	public static final transient SLF4JGOClientLogger instance = new SLF4JGOClientLogger();

	private static final Logger logger = LogManager.getLogger(SLF4JGOClientLogger.class);

	private SLF4JGOClientLogger()
	{
	}

	@Override
	public void log(@NonNull final GOClientLogEvent event)
	{
		if (!logger.isTraceEnabled())
		{
			return;
		}

		if (event.getResponseException() != null)
		{
			logger.trace("GO Send/Receive error: {}", event, event.getResponseException());
		}
		else
		{
			logger.trace("GO Send/Receive OK: {}", event);
		}
	}

}

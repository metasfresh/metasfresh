package de.metas.event.log.impl;

import javax.annotation.Nullable;

import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.lang.IAutoCloseable;

import de.metas.event.log.EventLogUserService.EventLogEntryRequest;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class EventLogLoggable implements ILoggable
{
	private final Class<?> handlerClass;

	public static IAutoCloseable createAndRegisterThreadLocal(@NonNull final Class<?> handlerClass)
	{
		final EventLogLoggable eventLogLoggable = new EventLogLoggable(handlerClass);
		return Loggables.temporarySetLoggable(eventLogLoggable);
	}

	private EventLogLoggable(@NonNull final Class<?> handlerClass)
	{
		this.handlerClass = handlerClass;
	}

	@Override
	public void addLog(
			@NonNull final String msg,
			@Nullable final Object... msgParameters)
	{
		EventLogEntryRequest.builder()
				.formattedMessage(msg, msgParameters)
				.eventHandlerClass(handlerClass)
				.createAndStore();
	}

}

package de.metas.event.log;

import org.compiere.util.Util;

import de.metas.event.Event;
import de.metas.event.log.impl.EventLogEntryCollector;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Tools to be used by the event bus implementation.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class EventLogSystemBusTools
{
	public static final String PROPERTY_PROCESSED_BY_HANDLER_CLASS_NAMES = "EventStore_ProcessedByHandlerClassNames";

	public static final String PROPERTY_STORE_THIS_EVENT = "EventStore_StoreThisEvent";


	public static boolean isAdvisedToStoreEvent(@NonNull final Event event)
	{
		boolean result = Util.coalesce(event.getProperty(EventLogUserService.PROPERTY_STORE_THIS_EVENT), false);
		return result;
	}

	public static EventLogEntryCollector provideEventLogEntryCollectorForCurrentThread(
			@NonNull final Event event)
	{
		return EventLogEntryCollector.createThreadLocalForEvent(event);
	}
}

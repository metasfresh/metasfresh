package org.adempiere.ad.security;

import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;

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

public final class UserRolePermissionsEventBus
{
	private static final Logger logger = LogManager.getLogger(UserRolePermissionsEventBus.class);

	private static final Topic EVENTBUS_TOPIC_Permissions = Topic.builder()
			.name("de.metas.permissions")
			.type(Type.REMOTE)
			.build();

	private static final String EVENT_PROPERTY_Type = "eventType";
	private static final String EVENTTYPE_CacheReset = "cacheReset";

	private UserRolePermissionsEventBus()
	{
	}

	private static final IEventBus getEventBus()
	{
		return Services.get(IEventBusFactory.class).getEventBus(EVENTBUS_TOPIC_Permissions);
	}

	public static void install()
	{
		final IEventBus eventBus = getEventBus();
		eventBus.subscribe(event -> {
			if (event.isLocalEvent())
			{
				return;
			}

			final String type = event.getProperty(EVENT_PROPERTY_Type);
			if (EVENTTYPE_CacheReset.equals(type))
			{
				logger.debug("Got cache reset event: {}", event);
				Services.get(IUserRolePermissionsDAO.class).resetLocalCache();
			}
		});

		logger.info("Subscribed to event bus: {}", eventBus);
	}

	public static void fireCacheResetEvent()
	{
		final Event event = Event.builder()
				.putProperty(EVENT_PROPERTY_Type, EVENTTYPE_CacheReset)
				.build();
		final IEventBus eventBus = getEventBus();
		eventBus.postEvent(event);

		logger.debug("Post cache reset event: {} to {}", event, eventBus);
	}
}

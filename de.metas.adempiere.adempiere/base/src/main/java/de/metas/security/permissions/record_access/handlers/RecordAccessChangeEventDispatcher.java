package de.metas.security.permissions.record_access.handlers;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;

import de.metas.Profiles;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.logging.LogManager;
import de.metas.security.permissions.record_access.RecordAccess;
import de.metas.security.permissions.record_access.RecordAccessConfigService;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Component
@Profile(Profiles.PROFILE_App)
public class RecordAccessChangeEventDispatcher
{
	private static final Logger logger = LogManager.getLogger(RecordAccessChangeEventDispatcher.class);

	public static final Topic TOPIC = Topic.remote("de.metas.security.permissions.record_access.RecordAccessChangeEvent");

	private final RecordAccessConfigService configs;
	private final IEventBusFactory eventBusFactory;

	public RecordAccessChangeEventDispatcher(
			@NonNull final RecordAccessConfigService configs,
			@NonNull final IEventBusFactory eventBusFactory)
	{
		this.configs = configs;
		this.eventBusFactory = eventBusFactory;
	}

	private CompositeRecordAccessHandler getHandlers()
	{
		return configs.getAllHandlers();
	}

	@PostConstruct
	@VisibleForTesting
	public void postConstruct()
	{
		final CompositeRecordAccessHandler handlers = getHandlers();
		if (handlers.isEmpty())
		{
			logger.warn("No handler registered so we won't subscribe to {}", TOPIC);
			return;
		}

		eventBusFactory
				.getEventBus(TOPIC)
				.subscribeOn(RecordAccessChangeEvent.class, this::onEvent);
	}

	private void onEvent(@NonNull final RecordAccessChangeEvent event)
	{
		final CompositeRecordAccessHandler handlers = getHandlers();

		for (final RecordAccess accessGrant : event.getAccessGrants())
		{
			handlers.onAccessGranted(accessGrant);
		}

		for (final RecordAccess accessRevoke : event.getAccessRevokes())
		{
			handlers.onAccessRevoked(accessRevoke);
		}
	}
}

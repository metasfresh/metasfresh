package de.metas.security.permissions.record_access.listeners;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.metas.Profiles;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.logging.LogManager;
import de.metas.security.permissions.record_access.UserGroupRecordAccess;
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
public class UserGroupAccessChangeEventDispatcher
{
	private static final Logger logger = LogManager.getLogger(UserGroupAccessChangeEventDispatcher.class);

	public static final Topic TOPIC = Topic.remote("de.metas.security.permissions.record_access.listeners.UserGroupAccessChangeEvent");

	private final UserGroupAccessChangeListener listeners;
	private final IEventBusFactory eventBusFactory;

	public UserGroupAccessChangeEventDispatcher(
			@NonNull final Optional<List<UserGroupAccessChangeListener>> listeners,
			@NonNull final IEventBusFactory eventBusFactory)
	{
		this.listeners = CompositeUserGroupAccessChangeListener.of(listeners);
		logger.info("{}", this.listeners);

		this.eventBusFactory = eventBusFactory;
	}

	@PostConstruct
	private void postConstruct()
	{
		if (NullUserGroupAccessChangeListener.isNull(listeners))
		{
			logger.warn("No listeners registered so we won't subscribe to {}", TOPIC);
			return;
		}

		eventBusFactory
				.getEventBus(TOPIC)
				.subscribeOn(UserGroupAccessChangeEvent.class, this::onEvent);
	}

	private void onEvent(@NonNull final UserGroupAccessChangeEvent event)
	{
		for (final UserGroupRecordAccess accessGrant : event.getAccessGrants())
		{
			listeners.onAccessGranted(accessGrant);
		}

		for (final UserGroupRecordAccess accessRevoke : event.getAccessRevokes())
		{
			listeners.onAccessRevoked(accessRevoke);
		}
	}
}

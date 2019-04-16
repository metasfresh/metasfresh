package de.metas.security.permissions.record_access;

import javax.annotation.PostConstruct;

import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.metas.Profiles;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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
class BPartnerDependentDocumentCreatedEventDispatcher
{
	static final Topic EVENTS_TOPIC = Topic.remote("de.metas.security.permissions.record_access.BPartnerUserGroupAccessChangeListener.Events");

	private final IEventBusFactory eventBusFactory;
	private final BPartnerUserGroupAccessChangeListener listener;

	public BPartnerDependentDocumentCreatedEventDispatcher(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull BPartnerUserGroupAccessChangeListener listener)
	{
		this.eventBusFactory = eventBusFactory;
		this.listener = listener;
	}

	@PostConstruct
	public void postConstruct()
	{
		eventBusFactory
				.getEventBus(EVENTS_TOPIC)
				.subscribeOn(BPartnerDependentDocumentEvent.class, this::onEvent);
	}

	private void onEvent(@NonNull final BPartnerDependentDocumentEvent event)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInThreadInheritedTrx(() -> listener.onBPartnerDependentDocumentEvent(event));
	}

}

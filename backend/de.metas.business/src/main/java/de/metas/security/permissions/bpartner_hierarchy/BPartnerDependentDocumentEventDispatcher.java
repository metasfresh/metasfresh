package de.metas.security.permissions.bpartner_hierarchy;

import javax.annotation.PostConstruct;

import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.metas.Profiles;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.logging.LogManager;
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

/**
 * Dispatches {@link BPartnerDependentDocumentEvent} to {@link BPartnerHierarchyRecordAccessHandler}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
@Profile(Profiles.PROFILE_App)
class BPartnerDependentDocumentEventDispatcher
{
	static final Topic EVENTS_TOPIC = Topic.remote("de.metas.security.permissions.bpartner_hierarchy.BPartnerDependentDocumentEvent");

	private static final Logger logger = LogManager.getLogger(BPartnerDependentDocumentEventDispatcher.class);

	private final IEventBusFactory eventBusFactory;
	private final BPartnerHierarchyRecordAccessHandler listener;

	public BPartnerDependentDocumentEventDispatcher(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull BPartnerHierarchyRecordAccessHandler listener)
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
		// Do nothing if not enabled.
		// Usually this shall not happen because in case the feature is not enabled no events will be sent.
		if (!listener.isEnabled())
		{
			logger.debug("Skip event because feature is not enabled: {}", event);
			return;
		}

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInThreadInheritedTrx(() -> listener.onBPartnerDependentDocumentEvent(event));
	}

}

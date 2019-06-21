package de.metas.vertical.pharma.securpharm.actions;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
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
public class SecurPharmActionProcessor
{
	public static final Topic EVENTS_TOPIC = Topic.remote("de.metas.vertical.pharma.securpharm.actions");

	public static final String BEANNAME_EXECUTOR = "de.metas.vertical.pharma.securpharm.actions.SecurPharmActionProcessor.executor";

	private static final Logger logger = LogManager.getLogger(SecurPharmActionProcessor.class);
	private final IEventBusFactory eventBusFactory;
	private final SecurPharmService securPharmService;

	private final Executor executor;

	public SecurPharmActionProcessor(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final SecurPharmService securPharmService,
			@Qualifier(BEANNAME_EXECUTOR) final Optional<Executor> executor)
	{
		this.eventBusFactory = eventBusFactory;
		this.securPharmService = securPharmService;

		this.executor = executor.orElseGet(() -> createAsyncExecutor());

		logger.info("Started ({})", executor);
	}

	private static Executor createAsyncExecutor()
	{
		final CustomizableThreadFactory asyncThreadFactory = new CustomizableThreadFactory(SecurPharmActionProcessor.class.getSimpleName());
		asyncThreadFactory.setDaemon(true);

		return Executors.newSingleThreadExecutor(asyncThreadFactory);
	}

	@PostConstruct
	public void postConstruct()
	{
		eventBusFactory
				.getEventBus(EVENTS_TOPIC)
				.subscribeOn(SecurPharmaActionRequest.class, this::handleEventAsync);
		logger.info("Subscribed to {} event bus topic", EVENTS_TOPIC);
	}

	private void handleEventAsync(@NonNull final SecurPharmaActionRequest event)
	{
		try
		{
			executor.execute(() -> handleEventInTrx(event));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed enqueueing {}", event, AdempiereException.extractCause(ex));
		}
	}

	private void handleEventInTrx(@NonNull final SecurPharmaActionRequest event)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		try
		{
			trxManager.runInThreadInheritedTrx(() -> handleEvent0(event));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed processing {}", event, AdempiereException.extractCause(ex));
		}
	}

	private void handleEvent0(@NonNull final SecurPharmaActionRequest event)
	{
		final SecurPharmAction action = event.getAction();
		if (SecurPharmAction.DECOMMISSION.equals(action))
		{
			securPharmService.decommissionProductsByInventoryId(event.getInventoryId());
		}
		else if (SecurPharmAction.UNDO_DECOMMISSION.equals(action))
		{
			securPharmService.undoDecommissionProductsByInventoryId(event.getInventoryId());
		}
		else
		{
			throw new AdempiereException("Unknown action: " + action)
					.setParameter("event", event);
		}
	}

}

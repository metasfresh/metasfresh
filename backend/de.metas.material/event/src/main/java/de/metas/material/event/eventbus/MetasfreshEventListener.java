package de.metas.material.event.eventbus;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventHandlerRegistry;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Properties;

/*
 * #%L
 * metasfresh-material-event
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

@Service
@DependsOn(Adempiere.BEAN_NAME)
public class MetasfreshEventListener
{
	private static final Logger logger = LogManager.getLogger(MetasfreshEventListener.class);

	private final MaterialEventHandlerRegistry materialEventHandlerRegistry;

	private final MetasfreshEventBusService metasfreshEventBusService;

	private final MaterialEventConverter materialEventConverter;

	private final IEventListener internalListener = new IEventListener()
	{
		@Override
		public void onEvent(@NonNull final IEventBus eventBus, @NonNull final Event event)
		{
			final MaterialEvent lightWeightEvent = materialEventConverter.toMaterialEvent(event);
			try (final MDCCloseable ignored = MDC.putCloseable("MaterialEventClass", lightWeightEvent.getClass().getName()))
			{
				logger.debug("Received MaterialEvent={}, Timestamp={}, TheadId={}", lightWeightEvent, Instant.now(), Thread.currentThread().getId());

				// make sure that every record we create has the correct AD_Client_ID and AD_Org_ID
				final Properties temporaryCtx = Env.copyCtx(Env.getCtx());

				Env.setClientId(temporaryCtx, lightWeightEvent.getEventDescriptor().getClientId());
				Env.setOrgId(temporaryCtx, lightWeightEvent.getEventDescriptor().getOrgId());

				try (final IAutoCloseable ignored1 = Env.switchContext(temporaryCtx))
				{
					invokeListenerInTrx(lightWeightEvent);
				}
			}
		}

		private void invokeListenerInTrx(@NonNull final MaterialEvent materialEvent)
		{
			Services.get(ITrxManager.class).runInNewTrx(() -> {
				materialEventHandlerRegistry.onEvent(materialEvent);
			});
		}

		@Override
		public String toString()
		{
			return MetasfreshEventBusService.class.getName() + ".internalListener";
		}
	};

	public MetasfreshEventListener(
			@NonNull final MaterialEventHandlerRegistry materialEventHandlerRegistry,
			@NonNull final MetasfreshEventBusService metasfreshEventBusService,
			@NonNull final MaterialEventConverter materialEventConverter)
	{
		this.materialEventConverter = materialEventConverter;
		this.materialEventHandlerRegistry = materialEventHandlerRegistry;
		this.metasfreshEventBusService = metasfreshEventBusService;

		this.metasfreshEventBusService.subscribe(internalListener);
	}
}

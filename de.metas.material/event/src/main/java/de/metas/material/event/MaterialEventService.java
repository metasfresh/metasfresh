package de.metas.material.event;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.material.event.impl.MaterialEventBus;
import de.metas.material.event.impl.MaterialEventSerializer;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-manufacturing-event-api
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
public class MaterialEventService
{
	public static final String MANUFACTURING_DISPOSITION_EVENT = "ManufacturingDispositionEvent";

	private final List<MaterialEventListener> listeners = new ArrayList<>();

	/** Topic used to send notifications about sales and purchase orders that were generated/reversed asynchronously */
	private final Topic eventBusTopic;

	private final IEventListener internalListener = new IEventListener()
	{
		@Override
		public void onEvent(final IEventBus eventBus, final Event event)
		{
			final String lightWeigthEventStr = event.getProperty(MANUFACTURING_DISPOSITION_EVENT);
			final MaterialEvent lightWeightEvent = MaterialEventSerializer.get().deserialize(lightWeigthEventStr);

			listeners.forEach(l -> l.onEvent(lightWeightEvent));
		}
	};

	/**
	 * Can be called to create a local-only event service which will not try to set up or connect to a message broker. Useful for testing.
	 * <p>
	 * This constructor is supposed to be invoked by {@link MaterialEventConfiguration}.
	 *
	 * @param eventType
	 */
	public MaterialEventService(@NonNull final Type eventType)
	{
		eventBusTopic = Topic.builder()
				.setName(MaterialEventBus.EVENTBUS_TOPIC_NAME)
				.setType(eventType)
				.build();

		getEventBus().subscribe(internalListener);
	}

	public void registerListener(final MaterialEventListener materialDemandListener)
	{
		Preconditions.checkNotNull(materialDemandListener, "Param materialDemandListener is null");
		listeners.add(materialDemandListener);
	}

	public void fireEventAfterCommit(final MaterialEvent event, final String trxName)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		trxManager
				.getTrxListenerManager(trxName)
				.onAfterCommit(() -> fireEvent(event));
	}

	public void fireEvent(final MaterialEvent event)
	{
		final String eventStr = MaterialEventSerializer.get().serialize(event);

		final Event realEvent = Event.builder()
				.putProperty(MANUFACTURING_DISPOSITION_EVENT, eventStr)
				.build();

		getEventBus().postEvent(realEvent);
	}

	private IEventBus getEventBus()
	{
		final IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);

		final IEventBus eventBus = eventBusFactory.getEventBus(eventBusTopic);
		return eventBus;
	}

}

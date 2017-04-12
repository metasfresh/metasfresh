package de.metas.manufacturing.event;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.manufacturing.event.impl.ManufacturingEventBus;
import de.metas.manufacturing.event.impl.ManufacturingEventSerializer;

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

public class ManufacturingEventService
{
	public static final String MANUFACTURING_DISPOSITION_EVENT = "ManufacturingDispositionEvent";

	private static final ManufacturingEventService INSTANCE = new ManufacturingEventService();

	private final List<ManufacturingEventListener> listeners = new ArrayList<>();

	private final IEventListener internalListener = new IEventListener()
	{
		@Override
		public void onEvent(final IEventBus eventBus, final Event event)
		{
			final String lightWeigthEventStr = event.getProperty(MANUFACTURING_DISPOSITION_EVENT);
			final ManufacturingEvent lightWeightEvent = ManufacturingEventSerializer.get().deserialize(lightWeigthEventStr);

			listeners.forEach(l -> l.onEvent(lightWeightEvent));
		}
	};

	private ManufacturingEventService()
	{
		getEventBus().subscribe(internalListener);
	}

	public static ManufacturingEventService get()
	{
		return INSTANCE;
	}

	public void registerListener(final ManufacturingEventListener l)
	{
		listeners.add(l);
	}

	public void fireEventAfterCommit(final ManufacturingEvent event, final String trxName)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		trxManager
				.getTrxListenerManager(trxName)
				.onAfterCommit(() -> fireEvent(event));
	}

	public void fireEvent(final ManufacturingEvent event)
	{
		final String eventStr = ManufacturingEventSerializer.get().serialize(event);

		final Event realEvent = Event.builder()
				.putProperty(MANUFACTURING_DISPOSITION_EVENT, eventStr)
				.build();

		getEventBus().postEvent(realEvent);
	}

	private IEventBus getEventBus()
	{
		final IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);
		final IEventBus eventBus = eventBusFactory.getEventBus(ManufacturingEventBus.EVENTBUS_TOPIC);
		return eventBus;
	}

}

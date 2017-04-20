package de.metas.material.event;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;

import com.google.common.base.Preconditions;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.material.event.impl.MaterialEventBus;
import de.metas.material.event.impl.MaterialEventSerializer;

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

public class MaterialEventService
{
	public static final String MANUFACTURING_DISPOSITION_EVENT = "ManufacturingDispositionEvent";

	private static final MaterialEventService INSTANCE = new MaterialEventService();

	private final List<MaterialEventListener> listeners = new ArrayList<>();

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

	private MaterialEventService()
	{
		getEventBus().subscribe(internalListener);
	}

	public static MaterialEventService get()
	{
		return INSTANCE;
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
		final IEventBus eventBus = eventBusFactory.getEventBus(MaterialEventBus.EVENTBUS_TOPIC);
		return eventBus;
	}

}

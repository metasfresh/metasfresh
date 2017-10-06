package de.metas.material.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.SimpleObjectSerializer;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.material.event.impl.MaterialEventBus;
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

	private boolean subscribedToEventBus = false;

	private final IEventListener internalListener = new IEventListener()
	{
		@Override
		public void onEvent(final IEventBus eventBus, final Event event)
		{
			final String lightWeigthEventStr = event.getProperty(MANUFACTURING_DISPOSITION_EVENT);
			final MaterialEvent lightWeightEvent = SimpleObjectSerializer.get().deserialize(lightWeigthEventStr, MaterialEvent.class);

			//
			// make sure that every record we create has the correct AD_Client_ID and AD_Org_ID
			final Properties copyCtx = Env.copyCtx(Env.getCtx());

			Env.setContext(copyCtx, Env.CTXNAME_AD_Client_ID, lightWeightEvent.getEventDescr().getClientId());
			Env.setContext(copyCtx, Env.CTXNAME_AD_Org_ID, lightWeightEvent.getEventDescr().getOrgId());

			try (final IAutoCloseable c = Env.switchContext(copyCtx))
			{
				listeners.forEach(l -> l.onEvent(lightWeightEvent));
			}
		}
	};

	public static MaterialEventService createLocalServiceThatIsReadyToUse()
	{
		final MaterialEventService materialEventService = new MaterialEventService(Type.LOCAL);
		materialEventService.subscribeToEventBus();
		return materialEventService;
	}

	/**
	 * Also see {@link #subscribeToEventBus()}.
	 * 
	 * @return
	 */
	public static MaterialEventService createDistributedServiceThatNeedsToSubscribe()
	{
		return new MaterialEventService(Type.REMOTE);
	}

	/**
	 * Can be called to create a local-only event service which will not try to set up or connect to a message broker. Useful for testing.
	 * <p>
	 * This constructor is supposed to be invoked by {@link MaterialEventConfiguration}.
	 *
	 * @param eventType
	 */
	private MaterialEventService(@NonNull final Type eventType)
	{
		eventBusTopic = Topic.builder()
				.setName(MaterialEventBus.EVENTBUS_TOPIC_NAME)
				.setType(eventType)
				.build();
	}

	/**
	 * With a "non-local" eventService, we can't directly get the event-bus on startup, because at that time, metasfresh is not yet ready.
	 * More concretely, the problem is that the registerListener method will invoke {@link ISysConfigBL} which will try an look into the DB.
	 * <p>
	 * Therefore, we have this particular method to be called whenever we know that it's now safe to call it. In old-school scenarios, that is probably a model validator.
	 * Note that this method can be called often, but only the first call makes a difference.
	 */
	public synchronized void subscribeToEventBus()
	{
		if (subscribedToEventBus)
		{
			return; // nothing to do
		}
		getEventBus().subscribe(internalListener);
		subscribedToEventBus = true;
	}

	/**
	 * Register the given {@code listener} to this service.
	 * This can be done before {@link #subscribeToEventBus()} was called, but the registered listener then won't yet be invoked by the framework.
	 *
	 * @param materialDemandListener
	 */
	public void registerListener(final MaterialEventListener listener)
	{
		Preconditions.checkNotNull(listener, "Param listener is null");
		listeners.add(listener);
	}

	/**
	 * Adds a trx listener to make sure the given {@code event} will be fired via {@link #fireEvent(MaterialEvent)} when the given {@code trxName} is committed.
	 *
	 * @param event
	 * @param trxName
	 */
	public void fireEventAfterNextCommit(final MaterialEvent event, final String trxName)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		trxManager
				.getTrxListenerManager(trxName)
				.onAfterNextCommit(() -> fireEvent(event));
	}

	/**
	 * Fires the given event using our (distributed) event framework. If {@link #subscribeToEventBus()} was not yet invoked, an exception is thrown.
	 *
	 * @param event
	 */
	public void fireEvent(final MaterialEvent event)
	{
		Preconditions.checkState(subscribedToEventBus, "The method subscribeToEventBus() was no yet called on this instance; this=%s", this);

		final String eventStr = SimpleObjectSerializer.get().serialize(event);

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

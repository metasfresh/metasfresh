package de.metas.procurement.webui.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.google.gwt.thirdparty.guava.common.cache.CacheBuilder;
import com.google.gwt.thirdparty.guava.common.cache.CacheLoader;
import com.google.gwt.thirdparty.guava.common.cache.LoadingCache;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.eventbus.AsyncEventBus;
import com.google.gwt.thirdparty.guava.common.eventbus.EventBus;
import com.google.gwt.thirdparty.guava.common.eventbus.Subscribe;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionContext;
import com.google.gwt.thirdparty.guava.common.eventbus.SubscriberExceptionHandler;
import com.vaadin.ui.UI;

import de.metas.procurement.webui.MFProcurementUI;

/*
 * #%L
 * metasfresh-procurement-webui
 * %%
 * Copyright (C) 2016 metas GmbH
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
public class MFEventBus
{
	private final transient Logger logger = LoggerFactory.getLogger(getClass());

	private final EventBus eventBus;

	private final LoadingCache<IApplicationEventListener, EventBusSubscriber> listener2subscribers = CacheBuilder.newBuilder()
			.build(new CacheLoader<IApplicationEventListener, EventBusSubscriber>()
			{
				@Override
				public EventBusSubscriber load(final IApplicationEventListener listener) throws Exception
				{
					return new EventBusSubscriber(listener);
				}

			});

	@Autowired
	public MFEventBus(final TaskExecutor taskExecutor)
	{
		super();

		eventBus = new AsyncEventBus(taskExecutor, new SubscriberExceptionHandler()
		{

			@Override
			public void handleException(final Throwable exception, final SubscriberExceptionContext context)
			{
				onEventBusException(exception, context);

			}
		});

		eventBus.register(this);
	}

	private void onEventBusException(final Throwable exception, final SubscriberExceptionContext context)
	{
		logger.warn("Failed handling event:"
				+ "\n Event: {}"
				+ "\n Method: {}" //
				, context.getEvent(), context.getSubscriberMethod() //
				, exception //
		);
	}

	public void register(final IApplicationEventListener listener)
	{
		EventBusSubscriber subscriber = listener2subscribers.getIfPresent(listener);
		if (subscriber != null)
		{
			// already registered
			return;
		}

		try
		{
			subscriber = listener2subscribers.get(listener);
			eventBus.register(subscriber);
		}
		catch (final ExecutionException e)
		{
			throw new RuntimeException("Failed creating subscriber for " + listener, e.getCause());
		}
	}

	public void unregister(final IApplicationEventListener listener)
	{
		if (listener == null)
		{
			return;
		}

		final EventBusSubscriber subscriber = listener2subscribers.getIfPresent(listener);
		if (subscriber == null)
		{
			// not registered
			return;
		}

		listener2subscribers.invalidate(listener);
		eventBus.unregister(subscriber);
	}

	public void unregisterAllExpired()
	{
		for (final EventBusSubscriber subscriber : new ArrayList<>(listener2subscribers.asMap().values()))
		{
			subscriber.checkExpiredAndGetUI();
		}
	}

	public void post(final IApplicationEvent event)
	{
		final PostAfterCommitCollector postAfterCommit = getPostAfterCommit();
		if (postAfterCommit != null)
		{
			postAfterCommit.add(event);
		}
		else
		{
			eventBus.post(event);
		}
	}

	private final class EventBusSubscriber
	{
		private final IApplicationEventListener delegate;
		private boolean expired = false;

		private EventBusSubscriber(final IApplicationEventListener listener)
		{
			super();
			delegate = listener;
		}

		private final UI checkExpiredAndGetUI()
		{
			if (expired)
			{
				return null;
			}

			final UI listenerUI = delegate.getUI();
			if (listenerUI == null || listenerUI.isClosing())
			{
				// listener expired
				expireNow();
				return null;
			}

			return listenerUI;
		}

		private final void expireNow()
		{
			expired = true;
			unregister(delegate);
		}

		private final void executeInUI(final IApplicationEvent event, final Runnable runnable)
		{
			if (event == null)
			{
				// shall not happen
				return;
			}

			final UI listenerUI = checkExpiredAndGetUI();
			if (listenerUI == null)
			{
				// expired
				return;
			}

			// Skip events which are not for our UI
			final String bpartner_uuid = MFProcurementUI.getBpartner_uuid(listenerUI);
			if (!Objects.equals(bpartner_uuid, event.getBpartner_uuid()))
			{
				return;
			}

			final UI currentUI = UI.getCurrent();
			if (currentUI != listenerUI)
			{
				listenerUI.access(runnable);
			}
			else
			{
				runnable.run();
			}
		}

		@Subscribe
		public void onContractChanged(final ContractChangedEvent event)
		{
			executeInUI(event, new Runnable()
			{

				@Override
				public void run()
				{
					delegate.onContractChanged(event);
				}
			});
		}

		@Subscribe
		public void onRfqChanged(final RfqChangedEvent event)
		{
			executeInUI(event, new Runnable()
			{

				@Override
				public void run()
				{
					delegate.onRfqChanged(event);
				}
			});
		}

		@Subscribe
		public void onProductSupplyChanged(final ProductSupplyChangedEvent event)
		{
			executeInUI(event, new Runnable()
			{

				@Override
				public void run()
				{
					delegate.onProductSupplyChanged(event);
				}
			});
		}

	}

	private final PostAfterCommitCollector getPostAfterCommit()
	{
		if (!TransactionSynchronizationManager.isActualTransactionActive())
		{
			return null;
		}

		PostAfterCommitCollector instance = null;
		for (final TransactionSynchronization sync : TransactionSynchronizationManager.getSynchronizations())
		{
			if (sync instanceof PostAfterCommitCollector)
			{
				instance = (PostAfterCommitCollector)sync;
				logger.debug("Found PostAfterCommitCollector instance: {}", instance);
			}
		}

		if (instance == null)
		{
			instance = new PostAfterCommitCollector();
			TransactionSynchronizationManager.registerSynchronization(instance);

			logger.debug("Registered synchronization: {}", instance);
		}

		return instance;
	}

	private final class PostAfterCommitCollector extends TransactionSynchronizationAdapter
	{
		private final List<Object> _events = new ArrayList<>();

		public synchronized void add(final Object event)
		{
			_events.add(event);
		}

		private synchronized List<Object> getAndClearEvents()
		{
			if (_events.isEmpty())
			{
				return ImmutableList.of();
			}

			final List<Object> eventsToPost = new ArrayList<>(_events);
			_events.clear();
			return eventsToPost;
		}

		@Override
		public void afterCommit()
		{
			final List<Object> eventsToPost = getAndClearEvents();
			if (eventsToPost.isEmpty())
			{
				return;
			}

			logger.debug("Posting events after commit: {}", eventsToPost);
			for (final Object event : eventsToPost)
			{
				eventBus.post(event);
			}
		}
	}

}

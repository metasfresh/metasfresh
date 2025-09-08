package de.metas.event;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.base.MoreObjects;
import de.metas.event.impl.EventMDC;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An {@link IEventBus} wrapper implementation which can be asked to collect posted events and send them all together when {@link #flush()} is called.
 */
public class QueueableForwardingEventBus extends ForwardingEventBus
{
	private static final Logger logger = LogManager.getLogger(QueueableForwardingEventBus.class);

	private boolean queuing = false;
	private final List<Event> queuedEvents = new ArrayList<>();

	protected QueueableForwardingEventBus(@NonNull final IEventBus delegate)
	{
		super(delegate);
	}

	@Override
	public final void processEvent(@NonNull final Event event)
	{
		try (final MDCCloseable mdc = EventMDC.putEvent(event))
		{
			if (queuing)
			{
				logger.debug("queuing=true; -> add event to queue; this={}", this);
				queuedEvents.add(event);
			}
			else
			{
				logger.debug("queuing=false; -> post event immediately; this={}", this);
				super.processEvent(event);
			}
		}
	}

	/**
	 * Start queuing events. From this point on NO events will be sent to bus, but they will be queued until {@link #flush()} is called.
	 */
	@OverridingMethodsMustInvokeSuper
	public QueueableForwardingEventBus queueEvents()
	{
		logger.debug("starting to queue all events until flush is called; this={}", this);
		this.queuing = true;
		return this;
	}

	/**
	 * Flush queued events and stop queuing.
	 */
	@OverridingMethodsMustInvokeSuper
	public QueueableForwardingEventBus flushAndStopQueuing()
	{
		if (!queuing)
		{
			return this;
		}

		logger.debug("stopping to queue further events; this={}", this);
		this.queuing = false;

		// make sure our queue is empty
		flush();

		return this;
	}

	/**
	 * Start queuing events until given transaction is committed.
	 *
	 * When transaction is committed, all queued events will be flushed and queuing disabled.
	 *
	 * @param trxName
	 */
	@OverridingMethodsMustInvokeSuper
	public QueueableForwardingEventBus queueEventsUntilTrxCommit(final String trxName)
	{
		// If no transaction, there is no point to create a queuing event bus,
		// but just use the original one.
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		if (trxManager.isNull(trxName))
		{
			flushAndStopQueuing();
			return this;
		}

		//
		// Start queuing events and register a transaction listener which will disable it after commit.
		queueEvents();

		// NOTE: ask for OrAutoCommit to cover the when trxName=ThreadInherited but does not exist
		final ITrxListenerManager trxListenerManager = trxManager.getTrxListenerManagerOrAutoCommit(trxName);

		// Flush queued events and stop queuing
		trxListenerManager
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(innerTrx -> flushAndStopQueuing());

		// On close, discard any queued event and make sure we are not queuing anymore.
		// This is a defensive guard in case there was a rollback.
		trxListenerManager
				.newEventListener(TrxEventTiming.AFTER_CLOSE)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(innerTrx -> {
					clearQueuedEvents();
					flushAndStopQueuing();
				});

		return this;
	}

	/**
	 * Start queuing events until current transaction is committed.
	 *
	 * When transaction is committed, all queued events will be flushed and queuing disabled.
	 *
	 * @see #queueEventsUntilTrxCommit(String)
	 */
	@OverridingMethodsMustInvokeSuper
	public QueueableForwardingEventBus queueEventsUntilCurrentTrxCommit()
	{
		return queueEventsUntilTrxCommit(ITrx.TRXNAME_ThreadInherited);
	}

	private final void clearQueuedEvents()
	{
		queuedEvents.clear();
	}

	private final List<Event> getQueuedEventsAndClear()
	{
		if (queuedEvents.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<Event> copy = new ArrayList<>(queuedEvents);
		queuedEvents.clear();
		return copy;
	}

	/**
	 * Post all queued events.
	 */
	public final void flush()
	{
		final List<Event> queuedEventsList = getQueuedEventsAndClear();
		logger.debug("flush - posting {} queued events to event bus; this={}", queuedEventsList.size(), this);
		for (final Event event : queuedEventsList)
		{
			super.processEvent(event);
		}
	}

	@Override
	public String toString()
	{
		final Topic topic = getTopic();

		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("topicName", topic.getName())
				.add("type", topic.getType())
				.add("destroyed", isDestroyed() ? Boolean.TRUE : null)
				.toString();
	}
}

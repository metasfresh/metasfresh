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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;

import lombok.NonNull;

/**
 * An {@link IEventBus} wrapper implementation which can be asked to collect posted events and send them all together when {@link #flush()} is called.
 *
 * @author tsa
 *
 */
public class QueueableForwardingEventBus extends ForwardingEventBus
{
	private boolean queuing = false;
	private final List<Event> queuedEvents = new ArrayList<>();

	protected QueueableForwardingEventBus(final IEventBus delegate)
	{
		super(delegate);
	}

	@Override
	public final void postEvent(@NonNull final Event event)
	{
		if (queuing)
		{
			queuedEvents.add(event);
		}
		else
		{
			super.postEvent(event);
		}
	}

	/**
	 * Start queuing events. From this point on NO events will be sent to bus, but they will be queued until {@link #flush()} is called.
	 */
	@OverridingMethodsMustInvokeSuper
	public QueueableForwardingEventBus queueEvents()
	{
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
		for (final Event event : getQueuedEventsAndClear())
		{
			super.postEvent(event);
		}
	}
}

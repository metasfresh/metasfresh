package org.adempiere.ad.trx.api.impl;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.exceptions.TrxException;
import org.adempiere.ad.trx.spi.ITrxListener;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.WeakList;

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

import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;

/**
 * Default {@link ITrxListenerManager} implementation
 *
 * @author tsa
 *
 */
public class TrxListenerManager implements ITrxListenerManager
{
	private static final Logger logger = LogManager.getLogger(TrxListenerManager.class);

	private volatile WeakList<ITrxListener> listeners = null; // NOSONAR ts: i think volatile is required here

	private final AtomicReference<String> running = new AtomicReference<>(null);

	private static enum OnError
	{
		ThrowException, LogAndSkip
	};

	public TrxListenerManager()
	{
	}

	@Override
	public void registerListener(final ITrxListener listener)
	{
		final boolean weak = false;
		registerListener(weak, listener);
	}

	@Override
	public void onAfterNextCommit(Runnable runnable)
	{
		registerListener(new TrxListenerAdapter()
		{
			@Override
			public String toString()
			{
				return MoreObjects.toStringHelper("runOnAfterFirstCommit").addValue(runnable).toString();
			}

			@Override
			public void afterCommit(final ITrx trx)
			{
				runnable.run();
				listeners.remove(this);
			}
		});
	}

	@Override
	public void registerListener(final boolean weak, final ITrxListener listener)
	{
		if (listener == null || !listener.isActive())
		{
			return;
		}

		final String timingInfo = running.get();
		if (timingInfo != null)
		{
			logger.warn("Registering a listener while other listeners are fired might be a development error and the listener might not be fired."
					+ "\n timingInfo={}"
					+ "\n listener={}", timingInfo, listener, new AdempiereException("STACKTRACE"));
		}

		if (listeners == null)
		{
			listeners = new WeakList<>();
			listeners.setWeakDefault(false);
		}

		// NOTE: this checking is quite expensive and in most of the cases we are registering annonymous inner classes
		// so i think is quite save to take it down
		// if (listeners.contains(listener))
		// {
		// return;
		// }

		listeners.add(listener, weak);
	}

	@Override
	public void fireBeforeCommit(final ITrx trx)
	{
		// Execute the "beforeCommit". On error, propagate the exception.
		fireListeners(OnError.ThrowException, "beforeCommit", listener -> listener.beforeCommit(trx));
	}

	@Override
	public void fireAfterCommit(final ITrx trx)
	{
		// Execute the "afterCommit", but don't fail because we are not allowed to fail by method's contract
		fireListeners(OnError.LogAndSkip, "afterCommit", listener -> listener.afterCommit(trx));
	}

	@Override
	public void fireAfterRollback(final ITrx trx)
	{
		// Execute the "afterRollback", but don't fail because we are not allowed to fail by method's contract
		fireListeners(OnError.LogAndSkip, "afterRollback", listener -> listener.afterRollback(trx));
	}

	@Override
	public void fireAfterClose(final ITrx trx)
	{
		// Execute the "afterClose", but don't fail because we are not allowed to fail by method's contract
		fireListeners(OnError.LogAndSkip, "afterClose", listener -> listener.afterClose(trx));
	}

	private final void fireListeners(final OnError onError, final String timingInfo, final Consumer<ITrxListener> listenerMethod)
	{
		if (listeners == null)
		{
			return;
		}

		running.set(timingInfo);
		try
		{
			for (final ITrxListener listener : listeners.hardList())
			{
				// shouldn't be necessary but in fact i just had an NPE at this place
				if (listener == null || !listener.isActive())
				{
					continue;
				}

				// Execute the listener method
				try
				{
					listenerMethod.accept(listener);
				}
				catch (final Exception ex)
				{
					if (onError == OnError.LogAndSkip)
					{
						logger.warn("Error while invoking {} using {}. Error was discarded.", timingInfo, listener, ex);
					}
					else // if (onError == OnError.ThrowException)
					{
						throw new TrxException("Error on " + timingInfo, ex)
								.setParameter("listener", listener)
								.appendParametersToMessage();
					}
				}
			}
		}
		finally
		{
			running.set(null);
		}
	}
}

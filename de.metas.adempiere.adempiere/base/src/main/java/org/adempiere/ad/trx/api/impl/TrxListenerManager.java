package org.adempiere.ad.trx.api.impl;

import java.util.concurrent.atomic.AtomicReference;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.exceptions.TrxException;
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

import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Default {@link ITrxListenerManager} implementation
 *
 * @author tsa
 *
 */
public class TrxListenerManager implements ITrxListenerManager
{
	private static final Logger logger = LogManager.getLogger(TrxListenerManager.class);

	private volatile WeakList<RegisterListenerRequest> listeners = null;

	/**
	 * Never contains {@code null} or {@link TrxEventTiming#UNSPECIFIED}.
	 */
	private final AtomicReference<TrxEventTiming> runningWithinTrxEventInfo = new AtomicReference<>(TrxEventTiming.NONE);

	private static enum OnError
	{
		ThrowException, LogAndSkip
	};

	public TrxListenerManager()
	{
	}

	@Override
	public void registerListener(@NonNull final RegisterListenerRequest listener)
	{
		if (!listener.isActive())
		{
			return;
		}

		final TrxEventTiming eventTimingOfListener = listener.getTiming();
		final boolean listenerHasProblematicTiming = !eventTimingOfListener.canBeRegisteredWithinOtherTiming(runningWithinTrxEventInfo.get());
		if (listenerHasProblematicTiming)
		{
			logger.warn(
					"Registering another listener within a listener's event handling code might be a development error and that other listener might not be fired."
							+ "\n current trx event timing={}"
							+ "\n listener that is registerd={}",
					runningWithinTrxEventInfo.get(),
					listener,
					new AdempiereException("STACKTRACE"));
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

		listeners.add(listener, listener.isRegisterWeakly());
	}

	@Override
	public void fireBeforeCommit(final ITrx trx)
	{
		// Execute the "beforeCommit". On error, propagate the exception.
		fireListeners(OnError.ThrowException, TrxEventTiming.BEFORE_COMMIT, trx);
	}

	@Override
	public void fireAfterCommit(final ITrx trx)
	{
		// Execute the "afterCommit", but don't fail because we are not allowed to fail by method's contract
		fireListeners(OnError.LogAndSkip, TrxEventTiming.AFTER_COMMIT, trx);
	}

	@Override
	public void fireAfterRollback(final ITrx trx)
	{
		// Execute the "afterRollback", but don't fail because we are not allowed to fail by method's contract
		fireListeners(OnError.LogAndSkip, TrxEventTiming.AFTER_ROLLBACK, trx);
	}

	@Override
	public void fireAfterClose(final ITrx trx)
	{
		// Execute the "afterClose", but don't fail because we are not allowed to fail by method's contract
		fireListeners(OnError.LogAndSkip, TrxEventTiming.AFTER_CLOSE, trx);
	}

	private final void fireListeners(
			final OnError onError,
			final TrxEventTiming timingInfo,
			final ITrx trx)
	{
		if (listeners == null)
		{
			return;
		}

		runningWithinTrxEventInfo.set(timingInfo);
		try
		{
			for (final RegisterListenerRequest listener : listeners.hardList())
			{
				// shouldn't be necessary but in fact i just had an NPE at this place
				if (listener == null || !listener.isActive())
				{
					continue;
				}

				// Execute the listener method
				try
				{
					listener.getHandlingMethod().onTransactionEvent(trx);
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
				finally
				{
					if (listener.isInvokeMethodJustOnce())
					{
						listener.deactivate();
					}
				}
			}
		}
		finally
		{
			runningWithinTrxEventInfo.set(TrxEventTiming.NONE);
		}
	}
}

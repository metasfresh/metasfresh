package org.adempiere.ad.trx.api.impl;

import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.exceptions.TrxException;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.StringUtils;
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

import com.google.common.base.Predicates;

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
	private final AtomicReference<TrxEventTiming> runningWithinTrxEventTiming = new AtomicReference<>(TrxEventTiming.NONE);

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

		verifyEventTiming(listener);

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

	private void verifyEventTiming(@NonNull final RegisterListenerRequest listener)
	{
		final TrxEventTiming eventTimingOfListener = listener.getTiming();
		final boolean listenerHasProblematicTiming = !eventTimingOfListener.canBeRegisteredWithinOtherTiming(runningWithinTrxEventTiming.get());

		if (listenerHasProblematicTiming)
		{
			final String message = StringUtils.formatMessage("Registering another listener within a listener's event handling code might be a development error and that other listener might not be fired."
					+ "\n current trx event timing={}"
					+ "\n listener that is registerd={}",
					runningWithinTrxEventTiming.get(),
					listener);

			new AdempiereException(message).throwIfDeveloperModeOrLogWarningElse(logger);
		}
	}
	
	@Override
	public boolean canRegisterOnTiming(@NonNull final TrxEventTiming timing)
	{
		return timing.canBeRegisteredWithinOtherTiming(runningWithinTrxEventTiming.get());
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
			@NonNull final OnError onError,
			@NonNull final TrxEventTiming timingInfo,
			@NonNull final ITrx trx)
	{
		if (listeners == null)
		{
			return;
		}

		runningWithinTrxEventTiming.set(timingInfo);
		try
		{
			listeners.hardList().stream()
					.filter(Predicates.notNull())
					.filter(listener -> timingInfo.equals(listener.getTiming()))
					.forEach(listener -> fireListener(onError, timingInfo, trx, listener));
		}
		finally
		{
			runningWithinTrxEventTiming.set(TrxEventTiming.NONE);
		}
	}

	private void fireListener(
			@NonNull final OnError onError,
			@NonNull final TrxEventTiming timingInfo,
			@NonNull final ITrx trx,
			@Nullable final RegisterListenerRequest listener)
	{
		// shouldn't be necessary but in fact i just had an NPE at this place
		if (listener == null || !listener.isActive())
		{
			return;
		}

		// Execute the listener method
		try
		{
			listener.getHandlingMethod().onTransactionEvent(trx);
		}
		catch (final Exception ex)
		{
			handleException(onError, timingInfo, listener, ex);
		}
		finally
		{
			if (listener.isInvokeMethodJustOnce())
			{
				listener.deactivate();
			}
		}
	}

	private void handleException(
			@NonNull final OnError onError,
			@NonNull final TrxEventTiming timingInfo,
			@NonNull final RegisterListenerRequest listener,
			@NonNull final Exception ex)
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

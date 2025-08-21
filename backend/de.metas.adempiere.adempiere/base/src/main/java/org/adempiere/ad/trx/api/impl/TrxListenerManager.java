package org.adempiere.ad.trx.api.impl;

import de.metas.logging.LogManager;
import de.metas.util.StringUtils;
import de.metas.util.WeakList;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Default {@link ITrxListenerManager} implementation
 */
public class TrxListenerManager implements ITrxListenerManager
{
	private static final Logger logger = LogManager.getLogger(TrxListenerManager.class);

	private volatile WeakList<RegisterListenerRequest> listeners = null;

	/** for debugging */
	private final String trxName;

	/**
	 * Never contains {@code null}.
	 */
	private final AtomicReference<TrxEventTiming> runningWithinTrxEventTiming = new AtomicReference<>(TrxEventTiming.NONE);

	private enum OnError
	{
		ThrowException, LogAndSkip
	}

	public TrxListenerManager(final String trxName)
	{
		this.trxName = trxName;
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

		final TrxEventTiming currentTiming = getCurrentTiming();
		final boolean listenerHasProblematicTiming = !eventTimingOfListener.canBeRegisteredWithinOtherTiming(currentTiming);
		if (listenerHasProblematicTiming)
		{
			final String message = StringUtils.formatMessage("""
					Registering another listener within a listener's event handling code might be a development error and that other listener might not be fired.
					 trxName={}
					 current trx event timing={}
					 listener that is registered={}\
					""",
					this.trxName,
					currentTiming,
					listener);
			new AdempiereException(message).throwIfDeveloperModeOrLogWarningElse(logger);
		}
	}

	@Override
	public boolean canRegisterOnTiming(@NonNull final TrxEventTiming timing)
	{
		return timing.canBeRegisteredWithinOtherTiming(getCurrentTiming());
	}

	@Override
	public void fireBeforeCommit(final ITrx trx)
	{
		// Execute the "beforeCommit". On error, propagate the exception.
		fireListeners(OnError.ThrowException, TrxEventTiming.BEFORE_COMMIT, trx);
	}

	private TrxEventTiming getCurrentTiming()
	{
		return runningWithinTrxEventTiming.get();
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
					.filter(Objects::nonNull)
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
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("listener", listener)
					.setParameter(timingInfo)
					.appendParametersToMessage();
		}
	}
}

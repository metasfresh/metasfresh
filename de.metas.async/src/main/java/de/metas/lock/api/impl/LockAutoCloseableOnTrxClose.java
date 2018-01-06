package de.metas.lock.api.impl;

import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;

/**
 * An {@link ILockAutoCloseable} implementation which when {@link #close()} is called it will
 * <ul>
 * <li>if transaction is null or no longer exist, it will close the lock immediatelly
 * <li>if transaction exists, it will close the lock asynchronously when the transaction is commited or rolled back.
 * </ul>
 *
 * @author tsa
 */
/* package */final class LockAutoCloseableOnTrxClose extends AbstractLockAutoCloseable
{
	private final String trxName;

	/* package */ LockAutoCloseableOnTrxClose(final ILock lock, final String trxName)
	{
		super(lock);
		this.trxName = trxName;
	}

	@Override
	protected void closeImpl()
	{
		Services.get(ITrxManager.class)
				.getTrxListenerManagerOrAutoCommit(trxName)
				.newEventListener(TrxEventTiming.AFTER_CLOSE)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(innerTrx -> closeNow());
	}
}

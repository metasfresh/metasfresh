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
				.newEventListener().timing(TrxEventTiming.AFTER_CLOSE)
				.handlingMethod(innerTrx -> closeNow())
				.register();
	}
}

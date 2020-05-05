package de.metas.lock.api.impl;

import org.adempiere.util.lang.ObjectUtils;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.util.Check;

/**
 * {@link ILockAutoCloseable} base implementation which addresses the common concerns.
 * 
 * Developers are adviced to extend this class instead of implementing {@link ILockAutoCloseable}.
 * 
 * @author tsa
 *
 */
/* package */abstract class AbstractLockAutoCloseable implements ILockAutoCloseable
{
	private final ILock lock;

	public AbstractLockAutoCloseable(final ILock lock)
	{
		super();
		Check.assumeNotNull(lock, "lock not null");
		this.lock = lock;
	}

	@Override
	public final String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public final ILock getLock()
	{
		return lock;
	}

	@Override
	public final void close()
	{
		// If lock was already closed, there is nothing to do
		if (lock.isClosed())
		{
			return;
		}

		closeImpl();

	}

	/** Actual lock closing logic */
	protected abstract void closeImpl();

	protected final void closeNow()
	{
		lock.close();
	}
}

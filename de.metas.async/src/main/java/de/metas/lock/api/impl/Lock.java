package de.metas.lock.api.impl;

/*
 * #%L
 * de.metas.async
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

import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.concurrent.CloseableReentrantLock;
import org.adempiere.util.lang.ObjectUtils;
import org.slf4j.Logger;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.lock.exceptions.LockAlreadyClosedException;
import de.metas.lock.exceptions.UnlockFailedException;
import de.metas.lock.spi.ILockDatabase;
import de.metas.logging.LogManager;

/* package */class Lock implements ILock
{
	private static final transient Logger logger = LogManager.getLogger(Lock.class);

	/* package */CloseableReentrantLock mutex = new CloseableReentrantLock();

	private final ILockDatabase lockDatabase;
	private final LockOwner owner;
	private final boolean isAutoCleanup;
	private int _countLocked = 0;

	// Status
	private final AtomicBoolean closed = new AtomicBoolean(false);

	/* package */ Lock(final ILockDatabase lockDatabase, final LockOwner owner, final boolean isAutoCleanup, final int countLocked)
	{
		super();

		Check.assumeNotNull(lockDatabase, "lockDatabase not null");
		this.lockDatabase = lockDatabase;

		Check.assumeNotNull(owner, "owner not null");
		Check.assume(owner.isRealOwnerOrNoOwner(), "owner shall be a real owner or no owner: {}", owner);
		this.owner = owner;

		this.isAutoCleanup = isAutoCleanup;
		_countLocked = countLocked;
	}

	/** @return locks database; never return null */
	/* package */final ILockDatabase getLockDatabase()
	{
		return lockDatabase;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public LockOwner getOwner()
	{
		return owner;
	}

	@Override
	public boolean isAutoCleanup()
	{
		return isAutoCleanup;
	}

	@Override
	public int getCountLocked()
	{
		return _countLocked;
	}

	/* package */final void subtractCountLocked(final int countLockedToSubtract)
	{
		try (CloseableReentrantLock l = mutex.open())
		{
			if (_countLocked < countLockedToSubtract)
			{
				new UnlockFailedException("Unlocked more than counted"
						+ "\n Current locked count: " + _countLocked
						+ "\n Locked to subtract: " + countLockedToSubtract
						+ "\n Lock: " + this)
								.throwIfDeveloperModeOrLogWarningElse(logger);

				_countLocked = 0;
			}
			else
			{
				_countLocked -= countLockedToSubtract;
			}
		}
	}

	@Override
	public ILockCommand split()
	{
		LockAlreadyClosedException.throwIfClosed(this);
		assertHasRealOwner();

		return new LockCommand(this);
	}

	@Override
	public void unlockAll()
	{
		assertHasRealOwner();

		try (CloseableReentrantLock l = mutex.open())
		{
			// Mark it as closed.
			// If it was already closed, do nothing.
			if (closed.getAndSet(true))
			{
				throw new LockAlreadyClosedException(this);
			}

			final LockOwner ownerCurrent = getOwner();

			final int countUnlocked = Services.get(ILockManager.class).unlock()
					.setOwner(ownerCurrent)
					.release();

			subtractCountLocked(countUnlocked);
		}
	}

	@Override
	public boolean isClosed()
	{
		return closed.get();
	}

	@Override
	public void close()
	{
		// If it was already closed, do nothing (method contract).
		if (isClosed())
		{
			return;
		}

		unlockAll();
	}

	@Override
	public void closeOnTrxClose(final String trxName)
	{
		Services.get(ITrxManager.class)
				.getTrxListenerManagerOrAutoCommit(trxName)
				.newEventListener(TrxEventTiming.AFTER_CLOSE)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(innerTrx -> close());
	}

	private final void assertHasRealOwner()
	{
		final LockOwner owner = getOwner();
		Check.assume(owner.isRealOwner(), "lock {} shall have an owner", this);
	}

	@Override
	public ILockAutoCloseable asAutoCloseable()
	{
		return new LockAutoCloseable(this);
	}

	@Override
	public ILockAutoCloseable asAutocloseableOnTrxClose(final String trxName)
	{
		return new LockAutoCloseableOnTrxClose(this, trxName);
	}

	@Override
	public boolean isLocked(final Object model)
	{
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);
		return getLockDatabase().isLocked(adTableId, recordId, getOwner());
	}
}

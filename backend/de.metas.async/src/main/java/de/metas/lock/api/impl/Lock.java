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

import com.google.common.collect.ImmutableList;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.lock.exceptions.LockAlreadyClosedException;
import de.metas.lock.exceptions.UnlockFailedException;
import de.metas.lock.spi.ILockDatabase;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.concurrent.CloseableReentrantLock;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ToString
		/* package */class Lock implements ILock
{
	private static final Logger logger = LogManager.getLogger(Lock.class);

	/* package */ final CloseableReentrantLock mutex = new CloseableReentrantLock();

	private final ILockDatabase lockDatabase;

	@Getter
	private final LockOwner owner;

	@Getter
	private final boolean isAutoCleanup;
	private int _countLocked;

	// Status
	private final AtomicBoolean closed = new AtomicBoolean(false);

	/* package */ Lock(
			@NonNull final ILockDatabase lockDatabase,
			@NonNull final LockOwner owner,
			final boolean isAutoCleanup,
			final int countLocked)
	{
		this.lockDatabase = lockDatabase;

		Check.assume(owner.isRealOwnerOrNoOwner(), "owner shall be a real owner or no owner: {}", owner);
		this.owner = owner;

		this.isAutoCleanup = isAutoCleanup;
		_countLocked = countLocked;
	}

	/**
	 * @return locks database; never return null
	 */
	/* package */
	final ILockDatabase getLockDatabase()
	{
		return lockDatabase;
	}

	@Override
	public int getCountLocked()
	{
		return _countLocked;
	}

	/* package */
	final void subtractCountLocked(final int countLockedToSubtract)
	{
		try (final CloseableReentrantLock ignore = mutex.open())
		{
			if (_countLocked < countLockedToSubtract)
			{
				//noinspection ThrowableNotThrown
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

		try (final CloseableReentrantLock ignore = mutex.open())
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
	public void unlockAllAfterTrxRollback()
	{
		Services.get(ITrxManager.class)
				.accumulateAndProcessAfterRollback(
						"LocksToReleaseOnRollback",
						ImmutableList.of(this),
						Lock::unlockAllNoFail);
	}

	private static void unlockAllNoFail(final List<Lock> locks)
	{
		if (locks.isEmpty())
		{
			return;
		}

		for (final Lock lock : locks)
		{
			if (lock.isClosed())
			{
				continue;
			}

			try
			{
				lock.unlockAll();
			}
			catch (Exception ex)
			{
				logger.warn("Failed to unlock {}. Ignored", lock, ex);
			}
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

	private void assertHasRealOwner()
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

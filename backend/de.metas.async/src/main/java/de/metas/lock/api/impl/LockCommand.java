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

import com.google.common.collect.ImmutableSet;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.LockOwner;
import de.metas.lock.exceptions.LockAlreadyClosedException;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.lock.spi.ILockDatabase;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.concurrent.CloseableReentrantLock;
import org.adempiere.util.concurrent.FutureValue;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Future;

/* package */class LockCommand implements ILockCommand
{
	// services
	private final transient ILockDatabase lockDatabase;

	// Lock parameters
	private Lock _parentLock;
	private LockOwner owner = null;
	private Boolean _autoCleanup = null;
	private static final boolean DEFAULT_AutoCleanup = true;
	private boolean failIfAlreadyLocked = DEFAULT_FailIfNothingLocked;

	private final LockRecords _recordsToLock = new LockRecords();

	private AllowAdditionalLocks _allowAdditionalLocks;

	private boolean failIfNothingLocked;

	LockCommand(final ILockDatabase lockDatabase)
	{
		super();

		Check.assumeNotNull(lockDatabase, "LockDatabase not null");
		this.lockDatabase = lockDatabase;
	}

	/**
	 * Creates a lock change command
	 */
	public LockCommand(@NonNull final Lock parentLock)
	{
		this._parentLock = parentLock;

		this.lockDatabase = parentLock.getLockDatabase();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public final ILock acquire()
	{
		final Lock parentLock = getParentLock();
		if (parentLock == null)
		{
			return lockDatabase.lock(this);
		}
		else
		{
			try (final CloseableReentrantLock ignored = parentLock.mutex.open())
			{
				LockAlreadyClosedException.throwIfClosed(parentLock);
				final ILock lock = lockDatabase.lock(this);
				parentLock.subtractCountLocked(lock.getCountLocked());
				return lock;
			}
		}
	}

	@Override
	public Future<ILock> acquireBeforeTrxCommit(final String trxName)
	{
		return acquireOnTrxEventTiming(trxName, TrxEventTiming.BEFORE_COMMIT);
	}
	
	@Override
	public Future<ILock> acquireAfterTrxCommit(final String trxName)
	{
		return acquireOnTrxEventTiming(trxName, TrxEventTiming.AFTER_COMMIT);
	}
	
	private Future<ILock> acquireOnTrxEventTiming(final String trxName, final TrxEventTiming timing)
	{
		final FutureValue<ILock> futureLock = new FutureValue<>();
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.getTrxListenerManagerOrAutoCommit(trxName)
				.newEventListener(timing)
				.invokeMethodJustOnce(true) // only once, also because subsequent calls on FutureValue.setValue will throw exception
				.registerHandlingMethod(innerTrx -> {

					try
					{
						final ILock lock = acquire();
						futureLock.set(lock);
					}
					catch (final Exception e)
					{
						futureLock.setError(e);
					}
				});
		return futureLock;
	}



	@Override
	public ILockCommand setOwner(final LockOwner owner)
	{
		this.owner = owner;
		return this;
	}

	@Override
	public final LockOwner getOwner()
	{
		Check.assumeNotNull(owner, LockFailedException.class, "owner not null");
		return owner;
	}

	@Override
	public final Lock getParentLock()
	{
		return _parentLock;
	}

	@Override
	public ILockCommand setAllowAdditionalLocks(final AllowAdditionalLocks allowAdditionalLocks)
	{
		_allowAdditionalLocks = allowAdditionalLocks;
		return this;
	}

	@Override
	public AllowAdditionalLocks getAllowAdditionalLocks()
	{
		return _allowAdditionalLocks;
	}

	@Override
	public ILockCommand setAutoCleanup(final boolean autoCleanup)
	{
		this._autoCleanup = autoCleanup;
		return this;
	}

	@Override
	public final boolean isAutoCleanup()
	{
		if (_autoCleanup != null)
		{
			return _autoCleanup;
		}
		else if (_parentLock != null)
		{
			return _parentLock.isAutoCleanup();
		}
		else
		{
			return DEFAULT_AutoCleanup;
		}
	}

	@Override
	public ILockCommand setFailIfAlreadyLocked(final boolean failIfAlreadyLocked)
	{
		this.failIfAlreadyLocked = failIfAlreadyLocked;
		return this;
	}

	@Override
	public final boolean isFailIfAlreadyLocked()
	{
		return failIfAlreadyLocked;
	}

	@Override
	public ILockCommand setFailIfNothingLocked(final boolean failIfNothingLocked)
	{
		this.failIfNothingLocked = failIfNothingLocked;
		return this;
	}

	@Override
	public boolean isFailIfNothingLocked()
	{
		return failIfNothingLocked;
	}

	@Override
	public ILockCommand setRecordByModel(final Object model)
	{
		_recordsToLock.setRecordByModel(model);
		return this;
	}

	@Override
	public ILockCommand setRecordByTableRecordId(final int tableId, final int recordId)
	{
		_recordsToLock.setRecordByTableRecordId(tableId, recordId);
		return this;
	}

	@Override
	public ILockCommand setRecordsBySelection(final Class<?> modelClass, final PInstanceId pinstanceId)
	{
		_recordsToLock.setRecordsBySelection(modelClass, pinstanceId);
		return this;
	}

	@Override
	public <T> ILockCommand setSetRecordsByFilter(final Class<T> clazz, final IQueryFilter<T> filters)
	{
		_recordsToLock.setSetRecordsByFilter(clazz, filters);
		return this;
	}

	@Override
	public IQueryFilter<?> getSelectionToLock_Filters()
	{
		return _recordsToLock.getSelection_Filters();
	}

	@Override
	public final int getSelectionToLock_AD_Table_ID()
	{
		return _recordsToLock.getSelection_AD_Table_ID();
	}

	@Override
	public final PInstanceId getSelectionToLock_AD_PInstance_ID()
	{
		return _recordsToLock.getSelection_PInstanceId();
	}

	@Override
	public final Iterator<TableRecordReference> getRecordsToLockIterator()
	{
		return _recordsToLock.getRecordsIterator();
	}

	@Override
	public LockCommand addRecordByModel(final Object model)
	{
		_recordsToLock.addRecordByModel(model);
		return this;
	}

	@Override
	public LockCommand addRecordsByModel(final Collection<?> models)
	{
		_recordsToLock.addRecordByModels(models);
		return this;
	}

	@Override
	public ILockCommand addRecord(@NonNull final TableRecordReference record)
	{
		_recordsToLock.addRecords(ImmutableSet.of(record));
		return this;
	}
}

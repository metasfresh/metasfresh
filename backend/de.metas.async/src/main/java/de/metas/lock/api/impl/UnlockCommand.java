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

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Future;

import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.concurrent.FutureValue;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.lock.api.IUnlockCommand;
import de.metas.lock.api.LockOwner;
import de.metas.lock.exceptions.UnlockFailedException;
import de.metas.lock.spi.ILockDatabase;

public class UnlockCommand implements IUnlockCommand
{
	// services
	private final transient ILockDatabase lockDatabase;

	// Lock parameters
	private LockOwner owner = null;

	// Records to lock
	private final LockRecords _recordsToUnlock = new LockRecords();

	UnlockCommand(final ILockDatabase lockDatabase)
	{
		super();

		Check.assumeNotNull(lockDatabase, "lockDatabase not null");
		this.lockDatabase = lockDatabase;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int release()
	{
		return lockDatabase.unlock(this);
	}

	@Override
	public Future<Integer> releaseAfterTrxCommit(final String trxName)
	{
		final FutureValue<Integer> countUnlockedFuture = new FutureValue<>();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.getTrxListenerManagerOrAutoCommit(trxName)
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(innerTrx -> {
					try
					{
						final int countUnlocked = release();
						countUnlockedFuture.set(countUnlocked);
					}
					catch (Exception e)
					{
						countUnlockedFuture.setError(e);
					}
				});
		return countUnlockedFuture;
	}

	@Override
	public IUnlockCommand setOwner(final LockOwner owner)
	{
		this.owner = owner;
		return this;
	}

	@Override
	public final LockOwner getOwner()
	{
		Check.assumeNotNull(owner, UnlockFailedException.class, "owner not null");
		return this.owner;
	}

	@Override
	public IUnlockCommand setRecordByModel(final Object model)
	{
		_recordsToUnlock.setRecordByModel(model);
		return this;
	}

	@Override
	public IUnlockCommand setRecordsByModels(final Collection<?> models)
	{
		_recordsToUnlock.setRecordsByModels(models);
		return this;
	}

	@Override
	public IUnlockCommand setRecordByTableRecordId(final int tableId, final int recordId)
	{
		_recordsToUnlock.setRecordByTableRecordId(tableId, recordId);
		return this;
	}

	@Override
	public IUnlockCommand setRecordByTableRecordId(final String tableName, final int recordId)
	{
		_recordsToUnlock.setRecordByTableRecordId(tableName, recordId);
		return this;
	}

	@Override
	public IUnlockCommand setRecordsBySelection(final Class<?> modelClass, final int adPIstanceId)
	{
		_recordsToUnlock.setRecordsBySelection(modelClass, adPIstanceId);
		return this;
	}

	@Override
	public final int getSelectionToUnlock_AD_Table_ID()
	{
		return _recordsToUnlock.getSelection_AD_Table_ID();
	}

	@Override
	public final int getSelectionToUnlock_AD_PInstance_ID()
	{
		return _recordsToUnlock.getSelection_AD_PInstance_ID();
	}

	@Override
	public final Iterator<ITableRecordReference> getRecordsToUnlockIterator()
	{
		return _recordsToUnlock.getRecordsIterator();
	}
}

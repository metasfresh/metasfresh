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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.SetMultimap;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.IUnlockCommand;
import de.metas.lock.api.LockOwner;
import de.metas.lock.spi.ExistingLockInfo;
import de.metas.lock.spi.ILockDatabase;
import de.metas.lock.spi.impl.SqlLockDatabase;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.IQuery;

import java.util.List;

public class LockManager implements ILockManager
{
	private final ILockDatabase lockDatabase = new SqlLockDatabase();

	public ILockDatabase getLockDatabase()
	{
		return lockDatabase;
	}

	@Override
	public final ILockCommand lock()
	{
		return new LockCommand(getLockDatabase());
	}

	@Override
	public final boolean lock(final Object model)
	{
		final int countLocked = lock()
				.setOwner(LockOwner.NONE)
				.setFailIfAlreadyLocked(false)
				.setRecordByModel(model)
				.acquire()
				.getCountLocked();
		return countLocked == 1;
	}

	@Override
	public final boolean lock(final int adTableId, final int recordId)
	{
		if (recordId < 0)
		{
			return false;
		}

		final int countLocked = lock()
				.setOwner(LockOwner.NONE)
				.setFailIfAlreadyLocked(false)
				.setRecordByTableRecordId(adTableId, recordId)
				.acquire()
				.getCountLocked();
		return countLocked == 1;
	}

	@Override
	public final IUnlockCommand unlock()
	{
		return new UnlockCommand(getLockDatabase());
	}

	@Override
	public final boolean unlock(final Object model)
	{
		Check.assume(model != null, "model not null");

		final int countUnlocked = unlock()
				.setOwner(LockOwner.ANY)
				.setRecordByModel(model)
				.release();
		return countUnlocked > 0;
	}

	@Override
	public boolean isLocked(final int adTableId, final int recordId)
	{
		return getLockDatabase().isLocked(adTableId, recordId, LockOwner.ANY);
	}

	@Override
	public boolean isLocked(final Class<?> modelClass, final int recordId)
	{
		return getLockDatabase().isLocked(modelClass, recordId, LockOwner.ANY);
	}

	@Override
	public boolean isLocked(final Class<?> modelClass, final int recordId, final LockOwner lockOwner)
	{
		return getLockDatabase().isLocked(modelClass, recordId, lockOwner);
	}

	@Override
	public boolean isLocked(final Object model)
	{
		return getLockDatabase().isLocked(model, LockOwner.ANY);
	}

	@Override
	public boolean isLocked(final Object model, LockOwner lockOwner)
	{
		return getLockDatabase().isLocked(model, lockOwner);
	}

	@Override
	public final <T> T retrieveAndLock(final IQuery<T> query, final Class<T> clazz)
	{
		return getLockDatabase().retrieveAndLock(query, clazz);
	}

	@Override
	public final String getNotLockedWhereClause(final String tableName, final String joinColumnNameFQ)
	{
		return getLockDatabase().getNotLockedWhereClause(tableName, joinColumnNameFQ);
	}

	@Override
	public final <T> IQueryFilter<T> getNotLockedFilter(final Class<T> modelClass)
	{
		return getLockDatabase().getNotLockedFilter(modelClass);
	}

	@Override
	public String getLockedWhereClause(final Class<?> modelClass, final String joinColumnNameFQ, final LockOwner lockOwner)
	{
		return getLockDatabase().getLockedWhereClause(modelClass, joinColumnNameFQ, lockOwner);
	}

	@Override
	public final <T> IQueryFilter<T> getLockedByFilter(final Class<T> modelClass, final LockOwner lockOwner)
	{
		return getLockDatabase().getLockedByFilter(modelClass, lockOwner);
	}

	@Override
	public ILock getExistingLockForOwner(final LockOwner lockOwner)
	{
		return getLockDatabase().retrieveLockForOwner(lockOwner);
	}

	@Override
	public <T> IQueryFilter<T> getNotLockedFilter(@NonNull String modelTableName, @NonNull String joinColumnNameFQ)
	{
		return getLockDatabase().getNotLockedFilter(modelTableName, joinColumnNameFQ);
	}

	@Override
	public <T> IQueryBuilder<T> getLockedRecordsQueryBuilder(final Class<T> modelClass, final Object contextProvider)
	{
		return getLockDatabase().getLockedRecordsQueryBuilder(modelClass, contextProvider);
	}

	@Override
	public int removeAutoCleanupLocks()
	{
		return getLockDatabase().removeAutoCleanupLocks();
	}

	@Override
	public <T> List<T> retrieveAndLockMultipleRecords(@NonNull final IQuery<T> query, @NonNull final Class<T> clazz)
	{
		return getLockDatabase().retrieveAndLockMultipleRecords(query, clazz);
	}

	@Override
	public <T> IQuery<T> addNotLockedClause(final IQuery<T> query)
	{
		return getLockDatabase().addNotLockedClause(query);
	}

	@Override
	public ExistingLockInfo getLockInfo(final TableRecordReference tableRecordReference, final LockOwner lockOwner)
	{
		return getLockDatabase().getLockInfo(tableRecordReference, lockOwner);
	}

	@Override
	public SetMultimap<TableRecordReference, ExistingLockInfo> getLockInfosByRecordIds(final @NonNull TableRecordReferenceSet recordRefs)
	{
		return getLockDatabase().getLockInfosByRecordIds(recordRefs);
	}
}

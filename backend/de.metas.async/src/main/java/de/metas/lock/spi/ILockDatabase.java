package de.metas.lock.spi;

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
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.IQuery;

import java.util.List;

/**
 * Locks database. Use {@link ILockManager} to get an instance
 */
public interface ILockDatabase
{
	boolean isLocked(Class<?> modelClass, int recordId, LockOwner lockOwner);

	boolean isLocked(int adTableId, int recordId, LockOwner lockOwner);

	boolean isLocked(Object model, LockOwner lockOwner);

	ILock lock(ILockCommand lockCommand);

	int unlock(IUnlockCommand unlockCommand);

	<T> T retrieveAndLock(IQuery<T> query, Class<T> clazz);

	<T> IQueryFilter<T> getNotLockedFilter(@NonNull String modelTableName, @NonNull String joinColumnNameFQ);

	<T> IQueryFilter<T> getLockedByFilter(Class<T> modelClass, LockOwner lockOwner);

	<T> IQueryFilter<T> getNotLockedFilter(Class<T> modelClass);

	String getNotLockedWhereClause(String tableName, String joinColumnNameFQ);

	/**
	 * See {@link ILockManager#getLockedWhereClause(Class, String, LockOwner)}.
	 */
	String getLockedWhereClause(@NonNull Class<?> modelClass, @NonNull String joinColumnNameFQ, @NonNull LockOwner lockOwner);

	ILock retrieveLockForOwner(LockOwner lockOwner);

	<T> IQueryBuilder<T> getLockedRecordsQueryBuilder(Class<T> modelClass, Object contextProvider);

	<T> List<T> retrieveAndLockMultipleRecords(IQuery<T> query, Class<T> clazz);

	<T> IQuery<T> addNotLockedClause(IQuery<T> query);

	int removeAutoCleanupLocks();

	ExistingLockInfo getLockInfo(TableRecordReference tableRecordReference, LockOwner lockOwner);

	SetMultimap<TableRecordReference, ExistingLockInfo> getLockInfosByRecordIds(@NonNull TableRecordReferenceSet recordRefs);
}

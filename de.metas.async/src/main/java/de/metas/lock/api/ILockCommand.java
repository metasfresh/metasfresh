package de.metas.lock.api;

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

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.lang.ITableRecordReference;

/**
 * Lock acquire/Lock change command.
 *
 * @author tsa
 *
 */
public interface ILockCommand
{
	/**
	 * Also see {@link ILockCommand#setAllowAdditionalLocks(AllowAdditionalLocks)}.
	 *
	 * @task http://dewiki908/mediawiki/index.php/09849_allow_multiple_locks_with_different_owners_on_the_same_record_%28106402984513%29
	 */
	enum AllowAdditionalLocks
	{
		/**
		 * No other lock can be obtained for the same record(s). This is the default behavior.
		 */
		NEVER,

		/**
		 * Another lock can be obtained for the same record, but only if the lock owner's name is different.<br>
		 * Important notes:
		 * <ul>
		 * <li>lock owner instances obtained via {@link LockOwner#newOwner()} and its overloaded siblings generally have different names and count as different with respect to this enum value.</li>
		 * <li>With the current implementation, it is possible to obtain a lock with <code>FOR_DIFFERENT_OWNERS</code>, even if the same record was already locked with {@link #NEVER} by another owner</li>
		 * <li>I (ts) don't wish to drive this locking-thing further. Instead, should use existing tools for distributed locking, such as apache-zookeeper in the future.</li>
		 * </ul>
		 * <p>
		 * See {@link ILockCommand#setAllowAdditionalLocks(AllowAdditionalLocks)} for a use case where this feature makes sense.
		 */
		FOR_DIFFERENT_OWNERS
	};

	/**
	 * Default value for {@link #isFailIfNothingLocked()} is <code>true</code>.
	 *
	 * NOTE: we decided to do so because in most of the cases, when there is nothing inserted it's because the selection is created in a transaction which is not commited yet, and debugging this issue
	 * could be quite hard.
	 */
	boolean DEFAULT_FailIfNothingLocked = true;

	ILock acquire();

	Future<ILock> acquireBeforeTrxCommit(String trxName);

	ILock getParentLock();

	/**
	 * Sets owner/new owner of the lock which will be acquired.
	 * <p>
	 * <b>IMPORTANT: </b> the lock owner's name is part of the <code>T_Lock</code> record, so don't use {@link LockOwner#ANY} or {@link LockOwner#NONE} if you are going to invoke {@link ILockManager#getLockedByFilter(Class, ILock)}, {@link ILockManager#getLockedRecordsQueryBuilder(Class, Object)} or {@link ILockManager#getLockedWhereClause(Class, String, ILock)}.
	 */
	ILockCommand setOwner(final LockOwner owner);

	/**
	 * @return owner/new owner of the lock which will be acquired; never returns <code>null</code>
	 */
	LockOwner getOwner();

	/**
	 * Specify if other locks can be obtained on the same records while this one is still in place.<br>
	 * If omitted, then {@link AllowAdditionalLocks#NEVER} will be the default.
	 * <p>
	 * Use case: you are obtaining a lock on the <code>DocumentEngine</code> level and don't care which other code will be invoked that might also lock the same document.<br>
	 * All you care about is that this document will <b>not</b> concurrently be processed by another <code>DocumentEngine</code>.<br>
	 * For this case, you can call <code>setAllowAdditionalLocks(AllowAdditionalLocks.FOR_DIFFERENT_OWNERS)</code>.
	 *
	 * @param multipleLocks
	 * @return
	 *
	 * @task http://dewiki908/mediawiki/index.php/09849_allow_multiple_locks_with_different_owners_on_the_same_record_%28106402984513%29
	 *
	 */
	ILockCommand setAllowAdditionalLocks(AllowAdditionalLocks allowAdditionalLocks);

	/**
	 * See {@link #setAllowAdditionalLocks(AllowAdditionalLocks)}.
	 *
	 * @return
	 */
	AllowAdditionalLocks getAllowAdditionalLocks();

	ILockCommand setAutoCleanup(boolean autoCleanup);

	boolean isAutoCleanup();

	ILockCommand setFailIfAlreadyLocked(boolean failIfAlreadyLocked);

	boolean isFailIfAlreadyLocked();

	/**
	 * Sets if the lock command shall fail if on {@link #acquire()} nothing was locked.
	 *
	 * By default this is {@link #DEFAULT_FailIfNothingLocked}
	 *
	 * @param failIfNothingLocked
	 */
	ILockCommand setFailIfNothingLocked(boolean failIfNothingLocked);

	boolean isFailIfNothingLocked();

	ILockCommand setRecordByModel(final Object model);

	ILockCommand setRecordByTableRecordId(final int tableId, final int recordId);

	ILockCommand addRecord(ITableRecordReference record);

	ILockCommand addRecordByModel(Object model);

	ILockCommand addRecordsByModel(Collection<?> models);

	Iterator<ITableRecordReference> getRecordsToLockIterator();

	ILockCommand setRecordsBySelection(Class<?> modelClass, int adPIstanceId);

	int getSelectionToLock_AD_Table_ID();

	int getSelectionToLock_AD_PInstance_ID();

	<T> ILockCommand setSetRecordsByFilter(Class<T> modelClass, IQueryFilter<T> filters);

	IQueryFilter<?> getSelectionToLock_Filters();
}

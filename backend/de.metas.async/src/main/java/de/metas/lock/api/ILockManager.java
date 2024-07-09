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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.SetMultimap;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.lock.spi.ExistingLockInfo;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.IQuery;

import java.util.List;

/**
 * Lock manager - this is the starting point for manipulating the locks
 *
 * @author tsa
 *
 */
public interface ILockManager extends ISingletonService
{
	/** @return true if given record is locked */
	boolean isLocked(int adTableId, int recordId);

	/** @return true if given record is locked */
	boolean isLocked(Class<?> modelClass, int recordId);

	/**
	 * @param lockOwner lock owner; if null it will check if the record is locked by any owner
	 * @return true if the record is locked by given lock owner
	 */
	boolean isLocked(Class<?> modelClass, int recordId, LockOwner lockOwner);

	/** Starting building a lock command */
	ILockCommand lock();

	/**
	 * Tries to lock given persistent model
	 *
	 * @return true if locked, false if the record was already locked
	 */
	boolean lock(Object model);

	/**
	 * Tries to lock given persistent model
	 *
	 * @return true if locked, false if the record was already locked
	 */
	boolean lock(int adTableId, int recordId);

	/** Starting building a unlock command */
	IUnlockCommand unlock();

	/**
	 * Tries to unlock given persistent model
	 *
	 * @return true if unlocked
	 */
	boolean unlock(Object model);

	/** @return true if given model is locked by any lock */
	boolean isLocked(Object model);

	boolean isLocked(Object model, LockOwner lockOwner);

	/**
	 * Retrieves next model from query and locks it (using {@link LockOwner#NONE}.
	 *
	 * @return retrieved record (already locked)
	 */
	<T> T retrieveAndLock(IQuery<T> query, Class<T> clazz);

	/**
	 * Builds a SQL where clause to be used in other queries to filter the results.
	 * <p>
	 * For example, for table name 'MyTable' and join column 'MyTable.MyTable_ID', this method could return:
	 *
	 * <pre>
	 * NOT EXISTS ("SELECT 1 FROM LockTable zz WHERE zz.AD_Table_ID=12345 AND zz.Record_ID=MyTable.MyTable_ID
	 * </pre>
	 *
	 * but this is dependent on implementation.
	 *
	 * @param joinColumnNameFQ fully qualified record id column name
	 * @return SQL where clause
	 * 
	 * @deprecated Please consider using {@link #getNotLockedFilter(Class)}.
	 */
	@Deprecated
	String getNotLockedWhereClause(String tableName, String joinColumnNameFQ);

	/**
	 * @return filter which accepts only the NOT locked records of given model class.
	 */
	<T> IQueryFilter<T> getNotLockedFilter(Class<T> modelClass);

	/**
	 * Builds a SQL where clause used to filter records locked by a given {@link LockOwner}. Also see {@link #getNotLockedWhereClause(String, String)} for information.
	 *
	 * @param lockOwner may <b>not</b> be <code>null</code>
	 * @return SQL where clause
	 * 
	 * @deprecated Please consider using {@link #getLockedByFilter(Class, LockOwner)}. Only keep in mind, while this method accepts a null <code>lock</code>, the {@link #getLockedByFilter(Class, LockOwner)} enforces a mandatory lock.
	 */
	@Deprecated
	String getLockedWhereClause(Class<?> modelClass, String joinColumnNameFQ, LockOwner lockOwner);

	/**
	 * @param lockOwner lock owner (mandatory)
	 * @return filter which accepts only those records which were locked by given {@link LockOwner}.
	 */
	<T> IQueryFilter<T> getLockedByFilter(Class<T> modelClass, LockOwner lockOwner);
	
	default <T> IQueryFilter<T> getLockedByFilter(final Class<T> modelClass, final ILock lock)
	{
		return getLockedByFilter(modelClass, lock == null ? null : lock.getOwner());
	}


	/**
	 * Gets existing lock for given owner name
	 *
	 * @return existing lock
	 * @throws LockFailedException in case lock does not exist
	 */
	ILock getExistingLockForOwner(LockOwner lockOwner);

	<T> IQueryFilter<T> getNotLockedFilter(@NonNull String modelTableName, @NonNull String joinColumnNameFQ);

	/**
	 * Create and return a query builder that allows to retrieve all records of the given <code>modelClass</code> which are currently locked.
	 * <p>
	 * Note that the query builder does not specify any ordering.
	 */
	<T> IQueryBuilder<T> getLockedRecordsQueryBuilder(Class<T> modelClass, Object contextProvider);

	<T> List<T> retrieveAndLockMultipleRecords(IQuery<T> query, Class<T> clazz);

	<T> IQuery<T> addNotLockedClause(IQuery<T> query);
	
	int removeAutoCleanupLocks();

	ExistingLockInfo getLockInfo(TableRecordReference tableRecordReference, LockOwner lockOwner);

	SetMultimap<TableRecordReference, ExistingLockInfo> getLockInfosByRecordIds(@NonNull TableRecordReferenceSet recordRefs);
}

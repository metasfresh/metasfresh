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
	ILock acquire();

	Future<ILock> acquireBeforeTrxCommit(String trxName);

	ILock getParentLock();

	/** Sets owner/new owner of the lock which will be acquired */
	ILockCommand setOwner(final LockOwner owner);

	/** @return owner/new owner of the lock which will be acquired; never returns <code>null</code> */
	LockOwner getOwner();

	ILockCommand setAutoCleanup(final boolean autoCleanup);

	boolean isAutoCleanup();

	ILockCommand setFailIfAlreadyLocked(boolean failIfAlreadyLocked);

	boolean isFailIfAlreadyLocked();

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

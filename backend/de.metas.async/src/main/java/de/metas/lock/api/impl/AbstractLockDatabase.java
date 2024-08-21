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
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockCommand.AllowAdditionalLocks;
import de.metas.lock.api.IUnlockCommand;
import de.metas.lock.api.LockOwner;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.lock.exceptions.UnlockFailedException;
import de.metas.lock.spi.ILockDatabase;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract lock database which does not implement any database specific logic.
 *
 * @author tsa
 */
public abstract class AbstractLockDatabase implements ILockDatabase
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	/**
	 * Asserts given lock owner is a valid owner to be used on for Locks
	 */
	protected static void assertValidLockOwner(@NonNull final LockOwner lockOwner)
	{
		// NOTE: LockOwner.ANY is not tolerated because that's a filter criteria and not a LockOwner that we could use for assigning
		Check.assume(lockOwner.isRealOwnerOrNoOwner(), "Lock owner shall be real owner or no owner but it was {}", lockOwner);
	}

	@Override
	public final boolean isLocked(final Class<?> modelClass, final int recordId, final LockOwner lockOwner)
	{
		final int adTableId = InterfaceWrapperHelper.getTableId(modelClass);
		return isLocked(adTableId, recordId, lockOwner);
	}

	@Override
	public final boolean isLocked(final Object model, final LockOwner lockOwner)
	{
		Check.assume(model != null, "model not null");

		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		return isLocked(adTableId, recordId, lockOwner);
	}

	@Override
	public final ILock lock(final ILockCommand lockCommand)
	{
		final int countLocked;

		if (lockCommand.getSelectionToLock_Filters() != null)
		{
			countLocked = lockByFilters(lockCommand);
		}
		//
		// Lock by selection
		else if (lockCommand.getSelectionToLock_AD_PInstance_ID() != null)
		{
			if (lockCommand.getParentLock() != null)
			{
				throw new LockFailedException("Changing the lock for a given selection (AD_PInstance_ID) is not supported)")
						.setLockCommand(lockCommand);
			}
			countLocked = lockBySelection(lockCommand);
		}
		//
		// Lock by iterator
		else if (lockCommand.getRecordsToLockIterator() != null)
		{
			countLocked = lockByIterator(lockCommand);
		}
		//
		// No lock records were specified
		else
		{
			throw new LockFailedException("No records to be locked were specified")
					.setLockCommand(lockCommand);
		}

		return newLock(lockCommand.getOwner(), lockCommand.isAutoCleanup(), countLocked);
	}

	protected final ILock newLock(final LockOwner lockOwner, final boolean autoCleanup, final int countLocked)
	{
		return new Lock(this, lockOwner, autoCleanup, countLocked);
	}

	/**
	 * Lock all records specified by {@link LockCommand#getSelectionToLock_AD_PInstance_ID()}.
	 *
	 * @return how many records were locked
	 */
	protected abstract int lockBySelection(ILockCommand lockCommand);

	protected abstract int lockByFilters(ILockCommand lockCommand);

	/**
	 * Lock all records specified by {@link LockCommand#getRecordsToLockIterator()}.
	 *
	 * @return how many records were locked
	 */
	private int lockByIterator(@NonNull final ILockCommand lockCommand)
	{
		final Iterator<TableRecordReference> records = lockCommand.getRecordsToLockIterator();
		Check.assumeNotNull(records, "records not null");

		final boolean failIfAlreadyLocked = lockCommand.isFailIfAlreadyLocked();
		final boolean changeLock = lockCommand.getParentLock() != null;
		int countLocked = 0;
		while (records.hasNext())
		{
			final TableRecordReference record = records.next();

			//
			// Acquire/Change the lock
			final boolean locked;
			if (changeLock)
			{
				locked = changeLockRecord(lockCommand, record);
			}
			else
			{
				locked = lockRecord(lockCommand, record);
			}

			// Increment the locked counter
			if (locked)
			{
				countLocked++;
			}

			//
			// If lock could not be acquired/changed and we were asked to fail, do so
			if (failIfAlreadyLocked && !locked)
			{
				// NOTE: we are checking this just to me sure, but basically, the "lockRecord" method is already throwing an exception in this case
				throw new LockFailedException("Record was already locked: " + record)
						.setLockCommand(lockCommand)
						.setRecordToLock(record);
			}
		}

		return countLocked;
	}

	/**
	 * Locks a single record.
	 *
	 * @return <ul>
	 * <li><code>true</code> if record was locked
	 * <li><code>false</code> if records was NOT locked because it's already locked and {@link LockCommand#isFailIfAlreadyLocked()} is false
	 * </ul>
	 * @throws LockFailedException if locking failed
	 */
	protected abstract boolean lockRecord(final ILockCommand lockCommand, final TableRecordReference record);

	/**
	 * Change the lock of given record.
	 *
	 * @return true if lock was changed
	 */
	protected abstract boolean changeLockRecord(final ILockCommand lockCommand, final TableRecordReference record);

	@Override
	public final int unlock(final IUnlockCommand unlockCommand)
	{
		final int countUnlocked;

		//
		// Unlock by selection
		if (unlockCommand.getSelectionToUnlock_AD_PInstance_ID() != null)
		{
			countUnlocked = unlockBySelection(unlockCommand);
		}
		//
		// Unlock by iterator
		else if (unlockCommand.getRecordsToUnlockIterator() != null)
		{
			countUnlocked = unlockByIterator(unlockCommand);
		}
		//
		// Unlock by owner
		else if (!unlockCommand.getOwner().isAnyOwner())
		{
			countUnlocked = unlockByOwner(unlockCommand);
		}
		//
		// No lock records were specified
		else
		{
			throw new UnlockFailedException("No records to be locked were specified")
					.setUnlockCommand(unlockCommand);
		}

		return countUnlocked;
	}

	protected abstract int unlockBySelection(final IUnlockCommand unlockCommand);

	protected abstract int unlockByOwner(final IUnlockCommand unlockCommand);

	private int unlockByIterator(final IUnlockCommand unlockCommand)
	{
		final Iterator<TableRecordReference> records = unlockCommand.getRecordsToUnlockIterator();
		Check.assumeNotNull(records, "records not null");

		int countUnlocked = 0;
		while (records.hasNext())
		{
			final TableRecordReference record = records.next();
			final boolean unlocked = unlockRecord(unlockCommand, record);
			if (unlocked)
			{
				countUnlocked++;
			}
		}

		return countUnlocked;
	}

	protected abstract boolean unlockRecord(IUnlockCommand unlockCommand, TableRecordReference record);

	@Override
	public final <T> T retrieveAndLock(final IQuery<T> query, final Class<T> clazz)
	{
		final IQuery<T> finalQuery = retrieveNotLockedQuery(query);

		final int maxLockRetries = 50;
		final ILockCommand lockCommand = new LockCommand(this)
				.setOwner(LockOwner.NONE);

		int retryCounter = 0;
		while (retryCounter < maxLockRetries)
		{

			// find the next work package
			final T model = finalQuery.first(clazz);

			if (model == null)
			{
				// there is no record available
				return null;
			}

			// attempt to get a lock
			final TableRecordReference record = TableRecordReference.of(model);
			if (lockRecord(lockCommand, record))
			{
				// Successfully acquired our lock :-)
				return model;
			}
			else
			{
				retryCounter++;
			}
		}

		// We attempted to select and lock an item 'maxLockRetries' times in a row
		// and every time the item we selected (at select time it wasn't locked yet) was then locked by another
		// DB-client before we could lock it. This means that we are either too slow to do anything meaningful at all
		// or that there are already way too many clients attempting to find work on this table
		logger.info("Unable to select and lock a record in {} after {} retries."
				+ ". Giving up, because we are either too slow or there are too many concurent DB clients looking for work.", query.getTableName(), maxLockRetries);

		return null;
	}

	@Override
	public final String getLockedWhereClause(final @NonNull Class<?> modelClass, final @NonNull String joinColumnNameFQ, @NonNull final LockOwner lockOwner)
	{
		return getLockedWhereClauseAllowNullLock(modelClass, joinColumnNameFQ, lockOwner);
	}

	@Override
	public final <T> IQueryBuilder<T> getLockedRecordsQueryBuilder(final Class<T> modelClass, final Object contextProvider)
	{
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(modelClass);
		final String joinColumnNameFQ = InterfaceWrapperHelper.getTableName(modelClass) + "." + keyColumnName;

		final String lockedRecordsSQL = getLockedWhereClauseAllowNullLock(modelClass, joinColumnNameFQ, null);

		// note: don't specify a particular ordering; leave that freedom to the caller if this method
		return Services.get(IQueryBL.class).createQueryBuilder(modelClass, contextProvider)
				.addOnlyActiveRecordsFilter()
				// .addOnlyContextClientOrSystem() // avoid applying context client because in some cases context is not available
				.filter(TypedSqlQueryFilter.of(lockedRecordsSQL));
	}

	@Override
	@NonNull
	public final <T> List<T> retrieveAndLockMultipleRecords(@NonNull final IQuery<T> query, @NonNull final Class<T> clazz)
	{
		final ILockCommand lockCommand = new LockCommand(this)
				.setOwner(LockOwner.NONE);

		final ImmutableList.Builder<T> lockedModelsCollector = ImmutableList.builder();

		final List<T> models = query.list(clazz);

		if (models == null || models.isEmpty())
		{
			return ImmutableList.of();
		}

		models.forEach(model -> {
			final TableRecordReference record = TableRecordReference.of(model);

			if (lockRecord(lockCommand, record))
			{
				lockedModelsCollector.add(model);
			}
		});

		final ImmutableList<T> lockedModels = lockedModelsCollector.build();

		if (lockedModels.size() != models.size())
		{
			logger.warn("*** retrieveAndLockMultipleRecords: not all retrieved records could be locked! expectedLockedSize: {}, actualLockedSize: {}"
					, models.size(), lockedModels.size());
		}

		return lockedModels;
	}

	public <T> IQuery<T> addNotLockedClause(final IQuery<T> query)
	{
		return retrieveNotLockedQuery(query);
	}

	/**
	 * @return <code>true</code> if the given <code>allowAdditionalLocks</code> is <code>FOR_DIFFERENT_OWNERS</code>.
	 */
	protected static boolean isAllowMultipleOwners(final AllowAdditionalLocks allowAdditionalLocks)
	{
		return allowAdditionalLocks == AllowAdditionalLocks.FOR_DIFFERENT_OWNERS;
	}

	protected abstract <T> IQuery<T> retrieveNotLockedQuery(IQuery<T> query);

	protected abstract String getLockedWhereClauseAllowNullLock(@NonNull final Class<?> modelClass, @NonNull final String joinColumnNameFQ, @Nullable final LockOwner lockOwner);
}

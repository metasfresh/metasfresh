package de.metas.lock.spi.impl;

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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.POJOInSelectionQueryFilter;
import org.adempiere.ad.dao.impl.POJOQuery;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.concurrent.CloseableReentrantLock;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.util.Util.ArrayKey;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.IUnlockCommand;
import de.metas.lock.api.LockOwner;
import de.metas.lock.api.impl.AbstractLockDatabase;
import de.metas.lock.exceptions.LockFailedException;

/**
 * In-memory locks database
 *
 * @author tsa
 *
 */
public class PlainLockDatabase extends AbstractLockDatabase
{
	private final CloseableReentrantLock mainLock = new CloseableReentrantLock();
	private final Map<ArrayKey, LockInfo> locks = new LinkedHashMap<>();

	private final ArrayKey createKey(final int adTableId, final int recordId)
	{
		Check.assume(adTableId > 0, "adTableId > 0");
		Check.assume(recordId > 0, "recordId > 0");
		final ArrayKey key = new ArrayKey(adTableId, recordId);
		return key;
	}

	private final ArrayKey createKey(final ITableRecordReference record, final ILockCommand lockCommand)
	{
		Check.assumeNotNull(record, "record not null");
		final ArrayKey key;
		if (lockCommand != null && isAllowMultipleOwners(lockCommand.getAllowAdditionalLocks()))
		{
			final String ownerName = lockCommand.getOwner().getOwnerName();
			key = createKeyForRecordAndOwner(record, ownerName);
		}
		else
		{
			key = createKeyforRecord(record);
		}

		return key;
	}

	private ArrayKey createKeyForRecordAndOwner(final ITableRecordReference record, final String ownerName)
	{
		final ArrayKey key;
		key = new ArrayKey(
				record.getAD_Table_ID(),
				record.getRecord_ID(),
				ownerName);
		return key;
	}

	private ArrayKey createKeyforRecord(final ITableRecordReference record)
	{
		return new ArrayKey(
				record.getAD_Table_ID(),
				record.getRecord_ID());
	}

	@Override
	public boolean isLocked(final int adTableId, final int recordId, final ILock lockedBy)
	{
		try (final CloseableReentrantLock lock = mainLock.open())
		{
			final ArrayKey key = createKey(adTableId, recordId);
			final LockInfo lockInfo = locks.get(key);
			if (lockInfo == null)
			{
				return false;
			}

			if (lockedBy == ILock.NULL)
			{
				return true;
			}

			final LockOwner lockOwner = lockedBy.getOwner();
			return Check.equals(lockOwner, lockInfo.getLockOwner());
		}
	}

	@Override
	protected int lockBySelection(final ILockCommand lockCommand)
	{
		final int adTableId = lockCommand.getSelectionToLock_AD_Table_ID();
		final int adPInstanceId = lockCommand.getSelectionToLock_AD_PInstance_ID();

		int countLocked = 0;
		for (final ITableRecordReference record : retrieveSelection(adTableId, adPInstanceId))
		{
			final boolean locked = lockRecord(lockCommand, record);
			if (locked)
			{
				countLocked++;
			}
		}

		return countLocked;
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/08756_EDI_Lieferdispo_Lieferschein_und_Complete_%28101564484292%29
	 */
	@Override
	protected int lockByFilters(ILockCommand lockCommand)
	{
		@SuppressWarnings("unchecked")
		final IQueryFilter<Object> selectionToLockFilters = (IQueryFilter<Object>)lockCommand.getSelectionToLock_Filters();

		final LockOwner lockOwner = lockCommand.getOwner();
		assertValidLockOwner(lockOwner);
		
		//
		// Retrieve records to lock
		final int adTableId = lockCommand.getSelectionToLock_AD_Table_ID();
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final String tableName = adTableDAO.retrieveTableName(adTableId);
		Comparator<Object> orderByComparator = null; // don't care
		final List<Object> recordsToLock = POJOLookupMap.get().getRecords(tableName, Object.class, selectionToLockFilters, orderByComparator);
		
		int countLocked = 0;
		for (final Object recordToLock : recordsToLock)
		{
			final ITableRecordReference recordToLockRef = TableRecordReference.of(recordToLock);
			final boolean locked = lockRecord(lockCommand, recordToLockRef);
			if(!locked)
			{
				throw new LockFailedException("Record already locked: "+recordToLock)
				.setLockCommand(lockCommand);
			}
			
			if (locked)
			{
				countLocked++;
			}
		}
		
		if (countLocked <= 0 && lockCommand.isFailIfNothingLocked())
		{
			throw new LockFailedException("Nothing locked for selection");
		}
		
		return countLocked;
	}

	@Override
	protected boolean lockRecord(final ILockCommand lockCommand, final ITableRecordReference record)
	{
		final ArrayKey recordKey = createKey(record, lockCommand);

		try (final CloseableReentrantLock lock = mainLock.open())
		{
			if (locks.containsKey(recordKey))
			{
				logger.warn("Already found a lock for key " + recordKey + ". Info: " + locks.get(recordKey));
				return false;
			}

			locks.put(recordKey, new LockInfo(recordKey, lockCommand));
			return true;
		}
	}

	@Override
	protected boolean changeLockRecord(final ILockCommand lockCommand, final ITableRecordReference record)
	{
		final ArrayKey recordKey = createKey(record, lockCommand);

		try (final CloseableReentrantLock lock = mainLock.open())
		{
			final LockInfo lockInfo = locks.get(recordKey);
			if (lockInfo == null)
			{
				return false;
			}

			lockInfo.setLockOwner(lockCommand.getOwner());
			lockInfo.setAutoCleanup(lockCommand.isAutoCleanup());
			return true;
		}
	}

	@Override
	protected boolean unlockRecord(final IUnlockCommand unlockCommand, final ITableRecordReference record)
	{
		final LockOwner ownerRequired = unlockCommand.getOwner();

		final boolean unlockForRecord = unlockForKey(ownerRequired, createKeyforRecord(record));
		final boolean unlockForRecordAndOwner = unlockForKey(ownerRequired, createKeyForRecordAndOwner(record, unlockCommand.getOwner().getOwnerName()));

		return unlockForRecord || unlockForRecordAndOwner;
	}

	private boolean unlockForKey(final LockOwner ownerRequired, final ArrayKey recordKey)
	{
		try (final CloseableReentrantLock lock = mainLock.open())
		{
			final LockInfo lockInfo = locks.get(recordKey);
			if (lockInfo == null)
			{
				return false;
			}

			// Make sure it was the same owner
			if (!isLockOwnerMatch(ownerRequired, lockInfo.getLockOwner()))
			{
				return false;
			}

			// Actually remove the lock
			locks.remove(recordKey);
			return true;
		}
	}

	@Override
	protected int unlockByOwner(final IUnlockCommand unlockCommand)
	{
		final LockOwner lockOwner = unlockCommand.getOwner();
		assertValidLockOwner(lockOwner);

		int countUnlocked = 0;
		try (final CloseableReentrantLock lock = mainLock.open())
		{
			for (final Iterator<LockInfo> lockInfos = locks.values().iterator(); lockInfos.hasNext();)
			{
				final LockInfo lockInfo = lockInfos.next();

				// Make sure it was the same owner
				if (!isLockOwnerMatch(lockOwner, lockInfo.getLockOwner()))
				{
					continue;
				}

				lockInfos.remove();
				countUnlocked++;
			}
		}
		return countUnlocked;
	}

	@Override
	protected int unlockBySelection(final IUnlockCommand unlockCommand)
	{
		final int adTableId = unlockCommand.getSelectionToUnlock_AD_Table_ID();
		final int adPInstanceId = unlockCommand.getSelectionToUnlock_AD_PInstance_ID();

		int countUnlocked = 0;
		for (final ITableRecordReference record : retrieveSelection(adTableId, adPInstanceId))
		{
			final boolean unlocked = unlockRecord(unlockCommand, record);
			if (unlocked)
			{
				countUnlocked++;
			}
		}

		return countUnlocked;
	}

	private List<ITableRecordReference> retrieveSelection(final int adTableId, final int adPInstanceId)
	{
		// NOTE: below comes a fucked up, not optimum implementation shit which shall do the work for testing

		final POJOInSelectionQueryFilter<Object> filter = POJOInSelectionQueryFilter.inSelection(adPInstanceId);

		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
		final List<ITableRecordReference> records = new ArrayList<>();
		for (final Object model : POJOLookupMap.get().getRawRecords(tableName))
		{
			if (!filter.accept(model))
			{
				continue;
			}

			final ITableRecordReference record = TableRecordReference.of(model);
			records.add(record);
		}

		return records;
	}

	public List<ArrayKey> getLocks()
	{
		try (final CloseableReentrantLock lock = mainLock.open())
		{
			return new ArrayList<>(locks.keySet());
		}
	}

	@Override
	protected <T> IQuery<T> retrieveNotLockedQuery(final IQuery<T> query)
	{
		final POJOQuery<T> pojoQuery = (POJOQuery<T>)query;
		final POJOQuery<T> pojoQueryFinal = pojoQuery.copy();

		// filter out locked records
		pojoQueryFinal.addFilter(new IQueryFilter<T>()
		{
			@Override
			public boolean accept(final T model)
			{
				return !isLocked(model, ILock.NULL);
			}
		});

		return pojoQueryFinal;
	}

	private Object getObjectForKey(final ArrayKey key)
	{
		final int tableId = (Integer)key.getArray()[0];
		final int recordId = (Integer)key.getArray()[1];
		return POJOLookupMap.get().lookup(tableId, recordId);
	}

	public List<Object> getLockedObjects()
	{
		final List<Object> result = new ArrayList<Object>();
		for (final ArrayKey key : getLocks())
		{
			final Object model = getObjectForKey(key);
			if (model != null)
			{
				result.add(model);
			}
		}

		return result;
	}

	public String getLockedObjectsInfo()
	{
		try (final CloseableReentrantLock lock = mainLock.open())
		{
			final StringBuilder sb = new StringBuilder();
			for (final ArrayKey key : locks.keySet())
			{
				if (sb.length() > 0)
				{
					sb.append("\n");
				}

				sb.append("*** key=" + key);

				final Object model = getObjectForKey(key);
				sb.append(", object=" + model);

				sb.append(", info=" + locks.get(key));
			}
			return sb.toString();
		}
	}

	public int getLocksCount()
	{
		try (final CloseableReentrantLock lock = mainLock.open())
		{
			return locks.size();
		}
	}

	private class LockInfo
	{
		@SuppressWarnings("unused")
		private final ArrayKey key;
		private LockOwner _lockOwner;
		private boolean autoCleanup;

		//
		// Debugging info:
		@SuppressWarnings("unused")
		private final Exception aquiredStackTrace;
		@SuppressWarnings("unused")
		private final String aquiredThreadName;

		public LockInfo(final ArrayKey key, final ILockCommand lockCommand)
		{
			super();

			Check.assumeNotNull(lockCommand, "lockCommand not null");

			Check.assumeNotNull(key, "key not null");
			this.key = key;

			aquiredStackTrace = new Exception("Aquired stack trace");
			aquiredThreadName = Thread.currentThread().getName();

			setLockOwner(lockCommand.getOwner());
			setAutoCleanup(lockCommand.isAutoCleanup());
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public LockOwner getLockOwner()
		{
			return _lockOwner;
		}

		public void setLockOwner(final LockOwner lockOwner)
		{
			assertValidLockOwner(lockOwner);
			_lockOwner = lockOwner;
		}

		public boolean isAutoCleanup()
		{
			return autoCleanup;
		}

		public void setAutoCleanup(final boolean autoCleanup)
		{
			this.autoCleanup = autoCleanup;
		}
	}

	@Override
	public final <T> IQueryFilter<T> getLockedByFilter(final Class<T> modelClass, final ILock lock)
	{
		return new IQueryFilter<T>()
		{
			@Override
			public boolean accept(final T model)
			{
				return isLocked(model, lock);
			}
		};
	}

	@Override
	public <T> IQueryFilter<T> getNotLockedFilter(final Class<T> modelClass)
	{
		return new IQueryFilter<T>()
		{
			@Override
			public boolean accept(final T model)
			{
				return !isLocked(model, ILock.NULL);
			}
		};
	}

	@Override
	public String getNotLockedWhereClause(final String tableName, final String joinColumnName)
	{
		throw new UnsupportedOperationException("not supported (tableName=" + tableName + ", joinColumnName=" + joinColumnName + ")");
	}

	@Override
	protected String getLockedWhereClauseAllowNullLock(Class<?> modelClass, String joinColumnNameFQ, ILock lock)
	{
		return "1=1";
	}

	@Override
	public ILock retrieveLockForOwner(final LockOwner lockOwner)
	{
		Check.assumeNotNull(lockOwner, "Lock owner shall not be null");
		Check.assumeNotNull(lockOwner.isRealOwner(), "Lock owner shall be real owner but it was {}", lockOwner);

		final List<LockInfo> lockInfosForOwner = new ArrayList<>();
		final Set<Boolean> autoCleanups = new HashSet<>();

		try (CloseableReentrantLock l = mainLock.open())
		{
			for (final LockInfo lockInfo : locks.values())
			{
				if (!lockInfo.getLockOwner().equals(lockOwner))
				{
					continue;
				}

				lockInfosForOwner.add(lockInfo);
				autoCleanups.add(lockInfo.isAutoCleanup());
			}
		}

		if (autoCleanups.isEmpty())
		{
			throw new LockFailedException("No lock found for " + lockOwner);
		}
		else if (autoCleanups.size() > 1)
		{
			throw new LockFailedException("More then one lock found for " + lockOwner + ": " + lockInfosForOwner);
		}

		final boolean autoCleanup = ListUtils.singleElement(autoCleanups);
		final int countLocked = lockInfosForOwner.size();
		return newLock(lockOwner, autoCleanup, countLocked);
	}
}

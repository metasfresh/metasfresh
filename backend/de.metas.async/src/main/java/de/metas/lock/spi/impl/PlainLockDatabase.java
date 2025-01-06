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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.IUnlockCommand;
import de.metas.lock.api.LockOwner;
import de.metas.lock.api.impl.AbstractLockDatabase;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.lock.spi.ExistingLockInfo;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.POJOInSelectionQueryFilter;
import org.adempiere.ad.dao.impl.POJOQuery;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.concurrent.CloseableReentrantLock;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * In-memory locks database
 *
 * @author tsa
 */
public class PlainLockDatabase extends AbstractLockDatabase
{
	private static final Logger logger = LogManager.getLogger(PlainLockDatabase.class);

	private final CloseableReentrantLock mainLock = new CloseableReentrantLock();
	private final Map<LockKey, RecordLocks> locks = new LinkedHashMap<>();

	private Boolean failOnWarnings = null;

	@Override
	protected boolean isFailOnWarnings()
	{
		if (failOnWarnings != null)
		{
			return failOnWarnings;
		}
		
		return super.isFailOnWarnings();
	}

	public IAutoCloseable withFailOnWarnings(final boolean fail)
	{
		final Boolean failOnWarningsBackup = this.failOnWarnings;
		this.failOnWarnings = fail;
		return () -> this.failOnWarnings = failOnWarningsBackup;
	}

	public void dump()
	{
		System.out.println("\n\n\n LOCKS (" + getClass() + ") ------------------------------------------------------------ ");
		locks.values()
				.stream()
				.forEach(lock -> System.out.println(lock));
	}

	private final LockKey createKey(final int adTableId, final int recordId)
	{
		Check.assume(adTableId > 0, "adTableId > 0");
		Check.assume(recordId > 0, "recordId > 0");
		return LockKey.of(adTableId, recordId);
	}

	private LockKey createKeyForRecord(final TableRecordReference record)
	{
		return LockKey.of(
				record.getAD_Table_ID(),
				record.getRecord_ID());
	}

	@Override
	public boolean isLocked(final int adTableId, final int recordId, final LockOwner lockOwner)
	{
		try (final CloseableReentrantLock lock = mainLock.open())
		{
			final LockKey key = createKey(adTableId, recordId);
			final RecordLocks recordLock = locks.get(key);
			if (recordLock == null)
			{
				return false;
			}

			return recordLock.isLockedBy(lockOwner);
		}
	}

	@Override
	public <T> IQueryFilter<T> getNotLockedFilter(@NonNull final String modelTableName, @NonNull final String joinColumnNameFQ)
	{
		final int columnNameDotIdx = joinColumnNameFQ.lastIndexOf(".");
		final String columnName = columnNameDotIdx > 0 ? joinColumnNameFQ.substring(columnNameDotIdx + 1) : joinColumnNameFQ;
		final AdTableId modelTableId = TableIdsCache.instance.getTableId(modelTableName).get();

		return model -> {
			final Object recordIdObj = InterfaceWrapperHelper.getValue(model, columnName).get();
			final int recordId = NumberUtils.asInt(recordIdObj);
			return !isLocked(modelTableId.getRepoId(), recordId, LockOwner.ANY);
		};

	}

	@Override
	protected int lockBySelection(final ILockCommand lockCommand)
	{
		final int adTableId = lockCommand.getSelectionToLock_AD_Table_ID();
		final PInstanceId adPInstanceId = lockCommand.getSelectionToLock_AD_PInstance_ID();

		int countLocked = 0;
		for (final TableRecordReference record : retrieveSelection(adTableId, adPInstanceId))
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
	protected int lockByFilters(final ILockCommand lockCommand)
	{
		@SuppressWarnings("unchecked") final IQueryFilter<Object> selectionToLockFilters = (IQueryFilter<Object>)lockCommand.getSelectionToLock_Filters();

		final LockOwner lockOwner = lockCommand.getOwner();
		assertValidLockOwner(lockOwner);

		//
		// Retrieve records to lock
		final int adTableId = lockCommand.getSelectionToLock_AD_Table_ID();
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final String tableName = adTableDAO.retrieveTableName(adTableId);
		final Comparator<Object> orderByComparator = null; // don't care
		final List<Object> recordsToLock = POJOLookupMap.get().getRecords(tableName, Object.class, selectionToLockFilters, orderByComparator);

		int countLocked = 0;
		for (final Object recordToLock : recordsToLock)
		{
			final TableRecordReference recordToLockRef = TableRecordReference.of(recordToLock);
			final boolean locked = lockRecord(lockCommand, recordToLockRef);
			if (!locked)
			{
				throw new LockFailedException("Record already locked: " + recordToLock)
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
	protected boolean lockRecord(final ILockCommand lockCommand, final TableRecordReference record)
	{
		final LockKey recordKey = createKeyForRecord(record);

		try (final CloseableReentrantLock lock = mainLock.open())
		{
			final RecordLocks recordLock = locks.computeIfAbsent(recordKey, RecordLocks::new);
			return recordLock.addLock(new LockInfo(recordKey, lockCommand));
		}
	}

	@Override
	protected boolean changeLockRecord(final ILockCommand lockCommand, final TableRecordReference record)
	{
		final LockKey recordKey = createKeyForRecord(record);

		try (final CloseableReentrantLock lock = mainLock.open())
		{
			final RecordLocks recordLock = locks.get(recordKey);
			if (recordLock == null)
			{
				return false;
			}

			final LockOwner lockOwner;
			final LockOwner newLockOwner;
			if (lockCommand.getParentLock() != null)
			{
				lockOwner = lockCommand.getParentLock().getOwner();
				newLockOwner = lockCommand.getOwner();
			}
			else
			{
				lockOwner = lockCommand.getOwner();
				newLockOwner = lockOwner;
			}

			return recordLock.changeLock(lockOwner, newLockOwner, lockCommand.isAutoCleanup());
		}
	}

	@Override
	protected boolean unlockRecord(final IUnlockCommand unlockCommand, final TableRecordReference record)
	{
		final LockOwner ownerRequired = unlockCommand.getOwner();

		final boolean unlockForRecord = unlockForKey(ownerRequired, createKeyForRecord(record));

		return unlockForRecord;
	}

	private boolean unlockForKey(final LockOwner ownerRequired, final LockKey recordKey)
	{
		try (final CloseableReentrantLock lock = mainLock.open())
		{
			final RecordLocks recordLock = locks.get(recordKey);
			if (recordLock == null)
			{
				return false;
			}

			final boolean removed = recordLock.removeLocks(ownerRequired);

			if (!recordLock.hasLocks())
			{
				locks.remove(recordLock.getKey());
			}

			return removed;
		}
	}

	@Override
	protected int unlockByOwner(final IUnlockCommand unlockCommand)
	{
		final LockOwner lockOwner = unlockCommand.getOwner();
		assertValidLockOwner(lockOwner);

		final int countUnlocked = updateRecordLocks(recordLock -> recordLock.removeLocks(lockOwner) ? 1 : 0);
		return countUnlocked;
	}

	private final int updateRecordLocks(final ToIntFunction<RecordLocks> processor)
	{
		try (final CloseableReentrantLock lock = mainLock.open())
		{
			int countAffected = 0;

			for (final Iterator<RecordLocks> it = locks.values().iterator(); it.hasNext(); )
			{
				final RecordLocks recordLocks = it.next();
				final int count = processor.applyAsInt(recordLocks);
				countAffected += count;

				if (!recordLocks.hasLocks())
				{
					it.remove();
				}
			}

			return countAffected;
		}

	}

	@Override
	protected int unlockBySelection(final IUnlockCommand unlockCommand)
	{
		final int adTableId = unlockCommand.getSelectionToUnlock_AD_Table_ID();
		final PInstanceId adPInstanceId = unlockCommand.getSelectionToUnlock_AD_PInstance_ID();

		int countUnlocked = 0;
		for (final TableRecordReference record : retrieveSelection(adTableId, adPInstanceId))
		{
			final boolean unlocked = unlockRecord(unlockCommand, record);
			if (unlocked)
			{
				countUnlocked++;
			}
		}

		return countUnlocked;
	}

	private List<TableRecordReference> retrieveSelection(final int adTableId, final PInstanceId pinstanceId)
	{
		// NOTE: below comes a fucked up, not optimum implementation shit which shall do the work for testing

		final POJOInSelectionQueryFilter<Object> filter = POJOInSelectionQueryFilter.inSelection(pinstanceId);

		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);
		final List<TableRecordReference> records = new ArrayList<>();
		for (final Object model : POJOLookupMap.get().getRawRecords(tableName))
		{
			if (!filter.accept(model))
			{
				continue;
			}

			final TableRecordReference record = TableRecordReference.of(model);
			records.add(record);
		}

		return records;
	}

	public List<LockKey> getLocks()
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
		pojoQueryFinal.addFilter(model -> !isLocked(model, LockOwner.ANY));

		return pojoQueryFinal;
	}

	private Object getObjectForKey(final LockKey key)
	{
		final int tableId = key.getAdTableId();
		final int recordId = key.getRecordId();
		return POJOLookupMap.get().lookup(tableId, recordId);
	}

	public List<Object> getLockedObjects()
	{
		final List<Object> result = new ArrayList<>();
		for (final LockKey key : getLocks())
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
			for (final LockKey key : locks.keySet())
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

	@Override
	public int removeAutoCleanupLocks()
	{
		return updateRecordLocks(recordLock -> recordLock.removeAutoCleanupLocks());
	}

	//
	//
	//
	//
	//
	private static class RecordLocks
	{
		@Getter private final LockKey key;
		private final Map<LockOwner, LockInfo> locksByLockOwner = new HashMap<>();

		private RecordLocks(final LockKey key)
		{
			this.key = key;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.addValue(Joiner.on("\n").join(locksByLockOwner.values()))
					.toString();
		}

		public synchronized boolean isLockedBy(final LockOwner lockOwner)
		{
			if (lockOwner == null)
			{
				return true;
			}

			if (lockOwner.isAnyOwner())
			{
				return !locksByLockOwner.isEmpty();
			}

			final LockInfo lockInfo = locksByLockOwner.get(lockOwner);
			if (lockInfo == null)
			{
				return false;
			}

			return Objects.equals(lockOwner, lockInfo.getLockOwner());
		}

		/**
		 * @return true if lock was added; false if already exists
		 */
		public synchronized boolean addLock(final LockInfo lockInfo)
		{
			final LockOwner lockOwner = lockInfo.getLockOwner();
			final LockInfo existingLockInfo = locksByLockOwner.get(lockOwner);

			if (existingLockInfo == null && !isAllowMultipleOwners() && !locksByLockOwner.isEmpty())
			{
				logger.warn("Locked by a different owner: {} with allowMultipleOwner={}, see lockCandidateInfo={}", lockOwner, isAllowMultipleOwners(), lockInfo);
				return false;
			}

			if (existingLockInfo != null)
			{
				logger.warn("Already found a lock for " + lockOwner + ". Info: " + existingLockInfo);
				return false;
			}

			locksByLockOwner.put(lockOwner, lockInfo);
			return true;
		}

		// not sync
		private boolean isAllowMultipleOwners()
		{
			return locksByLockOwner.values().stream().anyMatch(LockInfo::isAllowMultipleOwners);
		}

		public synchronized boolean changeLock(final LockOwner owner, final LockOwner newOwner, final boolean autoCleanup)
		{
			final LockInfo lockInfo = locksByLockOwner.remove(owner);
			if (lockInfo == null)
			{
				return false;
			}

			lockInfo.setLockOwner(newOwner);
			lockInfo.setAutoCleanup(autoCleanup);
			locksByLockOwner.put(lockInfo.getLockOwner(), lockInfo);
			return true;
		}

		public synchronized boolean removeLocks(final LockOwner owner)
		{
			if (locksByLockOwner.isEmpty())
			{
				return false;
			}

			// Remove all locks
			if (owner.isAnyOwner())
			{
				if (locksByLockOwner.isEmpty())
				{
					return false;
				}

				locksByLockOwner.clear();
				return true;
			}
			// Remove for given owner
			else
			{
				final LockInfo lockRemoved = locksByLockOwner.remove(owner);
				return lockRemoved != null;
			}
		}

		@SuppressWarnings("BooleanMethodIsAlwaysInverted")
		public synchronized boolean hasLocks()
		{
			return !locksByLockOwner.isEmpty();
		}

		public synchronized boolean hasOwner(final LockOwner lockOwner)
		{
			return locksByLockOwner.containsKey(lockOwner);
		}

		public LockInfo getLockByOwner(final LockOwner lockOwner)
		{
			return locksByLockOwner.get(lockOwner);
		}

		public synchronized ImmutableList<LockInfo> getLocks() {return ImmutableList.copyOf(locksByLockOwner.values());}

		public int removeAutoCleanupLocks()
		{
			int countRemoved = 0;
			for (Iterator<LockInfo> it = locksByLockOwner.values().iterator(); it.hasNext(); )
			{
				LockInfo lockInfo = it.next();
				if (lockInfo.isAutoCleanup())
				{
					it.remove();
					countRemoved++;
				}
			}

			return countRemoved;
		}

	}

	private static class LockInfo
	{
		@SuppressWarnings("unused")
		private final LockKey key;
		private LockOwner _lockOwner;
		private boolean autoCleanup;
		private final boolean allowMultipleOwners;
		@Getter
		private final Instant acquiredAt;

		//
		// Debugging info:
		@SuppressWarnings("unused")
		private final Exception aquiredStackTrace;
		@SuppressWarnings("unused")
		private final String aquiredThreadName;

		public LockInfo(@NonNull final LockKey key, @NonNull final ILockCommand lockCommand)
		{
			this.key = key;

			aquiredStackTrace = new Exception("Aquired stack trace");
			aquiredThreadName = Thread.currentThread().getName();
			allowMultipleOwners = AbstractLockDatabase.isAllowMultipleOwners(lockCommand.getAllowAdditionalLocks());
			acquiredAt = Instant.now();

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

		public boolean isAllowMultipleOwners()
		{
			return allowMultipleOwners;
		}
	}

	@Override
	public final <T> IQueryFilter<T> getLockedByFilter(final Class<T> modelClass, final LockOwner lockOwner)
	{
		return model -> isLocked(model, lockOwner);
	}

	@Override
	public <T> IQueryFilter<T> getNotLockedFilter(final Class<T> modelClass)
	{
		return model -> !isLocked(model, LockOwner.ANY);
	}

	@Override
	public String getNotLockedWhereClause(final String tableName, final String joinColumnName)
	{
		throw new UnsupportedOperationException("not supported (tableName=" + tableName + ", joinColumnName=" + joinColumnName + ")");
	}

	@Override
	protected String getLockedWhereClauseAllowNullLock(final @NonNull Class<?> modelClass, final @NonNull String joinColumnNameFQ, final LockOwner lockOwner)
	{
		logger.warn("getLockedWhereClauseAllowNullLock() not supported; returning '1=1'");
		return "1=1";
	}

	@Override
	public ILock retrieveLockForOwner(final LockOwner lockOwner)
	{
		Check.assumeNotNull(lockOwner, "Lock owner shall not be null");
		Check.assumeNotNull(lockOwner.isRealOwner(), "Lock owner shall be real owner but it was {}", lockOwner);

		try (CloseableReentrantLock l = mainLock.open())
		{
			final List<RecordLocks> recordLocksList = locks.values().stream()
					.filter(recordLocks -> recordLocks.hasOwner(lockOwner))
					.collect(ImmutableList.toImmutableList());

			if (recordLocksList.isEmpty())
			{
				throw new LockFailedException("No lock found for " + lockOwner);
			}
			else if (recordLocksList.size() > 1)
			{
				throw new LockFailedException("More then one lock found for " + lockOwner + ": " + recordLocksList);
			}

			final RecordLocks recordLocks = recordLocksList.get(0);

			final LockInfo lockInfo = recordLocks.getLockByOwner(lockOwner);
			if (lockInfo == null)
			{
				throw new LockFailedException("No lock found for " + lockOwner + " in " + recordLocks);
			}

			return newLock(lockOwner, lockInfo.isAutoCleanup(), 1, 0);
		}
	}

	@Override
	@Nullable
	public ExistingLockInfo getLockInfo(@NonNull final TableRecordReference tableRecordReference, @Nullable final LockOwner lockOwner)
	{
		try (final CloseableReentrantLock ignored = mainLock.open())
		{
			final LockKey lockKey = LockKey.ofTableRecordReference(tableRecordReference);

			final RecordLocks recordLocks = locks.get(lockKey);
			if (recordLocks == null)
			{
				return null;
			}

			final LockOwner lockOwnerToUse = lockOwner == null ? LockOwner.NONE : lockOwner;
			final LockInfo lockInfo = recordLocks.getLockByOwner(lockOwnerToUse);
			if (lockInfo == null)
			{
				return null;
			}

			return toExistingLockInfo(lockInfo, tableRecordReference);
		}
	}

	private static ExistingLockInfo toExistingLockInfo(@NonNull final LockInfo lockInfo, @NonNull final TableRecordReference tableRecordReference)
	{
		return ExistingLockInfo.builder()
				.ownerName(lockInfo.getLockOwner().getOwnerName())
				.lockedRecord(tableRecordReference)
				.allowMultipleOwners(lockInfo.isAllowMultipleOwners())
				.autoCleanup(lockInfo.isAutoCleanup())
				.created(lockInfo.getAcquiredAt())
				.build();
	}

	@Override
	public SetMultimap<TableRecordReference, ExistingLockInfo> getLockInfosByRecordIds(@NonNull final TableRecordReferenceSet recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		final ImmutableSetMultimap.Builder<TableRecordReference, ExistingLockInfo> result = ImmutableSetMultimap.builder();
		try (final CloseableReentrantLock ignored = mainLock.open())
		{
			for (final TableRecordReference recordRef : recordRefs)
			{
				final LockKey lockKey = LockKey.ofTableRecordReference(recordRef);

				final RecordLocks recordLocks = locks.get(lockKey);
				if (recordLocks == null)
				{
					continue;
				}

				for (final LockInfo lockInfo : recordLocks.getLocks())
				{
					result.put(recordRef, toExistingLockInfo(lockInfo, recordRef));
				}
			}

			return result.build();
		}

	}

	@Value(staticConstructor = "of")
	public static class LockKey
	{
		int adTableId;
		int recordId;

		public static LockKey ofTableRecordReference(@NonNull final TableRecordReference recordRef) {return of(recordRef.getAD_Table_ID(), recordRef.getRecord_ID());}
	}
}

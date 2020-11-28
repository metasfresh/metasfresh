package de.metas.lock.spi.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.IUnlockCommand;
import de.metas.lock.api.LockOwner;
import de.metas.lock.api.impl.AbstractLockDatabase;
import de.metas.lock.exceptions.LockChangeFailedException;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.lock.exceptions.UnlockFailedException;
import de.metas.lock.model.I_T_Lock;
import de.metas.lock.spi.ILockDatabase;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * {@link ILockDatabase} implementation which stores the locks in {@link I_T_Lock} table.
 *
 * This is the default implementation.
 *
 * @author tsa
 *
 */
public class SqlLockDatabase extends AbstractLockDatabase
{
	private static final String SQL_DeleteLock = "DELETE FROM " + I_T_Lock.Table_Name + " WHERE 1=1 ";

	/**
	 * @param lockOwner
	 * @param sql
	 * @param sqlParams sql parameters list or null
	 */
	private final void appendLockOwnerWhereClause(final LockOwner lockOwner, final StringBuilder sql, final List<Object> sqlParams)
	{
		Check.assumeNotNull(lockOwner, "lockOwner not null");
		if (lockOwner.isAnyOwner())
		{
			return;
		}

		assertValidLockOwner(lockOwner);
		final String lockOwnerName = lockOwner.getOwnerName();
		if (lockOwnerName == null)
		{
			sql.append(" AND ").append(I_T_Lock.COLUMNNAME_Owner).append(" IS NULL");
		}
		else
		{
			sql.append(" AND ").append(I_T_Lock.COLUMNNAME_Owner).append("=").append(toSqlParam(lockOwnerName, sqlParams));
		}
	}

	private static final String toSqlParam(final Object param, final List<Object> sqlParams)
	{
		if (sqlParams != null)
		{
			sqlParams.add(param);
			return "?";
		}
		else
		{
			return DB.TO_SQL(param);
		}
	}

	private final void appendTableRecordWhereClause(final TableRecordReference record, final StringBuilder sql, final List<Object> sqlParams)
	{
		final int adTableId = record.getAD_Table_ID();
		final int recordId = record.getRecord_ID();
		// TODO: shall we validate adTableId/recordId

		// For AD_Table_ID
		sql.append(" AND ").append(I_T_Lock.COLUMNNAME_AD_Table_ID).append("=").append(toSqlParam(adTableId, sqlParams));

		// For Record_ID
		sql.append(" AND ").append(I_T_Lock.COLUMNNAME_Record_ID).append("=").append(toSqlParam(recordId, sqlParams));
	}

	private final void appendTableSelectionWhereClause(final int adTableId, @NonNull final PInstanceId pinstanceId, final StringBuilder sql, final List<Object> sqlParams)
	{
		Check.assume(adTableId > 0, "adTableId > 0");
		sql.append(" AND ").append(I_T_Lock.COLUMNNAME_AD_Table_ID).append("=").append(toSqlParam(adTableId, sqlParams));
		//sqlParams.add(adTableId);

		// Record_ID shall be in our selection
		sql.append(" AND ").append(I_T_Lock.COLUMNNAME_Record_ID)
				.append(" IN (SELECT T_Selection_ID FROM T_Selection WHERE AD_PInstance_ID=").append(toSqlParam(pinstanceId, sqlParams)).append(")");
		//sqlParams.add(adPInstanceId);
	}

	@Override
	public boolean isLocked(final int adTableId, final int recordId, final LockOwner lockOwner)
	{
		Check.assume(adTableId > 0, "asTableId > 0");

		if (recordId < 0)
		{
			return false;
		}

		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder(" SELECT count(1)"
				+ " FROM " + I_T_Lock.Table_Name
				+ " WHERE "
				+ I_T_Lock.COLUMNNAME_AD_Table_ID + "=" + toSqlParam(adTableId, sqlParams)
				+ " AND " + I_T_Lock.COLUMNNAME_Record_ID + "=" + toSqlParam(recordId, sqlParams));

		if (lockOwner != null)
		{
			appendLockOwnerWhereClause(lockOwner, sql, sqlParams);
		}

		final int countLocked = DB.getSQLValueEx(ITrx.TRXNAME_None, sql.toString(), sqlParams);
		return countLocked > 0;
	}

	/**
	 * Implements the method for ISqlQueryFilters. Throws an error for other IQueryFilters
	 *
	 * @task http://dewiki908/mediawiki/index.php/08756_EDI_Lieferdispo_Lieferschein_und_Complete_%28101564484292%29
	 */
	@Override
	protected int lockByFilters(final ILockCommand lockCommand)
	{
		final IQueryFilter<?> selectionToLockFilters = lockCommand.getSelectionToLock_Filters();

		final LockOwner lockOwner = lockCommand.getOwner();
		assertValidLockOwner(lockOwner);

		if (selectionToLockFilters instanceof ISqlQueryFilter)
		{
			final int adTableId = lockCommand.getSelectionToLock_AD_Table_ID();
			final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

			final List<Object> sqlParams = new ArrayList<>();
			final ISqlQueryFilter sqlFilter = ISqlQueryFilter.cast(selectionToLockFilters);
			final String tableName = adTableDAO.retrieveTableName(adTableId);
			final String sql = "INSERT INTO " + I_T_Lock.Table_Name + " ("
					+ I_T_Lock.COLUMNNAME_AD_Table_ID
					+ ", " + I_T_Lock.COLUMNNAME_Record_ID
					+ ", " + I_T_Lock.COLUMNNAME_Owner
					+ ", " + I_T_Lock.COLUMNNAME_IsAutoCleanup
					+ ", " + I_T_Lock.COLUMNNAME_IsAllowMultipleOwners
					+ ")"
					//
					+ " SELECT "
					+ toSqlParam(adTableId, sqlParams) // AD_Table_ID
					+ ", " + tableName + "_ID" // Record_ID
					+ ", " + toSqlParam(lockOwner.getOwnerName(), sqlParams) // Owner
					+ ", " + toSqlParam(lockCommand.isAutoCleanup(), sqlParams) // IsAutoCleanup
					+ ", " + toSqlParam(isAllowMultipleOwners(lockCommand.getAllowAdditionalLocks()), sqlParams) // IsAllowMultipleOwners
					//
					+ " FROM " + tableName
					+ " WHERE (" + sqlFilter.getSql() + ")";
			sqlParams.addAll(sqlFilter.getSqlParams(null));
			return performLockSQLInsert(lockCommand, sqlParams, sql);
		}

		Check.errorIf(true, "Currently we just support ISqlQueryFilters. This filter is not supported: {}", selectionToLockFilters);
		return -1; // not reached
	}

	@Override
	protected int lockBySelection(@NonNull final ILockCommand lockCommand)
	{
		final int adTableId = lockCommand.getSelectionToLock_AD_Table_ID();
		Check.assume(adTableId > 0, "adTableId > 0; lockCommand={}", lockCommand);

		final PInstanceId pinstanceId = lockCommand.getSelectionToLock_AD_PInstance_ID();
		Check.assumeNotNull(pinstanceId, "pinstanceId not null; lockCommand={}", lockCommand);

		final LockOwner lockOwner = lockCommand.getOwner();
		assertValidLockOwner(lockOwner);

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = "INSERT INTO " + I_T_Lock.Table_Name + " ("
				+ I_T_Lock.COLUMNNAME_AD_Table_ID
				+ ", " + I_T_Lock.COLUMNNAME_Record_ID
				+ ", " + I_T_Lock.COLUMNNAME_Owner
				+ ", " + I_T_Lock.COLUMNNAME_IsAutoCleanup
				+ ", " + I_T_Lock.COLUMNNAME_IsAllowMultipleOwners
				+ ")"
				//
				+ " SELECT "
				+ toSqlParam(adTableId, sqlParams) // AD_Table_ID
				+ ", T_Selection_ID" // Record_ID
				+ ", " + toSqlParam(lockOwner.getOwnerName(), sqlParams) // Owner
				+ ", " + toSqlParam(lockCommand.isAutoCleanup(), sqlParams) // IsAutoCleanup
				+ ", " + toSqlParam(isAllowMultipleOwners(lockCommand.getAllowAdditionalLocks()), sqlParams) // IsAllowMultipleOwners
				//
				+ " FROM T_Selection"
				+ " WHERE AD_PInstance_ID=" + toSqlParam(pinstanceId, sqlParams);

		return performLockSQLInsert(lockCommand, sqlParams, sql);
	}

	protected int performLockSQLInsert(final ILockCommand lockCommand, final List<Object> sqlParams, final String sql)
	{
		try
		{
			final int countLocked = DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_None);
			if (countLocked <= 0 && lockCommand.isFailIfNothingLocked())
			{
				throw new LockFailedException("Nothing locked for selection");
			}

			return countLocked;
		}
		catch (final DBUniqueConstraintException e)
		{
			// TODO: implement for lockCommand.isFailIfAlreadyLocked() == false to allow partial locking

			throw new LockFailedException("Some of the records were already locked", e)
					.setLockCommand(lockCommand)
					.setSql(sql, sqlParams.toArray());

		}
		catch (final Exception e)
		{
			throw LockFailedException.wrapIfNeeded(e)
					.setLockCommand(lockCommand)
					.setSql(sql, sqlParams.toArray());
		}
	}

	@Override
	protected boolean lockRecord(final ILockCommand lockCommand, final TableRecordReference record)
	{
		Check.assumeNotNull(record, "record not null");

		final int adTableId = record.getAD_Table_ID();
		Check.assume(adTableId > 0, "adTableId > 0");

		final int recordId = record.getRecord_ID();
		if (recordId < 0)
		{
			return false;
		}

		final LockOwner lockOwner = lockCommand.getOwner();
		assertValidLockOwner(lockOwner);

		final boolean autoCleanup = lockCommand.isAutoCleanup();

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = "INSERT INTO " + I_T_Lock.Table_Name + " ("
				+ I_T_Lock.COLUMNNAME_AD_Table_ID
				+ ", " + I_T_Lock.COLUMNNAME_Record_ID
				+ ", " + I_T_Lock.COLUMNNAME_Owner
				+ ", " + I_T_Lock.COLUMNNAME_IsAutoCleanup
				+ ", " + I_T_Lock.COLUMNNAME_IsAllowMultipleOwners
				+ ") VALUES ("
				+ toSqlParam(adTableId, sqlParams)
				+ ", " + toSqlParam(recordId, sqlParams)
				+ ", " + toSqlParam(lockOwner.getOwnerName(), sqlParams)
				+ ", " + toSqlParam(autoCleanup, sqlParams)
				+ ", " + toSqlParam(isAllowMultipleOwners(lockCommand.getAllowAdditionalLocks()), sqlParams) // IsAllowMultipleOwners
				+ ")";

		try
		{
			DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_None);
			return true;
		}
		catch (final DBUniqueConstraintException e)
		{
			// we are in a concurrent situation where another DB client acquired the lock for a record since our select
			// => fail if we were asked to fail, else return false

			if (lockCommand.isFailIfAlreadyLocked())
			{
				throw new LockFailedException("Record was already locked: " + record, e)
						.setLockCommand(lockCommand)
						.setSql(sql, sqlParams.toArray())
						.setRecordToLock(record);
			}
			return false;
		}
		catch (final Exception e)
		{
			throw new LockFailedException("Failed locking AD_Table_ID=" + adTableId + ", Record_ID=" + recordId, e)
					.setLockCommand(lockCommand)
					.setSql(sql, sqlParams.toArray())
					.setRecordToLock(record);
		}
	}

	@Override
	protected boolean changeLockRecord(final ILockCommand lockCommand, final TableRecordReference record)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder("UPDATE " + I_T_Lock.Table_Name)
				.append(" SET ");

		//
		// Set Owner
		final LockOwner ownerNew = lockCommand.getOwner();
		assertValidLockOwner(ownerNew);
		sql.append(I_T_Lock.COLUMNNAME_Owner).append("=").append(toSqlParam(ownerNew.getOwnerName(), sqlParams));

		//
		// Set AutoCleanup
		sql.append(", ").append(I_T_Lock.COLUMNNAME_IsAutoCleanup).append("=").append(toSqlParam(lockCommand.isAutoCleanup(), sqlParams));

		//
		// Set AllowMultipleOwners
		sql.append(", ").append(I_T_Lock.COLUMNNAME_IsAllowMultipleOwners).append("=").append(toSqlParam(isAllowMultipleOwners(lockCommand.getAllowAdditionalLocks()), sqlParams));

		//
		// Where Clause...
		sql.append(" WHERE 1=1 ");

		// For AD_Table_ID
		appendTableRecordWhereClause(record, sql, sqlParams);

		// For Owner
		final ILock parentLock = lockCommand.getParentLock();
		final LockOwner ownerCurrent = parentLock.getOwner();
		appendLockOwnerWhereClause(ownerCurrent, sql, sqlParams);

		try
		{
			final int countChanged = DB.executeUpdateEx(sql.toString(), sqlParams.toArray(), ITrx.TRXNAME_None);
			return countChanged > 0;
		}
		catch (final Exception e)
		{
			throw new LockChangeFailedException("Failed changing owner for record", e)
					.setLockCommand(lockCommand)
					.setLock(parentLock)
					.setRecord(record)
					.setParameter("Owner (old)", ownerCurrent)
					.setParameter("Owner (new)", ownerNew)
					.setSql(sql.toString(), sqlParams.toArray());
		}
	}

	@Override
	protected int unlockBySelection(final IUnlockCommand unlockCommand)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder(SQL_DeleteLock);

		// For AD_Table_ID / Record_ID in Selection
		appendTableSelectionWhereClause(
				unlockCommand.getSelectionToUnlock_AD_Table_ID(),
				unlockCommand.getSelectionToUnlock_AD_PInstance_ID(),
				sql, sqlParams);

		// For Owner
		appendLockOwnerWhereClause(unlockCommand.getOwner(), sql, sqlParams);

		int countUnlocked = -1;
		try
		{
			countUnlocked = DB.executeUpdateEx(sql.toString(), sqlParams.toArray(), ITrx.TRXNAME_None);
		}
		catch (final Exception e)
		{
			throw new UnlockFailedException("Failed unlocking selection", e)
					.setUnlockCommand(unlockCommand)
					.setSql(sql.toString(), sqlParams.toArray());
		}

		return countUnlocked;
	}

	@Override
	protected boolean unlockRecord(final IUnlockCommand unlockCommand, final TableRecordReference record)
	{
		final StringBuilder sql = new StringBuilder(SQL_DeleteLock);
		final List<Object> sqlParams = new ArrayList<>();

		// For AD_Table_ID/RecordId
		appendTableRecordWhereClause(record, sql, sqlParams);

		// For Owner
		appendLockOwnerWhereClause(unlockCommand.getOwner(), sql, sqlParams);

		try
		{
			final int countUnlocked = DB.executeUpdateEx(sql.toString(), sqlParams.toArray(), ITrx.TRXNAME_None);
			return countUnlocked > 0;
		}
		catch (final Exception e)
		{
			throw new UnlockFailedException("Failed unlocking record: " + record, e)
					.setUnlockCommand(unlockCommand)
					.setSql(sql.toString(), sqlParams.toArray());
		}
	}

	@Override
	protected int unlockByOwner(final IUnlockCommand unlockCommand)
	{
		final StringBuilder sql = new StringBuilder(SQL_DeleteLock);
		final List<Object> sqlParams = new ArrayList<>();

		// For Owner
		final LockOwner lockOwner = unlockCommand.getOwner();
		assertValidLockOwner(lockOwner);
		appendLockOwnerWhereClause(lockOwner, sql, sqlParams);

		try
		{
			final int countUnlocked = DB.executeUpdateEx(sql.toString(), sqlParams.toArray(), ITrx.TRXNAME_None);
			return countUnlocked;
		}
		catch (final Exception e)
		{
			throw new UnlockFailedException("Failed unlocking for owner: " + lockOwner, e)
					.setUnlockCommand(unlockCommand)
					.setSql(sql.toString(), sqlParams.toArray());
		}
	}

	@Override
	protected <T> IQuery<T> retrieveNotLockedQuery(final IQuery<T> query)
	{
		final TypedSqlQuery<T> sqlQuery = TypedSqlQuery.cast(query);

		final String tableName = sqlQuery.getTableName();
		final String keyColumnName = sqlQuery.getKeyColumnName();
		final String keyColumnNameFQ = tableName + "." + keyColumnName;

		final TypedSqlQuery<T> finalQuery = sqlQuery.addWhereClause(true, getNotLockedWhereClause(tableName, keyColumnNameFQ));

		return finalQuery;
	}

	@Override
	public final String getNotLockedWhereClause(final String tableName, final String joinColumnNameFQ)
	{
		final List<Object> sqlParams = null; // no params
		final StringBuilder whereClause = new StringBuilder();

		// For AD_Table_ID/Record_ID
		{
			Check.assumeNotEmpty(tableName, "tableName not empty");
			final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
			Check.assume(adTableId > 0, "Table {} exists", tableName);

			Check.assumeNotEmpty(joinColumnNameFQ, "joinColumnNameFQ not empty");

			whereClause.append(" AND zz." + I_T_Lock.COLUMNNAME_AD_Table_ID + "=" + toSqlParam(adTableId, sqlParams));
			whereClause.append(" AND zz." + I_T_Lock.COLUMNNAME_Record_ID + "=" + joinColumnNameFQ);
		}

		return whereClause
				.insert(0, "NOT EXISTS (SELECT 1 FROM " + I_T_Lock.Table_Name + " zz WHERE 1=1 ").append(")")
				.toString();
	}

	@Override
	public <T> IQueryFilter<T> getNotLockedFilter(final Class<T> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		final String joinColumnNameFQ = tableName + "." + keyColumnName;
		final String sqlWhereClause = getNotLockedWhereClause(tableName, joinColumnNameFQ);
		final TypedSqlQueryFilter<T> filter = TypedSqlQueryFilter.of(sqlWhereClause);
		return filter;
	}

	@Override
	public final <T> IQueryFilter<T> getLockedByFilter(final Class<T> modelClass, final LockOwner lockOwner)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		final String joinColumnNameFQ = tableName + "." + keyColumnName;
		final String sqlWhereClause = getLockedWhereClause(modelClass, joinColumnNameFQ, lockOwner);
		final TypedSqlQueryFilter<T> filter = TypedSqlQueryFilter.of(sqlWhereClause);
		return filter;
	}

	@Override
	protected String getLockedWhereClauseAllowNullLock(final Class<?> modelClass, final String joinColumnNameFQ, final LockOwner lockOwner)
	{
		final List<Object> sqlParams = null; // no params
		final StringBuilder whereClause = new StringBuilder();

		// For AD_Table_ID/Record_ID
		{
			Check.assumeNotNull(modelClass, "modelClass not null");
			final int adTableId = InterfaceWrapperHelper.getTableId(modelClass);
			Check.assume(adTableId > 0, "Table {} exists", modelClass);

			Check.assumeNotEmpty(joinColumnNameFQ, "joinColumnNameFQ not empty");

			whereClause.append(" AND zz." + I_T_Lock.COLUMNNAME_AD_Table_ID + "=" + toSqlParam(adTableId, sqlParams));
			whereClause.append(" AND zz." + I_T_Lock.COLUMNNAME_Record_ID + "=" + joinColumnNameFQ);
		}

		// For given lock owner
		if (lockOwner != null)
		{
			appendLockOwnerWhereClause(lockOwner, whereClause, sqlParams);
		}

		return whereClause
				.insert(0, "EXISTS (SELECT 1 FROM " + I_T_Lock.Table_Name + " zz WHERE 1=1 ").append(")")
				.toString();
	}

	@Override
	public final ILock retrieveLockForOwner(@NonNull final LockOwner lockOwner)
	{
		Check.assumeNotNull(lockOwner.isRealOwner(), "Lock owner shall be real owner but it was {}", lockOwner);

		final String sql = "SELECT "
				+ " " + I_T_Lock.COLUMNNAME_IsAutoCleanup
				+ ", COUNT(1) as CountLocked"
				+ " FROM " + I_T_Lock.Table_Name
				+ " WHERE "
				+ " " + I_T_Lock.COLUMNNAME_Owner + "=?"
				+ " GROUP BY "
				+ I_T_Lock.COLUMNNAME_IsAutoCleanup;

		final List<Object> sqlParams = Arrays.<Object> asList(lockOwner.getOwnerName());

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final boolean autoCleanup = DisplayType.toBoolean(rs.getString(I_T_Lock.COLUMNNAME_IsAutoCleanup), false);
				final int countLocked = rs.getInt("CountLocked");
				final ILock lock = newLock(lockOwner, autoCleanup, countLocked);

				Check.assume(!rs.next(), "More than one lock found for owner");

				return lock;
			}
		}
		catch (final SQLException e)
		{
			throw new LockFailedException("Failed loading lock for owner " + lockOwner, e)
					.setSql(sql, sqlParams.toArray());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		throw new LockFailedException("No lock found for " + lockOwner);
	}

	@Override
	public int removeAutoCleanupLocks()
	{
		final String sql = "DELETE FROM " + I_T_Lock.Table_Name + " WHERE " + I_T_Lock.COLUMNNAME_IsAutoCleanup + "=?";
		final Object[] sqlParams = new Object[] { true };
		final int countLocksReleased = DB.executeUpdateEx(sql, sqlParams, ITrx.TRXNAME_None);
		if (countLocksReleased > 0)
		{
			logger.info("Deleted {} lock records from {} which were flagged with IsAutoCleanup=true", countLocksReleased, I_T_Lock.Table_Name);
		}
		return countLocksReleased;
	}
}

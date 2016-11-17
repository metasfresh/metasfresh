package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.adempiere.service.ISweepTableBL;
import de.metas.adempiere.util.CacheIgnore;

public class SweepTableBL implements ISweepTableBL
{

	private static final Logger logger = LogManager.getLogger(SweepTableBL.class);

	private static final String DELETED = "DELETED";

	private static final String REFERRING_RECORDS_ADDED = "ADDED";

	public static final String COLUMNNAME_TableRecordId = "AD_Table_ID#Record_ID";

	@Override
	public boolean sweepTable(final Properties ctx, final String tableName,
			final int targetClientId, final JavaProcess process,
			final String trxName)
	{
		return sweepTable(ctx, tableName, "1=1", targetClientId, process,
				trxName);
	}

	@Override
	public boolean sweepTable(final Properties ctx, final String tableName,
			final String whereClause, final int targetClientId,
			final JavaProcess process, final String trxName)
	{
		final String whereClauseToUse = "( " + whereClause + " )"
				+ " AND AD_Client_ID=" + Env.getAD_Client_ID(ctx);

		final List<Integer> initalIds = new ArrayList<Integer>();
		for (final int initialId : PO.getAllIDs(tableName, whereClauseToUse,
				trxName))
		{
			initalIds.add(initialId);
		}
		return sweepTable(ctx, tableName, initalIds, targetClientId, process,
				trxName);
	}

	@Override
	public boolean sweepTable(final Properties ctx, final String tableName,
			final Collection<Integer> initalIds, final int targetClientId,
			final JavaProcess process, final String trxName)
	{
		final RuntimeContext sweepCtx = new RuntimeContext();
		sweepCtx.ctx = ctx;
		sweepCtx.trxName = trxName;
		sweepCtx.targetClientId = targetClientId;
		sweepCtx.process = process;

		final String path = tableName;
		final boolean result = sweepTableRecurse(sweepCtx, tableName, initalIds, path);
		//
		// Recalculate storage if the tables C_OrderLine and M_Transaction are touched
		if (isTableAffected(sweepCtx, I_C_OrderLine.Table_Name)
				|| isTableAffected(sweepCtx, I_M_Transaction.Table_Name))
		{
			m_storage_recalculate(trxName);
			logMsg(sweepCtx, "M_Storage recalculated");
		}
		return result;
	}

	private boolean isTableAffected(RuntimeContext sweepCtx, String tableName)
	{
		for (String p : sweepCtx.pathsVisited)
		{
			if (p.contains(tableName))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Method attempts to remove the given record ids from the given table. Before the removal the method finds the
	 * tables and record ids that refer to the given table and record ids. It then calls itself with those referring ids
	 * to make sure that there will be not problems with dangling references.
	 * 
	 * @param tableName
	 *            the table to remove records from
	 * @param recordIds
	 *            the record ids that need to be removed from the given table
	 * @param path
	 *            textual information
	 * @return
	 */
	private boolean sweepTableRecurse(final RuntimeContext sweepCtx,
			final String tableName, final Collection<Integer> recordIds,
			final String path)
	{
		if (recordIds.isEmpty())
		{
			return true;
		}

		if (sweepCtx.pathsVisited.add(path))
		{
			logMsg(sweepCtx, "Looking at " + path);
		}
		logger.info("Current path: " + path);

		// ID from 'recordIds' -> ( Refering_Table -> Refering_IDs )
		final Map<Integer, Map<String, List<Integer>>> referringRecords;

		try
		{
			referringRecords = retrieveReferringRecords(sweepCtx, tableName,
					recordIds);
		}
		catch (DBException e)
		{
			logMsg(sweepCtx,
					"DBException while trying to retrieve referring tables and ids for tableName="
							+ tableName + " and " + recordIds.size()
							+ " RecordIDs. \nMsg=[" + e.getMessage() + "]");
			return false;
		}

		int deleteSuccesses = 0;

		for (final int recordId : recordIds)
		{
			boolean canDeleteCurrentId = true;

			final Map<String, List<Integer>> referencesToDelete = referringRecords
					.get(recordId);

			if (referencesToDelete != null)
			{
				//
				// For each recordId, try to remove all records referring to it.
				// Only if the removal is successful, the recordId can be
				// removed as well
				for (final String referringTable : referencesToDelete.keySet())
				{
					final List<Integer> referringRecordIds = referencesToDelete
							.get(referringTable);
					final boolean result = sweepTableRecurse(sweepCtx,
							referringTable, referringRecordIds, path + "->"
									+ referringTable);
					if (!result)
					{
						canDeleteCurrentId = false;
					}
				}
			}
			final String strRecord = mkRecordStr(tableName, recordId);

			if (isAlreadyDeleted(sweepCtx, tableName, recordId))
			{
				// record has already been deleted earlier, but shows up again
				// (which can be ok?)
				deleteSuccesses += 1;
			}
			else if (canDeleteCurrentId)
			{
				// here the non-Ex-delete comes in handy :)
				if (removePO(sweepCtx, tableName, recordId))

				{
					deleteSuccesses += 1;
					sweepCtx.records.put(strRecord, DELETED);

					logger.trace("Delete success for [Table=" + tableName
							+ ", RecordId=" + recordId + "]");
				}
				else
				{
					logMsg(sweepCtx, "WARNING: Delete failure for " + strRecord
							+ "; " + MiscUtils.loggerMsgs());
				}
			}
			else
			{
				logMsg(sweepCtx, "WARNING: Skipping deletion for [Table="
						+ tableName + ", RecordId=" + recordId
						+ "] because not all depending pos could be removed");
			}
		}

		final boolean success = deleteSuccesses == recordIds.size();

		if (success)
		{
			if (!recordIds.isEmpty())
			{
				logger.info(path + ": All " + recordIds.size()
						+ " record(s) from table " + tableName
						+ " successfully deleted :-)");
			}
		}
		else
		{
			logMsg(sweepCtx, path + ": Had to leave table " + tableName
					+ " with " + (recordIds.size() - deleteSuccesses)
					+ " records undeleted :-/");
		}

		return success;
	}

	private boolean removePO(final RuntimeContext sweepCtx, final String table,
			final int id)
	{
		final String keyColumnName = getKeyColName(sweepCtx.ctx, table);
		final int adClientId = Env.getAD_Client_ID(sweepCtx.ctx);
		final String whereClause = keyColumnName + "=" + id
				+ " AND AD_Client_ID=" + adClientId;

		final String sql;
		final boolean isDelete;
		if (sweepCtx.targetClientId > 0)
		{
			isDelete = false;
			sql = "UPDATE " + table + " SET AD_Client_ID="
					+ sweepCtx.targetClientId + " WHERE " + whereClause;
		}
		else
		{
			isDelete = true;
			sql = "DELETE FROM " + table + " WHERE " + whereClause;
		}

		final int no = DB.executeUpdate(sql, sweepCtx.trxName);
		if (no < 0)
		{
			// sql error, returning false, error is already logged in console
			return false;
		}
		else if (no == 0)
		{
			if (isDelete)
			{
				// maybe the record was previously deleted, consider it as
				// success
				return true;
			}
			else
			{
				logger.warn("No records found for update (" + sql + ")");
				return false;
			}
		}
		else if (no == 1)
		{
			// record was actually deleted NOW
			return true;
		}
		else
		// no > 1
		{
			// shall not happen
			logger.warn(
					"Query deleted/updated more then one record (" + sql + ")");
			return false;
		}

	}

	/**
	 * 
	 * @param sweepCtx
	 * @param tableName
	 * @param recordIds
	 * @return RecordId (from <code>recordIds</code>) => Referring TableName => Referring Record_IDs
	 */
	private Map<Integer, Map<String, List<Integer>>> retrieveReferringRecords(
			final RuntimeContext sweepCtx, final String tableName,
			final Collection<Integer> recordIds)
	{
		final Map<String, List<String>> referringTable2Col = retrieveReferringTablesAndCols(
				sweepCtx, tableName);

		// Map Record_ID => TableName => Record_IDs
		final Map<Integer, Map<String, List<Integer>>> result = new HashMap<Integer, Map<String, List<Integer>>>();

		//
		// TableName is not referred to by any other table
		if (referringTable2Col.isEmpty())
		{
			for (final int recordId : recordIds)
			{
				sweepCtx.records.put(mkRecordStr(tableName, recordId),
						REFERRING_RECORDS_ADDED);
			}
			return result;
		}

		int oldRetrievedCounter = 0;
		int retrievedCounter = 0;
		int idCounter = 0;

		// for the tables that can contain records referring to 'tableName',
		// find the actual referring recordIds
		for (final int recordId : recordIds)
		{
			Map<String, List<Integer>> referingTable2Ids = result.get(recordId);
			if (referingTable2Ids == null)
			{
				referingTable2Ids = new HashMap<String, List<Integer>>();
				result.put(recordId, referingTable2Ids);
			}

			if (!isReferringRecordIdsAdded(sweepCtx, tableName, recordId))
			{
				// the records that refer to 'recordId' have not yet been
				// retrieved
				for (final String referingTable : referringTable2Col.keySet())
				{
					List<Integer> referingTableIds = referingTable2Ids
							.get(referingTable);
					if (referingTableIds == null)
					{
						referingTableIds = new ArrayList<Integer>();
						referingTable2Ids.put(referingTable, referingTableIds);
					}

					for (final String referingTableCol : referringTable2Col
							.get(referingTable))
					{
						final List<Integer> retrievedIds;
						if (COLUMNNAME_TableRecordId == referingTableCol)
						{
							retrievedIds = retrieveDynRecordIds(sweepCtx,
									referingTable, tableName, recordId);
						}
						else
						{
							retrievedIds = retrieveRecordIds(sweepCtx,
									referingTable, referingTableCol, recordId);
						}

						retrievedCounter += retrievedIds.size();

						logger.trace("Retrived "
								+ retrievedIds.size()
								+ " depending ids for "
								+ getRefInfoString(referingTable,
										referingTableCol, recordId));
						referingTableIds.addAll(retrievedIds);
					}

					if (referingTableIds.isEmpty())
					{
						referingTable2Ids.remove(referingTable);
					}
				}

				// make sure that the records referring to 'recordId' won't be
				// retrieved again
				sweepCtx.records.put(mkRecordStr(tableName, recordId),
						REFERRING_RECORDS_ADDED);
			}

			idCounter += 1;
			if (retrievedCounter >= oldRetrievedCounter + 1000)
			{
				logger.info("Retrieved " + retrievedCounter
						+ " referring records for " + idCounter + "/"
						+ recordIds.size() + " records of table " + tableName);
				oldRetrievedCounter = retrievedCounter;
			}
		}

		if (retrievedCounter > 0)
		{
			final String msg = "Retrieved " + retrievedCounter
					+ " depending ids for table " + tableName;
			logMsg(sweepCtx, msg);
		}

		return result;
	}

	private boolean isReferringRecordIdsAdded(final RuntimeContext sweepCtx,
			final String tableName, final int recordId)
	{
		if (isAlreadyDeleted(sweepCtx, tableName, recordId))
		{
			return true;
		}

		final String recordStr = mkRecordStr(tableName, recordId);
		return REFERRING_RECORDS_ADDED.equals(sweepCtx.records.get(recordStr));
	}

	private boolean isAlreadyDeleted(final RuntimeContext sweepCtx,
			final String tableName, final int recordId)
	{
		final String recordStr = mkRecordStr(tableName, recordId);
		return DELETED.equals(sweepCtx.records.get(recordStr));
	}

	/**
	 * Retrieve Ids from <code>referingTable<code> where <code>referingTableCol</code> = <code>recordId</code>
	 * 
	 * @param referingTable
	 * @param referingTableCol
	 * @param recordId
	 * @return matched ids from referingTable
	 */
	private List<Integer> retrieveRecordIds(final RuntimeContext sweepCtx,
			final String referingTable, final String referingTableCol,
			final int recordId)
	{
		final String keyColName = getKeyColName(sweepCtx.ctx, referingTable);

		if (keyColName == null)
		{
			logger.trace("Table " + referingTable
					+ " has no regular key column; Returning emtpy list");
			return Collections.emptyList();
		}

		final String refInfoStr = getRefInfoString(referingTable,
				referingTableCol, recordId);
		try
		{
			final String wc = referingTableCol + "=" + recordId;
			final int[] ids = new Query(sweepCtx.ctx, referingTable, wc,
					sweepCtx.trxName).setClient_ID().setOrderBy(keyColName)
					.getIDs();

			if (ids.length == 0)
			{
				logger.trace("Retrieved NO RecordIDs for " + refInfoStr);
				return Collections.emptyList();
			}

			logger.debug("Retrieved " + ids.length + " RecordIDs for "
					+ refInfoStr);
			final List<Integer> result = new ArrayList<Integer>(ids.length);
			for (final int id : ids)
			{
				result.add(id);
			}

			//
			// If referingTableCol column is nullable set it, set it to null
			// because
			// * we already got the IDs, so is not needed anymore
			// * we can avoid cycle dependencies extremelly easy and efficient
			// by doing this
			if (isColumnNullable(sweepCtx.ctx, referingTable, referingTableCol)
					&& sweepCtx.targetClientId <= 0)
			{
				final String updateSQL = "UPDATE " + referingTable + " SET "
						+ referingTableCol + "=NULL WHERE " + wc
						+ " AND AD_Client_ID="
						+ Env.getAD_Client_ID(sweepCtx.ctx);
				final int updateCount = DB.executeUpdateEx(updateSQL,
						new Object[] {}, sweepCtx.trxName);
				if (updateCount != ids.length)
				{
					// shall not happen
					logger.error("Query ["
									+ updateSQL
									+ "] updated "
									+ updateCount
									+ " records, while the previous IDs fetch returned "
									+ ids.length + " records");
				}

				logger.debug(referingTable + ": set " + referingTableCol
						+ " to NULL on " + updateCount + " records (" + wc
						+ ")");
			}

			return result;
		}
		catch (DBException e)
		{
			logMsg(sweepCtx, "DBException while trying to retrieve Ids for "
					+ refInfoStr + ". \nMsg=[" + e.getMessage() + "] \nSQL=["
					+ e.getSQL() + "]");
			logger.warn(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	private String getRefInfoString(final String referingTable,
			final String referingTableCol, final int recordId)
	{
		// return "[referingTable=" + referingTable + ", referingTableCol=" +
		// referingTableCol + ", recordId=" +
		// recordId + "]";
		return "[ FROM " + referingTable + " WHERE " + referingTableCol + "="
				+ recordId + "]";
	}

	/**
	 * Get Key ColumnName for given tableName
	 * 
	 * @param ctx
	 * @param tableName
	 * @return
	 */
	private String getKeyColName(final Properties ctx, final String tableName)
	{
		final String[] keyCols = MTable.get(ctx, tableName).getKeyColumns();

		if (keyCols.length != 1)
		{
			return null;
		}

		return keyCols[0];
	}

	private boolean isColumnNullable(final Properties ctx,
			final String tableName, final String columnName)
	{
		final MTable table = MTable.get(ctx, tableName);
		final MColumn column = table.getColumn(columnName);
		if (column == null)
		{
			return false;
		}

		boolean isNullable = !column.isMandatory() && !column.isKey();
		return isNullable;
	}

	private void logMsg(RuntimeContext sweepCtx, final String msg)
	{
		if (sweepCtx == null || sweepCtx.process == null)
		{
			logger.info(msg);
		}
		else
		{
			sweepCtx.process.addLog(msg);
		}
	}

	private String mkRecordStr(final String table, final int id)
	{
		return table + "#" + id;
	}

	/**
	 * Retrieve Table/Columns that are referencing <code>tableName</code>.
	 * 
	 * @param ctx
	 * @param tableName
	 * @param trxName
	 * @return map of TableName => list of ColumnNames
	 */
	private Map<String, List<String>> retrieveReferringTablesAndCols(
			final RuntimeContext sweepCtx, final String tableName)
	{
		final Map<String, List<String>> referingTable2Col = new HashMap<String, List<String>>();

		final String sql = "SELECT TableName, ColumnName FROM db_columns_fk WHERE upper(Ref_TableName)=upper(?)";
		final PreparedStatement pstmt = DB.prepareStatement(sql,
				sweepCtx.trxName);

		ResultSet rsReferingTable = null;
		try
		{
			pstmt.setString(1, tableName);
			rsReferingTable = pstmt.executeQuery();

			while (rsReferingTable.next())
			{
				final String referingTable = rsReferingTable
						.getString("TableName");
				final String referingTableCol = rsReferingTable
						.getString("ColumnName");

				if (tableName.equals(referingTable)
						&& referingTableCol.equals(getKeyColName(sweepCtx.ctx,
								tableName)))
				{
					logger.warn(
							"Deletected a weird self reference: tableName="
									+ referingTable + ", columnName="
									+ referingTableCol + ". [IGNORED]");
					// prevent a stack overflow error due to strange references
					continue;
				}

				List<String> referingColNames = referingTable2Col
						.get(referingTable);
				if (referingColNames == null)
				{
					referingColNames = new ArrayList<String>();
					referingTable2Col.put(referingTable, referingColNames);
				}

				if (!referingColNames.contains(referingTableCol))
				{
					referingColNames.add(referingTableCol);
				}
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rsReferingTable, pstmt);
		}

		//
		// Retrieve Dynamic References
		List<String> dynReferringTables = retrieveDynReferringTables(sweepCtx,
				tableName);
		for (String referringTable : dynReferringTables)
		{
			List<String> referingColNames = referingTable2Col
					.get(referringTable);
			if (referingColNames == null)
			{
				referingColNames = new ArrayList<String>();
				referingTable2Col.put(referringTable, referingColNames);
			}

			if (!referingColNames.contains(COLUMNNAME_TableRecordId))
			{
				referingColNames.add(COLUMNNAME_TableRecordId);
			}
		}

		return referingTable2Col;
	}

	@Cached(cacheName = I_AD_Table.Table_Name)
	/* package */ List<String> retrieveDynReferringTables(
			@CacheIgnore final RuntimeContext sweepCtx,
			final String tableName)
	{
		final List<String> result = new ArrayList<String>();
		final List<String> tables = retrieveTablesWithDynRef(sweepCtx);
		for (String t : tables)
		{
			final List<String> referenceTables = retrieveDynReferenceTables(sweepCtx, t);
			if (referenceTables.contains(tableName))
			{
				result.add(t);
			}
		}

		return result;
	}

	@Cached(cacheName = I_AD_Table.Table_Name)
	/* package */ List<String> retrieveDynReferenceTables(
			@CacheIgnore final RuntimeContext sweepCtx, final String tableName)
	{
		final String sql = "SELECT DISTINCT t.TableName" + " FROM " + tableName
				+ " r"
				+ " INNER JOIN AD_Table t ON (t.AD_Table_ID=r.AD_Table_ID)"
				+ " WHERE r.AD_Table_ID IS NOT NULL";
		final List<String> result = retrieveStringList(sql, sweepCtx.trxName);
		return result;
	}

	@Cached(cacheName = I_AD_Table.Table_Name)
	public List<String> retrieveTablesWithDynRef(
			@CacheIgnore final RuntimeContext sweepCtx)
	{
		final String sql = "SELECT TableName FROM AD_Table"
				+ " WHERE (SELECT count(*) FROM AD_Column c WHERE c.AD_Table_ID=AD_Table.AD_Table_ID AND c.ColumnName IN ('AD_Table_ID', 'Record_ID') AND c.IsActive='Y') = 2"
				+ " AND IsView='N'";
		final List<String> result = retrieveStringList(sql, sweepCtx.trxName);
		return result;
	}

	private static List<String> retrieveStringList(final String sql,
			final String trxName)
	{
		final List<String> result = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String s = rs.getString(1);
				result.add(s);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return result;
	}

	private List<Integer> retrieveDynRecordIds(RuntimeContext sweepCtx,
			String referingTable, String tableName, int recordId)
	{
		final String keyColName = getKeyColName(sweepCtx.ctx, referingTable);

		if (keyColName == null)
		{
			logger.trace("Table " + referingTable
					+ " has no regular key column; Returning emtpy list");
			return Collections.emptyList();
		}

		final MTable table = MTable.get(sweepCtx.ctx, tableName);
		if (table == null || table.getAD_Table_ID() <= 0)
		{
			logger.warn("Table " + tableName + " not found");
			return Collections.emptyList();
		}
		final int adTableId = table.getAD_Table_ID();

		final String refInfoStr = "[ FROM " + referingTable
				+ " WHERE AD_Table_ID=" + tableName + " AND Record_ID"
				+ recordId + "]";
		try
		{
			final String wc = "AD_Table_ID=" + adTableId + " AND Record_ID="
					+ recordId;
			final int[] ids = new Query(sweepCtx.ctx, referingTable, wc,
					sweepCtx.trxName).setClient_ID().setOrderBy(keyColName)
					.getIDs();

			if (ids.length == 0)
			{
				logger.trace("Retrieved NO RecordIDs for " + refInfoStr);
				return Collections.emptyList();
			}

			logger.debug("Retrieved " + ids.length + " RecordIDs for "
					+ refInfoStr);
			final List<Integer> result = new ArrayList<Integer>(ids.length);
			for (final int id : ids)
			{
				result.add(id);
			}

			return result;
		}
		catch (DBException e)
		{
			logMsg(sweepCtx, "DBException while trying to retrieve Ids for "
					+ refInfoStr + ". \nMsg=[" + e.getMessage() + "] \nSQL=["
					+ e.getSQL() + "]");
			logger.warn(e.getLocalizedMessage(), e);
			throw e;
		}
	}

	private int m_storage_recalculate(String trxName)
	{
		final String sql = "select m_storage_recalculate()";
		return DB.getSQLValueEx(trxName, sql);
	}

	private static final class RuntimeContext
	{
		private Properties ctx;
		private String trxName;
		private int targetClientId = -1;
		private JavaProcess process;

		/**
		 * (TableName#RecordId) => {@link #DELETED} or {@link #REFERRING_RECORDS_ADDED}
		 */
		private final Map<String, String> records = new HashMap<String, String>();

		private Set<String> pathsVisited = new HashSet<String>();
	}
}

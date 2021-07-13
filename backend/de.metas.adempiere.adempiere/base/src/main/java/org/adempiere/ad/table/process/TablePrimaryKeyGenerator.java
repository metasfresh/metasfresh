package org.adempiere.ad.table.process;

import ch.qos.logback.classic.Level;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.cache.CacheMgt;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;
import org.compiere.model.MSequence;
import org.compiere.model.M_Element;
import org.compiere.model.POInfo;
import org.compiere.model.X_AD_Column;
import org.compiere.process.AD_Tab_CreateFields;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

class TablePrimaryKeyGenerator
{
	public static TablePrimaryKeyGenerator newInstance()
	{
		return new TablePrimaryKeyGenerator();
	}

	// services
	private static final Logger logger = LogManager.getLogger(TablePrimaryKeyGenerator.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private boolean migrateDataUsingIDServer = true;
	private final Set<String> resultTableNames = new LinkedHashSet<>();

	private TablePrimaryKeyGenerator()
	{
	}

	public TablePrimaryKeyGenerator migrateDataUsingIDServer(final boolean migrateDataUsingIDServer)
	{
		this.migrateDataUsingIDServer = migrateDataUsingIDServer;
		return this;
	}

	private boolean isMigrateDataUsingIDServer()
	{
		return migrateDataUsingIDServer;
	}

	public TablePrimaryKeyGenerator generateForTable(final I_AD_Table adTable)
	{
		if (adTable.isView())
		{
			addLog("Skip {} because it's view", adTable.getTableName());
			return this;
		}

		if (hasColumnPK(adTable))
		{
			addLog("Skip {} because it already has PK", adTable.getTableName());
			return this;
		}

		//
		// Create the primary key
		final I_AD_Column columnPK = createColumnPK(adTable);
		createColumnPK_DDL(adTable.getAD_Table_ID(), adTable.getTableName(), columnPK.getColumnName());

		// Add the primary key column to all tabs
		addToTabs(columnPK);

		// Cache reset
		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.reset(I_AD_Table.Table_Name);
		cacheMgt.reset(I_AD_Column.Table_Name);
		cacheMgt.reset(POInfo.CACHE_PREFIX);

		resultTableNames.add(adTable.getTableName());

		return this;
	}

	public TablePrimaryKeyGenerator generateForTablesIfPossible(final Iterable<I_AD_Table> adTables)
	{
		for (final I_AD_Table adTable : adTables)
		{
			generateForTableIfPossible(adTable);
		}

		return this;
	}

	private void generateForTableIfPossible(final I_AD_Table adTable)
	{
		trxManager.runInNewTrx(new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName)
			{
				generateForTable(adTable);
			}

			@Override
			public boolean doCatch(final Throwable ex)
			{
				logger.warn("Failed generating PK for {}", adTable, ex);
				addLog("@Error@ Generating for {}: {}", adTable.getTableName(), ex.getLocalizedMessage());
				return ROLLBACK;
			}
		});

	}

	public String getSummary()
	{
		return "Generated primary keys for " + resultTableNames.size() + " table(s): " + resultTableNames;
	}

	private void addLog(final String msg, final Object... msgParameters)
	{
		Loggables.withLogger(logger, Level.DEBUG).addLog(msg, msgParameters);
	}

	private boolean hasColumnPK(final I_AD_Table table)
	{
		return queryBL
				.createQueryBuilder(I_AD_Column.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, table.getAD_Table_ID())
				.addEqualsFilter(I_AD_Column.COLUMN_IsKey, true)
				.create()
				.anyMatch();
	}

	private List<String> getParentColumnNames(final int adTableId)
	{
		return queryBL
				.createQueryBuilder(I_AD_Column.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_AD_Column.COLUMN_IsParent, true)
				.create()
				.listDistinct(I_AD_Column.COLUMNNAME_ColumnName, String.class);
	}

	private I_AD_Column createColumnPK(final I_AD_Table table)
	{
		final I_AD_Column columnPK = InterfaceWrapperHelper.newInstance(I_AD_Column.class);
		columnPK.setAD_Org_ID(0);
		columnPK.setAD_Table_ID(table.getAD_Table_ID());
		columnPK.setEntityType(table.getEntityType());

		final I_AD_Element adElement = getOrCreateKeyColumnNameElement(table);
		final String elementColumnName = adElement.getColumnName();
		Check.assumeNotNull(elementColumnName, "The element {} does not have a column name set", adElement);

		columnPK.setAD_Element(adElement);
		columnPK.setColumnName(adElement.getColumnName());
		columnPK.setName(elementColumnName);
		columnPK.setDescription(adElement.getDescription());
		columnPK.setHelp(adElement.getHelp());

		columnPK.setVersion(BigDecimal.ONE);
		columnPK.setAD_Val_Rule(null);
		columnPK.setDefaultValue(null);
		columnPK.setFieldLength(10);
		columnPK.setIsKey(true);
		columnPK.setIsParent(false);
		columnPK.setIsMandatory(true);
		columnPK.setIsTranslated(false);
		columnPK.setIsIdentifier(false);
		// columnPK.setSeqNo();
		columnPK.setIsEncrypted(X_AD_Column.ISENCRYPTED_NichtVerschluesselt);
		columnPK.setAD_Reference_ID(DisplayType.ID);
		columnPK.setAD_Reference_Value(null);
		columnPK.setIsActive(true);
		columnPK.setVFormat(null);
		columnPK.setIsUpdateable(false);
		// columnPK.setAD_Process_ID();
		// columnPK.setValueMin();
		// columnPK.setValueMax();
		columnPK.setIsSelectionColumn(false);
		columnPK.setReadOnlyLogic(null);
		// columnPK.setIsAlwaysUpdateable();
		// columnPK.setColumnSQL(null);
		columnPK.setIsAllowLogging(true);

		saveRecord(columnPK);
		addLog("@Created@ @AD_Column_ID@ " + table.getTableName() + "." + columnPK.getColumnName());

		return columnPK;
	}

	private I_AD_Element getOrCreateKeyColumnNameElement(final I_AD_Table table)
	{
		final String keyColumnName = table.getTableName() + "_ID";

		//
		// Check existing
		final Properties ctx = Env.getCtx();
		final I_AD_Element existingElement = M_Element.get(ctx, keyColumnName);
		if (existingElement != null)
		{
			return existingElement;
		}

		//
		// Create new
		final I_AD_Element element = new M_Element(ctx, keyColumnName, table.getEntityType(), ITrx.TRXNAME_ThreadInherited);
		element.setColumnName(keyColumnName);
		element.setName(table.getName());
		element.setPrintName(table.getName());
		element.setEntityType(table.getEntityType());
		InterfaceWrapperHelper.save(element);
		addLog("@Created@ @AD_Element_ID@: " + element.getColumnName());
		return element;
	}

	private void createColumnPK_DDL(final int adTableId, final String tableName, final String pkColumnName)
	{
		//
		// Fetch the rows to migrate using ID server
		// NOTE: we are doing this ahead, before starting to alter the table
		final List<Map<String, Object>> rowsToMigrateUsingIDServer = retrieveRowsToMigrateUsingIDServer(adTableId, tableName);

		// Create/update the DB sequence
		DB.createTableSequence(tableName);

		//
		// Create the DB column (nullable, no default value)
		executeDDL("ALTER TABLE " + tableName + " ADD COLUMN " + pkColumnName + " numeric(10,0)");

		//
		// Migrate PK's value using ID server
		rowsToMigrateUsingIDServer.forEach(row -> updateRowPKFromIDServer(tableName, pkColumnName, row));
		addLog("Migrated {} rows using ID server", rowsToMigrateUsingIDServer.size());

		//
		// Migrate PK's value using local DB sequence
		// usually this happens for tables where we don't have centralized ID maintainance (e.g. C_Invoice_Candidate_Recompute), so in that case the "rows" above is empty.
		updatePKFromDBSequence(tableName, pkColumnName);

		//
		// Set default value
		// Make it not null
		executeDDL("ALTER TABLE " + tableName + " ALTER COLUMN " + pkColumnName + " SET DEFAULT " + DB.TO_TABLESEQUENCE_NEXTVAL(tableName));
		executeDDL("ALTER TABLE " + tableName + " ALTER COLUMN " + pkColumnName + " SET NOT NULL");

		//
		// Delete the previous PK, if any
		final String pkName = (tableName + "_pkey").toLowerCase();
		final String pkNameAlt = (tableName + "_key").toLowerCase(); // some tables also have this PK name
		executeDDL("ALTER TABLE " + tableName + " DROP CONSTRAINT IF EXISTS " + pkName);
		executeDDL("ALTER TABLE " + tableName + " DROP CONSTRAINT IF EXISTS " + pkNameAlt);

		//
		// Create the new PK
		executeDDL("ALTER TABLE " + tableName + " ADD CONSTRAINT " + pkName + " PRIMARY KEY (" + pkColumnName + ")");
	}

	private List<Map<String, Object>> retrieveRowsToMigrateUsingIDServer(final int adTableId, final String tableName)
	{
		if (!isMigrateDataUsingIDServer())
		{
			return ImmutableList.of();
		}

		final List<String> parentColumnNames = getParentColumnNames(adTableId);
		if (parentColumnNames.isEmpty())
		{
			addLog("Skip migrating " + tableName + "  because it has no parent column names defined");
			return ImmutableList.of();
		}

		return retrieveMaps(tableName, parentColumnNames);
	}

	private static List<Map<String, Object>> retrieveMaps(@NonNull final String tableName, @NonNull final List<String> columnNames)
	{
		Check.assumeNotEmpty(columnNames, "columnNames is not empty");

		final String sqlColumnNames = Joiner.on(", ").join(columnNames);
		final String sql = "SELECT " + sqlColumnNames + " FROM " + tableName
				+ " ORDER BY " + sqlColumnNames;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();

			final List<Map<String, Object>> rows = new ArrayList<>();
			while (rs.next())
			{
				final Map<String, Object> row = retrieveMap(rs, columnNames);
				rows.add(row);
			}

			return rows;
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private static Map<String, Object> retrieveMap(final ResultSet rs, final List<String> columnNames) throws SQLException
	{
		final Map<String, Object> map = new HashMap<>();
		for (final String columnName : columnNames)
		{
			final Object value = rs.getObject(columnName);
			map.put(columnName, value);
		}
		return map;
	}

	private static void updateRowPKFromIDServer(final String tableName, final String pkColumnName, final Map<String, Object> whereClause)
	{
		final int id = MSequence.getNextProjectID_HTTP(tableName);
		if (id <= 0)
		{
			throw new AdempiereException("Failed retrieving ID for " + tableName + " from ID server");
		}

		final StringBuilder sql = new StringBuilder();

		sql.append("UPDATE ").append(tableName).append(" SET ").append(pkColumnName).append("=").append(id);

		sql.append(" WHERE 1=1");
		for (final Map.Entry<String, Object> e : whereClause.entrySet())
		{
			final String columnName = e.getKey();
			final Object value = e.getValue();

			sql.append(" AND ").append(columnName).append("=").append(DB.TO_SQL(value));
		}

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void updatePKFromDBSequence(final String tableName, final String pkColumnName)
	{
		final int count = DB.executeUpdateEx(
				"UPDATE " + tableName + " SET " + pkColumnName + "=" + DB.TO_TABLESEQUENCE_NEXTVAL(tableName) + " WHERE " + pkColumnName + " IS NULL",
				new Object[] {},
				ITrx.TRXNAME_ThreadInherited);
		addLog("Updated {}.{} for {} rows using local DB sequence", tableName, pkColumnName, count);
	}

	private void executeDDL(final String sql)
	{
		DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		addLog("DDL: " + sql);
	}

	private void addToTabs(final I_AD_Column column)
	{
		final int adTableId = column.getAD_Table_ID();

		queryBL.createQueryBuilder(I_AD_Tab.class, column)
				.addEqualsFilter(I_AD_Tab.COLUMN_AD_Table_ID, adTableId)
				.create()
				.stream(I_AD_Tab.class)
				.forEach(tab -> createAD_Field(tab, column));
	}

	private void createAD_Field(final I_AD_Tab tab, final I_AD_Column column)
	{
		final String fieldEntityType = null; // auto

		final I_AD_Field field;
		try
		{
			final boolean displayedIfNotIDColumn = true; // actually doesn't matter because PK probably is an ID column anyways
			field = AD_Tab_CreateFields.createADField(tab, column, displayedIfNotIDColumn, fieldEntityType);

			// log
			final I_AD_Window window = tab.getAD_Window();
			addLog("@Created@ " + window + " -> " + tab + " -> " + field);

		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating AD_Field for {} in {}", column, tab, ex);
			addLog("@Error@ creating AD_Field for {} in {}: {}", column, tab, ex.getLocalizedMessage());
		}

	}
}

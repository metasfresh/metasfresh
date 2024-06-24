package org.adempiere.ad.migration.logger.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.dao.selection.model.I_T_Query_Selection;
import de.metas.dao.selection.model.I_T_Query_Selection_ToDelete;
import de.metas.logging.LogManager;
import de.metas.process.model.I_AD_PInstance_SelectedIncludedRecords;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.migration.logger.IMigrationLoggerContext;
import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.model.I_AD_MigrationData;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_MigrationStep;
import org.adempiere.ad.migration.service.IMigrationBL;
import org.adempiere.ad.migration.util.DefaultDataConverter;
import org.adempiere.ad.migration.util.IDataConverter;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.I_AD_Attachment_Log;
import org.compiere.model.I_AD_Attachment_MultiRef;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Column_Access;
import org.compiere.model.I_AD_Document_Action_Access;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Form_Access;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_AD_Preference;
import org.compiere.model.I_AD_Process_Access;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Process_Stats;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Table_Access;
import org.compiere.model.I_AD_Task_Access;
import org.compiere.model.I_AD_Window_Access;
import org.compiere.model.I_AD_Workflow_Access;
import org.compiere.model.I_API_Request_Audit;
import org.compiere.model.I_API_Request_Audit_Log;
import org.compiere.model.I_API_Response_Audit;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MigrationLogger implements IMigrationLogger
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final IDataConverter converter = new DefaultDataConverter();

	/**
	 * Upper case list of table names that shall be ignored when creating migration scripts and we are running in system mode
	 */
	private final Set<String> _tablesIgnoreSystem = new CopyOnWriteArraySet<>();
	private final Set<String> _tablesIgnoreSystemRO = Collections.unmodifiableSet(_tablesIgnoreSystem);

	/**
	 * Upper case list of table names that shall be ignored when creating migration scripts and we are running in client mode
	 */
	private final Set<String> _tablesIgnoreClient = new CopyOnWriteArraySet<>();
	private final Set<String> _tablesIgnoreClientRO = Collections.unmodifiableSet(_tablesIgnoreClient);

	public MigrationLogger()
	{
		initTablesIgnoreList();
	}

	private void initTablesIgnoreList()
	{
		final List<String> tablesIgnoreListDefault = Arrays.asList(
				"AD_ACCESSLOG",
				"AD_ALERTPROCESSORLOG",
				I_AD_Attachment.Table_Name.toUpperCase(),
				I_AD_Attachment_Log.Table_Name.toUpperCase(),
				I_AD_Attachment_MultiRef.Table_Name.toUpperCase(),
				I_AD_AttachmentEntry.Table_Name.toUpperCase(),
				"AD_CHANGELOG",
				"AD_ISSUE",
				I_AD_Note.Table_Name.toUpperCase(),
				"AD_PACKAGE_IMP",
				"AD_PACKAGE_IMP_BACKUP",
				"AD_PACKAGE_IMP_DETAIL",
				"AD_PACKAGE_IMP_INST",
				"AD_PACKAGE_IMP_PROC",
				I_AD_Process_Stats.Table_Name.toUpperCase(),
				"AD_PINSTANCE",
				"AD_PINSTANCE_LOG",
				"AD_PINSTANCE_PARA",
				I_AD_Preference.Table_Name.toUpperCase(),
				"AD_REPLICATION_LOG",
				"AD_SCHEDULERLOG",
				"AD_SESSION",
				"AD_WORKFLOWPROCESSORLOG",
				I_API_Request_Audit.Table_Name.toUpperCase(),
				I_API_Request_Audit_Log.Table_Name.toUpperCase(),
				I_API_Response_Audit.Table_Name.toUpperCase(),
				"CM_WEBACCESSLOG",
				"K_INDEXLOG",
				"R_REQUESTPROCESSORLOG",
				"T_AGING",
				// "T_ALTER_COLUMN", // this one NEEDs to be logged
				"T_DISTRIBUTIONRUNDETAIL",
				"T_INVENTORYVALUE",
				"T_INVOICEGL",
				"T_REPLENISH",
				"T_REPORT",
				"T_REPORTSTATEMENT",
				"T_SELECTION",
				"T_SELECTION2",
				I_T_Query_Selection.Table_Name.toUpperCase(),
				I_T_Query_Selection_ToDelete.Table_Name.toUpperCase(),
				"T_SPOOL",
				"T_TRANSACTION",
				"T_TRIALBALANCE",
				//
				"AD_VISIBLEFIELDS", // metas: tsa: US762
				"AD_ROLE_PERMREQUEST", // metas: ts: Ma01_US1057
				//
				// Don't log migration scripts tables - 02662
				"AD_MIGRATION",
				"AD_MIGRATIONSTEP",
				"AD_MIGRATIONDATA",

				// Don't log AD_Sequence because these will be created automatically (at least for Table_ID)
				// NOTE: while this is applying for XML migrations, we need this to be logged in SQL migrations
				// and currently we use SQL migrations
				// FIXME: find a way to fine tune this
				// "AD_SEQUENCE"

				I_AD_PInstance_SelectedIncludedRecords.Table_Name.toUpperCase() // https://github.com/metasfresh/metasfresh-webui-api/issues/645
		);

		//
		// Add our standard list of tables to ignore to both lists: client and system
		_tablesIgnoreSystem.addAll(tablesIgnoreListDefault);
		_tablesIgnoreClient.addAll(tablesIgnoreListDefault);

		//
		// Do not log *Access records - teo_Sarca BF [ 2782095 ]
		// NOTE: Only if we are running as system. If user is logged in regular Tenant, we want to log them (07122)
		_tablesIgnoreSystem.addAll(Arrays.asList(
				I_AD_Window_Access.Table_Name.toUpperCase(),
				I_AD_Process_Access.Table_Name.toUpperCase(),
				I_AD_Workflow_Access.Table_Name.toUpperCase(),
				I_AD_Form_Access.Table_Name.toUpperCase(),
				I_AD_Task_Access.Table_Name.toUpperCase(),
				I_AD_Document_Action_Access.Table_Name.toUpperCase(),
				I_AD_Table_Access.Table_Name.toUpperCase(),
				I_AD_Column_Access.Table_Name.toUpperCase()));
	}

	@Override
	public boolean isLogTableName(final String tableName)
	{
		final Set<String> tablesToIgnore = getTablesToIgnoreUC();
		if (tablesToIgnore.contains(tableName.toUpperCase()))
		{
			return false;
		}
		return true;
	}

	@Override
	public void addTableToIgnoreList(final String tableName)
	{
		final String tableNameUC = tableName.toUpperCase();
		_tablesIgnoreSystem.add(tableNameUC);
		_tablesIgnoreClient.add(tableNameUC);
	}

	@Override
	public void removeTableFromIgnoreList(final String tableName)
	{
		final String tableNameUC = tableName.toUpperCase();
		_tablesIgnoreSystem.remove(tableNameUC);
		_tablesIgnoreClient.remove(tableNameUC);
	}

	@Override
	public Set<String> getTablesToIgnoreUC()
	{
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		if (adClientId == Env.CTXVALUE_AD_Client_ID_System)
		{
			return _tablesIgnoreSystemRO;
		}
		else
		{
			return _tablesIgnoreClientRO;
		}
	}

	@Override
	public void logMigration(final MFSession session, final PO po, final POInfo info, final String event)
	{
		final IMigrationLoggerContext migrationCtx = getSessionMigrationLoggerContext(session);
		logMigration(migrationCtx, po, info, event);
	}

	@Override
	public void logMigration(final IMigrationLoggerContext migrationCtx, final PO po, final POInfo info, final String event)
	{
		if (!migrationCtx.isEnabled())
		{
			return;
		}
		if (!isLogTableName(po.get_TableName()))
		{
			return;
		}
		if (!isLogPO(po, info))
		{
			return;
		}

		I_AD_MigrationStep migrationStep = null;
		List<I_AD_MigrationData> stepDataList = null;

		final int size = po.get_ColumnCount();
		for (int i = 0; i < size; i++)
		{
			final I_AD_MigrationData data = createMigrationData(po, info, i, event);
			//
			// Save if needed
			if (data != null)
			{
				if (migrationStep == null)
				{
					migrationStep = createMigrationStep(migrationCtx, po, event);
					stepDataList = new ArrayList<>();
				}
				data.setAD_MigrationStep_ID(migrationStep.getAD_MigrationStep_ID());
				InterfaceWrapperHelper.save(data);
				stepDataList.add(data);
			}
		}

		//
		// Set migration comments (useful for quick review)
		if (migrationStep != null && !stepDataList.isEmpty())
		{
			setComments(po, migrationStep, stepDataList);
		}
	}

	private boolean isLogPO(final PO po, final POInfo pinfo)
	{
		// ignore statistic updates
		// TODO: metas: 02662: shall be deleted because it's handled by AD_Column.IsCalculated flag
			if (pinfo.getTableName().equalsIgnoreCase("AD_Process") && !po.is_new() && po.is_ValueChanged("Statistic_Count"))
		{
			return false;
		}
		return true;
	}

	private I_AD_MigrationData createMigrationData(final PO po, final POInfo info, final int columnIndex, final String event)
	{
		// Don't log encrypted columns
		if (info.isEncrypted(columnIndex))
		{
			return null;
		}
		// Don't log virtual columns
		if (info.isVirtualColumn(columnIndex))
		{
			return null;
		}
		// Don't log calculated columns
		if (info.isCalculated(columnIndex))
		{
			return null;
		}

		final POInfoColumn infoColumn = info.getColumn(columnIndex);

		final I_AD_MigrationData data = InterfaceWrapperHelper.create(po.getCtx(), I_AD_MigrationData.class, po.get_TrxName());
		data.setColumnName(infoColumn.getColumnName());
		data.setAD_Column_ID(infoColumn.getAD_Column_ID());
		boolean create = false;

		//
		// Reference data (old value) on delete/update
		if (X_AD_MigrationStep.ACTION_Delete.equals(event)
				|| (X_AD_MigrationStep.ACTION_Update.equals(event) && po.is_ValueChanged(columnIndex)))
		{
			final Object valueOld = po.get_ValueOld(columnIndex);
			if (valueOld == null)
			{
				data.setIsOldNull(true);
				data.setOldValue(null);
			}
			else
			{
				final String valueOldStr = converter.objectToString(infoColumn, valueOld);
				data.setIsOldNull(false);
				data.setOldValue(valueOldStr);
			}
			create = true;
		}

		//
		// Save new value
		if (X_AD_MigrationStep.ACTION_Insert.equals(event)
				|| (X_AD_MigrationStep.ACTION_Update.equals(event) && po.is_ValueChanged(columnIndex)))
		{
			final Object value = po.get_Value(columnIndex);
			if (value == null)
			{
				data.setIsNewNull(true);
				data.setNewValue(null);
			}
			else
			{
				final String valueStr = converter.objectToString(infoColumn, value);
				data.setIsNewNull(false);
				data.setNewValue(valueStr);
			}
			create = true;
		}

		return create ? data : null;
	}

	/**
	 * Create and set a short description about what was changed in this step
	 */
	protected void setComments(final PO po, final I_AD_MigrationStep migrationStep, final List<I_AD_MigrationData> stepDataList)
	{
		final boolean isDeleted = X_AD_MigrationStep.ACTION_Delete.equals(migrationStep.getAction());
		final String poStr = getSummary(po, isDeleted);
		if (Check.isEmpty(poStr))
		{
			return;
		}

		if (isDeleted) // X_AD_MigrationStep.ACTION_Delete.equals(migrationStep.getAction())
		{
			migrationStep.setComments("Deleted " + poStr);
		}
		else if (X_AD_MigrationStep.ACTION_Insert.equals(migrationStep.getAction()))
		{
			migrationStep.setComments("Created " + poStr);
		}
		else if (X_AD_MigrationStep.ACTION_Update.equals(migrationStep.getAction()))
		{
			// build fields info
			final StringBuffer fieldsInfo = new StringBuffer();
			for (I_AD_MigrationData data : stepDataList)
			{
				final String dataStr = getSummary(data);
				if (Check.isEmpty(dataStr, true))
				{
					continue;
				}
				if (fieldsInfo.length() > 0)
				{
					fieldsInfo.append(", ");
				}
				fieldsInfo.append(dataStr);
			}

			migrationStep.setComments("Updated " + poStr + ": " + fieldsInfo);
		}

		InterfaceWrapperHelper.save(migrationStep);
	}

	protected String getSummary(final I_AD_MigrationData data)
	{
		String columnName = data.getAD_Column().getColumnName();
		return columnName;
	}

	protected String getSummary(final PO po, final boolean isDeleted)
	{
		if (po == null)
		{
			return null;
		}

		final String trxName = po.get_TrxName();

		//
		// Handle AD_Table
		if (I_AD_Table.Table_Name.equals(po.get_TableName()))
		{
			final I_AD_Table table = InterfaceWrapperHelper.create(po, I_AD_Table.class, isDeleted);
			final String tableName = table.getTableName();
			return "Table " + tableName;
		}
		//
		// Handle AD_Column
		else if (I_AD_Column.Table_Name.equals(po.get_TableName()))
		{
			final I_AD_Column column = InterfaceWrapperHelper.create(po, I_AD_Column.class, isDeleted);
			final String tableName = DB.getSQLValueString(trxName, "SELECT t.TableName FROM AD_Table t WHERE t.AD_Table_ID=?", column.getAD_Table_ID());
			return "Column " + tableName + "." + column.getColumnName();
		}
		//
		// Handle AD_Tab
		else if (I_AD_Tab.Table_Name.equals(po.get_TableName()))
		{
			final I_AD_Tab tab = InterfaceWrapperHelper.create(po, I_AD_Tab.class, isDeleted);
			final String sql = "SELECT w.Name||'-'||?||'['||t.TableName||']'"
					+ " FROM AD_Window w, AD_Table t"
					+ " WHERE w.AD_Window_ID=? AND t.AD_Table_ID=?";
			final String tabStr = DB.getSQLValueString(trxName, sql, tab.getName(), tab.getAD_Window_ID(), tab.getAD_Table_ID());
			return "Tab " + tabStr;
		}
		//
		// Handle AD_Field
		else if (I_AD_Field.Table_Name.equals(po.get_TableName()))
		{
			final I_AD_Field field = InterfaceWrapperHelper.create(po, I_AD_Field.class, isDeleted);
			final String sql = "SELECT w.Name||'-'||tt.Name||'['||t.TableName||']-'||?||'['||c.ColumnName||']'"
					+ " FROM AD_Tab tt, AD_Window w, AD_Table t, AD_Column c"
					+ " WHERE tt.AD_Tab_ID=? AND tt.AD_Window_ID=w.AD_Window_ID AND t.AD_Table_ID=tt.AD_Table_ID AND c.AD_Column_ID=?";

			final String fieldStr = DB.getSQLValueString(trxName, sql, field.getName(), field.getAD_Tab_ID(), field.getAD_Column_ID());
			return "Field " + fieldStr;
		}
		//
		// Handle AD_Process_Para
		else if (I_AD_Process_Para.Table_Name.equals(po.get_TableName()))
		{
			final I_AD_Process_Para ppara = InterfaceWrapperHelper.create(po, I_AD_Process_Para.class, isDeleted);
			final String processName = DB.getSQLValueString(trxName, "SELECT p.Name FROM AD_Process p WHERE p.AD_Process_ID=?", ppara.getAD_Process_ID());
			return "Process param " + processName + "." + ppara.getColumnName();
		}
		//
		// Handle AD_Ref_List
		else if (I_AD_Ref_List.Table_Name.equals(po.get_TableName()))
		{
			final I_AD_Ref_List refList = InterfaceWrapperHelper.create(po, I_AD_Ref_List.class, isDeleted);
			final String refName = DB.getSQLValueString(trxName, "SELECT r.Name FROM AD_Reference r WHERE r.AD_Reference_ID=?", refList.getAD_Reference_ID());
			return "Ref List " + refName + "." + refList.getValue() + "_" + refList.getName();
		}
		//
		// Handle AD_Ref_List
		else if (I_AD_Ref_Table.Table_Name.equals(po.get_TableName()))
		{
			final I_AD_Ref_Table refTable = InterfaceWrapperHelper.create(po, I_AD_Ref_Table.class, isDeleted);
			final String refTableName = DB.getSQLValueString(trxName,
					"SELECT r.Name||'-'||t.TableName"
							+ " FROM AD_Reference r, AD_Table t"
							+ " WHERE r.AD_Reference_ID=? AND t.AD_Table_ID=?",
					refTable.getAD_Reference_ID(), refTable.getAD_Table_ID());
			return "Ref Table " + refTableName;
		}
		//
		// Handle general case
		else
		{
			final StringBuffer sb = new StringBuffer();
			final int size = po.get_ColumnCount();
			for (int i = 0; i < size; i++)
			{
				String columnName = po.get_ColumnName(i);
				if (columnName.indexOf("Name") >= 0
						|| columnName.equals("Value"))
				{
					final Object value = isDeleted ? po.get_ValueOld(i) : po.get_Value(i);
					final String valueStr = String.valueOf(value);
					if (Check.isEmpty(valueStr, true))
					{
						continue;
					}
					if (sb.length() > 0)
					{
						sb.append(", ");
					}
					sb.append(columnName).append("=").append(value);
				}
			}

			if (sb.length() == 0)
			{
				return null;
			}

			sb.insert(0, po.get_TableName() + "[");
			sb.append("]");
			return sb.toString();
		}
	}

	@Override
	public void logMigrationSQL(final PO contextPO, final String sql)
	{
		final Properties ctx = contextPO == null ? Env.getCtx() : contextPO.getCtx();
		final MFSession session = Services.get(ISessionBL.class).getCurrentSession(ctx);
		if (session == null)
		{
			logger.warn("AD_Session not found");
			return;
		}
		final IMigrationLoggerContext migrationCtx = getSessionMigrationLoggerContext(session);

		if (!migrationCtx.isEnabled())
		{
			return;
		}

		final I_AD_MigrationStep migrationStep = createMigrationStep(migrationCtx, contextPO);
		migrationStep.setStepType(X_AD_MigrationStep.STEPTYPE_SQLStatement);
		migrationStep.setDBType(X_AD_MigrationStep.DBTYPE_AllDatabaseTypes);
		migrationStep.setSQLStatement(sql);
		InterfaceWrapperHelper.save(migrationStep);
	}

	protected I_AD_MigrationStep createMigrationStep(final IMigrationLoggerContext migrationCtx, final PO contextPO)
	{
		String entityType = null;
		final Properties ctx;
		final String trxName;
		if (contextPO != null)
		{
			final int idxEntityType = contextPO.get_ColumnIndex("EntityType");
			if (idxEntityType >= 0)
			{
				entityType = contextPO.get_ValueAsString("EntityType");
			}

			ctx = contextPO.getCtx();
			trxName = contextPO.get_TrxName();
		}
		else
		{
			ctx = Env.getCtx();
			trxName = null;
		}

		final I_AD_Migration migration = getCreateMigration(migrationCtx, entityType);

		final I_AD_MigrationStep migrationStep = InterfaceWrapperHelper.create(ctx, I_AD_MigrationStep.class, trxName);
		migrationStep.setAD_Migration(migration);

		return migrationStep;
	}

	protected I_AD_MigrationStep createMigrationStep(final IMigrationLoggerContext migrationCtx, final PO po, final String event)
	{
		final I_AD_MigrationStep migrationStep = createMigrationStep(migrationCtx, po);
		migrationStep.setStepType(X_AD_MigrationStep.STEPTYPE_ApplicationDictionary);
		migrationStep.setAction(event);
		migrationStep.setAD_Table_ID(po.get_Table_ID());
		migrationStep.setTableName(po.get_TableName());
		migrationStep.setRecord_ID(po.get_ID());
		Services.get(IMigrationBL.class).setSeqNo(migrationStep);
		InterfaceWrapperHelper.save(migrationStep);

		return migrationStep;
	}

	private I_AD_Migration getCreateMigration(final IMigrationLoggerContext mctx, final String entityType)
	{
		String entityTypeActual = entityType;
		if (entityTypeActual == null)
		{
			entityTypeActual = getDefaultEntityType();
		}

		final String key = entityTypeActual;
		I_AD_Migration migration = mctx.getMigration(key);
		if (migration == null
				|| !migration.isActive()) // migration was closed, open another one
		{
			migration = createMigration(Env.getCtx(), entityTypeActual);
			mctx.putMigration(key, migration);
		}
		return migration;
	}

	protected I_AD_Migration createMigration(final Properties ctx, final String entityType)
	{
		// this record shall be created out of transaction since it will accessible in more then one transaction
		final String trxName = null;

		final I_AD_Migration migration = InterfaceWrapperHelper.create(ctx, I_AD_Migration.class, trxName);
		migration.setEntityType(entityType);
		Services.get(IMigrationBL.class).setSeqNo(migration);
		setName(migration);
		InterfaceWrapperHelper.save(migration);

		return migration;
	}

	protected void setName(final I_AD_Migration migration)
	{
		final String name = Services.get(ISysConfigBL.class).getValue("DICTIONARY_ID_COMMENTS");
		migration.setName(name);
	}

	protected String getDefaultEntityType()
	{
		boolean dict = Ini.isPropertyBool(Ini.P_ADEMPIERESYS);
		return dict ? "D" : "U";
	}

	protected IMigrationLoggerContext getSessionMigrationLoggerContext(final MFSession session)
	{
		final String key = getClass().getCanonicalName();
		IMigrationLoggerContext mctx = session.getTransientProperty(key);
		if (mctx == null)
		{
			mctx = new SessionMigrationLoggerContext();
			session.putTransientProperty(key, mctx);
		}

		return mctx;
	}
}

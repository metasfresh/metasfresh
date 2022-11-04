package org.adempiere.ad.migration.executor.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.migration.executor.IMigrationExecutorContext;
import org.adempiere.ad.migration.executor.MigrationExecutorException;
import org.adempiere.ad.migration.model.I_AD_MigrationData;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_MigrationStep;
import org.adempiere.ad.migration.service.IMigrationDAO;
import org.adempiere.ad.migration.util.DefaultDataConverter;
import org.adempiere.ad.migration.util.IDataConverter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

public class POMigrationStepExecutor extends AbstractMigrationStepExecutor
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final IDataConverter converter = new DefaultDataConverter();

	private List<I_AD_MigrationData> m_migrationData;
	private List<I_AD_MigrationData> m_migrationDataKeys;

	public POMigrationStepExecutor(final IMigrationExecutorContext migrationCtx, final I_AD_MigrationStep step)
	{
		super(migrationCtx, step);
	}

	@Override
	public ExecutionResult apply(final String trxName)
	{
		final I_AD_MigrationStep step = getAD_MigrationStep();

		if (getAD_Table(trxName) == null)
		{
			final boolean fatal = !getMigrationExecutorContext().isSkipMissingTables();
			throw new MigrationExecutorException("@NotFound@ @AD_Table_ID@=" + step.getAD_Table_ID() + " (@TableName@: " + step.getTableName() + ") on step " + step, fatal);
		}

		//
		// Fetch PO, create if not exist and action is Insert
		final boolean createPOIfNotExists = X_AD_MigrationStep.ACTION_Insert.equals(step.getAction())
				&& getMigrationExecutorContext().isApplyDML();
		final PO po = fetchPO(createPOIfNotExists, trxName);
		if (po == null)
		{
			// No PO record found
			final AdempiereException ex = new AdempiereException("PO record not found for AD_MigrationStep: " + step + ", SeqNo=" + step.getSeqNo());
			logger.warn(ex.getLocalizedMessage(), ex);
			return ExecutionResult.Skipped;
		}

		//
		// DML: Apply migration data
		if (getMigrationExecutorContext().isApplyDML())
		{
			for (final I_AD_MigrationData data : getMigrationData())
			{
				try
				{
					final MigrationDataExecutor dataExecutor = new MigrationDataExecutor(getMigrationExecutorContext(), step, data, po, converter);
					dataExecutor.apply();
				}
				catch (MigrationExecutorException e)
				{
					if (e.isFatal())
					{
						throw e;
					}
					logger.info(e.getLocalizedMessage(), e);
				}
			}
		}

		if (X_AD_MigrationStep.ACTION_Delete.equals(step.getAction()))
		{
			//
			// DDL: Synchronize with database
			if (getMigrationExecutorContext().isApplyDDL() && po != null)
			{
				if (I_AD_Column.Table_Name.equals(po.get_TableName()))
				{
					syncDBColumn(InterfaceWrapperHelper.create(po, I_AD_Column.class), true);
				}
			}

			//
			// DML: Apply migration data
			if (getMigrationExecutorContext().isApplyDML())
			{
				po.deleteEx(false);
			}
		}
		else
		{
			//
			// DML: Apply migration data
			if (getMigrationExecutorContext().isApplyDML())
			{
				po.saveEx();
			}

			//
			// DDL: Synchronize with database
			if (I_AD_Column.Table_Name.equals(po.get_TableName())
					&& getMigrationExecutorContext().isApplyDDL())
			{
				syncDBColumn(InterfaceWrapperHelper.create(po, I_AD_Column.class), false);
			}
		}

		log(null, "Applied", false);
		return ExecutionResult.Executed;
	}

	@Override
	public ExecutionResult rollback(final String trxName)
	{
		final I_AD_MigrationStep step = getAD_MigrationStep();

		if (getAD_Table(trxName) == null)
		{
			final boolean fatal = !getMigrationExecutorContext().isSkipMissingTables();
			throw new MigrationExecutorException("@NotFound@ @AD_Table_ID@=" + step.getAD_Table_ID() + " (@TableName@: " + step.getTableName() + ") on step " + step, fatal);
		}

		final boolean createPOIfNotExists = X_AD_MigrationStep.ACTION_Delete.equals(step.getAction()); // create if not exist and action is Delete
		final PO po = fetchPO(createPOIfNotExists, trxName);

		if (po == null)
		{
			log("PO not found", "SKIP", true);
			return ExecutionResult.Skipped;
		}

		//
		// Action=Insert => Rollback action=Delete
		if (X_AD_MigrationStep.ACTION_Insert.equals(step.getAction()))
		{
			if (I_AD_Column.Table_Name.equals(po.get_TableName()))
			{
				syncDBColumn(InterfaceWrapperHelper.create(po, I_AD_Column.class), true);
			}

			po.deleteEx(false);
		}
		//
		// Action=Update/Delete => Rollback action=Insert/Update
		else if (X_AD_MigrationStep.ACTION_Update.equals(step.getAction())
				|| X_AD_MigrationStep.ACTION_Delete.equals(step.getAction()))
		{
			for (final I_AD_MigrationData data : getMigrationData())
			{
				final MigrationDataExecutor dataExecutor = new MigrationDataExecutor(getMigrationExecutorContext(), step, data, po, converter);
				dataExecutor.rollback();
			}

			po.saveEx();

			if (I_AD_Column.Table_Name.equals(po.get_TableName()))
			{
				syncDBColumn(InterfaceWrapperHelper.create(po, I_AD_Column.class), false);
			}
		}
		else
		{
			throw new AdempiereException("Unknown step action " + step.getAction());
		}

		log(null, "Rollback", false);
		return ExecutionResult.Executed;
	}

	private PO fetchPO(final boolean createIfNotExists, final String trxName)
	{
		final I_AD_MigrationStep step = getAD_MigrationStep();

		final I_AD_Table table = getAD_Table(trxName);
		final MTable tablePO = (MTable)InterfaceWrapperHelper.getPO(table);

		final StringBuffer whereClause = new StringBuffer();
		final LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		//
		// Single Key Record
		if (step.getRecord_ID() > 0)
		{
			final String[] keyColumns = tablePO.getKeyColumns();
			if (keyColumns == null || keyColumns.length != 1)
			{
				logger.warn("Table " + tablePO + " has none or more then one key columns: " + keyColumns);
			}
			else
			{
				whereClause.append(keyColumns[0]).append("=?");
				params.put(keyColumns[0], step.getRecord_ID());
			}
		}

		//
		// Trying to identify the key columns from our records

		if (whereClause.length() == 0)
		{
			final List<I_AD_MigrationData> keys = getKeyData();
			if (keys != null && !keys.isEmpty())
			{
				for (final I_AD_MigrationData key : keys)
				{
					if (whereClause.length() > 0)
					{
						whereClause.append(" AND ");
					}

					final I_AD_Column column = key.getAD_Column();
					final String columnName = column.getColumnName();
					final String valueStr = key.getNewValue();
					final Object value = converter.stringToObject(column, valueStr);

					whereClause.append(columnName).append("=?");
					params.put(columnName, value);
				}
			}
		}

		//
		// Query PO
		PO po = new Query(getCtx(), tablePO, whereClause.toString(), trxName)
				.setParameters(params.values().toArray())
				.firstOnly(PO.class);

		//
		// Create new PO
		if (po == null && createIfNotExists)
		{
			po = tablePO.getPO(0, trxName);

			for (String columnName : po.get_KeyColumns())
			{
				final Object value = params.get(columnName);
				if (value == null)
				{
					logger.warn("No value found for key " + columnName);
				}
				po.set_ValueNoCheck(columnName, value);
			}
			po.setIsAssignedID(true);
		}

		return po;
	}

	private I_AD_Table getAD_Table(final String trxName)
	{
		final I_AD_MigrationStep step = getAD_MigrationStep();

		I_AD_Table table = step.getAD_Table();

		if (table != null && !step.getTableName().equalsIgnoreCase(table.getTableName()))
		{
			logger.warn("Table ID collision '" + step.getTableName() + "' with existing '" + table.getTableName()
					+ "' (ID=" + step.getAD_Table_ID() + "). Attempting to retrieve table by name.");
			table = null;
		}

		if (table == null)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(step);
			final String whereClause = I_AD_Table.COLUMNNAME_TableName + "=?";
			table = new TypedSqlQuery<I_AD_Table>(ctx, I_AD_Table.class, whereClause, trxName)
					.setParameters(step.getTableName())
					.firstOnly(I_AD_Table.class);
		}

		return table;
	}

	private List<I_AD_MigrationData> getMigrationData()
	{
		if (m_migrationData == null)
		{
			m_migrationData = Services.get(IMigrationDAO.class).retrieveMigrationData(getAD_MigrationStep());
			m_migrationDataKeys = null;
		}
		return m_migrationData;
	}

	private List<I_AD_MigrationData> getKeyData()
	{
		if (m_migrationDataKeys != null)
		{
			return m_migrationDataKeys;
		}

		final List<I_AD_MigrationData> dataKeys = new ArrayList<I_AD_MigrationData>();
		final List<I_AD_MigrationData> dataParents = new ArrayList<I_AD_MigrationData>();
		for (final I_AD_MigrationData data : getMigrationData())
		{
			final I_AD_Column column = data.getAD_Column();

			if (column == null)
			{
				logger.warn("Column is null for data: {}", new Object[] { data });
				continue;
			}

			if (column.isKey())
			{
				dataKeys.add(data);
			}
			if (column.isParent())
			{
				dataParents.add(data);
			}
		}

		if (dataKeys.size() == 1)
		{
			m_migrationDataKeys = dataKeys;
		}
		else if (!dataParents.isEmpty())
		{
			m_migrationDataKeys = dataParents;
		}
		else
		{
			throw new AdempiereException("Invalid key/parent constraints. Keys: " + dataKeys + ", Parents: " + dataParents);
		}

		return m_migrationDataKeys;
	}

	private void syncDBColumn(final I_AD_Column column, final boolean drop)
	{
		final IMigrationExecutorContext migrationCtx = getMigrationExecutorContext();
		final ColumnSyncDDLExecutable ddlExecutable = new ColumnSyncDDLExecutable(AdColumnId.ofRepoId(column.getAD_Column_ID()), drop);
		migrationCtx.addPostponedExecutable(ddlExecutable);
	}
}

package org.adempiere.impexp;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.LoggerLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.DB;
import org.compiere.util.DB.OnFail;
import org.compiere.util.Env;
import org.compiere.util.ISqlUpdateReturnProcessor;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;

/**
 * Base implementation of {@link IImportProcess}.
 *
 * Implementors shall extend this class instead of implementing {@link IImportProcess}.
 *
 * @author tsa
 *
 * @param <ImportRecordType> import table model (e.g. I_I_BPartner).
 */
public abstract class AbstractImportProcess<ImportRecordType> implements IImportProcess<ImportRecordType>
{
	public static enum ImportRecordResult
	{
		Inserted, Updated, Nothing,
	};

	public static final String COLUMNNAME_I_IsImported = "I_IsImported";
	public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";
	public static final String COLUMNNAME_Processed = "Processed";
	public static final String COLUMNNAME_Processing = "Processing";

	// services
	protected final transient Logger log = LogManager.getLogger(getClass());
	protected final ITrxManager trxManager = Services.get(ITrxManager.class);

	//
	// Parameters
	private Properties _ctx;
	private IParams _parameters = IParams.NULL;
	private ILoggable loggable = LoggerLoggable.of(log, Level.INFO);

	@Override
	public final AbstractImportProcess<ImportRecordType> setCtx(final Properties ctx)
	{
		this._ctx = ctx;
		return this;
	}

	@Override
	public final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	protected final int getAD_Client_ID()
	{
		return Env.getAD_Client_ID(getCtx());
	}

	@Override
	public final AbstractImportProcess<ImportRecordType> setParameters(final IParams params)
	{
		Check.assumeNotNull(params, "params not null");
		this._parameters = params;
		return this;
	}

	protected final IParams getParameters()
	{
		return _parameters;
	}

	@Override
	public final AbstractImportProcess<ImportRecordType> setLoggable(final ILoggable loggable)
	{
		Check.assumeNotNull(loggable, "loggable not null");
		this.loggable = loggable;
		return this;
	}

	protected final ILoggable getLoggable()
	{
		return loggable;
	}

	private final boolean isValidateOnly()
	{
		return getParameters().getParameterAsBool(PARAM_IsValidateOnly);
	}

	private final boolean isDeleteOldImported()
	{
		return getParameters().getParameterAsBool(PARAM_DeleteOldImported);
	}

	protected String getImportKeyColumnName()
	{
		return getImportTableName() + "_ID";
	}

	protected abstract String getTargetTableName();

	@Override
	public final String getWhereClause()
	{
		StringBuilder whereClause = new StringBuilder();

		// AD_Client
		whereClause.append(" AND AD_Client_ID=").append(getAD_Client_ID());

		// Selection_ID
		final int selectionId = getParameters().getParameterAsInt(PARAM_Selection_ID);
		if (selectionId > 0)
		{
			whereClause.append(" AND EXISTS (SELECT 1 FROM T_SELECTION s WHERE s.AD_PInstance_ID=" + selectionId + " AND s.T_Selection_ID=" + getImportKeyColumnName() + ")");
		}

		return whereClause.toString();
	}


	@Override
	public final ImportProcessResult run()
	{
		// Assume we are not running in another transaction because that could introduce deadlocks,
		// because we are creating the transactions here.
		trxManager.assertThreadInheritedTrxNotExists();

		//
		// Delete old imported records (out of trx)
		if (isDeleteOldImported())
		{
			deleteOldImported();
		}

		//
		// Reset standard columns (out of trx)
		resetStandardColumns();

		final ImportProcessResult importResult = ImportProcessResult.newInstance(getTargetTableName());

		//
		// Update and validate
		ModelValidationEngine.get().fireImportValidate(this, null, null, IImportInterceptor.TIMING_BEFORE_VALIDATE);
		trxManager.run(new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				updateAndValidateImportRecords();
			}
		});
		ModelValidationEngine.get().fireImportValidate(this, null, null, IImportInterceptor.TIMING_AFTER_VALIDATE);
		if (isValidateOnly())
		{
			return importResult;
		}

		//
		// Actual import (allow the method to manage the transaction)
		importData(importResult);

		loggable.addLog("" + importResult);

		return importResult;
	}

	protected final void deleteOldImported()
	{
		final String sql = "DELETE FROM " + getImportTableName() + "WHERE " + COLUMNNAME_I_IsImported + "='Y'" + getWhereClause();
		final int no = DB.executeUpdateEx(sql, ITrx.TRXNAME_ThreadInherited);
		loggable.addLog("Deleted Old Imported =" + no);
	}

	/** @return a map of ImportTable_ColumnName to DefaultValue, to be used when the value is null */
	protected Map<String, Object> getImportTableDefaultValues()
	{
		return ImmutableMap.of();
	}

	/**
	 * Reset standard columns (Client, Org, IsActive, Created/Updated).
	 *
	 * Called before starting to validate.
	 */
	protected void resetStandardColumns()
	{
		final StringBuilder sql = new StringBuilder("UPDATE " + getImportTableName()
				+ " SET AD_Client_ID = COALESCE (AD_Client_ID, ").append(getAD_Client_ID()).append("),"
						+ " AD_Org_ID = COALESCE (AD_Org_ID, 0),"
						+ " IsActive = COALESCE (IsActive, 'Y'),"
						+ " Created = COALESCE (Created, now()),"
						+ " CreatedBy = COALESCE (CreatedBy, 0),"
						+ " Updated = COALESCE (Updated, now()),"
						+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
						+ COLUMNNAME_I_ErrorMsg + " = ' ',"
						+ COLUMNNAME_I_IsImported + "= 'N' ");
		final List<Object> sqlParams = new ArrayList<>();

		for (final Map.Entry<String, Object> defaultValueEntry : getImportTableDefaultValues().entrySet())
		{
			final String columnName = defaultValueEntry.getKey();
			final Object value = defaultValueEntry.getValue();

			sql.append("\n, " + columnName + "=COALESCE(" + columnName + ", ?)");
			sqlParams.add(value);
		}

		sql.append("\n WHERE (" + COLUMNNAME_I_IsImported + "<>'Y' OR " + COLUMNNAME_I_IsImported + " IS NULL) " + getWhereClause());
		final int no = DB.executeUpdateEx(sql.toString(),
				sqlParams.toArray(),
				ITrx.TRXNAME_ThreadInherited);
		log.debug("Reset=" + no);

	}

	/**
	 * Prepare data import: fill missing fields (if possible) and validate the records.
	 */
	protected abstract void updateAndValidateImportRecords();

	/**
	 * Actual data import.
	 *
	 * @param importResult
	 */
	protected final void importData(final ImportProcessResult importResult)
	{
		final Properties ctx = getCtx();

		//
		// Build SQL
		final String whereClause = getWhereClause();
		final StringBuilder sql = new StringBuilder("SELECT * FROM " + getImportTableName() + " WHERE " + COLUMNNAME_I_IsImported + "='N' ").append(whereClause);
		// ORDER BY
		sql.append(" ORDER BY ");
		final String sqlOrderBy = getImportOrderBySql();
		if (!Check.isEmpty(sqlOrderBy, true))
		{
			sql.append(sqlOrderBy);
		}
		else
		{
			sql.append(getImportKeyColumnName());
		}

		//
		// Go through Records
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None); // i.e. out of transaction
			rs = pstmt.executeQuery();

			final IMutable<Object> state = new Mutable<>();
			while (rs.next())
			{
				final ImportRecordType importRecord = retrieveImportRecord(ctx, rs);
				trxManager.run(new TrxRunnableAdapter()
				{
					private ImportRecordResult recordImportResult;
					private Throwable error;

					@Override
					public void run(final String localTrxName) throws Exception
					{
						this.recordImportResult = importRecord(state, importRecord);

						markImported(importRecord);
					}

					@Override
					public boolean doCatch(final Throwable e) throws Throwable
					{
						this.error = e;
						return true; // rollback
					}

					@Override
					public void doFinally()
					{
						if (error != null)
						{
							reportError(importRecord, error.getLocalizedMessage());
							InterfaceWrapperHelper.markStaled(importRecord); // just in case some BL wants to get values from it
						}
						else if (recordImportResult == ImportRecordResult.Inserted)
						{
							importResult.incrementInsertCounter();
						}
						else if (recordImportResult == ImportRecordResult.Updated)
						{
							importResult.incrementUpdateCounter();
						}
					}
				});
			}

			afterImport();
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql.toString());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;

			final int noErrors = markNotImportedAllWithErrors();
			importResult.setErrorCount(noErrors);
		}
	}

	protected abstract String getImportOrderBySql();

	protected abstract ImportRecordType retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException;

	protected abstract ImportRecordResult importRecord(final IMutable<Object> state, final ImportRecordType importRecord) throws Exception;

	protected final void reportError(final ImportRecordType importRecord, final String errorMsg)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(importRecord);
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		final int importRecordId = InterfaceWrapperHelper.getId(importRecord);

		final StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET " + COLUMNNAME_I_IsImported + "=?, " + COLUMNNAME_I_ErrorMsg + "=I_ErrorMsg || ?")
				.append(" WHERE " + keyColumnName + "=?");
		final Object[] sqlParams = new Object[] {
				"E" // I_IsImported
				, Check.isEmpty(errorMsg, true) ? "" : errorMsg + ", " // ErrorMsg
				, importRecordId // record Id
		};

		DB.executeUpdate(sql.toString(),
				sqlParams,
				OnFail.IgnoreButLog,
				ITrx.TRXNAME_ThreadInherited,
				0, // no timeOut
				(ISqlUpdateReturnProcessor)null);
	}

	protected final int markNotImportedAllWithErrors()
	{
		final StringBuilder sql = new StringBuilder("UPDATE " + getImportTableName()
				+ " SET " + COLUMNNAME_I_IsImported + "='N', Updated=now() "
				+ " WHERE " + COLUMNNAME_I_IsImported + "<>'Y' ").append(getWhereClause());
		final int no = DB.executeUpdate(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		return no >= 0 ? no : 0;
	}

	protected void markImported(final ImportRecordType importRecord)
	{
		InterfaceWrapperHelper.setValue(importRecord, COLUMNNAME_I_IsImported, true);
		InterfaceWrapperHelper.setValue(importRecord, COLUMNNAME_Processed, true);
		InterfaceWrapperHelper.setValue(importRecord, COLUMNNAME_Processing, false);
		InterfaceWrapperHelper.save(importRecord);
	}

	protected void afterImport()
	{
		// nothing to do here
	}
}

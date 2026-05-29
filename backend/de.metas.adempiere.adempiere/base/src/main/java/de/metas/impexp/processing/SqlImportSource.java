package de.metas.impexp.processing;

import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.impexp.ActualImportRecordsResult;
import de.metas.impexp.DataImportRunId;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.impexp.format.ImportTableDescriptor;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.StringUtils;
import de.metas.util.collections.IteratorUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.dao.impl.SqlQueryOrderBy;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.db.util.AbstractPreparedStatementBlindIterator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public class SqlImportSource<ImportRecordType> implements ImportSource<ImportRecordType>
{
	// Services
	@NonNull private final IQueryBL queryBL;
	@NonNull private final IErrorManager errorManager;
	@NonNull private final ILoggable loggable;

	// Params
	@NonNull private final ImportTableDescriptor tableDescriptor;
	@NonNull private final ImportRecordLoader<ImportRecordType> recordLoader;
	@NonNull private final DBFunctions dbFunctions;
	@Nullable private final Map<String, Object> importRecordDefaultValues;
	@NonNull private final ClientId clientId;
	@NonNull private final QueryLimit limit;
	@NonNull private final SqlQueryOrderBy sqlOrderBy;

	//
	// State
	@NonNull private final ImportRecordsSelection mainSelection;
	@Nullable private ImportRecordsSelection _batchSelection; // lazy

	@Builder
	private SqlImportSource(
			@NonNull final IQueryBL queryBL,
			@NonNull final IErrorManager errorManager,
			@NonNull final ILoggable loggable,
			@NonNull final ImportTableDescriptor tableDescriptor,
			@NonNull final ImportRecordLoader<ImportRecordType> recordLoader,
			@NonNull final DBFunctions dbFunctions,
			@Nullable final Map<String, Object> importRecordDefaultValues,
			@Nullable final String importOrderBySql,
			@NonNull final ClientId clientId,
			@Nullable final PInstanceId selectionId,
			@Nullable final QueryLimit limit)
	{
		this.queryBL = queryBL;
		this.errorManager = errorManager;
		this.loggable = loggable;
		this.tableDescriptor = tableDescriptor;
		this.recordLoader = recordLoader;
		this.dbFunctions = dbFunctions;
		this.importRecordDefaultValues = importRecordDefaultValues;
		this.clientId = clientId;
		this.limit = limit != null ? limit : QueryLimit.NO_LIMIT;
		this.sqlOrderBy = SqlQueryOrderBy.of(StringUtils.trimBlankToOptional(importOrderBySql).orElse(tableDescriptor.getKeyColumnName()));
		this.mainSelection = ImportRecordsSelection.builder()
				.importTableName(tableDescriptor.getTableName())
				.importKeyColumnName(tableDescriptor.getKeyColumnName())
				.clientId(clientId)
				.selectionId(selectionId)
				.build();

		loggable.addLog("Using " + this.dbFunctions);
	}

	@NonNull
	@Override
	public ImportRecordsSelection getSelection()
	{
		ImportRecordsSelection batchSelection = this._batchSelection;
		if (batchSelection == null)
		{
			batchSelection = this._batchSelection = createBatchSelection();
		}
		return batchSelection;
	}

	@NonNull
	private ImportRecordsSelection createBatchSelection()
	{
		if (mainSelection.isEmpty())
		{
			return mainSelection;
		}
		else if (limit.isNoLimit())
		{
			return mainSelection;
		}
		else
		{
			final String importTableName = mainSelection.getImportTableName();
			final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilderOutOfTrx(importTableName)
					.filter(mainSelection.toQueryFilter(importTableName))
					.addNotEqualsFilter(ImportTableDescriptor.COLUMNNAME_I_IsImported, "Y") // not already imported, but accept with Errors because might be corrected in the meantime
					;

			final PInstanceId batchSelectionId = queryBuilder
					.create()
					.setOrderBy(sqlOrderBy)
					.setLimit(limit)
					.createSelection();
			if (batchSelectionId == null)
			{
				return mainSelection.withEmpty(true);
			}
			else
			{
				return mainSelection.withSelectionId(batchSelectionId);
			}
		}
	}

	@Override
	public String getTableName() {return tableDescriptor.getTableName();}

	@Override
	public String getKeyColumnName() {return tableDescriptor.getKeyColumnName();}

	@Override
	public boolean isEmpty()
	{
		return getSelection().isEmpty();
	}

	@Override
	public PInstanceId getMainSelectionId() {return mainSelection.getSelectionId();}

	@Override
	public ActualImportRecordsResult.Error markAsError(
			@NonNull final Set<Integer> importRecordIds,
			@NonNull final Throwable exception)
	{
		final String importTableName = tableDescriptor.getTableName();
		final String keyColumnName = tableDescriptor.getKeyColumnName();

		final ArrayList<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder("UPDATE " + importTableName + " SET ");

		// I_IsImported
		sql.append(ImportTableDescriptor.COLUMNNAME_I_IsImported + "=?");
		sqlParams.add("E");

		// I_ErrorMsg
		final String errorMsg = AdempiereException.extractMessage(exception);
		{
			sql.append(", " + ImportTableDescriptor.COLUMNNAME_I_ErrorMsg + "=I_ErrorMsg || ?");
			sqlParams.add(Check.isEmpty(errorMsg, true) ? "" : errorMsg + ", ");
		}

		// AD_Issue_ID
		final AdIssueId adIssueId = errorManager.createIssue(exception);
		if (tableDescriptor.getAdIssueIdColumnName() != null)
		{
			sql.append(", ").append(tableDescriptor.getAdIssueIdColumnName()).append("=?");
			sqlParams.add(adIssueId);
		}

		//
		// WHERE clause
		sql.append(" WHERE ").append(DB.buildSqlList(keyColumnName, importRecordIds));

		//
		// Execute
		DB.executeUpdateAndThrowExceptionOnFail(
				sql.toString(),
				sqlParams.toArray(),
				ITrx.TRXNAME_ThreadInherited,
				0, // no timeOut
				null);

		CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(
				ITrx.TRXNAME_ThreadInherited,
				CacheInvalidateMultiRequest.fromTableNameAndRecordIds(importTableName, importRecordIds));

		return ActualImportRecordsResult.Error.builder()
				.message(errorMsg)
				.adIssueId(adIssueId)
				.affectedImportRecordsCount(importRecordIds.size())
				.build();
	}

	@Override
	public int deleteImportedRecordsOfMainSelection()
	{
		final String importTableName = mainSelection.getImportTableName();
		final String sql = "DELETE FROM " + importTableName
				+ " WHERE 1=1"
				+ "\n /* standard import filter */ " +/* AND */mainSelection.toSqlWhereClause(importTableName)
				+ "\n /* only imported */ AND " + ImportTableDescriptor.COLUMNNAME_I_IsImported + "='Y'";
		return DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	public int deleteImportRecords(@NonNull final ImportDataDeleteRequest request)
	{
		final String sql = toSqlDelete(request);
		return DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
	}

	private String toSqlDelete(@NonNull final ImportDataDeleteRequest request)
	{
		final String importTableName = request.getImportTableName();
		final StringBuilder sql = new StringBuilder("DELETE FROM " + importTableName + " WHERE 1=1");

		//
		sql.append("\n /* standard import filter */ ").append(/* AND */getSelection().toSqlWhereClause(importTableName));

		//
		// Delete mode filters
		final boolean appendViewSqlWhereClause;
		final ImportDataDeleteMode mode = request.getMode();
		if (ImportDataDeleteMode.ONLY_SELECTED.equals(mode))
		{
			appendViewSqlWhereClause = false;
			if (!Check.isEmpty(request.getSelectionSqlWhereClause(), true))
			{
				sql.append("\n /* selection */ AND ").append(request.getSelectionSqlWhereClause());
			}
		}
		else if (ImportDataDeleteMode.ALL.equals(mode))
		{
			// allow deleting ALL for the current selection
			appendViewSqlWhereClause = true;
		}
		else if (ImportDataDeleteMode.ONLY_IMPORTED.equals(mode))
		{
			appendViewSqlWhereClause = true;
			sql.append("\n /* only imported */ AND ").append(ImportTableDescriptor.COLUMNNAME_I_IsImported).append("='Y'");
		}
		else
		{
			throw new AdempiereException("Unknown mode: " + mode);
		}

		//
		// View filter
		if (appendViewSqlWhereClause && !Check.isBlank(request.getViewSqlWhereClause()))
		{
			sql.append("\n /* view */ AND (").append(request.getViewSqlWhereClause()).append(")");
		}

		return sql.toString();
	}

	@Override
	public void clearErrorsForMainSelection()
	{
		final String importTableName = mainSelection.getImportTableName();

		final String sql = "UPDATE " + importTableName
				+ " SET "
				+ "  " + ImportTableDescriptor.COLUMNNAME_I_IsImported + "= 'N' "
				+ ", " + ImportTableDescriptor.COLUMNNAME_I_ErrorMsg + " = ' ' "
				+ "\n WHERE "
				+ "\n COALESCE(" + ImportTableDescriptor.COLUMNNAME_I_IsImported + ", 'N') <> 'Y' "
				+ "\n " +/* AND */ mainSelection.toSqlWhereClause();
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, null, ITrx.TRXNAME_ThreadInherited);
		loggable.addLog("Cleared errors for {} records", no);

		if (no > 0)
		{
			this._batchSelection = null;
		}
	}

	@Override
	public void resetStandardColumns()
	{
		final ImportRecordsSelection selection = getSelection();
		final String importTableName = selection.getImportTableName();

		final ArrayList<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder("UPDATE " + importTableName
				+ " SET "
				+ "  " + ImportTableDescriptor.COLUMNNAME_AD_Client_ID + " = COALESCE(AD_Client_ID, " + clientId.getRepoId() + ")"
				+ ", " + ImportTableDescriptor.COLUMNNAME_AD_Org_ID + "=COALESCE(AD_Org_ID, " + OrgId.ANY.getRepoId() + ")"
				+ ", " + ImportTableDescriptor.COLUMNNAME_IsActive + "=COALESCE(IsActive, 'Y')"
				+ ", Created = COALESCE (Created, now())"
				+ ", CreatedBy = COALESCE (CreatedBy, 0)"
				+ ", Updated = COALESCE (Updated, now())"
				+ ", UpdatedBy = COALESCE (UpdatedBy, 0)"
				+ ", " + ImportTableDescriptor.COLUMNNAME_I_ErrorMsg + " = ' ' "
				+ ", " + ImportTableDescriptor.COLUMNNAME_I_IsImported + "= 'N' ");

		if (importRecordDefaultValues != null && !importRecordDefaultValues.isEmpty())
		{
			for (final Map.Entry<String, Object> defaultValueEntry : importRecordDefaultValues.entrySet())
			{
				final String columnName = defaultValueEntry.getKey();
				final Object value = defaultValueEntry.getValue();

				sql.append("\n, ").append(columnName).append("=COALESCE(").append(columnName).append(", ?)");
				sqlParams.add(value);
			}
		}

		sql.append("\n WHERE ")
				.append("\n (" + ImportTableDescriptor.COLUMNNAME_I_IsImported + "<>'Y' OR " + ImportTableDescriptor.COLUMNNAME_I_IsImported + " IS NULL) ")
				.append("\n ").append(/* AND */ selection.toSqlWhereClause());
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(),
				sqlParams.toArray(),
				ITrx.TRXNAME_ThreadInherited);
		loggable.addLog("{} records were reset to standard default values", no);
	}

	@Override
	public Iterator<ImportRecordType> retrieveRecordsToImport()
	{
		final Properties ctx = Env.getCtx();

		final ImportRecordsSelection selection = getSelection();
		final String importTableName = selection.getImportTableName();
		final String sql = "SELECT * "
				+ " FROM " + importTableName
				+ " WHERE "
				+ " " + ImportTableDescriptor.COLUMNNAME_I_IsImported + "='N' "
				+ " AND " + ImportTableDescriptor.COLUMNNAME_IsActive + "='Y' "
				+ " " + /* and */ selection.toSqlWhereClause(importTableName)
				+ " ORDER BY " + sqlOrderBy;

		return IteratorUtils.asIterator(new AbstractPreparedStatementBlindIterator<ImportRecordType>()
		{
			@Override
			protected PreparedStatement createPreparedStatement() {return DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);}

			@Override
			protected ImportRecordType fetch(final ResultSet rs) throws SQLException
			{
				return recordLoader.retrieveImportRecord(ctx, rs);
			}
		});
	}

	@Override
	public void runSQLAfterRowImport(@NonNull final ImportRecordType importRecord)
	{
		final List<DBFunction> functions = dbFunctions.getAvailableAfterRowFunctions();
		if (functions.isEmpty())
		{
			return;
		}

		final DataImportConfigId dataImportConfigId = extractDataImportConfigId(importRecord).orElse(null);
		final Optional<Integer> recordId = InterfaceWrapperHelper.getValue(importRecord, getKeyColumnName());
		functions.forEach(function -> {
			try
			{
				DBFunctionHelper.doDBFunctionCall(function, dataImportConfigId, recordId.orElse(0));
			}
			catch (final Exception ex)
			{
				final AdempiereException metasfreshEx = AdempiereException.wrapIfNeeded(ex);
				final AdIssueId issueId = errorManager.createIssue(metasfreshEx);
				loggable.addLog("Failed running " + function + ": " + AdempiereException.extractMessage(metasfreshEx) + " (AD_Issue_ID=" + issueId.getRepoId() + ")");
				throw metasfreshEx;
			}
		});
	}

	@Override
	public void runSQLAfterAllImport()
	{
		final List<DBFunction> functions = dbFunctions.getAvailableAfterAllFunctions();
		if (functions.isEmpty())
		{
			return;
		}

		final DataImportRunId dataImportRunId = getDataImportRunIdOfImportedRecords();
		functions.forEach(function -> {
			try
			{
				DBFunctionHelper.doDBFunctionCall(function, dataImportRunId);
			}
			catch (Exception ex)
			{
				final AdempiereException metasfreshEx = AdempiereException.wrapIfNeeded(ex);
				final AdIssueId issueId = errorManager.createIssue(metasfreshEx);
				loggable.addLog("Failed running " + function + ": " + AdempiereException.extractMessage(metasfreshEx) + " (AD_Issue_ID=" + issueId.getRepoId() + ")");
				throw metasfreshEx;
			}
		});
	}

	private DataImportRunId getDataImportRunIdOfImportedRecords()
	{
		final ImportRecordsSelection selection = getSelection();
		final String importTableName = selection.getImportTableName();

		return DataImportRunId.ofRepoIdOrNull(
				DB.getSQLValueEx(
						ITrx.TRXNAME_ThreadInherited,
						"SELECT " + ImportTableDescriptor.COLUMNNAME_C_DataImport_Run_ID
								+ " FROM " + importTableName
								+ " WHERE " + ImportTableDescriptor.COLUMNNAME_I_IsImported + "='Y' "
								+ " " + selection.toSqlWhereClause(importTableName)
				)
		);
	}

	private Optional<DataImportConfigId> extractDataImportConfigId(@NonNull final ImportRecordType importRecord)
	{
		if (tableDescriptor.getDataImportConfigIdColumnName() == null)
		{
			return Optional.empty();
		}

		final Optional<Integer> value = InterfaceWrapperHelper.getValue(importRecord, tableDescriptor.getDataImportConfigIdColumnName());
		return value.map(DataImportConfigId::ofRepoIdOrNull);
	}
}
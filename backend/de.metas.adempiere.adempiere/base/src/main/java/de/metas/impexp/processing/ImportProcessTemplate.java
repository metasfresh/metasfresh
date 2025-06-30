package de.metas.impexp.processing;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.error.IErrorManager;
import de.metas.impexp.ActualImportRecordsResult;
import de.metas.impexp.ImportRecordsAsyncExecutor;
import de.metas.impexp.ImportRecordsRequest;
import de.metas.impexp.format.ImportTableDescriptor;
import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.ImportProcessResult.ImportProcessResultCollector;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder.OnItemErrorPolicy;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.TrxItemChunkProcessorAdapter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.Params;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReferenceSelection;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Base implementation of {@link IImportProcess}.
 * <p>
 * Implementors shall extend this class instead of implementing {@link IImportProcess}.
 *
 * @param <ImportRecordType> import table model (e.g. I_I_BPartner).
 * @author tsa
 */
public abstract class ImportProcessTemplate<ImportRecordType, ImportGroupKey>
		implements IImportProcess<ImportRecordType>, ImportRecordLoader<ImportRecordType>
{
	// services
	@NonNull private final transient Logger logger = LogManager.getLogger(getClass());
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull final ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);
	@NonNull private final DBFunctionsRepository dbFunctionsRepo = SpringContextHolder.instance.getBean(DBFunctionsRepository.class);
	@NonNull private final ImportTableDescriptorRepository importTableDescriptorRepo = SpringContextHolder.instance.getBean(ImportTableDescriptorRepository.class);
	@NonNull private final ImportRecordsAsyncExecutor asyncImportScheduler = SpringContextHolder.instance.getBean(ImportRecordsAsyncExecutor.class);

	private static final int LOG_PROGRESS_EACH_N_ROWS = 100;

	//
	// Parameters
	@NonNull private Properties _ctx = Env.getCtx();
	@Nullable private ClientId clientId;
	private Boolean validateOnly;
	private boolean completeDocuments;
	@NonNull private Params _parameters = Params.EMPTY;
	@NonNull private ILoggable loggable = Loggables.getLoggableOrLogger(logger, Level.INFO);
	@Nullable private TableRecordReferenceSelection selectedRecordRefs;
	private boolean async;
	@NonNull private QueryLimit limit = QueryLimit.NO_LIMIT;
	@Nullable private UserId notifyUserId;

	//
	// State
	private ImportProcessResultCollector resultCollector;
	private ImportSource<ImportRecordType> _importSource; // lazy
	private int countImportRecordsConsideredLastLogged = 0;

	private void assertNotStarted()
	{
		if (resultCollector != null)
		{
			throw new AdempiereException("Cannot change parameters after process is started: " + this);
		}
	}

	@Override
	public final ImportProcessTemplate<ImportRecordType, ImportGroupKey> setCtx(final Properties ctx)
	{
		assertNotStarted();

		this._ctx = ctx;
		return this;
	}

	public final Properties getCtx() {return Check.assumeNotNull(_ctx, "_ctx not null");}

	@Override
	public final ImportProcessTemplate<ImportRecordType, ImportGroupKey> clientId(@NonNull final ClientId clientId)
	{
		assertNotStarted();

		this.clientId = clientId;
		return this;
	}

	private ClientId getClientId()
	{
		if (clientId != null)
		{
			return clientId;
		}

		return Env.getClientId(getCtx());
	}

	@Override
	public final ImportProcessTemplate<ImportRecordType, ImportGroupKey> setParameters(@NonNull final IParams params)
	{
		assertNotStarted();

		this._parameters = Params.copyOf(params);
		return this;
	}

	protected final Params getParameters()
	{
		return _parameters;
	}

	@Override
	public final ImportProcessTemplate<ImportRecordType, ImportGroupKey> validateOnly(final boolean validateOnly)
	{
		assertNotStarted();

		this.validateOnly = validateOnly;
		return this;
	}

	@Override
	public final ImportProcessTemplate<ImportRecordType, ImportGroupKey> completeDocuments(final boolean completeDocuments)
	{
		assertNotStarted();

		this.completeDocuments = completeDocuments;
		return this;
	}

	@Override
	public final ImportProcessTemplate<ImportRecordType, ImportGroupKey> setLoggable(@NonNull final ILoggable loggable)
	{
		assertNotStarted();

		this.loggable = loggable;
		return this;
	}

	@NotNull
	protected final ILoggable getLoggable()
	{
		return loggable;
	}

	private boolean isValidateOnly()
	{
		final Boolean validateOnly = this.validateOnly;
		if (validateOnly != null)
		{
			return validateOnly;
		}

		return getParameters().getParameterAsBool(PARAM_IsValidateOnly);
	}

	private boolean isImportData()
	{
		return !isValidateOnly();
	}

	protected final boolean isCompleteDocuments()
	{
		if (this.completeDocuments)
		{
			return true;
		}
		return getParameters().getParameterAsBool(PARAM_IsDocComplete);
	}

	@Override
	public final ImportProcessTemplate<ImportRecordType, ImportGroupKey> selectedRecords(@NonNull final TableRecordReferenceSet selectedRecordRefs)
	{
		assertNotStarted();

		if (selectedRecordRefs.isEmpty())
		{
			throw new AdempiereException("No import records: " + selectedRecordRefs);
		}

		this.selectedRecordRefs = TableRecordReferenceSelection.ofRecordRefs(selectedRecordRefs);
		return this;
	}

	@Override
	public final ImportProcessTemplate<ImportRecordType, ImportGroupKey> selectedRecords(@Nullable final PInstanceId selectionId)
	{
		assertNotStarted();
		this.selectedRecordRefs = selectionId != null ? TableRecordReferenceSelection.ofSelectionId(selectionId) : null;
		return this;
	}

	@Override
	public final ImportProcessTemplate<ImportRecordType, ImportGroupKey> async(final boolean async)
	{
		assertNotStarted();
		this.async = async;
		return this;
	}

	@Override
	public final ImportProcessTemplate<ImportRecordType, ImportGroupKey> limit(@NonNull QueryLimit limit)
	{
		assertNotStarted();
		this.limit = limit;
		return this;
	}

	@Override
	public final ImportProcessTemplate<ImportRecordType, ImportGroupKey> notifyUserId(@Nullable UserId notifyUserId)
	{
		assertNotStarted();
		this.notifyUserId = notifyUserId;
		return this;
	}

	protected final boolean isInsertOnly()
	{
		return getParameters().getParameterAsBool(PARAM_IsInsertOnly);
	}

	private boolean isDeleteOldImported()
	{
		return getParameters().getParameterAsBool(PARAM_DeleteOldImported);
	}

	private ImportSource<ImportRecordType> getImportSource()
	{
		ImportSource<ImportRecordType> importSource = this._importSource;
		if (importSource == null)
		{
			importSource = this._importSource = createInputSource();
		}
		return importSource;
	}

	@VisibleForTesting
	protected ImportSource<ImportRecordType> createInputSource()
	{
		final PInstanceId mainSelectionId;
		if (selectedRecordRefs != null)
		{
			mainSelectionId = selectedRecordRefs.map(new TableRecordReferenceSelection.CaseMapper<PInstanceId>()
			{
				@Override
				public PInstanceId selectionId(@NonNull final PInstanceId selectionId) {return selectionId;}

				@Override
				public PInstanceId recordRefs(@NonNull final TableRecordReferenceSet recordRefs)
				{
					return DB.createT_Selection(recordRefs, ITrx.TRXNAME_None);
				}
			});
		}
		else
		{
			mainSelectionId = getParameters().getParameterAsId(PARAM_Selection_ID, PInstanceId.class);
		}

		final String importTableName = getImportTableName();
		return SqlImportSource.<ImportRecordType>builder()
				.queryBL(queryBL)
				.errorManager(errorManager)
				.loggable(loggable)
				.tableDescriptor(importTableDescriptorRepo.getByTableName(importTableName))
				.recordLoader(this)
				.dbFunctions(dbFunctionsRepo.retrieveByTableName(importTableName))
				.importRecordDefaultValues(getImportTableDefaultValues())
				.importOrderBySql(getImportOrderBySql())
				.clientId(getClientId())
				.selectionId(mainSelectionId)
				.limit(limit)
				.build();
	}

	protected abstract String getTargetTableName();

	protected final ImportRecordsSelection getImportRecordsSelection() {return getImportSource().getSelection();}

	protected final ImportProcessResultCollector getResultCollector()
	{
		if (resultCollector == null)
		{
			throw new AdempiereException("Import not started yet");
		}
		return resultCollector;
	}

	private boolean isRunningInAsyncWorkpackage() {return limit.isLimited();}

	@Override
	public final ImportProcessResult run()
	{
		if (resultCollector != null)
		{
			throw new AdempiereException("Process already started: " + this);
		}
		resultCollector = ImportProcessResult.newCollector(getTargetTableName()).importTableName(getImportTableName());

		// Assume we are not running in another transaction because that could introduce deadlocks,
		// because we are creating the transactions here.
		trxManager.assertThreadInheritedTrxNotExists();

		loggable.addLog("AD_Client_ID: " + getClientId().getRepoId());
		loggable.addLog("ValidateOnly: " + validateOnly);
		loggable.addLog("CompleteDocuments: " + completeDocuments);
		loggable.addLog("SelectedRecordRefs: " + selectedRecordRefs);
		loggable.addLog("Async: " + async);
		loggable.addLog("Limit: " + limit);
		loggable.addLog("NotifyUserId: " + UserId.toRepoId(notifyUserId));

		//
		// Case: asked to enqueue the data to import for asynchronous processing
		if (async)
		{
			prepareBeforeImporting();
			scheduleAsync();
		}
		//
		// Case: called by asynchronous processor to import a batch of data
		else if (isRunningInAsyncWorkpackage())
		{
			// NOTE: in this case we assume the data preparation was already performed for the main selection before enqueuing
			runNow();
		}
		//
		// Case: called to directly import the data now
		else
		{
			prepareBeforeImporting();
			runNow();
		}

		final ImportProcessResult result = resultCollector.toResult();
		loggable.addLog(result.getSummary());
		return result;
	}

	private void scheduleAsync()
	{
		final ImportSource<ImportRecordType> importSource = getImportSource();
		importSource.clearErrorsForMainSelection();

		asyncImportScheduler.schedule(ImportRecordsRequest.builder()
				.importTableName(getImportTableName())
				.clientId(getClientId())
				.selectionId(getImportSource().getMainSelectionId())
				.notifyUserId(notifyUserId)
				.limit(limit)
				.completeDocuments(isCompleteDocuments())
				.additionalParameters(getParameters())
				.build());

		loggable.addLog("Scheduled next workpackage");
	}

	private void prepareBeforeImporting()
	{
		final ImportSource<ImportRecordType> importSource = getImportSource();

		if (isDeleteOldImported())
		{
			int countDeleted = importSource.deleteImportedRecordsOfMainSelection();
			getResultCollector().setCountImportRecordsDeleted(countDeleted);
		}

		importSource.clearErrorsForMainSelection();
	}

	private void runNow()
	{
		final ImportSource<ImportRecordType> importSource = getImportSource();
		if (importSource.isEmpty())
		{
			loggable.addLog("No more records no import");
			return;
		}

		importSource.resetStandardColumns();
		updateAndValidateImportRecords();
		importDataIfNeeded();

		if (limit.isLimited() && limit.isLessThanOrEqualTo(resultCollector.getCountImportRecordsConsidered()))
		{
			scheduleAsync();
		}
		else
		{
			loggable.addLog("Import done");
		}
	}

	@Override
	public final int deleteImportRecords(@NonNull final ImportDataDeleteRequest request)
	{
		return getImportSource().deleteImportRecords(request);
	}

	/**
	 * @return a map of ImportTable_ColumnName to DefaultValue, to be used when the value is null
	 */
	protected Map<String, Object> getImportTableDefaultValues()
	{
		return ImmutableMap.of();
	}

	private void updateAndValidateImportRecords()
	{
		ModelValidationEngine.get().fireImportValidate(this, null, null, IImportInterceptor.TIMING_BEFORE_VALIDATE);
		trxManager.runInNewTrx(this::updateAndValidateImportRecordsImpl);
		ModelValidationEngine.get().fireImportValidate(this, null, null, IImportInterceptor.TIMING_AFTER_VALIDATE);
		loggable.addLog("Records were updated and validated - " + resultCollector.toResult().getSummary());
	}

	/**
	 * Prepare data import: fill missing fields (if possible) and validate the records.
	 */
	protected abstract void updateAndValidateImportRecordsImpl();

	protected abstract ImportGroupKey extractImportGroupKey(final ImportRecordType importRecord);

	/**
	 * Actual data import.
	 */
	private void importDataIfNeeded()
	{
		if (!isImportData())
		{
			loggable.addLog("Skip importing the data");
			return;
		}

		//
		// Actual import (allow the method to manage the transaction)
		newImportRecordsExecutor().execute(retrieveRecordsToImport());
		logCurrentProgress(true);

		//
		// run whatever after import code
		afterImport();

		//
		getImportSource().runSQLAfterAllImport();

	}

	private ITrxItemProcessorExecutor<ImportRecordType, Void> newImportRecordsExecutor()
	{
		final IMutable<Object> stateHolder = new Mutable<>();
		final Mutable<ImportGroup<ImportGroupKey, ImportRecordType>> currentImportGroupHolder = new Mutable<>();

		return trxItemProcessorExecutorService
				.<ImportRecordType, Void>createExecutor()
				.setOnItemErrorPolicy(OnItemErrorPolicy.CancelChunkAndRollBack)
				.setExceptionHandler(new FailTrxItemExceptionHandler()
				{
					@Override
					public void onCompleteChunkError(final Throwable ex)
					{
						// do nothing.
						// the error will be handled in the "afterCompleteChunkError" method
					}

					@Override
					public void afterCompleteChunkError(final Throwable ex)
					{
						final ImportGroup<ImportGroupKey, ImportRecordType> currentGroup = currentImportGroupHolder.getValueNotNull();
						onImportError(currentGroup, ex);
					}
				})
				.setProcessor(new TrxItemChunkProcessorAdapter<ImportRecordType, Void>()
				{
					@Override
					public void newChunk(final ImportRecordType importRecord)
					{
						final ImportGroupKey groupKey = extractImportGroupKey(importRecord);
						currentImportGroupHolder.setValue(ImportGroup.newInstance(groupKey));
					}

					@Override
					public boolean isSameChunk(final ImportRecordType importRecord)
					{
						final ImportGroup<ImportGroupKey, ImportRecordType> currentGroup = currentImportGroupHolder.getValueNotNull();
						final ImportGroupKey groupKey = extractImportGroupKey(importRecord);
						return Objects.equals(currentGroup.getGroupKey(), groupKey);
					}

					@Override
					public void process(final ImportRecordType importRecord)
					{
						final ImportGroup<ImportGroupKey, ImportRecordType> currentGroup = currentImportGroupHolder.getValueNotNull();
						currentGroup.addImportRecord(importRecord);
					}

					@Override
					public void completeChunk()
					{
						final ImportGroup<ImportGroupKey, ImportRecordType> currentGroup = currentImportGroupHolder.getValueNotNull();
						importGroup(currentGroup, stateHolder);
					}
				})
				.build();
	}

	@VisibleForTesting
	protected Iterator<ImportRecordType> retrieveRecordsToImport()
	{
		final ImportSource<ImportRecordType> importSource = getImportSource();
		return importSource.retrieveRecordsToImport();
	}

	private void importGroup(
			@NonNull final ImportGroup<ImportGroupKey, ImportRecordType> importGroup,
			@NonNull final IMutable<Object> stateHolder)
	{
		// shall not happen
		if (importGroup.isEmpty())
		{
			logger.warn("Skip importing empty group: {}", importGroup);
			return;
		}

		final ImportProcessResultCollector overallResultCollector = getResultCollector();
		try
		{
			overallResultCollector.addCountImportRecordsConsidered(importGroup.size());

			final ImportGroupResult importGroupResult = importRecords(
					importGroup.getGroupKey(),
					importGroup.getImportRecords(),
					stateHolder);

			onImportSuccess(importGroup);

			overallResultCollector.addInsertsIntoTargetTable(importGroupResult.getCountInserted());
			overallResultCollector.addUpdatesIntoTargetTable(importGroupResult.getCountUpdated());
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
		finally
		{
			logCurrentProgress(false);
		}
	}

	protected abstract String getImportOrderBySql();

	protected abstract ImportGroupResult importRecords(
			final ImportGroupKey groupKey,
			final List<ImportRecordType> importRecords,
			final IMutable<Object> stateHolder) throws Exception;

	private void onImportSuccess(@NonNull final ImportGroup<ImportGroupKey, ImportRecordType> importGroup)
	{
		final ImportSource<ImportRecordType> importSource = getImportSource();

		final ImmutableList<ImportRecordType> importRecords = importGroup.getImportRecords();
		for (final ImportRecordType importRecord : importRecords)
		{
			markImported(importRecord);
		}

		// run after markImported because we need the recordId saved
		for (final ImportRecordType importRecord : importRecords)
		{
			importSource.runSQLAfterRowImport(importRecord);
		}
	}

	protected void markImported(final ImportRecordType importRecord)
	{
		InterfaceWrapperHelper.setValue(importRecord, ImportTableDescriptor.COLUMNNAME_I_IsImported, true);
		InterfaceWrapperHelper.setValue(importRecord, ImportTableDescriptor.COLUMNNAME_Processed, true);
		InterfaceWrapperHelper.setValue(importRecord, ImportTableDescriptor.COLUMNNAME_Processing, false);
		InterfaceWrapperHelper.save(importRecord);
	}

	protected void afterImport()
	{
	}

	private void onImportError(
			@NonNull final ImportGroup<ImportGroupKey, ImportRecordType> importGroup,
			@NonNull final Throwable exception)
	{
		final ImportSource<ImportRecordType> importSource = getImportSource();
		final ActualImportRecordsResult.Error error = importSource.markAsError(importGroup.getImportRecordIds(), exception);

		importGroup.markImportRecordsAsStale(); // just in case some BL wants to get values from it
		getResultCollector().actualImportError(error);

	}

	private void logCurrentProgress(boolean force)
	{
		final int countImportRecordsConsidered = getResultCollector().getCountImportRecordsConsidered();
		if (force
				|| (countImportRecordsConsidered - countImportRecordsConsideredLastLogged) >= LOG_PROGRESS_EACH_N_ROWS)
		{
			loggable.addLog("Progress: " + getResultCollector().toResult().getSummary());
			loggable.flush();
			countImportRecordsConsideredLastLogged = countImportRecordsConsidered;
		}
	}
}

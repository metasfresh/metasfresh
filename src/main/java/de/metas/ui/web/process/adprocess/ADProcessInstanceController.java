package de.metas.ui.web.process.adprocess;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.RecordZoomWindowFinder;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutionResult.RecordsToOpen;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.ProcessInstanceResult.OpenIncludedViewAction;
import de.metas.ui.web.process.ProcessInstanceResult.OpenReportAction;
import de.metas.ui.web.process.ProcessInstanceResult.OpenSingleDocument;
import de.metas.ui.web.process.ProcessInstanceResult.OpenViewAction;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.exceptions.ProcessExecutionException;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentSaveStatus;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * WEBUI AD_Process based process instance controller
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */final class ADProcessInstanceController implements IProcessInstanceController
{
	private static final transient Logger logger = LogManager.getLogger(ADProcessInstanceController.class);

	private final DocumentId instanceId;

	private final ProcessDescriptor processDescriptor;
	private final Document parameters;
	private final Object processClassInstance;

	private final ViewId viewId;

	private final DocumentPath contextSingleDocumentPath;

	private boolean executed = false;
	private ProcessInstanceResult executionResult;

	private final ReentrantReadWriteLock readwriteLock;

	/** New instance constructor */
	@Builder
	private ADProcessInstanceController(
			@NonNull final ProcessDescriptor processDescriptor,
			@NonNull final DocumentId instanceId,
			@Nullable final Document parameters,
			@Nullable final Object processClassInstance,
			@Nullable final DocumentPath contextSingleDocumentPath,
			//
			@Nullable final ViewId viewId)
	{
		this.processDescriptor = processDescriptor;
		this.instanceId = instanceId;
		this.parameters = parameters;
		this.processClassInstance = processClassInstance;

		this.viewId = viewId;

		this.contextSingleDocumentPath = contextSingleDocumentPath;

		executed = false;
		executionResult = null;

		readwriteLock = new ReentrantReadWriteLock();
	}

	/** Copy constructor */
	private ADProcessInstanceController(final ADProcessInstanceController from, final CopyMode copyMode, final IDocumentChangesCollector changesCollector)
	{
		instanceId = from.instanceId;

		processDescriptor = from.processDescriptor;
		parameters = from.parameters.copy(copyMode, changesCollector);
		processClassInstance = from.processClassInstance;

		viewId = from.viewId;

		contextSingleDocumentPath = from.contextSingleDocumentPath;

		executed = from.executed;
		executionResult = from.executionResult;

		readwriteLock = from.readwriteLock; // always share
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("AD_PInstance_ID", instanceId)
				.add("executed", "executed")
				.add("executionResult", executionResult)
				.add("processDescriptor", processDescriptor)
				.toString();
	}

	private ProcessDescriptor getDescriptor()
	{
		return processDescriptor;
	}

	@Override
	public DocumentId getInstanceId()
	{
		return instanceId;
	}

	public ViewId getViewId()
	{
		return viewId;
	}

	private Document getParametersDocument()
	{
		return parameters;
	}

	@Override
	public Collection<IDocumentFieldView> getParameters()
	{
		return parameters.getFieldViews();
	}

	public ADProcessInstanceController copy(final CopyMode copyMode, final IDocumentChangesCollector changesCollector)
	{
		return new ADProcessInstanceController(this, copyMode, changesCollector);
	}

	public ADProcessInstanceController bindContextSingleDocumentIfPossible(@NonNull final DocumentCollection documentsCollection)
	{
		if (contextSingleDocumentPath == null)
		{
			return this;
		}
		if (!documentsCollection.isWindowIdSupported(contextSingleDocumentPath.getWindowIdOrNull()))
		{
			return this;
		}

		final Document contextSingleDocument = documentsCollection.getDocumentReadonly(contextSingleDocumentPath);
		getParametersDocument().setShadowParentDocumentEvaluatee(contextSingleDocument.asEvaluatee());

		return this;
	}

	public IAutoCloseable activate()
	{
		return JavaProcess.temporaryChangeCurrentInstance(processClassInstance);
	}

	@Override
	public LookupValuesList getParameterLookupValues(final String parameterName)
	{
		return parameters.getFieldLookupValues(parameterName);
	}

	@Override
	public LookupValuesList getParameterLookupValuesForQuery(final String parameterName, final String query)
	{
		return parameters.getFieldLookupValuesForQuery(parameterName, query);
	}

	@Override
	public void processParameterValueChanges(final List<JSONDocumentChangedEvent> events, final ReasonSupplier reason)
	{
		assertNotExecuted();
		parameters.processValueChanges(events, reason);
	}

	@Override
	public ProcessInstanceResult getExecutionResult()
	{
		final ProcessInstanceResult executionResult = this.executionResult;
		if (executionResult == null)
		{
			throw new AdempiereException("Process instance does not have an execution result yet: " + this);
		}
		return executionResult;
	}

	private boolean isExecuted()
	{
		return executed;
	}

	/* package */ final void assertNotExecuted()
	{
		if (isExecuted())
		{
			throw new AdempiereException("Process already executed");
		}
	}

	@Override
	public ProcessInstanceResult startProcess(final IViewsRepository viewsRepo, final DocumentCollection documentsCollection)
	{
		assertNotExecuted();

		//
		// Make sure it's saved in database
		if (!saveIfValidAndHasChanges(true))
		{
			// shall not happen because the method throws the exception in case of failure
			throw new ProcessExecutionException("Instance could not be saved because it's not valid");
		}

		//
		executionResult = executeADProcess(viewsRepo, documentsCollection);
		if (executionResult.isSuccess())
		{
			executed = false;
		}
		return executionResult;
	}

	private final ProcessInstanceResult executeADProcess(final IViewsRepository viewsRepo, final DocumentCollection documentsCollection)
	{
		//
		// Create the process info and execute the process synchronously
		final Properties ctx = Env.getCtx(); // We assume the right context was already used when the process was loaded
		final String adLanguage = Env.getAD_Language(ctx);
		final ProcessExecutor processExecutor = ProcessInfo.builder()
				.setCtx(ctx)
				.setCreateTemporaryCtx()
				.setAD_PInstance_ID(getInstanceId().toInt())
				.setTitle(getDescriptor().getCaption().translate(adLanguage))
				.setPrintPreview(true)
				.setJRDesiredOutputType(OutputType.PDF)
				//
				// Execute the process/report
				.buildAndPrepareExecution()
				.onErrorThrowException() // throw exception directly... this will allow the original exception (including exception params) to be sent back to frontend
				.executeSync();
		final ProcessExecutionResult processExecutionResult = processExecutor.getResult();

		invalidateDocumentsAndViews(processExecutionResult, viewsRepo, documentsCollection);

		//
		// Build and return the execution result
		{
			String summary = processExecutionResult.getSummary();
			if (Check.isEmpty(summary, true) || JavaProcess.MSG_OK.equals(summary))
			{
				// hide summary if empty or MSG_OK (which is the most used non-message)
				summary = null;
			}

			final ProcessInstanceResult.Builder resultBuilder = ProcessInstanceResult.builder(DocumentId.of(processExecutionResult.getAD_PInstance_ID()))
					.setSummary(summary)
					.setError(processExecutionResult.isError());

			//
			// Create result post process actions
			{
				final File reportTempFile = saveReportToDiskIfAny(processExecutionResult);
				final RecordsToOpen recordsToOpen = processExecutionResult.getRecordsToOpen();

				//
				// Open report
				if (reportTempFile != null)
				{
					resultBuilder.setAction(OpenReportAction.builder()
							.filename(processExecutionResult.getReportFilename())
							.contentType(processExecutionResult.getReportContentType())
							.tempFile(reportTempFile)
							.build());
				}
				//
				// Open view
				else if (recordsToOpen != null && recordsToOpen.getTarget() == OpenTarget.GridView)
				{
					final Set<DocumentPath> referencingDocumentPaths = extractReferencingDocumentPaths(processExecutor.getProcessInfo());
					final String parentViewIdStr = processExecutionResult.getWebuiViewId();
					final ViewId parentViewId = parentViewIdStr != null ? ViewId.ofViewIdString(parentViewIdStr) : null;
					final CreateViewRequest viewRequest = createViewRequest(recordsToOpen, referencingDocumentPaths, parentViewId);

					final IView view = viewsRepo.createView(viewRequest);
					resultBuilder.setAction(OpenViewAction.builder()
							.viewId(view.getViewId())
							.build());
				}
				//
				// Open included view
				else if (processExecutionResult.getWebuiIncludedViewIdToOpen() != null)
				{
					resultBuilder.setAction(OpenIncludedViewAction.builder()
							.viewId(ViewId.ofViewIdString(processExecutionResult.getWebuiIncludedViewIdToOpen()))
							.profileId(ViewProfileId.fromJson(processExecutionResult.getWebuiViewProfileId()))
							.build());
				}
				//
				// Open single document
				else if (recordsToOpen != null && recordsToOpen.getTarget() == OpenTarget.SingleDocument)
				{
					final DocumentPath documentPath = extractSingleDocumentPath(recordsToOpen);
					resultBuilder.setAction(OpenSingleDocument.builder()
							.documentPath(documentPath)
							.modal(false)
							.build());
				}
				//
				// Open single document
				else if (recordsToOpen != null && recordsToOpen.getTarget() == OpenTarget.SingleDocumentModal)
				{
					final DocumentPath documentPath = extractSingleDocumentPath(recordsToOpen);
					resultBuilder.setAction(OpenSingleDocument.builder()
							.documentPath(documentPath)
							.modal(true)
							.build());
				}
			}

			final ProcessInstanceResult result = resultBuilder.build();
			return result;
		}
	}

	private void invalidateDocumentsAndViews(final ProcessExecutionResult processExecutionResult,
			final IViewsRepository viewsRepo,
			final DocumentCollection documentsCollection)
	{
		final Supplier<IView> viewSupplier = Suppliers.memoize(() -> {
			final ViewId viewId = getViewId();
			if (viewId == null)
			{
				return null;
			}

			final IView view = viewsRepo.getViewIfExists(viewId);
			if (view == null)
			{
				logger.warn("No view found for {}. View invalidation will be skipped for {}", viewId, processExecutionResult);
			}
			return view;
		});

		//
		// Refresh all
		boolean viewInvalidateAllCalled = false;
		if (processExecutionResult.isRefreshAllAfterExecution() && viewSupplier.get() != null)
		{
			viewSupplier.get().invalidateAll();
			viewInvalidateAllCalled = true;
		}

		//
		// Refresh required document
		final TableRecordReference recordToRefresh = processExecutionResult.getRecordToRefreshAfterExecution();
		if (recordToRefresh != null)
		{
			documentsCollection.invalidateDocumentByRecordId(recordToRefresh.getTableName(), recordToRefresh.getRecord_ID());

			if (!viewInvalidateAllCalled && viewSupplier.get() != null)
			{
				viewSupplier.get().notifyRecordsChanged(ImmutableSet.of(recordToRefresh));
			}
		}
	}

	private static final File saveReportToDiskIfAny(final ProcessExecutionResult processExecutionResult)
	{
		//
		// If we are not dealing with a report, stop here
		final byte[] reportData = processExecutionResult.getReportData();
		if (reportData == null || reportData.length <= 0)
		{
			return null;
		}

		//
		// Create report temporary file
		File reportFile = null;
		try
		{
			final String reportFilePrefix = "report_" + processExecutionResult.getAD_PInstance_ID() + "_";

			final String reportContentType = processExecutionResult.getReportContentType();
			final String reportFileExtension = MimeType.getExtensionByType(reportContentType);
			final String reportFileSuffix = Check.isEmpty(reportFileExtension, true) ? "" : "." + reportFileExtension.trim();
			reportFile = File.createTempFile(reportFilePrefix, reportFileSuffix);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed creating report temporary file " + reportFile);
		}

		//
		// Write report data to our temporary report file
		Util.writeBytes(reportFile, reportData);

		return reportFile;
	}

	private static final CreateViewRequest createViewRequest(final RecordsToOpen recordsToOpen, final Set<DocumentPath> referencingDocumentPaths, final ViewId parentViewId)
	{
		final List<TableRecordReference> recordRefs = recordsToOpen.getRecords();
		if (recordRefs.isEmpty())
		{
			return null; // shall not happen
		}

		final WindowId windowId_Override = WindowId.fromNullableJson(recordsToOpen.getWindowIdString()); // optional

		//
		// Create view create request builders from current records
		final Map<WindowId, CreateViewRequest.Builder> viewRequestBuilders = new HashMap<>();
		for (final TableRecordReference recordRef : recordRefs)
		{
			final WindowId recordWindowId = windowId_Override != null ? windowId_Override : WindowId.ofIntOrNull(RecordZoomWindowFinder.findAD_Window_ID(recordRef));
			final CreateViewRequest.Builder viewRequestBuilder = viewRequestBuilders.computeIfAbsent(recordWindowId, key -> CreateViewRequest.builder(recordWindowId, JSONViewDataType.grid));

			viewRequestBuilder.addFilterOnlyId(recordRef.getRecord_ID());
		}
		// If there is no view create request builder there stop here (shall not happen)
		if (viewRequestBuilders.isEmpty())
		{
			return null;
		}

		//
		// Create the view create request from first builder that we have.
		if (viewRequestBuilders.size() > 1)
		{
			logger.warn("More than one views to be created found for {}. Creating only the first view.", recordRefs);
		}
		final CreateViewRequest viewRequest = viewRequestBuilders.values().iterator().next()
				.setReferencingDocumentPaths(referencingDocumentPaths)
				.setParentViewId(parentViewId)
				.build();
		return viewRequest;
	}

	private static final Set<DocumentPath> extractReferencingDocumentPaths(final ProcessInfo processInfo)
	{
		final String tableName = processInfo.getTableNameOrNull();
		if (tableName == null)
		{
			return ImmutableSet.of();
		}
		final TableRecordReference sourceRecordRef = processInfo.getRecordRefOrNull();

		final IQueryFilter<Object> selectionQueryFilter = processInfo.getQueryFilterOrElse(null);
		if (selectionQueryFilter != null)
		{
			// TODO: hardcoded limit. It shall be much more obvious!!!
			final int maxRecordAllowedToSelect = 200;
			final List<Integer> recordIds = Services.get(IQueryBL.class).createQueryBuilder(tableName, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
					.filter(selectionQueryFilter)
					.setLimit(maxRecordAllowedToSelect + 1)
					.create()
					.listIds();
			if (recordIds.isEmpty())
			{
				return ImmutableSet.of();
			}
			else if (recordIds.size() > maxRecordAllowedToSelect)
			{
				throw new AdempiereException("Selecting more than " + maxRecordAllowedToSelect + " records is not allowed");
			}

			final TableRecordReference firstRecordRef = TableRecordReference.of(tableName, recordIds.get(0));
			final WindowId windowId = WindowId.of(RecordZoomWindowFinder.findAD_Window_ID(firstRecordRef)); // assume all records are from same window
			return recordIds.stream()
					.map(recordId -> DocumentPath.rootDocumentPath(windowId, recordId))
					.collect(ImmutableSet.toImmutableSet());
		}
		else if (sourceRecordRef != null)
		{
			final WindowId windowId = WindowId.of(RecordZoomWindowFinder.findAD_Window_ID(sourceRecordRef));
			final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, sourceRecordRef.getRecord_ID());
			return ImmutableSet.of(documentPath);
		}
		else
		{
			return ImmutableSet.of();
		}
	}

	private static final DocumentPath extractSingleDocumentPath(final RecordsToOpen recordsToOpen)
	{
		final TableRecordReference recordRef = recordsToOpen.getSingleRecord();
		final int documentId = recordRef.getRecord_ID();

		WindowId windowId = WindowId.fromNullableJson(recordsToOpen.getWindowIdString());
		if (windowId == null)
		{
			windowId = WindowId.ofIntOrNull(RecordZoomWindowFinder.findAD_Window_ID(recordRef));
		}

		return DocumentPath.rootDocumentPath(windowId, documentId);
	}

	/* package */boolean saveIfValidAndHasChanges(final boolean throwEx)
	{
		final Document parametersDocument = getParametersDocument();
		final DocumentSaveStatus parametersSaveStatus = parametersDocument.saveIfValidAndHasChanges();
		final boolean saved = parametersSaveStatus.isSaved();
		if (!saved && throwEx)
		{
			throw new ProcessExecutionException(parametersSaveStatus.getReason());
		}

		return saved;
	}

	/* package */ final IAutoCloseable lockForReading()
	{
		final ReadLock readLock = readwriteLock.readLock();
		logger.debug("Acquiring read lock for {}: {}", this, readLock);
		readLock.lock();
		logger.debug("Acquired read lock for {}: {}", this, readLock);

		return () -> {
			readLock.unlock();
			logger.debug("Released read lock for {}: {}", this, readLock);
		};
	}

	/* package */ final IAutoCloseable lockForWriting()
	{
		final WriteLock writeLock = readwriteLock.writeLock();
		logger.debug("Acquiring write lock for {}: {}", this, writeLock);
		writeLock.lock();
		logger.debug("Acquired write lock for {}: {}", this, writeLock);

		return () -> {
			writeLock.unlock();
			logger.debug("Released write lock for {}: {}", this, writeLock);
		};
	}
}

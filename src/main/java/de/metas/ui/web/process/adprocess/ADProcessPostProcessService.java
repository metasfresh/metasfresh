package de.metas.ui.web.process.adprocess;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.model.RecordZoomWindowFinder;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.MimeType;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutionResult.RecordsToOpen;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.ProcessInstanceResult.OpenIncludedViewAction;
import de.metas.ui.web.process.ProcessInstanceResult.OpenReportAction;
import de.metas.ui.web.process.ProcessInstanceResult.OpenSingleDocument;
import de.metas.ui.web.process.ProcessInstanceResult.OpenViewAction;
import de.metas.ui.web.process.ProcessInstanceResult.ResultAction;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentCollection;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ADProcessPostProcessService
{
	private static final Logger logger = LogManager.getLogger(ADProcessPostProcessService.class);

	private final IViewsRepository viewsRepo;
	private final DocumentCollection documentsCollection;

	// TODO: hardcoded limit. It shall be much more obvious!!!
	private static final int MAX_REFERENCED_DOCUMENTPATHS_ALLOWED = 200;

	@Builder
	private ADProcessPostProcessService(
			@NonNull final IViewsRepository viewsRepo,
			@NonNull final DocumentCollection documentsCollection)
	{
		this.viewsRepo = viewsRepo;
		this.documentsCollection = documentsCollection;
	}

	public ProcessInstanceResult postProcess(@NonNull final ADProcessPostProcessRequest request)
	{
		final ProcessInfo processInfo = request.getProcessInfo();
		final ProcessExecutionResult processExecutionResult = request.getProcessExecutionResult();

		invalidateDocumentsAndViews(request.getViewId(), processExecutionResult);

		return ProcessInstanceResult.builder(extractInstanceId(request))
				.summary(extractSummary(processExecutionResult))
				.error(processExecutionResult.isError())
				.action(createResultAction(processInfo, processExecutionResult))
				.build();
	}

	private void invalidateDocumentsAndViews(final ViewId viewId, final ProcessExecutionResult processExecutionResult)
	{
		final Supplier<IView> viewSupplier = Suppliers.memoize(() -> {
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

	private static final DocumentId extractInstanceId(final ADProcessPostProcessRequest request)
	{
		final DocumentId instanceIdOverride = request.getInstanceIdOverride();
		return instanceIdOverride != null ? instanceIdOverride : DocumentId.of(request.getProcessExecutionResult().getAD_PInstance_ID());
	}

	private static final String extractSummary(final ProcessExecutionResult processExecutionResult)
	{
		final String summary = processExecutionResult.getSummary();
		if (Check.isEmpty(summary, true) || JavaProcess.MSG_OK.equals(summary))
		{
			// hide summary if empty or MSG_OK (which is the most used non-message)
			return null;
		}

		return summary;
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
			final List<Integer> recordIds = Services.get(IQueryBL.class).createQueryBuilder(tableName, PlainContextAware.newWithThreadInheritedTrx())
					.filter(selectionQueryFilter)
					.setLimit(MAX_REFERENCED_DOCUMENTPATHS_ALLOWED + 1)
					.create()
					.listIds();
			if (recordIds.isEmpty())
			{
				return ImmutableSet.of();
			}
			else if (recordIds.size() > MAX_REFERENCED_DOCUMENTPATHS_ALLOWED)
			{
				throw new AdempiereException("Selecting more than " + MAX_REFERENCED_DOCUMENTPATHS_ALLOWED + " records is not allowed");
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

	private static final CreateViewRequest createViewRequest(
			final RecordsToOpen recordsToOpen,
			final Set<DocumentPath> referencingDocumentPaths,
			final ViewId parentViewId)
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

	private ResultAction createResultAction(final ProcessInfo processInfo, final ProcessExecutionResult processExecutionResult)
	{
		final File reportTempFile = saveReportToDiskIfAny(processExecutionResult);
		final RecordsToOpen recordsToOpen = processExecutionResult.getRecordsToOpen();

		//
		// Open report
		if (reportTempFile != null)
		{
			return OpenReportAction.builder()
					.filename(processExecutionResult.getReportFilename())
					.contentType(processExecutionResult.getReportContentType())
					.tempFile(reportTempFile)
					.build();
		}
		//
		// Open view
		else if (recordsToOpen != null && recordsToOpen.getTarget() == OpenTarget.GridView)
		{
			final Set<DocumentPath> referencingDocumentPaths = extractReferencingDocumentPaths(processInfo);
			final String parentViewIdStr = processExecutionResult.getWebuiViewId();
			final ViewId parentViewId = parentViewIdStr != null ? ViewId.ofViewIdString(parentViewIdStr) : null;
			final CreateViewRequest viewRequest = createViewRequest(recordsToOpen, referencingDocumentPaths, parentViewId);
			final IView view = viewsRepo.createView(viewRequest);

			return OpenViewAction.builder()
					.viewId(view.getViewId())
					.build();
		}
		//
		// Open included view
		else if (processExecutionResult.getWebuiIncludedViewIdToOpen() != null)
		{
			return OpenIncludedViewAction.builder()
					.viewId(ViewId.ofViewIdString(processExecutionResult.getWebuiIncludedViewIdToOpen()))
					.profileId(ViewProfileId.fromJson(processExecutionResult.getWebuiViewProfileId()))
					.build();
		}
		//
		// Open single document
		else if (recordsToOpen != null && recordsToOpen.getTarget() == OpenTarget.SingleDocument)
		{
			final DocumentPath documentPath = extractSingleDocumentPath(recordsToOpen);
			return OpenSingleDocument.builder()
					.documentPath(documentPath)
					.modal(false)
					.build();
		}
		//
		// Open single document
		else if (recordsToOpen != null && recordsToOpen.getTarget() == OpenTarget.SingleDocumentModal)
		{
			final DocumentPath documentPath = extractSingleDocumentPath(recordsToOpen);
			return OpenSingleDocument.builder()
					.documentPath(documentPath)
					.modal(true)
					.build();
		}
		//
		// No action
		else
		{
			return null;
		}

	}
}

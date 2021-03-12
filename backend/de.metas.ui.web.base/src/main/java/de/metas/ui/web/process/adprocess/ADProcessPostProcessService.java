package de.metas.ui.web.process.adprocess;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import de.metas.document.references.RecordZoomWindowFinder;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutionResult.RecordsToOpen;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.process.ProcessInfo;
import de.metas.report.ReportResultData;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.ProcessInstanceResult.DisplayQRCodeAction;
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
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

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

		final TableRecordReference currentSingleSelectedDocumentRef = processInfo.getRecordRefOrNull();
		final ProcessExecutionResult processExecutionResult = request.getProcessExecutionResult();

		invalidateDocumentsAndViews(request.getViewId(), currentSingleSelectedDocumentRef, processExecutionResult);

		return ProcessInstanceResult.builder(extractInstanceId(request))
				.summary(extractSummary(processExecutionResult))
				.error(processExecutionResult.isError())
				.action(createResultAction(processInfo, processExecutionResult))
				.build();
	}

	private void invalidateDocumentsAndViews(
			final ViewId viewId,
			final TableRecordReference currentSingleSelectedDocumentRef,
			final ProcessExecutionResult processExecutionResult)
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

		if (processExecutionResult.isRefreshAllAfterExecution())
		{
			final IView view = viewSupplier.get();

			if (view != null)
			{ // multiple rows selected
				view.invalidateAll();
				ViewChangesCollector.getCurrentOrAutoflush()
						.collectFullyChanged(view);
				viewInvalidateAllCalled = true;

				documentsCollection.invalidateDocumentsByWindowId(view.getViewId().getWindowId());
			}
			else if (currentSingleSelectedDocumentRef != null)
			{ // single row selected
				documentsCollection.invalidateDocumentByRecordId(
						currentSingleSelectedDocumentRef.getTableName(),
						currentSingleSelectedDocumentRef.getRecord_ID());
			}
		}

		//
		// Refresh required document
		final TableRecordReference recordToRefresh = processExecutionResult.getRecordToRefreshAfterExecution();
		if (recordToRefresh != null)
		{
			documentsCollection.invalidateDocumentByRecordId(recordToRefresh.getTableName(), recordToRefresh.getRecord_ID());

			final IView view = viewSupplier.get();
			if (!viewInvalidateAllCalled && view != null)
			{
				final boolean watchedByFrontend = viewsRepo.isWatchedByFrontend(view.getViewId());
				view.notifyRecordsChanged(TableRecordReferenceSet.of(recordToRefresh), watchedByFrontend);
			}
		}
	}

	private static DocumentId extractInstanceId(final ADProcessPostProcessRequest request)
	{
		final DocumentId instanceIdOverride = request.getInstanceIdOverride();
		return instanceIdOverride != null ? instanceIdOverride : DocumentId.of(request.getProcessExecutionResult().getPinstanceId());
	}

	@Nullable
	private static String extractSummary(final ProcessExecutionResult processExecutionResult)
	{
		final String summary = processExecutionResult.getSummary();
		if (Check.isEmpty(summary, true) || JavaProcess.MSG_OK.equals(summary))
		{
			// hide summary if empty or MSG_OK (which is the most used non-message)
			return null;
		}

		return summary;
	}

	@Nullable
	private static File saveReportToDiskIfAny(final ProcessExecutionResult processExecutionResult)
	{
		//
		// If we are not dealing with a report, stop here
		final ReportResultData reportData = processExecutionResult.getReportData();
		if (reportData == null || reportData.isEmpty())
		{
			return null;
		}

		final String reportFilePrefix = "report_" + processExecutionResult.getPinstanceId().getRepoId() + "_";
		return reportData.writeToTemporaryFile(reportFilePrefix);
	}

	private static DocumentPath extractSingleDocumentPath(final RecordsToOpen recordsToOpen)
	{
		final TableRecordReference recordRef = recordsToOpen.getFirstRecord();
		final int documentId = recordRef.getRecord_ID();

		WindowId windowId = WindowId.fromNullableJson(recordsToOpen.getWindowIdString());
		if (windowId == null)
		{
			windowId = WindowId.ofNullable(RecordZoomWindowFinder.findAdWindowId(recordRef).orElse(null));
		}

		return DocumentPath.rootDocumentPath(windowId, documentId);
	}

	private static Set<DocumentPath> extractReferencingDocumentPaths(final ProcessInfo processInfo)
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
			final WindowId windowId = WindowId.of(RecordZoomWindowFinder.findAdWindowId(firstRecordRef).get()); // assume all records are from same window
			return recordIds.stream()
					.map(recordId -> DocumentPath.rootDocumentPath(windowId, recordId))
					.collect(ImmutableSet.toImmutableSet());
		}
		else if (sourceRecordRef != null)
		{
			final WindowId windowId = WindowId.of(RecordZoomWindowFinder.findAdWindowId(sourceRecordRef).get());
			final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, sourceRecordRef.getRecord_ID());
			return ImmutableSet.of(documentPath);
		}
		else
		{
			return ImmutableSet.of();
		}
	}

	@Nullable
	private static CreateViewRequest createViewRequest(
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
			final WindowId recordWindowId = windowId_Override != null ? windowId_Override : WindowId.ofNullable(RecordZoomWindowFinder.findAdWindowId(recordRef).orElse(null));
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
		return viewRequestBuilders.values().iterator().next()
				.setReferencingDocumentPaths(referencingDocumentPaths)
				.setParentViewId(parentViewId)
				.setUseAutoFilters(recordsToOpen.isUseAutoFilters())
				.build();
	}

	@Nullable
	private ResultAction createResultAction(final ProcessInfo processInfo, final ProcessExecutionResult processExecutionResult)
	{
		final File reportTempFile = saveReportToDiskIfAny(processExecutionResult);
		final RecordsToOpen recordsToOpen = processExecutionResult.getRecordsToOpen();

		//
		// Open report
		if (reportTempFile != null)
		{
			logger.debug("The processExecutionResult specifies reportTempFile={}", reportTempFile);
			return OpenReportAction.builder()
					.filename(processExecutionResult.getReportFilename())
					.contentType(processExecutionResult.getReportContentType())
					.tempFile(reportTempFile)
					.build();
		}
		//
		// Create & open view from Records
		else if (recordsToOpen != null && recordsToOpen.getTarget() == OpenTarget.GridView)
		{
			logger.debug("The processExecutionResult specifies recordsToOpen={}", recordsToOpen);
			final Set<DocumentPath> referencingDocumentPaths = recordsToOpen.isAutomaticallySetReferencingDocumentPaths()
					? extractReferencingDocumentPaths(processInfo)
					: null;

			final String parentViewIdStr = processExecutionResult.getWebuiViewId();
			final ViewId parentViewId = parentViewIdStr != null ? ViewId.ofViewIdString(parentViewIdStr) : null;
			final CreateViewRequest viewRequest = createViewRequest(recordsToOpen, referencingDocumentPaths, parentViewId);
			final IView view = viewsRepo.createView(viewRequest);

			return OpenViewAction.builder()
					.viewId(view.getViewId())
					.targetTab(recordsToOpen.getTargetTab())
					.build();
		}
		//
		// Open existing view
		else if (processExecutionResult.getWebuiViewToOpen() != null)
		{
			final WebuiViewToOpen viewToOpen = processExecutionResult.getWebuiViewToOpen();
			logger.debug("The processExecutionResult specifies viewToOpen={}", viewToOpen);

			final ViewOpenTarget target = viewToOpen.getTarget();
			if (ViewOpenTarget.IncludedView.equals(target))
			{
				return OpenIncludedViewAction.builder()
						.viewId(ViewId.ofViewIdString(viewToOpen.getViewId()))
						.profileId(ViewProfileId.fromJson(viewToOpen.getProfileId()))
						.build();
			}
			else if (ViewOpenTarget.ModalOverlay.equals(target))
			{
				return OpenViewAction.builder()
						.viewId(ViewId.ofViewIdString(viewToOpen.getViewId()))
						.profileId(ViewProfileId.fromJson(viewToOpen.getProfileId()))
						.targetTab(recordsToOpen != null ? recordsToOpen.getTargetTab() : RecordsToOpen.TargetTab.SAME_TAB_OVERLAY)
						.build();
			}
			else if (ViewOpenTarget.NewBrowserTab.equals(target))
			{
				return OpenViewAction.builder()
						.viewId(ViewId.ofViewIdString(viewToOpen.getViewId()))
						.profileId(ViewProfileId.fromJson(viewToOpen.getProfileId()))
						.targetTab(recordsToOpen != null ? recordsToOpen.getTargetTab() : RecordsToOpen.TargetTab.SAME_TAB_OVERLAY)
						.build();
			}

			else
			{
				throw new AdempiereException("Unknown target: " + target);
			}
		}
		//
		// Open single document
		else if (recordsToOpen != null && recordsToOpen.getTarget() == OpenTarget.SingleDocument)
		{
			final DocumentPath documentPath = extractSingleDocumentPath(recordsToOpen);

			return OpenSingleDocument.builder()
					.documentPath(documentPath)
					.targetTab(recordsToOpen.getTargetTab() != RecordsToOpen.TargetTab.SAME_TAB_OVERLAY ? recordsToOpen.getTargetTab() : RecordsToOpen.TargetTab.SAME_TAB)
					.build();
		}
		//
		// Open single document
		else if (recordsToOpen != null && recordsToOpen.getTarget() == OpenTarget.SingleDocumentModal)
		{
			final DocumentPath documentPath = extractSingleDocumentPath(recordsToOpen);
			return OpenSingleDocument.builder()
					.documentPath(documentPath)
					.targetTab(recordsToOpen.getTargetTab())
					.build();
		}
		//
		// Close underlying modal view
		else if(processExecutionResult.isCloseWebuiModalView())
		{
			return ProcessInstanceResult.CloseViewAction.instance;
		}
		//
		// Display QRCode to user
		else if (processExecutionResult.getDisplayQRCode() != null)
		{
			return DisplayQRCodeAction.builder()
					.code(processExecutionResult.getDisplayQRCode().getCode())
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

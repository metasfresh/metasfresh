/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.view;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.impexp.spreadsheet.excel.ExcelFormat;
import de.metas.impexp.spreadsheet.excel.ExcelFormats;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.annotation.Monitor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.ui.web.cache.ETagResponseEntityBuilder;
import de.metas.ui.web.comments.CommentsService;
import de.metas.ui.web.comments.ViewRowCommentsSummary;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.process.ProcessRestController;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.process.WebuiPreconditionsContext;
import de.metas.ui.web.process.json.JSONDocumentActionsList;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONCreateViewRequest;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONGetFilterParameterDropdown;
import de.metas.ui.web.view.json.JSONGetFilterParameterTypeahead;
import de.metas.ui.web.view.json.JSONGetViewActionsRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.view.json.JSONViewHeaderProperties;
import de.metas.ui.web.view.json.JSONViewLayout;
import de.metas.ui.web.view.json.JSONViewProfilesList;
import de.metas.ui.web.view.json.JSONViewResult;
import de.metas.ui.web.view.json.JSONViewRow;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONDocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONZoomInto;
import de.metas.ui.web.window.model.lookup.zoom_into.DocumentZoomIntoInfo;
import de.metas.ui.web.window.model.lookup.zoom_into.DocumentZoomIntoService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.MimeType;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Tag(name = "ViewRestController")
@RestController
@RequestMapping(value = ViewRestController.ENDPOINT)
@RequiredArgsConstructor
public class ViewRestController
{
	public static final String PARAM_WindowId = "windowId";

	// FIXME: change "documentView" to "view"
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/documentView/{" + PARAM_WindowId + "}";

	private static final String PARAM_ViewId = "viewId";
	private static final String PARAM_ViewDataType = "viewType";
	private static final String PARAM_OrderBy = "orderBy";
	private static final String PARAM_OrderBy_Description = "Command separated field names. Use +/- prefix for ascending/descending. e.g. +C_BPartner_ID,-DateOrdered";
	//
	private static final String PARAM_FirstRow = "firstRow";
	private static final String PARAM_FirstRow_Description = "first row to fetch (starting from 0)";
	private static final String PARAM_PageLength = "pageLength";
	//
	private static final String PARAM_FilterId = "filterId";

	@NonNull private final UserSession userSession;
	@NonNull private final IViewsRepository viewsRepo;
	@NonNull private final ProcessRestController processRestController;
	@NonNull private final WindowRestController windowRestController;
	@NonNull private final CommentsService commentsService;
	@NonNull private final DocumentZoomIntoService documentZoomIntoService;

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.of(userSession);
	}

	private JSONDocumentLayoutOptions newJSONLayoutOptions()
	{
		return JSONDocumentLayoutOptions.of(userSession);
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@PostMapping
	public JSONViewResult createView(
			@PathVariable(PARAM_WindowId) final String windowIdStr //
			, @RequestBody final JSONCreateViewRequest jsonRequest //
	)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = extractWindowId(windowIdStr, jsonRequest.getWindowId());
		final CreateViewRequest request = CreateViewRequest.builder(windowId, jsonRequest.getViewType())
				.setProfileId(jsonRequest.getProfileId())
				.setReferencingDocumentPaths(jsonRequest.getReferencingDocumentPaths())
				.setDocumentReferenceId(jsonRequest.getDocumentReferenceId().orElse(null))
				// .setStickyFilters(stickyFilters) // none
				.setFiltersFromJSON(jsonRequest.getFilters())
				.setFilterOnlyIds(jsonRequest.getFilterOnlyIds())
				.setUseAutoFilters(true)
				.build();

		final IView view = viewsRepo.createView(request);

		//
		// Fetch result if requested
		final ViewResult result;
		if (jsonRequest.getQueryPageLength() > 0)
		{
			final JSONOptions jsonOpts = newJSONOptions();
			result = view.getPage(
					jsonRequest.getQueryFirstRow(),
					jsonRequest.getQueryPageLength(),
					ViewRowsOrderBy.empty(jsonOpts));
		}
		else
		{
			result = ViewResult.ofView(view);
		}

		final IViewRowOverrides rowOverrides = ViewRowOverridesHelper.getViewRowOverrides(view);
		final JSONOptions jsonOpts = newJSONOptions();

		final List<IViewRow> rows = result.isPageLoaded() ? result.getPage() : Collections.emptyList();
		final ViewRowCommentsSummary viewRowCommentsSummary = commentsService.getRowCommentsSummary(rows);

		return JSONViewResult.of(result, rowOverrides, jsonOpts, viewRowCommentsSummary);
	}

	private static WindowId extractWindowId(final String pathWindowIdStr, final WindowId requestWindowId)
	{
		final WindowId pathWindowId = WindowId.fromNullableJson(pathWindowIdStr);
		WindowId windowIdEffective = requestWindowId;
		if (windowIdEffective == null)
		{
			windowIdEffective = WindowId.fromJson(pathWindowIdStr);
		}
		else if (!Objects.equals(pathWindowId, windowIdEffective))
		{
			throw new IllegalArgumentException("Request's windowId is not matching the one from path");
		}

		return windowIdEffective;
	}

	/**
	 * Creates a new view by filtering the given one.
	 */
	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@PostMapping("/{viewId}/filter")
	public JSONViewResult filterView( //
									  @PathVariable(PARAM_WindowId) final String windowIdStr //
			, @PathVariable(PARAM_ViewId) final String viewIdStr //
			, @RequestBody final JSONFilterViewRequest jsonRequest //
	)
	{
		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);

		final IView newView = viewsRepo.filterView(viewId, jsonRequest);
		final JSONOptions jsonOpts = newJSONOptions();
		final ViewResult viewResult = ViewResult.ofView(newView);

		final List<IViewRow> rows = viewResult.isPageLoaded() ? viewResult.getPage() : Collections.emptyList();
		final ViewRowCommentsSummary viewRowCommentsSummary = commentsService.getRowCommentsSummary(rows);

		return JSONViewResult.of(viewResult, ViewRowOverridesHelper.getViewRowOverrides(newView), jsonOpts, viewRowCommentsSummary);
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@DeleteMapping("/{viewId}/staticFilter/{filterId}")
	public JSONViewResult deleteStickyFilter(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable(PARAM_FilterId) final String filterId)
	{
		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);

		final IView newView = viewsRepo.deleteStickyFilter(viewId, filterId);
		final JSONOptions jsonOpts = newJSONOptions();
		final ViewResult viewResult = ViewResult.ofView(newView);

		final List<IViewRow> rows = viewResult.isPageLoaded() ? viewResult.getPage() : Collections.emptyList();
		final ViewRowCommentsSummary viewRowCommentsSummary = commentsService.getRowCommentsSummary(rows);

		return JSONViewResult.of(viewResult, ViewRowOverridesHelper.getViewRowOverrides(newView), jsonOpts, viewRowCommentsSummary);
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@DeleteMapping("/{viewId}")
	public void closeView(
			@PathVariable(PARAM_WindowId) final String windowId,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@RequestParam(name = "action", required = false) final String closeActionStr)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		final ViewCloseAction closeAction = ViewCloseAction.fromJsonOr(closeActionStr, ViewCloseAction.DONE);
		viewsRepo.closeView(viewId, closeAction);
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@GetMapping("/{viewId}")
	public JSONViewResult getViewData(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@RequestParam(name = PARAM_FirstRow) @Parameter(description = PARAM_FirstRow_Description) final int firstRow,
			@RequestParam(name = PARAM_PageLength) final int pageLength,
			@RequestParam(name = PARAM_OrderBy, required = false) @Parameter(description = PARAM_OrderBy_Description) final String orderBysListStr)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);
		final IView view = viewsRepo.getView(viewId);
		final JSONOptions jsonOpts = newJSONOptions();
		final ViewResult result = view.getPage(
				firstRow,
				pageLength,
				ViewRowsOrderBy.parseString(orderBysListStr, jsonOpts));
		final IViewRowOverrides rowOverrides = ViewRowOverridesHelper.getViewRowOverrides(view);

		final List<IViewRow> rows = result.isPageLoaded() ? result.getPage() : Collections.emptyList();
		final ViewRowCommentsSummary viewRowCommentsSummary = commentsService.getRowCommentsSummary(rows);

		return JSONViewResult.of(result, rowOverrides, jsonOpts, viewRowCommentsSummary);
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@GetMapping("/layout")
	public ResponseEntity<JSONViewLayout> getViewLayout(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@RequestParam(name = PARAM_ViewDataType) final JSONViewDataType viewDataType,
			@RequestParam(name = "profileId", required = false) final String profileIdStr,
			final WebRequest request)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final ViewLayout viewLayout = viewsRepo.getViewLayout(windowId, viewDataType, ViewProfileId.fromJson(profileIdStr), userSession.getUserRolePermissionsKey());

		return ETagResponseEntityBuilder.ofETagAware(request, viewLayout)
				.includeLanguageInETag()
				.cacheMaxAge(userSession.getHttpCacheMaxAge())
				.jsonLayoutOptions(this::newJSONLayoutOptions)
				.toLayoutJson(JSONViewLayout::of);
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@GetMapping("/availableProfiles")
	public JSONViewProfilesList getAvailableViewProfiles(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@RequestParam(name = PARAM_ViewDataType) final JSONViewDataType viewDataType)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final List<ViewProfile> availableProfiles = viewsRepo.getAvailableProfiles(windowId, viewDataType);
		return JSONViewProfilesList.of(availableProfiles, userSession.getAD_Language());
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@GetMapping("/{viewId}/headerProperties")
	public JSONViewHeaderProperties getHeaderProperties(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable(PARAM_ViewId) final String viewIdStr//
	)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		final IView view = viewsRepo.getView(viewId);
		final JSONOptions jsonOpts = newJSONOptions();
		final ViewHeaderProperties headerProperties = view.getHeaderProperties();
		return JSONViewHeaderProperties.of(headerProperties, jsonOpts.getAdLanguage());
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@GetMapping("/{viewId}/byIds")
	public List<JSONViewRow> getByIds(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable(PARAM_ViewId) final String viewIdStr //
			, @RequestParam("ids") @Parameter(description = "comma separated IDs") final String idsListStr //
	)
	{
		userSession.assertLoggedIn();

		final DocumentIdsSelection rowIds = DocumentIdsSelection.ofCommaSeparatedString(idsListStr);
		if (rowIds.isAll())
		{
			throw new AdempiereException("retrieving ALL rows is not allowed here");
		}

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		final IView view = viewsRepo.getView(viewId);
		final List<? extends IViewRow> result = view.streamByIds(rowIds).collect(ImmutableList.toImmutableList());
		final IViewRowOverrides rowOverrides = ViewRowOverridesHelper.getViewRowOverrides(view);
		final JSONOptions jsonOpts = newJSONOptions();

		final ViewRowCommentsSummary viewRowCommentsSummary = commentsService.getRowCommentsSummary(result);

		return JSONViewRow.ofViewRows(result, rowOverrides, jsonOpts, viewRowCommentsSummary);
	}

	private ViewFilterParameterLookupEvaluationCtx createFilterParameterLookupContext(
			@NonNull final IView view,
			@Nullable final Map<String, Object> filterParameterValues)
	{
		return ViewFilterParameterLookupEvaluationCtx.builder()
				.viewId(view.getViewId())
				.viewSize(view.size())
				.userSessionCtx(userSession.toEvaluatee())
				.parameterValues(filterParameterValues)
				.build();
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@GetMapping("/{viewId}/filter/{filterId}/field/{parameterName}/typeahead")
	@Deprecated
	public JSONLookupValuesPage getFilterParameterTypeahead(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable(PARAM_ViewId) final String viewIdStr //
			, @PathVariable(PARAM_FilterId) final String filterId //
			, @PathVariable("parameterName") final String parameterName //
			, @RequestParam(name = "query") final String query //
	)
	{
		return getFilterParameterTypeahead(
				windowId,
				viewIdStr,
				filterId,
				parameterName,
				JSONGetFilterParameterTypeahead.builder()
						.query(query)
						.context(ImmutableMap.of())
						.build());
	}

	@PostMapping("/{viewId}/filter/{filterId}/field/{parameterName}/typeahead")
	public JSONLookupValuesPage getFilterParameterTypeahead(
			@PathVariable(PARAM_WindowId) final String windowId,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable(PARAM_FilterId) final String filterId,
			@PathVariable("parameterName") final String parameterName,
			@RequestBody final JSONGetFilterParameterTypeahead request)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		final IView view = viewsRepo.getView(viewId);
		final ViewFilterParameterLookupEvaluationCtx ctx = createFilterParameterLookupContext(view, request.getContext());
		final String adLanguage = userSession.getAD_Language();

		try
		{
			return view
					.getFilterParameterTypeahead(filterId, parameterName, request.getQuery(), ctx)
					.transform(page -> JSONLookupValuesPage.of(page, adLanguage));
		}
		catch (final Exception ex)
		{
			// NOTE: don't propagate exceptions because some of them are thrown because not all parameters are provided (standard use case)
			return JSONLookupValuesPage.error(JsonErrors.ofThrowable(ex, adLanguage));
		}
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@GetMapping("/{viewId}/filter/{filterId}/field/{parameterName}/dropdown")
	@Deprecated
	public JSONLookupValuesPage getFilterParameterDropdown(
			@PathVariable(PARAM_WindowId) final String windowId,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable(PARAM_FilterId) final String filterId,
			@PathVariable("parameterName") final String parameterName)
	{
		return getFilterParameterDropdown(
				windowId,
				viewIdStr,
				filterId,
				parameterName,
				JSONGetFilterParameterDropdown.builder()
						.context(ImmutableMap.of())
						.build());
	}

	@PostMapping("/{viewId}/filter/{filterId}/field/{parameterName}/dropdown")
	public JSONLookupValuesPage getFilterParameterDropdown(
			@PathVariable(PARAM_WindowId) final String windowId,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable(PARAM_FilterId) final String filterId,
			@PathVariable("parameterName") final String parameterName,
			@RequestBody final JSONGetFilterParameterDropdown request)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		final IView view = viewsRepo.getView(viewId);
		final ViewFilterParameterLookupEvaluationCtx ctx = createFilterParameterLookupContext(view, request.getContext());

		try
		{
			return view
					.getFilterParameterDropdown(filterId, parameterName, ctx)
					.transform(page -> JSONLookupValuesPage.of(page, userSession.getAD_Language()));
		}
		catch (final Exception ex)
		{
			// NOTE: don't propagate exceptions because some of them are thrown because not all parameters are provided (standard use case)
			final String adLanguage = userSession.getAD_Language();
			return JSONLookupValuesPage.error(JsonErrors.ofThrowable(ex, adLanguage));
		}
	}

	@Builder(builderMethodName = "newPreconditionsContextBuilder")
	private ViewAsPreconditionsContext createPreconditionsContext(
			@NonNull final String windowId,
			@NonNull final String viewIdString,
			final String viewProfileIdStr,
			final Set<String> selectedIds,
			final String parentViewId,
			final Set<String> parentViewSelectedIds,
			final String childViewId,
			final Set<String> childViewSelectedIds,
			final DisplayPlace displayPlace)
	{
		final ViewId viewId = ViewId.of(windowId, viewIdString);
		final IView view = viewsRepo.getView(viewId);

		ViewRowIdsSelection viewRowIdsSelection = ViewRowIdsSelection.of(viewId, selectedIds);
		ViewRowIdsSelection parentViewRowIdsSelection = ViewRowIdsSelection.ofNullableStrings(parentViewId, parentViewSelectedIds);
		ViewRowIdsSelection childViewRowIdsSelection = ViewRowIdsSelection.ofNullableStrings(childViewId, childViewSelectedIds);

		return ViewAsPreconditionsContext.builder()
				.view(view)
				.viewProfileId(ViewProfileId.fromJson(viewProfileIdStr))
				.viewRowIdsSelection(viewRowIdsSelection)
				.parentViewRowIdsSelection(parentViewRowIdsSelection)
				.childViewRowIdsSelection(childViewRowIdsSelection)
				.displayPlace(displayPlace)
				.build();
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@PostMapping("/{viewId}/actions")
	public JSONDocumentActionsList getRowsActions(
			@PathVariable(PARAM_WindowId) final String windowId,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@RequestBody final JSONGetViewActionsRequest request)
	{
		userSession.assertLoggedIn();

		final WebuiPreconditionsContext preconditionsContext = newPreconditionsContextBuilder()
				.windowId(windowId)
				.viewIdString(viewIdStr)
				.selectedIds(request.getSelectedIds())
				.parentViewId(request.getParentViewId())
				.parentViewSelectedIds(request.getParentViewSelectedIds())
				.childViewId(request.getChildViewId())
				.childViewSelectedIds(request.getChildViewSelectedIds())
				.displayPlace(DisplayPlace.ViewActionsMenu)
				.build();

		return processRestController.streamDocumentRelatedProcesses(preconditionsContext)
				.filter(descriptor -> descriptor.isDisplayedOn(preconditionsContext.getDisplayPlace())) // shall be already filtered out, but just to make sure
				.filter(descriptor -> request.isAll() || descriptor.isEnabled()) // only those which are enabled and not internally rejected
				.collect(JSONDocumentActionsList.collect(newJSONOptions()));
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@PostMapping("/{viewId}/quickActions")
	public JSONDocumentActionsList getRowsQuickActions(
			@PathVariable(PARAM_WindowId) final String windowId,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@RequestBody final JSONGetViewActionsRequest request)
	{
		userSession.assertLoggedIn();

		final WebuiPreconditionsContext preconditionsContext = newPreconditionsContextBuilder()
				.windowId(windowId)
				.viewIdString(viewIdStr)
				.viewProfileIdStr(request.getViewProfileId())
				.selectedIds(request.getSelectedIds())
				.parentViewId(request.getParentViewId())
				.parentViewSelectedIds(request.getParentViewSelectedIds())
				.childViewId(request.getChildViewId())
				.childViewSelectedIds(request.getChildViewSelectedIds())
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();

		return processRestController.streamDocumentRelatedProcesses(preconditionsContext)
				.filter(descriptor -> descriptor.isDisplayedOn(preconditionsContext.getDisplayPlace())) // shall be already filtered out, but just to make sure
				.filter(descriptor -> request.isAll() || descriptor.isEnabledOrNotSilent()) // only those which are enabled or not silent
				.collect(JSONDocumentActionsList.collect(newJSONOptions()));
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@GetMapping("/{viewId}/{rowId}/field/{fieldName}/zoomInto")
	public JSONZoomInto getRowFieldZoomInto(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable("rowId") final String rowIdStr,
			@PathVariable("fieldName") final String fieldName)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.ofViewIdString(viewIdStr, WindowId.fromJson(windowIdStr));
		final IView view = viewsRepo.getView(viewId);
		if (view instanceof IViewZoomIntoFieldSupport)
		{
			final IViewZoomIntoFieldSupport zoomIntoSupport = (IViewZoomIntoFieldSupport)view;
			final DocumentId rowId = DocumentId.of(rowIdStr);
			final DocumentZoomIntoInfo zoomIntoInfo = zoomIntoSupport.getZoomIntoInfo(rowId, fieldName);
			final DocumentPath zoomIntoDocumentPath = documentZoomIntoService.getDocumentPath(zoomIntoInfo);
			return JSONZoomInto.builder()
					.documentPath(JSONDocumentPath.ofWindowDocumentPath(zoomIntoDocumentPath))
					.source(JSONDocumentPath.builder().viewId(viewId).rowId(rowId).fieldName(fieldName).build())
					.build();
		}

		// Fallback to windowRestController, hoping the document existing and has the same ID as view's row ID.
		return windowRestController.getDocumentFieldZoomInto(windowIdStr, rowIdStr, fieldName);
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER_WITH_WINDOW_ID)
	@GetMapping("/{viewId}/export/excel")
	public ResponseEntity<Resource> exportToExcel(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@RequestParam(name = "selectedIds", required = false) @Parameter(description = "comma separated IDs") final String selectedIdsListStr)
			throws Exception
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.ofViewIdString(viewIdStr, WindowId.fromJson(windowIdStr));

		final ExcelFormat excelFormat = ExcelFormats.getDefaultFormat();
		final File tmpFile = File.createTempFile("exportToExcel", "." + excelFormat.getFileExtension());

		try (final FileOutputStream out = new FileOutputStream(tmpFile))
		{
			ViewExcelExporter.builder()
					.excelFormat(excelFormat)
					.view(viewsRepo.getView(viewId))
					.rowIds(DocumentIdsSelection.ofCommaSeparatedString(selectedIdsListStr))
					.layout(viewsRepo.getViewLayout(viewId.getWindowId(), JSONViewDataType.grid, ViewProfileId.NULL, userSession.getUserRolePermissionsKey()))
					.language(userSession.getLanguage())
					.zoneId(userSession.getTimeZone())
					.build()
					.export(out);
		}

		final String filename = "report." + excelFormat.getFileExtension(); // TODO: use a better name
		final String contentType = MimeType.getMimeType(filename);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return new ResponseEntity<>(new InputStreamResource(Files.newInputStream(tmpFile.toPath())), headers, HttpStatus.OK);
	}
}

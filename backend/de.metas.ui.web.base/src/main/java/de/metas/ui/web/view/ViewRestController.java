/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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
import de.metas.impexp.spreadsheet.excel.ExcelFormat;
import de.metas.impexp.spreadsheet.excel.ExcelFormats;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
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
import de.metas.ui.web.view.json.JSONGetViewActionsRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.view.json.JSONViewHeaderProperties;
import de.metas.ui.web.view.json.JSONViewLayout;
import de.metas.ui.web.view.json.JSONViewProfilesList;
import de.metas.ui.web.view.json.JSONViewResult;
import de.metas.ui.web.view.json.JSONViewRow;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONZoomInto;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Api
@RestController
@RequestMapping(value = ViewRestController.ENDPOINT)
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

	private final UserSession userSession;
	private final IViewsRepository viewsRepo;
	private final ProcessRestController processRestController;
	private final WindowRestController windowRestController;
	private final CommentsService commentsService;

	public ViewRestController(
			@NonNull final UserSession userSession,
			@NonNull final IViewsRepository viewsRepo,
			@NonNull final ProcessRestController processRestController,
			@NonNull final WindowRestController windowRestController,
			@NonNull final CommentsService commentsService)
	{
		this.userSession = userSession;
		this.viewsRepo = viewsRepo;
		this.processRestController = processRestController;
		this.windowRestController = windowRestController;
		this.commentsService = commentsService;
	}

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.of(userSession);
	}

	private JSONDocumentLayoutOptions newJSONLayoutOptions()
	{
		return JSONDocumentLayoutOptions.of(userSession);
	}

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

	@GetMapping("/{viewId}")
	public JSONViewResult getViewData(
			@PathVariable(PARAM_WindowId) final String windowId,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@RequestParam(name = PARAM_FirstRow) @ApiParam(PARAM_FirstRow_Description) final int firstRow,
			@RequestParam(name = PARAM_PageLength) final int pageLength,
			@RequestParam(name = PARAM_OrderBy, required = false) @ApiParam(PARAM_OrderBy_Description) final String orderBysListStr)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
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

	@GetMapping("/layout")
	public ResponseEntity<JSONViewLayout> getViewLayout(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@RequestParam(name = PARAM_ViewDataType) final JSONViewDataType viewDataType,
			@RequestParam(name = "profileId", required = false) final String profileIdStr,
			final WebRequest request)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final ViewLayout viewLayout = viewsRepo.getViewLayout(windowId, viewDataType, ViewProfileId.fromJson(profileIdStr));

		return ETagResponseEntityBuilder.ofETagAware(request, viewLayout)
				.includeLanguageInETag()
				.cacheMaxAge(userSession.getHttpCacheMaxAge())
				.jsonLayoutOptions(this::newJSONLayoutOptions)
				.toLayoutJson(JSONViewLayout::of);
	}

	@GetMapping("/availableProfiles")
	public JSONViewProfilesList getAvailableViewProfiles(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@RequestParam(name = PARAM_ViewDataType) final JSONViewDataType viewDataType)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final List<ViewProfile> availableProfiles = viewsRepo.getAvailableProfiles(windowId, viewDataType);
		return JSONViewProfilesList.of(availableProfiles, userSession.getAD_Language());
	}

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

	@GetMapping("/{viewId}/byIds")
	public List<JSONViewRow> getByIds(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable(PARAM_ViewId) final String viewIdStr //
			, @RequestParam("ids") @ApiParam("comma separated IDs") final String idsListStr //
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

	private Evaluatee createFilterParameterLookupContext(final IView view)
	{
		return Evaluatees.mapBuilder()
				.put(LookupDataSourceContext.PARAM_ViewId, view.getViewId())
				.put(LookupDataSourceContext.PARAM_ViewSize, view.size())
				.build()
				.andComposeWith(userSession.toEvaluatee());
	}

	private JSONLookupValuesList toJSONLookupValuesList(final LookupValuesList lookupValuesList)
	{
		return JSONLookupValuesList.ofLookupValuesList(lookupValuesList, userSession.getAD_Language());
	}

	@GetMapping("/{viewId}/filter/{filterId}/field/{parameterName}/typeahead")
	public JSONLookupValuesPage getFilterParameterTypeahead(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable(PARAM_ViewId) final String viewIdStr //
			, @PathVariable(PARAM_FilterId) final String filterId //
			, @PathVariable("parameterName") final String parameterName //
			, @RequestParam(name = "query") final String query //
	)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		final IView view = viewsRepo.getView(viewId);
		final Evaluatee ctx = createFilterParameterLookupContext(view);

		return view
				.getFilterParameterTypeahead(filterId, parameterName, query, ctx)
				.transform(page -> JSONLookupValuesPage.of(page, userSession.getAD_Language()));
	}

	@GetMapping("/{viewId}/filter/{filterId}/field/{parameterName}/dropdown")
	public JSONLookupValuesList getFilterParameterDropdown(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable(PARAM_ViewId) final String viewIdStr //
			, @PathVariable(PARAM_FilterId) final String filterId //
			, @PathVariable("parameterName") final String parameterName //
	)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		final IView view = viewsRepo.getView(viewId);
		final Evaluatee ctx = createFilterParameterLookupContext(view);

		return view
				.getFilterParameterDropdown(filterId, parameterName, ctx)
				.transform(this::toJSONLookupValuesList);
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

	@GetMapping("/{viewId}/{rowId}/field/{fieldName}/zoomInto")
	public JSONZoomInto getRowFieldZoomInto(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@PathVariable("rowId") final String rowId,
			@PathVariable("fieldName") final String fieldName)
	{
		// userSession.assertLoggedIn(); // NOTE: not needed because we are forwarding to windowRestController

		ViewId.ofViewIdString(viewIdStr, WindowId.fromJson(windowIdStr)); // just validate the windowId and viewId

		// TODO: atm we are forwarding all calls to windowRestController hoping the document existing and has the same ID as view's row ID.

		return windowRestController.getDocumentFieldZoomInto(windowIdStr, rowId, fieldName);
	}

	@GetMapping("/{viewId}/export/excel")
	public ResponseEntity<Resource> exportToExcel(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable(PARAM_ViewId) final String viewIdStr,
			@RequestParam(name = "selectedIds", required = false) @ApiParam("comma separated IDs") final String selectedIdsListStr)
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
					.layout(viewsRepo.getViewLayout(viewId.getWindowId(), JSONViewDataType.grid, ViewProfileId.NULL))
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

		return new ResponseEntity<>(new InputStreamResource(new FileInputStream(tmpFile)), headers, HttpStatus.OK);
	}
}

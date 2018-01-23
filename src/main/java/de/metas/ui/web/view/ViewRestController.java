package de.metas.ui.web.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.cache.ETagResponseEntityBuilder;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.process.ProcessRestController;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.process.WebuiPreconditionsContext;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.json.JSONDocumentActionsList;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONCreateViewRequest;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.view.json.JSONViewLayout;
import de.metas.ui.web.view.json.JSONViewProfilesList;
import de.metas.ui.web.view.json.JSONViewResult;
import de.metas.ui.web.view.json.JSONViewRow;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONZoomInto;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
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

@Api
@RestController
@RequestMapping(value = ViewRestController.ENDPOINT)
public class ViewRestController
{
	static final String PARAM_WindowId = "windowId";

	// FIXME: change "documentView" to "view"
	/* package */static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/documentView/{" + PARAM_WindowId + "}";

	private static final String PARAM_ViewDataType = "viewType";
	private static final String PARAM_OrderBy = "orderBy";
	private static final String PARAM_OrderBy_Description = "Command separated field names. Use +/- prefix for ascending/descending. e.g. +C_BPartner_ID,-DateOrdered";
	//
	private static final String PARAM_FirstRow = "firstRow";
	private static final String PARAM_FirstRow_Description = "first row to fetch (starting from 0)";
	private static final String PARAM_PageLength = "pageLength";

	@Autowired
	private UserSession userSession;

	@Autowired
	private IViewsRepository viewsRepo;

	@Autowired
	private ProcessRestController processRestController;

	@Autowired
	private WindowRestController windowRestController;

	public ViewRestController()
	{
	}

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.builder(userSession).build();
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
			final List<DocumentQueryOrderBy> orderBys = ImmutableList.of();
			result = view.getPage(jsonRequest.getQueryFirstRow(), jsonRequest.getQueryPageLength(), orderBys);
		}
		else
		{
			result = ViewResult.ofView(view);
		}

		final IViewRowOverrides rowOverrides = ViewRowOverridesHelper.getViewRowOverrides(view);
		return JSONViewResult.of(result, rowOverrides, userSession.getAD_Language());
	}

	private static final WindowId extractWindowId(final String pathWindowIdStr, final WindowId requestWindowId)
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
			, @PathVariable("viewId") final String viewIdStr //
			, @RequestBody final JSONFilterViewRequest jsonRequest //
	)
	{
		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);

		final IView newView = viewsRepo.filterView(viewId, jsonRequest);
		return JSONViewResult.of(ViewResult.ofView(newView), ViewRowOverridesHelper.getViewRowOverrides(newView), userSession.getAD_Language());
	}

	@DeleteMapping("/{viewId}/staticFilter/{filterId}")
	public JSONViewResult deleteStickyFilter(@PathVariable(PARAM_WindowId) final String windowIdStr, @PathVariable("viewId") final String viewIdStr, @PathVariable("filterId") final String filterId)
	{
		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);

		final IView newView = viewsRepo.deleteStickyFilter(viewId, filterId);
		return JSONViewResult.of(ViewResult.ofView(newView), ViewRowOverridesHelper.getViewRowOverrides(newView), userSession.getAD_Language());
	}

	@DeleteMapping("/{viewId}")
	public void deleteView(@PathVariable(PARAM_WindowId) final String windowId, @PathVariable("viewId") final String viewIdStr)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		viewsRepo.deleteView(viewId);
	}

	@GetMapping("/{viewId}")
	public JSONViewResult getViewData(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable("viewId") final String viewIdStr//
			, @RequestParam(name = PARAM_FirstRow, required = true) @ApiParam(PARAM_FirstRow_Description) final int firstRow //
			, @RequestParam(name = PARAM_PageLength, required = true) final int pageLength //
			, @RequestParam(name = PARAM_OrderBy, required = false) @ApiParam(PARAM_OrderBy_Description) final String orderBysListStr //
	)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		final IView view = viewsRepo.getView(viewId);
		final ViewResult result = view.getPage(firstRow, pageLength, DocumentQueryOrderBy.parseOrderBysList(orderBysListStr));
		final IViewRowOverrides rowOverrides = ViewRowOverridesHelper.getViewRowOverrides(view);
		return JSONViewResult.of(result, rowOverrides, userSession.getAD_Language());
	}

	@GetMapping("/layout")
	public ResponseEntity<JSONViewLayout> getViewLayout(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType,
			@RequestParam(name = "profileId", required = false) final String profileIdStr,
			final WebRequest request)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final ViewLayout viewLayout = viewsRepo.getViewLayout(windowId, viewDataType, ViewProfileId.fromJson(profileIdStr));

		return ETagResponseEntityBuilder.ofETagAware(request, viewLayout)
				.includeLanguageInETag()
				.cacheMaxAge(userSession.getHttpCacheMaxAge())
				.jsonOptions(() -> newJSONOptions())
				.toJson(JSONViewLayout::of);
	}

	@GetMapping("/availableProfiles")
	public JSONViewProfilesList getAvailableViewProfiles(
			@PathVariable(PARAM_WindowId) final String windowIdStr,
			@RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final List<ViewProfile> availableProfiles = viewsRepo.getAvailableProfiles(windowId, viewDataType);
		return JSONViewProfilesList.of(availableProfiles, userSession.getAD_Language());
	}

	@GetMapping("/{viewId}/byIds")
	public List<JSONViewRow> getByIds(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable("viewId") final String viewIdStr //
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
		return JSONViewRow.ofViewRows(result, rowOverrides, userSession.getAD_Language());
	}

	@GetMapping("/{viewId}/filter/{filterId}/field/{parameterName}/typeahead")
	public JSONLookupValuesList getFilterParameterTypeahead(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable("viewId") final String viewIdStr //
			, @PathVariable("filterId") final String filterId //
			, @PathVariable("parameterName") final String parameterName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		return viewsRepo.getView(viewId)
				.getFilterParameterTypeahead(filterId, parameterName, query, userSession.toEvaluatee())
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@GetMapping("/{viewId}/filter/{filterId}/field/{parameterName}/dropdown")
	public JSONLookupValuesList getFilterParameterDropdown(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable("viewId") final String viewIdStr //
			, @PathVariable("filterId") final String filterId //
			, @PathVariable("parameterName") final String parameterName //
	)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		return viewsRepo.getView(viewId)
				.getFilterParameterDropdown(filterId, parameterName, userSession.toEvaluatee())
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@Builder(builderMethodName = "newPreconditionsContextBuilder")
	private ViewAsPreconditionsContext createPreconditionsContext(
			@NonNull final String windowId,
			@NonNull final String viewIdString,
			final String selectedIdsList,
			final String parentViewId,
			final String parentViewSelectedIdsList,
			final String childViewId,
			final String childViewSelectedIdsList)
	{
		final ViewId viewId = ViewId.of(windowId, viewIdString);
		final IView view = viewsRepo.getView(viewId);

		ViewRowIdsSelection viewRowIdsSelection = ViewRowIdsSelection.of(viewId, DocumentIdsSelection.ofCommaSeparatedString(selectedIdsList));
		ViewRowIdsSelection parentViewRowIdsSelection = ViewRowIdsSelection.ofNullableStrings(parentViewId, parentViewSelectedIdsList);
		ViewRowIdsSelection childViewRowIdsSelection = ViewRowIdsSelection.ofNullableStrings(childViewId, childViewSelectedIdsList);

		return ViewAsPreconditionsContext.builder()
				.view(view)
				.viewRowIdsSelection(viewRowIdsSelection)
				.parentViewRowIdsSelection(parentViewRowIdsSelection)
				.childViewRowIdsSelection(childViewRowIdsSelection)
				.build();
	}

	@GetMapping("/{viewId}/actions")
	public JSONDocumentActionsList getDocumentActions(
			@PathVariable(PARAM_WindowId) final String windowId,
			@PathVariable("viewId") final String viewIdStr,
			@RequestParam(name = "selectedIds", required = false) @ApiParam("comma separated IDs") final String selectedIdsListStr,
			@RequestParam(name = "parentViewId", required = false) final String parentViewIdStr,
			@RequestParam(name = "parentViewSelectedIds", required = false) @ApiParam("comma separated IDs") final String parentViewSelectedIdsListStr,
			@RequestParam(name = "childViewId", required = false) final String childViewIdStr,
			@RequestParam(name = "childViewSelectedIds", required = false) @ApiParam("comma separated IDs") final String childViewSelectedIdsListStr)
	{
		userSession.assertLoggedIn();

		final WebuiPreconditionsContext preconditionsContext = newPreconditionsContextBuilder()
				.windowId(windowId)
				.viewIdString(viewIdStr)
				.selectedIdsList(selectedIdsListStr)
				.parentViewId(parentViewIdStr)
				.parentViewSelectedIdsList(parentViewSelectedIdsListStr)
				.childViewId(childViewIdStr)
				.childViewSelectedIdsList(childViewSelectedIdsListStr)
				.build();

		return processRestController.streamDocumentRelatedProcesses(preconditionsContext)
				.filter(WebuiRelatedProcessDescriptor::isEnabled) // only those which are enabled or not silent
				.collect(JSONDocumentActionsList.collect(newJSONOptions()));
	}

	@GetMapping("/{viewId}/quickActions")
	public JSONDocumentActionsList getDocumentQuickActions(
			@PathVariable(PARAM_WindowId) final String windowId,
			@PathVariable("viewId") final String viewIdStr,
			@RequestParam(name = "selectedIds", required = false) @ApiParam("comma separated IDs") final String selectedIdsListStr,
			@RequestParam(name = "parentViewId", required = false) final String parentViewIdStr,
			@RequestParam(name = "parentViewSelectedIds", required = false) @ApiParam("comma separated IDs") final String parentViewSelectedIdsListStr,
			@RequestParam(name = "childViewId", required = false) final String childViewIdStr,
			@RequestParam(name = "childViewSelectedIds", required = false) @ApiParam("comma separated IDs") final String childViewSelectedIdsListStr)
	{
		userSession.assertLoggedIn();

		final WebuiPreconditionsContext preconditionsContext = newPreconditionsContextBuilder()
				.windowId(windowId)
				.viewIdString(viewIdStr)
				.selectedIdsList(selectedIdsListStr)
				.parentViewId(parentViewIdStr)
				.parentViewSelectedIdsList(parentViewSelectedIdsListStr)
				.childViewId(childViewIdStr)
				.childViewSelectedIdsList(childViewSelectedIdsListStr)
				.build();

		return processRestController.streamDocumentRelatedProcesses(preconditionsContext)
				.filter(WebuiRelatedProcessDescriptor::isQuickAction)
				.filter(WebuiRelatedProcessDescriptor::isEnabledOrNotSilent) // only those which are enabled or not silent
				.collect(JSONDocumentActionsList.collect(newJSONOptions()));
	}

	@GetMapping("/{viewId}/{rowId}/field/{fieldName}/zoomInto")
	public JSONZoomInto getRowFieldZoomInto(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("viewId") final String viewIdStr,
			@PathVariable("rowId") final String rowId,
			@PathVariable("fieldName") final String fieldName)
	{
		// userSession.assertLoggedIn(); // NOTE: not needed because we are forwarding to windowRestController

		ViewId.ofViewIdString(viewIdStr, WindowId.fromJson(windowIdStr)); // just validate the windowId and viewId

		// TODO: atm we are forwarding all calls to windowRestController hopping the document existing and has the same ID as view's row ID.

		return windowRestController.getDocumentFieldZoomInto(windowIdStr, rowId, fieldName);
	}

	@GetMapping("/{viewId}/export/excel")
	public ResponseEntity<Resource> exportToExcel(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("viewId") final String viewIdStr,
			@RequestParam(name = "selectedIds", required = false) @ApiParam("comma separated IDs") final String selectedIdsListStr)
			throws Exception
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.ofViewIdString(viewIdStr, WindowId.fromJson(windowIdStr));

		final File tmpFile = File.createTempFile("exportToExcel", ".xls");

		try (final FileOutputStream out = new FileOutputStream(tmpFile))
		{
			ViewExcelExporter.builder()
					.view(viewsRepo.getView(viewId))
					.rowIds(DocumentIdsSelection.ofCommaSeparatedString(selectedIdsListStr))
					.layout(viewsRepo.getViewLayout(viewId.getWindowId(), JSONViewDataType.grid, ViewProfileId.NULL))
					.adLanguage(userSession.getAD_Language())
					.build()
					.export(out);
		}

		final String filename = "report.xls"; // TODO: use a better name
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		final ResponseEntity<Resource> response = new ResponseEntity<>(new InputStreamResource(new FileInputStream(tmpFile)), headers, HttpStatus.OK);
		return response;
	}
}

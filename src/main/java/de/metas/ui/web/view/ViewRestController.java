package de.metas.ui.web.view;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import de.metas.process.IProcessPreconditionsContext;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.process.ProcessRestController;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.json.JSONDocumentActionsList;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.json.JSONViewCreateRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.view.json.JSONViewLayout;
import de.metas.ui.web.view.json.JSONViewResult;
import de.metas.ui.web.view.json.JSONViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

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

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.of(userSession);
	}

	@PostMapping
	public JSONViewResult createView(
			@PathVariable(PARAM_WindowId) final String windowIdStr //
			, @RequestBody final JSONViewCreateRequest jsonRequest //
	)
	{
		userSession.assertLoggedIn();

		final ViewCreateRequest request = ViewCreateRequest.builder(jsonRequest.getWindowId(), jsonRequest.getViewType())
				.setReferencingDocumentPaths(jsonRequest.getReferencingDocumentPaths())
				.setFilters(jsonRequest.getFilters())
				.setFilterOnlyIds(jsonRequest.getFilterOnlyIds())
				.setFetchPage(jsonRequest.getQueryFirstRow(), jsonRequest.getQueryPageLength())
				.build();

		final WindowId windowIdParam = WindowId.fromNullableJson(windowIdStr);
		WindowId windowIdEffective = request.getWindowId();
		if (windowIdEffective == null)
		{
			windowIdEffective = WindowId.fromJson(windowIdStr);
		}
		else if (!Objects.equals(windowIdParam, windowIdEffective))
		{
			throw new IllegalArgumentException("Request's windowId is not matching the one from path");
		}

		final IView view = viewsRepo.createView(request);

		//
		// Fetch result if requested
		final ViewResult result;
		if (request.getQueryPageLength() > 0)
		{
			final List<DocumentQueryOrderBy> orderBys = ImmutableList.of();
			result = view.getPage(request.getQueryFirstRow(), request.getQueryPageLength(), orderBys);
		}
		else
		{
			result = ViewResult.ofView(view);
		}

		return JSONViewResult.of(result);
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
		final ViewResult result = viewsRepo.getView(viewId)
				.getPage(firstRow, pageLength, orderBysListStr);
		return JSONViewResult.of(result);
	}

	@GetMapping("/layout")
	@Deprecated
	public JSONViewLayout getViewLayout_DEPRECATED(
			@PathVariable(PARAM_WindowId) final String windowIdStr //
			, @RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType //
			)
	{
		userSession.assertLoggedIn();
//		userSession.assertDeprecatedRestAPIAllowed();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		return viewsRepo.getViewLayout(windowId, viewDataType, newJSONOptions());
	}

	@GetMapping("/{viewId}/layout")
	public JSONViewLayout getViewLayout(
			@PathVariable(PARAM_WindowId) final String windowIdStr //
			, @PathVariable("viewId") final String viewIdStr //
			, @RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType //
			)
	{
		userSession.assertLoggedIn();

		
		final ViewId viewId = ViewId.of(windowIdStr, viewIdStr);
		final IView view = viewsRepo.getView(viewId);

		final JSONViewLayout viewLayout = viewsRepo.getViewLayout(viewId.getWindowId(), viewDataType, newJSONOptions());
		viewLayout.setViewId(viewId.getViewId());
		if (view.isIncludedView())
		{
			viewLayout.setSupportAttributes(false);
		}
		else
		{
			viewLayout.setSupportAttributes(view.hasAttributesSupport());
		}

		return viewLayout;
	}

	@GetMapping("/{viewId}/byIds")
	public List<JSONViewRow> getByIds(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable("viewId") final String viewIdStr //
			, @RequestParam("ids") @ApiParam("comma separated IDs") final String idsListStr //
			)
	{
		userSession.assertLoggedIn();

		final Set<DocumentId> rowIds = DocumentId.ofCommaSeparatedString(idsListStr);

		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		final List<? extends IViewRow> result = viewsRepo.getView(viewId)
				.getByIds(rowIds);
		return JSONViewRow.ofViewRows(result);
	}

	@GetMapping("/{viewId}/filter/{filterId}/attribute/{parameterName}/typeahead")
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

	@GetMapping("/{viewId}/filter/{filterId}/attribute/{parameterName}/dropdown")
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

	private Stream<WebuiRelatedProcessDescriptor> streamAllViewActions(final String windowId, final String viewIdStr, final String selectedRowIdsAsStringList)
	{
		final ViewId viewId = ViewId.of(windowId, viewIdStr);
		final Set<DocumentId> selectedRowIds = DocumentId.ofCommaSeparatedString(selectedRowIdsAsStringList);
		final IView view = viewsRepo.getView(viewId);
		final IProcessPreconditionsContext preconditionsContext = ViewAsPreconditionsContext.newInstance(view, selectedRowIds);
		return processRestController.streamDocumentRelatedProcesses(preconditionsContext);
	}

	@GetMapping("/{viewId}/actions")
	public JSONDocumentActionsList getDocumentActions(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable("viewId") final String viewIdStr//
			, @RequestParam(name = "selectedIds", required = false) @ApiParam("comma separated IDs") final String selectedIdsListStr //
			)
	{
		userSession.assertLoggedIn();

		return streamAllViewActions(windowId, viewIdStr, selectedIdsListStr)
				.filter(WebuiRelatedProcessDescriptor::isEnabled) // only those which are enabled or not silent
				.collect(JSONDocumentActionsList.collect(newJSONOptions()));
	}

	@GetMapping("/{viewId}/quickActions")
	public JSONDocumentActionsList getDocumentQuickActions(
			@PathVariable(PARAM_WindowId) final String windowId //
			, @PathVariable("viewId") final String viewIdStr //
			, @RequestParam(name = "selectedIds", required = false) @ApiParam("comma separated IDs") final String selectedIdsListStr //
			)
	{
		userSession.assertLoggedIn();

		return streamAllViewActions(windowId, viewIdStr, selectedIdsListStr)
				.filter(WebuiRelatedProcessDescriptor::isQuickAction)
				.filter(WebuiRelatedProcessDescriptor::isEnabledOrNotSilent) // only those which are enabled or not silent
				.collect(JSONDocumentActionsList.collect(newJSONOptions()));
	}
}

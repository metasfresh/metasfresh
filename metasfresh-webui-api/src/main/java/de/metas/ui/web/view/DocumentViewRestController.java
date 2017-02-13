package de.metas.ui.web.view;

import java.util.List;

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

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.json.JSONDocumentActionsList;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.json.JSONCreateDocumentViewRequest;
import de.metas.ui.web.view.json.JSONDocumentViewLayout;
import de.metas.ui.web.view.json.JSONDocumentViewResult;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Api
@RestController
@RequestMapping(value = DocumentViewRestController.ENDPOINT)
public class DocumentViewRestController
{
	private static final String PARAM_WindowId = "windowId";

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
	private CompositeDocumentViewSelectionFactory factory;

	@Autowired
	private IDocumentViewsRepository documentViewsRepo;

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.builder()
				.setUserSession(userSession)
				.build();
	}

	@GetMapping("/layout")
	public JSONDocumentViewLayout getViewLayout(
			@PathVariable(PARAM_WindowId) final int adWindowId //
			, @RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType //
	)
	{
		userSession.assertLoggedIn();

		return factory.getViewLayout(adWindowId, viewDataType, newJSONOptions());
	}

	@PostMapping
	public JSONDocumentViewResult createView(
			@PathVariable(PARAM_WindowId) final int adWindowId //
			, @RequestBody final JSONCreateDocumentViewRequest jsonRequest //
	)
	{
		userSession.assertLoggedIn();

		int adWindowIdEffective = jsonRequest.getAD_Window_ID();
		if (adWindowIdEffective <= 0)
		{
			adWindowIdEffective = adWindowId;
		}
		else if (adWindowId > 0 && adWindowIdEffective != adWindowId)
		{
			throw new IllegalArgumentException("Request's windowId is not matching the one from path");
		}

		final IDocumentViewSelection view = documentViewsRepo.createView(jsonRequest);

		//
		// Fetch result if requested
		final DocumentViewResult result;
		if (jsonRequest.getQueryPageLength() > 0)
		{
			final List<DocumentQueryOrderBy> orderBys = ImmutableList.of();
			result = view.getPage(jsonRequest.getQueryFirstRow(), jsonRequest.getQueryPageLength(), orderBys);
		}
		else
		{
			result = DocumentViewResult.of(view);
		}

		return JSONDocumentViewResult.of(result);
	}

	@DeleteMapping("/{viewId}")
	public void deleteView(@PathVariable("viewId") final String viewId)
	{
		userSession.assertLoggedIn();

		documentViewsRepo.deleteView(viewId);
	}

	@GetMapping("/{viewId}")
	public JSONDocumentViewResult getViewData(
			@PathVariable(PARAM_WindowId) final int adWindowId //
			, @PathVariable("viewId") final String viewId//
			, @RequestParam(name = PARAM_FirstRow, required = true) @ApiParam(PARAM_FirstRow_Description) final int firstRow //
			, @RequestParam(name = PARAM_PageLength, required = true) final int pageLength //
			, @RequestParam(name = PARAM_OrderBy, required = false) @ApiParam(PARAM_OrderBy_Description) final String orderBysListStr //
	)
	{
		userSession.assertLoggedIn();

		final DocumentViewResult result = documentViewsRepo.getView(viewId)
				.assertWindowIdMatches(adWindowId)
				.getPage(firstRow, pageLength, orderBysListStr);
		return JSONDocumentViewResult.of(result);
	}

	@GetMapping("/{viewId}/filter/{filterId}/attribute/{parameterName}/typeahead")
	public JSONLookupValuesList getFilterParameterTypeahead(
			@PathVariable(PARAM_WindowId) final int adWindowId //
			, @PathVariable("viewId") final String viewId//
			, @PathVariable("filterId") final String filterId //
			, @PathVariable("parameterName") final String parameterName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertLoggedIn();

		return documentViewsRepo.getView(viewId)
				.assertWindowIdMatches(adWindowId)
				.getFilterParameterTypeahead(filterId, parameterName, query, userSession.toEvaluatee())
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@GetMapping("/{viewId}/filter/{filterId}/attribute/{parameterName}/dropdown")
	public JSONLookupValuesList getFilterParameterDropdown(
			@PathVariable(PARAM_WindowId) final int adWindowId //
			, @PathVariable("viewId") final String viewId//
			, @PathVariable("filterId") final String filterId //
			, @PathVariable("parameterName") final String parameterName //
	)
	{
		userSession.assertLoggedIn();

		return documentViewsRepo.getView(viewId)
				.assertWindowIdMatches(adWindowId)
				.getFilterParameterDropdown(filterId, parameterName, userSession.toEvaluatee())
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@GetMapping("/{viewId}/actions")
	public JSONDocumentActionsList getDocumentActions(
			@PathVariable(PARAM_WindowId) final int adWindowId //
			, @PathVariable("viewId") final String viewId//
			, @RequestParam(name = "selectedIds", required = false) @ApiParam("comma separated IDs") final String selectedIdsListStr //
	)
	{
		userSession.assertLoggedIn();

		final IDocumentViewSelection view = documentViewsRepo.getView(viewId)
				.assertWindowIdMatches(adWindowId);

		return view.streamActions(DocumentId.ofCommaSeparatedString(selectedIdsListStr))
				//
				.filter(WebuiRelatedProcessDescriptor::isEnabled) // only those which are enabled
				// TODO: replace the line above with following, after https://github.com/metasfresh/metasfresh-webui-frontend/issues/191#issuecomment-275927950 it's implemented
				// .filter(WebuiRelatedProcessDescriptor::isEnabledOrNotSilent) // only those which are enabled or not silent
				//
				.collect(JSONDocumentActionsList.collect(newJSONOptions()));
	}

	@GetMapping("/{viewId}/quickActions")
	public JSONDocumentActionsList getDocumentQuickActions(
			@PathVariable(PARAM_WindowId) final int adWindowId //
			, @PathVariable("viewId") final String viewId//
			, @RequestParam(name = "selectedIds", required = false) @ApiParam("comma separated IDs") final String selectedIdsListStr //
	)
	{
		userSession.assertLoggedIn();

		return documentViewsRepo.getView(viewId)
				.assertWindowIdMatches(adWindowId)
				.streamQuickActions(DocumentId.ofCommaSeparatedString(selectedIdsListStr))
				.collect(JSONDocumentActionsList.collect(newJSONOptions()));
	}
}

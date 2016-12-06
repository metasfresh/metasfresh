package de.metas.ui.web.window.controller;

import java.util.Collection;
import java.util.List;

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.process.json.JSONDocumentActionsList;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONCreateDocumentViewRequest;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutTab;
import de.metas.ui.web.window.datatypes.json.JSONDocumentViewResult;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSideListDescriptor;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentReference;
import de.metas.ui.web.window.model.DocumentReferencesService;
import de.metas.ui.web.window.model.DocumentViewCreateRequest;
import de.metas.ui.web.window.model.DocumentViewResult;
import de.metas.ui.web.window.model.DocumentViewsRepository;
import de.metas.ui.web.window.model.IDocumentViewSelection;
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
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/documentView";

	private static final String PARAM_WindowId = "type";
	private static final String PARAM_ViewDataType = "viewType";
	private static final String PARAM_ViewId = "viewId";
	private static final String PARAM_OrderBy = "orderBy";
	private static final String PARAM_OrderBy_Description = "Command separated field names. Use +/- prefix for ascending/descending. e.g. +C_BPartner_ID,-DateOrdered";
	//
	private static final String PARAM_FirstRow = "firstRow";
	private static final String PARAM_FirstRow_Description = "first row to fetch (starting from 0)";
	private static final String PARAM_PageLength = "pageLength";
	//
	private static final String PARAM_FilterId = "filterId";
	private static final String PARAM_FilterParameterName = "parameterName";

	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentCollection documentCollection;

	@Autowired
	private DocumentViewsRepository documentViewsRepo;

	@Autowired
	private ProcessDescriptorsFactory processDescriptorFactory;

	@Autowired
	private DocumentReferencesService documentReferencesService;

	private JSONOptions.Builder newJSONOptions()
	{
		return JSONOptions.builder()
				.setUserSession(userSession);
	}

	/**
	 * Gets view layout
	 *
	 * @param adWindowId
	 * @param viewDataType
	 */
	@RequestMapping(value = "/layout", method = RequestMethod.GET)
	public JSONDocumentLayoutTab getLayout(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType //
	)
	{
		userSession.assertLoggedIn();

		final DocumentDescriptor descriptor = documentCollection.getDocumentDescriptor(adWindowId);

		final DocumentLayoutDescriptor layout = descriptor.getLayout();
		final Collection<DocumentFilterDescriptor> filters = descriptor.getDocumentFiltersProvider().getAll();

		final JSONOptions jsonOpts = newJSONOptions().build();

		switch (viewDataType)
		{
			case grid:
			{
				final DocumentLayoutDetailDescriptor gridLayout = layout.getGridView();
				return JSONDocumentLayoutTab.of(gridLayout, filters, jsonOpts);
			}
			case list:
			{
				final DocumentLayoutSideListDescriptor sideListLayout = layout.getSideList();
				return JSONDocumentLayoutTab.ofSideListLayout(sideListLayout, filters, jsonOpts);
			}
			default:
				throw new IllegalArgumentException("Invalid " + PARAM_ViewDataType + "=" + viewDataType);
		}
	}

	/**
	 * Create view
	 */
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@Deprecated
	public JSONDocumentViewResult createView_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType //
			, @RequestParam(name = PARAM_FirstRow, required = false, defaultValue = "0") @ApiParam(PARAM_FirstRow_Description) final int firstRow //
			, @RequestParam(name = PARAM_PageLength, required = false, defaultValue = "0") final int pageLength //
			, @RequestBody(required = false) final List<JSONDocumentFilter> jsonFilters //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		final JSONCreateDocumentViewRequest jsonRequest = JSONCreateDocumentViewRequest.of(adWindowId, viewDataType, jsonFilters, firstRow, pageLength);
		return createView(jsonRequest);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public JSONDocumentViewResult createView(@RequestBody final JSONCreateDocumentViewRequest jsonRequest)
	{
		userSession.assertLoggedIn();

		final int adWindowId = jsonRequest.getAD_Window_ID();
		final DocumentEntityDescriptor entityDescriptor = documentCollection.getDocumentEntityDescriptor(adWindowId);

		final JSONViewDataType viewDataType = jsonRequest.getViewType();
		final Characteristic requiredFieldCharacteristic = viewDataType.getRequiredFieldCharacteristic();
		final List<DocumentFieldDescriptor> fields = entityDescriptor.getFieldsWithCharacteristic(requiredFieldCharacteristic);
		if (fields.isEmpty())
		{
			throw new IllegalStateException("No fields were found for " + PARAM_ViewDataType + ": " + viewDataType + "(required field characteristic: " + requiredFieldCharacteristic + ")");
		}
		
		final DocumentViewCreateRequest.Builder request = DocumentViewCreateRequest.builder()
				.setEntityDescriptor(entityDescriptor)
				.setViewFields(fields)
				.addFilters(JSONDocumentFilter.unwrapList(jsonRequest.getFilters(), entityDescriptor.getFiltersProvider()));

		if(jsonRequest.getReferencing() != null)
		{
			final Document referencingDocument = documentCollection.getDocument(jsonRequest.getReferencing().toDocumentPath());
			final DocumentReference reference = documentReferencesService.getDocumentReference(referencingDocument, adWindowId);
			request.addStickyFilter(reference.getFilter());
		}

		final IDocumentViewSelection view = documentViewsRepo.createView(request.build());

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

	@RequestMapping(value = "/{" + PARAM_ViewId + "}", method = RequestMethod.DELETE)
	public void deleteView(@PathVariable(PARAM_ViewId) final String viewId)
	{
		userSession.assertLoggedIn();

		documentViewsRepo.deleteView(viewId);
	}

	@RequestMapping(value = "/{" + PARAM_ViewId + "}", method = RequestMethod.GET)
	public JSONDocumentViewResult browseView(
			@PathVariable(PARAM_ViewId) final String viewId//
			, @RequestParam(name = PARAM_FirstRow, required = true) @ApiParam(PARAM_FirstRow_Description) final int firstRow //
			, @RequestParam(name = PARAM_PageLength, required = true) final int pageLength //
			, @RequestParam(name = PARAM_OrderBy, required = false) @ApiParam(PARAM_OrderBy_Description) final String orderBysListStr //
	)
	{
		userSession.assertLoggedIn();

		final List<DocumentQueryOrderBy> orderBys = DocumentQueryOrderBy.parseOrderBysList(orderBysListStr);

		final IDocumentViewSelection view = documentViewsRepo.getView(viewId);
		final DocumentViewResult result = view.getPage(firstRow, pageLength, orderBys);
		return JSONDocumentViewResult.of(result);
	}

	@RequestMapping(value = "/filters/parameter/typeahead", method = RequestMethod.GET)
	public JSONLookupValuesList typeahead(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_FilterId, required = true) final String filterId //
			, @RequestParam(name = PARAM_FilterParameterName, required = true) final String parameterName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertLoggedIn();

		final Evaluatee ctx = Evaluatees.ofCtx(userSession.getCtx());

		return documentCollection
				.getDocumentDescriptor(adWindowId)
				.getDocumentFiltersProvider()
				.getByFilterId(filterId)
				.getParameterByName(parameterName)
				.getLookupDataSource()
				.findEntities(ctx, query)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@RequestMapping(value = "/filters/parameter/dropdown", method = RequestMethod.GET)
	public JSONLookupValuesList dropdown(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_FilterId, required = true) final String filterId //
			, @RequestParam(name = PARAM_FilterParameterName, required = true) final String parameterName //
	)
	{
		userSession.assertLoggedIn();

		final Evaluatee ctx = Evaluatees.ofCtx(userSession.getCtx());

		return documentCollection
				.getDocumentDescriptor(adWindowId)
				.getDocumentFiltersProvider()
				.getByFilterId(filterId)
				.getParameterByName(parameterName)
				.getLookupDataSource()
				.findEntities(ctx)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@RequestMapping(value = "/{" + PARAM_ViewId + "}/actions", method = RequestMethod.GET)
	public JSONDocumentActionsList getDocumentActions(
			@PathVariable(PARAM_ViewId) final String viewId //
			, @RequestParam(name = "selectedIds", required = false) @ApiParam("comma separated IDs") final String selectedIdsListStr //
	)
	{
		userSession.assertLoggedIn();

		final IDocumentViewSelection view = documentViewsRepo.getView(viewId);

		final IUserRolePermissions permissions = userSession.getUserRolePermissions();

		return processDescriptorFactory.getDocumentRelatedProcesses(view.getTableName())
				.stream()
				.filter(processDescriptor -> processDescriptor.isExecutionGranted(permissions))
				// .filter(processDescriptor -> processDescriptor.isPreconditionsApplicable(preconditionsContext))
				.collect(JSONDocumentActionsList.collect(newJSONOptions().build()));
	}

}

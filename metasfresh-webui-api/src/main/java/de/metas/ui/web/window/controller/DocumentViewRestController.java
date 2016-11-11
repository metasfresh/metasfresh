package de.metas.ui.web.window.controller;

import java.util.Collection;
import java.util.List;

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
import de.metas.ui.web.login.LoginService;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutTab;
import de.metas.ui.web.window.datatypes.json.JSONDocumentViewResult;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSideListDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptor;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
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
	private LoginService loginService;

	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;

	@Autowired
	private DocumentViewsRepository documentViewsRepo;

	private JSONOptions.Builder newJSONFilteringOptions()
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
	public JSONDocumentLayoutTab layout(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType //

	)
	{
		loginService.assertLoggedIn();

		final DocumentDescriptor descriptor = documentDescriptorFactory.getDocumentDescriptor(adWindowId);

		final DocumentLayoutDescriptor layout = descriptor.getLayout();
		final Collection<DocumentFilterDescriptor> filters = descriptor.getDocumentFiltersProvider().getAll();

		final JSONOptions jsonOpts = newJSONFilteringOptions().build();

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
	public JSONDocumentViewResult createView(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType //
			, @RequestParam(name = PARAM_FirstRow, required = false, defaultValue = "0") @ApiParam(PARAM_FirstRow_Description) final int firstRow //
			, @RequestParam(name = PARAM_PageLength, required = false, defaultValue = "0") final int pageLength //
			, @RequestBody(required = false) final List<JSONDocumentFilter> jsonFilters //
	)
	{
		loginService.assertLoggedIn();

		final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory
				.getDocumentDescriptor(adWindowId)
				.getEntityDescriptor();

		final Characteristic requiredFieldCharacteristic = viewDataType.getRequiredFieldCharacteristic();
		final List<DocumentFieldDescriptor> fields = entityDescriptor.getFieldsWithCharacteristic(requiredFieldCharacteristic);
		if (fields.isEmpty())
		{
			throw new IllegalStateException("No fields were found for " + PARAM_ViewDataType + ": " + viewDataType + "(required field characteristic: " + requiredFieldCharacteristic + ")");
		}

		final DocumentFilterDescriptorsProvider filterDescriptorProvider = entityDescriptor.getFiltersProvider();

		final DocumentQuery query = DocumentQuery.builder(entityDescriptor)
				.setViewFields(fields)
				.addFilters(JSONDocumentFilter.unwrapList(jsonFilters, filterDescriptorProvider))
				.build();

		final IDocumentViewSelection view = documentViewsRepo.createView(query);

		//
		// Fetch result if requested
		final DocumentViewResult result;
		if (pageLength > 0)
		{
			final List<DocumentQueryOrderBy> orderBys = ImmutableList.of();
			result = view.getPage(firstRow, pageLength, orderBys);
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
		loginService.assertLoggedIn();

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
		loginService.assertLoggedIn();

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
		loginService.assertLoggedIn();

		final Evaluatee ctx = Evaluatees.ofCtx(userSession.getCtx());

		return documentDescriptorFactory
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
		loginService.assertLoggedIn();

		final Evaluatee ctx = Evaluatees.ofCtx(userSession.getCtx());

		return documentDescriptorFactory
				.getDocumentDescriptor(adWindowId)
				.getDocumentFiltersProvider()
				.getByFilterId(filterId)
				.getParameterByName(parameterName)
				.getLookupDataSource()
				.findEntities(ctx)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}
}

package de.metas.ui.web.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.json.JSONDocumentViewLayout;
import de.metas.ui.web.view.json.JSONDocumentViewResult;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;
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
@RequestMapping(value = WindowRestController.ENDPOINT)
@Deprecated
public class DocumentViewRestControllerDeprecated
{
	private static final String PARAM_WindowId = WebConfig.PARAM_WindowId;
	private static final String PARAM_ViewId = "viewId";
	private static final String PARAM_ViewDataType = "viewType";
	private static final String PARAM_FirstRow = "firstRow";
	private static final String PARAM_FirstRow_Description = "first row to fetch (starting from 0)";
	private static final String PARAM_PageLength = "pageLength";
	private static final String PARAM_OrderBy = "orderBy";
	private static final String PARAM_OrderBy_Description = "Command separated field names. Use +/- prefix for ascending/descending. e.g. +C_BPartner_ID,-DateOrdered";

	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentViewRestControllerDeprecated2 documentViewController;

	@RequestMapping(value = "/viewLayout", method = RequestMethod.GET)
	@Deprecated
	public JSONDocumentViewLayout getViewLayout_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType //

	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return documentViewController.getLayout(adWindowId, viewDataType);
	}

	/**
	 * Create view
	 */
	@RequestMapping(value = "/view", method = RequestMethod.PUT)
	@Deprecated
	public JSONDocumentViewResult createView_DEPRECATED(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType //
			, @RequestParam(name = PARAM_FirstRow, required = false, defaultValue = "0") @ApiParam(PARAM_FirstRow_Description) final int firstRow //
			, @RequestParam(name = PARAM_PageLength, required = false, defaultValue = "0") final int pageLength //
			, @RequestBody final List<JSONDocumentFilter> jsonFilters //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return documentViewController.createView_DEPRECATED(adWindowId, viewDataType, firstRow, pageLength, jsonFilters);
	}

	@RequestMapping(value = "/view/{" + PARAM_ViewId + "}", method = RequestMethod.DELETE)
	@Deprecated
	public void deleteView_DEPRECATED(@PathVariable(PARAM_ViewId) final String viewId)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		documentViewController.deleteView(viewId);
	}

	@RequestMapping(value = "/view/{" + PARAM_ViewId + "}", method = RequestMethod.GET)
	@Deprecated
	public JSONDocumentViewResult browseView_DEPRECATED(
			@PathVariable(PARAM_ViewId) final String viewId//
			, @RequestParam(name = PARAM_FirstRow, required = true) @ApiParam(PARAM_FirstRow_Description) final int firstRow //
			, @RequestParam(name = PARAM_PageLength, required = true) final int pageLength //
			, @RequestParam(name = PARAM_OrderBy, required = false) @ApiParam(PARAM_OrderBy_Description) final String orderBysListStr //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return documentViewController.browseView(viewId, firstRow, pageLength, orderBysListStr);
	}
}

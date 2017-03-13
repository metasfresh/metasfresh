package de.metas.ui.web.view.json;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.view.DocumentViewResult;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;

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

@SuppressWarnings("serial")
public final class JSONDocumentViewResult implements Serializable
{
	public static final JSONDocumentViewResult of(final DocumentViewResult result)
	{
		return new JSONDocumentViewResult(result);
	}

	//
	// View informations
	@JsonProperty(value = "viewId", index = 10)
	private final String viewId;

	@JsonProperty(value = "type", index = 20)
	private final int AD_Window_ID;

	@JsonProperty(value = "size", index = 30)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Long size;

	@JsonProperty(value = "orderBy", index = 70)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentViewOrderBy> orderBy;

	@JsonProperty(value = "filters", index = 60)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentFilter> filters;

	//
	// Page informations (optional)
	// NOTE: important to distinguish between "empty" and "null", i.e.
	// * empty => frontend will consider there is a page loaded and it's empty
	// * null (excluded from JSON) => frontend will consider the page is not loaded, so it won't update the result on it's side
	// see https://github.com/metasfresh/metasfresh-webui-frontend/issues/330
	// 
	@JsonProperty(value = "result", index = 1000)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final List<JSONDocument> result;

	@JsonProperty(value = "firstRow", index = 40)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer firstRow;

	@JsonProperty(value = "pageLength", index = 50)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer pageLength;
	
	//
	// Query limit informations
	@JsonProperty("queryLimitHit")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean queryLimitHit;
	@JsonProperty("queryLimit")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer queryLimit;

	//
	// Debug properties
	private final Map<String, Object> debugPropeties;

	public JSONDocumentViewResult(final DocumentViewResult viewResult)
	{
		super();

		//
		// View informations
		final IDocumentViewSelection view = viewResult.getView();
		viewId = view.getViewId();
		AD_Window_ID = view.getAD_Window_ID();

		final long size = view.size();
		this.size = size >= 0 ? size : null;

		filters = JSONDocumentFilter.ofList(viewResult.getFilters());
		orderBy = JSONDocumentViewOrderBy.ofList(viewResult.getOrderBys());

		//
		// Page informations
		if (viewResult.isPageLoaded())
		{
			result = JSONDocument.ofDocumentViewList(viewResult.getPage());
			firstRow = viewResult.getFirstRow();
			pageLength = viewResult.getPageLength();
		}
		else
		{
			result = null;
			firstRow = null;
			pageLength = null;
		}
		
		//
		// Query limit informations
		queryLimit = viewResult.getQueryLimit() > 0 ? viewResult.getQueryLimit() : null;
		queryLimitHit = viewResult.isQueryLimitHit() ? Boolean.TRUE : null;

		//
		// Debugging informations
		if (WindowConstants.isProtocolDebugging())
		{
			debugPropeties = ImmutableMap.<String, Object> of("debug-view-info", view.toString());
		}
		else
		{
			debugPropeties = ImmutableMap.of();
		}
	}

	@JsonCreator
	private JSONDocumentViewResult( //
			@JsonProperty("viewId") final String viewId //
			, @JsonProperty("type") final int adWindowId //
			, @JsonProperty("size") final Long size //
			, @JsonProperty("filters") final List<JSONDocumentFilter> filters //
			, @JsonProperty("orderBy") final List<JSONDocumentViewOrderBy> orderBy //
			//
			, @JsonProperty("result") final List<JSONDocument> result //
			, @JsonProperty("firstRow") final Integer firstRow //
			, @JsonProperty("pageLength") final Integer pageLength //
			//
			, @JsonProperty("queryLimit") final Integer queryLimit //
			, @JsonProperty("queryLimitHit") final Boolean queryLimitHit //
	)
	{
		super();

		//
		// View informations
		this.viewId = viewId;
		AD_Window_ID = adWindowId;
		this.size = size;
		this.filters = filters == null ? ImmutableList.of() : filters;
		this.orderBy = orderBy == null ? ImmutableList.of() : orderBy;

		//
		// Page informations
		this.result = result;
		this.firstRow = firstRow;
		this.pageLength = pageLength;
		
		//
		// Query limit hit
		this.queryLimit = queryLimit;
		this.queryLimitHit = queryLimitHit;

		//
		// Debug informations
		// NOTE: initialize as mutable.
		// The actual debug properties will be loaded by calling #putDebugProperty(...)
		debugPropeties = new HashMap<>();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				//
				// View info
				.add("viewId", viewId)
				.add("AD_Window_ID", AD_Window_ID)
				.add("size", size)
				//
				// Page info
				.add("firstRow", firstRow)
				.add("pageLength", pageLength)
				.add("result", result)
				//
				.toString();
	}

	@JsonAnyGetter
	public Map<String, Object> getDebugPropeties()
	{
		return debugPropeties;
	}

	@JsonAnySetter
	private void putDebugProperty(final String name, final Object value)
	{
		debugPropeties.put(name, value);
	}
}

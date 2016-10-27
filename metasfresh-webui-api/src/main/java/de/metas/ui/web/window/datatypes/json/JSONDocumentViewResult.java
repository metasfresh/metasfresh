package de.metas.ui.web.window.datatypes.json;

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

import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;
import de.metas.ui.web.window.model.DocumentViewResult;
import de.metas.ui.web.window.model.IDocumentViewSelection;

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

@SuppressWarnings("serial")
public final class JSONDocumentViewResult implements Serializable
{
	public static final JSONDocumentViewResult of(final DocumentViewResult result)
	{
		return new JSONDocumentViewResult(result);
	}

	@JsonProperty(value = "viewId", index = 10)
	private final String viewId;

	@JsonProperty(value = "type", index = 20)
	private final int AD_Window_ID;

	@JsonProperty(value = "size", index = 30)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Long size;

	//
	// Result
	@JsonProperty(value = "firstRow", index = 40)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer firstRow;

	@JsonProperty(value = "pageLength", index = 50)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer pageLength;

	@JsonProperty(value = "filters", index = 60)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentFilter> filters;

	@JsonProperty(value = "orderBy", index = 70)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentViewOrderBy> orderBy;

	@JsonProperty(value = "result", index = 80)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocument> result;

	private final Map<String, Object> debugPropeties;

	public JSONDocumentViewResult(final DocumentViewResult viewResult)
	{
		super();
		final IDocumentViewSelection view = viewResult.getView();
		viewId = view.getViewId();
		AD_Window_ID = view.getAD_Window_ID();

		final long size = view.size();
		this.size = size >= 0 ? size : null;

		firstRow = viewResult.getFirstRow();
		pageLength = viewResult.getPageLength();

		filters = JSONDocumentFilter.ofList(viewResult.getFilters());
		orderBy = JSONDocumentViewOrderBy.ofList(viewResult.getOrderBys());

		result = JSONDocument.ofDocumentViewList(viewResult.getPage());

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
			, @JsonProperty("result") final List<JSONDocument> result //
			, @JsonProperty("firstRow") final Integer firstRow //
			, @JsonProperty("pageLength") final Integer pageLength //
			, @JsonProperty(value = "filters") final List<JSONDocumentFilter> filters //
			, @JsonProperty("orderBy") final List<JSONDocumentViewOrderBy> orderBy //
	)
	{
		super();
		this.viewId = viewId;
		AD_Window_ID = adWindowId;
		this.size = size;

		this.firstRow = firstRow;
		this.pageLength = pageLength;
		
		this.filters = filters == null ? ImmutableList.of() : filters;
		this.orderBy = orderBy == null ? ImmutableList.of() : orderBy;

		this.result = result;

		debugPropeties = new HashMap<>();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("viewId", viewId)
				.add("AD_Window_ID", AD_Window_ID)
				.add("size", size)
				.add("firstRow", firstRow)
				.add("pageLength", pageLength)
				.add("result", result)
				.toString();
	}

	public String getViewId()
	{
		return viewId;
	}

	public Long getSize()
	{
		return size;
	}

	public List<JSONDocument> getResult()
	{
		return result;
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

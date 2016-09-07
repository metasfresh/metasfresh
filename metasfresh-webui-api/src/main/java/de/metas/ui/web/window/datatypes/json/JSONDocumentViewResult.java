package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.model.IDocumentView;
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
	public static final JSONDocumentViewResult of(
			final IDocumentViewSelection view //
			, final Integer firstRow //
			, final Integer pageLength //
			, final List<IDocumentView> result //
	)
	{
		return new JSONDocumentViewResult(
				view //
				, firstRow //
				, pageLength //
				, JSONDocument.ofDocumentViewList(result) //
		);
	}

	public static final JSONDocumentViewResult of(
			final IDocumentViewSelection view //
	)
	{
		final Integer firstRow = null;
		final Integer pageLength = null;
		final List<JSONDocument> result = null;
		return new JSONDocumentViewResult(view, firstRow, pageLength, result);
	}

	@JsonProperty("viewId")
	private final String viewId;
	@JsonProperty("size")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer size;

	//
	// Result
	@JsonProperty("firstRow")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer firstRow;

	@JsonProperty("pageLength")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer pageLength;

	@JsonProperty("result")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocument> result;

	private final Map<String, Object> debugPropeties;

	public JSONDocumentViewResult(
			final IDocumentViewSelection view //
			, final Integer firstRow //
			, final Integer pageLength //
			, final List<JSONDocument> result //
	)
	{
		super();
		viewId = view.getId();

		final int size = view.size();
		this.size = size >= 0 ? size : null;

		this.firstRow = firstRow;
		this.pageLength = pageLength;
		this.result = result;

		if (WindowConstants.isProtocolDebugging())
		{
			debugPropeties = ImmutableMap.<String, Object> of("debug-view-info", view.toString());
		}
		else
		{
			debugPropeties = ImmutableMap.of();
		}
	}

	public JSONDocumentViewResult( //
			@JsonProperty("viewId") final String viewId //
			, @JsonProperty("size") final Integer size //
			, @JsonProperty("result") final List<JSONDocument> result //
			, @JsonProperty("firstRow") final Integer firstRow //
			, @JsonProperty("pageLength") final Integer pageLength //
	)
	{
		super();
		this.viewId = viewId;
		this.size = size;

		this.firstRow = firstRow;
		this.pageLength = pageLength;
		this.result = result;

		debugPropeties = new HashMap<>();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("viewId", viewId)
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

	public Integer getSize()
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

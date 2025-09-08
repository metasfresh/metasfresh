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

package de.metas.ui.web.view.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.comments.ViewRowCommentsSummary;
import de.metas.ui.web.document.filter.json.JSONDocumentFilter;
import de.metas.ui.web.document.filter.json.JSONStickyDocumentFilter;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowOverrides;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONViewResult
{
	public static JSONViewResult of(
			final ViewResult viewResult,
			final IViewRowOverrides rowOverrides,
			final JSONOptions jsonOpts,
			@NonNull final ViewRowCommentsSummary viewRowCommentsSummary)
	{
		final List<? extends JSONViewRowBase> jsonRows;
		if (viewResult.isPageLoaded())
		{
			final List<IViewRow> rows = viewResult.getPage();
			jsonRows = JSONViewRow.ofViewRows(rows, rowOverrides, jsonOpts, viewRowCommentsSummary);
		}
		else
		{
			jsonRows = null;
		}
		return new JSONViewResult(viewResult, jsonRows, jsonOpts);
	}

	public static JSONViewResult of(final ViewResult viewResult, final List<? extends JSONViewRowBase> rows, final JSONOptions jsonOpts)
	{
		return new JSONViewResult(viewResult, rows, jsonOpts);
	}

	//
	// View informations
	@JsonProperty("type")
	@Deprecated
	private final WindowId type;
	@JsonProperty("windowId")
	private final WindowId windowId;
	//
	@JsonProperty("viewId")
	private final String viewId;

	@JsonProperty("profileId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final ViewProfileId profileId;

	@JsonProperty("parentWindowId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final WindowId parentWindowId;
	//
	@JsonProperty("parentViewId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String parentViewId;

	/**
	 * View description (e.g. Manufacturing order 12345). See https://github.com/metasfresh/metasfresh-webui-api/issues/433
	 */
	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String description;

	@JsonProperty("headerProperties")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONViewHeaderProperties headerProperties;

	@JsonProperty("size")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Long size;

	@JsonProperty("orderBy")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONViewOrderBy> orderBy;

	@JsonProperty("staticFilters")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONStickyDocumentFilter> staticFilters;

	@JsonProperty("filters")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentFilter> filters;

	//
	// Page informations (optional)
	// NOTE: important to distinguish between "empty" and "null", i.e.
	// * empty => frontend will consider there is a page loaded and it's empty
	// * null (excluded from JSON) => frontend will consider the page is not loaded, so it won't update the result on it's side
	// see https://github.com/metasfresh/metasfresh-webui-frontend/issues/330
	//
	@JsonProperty("result")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final List<? extends JSONViewRowBase> result;

	@JsonProperty("columnsByFieldName")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Map<String, JSONViewResultColumn> columnsByFieldName;

	@JsonProperty("firstRow")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer firstRow;

	@JsonProperty("pageLength")
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

	@JsonProperty("emptyResultText")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String emptyResultText;

	@JsonProperty("emptyResultHint")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String emptyResultHint;

	private JSONViewResult(@NonNull final ViewResult viewResult, @Nullable final List<? extends JSONViewRowBase> rows, @NonNull final JSONOptions jsonOpts)
	{
		//
		// View informations
		final ViewId viewId = viewResult.getViewId();
		this.viewId = viewId.getViewId();
		windowId = viewId.getWindowId();
		type = windowId;
		profileId = viewResult.getProfileId();

		final ViewId parentViewId = viewResult.getParentViewId();
		parentWindowId = parentViewId == null ? null : parentViewId.getWindowId();
		this.parentViewId = parentViewId == null ? null : parentViewId.getViewId();

		final String adLanguage = jsonOpts.getAdLanguage();
		description = viewResult.getViewDescription(adLanguage);
		headerProperties = JSONViewHeaderProperties.of(viewResult.getHeaderProperties(), adLanguage);

		final long size = viewResult.getSize();
		this.size = size >= 0 ? size : null;

		staticFilters = JSONStickyDocumentFilter.ofStickyFiltersList(viewResult.getStickyFilters(), adLanguage);
		filters = JSONDocumentFilter.ofList(viewResult.getFilters(), jsonOpts);

		orderBy = JSONViewOrderBy.ofList(viewResult.getOrderBys());

		//
		// Page informations
		if (rows != null)
		{
			result = rows;
			firstRow = viewResult.getFirstRow();
			pageLength = viewResult.getPageLength();
		}
		else
		{
			result = null;
			firstRow = null;
			pageLength = null;
		}

		columnsByFieldName = viewResult.getColumnInfosByFieldName()
				.values()
				.stream()
				.map(JSONViewResultColumn::of)
				.collect(GuavaCollectors.toImmutableMapByKey(JSONViewResultColumn::getFieldName));

		//
		// Query limit informations
		queryLimit = viewResult.getQueryLimit() > 0 ? viewResult.getQueryLimit() : null;
		queryLimitHit = viewResult.isQueryLimitHit() ? Boolean.TRUE : null;

		if(viewResult.getEmptyReason() != null)
		{
			this.emptyResultText = StringUtils.trimBlankToNull(viewResult.getEmptyReason().getText().translate(jsonOpts.getAdLanguage()));
			this.emptyResultHint = StringUtils.trimBlankToNull(viewResult.getEmptyReason().getHint().translate(jsonOpts.getAdLanguage()));
		}
		else
		{
			this.emptyResultText = null;
			this.emptyResultHint = null;
		}
	}

	@JsonCreator
	private JSONViewResult( //
							@JsonProperty("windowId") final WindowId windowId,
							@JsonProperty("viewId") final String viewId,
							@JsonProperty("profileId") final ViewProfileId profileId,
							//
							@JsonProperty("parentWindowId") final WindowId parentWindowId,
							@JsonProperty("parentViewId") final String parentViewId,
							//
							@JsonProperty("description") final String description,
							@JsonProperty("headerProperties") final JSONViewHeaderProperties headerProperties,
							//
							@JsonProperty("size") final Long size,
							@JsonProperty("staticFilters") final List<JSONStickyDocumentFilter> staticFilters,
							@JsonProperty("filters") final List<JSONDocumentFilter> filters,
							@JsonProperty("orderBy") final List<JSONViewOrderBy> orderBy,
							//
							@JsonProperty("result") final List<JSONViewRowBase> result,
							@JsonProperty("columnsByFieldName") final Map<String, JSONViewResultColumn> columnsByFieldName,
							@JsonProperty("firstRow") final Integer firstRow,
							@JsonProperty("pageLength") final Integer pageLength,
							//
							@JsonProperty("queryLimit") final Integer queryLimit,
							@JsonProperty("queryLimitHit") final Boolean queryLimitHit,
							@JsonProperty("emptyResultText") final String emptyResultText,
							@JsonProperty("emptyResultHint") final String emptyResultHint)
	{
		//
		// View informations
		this.viewId = viewId;
		type = windowId;
		this.windowId = windowId;
		this.profileId = profileId;
		//
		this.parentWindowId = parentWindowId;
		this.parentViewId = parentViewId;
		//
		this.description = description;
		this.headerProperties = headerProperties;
		//
		this.size = size;
		this.filters = filters == null ? ImmutableList.of() : filters;
		this.staticFilters = staticFilters == null ? ImmutableList.of() : staticFilters;
		this.orderBy = orderBy == null ? ImmutableList.of() : orderBy;

		//
		// Page informations
		this.result = result;
		this.columnsByFieldName = columnsByFieldName;
		this.firstRow = firstRow;
		this.pageLength = pageLength;

		//
		// Query limit hit
		this.queryLimit = queryLimit;
		this.queryLimitHit = queryLimitHit;
		this.emptyResultText = emptyResultText;
		this.emptyResultHint = emptyResultHint;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				//
				// View info
				.add("viewId", viewId)
				.add("AD_Window_ID", windowId)
				.add("size", size)
				//
				// Page info
				.add("firstRow", firstRow)
				.add("pageLength", pageLength)
				.add("result", result)
				//
				.toString();
	}
}

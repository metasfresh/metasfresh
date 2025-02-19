package de.metas.ui.web.view;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.List;

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

@Immutable
public final class ViewResult
{
	/**
	 * Creates a view result having given loaded page
	 */
	public static ViewResult ofViewAndPage(
			final IView view,
			final int firstRow,
			final int pageLength,
			final DocumentQueryOrderByList orderBys,
			final List<? extends IViewRow> page)
	{
		return builder()
				.view(view)
				.firstRow(firstRow)
				.pageLength(pageLength)
				.orderBys(orderBys)
				.rows(page)
				.build();
	}

	/**
	 * Creates a view result without any loaded page.
	 */
	public static ViewResult ofView(final IView view)
	{
		return new ViewResult(view);
	}

	//
	// View info
	@Getter private final ViewId viewId;
	@Getter private final ViewProfileId profileId;
	@Getter private final ViewId parentViewId;
	@Nullable private final ITranslatableString viewDescription;
	@NonNull @Getter private final ViewHeaderProperties headerProperties;
	@Getter private final long size;
	@Getter private final int queryLimit;
	@Getter private final boolean queryLimitHit;

	@Nullable @Getter private final EmptyReason emptyReason;

	@Getter private final DocumentFilterList stickyFilters;
	@Getter private final DocumentFilterList filters;
	@Getter private final DocumentQueryOrderByList orderBys;

	//
	// Page info
	@Getter private final int firstRow;
	@Getter private final int pageLength;
	@Nullable private final ImmutableList<DocumentId> rowIds;
	@Nullable private final ImmutableList<IViewRow> page;
	@Getter private final ImmutableMap<String, ViewResultColumn> columnInfosByFieldName;

	/**
	 * +
	 * View and loaded page constructor
	 */
	@Builder
	private ViewResult(
			@NonNull final IView view,
			@NonNull final Integer firstRow,
			@NonNull final Integer pageLength,
			@NonNull final DocumentQueryOrderByList orderBys,
			@Nullable final List<DocumentId> rowIds,
			@Nullable final List<? extends IViewRow> rows,
			@Nullable final EmptyReason emptyReason,
			@Nullable final List<ViewResultColumn> columnInfos)
	{
		this.viewId = view.getViewId();
		this.profileId = view.getProfileId();
		this.parentViewId = view.getParentViewId();
		this.viewDescription = view.getDescription();
		this.headerProperties = view.getHeaderProperties() != null ? view.getHeaderProperties() : ViewHeaderProperties.EMPTY;
		this.size = view.size();
		this.queryLimit = view.getQueryLimit();
		this.queryLimitHit = view.isQueryLimitHit();
		this.emptyReason = emptyReason;

		stickyFilters = view.getStickyFilters();
		filters = view.getFilters();
		this.orderBys = orderBys;

		//
		// Page
		if (rowIds == null && rows == null)
		{
			throw new IllegalArgumentException("rowIds or rows shall not be null");
		}
		this.firstRow = firstRow;
		this.pageLength = pageLength;
		this.rowIds = rowIds != null ? ImmutableList.copyOf(rowIds) : null;
		this.page = rows != null ? ImmutableList.copyOf(rows) : null;
		this.columnInfosByFieldName = columnInfos != null ? Maps.uniqueIndex(columnInfos, ViewResultColumn::getFieldName)
				: ImmutableMap.of();
	}

	/**
	 * View (WITHOUT loaded page) constructor
	 */
	private ViewResult(@NonNull final IView view)
	{
		this.viewId = view.getViewId();
		this.profileId = view.getProfileId();
		this.parentViewId = view.getParentViewId();
		this.viewDescription = view.getDescription();
		this.headerProperties = view.getHeaderProperties() != null ? view.getHeaderProperties() : ViewHeaderProperties.EMPTY;
		this.size = view.size();
		this.queryLimit = view.getQueryLimit();
		this.queryLimitHit = view.isQueryLimitHit();
		this.emptyReason = view.getEmptyReason();

		stickyFilters = view.getStickyFilters();
		filters = view.getFilters();
		orderBys = view.getDefaultOrderBys();

		//
		// Page
		firstRow = 0;
		pageLength = 0;
		rowIds = null;
		page = null;
		columnInfosByFieldName = ImmutableMap.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				//
				// View info
				.add("viewId", viewId)
				.add("profileId", profileId)
				.add("filters", filters)
				.add("orderBys", orderBys)
				//
				// Page info
				.add("firstRow", firstRow)
				.add("pageLength", pageLength)
				.add("page", page)
				//
				.toString();
	}

	@Nullable
	public String getViewDescription(final String adLanguage)
	{
		if (viewDescription == null)
		{
			return null;
		}
		final String viewDescriptionStr = viewDescription.translate(adLanguage);
		return !Check.isBlank(viewDescriptionStr) ? viewDescriptionStr : null;
	}

	public boolean isPageLoaded()
	{
		return page != null;
	}

	public List<DocumentId> getRowIds()
	{
		if (rowIds != null)
		{
			return rowIds;
		}

		return getPage().stream().map(IViewRow::getId).collect(ImmutableList.toImmutableList());
	}

	public boolean isEmpty()
	{
		return getPage().isEmpty();
	}

	/**
	 * @return loaded page
	 * @throws IllegalStateException if the page is not loaded, see {@link #isPageLoaded()}
	 */
	public List<IViewRow> getPage()
	{
		if (page == null)
		{
			throw new IllegalStateException("page not loaded for " + this);
		}
		return page;
	}
}

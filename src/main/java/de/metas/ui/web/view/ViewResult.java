package de.metas.ui.web.view;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;

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
			final IView view //
			, final int firstRow //
			, final int pageLength //
			, final List<DocumentQueryOrderBy> orderBys //
			, final List<? extends IViewRow> page //
	)
	{
		final List<DocumentId> rowIds = null;
		return new ViewResult(view, firstRow, pageLength, orderBys, rowIds, page);
	}

	public static ViewResult ofViewAndRowIds(
			final IView view //
			, final int firstRow //
			, final int pageLength //
			, final List<DocumentQueryOrderBy> orderBys //
			, final List<DocumentId> rowIds //
	)
	{
		final List<? extends IViewRow> page = null; // N/A
		return new ViewResult(view, firstRow, pageLength, orderBys, rowIds, page);
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
	private final ViewId viewId;
	private final ViewId parentViewId;
	private final ITranslatableString viewDescription;
	private final long size;
	private final int queryLimit;
	private final boolean queryLimitHit;

	private final List<DocumentFilter> stickyFilters;
	private final List<DocumentFilter> filters;
	private final List<DocumentQueryOrderBy> orderBys;

	//
	// Page info
	private final int firstRow;
	private final int pageLength;
	private final List<DocumentId> rowIds;
	private final List<IViewRow> page;

	/**
	 * View and loaded page constructor
	 * 
	 * @param rowIds
	 */
	private ViewResult(
			final IView view,
			final int firstRow,
			final int pageLength,
			final List<DocumentQueryOrderBy> orderBys,
			final List<DocumentId> rowIds,
			final List<? extends IViewRow> page)
	{
		super();
		this.viewId = view.getViewId();
		this.parentViewId = view.getParentViewId();
		this.viewDescription = view.getDescription();
		this.size = view.size();
		this.queryLimit = view.getQueryLimit();
		this.queryLimitHit = view.isQueryLimitHit();

		stickyFilters = ImmutableList.copyOf(view.getStickyFilters());
		filters = ImmutableList.copyOf(view.getFilters());
		this.orderBys = ImmutableList.copyOf(orderBys);

		//
		// Page
		if (rowIds == null && page == null)
		{
			throw new IllegalArgumentException("rowIds or page shall not be null");
		}
		this.rowIds = rowIds != null ? ImmutableList.copyOf(rowIds) : null;
		this.page = page != null ? ImmutableList.copyOf(page) : null;
		this.firstRow = firstRow;
		this.pageLength = pageLength;
	}

	/** View (WITHOUT loaded page) constructor */
	private ViewResult(final IView view)
	{
		super();
		this.viewId = view.getViewId();
		this.parentViewId = view.getParentViewId();
		this.viewDescription = view.getDescription();
		this.size = view.size();
		this.queryLimit = view.getQueryLimit();
		this.queryLimitHit = view.isQueryLimitHit();

		stickyFilters = ImmutableList.copyOf(view.getStickyFilters());
		filters = ImmutableList.copyOf(view.getFilters());
		orderBys = view.getDefaultOrderBys();

		//
		// Page
		rowIds = null;
		page = null;
		firstRow = 0;
		pageLength = 0;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				//
				// View info
				.add("viewId", viewId)
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

	public ViewId getViewId()
	{
		return viewId;
	}

	public ViewId getParentViewId()
	{
		return parentViewId;
	}

	public String getViewDescription(final String adLanguage)
	{
		if (viewDescription == null)
		{
			return null;
		}
		final String viewDescriptionStr = viewDescription.translate(adLanguage);
		return !Check.isEmpty(viewDescriptionStr, true) ? viewDescriptionStr : null;
	}

	public long getSize()
	{
		return size;
	}

	public int getFirstRow()
	{
		return firstRow;
	}

	public int getPageLength()
	{
		return pageLength;
	}

	public List<DocumentFilter> getStickyFilters()
	{
		return stickyFilters;
	}

	public List<DocumentFilter> getFilters()
	{
		return filters;
	}

	public List<DocumentQueryOrderBy> getOrderBys()
	{
		return orderBys;
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

	public int getQueryLimit()
	{
		return queryLimit;
	}

	public boolean isQueryLimitHit()
	{
		return queryLimitHit;
	}
}

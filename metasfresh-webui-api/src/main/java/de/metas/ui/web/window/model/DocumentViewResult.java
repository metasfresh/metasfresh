package de.metas.ui.web.window.model;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.model.filters.DocumentFilter;

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

@Immutable
public final class DocumentViewResult
{
	public static DocumentViewResult of(
			final IDocumentViewSelection view //
			, final int firstRow //
			, final int pageLength //
			, final List<DocumentQueryOrderBy> orderBys //
			, final List<IDocumentView> page //
	)
	{
		return new DocumentViewResult(view, firstRow, pageLength, orderBys, page);
	}

	public static DocumentViewResult of(final IDocumentViewSelection view)
	{
		final int firstRow = 0;
		final int pageLength = 0;
		final List<DocumentQueryOrderBy> orderBys = view.getDefaultOrderBys();
		final List<IDocumentView> page = ImmutableList.of();
		return new DocumentViewResult(view, firstRow, pageLength, orderBys, page);
	}

	private final IDocumentViewSelection view;
	private final int firstRow;
	private final int pageLength;
	private final List<DocumentFilter> filters;
	private final List<DocumentQueryOrderBy> orderBys;
	private final List<IDocumentView> page;

	private DocumentViewResult(
			final IDocumentViewSelection view //
			, final int firstRow //
			, final int pageLength //
			, final List<DocumentQueryOrderBy> orderBys //
			, final List<IDocumentView> page //
	)
	{
		super();
		this.view = view;
		this.firstRow = firstRow;
		this.pageLength = pageLength;
		this.filters = ImmutableList.copyOf(view.getFilters());
		this.orderBys = ImmutableList.copyOf(orderBys);
		this.page = ImmutableList.copyOf(page);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("view", view)
				.add("firstRow", firstRow)
				.add("pageLength", pageLength)
				.add("filters", filters)
				.add("orderBys", orderBys)
				.add("page", page)
				.toString();
	}

	public IDocumentViewSelection getView()
	{
		return view;
	}

	public int getFirstRow()
	{
		return firstRow;
	}

	public int getPageLength()
	{
		return pageLength;
	}
	
	public List<DocumentFilter> getFilters()
	{
		return filters;
	}

	public List<DocumentQueryOrderBy> getOrderBys()
	{
		return orderBys;
	}

	public List<IDocumentView> getPage()
	{
		return page;
	}
}

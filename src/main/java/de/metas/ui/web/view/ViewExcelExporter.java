package de.metas.ui.web.view;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.AbstractExcelExporter;
import org.adempiere.impexp.CellValue;
import org.adempiere.impexp.CellValues;
import org.adempiere.util.Check;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.util.PageIndex;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/* package */ class ViewExcelExporter extends AbstractExcelExporter
{
	private final RowsSupplier rows;
	private final ViewLayout layout;
	private final String adLanguage;

	@Builder
	private ViewExcelExporter(
			@NonNull final IView view,
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final ViewLayout layout,
			@NonNull final String adLanguage)
	{
		this.layout = layout;
		this.adLanguage = adLanguage;

		if (rowIds.isAll())
		{
			this.rows = new AllRowsSupplier(view);
		}
		else if (rowIds.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}
		else
		{
			this.rows = new ListRowsSupplier(view, rowIds);
		}

		setFreezePane(0, 1);
	}

	private IViewRow getRow(final int rowIndex)
	{
		return rows.getRow(rowIndex);
	}

	private String getFieldName(final int columnIndex)
	{
		final Set<DocumentLayoutElementFieldDescriptor> fields = layout.getElements().get(columnIndex).getFields();
		return fields.iterator().next().getField();
	}

	private DocumentFieldWidgetType getWidgetType(final int columnIndex)
	{
		return layout.getElements().get(columnIndex).getWidgetType();
	}

	@Override
	public boolean isFunctionRow(final int row)
	{
		return false;
	}

	@Override
	public int getColumnCount()
	{
		return layout.getElements().size();
	}

	@Override
	public int getRowCount()
	{
		return rows.getRowCount();
	}

	@Override
	public boolean isColumnPrinted(final int col)
	{
		return true;
	}

	@Override
	public String getHeaderName(final int col)
	{
		return layout.getElements().get(col).getCaption(adLanguage);
	}

	@Override
	public int getDisplayType(final int rowIndex_NOTUSED, final int columnIndex)
	{
		return getWidgetType(columnIndex).getDisplayType();
	}

	@Override
	public CellValue getValueAt(final int rowIndex, final int columnIndex)
	{
		final String fieldName = getFieldName(columnIndex);

		final IViewRow row = getRow(rowIndex);
		if (row == null)
		{
			return null;
		}

		final Object value = row.getFieldNameAndJsonValues().get(fieldName);
		if (value == null)
		{
			return null;
		}

		final DocumentFieldWidgetType widgetType = getWidgetType(columnIndex);
		if (widgetType.isDateOrTime())
		{
			return CellValue.ofDate(JSONDate.fromJson(value.toString(), widgetType));
		}
		else if (value instanceof JSONLookupValue)
		{
			final String valueStr = ((JSONLookupValue)value).getCaption();
			return CellValues.toCellValue(valueStr, widgetType.getDisplayType());
		}
		else
		{
			return CellValues.toCellValue(value, widgetType.getDisplayType());
		}
	}

	@Override
	public boolean isPageBreak(final int row, final int col)
	{
		return false;
	}

	private static interface RowsSupplier
	{
		IViewRow getRow(int rowIndex);

		int getRowCount();
	}

	private static class AllRowsSupplier implements RowsSupplier
	{
		private static final int PAGE_LENGTH = 100;
		private final IView view;
		private LoadingCache<PageIndex, ViewResult> cache = CacheBuilder.newBuilder()
				.maximumSize(2) // cache max 2 pages
				.build(new CacheLoader<PageIndex, ViewResult>()
				{
					@Override
					public ViewResult load(final PageIndex pageIndex)
					{
						final List<DocumentQueryOrderBy> orderBys = ImmutableList.of(); // default
						return view.getPage(pageIndex.getFirstRow(), pageIndex.getPageLength(), orderBys);
					}

				});

		private AllRowsSupplier(@NonNull final IView view)
		{
			this.view = view;
		}

		private ViewResult getPage(final PageIndex pageIndex)
		{
			try
			{
				return cache.get(pageIndex);
			}
			catch (ExecutionException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}

		@Override
		public IViewRow getRow(final int rowIndex)
		{
			final ViewResult page = getPage(PageIndex.getPageContainingRow(rowIndex, PAGE_LENGTH));

			final int rowIndexInPage = rowIndex - page.getFirstRow();
			if (rowIndexInPage < 0)
			{
				// shall not happen
				return null;
			}

			final List<IViewRow> rows = page.getPage();
			if (rowIndexInPage >= rows.size())
			{
				return null;
			}

			return rows.get(rowIndexInPage);
		}

		@Override
		public int getRowCount()
		{
			return (int)view.size();
		}
	}

	private static class ListRowsSupplier implements RowsSupplier
	{
		private final ImmutableList<IViewRow> rows;

		private ListRowsSupplier(@NonNull final IView view, @NonNull DocumentIdsSelection rowIds)
		{
			Check.assume(!rowIds.isAll(), "rowIds is not ALL");

			this.rows = view.streamByIds(rowIds).collect(ImmutableList.toImmutableList());
		}

		@Override
		public IViewRow getRow(final int rowIndex)
		{
			Check.assume(rowIndex >= 0, "rowIndex >= 0");

			final int rowsCount = rows.size();
			Check.assume(rowIndex < rowsCount, "rowIndex < {}", rowsCount);

			return rows.get(rowIndex);
		}

		@Override
		public int getRowCount()
		{
			return rows.size();
		}
	}
}

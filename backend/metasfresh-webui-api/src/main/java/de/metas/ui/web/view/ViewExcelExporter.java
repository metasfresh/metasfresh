package de.metas.ui.web.view;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.Language;
import de.metas.impexp.excel.AbstractExcelExporter;
import de.metas.impexp.excel.CellValue;
import de.metas.impexp.excel.CellValues;
import de.metas.impexp.excel.ExcelExportConstants;
import de.metas.impexp.excel.ExcelFormat;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.util.PageIndex;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.util.Check;
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
	private final JSONOptions jsonOpts;
	private int rowNumber = 0;

	@Builder
	private ViewExcelExporter(
			@Nullable final ExcelFormat excelFormat,
			@Nullable final ExcelExportConstants constants,
			@NonNull final IView view,
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final ViewLayout layout,
			@NonNull final Language language,
			@NonNull final ZoneId zoneId)
	{
		super(excelFormat, constants);
		this.layout = layout;
		setLanguage(language);
		jsonOpts = JSONOptions.builder()
				.adLanguage(language.getAD_Language())
				.zoneId(zoneId)
				.build();

		if (rowIds.isAll())
		{
			this.rows = new AllRowsSupplier(
					view,
					getConstants().getAllRowsPageSize(),
					jsonOpts);
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
	public List<CellValue> getHeaderNames()
	{
		final ArrayList<CellValue> result = new ArrayList<>();
		for (int i = 0; i < getColumnCount(); i++)
		{
			result.add(CellValues.toCellValue(getHeaderName(i)));
		}
		return result;
	}

	private String getHeaderName(final int col)
	{
		return layout.getElements().get(col).getCaption(getLanguage().getAD_Language());
	}

	@Override
	public int getDisplayType(final int rowIndex_NOTUSED, final int columnIndex)
	{
		return getWidgetType(columnIndex).getDisplayType();
	}

	private CellValue getValueAt(final int rowIndex, final int columnIndex)
	{
		final String fieldName = getFieldName(columnIndex);

		final IViewRow row = getRow(rowIndex);
		if (row == null)
		{
			return null;
		}

		final Object value = row.getFieldValueAsJsonObject(fieldName, jsonOpts);
		if (JSONNullValue.isNull(value))
		{
			return null;
		}

		final DocumentFieldWidgetType widgetType = getWidgetType(columnIndex);
		if (widgetType.isDateOrTime())
		{
			return CellValue.ofDate(DateTimeConverters.fromObject(value, widgetType));
		}
		else if (value instanceof JSONLookupValue)
		{
			final String valueStr = ((JSONLookupValue)value).getCaption();
			return CellValues.toCellValue(valueStr, widgetType.getDisplayType());
		}
		else if (value instanceof JSONLookupValuesList)
		{
			final JSONLookupValuesList jsonLookupValuesList = (JSONLookupValuesList)value;
			final String valueStr = jsonLookupValuesList
					.getValues()
					.stream()
					.map(lookupValue -> lookupValue.getCaption())
					.collect(Collectors.joining(", "));
			return CellValue.ofString(valueStr);
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

	private interface RowsSupplier
	{
		IViewRow getRow(int rowIndex);

		int getRowCount();
	}

	private static class AllRowsSupplier implements RowsSupplier
	{
		private final int pageSize;
		private final IView view;
		private final JSONOptions jsonOpts;

		private LoadingCache<PageIndex, ViewResult> cache = CacheBuilder.newBuilder()
				.maximumSize(2) // cache max 2 pages
				.build(new CacheLoader<PageIndex, ViewResult>()
				{
					@Override
					public ViewResult load(final PageIndex pageIndex)
					{
						final ViewRowsOrderBy orderBys = ViewRowsOrderBy.empty(jsonOpts); // default
						return view.getPage(pageIndex.getFirstRow(), pageIndex.getPageLength(), orderBys);
					}

				});

		private AllRowsSupplier(
				@NonNull final IView view,
				final int pageSize,
				@NonNull final JSONOptions jsonOpts)
		{
			this.view = view;
			this.pageSize = pageSize;
			this.jsonOpts = jsonOpts;
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
			final ViewResult page = getPage(PageIndex.getPageContainingRow(rowIndex, pageSize));

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

	@Override
	protected List<CellValue> getNextRow()
	{
		final ArrayList<CellValue> result = new ArrayList<>();
		for (int i = 0; i < getColumnCount(); i++)
		{
			result.add(getValueAt(rowNumber, i));
		}

		rowNumber++;
		return result;
	}

	@Override
	protected boolean hasNextRow()
	{
		return rowNumber < getRowCount();
	}
}

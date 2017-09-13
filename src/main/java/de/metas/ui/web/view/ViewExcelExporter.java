package de.metas.ui.web.view;

import java.util.List;
import java.util.Set;

import org.adempiere.impexp.AbstractExcelExporter;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.view.descriptor.ViewLayout;
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
	private final IView view;
	private final ViewLayout layout;
	private final String adLanguage;

	@Builder
	private ViewExcelExporter(@NonNull final IView view, @NonNull final ViewLayout layout, @NonNull final String adLanguage)
	{
		this.view = view;
		this.layout = layout;
		this.adLanguage = adLanguage;

		setFreezePane(0, 1);
	}

	private IViewRow getRow(final int rowIndex)
	{
		final int firstRow = rowIndex;
		final int pageLength = 1;
		final List<DocumentQueryOrderBy> orderBys = ImmutableList.of(); // default
		final ViewResult page = view.getPage(firstRow, pageLength, orderBys);
		final List<IViewRow> rows = page.getPage();
		if (rows.isEmpty())
		{
			return null;
		}
		else
		{
			return rows.get(0);
		}
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
	public boolean isFunctionRow()
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
		return (int)view.size();
	}

	@Override
	protected void setCurrentRow(final int row)
	{
		// nothing
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
	public Object getValueAt(final int rowIndex, final int columnIndex)
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
			return JSONDate.fromJson(value.toString(), widgetType);
		}
		else if (value instanceof JSONLookupValue)
		{
			return ((JSONLookupValue)value).getName();
		}

		return value;
	}

	@Override
	public boolean isPageBreak(final int row, final int col)
	{
		return false;
	}

}

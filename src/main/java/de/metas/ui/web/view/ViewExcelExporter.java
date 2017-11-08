package de.metas.ui.web.view;

import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.AbstractExcelExporter;
import org.adempiere.impexp.CellValue;
import org.adempiere.impexp.CellValues;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.DocumentId;
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
	private final IView view;
	private final ViewLayout layout;
	private final String adLanguage;
	/** Selected rowIds. Might be null in case ALL rows shall be exported. */
	private final List<DocumentId> rowIds;

	@Builder
	private ViewExcelExporter(
			@NonNull final IView view,
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final ViewLayout layout,
			@NonNull final String adLanguage)
	{
		this.view = view;
		this.layout = layout;
		this.adLanguage = adLanguage;

		if (rowIds.isAll())
		{
			this.rowIds = null;
		}
		else if (rowIds.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}
		else
		{
			this.rowIds = ImmutableList.copyOf(rowIds.toSet());
		}

		setFreezePane(0, 1);
	}

	private IViewRow getRow(final int rowIndex)
	{
		if (rowIds == null) // All rows
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
		else
		{
			final DocumentId rowId = rowIds.get(rowIndex);
			return view.getById(rowId);
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
		if (rowIds == null)
		{
			return (int)view.size();
		}
		else
		{
			return rowIds.size();
		}
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

}

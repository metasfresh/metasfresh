/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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
package de.metas.impexp.spreadsheet.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTabLayoutMode;
import org.compiere.model.Lookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.adempiere.service.IColumnBL;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/**
 * Excel Exporter Adapter for GridTab
 */
public class GridTabExcelExporter extends AbstractExcelExporter
{
	private final MLookupFactory lookupFactory = MLookupFactory.newInstance();
	private final GridTab m_tab;
	private int rowNumber = 0;

	@Builder
	private GridTabExcelExporter(
			@Nullable final ExcelFormat excelFormat,
			@Nullable final ExcelExportConstants constants,
			@NonNull final GridTab tab)
	{
		super(excelFormat, constants);
		m_tab = tab;
		setFreezePane(0, 1);
	}

	@Override
	public int getColumnCount()
	{
		return m_tab.getFieldCount();
	}

	@Override
	public int getDisplayType(final int row, final int col)
	{
		return m_tab.getField(col).getDisplayType();
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
		return m_tab.getField(col).getHeader();
	}

	@Override
	public int getRowCount()
	{
		return m_tab.getRowCount();
	}

	private CellValue getValueAt(final int row, final int col)
	{
		final GridField f = m_tab.getField(col);
		final Object key = m_tab.getValue(row, f.getColumnName());
		Object value = key;
		Lookup lookup = f.getLookup();
		if (lookup != null)
		{
			// nothing to do
		}
		else if (f.getDisplayType() == DisplayType.Button)
		{
			lookup = getButtonLookup(f);
		}

		if (lookup != null)
		{
			value = lookup.getDisplay(key);
		}

		final int displayType = f.getDisplayType();
		return CellValues.toCellValue(value, displayType);
	}

	@Override
	public boolean isColumnPrinted(final int col)
	{
		final GridField f = m_tab.getField(col);

		// Hide not displayed fields
		if (!f.isDisplayed(GridTabLayoutMode.Grid))
		{
			return false;
		}

		// Hide encrypted fields
		if (f.isEncrypted())
		{
			return false;
		}

		// Hide simple button fields without a value
		if (f.getDisplayType() == DisplayType.Button && f.getAD_Reference_Value_ID() == null)
		{
			return false;
		}

		// Hide included tab fields (those are only placeholders for included tabs, always empty)
		if (f.getIncluded_Tab_ID() > 0)
		{
			return false;
		}

		//
		return true;
	}

	@Override
	public boolean isFunctionRow(final int row)
	{
		return false;
	}

	@Override
	public boolean isPageBreak(final int row, final int col)
	{
		return false;
	}

	private final HashMap<String, MLookup> m_buttonLookups = new HashMap<>();

	private MLookup getButtonLookup(final GridField mField)
	{
		MLookup lookup = m_buttonLookups.get(mField.getColumnName());
		if (lookup != null)
		{
			return lookup;
		}

		// TODO: refactor with org.compiere.grid.ed.VButton.setField(GridField)
		if (mField.getColumnName().endsWith("_ID") && !IColumnBL.isRecordIdColumnName(mField.getColumnName()))
		{
			lookup = lookupFactory.get(Env.getCtx(), mField.getWindowNo(), 0,
					mField.getAD_Column_ID(), DisplayType.Search);
		}
		else if (mField.getAD_Reference_Value_ID() != null)
		{
			// Assuming List
			lookup = lookupFactory.get(Env.getCtx(), mField.getWindowNo(), 0,
					mField.getAD_Column_ID(), DisplayType.List);
		}
		//
		m_buttonLookups.put(mField.getColumnName(), lookup);
		return lookup;
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

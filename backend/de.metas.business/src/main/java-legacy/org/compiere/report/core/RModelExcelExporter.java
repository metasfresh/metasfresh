/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package org.compiere.report.core;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import de.metas.impexp.spreadsheet.excel.AbstractExcelExporter;
import de.metas.impexp.spreadsheet.excel.CellValue;
import de.metas.impexp.spreadsheet.excel.CellValues;
import de.metas.impexp.spreadsheet.excel.ExcelExportConstants;
import de.metas.impexp.spreadsheet.excel.ExcelFormat;
import lombok.Builder;
import lombok.NonNull;

/**
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *
 */
public class RModelExcelExporter
		extends AbstractExcelExporter
{
	private final RModel m_model;
	private int rowNumber = 0;

	@Builder
	private RModelExcelExporter(
			@Nullable final ExcelFormat poiFactory,
			@Nullable final ExcelExportConstants constants,
			@NonNull final RModel model)
	{
		super(poiFactory, constants);
		m_model = model;
	}

	@Override
	public int getColumnCount()
	{
		return m_model.getColumnCount();
	}

	@Override
	public int getDisplayType(final int row, final int col)
	{
		return m_model.getRColumn(col).getDisplayType();
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
		return m_model.getRColumn(col).getColHeader();
	}

	@Override
	public int getRowCount()
	{
		return m_model.getRowCount();
	}

	private CellValue getValueAt(final int row, final int col)
	{
		final Object valueObj = m_model.getValueAt(row, col);
		final int displayType = getDisplayType(row, col);
		return CellValues.toCellValue(valueObj, displayType);
	}

	@Override
	public boolean isColumnPrinted(final int col)
	{
		return true;
	}

	@Override
	public boolean isFunctionRow(final int row)
	{
		return m_model.isGroupRow(row);
	}

	@Override
	public boolean isPageBreak(final int row, final int col)
	{
		return false;
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

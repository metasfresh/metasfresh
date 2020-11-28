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
package de.metas.impexp.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.compiere.util.Env;

import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;

/**
 * Export excel from ArrayList of data
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *
 */

public class ArrayExcelExporter extends AbstractExcelExporter
{
	private final Properties m_ctx;
	private final List<List<Object>> m_data;
	private final List<String> m_columnHeaders;
	private final boolean translateHeaders;

	private List<Object> currentRow;
	private int currentRowNumber = 0;

	@Builder
	private ArrayExcelExporter(
			@Nullable final ExcelFormat excelFormat,
			@Nullable final ExcelExportConstants constants,
			@Nullable final Properties ctx,
			@NonNull final List<List<Object>> data,
			@Nullable final List<String> columnHeaders,
			@Nullable final Boolean translateHeaders)
	{
		super(excelFormat, constants);

		m_ctx = ctx != null ? ctx : Env.getCtx();
		m_data = data;
		m_columnHeaders = columnHeaders;
		this.translateHeaders = CoalesceUtil.coalesce(translateHeaders, true);
	}

	@Override
	protected Properties getCtx()
	{
		return m_ctx;
	}

	@Override
	public int getColumnCount()
	{
		return m_data.get(0).size();
	}

	@Override
	public int getDisplayType(final int IGNORED, final int col)
	{
		final Object value = currentRow.get(col);
		return CellValues.extractDisplayTypeFromValue(value);
	}

	@Override
	public List<CellValue> getHeaderNames()
	{
		final List<String> headerNames = new ArrayList<>();
		if (m_columnHeaders == null || m_columnHeaders.isEmpty())
		{
			// use the next data row; can be the first, but if we add another sheet, it can also be another one.
			stepToNextRow();
			for (Object headerNameObj : currentRow)
			{
				headerNames.add(headerNameObj != null ? headerNameObj.toString() : null);
			}
		}
		else
		{
			headerNames.addAll(m_columnHeaders);
		}

		final ArrayList<CellValue> result = new ArrayList<>();
		final String adLanguage = getLanguage().getAD_Language();
		for (final String rawHeaderName : headerNames)
		{
			final String headerName;
			if (translateHeaders)
			{
				headerName = msgBL.translatable(rawHeaderName).translate(adLanguage);
			}
			else
			{
				headerName = rawHeaderName;
			}
			result.add(CellValues.toCellValue(headerName));
		}
		return result;
	}

	@Override
	public int getRowCount()
	{
		return m_data.size() - 1;
	}

	@Override
	public boolean isColumnPrinted(final int col)
	{
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

	@Override
	protected List<CellValue> getNextRow()
	{
		stepToNextRow();
		return CellValues.toCellValues(currentRow);
	}

	private void stepToNextRow()
	{
		currentRow = m_data.get(currentRowNumber);
		currentRowNumber++;
	}

	@Override
	protected boolean hasNextRow()
	{
		return currentRowNumber < m_data.size();
	}
}

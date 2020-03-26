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

import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.compiere.util.Env;

import de.metas.util.lang.CoalesceUtil;
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
	public String getHeaderName(final int col)
	{
		String headerName;
		if (m_columnHeaders == null || m_columnHeaders.isEmpty())
		{
			final Object headerNameObj = m_data.get(0).get(col);
			headerName = headerNameObj != null ? headerNameObj.toString() : null;
		}
		else
		{
			headerName = m_columnHeaders.get(col);
		}

		if (translateHeaders)
		{
			final String adLanguage = getLanguage().getAD_Language();
			return msgBL.translatable(headerName).translate(adLanguage);
		}
		else
		{
			return headerName;
		}
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
		currentRow = m_data.get(currentRowNumber);
		currentRowNumber++;
		return CellValues.toCellValues(currentRow);
	}

	@Override
	protected boolean hasNextRow()
	{
		return currentRowNumber < m_data.size();
	}
}

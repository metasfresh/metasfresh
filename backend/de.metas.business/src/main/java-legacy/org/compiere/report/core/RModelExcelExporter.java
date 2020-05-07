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

import org.adempiere.impexp.AbstractExcelExporter;
import org.adempiere.impexp.CellValue;
import org.adempiere.impexp.CellValues;

/**
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *
 */
public class RModelExcelExporter
		extends AbstractExcelExporter
{
	private RModel m_model = null;

	public RModelExcelExporter(final RModel model)
	{
		super();
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
	public String getHeaderName(final int col)
	{
		return m_model.getRColumn(col).getColHeader();
	}

	@Override
	public int getRowCount()
	{
		return m_model.getRowCount();
	}

	@Override
	public CellValue getValueAt(final int row, final int col)
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
}

/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.report.core;

import org.adempiere.impexp.AbstractExcelExporter;

/**
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *
 */
public class RModelExcelExporter
extends AbstractExcelExporter
{
	private RModel m_model = null;
	private int m_currentRow = 0;

	public RModelExcelExporter(RModel model) {
		super();
		m_model = model;
	}
	
	@Override
	public int getColumnCount() {
		return m_model.getColumnCount();
	}

	@Override
	public int getDisplayType(int row, int col) {
		return m_model.getRColumn(col).getDisplayType();
	}

	@Override
	public String getHeaderName(int col) {
		return m_model.getRColumn(col).getColHeader();
	}

	@Override
	public int getRowCount() {
		return m_model.getRowCount();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return m_model.getValueAt(row, col);
	}

	@Override
	public boolean isColumnPrinted(int col) {
		return true;
	}

	@Override
	public boolean isFunctionRow() {
		return m_model.isGroupRow(m_currentRow);
	}

	@Override
	public boolean isPageBreak(int row, int col) {
		return false;
	}

	@Override
	protected void setCurrentRow(int row) {
		m_currentRow = row;
	}
}

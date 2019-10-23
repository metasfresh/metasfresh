/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 Adempiere, Inc. All Rights Reserved.                    *
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
package org.compiere.swing;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Dummy editor and renderer use for invisible column
 * 
 * @author Low Heng Sin
 *
 */
public final class TableCellNone implements TableCellEditor, TableCellRenderer
{

	private Object m_value;

	// private String m_columnName;

	public TableCellNone(String columnName)
	{
		super();
		// m_columnName = columnName;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		m_value = value;
		return null;
	}

	@Override
	public void addCellEditorListener(CellEditorListener l)
	{
	}

	@Override
	public void cancelCellEditing()
	{
	}

	@Override
	public Object getCellEditorValue()
	{
		return m_value;
	}

	@Override
	public boolean isCellEditable(EventObject anEvent)
	{
		return false;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l)
	{
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent)
	{
		return false;
	}

	@Override
	public boolean stopCellEditing()
	{
		return true;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		m_value = value;
		return null;
	}

}

/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps.search;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.swing.CEditor;

import de.metas.util.Check;

/**
 * Cell Editor.
 *
 * <pre>
 * 	Sequence of events:
 * 		isCellEditable
 * 		getTableCellEditor
 * 		shouldSelectCell
 * 		getCellEditorValue
 * </pre>
 *
 * @author Jorg Janke
 */
abstract class FindCellEditor extends AbstractCellEditor
		implements TableCellEditor
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6162302205998931936L;

	// /** The Table Editor */
	// private CEditor _editor = null;
	private Object valueOld = null;

	public FindCellEditor()
	{
		super();
	}

	public void dispose()
	{
		valueOld = null;
	}

	protected abstract CEditor getEditor();

	@Override
	public boolean isCellEditable(final EventObject anEvent)
	{
		return true;
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int col)
	{
		if (row >= 0)
		{
			table.setRowSelectionInterval(row, row);     // force moving to new row
		}

		// Set Value
		final CEditor editor = getEditor();
		editor.setValue(value);
		valueOld = value; // remember the old value

		//
		// Set Background/Foreground to "normal" (unselected) colors
		((JComponent)editor).setForeground(AdempierePLAF.getTextColor_Normal());
		// Other UI
		((JComponent)editor).setFont(table.getFont());
		if (editor instanceof JComboBox)
		{
			((JComboBox<?>)editor).setBorder(BorderFactory.createEmptyBorder());
		}
		else
		{
			((JComponent)editor).setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		}

		//
		return (Component)editor;
	}	// getTableCellEditorComponent

	/**
	 * The editing cell should be selected or not
	 *
	 * @param e event
	 * @return true (constant)
	 */
	@Override
	public boolean shouldSelectCell(final EventObject e)
	{
		return true;
	}

	@Override
	public Object getCellEditorValue()
	{
		final CEditor editor = getEditor();
		if (editor == null)
		{
			return null;
		}

		return editor.getValue();
	}

	public Object getCellEditorValueOld()
	{
		return valueOld;
	}

	public boolean isCellEditorValueChanged()
	{
		// If editor was disposed, return false
		final CEditor editor = getEditor();
		if (editor == null)
		{
			return false;
		}

		final Object value = getCellEditorValue();
		return !Check.equals(valueOld, value);
	}

	public boolean isCellEditorValueNull()
	{
		return getCellEditorValue() == null;
	}

	@Override
	public boolean stopCellEditing()
	{
		final boolean stopped = super.stopCellEditing();

		if (stopped)
		{
			valueOld = null;  // reset old value
		}

		return stopped;
	}

	@Override
	public void cancelCellEditing()
	{
		fireEditingCanceled();
		valueOld = null;  // reset old value
	}
}

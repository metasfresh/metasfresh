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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Check;
import org.compiere.swing.CEditor;

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
 * A new Editor is created for editable columns.
 * 
 * @author Jorg Janke
 * @version $Id: VCellEditor.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public final class FindCellEditor extends AbstractCellEditor
		implements TableCellEditor, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6542861196427426178L;

	/** The Table Editor */
	private CEditor m_editor = null;
	private Object valueOld = null;

	/**
	 * Constructor for Grid
	 * 
	 * @param mField
	 */
	public FindCellEditor(final CEditor component)
	{
		super();
		m_editor = component;
		// m_editor.addActionListener(this);
		// Click
	}	// FindCellEditor

	/**
	 * @param anEvent event
	 * @return true if editable
	 */
	@Override
	public boolean isCellEditable(EventObject anEvent)
	{
		return true;
	}	// isCellEditable

	/**
	 * Sets an initial value for the editor. This will cause the editor to
	 * stopEditing and lose any partially edited value if the editor is editing
	 * when this method is called.
	 * Returns the component that should be added to the client's Component hierarchy.
	 * Once installed in the client's hierarchy this component
	 * will then be able to draw and receive user input.
	 *
	 * @param table
	 * @param value
	 * @param isSelected
	 * @param row
	 * @param col
	 * @return component
	 */
	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int col)
	{
		if (row >= 0)
		{
			table.setRowSelectionInterval(row, row);     // force moving to new row
		}

		// Set Value
		m_editor.setValue(value);
		valueOld = value; // remember the old value

		//
		// Set Background/Foreground to "normal" (unselected) colors
		((JComponent)m_editor).setForeground(AdempierePLAF.getTextColor_Normal());
		// Other UI
		((JComponent)m_editor).setFont(table.getFont());
		if (m_editor instanceof JComboBox)
		{
			((JComboBox<?>)m_editor).setBorder(BorderFactory.createEmptyBorder());
		}
		else
		{
			((JComponent)m_editor).setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		}

		//
		return (Component)m_editor;
	}	// getTableCellEditorComponent

	/**
	 * The editing cell should be selected or not
	 * 
	 * @param e event
	 * @return true (constant)
	 */
	@Override
	public boolean shouldSelectCell(EventObject e)
	{
		return true;
	}	// shouldSelectCell

	/**
	 * Returns the value contained in the editor
	 * 
	 * @return value
	 */
	@Override
	public Object getCellEditorValue()
	{
		return m_editor.getValue();
	}	// getCellEditorValue

	public Object getCellEditorValueOld()
	{
		return valueOld;
	}

	public boolean isCellEditorValueChanged()
	{
		// If editor was disposed, return false
		if (m_editor == null)
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

	/**
	 * Action Editor - Stop Editor
	 * 
	 * @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
	}

	/**
	 * Dispose
	 */
	public void dispose()
	{
		m_editor = null;
		valueOld = null;
	}	// dispose

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

}	// FindCellEditor

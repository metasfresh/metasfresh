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
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.adempiere.util.Services;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.MQuery.Operator;

/**
 * Cell editor for Find Value/ValueTo fields. Editor depends on Column setting. Has to save entries how they are used in the query, i.e. '' for strings.
 */
final class FindValueEditor extends AbstractCellEditor implements TableCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7409932376841865389L;

	// services
	private final transient ISwingEditorFactory swingEditorFactory = Services.get(ISwingEditorFactory.class);

	/** Is ValueTo Column? */
	private final boolean isValueToColumn;
	/** Between selected */
	private boolean isValueToEnabled = false;
	/** Editor */
	private VEditor editor = null;

	/**
	 * Constructor
	 *
	 * @param valueTo true if it is the "to" value column
	 */
	public FindValueEditor(final boolean valueTo)
	{
		super();
		isValueToColumn = valueTo;
	}

	/**
	 * Get Value Need to convert to String
	 *
	 * @return current value
	 */
	@Override
	public Object getCellEditorValue()
	{
		if (editor == null)
		{
			return null;
		}
		return editor.getValue();
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int rowIndex, final int columnIndex)
	{
		final IUserQueryRestriction row = getRow(table, rowIndex);

		//
		// Update valueTo enabled
		final Operator operator = row.getOperator();
		isValueToEnabled = isValueToColumn && operator != null && operator.isRangeOperator();

		final FindPanelSearchField searchField = FindPanelSearchField.castToFindPanelSearchField(row.getSearchField());
		if (searchField == null)
		{
			// shall not happen
			return null;
		}

		// Make sure the old editor is destroyed
		destroyEditor();

		// Create editor
		editor = searchField.createEditor(true);
		editor.setReadWrite(isValueDisplayed());
		editor.setValue(value);

		return swingEditorFactory.getEditorComponent(editor);
	}

	private final boolean isValueDisplayed()
	{
		return !isValueToColumn || isValueToColumn && isValueToEnabled;
	}

	@Override
	public boolean isCellEditable(final EventObject e)
	{
		return true;
	}

	@Override
	public boolean shouldSelectCell(final EventObject e)
	{
		return isValueDisplayed();
	}

	private IUserQueryRestriction getRow(final JTable table, final int viewRowIndex)
	{
		final FindAdvancedSearchTableModel model = (FindAdvancedSearchTableModel)table.getModel();
		final int modelRowIndex = table.convertRowIndexToModel(viewRowIndex);
		return model.getRow(modelRowIndex);
	}

	/**
	 * Destroy existing editor.
	 * 
	 * Very important to be called because this will also unregister the listeners from editor to underlying lookup (if any).
	 */
	private final void destroyEditor()
	{
		if (editor == null)
		{
			return;
		}
		editor.dispose();
		editor = null;
	}

	@Override
	public boolean stopCellEditing()
	{
		if (!super.stopCellEditing())
		{
			return false;
		}

		destroyEditor();

		return true;
	}

	@Override
	public void cancelCellEditing()
	{
		super.cancelCellEditing();

		destroyEditor();
	}
}

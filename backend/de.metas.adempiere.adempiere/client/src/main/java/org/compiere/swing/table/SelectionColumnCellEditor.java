package org.compiere.swing.table;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.compiere.swing.CCheckBox;
import org.compiere.util.DisplayType;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

class SelectionColumnCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private static final long serialVersionUID = 1L;

	public SelectionColumnCellEditor()
	{
		checkbox.setMargin(new Insets(0, 0, 0, 0));
		checkbox.setHorizontalAlignment(SwingConstants.CENTER);

		checkbox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				fireEditingStopped();
			}
		});
	}

	private final JCheckBox checkbox = new CCheckBox();

	/**
	 * Return Selection Status as IDColumn
	 *
	 * @return value
	 */
	@Override
	public Object getCellEditorValue()
	{
		return checkbox.isSelected();
	}

	/**
	 * Get visual Component
	 *
	 * @param table
	 * @param value
	 * @param isSelected
	 * @param row
	 * @param column
	 * @return Component
	 */
	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column)
	{
		final boolean selected = DisplayType.toBooleanNonNull(value, false);
		checkbox.setSelected(selected);
		return checkbox;
	}

	@Override
	public boolean isCellEditable(final EventObject anEvent)
	{
		return true;
	}

	@Override
	public boolean shouldSelectCell(final EventObject anEvent)
	{
		return true;
	}
}

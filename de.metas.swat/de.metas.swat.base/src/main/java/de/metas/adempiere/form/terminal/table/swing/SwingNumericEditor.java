package de.metas.adempiere.form.terminal.table.swing;

/*
 * #%L
 * de.metas.swat.base
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


import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.compiere.grid.ed.VHeaderRenderer;
import org.compiere.util.DisplayType;

import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalNumericField;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;

/* package */class SwingNumericEditor extends AbstractCellEditor
		implements TableCellRenderer, TableCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2978590233144852288L;

	private final SwingDefaultRenderer renderer;
	private final ITerminalNumericField editor;
	private final Component editorSwing;

	private final DecimalFormat numberFormat;

	public SwingNumericEditor(final ITerminalContext terminalContext)
	{
		super();
		final ITerminalFactory factory = terminalContext.getTerminalFactory();

		final int displayType = DisplayType.Quantity;
		final int alignment = VHeaderRenderer.getHorizontalAlignmentForDisplayType(displayType);
		renderer = new SwingDefaultRenderer(terminalContext, alignment);

		numberFormat = DisplayType.getNumberFormat(displayType);

		editor = factory.createTerminalNumericField("",
				displayType,
				true, // withButtons,
				false // withLabel
				);
		editor.setEditable(true);

		editorSwing = SwingTerminalFactory.getUI(editor);
	}

	@Override
	public Object getCellEditorValue()
	{
		final Object value = editor.getValue();
		return value;
	}

	@Override
	public boolean stopCellEditing()
	{
		// editor.processPendingEvents();
		return super.stopCellEditing();
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column)
	{
		editor.setValue(value);

		// NOTE: we consider isSelected=true because when editing the row is always selected
		// more, i think there is a bug (maybe because selection model is not implemented at the moment when i am checking this)
		// so i always get isSelected=false first and then when i am clicking again on cell this method is invoked again with isSelected=true
		editor.setBackgroundColor(getBackgroundColor(table, true)); // isSelected=true

		return editorSwing;
	}

	private Color getBackgroundColor(final JTable table, final boolean isSelected)
	{
		if (isSelected)
		{
			return table.getSelectionBackground();
		}
		else
		{
			return table.getBackground();
		}
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
	{
		// Convert value to String
		final String valueStr;
		if (value == null)
		{
			valueStr = "";
		}
		else if (value instanceof String)
		{
			valueStr = (String)value;
		}
		else
		{
			valueStr = numberFormat.format(value);
		}

		// Use default renderer
		return renderer.getTableCellRendererComponent(table, valueStr, isSelected, hasFocus, row, column);
	}
}

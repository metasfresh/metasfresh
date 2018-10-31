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


import java.awt.Component;
import java.text.ParseException;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.compiere.grid.ed.VHeaderRenderer;
import org.compiere.util.DisplayType;

import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalTextField;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.util.Check;

/* package */ class SwingStringEditor extends AbstractCellEditor
		implements TableCellRenderer, TableCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3496090951647091453L;

	private final ITerminalFactory terminalFactory;

	private final SwingDefaultRenderer renderer;

	private final ITerminalTextField editor;
	private final Component editorSwing;

	public SwingStringEditor(ITerminalContext terminalContext)
	{
		super();
		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalFactory = terminalContext.getTerminalFactory();

		final int displayType = DisplayType.String;
		final int alignment = VHeaderRenderer.getHorizontalAlignmentForDisplayType(displayType);
		renderer = new SwingDefaultRenderer(terminalContext, alignment);

		editor = terminalFactory.createTerminalTextField("", displayType, terminalContext.getDefaultFontSize());
		editor.setEditable(true);

		editorSwing = SwingTerminalFactory.getUI(editor);
	}

	@Override
	public Object getCellEditorValue()
	{
		return editor.getValue();
	}

	@Override
	public boolean stopCellEditing()
	{
		try
		{
			editor.commitEdit();
		}
		catch (ParseException e)
		{
			terminalFactory.showWarning(editor, "Error", e);
		}

		return super.stopCellEditing();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
	{
		editor.setValue(value);
		return editorSwing;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

}

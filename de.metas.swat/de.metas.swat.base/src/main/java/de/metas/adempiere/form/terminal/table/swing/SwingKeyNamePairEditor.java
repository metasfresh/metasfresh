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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.compiere.grid.ed.VHeaderRenderer;
import org.compiere.model.Lookup;
import org.compiere.model.POInfo;
import org.compiere.util.DisplayType;
import org.compiere.util.NamePair;

import de.metas.adempiere.form.terminal.ITerminalComboboxField;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.adempiere.form.terminal.table.ITableColumnInfo;
import de.metas.util.Check;

/* package */class SwingKeyNamePairEditor<T> extends AbstractCellEditor
		implements TableCellRenderer, TableCellEditor
{
	/**
	* 
	*/
	private static final long serialVersionUID = 3496090951647091453L;

	private final ITerminalFactory terminalFactory;
	private final SwingTerminalTable2<T> swingTerminalTable;

	private final SwingDefaultRenderer renderer;

	private ITerminalComboboxField currentEditor;

	private final Map<Integer, ITerminalComboboxField> modelColumnIndex2editor = new HashMap<Integer, ITerminalComboboxField>();

	public SwingKeyNamePairEditor(final ITerminalContext terminalContext, final SwingTerminalTable2<T> swingTerminalTable)
	{
		super();

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalFactory = terminalContext.getTerminalFactory();

		Check.assumeNotNull(swingTerminalTable, "swingTerminalTable not null");
		this.swingTerminalTable = swingTerminalTable;

		final int displayType = DisplayType.String;
		final int alignment = VHeaderRenderer.getHorizontalAlignmentForDisplayType(displayType);
		renderer = new SwingDefaultRenderer(terminalContext, alignment);
	}

	private ITerminalComboboxField getEditor(final int columnIndexView)
	{
		final int columnIndexModel = swingTerminalTable.convertColumnIndexToModel(columnIndexView);
		ITerminalComboboxField editor = modelColumnIndex2editor.get(columnIndexModel);
		if (editor == null)
		{
			// task FRESH-305: Create a new, empty combobox. It will be filled afterwards with the needed info.
			editor = terminalFactory.createTerminalCombobox("");
			modelColumnIndex2editor.put(columnIndexModel, editor);
		}

		this.currentEditor = editor;
		return editor;
	}

	/**
	 * Update the editor with the correct info required by the current columnIndexView.
	 * 
	 * @param editor
	 * @param columnIndexView
	 */
	private void updateEditorValues(final ITerminalComboboxField editor, final int columnIndexView)
	{
		final ITerminalContext terminalContext = swingTerminalTable.getTerminalContext();
		final ITableColumnInfo columnInfo = swingTerminalTable.getTableColumnInfo(columnIndexView);
		final String lookupTableName = columnInfo.getLookupTableName();
		final String lookupColumnName = columnInfo.getLookupColumnName();

		final POInfo poInfo = POInfo.getPOInfo(lookupTableName);

		final Lookup lookup = poInfo.getColumnLookup(terminalContext.getCtx(), terminalContext.getWindowNo(), poInfo.getColumnIndex(lookupColumnName));
		final List<Object> valuesObj = lookup.getData(
				false,  // mandatory,
				true,  // onlyValidated,
				true,  // onlyActive,
				false // temporary
		);

		final List<NamePair> values = new ArrayList<>();
		for (final Object valueObj : valuesObj)
		{
			final NamePair value = (NamePair)valueObj;
			values.add(value);
		}

		editor.setValues(values);
	}

	private ITerminalComboboxField getCurrentEditor()
	{
		return currentEditor;
	}

	@Override
	public Object getCellEditorValue()
	{
		final ITerminalComboboxField editor = getCurrentEditor();
		if (editor == null)
		{
			return null;
		}
		return editor.getValue();
	}

	@Override
	public boolean stopCellEditing()
	{
		final boolean stopped = super.stopCellEditing();
		if (stopped)
		{
			currentEditor = null;
		}

		return stopped;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndexView, int columnIndexView)
	{
		final ITerminalComboboxField editor = getEditor(columnIndexView);

		// task FRESH-305: Make sure the editor contains information that is up to date with the context
		updateEditorValues(editor, columnIndexView);
		editor.setValue(value);

		return SwingTerminalFactory.getUI(editor);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndexView, int columnIndexView)
	{
		return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndexView, columnIndexView);
	}

}

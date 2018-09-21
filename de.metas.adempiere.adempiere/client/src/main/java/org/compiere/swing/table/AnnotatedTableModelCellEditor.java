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


import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.compiere.grid.ed.VCheckBox;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VNumber;
import org.compiere.grid.ed.VString;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.IClientUI;
import de.metas.util.Services;

class AnnotatedTableCellEditor extends AbstractCellEditor implements TableCellEditor
{
	private static final long serialVersionUID = 1L;

	public AnnotatedTableCellEditor(final TableColumnInfo columnInfo)
	{
		super();

		// this.columnInfo = columnInfo;
		this.modelValueClass = columnInfo.getColumnClass();
		final boolean mandatory = false;
		final boolean readonly = false;
		final boolean updateable = true;

		if (modelValueClass == Timestamp.class)
		{
			final VDate editor = new VDate();
			m_editor = editor;
			editorValueClass = Timestamp.class;
		}
		else if (modelValueClass == BigDecimal.class)
		{
			final int displayTypeToUse = columnInfo.getDisplayType(DisplayType.Amount);
			final VNumber editor = new VNumber(columnInfo.getColumnName(), mandatory, readonly, updateable, displayTypeToUse, columnInfo.getDisplayName());
			this.m_editor = editor;
			editorValueClass = modelValueClass;
		}
		else if (modelValueClass == Double.class)
		{
			final int displayTypeToUse = columnInfo.getDisplayType(DisplayType.Number);
			final VNumber editor = new VNumber(columnInfo.getColumnName(), mandatory, readonly, updateable, displayTypeToUse, columnInfo.getDisplayName());
			this.m_editor = editor;
			editorValueClass = modelValueClass;
		}
		else if (modelValueClass == Integer.class)
		{
			final int displayTypeToUse = columnInfo.getDisplayType(DisplayType.Integer);
			final VNumber editor = new VNumber(columnInfo.getColumnName(), mandatory, readonly, updateable, displayTypeToUse, columnInfo.getDisplayName());
			this.m_editor = editor;
			editorValueClass = modelValueClass;
		}
		else if (modelValueClass == Boolean.class || modelValueClass == boolean.class)
		{
			final VCheckBox editor = new VCheckBox();
			this.m_editor = editor;
			editorValueClass = modelValueClass;

		}
		else
		{
			final VString editor = new VString();
			this.m_editor = editor;
			editorValueClass = modelValueClass;
		}
		
		final Component editorComp = (Component)m_editor;
		editorComp.addPropertyChangeListener("ancestor", new PropertyChangeListener()
		{
			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				final Object parentNew = evt.getNewValue();
				if (parentNew != null)
				{
					editorComp.requestFocus();
				}
			}
		});
	}

	// private final TableColumnInfo columnInfo;
	private final VEditor m_editor;
	private final Class<?> modelValueClass;
	private final Class<?> editorValueClass;

	@Override
	public Component getTableCellEditorComponent(final JTable table,
			final Object valueModel,
			final boolean isSelected,
			int rowIndexView,
			int columnIndexView)
	{
		// Set Value
		final Object valueEditor = convertToEditorValue(valueModel);
		m_editor.setValue(valueEditor);

		// Set UI
		m_editor.setBorder(null);
		// m_editor.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		m_editor.setFont(table.getFont());
		
		final Component editorComp = (Component)m_editor;
		
		return editorComp;
	}

	@Override
	public Object getCellEditorValue()
	{
		if (m_editor == null)
		{
			return null;
		}

		final Object valueEditor = m_editor.getValue();

		//
		// Check and convert the value if needed
		final Object valueModel;
		if (valueEditor instanceof Number && modelValueClass == KeyNamePair.class)
		{
			final int key = ((Number)valueEditor).intValue();
			final String name = m_editor.getDisplay();
			valueModel = new KeyNamePair(key, name);
		}
		else
		{
			valueModel = valueEditor;
		}

		return valueModel;
	}

	@Override
	public boolean stopCellEditing()
	{
		try
		{
			return super.stopCellEditing();
		}
		catch (Exception e)
		{
			final Component comp = (Component)m_editor;
			final int windowNo = Env.getWindowNo(comp);
			Services.get(IClientUI.class).warn(windowNo, e);
		}

		return false;
	}

	@Override
	public void cancelCellEditing()
	{
		super.cancelCellEditing();
	}

	private Object convertToEditorValue(final Object valueModel)
	{
		final Object valueEditor;
		if (valueModel instanceof KeyNamePair && editorValueClass == Integer.class)
		{
			valueEditor = ((KeyNamePair)valueModel).getKey();
		}
		else
		{
			valueEditor = valueModel;
		}

		return valueEditor;
	}

}

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


import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.metas.adempiere.form.terminal.table.ITerminalTableModel;
import de.metas.adempiere.form.terminal.table.ITerminalTableModelListener;
import de.metas.util.Check;

/**
 * Wraps an {@link ITerminalTableModel} and behaves as a {@link javax.swing.table.TableModel}.
 * 
 * @author tsa
 * 
 */
public class SwingTerminalTableModelAdapter<T> implements TableModel
{
	public static final <T> SwingTerminalTableModelAdapter<T> castOrNull(final TableModel tableModel)
	{
		if (tableModel instanceof SwingTerminalTableModelAdapter)
		{
			@SuppressWarnings("unchecked")
			final SwingTerminalTableModelAdapter<T> tableModelAdapter = (SwingTerminalTableModelAdapter<T>)tableModel;
			return tableModelAdapter;
		}
		else
		{
			return null;
		}
	}
	private final ITerminalTableModel<T> model;

	public SwingTerminalTableModelAdapter(final ITerminalTableModel<T> model)
	{
		super();
		Check.assumeNotNull(model, "model not null");
		this.model = model;
	}

	@Override
	public int getRowCount()
	{
		return model.getRowCount();
	}

	@Override
	public int getColumnCount()
	{
		return model.getColumnCount();
	}

	@Override
	public String getColumnName(final int columnIndex)
	{
		return model.getColumnName(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex)
	{
		return model.getColumnClass(columnIndex);
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex)
	{
		return model.isCellEditable(rowIndex, columnIndex);
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex)
	{
		return model.getValueAt(rowIndex, columnIndex);
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex)
	{
		model.setValueAt(aValue, rowIndex, columnIndex);
	}

	@Override
	public void addTableModelListener(final TableModelListener l)
	{
		if (l == null)
		{
			return;
		}

		final ITerminalTableModelListener listener = new SwingTerminalTableModelListener(this, l);
		model.addTerminalTableModelListener(listener);
	}

	@Override
	public void removeTableModelListener(final TableModelListener l)
	{
		if (l == null)
		{
			return;
		}

		final ITerminalTableModelListener listener = new SwingTerminalTableModelListener(this, l);
		model.removeTerminalTableModelListener(listener);
	}
	
	public String getPrototypeValue(final int columnIndex)
	{
		return model.getTableColumnInfo(columnIndex).getPrototypeValue();
	}
}

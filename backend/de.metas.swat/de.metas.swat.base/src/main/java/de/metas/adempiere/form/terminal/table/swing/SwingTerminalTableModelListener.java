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


import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import de.metas.adempiere.form.terminal.table.ITerminalTableModelListener;
import de.metas.util.Check;

/**
 * Wraps an {@link TableModelListener} and makes it behave like an {@link ITerminalTableModelListener}.
 * 
 * @author tsa
 * 
 */
/* package */class SwingTerminalTableModelListener extends TerminalTableModelListenerAdapter
{
	private final TableModel tableModel;
	private final TableModelListener listener;

	public SwingTerminalTableModelListener(final TableModel tableModel, final TableModelListener listener)
	{
		super();
		Check.assumeNotNull(tableModel, "tableModel not null");
		this.tableModel = tableModel;

		Check.assumeNotNull(listener, "listener not null");
		this.listener = listener;
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(tableModel)
				.append(listener)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final SwingTerminalTableModelListener other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.appendByRef(this.tableModel, other.tableModel)
				.append(this.listener, other.listener)
				.isEqual();
	}

	private void fireTableChanged(TableModelEvent e)
	{
		listener.tableChanged(e);
	}

	@Override
	public void fireTableStructureChanged()
	{
		fireTableChanged(new TableModelEvent(tableModel, TableModelEvent.HEADER_ROW));
	}

	@Override
	public void fireTableRowsInserted(int rowFirst, int rowLast)
	{
		fireTableChanged(new TableModelEvent(tableModel, rowFirst, rowLast,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}

	@Override
	public void fireTableRowsUpdated(int rowFirst, int rowLast)
	{
		fireTableChanged(new TableModelEvent(tableModel, rowFirst, rowLast,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
	}

	@Override
	public void fireTableRowsDeleted(int rowFirst, int rowLast)
	{
		fireTableChanged(new TableModelEvent(tableModel, rowFirst, rowLast,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
	}

}

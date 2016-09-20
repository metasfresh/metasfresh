package de.metas.adempiere.form.terminal.table;

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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/* package */class CompositeTerminalTableModelListener implements ITerminalTableModelListener, IDisposable
{
	private List<ITerminalTableModelListener> listeners;

	private boolean disposed = false;

	public CompositeTerminalTableModelListener(final ITerminalContext terminalContext)
	{
		super();

		this.listeners = new ArrayList<ITerminalTableModelListener>();
		terminalContext.addToDisposableComponents(this);
	}

	@Override
	public void dispose()
	{
		clear();
		listeners = null;
		disposed  = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed ;
	}

	public boolean addTerminalTableModelListener(final ITerminalTableModelListener listener)
	{
		Check.assumeNotNull(listeners, "not already destroyed");

		Check.assumeNotNull(listener, "listener not null");
		if (listeners.contains(listener))
		{
			return false;
		}

		listeners.add(listener);
		return true;
	}

	public boolean removeTerminalTableModelListener(final ITerminalTableModelListener listener)
	{
		if (listeners == null)
		{
			// already destroyed
			return false;
		}

		Check.assumeNotNull(listener, "listener not null");
		return listeners.remove(listener);
	}

	public void clear()
	{
		if (listeners != null)
		{
			listeners.clear();
		}
	}

	@Override
	public void fireTableStructureChanged()
	{
		for (final ITerminalTableModelListener listener : listeners)
		{
			listener.fireTableStructureChanged();
		}
	}

	@Override
	public void fireTableRowsInserted(int rowFirst, int rowLast)
	{
		for (final ITerminalTableModelListener listener : listeners)
		{
			listener.fireTableRowsInserted(rowFirst, rowLast);
		}
	}

	@Override
	public void fireTableRowsUpdated(int rowFirst, int rowLast)
	{
		for (final ITerminalTableModelListener listener : listeners)
		{
			listener.fireTableRowsUpdated(rowFirst, rowLast);
		}
	}

	@Override
	public void fireTableRowsDeleted(int rowFirst, int rowLast)
	{
		for (final ITerminalTableModelListener listener : listeners)
		{
			listener.fireTableRowsDeleted(rowFirst, rowLast);
		}
	}

	@Override
	public void fireSelectionChanged()
	{
		for (final ITerminalTableModelListener listener : listeners)
		{
			listener.fireSelectionChanged();
		}
	}

	@Override
	public void fireRequestStopEditing()
	{
		for (final ITerminalTableModelListener listener : listeners)
		{
			listener.fireRequestStopEditing();
		}
	}
}

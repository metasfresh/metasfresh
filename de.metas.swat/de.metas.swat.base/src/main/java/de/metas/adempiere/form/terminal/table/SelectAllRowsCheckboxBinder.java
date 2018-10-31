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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicBoolean;

import de.metas.adempiere.form.terminal.ITerminalCheckboxField;
import de.metas.adempiere.form.terminal.ITerminalField;
import de.metas.adempiere.form.terminal.table.swing.TerminalTableModelListenerAdapter;
import de.metas.util.Check;

/**
 * Controller class used to bind a given "Select All" checkbox to the given {@link ITerminalTableModel}.
 * 
 * @author tsa
 *
 */
class SelectAllRowsCheckboxBinder
{
	private ITerminalTableModel<?> tableModel;
	private ITerminalCheckboxField selectAllCheckbox;

	private final AtomicBoolean sync = new AtomicBoolean(false);

	private final ITerminalTableModelListener tableModelListener = new TerminalTableModelListenerAdapter()
	{
		@Override
		public void fireSelectionChanged()
		{
			updateCheckboxStatus();
		};
	};

	private final PropertyChangeListener selectAllCheckboxListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			updateTableModelSelection();
		}
	};

	SelectAllRowsCheckboxBinder(final ITerminalTableModel<?> tableModel, final ITerminalCheckboxField selectAllCheckbox)
	{
		super();

		Check.assumeNotNull(tableModel, "tableModel not null");
		Check.assumeNotNull(selectAllCheckbox, "selectAllCheckbox not null");

		this.tableModel = tableModel;
		this.selectAllCheckbox = selectAllCheckbox;

		// Update checkbox status to make sure it's reflecting the state of given table.
		// NOTE: we do this BEFORE we add the listeners
		updateCheckboxStatus();

		// Add listeners
		this.tableModel.addTerminalTableModelListener(tableModelListener);
		this.selectAllCheckbox.addListener(ITerminalField.ACTION_ValueChanged, selectAllCheckboxListener);
	}

	private void updateCheckboxStatus()
	{
		// Make sure both components are in place, else do nothing
		if (tableModel == null || selectAllCheckbox == null)
		{
			return;
		}

		if (sync.getAndSet(true))
		{
			// already synchronizing
			return;
		}
		try
		{
			final boolean allRowsSelected = tableModel != null && tableModel.isAllRowsSelected();
			selectAllCheckbox.setValue(allRowsSelected);
		}
		finally
		{
			sync.set(false);
		}
	}

	protected void updateTableModelSelection()
	{
		// Make sure both components are in place, else do nothing
		if (tableModel == null || selectAllCheckbox == null)
		{
			return;
		}

		if (sync.getAndSet(true))
		{
			// already synchronizing
			return;
		}
		try
		{
			final boolean selectAll = selectAllCheckbox != null && selectAllCheckbox.getValue();
			if (selectAll)
			{
				tableModel.setSelectedRowsAll();
			}
			else
			{
				tableModel.setSelectedRowsNone();
			}
		}
		finally
		{
			sync.set(false);
		}

	}

}

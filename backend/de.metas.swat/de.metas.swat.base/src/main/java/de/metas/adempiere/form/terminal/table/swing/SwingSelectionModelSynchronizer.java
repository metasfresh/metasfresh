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


import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.WeakReference;

import de.metas.adempiere.form.terminal.table.ITerminalTableModel;

public class SwingSelectionModelSynchronizer<T>
		extends TerminalTableModelListenerAdapter
		implements ListSelectionListener
{
	private final IReference<ITerminalTableModel<T>> _tableModel;
	private final IReference<JTable> _tableSwing;
	private final AtomicBoolean sync = new AtomicBoolean(false);

	public SwingSelectionModelSynchronizer(final ITerminalTableModel<T> tableModel, final JTable tableSwing)
	{
		super();

		this._tableModel = new WeakReference<>(tableModel);
		this._tableSwing = new WeakReference<>(tableSwing);
	}

	private final ITerminalTableModel<T> getTableModel()
	{
		return _tableModel.getValue();
	}

	private final JTable getJTable()
	{
		return _tableSwing.getValue();
	}

	/**
	 * UI(model) to {@link ITerminalTableModel}
	 * 
	 */
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting())
		{
			return;
		}

		doSync(new Runnable()
		{

			@Override
			public void run()
			{
				final JTable tableSwing = getJTable();
				final ITerminalTableModel<T> tableModel = getTableModel();
				if (tableSwing == null || tableModel == null)
				{
					// references expired
					return;
				}

				final int[] selectedRowsView = tableSwing.getSelectedRows();
				if (selectedRowsView == null || selectedRowsView.length == 0)
				{
					tableModel.setSelectedRows((int[])null);
					return;
				}

				//
				// Convert row indexes from view indexes to model indexes
				final int[] selectedRowsModel = new int[selectedRowsView.length];
				for (int i = 0; i < selectedRowsView.length; i++)
				{
					final int rowIndexView = selectedRowsView[i];
					final int rowIndexModel = tableSwing.convertRowIndexToModel(rowIndexView);
					selectedRowsModel[i] = rowIndexModel;
				}

				tableModel.setSelectedRows(selectedRowsModel);
			}
		});

	}

	/**
	 * {@link ITerminalTableModel} to UI
	 */
	@Override
	public void fireSelectionChanged()
	{
		doSync(new Runnable()
		{

			@Override
			public void run()
			{
				final JTable tableSwing = getJTable();
				final ITerminalTableModel<T> tableModel = getTableModel();
				if (tableSwing == null || tableModel == null)
				{
					// references expired
					return;
				}

				final Set<Integer> rowIndices = tableModel.getSelectedRowIndices();

				final ListSelectionModel swingSelectionModel = tableSwing.getSelectionModel();
				swingSelectionModel.clearSelection();

				for (final int rowIndex : rowIndices)
				{
					swingSelectionModel.addSelectionInterval(rowIndex, rowIndex);
				}
			}
		});
	}

	private final void doSync(final Runnable runnable)
	{
		if (!sync.compareAndSet(false, true))
		{
			// sync is true, so we are out of here
			return;
		}
		try
		{
			runnable.run();
		}
		finally
		{
			sync.set(false);
		}
	}
}

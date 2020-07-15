package org.compiere.apps.search;

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


import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.util.Check;

/**
 * Listen on grid table cell change and notifies corresponding {@link IInfoColumnController} and also all others {@link IInfoColumnController}s that depends on it
 * 
 * @author tsa
 * 
 */
/* package */class InfoColumnControllerBinder implements TableModelListener
{
	private InfoSimple infoPanel;
	private final Info_Column infoColumn;
	private final TableModel tableModel;
	private final int columnIndexModel;
	//
	private boolean running = false;

	public InfoColumnControllerBinder(final InfoSimple infoPanel
			, final Info_Column infoColumn
			, final int columnIndexModel)
	{
		super();

		Check.assumeNotNull(infoPanel, "infoPanel not null");
		this.infoPanel = infoPanel;

		Check.assumeNotNull(infoColumn, "infoColumn not null");
		this.infoColumn = infoColumn;

		this.tableModel = infoPanel.getTableModel();
		this.columnIndexModel = columnIndexModel;
	}

	@Override
	public void tableChanged(final TableModelEvent e)
	{
		// Check: if table is just loading, those are load events which shall not be handled by this method
		// because here we are handling only user changes
		if (infoPanel.isLoading())
		{
			return;
		}
		
		// Avoid recursion
		if (running)
		{
			return;
		}
		running = true;

		try
		{
			if (e.getType() != TableModelEvent.UPDATE)
			{
				return;
			}
			if (e.getFirstRow() != e.getLastRow())
			{
				return;
			}
			final int currentRowIndexModel = e.getFirstRow();
			if (e.getColumn() != columnIndexModel
					&& e.getColumn() != TableModelEvent.ALL_COLUMNS)
			{
				return;
			}

			final Object value = tableModel.getValueAt(currentRowIndexModel, columnIndexModel);

			final SortedSet<String> propagationKeyColumnNames = new TreeSet<String>();

			//
			// Fire columns controller
			final IInfoColumnController columnController = infoColumn.getColumnController();
			if (columnController != null)
			{
				columnController.gridValueChanged(infoColumn,
						currentRowIndexModel,
						value);
				propagationKeyColumnNames.addAll(columnController.getValuePropagationKeyColumnNames(infoColumn));
			}

			//
			// Fire dependent controllers
			final List<Info_Column> dependentColumns = infoColumn.getDependentColumns();
			if (dependentColumns != null && !dependentColumns.isEmpty())
			{
				for (final Info_Column dependentColumn : dependentColumns)
				{
					if (dependentColumn == null)
					{
						continue;
					}
					final IInfoColumnController dependentController = dependentColumn.getColumnController();
					if (dependentController == null)
					{
						continue;
					}

					dependentController.gridValueChanged(infoColumn, currentRowIndexModel, value);

					propagationKeyColumnNames.addAll(dependentController.getValuePropagationKeyColumnNames(infoColumn));
				}
			}

			// Propagate the value to other rows that have the same Record_ID
			setValueForPropagationKey(value, propagationKeyColumnNames, currentRowIndexModel);
		}
		finally
		{
			running = false;
		}
	}

	/**
	 * Sets <code>value</code> to all rows that have the same propagation key as <code>referenceRowIndexModel</code>.
	 * 
	 * @param value value to be set
	 * @param propagationKeyColumnNames these columns will be used to match the propagation key
	 * @param referenceRowIndexModel
	 */
	private void setValueForPropagationKey(final Object value,
			final SortedSet<String> propagationKeyColumnNames,
			final int referenceRowIndexModel)
	{
		//
		// Create the propagation key of referenced row
		final ArrayKey propagationKey = createPropagationKey(referenceRowIndexModel, propagationKeyColumnNames);
		if (propagationKey == null)
		{
			return;
		}

		//
		// Iterate all rows, skip the reference one
		// and for all those rows which have same propagation key, set the given value
		for (int rowIndexModel = 0; rowIndexModel < tableModel.getRowCount(); rowIndexModel++)
		{
			// skip reference row
			if (rowIndexModel == referenceRowIndexModel)
			{
				continue;
			}

			// skip read-only cells
			if (!infoPanel.getMiniTable().isModelCellEditable(rowIndexModel, columnIndexModel))
			{
				continue;
			}

			// create propagation key of current row
			final ArrayKey currentPropagationKey = createPropagationKey(rowIndexModel, propagationKeyColumnNames);
			
			// skip rows which does not have same propagation key as reference row
			if (!propagationKey.equals(currentPropagationKey))
			{
				continue;
			}

			tableModel.setValueAt(value, rowIndexModel, columnIndexModel);
		}
	}

	/**
	 * Creates a propagation key for given <code>propagationKeyColumnNames</code> (a set of column names of which value to be used when building the propagation key).
	 * 
	 * @param row
	 * @param propagationKeyColumnNames set of key column names
	 * @return key
	 */
	private ArrayKey createPropagationKey(int row, SortedSet<String> propagationKeyColumnNames)
	{
		
		// If there are no column names in propagation key return null
		// this key shall be skipped and no propagation shall happen
		if (propagationKeyColumnNames == null || propagationKeyColumnNames.isEmpty())
		{
			return null;
		}

		//
		// Build propagation key values
		final List<Object> keyValues = new ArrayList<Object>(propagationKeyColumnNames.size());
		for (String propagationKeyColumnName : propagationKeyColumnNames)
		{
			final Object value = infoPanel.getValue(row, propagationKeyColumnName);
			keyValues.add(value);
		}

		return Util.mkKey(keyValues.toArray());
	}

}

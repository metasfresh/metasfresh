package org.compiere.grid;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTab;
import org.compiere.swing.CTable;
import org.compiere.swing.CTable.ITableColumnWidthCallback;

import de.metas.util.Check;

/**
 * Synchronize {@link TableColumn} settings with {@link GridFieldVO} settings.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/05731_Spaltenbreite_persitieren_%28103033707449%29
 */
public class CTableColumns2GridTabSynchronizer
{
	private static final String ATTR_CTableColumnPersistenceListener = CTableColumns2GridTabSynchronizer.class.getName();

	public static CTableColumns2GridTabSynchronizer get(final CTable table)
	{
		if (table == null)
		{
			return null;
		}

		final CTableColumns2GridTabSynchronizer listener = (CTableColumns2GridTabSynchronizer)table.getClientProperty(ATTR_CTableColumnPersistenceListener);
		return listener;
	}

	public static CTableColumns2GridTabSynchronizer setup(final CTable table, final GridTab gridTab)
	{
		if (get(table) != null)
		{
			throw new IllegalStateException("There is already an " + CTableColumns2GridTabSynchronizer.class + " registered for " + table);
		}

		final CTableColumns2GridTabSynchronizer listener = new CTableColumns2GridTabSynchronizer(table, gridTab);
		table.putClientProperty(ATTR_CTableColumnPersistenceListener, listener);

		return listener;
	}

	private final CTable table;
	private final GridTab gridTab;
	private boolean enabled = false;

	private final PropertyChangeListener onTableColumnChangedListener = new PropertyChangeListener()
	{

		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			final TableColumn column = (TableColumn)evt.getNewValue();
			if (column == null)
			{
				return;
			}

			saveTableColumn(column);
		}
	};

	private final ITableColumnWidthCallback tableColumnFixedWidthCallback = new ITableColumnWidthCallback()
	{
		@Override
		public int getWidth(TableColumn column)
		{
			final GridField gridField = getGridField(column);
			if (gridField == null)
			{
				return -1;
			}

			final GridFieldVO gridFieldVO = gridField.getVO();
			final int width = gridFieldVO.getLayoutConstraints().getColumnDisplayLength();
			if (width <= 0)
			{
				return -1;
			}
			return width;
		}
	};

	private final TableColumnModelListener tableColumnModelListener = new TableColumnModelListener()
	{
		@Override
		public void columnAdded(TableColumnModelEvent e)
		{
		}

		@Override
		public void columnMarginChanged(ChangeEvent e)
		{
			final CTable table = getTable();
			final TableColumn column = table.getTableHeader().getResizingColumn();
			if (column == null)
			{
				return;
			}
			saveTableColumn(column);
		}

		@Override
		public void columnMoved(TableColumnModelEvent e)
		{
			final int indexFrom = e.getFromIndex();
			final int indexTo = e.getToIndex();
			saveColumnMoved(indexFrom, indexTo);
		}

		@Override
		public void columnRemoved(TableColumnModelEvent e)
		{
		}

		@Override
		public void columnSelectionChanged(ListSelectionEvent e)
		{
		}
	};

	private CTableColumns2GridTabSynchronizer(final CTable table, final GridTab gridTab)
	{
		super();

		Check.assumeNotNull(table, "table not null");
		this.table = table;

		Check.assumeNotNull(gridTab, "gridTab not null");
		this.gridTab = gridTab;

		table.setTableColumnFixedWidthCallback(tableColumnFixedWidthCallback);
		setEnabled(true);

		loadFromGridTab();
	}

	public void setEnabled(final boolean enabled)
	{
		if (this.enabled == enabled)
		{
			return;
		}

		final TableColumnModel columnModel = table.getColumnModel();

		//
		// First, remove the listeners anyway to make sure we are not adding them twice
		columnModel.removeColumnModelListener(tableColumnModelListener);
		table.removePropertyChangeListener(CTable.PROPERTY_TableColumnChanged, onTableColumnChangedListener);

		//
		// If Enabled, add the listeners
		if (enabled)
		{
			columnModel.addColumnModelListener(tableColumnModelListener);
			table.addPropertyChangeListener(CTable.PROPERTY_TableColumnChanged, onTableColumnChangedListener);
		}

		this.enabled = enabled;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * Execute <code>runnable</code> with all listeners disabled
	 * 
	 * @param runnable
	 */
	private void runDisabled(final Runnable runnable)
	{
		final boolean enabledOld = isEnabled();
		setEnabled(false);
		try
		{
			runnable.run();
		}
		finally
		{
			setEnabled(enabledOld);
		}
	}

	public CTable getTable()
	{
		return table;
	}

	public void saveToGridTab()
	{
		runDisabled(new Runnable()
		{
			@Override
			public void run()
			{
				saveToGridTab0();
			}
		});
	}

	private void saveToGridTab0()
	{
		final GridTab gridTab = getGridTab();
		if (gridTab == null)
		{
			return;
		}

		//
		// Save column attributes
		final TableColumnModel tcm = table.getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++)
		{
			final TableColumn column = tcm.getColumn(i);
			saveTableColumn(column);
		}

		//
		// Save column sequence numbers
		saveColumnSeqNo();
	}

	/**
	 * Save {@link TableColumn} status to {@link GridTab}.
	 * 
	 * NOTE: SeqNo is not saved.
	 * 
	 * @param column
	 */
	private void saveTableColumn(final TableColumn column)
	{
		final GridField gridField = getGridField(column);
		if (gridField == null)
		{
			return;
		}
		final boolean visible = table.isColumnVisible(column);

		final GridFieldVO gridFieldVO = gridField.getVO();
		gridFieldVO.setDisplayedGrid(visible);

		if (visible)
		{
			gridFieldVO.getLayoutConstraints().setColumnDisplayLength(column.getWidth());
		}
	}

	/**
	 * Loads table column settings from given <code>gridField</code>.
	 * 
	 * NOTE: SeqNo is not loaded
	 * 
	 * @param tableColumn
	 * @param gridField
	 */
	private void loadTableColumn(final TableColumn tableColumn, final GridField gridField)
	{
		runDisabled(new Runnable()
		{
			@Override
			public void run()
			{
				loadTableColumn0(tableColumn, gridField);
			}
		});
	}

	private void loadTableColumn0(final TableColumn tableColumn, final GridField gridField)
	{
		//
		// No GridField associated with tableColumn => hide column
		if (gridField == null)
		{
			table.setColumnVisibility(tableColumn, false);
			return;
		}

		//
		// Load TableColumn properties from GridField
		final GridFieldVO gridFieldVO = gridField.getVO();
		final int width = gridFieldVO.getLayoutConstraints().getColumnDisplayLength();
		if (width > 0)
		{
			tableColumn.setPreferredWidth(width);
		}

		final boolean visible = gridFieldVO.isDisplayedGrid();
		table.setColumnVisibility(tableColumn, visible);
	}

	private void saveColumnMoved(final int indexFrom, final int indexTo)
	{
		if (indexFrom == indexTo)
		{
			// nothing changed
			return;
		}

		saveColumnSeqNo();
	}

	/**
	 * Save columns sequence number to {@link GridFieldVO}s.
	 */
	private void saveColumnSeqNo()
	{
		final GridTab mTab = getGridTab();
		if (mTab == null)
		{
			return;
		}

		final TableColumnModel tcm = table.getColumnModel();
		final int columnCount = tcm.getColumnCount();
		int nextSeqNo = 10;
		for (int i = 0; i < columnCount; i++)
		{
			final TableColumn column = tcm.getColumn(i);
			final GridField gridField = getGridField(column);
			if (gridField == null)
			{
				continue;
			}

			final GridFieldVO gridFieldVO = gridField.getVO();

			final int seqNo;
			if (table.isColumnVisible(column) && gridFieldVO.isDisplayedGrid())
			{
				seqNo = nextSeqNo;
				nextSeqNo += 10;
			}
			else
			{
				seqNo = 0;
			}

			gridFieldVO.setSeqNoGrid(seqNo);
		}
	}

	public GridTab getGridTab()
	{
		return gridTab;
	}

	private GridField getGridField(final TableColumn column)
	{
		final GridTab mTab = getGridTab();

		if (mTab == null)
		{
			return null;
		}

		final String columnName = getColumnName(column);
		if (columnName == null)
		{
			return null;
		}

		final GridField gridField = mTab.getField(columnName);
		return gridField;
	}

	private String getColumnName(final TableColumn column)
	{
		if (column == null)
		{
			return null;
		}

		if (column.getIdentifier() == null)
		{
			return null;
		}
		final String columnName = column.getIdentifier().toString();
		if (Check.isEmpty(columnName, true))
		{
			return null;
		}

		return columnName;
	}

	/**
	 * Load {@link TableColumn} status from {@link GridTab}
	 */
	private void loadFromGridTab()
	{
		runDisabled(new Runnable()
		{
			@Override
			public void run()
			{
				loadFromGridTab0();
			}
		});
	}

	private void loadFromGridTab0()
	{
		final GridTab mTab = getGridTab();
		if (mTab == null)
		{
			return;
		}

		//
		// Get GridFields and sort them by SeqNoGrid
		final GridField[] gridFields = mTab.getFields(); // NOTE: returns a copy
		Arrays.sort(gridFields, new Comparator<GridField>()
		{
			@Override
			public int compare(GridField o1, GridField o2)
			{
				if (o1 == o2)
				{
					return 0;
				}
				final GridFieldVO vo1 = o1.getVO();
				final GridFieldVO vo2 = o2.getVO();
				final int seqNo1 = vo1.isDisplayedGrid() && vo1.getSeqNoGrid() > 0 ? vo1.getSeqNoGrid() : Integer.MAX_VALUE;
				final int seqNo2 = vo2.isDisplayedGrid() && vo2.getSeqNoGrid() > 0 ? vo2.getSeqNoGrid() : Integer.MAX_VALUE;
				return seqNo1 - seqNo2;
			}
		});

		//
		// From GridTab's fields
		// Build map: ColumnName to GridField
		// Build list: visible columnNames
		final Map<String, GridField> columnName2GridField = new HashMap<String, GridField>(gridFields.length);
		final List<String> visibleColumnNames = new ArrayList<String>(gridFields.length);
		for (int i = 0; i < gridFields.length; i++)
		{
			final GridField gridField = gridFields[i];
			final GridFieldVO gridFieldVO = gridField.getVO();
			final String columnName = gridField.getColumnName();
			columnName2GridField.put(columnName, gridField);

			final boolean visible = gridFieldVO.isDisplayedGrid();
			if (visible)
			{
				visibleColumnNames.add(columnName);
			}
		}

		//
		// From Table's column model
		// Load column from GridField
		final TableColumnModel tableColumnModel = table.getColumnModel();
		final int tableColumnsCount = tableColumnModel.getColumnCount();
		for (int i = 0; i < tableColumnsCount; i++)
		{
			final TableColumn column = tableColumnModel.getColumn(i);
			final String columnName = getColumnName(column);

			// Update
			final GridField gridField = columnName2GridField.get(columnName);
			loadTableColumn(column, gridField);
		}

		//
		// Reorder table's columns based on GridField settings
		int currentIdx = 0;
		final int visibleFieldsCount = visibleColumnNames.size();
		for (final String columnName : visibleColumnNames)
		{
			if (currentIdx == visibleFieldsCount - 1)
			{
				// no reordering for the last one (see javadoc of 'tcm.moveColumn')
				continue;
			}

			// Search where our column is now
			int fromIdx = -1;
			for (int i = currentIdx; i < tableColumnsCount; i++)
			{
				final TableColumn column = tableColumnModel.getColumn(i);
				final String fromColumnName = getColumnName(column);
				if (columnName.equals(fromColumnName))
				{
					fromIdx = i;
					break;
				}
			}

			// Move column
			if (fromIdx != currentIdx)
			{
				tableColumnModel.moveColumn(fromIdx, currentIdx);
			}
			currentIdx++;
		}
	}

}

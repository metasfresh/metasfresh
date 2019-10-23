package org.compiere.swing.table;

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


import java.awt.Color;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

import de.metas.util.Check;

/**
 * {@link TableModel} implementation which introspects the a model class, checking the {@link ColumnInfo} annotated methods.
 *
 * @author tsa
 *
 * @param <ModelType> model type
 */
public class AnnotatedTableModel<ModelType> extends AbstractTableModel
{
	private static final long serialVersionUID = 8820863581724987690L;

	private final TableModelMetaInfo<ModelType> metaInfo;

	private boolean editable = true;

	private final List<ModelType> rows = new ArrayList<ModelType>();

	/**
	 * @param modelClass the class of row data to be added to the model
	 */
	public AnnotatedTableModel(final Class<ModelType> modelClass)
	{
		super();

		this.metaInfo = TableModelMetaInfo.ofModelClass(modelClass);
	}

	public final TableModelMetaInfo<ModelType> getMetaInfo()
	{
		return metaInfo;
	}

	public final void setColumnNames(final String... columnNames)
	{
		Check.assumeNotNull(columnNames, "columnNames not null");
		final List<String> columnNamesNew = ImmutableList.copyOf(columnNames);
		final List<String> columnNamesOld = metaInfo.getColumnNames();
		if (Check.equals(columnNamesOld, columnNamesNew))
		{
			// nothing changed
			return;
		}

		metaInfo.setColumnNames(columnNamesNew);
		fireTableStructureChanged();
	}

	/**
	 * Returns the Class of the queried <code>column</code>.
	 *
	 * First it will check to see if a Class has been specified for the <code>column</code> by using the <code>setColumnClass</code> method. If not, then the superclass value is returned.
	 *
	 * @param column the column being queried
	 * @return the Class of the column being queried
	 */
	@Override
	public Class<?> getColumnClass(final int column)
	{
		// Get the class, if set, for the specified column
		final ITableColumnInfo columnInfo = getTableColumnInfo(column);
		final Class<?> columnClass = columnInfo.getColumnClass();
		if (columnClass != null)
		{
			return columnClass;
		}

		return Object.class;
	}

	public final int getColumnIndexByColumnName(final String columnName)
	{
		return metaInfo.getColumnIndexByColumnName(columnName);
	}

	public final String getColumnNameByColumnIndex(final int column)
	{
		return metaInfo.getColumnNameByColumnIndex(column);
	}

	public final TableColumnInfo getTableColumnInfo(final int columnModelIndex)
	{
		return metaInfo.getTableColumnInfo(columnModelIndex);
	}

	public final TableColumnInfo getTableColumnInfo(final String columnName)
	{
		return metaInfo.getTableColumnInfo(columnName);
	}

	/**
	 * Returns the number of columns in this table model.
	 *
	 * @return the number of columns in the model
	 */
	@Override
	public final int getColumnCount()
	{
		return metaInfo.getColumnCount();
	}

	/**
	 *
	 * @return column display name
	 */
	@Override
	public final String getColumnName(final int column)
	{
		final ITableColumnInfo columnInfo = getTableColumnInfo(column);
		final String displayName = columnInfo.getDisplayName();
		if (displayName != null)
		{
			return displayName;
		}

		return columnInfo.getColumnName();
	}

	@Override
	public final int getRowCount()
	{
		return rows.size();
	}

	@Override
	public final boolean isCellEditable(final int row, final int column)
	{
		//
		// Check if the whole table is read-only
		if (!editable)
		{
			return false;
		}

		//
		// Check if the column is read-only
		final ITableColumnInfo columnInfo = getTableColumnInfo(column);
		if (!columnInfo.isEditable())
		{
			return false;
		}
		// If the column has no write method, consider it read-only
		if (columnInfo.getWriteMethod() == null)
		{
			return false;
		}

		//
		// Check custom method is this cell shall be considered read-only
		if (!isCellEditableExt(row, column))
		{
			return false;
		}

		// Fallback: allow editing this cell
		return true;
	}

	/**
	 * Checks if the given cell is editable.
	 * 
	 * Extending classes shall feel free to implement this method with custom logic.
	 * 
	 * NOTE: NEVER call this method because it's called by {@link #isCellEditable(int, int)} when needed.
	 * 
	 * @param modelRowIndex
	 * @param modelColumnIndex
	 * @return true if given cell is editable.
	 */
	protected boolean isCellEditableExt(final int modelRowIndex, final int modelColumnIndex)
	{
		return true;
	}

	//
	// Implement custom methods
	//
	/**
	 * Adds a row of data to the end of the model. Notification of the row being added will be generated.
	 *
	 * @param rowData data of the row being added
	 */
	public final void addRow(final ModelType rowData)
	{
		insertRow(getRowCount(), rowData);
	}

	/**
	 * Returns the Object of the requested <code>row</code>.
	 *
	 * @return the Object of the requested row.
	 */
	public final ModelType getRow(final int rowModelIndex)
	{
		return rows.get(rowModelIndex);
	}

	public final int getRowIndex(final ModelType row)
	{
		if (row == null)
		{
			return -1;
		}

		return rows.indexOf(row);
	}

	/**
	 * Returns an array of Objects for the requested <code>rows</code>.
	 *
	 * @return an array of Objects for the requested rows.
	 */
	@SuppressWarnings("unchecked")
	public final ModelType[] getRowsAsArray(final int... rows)
	{
		final Class<ModelType> modelClass = metaInfo.getModelClass();
		final List<ModelType> rowData = getRowsAsList(rows);
		final ModelType[] array = (ModelType[])Array.newInstance(modelClass, rowData.size());
		return rowData.toArray(array);
	}

	/**
	 * Returns a List of Objects for the requested <code>rows</code>.
	 *
	 * @return a List of Objects for the requested rows.
	 */
	public final List<ModelType> getRowsAsList(final int... rows)
	{
		if (rows == null || rows.length == 0)
		{
			return Collections.emptyList();
		}

		final List<ModelType> rowData = new ArrayList<ModelType>(rows.length);

		for (int i = 0; i < rows.length; i++)
		{
			rowData.add(getRow(rows[i]));
		}

		return rowData;
	}

	public final List<ModelType> getRows()
	{
		return new ArrayList<>(rows);
	}

	protected final List<ModelType> getRowsInnerList()
	{
		return rows;
	}

	public final FluentIterable<ModelType> getRowsAsIterable()
	{
		final List<ModelType> rows = getRows(); // take a copy of current rows
		return FluentIterable.from(rows);
	}

	/**
	 * Remove all rows
	 */
	public final void removeRows()
	{
		if (rows.isEmpty())
		{
			// already empty
			return;
		}

		final int rowFirst = 0;
		final int rowLast = rows.size() - 1;

		rows.clear();

		fireTableRowsDeleted(rowFirst, rowLast);
	}

	public final void addRows(final Collection<ModelType> rowsToAdd)
	{
		if (rowsToAdd == null || rowsToAdd.isEmpty())
		{
			return;
		}

		final int rowFirst = rows.size();
		rows.addAll(rowsToAdd);
		final int rowLast = rows.size() - 1;

		fireTableRowsInserted(rowFirst, rowLast);
	}

	public final void setRows(final Collection<ModelType> rows)
	{
		// Request stop editing and wait for it
		// listeners.fireRequestStopEditing(); // FIXME

		removeRows();
		addRows(rows);
	}

	/**
	 * Insert a row of data at the <code>row</code> location in the model. Notification of the row being added will be generated.
	 *
	 * @param row row in the model where the data will be inserted
	 * @param rowData data of the row being added
	 */
	public final void insertRow(final int row, final ModelType rowData)
	{
		rows.add(row, rowData);
		fireTableRowsInserted(row, row);
	}

	/**
	 * Insert multiple rows of data at the <code>row</code> location in the model. Notification of the row being added will be generated.
	 *
	 * @param row row in the model where the data will be inserted
	 * @param rowList each item in the list is a separate row of data
	 */
	public final void insertRows(final int row, final List<ModelType> rowList)
	{
		rows.addAll(row, rowList);
		fireTableRowsInserted(row, row + rowList.size() - 1);
		fireTableStructureChanged();
	}

	/**
	 * Insert multiple rows of data at the <code>row</code> location in the model. Notification of the row being added will be generated.
	 *
	 * @param row row in the model where the data will be inserted
	 * @param rowArray each item in the Array is a separate row of data
	 */
	public final void insertRows(final int row, final ModelType[] rowArray)
	{
		final List<ModelType> rowList = new ArrayList<ModelType>(rowArray.length);

		for (int i = 0; i < rowArray.length; i++)
		{
			rowList.add(rowArray[i]);
		}

		insertRows(row, rowList);
	}

	/**
	 * Moves one or more rows from the inlcusive range <code>start</code> to <code>end</code> to the <code>to</code> position in the model. After the move, the row that was at index <code>start</code>
	 * will be at index <code>to</code>. This method will send a <code>tableRowsUpdated</code> notification message to all the listeners.
	 * <p>
	 *
	 * <pre>
	 *  Examples of moves:
	 *  <p>
	 *  1. moveRow(1,3,5);
	 * 	  a|B|C|D|e|f|g|h|i|j|k   - before
	 * 	  a|e|f|g|h|B|C|D|i|j|k   - after
	 *  <p>
	 *  2. moveRow(6,7,1);
	 * 	  a|b|c|d|e|f|G|H|i|j|k   - before
	 * 	  a|G|H|b|c|d|e|f|i|j|k   - after
	 *  <p>
	 * </pre>
	 *
	 * @param start the starting row index to be moved
	 * @param end the ending row index to be moved
	 * @param to the destination of the rows to be moved
	 * @exception IllegalArgumentException if any of the elements would be moved out of the table's range
	 */
	public final void moveRow(final int start, final int end, final int to)
	{
		if (start < 0)
		{
			final String message = "Start index must be positive: " + start;
			throw new IllegalArgumentException(message);
		}

		if (end > getRowCount() - 1)
		{
			final String message = "End index must be less than total rows: " + end;
			throw new IllegalArgumentException(message);
		}

		if (start > end)
		{
			final String message = "Start index cannot be greater than end index";
			throw new IllegalArgumentException(message);
		}

		final int rowsMoved = end - start + 1;

		if (to < 0
				|| to > getRowCount() - rowsMoved)
		{
			final String message = "New destination row (" + to + ") is invalid";
			throw new IllegalArgumentException(message);
		}

		// Save references to the rows that are about to be moved

		final ArrayList<ModelType> temp = new ArrayList<ModelType>(rowsMoved);

		for (int i = start; i < end + 1; i++)
		{
			temp.add(rows.get(i));
		}

		// Remove the rows from the current location and add them back
		// at the specified new location

		rows.subList(start, end + 1).clear();
		rows.addAll(to, temp);

		// Determine the rows that need to be repainted to reflect the move

		int first;
		int last;

		if (to < start)
		{
			first = to;
			last = end;
		}
		else
		{
			first = start;
			last = to + end - start;
		}

		fireTableRowsUpdated(first, last);
	}

	/**
	 * Remove the specified rows from the model. Rows between the starting and ending indexes, inclusively, will be removed. Notification of the rows being removed will be generated.
	 *
	 * @param start starting row index
	 * @param end ending row index
	 * @exception ArrayIndexOutOfBoundsException if any row index is invalid
	 */
	public final void removeRowRange(final int start, final int end)
	{
		rows.subList(start, end + 1).clear();
		fireTableRowsDeleted(start, end);
	}

	/**
	 * Remove the specified rows from the model. The row indexes in the array must be in increasing order. Notification of the rows being removed will be generated.
	 *
	 * @param rows array containing indexes of rows to be removed
	 * @exception ArrayIndexOutOfBoundsException if any row index is invalid
	 */
	public final void removeRows(final int... rowIndexes)
	{
		for (int i = rowIndexes.length - 1; i >= 0; i--)
		{
			final int row = rowIndexes[i];
			rows.remove(row);
			fireTableRowsDeleted(row, row);
		}
	}
	
	public final void removeRows(final Iterable<ModelType> rowsToRemove)
	{
		if (rowsToRemove == null)
		{
			return;
		}
		
		for (final ModelType rowToRemove : rowsToRemove)
		{
			removeRow(rowToRemove);
		}
	}
	
	public final void removeRow(final ModelType rowToRemove)
	{
		if (rowToRemove == null)
		{
			return;
		}
		
		final int rowIndex = getRowIndex(rowToRemove);
		removeRows(rowIndex);
	}

	/**
	 * Replace a row of data at the <code>row</code> location in the model. Notification of the row being replaced will be generated.
	 *
	 * @param row row in the model where the data will be replaced
	 * @param rowData data of the row to replace the existing data
	 * @exception IllegalArgumentException when the Class of the row data does not match the row Class of the model.
	 */
	public final void replaceRow(final int row, final ModelType rowData)
	{
		rows.set(row, rowData);
		fireTableRowsUpdated(row, row);
	}

	/**
	 * Sets the Class for the specified column.
	 *
	 * @param column the column whose Class is being changed
	 * @param columnClass the new Class of the column
	 * @exception ArrayIndexOutOfBoundsException if an invalid column was given
	 */
	public final void setColumnClass(final String columnName, final Class<?> columnClass)
	{
		final TableColumnInfo columnInfo = getTableColumnInfo(columnName);
		columnInfo.setColumnClass(columnClass);
		fireTableRowsUpdated(0, getRowCount() - 1);
	}

	/**
	 * Sets the editability for the specified column.
	 *
	 * @param column the column whose Class is being changed
	 * @param editable indicates if the column is editable or not
	 * @exception ArrayIndexOutOfBoundsException if an invalid column was given
	 */
	public final void setColumnEditable(final int column, final boolean editable)
	{
		final TableColumnInfo columnInfo = getTableColumnInfo(column);
		columnInfo.setEditable(editable);
	}

	/**
	 * Set the ability to edit cell data for the entire model
	 *
	 * Note: values set by the setColumnEditable(...) method will have prioritiy over this value.
	 *
	 * @param editable true/false
	 */
	public final void setEditable(final boolean editable)
	{
		this.editable = editable;
	}

	public final boolean isEditable()
	{
		return this.editable;
	}

	@Override
	public final Object getValueAt(final int rowModelIndex, final int columnModelIndex)
	{
		final TableColumnInfo columnInfo = getTableColumnInfo(columnModelIndex);
		final ModelType row = getRow(rowModelIndex);

		Object value;
		try
		{
			value = columnInfo.getReadMethod().invoke(row);
		}
		catch (final IllegalAccessException e)
		{
			throw new AdempiereException("Cannot get value for " + columnInfo, e);
		}
		catch (final InvocationTargetException e)
		{
			final Throwable cause = e.getTargetException() == null ? e : e.getTargetException();
			throw new AdempiereException("Cannot get value for " + columnInfo, cause);
		}
		return value;
	}

	@Override
	public final void setValueAt(final Object value, final int rowModelIndex, final int columnModelIndex)
	{
		final TableColumnInfo columnInfo = getTableColumnInfo(columnModelIndex);
		final ModelType row = getRow(rowModelIndex);

		try
		{
			columnInfo.getWriteMethod().invoke(row, value);
		}
		catch (final IllegalAccessException e)
		{
			throw new AdempiereException("Cannot set value for " + columnInfo, e);
		}
		catch (final InvocationTargetException e)
		{
			final Throwable cause = e.getTargetException() == null ? e : e.getTargetException();
			throw new AdempiereException("Cannot set value for " + columnInfo, cause);
		}

		fireTableCellUpdated(rowModelIndex, columnModelIndex);
	}

	/**
	 * Notifies all listeners that all values of given column where changed.
	 *
	 * @param columnName
	 * @see TableModelEvent
	 */
	protected final void fireTableColumnChanged(final String columnName)
	{
		final int rowCount = getRowCount();
		if (rowCount <= 0)
		{
			// no rows => no point to fire the event
			return;
		}

		final int columnIndex = getColumnIndexByColumnName(columnName);
		final int firstRow = 0;
		final int lastRow = rowCount - 1;
		final TableModelEvent event = new TableModelEvent(this, firstRow, lastRow, columnIndex, TableModelEvent.UPDATE);
		fireTableChanged(event);
	}
	
	public final void fireTableRowsUpdated(final Collection<ModelType> rows)
	{
		if (rows == null || rows.isEmpty())
		{
			return;
		}
		
		// NOTE: because we are working with small amounts of rows,
		// it's pointless to figure out which are the row indexes of those rows
		// so it's better to fire a full data change event.
		// In future we can optimize this.
		fireTableDataChanged();
	}

	/**
	 * @return foreground color to be used when rendering the given cell or <code>null</code> if no suggestion
	 */
	protected Color getCellForegroundColor(final int modelRowIndex, final int modelColumnIndex)
	{
		return null;
	}
	
	public List<ModelType> getSelectedRows(final ListSelectionModel selectionModel, final Function<Integer, Integer> convertRowIndexToModel)
	{
		final int rowMinView = selectionModel.getMinSelectionIndex();
		final int rowMaxView = selectionModel.getMaxSelectionIndex();
		if (rowMinView < 0 || rowMaxView < 0)
		{
			return ImmutableList.of();
		}
		
		final ImmutableList.Builder<ModelType> selection = ImmutableList.builder(); 
		for (int rowView = rowMinView; rowView <= rowMaxView; rowView++)
		{
			if (selectionModel.isSelectedIndex(rowView))
			{
				final int rowModel = convertRowIndexToModel.apply(rowView);
				selection.add(getRow(rowModel));
			}
		}
		
		return selection.build();
	}
	
	public ModelType getSelectedRow(final ListSelectionModel selectionModel, final Function<Integer, Integer> convertRowIndexToModel)
	{
		final int rowIndexView = selectionModel.getMinSelectionIndex();
		if (rowIndexView < 0)
		{
			return null;
		}
		
		final int rowIndexModel = convertRowIndexToModel.apply(rowIndexView);
		
		return getRow(rowIndexModel);
	}
}

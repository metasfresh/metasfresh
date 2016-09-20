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


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.Env;

import de.metas.adempiere.form.terminal.ITerminalCheckboxField;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.table.annotation.TableInfo;

/**
 *
 * @author http://www.camick.com/java/source/RowTableModel.java
 * @author tsa
 */
public class TerminalTableModel<T> implements ITerminalTableModel<T>
{
	private final Class<T> beanClass;
	private List<String> columnNames;
	private Map<String, TableColumnInfo> columnInfos = new HashMap<String, TableColumnInfo>();

	private boolean editable = true;

	private final List<T> rows = new ArrayList<T>();
	private final Set<T> rowsSelected = new HashSet<T>();
	private final Set<T> rowsSelectedRO = Collections.unmodifiableSet(rowsSelected);

	private final CompositeTerminalTableModelListener listeners;

	//
	// Selection
	private SelectionMode selectionMode = SelectionMode.SINGLE;

	private boolean disposed = false;

	/**
	 * Constructs a <code>RowTableModel</code> with the row class.
	 *
	 * This value is used by the getRowsAsArray() method.
	 *
	 * Sub classes creating a model using this constructor must make sure to invoke the setDataAndColumnNames() method.
	 *
	 * @param beanClass the class of row data to be added to the model
	 */
	public TerminalTableModel(final ITerminalContext terminalContext, final Class<T> beanClass)
	{
		Check.assumeNotNull(beanClass, "beanClass not null");
		this.beanClass = beanClass;

		this.listeners = new CompositeTerminalTableModelListener(terminalContext);

		try
		{
			setupColumnInfos();
		}
		catch (final IntrospectionException e)
		{
			throw new RuntimeException("Error while loading info from " + beanClass, e);
		}
		terminalContext.addToDisposableComponents(this);
	}

	private Properties getCtx()
	{
		return Env.getCtx(); // FIXME
	}

	private void setupColumnInfos() throws IntrospectionException
	{
		columnNames = new ArrayList<String>();
		columnInfos = new HashMap<String, TableColumnInfo>();

		final BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
		for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors())
		{
			final Method readMethod = pd.getReadMethod();
			if (readMethod == null)
			{
				// we cannot read this property, skip it
				continue;
			}

			// skip methods declared on Object class level
			if (Object.class.equals(readMethod.getDeclaringClass()))
			{
				continue;
			}

			loadInfo(pd);

			if (pd.isHidden())
			{
				continue;
			}

			final String columnName = pd.getName();
			final String displayName = pd.getDisplayName();
			pd.setDisplayName(displayName);
			final Class<?> columnClass = pd.getPropertyType();
			final Method writeMethod = pd.getWriteMethod();
			final int seqNo = getSeqNo(pd);
			final String lookupTableName = getLookupTableName(pd);
			final String lookupColumnName = getLookupColumnName(pd);
			final String prototypeValue = getPrototypeValue(pd);

			final TableColumnInfo columnInfo = new TableColumnInfo(columnName, columnClass, readMethod);
			columnInfo.setSeqNo(seqNo);
			columnInfo.setDisplayName(displayName);
			columnInfo.setWriteMethod(writeMethod);
			columnInfo.setEditable(isEditable());
			columnInfo.setLookupTableName(lookupTableName);
			columnInfo.setLookupColumnName(lookupColumnName);
			columnInfo.setPrototypeValue(prototypeValue);

			//
			columnNames.add(columnName);
			columnInfos.put(columnName, columnInfo);
		}

		//
		// Sort columnNames by TableColumnInfo's SeqNo
		Collections.sort(columnNames, new Comparator<String>()
		{

			@Override
			public int compare(final String o1, final String o2)
			{
				if (Check.equals(o1, o2))
				{
					return 0;
				}

				final ITableColumnInfo columnInfo1 = columnInfos.get(o1);
				final int seqNo1 = columnInfo1 == null ? Integer.MAX_VALUE : columnInfo1.getSeqNo();

				final ITableColumnInfo columnInfo2 = columnInfos.get(o2);
				final int seqNo2 = columnInfo2 == null ? Integer.MAX_VALUE : columnInfo2.getSeqNo();

				return seqNo1 - seqNo2;
			}
		});
	}

	private void loadInfo(final PropertyDescriptor pd)
	{
		final Method readMethod = pd.getReadMethod();
		if (readMethod != null)
		{
			loadInfo(pd, readMethod);
		}

		final Method writeMethod = pd.getWriteMethod();
		if (writeMethod != null)
		{
			loadInfo(pd, writeMethod);
		}
	}

	private void loadInfo(final PropertyDescriptor pd, final Method method)
	{
		final TableInfo info = method.getAnnotation(TableInfo.class);
		if (info == null)
		{
			return;
		}

		if (!Check.isEmpty(info.displayName(), false))
		{
			String displayName = info.displayName();
			if (info.translate())
			{
				displayName = Services.get(IMsgBL.class).translate(getCtx(), displayName);
			}

			pd.setDisplayName(displayName);
		}

		if (info.hidden())
		{
			pd.setHidden(true);
		}
	}

	private int getSeqNo(final PropertyDescriptor pd)
	{
		final Method method = pd.getReadMethod();
		if (method == null)
		{
			return Integer.MAX_VALUE;
		}
		final TableInfo info = method.getAnnotation(TableInfo.class);
		if (info == null)
		{
			return Integer.MAX_VALUE;
		}

		return info.seqNo();
	}

	private String getLookupTableName(final PropertyDescriptor pd)
	{
		String lookupTableName = getLookupTableName(pd.getReadMethod());
		if (Check.isEmpty(lookupTableName))
		{
			lookupTableName = getLookupTableName(pd.getWriteMethod());
		}

		return lookupTableName;
	}

	private String getLookupTableName(final Method method)
	{
		if (method == null)
		{
			return null;
		}
		final TableInfo info = method.getAnnotation(TableInfo.class);
		if (info == null)
		{
			return null;
		}
		final String lookupTableName = info.lookupTableName();
		if (Check.isEmpty(lookupTableName))
		{
			return null;
		}
		return lookupTableName;
	}

	private String getLookupColumnName(final PropertyDescriptor pd)
	{
		String lookupColumnName = getLookupColumnName(pd.getReadMethod());
		if (Check.isEmpty(lookupColumnName))
		{
			lookupColumnName = getLookupColumnName(pd.getWriteMethod());
		}

		return lookupColumnName;
	}

	private String getLookupColumnName(final Method method)
	{
		if (method == null)
		{
			return null;
		}
		final TableInfo info = method.getAnnotation(TableInfo.class);
		if (info == null)
		{
			return null;
		}
		final String lookupColumnName = info.lookupColumnName();
		if (Check.isEmpty(lookupColumnName))
		{
			return null;
		}
		return lookupColumnName;
	}

	private String getPrototypeValue(final PropertyDescriptor pd)
	{
		String prototypeValue = getPrototypeValue(pd.getReadMethod());
		if (Check.isEmpty(prototypeValue))
		{
			prototypeValue = getPrototypeValue(pd.getWriteMethod());
		}

		return prototypeValue;

	}

	private String getPrototypeValue(final Method method)
	{
		if (method == null)
		{
			return null;
		}
		final TableInfo info = method.getAnnotation(TableInfo.class);
		if (info == null)
		{
			return null;
		}
		final String prototypeValue = info.prototypeValue();
		if (Check.isEmpty(prototypeValue, false))
		{
			return null;
		}
		return prototypeValue;
	}

	@Override
	public void setColumnNames(final String... columnNames)
	{
		Check.assumeNotNull(columnNames, "columnNames not null");

		final List<String> columnNamesNew = new ArrayList<String>(columnNames.length);
		for (final String columnName : columnNames)
		{
			Check.assumeNotNull(columnName, "columnName not null");
			columnNamesNew.add(columnName);
		}

		final List<String> columnNamesOld = this.columnNames;
		if (Check.equals(columnNamesOld, columnNamesNew))
		{
			// nothing changed
			return;
		}

		this.columnNames = columnNamesNew;
		listeners.fireTableStructureChanged();
	}

	@Override
	public void addTerminalTableModelListener(final ITerminalTableModelListener listener)
	{
		listeners.addTerminalTableModelListener(listener);
	}

	@Override
	public void removeTerminalTableModelListener(final ITerminalTableModelListener listener)
	{
		listeners.removeTerminalTableModelListener(listener);
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
		final int column = columnNames.indexOf(columnName);
		if (column < 0)
		{
			throw new IllegalArgumentException("ColumnName '" + columnName + "' was not found");
		}
		return column;
	}

	public final String getColumnNameByColumnIndex(final int column)
	{
		return columnNames.get(column);
	}

	@Override
	public final TableColumnInfo getTableColumnInfo(final int column)
	{
		final String columnName = getColumnNameByColumnIndex(column);
		return getTableColumnInfo(columnName);
	}

	@Override
	public final TableColumnInfo getTableColumnInfo(final String columnName)
	{
		Check.assumeNotNull(columnName, "columnName not null");
		final TableColumnInfo columnInfo = columnInfos.get(columnName);
		if (columnInfo == null)
		{
			throw new IllegalStateException("No column info found for column name '" + columnName + "'");
		}

		return columnInfo;
	}

	/**
	 * Returns the number of columns in this table model.
	 *
	 * @return the number of columns in the model
	 */
	@Override
	public int getColumnCount()
	{
		return columnNames.size();
	}

	/**
	 *
	 * @return column display name
	 */
	@Override
	public String getColumnName(final int column)
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
	public int getRowCount()
	{
		return rows.size();
	}

	@Override
	public boolean isCellEditable(final int row, final int column)
	{
		if (!editable)
		{
			return false;
		}

		final ITableColumnInfo columnInfo = getTableColumnInfo(column);
		if (!columnInfo.isEditable())
		{
			return false;
		}

		if (columnInfo.getWriteMethod() == null)
		{
			return false;
		}

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
	public void addRow(final T rowData)
	{
		insertRow(getRowCount(), rowData);
	}

	/**
	 * Returns the Object of the requested <code>row</code>.
	 *
	 * @return the Object of the requested row.
	 */
	public T getRow(final int rowModelIndex)
	{
		return rows.get(rowModelIndex);
	}

	public int getRowIndex(final T row)
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
	public T[] getRowsAsArray(final int... rows)
	{
		final List<T> rowData = getRowsAsList(rows);
		final T[] array = (T[])Array.newInstance(beanClass, rowData.size());
		return rowData.toArray(array);
	}

	/**
	 * Returns a List of Objects for the requested <code>rows</code>.
	 *
	 * @return a List of Objects for the requested rows.
	 */
	public List<T> getRowsAsList(final int... rows)
	{
		if (rows == null || rows.length == 0)
		{
			return Collections.emptyList();
		}

		final List<T> rowData = new ArrayList<T>(rows.length);

		for (int i = 0; i < rows.length; i++)
		{
			rowData.add(getRow(rows[i]));
		}

		return rowData;
	}

	public void removeRows()
	{
		if (rows.isEmpty())
		{
			// already empty
			return;
		}

		final int rowFirst = 0;
		final int rowLast = rows.size() - 1;

		rows.clear();

		listeners.fireTableRowsDeleted(rowFirst, rowLast);
	}

	public void addRows(final List<T> rowsToAdd)
	{
		if (rowsToAdd == null || rowsToAdd.isEmpty())
		{
			return;
		}

		final int rowFirst = rows.size();
		rows.addAll(rowsToAdd);
		final int rowLast = rows.size() - 1;

		listeners.fireTableRowsInserted(rowFirst, rowLast);

	}

	@Override
	public void setRows(final List<T> rows)
	{
		// Request stop editing and wait for it
		listeners.fireRequestStopEditing();

		removeRows();
		addRows(rows);
	}

	/**
	 * Insert a row of data at the <code>row</code> location in the model. Notification of the row being added will be generated.
	 *
	 * @param row row in the model where the data will be inserted
	 * @param rowData data of the row being added
	 */
	public void insertRow(final int row, final T rowData)
	{
		rows.add(row, rowData);
		listeners.fireTableRowsInserted(row, row);
	}

	/**
	 * Insert multiple rows of data at the <code>row</code> location in the model. Notification of the row being added will be generated.
	 *
	 * @param row row in the model where the data will be inserted
	 * @param rowList each item in the list is a separate row of data
	 */
	public void insertRows(final int row, final List<T> rowList)
	{
		rows.addAll(row, rowList);
		listeners.fireTableRowsInserted(row, row + rowList.size() - 1);
		listeners.fireTableStructureChanged();
	}

	/**
	 * Insert multiple rows of data at the <code>row</code> location in the model. Notification of the row being added will be generated.
	 *
	 * @param row row in the model where the data will be inserted
	 * @param rowArray each item in the Array is a separate row of data
	 */
	public void insertRows(final int row, final T[] rowArray)
	{
		final List<T> rowList = new ArrayList<T>(rowArray.length);

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
	public void moveRow(final int start, final int end, final int to)
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

		final ArrayList<T> temp = new ArrayList<T>(rowsMoved);

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

		listeners.fireTableRowsUpdated(first, last);
	}

	/**
	 * Remove the specified rows from the model. Rows between the starting and ending indexes, inclusively, will be removed. Notification of the rows being removed will be generated.
	 *
	 * @param start starting row index
	 * @param end ending row index
	 * @exception ArrayIndexOutOfBoundsException if any row index is invalid
	 */
	public void removeRowRange(final int start, final int end)
	{
		rows.subList(start, end + 1).clear();
		listeners.fireTableRowsDeleted(start, end);
	}

	/**
	 * Remove the specified rows from the model. The row indexes in the array must be in increasing order. Notification of the rows being removed will be generated.
	 *
	 * @param rows array containing indexes of rows to be removed
	 * @exception ArrayIndexOutOfBoundsException if any row index is invalid
	 */
	public void removeRows(final int... rowIndexes)
	{
		for (int i = rowIndexes.length - 1; i >= 0; i--)
		{
			final int row = rowIndexes[i];
			rows.remove(row);
			listeners.fireTableRowsDeleted(row, row);
		}
	}

	/**
	 * Replace a row of data at the <code>row</code> location in the model. Notification of the row being replaced will be generated.
	 *
	 * @param row row in the model where the data will be replaced
	 * @param rowData data of the row to replace the existing data
	 * @exception IllegalArgumentException when the Class of the row data does not match the row Class of the model.
	 */
	public void replaceRow(final int row, final T rowData)
	{
		rows.set(row, rowData);
		listeners.fireTableRowsUpdated(row, row);
	}

	/**
	 * Sets the Class for the specified column.
	 *
	 * @param column the column whose Class is being changed
	 * @param columnClass the new Class of the column
	 * @exception ArrayIndexOutOfBoundsException if an invalid column was given
	 */
	public void setColumnClass(final String columnName, final Class<?> columnClass)
	{
		final TableColumnInfo columnInfo = getTableColumnInfo(columnName);
		columnInfo.setColumnClass(columnClass);
		listeners.fireTableRowsUpdated(0, getRowCount() - 1);
	}

	/**
	 * Sets the editability for the specified column.
	 *
	 * @param column the column whose Class is being changed
	 * @param editable indicates if the column is editable or not
	 * @exception ArrayIndexOutOfBoundsException if an invalid column was given
	 */
	public void setColumnEditable(final int column, final boolean editable)
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
	@Override
	public void setEditable(final boolean editable)
	{
		this.editable = editable;
	}

	@Override
	public boolean isEditable()
	{
		return this.editable;
	}

	@Override
	public Object getValueAt(final int rowModelIndex, final int columnModelIndex)
	{
		final TableColumnInfo columnInfo = getTableColumnInfo(columnModelIndex);
		final T row = getRow(rowModelIndex);

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
	public void setValueAt(final Object value, final int rowModelIndex, final int columnModelIndex)
	{
		final TableColumnInfo columnInfo = getTableColumnInfo(columnModelIndex);
		final T row = getRow(rowModelIndex);

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
	}

	@Override
	public void setSelectionMode(final SelectionMode selectionMode)
	{
		this.selectionMode = selectionMode;
		// TODO: check & fix current selection when selection mode changes
	}

	@Override
	public SelectionMode getSelectionMode()
	{
		return this.selectionMode;
	}

	@Override
	public void setSelectedRows(final int[] selectedRows)
	{
		final List<T> selectedRowsNew = getRowsAsList(selectedRows);
		setSelectedRows(selectedRowsNew);
	}

	@Override
	public void setSelectedRows(final Collection<T> selectedRows)
	{
		rowsSelected.clear();

		if (selectedRows != null && !selectedRows.isEmpty())
		{
			rowsSelected.addAll(selectedRows);
		}

		listeners.fireSelectionChanged();
	}

	@Override
	public void setSelectedRowsAll()
	{
		final List<T> rowsToSelect = new ArrayList<>(rows);
		setSelectedRows(rowsToSelect);
	}

	@Override
	public void setSelectedRowsNone()
	{
		final List<T> rowsToSelect = Collections.emptyList();
		setSelectedRows(rowsToSelect);
	}

	@Override
	public Set<T> getSelectedRows()
	{
		return rowsSelectedRO;
	}

	@Override
	public boolean isAllRowsSelected()
	{
		// If there are no rows, consider it as nothing selected
		final int rowsCount = getRowCount();
		if (rowsCount == 0)
		{
			return false;
		}

		final int selectedRowsCount = getSelectedRows().size();
		return selectedRowsCount == rowsCount;
	}

	@Override
	public Set<Integer> getSelectedRowIndices()
	{
		final Set<Integer> selectedRowIndices = new HashSet<Integer>();
		for (final T row : getSelectedRows())
		{
			final int rowIndex = getRowIndex(row);
			if (rowIndex < 0)
			{
				continue;
			}
			selectedRowIndices.add(rowIndex);
		}

		return selectedRowIndices;
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		rows.clear();
		rowsSelected.clear();

		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed  ;
	}

	@Override
	public void bindSelectAllCheckbox(final ITerminalCheckboxField selectAllCheckbox)
	{
		new SelectAllRowsCheckboxBinder(this, selectAllCheckbox);
	}
}

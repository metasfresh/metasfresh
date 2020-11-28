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


import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.ITerminalCheckboxField;

public interface ITerminalTableModel<T> extends IDisposable
{
	enum SelectionMode
	{
		NONE,
		SINGLE,
		MULTIPLE,
	}

	void addTerminalTableModelListener(ITerminalTableModelListener listener);

	void removeTerminalTableModelListener(ITerminalTableModelListener listener);

	boolean isEditable();

	void setEditable(boolean editable);

	boolean isCellEditable(int row, int column);

	Class<?> getColumnClass(int column);

	int getColumnCount();

	String getColumnName(int column);

	int getRowCount();

	Object getValueAt(int rowModelIndex, int columnModelIndex);

	void setValueAt(Object value, int rowModelIndex, int columnModelIndex);

	void setColumnNames(String... columnNames);

	void setRows(List<T> rows);

	void setSelectionMode(SelectionMode selectionMode);

	SelectionMode getSelectionMode();

	void setSelectedRows(int[] selectedRows);

	void setSelectedRows(Collection<T> selectedRows);

	void setSelectedRowsAll();

	void setSelectedRowsNone();

	/**
	 *
	 * @return selected rows or empty set; never return null
	 */
	Set<T> getSelectedRows();

	Set<Integer> getSelectedRowIndices();

	/** @return true if all rows are selected; if there are no rows at all, this method will return false */
	boolean isAllRowsSelected();

	ITableColumnInfo getTableColumnInfo(int column);

	ITableColumnInfo getTableColumnInfo(String columnName);

	@Override
	void dispose();

	/**
	 * Binds given checkbox as the "Select All" checkbox of this table.
	 * 
	 * From now on you don't need to take care about updating the status of given checkbox
	 * or about selecting/unselecting the rows when the checkbox is checked or unchecked.
	 * This will be performed automatically.
	 * 
	 * Also, please note that the checkbox will be initialized by checking the status of this table model (i.e. if it has all rows selected or not).
	 * 
	 * @param selectAllCheckbox
	 */
	void bindSelectAllCheckbox(ITerminalCheckboxField selectAllCheckbox);
}

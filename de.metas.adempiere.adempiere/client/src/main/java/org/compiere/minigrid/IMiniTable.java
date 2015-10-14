package org.compiere.minigrid;

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


import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.util.List;

import org.adempiere.ad.ui.ITable;
import org.adempiere.ad.ui.ITableColorProvider;
import org.compiere.model.PO;
import org.compiere.util.DisplayType;

public interface IMiniTable extends ITable
{
	/**
	 * Event fired when table selection changes.
	 * 
	 * NOTE: OldValue=false and NewValue=true, so they are not relevant.
	 */
	String PROPERTY_SelectionChanged = IMiniTable.class.getName() + "#SelectionChanged";

	public void addPropertyChangeListener(PropertyChangeListener listener);

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	public boolean isCellEditable(int rowIndexView, int columnIndexView);

	public boolean isModelCellEditable(int rowIndexModel, int columnIndexModel);

	@Override
	public Object getValueAt(int row, int column);
	
	/**
	 * Same as {@link #getValueAt(int, int)} but it's using model coordinates instead of view coordinates.
	 * 
	 * @param rowIndexModel
	 * @param columnIndexModel
	 * @return value
	 */
	@Override
	public Object getModelValueAt(int rowIndexModel, int columnIndexModel);
	
	public void setValueAt(Object value, int row, int column);
	
	public int convertColumnIndexToModel(int viewColumnIndex);
	
	public void setColumnReadOnly (int index, boolean readOnly);
	
	public String prepareTable(ColumnInfo[] layout, String from, String where, boolean multiSelection, String tableName);

	/**
	 * Similar to {@link #prepareTable(ColumnInfo[], String, String, boolean, String)}, but allows us to omit the attempts to add role access sql. This can be seem as a workaround ofr cases where the
	 * addAccessSql does not work dues to the value the SQL is made up and/or it is not required to add the access sql.
	 * 
	 * @param layout
	 * @param from
	 * @param where
	 * @param multiSelection
	 * @param tableName
	 * @param addAccessSql
	 * @return
	 */
	public String prepareTable(ColumnInfo[] layout, String from, String where, boolean multiSelection, String tableName, boolean addAccessSql);

	public void addColumn (String header);

	/**
	 * Set Column Editor & Renderer to Class (after all columns were added).
	 * 
	 * Layout of IDColumn depends on multiSelection.
	 * 
	 * @param index column index
	 * @param displayType display type (see {@link DisplayType}). If it's -1 then class will be used to determine the default DisplayType
	 * @param c class of column - determines renderer/editors supported: IDColumn, Boolean, Double (Quantity), BigDecimal (Amount), Integer, Timestamp, String (default)
	 * @param readOnly read only flag
	 * @param header optional header value
	 */
	public void setColumnClass(int index, int displayType, Class<?> classType, boolean readOnly, String header);

	/**
	 * Set Column Editor & Renderer to Class (after all columns were added).
	 * 
	 * Layout of IDColumn depends on multiSelection.
	 * 
	 * @param index column index
	 * @param c class of column - determines renderer/editors supported: IDColumn, Boolean, Double (Quantity), BigDecimal (Amount), Integer, Timestamp, String (default)
	 * @param readOnly read only flag
	 * @param header optional header value
	 */
	public void setColumnClass(int index, Class<?> classType, boolean readOnly, String header);
	
	/**
	 * Set Column Editor & Renderer to Class. (after all columns were added).
	 * 
	 * @param index column index
	 * @param c class of column - determines reference
	 * @param readOnly read only flag
	 */
	public void setColumnClass(int index, Class<?> classType, boolean readOnly);
	public void setColumnClass(int index, ColumnInfo columnInfo);
	
	public void loadTable(ResultSet rs);
	
	public void loadTable(PO[] pos);
	
	public Integer getSelectedRowKey();

	/**
	 * Returns the index of the first selected row, -1 if no row is selected.
	 * 
	 * @return the index of the first selected row
	 */
	public int getSelectedRow();

	/**
	 * Returns the indices of all selected rows.
	 * 
	 * @return an array of integers containing the indices of all selected rows, or an empty array if no row is selected
	 * @see #getSelectedRow()
	 */
	public int[] getSelectedRows();

	void setSelectedRows(List<Integer> rowsToSelect);

	public void setRowCount (int rowCount);
	
	public ColumnInfo[] getLayoutInfo();

	public int getColumnCount();
	
	public int getRowCount();
	
	public void setMultiSelection(boolean multiSelection);
	
	public boolean isMultiSelection();
	
	public void setColorCompare (Object dataCompare);
	
	public void repaint();
	
	public void autoSize();
	
	@Override
	public void setColorProvider(final ITableColorProvider colorProvider);
	
	@Override
	public ITableColorProvider getColorProvider();

	public boolean setLoading(boolean loading);

	public void clear();
}

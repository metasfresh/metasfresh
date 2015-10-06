/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.event.TableValueChangeEvent;
import org.adempiere.webui.event.TableValueChangeListener;
import org.compiere.minigrid.IDColumn;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.MSort;
import org.compiere.util.Util;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.ListitemRendererExt;

/**
 * Renderer for {@link org.adempiere.webui.component.ListItems}
 * for the {@link org.adempiere.webui.component.Listbox}.
 *
 * @author Andrew Kimball
 *
 */
public class WListItemRenderer implements ListitemRenderer, EventListener, ListitemRendererExt
{
	/** Array of listeners for changes in the table components. */
	protected ArrayList<TableValueChangeListener> m_listeners =
            new ArrayList<TableValueChangeListener>();

	/** A list containing the indices of the currently selected ListItems. */
	private Set<ListItem> m_selectedItems = new HashSet<ListItem>();
	/**	Array of table details. */
	private ArrayList<WTableColumn> m_tableColumns = new ArrayList<WTableColumn>();
	/** Array of {@link ListHeader}s for the list head. */
    private ArrayList<ListHeader> m_headers = new ArrayList<ListHeader>();

    private Listbox listBox;

	private EventListener cellListener;

	/**
	 * Default constructor.
	 *
	 */
	public WListItemRenderer()
	{
		super();
	}

	/**
	 * Constructor specifying the column headers.
	 *
	 * @param columnNames	vector of column titles.
	 */
	public WListItemRenderer(List< ? extends String> columnNames)
	{
		super();
		WTableColumn tableColumn;

		for (String columnName : columnNames)
		{
			tableColumn = new WTableColumn();
			tableColumn.setHeaderValue(Util.cleanAmp(columnName));
			m_tableColumns.add(tableColumn);
		}
	}

	/**
	 * Get the column details of the specified <code>column</code>.
	 *
	 * @param columnIndex	The index of the column for which details are to be retrieved.
	 * @return	The details of the column at the specified index.
	 */
	private WTableColumn getColumn(int columnIndex)
	{
		try
		{
			return m_tableColumns.get(columnIndex);
		}
		catch (IndexOutOfBoundsException exception)
		{
			throw new IllegalArgumentException("There is no WTableColumn at column "
                    + columnIndex);
		}
	}


	/* (non-Javadoc)
	 * @see org.zkoss.zul.ListitemRenderer#render(org.zkoss.zul.Listitem, java.lang.Object)
	 */
	public void render(Listitem item, Object data) throws Exception
	{
		render((ListItem)item, data);
	}

	/**
	 * Renders the <code>data</code> to the specified <code>Listitem</code>.
	 *
	 * @param item 	the listitem to render the result.
	 * 				Note: when this method is called, the listitem has no child
	 * 				at all.
	 * @param data 	that is returned from {@link ListModel#getElementAt}
	 * @throws Exception
	 * @see {@link #render(Listitem, Object)}
	 */
	private void render(ListItem item, Object data)
	{
		Listcell listcell = null;
		int colIndex = 0;
		int rowIndex = item.getIndex();
		WListbox table = null;

		if (item.getListbox() instanceof WListbox)
		{
			table = (WListbox)item.getListbox();
		}

		if (!(data instanceof List))
		{
			throw new IllegalArgumentException("A model element was not a list");
		}

		if (listBox == null || listBox != item.getListbox())
		{
			listBox = item.getListbox();
		}
		if (cellListener == null)
		{
			cellListener = new CellListener();
		}

		for (Object field : (List<?>)data)
		{
			listcell = getCellComponent(table, field, rowIndex, colIndex);
			listcell.setParent(item);
			listcell.addEventListener(Events.ON_DOUBLE_CLICK, cellListener);
			colIndex++;
		}

		return;
	}

	/**
	 * Generate the cell for the given <code>field</code>.
	 *
	 * @param table 	The table into which the cell will be placed.
	 * @param field		The data field for which the cell is to be created.
	 * @param rowIndex	The row in which the cell is to be placed.
	 * @param columnIndex	The column in which the cell is to be placed.
	 * @return	The list cell component.
	 */
	private Listcell getCellComponent(WListbox table, Object field,
									  int rowIndex, int columnIndex)
	{
		ListCell listcell = new ListCell();
		boolean isCellEditable = table != null ? table.isCellEditable(rowIndex, columnIndex) : false;

        // TODO put this in factory method for generating cell renderers, which
        // are assigned to Table Columns
		if (field != null)
		{
			if (field instanceof Boolean)
			{
				listcell.setValue(Boolean.valueOf(field.toString()));

				if (table != null && columnIndex == 0)
					table.setCheckmark(false);
				Checkbox checkbox = new Checkbox();
				checkbox.setChecked(Boolean.valueOf(field.toString()));

				if (isCellEditable)
				{
					checkbox.setEnabled(true);
					checkbox.addEventListener(Events.ON_CHECK, this);
				}
				else
				{
					checkbox.setEnabled(false);
				}

				listcell.appendChild(checkbox);
				ZkCssHelper.appendStyle(listcell, "text-align:center");
			}
			else if (field instanceof Number)
			{
				DecimalFormat format = field instanceof BigDecimal
					? DisplayType.getNumberFormat(DisplayType.Amount, AEnv.getLanguage(Env.getCtx()))
				    : DisplayType.getNumberFormat(DisplayType.Integer, AEnv.getLanguage(Env.getCtx()));

				// set cell value to allow sorting
				listcell.setValue(field.toString());

				if (isCellEditable)
				{
					NumberBox numberbox = new NumberBox(false);
					numberbox.setFormat(format);
					numberbox.setValue(field);
					numberbox.setWidth("100px");
					numberbox.setEnabled(true);
					numberbox.setStyle("text-align:right; "
									+ listcell.getStyle());
					numberbox.addEventListener(Events.ON_CHANGE, this);
					listcell.appendChild(numberbox);
				}
				else
				{
					listcell.setLabel(format.format(((Number)field).doubleValue()));
					ZkCssHelper.appendStyle(listcell, "text-align:right");
				}
			}
			else if (field instanceof Timestamp)
			{

				SimpleDateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date, AEnv.getLanguage(Env.getCtx()));
				listcell.setValue(dateFormat.format((Timestamp)field));
				if (isCellEditable)
				{
					Datebox datebox = new Datebox();
					datebox.setFormat(dateFormat.toPattern());
					datebox.setValue(new Date(((Timestamp)field).getTime()));
					datebox.addEventListener(Events.ON_CHANGE, this);
					listcell.appendChild(datebox);
				}
				else
				{
					listcell.setLabel(dateFormat.format((Timestamp)field));
				}
			}
			else if (field instanceof String)
			{
				listcell.setValue(field.toString());
				if (isCellEditable)
				{
					Textbox textbox = new Textbox();
					textbox.setValue(field.toString());
					textbox.addEventListener(Events.ON_CHANGE, this);
					listcell.appendChild(textbox);
				}
				else
				{
					listcell.setLabel(field.toString());
				}
			}
			// if ID column make it invisible
			else if (field instanceof IDColumn)
			{
				listcell.setValue(((IDColumn) field).getRecord_ID());
				if (!table.isCheckmark()) {
					table.setCheckmark(true);
					table.removeEventListener(Events.ON_SELECT, this);
					table.addEventListener(Events.ON_SELECT, this);
				}
			}
			else
			{
				listcell.setLabel(field.toString());
				listcell.setValue(field.toString());
			}
		}
		else
		{
			listcell.setLabel("");
			listcell.setValue("");
		}

		return listcell;
	}


	/**
	 *  Update Table Column.
	 *
	 *  @param index	The index of the column to update
	 *  @param header 	The header text for the column
	 */
	public void updateColumn(int index, String header)
	{
		WTableColumn tableColumn;

		tableColumn = getColumn(index);
		tableColumn.setHeaderValue(Util.cleanAmp(header));

		return;
	}   //  updateColumn

	/**
	 *  Add Table Column.
	 *  after adding a column, you need to set the column classes again
	 *  (DefaultTableModel fires TableStructureChanged, which calls
	 *  JTable.tableChanged .. createDefaultColumnsFromModel
	 *  @param header The header text for the column
	 */
	public void addColumn(String header)
	{
		WTableColumn tableColumn;

		tableColumn = new WTableColumn();
		tableColumn.setHeaderValue(Util.cleanAmp(header));
		m_tableColumns.add(tableColumn);

		return;
	}   //  addColumn

	/**
	 * Get the number of columns.
	 * @return the number of columns
	 */
	public int getNoColumns()
	{
		return m_tableColumns.size();
	}

	/**
	 * This is unused.
	 * The readonly proprty of a column should be set in
	 * the parent table.
	 *
	 * @param colIndex
	 * @param readOnly
	 * @deprecated
	 */
	public void setRO(int colIndex, Boolean readOnly)
	{
		return;
	}

	/**
	 * Create a ListHeader using the given <code>headerValue</code> to
	 * generate the header text.
	 * The <code>toString</code> method of the <code>headerValue</code>
	 * is used to set the header text.
	 *
	 * @param headerValue	The object to use for generating the header text.
     * @param headerIndex   The column index of the header
	 * @param classType
	 * @return The generated ListHeader
	 * @see #renderListHead(ListHead)
	 */
	private Component getListHeaderComponent(Object headerValue, int headerIndex, Class<?> classType)
	{
        ListHeader header = null;

        String headerText = headerValue.toString();
        if (m_headers.size() <= headerIndex || m_headers.get(headerIndex) == null)
        {
        	if (classType != null && classType.isAssignableFrom(IDColumn.class))
        	{
        		header = new ListHeader("");
        		header.setWidth("20px");
        	}
        	else
        	{
	            Comparator<Object> ascComparator =  getColumnComparator(true, headerIndex);
	            Comparator<Object> dscComparator =  getColumnComparator(false, headerIndex);
	
	            header = new ListHeader(headerText);
	
	            header.setSort("auto");
	            header.setSortAscending(ascComparator);
	            header.setSortDescending(dscComparator);
	
	            int width = headerText.trim().length() * 9;
	            if (width > 300)
	            	width = 300;
	            else if (classType != null)
	            {
	            	if (classType.equals(String.class))
	            	{
	            		if (width > 0 && width < 180)
	            			width = 180;
	            	}
	            	else if (classType.equals(IDColumn.class))
	            	{
	            		header.setSort("none");
	            		if (width == 0)
	            			width = 30;
	            	}
		            else if (width > 0 && width < 100 && (classType == null || !classType.isAssignableFrom(Boolean.class)))
	            		width = 100;
	            }
	            else if (width > 0 && width < 100)
	            	width = 100;
	
	            header.setWidth(width + "px");
        	}
            m_headers.add(header);
        }
        else
        {
            header = m_headers.get(headerIndex);

            if (!header.getLabel().equals(headerText))
            {
                header.setLabel(headerText);
            }
        }

		return header;
	}

	/**
	 * set custom list header
	 * @param index
	 * @param header
	 */
	public void setListHeader(int index, ListHeader header) {
		int size = m_headers.size();
		if (size <= index) {
			while (size <= index) {
				if (size == index)
					m_headers.add(header);
				else
					m_headers.add(null);
				size++;
			}

		} else
			m_headers.set(index, header);
	}

    /**
     * Obtain the comparator for a given column.
     *
     * @param ascending     whether the comparator will sort ascending
     * @param columnIndex   the index of the column for which the comparator is required
     * @return  comparator for the given column for the given direction
     */
    protected Comparator<Object> getColumnComparator(boolean ascending, final int columnIndex)
    {
    	return new ColumnComparator(ascending, columnIndex);
    }

    public static class ColumnComparator implements Comparator<Object>
    {

    	private int columnIndex;
		private MSort sort;

		public ColumnComparator(boolean ascending, int columnIndex)
    	{
    		this.columnIndex = columnIndex;
    		sort = new MSort(0, null);
        	sort.setSortAsc(ascending);
    	}

        public int compare(Object o1, Object o2)
        {
                Object item1 = ((List<?>)o1).get(columnIndex);
                Object item2 = ((List<?>)o2).get(columnIndex);
                return sort.compare(item1, item2);
        }

		public int getColumnIndex()
		{
			return columnIndex;
		}
    }

	/**
	 * Render the ListHead for the table with headers for the table columns.
	 *
	 * @param head	The ListHead component to render.
	 * @see #addColumn(String)
	 * @see #WListItemRenderer(List)
	 */
	public void renderListHead(ListHead head)
	{
		Component header;
        WTableColumn column;

		for (int columnIndex = 0; columnIndex < m_tableColumns.size(); columnIndex++)
        {
            column = m_tableColumns.get(columnIndex);
			header = getListHeaderComponent(column.getHeaderValue(), columnIndex, column.getColumnClass());
            head.appendChild(header);
		}
		head.setSizable(true);

		return;
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
	 */
	public void onEvent(Event event) throws Exception
	{
		int col = -1;
		int row = -1;
		Object value = null;
		TableValueChangeEvent vcEvent = null;
		WTableColumn tableColumn;

		Component source = event.getTarget();

		if (isWithinListCell(source))
		{
			row = getRowPosition(source);
			col = getColumnPosition(source);

			tableColumn = m_tableColumns.get(col);

			if (source instanceof Checkbox)
			{
				value = Boolean.valueOf(((Checkbox)source).isChecked());
			}
			else if (source instanceof Decimalbox)
			{
				value = ((Decimalbox)source).getValue();
			}
			else if (source instanceof Datebox)
			{
				value = ((Datebox)source).getValue();
			}
			else if (source instanceof Textbox)
			{
				value = ((Textbox)source).getValue();
			}

			if(value != null)
			{
				vcEvent = new TableValueChangeEvent(source,
						tableColumn.getHeaderValue().toString(),
						row, col,
						value, value);

				fireTableValueChange(vcEvent);
			}
		}
		else if (event.getTarget() instanceof WListbox && Events.ON_SELECT.equals(event.getName()))
		{
			WListbox table = (WListbox) event.getTarget();
			if (table.isCheckmark()) {
				int cnt = table.getRowCount();
				if (cnt == 0 || !(table.getValueAt(0, 0) instanceof IDColumn))
					return;

				//update IDColumn
				tableColumn = m_tableColumns.get(0);
				for (int i = 0; i < cnt; i++) {
					IDColumn idcolumn = (IDColumn) table.getValueAt(i, 0);
					Listitem item = table.getItemAtIndex(i);

					value = item.isSelected();
					Boolean old = idcolumn.isSelected();

					if (!old.equals(value)) {
						vcEvent = new TableValueChangeEvent(source,
								tableColumn.getHeaderValue().toString(),
								i, 0,
								old, value);

						fireTableValueChange(vcEvent);
					}
				}
			}
		}

		return;
	}

	private boolean isWithinListCell(Component source) {
		if (source instanceof Listcell)
			return true;
		Component c = source.getParent();
		while(c != null) {
			if (c instanceof Listcell)
				return true;
			c = c.getParent();
		}
		return false;
	}

	/**
	 * Get the row index of the given <code>source</code> component.
	 *
	 * @param source	The component for which the row index is to be found.
	 * @return The row index of the given component.
	 */
	protected int getRowPosition(Component source)
	{
		Listcell cell;
		ListItem item;
		int row = -1;

		cell = findListcell(source);
		item = (ListItem)cell.getParent();

		row = item.getIndex();

		return row;
	}

	private Listcell findListcell(Component source) {
		if (source instanceof Listcell)
			return (Listcell) source;
		Component c = source.getParent();
		while(c != null) {
			if (c instanceof Listcell)
				return (Listcell) c;
			c = c.getParent();
		}
		return null;
	}

	/**
	 * Get the column index of the given <code>source</code> component.
	 *
	 * @param source	The component for which the column index is to be found.
	 * @return The column index of the given component.
	 */
	protected int getColumnPosition(Component source)
	{
		Listcell cell;
		int col = -1;

		cell = findListcell(source);
		col = cell.getColumnIndex();

		return col;
	}


	/**
	 * Reset the renderer.
	 * This should be called if the table using this renderer is cleared.
	 */
	public void clearColumns()
	{
		m_tableColumns.clear();
	}

	/**
	 * Clear the renderer.
	 * This should be called if the table using this renderer is cleared.
	 */
	public void clearSelection()
	{
		m_selectedItems.clear();
	}

	/**
	 * Add a listener for changes in the table's component values.
	 *
	 * @param listener	The listener to add.
	 */
	public void addTableValueChangeListener(TableValueChangeListener listener)
	{
	    if (listener == null)
	    {
	    	return;
	    }

	    m_listeners.add(listener);
	}

	public void removeTableValueChangeListener(TableValueChangeListener listener)
	{
		if (listener == null)
	    {
	    	return;
		}

	    m_listeners.remove(listener);
	}

	/**
	 * Fire the given table value change <code>event</code>.
	 *
	 * @param event	The event to pass to the listeners
	 */
	private void fireTableValueChange(TableValueChangeEvent event)
	{
	    for (TableValueChangeListener listener : m_listeners)
	    {
	       listener.tableValueChange(event);
	    }
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zul.ListitemRendererExt#getControls()
	 */
	public int getControls()
	{
		return DETACH_ON_RENDER;
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zul.ListitemRendererExt#newListcell(org.zkoss.zul.Listitem)
	 */
	public Listcell newListcell(Listitem item)
	{
		ListCell cell = new ListCell();
		cell.applyProperties();
		return cell;
	}

	/* (non-Javadoc)
	 * @see org.zkoss.zul.ListitemRendererExt#newListitem(org.zkoss.zul.Listbox)
	 */
	public Listitem newListitem(Listbox listbox)
	{
		ListItem item = new ListItem();
		item.applyProperties();

		return item;
	}

	/**
	 * @param index
	 * @param header
	 */
	public void setColumnHeader(int index, String header)
	{
		if (index >= 0 && index < m_tableColumns.size())
		{
			m_tableColumns.get(index).setHeaderValue(Util.cleanAmp(header));
		}

	}

	public void setColumnClass(int index, Class<?> classType) {
		if (index >= 0 && index < m_tableColumns.size())
		{
			m_tableColumns.get(index).setColumnClass(classType);
		}
	}

	class CellListener implements EventListener {

		public CellListener() {
		}

		public void onEvent(Event event) throws Exception {
			if (listBox != null && Events.ON_DOUBLE_CLICK.equals(event.getName())) {
				Event evt = new Event(Events.ON_DOUBLE_CLICK, listBox);
				Events.sendEvent(listBox, evt);
			}
		}

	}
}



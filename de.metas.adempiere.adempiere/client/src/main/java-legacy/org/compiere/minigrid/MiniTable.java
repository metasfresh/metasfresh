/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.minigrid;

import java.awt.Component;
import java.awt.Insets;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.adempiere.ad.ui.DefaultTableColorProvider;
import org.adempiere.ad.ui.ITableColorProvider;
import org.compiere.apps.search.Info_Column;
import org.compiere.grid.ed.VCellRenderer;
import org.compiere.grid.ed.VHeaderRenderer;
import org.compiere.model.PO;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CTable;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.collect.Ordering;

import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.util.Check;

/**
 * Mini Table. Default Read Only Table for Boolean, String, Number, Timestamp values
 * <p>
 * After initializing the Table Model, you need to call setColumnClass, add columns via addColumn or in one go prepare the table. <code>
 *  MiniTable mt = new MiniTable();
 *  String sql = mt.prepareTable(..);   //  table defined
 *  //  add where to the sql statement
 *  ResultSet rs = ..
 *  mt.loadTable(rs);
 *  rs.close();
 *  </code>
 * 
 * @author Jorg Janke
 * @version $Id: MiniTable.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1891082 ] NPE on MiniTable when you hide some columns <li>FR [ 1974299 ] Add MiniTable.getSelectedKeys method <li>FR [ 2847295 ] MiniTable
 *         multiselection checkboxes not working https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2847295&group_id=176962
 * @author Teo Sarca, teo.sarca@gmail.com <li>BF [ 2876895 ] MiniTable.loadTable: NPE if column is null https://sourceforge.net/tracker/?func=detail&aid=2876895&group_id=176962&atid=879332
 */
public class MiniTable extends CTable implements IMiniTable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2853772547464132497L;

	/**
	 * Default Constructor
	 */
	public MiniTable()
	{
		super(new MiniTableModel());
		// log.info( "MiniTable");
		setCellSelectionEnabled(false);
		setRowSelectionAllowed(false);
		// Default Editor
		this.setCellEditor(new ROCellEditor());

		final MiniTableModel tableModel = (MiniTableModel)getModel();

		//
		// Row Selection Changed Listener
		tableModel.addTableModelListener(new TableModelListener()
		{

			@Override
			public void tableChanged(final TableModelEvent e)
			{
				//
				// If table is loading mode, don't send any SelectionChanged events because those will be totally inaccurate
				// and will do more harm than good
				if (isLoading())
				{
					return;
				}

				if (TableModelEvent.INSERT == e.getType())
				{
					firePropertyChange(PROPERTY_SelectionChanged, false, true);
				}
				else if (TableModelEvent.UPDATE == e.getType())
				{
					firePropertyChange(PROPERTY_SelectionChanged, false, true);
				}
				else if (TableModelEvent.DELETE == e.getType())
				{
					firePropertyChange(PROPERTY_SelectionChanged, false, true);
				}
			}
		});

		//
		// Single selection listener
		// FIXME: this code part needs to be fixed after integration of multiselection
		getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{

			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				// skip until we have final changes
				if (e.getValueIsAdjusting())
				{
					return;
				}

				if (isLoading())
				{
					return;
				}

				firePropertyChange(PROPERTY_SelectionChanged, false, true);
			}
		});
	}   // MiniTable

	private boolean editable = true;

	/** List of R/W columns */
	private ArrayList<Integer> m_readWriteColumn = new ArrayList<Integer>();
	/** List of Column Width */
	private ArrayList<Integer> m_minWidth = new ArrayList<Integer>();

	/** Multi Selection mode (default false) */
	private boolean m_multiSelection = false;

	/** Lauout set in prepareTable and used in loadTable */
	private ColumnInfo[] m_layout = null;
	/** Logger */
	private static Logger log = LogManager.getLogger(MiniTable.class);
	/** Is Total Show */
	private boolean showTotals = false;
	private boolean autoResize = true;

	public boolean isEditable()
	{
		return editable;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public boolean isAutoResize()
	{
		return autoResize;
	}

	public void setAutoResize(boolean autoResize)
	{
		this.autoResize = autoResize;
	}

	/**
	 * Gets the swing column of given index. No index checking is done.
	 * 
	 * @param col
	 * @return
	 */
	public TableColumn getColumn(int col)
	{
		return (getColumnModel().getColumn(col));
	}

	/**
	 * Return number of columns in the mini table
	 */
	@Override
	public int getColumnCount()
	{
		return (getColumnModel().getColumnCount());
	}

	/**
	 * Size Columns. Uses Mimimum Column Size
	 */
	@Override
	public void autoSize()
	{
		if (!autoResize)
			return;

		long start = System.currentTimeMillis();
		//
		final int SLACK = 8;		// making sure it fits in a column
		final int MAXSIZE = 300;    // max size of a column
		//
		TableModel model = this.getModel();
		int size = model.getColumnCount();
		// for all columns
		for (int col = 0; col < size; col++)
		{
			// Column & minimum width
			TableColumn tc = this.getColumnModel().getColumn(col);
			int width = 0;
			if (m_minWidth.size() > col)
				width = m_minWidth.get(col).intValue();
			// log.info( "Column=" + col + " " + column.getHeaderValue());

			// Header
			TableCellRenderer renderer = tc.getHeaderRenderer();
			if (renderer == null)
				renderer = new DefaultTableCellRenderer();
			Component comp = renderer.getTableCellRendererComponent
					(this, tc.getHeaderValue(), false, false, 0, 0);
			// log.debug( "Hdr - preferred=" + comp.getPreferredSize().width + ", width=" + comp.getWidth());
			width = Math.max(width, comp.getPreferredSize().width + SLACK);

			// Cells
			int maxRow = Math.min(30, getRowCount());       // first 30 rows
			for (int row = 0; row < maxRow; row++)
			{
				renderer = getCellRenderer(row, col);
				comp = renderer.getTableCellRendererComponent
						(this, getValueAt(row, col), false, false, row, col);
				if (comp != null)
				{
					int rowWidth = comp.getPreferredSize().width + SLACK;
					width = Math.max(width, rowWidth);
				}
			}
			// Width not greater ..
			width = Math.min(MAXSIZE, width);
			tc.setPreferredWidth(width);
			// log.debug( "width=" + width);
		}	// for all columns
		log.trace("Cols=" + size + " - " + (System.currentTimeMillis() - start) + "ms");
	}	// autoSize

	/**
	 * Is Cell Editable
	 * 
	 * @param row row
	 * @param column column
	 * @return true if editable
	 */
	@Override
	public boolean isCellEditable(int row, int column)
	{
		if (!isEditable())
		{
			return false;
		}

		// if the first column is a boolean and it is false, it is not editable
		if (column != 0
				&& getValueAt(row, 0) instanceof Boolean
				&& !((Boolean)getValueAt(row, 0)).booleanValue())
			return false;

		// is the column RW?
		if (m_readWriteColumn.contains(new Integer(column)))
			return true;
		return false;
	}   // isCellEditable

	@Override
	public boolean isModelCellEditable(int rowIndexModel, int columnIndexModel)
	{
		final int rowIndexView = convertRowIndexToView(rowIndexModel);
		final int columnIndexView = convertColumnIndexToView(columnIndexModel);
		return isCellEditable(rowIndexView, columnIndexView);
	}

	/**
	 * Set Column to ReadOnly
	 * 
	 * @param column column
	 * @param readOnly read only
	 */
	@Override
	public void setColumnReadOnly(int column, boolean readOnly)
	{
		// Column is ReadWrite
		if (m_readWriteColumn.contains(new Integer(column)))
		{
			// Remove from list
			if (readOnly)
			{
				int size = m_readWriteColumn.size();
				for (int i = 0; i < size; i++)
				{
					if (m_readWriteColumn.get(i).intValue() == column)
					{
						m_readWriteColumn.remove(i);
						break;
					}
				}
			}   // ReadOnly
		}
		// current column is R/O - ReadWrite - add to list
		else if (!readOnly)
			m_readWriteColumn.add(new Integer(column));
	}   // setColumnReadOnly

	@Override
	public String prepareTable(final ColumnInfo[] layout,
			final String from, 
			final String where, 
			final boolean multiSelection, 
			final String tableName)
	{
		final boolean addAccessSQL = true; // the default/ old behavior.
		return prepareTable(layout, from, where, multiSelection, tableName, addAccessSQL);
	}
	
	/**************************************************************************
	 * Prepare Table and return SQL
	 *
	 * @param layout array of column info
	 * @param from SQL FROM content
	 * @param where SQL WHERE content
	 * @param multiSelection multiple selections
	 * @param tableName table name
	 * @return SQL
	 */
	@Override
	public String prepareTable(final ColumnInfo[] layout,
			final String from, 
			final String where, 
			final boolean multiSelection, 
			final String tableName,
			final boolean addAccessSQL)
	{
		m_layout = layout;
		m_multiSelection = multiSelection;
		//
		StringBuilder sql = new StringBuilder("SELECT ");
		// add columns & sql
		for (int i = 0; i < layout.length; i++)
		{
			// create sql
			if (i > 0)
				sql.append(", ");
			sql.append(layout[i].getColSQL());
			// adding ID column
			if (layout[i].isKeyPairCol())
				sql.append(",").append(layout[i].getKeyPairColSQL());

			// add to model
			addColumn(layout[i].getColHeader());
			if (layout[i].isColorColumn())
				setColorColumn(i);
			if (layout[i].getColClass() == IDColumn.class)
				p_keyColumnIndex = i;
		}
		// set editors (two steps)
		for (int i = 0; i < layout.length; i++)
		{
			setColumnClass(i, layout[i]);
		}

		sql.append(" FROM ").append(from);
		sql.append(" WHERE ").append(where);

		// Table Selection
		setRowSelectionAllowed(true);

		// org.compiere.apps.form.VMatch.dynInit calls routine for initial init only
		if (from.length() == 0)
			return sql.toString();
		//
		final String finalSQL;
		if (addAccessSQL)
		{
			finalSQL = Env.getUserRolePermissions().addAccessSQL(
					sql.toString(),
					tableName, IUserRolePermissions.SQL_FULLYQUALIFIED,
					Access.READ);
		}
		else
		{
			finalSQL = sql.toString();
		}
		log.trace(finalSQL);
		return finalSQL;
	}   // prepareTable

	/**
	 * Add Table Column. after adding a column, you need to set the column classes again (DefaultTableModel fires TableStructureChanged, which calls JTable.tableChanged ..
	 * createDefaultColumnsFromModel
	 * 
	 * @param header header
	 */
	@Override
	public void addColumn(String header)
	{
		if (getModel() instanceof DefaultTableModel)
		{
			DefaultTableModel model = (DefaultTableModel)getModel();
			model.addColumn(Util.cleanAmp(header));
		}
		else
			throw new IllegalArgumentException("Model must be instance of DefaultTableModel");
	}   // addColumn

	@Override
	public void setColumnClass(int index, Class<?> c, boolean readOnly)
	{
		setColumnClass(index, c, readOnly, null);
	}   // setColumnClass

	@Override
	public void setColumnClass(final int index, final Class<?> c, final boolean readOnly, final String header)
	{
		final int displayType = -1;
		setColumnClass(index, displayType, c, readOnly, header);
	}

	@Override
	public void setColumnClass(final int index, final int displayType, final Class<?> c, final boolean readOnly, final String header)
	{
		final String colSQL = null; // not relevant
		final ColumnInfo columnInfo = new ColumnInfo(header, colSQL, c);
		columnInfo.setReadOnly(readOnly);
		columnInfo.setDisplayType(displayType);
		setColumnClass(index, columnInfo);
	}

	@Override
	public void setColumnClass(final int index, final ColumnInfo columnInfo)
	{
		if (columnInfo == null)
		{
			return;
		}

		// log.info( "MiniTable.setColumnClass - " + index, c.getName() + ", r/o=" + readOnly);
		final TableColumn tc = getColumnModel().getColumn(index);
		if (tc == null)
		{
			return;
		}

		final boolean readOnly = columnInfo.isReadOnly();
		final String header = columnInfo.getColHeader();
		final int displayType = columnInfo.getDisplayType();
		final Class<?> c = columnInfo.getColClass();
		final String columnName = columnInfo.getColumnName();

		//
		// Set ColumnName to Index binding
		if (!Check.isEmpty(columnName, true))
		{
			columnName2modelIndex.put(columnName, index);
		}

		// Set R/O
		setColumnReadOnly(index, readOnly);

		// Header
		if (header != null && header.length() > 0)
		{
			tc.setHeaderValue(Util.cleanAmp(header));
		}

		// ID Column & Selection
		if (c == IDColumn.class)
		{
			final int displayTypeToUse = displayType > 0 ? displayType : DisplayType.ID;

			tc.setCellRenderer(new IDColumnRenderer(m_multiSelection));
			if (m_multiSelection)
			{
				tc.setCellEditor(new IDColumnEditor());
				setColumnReadOnly(index, false);
			}
			else
			{
				tc.setCellEditor(new ROCellEditor());
			}
			m_minWidth.add(new Integer(10));
			tc.setMaxWidth(20);
			tc.setPreferredWidth(20);
			tc.setResizable(false);

			tc.setHeaderRenderer(new VHeaderRenderer(displayTypeToUse));
		}
		// Boolean
		else if (c == Boolean.class)
		{
			tc.setCellRenderer(new CheckRenderer());
			if (readOnly)
				tc.setCellEditor(new ROCellEditor());
			else
			{
				CCheckBox check = new CCheckBox();
				check.setMargin(new Insets(0, 0, 0, 0));
				check.setHorizontalAlignment(SwingConstants.CENTER);
				tc.setCellEditor(new DefaultCellEditor(check));
			}
			m_minWidth.add(new Integer(30));

			Check.assume(displayType <= 0 || displayType == DisplayType.YesNo,
					"Invalid DisplayType={}. Only YesNo is allowed", displayType);
			tc.setHeaderRenderer(new VHeaderRenderer(DisplayType.YesNo));
		}
		// Date
		else if (c == Timestamp.class)
		{
			Check.assume(displayType <= 0 || DisplayType.isDate(displayType),
					"Invalid DisplayType={}. Date/Time type allowed", displayType);
			final int displayTypeToUse = displayType > 0 ? displayType : DisplayType.Date;

			tc.setCellRenderer(new VCellRenderer(displayTypeToUse));
			if (readOnly)
				tc.setCellEditor(new ROCellEditor());
			else
				tc.setCellEditor(new MiniCellEditor(columnInfo, displayTypeToUse));
			m_minWidth.add(new Integer(30));

			tc.setHeaderRenderer(new VHeaderRenderer(displayTypeToUse));
			setSortIndexComparator(index, Ordering.<Timestamp>natural().nullsLast());
		}
		// Amount
		else if (c == BigDecimal.class)
		{
			Check.assume(displayType <= 0 || DisplayType.isNumeric(displayType),
					"Invalid DisplayType={}. Numeric type allowed", displayType);
			final int displayTypeToUse = displayType > 0 ? displayType : DisplayType.Amount;

			final VCellRenderer cellRenderer = new VCellRenderer(displayTypeToUse);

			// Set custom number precision if any
			final int precision = columnInfo.getPrecision();
			if (precision >= 0)
			{
				cellRenderer.setPrecision(precision);
			}

			tc.setCellRenderer(cellRenderer);

			if (readOnly)
			{
				tc.setCellEditor(new ROCellEditor(SwingConstants.RIGHT));
				m_minWidth.add(columnInfo.getWidthMin(70));
			}
			else
			{
				tc.setCellEditor(new MiniCellEditor(columnInfo, displayTypeToUse));
				m_minWidth.add(columnInfo.getWidthMin(80));
			}

			tc.setHeaderRenderer(new VHeaderRenderer(displayTypeToUse));
			setSortIndexComparator(index, Ordering.<BigDecimal>natural().nullsLast());
		}
		// Number
		else if (c == Double.class)
		{
			Check.assume(displayType <= 0 || DisplayType.isNumeric(displayType),
					"Invalid DisplayType={}. Numeric type allowed", displayType);
			final int displayTypeToUse = displayType > 0 ? displayType : DisplayType.Number;

			tc.setCellRenderer(new VCellRenderer(displayTypeToUse));
			if (readOnly)
			{
				tc.setCellEditor(new ROCellEditor(SwingConstants.RIGHT));
				m_minWidth.add(new Integer(70));
			}
			else
			{
				tc.setCellEditor(new MiniCellEditor(columnInfo, displayTypeToUse));
				m_minWidth.add(new Integer(80));
			}

			tc.setHeaderRenderer(new VHeaderRenderer(displayTypeToUse));
			setSortIndexComparator(index, Ordering.<Double>natural().nullsLast());
		}
		// Integer
		else if (c == Integer.class)
		{
			Check.assume(displayType <= 0 || DisplayType.isNumeric(displayType) || DisplayType.isID(displayType),
					"Invalid DisplayType={}. Numeric type allowed", displayType);
			final int displayTypeToUse = displayType > 0 ? displayType : DisplayType.Integer;

			tc.setCellRenderer(new VCellRenderer(displayTypeToUse));
			if (readOnly)
				tc.setCellEditor(new ROCellEditor());
			else
				tc.setCellEditor(new MiniCellEditor(columnInfo, displayTypeToUse));
			m_minWidth.add(new Integer(30));

			tc.setHeaderRenderer(new VHeaderRenderer(displayTypeToUse));
			setSortIndexComparator(index, Ordering.<Integer>natural().nullsLast());
		}
		else if (c == KeyNamePair.class || c == ValueNamePair.class)
		{
			final int displayTypeToUse = displayType > 0 ? displayType : DisplayType.String;

			tc.setCellRenderer(new VCellRenderer(displayTypeToUse));
			if (readOnly)
				tc.setCellEditor(new ROCellEditor());
			else
				tc.setCellEditor(new MiniCellEditor(columnInfo, displayTypeToUse));
			m_minWidth.add(new Integer(30));

			tc.setHeaderRenderer(new VHeaderRenderer(displayTypeToUse));
		}
		// String
		else
		{
			// NOTE: don't validate because here we can get TableDir but it will be displayed as String
			// Check.assume(displayType <= 0 || DisplayType.isText(displayType),
			// "Invalid DisplayType={}. Text type allowed", displayType);
			final int displayTypeToUse = displayType > 0 && DisplayType.isText(displayType) ? displayType : DisplayType.String;

			tc.setCellRenderer(new VCellRenderer(displayTypeToUse));
			if (readOnly)
				tc.setCellEditor(new ROCellEditor());
			else
				tc.setCellEditor(new MiniCellEditor(columnInfo, displayTypeToUse, String.class));
			m_minWidth.add(new Integer(30));

			tc.setHeaderRenderer(new VHeaderRenderer(displayTypeToUse));
		}
		// log.debug( "Renderer=" + tc.getCellRenderer().toString() + ", Editor=" + tc.getCellEditor().toString());
	}   // setColumnClass

	/**
	 * Clear Table Content
	 * 
	 * @param no number of rows
	 */
	@Override
	public void setRowCount(int no)
	{
		if (getModel() instanceof DefaultTableModel)
		{
			DefaultTableModel model = (DefaultTableModel)getModel();
			model.setRowCount(no);
			// log.info( "MiniTable.setRowCount", "rows=" + getRowCount() + ", cols=" + getColumnCount());
		}
		else
			throw new IllegalArgumentException("Model must be instance of DefaultTableModel");
	}   // setRowCount

	/**************************************************************************
	 * Load Table from ResultSet - The ResultSet is not closed
	 *
	 * @param rs ResultSet with the column layout defined in prepareTable
	 */
	@Override
	public final void loadTable(final ResultSet rs)
	{
		if (m_layout == null)
			throw new UnsupportedOperationException("Layout not defined");

		// Clear Table
		setRowCount(0);
		//
		try
		{
			while (rs.next())
			{
				int row = getRowCount();
				setRowCount(row + 1);
				int colOffset = 1;  // columns start with 1
				for (int col = 0; col < m_layout.length; col++)
				{
					Object data = null;
					Class<?> c = m_layout[col].getColClass();
					int colIndex = col + colOffset;
					if (c == IDColumn.class)
						data = new IDColumn(rs.getInt(colIndex));
					else if (c == Boolean.class)
						data = Boolean.valueOf("Y".equals(rs.getString(colIndex)));
					else if (c == Timestamp.class)
						data = rs.getTimestamp(colIndex);
					else if (c == BigDecimal.class)
						data = rs.getBigDecimal(colIndex);
					else if (c == Double.class)
						data = rs.getDouble(colIndex);
					else if (c == Integer.class)
						data = rs.getInt(colIndex);
					else if (c == KeyNamePair.class)
					{
						String display = rs.getString(colIndex);
						int key = rs.getInt(colIndex + 1);
						data = new KeyNamePair(key, display);
						colOffset++;
					}
					else
					{
						String s = rs.getString(colIndex);
						if (s != null)
							data = s.trim();	// problems with NCHAR
					}
					// store
					setValueAt(data, row, col);
					// log.debug( "r=" + row + ", c=" + col + " " + m_layout[col].getColHeader(),
					// "data=" + data.toString() + " " + data.getClass().getName() + " * " + m_table.getCellRenderer(row, col));
				}

			}
		}
		catch (SQLException e)
		{
			log.error("", e);
		}
		if (getShowTotals())
			addTotals(m_layout);
		autoSize();
		log.info("Row(rs)=" + getRowCount());

	}	// loadTable

	/**
	 * Load Table from Object Array
	 * 
	 * @param pos array of POs
	 */
	@Override
	public void loadTable(PO[] pos)
	{
		if (m_layout == null)
			throw new UnsupportedOperationException("Layout not defined");

		// Clear Table
		setRowCount(0);
		//
		for (int i = 0; i < pos.length; i++)
		{
			PO myPO = pos[i];
			int row = getRowCount();
			setRowCount(row + 1);

			for (int col = 0; col < m_layout.length; col++)
			{
				String columnName = m_layout[col].getColSQL();
				Object data = myPO.get_Value(columnName);
				if (data != null)
				{
					Class<?> c = m_layout[col].getColClass();
					if (c == IDColumn.class)
						data = new IDColumn(((Integer)data).intValue());
					else if (c == Double.class)
						data = new Double(((BigDecimal)data).doubleValue());
				}
				// store
				setValueAt(data, row, col);
			}
		}
		if (getShowTotals())
			addTotals(m_layout);
		autoSize();
		log.info("Row(array)=" + getRowCount());
	}	// loadTable

	/**
	 * Get the key of currently selected row based on layout defined in prepareTable
	 * 
	 * @return ID if key
	 */
	@Override
	public Integer getSelectedRowKey()
	{
		final int row = getSelectedRow();
		if (row < 0)
		{
			return null;
		}

		final boolean onlyIfSelected = false; // NOTE: using false to preserve backward compatibility
		return getRowKey(row, onlyIfSelected);
	}   // getSelectedRowKey

	/**
	 * 
	 * @param row
	 * @param onlyIfSelected if true and row is not selected it will return null
	 * @return rowKey or null
	 */
	private Integer getRowKey(final int row, final boolean onlyIfSelected)
	{
		if (m_layout == null)
		{
			throw new IllegalStateException("Layout not defined (m_layout)");
		}

		if (row < 0)
		{
			return null;
		}

		if (p_keyColumnIndex < 0)
		{
			return null;
		}

		final Object data = getModel().getValueAt(row, p_keyColumnIndex);
		if (data instanceof IDColumn)
		{
			final IDColumn idColumn = (IDColumn)data;
			if (onlyIfSelected && !idColumn.isSelected())
			{
				return null;
			}
			final Integer rowKey = idColumn.getRecord_ID();
			return rowKey;
		}
		else if (data instanceof Integer)
		{
			if (onlyIfSelected)
			{
				return null;
			}
			final Integer rowKey = (Integer)data;
			return rowKey;
		}

		return null;
	}

	/**
	 * @return collection of selected IDs
	 */
	public Collection<Integer> getSelectedKeys()
	{
		if (m_layout == null)
		{
			throw new UnsupportedOperationException("Layout not defined");
		}
		if (p_keyColumnIndex < 0)
		{
			throw new UnsupportedOperationException("Key Column is not defined");
		}
		//
		final Set<Integer> list = new HashSet<Integer>();
		for (int row = 0; row < getRowCount(); row++)
		{
			final int rowKey = getRowKey(row, true); // onlyIfSelected=true
			if (rowKey == -1)
			{
				continue;
			}
			list.add(rowKey);
		}
		return list;
	}

	/**************************************************************************
	 * Get Layout
	 * 
	 * @return Array of ColumnInfo
	 */
	@Override
	public ColumnInfo[] getLayoutInfo()
	{
		return m_layout;
	}   // getLayout

	/**
	 * Set Single Selection
	 * 
	 * @param multiSelection multiple selections
	 */
	@Override
	public void setMultiSelection(boolean multiSelection)
	{
		m_multiSelection = multiSelection;
	}   // setMultiSelection

	/**
	 * Single Selection Table
	 * 
	 * @return true if multiple rows can be selected
	 */
	@Override
	public boolean isMultiSelection()
	{
		return m_multiSelection;
	}   // isMultiSelection

	/**
	 * Set the Column to determine the color of the row (based on model index)
	 * 
	 * @param modelIndex model index
	 */
	public void setColorColumn(int modelIndex)
	{
		final ITableColorProvider colorProvider = getColorProvider();
		if (colorProvider instanceof DefaultTableColorProvider)
		{
			final DefaultTableColorProvider defaultColorProvider = (DefaultTableColorProvider)colorProvider;
			defaultColorProvider.setColorColumnIndex(modelIndex);
		}
		else
		{
			throw new IllegalStateException("Cannot set color column when color provider is " + colorProvider);
		}
	}   // setColorColumn

	/**
	 * Set ColorColumn comparison criteria
	 * 
	 * @param dataCompare data
	 */
	@Override
	public void setColorCompare(Object dataCompare)
	{
		final ITableColorProvider colorProvider = getColorProvider();
		if (colorProvider instanceof DefaultTableColorProvider)
		{
			final DefaultTableColorProvider defaultColorProvider = (DefaultTableColorProvider)colorProvider;
			defaultColorProvider.setColorDataCompare(dataCompare);
		}
		else
		{
			throw new IllegalStateException("Cannot set color data compare when color provider is " + colorProvider);
		}
	}   //

	/**
	 * Set if Totals is Show
	 * 
	 * @param boolean Show
	 */
	public void setShowTotals(boolean show)
	{
		showTotals = show;
	}

	/**
	 * get if Totals is Show
	 * 
	 * @param boolean Show
	 */
	public boolean getShowTotals()
	{
		return showTotals;
	}

	/**
	 * Adding a new row with the totals
	 */
	public void addTotals(ColumnInfo[] layout)
	{
		if (getRowCount() == 0 || layout.length == 0)
			return;

		Object[] total = new Object[layout.length];

		for (int row = 0; row < getRowCount(); row++)
		{

			for (int col = 0; col < layout.length; col++)
			{
				Object data = getModel().getValueAt(row, col);
				Class<?> c = layout[col].getColClass();
				if (c == BigDecimal.class)
				{
					BigDecimal subtotal = Env.ZERO;
					if (total[col] != null)
						subtotal = (BigDecimal)(total[col]);

					BigDecimal amt = (BigDecimal)data;
					if (subtotal == null)
						subtotal = Env.ZERO;
					if (amt == null)
						amt = Env.ZERO;
					total[col] = subtotal.add(amt);
				}
				else if (c == Double.class)
				{
					Double subtotal = new Double(0);
					if (total[col] != null)
						subtotal = (Double)(total[col]);

					Double amt = (Double)data;
					if (subtotal == null)
						subtotal = new Double(0);
					if (amt == null)
						subtotal = new Double(0);
					total[col] = subtotal + amt;

				}
			}
		}

		// adding total row

		int row = getRowCount() + 1;
		setRowCount(row);
		for (int col = 0; col < layout.length; col++)
		{
			Class<?> c = layout[col].getColClass();
			if (c == BigDecimal.class)
			{
				setValueAt(total[col], row - 1, col);
			}
			else if (c == Double.class)
			{
				setValueAt(total[col], row - 1, col);
			}
			else
			{
				if (col == 0)
				{
					setValueAt(" Σ  ", row - 1, col);
				}
				else
					setValueAt(null, row - 1, col);
			}

		}
	}

	/**
	 * Adding a new row with the totals
	 */
	public void addTotals(Info_Column[] layout)
	{
		if (getRowCount() == 0 || layout.length == 0)
			return;

		Object[] total = new Object[layout.length];

		for (int row = 0; row < getRowCount(); row++)
		{

			for (int col = 0; col < layout.length; col++)
			{
				Object data = getModel().getValueAt(row, col);
				Class<?> c = layout[col].getColClass();
				if (c == BigDecimal.class)
				{
					BigDecimal subtotal = Env.ZERO;
					if (total[col] != null)
						subtotal = (BigDecimal)(total[col]);

					BigDecimal amt = (BigDecimal)data;
					if (subtotal == null)
						subtotal = Env.ZERO;
					if (amt == null)
						amt = Env.ZERO;
					total[col] = subtotal.add(amt);
				}
				else if (c == Double.class)
				{
					Double subtotal = new Double(0);
					if (total[col] != null)
						subtotal = (Double)(total[col]);

					Double amt = (Double)data;
					if (subtotal == null)
						subtotal = new Double(0);
					if (amt == null)
						subtotal = new Double(0);
					total[col] = subtotal + amt;

				}
			}
		}

		// adding total row

		int row = getRowCount() + 1;
		setRowCount(row);
		for (int col = 0; col < layout.length; col++)
		{
			Class<?> c = layout[col].getColClass();
			if (c == BigDecimal.class)
			{
				setValueAt(total[col], row - 1, col);
			}
			else if (c == Double.class)
			{
				setValueAt(total[col], row - 1, col);
			}
			else
			{
				if (col == 1)
				{
					setValueAt(" Σ  ", row - 1, col);
				}
				else
					setValueAt(null, row - 1, col);
			}

		}
	}

	@Override
	public void setSelectedRows(final List<Integer> rowsToSelect)
	{
		final ListSelectionModel selectionModel = getSelectionModel();

		// Force fire event
		if (rowsToSelect == null && !isLoading() && selectionModel.isSelectionEmpty())
		{
			firePropertyChange(PROPERTY_SelectionChanged, false, true);
		}

		//
		// Check if current selection changed
		// NOTE: this is important because we don't want to send false selection changed events
		if (!selectionModel.isSelectionEmpty())
		{
			boolean selectionChanged = false;
			final Set<Integer> rowsToSelectNotInModel = rowsToSelect == null ? Collections.<Integer> emptySet() : new HashSet<Integer>(rowsToSelect);
			for (int row = selectionModel.getMinSelectionIndex(); row <= selectionModel.getMaxSelectionIndex(); row++)
			{
				final boolean selectedOld = selectionModel.isSelectedIndex(row);
				final boolean selectedNew = rowsToSelect != null && rowsToSelectNotInModel.remove(row);
				if (selectedOld != selectedNew)
				{
					selectionChanged = true;
					break;
				}
			}
			if (!selectionChanged && rowsToSelectNotInModel.isEmpty())
			{
				return;
			}
		}

		selectionModel.clearSelection();

		if (rowsToSelect == null || rowsToSelect.isEmpty())
		{
			return;
		}

		final int rowsCount = getRowCount();
		for (final Integer row : rowsToSelect)
		{
			if (row == null || row < 0)
			{
				continue;
			}
			if (row >= rowsCount)
			{
				continue;
			}

			selectionModel.addSelectionInterval(row, row);
		}
	}

	private boolean _loading = false;

	@Override
	public boolean setLoading(boolean loading)
	{
		final boolean loadingOld = this._loading;
		if (loadingOld == loading)
		{
			return loadingOld;
		}

		this._loading = loading;

		// Loading flag was switched from "true" to "false"
		if (!this._loading)
		{
			firePropertyChange(PROPERTY_SelectionChanged, false, true);
		}
		return loadingOld;
	}

	public boolean isLoading()
	{
		return _loading;
	}

	private Map<String, Integer> columnName2modelIndex = new HashMap<String, Integer>();

	/**
	 * Note: this method only works as expected if the given <code>columnName</code> was set with {@link ColumnInfo#setColumnName(String)}.
	 * 
	 * @param columnName
	 * @return
	 */
	public int getColumnModelIndex(final String columnName)
	{
		final Integer indexModel = columnName2modelIndex.get(columnName);
		if (indexModel == null)
		{
			return -1;
		}
		return indexModel;
	}

	/**
	 * Note: this method only works as expected if the given <code>columnName</code> was set with {@link ColumnInfo#setColumnName(String)}.
	 * 
	 * @param rowIndexView
	 * @param columnName
	 * @return
	 */
	public Object getValueAt(final int rowIndexView, final String columnName)
	{
		final int colIndexModel = getColumnModelIndex(columnName);
		if (colIndexModel < 0) // it starts with 0, not with 1, so it's <0, not <=0
		{
			throw new IllegalArgumentException("No column index found for " + columnName);
		}

		final int rowIndexModel = convertRowIndexToModel(rowIndexView);

		return getModel().getValueAt(rowIndexModel, colIndexModel);
	}

	public void setValueAt(final Object value, final int rowIndexView, final String columnName)
	{
		final int colIndexModel = getColumnModelIndex(columnName);
		if (colIndexModel <= 0)
		{
			throw new IllegalArgumentException("No column index found for " + columnName);
		}

		final int rowIndexModel = convertRowIndexToModel(rowIndexView);

		getModel().setValueAt(value, rowIndexModel, colIndexModel);
	}

	@Override
	public void clear()
	{
		final PO[] pos = new PO[] {};
		loadTable(pos);
	}
}   // MiniTable

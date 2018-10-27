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
package org.compiere.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SortOrder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.adempiere.ad.ui.DefaultTableColorProvider;
import org.adempiere.ad.ui.ITable;
import org.adempiere.ad.ui.ITableColorProvider;
import org.jdesktop.swingx.icon.ColumnControlIcon;

/**
 * Model Independent enhanced JTable. Provides sizing and sorting.
 * 
 * @author Jorg Janke
 * @version $Id: CTable.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL - BF [ 1585369 ], FR [ 1753943 ]
 */
public class CTable extends JTable implements ITable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 772619961990977001L;

	/**
	 * Fired when table column layout changes.
	 * <ul>
	 * <li>oldValue - null
	 * <li>newValue - {@link TableColumn} that changed
	 * </ul>
	 */
	public static final String PROPERTY_TableColumnChanged = "TableColumnChanged";

	public static final String PROPERTY_ColumnControlVisible = "columnControlVisible";

	/**
	 * Fired when columnModel has changed or any TableColumn settings were changed
	 */
	public static final String PROPERTY_ColumnModel = "columnModel";

	/**
	 * Default Constructor
	 */
	public CTable()
	{
		this(new DefaultTableModel());
	}

	public CTable(final DefaultTableModel tableModel)
	{
		// NOTE: we are using DefaultTableModel instead of TableModel interface because in a lot of places in the code we have (DefaultTableModel)getModel()
		super(tableModel);
		setColumnSelectionAllowed(false);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // teo_sarca - FR [ 1753943 ]
		setUpdateSelectionOnSort(true); // 04771
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getTableHeader().addMouseListener(mouseListener);
		setSurrendersFocusOnKeystroke(true);
		// Default row height too narrow
		setRowHeight(getFont().getSize() + 8);

		setColumnControlVisible(false); // NOTE: deactivating it. It shall be activated where is needed.
		addHierarchyListener(createHierarchyListener());
	}	// CTable

	
	private final HierarchyListener createHierarchyListener()
	{
		return new HierarchyListener()
		{

			@Override
			public void hierarchyChanged(HierarchyEvent e)
			{
				if (e.getChangeFlags() == HierarchyEvent.PARENT_CHANGED)
				{
					configureColumnControl();
				}
			}

		};
	}
	
	private final CTableModelRowSorter modelRowSorter = createModelRowSorter();
	private final CTableViewRowSorter viewRowSorter = new CTableViewRowSorter()
			.setModelRowSorter(modelRowSorter);

	private final CTableMouseListener mouseListener = new CTableMouseListener();
	
	public final CTableMouseListener getMouseListener() 
	{
		return mouseListener;
	}

	protected CTableModelRowSorter createModelRowSorter()
	{
		return new CTableModelRowSorter(this);
	}

	public final void setModelSortingEnabled(final boolean modelSortingEnabled)
	{
		final boolean modelSortingEnabledOld = modelRowSorter.isEnabled();
		if (modelSortingEnabledOld == modelSortingEnabled)
		{
			return;
		}

		modelRowSorter.setEnabled(modelSortingEnabled);

		if (modelSortingEnabled)
		{
			this.setAutoCreateRowSorter(false);
			this.setRowSorter(null);
		}
		else
		{
			this.setAutoCreateRowSorter(false);
			setupViewRowSorter();
		}
	}

	public final void setSortIndexComparator(final int modelColumnIndex, final Comparator<? extends Object> comparator)
	{
		@SuppressWarnings("unchecked")
		final Comparator<Object> objectComparator = (Comparator<Object>)comparator;
		modelRowSorter.setSortIndexComparator(modelColumnIndex, objectComparator);
		
		// Update the view sorter's comparator, only if is active.
		// Else would throw "column beyond range of TableModel" because there is no model set.
		if (getRowSorter() == viewRowSorter)
		{
			viewRowSorter.setComparator(modelColumnIndex, comparator);
		}
	}

	/** Sizing: making sure it fits in a column */
	private final int SLACK = 15;
	/** Sizing: max size in pt */
	private final int MAXSIZE = 250;
	/** Model Index of Key Column */
	protected int p_keyColumnIndex = -1;

	/** Logger */
	private static final transient Logger log = LogManager.getLogger(CTable.class.getName());

	/**
	 * ScrollPane's original vertical scroll policy. If the column control is visible the policy is set to ALWAYS.
	 */
	private int verticalScrollPolicy;

	/**
	 * Flag to indicate if the column control is visible.
	 */
	private boolean columnControlVisible = false;

	/**
	 * The component used a column control in the upper trailing corner of an enclosing <code>JScrollPane</code>.
	 */
	private CColumnControlButton columnControlButton;

	private Map<TableColumn, ColumnAttributes> hiddenColumns = new IdentityHashMap<TableColumn, ColumnAttributes>();

	/**
	 * Set Model index of Key Column. Used for identifying previous selected row after fort complete to set as selected row. If not set, column 0 is used.
	 * 
	 * @param keyColumnIndex model index
	 */
	public void setKeyColumnIndex(int keyColumnIndex)
	{
		p_keyColumnIndex = keyColumnIndex;
	}	// setKeyColumnIndex

	/**
	 * Get Model index of Key Column
	 * 
	 * @return model index
	 */
	public int getKeyColumnIndex()
	{
		return p_keyColumnIndex;
	}	// getKeyColumnIndex

	/**
	 * Get Current Row Key Column Value
	 * 
	 * @return value or null
	 */
	public Object getSelectedKeyColumnValue()
	{
		int row = getSelectedRow();
		if (row != -1 && p_keyColumnIndex != -1)
			return getModel().getValueAt(row, p_keyColumnIndex);
		return null;
	}	// getKeyColumnValue

	/**
	 * Get Selected Value or null
	 * 
	 * @return value
	 */
	public Object getSelectedValue()
	{
		int row = getSelectedRow();
		int col = getSelectedColumn();
		if (row == -1 || col == -1)
			return null;
		return getValueAt(row, col);
	}   // getSelectedValue

	/**
	 * Stop Table Editors and remove focus
	 * 
	 * @param saveValue save value
	 */
	public void stopEditor(boolean saveValue)
	{
		stopEditor(this, saveValue);
	}
	
	/**
	 * Stop Table Editors and remove focus
	 *
	 * @param table
	 * @param saveValue save value
	 */
	public static final void stopEditor(final JTable table, final boolean saveValue)
	{
		// Do nothing if no table cell is in editing mode
		if (!table.isEditing())
		{
			return;
		}
		
		// MultiRow - remove editors
		ChangeEvent ce = new ChangeEvent(table);
		if (saveValue)
			table.editingStopped(ce);
		else
			table.editingCanceled(ce);
		//
		if (table.getInputContext() != null)
			table.getInputContext().endComposition();
		// change focus to next
		table.transferFocus();
	}

	/**************************************************************************
	 * Size Columns.
	 *
	 * @param useColumnIdentifier if false uses plain content - otherwise uses Column Identifier to indicate displayed columns
	 */
	public void autoSize(boolean useColumnIdentifier)
	{
		final TableColumnModel columnModel = getColumnModel();
		final int size = columnModel.getColumnCount();
		// for all columns
		for (int c = 0; c < size; c++)
		{
			final TableColumn column = columnModel.getColumn(c);

			//
			// Not displayed columns
			if (useColumnIdentifier
					&& (column.getIdentifier() == null
							|| column.getMaxWidth() == 0
							|| column.getIdentifier().toString().length() == 0))
			{
				continue;
			}

			packColumn(column);
		}	// for all columns
	}	// autoSize

	/**
	 * Auto-size given <code>column</code>.
	 * 
	 * @param column column to be auto-sized
	 */
	public void packColumn(final TableColumn column)
	{
		//
		// Check if we have fixed length set from GridField
		final int widthFixed = getFixedWidth(column);
		if (widthFixed > 0)
		{
			column.setPreferredWidth(widthFixed);
			column.setWidth(widthFixed);
			return;
		}

		int width = 0;
		// Header
		TableCellRenderer renderer = column.getHeaderRenderer();
		if (renderer == null)
			renderer = new DefaultTableCellRenderer();
		Component comp = null;
		if (renderer != null)
		{
			comp = renderer.getTableCellRendererComponent(this,
					column.getHeaderValue(),
					false, // isSelected
					false, // hasFocus
					0, // row
					0 // column
					);
		}
		//
		if (comp != null)
		{
			width = comp.getPreferredSize().width;
			width = Math.max(width, comp.getWidth());

			// Cells
			int col = column.getModelIndex();
			int maxRow = Math.min(20, getRowCount());
			try
			{
				for (int row = 0; row < maxRow; row++)
				{
					renderer = getCellRenderer(row, col);
					comp = renderer.getTableCellRendererComponent
							(this, getValueAt(row, col), false, false, row, col);
					if (comp != null)
					{
						int rowWidth = comp.getPreferredSize().width;
						width = Math.max(width, rowWidth);
						// start: metas-2009_0021_AP1_CR52
						// if (comp instanceof VCellRenderer) {
						// Lookup lk = ((VCellRenderer) comp).getLookup();
						// if (lk instanceof MLookup && ((MLookup) lk).getDisplayType() != DisplayType.Search) {
						// ((MLookup) lk).refresh(true);
						// }
						// }
						// end: metas-2009_0021_AP1_CR52
					}
				}
			}
			catch (Exception e)
			{
				log.error("Error when packing column " + column.getIdentifier().toString(), e);
			}
			// Width not greater than 250
			width = Math.min(MAXSIZE, width + SLACK);
		}
		//
		column.setPreferredWidth(width);
	}

	/**
	 * Set initial sort configuration. If the sort settings are cleared by {@link #clearSortCriteria()}, they will subsequently be reloaded from this map. Note that the map implementation must be
	 * pre-ordered to the desired form. Also note that we demand a linked map to make it clear that the map's keys' ordering is a important part of the sort specification.
	 *
	 * @param initialSortIndexes2Direction
	 */
	public final void setInitialSortIndexes(final LinkedHashMap<Integer, Boolean> initialSortIndexes2Direction)
	{
		modelRowSorter.setInitialSortIndexes(initialSortIndexes2Direction);
	}

	/**
	 * Tells if there are actual sort index column to sort by (i.e. if there are sorting settings).
	 * 
	 * @return <code>true</code> is the internal <code>_initialSortIndexes2Direction</code> is <code>null</code> or empty.
	 * 
	 * @task 08416
	 */
	public final boolean isInitialSortIndexesEmpty()
	{
		return modelRowSorter.isInitialSortIndexesEmpty();
	}

	/**
	 * Sort this table according to its current settings. If there are no current settings, then they are loaded from this table's "initital" settings.
	 * 
	 * @see #setInitialSortIndexes(Map)
	 * @see #setSortIndexComparator(int, Comparator)
	 */
	public final void sort()
	{
		if (modelRowSorter.isEnabled())
		{
			modelRowSorter.sort();
		}
	}
	
	/**
	 * Sort Table
	 * 
	 * @param modelColumnIndex model column sort index
	 */
	protected void sort(final int modelColumnIndex)
	{
		if (modelRowSorter.isEnabled())
		{
			modelRowSorter.sort(modelColumnIndex);
		}
	}


	/**
	 * Clear sort criteria that were set by the user and invoke {@link #sort()}. This causes the "initial" sort settings to be restored and the sorting to be done according to those.
	 */
	public final void clearSortCriteria()
	{
		modelRowSorter.clearSortCriteria();
	}

	/**
	 * Mark that the user changed something, therefore persist his/her changes.
	 *
	 * @param reloadOriginalSorting
	 */
	public final void setReloadOriginalSorting(final boolean reloadOriginalSorting)
	{
		modelRowSorter.setReloadOriginalSorting(reloadOriginalSorting);
	}

	public void setClearFiltersAfterRefresh(final boolean clearFiltersAfterRefresh)
	{
		modelRowSorter.setClearFiltersAfterRefresh(clearFiltersAfterRefresh);
	}

	/* package */Vector<Object> getModelDataVector()
	{
		final DefaultTableModel model = (DefaultTableModel)getModel();

		@SuppressWarnings("unchecked")
		final Vector<Object> modelDataVector = model.getDataVector();
		return modelDataVector;
	}

	@Override
	public final void tableChanged(final TableModelEvent e)
	{
		if (modelRowSorter != null && modelRowSorter.isEnabled())
		{
			modelRowSorter.tableChanged(e);
		}

		super.tableChanged(e);
	}

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		return new StringBuilder("CTable[").append(getModel()).append("]").toString();
	}   // toString

	/**
	 * MouseListener
	 */
	private final class CTableMouseListener extends MouseAdapter
	{
		private TableColumn cachedResizingColumn = null;

		private CTableMouseListener()
		{
			super();
		}

		/**
		 * Mouse clicked
		 * 
		 * @param e event
		 */
		@Override
		public void mouseClicked(final MouseEvent e)
		{
			//
			// Resizing
			if (isInResizeRegion(e))
			{
				if (e.getClickCount() == 2)
				{
					packColumn(cachedResizingColumn);
				}
				uncacheResizingColumn();
			}
			//
			// Sorting
			else if (modelRowSorter.isEnabled())
			{
				final int viewColumnIndex = getColumnModel().getColumnIndexAtX(e.getX());
				// log.info( "Sort " + vc + "=" + getColumnModel().getColumn(vc).getHeaderValue());
				final int modelColumnIndex = convertColumnIndexToModel(viewColumnIndex);
				final TableColumn column = getTableHeader().getResizingColumn();
				if (column != null)
				{
					return;
				}
				modelRowSorter.setReloadOriginalSorting(false); // user did something; do not reload original sorting
				modelRowSorter.sort(modelColumnIndex);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			cacheResizingColumn(e);
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			cacheResizingColumn(e);
		}

		private void cacheResizingColumn(MouseEvent e)
		{
			final TableColumn column = getTableHeader().getResizingColumn();
			if (column != null)
			{
				cachedResizingColumn = column;
			}
		}

		private void uncacheResizingColumn()
		{
			cachedResizingColumn = null;
		}

		private boolean isInResizeRegion(MouseEvent e)
		{
			return cachedResizingColumn != null; // inResize;
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			uncacheResizingColumn();
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			uncacheResizingColumn();
		}
	}

	@Override
	public final void setFont(final Font font)
	{
		super.setFont(font);
		// Update row height
		setRowHeight(getFont().getSize() + 8);
	}

	/**
	 * @return column index of last column which was sorted
	 */
	public final int getSortColumn()
	{
		return modelRowSorter.getSortColumn();
	}

	/**
	 * @return boolean (last sorted column)
	 */
	public final boolean isSortAscending()
	{
		return modelRowSorter.isSortAscending();
	}


	public final boolean isSortAscending(final int modelColumnIndex)
	{
		return modelRowSorter.isSortAscending(modelColumnIndex);
	}
	
	public final SortOrder getSortOrder(final int modelColumnIndex)
	{
		if (modelRowSorter.isEnabled())
		{
			return modelRowSorter.getSortOrder(modelColumnIndex);
		}
		else
		{
			return viewRowSorter.getSortOrder(modelColumnIndex);
		}
	}

	/**
	 * Returns the column control visible property.
	 * <p>
	 * 
	 * @return boolean to indicate whether the column control is visible.
	 * @see #setColumnControlVisible(boolean)
	 * @see #setColumnControl(JComponent)
	 */
	public boolean isColumnControlVisible()
	{
		return columnControlVisible;
	}

	/**
	 * Sets the column control visible property. If true and <code>JXTable</code> is contained in a <code>JScrollPane</code>, the table adds the column control to the trailing corner of the scroll
	 * pane.
	 * <p>
	 * 
	 * Note: if the table is not inside a <code>JScrollPane</code> the column control is not shown even if this returns true. In this case it's the responsibility of the client code to actually show
	 * it.
	 * <p>
	 * 
	 * The default value is <code>false</code>.
	 * 
	 * @param visible boolean to indicate if the column control should be shown
	 * @see #isColumnControlVisible()
	 * @see #setColumnControl(JComponent)
	 * 
	 */
	public void setColumnControlVisible(final boolean visible)
	{
		final boolean columnControlVisibleOld = this.columnControlVisible;
		if (columnControlVisibleOld == visible)
		{
			// no changes
			return;
		}

		this.columnControlVisible = visible;
		configureColumnControl();
		firePropertyChange(PROPERTY_ColumnControlVisible, columnControlVisibleOld, this.columnControlVisible);
	}

	/**
	 * Returns the component used as column control. Lazily creates the control to the default if it is <code>null</code>.
	 * 
	 * @return component for column control, guaranteed to be != null.
	 * @see #setColumnControl(JComponent)
	 * @see #createDefaultColumnControl()
	 */
	public CColumnControlButton getColumnControl()
	{
		if (columnControlButton == null)
		{
			columnControlButton = createDefaultColumnControl();
		}
		return columnControlButton;
	}

	/**
	 * Creates the default column control used by this table. This implementation returns a <code>ColumnControlButton</code> configured with default <code>ColumnControlIcon</code>.
	 * 
	 * @return the default component used as column control.
	 * @see #setColumnControl(JComponent)
	 * @see org.jdesktop.swingx.table.ColumnControlButton
	 * @see org.jdesktop.swingx.icon.ColumnControlIcon
	 */
	private CColumnControlButton createDefaultColumnControl()
	{
		return new CColumnControlButton(this, new ColumnControlIcon());
	}

	/**
	 * Configures the upper trailing corner of an enclosing <code>JScrollPane</code>.
	 * 
	 * Adds/removes the <code>ColumnControl</code> depending on the <code>columnControlVisible</code> property.
	 * <p>
	 * 
	 * @see #setColumnControlVisible(boolean)
	 * @see #setColumnControl(JComponent)
	 */
	private void configureColumnControl()
	{
		Container p = getParent();
		if (p instanceof JViewport)
		{
			Container gp = p.getParent();
			if (gp instanceof JScrollPane)
			{
				JScrollPane scrollPane = (JScrollPane)gp;
				// Make certain we are the viewPort's view and not, for
				// example, the rowHeaderView of the scrollPane -
				// an implementor of fixed columns might do this.
				JViewport viewport = scrollPane.getViewport();
				if (viewport == null || viewport.getView() != this)
				{
					return;
				}
				if (isColumnControlVisible())
				{
					verticalScrollPolicy = scrollPane.getVerticalScrollBarPolicy();
					scrollPane.setCorner(JScrollPane.UPPER_TRAILING_CORNER, getColumnControl());

					scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				}
				else
				{
					if (verticalScrollPolicy != 0)
					{
						// Fix #155-swingx: reset only if we had force always before
						// PENDING: JW - doesn't cope with dynamically changing the policy
						// shouldn't be much of a problem because doesn't happen too often??
						scrollPane.setVerticalScrollBarPolicy(verticalScrollPolicy);
					}
					try
					{
						scrollPane.setCorner(JScrollPane.UPPER_TRAILING_CORNER, null);
					}
					catch (Exception ex)
					{
						// Ignore spurious exception thrown by JScrollPane. This
						// is a Swing bug!
					}

				}
			}
		}
	}

	/**
	 * 
	 * @param column
	 * @return boolean
	 */
	public boolean isColumnVisible(TableColumn column)
	{
		return !hiddenColumns.containsKey(column);
	}

	/**
	 * Hide or show column
	 * 
	 * @param column
	 * @param visible
	 */
	public void setColumnVisibility(final TableColumn column, final boolean visible)
	{
		Check.assumeNotNull(column, "column not null");

		//
		// Check if visibility changed
		final boolean visibleOld = isColumnVisible(column);
		if (visible == visibleOld)
		{
			// nothing changed
			return;
		}

		if (visible)
		{
			ColumnAttributes attributes = hiddenColumns.remove(column);
			if (attributes == null)
			{
				return;
			}

			column.setCellEditor(attributes.cellEditor);
			column.setCellRenderer(attributes.cellRenderer);
			column.setMinWidth(attributes.minWidth);
			column.setMaxWidth(attributes.maxWidth);
			column.setPreferredWidth(attributes.preferredWidth);
		}
		else
		// not visible
		{
			final ColumnAttributes attributes = new ColumnAttributes();
			attributes.cellEditor = column.getCellEditor();
			attributes.cellRenderer = column.getCellRenderer();
			attributes.minWidth = column.getMinWidth();
			attributes.maxWidth = column.getMaxWidth();
			attributes.preferredWidth = column.getPreferredWidth();
			TableCellNone cellEditor = null;
			// CHANGED
			if (column.getIdentifier() == null && column.getHeaderValue() == null)
				cellEditor = new TableCellNone("");
			else
				cellEditor = new TableCellNone(column.getIdentifier() != null ? column.getIdentifier().toString() : column.getHeaderValue().toString());
			column.setCellEditor(cellEditor);
			column.setCellRenderer(cellEditor);
			column.setMinWidth(0);
			column.setMaxWidth(0);
			column.setPreferredWidth(0);

			hiddenColumns.put(column, attributes);
		}

		// metas: begin
		// if (persist)
		// {
		firePropertyChange(PROPERTY_TableColumnChanged, null, column);
		// }
		// else
		// {
		// // TODO: why the fuck we need to fire this event?
		// firePropertyChange(PROPERTY_ColumnModel, getColumnModel(), null);
		// }
		// metas: end
	}

	public static interface ITableColumnWidthCallback
	{
		int getWidth(TableColumn column);
	}

	private ITableColumnWidthCallback tableColumnFixedWidthCallback = null;

	public void setTableColumnFixedWidthCallback(ITableColumnWidthCallback tableColumnFixedWidthCallback)
	{
		if (this.tableColumnFixedWidthCallback == tableColumnFixedWidthCallback)
		{
			return;
		}

		// Avoid common development errors
		// i.e. current tableColumnFixedWidthCallback is set back to null
		if (tableColumnFixedWidthCallback == null)
		{
			throw new IllegalArgumentException("Cannot set tableColumnFixedWidthCallback back to null");
		}

		this.tableColumnFixedWidthCallback = tableColumnFixedWidthCallback;
	}

	private int getFixedWidth(final TableColumn column)
	{
		if (tableColumnFixedWidthCallback == null)
		{
			return -1;
		}
		return tableColumnFixedWidthCallback.getWidth(column);
	}

	/**
	 * Class used to store column attributes when the column it is hidden. We need those informations to restore the column in case it is unhide.
	 */
	private final class ColumnAttributes
	{
		protected TableCellEditor cellEditor;
		protected TableCellRenderer cellRenderer;
		// protected Object headerValue;
		protected int minWidth;
		protected int maxWidth;
		protected int preferredWidth;
	}

	@Override
	public Object getModelValueAt(int rowIndexModel, int columnIndexModel)
	{
		return getModel().getValueAt(rowIndexModel, columnIndexModel);
	}

	private ITableColorProvider colorProvider = new DefaultTableColorProvider();

	@Override
	public void setColorProvider(final ITableColorProvider colorProvider)
	{
		Check.assumeNotNull(colorProvider, "colorProvider not null");
		this.colorProvider = colorProvider;
	}

	@Override
	public ITableColorProvider getColorProvider()
	{
		return colorProvider;
	}

	@Override
	public void setModel(final TableModel dataModel)
	{
		super.setModel(dataModel);
		if (modelRowSorter == null)
		{
			// i.e. we are in JTable constructor and modelRowSorter was not yet set
			// => do nothing
		}
		else if (!modelRowSorter.isEnabled())
		{
			setupViewRowSorter();
		}
	}

	private final void setupViewRowSorter()
	{
		final TableModel model = getModel();
		viewRowSorter.setModel(model);
		
		if (modelRowSorter != null)
		{
			viewRowSorter.setSortKeys(modelRowSorter.getSortKeys());
		}
		
		setRowSorter(viewRowSorter);
		viewRowSorter.sort();
	}

}	// CTable

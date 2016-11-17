package org.compiere.apps.search;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.adempiere.plaf.VEditorUI;
import org.adempiere.util.Check;
import org.compiere.swing.CTable;
import org.jdesktop.swingx.JXTable;

/**
 * Advanced search table.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class FindAdvancedSearchTable extends JXTable
{
	private static final long serialVersionUID = -4660262679439670002L;

	public FindAdvancedSearchTable()
	{
		super(new FindAdvancedSearchTableModel());

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		setEditable(true);
		setSortable(false);
		setColumnControlVisible(false);
		setBorder(BorderFactory.createEmptyBorder());
		setRowHeight(VEditorUI.getVEditorHeight());

		final TableCellRenderer renderer = new ProxyRenderer(getDefaultRenderer(Object.class));
		setDefaultRenderer(Object.class, renderer);

		final InputMap inputMap = getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		// On TAB pressed => request focus in window
		{
			final KeyStroke tabKey = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
			final Action tabAction = getActionMap().get(inputMap.get(tabKey));
			final Action tabActionWrapper = new AbstractAction()
			{
				private static final long serialVersionUID = -6868476640719619801L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					tabAction.actionPerformed(e);

					final JTable table = (JTable)e.getSource();
					table.requestFocusInWindow();
				}
			};
			getActionMap().put(inputMap.get(tabKey), tabActionWrapper);
		}

		// On SHIFT-TAB pressed => request focus in window
		{
			final KeyStroke shiftTabKey = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_MASK);
			final Action shiftTabAction = getActionMap().get(inputMap.get(shiftTabKey));
			final Action shiftTabActionWrapper = new AbstractAction()
			{
				private static final long serialVersionUID = 5493691483070046620L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					shiftTabAction.actionPerformed(e);

					final JTable table = (JTable)e.getSource();
					table.requestFocusInWindow();
				}
			};
			getActionMap().put(inputMap.get(shiftTabKey), shiftTabActionWrapper);
		}

		// 0 = Join Operator
		{
			final TableColumn tc = getColumnModel().getColumn(FindAdvancedSearchTableModel.INDEX_JOIN);
			tc.setMaxWidth(70);

			final FindJoinCellEditor cellRendererAndEditor = new FindJoinCellEditor();
			tc.setCellRenderer(cellRendererAndEditor);
			tc.setCellEditor(cellRendererAndEditor);
		}

		// 1 = Columns
		{
			final TableColumn tc = getColumnModel().getColumn(FindAdvancedSearchTableModel.INDEX_COLUMNNAME);
			tc.setPreferredWidth(150);

			final FindColumnNameCellEditor cellEditor = new FindColumnNameCellEditor();
			cellEditor.addCellEditorListener(new CellEditorListener()
			{
				@Override
				public void editingCanceled(ChangeEvent ce)
				{
				}

				@Override
				public void editingStopped(ChangeEvent ce)
				{
					final int rowIndex = getSelectedRow();
					if (rowIndex < 0)
					{
						return; // do nothing if there is no row selected; shall not happen
					}

					if (cellEditor.isCellEditorValueChanged() || cellEditor.isCellEditorValueNull())
					{
						final FindAdvancedSearchTableModel model = getModel();
						final IUserQueryRestriction row = model.getRow(rowIndex);
						row.setValue(null);
						row.setValueTo(null);
						model.fireTableRowsUpdated(rowIndex, rowIndex);
					}
				}
			});

			tc.setCellRenderer(cellEditor);
			tc.setCellEditor(cellEditor);
		}

		// 2 = Operators
		{
			final TableColumn tc = getColumnModel().getColumn(FindAdvancedSearchTableModel.INDEX_OPERATOR);
			tc.setMaxWidth(100);
			tc.setCellEditor(new FindOperatorCellEditor());
		}

		// 3 = QueryValue
		{
			final TableColumn tc = getColumnModel().getColumn(FindAdvancedSearchTableModel.INDEX_VALUE);
			tc.setCellRenderer(new ProxyRenderer(new FindValueRenderer(false)));
			tc.setCellEditor(new FindValueEditor(false));
		}

		// 4 = QueryValue2
		{
			final TableColumn tc = getColumnModel().getColumn(FindAdvancedSearchTableModel.INDEX_VALUE2);
			tc.setPreferredWidth(50);
			tc.setCellRenderer(new ProxyRenderer(new FindValueRenderer(true)));
			tc.setCellEditor(new FindValueEditor(true));
		}

	}

	@Override
	public FindAdvancedSearchTableModel getModel()
	{
		return (FindAdvancedSearchTableModel)super.getModel();
	}

	@Override
	public void setModel(final TableModel dataModel)
	{
		Check.assumeInstanceOfOrNull(dataModel, FindAdvancedSearchTableModel.class, "dataModel");
		super.setModel(dataModel);
	}

	public void stopEditor(final boolean saveValue)
	{
		CTable.stopEditor(this, saveValue);
	}

	private static class ProxyRenderer implements TableCellRenderer
	{
		public ProxyRenderer(TableCellRenderer renderer)
		{
			super();
			this.delegate = renderer;
		}

		/** The renderer. */
		private final TableCellRenderer delegate;

		@Override
		public Component getTableCellRendererComponent(final JTable table, Object value, boolean isSelected, boolean hasFocus, final int row, final int col)
		{
			final Component comp = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
			if (hasFocus && table.isCellEditable(row, col))
				table.editCellAt(row, col);
			return comp;
		}
	} // ProxyRenderer

	/**
	 * Create a new row
	 */
	public void newRow()
	{
		stopEditor(true);

		final FindAdvancedSearchTableModel model = getModel();
		model.newRow();
		final int rowIndex = model.getRowCount() - 1;

		getSelectionModel().setSelectionInterval(rowIndex, rowIndex);

		requestFocusInWindow();
	}

	public void deleteCurrentRow()
	{
		stopEditor(false);
		final int rowIndex = getSelectedRow();
		if (rowIndex >= 0)
		{
			final FindAdvancedSearchTableModel model = getModel();
			model.removeRow(rowIndex);

			// Select next row if possible
			final int rowToSelect = Math.min(rowIndex, model.getRowCount() - 1);
			if (rowToSelect >= 0)
			{
				getSelectionModel().setSelectionInterval(rowToSelect, rowToSelect);
			}
		}
		requestFocusInWindow();
	}
}

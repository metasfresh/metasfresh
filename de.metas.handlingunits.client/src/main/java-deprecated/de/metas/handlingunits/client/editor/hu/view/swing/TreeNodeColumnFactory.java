package de.metas.handlingunits.client.editor.hu.view.swing;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.table.ColumnFactory;
import org.jdesktop.swingx.table.TableColumnExt;

import de.metas.handlingunits.client.editor.model.AbstractHUTreeTableModel;
import de.metas.handlingunits.client.editor.view.column.ITreeTableColumn;
import de.metas.handlingunits.client.editor.view.swing.NumberCellValueEditor;
import de.metas.handlingunits.tree.node.ITreeNode;

/**
 * Responsible for configuring tree table columns
 * 
 * @author tsa
 * 
 */
public class TreeNodeColumnFactory<T extends ITreeNode<T>> extends ColumnFactory
{
	private final HUComboBoxCellEditor cellEditorCombobox;
	private final NumberCellValueEditor cellEditorNumber;

	private final List<ITreeTableColumn<T>> columnAdapters;

	public TreeNodeColumnFactory(final JXTreeTable table, final List<ITreeTableColumn<T>> columnAdapters)
	{
		super();

		this.cellEditorCombobox = new HUComboBoxCellEditor(table);
		this.cellEditorNumber = new NumberCellValueEditor(0); // no decimal places

		this.columnAdapters = columnAdapters;
	}

	@Override
	public void configureTableColumn(final TableModel model, final TableColumnExt columnExt)
	{
		super.configureTableColumn(model, columnExt);
		
		final int columnIndex = columnExt.getModelIndex();
		final ITreeTableColumn<T> columnAdapter = this.columnAdapters.get(columnIndex);

		setCellEditor(columnExt, columnAdapter);
		setCellRenderer(columnExt, columnAdapter);
		
		columnAdapter.configureTableColumn(columnExt);
	}
	
	private void setCellEditor(final TableColumnExt columnExt, final ITreeTableColumn<T> columnAdapter)
	{
		if (columnAdapter.getColumnType() == Boolean.class)
		{
			columnExt.setCellEditor(null);
			return;
		}
		
		if (columnAdapter.getColumnType() == BigDecimal.class)
		{
			columnExt.setCellEditor(this.cellEditorNumber);
		}
		else if (columnAdapter.isCombobox())
		{
			columnExt.setCellEditor(TreeNodeColumnFactory.this.cellEditorCombobox);
		}
	}

	private void setCellRenderer(final TableColumnExt columnExt, final ITreeTableColumn<T> columnAdapter)
	{
		if (columnAdapter.getColumnType() == Boolean.class)
		{
			columnExt.setCellRenderer(null);
			return;
		}
		
		final DefaultTableCellRenderer cellRenderer = new HUDefaultTableCellRenderer<T>();

		final int alignment = columnAdapter.getAlignment();
		// custom alignment
		if (alignment != ITreeTableColumn.DEFAULT_ALIGNMENT)
		{
			cellRenderer.setHorizontalAlignment(alignment);
		}
		// align right in case of numbers
		else if (columnAdapter.getColumnType() == BigDecimal.class)
		{
			cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		// align left otherwise
		else
		{
			cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		}

		columnExt.setCellRenderer(cellRenderer);
	}

	private static class HUDefaultTableCellRenderer<T extends ITreeNode<T>> extends DefaultTableCellRenderer
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2386935539823400615L;

		public HUDefaultTableCellRenderer()
		{
			super();
		}

		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object value,
				final boolean isSelected, final boolean hasFocus, final int rowIndexView, final int columnIndexView)
		{
			final JXTreeTable treeTable = (JXTreeTable)table;
			@SuppressWarnings("unchecked")
			final AbstractHUTreeTableModel<T> model = (AbstractHUTreeTableModel<T>)treeTable.getTreeTableModel();

			final int columnIndexModel = table.convertColumnIndexToModel(columnIndexView);
			final String displayName = model.getDisplayName(columnIndexModel, value);

			final Component comp = super.getTableCellRendererComponent(treeTable, displayName, isSelected, hasFocus, rowIndexView, columnIndexView);
			return comp;
		}
	}
}

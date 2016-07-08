package org.compiere.apps.form.fileimport;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.impexp.CellErrorMessage;
import org.jdesktop.swingx.table.ColumnFactory;
import org.jdesktop.swingx.table.TableColumnExt;

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class FileImportPreviewColumnFactory extends ColumnFactory
{
	public static final transient FileImportPreviewColumnFactory instance = new FileImportPreviewColumnFactory();

	private FileImportPreviewColumnFactory()
	{
		super();
	}

	@Override
	public void configureTableColumn(final TableModel model, final TableColumnExt columnExt)
	{
		super.configureTableColumn(model, columnExt);

		final TableColumnModel columnModel = getTableColumnModelOrNull(model, columnExt);
		if (columnModel == null)
		{
			return;
		}

		final int width = columnModel.getWidth();
		if (width > 0)
		{
			columnExt.setWidth(width);
			columnExt.setMaxWidth(width);
		}

		if (columnModel instanceof ImpDataLineRowTableColumnModel)
		{
			columnExt.setCellRenderer(new ImpDataCellRenderer());
		}
	}

	private static final TableColumnModel getTableColumnModelOrNull(final TableModel model, final TableColumnExt columnExt)
	{
		FileImportPreviewTableModel importTableModel = FileImportPreviewTableModel.castOrNull(model);
		if (importTableModel == null)
		{
			return null;
		}

		return importTableModel.getColumnModel(columnExt.getModelIndex());
	}

	private static final class ImpDataCellRenderer extends DefaultTableCellRenderer
	{
		private static final long serialVersionUID = 1L;

		@Override
		public JComponent getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int rowIndexView, final int columnIndexView)
		{
			final JLabel comp = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndexView, columnIndexView);

			final CellErrorMessage cellErrorMessage = getCellErrorMessage(table, rowIndexView, columnIndexView);
			if (cellErrorMessage != null)
			{
				comp.setText("ERROR");
				comp.setToolTipText(cellErrorMessage.getMessage());
				comp.setBorder(BorderFactory.createLineBorder(AdempierePLAF.getTextColor_Issue()));
			}
			else
			{
				comp.setToolTipText(null);
			}

			return comp;
		}

		private final CellErrorMessage getCellErrorMessage(final JTable table, final int rowIndexView, final int columnIndexView)
		{
			final FileImportPreviewTableModel tableModel = FileImportPreviewTableModel.castOrNull(table.getModel());
			if (tableModel == null)
			{
				return null;
			}

			final int rowIndexModel = table.convertRowIndexToModel(rowIndexView);
			final int columnIndexModel = table.convertColumnIndexToModel(columnIndexView);
			return tableModel.getCellErrorMessage(rowIndexModel, columnIndexModel);
		}
	}
}

package org.compiere.apps.form.fileimport;

import javax.swing.table.TableModel;

import org.jdesktop.swingx.table.ColumnFactory;
import org.jdesktop.swingx.table.TableColumnExt;

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

		final TableColumnModel columnModel = getTableModelModelOrNull(model, columnExt);
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
	}

	private static final TableColumnModel getTableModelModelOrNull(final TableModel model, TableColumnExt columnExt)
	{
		if (!(model instanceof FileImportPreviewTableModel))
		{
			return null;
		}

		return ((FileImportPreviewTableModel)model).getColumnModel(columnExt.getModelIndex());
	}
}

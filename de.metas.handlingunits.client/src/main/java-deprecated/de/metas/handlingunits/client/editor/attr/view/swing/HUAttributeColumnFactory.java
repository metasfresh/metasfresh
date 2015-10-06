package de.metas.handlingunits.client.editor.attr.view.swing;

import javax.swing.table.TableModel;

import org.jdesktop.swingx.table.ColumnFactory;
import org.jdesktop.swingx.table.TableColumnExt;

import de.metas.handlingunits.client.editor.attr.model.HUAttributeSetModel;

public class HUAttributeColumnFactory extends ColumnFactory
{
	public static final transient HUAttributeColumnFactory instance = new HUAttributeColumnFactory();
	
	private final HUAttributeCellEditor cellEditor = new HUAttributeCellEditor();

	@Override
	public void configureTableColumn(final TableModel model, final TableColumnExt columnExt)
	{
		super.configureTableColumn(model, columnExt);

		if (columnExt.getModelIndex() == HUAttributeSetModel.COLUMN_PropertyName)
		{
			columnExt.setEditable(false);

		}
		else if (columnExt.getModelIndex() == HUAttributeSetModel.COLUMN_PropertyValue)
		{
			columnExt.setEditable(true);
			columnExt.setCellRenderer(cellEditor);
			columnExt.setCellEditor(cellEditor);
		}
	}
}

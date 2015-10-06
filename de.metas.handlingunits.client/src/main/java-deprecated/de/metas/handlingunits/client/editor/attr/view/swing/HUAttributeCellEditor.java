package de.metas.handlingunits.client.editor.attr.view.swing;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import de.metas.handlingunits.client.editor.attr.model.HUAttributeSetModel;
import de.metas.handlingunits.client.editor.view.swing.ListCellValueEditor;
import de.metas.handlingunits.client.editor.view.swing.ListCellValueRenderer;
import de.metas.handlingunits.client.editor.view.swing.NumberCellValueEditor;
import de.metas.handlingunits.client.editor.view.swing.NumberCellValueRenderer;
import de.metas.handlingunits.client.editor.view.swing.StringCellValueCellEditor;

/**
 * HU Attribute Cell Renderer and Editor
 * 
 * @author al
 * 
 */
public class HUAttributeCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -156197230220788570L;

	private final DefaultTableCellRenderer defaultCellRenderer = new DefaultTableCellRenderer();

	private final Map<String, TableCellRenderer> cellRenderers = new HashMap<String, TableCellRenderer>();
	private final Map<String, TableCellEditor> cellEditors = new HashMap<String, TableCellEditor>();

	private TableCellEditor cellEditor;

	public HUAttributeCellEditor()
	{
		super();

		cellEditors.put(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, new StringCellValueCellEditor());

		cellRenderers.put(X_M_Attribute.ATTRIBUTEVALUETYPE_Number, new NumberCellValueRenderer());
		cellEditors.put(X_M_Attribute.ATTRIBUTEVALUETYPE_Number, new NumberCellValueEditor());

		cellRenderers.put(X_M_Attribute.ATTRIBUTEVALUETYPE_List, new ListCellValueRenderer());
		cellEditors.put(X_M_Attribute.ATTRIBUTEVALUETYPE_List, new ListCellValueEditor());
	}

	@Override
	public Object getCellEditorValue()
	{
		if (cellEditor == null)
		{
			return null;
		}
		return cellEditor.getCellEditorValue();
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column)
	{
		if (column != HUAttributeSetModel.COLUMN_PropertyValue)
		{
			return null;
		}

		final HUAttributeSetModel model = (HUAttributeSetModel)table.getModel();

		final I_M_Attribute attribute = model.getM_Attribute(row);

		final String type = attribute.getAttributeValueType();
		cellEditor = cellEditors.get(type);
		return cellEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
	{
		if (column != HUAttributeSetModel.COLUMN_PropertyValue)
		{
			return null;
		}

		final HUAttributeSetModel model = (HUAttributeSetModel)table.getModel();
		final I_M_Attribute attribute = model.getM_Attribute(row);
		final String type = attribute.getAttributeValueType();

		TableCellRenderer cellRenderer = cellRenderers.get(type);
		if (cellRenderer == null)
		{
			cellRenderer = defaultCellRenderer;
		}
		return cellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}

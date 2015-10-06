package de.metas.handlingunits.client.editor.view.swing;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.compiere.model.I_M_AttributeValue;

public class ListCellValueRenderer extends DefaultTableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3970241592144924306L;

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
	{
		if (value == null)
		{
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
		final I_M_AttributeValue attributeValue = (I_M_AttributeValue)value;

		return super.getTableCellRendererComponent(table, attributeValue.getName(), isSelected, hasFocus, row, column);
	}
}

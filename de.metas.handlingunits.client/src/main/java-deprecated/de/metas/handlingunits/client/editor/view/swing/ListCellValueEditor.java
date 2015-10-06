package de.metas.handlingunits.client.editor.view.swing;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;

import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.util.Util;

import de.metas.handlingunits.client.editor.attr.model.HUAttributeSetModel;

public class ListCellValueEditor extends org.jdesktop.swingx.autocomplete.ComboBoxCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6707695717081733595L;

	private static final ListCellRenderer renderer = new DefaultListCellRenderer()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 909438237678300519L;

		@Override
		public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus)
		{
			final I_M_AttributeValue attributeValue = (I_M_AttributeValue)value;
			final String name = attributeValue == null ? null : attributeValue.getName();
			return super.getListCellRendererComponent(list, name, index, isSelected, cellHasFocus);
		}
	};

	private final JComboBox combobox;
	private final DefaultComboBoxModel comboboxModel;

	public ListCellValueEditor()
	{
		this(new JComboBox());
	}

	private ListCellValueEditor(final JComboBox combobox)
	{
		super(combobox);
		this.combobox = combobox;

		combobox.setRenderer(ListCellValueEditor.renderer);

		comboboxModel = new DefaultComboBoxModel();
		combobox.setModel(comboboxModel);
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column)
	{
		super.getTableCellEditorComponent(table, value, isSelected, row, column);

		final String valueStr;
		if (value == null)
		{
			valueStr = null;
		}
		else if (value instanceof I_M_AttributeValue)
		{
			valueStr = ((I_M_AttributeValue)value).getValue();
		}
		else
		{
			valueStr = value.toString();
		}

		//
		// Update model
		final HUAttributeSetModel model = (HUAttributeSetModel)table.getModel();
		final I_M_Attribute attribute = model.getM_Attribute(row);
		final List<I_M_AttributeValue> attributeValues = model.retrieveAttributeValues(attribute);

		I_M_AttributeValue selectedItem = null;

		comboboxModel.removeAllElements();
		for (final I_M_AttributeValue av : attributeValues)
		{
			comboboxModel.addElement(av);

			if (Util.equals(av.getValue(), valueStr))
			{
				selectedItem = av;
			}
		}
		combobox.setSelectedItem(selectedItem);

		return combobox;
	}
}

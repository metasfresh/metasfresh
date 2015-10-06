package de.metas.handlingunits.client.editor.hu.view.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;

import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;

public class HUComboBoxCellEditor extends org.jdesktop.swingx.autocomplete.ComboBoxCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6750092602187077044L;

	private static final String ATTR_HUExplorerEditorModel = HUEditorModel.class.getName();
	private static final String ATTR_HUExplorerEditorModel_Column = HUComboBoxCellEditor.ATTR_HUExplorerEditorModel + "#Column";

	private final JComboBox combobox;
	private final DefaultComboBoxModel comboboxModel;

	public HUComboBoxCellEditor(final JXTreeTable treeTable)
	{
		this(treeTable, new JComboBox());
	}

	private HUComboBoxCellEditor(final JXTreeTable treeTable, final JComboBox combobox)
	{
		super(combobox);
		this.combobox = combobox;

		final ListCellRenderer renderer = new DefaultListCellRenderer()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = -3686049690777962459L;

			@Override
			public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus)
			{
				final HUEditorModel model = (HUEditorModel)HUComboBoxCellEditor.this.combobox.getClientProperty(HUComboBoxCellEditor.ATTR_HUExplorerEditorModel);
				final Integer columnIndexView = (Integer)HUComboBoxCellEditor.this.combobox.getClientProperty(HUComboBoxCellEditor.ATTR_HUExplorerEditorModel_Column);

				if (model != null && columnIndexView != null)
				{
					final int columnIndexModel = treeTable.convertColumnIndexToModel(columnIndexView);

					final String displayName = model.getDisplayName(columnIndexModel, value);
					return super.getListCellRendererComponent(list, displayName, index, isSelected, cellHasFocus);
				}

				// Fallback to original
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		};
		combobox.setRenderer(renderer);

		comboboxModel = new DefaultComboBoxModel();
		combobox.setModel(comboboxModel);

		//
		// If user selects a value, commit the value immediately
		combobox.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				if (treeTable.getCellEditor() != HUComboBoxCellEditor.this)
				{
					return;
				}

				SwingUtilities.invokeLater(new Runnable()
				{

					@Override
					public void run()
					{
						HUComboBoxCellEditor.this.stopCellEditing();
					}
				});
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int rowIndexView, final int columnIndexView)
	{
		super.getTableCellEditorComponent(table, value, isSelected, rowIndexView, columnIndexView);

		final JXTreeTable treeTable = (JXTreeTable)table;
		final IHUTreeNode node = HUComboBoxCellEditor.this.getNode(treeTable, rowIndexView);
		if (node == null)
		{
			return combobox;
		}

		final int columnIndexModel = table.convertColumnIndexToModel(columnIndexView);
		final HUEditorModel editorModel = (HUEditorModel)treeTable.getTreeTableModel();

		combobox.putClientProperty(HUComboBoxCellEditor.ATTR_HUExplorerEditorModel, editorModel);
		combobox.putClientProperty(HUComboBoxCellEditor.ATTR_HUExplorerEditorModel_Column, columnIndexView);

		//
		// Update model
		final List<Object> availableValues = editorModel.getAvailableValuesList(node, columnIndexModel);
		comboboxModel.removeAllElements();
		for (final Object v : availableValues)
		{
			comboboxModel.addElement(v);
		}
		combobox.setSelectedItem(value);

		return combobox;
	}

	private final IHUTreeNode getNode(final JXTreeTable treeTable, final int row)
	{
		final TreePath path = treeTable.getPathForRow(row);
		if (path == null)
		{
			return null;
		}

		final IHUTreeNode node = (IHUTreeNode)path.getLastPathComponent();
		return node;
	}
}

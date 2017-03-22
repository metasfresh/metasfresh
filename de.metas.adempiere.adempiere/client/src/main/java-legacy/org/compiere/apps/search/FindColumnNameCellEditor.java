package org.compiere.apps.search;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.compiere.swing.CComboBox;
import org.compiere.swing.ListComboBoxModel;
import org.compiere.swing.ToStringListCellRenderer;

/**
 * Advanced search table - cell renderer and editor for ColumnName
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class FindColumnNameCellEditor extends FindCellEditor implements TableCellRenderer
{
	private static final long serialVersionUID = 508502538438329051L;

	private final DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
	private CComboBox<FindPanelSearchField> _editor;

	public FindColumnNameCellEditor()
	{
		super();
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
	{
		final String valueToDisplay = value == null ? "" : FindPanelSearchField.castToFindPanelSearchField(value).getDisplayNameTrl();
		return defaultRenderer.getTableCellRendererComponent(table, valueToDisplay, isSelected, hasFocus, row, column);
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int col)
	{
		updateEditor(table);
		return super.getTableCellEditorComponent(table, value, isSelected, row, col);
	}

	@Override
	protected CComboBox<FindPanelSearchField> getEditor()
	{
		if (_editor == null)
		{
			_editor = new CComboBox<>();
			_editor.setRenderer(new ToStringListCellRenderer<FindPanelSearchField>()
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected String renderToString(final FindPanelSearchField value)
				{
					return value == null ? "" : value.getDisplayNameTrl();
				}
			});

			_editor.enableAutoCompletion();
		}
		return _editor;
	}

	private void updateEditor(final JTable table)
	{
		final CComboBox<FindPanelSearchField> editor = getEditor();

		//
		// Set available search fields if not already set
		if (editor.getItemCount() == 0)
		{
			final FindAdvancedSearchTableModel model = (FindAdvancedSearchTableModel)table.getModel();
			final Collection<FindPanelSearchField> availableSearchFields = model.getAvailableSearchFields();
			if (availableSearchFields != null)
			{
				final List<FindPanelSearchField> availableSearchFieldsList = new ArrayList<>(availableSearchFields);
				Collections.sort(availableSearchFieldsList, Comparator.comparing(FindPanelSearchField::getDisplayNameTrl));
				editor.setModel(new ListComboBoxModel<FindPanelSearchField>(availableSearchFieldsList));
			}
		}
	}
}

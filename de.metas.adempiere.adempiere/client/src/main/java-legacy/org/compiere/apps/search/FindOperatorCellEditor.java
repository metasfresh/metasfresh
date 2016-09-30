package org.compiere.apps.search;

import java.awt.Component;

import javax.swing.JTable;

import org.compiere.model.MQuery;
import org.compiere.swing.CComboBox;
import org.compiere.swing.ListComboBoxModel;
import org.compiere.util.ValueNamePair;

/**
 * Advanced search table - cell editor for Operator cell
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class FindOperatorCellEditor extends FindCellEditor
{
	private static final long serialVersionUID = 6655568524454256089L;

	private CComboBox<ValueNamePair> _editor = null;

	private final ListComboBoxModel<ValueNamePair> modelForIDColumns = new ListComboBoxModel<>(MQuery.OPERATORS_ID);
	private final ListComboBoxModel<ValueNamePair> modelForYesNoColumns = new ListComboBoxModel<>(MQuery.OPERATORS_YN);
	private final ListComboBoxModel<ValueNamePair> modelDefault = new ListComboBoxModel<>(MQuery.OPERATORS);
	private final ListComboBoxModel<ValueNamePair> modelEmpty = new ListComboBoxModel<>();

	public FindOperatorCellEditor()
	{
		super();
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int col)
	{
		updateEditor(table, row);
		return super.getTableCellEditorComponent(table, value, isSelected, row, col);
	}

	@Override
	protected CComboBox<ValueNamePair> getEditor()
	{
		if (_editor == null)
		{
			_editor = new CComboBox<>();
			_editor.enableAutoCompletion();
		}
		return _editor;
	}

	private void updateEditor(final JTable table, final int viewRowIndex)
	{
		final CComboBox<ValueNamePair> editor = getEditor();

		final IUserQueryRestriction row = getRow(table, viewRowIndex);
		final FindPanelSearchField searchField = FindPanelSearchField.castToFindPanelSearchField(row.getSearchField());

		if (searchField != null)
		{
			// check if the column is columnSQL with reference (08757)
			final String columnName = searchField.getColumnName();
			final boolean isColumnSQL = searchField.isVirtualColumn();
			final boolean isReference = searchField.getAD_Reference_Value_ID() > 0;

			if (isColumnSQL && isReference)
			{
				// make sure also the columnSQLs with reference are only getting the ID operators (08757)
				editor.setModel(modelForIDColumns);
			}
			else if (columnName.endsWith("_ID") || columnName.endsWith("_Acct"))
			{
				editor.setModel(modelForIDColumns);
			}
			else if (columnName.startsWith("Is"))
			{
				editor.setModel(modelForYesNoColumns);
			}
			else
			{
				editor.setModel(modelDefault);
			}
		}
		else
		{
			editor.setModel(modelEmpty);
		}
	}

	private IUserQueryRestriction getRow(final JTable table, final int viewRowIndex)
	{
		final FindAdvancedSearchTableModel model = (FindAdvancedSearchTableModel)table.getModel();
		final int modelRowIndex = table.convertRowIndexToModel(viewRowIndex);
		return model.getRow(modelRowIndex);
	}
}

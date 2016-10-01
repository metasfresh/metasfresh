package org.compiere.apps.search;

import java.awt.Component;

import javax.swing.JTable;

import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.swing.CComboBox;
import org.compiere.swing.ListComboBoxModel;
import org.compiere.util.DisplayType;

/**
 * Advanced search table - cell editor for Operator cell
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class FindOperatorCellEditor extends FindCellEditor
{
	private static final long serialVersionUID = 6655568524454256089L;

	private CComboBox<Operator> _editor = null;

	private final ListComboBoxModel<Operator> modelForLookupColumns = new ListComboBoxModel<>(MQuery.Operator.operatorsForLookups);
	private final ListComboBoxModel<Operator> modelForYesNoColumns = new ListComboBoxModel<>(MQuery.Operator.operatorsForBooleans);
	private final ListComboBoxModel<Operator> modelDefault = new ListComboBoxModel<>(MQuery.Operator.values());
	private final ListComboBoxModel<Operator> modelEmpty = new ListComboBoxModel<>();

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
	protected CComboBox<Operator> getEditor()
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
		final CComboBox<Operator> editor = getEditor();

		final IUserQueryRestriction row = getRow(table, viewRowIndex);
		final FindPanelSearchField searchField = FindPanelSearchField.castToFindPanelSearchField(row.getSearchField());

		if (searchField != null)
		{
			// check if the column is columnSQL with reference (08757)
			// final String columnName = searchField.getColumnName();
			final int displayType = searchField.getDisplayType();
			final boolean isColumnSQL = searchField.isVirtualColumn();
			final boolean isReference = searchField.getAD_Reference_Value_ID() > 0;

			if (isColumnSQL && isReference)
			{
				// make sure also the columnSQLs with reference are only getting the ID operators (08757)
				editor.setModel(modelForLookupColumns);
			}
			else if (DisplayType.isAnyLookup(displayType))
			{
				editor.setModel(modelForLookupColumns);
			}
			else if (DisplayType.YesNo == displayType)
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

package org.compiere.apps.search;

import java.awt.Component;
import java.util.Map;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.util.Services;
import org.compiere.apps.search.IUserQueryRestriction.Join;
import org.compiere.model.X_AD_WF_NextCondition;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CEditor;
import org.compiere.swing.ToStringListCellRenderer;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableMap;

/**
 * Advanced search table - cell renderer and editor for AND/OR join option.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class FindJoinCellEditor extends FindCellEditor implements TableCellRenderer
{
	private static final long serialVersionUID = 4115046751452112894L;

	private static final int JoinAndOr_AD_Reference_ID = X_AD_WF_NextCondition.ANDOR_AD_Reference_ID;

	private final DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

	private CComboBox<Join> _editor;

	private final Map<Join, String> join2displayName;

	public FindJoinCellEditor()
	{
		super();

		// Load translation
		final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
		final Properties ctx = Env.getCtx();
		join2displayName = ImmutableMap.<Join, String> builder()
				.put(Join.AND, adReferenceDAO.retrieveListNameTrl(ctx, JoinAndOr_AD_Reference_ID, X_AD_WF_NextCondition.ANDOR_And))
				.put(Join.OR, adReferenceDAO.retrieveListNameTrl(ctx, JoinAndOr_AD_Reference_ID, X_AD_WF_NextCondition.ANDOR_Or))
				.build();
	}

	@Override
	protected CEditor getEditor()
	{
		if (_editor == null)
		{
			_editor = new CComboBox<>();
			_editor.setRenderer(new ToStringListCellRenderer<Join>()
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected String renderToString(Join value)
				{
					return getDisplayValue(value);
				}
			});
			// make sure we are setting the model AFTER we set the renderer because else,
			// the value from combobox editor will not be the one that is returned by rendered
			_editor.setModel(new DefaultComboBoxModel<>(Join.values()));
			_editor.enableAutoCompletion();
		}
		return _editor;
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
	{
		final String displayValue = getDisplayValue(value);
		return defaultRenderer.getTableCellRendererComponent(table, displayValue, isSelected, hasFocus, row, column);
	}

	private final String getDisplayValue(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		if (value instanceof Join)
		{
			return join2displayName.get(value);
		}
		else
		{
			return value.toString();
		}
	}

}

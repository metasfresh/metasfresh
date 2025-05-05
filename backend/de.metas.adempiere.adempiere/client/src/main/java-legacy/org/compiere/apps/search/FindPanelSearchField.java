package org.compiere.apps.search;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.ad_reference.ReferenceId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VString;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldLayoutConstraints;
import org.compiere.model.GridFieldVO;
import org.compiere.model.Lookup;
import org.compiere.swing.CLabel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a field which can be searched in {@link FindPanel}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public final class FindPanelSearchField implements IUserQueryField
{
	public static Map<String, FindPanelSearchField> createMapIndexedByColumnName(final GridField[] gridFields)
	{
		final Map<String, FindPanelSearchField> searchFields = new HashMap<>();
		for (final GridField gridField : gridFields)
		{
			// skip fields for included tab because there is nothing to search about them
			if (gridField.getIncluded_Tab_ID() > 0)
			{
				continue;
			}

			// skip fields which were hidden in UI (task 09504)
			// NOTE: we don't check for isDisplayable() because there are some fields which are not displayable but we want to search by them (e.g. CreatedBy).
			if (gridField.getVO().isHiddenFromUI())
			{
				continue;
			}

			// skip true buttons... nothing to search by
			if (gridField.getDisplayType() == DisplayType.Button && gridField.getAD_Reference_Value_ID() == null)
			{
				continue;
			}

			final FindPanelSearchField searchField = new FindPanelSearchField(gridField);
			searchFields.put(searchField.getColumnName(), searchField);
		}

		return searchFields;
	}

	public static FindPanelSearchField castToFindPanelSearchField(final Object field)
	{
		return (FindPanelSearchField)field;
	}

	public static boolean isSelectionColumn(final IUserQueryField field)
	{
		if (field instanceof FindPanelSearchField)
		{
			return ((FindPanelSearchField)field).isSelectionColumn();
		}
		return false;
	}

	/**
	 * Maximum allowed number of columns for a text field component displayed in simple search tab
	 */
	static final int MAX_TEXT_FIELD_COLUMNS = 20;

	// services
	private final transient ISwingEditorFactory swingEditorFactory = Services.get(ISwingEditorFactory.class);

	/**
	 * Reference ID for Yes/No
	 */
	private static final ReferenceId AD_REFERENCE_ID_YESNO = ReferenceId.ofRepoId(319);

	private final GridField gridField;
	private ITranslatableString _displayName;
	private final String adLanguage;

	private FindPanelSearchField(final GridField gridField)
	{
		super();
		Check.assumeNotNull(gridField, "gridField not null");

		final GridFieldVO vo = gridField.getVO().copy();
		vo.setMandatory(false);
		vo.setIsReadOnly(false);
		vo.setIsUpdateable(true);

		// Make sure the field is flagged as displayed. Else, the lookup will not be loaded.
		vo.setIsDisplayed(true);
		vo.setIsDisplayedGrid(true);

		// Make sure the field is NOT marked as label only or field only because it might be that the editor or label won't be created
		vo.setIsHeadingOnly(false);
		vo.setIsFieldOnly(false);

		// Enforce maximum text field columns
		final GridFieldLayoutConstraints layoutConstraints = vo.getLayoutConstraints();
		if (layoutConstraints.getColumnDisplayLength() > MAX_TEXT_FIELD_COLUMNS)
		{
			layoutConstraints.setDisplayLength(MAX_TEXT_FIELD_COLUMNS);
		}

		// Make Yes-No search-able as list
		final int displayType = vo.getDisplayType();
		if (displayType == DisplayType.YesNo)
		{
			vo.setDisplayType(DisplayType.List);
			vo.setAD_Reference_Value_ID(AD_REFERENCE_ID_YESNO);
		}
		// Make Button (with list) search-able as list
		else if (displayType == DisplayType.Button && gridField.getAD_Reference_Value_ID() != null)
		{
			vo.setDisplayType(DisplayType.List);
		}
		// Display the Key fields as numbers, so user can search by IDs too
		else if (vo.IsKey)
		{
			vo.setDisplayType(DisplayType.Number);
		}
		// TODO: handle the case of lookup fields with validation rules

		this.gridField = new GridField(vo);

		this.adLanguage = Env.getAD_Language(Env.getCtx());
	}

	@Override
	public String toString()
	{
		// VERY IMPORTANT: if we are not doing this, using this class in a CComboBox with auto-completion enabled will fail!
		return getDisplayName().translate(adLanguage);
	}

	@Override
	public String getColumnName()
	{
		return gridField.getColumnName();
	}

	public boolean isSelectionColumn()
	{
		return gridField.isSelectionColumn();
	}

	@Override
	public ITranslatableString getDisplayName()
	{
		if (_displayName == null)
		{
			String header = gridField.getHeader();
			if (Check.isEmpty(header, true))
			{
				final String columnName = gridField.getColumnName();
				header = Services.get(IMsgBL.class).translate(Env.getCtx(), columnName);
				if (Check.isEmpty(header, true))
				{
					header = columnName;
				}
			}
			if (gridField.isKey())
			{
				header += " (ID)";
			}

			_displayName = TranslatableStrings.constant(header);
		}

		return _displayName;
	}

	public String getDisplayNameTrl()
	{
		return getDisplayName().translate(adLanguage);
	}

	@Override
	public String getColumnSQL()
	{
		final boolean withAS = false;
		return gridField.getColumnSQL(withAS);
	}

	public int getDisplayType()
	{
		return gridField.getDisplayType();
	}

	public ReferenceId getAD_Reference_Value_ID()
	{
		return gridField.getAD_Reference_Value_ID();
	}

	public boolean isLookup()
	{
		return gridField.isLookup();
	}

	public Lookup getLookup()
	{
		return gridField.getLookup();
	}

	public boolean isVirtualColumn()
	{
		return gridField.isVirtualColumn();
	}

	/**
	 * Creates the editor component
	 *
	 * @param tableEditor true if table editor
	 * @return editor or null if editor could not be created
	 */
	public VEditor createEditor(final boolean tableEditor)
	{
		// Reset lookup state
		// background: the lookup implements MutableComboBoxModel which stores the selected item.
		// If this lookup was previously used somewhere, the selected item is retained from there and we will get unexpected results.
		final Lookup lookup = gridField.getLookup();
		if (lookup != null)
		{
			lookup.setSelectedItem(null);
		}

		//
		// Create a new editor
		VEditor editor = swingEditorFactory.getEditor(gridField, tableEditor);
		if (editor == null && tableEditor)
		{
			editor = new VString();
		}

		//
		// Configure the new editor
		if (editor != null)
		{
			editor.setMandatory(false);
			editor.setReadWrite(true);
		}

		return editor;
	}

	public CLabel createEditorLabel()
	{
		return swingEditorFactory.getLabel(gridField);
	}

	@Override
	public Object convertValueToFieldType(final Object valueObj)
	{
		return UserQueryFieldHelper.parseValueObjectByColumnDisplayType(valueObj, getDisplayType(), getColumnName());
	}

	@Override
	public String getValueDisplay(final Object value)
	{
		String infoDisplay = value == null ? "" : value.toString();
		if (isLookup())
		{
			final Lookup lookup = getLookup();
			if (lookup != null)
			{
				infoDisplay = lookup.getDisplay(value);
			}
		}
		else if (getDisplayType() == DisplayType.YesNo)
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			infoDisplay = msgBL.getMsg(Env.getCtx(), infoDisplay);
		}

		return infoDisplay;
	}

	@Override
	public boolean matchesColumnName(final String columnName)
	{
		if (columnName == null || columnName.isEmpty())
		{
			return false;
		}

		if (columnName.equals(getColumnName()))
		{
			return true;
		}

		if (gridField.isVirtualColumn())
		{
			if (columnName.equals(gridField.getColumnSQL(false)))
			{
				return true;
			}
			if (columnName.equals(gridField.getColumnSQL(true)))
			{
				return true;
			}
		}

		return false;
	}
}

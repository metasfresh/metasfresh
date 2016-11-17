package org.compiere.apps.search;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VString;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldLayoutConstraints;
import org.compiere.model.GridFieldVO;
import org.compiere.model.I_M_Product;
import org.compiere.model.Lookup;
import org.compiere.swing.CLabel;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;

/**
 * Represents a field which can be searched in {@link FindPanel}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
final class FindPanelSearchField implements IUserQueryField
{
	public static final Map<String, FindPanelSearchField> createMapIndexedByColumnName(final GridField[] gridFields)
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
			if (gridField.getDisplayType() == DisplayType.Button && gridField.getAD_Reference_Value_ID() <= 0)
			{
				continue;
			}

			final FindPanelSearchField searchField = new FindPanelSearchField(gridField);
			searchFields.put(searchField.getColumnName(), searchField);
		}

		return searchFields;
	}
	
	public static final FindPanelSearchField castToFindPanelSearchField(final Object field)
	{
		return (FindPanelSearchField)field;
	}
	
	public static final boolean isSelectionColumn(final IUserQueryField field)
	{
		if(field instanceof FindPanelSearchField)
		{
			return ((FindPanelSearchField)field).isSelectionColumn();
		}
		return false;
	}

	/** Maximum allowed number of columns for a text field component displayed in simple search tab */
	static final int MAX_TEXT_FIELD_COLUMNS = 20;

	// services
	private static final transient Logger logger = LogManager.getLogger(FindPanelSearchField.class);
	private final transient ISwingEditorFactory swingEditorFactory = Services.get(ISwingEditorFactory.class);

	/** Reference ID for Yes/No */
	private static final int AD_REFERENCE_ID_YESNO = 319;

	private final GridField gridField;
	private ITranslatableString _displayName;
	private final String adLanguage;

	private FindPanelSearchField(final GridField gridField)
	{
		super();
		Check.assumeNotNull(gridField, "gridField not null");

		final GridFieldVO vo = gridField.getVO().copy();
		vo.setMandatory(false);
		vo.IsReadOnly = false;
		vo.IsUpdateable = true;

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
		else if (displayType == DisplayType.Button && gridField.getAD_Reference_Value_ID() > 0)
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

			_displayName = ImmutableTranslatableString.constant(header);
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

	public int getAD_Reference_Value_ID()
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

	public int getDisplayLength()
	{
		return gridField.getDisplayLength();
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
		if (valueObj == null)
		{
			return null;
		}
		final int dt = getDisplayType();
		try
		{
			// Return Integer
			if (dt == DisplayType.Integer || DisplayType.isID(dt) && getColumnName().endsWith("_ID"))
			{
				if (valueObj instanceof Integer)
				{
					return valueObj;
				}
				final int i = Integer.parseInt(valueObj.toString());
				return new Integer(i);
			}
			// Return BigDecimal
			else if (DisplayType.isNumeric(dt))
			{
				if (valueObj instanceof BigDecimal)
				{
					return valueObj;
				}
				return DisplayType.getNumberFormat(dt).parse(valueObj.toString());
			}
			// Return Timestamp
			else if (DisplayType.isDate(dt))
			{
				if (valueObj instanceof java.util.Date)
				{
					return TimeUtil.asTimestamp((java.util.Date)valueObj);
				}
				long time = 0;
				try
				{
					time = DisplayType.getDateFormat_JDBC().parse(valueObj.toString()).getTime();
					return new Timestamp(time);
				}
				catch (final Exception e)
				{
					logger.error(valueObj + "(" + valueObj.getClass() + ")" + e);
					time = DisplayType.getDateFormat(dt).parse(valueObj.toString()).getTime();
				}
				return new Timestamp(time);
			}
			// Return Y/N for Boolean
			else if (valueObj instanceof Boolean)
			{
				return DisplayType.toBooleanString((Boolean)valueObj);
			}
		}
		catch (final Exception ex)
		{
			String error = ex.getLocalizedMessage();
			if (error == null || error.isEmpty())
			{
				error = ex.toString();
			}
			final StringBuilder errMsg = new StringBuilder();
			errMsg.append(gridField.getColumnName()).append(" = ").append(valueObj).append(" - ").append(error);
			//
			logger.error("ValidationError: " + errMsg, ex);
			// ADialog.error(0, getFrame(), "ValidationError", errMsg.toString());
			return null;
		}

		return valueObj;
	}

	@Override
	public final String getValueDisplay(final Object value)
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

	public boolean isProductCategoryField()
	{
		return I_M_Product.COLUMNNAME_M_Product_Category_ID.equals(getColumnName());
	}

	/**
	 * Returns a sql where string with the given category id and all of its subcategory ids. It is used as restriction in MQuery.
	 *
	 * @param columnSQL
	 * @param productCategoryId
	 * @return
	 */
	public String getSubCategoryWhereClause(final int productCategoryId)
	{
		final String columnSQL = getColumnSQL();
		// if a node with this id is found later in the search we have a loop in the tree
		int subTreeRootParentId = 0;
		final StringBuilder retString = new StringBuilder(" (" + columnSQL + ") IN (");

		final String sql = " SELECT M_Product_Category_ID, M_Product_Category_Parent_ID FROM M_Product_Category";
		final List<SimpleTreeNode> categories = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = DB.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				if (rs.getInt(1) == productCategoryId)
				{
					subTreeRootParentId = rs.getInt(2);
				}
				categories.add(new SimpleTreeNode(rs.getInt(1), rs.getInt(2)));
			}
			retString.append(getSubCategoriesString(productCategoryId, categories, subTreeRootParentId));
			retString.append(") ");
		}
		catch (final SQLException e)
		{
			logger.error(sql, e);
			return "";
		}
		catch (final AdempiereException e)
		{
			logger.error(sql, e);
			return "";
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null;
			stmt = null;
		}
		return retString.toString();
	}

	/**
	 * Recursive search for subcategories with loop detection.
	 *
	 * @param productCategoryId
	 * @param categories
	 * @param loopIndicatorId
	 * @return comma separated list of category ids
	 * @throws AdempiereException if a loop is detected
	 */
	private static String getSubCategoriesString(final int productCategoryId, final List<SimpleTreeNode> categories, final int loopIndicatorId)
	{
		String ret = "";
		final Iterator<SimpleTreeNode> iter = categories.iterator();
		while (iter.hasNext())
		{
			final SimpleTreeNode node = iter.next();
			if (node.getParentId() == productCategoryId)
			{
				if (node.getNodeId() == loopIndicatorId)
				{
					throw new AdempiereException("The product category tree contains a loop on categoryId: " + loopIndicatorId);
				}
				ret = ret + getSubCategoriesString(node.getNodeId(), categories, loopIndicatorId) + ",";
			}
		}
		logger.debug(ret);
		return ret + productCategoryId;
	}

	/**
	 * Simple tree node class for product category tree search.
	 *
	 * @author Karsten Thiemann, kthiemann@adempiere.org
	 *
	 */
	private static final class SimpleTreeNode
	{
		private final int nodeId;
		private final int parentId;

		public SimpleTreeNode(final int nodeId, final int parentId)
		{
			this.nodeId = nodeId;
			this.parentId = parentId;
		}

		public int getNodeId()
		{
			return nodeId;
		}

		public int getParentId()
		{
			return parentId;
		}
	}
}

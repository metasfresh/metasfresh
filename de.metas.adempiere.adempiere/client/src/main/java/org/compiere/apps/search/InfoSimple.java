package org.compiere.apps.search;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.table.TableModel;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.service.IADInfoWindowBL;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.search.history.IInvoiceHistoryTabHandler;
import org.compiere.apps.search.history.impl.InvoiceHistory;
import org.compiere.apps.search.history.impl.InvoiceHistoryContext;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.tree.VTreePanel;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupFactory.LanguageInfo;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.MTreeNode;
import org.compiere.model.M_Element;
import org.compiere.swing.CLabel;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;

import net.miginfocom.swing.MigLayout;

/**
 * @author cg
 *
 */
public class InfoSimple extends Info
		implements IInfoSimple
{
	private static final long serialVersionUID = -2287443834003089204L;

	//
	// Services
	private final transient IADInfoWindowDAO adInfoWindowDAO = Services.get(IADInfoWindowDAO.class);
	private final transient IADInfoWindowBL adInfoWindowBL = Services.get(IADInfoWindowBL.class);
	private final transient IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private boolean isJoinClauseAnd = true;

	private I_AD_InfoWindow infoWindow = null;
	private String searchText;

	/**
	 * AD_InfoColumn_ID to IInfoQueryCriteria
	 */
	private final Map<Integer, IInfoQueryCriteria> criteriasById = new HashMap<Integer, IInfoQueryCriteria>();
	private final Map<String, IInfoQueryCriteria> criteriasBySelectClause = new HashMap<String, IInfoQueryCriteria>();
	/**
	 * Displayed parameters (in order they are displayed)
	 */
	private final List<IInfoQueryCriteria> displayedParameters = new ArrayList<IInfoQueryCriteria>();
	private List<I_AD_InfoColumn> displayedInfoColumns = null;

	/**
	 * hash map for attributes
	 */
	private final Map<String, Object> attributes = new HashMap<String, Object>();

	/**
	 * Value of this attribute will contain the text that was searched in field, before this window was opened.
	 *
	 * To be used in AD_InfoColumn.DefaultValue like "@SearchText@" to flag on which query parameter we shall set the searched text.
	 */
	public static final String ATTR_SearchText = "SearchText";

	private class EvaluableCtx extends Properties implements Evaluatee
	{
		private static final long serialVersionUID = -752761216437290368L;

		public EvaluableCtx(final Properties ctx)
		{
			super(ctx);
		}

		@Override
		public synchronized Object get(final Object key)
		{
			final String name = getAttributeName(key);
			if (name != null && attributes.containsKey(name))
			{
				return attributes.get(key);
			}
			return super.get(key);
		}

		private String getAttributeName(final Object key)
		{
			if (!(key instanceof String))
			{
				return null;
			}
			final int p_WindowNo = getWindowNo();
			final String windowStr = p_WindowNo + "|";
			final String keyStr = (String)key;

			if (!keyStr.startsWith(windowStr))
			{
				return null;
			}
			final String columnName = keyStr.substring(windowStr.length()).trim();
			return columnName;
		}

		@Override
		public String getProperty(final String key)
		{
			final String name = getAttributeName(key);
			if (name != null && attributes.containsKey(name))
			{
				final Object value = attributes.get(name);
				return value == null ? "" : value.toString();
			}
			return super.getProperty(key);
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			final int p_WindowNo = getWindowNo();
			return Env.getContext(this, p_WindowNo, variableName);
		}
	};

	/**
	 * Context which first will check {@link #attributes} and then if nothing found will return from initial context
	 */
	private final EvaluableCtx ctx = new EvaluableCtx(Env.getCtx());

	// metas: product category tree
	private MTreeNode treeSelectedNode = null;
	private final PropertyChangeListener treeNodeSelectedListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			final MTreeNode nd = (MTreeNode)evt.getNewValue();
			log.info(nd.getNode_ID() + " - " + nd.toString());
			treeSelectedNode = nd;
			executeQuery();
		}
	};

	/**
	 * When window is opened, focus on parameter which contains the {@link #ATTR_SearchText} in it's DefaultValue.
	 */
	private final WindowListener onWindowOpenFocusFirstParameter = new WindowAdapter()
	{
		@Override
		public void windowOpened(final WindowEvent e)
		{
			for (final IInfoQueryCriteria parameter : displayedParameters)
			{
				windowOpened0(parameter);
			}
		}

		private void windowOpened0(final IInfoQueryCriteria parameter)
		{
			final I_AD_InfoColumn infoColumn = parameter.getAD_InfoColumn();
			if (infoColumn == null)
			{
				return; // shall not happen...
			}

			final String defaultValue = infoColumn.getDefaultValue();

			// search for first not null component
			for (int i = 0; i < parameter.getParameterCount(); i++)
			{
				final Component editor = getEditorComponentOrNull(parameter, i);
				if (editor == null)
				{
					continue;
				}

				// add general listeners on parameter components
				editor.addKeyListener(new KeyAdapter()
				{
					@Override
					public void keyReleased(final KeyEvent e)
					{
						if (KeyEvent.VK_ENTER == e.getKeyCode())
						{
							// each field shall execute query on enter
							executeQuery();
						}
					}
				});

				// we are focusing on field which as "SearchText" as default value
				if (defaultValue == null || defaultValue.indexOf(ATTR_SearchText) < 0)
				{
					return;
				}

				editor.requestFocus();

				final String text = parameter.getText();
				if (!Check.isEmpty(text, true))
				{
					executeQueryOnInit();
				}
			}
		}

		private Component getEditorComponentOrNull(final IInfoQueryCriteria parameter, final int i)
		{
			Component editor = (Component)parameter.getParameterComponent(i);
			if (editor == null)
			{
				editor = (Component)parameter.getParameterToComponent(i);
			}
			return editor;
		}
	};

	/**
	 * 
	 * @param frame
	 * @param modal note: reflection code relies on this parameter to be a <b>B</b>oolean, not a primitive boolean
	 */
	// NOTE: keep this constructor public because it's accessed by reflection
	public InfoSimple(final Frame frame, final Boolean modal)
	{
		super(frame, modal);
		getWindow().addWindowListener(onWindowOpenFocusFirstParameter);
	}

	protected void init(final boolean modal,
			final int WindowNo,
			final I_AD_InfoWindow infoWindow,
			final String keyColumn,
			final String value,
			final boolean multiSelection,
			final String whereClause)
	{
		setCtxAttribute(ATTR_SearchText, value);

		super.init(modal, WindowNo,
				adInfoWindowBL.getTableName(infoWindow),
				keyColumn,
				multiSelection,
				whereClause);

		this.infoWindow = infoWindow;
		searchText = value;

		final I_AD_InfoWindow infoWindowTrl = InterfaceWrapperHelper.translate(infoWindow, I_AD_InfoWindow.class);
		setTitle(infoWindowTrl.getName());
		initAttributes();
		initParametersUI();
		initParameters();
		initInfo();
		initTree();
		initTreeUI();
		// metas begin
		// loaded OK
		p_loadedOK = true;
		// window sized to fit the preferred size and layouts of its subcomponents
		getWindow().pack();
		// metas end
	}

	private void initTree()
	{
		// nothing (WTF?)
	}

	private void initTreeUI()
	{
		final int adTreeId = adInfoWindowBL.getAD_Tree_ID(infoWindow);
		if (adTreeId <= 0)
		{
			return;
		}
		
		final VTreePanel treePanel = new VTreePanel(Env.getWindowNo(this), false, false);
		treePanel.setBorder(BorderFactory.createEmptyBorder(2, 3, 2, 3));

		treePanel.initTree(adTreeId);
		treePanel.addPropertyChangeListener(VTreePanel.PROPERTY_ExecuteNode, treeNodeSelectedListener);
		treePanel.setPreferredSize(new Dimension(200, treePanel.getPreferredSize().height));

		setTreePanel(treePanel, 200);
	}

	private void initParametersUI()
	{
		parameterPanel.setLayout(new MigLayout());
	}

	private void initParameters()
	{
		final List<I_AD_InfoColumn> paramInfoColumns = adInfoWindowDAO.retrieveQueryColumns(infoWindow);
		for (final I_AD_InfoColumn col : paramInfoColumns)
		{
			if (!Check.isEmpty(col.getParameterDisplayLogic(), true))
			{
				final ILogicExpression expression = expressionFactory.compileOrDefault(col.getParameterDisplayLogic(), ILogicExpression.FALSE, ILogicExpression.class);

				// don't fail on missing parameter because we want to be compatible with old org.compiere.util.Evaluator.evaluateLogic(Evaluatee, String) method
				final boolean ignoreUnparsable = true;
				if (!expression.evaluate(ctx, ignoreUnparsable))
				{
					// this parameter will never-ever be displayed
					continue;
				}
			}

			if (col.isParameterNextLine() && !criteriasBySelectClause.isEmpty())
			{
				addComponentToParameters(null, true);
			}
			//
			final String selectSql = col.getSelectClause();

			final IInfoQueryCriteria criteria = getInfoQueryCriteria(col, true); // retrieve default if not found
			// Add criteria to map
			{
				String name = selectSql;
				final int i = 1;
				while (criteriasBySelectClause.containsKey(name))
				{
					name = selectSql + "#" + i;
				}
				criteriasBySelectClause.put(name, criteria);
			}

			//
			// Add criteria to Displayed Parameters
			displayedParameters.add(criteria);

			//
			// Iterate criterias and add editor fields to Parameters panel
			for (int i = 0; i < criteria.getParameterCount(); i++)
			{
				final Component editor = (Component)criteria.getParameterComponent(i);
				if (editor instanceof VLookup)
				{
					final VLookup lookup = (VLookup)editor;
					lookup.enableLookupAutocomplete();
				}

				//
				// Label
				final String labelText = criteria.getLabel(i);
				if (!Check.isEmpty(labelText))
				{
					addComponentToParameters(labelText, false);
				}

				//
				// Parameter
				addComponentToParameters(editor, false);

				//
				// Parameter To
				final Component editor2 = (Component)criteria.getParameterToComponent(i);
				if (col.isRange() && editor2 != null)
				{
					addComponentToParameters(" - ", false);
					addComponentToParameters(editor2, false);
				}
			}
		}
	}

	private void addComponentToParameters(final Object component, final boolean newLine)
	{
		final Component c;
		if (component == null)
		{
			c = null;
		}
		else if (component instanceof String)
		{
			c = new CLabel((String)component);
		}
		else if (component instanceof Component)
		{
			c = (Component)component;
		}
		else
		{
			throw new IllegalArgumentException("Component type not supported " + component.getClass());
		}

		if (newLine)
		{
			parameterPanel.add(new CLabel(""), "wrap");
		}
		if (c != null)
		{
			parameterPanel.add(c, "growx, growy");
		}
	}

	private void initInfo()
	{
		displayedInfoColumns = adInfoWindowDAO.retrieveDisplayedColumns(infoWindow);

		final Info_Column[] layout = new Info_Column[displayedInfoColumns.size()];

		//
		// Setup Layout
		int keyColumnIndex = -1;
		for (int i = 0; i < displayedInfoColumns.size(); i++)
		{
			final I_AD_InfoColumn field = displayedInfoColumns.get(i);
			layout[i] = createColumnInfo(field);
			if (layout[i].getColClass() == IDColumn.class && !field.isTree())
			{
				keyColumnIndex = i;
			}
		}

		// No KeyColumnIndex found. Pick the first ID column
		if (keyColumnIndex < 0)
		{
			int count = 0;
			for (final Info_Column infoColumn : layout)
			{
				if (infoColumn.isIDcol())
				{
					keyColumnIndex = count;
					break;
				}
				else
				{
					count++;
				}
			}
		}

		//
		// Setup dependent columns
		// NOTE: we need to do this after we initialized the layout
		for (int i = 0; i < displayedInfoColumns.size(); i++)
		{
			final Info_Column infoColumn = layout[i];
			final IInfoColumnController columnController = infoColumn.getColumnController();
			if (columnController == null)
			{
				continue;
			}
			final List<String> dependsOnColumnNames = columnController.getDependsOnColumnNames();
			if (dependsOnColumnNames == null || dependsOnColumnNames.isEmpty())
			{
				continue;
			}

			for (final String dependsOnColumnName : dependsOnColumnNames)
			{
				final int dependsOnColumnIndex = getIndexByColumnName(displayedInfoColumns, dependsOnColumnName);
				if (dependsOnColumnIndex < 0)
				{
					log.warn("Cannot find column name '" + dependsOnColumnName + "' required as dependency for " + columnController);
					continue;
				}

				final List<Info_Column> dependentColumns = layout[dependsOnColumnIndex].getDependentColumns();
				if (!dependentColumns.contains(infoColumn))
				{
					dependentColumns.add(infoColumn);
				}
			}
		}

		// Create Grid
		final StringBuffer where = new StringBuffer();
		where.append(infoWindow.getOtherClause());
		if (!Check.isEmpty(p_whereClause, true))
		{
			if (!Check.isEmpty(where.toString()))
			{
				where.append(" AND ");
			}
			where.append("(").append(p_whereClause).append(")");
		}

		//
		final String sqlFromClause = adInfoWindowBL.getSqlFrom(infoWindow);
		prepareTable(layout, sqlFromClause, where.toString(), infoWindow.getOrderByClause());
		setKeyColumnIndex(keyColumnIndex);
		setShowTotals(adInfoWindowBL.isShowTotals(infoWindow));

		initAddonPanel();

		//
		// Notify controllers that Info window was initialized
		for (final Info_Column infoColumn : p_layout)
		{
			final IInfoColumnController controller = infoColumn.getColumnController();
			if (controller == null)
			{
				continue;
			}

			controller.afterInfoWindowInit(this);
		}
	} // initInfo

	private Info_Column createColumnInfo(final I_AD_InfoColumn field)
	{
		final Properties ctx = getCtx();

		String columnName = field.getAD_Element().getColumnName();
		String name = msgBL.translate(ctx, columnName);
		final int displayType = field.getAD_Reference_ID();
		Class<?> colClass = DisplayType.getClass(displayType, true);
		String colSQL = Check.isEmpty(field.getDisplayField(), true) ? field.getSelectClause() : field.getDisplayField();
		String idColSQL = null;

		if (DisplayType.isLookup(field.getAD_Reference_ID()))
		{
			final LanguageInfo languageInfo = LanguageInfo.ofSpecificLanguage(ctx);
			colClass = KeyNamePair.class;
			idColSQL = field.getSelectClause();
			final String displayColumnSQL = MLookupFactory.getLookupEmbed(languageInfo,
					colSQL, // BaseColumn
					null, // BaseTable
					field.getAD_Reference_ID(),
					field.getAD_Reference_Value_ID());
			if (!Check.isEmpty(displayColumnSQL, true))
			{
				colSQL = "(" + displayColumnSQL + ")";
			}
		}
		if (DisplayType.List == field.getAD_Reference_ID())
		{
			columnName = field.getName();
		}
		if (columnName.endsWith("_ID") && DisplayType.ID == field.getAD_Reference_ID()) // should be only ID, 02883
		{
			name = " ";
			colClass = IDColumn.class;
		}

		final Info_Column ic = new Info_Column(name, columnName, colClass);
		ic.setDisplayType(displayType);
		ic.setIDcolSQL(idColSQL);
		ic.setColSQL(colSQL);
		ic.setColumnName(columnName);

		//
		// Check/Load Column Controller
		final IInfoColumnController columnController = getInfoColumnControllerOrNull(field);
		if (columnController != null)
		{
			ic.setColumnController(columnController);
			columnController.customize(this, ic);
		}

		return ic;
	}

	public final int getIndexByColumnName(final String columnName)
	{
		return getIndexByColumnName(displayedInfoColumns, columnName);
	}

	private static int getIndexByColumnName(final List<I_AD_InfoColumn> infoColumns, final String columnName)
	{
		for (int i = 0; i < infoColumns.size(); i++)
		{
			final I_AD_InfoColumn ic = infoColumns.get(i);
			final String icColumnName = ic.getColumnName();
			if (columnName.equals(icColumnName))
			{
				return i;
			}
		}
		return -1;

	}

	public final IInfoQueryCriteria getParameterByColumnNameOrNull(final String columnName)
	{
		Check.assumeNotEmpty(columnName, "columnName not null");
		for (final IInfoQueryCriteria param : displayedParameters)
		{
			final I_AD_InfoColumn infoColumn = param.getAD_InfoColumn();
			final String infoColumnName = infoColumn.getColumnName();
			if (columnName.equals(infoColumnName))
			{
				return param;
			}
		}
		return null;
	}

	/**
	 * Set default values from attributes
	 */
	public void initAttributes()
	{
		setCtxAttribute("M_Warehouse_ID", Env.getContextAsInt(ctx, "M_Warehouse_ID"));
		setCtxAttribute("M_Product_Category_ID", Env.getContextAsInt(ctx, "M_Product_Category_ID"));
		setCtxAttribute("M_PriceList_Version_ID", Env.getContextAsInt(ctx, "M_PriceList_Version_ID"));
		setCtxAttribute("M_PriceList_ID", Env.getContextAsInt(ctx, "M_PriceList_ID"));
		if (Check.isEmpty(Env.getContext(ctx, "#IsSOTrx")))
		{
			Env.setContext(ctx, "#IsSOTrx", true);
		}
	}

	protected void initAddonPanel()
	{
		// nothing at this level
	}

	/**
	 * Get where clause constructed from query fields
	 *
	 * @param params
	 * @return
	 */
	protected String getSQLWhere(final List<Object> params)
	{
		final StringBuilder where = new StringBuilder(); // clause form by query fields

		final List<Object> criteriasParams = new ArrayList<Object>();
		final String criteriasWhereClause = getCriteriasSQLWhere(criteriasParams);

		where.append(criteriasWhereClause);
		params.addAll(criteriasParams);

		final StringBuilder whereClause = new StringBuilder(); // general clause
		if (!Check.isEmpty(infoWindow.getOtherClause()) && where.length() > 0)
		{
			whereClause.append(" AND ");
		}

		// category id
		if (treeSelectedNode != null && treeSelectedNode.getNode_ID() != 0)
		{
			// need to iterate category and all subcategories
			if (where.length() > 0)
			{
				where.append(" AND ");
			}
			where.append(" ( ");
			where.append(" ").append(getTableName()).append(".");
			where.append(adInfoWindowBL.getTreeColumnName(infoWindow));
			where.append(" IN (");

			for (final int id : getSubtreeIds(treeSelectedNode))
			{
				where.append(id);
				where.append(',');
			}
			// remote the last ','
			where.setLength(where.length() - 1);
			where.append(")");
			where.append(" ) ");
		}
		//
		if (where.length() > 0)
		{
			whereClause.append(" ( ").append(where).append(" ) ");
		}
		
		final int p_WindowNo = getWindowNo();
		return Env.parseContext(ctx, p_WindowNo, whereClause.toString(), false);
	}

	private String getCriteriasSQLWhere(final List<Object> params)
	{
		final String joinClause = isJoinClauseAnd() ? " AND " : " OR ";
		final StringBuilder where = new StringBuilder();

		final Collection<IInfoQueryCriteria> criterias = criteriasBySelectClause.values();
		for (final IInfoQueryCriteria criteria : criterias)
		{
			final List<Object> criteriaParams = new ArrayList<Object>();
			final String[] whereClauses = criteria.getWhereClauses(criteriaParams);
			if (whereClauses != null && whereClauses.length > 0)
			{
				for (final String criteriaWhere : whereClauses)
				{
					if (IInfoQueryCriteria.WHERECLAUSE_CLEAR_PREVIOUS == criteriaWhere)
					{
						where.delete(0, where.length());
						params.clear();
						continue;
					}
					else if (IInfoQueryCriteria.WHERECLAUSE_STOP == criteriaWhere)
					{
						params.addAll(criteriaParams);
						return where.toString();
					}
					else if (!Check.isEmpty(criteriaWhere, true))
					{
						if (where.length() > 0)
						{
							where.append("\n").append(joinClause);
						}
						where.append(" ( " + criteriaWhere + " ) ");
					}
				}

				params.addAll(criteriaParams);
			}
		}

		return where.toString();
	}

	private Collection<Integer> getSubtreeIds(final MTreeNode category)
	{

		final ArrayList<Integer> result = new ArrayList<Integer>();
		result.add(category.getNode_ID());

		@SuppressWarnings("unchecked")
		final Enumeration<MTreeNode> enChildren = category.children();
		while (enChildren.hasMoreElements())
		{
			result.add(enChildren.nextElement().getNode_ID());
		}
		return result;
	}

	@Override()
	protected final int getAD_InfoWindow_ID()
	{
		if (infoWindow == null)
		{
			return super.getAD_InfoWindow_ID();
		}
		return infoWindow.getAD_InfoWindow_ID();
	}

	@Override
	protected String getSQLSelect()
	{
		final String sqlOrig = super.getSQLSelect();
		final String sql = Env.parseContext(getCtx(), getWindowNo(), sqlOrig, false);
		if (!Check.isEmpty(sql))
		{
			return sql;
		}
		return sqlOrig;
	}

	/**
	 * Called from {@link Info.Worker} only.
	 */
	@Override
	protected String getSQLWhere()
	{
		sqlWhereParams.set(new ArrayList<Object>());
		return getSQLWhere(sqlWhereParams.get());
	}

	/**
	 * This member is used by the {@link Info.Worker}'s thread only.
	 * Still, I make it threadlocal because I don't want to worry about other code/other threads calling it without considering the implications.
	 */
	private final ThreadLocal<List<Object>> sqlWhereParams = new ThreadLocal<List<Object>>();

	/**
	 * Called from {@link Info.Worker} only.
	 */
	@Override
	protected void setParameters(final PreparedStatement pstmt, final boolean IGNORED) throws SQLException
	{
		DB.setParameters(pstmt, sqlWhereParams.get());
	}

	@Override
	public void setCtxAttribute(final String name, final Object value)
	{
		attributes.put(name, value);
	}

	public Object getCtxAttribute(final String name)
	{
		return attributes.get(name);
	}

	public boolean hasCtxAttribute(final String name)
	{
		return attributes.containsKey(name);
	}

	@Override
	public Object getContextVariable(final String name)
	{
		final IInfoQueryCriteria criteria = criteriasBySelectClause.get(name);
		if (criteria != null)
		{
			return criteria.getParameterValue(0, false);
		}
		return attributes.get(name);
	}

	public String getContextVariableAsString(final String name)
	{
		final Object value = getContextVariable(name);
		return value == null ? "" : value.toString();
	}

	@Override
	public int getContextVariableAsInt(final String name)
	{
		final Object value = getContextVariable(name);
		if (value == null)
		{
			return -1;
		}
		else if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		else if (value instanceof KeyNamePair)
		{
			final KeyNamePair knp = (KeyNamePair)value;
			if (knp.getKey() <= 0 && Check.isEmpty(knp.getName()))
			{
				return -1;
			}
			else
			{
				return knp.getKey();
			}
		}
		else
		{
			try
			{
				return Integer.parseInt(value.toString());
			}
			catch (final Exception e)
			{
				log.warn("Cannot convert " + value + " to integer", e);
			}
		}
		return -1;
	}

	/**
	 * Zoom
	 */
	@Override
	protected void zoom()
	{
		log.info("");
		final Integer ID = getSelectedRowKey();
		if (ID == null)
		{
			return;
		}
		final String p_tableName = getTableName();
		final MQuery query = new MQuery(p_tableName);
		query.addRestriction(p_tableName + "_ID", Operator.EQUAL, ID);
		query.setRecordCount(1);
		final int AD_WindowNo = getAD_Window_ID(p_tableName, true);
		zoom(AD_WindowNo, query);
	} // zoom

	/**
	 * Show History
	 */
	@Override
	protected void showHistory()
	{
		log.info("");
		final Integer ID = getSelectedRowKey();
		if (ID == null)
		{
			return;
		}
		//
		int C_BPartner_ID = 0;
		int M_Product_ID = 0;
		final String p_tableName = getTableName();
		if (I_M_Product.Table_Name.equals(p_tableName))
		{
			M_Product_ID = ID;
		}
		else if (I_C_BPartner.Table_Name.equals(p_tableName))
		{
			C_BPartner_ID = ID;
		}
		//
		int M_Warehouse_ID = getContextVariableAsInt("M_Warehouse_ID");
		if (M_Warehouse_ID == -1)
		{
			M_Warehouse_ID = 0;
		}
		//
		int M_AttributeSetInstance_ID = getContextVariableAsInt("M_AttributeSetInstance_ID");
		if (M_AttributeSetInstance_ID == -1)
		{
			M_AttributeSetInstance_ID = 0;
		}

		final InvoiceHistoryContext ihCtx = InvoiceHistoryContext.builder()
				.setC_BPartner_ID(C_BPartner_ID)
				.setM_Product_ID(M_Product_ID)
				.setM_Warehouse_ID(M_Warehouse_ID)
				.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID)
				.build();

		final IInvoiceHistoryTabHandler invoiceHistoryTabHandler = ihCtx.getInvoiceHistoryTabHandler();
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_PRICEHISTORY, true);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_RESERVED, true);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_ORDERED, true);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_UNCONFIRMED, true);

		// task 08777: was true; setting to false because currently that tab is a performance nightmare and it's rarely used; TODO re-enable with task 08881
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_ATP, false);

		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_RECEIVED, false);
		invoiceHistoryTabHandler.setTabEnabled(InvoiceHistory.TAB_DELIVERED, false);

		//
		InvoiceHistory ih = new InvoiceHistory(ihCtx, getWindow());
		//
		ih.setVisible(true);
		ih = null;
	} // showHistory

	/**
	 * Has History
	 *
	 * @return true (has history)
	 */
	@Override
	protected boolean hasHistory()
	{
		return true;
	} // hasHistory

	/**
	 * Has Zoom
	 *
	 * @return (has zoom)
	 */
	@Override
	protected boolean hasZoom()
	{
		return true;
	} // hasZoom

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public void setJoinClauseAnd(final boolean isAND)
	{
		isJoinClauseAnd = isAND;
	}

	public boolean isJoinClauseAnd()
	{
		return isJoinClauseAnd;
	}

	protected void setShowTotals(final boolean showTotals)
	{
		p_table.setShowTotals(showTotals);
	}

	public TableModel getTableModel()
	{
		return p_table.getModel();
	}

	@Override
	public <T extends IInfoColumnController> T getInfoColumnControllerOrNull(final String columnName, final Class<T> controllerClass)
	{
		Check.assumeNotEmpty(columnName, "columnName not empty");
		Check.assumeNotNull(controllerClass, "controllerClass not empty");

		final int column = getColumnIndex(columnName);
		if (column < 0)
		{
			return null;
		}

		final Info_Column infoColumn = p_layout[column];
		final IInfoColumnController controller = infoColumn.getColumnController();
		if (controller == null)
		{
			return null;
		}

		if (!controllerClass.isAssignableFrom(controller.getClass()))
		{
			return null;
		}

		return controllerClass.cast(controller);
	}

	@Override
	public <T> T getValue(final int row, final String columnName)
	{
		final int column = getColumnIndex(columnName);
		if (column < 0)
		{
			throw new AdempiereException("Column " + columnName + " was not found");
		}

		final Object valueObj = getValue(row, column);

		@SuppressWarnings("unchecked")
		final T value = (T)valueObj;
		return value;
	}

	public int getValueAsInt(final int row, final String columnName)
	{
		final Object value = getValue(row, columnName);
		if (value == null)
		{
			return -1;
		}
		else if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		else if (value instanceof KeyNamePair)
		{
			return ((KeyNamePair)value).getKey();
		}
		else if (value instanceof IDColumn)
		{
			return ((IDColumn)value).getRecord_ID();
		}
		else
		{
			log.warn("Cannot convert value " + value + " (" + value.getClass() + ") to int (row=" + row + ", columnName=" + columnName + ")");
			return -1;
		}
	}

	public Object getValue(final int row, final int column)
	{
		return p_table.getValueAt(row, column);
	}

	public int getKeyValueInt(final int row)
	{
		final int column = getKeyColumnIndex();
		if (column < 0)
		{
			log.warn("No key column index found");
			return -1;
		}
		final Object data = getValue(row, column);
		if (data instanceof IDColumn)
		{
			final IDColumn dataColumn = (IDColumn)data;
			return dataColumn.getRecord_ID();
		}
		else
		{
			log.error("For multiple selection, IDColumn should be key column for selection");
			return -1;
		}
	}

	private final int getColumnIndex(final String columnName)
	{
		final int elementId = getAD_Element_ID(columnName);

		for (int i = 0; i < displayedInfoColumns.size(); i++)
		{
			final I_AD_InfoColumn ic = displayedInfoColumns.get(i);
			if (elementId == ic.getAD_Element_ID())
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * Local cache
	 */
	private final Map<String, Integer> columnName2elementId = new HashMap<String, Integer>();

	private int getAD_Element_ID(final String columnName)
	{
		if (Check.isEmpty(columnName, true))
		{
			return -1;
		}

		//
		// Search from local cache first
		Integer elementId = columnName2elementId.get(columnName);
		if (elementId != null)
		{
			return elementId;
		}

		final I_AD_Element element = M_Element.get(getCtx(), columnName);
		if (element == null)
		{
			return -1;
		}
		elementId = element.getAD_Element_ID();
		columnName2elementId.put(columnName, elementId);
		return elementId;
	}

	public int getColumnIndex(final Info_Column infoColumn)
	{
		if (p_layout == null)
		{
			return -1;
		}
		for (int i = 0; i < p_layout.length; i++)
		{
			if (p_layout[i] == infoColumn)
			{
				return i;
			}
		}

		return -1;
	}

	public boolean isSelected(final int row)
	{
		final int keyColumnIndex = getKeyColumnIndex();
		if (keyColumnIndex < 0)
		{
			log.warn("No key column index found");
			return false;
		}
		final Object value = getValue(row, keyColumnIndex);
		if (value instanceof IDColumn)
		{
			final IDColumn dataColumn = (IDColumn)value;
			return dataColumn.isSelected();
		}
		else
		{
			log.error("For multiple selection, IDColumn should be key column for selection");
			return false;
		}
	}

	public int getRowCount()
	{
		return p_table.getRowCount();
	}

	private IInfoQueryCriteria getInfoQueryCriteria(final I_AD_InfoColumn infoColumn, final boolean getDefaultIfNotFound)
	{
		final int infoColumnId = infoColumn.getAD_InfoColumn_ID();
		if (criteriasById.containsKey(infoColumnId))
		{
			return criteriasById.get(infoColumnId);
		}

		final IInfoQueryCriteria criteria;
		final String classname = infoColumn.getClassname();
		if (Check.isEmpty(classname, true))
		{
			if (getDefaultIfNotFound)
			{
				criteria = new InfoQueryCriteriaGeneral();
			}
			else
			{
				return null;
			}
		}
		else
		{
			criteria = Util.getInstance(IInfoQueryCriteria.class, classname);
		}

		criteria.init(this, infoColumn, searchText);
		criteriasById.put(infoColumnId, criteria);

		return criteria;
	}

	private IInfoColumnController getInfoColumnControllerOrNull(final I_AD_InfoColumn infoColumn)
	{
		final IInfoQueryCriteria criteria = getInfoQueryCriteria(infoColumn, false); // don't retrieve the default
		if (criteria == null)
		{
			return null;
		}
		if (criteria instanceof IInfoColumnController)
		{
			final IInfoColumnController columnController = (IInfoColumnController)criteria;
			return columnController;
		}

		return null;
	}

	@Override
	protected void prepareTable(final Info_Column[] layout, final String from, final String staticWhere, final String orderBy)
	{
		super.prepareTable(layout, from, staticWhere, orderBy);
		setupInfoColumnControllerBindings();
	}

	private void setupInfoColumnControllerBindings()
	{
		for (int i = 0; i < p_layout.length; i++)
		{
			final int columnIndexModel = i;
			final Info_Column infoColumn = p_layout[columnIndexModel];
			final IInfoColumnController columnController = infoColumn.getColumnController();
			final List<Info_Column> dependentColumns = infoColumn.getDependentColumns();

			if (columnController == null
					&& (dependentColumns == null || dependentColumns.isEmpty()))
			{
				// if there is no controller on this column and there are no dependent columns
				// then there is no point for adding a binder
				continue;
			}

			final TableModel tableModel = getTableModel();
			final InfoColumnControllerBinder binder = new InfoColumnControllerBinder(this, infoColumn, columnIndexModel);
			tableModel.addTableModelListener(binder);
		}
	}

	@Override
	public void setValue(final Info_Column infoColumn, final int rowIndexModel, final Object value)
	{
		final int colIndexModel = getColumnIndex(infoColumn);
		if (colIndexModel < 0)
		{
			return;
		}

		p_table.setValueAt(value, rowIndexModel, colIndexModel);
	}

	@Override
	public void setValueByColumnName(final String columnName, final int rowIndexModel, final Object value)
	{
		final int colIndexModel = getColumnIndex(columnName);
		if (colIndexModel < 0)
		{
			return;
		}

		p_table.setValueAt(value, rowIndexModel, colIndexModel);
	}
}

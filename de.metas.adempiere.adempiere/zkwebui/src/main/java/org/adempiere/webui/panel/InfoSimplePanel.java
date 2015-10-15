package org.adempiere.webui.panel;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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
import java.util.logging.Level;

import org.adempiere.ad.service.IADInfoWindowBL;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.ADTreeOnDropListener;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WEditor;
import org.compiere.apps.search.IInfoColumnController;
import org.compiere.apps.search.IInfoQueryCriteria;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.apps.search.Info_Column;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.MBPartner;
import org.compiere.model.MProduct;
import org.compiere.model.MQuery;
import org.compiere.model.MTreeNode;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Vbox;

/**
 * @author cg
 * 
 */
public class InfoSimplePanel extends InfoPanel
		implements IInfoSimple
{

	private static final long serialVersionUID = -2287443834003089204L;

	protected int INDEX_NAME = 1;

	private String s_FROM = null;

	private String s_OrderBy = null;

	private boolean checkAND = true;

	private I_AD_InfoWindow infoWindow = null;

	private static Info_Column s_Layout[];

	private Borderlayout borderlayout;

	protected Center plugPanel = new Center();

	protected Vbox southBody = new Vbox();

	private Map<String, IInfoQueryCriteria> criteriaList = new HashMap<String, IInfoQueryCriteria>();

	private final Map<String, Object> attributes = new HashMap<String, Object>();

	private Properties ctx = new Properties(Env.getCtx())
	{
		private static final long serialVersionUID = -752761216437290368L;

		@Override
		public synchronized Object get(Object key)
		{
			String name = getAttributeName(key);
			if (name != null && attributes.containsKey(name))
				return attributes.get(key);
			return super.get(key);
		}

		private String getAttributeName(Object key)
		{
			if (!(key instanceof String))
				return null;
			String windowStr = p_WindowNo + "|";
			String keyStr = (String)key;

			if (!keyStr.startsWith(windowStr))
			{
				return null;
			}
			String columnName = keyStr.substring(windowStr.length()).trim();
			return columnName;
		}

	};

	// metas: product category tree
	private ADTreeOnDropListener treePanel;
	private MTreeNode treeSelectedNode = null;
	private PropertyChangeListener treeNodeSelectedListener = new PropertyChangeListener()
	{
		

		public void propertyChange(PropertyChangeEvent evt)
		{

			MTreeNode nd = (MTreeNode)evt.getNewValue();
			log.info(nd.getNode_ID() + " - " + nd.toString());
			treeSelectedNode = nd;
			executeQuery();
			// InfoProduct.this.getSize()
		}
	};

	
	public InfoSimplePanel()
	{
		super();
		init();
	}

	public void init(int WindowNo, I_AD_InfoWindow infoWindow, String keyColumn, String value, boolean multiSelection,
			String whereClause, boolean lookup)
	{
		super.init(WindowNo,
				Services.get(IADInfoWindowBL.class).getTableName(infoWindow),
				keyColumn, multiSelection, whereClause, lookup);
		this.infoWindow = infoWindow;
		String treeName = Services.get(IADInfoWindowBL.class).getTreeColumnName(infoWindow);
		if (treeName != null && !Check.isEmpty(treeName, true))
		{
			INDEX_NAME = 2;
		}
		s_FROM = infoWindow.getFromClause();
		s_OrderBy = infoWindow.getOrderByClause();
		s_Layout = new Info_Column[Services.get(IADInfoWindowDAO.class).retrieveDisplayedColumns(infoWindow).size()];
		setTitle(infoWindow.getName());
		initAttributes();
		//
		initSearchFields(value);
		//
		Center center = new Center();
		borderlayout.appendChild(center);
		center.setFlex(true);
		Div div = new Div();
		div.appendChild(contentPanel);
		if (isLookup())
			contentPanel.setWidth("99%");
		else
			contentPanel.setStyle("width: 99%; margin: 0px auto;");
		contentPanel.setVflex(true);
		div.setStyle("width :100%; height: 100%");
		center.appendChild(div);
		//
		initAddonPanel();
		//
		South south = new South();
		borderlayout.appendChild(south);

		Borderlayout layout = new Borderlayout();
		south.appendChild(layout);
		layout.setHeight("200px");
		//
		North northPanel = new North();
		northPanel.appendChild(statusBar);
		layout.appendChild(northPanel);
		//
		layout.appendChild(plugPanel);
		//
		South southPanel = new South();
		layout.appendChild(southPanel);
		// southBody.setWidth("100%");
		southPanel.appendChild(confirmPanel);
		//
		int no = contentPanel.getRowCount();
		initInfo(value);
		setStatusLine(Integer.toString(no) + " " + Msg.getMsg(Env.getCtx(), "SearchRows_EnterQuery"), false);
		setStatusDB(Integer.toString(no));
		executeQuery();
		//
		renderItems();
		p_loadedOK = true;
	}

	private void initSearchFields(String value)
	{
		final List<I_AD_InfoColumn> fields = Services.get(IADInfoWindowDAO.class).retrieveQueryColumns(infoWindow);
		//
		borderlayout = new Borderlayout();
		borderlayout.setWidth("100%");
		borderlayout.setHeight("100%");
		if (!isLookup())
		{
			borderlayout.setStyle("position: absolute");
		}
		this.appendChild(borderlayout);
		//
		Grid grid = GridFactory.newGridLayout();
		North north = new North();
		borderlayout.appendChild(north);
		north.appendChild(grid);
		Rows rows = new Rows();
		grid.appendChild(rows);
		//
		Row ro = new Row();
		rows.appendChild(ro);
		//
		for (I_AD_InfoColumn col : fields)
		{
			if (col.isParameterNextLine() && !criteriaList.isEmpty())
			{
				ro = new Row();
				rows.appendChild(ro);
			}
			//
			final String selectSql = col.getSelectClause();

			final IInfoQueryCriteria criteria;
			final String className =  col.getClassname();
			if (Check.isEmpty(className, true))
			{
				criteria = new InfoQueryCriteriaGeneral();
			}
			else
			{
				criteria = InfoPanelClassLoader.get().newInstance(className, IInfoQueryCriteria.class);
			}
			criteria.init(this, col, value);
			criteriaList.put(selectSql, criteria);

			for (int i = 0; i < criteria.getParameterCount(); i++)
			{
				Component editor = null;
				Component editor2 = null;
				if (criteria.getParameterComponent(i) instanceof Component)
				{
					editor = (Component)criteria.getParameterComponent(i);
					editor2 = (Component)criteria.getParameterToComponent(i);
				}
				else if (criteria.getParameterComponent(i) instanceof WEditor)
				{
					editor = ((WEditor)criteria.getParameterComponent(i)).getComponent();
					editor2 = ((WEditor)criteria.getParameterComponent(i)).getComponent();
				}
				// Label
				{
					final String labelText = criteria.getLabel(i);
					final Label labelComp = new Label();
					if (!Check.isEmpty(labelText))
						labelComp.setText(criteria.getLabel(i));
					ro.appendChild(labelComp);
				}
				// Parameter
				ro.appendChild(editor);
				// Parameter To
				if (col.isRange() && editor2 != null)
				{
					ro.appendChild(new Label(" - "));
					ro.appendChild(editor2);
				}
			}
		}
	}

	/**
	 * Dynamic Init
	 * 
	 * @param value
	 *            value
	 * @param whereClause
	 *            where clause
	 */
	private void initInfo(String value)
	{
		final List<I_AD_InfoColumn> fields = Services.get(IADInfoWindowDAO.class).retrieveDisplayedColumns(infoWindow);
		int k = 0;
		for (I_AD_InfoColumn field : fields)
		{
			String columnName = Check.isEmpty(field.getDisplayField(), true) ? field.getSelectClause() : field.getDisplayField();
			String name = Msg.translate(Env.getCtx(), field.getAD_Element().getColumnName());;
			if (field.getAD_Reference_ID() == DisplayType.List)
				columnName = field.getAD_Element().getColumnName();
			Class<?> colClass = DisplayType.getClass(field.getAD_Reference_ID(), true);
			if (DisplayType.isLookup(field.getAD_Reference_ID()))
				colClass = String.class;
			if (columnName.endsWith("_ID") && DisplayType.isID(field.getAD_Reference_ID())
					&& !DisplayType.isLookup(field.getAD_Reference_ID()))
			{
				name = " ";
				colClass = IDColumn.class;
			}
			s_Layout[k] = new Info_Column(name, columnName, colClass);
			k++;
		}

		// Create Grid
		StringBuffer where = new StringBuffer();
		where.append(infoWindow.getOtherClause());
		if (!Check.isEmpty(p_whereClause, true))
		{
			if (where.length() > 0)
				where.append(" AND ");
			where.append("(").append(p_whereClause).append(")");
		}

		//
		prepareTable(s_Layout, s_FROM, where.toString(), s_OrderBy);
		//p_table.setShowTotals(infoWindow.isShowTotals());

	} // initInfo

	public void initAttributes()
	{
		setCtxAttribute("M_Warehouse_ID", Env.getContextAsInt(ctx, "M_Warehouse_ID"));
		setCtxAttribute("M_Product_Category_ID", Env.getContextAsInt(ctx, "M_Product_Category_ID"));
		setCtxAttribute("M_PriceList_Version_ID", Env.getContextAsInt(ctx, "M_PriceList_Version_ID"));
		setCtxAttribute("M_PriceList_ID", Env.getContextAsInt(ctx, "M_PriceList_ID"));
	}
	

	public void initAddonPanel()
	{

	}

	protected String getSQLWhere(List<Object> params)
	{
		StringBuffer whereClause = new StringBuffer(); // general clause
		StringBuffer where = new StringBuffer(); // clause form by query fields

		// Set the joinClause
		for (IInfoQueryCriteria criteria : criteriaList.values())
		{
			I_AD_InfoColumn col = criteria.getAD_InfoColumn();
			Object value = criteria.getParameterValue(0, false);
			if (col.isAndOr() && !Boolean.TRUE.equals(value))
			{
				checkAND = false;
			}
		}

		final String joinClause = checkAND ? " AND " : " OR ";

		for (IInfoQueryCriteria criteria : criteriaList.values())
		{
			final String[] whereClauses = criteria.getWhereClauses(params);
			if (whereClauses != null && whereClauses.length > 0)
			{
				for (String criteriaWhere : whereClauses)
				{
					if (!Check.isEmpty(criteriaWhere, true))
					{
						if (where.length() > 0)
							where.append(joinClause);
						where.append(" ( " + criteriaWhere + " ) ");
					}
				}
			}
		}

		if (!Check.isEmpty(infoWindow.getOtherClause()) && where.length() > 0)
			whereClause.append(" AND ");

		// category id
		if (treeSelectedNode != null && treeSelectedNode.getNode_ID() != 0)
		{
			// need to iterate category and all subcategories
			if (where.length() > 0)
				where.append(" AND ");
			where.append(" ( ");
			where.append(" ").append(getTableName()).append(".");
			where.append(Services.get(IADInfoWindowBL.class).getTreeColumnName(infoWindow));
			where.append(" IN (");

			for (int id : getSubtreeIds(treeSelectedNode))
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
		checkAND = true;
		return Env.parseContext(ctx, p_WindowNo, whereClause.toString(), false);
	}

	@SuppressWarnings("unchecked")
	private Collection<Integer> getSubtreeIds(final MTreeNode category)
	{

		ArrayList<Integer> result = new ArrayList<Integer>();
		result.add(category.getNode_ID());

		Enumeration<MTreeNode> enChildren = category.children();
		while (enChildren.hasMoreElements())
		{
			result.add(enChildren.nextElement().getNode_ID());
		}
		return result;
	}

	@Override
	protected String getSQLWhere()
	{
		sqlWhereParams = new ArrayList<Object>();
		return getSQLWhere(sqlWhereParams);
	}

	private List<Object> sqlWhereParams = null;

	@Override
	protected void setParameters(PreparedStatement pstmt, boolean forCount) throws SQLException
	{
		DB.setParameters(pstmt, sqlWhereParams);
	}

	@Override
	public void setCtxAttribute(String name, Object value)
	{
		attributes.put(name, value);
	}

	public Object getCtxAttribute(String name)
	{
		return attributes.get(name);
	}

	public boolean hasCtxAttribute(String name)
	{
		return attributes.containsKey(name);
	}

	public Object getContextVariable(String name)
	{
		IInfoQueryCriteria criteria = criteriaList.get(name);
		if (criteria != null)
			return criteria.getParameterValue(0, false);
		return attributes.get(name);
	}

	public String getContextVariableAsString(String name)
	{
		Object value = getContextVariable(name);
		return value == null ? "" : value.toString();
	}

	public int getContextVariableAsInt(String name)
	{
		Object value = getContextVariable(name);
		if (value == null)
			return -1;
		else if (value instanceof Number)
			return ((Number)value).intValue();
		else if (value instanceof KeyNamePair)
		{
			KeyNamePair knp = (KeyNamePair)value;
			if (knp.getKey() <= 0 && Check.isEmpty(knp.getName()))
				return -1;
			else
				return knp.getKey();
		}
		else
		{
			try
			{
				return Integer.parseInt(value.toString());
			}
			catch (Exception e)
			{
				log.log(Level.WARNING, "Cannot convert " + value + " to integer", e);
			}
		}
		return -1;
	}

	/**
	 * Zoom
	 */
	public void zoom()
	{
		log.info("");
		Integer ID = getSelectedRowKey();
		if (ID == null)
			return;
		MQuery query = new MQuery(this.p_tableName);
		query.addRestriction(this.p_tableName + "_ID", MQuery.EQUAL, ID);
		query.setRecordCount(1);
		int AD_WindowNo = getAD_Window_ID(this.p_tableName, true);
		AEnv.zoom(AD_WindowNo, query);
	} // zoom

	/**
	 * Show History
	 */
	protected void showHistory()
	{
		log.info("");
		Integer ID = getSelectedRowKey();
		if (ID == null)
			return;
		//
		int C_BPartner_ID = 0;
		int M_Product_ID = 0;
		if (MProduct.Table_Name.equals(this.p_tableName))
			M_Product_ID = ID;
		else if (MBPartner.Table_Name.equals(this.p_tableName))
			C_BPartner_ID = ID;
		//
		int M_Warehouse_ID = getContextVariableAsInt("M_Warehouse_ID");
		if (M_Warehouse_ID == -1)
			M_Warehouse_ID = 0;
		//
		int M_AttributeSetInstance_ID = getContextVariableAsInt("M_AttributeSetInstance_ID");
		if (M_AttributeSetInstance_ID == -1)
			M_AttributeSetInstance_ID = 0;
		//
		InvoiceHistory ih = new InvoiceHistory(this, C_BPartner_ID,
				M_Product_ID, M_Warehouse_ID, M_AttributeSetInstance_ID);
		//
		ih.setVisible(true);
		ih = null;
	} // showHistory

	/**
	 * Has History
	 * 
	 * @return true (has history)
	 */
	protected boolean hasHistory()
	{
		return true;
	} // hasHistory

	/**
	 * Has Zoom
	 * 
	 * @return (has zoom)
	 */
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
	public int getWindowNo()
	{
		return p_WindowNo;
	}

	@Override
	public void setJoinClauseAnd(boolean isAND)
	{
		checkAND = isAND;
	}

	@Override
	public <T> T getValue(int row, String columnName)
	{
		throw new UnsupportedOperationException(); // FIXME: implement
	}

	@Override
	public int getRecordId(int rowIndexModel)
	{
		throw new UnsupportedOperationException(); // FIXME: implement
	}

	@Override
	public void setValue(Info_Column infoColumn, int rowIndexModel, Object value)
	{
		throw new UnsupportedOperationException(); // FIXME: implement
	}

	@Override
	public void setValueByColumnName(String columnName, int rowIndexModel, Object value)
	{
		throw new UnsupportedOperationException(); // FIXME: implement
	}

	@Override
	public void setIgnoreLoading(boolean ignoreLoading)
	{
		// Nothing to do.
	}

	@Override
	public <T extends IInfoColumnController> T getInfoColumnControllerOrNull(String columnName, Class<T> controllerClass)
	{
		throw new UnsupportedOperationException(); // FIXME: implement
	}
}

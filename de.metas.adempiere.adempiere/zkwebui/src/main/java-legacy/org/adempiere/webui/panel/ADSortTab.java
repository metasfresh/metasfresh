/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.adempiere.webui.panel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListHead;
import org.adempiere.webui.component.ListHeader;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.SimpleListModel;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.GridTab;
import org.compiere.model.GridTabMaxRows;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.NamePair;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zhtml.Span;
import org.zkoss.zk.au.out.AuFocus;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.event.ListDataEvent;

/**
 *	Tab to maintain Order/Sequence
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VSortTab.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 				FR [ 1779410 ] VSortTab: display ID for not visible columns
 *
 * @author victor.perez@e-evolution.com, e-Evolution
 * 				FR [ 2826406 ] The Tab Sort without parent column
 *				<li> https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2826406&group_id=176962
 * Zk Port
 * @author Low Heng Sin
 */
public class ADSortTab extends Panel implements IADTabpanel
{

	private static final long serialVersionUID = 4289328613547509587L;
	private int m_AD_ColumnSortOrder_ID;

	/**
	 *	Sort Tab Constructor
	 *
	 *  @param WindowNo Window No
	 *  @param GridTab
	 */
	public ADSortTab(int WindowNo, GridTab gridTab)
	{
		log.info("SortOrder=" + gridTab.getAD_ColumnSortOrder_ID() + ", SortYesNo=" + gridTab.getAD_ColumnSortYesNo_ID());
		m_WindowNo = WindowNo;
		this.gridTab = gridTab;

		m_AD_Table_ID = gridTab.getAD_Table_ID();
		this.setHeight("100%");
	}	//	VSortTab

	/**	Logger			*/
	static Logger log = LogManager.getLogger(ADSortTab.class);
	private int			m_WindowNo;
	private int			m_AD_Table_ID;
	private String		m_TableName = null;
	private String		m_ColumnSortName= null;
	private String		m_ColumnYesNoName = null;
	private String		m_KeyColumnName = null;
	private String		m_IdentifierSql = null;
	private boolean		m_IdentifierTranslated = false;

	private String		m_ParentColumnName = null;
	private AbstractADWindowPanel adWindowPanel = null;

	//	UI variables
	private Label noLabel = new Label();
	private Label yesLabel = new Label();
	private Button bAdd = new Button();
	private Button bRemove = new Button();
	private Button bUp = new Button();
	private Button bDown = new Button();
	//
	SimpleListModel noModel = new SimpleListModel() {
		/**
		 *
		 */
		private static final long serialVersionUID = -8261235952902938774L;

		@Override
		public void addElement(Object obj) {
			Object[] elements = list.toArray();
			Arrays.sort(elements);
			int index = Arrays.binarySearch(elements, obj);
			if (index < 0)
				index = -1 * index - 1;
			if (index >= elements.length)
				list.add(obj);
			else
				list.add(index, obj);
			fireEvent(ListDataEvent.INTERVAL_ADDED, index, index);
		}
	};
	SimpleListModel yesModel = new SimpleListModel();
	Listbox noList = new Listbox();
	Listbox yesList = new Listbox();

	private GridTab gridTab;
	private boolean uiCreated;

	/**
	 * 	Dynamic Init
	 *  @param AD_Table_ID Table No
	 *  @param AD_ColumnSortOrder_ID Sort Column
	 *  @param AD_ColumnSortYesNo_ID YesNo Column
	 */
	private void dynInit (int AD_Table_ID, int AD_ColumnSortOrder_ID, int AD_ColumnSortYesNo_ID)
	{
		m_AD_Table_ID = AD_Table_ID;
		int identifiersCount = 0;
		StringBuffer identifierSql = new StringBuffer();
		String sql = "SELECT t.TableName, c.AD_Column_ID, c.ColumnName, e.Name,"	//	1..4
			+ "c.IsParent, c.IsKey, c.IsIdentifier, c.IsTranslated "				//	4..8
			+ "FROM AD_Table t, AD_Column c, AD_Element e "
			+ "WHERE t.AD_Table_ID=?"						//	#1
			+ " AND t.AD_Table_ID=c.AD_Table_ID"
			+ " AND (c.AD_Column_ID=? OR AD_Column_ID=?"	//	#2..3
			+ " OR c.IsParent='Y' OR c.IsKey='Y' OR c.IsIdentifier='Y')"
			+ " AND c.AD_Element_ID=e.AD_Element_ID";
		boolean trl = !Env.isBaseLanguage(Env.getCtx(), "AD_Element");
		if (trl)
			sql = "SELECT t.TableName, c.AD_Column_ID, c.ColumnName, et.Name,"	//	1..4
				+ "c.IsParent, c.IsKey, c.IsIdentifier, c.IsTranslated "		//	4..8
				+ "FROM AD_Table t, AD_Column c, AD_Element_Trl et "
				+ "WHERE t.AD_Table_ID=?"						//	#1
				+ " AND t.AD_Table_ID=c.AD_Table_ID"
				+ " AND (c.AD_Column_ID=? OR AD_Column_ID=?"	//	#2..3
				+ "	OR c.IsParent='Y' OR c.IsKey='Y' OR c.IsIdentifier='Y')"
				+ " AND c.AD_Element_ID=et.AD_Element_ID"
				+ " AND et.AD_Language=?";						//	#4
		sql += " ORDER BY c.SeqNo";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Table_ID);
			pstmt.setInt(2, AD_ColumnSortOrder_ID);
			pstmt.setInt(3, AD_ColumnSortYesNo_ID);
			if (trl)
				pstmt.setString(4, Env.getAD_Language(Env.getCtx()));
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				m_TableName = rs.getString(1);
				//	Sort Column
				if (AD_ColumnSortOrder_ID == rs.getInt(2))
				{
					log.debug("Sort=" + rs.getString(1) + "." + rs.getString(3));
					m_ColumnSortName = rs.getString(3);
					yesLabel.setValue(rs.getString(4));
				}
				//	Optional YesNo
				else if (AD_ColumnSortYesNo_ID == rs.getInt(2))
				{
					log.debug("YesNo=" + rs.getString(1) + "." + rs.getString(3));
					m_ColumnYesNoName = rs.getString(3);
				}
				//	Parent2
				else if (rs.getString(5).equals("Y"))
				{
					log.debug("Parent=" + rs.getString(1) + "." + rs.getString(3));
					m_ParentColumnName = rs.getString(3);
				}
				//	KeyColumn
				else if (rs.getString(6).equals("Y"))
				{
					log.debug("Key=" + rs.getString(1) + "." + rs.getString(3));
					m_KeyColumnName = rs.getString(3);
				}
				//	Identifier
				else if (rs.getString(7).equals("Y"))
				{
					log.debug("Identifier=" + rs.getString(1) + "." + rs.getString(3));
					boolean isTranslated = trl && "Y".equals(rs.getString(8));
					if (identifierSql.length() > 0)
						identifierSql.append(",");
					identifierSql.append(isTranslated ? "tt." : "t.").append(rs.getString(3));
					identifiersCount++;
//					m_IdentifierColumnName = rs.getString(3);
					if (isTranslated)
						m_IdentifierTranslated = true;
				}
				else
					log.debug("??NotUsed??=" + rs.getString(1) + "." + rs.getString(3));
			}
		}
		catch (SQLException e)
		{
			log.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		if (identifiersCount == 0)
			m_IdentifierSql = "NULL";
		else if (identifiersCount == 1)
			m_IdentifierSql = identifierSql.toString();
		else
			m_IdentifierSql = identifierSql.insert(0, "COALESCE(").append(")").toString();
		//
		noLabel.setValue(Msg.getMsg(Env.getCtx(), "Available"));
		log.debug(m_ColumnSortName);
	}	//	dynInit

	/**
	 * 	Static Layout
	 * 	@throws Exception
	 */
	private void init() throws Exception
	{
		this.setStyle("height: 100%; width: 100%;");
		//
		noLabel.setValue("No");
		yesLabel.setValue("Yes");

		yesList.setHeight("100%");
		noList.setHeight("100%");
		yesList.setVflex(true);
		noList.setVflex(true);

		EventListener mouseListener = new EventListener()
		{

			public void onEvent(Event event) throws Exception
			{
				if (Events.ON_DOUBLE_CLICK.equals(event.getName()))
				{
					migrateValueAcrossLists(event);
				}
			}
		};
		yesList.addDoubleClickListener(mouseListener);
		noList.addDoubleClickListener(mouseListener);
		//
		EventListener actionListener = new EventListener()
		{
			public void onEvent(Event event) throws Exception {
				migrateValueAcrossLists(event);
			}
		};
		yesList.setSeltype("multiple");
		noList.setSeltype("multiple");

		bAdd.setImage("images/Detail24.png");
		bAdd.addEventListener(Events.ON_CLICK, actionListener);

		bRemove.setImage("images/Parent24.png");
		bRemove.addEventListener(Events.ON_CLICK, actionListener);

		EventListener crossListMouseListener = new DragListener();
		yesList.addOnDropListener(crossListMouseListener);
		noList.addOnDropListener(crossListMouseListener);
		yesList.setItemDraggable(true);
		noList.setItemDraggable(true);

		actionListener = new EventListener()
		{
			public void onEvent(Event event) throws Exception {
				migrateValueWithinYesList(event);
			}
		};

		bUp.setImage("images/Previous24.png");
		bUp.addEventListener(Events.ON_CLICK, actionListener);

		bDown.setImage("images/Next24.png");
		bDown.addEventListener(Events.ON_CLICK, actionListener);

		EventListener yesListMouseMotionListener = new EventListener()
		{
			public void onEvent(Event event) throws Exception {
				if (event instanceof DropEvent)
				{
					DropEvent me = (DropEvent) event;
					ListItem startItem = (ListItem) me.getDragged();
					ListItem endItem = (ListItem) me.getTarget();
					if (startItem.getListbox() == endItem.getListbox() && startItem.getListbox() == yesList)
					{
						int startIndex = yesList.getIndexOfItem(startItem);
						int endIndex = yesList.getIndexOfItem(endItem);
						Object endElement = yesModel.getElementAt(endIndex);
						Object element = yesModel.getElementAt(startIndex);
						yesModel.removeElement(element);
						endIndex = yesModel.indexOf(endElement);
						yesModel.add(endIndex, element);
						yesList.setSelectedIndex(endIndex);
						if ( yesList.getSelectedItem() != null)
						{
							AuFocus focus = new AuFocus(yesList.getSelectedItem());
							Clients.response(focus);
						}
						setIsChanged(true);
					}
				}
			}
		};
		yesList.addOnDropListener(yesListMouseMotionListener);

		ListHead listHead = new ListHead();
		listHead.setParent(yesList);
		ListHeader listHeader = new ListHeader();
		listHeader.appendChild(yesLabel);
		listHeader.setParent(listHead);

		listHead = new ListHead();
		listHead.setParent(noList);
		listHeader = new ListHeader();
		listHeader.appendChild(noLabel);
		listHeader.setParent(listHead);

		Span span = new Span();
		span.setParent(this);
		span.setStyle("height: 99%; display: inline-block; width: 40%;");
		span.appendChild(noList);
		Vbox vbox = new Vbox();
		vbox.appendChild(bAdd);
		vbox.appendChild(bRemove);
		span = new Span();
		span.setParent(this);
		span.setStyle("height: 99%; display: inline-block; width: 46px");
		span.appendChild(vbox);

		span = new Span();
		span.setParent(this);
		span.setStyle("height: 99%; display: inline-block; width: 40%");
		span.appendChild(yesList);
		vbox = new Vbox();
		vbox.appendChild(bUp);
		vbox.appendChild(bDown);
		span = new Span();
		span.setParent(this);
		span.setStyle("height: 99%; display: inline-block; width: 46px");
		span.appendChild(vbox);
	}	//	Init

	/* (non-Javadoc)
	 * @see org.compiere.grid.APanelTab#loadData()
	 */
	public void loadData()
	{
		yesModel.removeAllElements();
		noModel.removeAllElements();

		boolean isReadWrite = true;
		//	SELECT t.AD_Field_ID,t.Name,t.SeqNo,t.IsDisplayed FROM AD_Field t WHERE t.AD_Tab_ID=? ORDER BY 4 DESC,3,2
		//	SELECT t.AD_PrintFormatItem_ID,t.Name,t.SeqNo,t.IsPrinted FROM AD_PrintFormatItem t WHERE t.AD_PrintFormat_ID=? ORDER BY 4 DESC,3,2
		//	SELECT t.AD_PrintFormatItem_ID,t.Name,t.SortNo,t.IsOrderBy FROM AD_PrintFormatItem t WHERE t.AD_PrintFormat_ID=? ORDER BY 4 DESC,3,2
		StringBuffer sql = new StringBuffer();
		//	Columns
		sql.append("SELECT t.").append(m_KeyColumnName)				//	1
		.append(",").append(m_IdentifierSql)						//	2
		.append(",t.").append(m_ColumnSortName)				//	3
		.append(", t.AD_Client_ID, t.AD_Org_ID");		// 4, 5
		if (m_ColumnYesNoName != null)
			sql.append(",t.").append(m_ColumnYesNoName);			//	6
		//	Tables
		sql.append(" FROM ").append(m_TableName).append( " t");
		if (m_IdentifierTranslated)
			sql.append(", ").append(m_TableName).append("_Trl tt");
		//	Where
		//FR [ 2826406 ]
		if(m_ParentColumnName != null)
		{
			sql.append(" WHERE t.").append(m_ParentColumnName).append("=?");
		}
		else
		{
			sql.append(" WHERE 1=?");
		}
		if (m_IdentifierTranslated)
			sql.append(" AND t.").append(m_KeyColumnName).append("=tt.").append(m_KeyColumnName)
			.append(" AND tt.AD_Language=?");
		//	Order
		sql.append(" ORDER BY ");
		if (m_ColumnYesNoName != null)
			sql.append("6 DESC,");		//	t.IsDisplayed DESC
		sql.append("3,2");				//	t.SeqNo, tt.Name
		//FR [ 2826406 ]
		int ID = 0;
		if(m_ParentColumnName != null)
		{	
			ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, m_ParentColumnName);
			log.debug(sql.toString() + " - ID=" + ID);
		}	
		else
		{
			ID = 1;
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, ID);

			if (m_IdentifierTranslated)
				pstmt.setString(2, Env.getAD_Language(Env.getCtx()));
			
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int key = rs.getInt(1);
				String name = rs.getString(2);
				int seq = rs.getInt(3);
				boolean isYes = seq != 0;
				int AD_Client_ID = rs.getInt(4);
				int AD_Org_ID = rs.getInt(5);
				if (m_ColumnYesNoName != null)
					isYes = rs.getString(6).equals("Y");

				//
				ListElement pp = new ListElement(key, name, seq, isYes, AD_Client_ID, AD_Org_ID);
				if (isYes)
					yesModel.addElement(pp);
				else
					noModel.addElement(pp);
				// If the one item from "Yes" list is readonly make entire tab readonly
				if (isYes && !pp.isUpdateable()) {
					isReadWrite = false;
				}
			}
		}
		catch (SQLException e)
		{
			log.error(sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		setIsChanged(false);
		bAdd.setEnabled(isReadWrite);
		bRemove.setEnabled(isReadWrite);
		bUp.setEnabled(isReadWrite);
		bDown.setEnabled(isReadWrite);
		yesList.setEnabled(isReadWrite);
		noList.setEnabled(isReadWrite);

		yesList.setItemRenderer(yesModel);
		yesList.setModel(yesModel);
		noList.setItemRenderer(noModel);
		noList.setModel(noModel);
	}	//	loadData

	/**
	 * Set tab change status.
	 * @param value
	 */
	private void setIsChanged(boolean value) {
		if (adWindowPanel != null) {
			adWindowPanel.getToolbar().enableSave(value);
			adWindowPanel.getToolbar().enableIgnore(value);
		}
	}

	/**
	 * @param event
	 */
	void migrateValueAcrossLists (Event event)
	{
		Object source = event.getTarget();
		if (source instanceof ListItem) {
			source = ((ListItem)source).getListbox();
		}
		Listbox listFrom = (source == bAdd || source == noList) ? noList : yesList;
		Listbox listTo =  (source == bAdd || source == noList) ? yesList : noList;
		SimpleListModel lmFrom = (source == bAdd || source == noList) ?
				noModel : yesModel;
		SimpleListModel lmTo = (lmFrom == yesModel) ? noModel : yesModel;
		Set selectedItems = listFrom.getSelectedItems();
		List<ListElement> selObjects = new ArrayList<ListElement>();
		for (Object obj : selectedItems) {
			ListItem listItem = (ListItem) obj;
			int index = listFrom.getIndexOfItem(listItem);
			ListElement selObject = (ListElement)lmFrom.getElementAt(index);
			selObjects.add(selObject);
		}
		for (ListElement selObject : selObjects)
		{
			if (selObject == null || !selObject.isUpdateable())
				continue;

			lmFrom.removeElement(selObject);
			lmTo.addElement(selObject);

			//  Enable explicit Save
			setIsChanged(true);
		}

		for (ListElement selObject : selObjects)
		{
			int index = lmTo.indexOf(selObject);
			listTo.setSelectedIndex(index);
		}
		if ( listTo.getSelectedItem() != null)
		{
			AuFocus focus = new AuFocus(listTo.getSelectedItem());
			Clients.response(focus);
		}
	}	//	migrateValueAcrossLists

	/**
	 * 	Move within Yes List
	 *	@param event event
	 */
	void migrateValueWithinYesList (Event event)
	{
		Object[] selObjects = yesList.getSelectedItems().toArray();
		if (selObjects == null)
			return;
		int length = selObjects.length;
		if (length == 0)
			return;
		//
		int[] indices = yesList.getSelectedIndices();
		//
		boolean change = false;
		//
		Object source = event.getTarget();
		if (source == bUp)
		{
			for (int i = 0; i < length; i++) {
				int index = indices[i];
				if (index == 0)
					break;
				ListElement selObject = (ListElement) yesModel.getElementAt(index);
				ListElement newObject = (ListElement)yesModel.getElementAt(index - 1);
				if (!selObject.isUpdateable() || !newObject.isUpdateable())
					break;
				yesModel.setElementAt(newObject, index);
				yesModel.setElementAt(selObject, index - 1);
				indices[i] = index - 1;
				change = true;
			}
		}	//	up

		else if (source == bDown)
		{
			for (int i = length - 1; i >= 0; i--) {
				int index = indices[i];
				if (index  >= yesModel.getSize() - 1)
					break;
				ListElement selObject = (ListElement) yesModel.getElementAt(index);
				ListElement newObject = (ListElement)yesModel.getElementAt(index + 1);
				if (!selObject.isUpdateable() || !newObject.isUpdateable())
					break;
				yesModel.setElementAt(newObject, index);
				yesModel.setElementAt(selObject, index + 1);
				yesList.setSelectedIndex(index + 1);
				indices[i] = index + 1;
				change = true;
			}
		}	//	down

		//
		if (change) {
			yesList.setSelectedIndices(indices);
			setIsChanged(true);
			if ( yesList.getSelectedItem() != null)
			{
				AuFocus focus = new AuFocus(yesList.getSelectedItem());
				Clients.response(focus);
			}
		}
	}	//	migrateValueWithinYesList

	/* (non-Javadoc)
	 * @see org.compiere.grid.APanelTab#registerAPanel(APanel)
	 */
	public void registerAPanel (AbstractADWindowPanel panel)
	{
		adWindowPanel = panel;
	}	//	registerAPanel


	/** (non-Javadoc)
	 * @see org.compiere.grid.APanelTab#saveData()
	 */
	public void saveData()
	{
		if (!adWindowPanel.getToolbar().isSaveEnable())
			return;
		log.debug("");
		boolean ok = true;
		StringBuffer info = new StringBuffer();
		StringBuffer sql = null;
		//	noList - Set SortColumn to null and optional YesNo Column to 'N'
		for (int i = 0; i < noModel.getSize(); i++)
		{
			ListElement pp = (ListElement)noModel.getElementAt(i);
			if (!pp.isUpdateable())
				continue;
			if(pp.getSortNo() == 0 && (m_ColumnYesNoName == null || !pp.isYes()))
				continue; // no changes
			//
			sql = new StringBuffer();
			sql.append("UPDATE ").append(m_TableName)
			.append(" SET ").append(m_ColumnSortName).append("=0");
			if (m_ColumnYesNoName != null)
				sql.append(",").append(m_ColumnYesNoName).append("='N'");
			sql.append(" WHERE ").append(m_KeyColumnName).append("=").append(pp.getKey());
			if (DB.executeUpdate(sql.toString(), null) == 1) {
				pp.setSortNo(0);
				pp.setIsYes(false);
			}
			else {
				ok = false;
				if (info.length() > 0)
					info.append(", ");
				info.append(pp.getName());
				log.error("NoModel - Not updated: " + m_KeyColumnName + "=" + pp.getKey());
			}
		}
		//	yesList - Set SortColumn to value and optional YesNo Column to 'Y'
		int index = 0;
		for (int i = 0; i < yesModel.getSize(); i++)
		{
			ListElement pp = (ListElement)yesModel.getElementAt(i);
			if (!pp.isUpdateable())
				continue;
			index += 10;
			if(pp.getSortNo() == index && (m_ColumnYesNoName == null || pp.isYes()))
				continue; // no changes
			//
			sql = new StringBuffer();
			sql.append("UPDATE ").append(m_TableName)
			.append(" SET ").append(m_ColumnSortName).append("=").append(index);
			if (m_ColumnYesNoName != null)
				sql.append(",").append(m_ColumnYesNoName).append("='Y'");
			sql.append(" WHERE ").append(m_KeyColumnName).append("=").append(pp.getKey());
			if (DB.executeUpdate(sql.toString(), null) == 1) {
				pp.setSortNo(index);
				pp.setIsYes(true);
			}
			else {
				ok = false;
				if (info.length() > 0)
					info.append(", ");
				info.append(pp.getName());
				log.error("YesModel - Not updated: " + m_KeyColumnName + "=" + pp.getKey());
			}
		}
		//
		if (ok) {
			setIsChanged(false);
		}
		else {
			FDialog.error(m_WindowNo, null, "SaveError", info.toString());
		}
	}	//	saveData

	/* (non-Javadoc)
	 * @see org.compiere.grid.APanelTab#unregisterPanel()
	 */
	public void unregisterPanel ()
	{
		saveData();
		adWindowPanel = null;
	}	//	dispose

	/**
	 * List Item
	 * @author Teo Sarca
	 */
	private class ListElement extends NamePair {
		/**
		 *
		 */
		private static final long serialVersionUID = -5645910649588308798L;
		private int		m_key;
		private int		m_AD_Client_ID;
		private int		m_AD_Org_ID;
		/** Initial seq number */
		private int		m_sortNo;
		/** Initial selection flag */
		private boolean m_isYes;
		private boolean	m_updateable;

		public ListElement(int key, String name, int sortNo, boolean isYes, int AD_Client_ID, int AD_Org_ID) {
			super(name);
			this.m_key = key;
			this.m_AD_Client_ID = AD_Client_ID;
			this.m_AD_Org_ID = AD_Org_ID;
			this.m_sortNo = sortNo;
			this.m_isYes = isYes;
			this.m_updateable = Env.getUserRolePermissions().canUpdate(m_AD_Client_ID, m_AD_Org_ID, m_AD_Table_ID, m_key, false);
		}
		public int getKey() {
			return m_key;
		}
		public void setSortNo(int sortNo) {
			m_sortNo = sortNo;
		}
		public int getSortNo() {
			return m_sortNo;
		}
		public void setIsYes(boolean value) {
			m_isYes = value;
		}
		public boolean isYes() {
			return m_isYes;
		}
		public int getAD_Client_ID() {
			return m_AD_Client_ID;
		}
		public int getAD_Org_ID() {
			return m_AD_Org_ID;
		}
		public boolean isUpdateable() {
			return m_updateable;
		}
		@Override
		public String getID() {
			return m_key != -1 ? String.valueOf(m_key) : null;
		}
		@Override
		public int hashCode() {
			return m_key;
		}
		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof ListElement)
			{
				ListElement li = (ListElement)obj;
				return
					li.getKey() == m_key
					&& li.getName() != null
					&& li.getName().equals(getName())
					&& li.getAD_Client_ID() == m_AD_Client_ID
					&& li.getAD_Org_ID() == m_AD_Org_ID;
			}
			return false;
		}	//	equals

		@Override
		public String toString() {
			String s = super.toString();
			if (s == null || s.trim().length() == 0)
				s = "<" + getKey() + ">";
			return s;
		}
	}

	/**
	 * @author eslatis
	 *
	 */
	private class DragListener implements EventListener
	{

		/**
		 * Creates a ADSortTab.DragListener.
		 */
		public DragListener()
		{
		}

		public void onEvent(Event event) throws Exception {
			if (event instanceof DropEvent)
			{
				DropEvent me = (DropEvent) event;

				ListItem endItem = (ListItem) me.getTarget();
				if (!(endItem.getListbox() == yesList))
				{
					return;		//	move within noList
				}

				ListItem startItem = (ListItem) me.getDragged();
				if (startItem.getListbox() == endItem.getListbox())
				{
					return; //move within same list
				}
				int startIndex = noList.getIndexOfItem(startItem);
				Object element = noModel.getElementAt(startIndex);
				noModel.removeElement(element);
				int endIndex = yesList.getIndexOfItem(endItem);
				yesModel.add(endIndex, element);
				//
				noList.clearSelection();
				yesList.clearSelection();

				yesList.setSelectedIndex(endIndex);
				//
				setIsChanged(true);
			}
		}
	}

	public void activate(boolean b) {
		if (b && !uiCreated) createUI();
	}

	public void createUI() {
		if (uiCreated) return;
		try
		{
			init();
			dynInit (gridTab.getAD_Table_ID(), gridTab.getAD_ColumnSortOrder_ID(), gridTab.getAD_ColumnSortYesNo_ID());
		}
		catch(Exception e)
		{
			log.error("", e);
		}
		uiCreated = true;
	}

	public void dynamicDisplay(int i) {
	}

	public void editRecord(boolean b) {
	}

	public ILogicExpression getDisplayLogic() {
		return gridTab.getDisplayLogic();
	}

	public GridTab getGridTab() {
		return gridTab;
	}

	public int getTabLevel() {
		return gridTab.getTabLevel();
	}

	public String getTitle() {
		return gridTab.getName();
	}

	public boolean isCurrent() {
		return gridTab != null ? gridTab.isCurrent() : false;
	}

	public void query() {
		loadData();
	}

	public void query(boolean currentRows, int currentDays, GridTabMaxRows maxRows_NOTUSED)
	{
		loadData();
	}

	public void refresh() {
		loadData();
	}

	public void switchRowPresentation() {
	}

	public String get_ValueAsString(String variableName) {
		return Env.getContext(Env.getCtx(), m_WindowNo, variableName);
	}

	public void afterSave(boolean onSaveEvent) {
	}

	public boolean onEnterKey() {
		return false;
	}
}	//ADSortTab


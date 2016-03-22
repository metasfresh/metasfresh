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
package org.compiere.grid;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.images.Images;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.ADialog;
import org.compiere.apps.APanel;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.NamePair;
import org.compiere.util.TrxRunnableAdapter;

/**
 *	Tab to maintain Order/Sequence
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VSortTab.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 				FR [ 1779410 ] VSortTab: display ID for not visible columns
 * @author victor.perez@e-evolution.com, e-Evolution
 * 				FR [ 2826406 ] The Tab Sort without parent column
 *				<li> https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2826406&group_id=176962
 */
public class VSortTab extends CPanel implements APanelTab
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2133358506913610514L;

	/**
	 *	Tab Order Constructor
	 *
	 *  @param WindowNo Window No
	 *  @param AD_Table_ID Table No
	 *  @param AD_ColumnSortOrder_ID Sort Column
	 *  @param AD_ColumnSortYesNo_ID YesNo Column
	 */
	public VSortTab(int WindowNo, int AD_Table_ID, int AD_ColumnSortOrder_ID, int AD_ColumnSortYesNo_ID)
	{
		log.info("SortOrder=" + AD_ColumnSortOrder_ID + ", SortYesNo=" + AD_ColumnSortYesNo_ID);
		m_WindowNo = WindowNo;

		try
		{
			jbInit();
			dynInit (AD_Table_ID, AD_ColumnSortOrder_ID, AD_ColumnSortYesNo_ID);
		}
		catch(Exception e)
		{
			log.error("", e);
		}
	}	//	VSortTab

	/**	Logger			*/
	static Logger log = LogManager.getLogger(VSortTab.class);
	private int			m_WindowNo;
	private int			m_AD_Table_ID;
	private String		m_TableName = null;
	private String		m_ColumnSortName= null;
	private String		m_ColumnYesNoName = null;
	private String		m_KeyColumnName = null;
	private String		m_IdentifierSql = null;
	private boolean		m_IdentifierTranslated = false;

	private String		m_ParentColumnName = null;
	private APanel		m_aPanel = null;

	//	UI variables
	private GridBagLayout mainLayout = new GridBagLayout();
	private CLabel noLabel = new CLabel();
	private CLabel yesLabel = new CLabel();
	private CButton bAdd = new CButton();
	private CButton bRemove = new CButton();
	private CButton bUp = new CButton();
	private CButton bDown = new CButton();
	//
	private final DefaultListModel<ListItem> noModel = new DefaultListModel<ListItem>()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2171680655634744697L;
		@Override
		public void addElement(ListItem obj) {
			Object[] elements = toArray();
			Arrays.sort(elements);
			int index = Arrays.binarySearch(elements, obj);
			if (index < 0)
				index = -1 * index - 1;
			if (index > elements.length)
				super.addElement(obj);
			else
				super.add(index, obj);
		}
		@Override
		public void add(int index, ListItem obj) {
			addElement(obj);
		}
	};
	private final DefaultListModel<ListItem> yesModel = new DefaultListModel<>();
	DefaultListCellRenderer listRenderer = new DefaultListCellRenderer() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -101524191283634472L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value != null && value instanceof ListItem && !((ListItem)value).isUpdateable()) {
				Font f = c.getFont();
				c.setFont(f.deriveFont(f.getStyle() | Font.ITALIC));
				c.setFocusable(false);
			}
			return c;
		}
		
	};
	private final JList<ListItem> noList = new JList<>(noModel);
	private final JList<ListItem> yesList = new JList<>(yesModel);
	private final JScrollPane noPane = new JScrollPane(noList);
	private final JScrollPane yesPane = new JScrollPane(yesList);

	/**
	 * 	Dyanamic Init
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
					yesLabel.setText(rs.getString(4));
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
		noLabel.setText(Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Available"));
		log.debug(m_ColumnSortName);
	}	//	dynInit

	/**
	 * 	Static Layout
	 * 	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setLayout(mainLayout);
		//
		noLabel.setText("No");
		yesLabel.setText("Yes");

		for (MouseMotionListener mml : noList.getMouseMotionListeners())
			noList.removeMouseMotionListener(mml);

		for (MouseMotionListener mml : yesList.getMouseMotionListeners())
			yesList.removeMouseMotionListener(mml);

		MouseListener mouseListener = new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent me)
			{
				if (me.getClickCount() > 1)
				{
					@SuppressWarnings("unchecked")
					final JList<ListItem> list = (JList<ListItem>)me.getComponent();
					Point p = me.getPoint();
					int index = list.locationToIndex(p);
					if (index > -1 && list.getCellBounds(index, index).contains(p))
						migrateValueAcrossLists(me);
				}
			}
		};
		yesList.addMouseListener(mouseListener);
		noList.addMouseListener(mouseListener);
		//
		yesList.setCellRenderer(listRenderer);
		noList.setCellRenderer(listRenderer);

		ActionListener actionListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				migrateValueAcrossLists(ae);
			}
		};

		bAdd.setIcon(Images.getImageIcon2("Detail24"));
		bAdd.setMargin(new Insets(2, 2, 2, 2));
		bAdd.addActionListener(actionListener);

		bRemove.setIcon(Images.getImageIcon2("Parent24"));
		bRemove.setMargin(new Insets(2, 2, 2, 2));
		bRemove.addActionListener(actionListener);

		MouseInputListener crossListMouseListener = new DragListener();
		yesList.addMouseListener(crossListMouseListener);
		yesList.addMouseMotionListener(crossListMouseListener);
		noList.addMouseListener(crossListMouseListener);
		noList.addMouseMotionListener(crossListMouseListener);

		actionListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				migrateValueWithinYesList(ae);
			}
		};

		bUp.setIcon(Images.getImageIcon2("Previous24"));
		bUp.setMargin(new Insets(2, 2, 2, 2));
		bUp.addActionListener(actionListener);

		bDown.setIcon(Images.getImageIcon2("Next24"));
		bDown.setMargin(new Insets(2, 2, 2, 2));
		bDown.addActionListener(actionListener);

		MouseMotionListener yesListMouseMotionListener = new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent me)
			{
				@SuppressWarnings("unchecked")
				final JList<ListItem> list = (JList<ListItem>)me.getComponent();
				Point p = me.getPoint();
				int index = list.locationToIndex(p);
				if (index > -1 && list.getCellBounds(index, index).contains(p))
					migrateValueWithinYesList(me);
			}
		};
		yesList.addMouseMotionListener(yesListMouseMotionListener);

		yesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		noList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		this.add(noLabel,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(yesLabel,    new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(bDown,         new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		this.add(noPane, new GridBagConstraints(
				0, 1 // gridx, gridy,
				, 1, 3 // gridwidth, gridheight
				, 1.0, 1.0 // weightx, weighty
				,GridBagConstraints.NORTH // anchor
				, GridBagConstraints.BOTH // fill
				, new Insets(4, 4, 4, 4) // insets
				, 0, 0) // ipadx, ipady
		);
		this.add(yesPane, new GridBagConstraints(
				2, 1 // gridx, gridy,
				, 1, 3 // gridwidth, gridheight
				, 1.0, 1.0 // weightx, weighty
				,GridBagConstraints.NORTH // anchor
				, GridBagConstraints.BOTH // fill
				, new Insets(4, 4, 4, 4) // insets
				, 0, 0) // ipadx, ipady
		);
		this.add(bUp,  new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		this.add(bAdd,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
		this.add(bRemove,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
	}	//	jbInit

	/* (non-Javadoc)
	 * @see org.compiere.grid.APanelTab#loadData()
	 */
	@Override
	public void loadData()
	{
		yesModel.removeAllElements();
		noModel.removeAllElements();

		boolean isReadWrite = true;
		//	SELECT t.AD_Field_ID,t.Name,t.SeqNo,t.IsDisplayed FROM AD_Field t WHERE t.AD_Tab_ID=? ORDER BY 4 DESC,3,2
		//	SELECT t.AD_PrintFormatItem_ID,t.Name,t.SeqNo,t.IsPrinted FROM AD_PrintFormatItem t WHERE t.AD_PrintFormat_ID=? ORDER BY 4 DESC,3,2
		//	SELECT t.AD_PrintFormatItem_ID,t.Name,t.SortNo,t.IsOrderBy FROM AD_PrintFormatItem t WHERE t.AD_PrintFormat_ID=? ORDER BY 4 DESC,3,2
		final StringBuilder sql = new StringBuilder();
		//	Columns
		sql.append("SELECT t.").append(m_KeyColumnName)				//	1
		.append(",").append(m_IdentifierSql)						//	2
		.append(",t.").append(m_ColumnSortName)				//	3
		.append(", t.AD_Client_ID, t.AD_Org_ID");		// 4, 5
		if (m_ColumnYesNoName != null)
			sql.append(",t.").append(m_ColumnYesNoName);			//	6
		// metas: begin
		boolean hasColumnName = false;
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			if ("AD_Field".equals(m_TableName))
		{
			sql.append(", (SELECT c.ColumnName FROM AD_Column c WHERE c.AD_Column_ID=t.AD_Column_ID) AS ColumnName");
				hasColumnName = true;
			}
			else if ("AD_Process_Para".equals(m_TableName))
			{
				sql.append(", t.ColumnName AS ColumnName");
				hasColumnName = true;
			}
		}
		// metas: end
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
		
		final String adLanguage = Env.getAD_Language(Env.getCtx());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
			pstmt.setInt(1, ID);
			
			if (m_IdentifierTranslated)
			{
				pstmt.setString(2, adLanguage);
			}
			
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
					isYes = "Y".equals(rs.getString(6));
				
				// metas: begin
				if (hasColumnName)
				{
					String columnName = rs.getString("ColumnName");
					name = name == null ? columnName : columnName + " / " + name;
				}
				// metas: end
				//
				final ListItem pp = new ListItem(key, name, seq, isYes, AD_Client_ID, AD_Org_ID);
				if (isYes)
				{
					yesModel.addElement(pp);
				}
				else
				{
					noModel.addElement(pp);
				}
				// If the one item from "Yes" list is readonly make entire tab readonly
				if (isYes && !pp.isUpdateable())
				{
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
	}	//	loadData

	/**
	 * Set tab change status.
	 * @param value
	 */
	private void setIsChanged(boolean value) {
		if (m_aPanel != null) {
			m_aPanel.aSave.setEnabled(value);
			m_aPanel.aIgnore.setEnabled(value);
		}
	}

	/**
	 * @param event
	 */
	void migrateValueAcrossLists (AWTEvent event)
	{
		Object source = event.getSource();
		final List<ListItem> selObjects = (source == bAdd || source == noList) ? noList.getSelectedValuesList() : yesList.getSelectedValuesList();
		for (int i = 0; i < selObjects.size(); i++)
		{
			ListItem selObject = selObjects.get(i);
			if (selObject == null || !selObject.isUpdateable())
				continue;

			DefaultListModel<ListItem> lmFrom = (source == bAdd || source == noList) ?
					noModel : yesModel;
			DefaultListModel<ListItem> lmTo = (lmFrom == yesModel) ? noModel : yesModel;
			lmFrom.removeElement(selObject);
			lmTo.addElement(selObject);

			JList<ListItem> list =  (source == bAdd || source == noList) ? yesList : noList;
			list.setSelectedValue(selObject, true);

			//  Enable explicit Save
			setIsChanged(true);
		}
	}	//	migrateValueAcrossLists

	/**
	 * 	Move within Yes List
	 *	@param event event
	 */
	void migrateValueWithinYesList (AWTEvent event)
	{
		final List<ListItem> selObjects = yesList.getSelectedValuesList();
		if (selObjects == null)
			return;
		int length = selObjects.size();
		if (length == 0)
			return;
//		Object selObject = selObjects[0];
//		if (selObject == null)
//		return;
		//
		int[] indices = yesList.getSelectedIndices();
		//
		boolean change = false;
		//
		Object source = event.getSource();
		if (source == bUp)
		{
			for (int i = 0; i < length; i++) {
				ListItem selObject = selObjects.get(i);
				int index = indices[i];
				if (index == 0)
					break;
				ListItem newObject = yesModel.getElementAt(index - 1);
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
				ListItem selObject = selObjects.get(i);
				int index = indices[i];
				if (index  >= yesModel.size () - 1)
					break;
				ListItem newObject = yesModel.getElementAt(index + 1);
				if (!selObject.isUpdateable() || !newObject.isUpdateable())
					break;
				yesModel.setElementAt(newObject, index);
				yesModel.setElementAt(selObject, index + 1);
				yesList.setSelectedIndex(index + 1);
				indices[i] = index + 1;
				change = true;
			}
		}	//	down

		else if (source == yesList)
		{
			// see org.compiere.grid.VSortTab.DragListener#mouseReleased(MouseEvent)
		}
		else
			log.error("Unknown source: " + source);
		//
		if (change) {
			yesList.setSelectedIndices(indices);
			setIsChanged(true);
		}
	}	//	migrateValueWithinYesList

	/* (non-Javadoc)
	 * @see org.compiere.grid.APanelTab#registerAPanel(APanel)
	 */
	@Override
	public void registerAPanel (APanel panel)
	{
		m_aPanel = panel;
	}	//	registerAPanel


	/** (non-Javadoc)
	 * @see org.compiere.grid.APanelTab#saveData()
	 */
	@Override
	public void saveData()
	{
		if (!m_aPanel.aSave.isEnabled())
			return;
		log.debug("");
		
		final AtomicBoolean ok = new AtomicBoolean(true);
		final StringBuffer info = new StringBuffer();
		Services.get(ITrxManager.class)
				.run(new TrxRunnableAdapter()
				{
					@Override
					public void run(String localTrxName) throws Exception
					{
		//	noList - Set SortColumn to null and optional YesNo Column to 'N'
		for (int i = 0; i < noModel.getSize(); i++)
		{
			ListItem pp = noModel.getElementAt(i);
			if (!pp.isUpdateable())
				continue;
			if(pp.getSortNo() == 0 && (m_ColumnYesNoName == null || !pp.isYes()))
				continue; // no changes
			
			if (!updateAndSaveListItem(pp, false, 0))
			{
								ok.set(false);
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
			ListItem pp = yesModel.getElementAt(i);
			if (!pp.isUpdateable())
				continue;
			index += 10;
			if(pp.getSortNo() == index && (m_ColumnYesNoName == null || pp.isYes()))
				continue; // no changes
			//
			if (!updateAndSaveListItem(pp, true, index))
			{
								ok.set(false);
				if (info.length() > 0)
					info.append(", ");
				info.append(pp.getName());
				log.error("YesModel - Not updated: " + m_KeyColumnName + "=" + pp.getKey());
			}
		}
					}
				});
		
		//
		if (ok.get())
		{
			setIsChanged(false);
		}
		else
		{
			ADialog.error(m_WindowNo, null, "SaveError", info.toString());
		}
	}	//	saveData
	
	private boolean updateAndSaveListItem(ListItem item, boolean isYes, int sortNo)
	{
		if (!item.isUpdateable())
		{
			return false;
		}
		
		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final PO po = new Query(ctx, m_TableName, m_KeyColumnName + "=?", trxName)
				.setParameters(item.getKey())
				.firstOnly();
		if (po == null)
		{
			return false;
		}
		
		po.set_ValueOfColumnReturningBoolean(m_ColumnSortName, sortNo);
		if(m_ColumnYesNoName != null)
		{
			po.set_ValueOfColumnReturningBoolean(m_ColumnYesNoName, isYes);
		}
		
		po.saveEx();
		
		//
		// Update Item
		item.setIsYes(isYes);
		item.setSortNo(sortNo);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.APanelTab#unregisterPanel()
	 */
	@Override
	public void unregisterPanel ()
	{
		saveData();
		m_aPanel = null;
	}	//	dispoase
	
	/**
	 * List Item
	 * @author Teo Sarca
	 */
	private class ListItem extends NamePair {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5399675004361331697L;
		private int		m_key;
		private int		m_AD_Client_ID;
		private int		m_AD_Org_ID;
		/** Initial seq number */
		private int		m_sortNo;
		/** Initial selection flag */
		private boolean m_isYes;
		private boolean	m_updateable;
		
		public ListItem(int key, String name, int sortNo, boolean isYes, int AD_Client_ID, int AD_Org_ID) {
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
			if (obj instanceof ListItem)
			{
				ListItem li = (ListItem)obj;
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
	private class DragListener extends MouseInputAdapter
	{

		/**
		 * Creates a VSortTab.DragListener.
		 */
		public DragListener()
		{
			final Image image = Images.getImage2("DragCursor32");
			
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			customCursor = toolkit.createCustomCursor(image, new Point(0, 0), "Drag");
		}

		/** The customCursor. */
		private final Cursor customCursor;

		/** StartList	*/
		private JList<ListItem> 	startList = null;

		/** The startModel. */
		private DefaultListModel<ListItem> startModel = null;

		/** The selObject. */
		private ListItem			selObject = null;

		private boolean				moved = false;

		/* (non-Javadoc)
		 * @see javax.swing.event.MouseInputAdapter#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent me)
		{
			@SuppressWarnings("unchecked")
			final JList<ListItem> list = (JList<ListItem>)me.getComponent();
			Point p = me.getPoint();
			int index = list.locationToIndex(p);
			if (index > -1 && list.getCellBounds(index, index).contains(p))
			{
				startList = list;
				startModel = (list == noList) ? noModel : yesModel;
				selObject = list.getModel().getElementAt(index);
			}
			if (list == noList)
				yesList.clearSelection();
			else
				noList.clearSelection();
			moved = false;
		}	//	mousePressed

		/* (non-Javadoc)
		 * @see javax.swing.event.MouseInputAdapter#mouseDragged(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseDragged(MouseEvent me)
		{
			if (selObject == null || !selObject.isUpdateable()) {
				moved = false;
				return;
			}
			moved = true;
			if (getCursor() != customCursor)
				setCursor(customCursor);
		}	//	mouseDragged

		/* (non-Javadoc)
		 * @see javax.swing.event.MouseInputAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent me)
		{
			if (startModel != null && moved)
			{
				Point p = me.getPoint();

				JList<ListItem> endList = yesList;
				DefaultListModel<ListItem> endModel = yesModel;

				if (me.getComponent() == yesList)
				{
					if (!yesList.contains (p))
					{
						endList = noList;
						endModel = noModel;
					}
				}
				else
				{
					if (noList.contains (p))
					{
						setCursor(Cursor.getDefaultCursor());
						moved = false;
						return;		//	move within noList
					}
					p = SwingUtilities.convertPoint (noList, p, yesList);
				}
				int index = endList.locationToIndex(p);
				if (index > -1)	// && endList.getCellBounds(index, index).contains(p))
				{
					startModel.removeElement(selObject);
					endModel.add(index, selObject);
					//
					startList.clearSelection();
					endList.clearSelection();
					endList.setSelectedValue(selObject, true);
					//
					setIsChanged(true);
				}
			}

			startList = null;
			startModel = null;
			selObject = null;
			moved = false;
			setCursor(Cursor.getDefaultCursor());
		}	//	mouseReleased
	}

}	//	VSortTab


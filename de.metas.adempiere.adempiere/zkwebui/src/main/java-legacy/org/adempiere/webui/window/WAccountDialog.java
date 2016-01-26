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
package org.adempiere.webui.window;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.adempiere.acct.api.impl.AccountDimension;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.ToolBar;
import org.adempiere.webui.component.ToolBarButton;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WebEditorFactory;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.panel.ADTabpanel;
import org.adempiere.webui.panel.StatusBarPanel;
import org.adempiere.webui.session.SessionManager;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.DataStatusListener;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridWindow;
import org.compiere.model.GridWindowVO;
import org.compiere.model.MAccount;
import org.compiere.model.MAccountLookup;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaElement;
import org.compiere.model.MQuery;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Vbox;

/**
 *	Dialog to enter Account Info
 *
 * 	@author Low Heng Sin
 */
public final class WAccountDialog extends Window
	implements EventListener, DataStatusListener, ValueChangeListener
{

	private static final long serialVersionUID = 7999516267209766287L;

	/**
	 * 	Constructor
	 *  @param title title
	 *  @param mAccount account info
	 *  @param C_AcctSchema_ID as
	 */
	public WAccountDialog (String title,
		MAccountLookup mAccount, int C_AcctSchema_ID)
	{
		super ();
		this.setTitle(title);
		this.setHeight("500px");
		this.setWidth("700px");

		log.config("C_AcctSchema_ID=" + C_AcctSchema_ID
			+ ", C_ValidCombination_ID=" + mAccount.getC_ValidCombination_ID());
		m_mAccount = mAccount;
		m_C_AcctSchema_ID = C_AcctSchema_ID;
		m_WindowNo = SessionManager.getAppDesktop().registerWindow(this);
		try
		{
			init();
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, ex.toString());
		}
		if (initAccount())
			AEnv.showCenterScreen(this);
		else
			dispose();
	}	//	WAccountDialog

	/** Window No					*/
	private int					m_WindowNo;
	/**	Journal Entry				*
	private boolean				m_onlyNonDocControlled = false;
	/** Selection changed			*/
	protected boolean			m_changed = false;

	/** Accounting Schema           */
	private static MAcctSchema	s_AcctSchema = null;
	/** MWindow for AccountCombination  */
	private GridWindow             m_mWindow = null;
	/** MTab for AccountCombination     */
	private GridTab                m_mTab = null;
	/** GridController                  */
	private ADTabpanel      m_adTabPanel = null;

	/** Account used                */
	private MAccountLookup		m_mAccount = null;
	/** Result                      */
	private int 				m_C_ValidCombination_ID;
	/** Acct Schema					*/
	private int					m_C_AcctSchema_ID = 0;
	/** Client                      */
	private int                 m_AD_Client_ID;
	/** Where clause for combination search */
	private MQuery				m_query;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WAccountDialog.class);

	//  Editors for Query
	private WEditor 			f_Alias, f_Combination,
		f_AD_Org_ID, f_Account_ID, f_SubAcct_ID,
		f_M_Product_ID, f_C_BPartner_ID, f_C_Campaign_ID, f_C_LocFrom_ID, f_C_LocTo_ID,
		f_C_Project_ID, f_C_SalesRegion_ID, f_AD_OrgTrx_ID, f_C_Activity_ID,
		f_User1_ID, f_User2_ID;
	//
	private Label f_Description = new Label ("");

	private int					m_line = 0;
	private boolean				m_newRow = true;
	//
	private Vbox panel = new Vbox();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	private StatusBarPanel statusBar = new StatusBarPanel();
	private Hbox northPanel = new Hbox();
	private Groupbox parameterPanel = new Groupbox();
	private Grid parameterLayout = new Grid();
	private ToolBar toolBar = new ToolBar();
	private ToolBarButton bRefresh = new ToolBarButton();
	private ToolBarButton bSave = new ToolBarButton();
	private ToolBarButton bIgnore = new ToolBarButton();
	private Row m_row;
	private Rows m_rows;

	/**
	 *	Static component init.
	 *  <pre>
	 *  - north
	 *    - parameterPanel
	 *    - toolBar
	 *  - center
	 *    - adtabpanel
	 *  - south
	 *    - confirmPanel
	 *    - statusBar
	 *  </pre>
	 *  @throws Exception
	 */
	void init() throws Exception
	{
		//
		Caption caption = new Caption(Msg.getMsg(Env.getCtx(),"Parameter"));
		parameterPanel.appendChild(caption);
		parameterPanel.setStyle("background-color: transparent;");
		toolBar.setOrient("vertical");
		toolBar.setStyle("border: none; margin: 5px");

		bSave.setImage("images/Save24.png");
		bSave.setTooltiptext(Msg.getMsg(Env.getCtx(),"AccountNewUpdate"));
		bSave.addEventListener(Events.ON_CLICK, this);
		bRefresh.setImage("images/Refresh24.png");
		bRefresh.setTooltiptext(Msg.getMsg(Env.getCtx(),"Refresh"));
		bRefresh.addEventListener(Events.ON_CLICK, this);
		bIgnore.setImage("images/Ignore24.png");
		bIgnore.setTooltiptext(Msg.getMsg(Env.getCtx(),"Ignore"));
		bIgnore.addEventListener(Events.ON_CLICK, this);
		//
		toolBar.appendChild(bRefresh);
		toolBar.appendChild(bIgnore);
		toolBar.appendChild(bSave);
		//

		northPanel.appendChild(parameterPanel);
		parameterPanel.setWidth("95%");
		northPanel.appendChild(toolBar);
		northPanel.setWidth("100%");

		m_adTabPanel = new ADTabpanel();

		Borderlayout layout = new Borderlayout();
		layout.setParent(this);
		if (AEnv.isFirefox2())
		{
			layout.setHeight("93%");
			layout.setWidth("98%");
			layout.setStyle("background-color: transparent; position: absolute;");
			this.setStyle("position: relative;");
		}
		else
		{
			layout.setHeight("100%");
			layout.setWidth("100%");
			layout.setStyle("background-color: transparent;");
		}

		North nRegion = new North();
		nRegion.setParent(layout);
		nRegion.setFlex(false);
		nRegion.appendChild(northPanel);
		nRegion.setStyle("background-color: transparent; border: none");
		northPanel.setStyle("background-color: transparent;");

		Center cRegion = new Center();
		cRegion.setParent(layout);
		cRegion.appendChild(m_adTabPanel);
		cRegion.setFlex(true);

		South sRegion = new South();
		sRegion.setParent(layout);
		Div div = new Div();
		div.appendChild(confirmPanel);
		confirmPanel.setStyle("margin-top: 5px; margin-bottom: 5px");
		div.appendChild(statusBar);
		sRegion.appendChild(div);
		sRegion.setStyle("background-color: transparent; border: none");

		confirmPanel.addActionListener(Events.ON_CLICK, this);

		this.setBorder("normal");
		this.setClosable(false);
		this.setAttribute("modal", Boolean.TRUE);

		this.setSizable(true);
	}	//	jbInit

	/**
	 *	Dyanmic Init.
	 *  When a row is selected, the editor values are set
	 *  (editors do not change grid)
	 *  @return true if initialized
	 */
	private boolean initAccount()
	{
		m_AD_Client_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "AD_Client_ID");
		//	Get AcctSchema Info
		if (s_AcctSchema == null || s_AcctSchema.getC_AcctSchema_ID() != m_C_AcctSchema_ID)
			s_AcctSchema = new MAcctSchema (Env.getCtx(), m_C_AcctSchema_ID, null);
		log.config(s_AcctSchema.toString()
			+ ", #" + s_AcctSchema.getAcctSchemaElements().length);
		Env.setContext(Env.getCtx(), m_WindowNo, "C_AcctSchema_ID", m_C_AcctSchema_ID);

		//  Model
		int AD_Window_ID = 153;		//	Maintain Account Combinations
		GridWindowVO wVO = AEnv.getMWindowVO (m_WindowNo, AD_Window_ID, 0);
		if (wVO == null)
			return false;
		m_mWindow = new GridWindow (wVO);
		m_mTab = m_mWindow.getTab(0);
		// Make sure is the tab is loaded - teo_sarca [ 1659124 ]
		if (!m_mTab.isLoadComplete())
			m_mWindow.initTab(0);

		//  ParameterPanel restrictions
		m_mTab.getField("Alias").setDisplayLength(15);
		m_mTab.getField("Combination").setDisplayLength(15);
		//  Grid restrictions
		m_mTab.getField("AD_Client_ID").setDisplayed(false);
		m_mTab.getField("C_AcctSchema_ID").setDisplayed(false);
		m_mTab.getField("IsActive").setDisplayed(false);
		m_mTab.getField("IsFullyQualified").setDisplayed(false);
		//  don't show fields not being displayed in this environment
		for (int i = 0; i < m_mTab.getFieldCount(); i++)
		{
			GridField field = m_mTab.getField(i);
			if (!field.isDisplayed (true))      //  check context
				field.setDisplayed (false);
		}

		//  GridController
		m_adTabPanel.init(null, m_WindowNo, m_mTab, null);

		//  Prepare Parameter
		parameterLayout.makeNoStrip();
		parameterLayout.setOddRowSclass("even");
		parameterLayout.setParent(parameterPanel);
		parameterLayout.setStyle("background-color: transparent;");

		m_rows = new Rows();
		m_rows.setParent(parameterLayout);

		int TabNo = 0;

		//	Alias
		if (s_AcctSchema.isHasAlias())
		{
			GridField alias = m_mTab.getField("Alias");
			f_Alias = WebEditorFactory.getEditor(alias, false);
			addLine(alias, f_Alias, false);
		}	//	Alias

		//	Combination
		GridField combination = m_mTab.getField("Combination");
		f_Combination = WebEditorFactory.getEditor(combination, false);
		addLine(combination, f_Combination, false);
		m_newRow = true;

		/**
		 *	Create Fields in Element Order
		 */
		MAcctSchemaElement[] elements = s_AcctSchema.getAcctSchemaElements();
		for (int i = 0; i < elements.length; i++)
		{
			MAcctSchemaElement ase = elements[i];
			String type = ase.getElementType();
			boolean isMandatory = ase.isMandatory();
			//
			if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Organization))
			{
				GridField field = m_mTab.getField("AD_Org_ID");
				f_AD_Org_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_AD_Org_ID, isMandatory);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Account))
			{
				GridField field = m_mTab.getField("Account_ID");
				f_Account_ID = WebEditorFactory.getEditor(field, false);
			//	((VLookup)f_Account_ID).setWidth(400);
				addLine(field, f_Account_ID, isMandatory);
				f_Account_ID.addValueChangeListener(this);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_SubAccount))
			{
				GridField field = m_mTab.getField("C_SubAcct_ID");
				f_SubAcct_ID = WebEditorFactory.getEditor(field, false);
			//	((VLookup)f_SubAcct_ID).setWidth(400);
				addLine(field, f_SubAcct_ID, isMandatory);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Product))
			{
				GridField field = m_mTab.getField("M_Product_ID");
				f_M_Product_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_M_Product_ID, isMandatory);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_BPartner))
			{
				GridField field = m_mTab.getField("C_BPartner_ID");
				f_C_BPartner_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_C_BPartner_ID, isMandatory);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Campaign))
			{
				GridField field = m_mTab.getField("C_Campaign_ID");
				f_C_Campaign_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_C_Campaign_ID, isMandatory);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_LocationFrom))
			{
				GridField field = m_mTab.getField("C_LocFrom_ID");
				f_C_LocFrom_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_C_LocFrom_ID, isMandatory);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_LocationTo))
			{
				GridField field = m_mTab.getField("C_LocTo_ID");
				f_C_LocTo_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_C_LocTo_ID, isMandatory);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Project))
			{
				GridField field = m_mTab.getField("C_Project_ID");
				f_C_Project_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_C_Project_ID, isMandatory);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_SalesRegion))
			{
				GridField field = m_mTab.getField("C_SalesRegion_ID");
				f_C_SalesRegion_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_C_SalesRegion_ID, isMandatory);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_OrgTrx))
			{
				GridField field = m_mTab.getField("AD_OrgTrx_ID");
				f_AD_OrgTrx_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_AD_OrgTrx_ID, isMandatory);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Activity))
			{
				GridField field = m_mTab.getField("C_Activity_ID");
				f_C_Activity_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_C_Activity_ID, isMandatory);
			}
			//	User1
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_UserList1))
			{
				GridField field = m_mTab.getField("User1_ID");
				f_User1_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_User1_ID, isMandatory);
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_UserList2))
			{
				GridField field = m_mTab.getField("User2_ID");
				f_User2_ID = WebEditorFactory.getEditor(field, false);
				addLine(field, f_User2_ID, isMandatory);
			}
		}	//	Create Fields in Element Order

		//	Add description
		m_newRow = true;
		Row row = new Row();
		f_Description.setStyle("font-decoration: italic;");
		row.appendChild(f_Description);
		row.setSpans("4");
		row.setStyle("background-color: transparent;");
		m_rows.appendChild(row);

		//	Finish
		m_query = new MQuery();
		m_query.addRestriction("C_AcctSchema_ID", MQuery.EQUAL, m_C_AcctSchema_ID);
		m_query.addRestriction("IsFullyQualified", MQuery.EQUAL, "Y");
		if (m_mAccount.getC_ValidCombination_ID() == 0)
			m_mTab.setQuery(MQuery.getEqualQuery("1", "2"));
		else
		{
			MQuery query = new MQuery();
			query.addRestriction("C_AcctSchema_ID", MQuery.EQUAL, m_C_AcctSchema_ID);
			query.addRestriction("C_ValidCombination_ID", MQuery.EQUAL, m_mAccount.getC_ValidCombination_ID());
			m_mTab.setQuery(query);
		}
		m_mTab.query(false);
		m_adTabPanel.getGridTab().addDataStatusListener(this);
		m_adTabPanel.activate(true);
		if (!m_adTabPanel.isGridView())
			m_adTabPanel.switchRowPresentation();

		statusBar.setStatusLine(s_AcctSchema.toString());
		statusBar.setStatusDB("?");

		//	Initial value
		if (m_mAccount.getC_ValidCombination_ID() != 0)
			m_mTab.navigate(0);

		log.config("fini");
		return true;
	}	//	initAccount

	/**
	 *	Add Editor to parameterPanel alernative right/left depending on m_newRow.
	 *  Field Value changes update Editors
	 *  @param field field
	 *  @param editor editor
	 *  @param mandatory mandatory
	 */
	private void addLine (GridField field, WEditor editor, boolean mandatory)
	{
		log.fine("Field=" + field);
		Label label = editor.getLabel();
		editor.setReadWrite(true);
		editor.setMandatory(mandatory);
		//  MField => VEditor
		field.addPropertyChangeListener(editor);

		//	label
		if (m_newRow)
		{
			m_row = new Row();
			m_row.setStyle("background-color: transparent");
			m_rows.appendChild(m_row);
		}
//		else
//			m_gbc.gridx = 2;
		Div div = new Div();
		div.setStyle("text-align: right");
		div.appendChild(label);
		m_row.appendChild(div);

		m_row.appendChild(editor.getComponent());
		editor.dynamicDisplay();
		//
		m_newRow = !m_newRow;
	}	//	addLine

	/**
	 *	Load Information
	 *  @param C_ValidCombination_ID valid combination
	 *  @param C_AcctSchema_ID acct schema
	 */
	private void loadInfo (int C_ValidCombination_ID, int C_AcctSchema_ID)
	{
		log.fine("C_ValidCombination_ID=" + C_ValidCombination_ID);
		String sql = "SELECT * FROM C_ValidCombination WHERE C_ValidCombination_ID=? AND C_AcctSchema_ID=?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_ValidCombination_ID);
			pstmt.setInt(2, C_AcctSchema_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				if (f_Alias != null)
					f_Alias.setValue(rs.getString("Alias"));
				f_Combination.setValue(rs.getString("Combination"));
				//
				loadInfoOf (rs, f_AD_Org_ID, "AD_Org_ID");
				loadInfoOf (rs, f_Account_ID, "Account_ID");
				loadInfoOf (rs, f_SubAcct_ID, "C_SubAcct_ID");
				//
				loadInfoOf (rs, f_M_Product_ID, "M_Product_ID");
				loadInfoOf (rs, f_C_BPartner_ID, "C_BPartner_ID");
				loadInfoOf (rs, f_C_Campaign_ID, "C_Campaign_ID");
				loadInfoOf (rs, f_C_LocFrom_ID, "C_LocFrom_ID");
				loadInfoOf (rs, f_C_LocTo_ID, "C_LocTo_ID");
				loadInfoOf (rs, f_C_Project_ID, "C_Project_ID");
				loadInfoOf (rs, f_C_SalesRegion_ID, "C_SalesRegion_ID");
				loadInfoOf (rs, f_AD_OrgTrx_ID, "AD_OrgTrx_ID");
				loadInfoOf (rs, f_C_Activity_ID, "C_Activity_ID");
				loadInfoOf (rs, f_User1_ID, "User1_ID");
				loadInfoOf (rs, f_User2_ID, "User2_ID");
				//
				f_Description.setValue (rs.getString("Description"));
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
	}	//	loadInfo

	/**
	 *	Set Value of Editor
	 *  @param rs result set
	 *  @param editor editor
	 *  @param name name
	 *  @throws SQLException
	 */
	private void loadInfoOf (ResultSet rs, WEditor editor, String name) throws SQLException
	{
		if (editor == null)
			return;
		int intValue = rs.getInt(name);
		if (rs.wasNull())
			editor.setValue(null);
		else
			editor.setValue(new Integer (intValue));
	}	//	loadInfoOf


	/**
	 *	dispose
	 */
	@Override
	public void dispose()
	{
		saveSelection();
		//  GridController
		if (m_adTabPanel != null)
			m_adTabPanel.detach();
		m_adTabPanel = null;
		//  Model
		m_mTab = null;
		if (m_mWindow != null)
			m_mWindow.dispose();
		m_mWindow = null;

		Env.clearWinContext(m_WindowNo);
		this.onClose();
	}	//	dispose

	/**
	 *	Save Selection
	 */
	private void saveSelection()
	{
		if (m_changed && m_adTabPanel != null)
		{
			int row = m_adTabPanel.getGridTab().getCurrentRow();
			if (row >= 0)
				m_C_ValidCombination_ID = ((Integer)m_mTab.getValue(row, "C_ValidCombination_ID")).intValue();
			log.config("(" + row + ") - " + m_C_ValidCombination_ID);
		}
	}	//	saveSelection

	@Override
	public void onEvent(Event event) throws Exception {
		if (event.getTarget().getId().equals("Ok"))
		{
			m_changed = true;
			dispose();
		}
		else if (event.getTarget().getId().equals("Cancel"))
		{
			m_changed = false;
			dispose();
		}
		//
		else if (event.getTarget() == bSave)
			action_Save();
		else if (event.getTarget() == bIgnore)
			action_Ignore();
		//	all other
		else
			action_Find (true);
	}

	/**
	 *	Status Change Listener
	 *  @param e event
	 */
	@Override
	public void dataStatusChanged (DataStatusEvent e)
	{
		log.config(e.toString());
		String info = (String)m_mTab.getValue("Description");
		if (Executions.getCurrent() != null)
			f_Description.setValue (info);
	}	//	statusChanged


	/**
	 *	Action Find.
	 *	- create where clause
	 *	- query database
	 *  @param includeAliasCombination include alias combination
	 */
	private void action_Find (boolean includeAliasCombination)
	{
		log.info("");

		//	Create where Clause
		MQuery query = null;
		if (m_query != null)
			query = m_query.deepCopy();
		else
			query = new MQuery();
		//	Alias
		if (includeAliasCombination && f_Alias != null && f_Alias.getValue().toString().length() > 0)
		{
			String value = f_Alias.getValue().toString().toUpperCase();
			if (!value.endsWith("%"))
				value += "%";
			query.addRestriction("UPPER(Alias)", MQuery.LIKE, value);
		}
		//	Combination (mandatory)
		if (includeAliasCombination && f_Combination.getValue().toString().length() > 0)
		{
			String value = f_Combination.getValue().toString().toUpperCase();
			if (!value.endsWith("%"))
				value += "%";
			query.addRestriction("UPPER(Combination)", MQuery.LIKE, value);
		}
		//	Org (mandatory)
		if (f_AD_Org_ID != null && f_AD_Org_ID.getValue() != null)
			query.addRestriction("AD_Org_ID", MQuery.EQUAL, f_AD_Org_ID.getValue());
		//	Account (mandatory)
		if (f_Account_ID != null && f_Account_ID.getValue() != null)
			query.addRestriction("Account_ID", MQuery.EQUAL, f_Account_ID.getValue());
		if (f_SubAcct_ID != null && f_SubAcct_ID.getValue() != null)
			query.addRestriction("C_SubAcct_ID", MQuery.EQUAL, f_SubAcct_ID.getValue());

		//	Product
		if (f_M_Product_ID != null && f_M_Product_ID.getValue() != null)
			query.addRestriction("M_Product_ID", MQuery.EQUAL, f_M_Product_ID.getValue());
		//	BPartner
		if (f_C_BPartner_ID != null && f_C_BPartner_ID.getValue() != null)
			query.addRestriction("C_BPartner_ID", MQuery.EQUAL, f_C_BPartner_ID.getValue());
		//	Campaign
		if (f_C_Campaign_ID != null && f_C_Campaign_ID.getValue() != null)
			query.addRestriction("C_Campaign_ID", MQuery.EQUAL, f_C_Campaign_ID.getValue());
		//	Loc From
		if (f_C_LocFrom_ID != null && f_C_LocFrom_ID.getValue() != null)
			query.addRestriction("C_LocFrom_ID", MQuery.EQUAL, f_C_LocFrom_ID.getValue());
		//	Loc To
		if (f_C_LocTo_ID != null && f_C_LocTo_ID.getValue() != null)
			query.addRestriction("C_LocTo_ID", MQuery.EQUAL, f_C_LocTo_ID.getValue());
		//	Project
		if (f_C_Project_ID != null && f_C_Project_ID.getValue() != null)
			query.addRestriction("C_Project_ID", MQuery.EQUAL, f_C_Project_ID.getValue());
		//	SRegion
		if (f_C_SalesRegion_ID != null && f_C_SalesRegion_ID.getValue() != null)
			query.addRestriction("C_SalesRegion_ID", MQuery.EQUAL, f_C_SalesRegion_ID.getValue());
		//	Org Trx
		if (f_AD_OrgTrx_ID != null && f_AD_OrgTrx_ID.getValue() != null)
			query.addRestriction("AD_OrgTrx_ID", MQuery.EQUAL, f_AD_OrgTrx_ID.getValue());
		//	Activity
		if (f_C_Activity_ID != null && f_C_Activity_ID.getValue() != null)
			query.addRestriction("C_Activity_ID", MQuery.EQUAL, f_C_Activity_ID.getValue());
		//	User 1
		if (f_User1_ID != null && f_User1_ID.getValue() != null)
			query.addRestriction("User1_ID", MQuery.EQUAL, f_User1_ID.getValue());
		//	User 2
		if (f_User2_ID != null && f_User2_ID.getValue() != null)
			query.addRestriction("User2_ID", MQuery.EQUAL, f_User2_ID.getValue());

		//	Query
		m_mTab.setQuery(query);
		m_mTab.query(false);
		statusBar.setStatusDB(String.valueOf(m_mTab.getRowCount()));
	}	//	action_Find


	/**
	 *	Create/Save Account
	 */
	private void action_Save()
	{
		log.info("");
		/**
		 *	Check completeness (mandatory fields) ... and for duplicates
		 */
		StringBuffer sb = new StringBuffer();
		StringBuffer sql = new StringBuffer ("SELECT C_ValidCombination_ID, Alias FROM C_ValidCombination WHERE ");
		Object value = null;
		if (s_AcctSchema.isHasAlias())
		{
			value = f_Alias.getValue().toString();
			if (isEmpty(value) && f_Alias.isMandatory())
				sb.append(Msg.translate(Env.getCtx(), "Alias")).append(", ");
		}
		MAcctSchemaElement[] elements = s_AcctSchema.getAcctSchemaElements();
		for (int i = 0; i < elements.length; i++)
		{
			MAcctSchemaElement ase = elements[i];
			String type = ase.getElementType();
			//
			if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Organization))
			{
				value = f_AD_Org_ID.getValue();
				sql.append("AD_Org_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Account))
			{
				value = f_Account_ID.getValue();
				sql.append("Account_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_SubAccount))
			{
				value = f_SubAcct_ID.getValue();
				sql.append("C_SubAcct_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Product))
			{
				value = f_M_Product_ID.getValue();
				sql.append("M_Product_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_BPartner))
			{
				value = f_C_BPartner_ID.getValue();
				sql.append("C_BPartner_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Campaign))
			{
				value = f_C_Campaign_ID.getValue();
				sql.append("C_Campaign_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_LocationFrom))
			{
				value = f_C_LocFrom_ID.getValue();
				sql.append("C_LocFrom_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_LocationTo))
			{
				value = f_C_LocTo_ID.getValue();
				sql.append("C_LocTo_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Project))
			{
				value = f_C_Project_ID.getValue();
				sql.append("C_Project_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_SalesRegion))
			{
				value = f_C_SalesRegion_ID.getValue();
				sql.append("C_SalesRegion_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_OrgTrx))
			{
				value = f_AD_OrgTrx_ID.getValue();
				sql.append("AD_OrgTrx_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_Activity))
			{
				value = f_C_Activity_ID.getValue();
				sql.append("C_Activity_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_UserList1))
			{
				value = f_User1_ID.getValue();
				sql.append("User1_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			else if (type.equals(MAcctSchemaElement.ELEMENTTYPE_UserList2))
			{
				value = f_User2_ID.getValue();
				sql.append("User2_ID");
				if (isEmpty(value))
					sql.append(" IS NULL AND ");
				else
					sql.append("=").append(value).append(" AND ");
			}
			//
			if (ase.isMandatory() && isEmpty(value))
				sb.append(ase.getName()).append(", ");
		}	//	Fields in Element Order

		if (sb.length() != 0)
		{
			FDialog.error(m_WindowNo, this, "FillMandatory", sb.substring(0, sb.length()-2));
			return;
		}
		if (f_AD_Org_ID == null || f_AD_Org_ID.getValue() == null)
		{
			FDialog.error(m_WindowNo, this, "FillMandatory", Msg.getElement(Env.getCtx(), "AD_Org_ID"));
			return;
		}
		if (f_Account_ID == null || f_Account_ID.getValue() == null)
		{
			FDialog.error(m_WindowNo, this, "FillMandatory", Msg.getElement(Env.getCtx(), "Account_ID"));
			return;
		}


		/**
		 *	Check if already exists
		 */
		sql.append("AD_Client_ID=? AND C_AcctSchema_ID=?");
		log.fine("Check = " + sql.toString());
		int IDvalue = 0;
		String Alias = null;
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, m_AD_Client_ID);
			pstmt.setInt(2, s_AcctSchema.getC_AcctSchema_ID());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				IDvalue = rs.getInt(1);
				Alias = rs.getString(2);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			IDvalue = 0;
		}
		log.fine("ID=" + IDvalue + ", Alias=" + Alias);

		if (Alias == null)
			Alias = "";

		//	We have an account like this already - check alias
		if (IDvalue != 0 && s_AcctSchema.isHasAlias()
			&& !f_Alias.getValue().toString().equals(Alias))
		{
			sql = new StringBuffer("UPDATE C_ValidCombination SET Alias=");
			if (f_Alias.getValue().toString().length() == 0)
				sql.append("NULL");
			else
				sql.append("'").append(f_Alias.getValue()).append("'");
			sql.append(" WHERE C_ValidCombination_ID=").append(IDvalue);
			int i = 0;
			try
			{
				java.sql.PreparedStatement stmt = DB.prepareStatement(sql.toString(),
						ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, null);
				i = stmt.executeUpdate();
				stmt.close();
			}
			catch (SQLException e)
			{
				log.log(Level.SEVERE, sql.toString(), e);
			}
			if (i == 0)
				FDialog.error(m_WindowNo, this, "AccountNotUpdated");
		}

		//	load and display
		if (IDvalue != 0)
		{
			loadInfo (IDvalue, s_AcctSchema.getC_AcctSchema_ID());
			action_Find (false);
			return;
		}

		log.config("New");
		Alias = null;
		if (f_Alias != null)
			Alias = f_Alias.getValue().toString();
		int C_SubAcct_ID = 0;
		if (f_SubAcct_ID != null && !isEmpty(f_SubAcct_ID.getValue()))
			C_SubAcct_ID = ((Integer)f_SubAcct_ID.getValue()).intValue();
		int M_Product_ID = 0;
		if (f_M_Product_ID != null && !isEmpty(f_M_Product_ID.getValue()))
			M_Product_ID = ((Integer)f_M_Product_ID.getValue()).intValue();
		int C_BPartner_ID = 0;
		if (f_C_BPartner_ID != null && !isEmpty(f_C_BPartner_ID.getValue()))
			C_BPartner_ID = ((Integer)f_C_BPartner_ID.getValue()).intValue();
		int AD_OrgTrx_ID = 0;
		if (f_AD_OrgTrx_ID != null && !isEmpty(f_AD_OrgTrx_ID.getValue()))
			AD_OrgTrx_ID = ((Integer)f_AD_OrgTrx_ID.getValue()).intValue();
		int C_LocFrom_ID = 0;
		if (f_C_LocFrom_ID != null && !isEmpty(f_C_LocFrom_ID.getValue()))
			C_LocFrom_ID = ((Integer)f_C_LocFrom_ID.getValue()).intValue();
		int C_LocTo_ID = 0;
		if (f_C_LocTo_ID != null && !isEmpty(f_C_LocTo_ID.getValue()))
			C_LocTo_ID = ((Integer)f_C_LocTo_ID.getValue()).intValue();
		int C_SRegion_ID = 0;
		if (f_C_SalesRegion_ID != null && !isEmpty(f_C_SalesRegion_ID.getValue()))
			C_SRegion_ID = ((Integer)f_C_SalesRegion_ID.getValue()).intValue();
		int C_Project_ID = 0;
		if (f_C_Project_ID != null && !isEmpty(f_C_Project_ID.getValue()))
			C_Project_ID=  ((Integer)f_C_Project_ID.getValue()).intValue();
		int C_Campaign_ID = 0;
		if (f_C_Campaign_ID != null && !isEmpty(f_C_Campaign_ID.getValue()))
			C_Campaign_ID = ((Integer)f_C_Campaign_ID.getValue()).intValue();
		int C_Activity_ID = 0;
		if (f_C_Activity_ID != null && !isEmpty(f_C_Activity_ID.getValue()))
			C_Activity_ID = ((Integer)f_C_Activity_ID.getValue()).intValue();
		int User1_ID = 0;
		if (f_User1_ID != null && !isEmpty(f_User1_ID.getValue()))
			User1_ID = ((Integer)f_User1_ID.getValue()).intValue();
		int User2_ID = 0;
		if (f_User2_ID != null && !isEmpty(f_User2_ID.getValue()))
			User2_ID = ((Integer)f_User2_ID.getValue()).intValue();

		final AccountDimension acctDim = AccountDimension.builder()
				.setC_AcctSchema_ID(s_AcctSchema.getC_AcctSchema_ID())
				.setAD_Client_ID(m_AD_Client_ID)
				.setAD_Org_ID(((Integer)f_AD_Org_ID.getValue()).intValue())
				.setC_ElementValue_ID(((Integer)f_Account_ID.getValue()).intValue())
				.setC_SubAcct_ID(C_SubAcct_ID)
				.setM_Product_ID(M_Product_ID)
				.setC_BPartner_ID(C_BPartner_ID)
				.setAD_OrgTrx_ID(AD_OrgTrx_ID)
				.setC_LocFrom_ID(C_LocFrom_ID)
				.setC_LocTo_ID(C_LocTo_ID)
				.setC_SalesRegion_ID(C_SRegion_ID)
				.setC_Project_ID(C_Project_ID)
				.setC_Campaign_ID(C_Campaign_ID)
				.setC_Activity_ID(C_Activity_ID)
				.setUser1_ID(User1_ID)
				.setUser2_ID(User2_ID)
				.build();
		MAccount acct = MAccount.get (Env.getCtx(), acctDim);
		if (acct != null && acct.get_ID() == 0)
			acct.save();

		//  Show Info
		if (acct == null || acct.get_ID() == 0)
			loadInfo (0, 0);
		else
		{
			//	Update Account with optional Alias
			if (Alias != null && Alias.length() > 0)
			{
				acct.setAlias(Alias);
				acct.save();
			}
			loadInfo (acct.get_ID(), s_AcctSchema.getC_AcctSchema_ID());
		}
		action_Find (false);
	}	//	action_Save

	private boolean isEmpty(Object value) {
		if (value == null)
			return true;

		if (value instanceof String)
			return ((String)value).trim().length() == 0;

		return false;
	}


	/**
	 *	Ignore
	 */
	private void action_Ignore()
	{
		if (f_Alias != null)
			f_Alias.setValue("");
		f_Combination.setValue("");
		f_Description.setValue("");
		//
		//	Org (mandatory)
		f_AD_Org_ID.setValue(null);
		//	Account (mandatory)
		f_Account_ID.setValue(null);
		if (f_SubAcct_ID != null)
			f_SubAcct_ID.setValue(null);

		//	Product
		if (f_M_Product_ID != null)
			f_M_Product_ID.setValue(null);
		//	BPartner
		if (f_C_BPartner_ID != null)
			f_C_BPartner_ID.setValue(null);
		//	Campaign
		if (f_C_Campaign_ID != null)
			f_C_Campaign_ID.setValue(null);
		//	Loc From
		if (f_C_LocFrom_ID != null)
			f_C_LocFrom_ID.setValue(null);
		//	Loc To
		if (f_C_LocTo_ID != null)
			f_C_LocTo_ID.setValue(null);
		//	Project
		if (f_C_Project_ID != null)
			f_C_Project_ID.setValue(null);
		//	SRegion
		if (f_C_SalesRegion_ID != null)
			f_C_SalesRegion_ID.setValue(null);
		//	Org Trx
		if (f_AD_OrgTrx_ID != null)
			f_AD_OrgTrx_ID.setValue(null);
		//	Activity
		if (f_C_Activity_ID != null)
			f_C_Activity_ID.setValue(null);
		//	User 1
		if (f_User1_ID != null)
			f_User1_ID.setValue(null);
		//	User 2
		if (f_User2_ID != null)
			f_User2_ID.setValue(null);
	}	//	action_Ignore

	/**
	 *	Get selected account
	 *  @return account
	 */
	public Integer getValue()
	{
		log.config("C_ValidCombination_ID=" + m_C_ValidCombination_ID + ", Changed=" + m_changed);
		if (!m_changed || m_C_ValidCombination_ID == 0)
			return null;
		return new Integer(m_C_ValidCombination_ID);
	}

	/**
	 * 	valueChange - Account Changed
	 *	@param evt event
	 */
	@Override
	public void valueChange(ValueChangeEvent evt) {
		Object newValue = evt.getNewValue();
		if (newValue instanceof Integer)
			Env.setContext(Env.getCtx(), m_WindowNo, "Account_ID", ((Integer)newValue).intValue());
	}
}	//	WAccountDialog

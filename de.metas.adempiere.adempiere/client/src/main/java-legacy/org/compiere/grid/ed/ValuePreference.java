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
package org.compiere.grid.ed;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.permissions.UserPreferenceLevelConstraint;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.images.Images;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.GridField;
import org.compiere.model.I_AD_Preference;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 *  Maintain Value Preferences.
 *  To delete a preference, select a null value and save.
 *
 *  @author Jorg Janke
 *  @version  $Id: ValuePreference.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class ValuePreference extends CDialog
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6718115525965508049L;

	/**
	 *  Factory
	 *  @param mField	field
	 *  @param aValue	value
	 *  @return ValuePreference or null
	 */
	public static ValuePreference start (GridField mField, Object aValue)
	{
		return start (mField, aValue, null);
	}   //  start

	/**
	 *  Factory
	 *  @param mField	field
	 *  @param aValue	value
	 *  @param aDisplayValue	display value
	 *  @return ValuePreference or null
	 */
	public static ValuePreference start (GridField mField, Object aValue, String aDisplayValue)
	{
		if (!mField.isEditable(false))
		{
			log.info("Field not editable (R/O)");
			return null;
		}
		
		//  Set Value/DisplayValue
		String Value = null;
		String DisplayValue = null;
		if (aValue != null)
		{
			Value = aValue.toString();
			DisplayValue = (aDisplayValue == null) ? Value : aDisplayValue;
		}

		//  Get from mField
		//  AD_Window_ID, DisplayAttribute, Attribute, DisplayType, AD_Referenece_ID
		int AD_Window_ID = mField.getAD_Window_ID();
		String Attribute = mField.getColumnName();
		String DisplayAttribute = mField.getHeader();
		int displayType = mField.getDisplayType();
		int AD_Reference_ID = 0;
		int WindowNo = mField.getWindowNo();

		//  Get from Environment (WindowNo)
		//  AD_Client_ID, AD_Org_ID, AD_User_ID, Frame
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		int AD_Org_ID = Env.getContextAsInt(Env.getCtx(), WindowNo, "AD_Org_ID");
		int AD_User_ID = Env.getAD_User_ID(Env.getCtx());
		Frame frame = Env.getWindow(WindowNo);

		//  Create Editor
		ValuePreference vp = new ValuePreference (frame, WindowNo,
			AD_Client_ID, AD_Org_ID, AD_User_ID, AD_Window_ID,
			Attribute, DisplayAttribute, Value, DisplayValue,
			displayType, AD_Reference_ID);
		return vp;
	}   //  create

	/**
	 *  Create the popup menu item to start the ValuePreference editor.
	 *  <code>
	 *  .. add method
	 *  public void setField (MField mField)
	 *  {
	 *      m_mField = mField;
	 *      if (m_mField != null)
	 *          ValuePreference.addMenu (this, m_popupMenu);
	 *	}   //  setField
	 *
	 *  .. in actionPerformed add ..
	 *  if (e.getActionCommand().equals(ValuePreference.NAME))
	 *  {
	 *      ValuePreference.start (m_mField, getValue(), DisplayValue);
	 *      return;
	 *  }
	 *  </code>
	 *  @param l listener
	 *  @param popupMenu menu
	 *  @return JMenuItem
	 */
	public static CMenuItem addMenu (ActionListener l, JPopupMenu popupMenu)
	{
		CMenuItem mi = new CMenuItem (Msg.getMsg(Env.getCtx(), NAME), s_icon);
		mi.setActionCommand(NAME);
		mi.addActionListener(l);
		popupMenu.add(mi);
		return mi;
	}   //  addMenu

	/** The Name of the Editor      */
	public static final String      NAME = "ValuePreference";
	/** The Menu Icon               */
	private static Icon             s_icon = Images.getImageIcon2("VPreference16");
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(ValuePreference.class);
	
	/**
	 *  Constructor
	 *
	 *  @param frame parent
	 *  @param WindowNo window no
	 *  @param AD_Client_ID client
	 *  @param AD_Org_ID org
	 *  @param AD_User_ID user
	 *  @param AD_Window_ID window id
	 *  @param Attribute attribute
	 *  @param DisplayAttribute attribute display
	 *  @param Value value
	 *  @param DisplayValue calue display
	 *  @param displayType display type
	 *  @param AD_Reference_ID reference
	 */
	public ValuePreference (Frame frame, int WindowNo,
		int AD_Client_ID, int AD_Org_ID, int AD_User_ID, int AD_Window_ID,
		String Attribute, String DisplayAttribute, String Value, String DisplayValue,
		int displayType, int AD_Reference_ID)
	{
		super(frame, Msg.getMsg(Env.getCtx(), NAME) + " " + DisplayAttribute, true);
		log.info("WindowNo=" + WindowNo
			+ ", Client_ID=" + AD_Client_ID + ", Org_ID=" + AD_Org_ID + ", User_ID=" + AD_User_ID + ", Window_ID=" + AD_Window_ID
			+ ",  Attribute=" + Attribute + "/" + DisplayAttribute + ",  Value=" + Value + "/" + DisplayValue
			+ ",  DisplayType=" + displayType + ", Reference_ID=" + AD_Reference_ID);
		m_ctx = Env.getCtx();
		m_WindowNo = WindowNo;
		m_AD_Client_ID = AD_Client_ID;
		m_AD_Org_ID = AD_Org_ID;
		m_AD_User_ID = AD_User_ID;
		m_AD_Window_ID = AD_Window_ID;
		m_Attribute = Attribute;
		m_DisplayAttribute = DisplayAttribute;
		m_Value = Value;
		m_DisplayValue = DisplayValue;
		m_DisplayType = displayType;
		//
		m_role = Env.getUserRolePermissions();
		try
		{
			jbInit();
			dynInit();
		}
		catch(Exception ex)
		{
			log.error("", ex);
		}
		AEnv.showCenterScreen(this);
	}   //  ValuePreference

	private Properties      m_ctx;
	private int             m_WindowNo;
	private int             m_AD_Client_ID;
	private int             m_AD_Org_ID;
	private int             m_AD_User_ID;
	private int             m_AD_Window_ID;
	private String          m_Attribute;
	private String          m_DisplayAttribute;
	private String          m_Value;
	private String          m_DisplayValue;
	private int             m_DisplayType;
	private IUserRolePermissions m_role;

	//  Display
	private CPanel setPanel = new CPanel();
	private GridBagLayout setLayout = new GridBagLayout();
	private CLabel lAttribute = new CLabel();
	private CTextField fAttribute = new CTextField();
	private CLabel lAttributeValue = new CLabel();
	private CLabel lValue = new CLabel();
	private CLabel lValueValue = new CLabel();
	private CTextField fValue = new CTextField();
	private CLabel lSetFor = new CLabel();
	private VCheckBox cbClient = new VCheckBox();
	private VCheckBox cbOrg = new VCheckBox();
	private VCheckBox cbUser = new VCheckBox();
	private VCheckBox cbWindow = new VCheckBox();
	private CLabel lExplanation = new CLabel();
	private CPanel currentPanel = new CPanel();
	private TitledBorder titledBorder;
	private JScrollPane scrollPane = new JScrollPane();
	private BorderLayout currentLayout = new BorderLayout();
	private JTable table = new JTable();

	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	private JButton bDelete;

	/**
	 *  Static Layout
	 *  @throws Exception
	 */
	void jbInit() throws Exception
	{
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		titledBorder = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),
			Msg.getMsg(m_ctx, "CurrentSettings"));
		//
		lAttribute.setText(Msg.translate(m_ctx, "Attribute"));
		lValue.setText(Msg.translate(m_ctx, "Value"));
		lSetFor.setText(Msg.getMsg(m_ctx, "ValuePreferenceSetFor"));
		cbClient.setText(Msg.translate(m_ctx, "AD_Client_ID"));
		cbOrg.setText(Msg.translate(m_ctx, "AD_Org_ID"));
		cbUser.setText(Msg.translate(m_ctx, "AD_User_ID"));
		cbUser.setSelected(true);
		cbWindow.setText(Msg.translate(m_ctx, "AD_Window_ID"));
		cbWindow.setSelected(true);
		//
		setPanel.setLayout(setLayout);
		fAttribute.setEditable(false);
		fValue.setEditable(false);
		this.getContentPane().add(setPanel,  BorderLayout.NORTH);
		setPanel.add(lAttribute,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		setPanel.add(fAttribute,       new GridBagConstraints(1, 0, 4, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		setPanel.add(lValue,     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		setPanel.add(fValue,     new GridBagConstraints(1, 1, 4, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		setPanel.add(lSetFor,    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		setPanel.add(cbClient,      new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		setPanel.add(cbOrg,    new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		setPanel.add(cbUser,   new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		setPanel.add(cbWindow,   new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		setPanel.add(lAttributeValue,  new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		setPanel.add(lValueValue,  new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		setPanel.add(lExplanation,  new GridBagConstraints(1, 3, 4, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		//
		currentPanel.setBorder(titledBorder);
		currentPanel.setLayout(currentLayout);
	//	this.getContentPane().add(currentPanel, BorderLayout.CENTER);
		currentPanel.add(scrollPane,  BorderLayout.CENTER);
		scrollPane.getViewport().add(table, null);
		this.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
	}   //  jbInit

	/**
	 *  Dynamic Init
	 */
	private void dynInit()
	{
		//  Set Attribute/Value
		fAttribute.setText(m_DisplayAttribute);
		lAttributeValue.setText(m_Attribute);
		fValue.setText(m_DisplayValue);
		lValueValue.setText(m_Value);
		if (LogManager.isLevelFine())
		{
			lAttributeValue.setVisible(false);
			lValueValue.setVisible(false);
		}

		//  ActionListener
		cbClient.setEnabled(false);
		cbClient.setSelected(true);
	//	cbClient.addActionListener(this);
		
		//	Can Change Org
		final UserPreferenceLevelConstraint preferenceLevel = m_role.getPreferenceLevel();
		if (preferenceLevel.isClient())
		{
			cbOrg.addActionListener(this);
		}
		else
		{
			cbOrg.setEnabled(false);
			cbOrg.setSelected(true);
		}
		
		//	Can Change User
		if (preferenceLevel.isClient() || preferenceLevel.isOrganization())
		{
			cbUser.addActionListener(this);
		}
		else
		{
			cbUser.setEnabled(false);
			cbUser.setSelected(true);
		}
		//	Can change all/specific
		cbWindow.addActionListener(this);

		//  Other
		confirmPanel.setActionListener(this);
		bDelete = confirmPanel.addButton(ConfirmPanel.createDeleteButton(true));
		bDelete.addActionListener(this);
		setExplanation();
	}   //  dynInit

	/**
	 *  Action Listener
	 *  @param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			insert();
			dispose();
		}
		else if (e.getSource() == bDelete)
		{
			int no = delete();
			if (no == 0)
				ADialog.warn(m_WindowNo, this, "ValuePreferenceNotFound");
			else
				ADialog.info(m_WindowNo, this, "ValuePreferenceDeleted", String.valueOf(no));
			dispose();
		}
		else
			setExplanation();
	}   //  actionPerformed

	/**
	 *  Set Explanation
	 */
	private void setExplanation()
	{
		/** @todo translation */
		StringBuffer expl = new StringBuffer("For ");
		if (cbClient.isSelected() && cbOrg.isSelected())
			expl.append("this Client and Organization");
		else if (cbClient.isSelected() && !cbOrg.isSelected())
			expl.append("all Organizations of this Client");
		else if (!cbClient.isSelected() && cbOrg.isSelected())
		{
			cbOrg.setSelected(false);
			expl.append("entire System");
		}
		else
			expl.append("entire System");
		//
		if (cbUser.isSelected())
			expl.append(", this User");
		else
			expl.append(", all Users");
		//
		if (cbWindow.isSelected())
			expl.append(" and this Window");
		else
			expl.append(" and all Windows");
		//
		if (Env.getLanguage(Env.getCtx()).isBaseLanguage())
		{
			lExplanation.setText (expl.toString ());
			this.pack ();
		}
	}   //  setExplanation

	/**
	 *  Delete Preference
	 *  @return number of rows deleted
	 */
	public int delete()
	{
		log.info("");

		StringBuffer sql = new StringBuffer ("DELETE FROM AD_Preference WHERE ");
		sql.append("AD_Client_ID=").append(cbClient.isSelected() ? m_AD_Client_ID : 0);
		sql.append(" AND AD_Org_ID=").append(cbOrg.isSelected() ? m_AD_Org_ID : 0);
		if (cbUser.isSelected())
			sql.append(" AND AD_User_ID=").append(m_AD_User_ID);
		else
			sql.append(" AND AD_User_ID IS NULL");
		if (cbWindow.isSelected())
			sql.append(" AND AD_Window_ID=").append(m_AD_Window_ID);
		else
			sql.append(" AND AD_Window_ID IS NULL");
		sql.append(" AND Attribute='").append(m_Attribute).append("'");
		//
		log.debug( sql.toString());
		int no = DB.executeUpdate(sql.toString(), null);
		if (no > 0)
			Env.setContext(m_ctx, getContextKey(), (String)null);
		return no;
	}   //  delete

	/**
	 *  Get Context Key
	 *  @return Context Key
	 */
	private String getContextKey()
	{
		if (cbWindow.isSelected())
			return "P" + m_AD_Window_ID + "|" + m_Attribute;
		else
			return "P|" + m_Attribute;
	}   //  getContextKey

	/**
	 *  Save to Disk
	 */
	public void insert()
	{
		log.info("");

		//  --- Delete first
		int no = delete();
		
		//	Handle NULL
		if (m_Value == null || m_Value.length() == 0)
		{
			if (DisplayType.isLookup(m_DisplayType))
				m_Value = "-1";	//	 -1 may cause problems (BPartner - M_DiscountSchema
			else if (DisplayType.isDate(m_DisplayType))
				m_Value = " ";
			else
			{
				ADialog.warn(m_WindowNo, this, "ValuePreferenceNotInserted");
				return;
			}
		}

		//  --- Inserting
		int Client_ID = cbClient.isSelected() ? m_AD_Client_ID : 0;
		int Org_ID = cbOrg.isSelected() ? m_AD_Org_ID : 0;
		int AD_Preference_ID = DB.getNextID(m_ctx, I_AD_Preference.Table_Name, ITrx.TRXNAME_None);
		//
		StringBuffer sql = new StringBuffer ("INSERT INTO AD_Preference ("
			+ "AD_Preference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created,CreatedBy,Updated,UpdatedBy,"
			+ "AD_Window_ID, AD_User_ID, Attribute, Value) VALUES (");
		sql.append(AD_Preference_ID).append(",").append(Client_ID).append(",").append(Org_ID)
			.append(", 'Y',now(),").append(m_AD_User_ID).append(",now(),").append(m_AD_User_ID).append(", ");
		if (cbWindow.isSelected())
			sql.append(m_AD_Window_ID).append(",");
		else
			sql.append("NULL,") ;
		if (cbUser.isSelected())
			sql.append(m_AD_User_ID).append(",");
		else
			sql.append("NULL,");
		//
		sql.append(DB.TO_STRING(m_Attribute)).append(",").append(DB.TO_STRING(m_Value)).append(")");
		//
		log.debug( sql.toString());
		no = DB.executeUpdate(sql.toString(), null);
		if (no == 1)
		{
			Env.setContext(m_ctx, getContextKey(), m_Value);
			ADialog.info(m_WindowNo, this, "ValuePreferenceInserted");
		}
		else
			ADialog.warn(m_WindowNo, this, "ValuePreferenceNotInserted");

	}   //  insert

}   //  ValuePreference

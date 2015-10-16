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
package org.compiere.db;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.adempiere.images.Images;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.CLogger;
import org.compiere.util.Ini;

/**
 *  Connection Dialog.
 *
 *  @author     Jorg Janke
 *  @author     Marek Mosiewicz<marek.mosiewicz@jotel.com.pl> - support for RMI over HTTP
 *  @version    $Id: CConnectionDialog.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
class CConnectionDialog extends CDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;

	/**
	 *  Connection Dialog using current Connection
	 */
	public CConnectionDialog()
	{
		this (null);
	}   //  CConnectionDialog

	/**
	 *  Connection Dialog
	 *  @param cc Adempiere Connection
	 */
	public CConnectionDialog(CConnection cc)
	{
		super((Frame)null, true);
		try
		{
			jbInit();
			setConnection (cc);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
		AdempierePLAF.showCenterScreen(this);
	}   //  CConnection

	/** Resources							*/
	private static final transient ResourceBundle res = ResourceBundle.getBundle("org.compiere.db.DBRes");
	
	/**	 Default HTTP Port					*/
	public static final String	APPS_PORT_HTTP = "80";
	/** Default RMI Port					*/
	public static final String	APPS_PORT_JNP = "1099";
	/** Connection							*/
	private CConnection 	m_cc = null;
	private CConnection 	m_ccResult = null;
	private boolean 		m_updating = false;
	// private boolean m_saved = false;

	/**	Logger	*/
	private static CLogger	log	= CLogger.getCLogger (CConnectionDialog.class);

	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel centerPanel = new CPanel();
	private CPanel southPanel = new CPanel();
	private CButton bOK = AdempierePLAF.getOKButton();
	private CButton bCancel = AdempierePLAF.getCancelButton();
	private FlowLayout southLayout = new FlowLayout();
	private GridBagLayout centerLayout = new GridBagLayout();
	private CLabel nameLabel = new CLabel();
	private CTextField nameField = new CTextField();
	private CLabel hostLabel = new CLabel();
	private CTextField hostField = new CTextField();
	private CLabel portLabel = new CLabel();
	private CTextField dbPortField = new CTextField();
	private CLabel sidLabel = new CLabel();
	private CTextField sidField = new CTextField();
	private CCheckBox cbFirewall = new CCheckBox();
	private CLabel fwHostLabel = new CLabel();
	private CTextField fwHostField = new CTextField();
	private CLabel fwPortLabel = new CLabel();
	private CTextField fwPortField = new CTextField();
	private CButton bTestDB = new CButton();
	private CLabel dbTypeLabel = new CLabel();
	private CComboBox<String> dbTypeField = new CComboBox<>(Database.DB_NAMES);
	private CCheckBox cbBequeath = new CCheckBox();
	private CLabel appsHostLabel = new CLabel();
	private CTextField appsHostField = new CTextField();
	private CLabel appsPortLabel = new CLabel();
	private CTextField appsPortField = new CTextField();
	private CButton bTestApps = new CButton();
	//private CCheckBox cbOverwrite = new CCheckBox();
	private CLabel dbUidLabel = new CLabel();
	private CTextField dbUidField = new CTextField();
	private JPasswordField dbPwdField = new JPasswordField();

	private boolean isCancel = true;

	/**
	 *  Static Layout
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setTitle(res.getString("CConnectionDialog"));
		this.setIconImage(Images.getImage2("Database16"));
		
		mainPanel.setLayout(mainLayout);
		southPanel.setLayout(southLayout);
		southLayout.setAlignment(FlowLayout.RIGHT);
		centerPanel.setLayout(centerLayout);
		nameLabel.setText(res.getString("Name"));
		nameField.setColumns(30);
		nameField.setReadWrite(false);
		hostLabel.setText(res.getString("DBHost"));
		hostField.setColumns(30);
		portLabel.setText(res.getString("DBPort"));
		dbPortField.setColumns(10);
		sidLabel.setText(res.getString("DBName"));
		cbFirewall.setToolTipText("");
		cbFirewall.setText(res.getString("ViaFirewall"));
		fwHostLabel.setText(res.getString("FWHost"));
		fwHostField.setColumns(30);
		fwPortLabel.setText(res.getString("FWPort"));
		bTestDB.setText(res.getString("TestConnection"));
		bTestDB.setHorizontalAlignment(JLabel.LEFT);
		dbTypeLabel.setText(res.getString("Type"));
		sidField.setColumns(30);
		fwPortField.setColumns(10);
		cbBequeath.setText(res.getString("BequeathConnection"));
		appsHostLabel.setText(res.getString("AppsHost"));
		appsHostField.setColumns(30);
		appsPortLabel.setText(res.getString("AppsPort"));
		appsPortField.setColumns(10);
		bTestApps.setText(res.getString("TestApps"));
		bTestApps.setHorizontalAlignment(JLabel.LEFT);
		//cbOverwrite.setText(res.getString("Overwrite"));
		dbUidLabel.setText(res.getString("DBUidPwd"));
		dbUidField.setColumns(10);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel,  BorderLayout.SOUTH);
		southPanel.add(bCancel, null);
		southPanel.add(bOK, null);
		//
		centerPanel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(12, 12, 5, 5), 0, 0));
		centerPanel.add(nameField,  new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 0, 5, 12), 0, 0));
		centerPanel.add(appsHostLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		centerPanel.add(appsHostField, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 12), 0, 0));
		
		centerPanel.add(appsPortLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(appsPortField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		//
		centerPanel.add(bTestApps, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 12, 0), 0, 0));
		//centerPanel.add(cbOverwrite, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
			//,GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(0, 5, 0, 12), 0, 0));
		//	DB
		centerPanel.add(dbTypeLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		centerPanel.add(dbTypeField, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 0, 0));
		centerPanel.add(cbBequeath, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 12), 0, 0));
		centerPanel.add(hostLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		centerPanel.add(hostField,  new GridBagConstraints(1, 5, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 12), 0, 0));
		centerPanel.add(portLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(dbPortField, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
		centerPanel.add(sidLabel,  new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(sidField,   new GridBagConstraints(1, 7, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 12), 0, 0));
		centerPanel.add(dbUidLabel, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(dbUidField, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
		centerPanel.add(dbPwdField, new GridBagConstraints(2, 8, 1, 1, 1.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 12), 0, 0));
		centerPanel.add(cbFirewall, new GridBagConstraints(1, 9, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 12), 0, 0));
		centerPanel.add(fwHostLabel, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(fwHostField,  new GridBagConstraints(1, 10, 2, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 12), 0, 0));
		centerPanel.add(fwPortLabel, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(fwPortField, new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
		centerPanel.add(bTestDB,  new GridBagConstraints(1, 12, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 12, 0), 0, 0));
		//
		nameField.addActionListener(this);
		appsHostField.addActionListener(this);
		appsPortField.addActionListener(this);
		//cbOverwrite.addActionListener(this);
		bTestApps.addActionListener(this);
		//
		dbTypeField.addActionListener(this);
		hostField.addActionListener(this);
		dbPortField.addActionListener(this);
		sidField.addActionListener(this);
		cbBequeath.addActionListener(this);
		cbFirewall.addActionListener(this);
		fwHostField.addActionListener(this);
		fwPortField.addActionListener(this);
		bTestDB.addActionListener(this);
		bOK.addActionListener(this);
		bCancel.addActionListener(this);
		
		//	Server
		if (!Ini.isClient())
		{
			appsHostLabel.setVisible(false);
			appsHostField.setVisible(false);
			appsPortLabel.setVisible(false);
			appsPortField.setVisible(false);
			bTestApps.setVisible(false);
		}
		else	//	Client
			cbBequeath.setVisible(false);
	}   //  jbInit

	/**
	 *  Set Busy - lock UI
	 *  @param busy busy
	 */
	private void setBusy (boolean busy)
	{
		if (busy)
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		else
			this.setCursor(Cursor.getDefaultCursor());
		m_updating = busy;
	}   //  setBusy

	/**
	 *  Set Connection
	 *  @param cc - if null use current connection
	 */
	public void setConnection (CConnection cc)
	{
		m_cc = cc;
		if (m_cc == null)
		{
			m_cc = CConnection.get();
			m_cc.setName();
		}
		//	Should copy values
		try {
			m_ccResult = (CConnection)m_cc.clone();
		} catch (CloneNotSupportedException e) {
			// should not happen
			e.printStackTrace();
		}
		//
		String type = m_cc.getType();
		if (type == null || type.length() == 0)
			dbTypeField.setSelectedItem(null);
		else
			m_cc.setType(m_cc.getType());   //  sets defaults
		updateInfo();
	}   //  setConnection

	/**
	 *  Get Connection
	 *  @return CConnection
	 */
	public CConnection getConnection()
	{
		return m_ccResult;
	}   //  getConnection;

	/**
	 *  ActionListener
	 *  @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (m_updating)
			return;
		Object src = e.getSource();

		if (src == bOK)
		{
			updateCConnection();
			m_cc.setName();			
			m_ccResult = m_cc;
			dispose();
			isCancel = false;
			return;
		}
		else if (src == bCancel)
		{
			m_cc.setName();
			dispose();
			return;
		}
		else if (src == dbTypeField)
		{
			if (dbTypeField.getSelectedItem() == null)
				return;
		}


		updateCConnection();
		//
		if (src == bTestApps)
			cmd_testApps();

		//  Database Selection Changed
		else if (src == dbTypeField)
		{
			m_cc.setType(dbTypeField.getSelectedItem());
			dbPortField.setText(String.valueOf(m_cc.getDbPort()));
			cbBequeath.setSelected(m_cc.isBequeath());
			fwPortField.setText(String.valueOf(m_cc.getFwPort()));
		}
		//
		else if (src == bTestDB)
		{
			cmd_testDB();
		}

		//  Name
		if (src == nameField)
			m_cc.setName(nameField.getText());

		updateInfo();
	}   //  actionPerformed

	private void updateCConnection() {
		if (Ini.isClient())
		{
			//hengsin: avoid unnecessary requery of application server status
			if (!appsHostField.getText().equals(m_cc.getAppsHost()))
				m_cc.setAppsHost(appsHostField.getText());
			if (!appsPortField.getText().equals(Integer.toString(m_cc.getAppsPort())))
				m_cc.setAppsPort(appsPortField.getText());
		}
		else
			m_cc.setAppsHost("localhost");
		//
		m_cc.setType(dbTypeField.getSelectedItem());
		m_cc.setDbHost(hostField.getText());
		m_cc.setDbPort(dbPortField.getText());
		m_cc.setDbName(sidField.getText());
		m_cc.setDbUid(dbUidField.getText());
		m_cc.setDbPwd(String.valueOf(dbPwdField.getPassword()));
		m_cc.setBequeath(cbBequeath.isSelected());
		m_cc.setViaFirewall(cbFirewall.isSelected());
		m_cc.setFwHost(fwHostField.getText());
		m_cc.setFwPort(fwPortField.getText());
	}

	/**
	 *  Update Fields from Connection
	 */
	private void updateInfo()
	{
		m_updating = true;
		try
		{
			nameField.setText(m_cc.getName());
			appsHostField.setText(m_cc.getAppsHost());
			appsPortField.setText(String.valueOf(m_cc.getAppsPort()));
			//
			bTestApps.setIcon(getStatusIcon(m_cc.isAppsServerOK(false)));
			// bTestApps.setToolTipText(m_cc.getRmiUri());

			// cbOverwrite.setVisible(m_cc.isAppsServerOK(false));
			boolean rw = CConnection.isServerEmbedded() ? true : !m_cc.isAppsServerOK(false);
			//
			dbTypeLabel.setReadWrite(rw);
			dbTypeField.setReadWrite(rw);
			dbTypeField.setSelectedItem(m_cc.getType());
			//
			hostLabel.setReadWrite(rw);
			hostField.setReadWrite(rw);
			hostField.setText(m_cc.getDbHost());
			portLabel.setReadWrite(rw);
			dbPortField.setReadWrite(rw);
			dbPortField.setText(String.valueOf(m_cc.getDbPort()));
			sidLabel.setReadWrite(rw);
			sidField.setReadWrite(rw);
			sidField.setText(m_cc.getDbName());
			//
			dbUidLabel.setReadWrite(rw);
			dbUidField.setReadWrite(rw);
			dbUidField.setText(m_cc.getDbUid());
			dbPwdField.setEditable(rw);
			dbPwdField.setText(m_cc.getDbPwd());
			//
			cbBequeath.setReadWrite(rw);
			cbBequeath.setEnabled(m_cc.isOracle());
			cbBequeath.setSelected(m_cc.isBequeath());
			//
			boolean fwEnabled = rw && m_cc.isViaFirewall() && m_cc.isOracle();
			cbFirewall.setReadWrite(rw && m_cc.isOracle());
			cbFirewall.setSelected(m_cc.isViaFirewall());
			fwHostLabel.setReadWrite(fwEnabled);
			fwHostField.setReadWrite(fwEnabled);
			fwHostField.setText(m_cc.getFwHost());
			fwPortLabel.setReadWrite(fwEnabled);
			fwPortField.setReadWrite(fwEnabled);
			fwPortField.setText(String.valueOf(m_cc.getFwPort()));
			//
			bTestDB.setToolTipText(m_cc.getConnectionURL());
			bTestDB.setIcon(getStatusIcon(m_cc.isDatabaseOK()));
		}
		finally
		{
			m_updating = false;
		}
	}   //  updateInfo

	/**
	 *  Get Status Icon - ok or not
	 *  @param ok ok
	 *  @return Icon
	 */
	private Icon getStatusIcon (boolean ok)
	{
		if (ok)
			return bOK.getIcon();
		else
			return bCancel.getIcon();
	}   //  getStatusIcon

	/**
	 *  Test Database connection
	 */
	private void cmd_testDB()
	{
		setBusy(true);
		try
		{
			m_cc.testDatabase();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(this,
					e,		// message
					res.getString("ConnectionError") + ": " + m_cc.getConnectionURL(),
					JOptionPane.ERROR_MESSAGE);
		}
		finally
		{
			setBusy(false);
		}
	}   //  cmd_testDB

	/**
	 *  Test Application connection
	 */
	private void cmd_testApps()
	{
		setBusy(true);
		try
		{
			Exception e = m_cc.testAppsServer();
			if (e != null)
			{
				JOptionPane.showMessageDialog(this,
						e.getLocalizedMessage(),
						res.getString("ServerNotActive") + " - " + m_cc.getAppsHost(),
						JOptionPane.ERROR_MESSAGE);
			}
		}
		finally
		{
			setBusy(false);
		}
	}   //  cmd_testApps

	public boolean isCancel() {
		return isCancel;
	}

}   //  CConnectionDialog

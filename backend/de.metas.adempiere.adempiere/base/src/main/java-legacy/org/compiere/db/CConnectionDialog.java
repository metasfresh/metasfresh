/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
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

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import lombok.NonNull;
import org.adempiere.images.Images;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.db.connectiondialog.i18n.DBRes;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.util.Check;

/**
 * Connection Dialog.
 *
 * @author Jorg Janke
 * @author Marek Mosiewicz<marek.mosiewicz@jotel.com.pl> - support for RMI over HTTP
 * @version $Id: CConnectionDialog.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class CConnectionDialog extends CDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Connection Dialog
	 *
	 * @param cc metasfresh connection
	 */
	public CConnectionDialog(final CConnection cc)
	{
		super((Frame)null, true);

		try
		{
			jbInit();
			setConnection(cc);
			AdempierePLAF.showCenterScreen(this);
		}
		catch (Exception ex)
		{
			log.error("", ex);
			showError("Error", ex);
			dispose();
			return;
		}
	}   // CConnection

	/** Resources */
	private static final transient ResourceBundle res = ResourceBundle.getBundle(DBRes.class.getName());

	/** Connection */
	private CConnection m_cc = null;
	private CConnection m_ccResult = null;
	private boolean m_updating = false;

	/** Logger */
	private static Logger log = LogManager.getLogger(CConnectionDialog.class);

	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel centerPanel = new CPanel();
	private CPanel southPanel = new CPanel();
	private CButton bOK = AdempierePLAF.getOKButton();
	private CButton bCancel = AdempierePLAF.getCancelButton();
	private FlowLayout southLayout = new FlowLayout();
	private GridBagLayout centerLayout = new GridBagLayout();
	private CLabel hostLabel = new CLabel();
	private CTextField hostField = new CTextField();
	private CLabel portLabel = new CLabel();
	private CTextField dbPortField = new CTextField();
	private CLabel sidLabel = new CLabel();
	private CTextField sidField = new CTextField();

	private CButton bTestDB = new CButton();

	private CLabel dbUidLabel = new CLabel();
	private CTextField dbUidField = new CTextField();
	private JPasswordField dbPwdField = new JPasswordField();

	private boolean isCancel = true;

	/**
	 * Static Layout
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setTitle(res.getString("CConnectionDialog"));
		this.setIconImage(Images.getImage2("Database16"));

		mainPanel.setLayout(mainLayout);
		southPanel.setLayout(southLayout);
		southLayout.setAlignment(FlowLayout.RIGHT);
		centerPanel.setLayout(centerLayout);
		hostLabel.setText(res.getString("DBHost"));
		hostField.setColumns(30);
		portLabel.setText(res.getString("DBPort"));
		dbPortField.setColumns(10);
		sidLabel.setText(res.getString("DBName"));
		bTestDB.setText(res.getString("TestConnection"));
		bTestDB.setHorizontalAlignment(JLabel.LEFT);
		sidField.setColumns(30);

		dbUidLabel.setText(res.getString("DBUidPwd"));
		dbUidField.setColumns(10);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.add(bCancel, null);
		southPanel.add(bOK, null);
		//

		centerPanel.add(hostLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		centerPanel.add(hostField, new GridBagConstraints(1, 5, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 12), 0, 0));
		centerPanel.add(portLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(dbPortField, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
		centerPanel.add(sidLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(sidField, new GridBagConstraints(1, 7, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 12), 0, 0));
		centerPanel.add(dbUidLabel, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		centerPanel.add(dbUidField, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 0), 0, 0));
		centerPanel.add(dbPwdField, new GridBagConstraints(2, 8, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 12), 0, 0));

		centerPanel.add(bTestDB, new GridBagConstraints(1, 12, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 12, 0), 0, 0));
		//
		hostField.addActionListener(this);
		dbPortField.addActionListener(this);
		sidField.addActionListener(this);

		bTestDB.addActionListener(this);
		bOK.addActionListener(this);
		bCancel.addActionListener(this);
	}   // jbInit

	/**
	 * Set Busy - lock UI
	 */
	private void setBusy(boolean busy)
	{
		if (busy)
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		else
			this.setCursor(Cursor.getDefaultCursor());
		m_updating = busy;
	}   // setBusy

	public void setConnection(@NonNull final CConnection cc)
	{
		m_cc = cc;

		// Should copy values
		try
		{
			m_ccResult = (CConnection)m_cc.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// should not happen
			e.printStackTrace();
		}
		updateInfo();
	}   // setConnection

	public CConnection getConnection()
	{
		return m_ccResult;
	}   // getConnection;

	/**
	 * ActionListener
	 */
	@Override
	public void actionPerformed(final ActionEvent event)
	{
		try
		{
			actionPerformed0(event);
		}
		catch (Exception ex)
		{
			showError("Error", ex);
		}
	}

	private void actionPerformed0(final ActionEvent e)
	{
		if (m_updating)
			return;

		final Object src = e.getSource();

		if (src == bOK)
		{
			updateCConnection();

			// Make sure the database connection is OK.
			// Else, there is no point to continue because it will fail a bit later.
			if (!m_cc.isDatabaseOK())
			{
				cmd_testDB();
				updateInfo(); // update the UI fields from "m_cc"
			}
			if (!m_cc.isDatabaseOK())
			{
				// NOTE: we assume an error popup was already displayed to user.
				return;
			}

			m_ccResult = m_cc;
			dispose();
			isCancel = false;
			return;
		}
		else if (src == bCancel)
		{
			dispose();
			return;
		}

		updateCConnection();

		if (src == bTestDB)
		{
			cmd_testDB();
		}

		updateInfo();
	}   // actionPerformed

	private void updateCConnection()
	{
		m_cc.setDbHost(hostField.getText());
		m_cc.setDbPort(dbPortField.getText());
		m_cc.setDbName(sidField.getText());
		m_cc.setDbUid(dbUidField.getText());
		m_cc.setDbPwd(String.valueOf(dbPwdField.getPassword()));
	}

	/**
	 * Update Fields from Connection
	 */
	private void updateInfo()
	{
		m_updating = true;
		try
		{
			final boolean dbSettingsWritable;

			dbSettingsWritable = true;

			hostLabel.setReadWrite(dbSettingsWritable);
			hostField.setReadWrite(dbSettingsWritable);
			hostField.setText(m_cc.getDbHost());
			portLabel.setReadWrite(dbSettingsWritable);
			dbPortField.setReadWrite(dbSettingsWritable);
			dbPortField.setText(String.valueOf(m_cc.getDbPort()));
			sidLabel.setReadWrite(dbSettingsWritable);
			sidField.setReadWrite(dbSettingsWritable);
			sidField.setText(m_cc.getDbName());
			//
			dbUidLabel.setReadWrite(dbSettingsWritable);
			dbUidField.setReadWrite(dbSettingsWritable);
			dbUidField.setText(m_cc.getDbUid());
			dbPwdField.setEditable(dbSettingsWritable);
			dbPwdField.setText(m_cc.getDbPwd());

			//
			bTestDB.setToolTipText(m_cc.getConnectionURL());
			bTestDB.setIcon(getStatusIcon(m_cc.isDatabaseOK()));
		}
		finally
		{
			m_updating = false;
		}
	}   // updateInfo

	/**
	 * Get Status Icon - ok or not
	 */
	private Icon getStatusIcon(boolean ok)
	{
		if (ok)
			return bOK.getIcon();
		else
			return bCancel.getIcon();
	}   // getStatusIcon

	private void showError(final String title, final Object messageObj)
	{
		JOptionPane.showMessageDialog(this,
				messageObj, // message
				title,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Test Database connection.
	 *
	 * If the database connection is not OK, an error popup will be displayed to user.
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
			log.error(e.getMessage(), e);
			showError(res.getString("ConnectionError") + ": " + m_cc.getConnectionURL(), e);
		}
		finally
		{
			setBusy(false);
		}
	}   // cmd_testDB

	public boolean isCancel()
	{
		return isCancel;
	}
}
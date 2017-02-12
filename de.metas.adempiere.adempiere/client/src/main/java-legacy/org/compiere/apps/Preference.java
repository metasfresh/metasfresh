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
package org.compiere.apps;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

import org.adempiere.acct.api.IPostingService;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.PLAFEditorPanel;
import org.adempiere.plaf.UIDefaultsEditorDialog;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.grid.ed.VDate;
import org.compiere.model.MSequence;
import org.compiere.print.CPrinter;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTabbedPane;
import org.compiere.swing.CTextArea;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.SupportInfo;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * Customize settings like L&F, AutoCommit, etc. & Diagnostics
 * <p>
 * Change log:
 * <ul>
 * <li>2007-02-12 - teo_sarca - [ 1658127 ] Select charset encoding on import
 * </ul>
 *
 * @author Jorg Janke
 * @version $Id: Preference.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 * 
 * @author Low Heng Sin
 * @version 2006-11-27
 */
public final class Preference extends CDialog
		implements ActionListener, ListSelectionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8923143271736597338L;

	// services
	private static final transient Logger log = LogManager.getLogger(Preference.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	/**
	 * Standard Constructor
	 * 
	 * @param frame frame
	 * @param WindowNo window
	 */
	public Preference(Frame frame, int WindowNo)
	{
		super(frame, Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Preference"), true);
		try
		{
			jbInit();
		}
		catch (Exception ex)
		{
			log.error("Failed initalizing", ex);
		}
		load();
		//
		StringBuilder sta = new StringBuilder("#");
		sta.append(Env.getCtx().size()).append(" - ")
				.append(msgBL.translate(Env.getCtx(), "AD_Window_ID"))
				.append("=").append(WindowNo);
		statusBar.setStatusLine(sta.toString());
		statusBar.setStatusDB("");
		AEnv.positionCenterWindow(frame, this);
	}	// Preference

	private CPanel panel = new CPanel();
	private BorderLayout panelLayout = new BorderLayout();
	private CTabbedPane tabPane = new CTabbedPane();
	private CPanel customizePane = new CPanel();
	private CPanel contextPane = new CPanel();
	private GridBagLayout customizeLayout = new GridBagLayout();
	private CCheckBox autoCommit = new CCheckBox();
	private CCheckBox autoNew = new CCheckBox();
	private CCheckBox printPreview = new CCheckBox();
	private CCheckBox validateConnectionOnStartup = new CCheckBox();
	private CCheckBox singleInstancePerWindow = new CCheckBox();
	private CCheckBox openWindowMaximized = new CCheckBox();
	private CPanel southPanel = new CPanel();
	private BorderLayout southLayout = new BorderLayout();
	private BorderLayout icontextLayout = new BorderLayout();
	private JList<String> infoList = new JList<>();
	private JScrollPane contextListScrollPane = new JScrollPane(infoList);
	private CPanel contextSouthPanel = new CPanel();
	private CTextArea contextHeader = new CTextArea(4, 15);
	private CTextArea contextDetail = new CTextArea(4, 35);
	private CTextArea infoArea = new CTextArea(5, 30);
	private BorderLayout contextSouthLayout = new BorderLayout();
	private StatusBar statusBar = new StatusBar();
	private ConfirmPanel confirm = ConfirmPanel.builder()
			.withCancelButton(true)
			.build();
	private CComboBox<Level> traceLevel = new CComboBox<>(LogManager.getAvailableLoggingLevels());
	private CLabel traceLabel = new CLabel();
	private CCheckBox traceFile = new CCheckBox();
	private CCheckBox autoLogin = new CCheckBox();
	private CCheckBox adempiereSys = new CCheckBox();
	private CCheckBox logMigrationScript = new CCheckBox();
	private CCheckBox storePassword = new CCheckBox();
	private CCheckBox showTrl = new CCheckBox();
	private CCheckBox showAcct = null;
	private CCheckBox showAdvanced = new CCheckBox();
	private CCheckBox cacheWindow = new CCheckBox();
	private CLabel lPrinter = new CLabel();
	private CPrinter fPrinter = new CPrinter();
	
	// metas: adding support for an additional label printer
	private CLabel lLabelPrinter = new CLabel();
	private CPrinter fLabelPrinter = new CPrinter();
	// metas end
	
	private CLabel lDate = new CLabel();
	private VDate fDate = new VDate();
	private CButton bRoleInfo = new CButton(msgBL.translate(Env.getCtx(), "AD_Role_ID"));
	// Charset:
	private CLabel lCharset = new CLabel();
	private CComboBox<Charset> fCharset = new CComboBox<>(Ini.getAvailableCharsets());

	private CPanel configPanel = new CPanel();

	private PLAFEditorPanel plafEditor = new PLAFEditorPanel()
			.setOnEditUIDefaultsActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					final Component btnEditUIDefaults = (Component)e.getSource();
					UIDefaultsEditorDialog.createAndShow(btnEditUIDefaults);
				}
			});

	/**
	 * Static Init.
	 * 
	 * <pre>
	 *  - panel
	 *      - tabPane
	 *          - customizePane
	 *              - infoArea
	 *              - fields ...
	 *          - contextPane
	 *              - contextList
	 *              - contextSouthPanel
	 *                  - contextHeader
	 *                  - contextDetail
	 * 			- errorPane
	 * 				- errorScollPane
	 * 					- errorTable
	 *      - southPanel
	 * </pre>
	 * 
	 * @throws Exception
	 */
	void jbInit() throws Exception
	{
		traceLabel.setRequestFocusEnabled(false);
		traceLabel.setText(msgBL.getMsg(Env.getCtx(), "TraceLevel", true));
		traceLabel.setToolTipText(msgBL.getMsg(Env.getCtx(), "TraceLevel", false));
		// traceFile.setText(msgBL.getMsg(Env.getCtx(), "TraceFile", true));
		// traceFile.setToolTipText(msgBL.getMsg(Env.getCtx(), "TraceFile", false));

		autoCommit.setText(msgBL.getMsg(Env.getCtx(), "AutoCommit", true));
		autoCommit.setToolTipText(msgBL.getMsg(Env.getCtx(), "AutoCommit", false));
		autoNew.setText(msgBL.getMsg(Env.getCtx(), "AutoNew", true));
		autoNew.setToolTipText(msgBL.getMsg(Env.getCtx(), "AutoNew", false));
		adempiereSys.setText(msgBL.getMsg(Env.getCtx(), "AdempiereSys", true));
		adempiereSys.setToolTipText(msgBL.getMsg(Env.getCtx(), "AdempiereSys", false));
		logMigrationScript.setText(msgBL.getMsg(Env.getCtx(), "LogMigrationScript", true));
		logMigrationScript.setToolTipText(msgBL.getMsg(Env.getCtx(), "LogMigrationScript", false));
		printPreview.setText(msgBL.getMsg(Env.getCtx(), "AlwaysPrintPreview", true));
		printPreview.setToolTipText(msgBL.getMsg(Env.getCtx(), "AlwaysPrintPreview", false));
		validateConnectionOnStartup.setText(msgBL.getMsg(Env.getCtx(), "ValidateConnectionOnStartup", true));
		validateConnectionOnStartup.setToolTipText(msgBL.getMsg(Env.getCtx(), "ValidateConnectionOnStartup", false));
		singleInstancePerWindow.setText(msgBL.getMsg(Env.getCtx(), "SingleInstancePerWindow", true));
		singleInstancePerWindow.setToolTipText(msgBL.getMsg(Env.getCtx(), "SingleInstancePerWindow", false));
		openWindowMaximized.setText(msgBL.getMsg(Env.getCtx(), "OpenWindowMaximized", true));
		openWindowMaximized.setToolTipText(msgBL.getMsg(Env.getCtx(), "OpenWindowMaximized", false));
		autoLogin.setText(msgBL.getMsg(Env.getCtx(), "AutoLogin", true));
		autoLogin.setToolTipText(msgBL.getMsg(Env.getCtx(), "AutoLogin", false));
		storePassword.setText(msgBL.getMsg(Env.getCtx(), "StorePassword", true));
		storePassword.setToolTipText(msgBL.getMsg(Env.getCtx(), "StorePassword", false));
		showTrl.setText(msgBL.getMsg(Env.getCtx(), "ShowTrlTab", true));
		showTrl.setToolTipText(msgBL.getMsg(Env.getCtx(), "ShowTrlTab", false));
		
		if(Services.get(IPostingService.class).isEnabled())
		{
			showAcct = new CCheckBox();
			showAcct.setText(msgBL.getMsg(Env.getCtx(), "ShowAcctTab", true));
			showAcct.setToolTipText(msgBL.getMsg(Env.getCtx(), "ShowAcctTab", false));
		}
		
		showAdvanced.setText(msgBL.getMsg(Env.getCtx(), "ShowAdvancedTab", true));
		showAdvanced.setToolTipText(msgBL.getMsg(Env.getCtx(), "ShowAdvancedTab", false));
		// connectionProfileLabel.setText(Msg.getElement(Env.getCtx(), "ConnectionProfile"));
		cacheWindow.setText(msgBL.getMsg(Env.getCtx(), "CacheWindow", true));
		cacheWindow.setToolTipText(msgBL.getMsg(Env.getCtx(), "CacheWindow", false));
		lPrinter.setText(msgBL.getMsg(Env.getCtx(), "Printer"));
		
		// metas: adding support for an additional label printer
		lLabelPrinter.setText(msgBL.getMsg(Env.getCtx(), Ini.P_LABELPRINTER));
		
		lDate.setText(msgBL.getMsg(Env.getCtx(), "Date"));
		infoArea.setReadWrite(false);
		// Charset:
		lCharset.setText(msgBL.getMsg(Env.getCtx(), "Charset", true));
		lCharset.setToolTipText(msgBL.getMsg(Env.getCtx(), "Charset", false));

		getContentPane().add(panel);
		panel.setLayout(panelLayout);
		panel.add(tabPane, BorderLayout.CENTER);
		// Customize
		tabPane.add(customizePane, msgBL.getMsg(Env.getCtx(), "Preference"));
		customizePane.setLayout(customizeLayout);
		customizePane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		Border insetBorder = BorderFactory.createEmptyBorder(2, 2, 2, 0);
		CPanel loginPanel = new CPanel();
		loginPanel.setBorder(BorderFactory.createTitledBorder(msgBL.getMsg(Env.getCtx(), "Login")));
		loginPanel.setLayout(new GridLayout(1, 2));
		autoLogin.setBorder(insetBorder);
		storePassword.setBorder(insetBorder);
		loginPanel.add(autoLogin);
		loginPanel.add(storePassword);
		customizePane.add(loginPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 2, 0), 0, 0));

		CPanel windowPanel = new CPanel();
		windowPanel.setBorder(BorderFactory.createTitledBorder(msgBL.getMsg(Env.getCtx(), "Window")));
		windowPanel.setLayout(new GridLayout(4, 2));

		if (showAcct != null)
		{
			windowPanel.add(showAcct);
			showAcct.setBorder(insetBorder);
		}
		
		windowPanel.add(showTrl);
		showTrl.setBorder(insetBorder);
		windowPanel.add(showAdvanced);
		showAdvanced.setBorder(insetBorder);
		windowPanel.add(autoCommit);
		autoCommit.setBorder(insetBorder);
		windowPanel.add(autoNew);
		autoNew.setBorder(insetBorder);
		windowPanel.add(cacheWindow);
		cacheWindow.setBorder(insetBorder);
		windowPanel.add(openWindowMaximized);
		openWindowMaximized.setBorder(insetBorder);
		windowPanel.add(singleInstancePerWindow);
		singleInstancePerWindow.setBorder(insetBorder);
		customizePane.add(windowPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 2, 0), 0, 0));

		CPanel connPanel = new CPanel();
		connPanel.setBorder(BorderFactory.createTitledBorder(msgBL.getMsg(Env.getCtx(), "Connection")));
		connPanel.setLayout(new GridBagLayout());
		// connPanel.add(connectionProfileLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
		// ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
		// connPanel.add(connectionProfile, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
		// ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
		connPanel.add(validateConnectionOnStartup, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
		customizePane.add(connPanel, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 2, 0), 0, 0));

		CPanel tracePanel = new CPanel();
		tracePanel.setBorder(BorderFactory.createTitledBorder(msgBL.getMsg(Env.getCtx(), "TraceInfo")));
		tracePanel.setLayout(new GridBagLayout());
		tracePanel.add(traceLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
		tracePanel.add(traceLevel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
		tracePanel.add(traceFile, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
		customizePane.add(tracePanel, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 2, 0), 0, 0));

		CPanel printPanel = new CPanel();
		printPanel.setBorder(BorderFactory.createTitledBorder(msgBL.getMsg(Env.getCtx(), "Printing")));
		printPanel.setLayout(new GridBagLayout());
		printPanel.add(lPrinter, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
		printPanel.add(fPrinter, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
		printPanel.add(printPreview, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
		// metas: adding support for an additional label printer
		printPanel.add(lLabelPrinter,new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
		printPanel.add(fLabelPrinter,new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
		//metas end

		customizePane.add(printPanel, new GridBagConstraints(0, 4, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 2, 0), 0, 0));

		CPanel otherPanel = new CPanel();
		otherPanel.setBorder(BorderFactory.createEmptyBorder());
		otherPanel.setLayout(new GridLayout());
		CPanel datePanel = new CPanel();
		datePanel.setLayout(new FlowLayout());
		((FlowLayout)datePanel.getLayout()).setAlignment(FlowLayout.LEFT);
		datePanel.add(lDate);
		datePanel.add(fDate);
		otherPanel.add(datePanel);
		datePanel.setBorder(insetBorder);
		otherPanel.add(adempiereSys);
		adempiereSys.setBorder(insetBorder);
		otherPanel.add(logMigrationScript);
		logMigrationScript.setBorder(insetBorder);
		customizePane.add(otherPanel, new GridBagConstraints(0, 5, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 2, 0), 0, 0));

		// Charset:
		CPanel charsetPanel = new CPanel();
		charsetPanel.setBorder(BorderFactory.createEmptyBorder());
		charsetPanel.setLayout(new FlowLayout());
		((FlowLayout)charsetPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		charsetPanel.add(lCharset);
		charsetPanel.add(fCharset);
		customizePane.add(charsetPanel, new GridBagConstraints(0, 6, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 2, 0), 0, 0));

		CPanel themePanel = new CPanel();
		themePanel.setLayout(new GridLayout(1, 1));

		themePanel.add(plafEditor);
		tabPane.add(themePanel, msgBL.getMsg(Env.getCtx(), "UITheme", true));

		configPanel.setLayout(new BorderLayout());
		configPanel.add(infoArea, BorderLayout.CENTER);
		CPanel configSouth = new CPanel();
		configSouth.setLayout(new FlowLayout());
		((FlowLayout)configSouth.getLayout()).setAlignment(FlowLayout.RIGHT);
		configSouth.add(bRoleInfo);
		configPanel.add(configSouth, BorderLayout.SOUTH);
		tabPane.add(configPanel, msgBL.getMsg(Env.getCtx(), "Info"));

		// Info
		tabPane.add(contextPane, msgBL.getMsg(Env.getCtx(), "Context"));
		contextPane.setLayout(icontextLayout);
		contextPane.add(contextListScrollPane, BorderLayout.CENTER);
		contextListScrollPane.setPreferredSize(new Dimension(200, 300));
		infoList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		infoList.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		infoList.addListSelectionListener(this);
		infoList.setFixedCellWidth(30);
		contextPane.add(contextSouthPanel, BorderLayout.SOUTH);
		contextSouthPanel.setLayout(contextSouthLayout);
		contextSouthPanel.add(contextHeader, BorderLayout.WEST);
		contextHeader.setBackground(SystemColor.info);
		contextHeader.setReadWrite(false);
		contextHeader.setLineWrap(true);
		contextHeader.setWrapStyleWord(true);
		contextHeader.setBorder(BorderFactory.createLoweredBevelBorder());
		contextSouthPanel.add(contextDetail, BorderLayout.CENTER);
		contextDetail.setBackground(SystemColor.info);
		contextDetail.setReadWrite(false);
		contextDetail.setLineWrap(true);
		contextDetail.setWrapStyleWord(true);
		contextDetail.setBorder(BorderFactory.createLoweredBevelBorder());
		
		// South
		panel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(southLayout);
		southPanel.add(statusBar, BorderLayout.SOUTH);
		southPanel.add(confirm, BorderLayout.CENTER);
		//
		bRoleInfo.addActionListener(this);
		confirm.setActionListener(this);
	}	// jbInit

	/**
	 * List Selection Listener - show info in header/detail fields
	 * 
	 * @param e evant
	 */
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting())
			return;

		String value = infoList.getSelectedValue();
		if (value == null)
			return;
		int pos = value.indexOf("==");
		if (pos == -1)
		{
			contextHeader.setText("");
			contextDetail.setText(value);
		}
		else
		{
			contextHeader.setText(value.substring(0, pos).replace('|', '\n'));
			contextDetail.setText(value.substring(pos + 3));
		}
	}	// valueChanged

	/**
	 * ActionListener
	 * 
	 * @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
			dispose();
		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
			cmd_save();
		//
		else if (e.getSource() == bRoleInfo)
		{
			final String roleInfo = Env.getUserRolePermissions().toStringX();
			final String roleInfoTrl = msgBL.parseTranslation(Env.getCtx(), roleInfo);
			ADialog.info(Env.WINDOW_MAIN, this, "RoleInfo", roleInfoTrl);
		}
	}	// actionPerformed

	/**
	 * Load Settings - and Context
	 */
	private void load()
	{
		log.debug("Loading");
		infoArea.setText(SupportInfo.getInfo());
		infoArea.setCaretPosition(0);

		// -- Load Settings --
		// AutoCommit
		autoCommit.setSelected(Env.isAutoCommit(Env.getCtx()));
		autoNew.setSelected(Env.isAutoNew(Env.getCtx()));
		
		//
		// AdempiereSys
		adempiereSys.setSelected(Ini.isPropertyBool(Ini.P_ADEMPIERESYS));
		// LogMigrationScript
		logMigrationScript.setSelected(Ini.isPropertyBool(Ini.P_LOGMIGRATIONSCRIPT));
		if (Env.getAD_Client_ID(Env.getCtx()) > 20)
		{
			adempiereSys.setSelected(false);
			adempiereSys.setEnabled(false);
			if (Env.getAD_User_ID(Env.getCtx()) > IUserDAO.SUPERUSER_USER_ID)
			{
				// disable log migration scripts on clients non-GW for non-SuperUser
				logMigrationScript.setSelected(false);
				logMigrationScript.setEnabled(false);
			}
		}
		
		// If the ID server is not enabled, don't show the log migration scripts flags because can cause huge problems (see task 09544)
		if (!MSequence.isExternalIDSystemEnabled())
		{
			adempiereSys.setSelected(false);
			adempiereSys.setVisible(false);
			logMigrationScript.setSelected(false);
			logMigrationScript.setVisible(false);
		}
		
		// AutoLogin
		autoLogin.setSelected(Ini.isPropertyBool(Ini.P_A_LOGIN));
		// Save Password
		storePassword.setSelected(Ini.isPropertyBool(Ini.P_STORE_PWD));
		
		// Show Acct Tab
		if (showAcct != null)
		{
			if (Env.getUserRolePermissions().hasPermission(IUserRolePermissions.PERMISSION_ShowAcct))
			{
				showAcct.setSelected(Ini.isPropertyBool(Ini.P_SHOW_ACCT));
			}
			else
			{
				showAcct.setSelected(false);
				showAcct.setReadWrite(false);
			}
		}
		
		// Show Trl/Advanced Tab
		showTrl.setSelected(Ini.isPropertyBool(Ini.P_SHOW_TRL));
		showAdvanced.setSelected(Ini.isPropertyBool(Ini.P_SHOW_ADVANCED));

		// // Connection Profile
		// MUser user = MUser.get(Env.getCtx());
		// String cp = user.getConnectionProfile();
		// if (cp == null)
		// cp = Env.getUserRolePermissions2().getConnectionProfile();
		// if (cp != null)
		// {
		// CConnection.get().setConnectionProfile(cp);
		// connectionProfile.setReadWrite(false);
		// }
		// connectionProfile.setSelectedItem(CConnection.get().getConnectionProfilePair());

		//
		// Cache Windows option
		// metas: c.ghita@metas.ro : start : 01552
		if (Services.get(ISysConfigBL.class).getBooleanValue("CACHE_WINDOWS_DISABLED", true))
		{
			cacheWindow.setSelected(false);
			cacheWindow.setEnabled(false);
		}
		// metas: c.ghita@metas.ro : end : 01552
		cacheWindow.setSelected(Ini.isCacheWindow());

		// Print Preview
		printPreview.setSelected(Ini.isPropertyBool(Ini.P_PRINTPREVIEW));

		// Validate Connection on Startup
		validateConnectionOnStartup.setSelected(Ini.isPropertyBool(Ini.P_VALIDATE_CONNECTION_ON_STARTUP));

		// Single Instance per Window
		singleInstancePerWindow.setSelected(Ini.isPropertyBool(Ini.P_SINGLE_INSTANCE_PER_WINDOW));

		// Open Window Maximized
		openWindowMaximized.setSelected(Ini.isPropertyBool(Ini.P_OPEN_WINDOW_MAXIMIZED));

		//
		// TraceLevel
		{
			final Level logLevel = LogManager.getLevel();
			final File logFile = LogManager.getActiveLogFile();
			final boolean logFileEnabled = LogManager.isFileLoggingEnabled();
			
			traceLevel.setSelectedItem(logLevel);
			String traceFileText = msgBL.getMsg(Env.getCtx(), Ini.P_TRACEFILE_ENABLED, true);
			if (logFile != null)
			{
				traceFileText += " (" + logFile + ")";
			}
			traceFile.setText(traceFileText);
			traceFile.setToolTipText(msgBL.getMsg(Env.getCtx(), Ini.P_TRACEFILE_ENABLED, false));
			traceFile.setSelected(logFileEnabled);
		}

		// Printer
		fPrinter.setValue(Env.getContext(Env.getCtx(), Env.CTXNAME_Printer));
		// metas: adding support for an additional label printer
		fLabelPrinter.setValue(Ini.getProperty(Ini.P_LABELPRINTER));

		// Date
		fDate.setValue(Env.getContextAsDate(Env.getCtx(), Env.CTXNAME_Date));
		// Charset
		fCharset.setSelectedItem(Ini.getCharset());

		// -- Load and sort Context --
		final String[] context = Env.getEntireContext(Env.getCtx());
		Arrays.sort(context);
		infoList.setListData(context);
	}	// load

	/**
	 * Save Settings
	 */
	private void cmd_save()
	{
		log.debug("Saving");
		// UI
		// AutoCommit
		Ini.setProperty(Ini.P_A_COMMIT, (autoCommit.isSelected()));
		Env.setAutoCommit(Env.getCtx(), autoCommit.isSelected());
		Ini.setProperty(Ini.P_A_NEW, (autoNew.isSelected()));
		Env.setAutoNew(Env.getCtx(), autoNew.isSelected());
		// AdempiereSys
		Ini.setProperty(Ini.P_ADEMPIERESYS, adempiereSys.isSelected());
		// LogMigrationScript
		Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, logMigrationScript.isSelected());
		// AutoLogin
		Ini.setProperty(Ini.P_A_LOGIN, (autoLogin.isSelected()));
		// Save Password
		Ini.setProperty(Ini.P_STORE_PWD, (storePassword.isSelected()));
		
		// Show Acct Tab
		if (showAcct != null)
		{
			Ini.setProperty(Ini.P_SHOW_ACCT, (showAcct.isSelected()));
			Env.setContext(Env.getCtx(), Env.CTXNAME_ShowAcct, (showAcct.isSelected()));
		}
		
		// Show Trl Tab
		Ini.setProperty(Ini.P_SHOW_TRL, (showTrl.isSelected()));
		Env.setContext(Env.getCtx(), "#ShowTrl", (showTrl.isSelected()));
		// Show Advanced Tab
		Ini.setProperty(Ini.P_SHOW_ADVANCED, (showAdvanced.isSelected()));
		Env.setContext(Env.getCtx(), "#ShowAdvanced", (showAdvanced.isSelected()));

		// // ConnectionProfile
		// ValueNamePair ppNew = (ValueNamePair)connectionProfile.getSelectedItem();
		// String cpNew = ppNew.getValue();
		// String cpOld = CConnection.get().getConnectionProfile();
		// CConnection.get().setConnectionProfile(cpNew);
		// if (!cpNew.equals(cpOld)
		// && (cpNew.equals(CConnection.PROFILE_WAN) || cpOld.equals(CConnection.PROFILE_WAN)))
		// ADialog.info(0, this, "ConnectionProfileChange");

		Ini.setProperty(Ini.P_CACHE_WINDOW, cacheWindow.isSelected());
		// Print Preview
		Ini.setProperty(Ini.P_PRINTPREVIEW, (printPreview.isSelected()));
		// Validate Connection on Startup
		Ini.setProperty(Ini.P_VALIDATE_CONNECTION_ON_STARTUP, (validateConnectionOnStartup.isSelected()));
		// Single Instance per Window
		Ini.setProperty(Ini.P_SINGLE_INSTANCE_PER_WINDOW, (singleInstancePerWindow.isSelected()));
		// Open Window Maximized
		Ini.setProperty(Ini.P_OPEN_WINDOW_MAXIMIZED, (openWindowMaximized.isSelected()));

		//
		// TraceLevel/File
		{
			final Level logLevel = traceLevel.getSelectedItem();
			final boolean logFileEnabled = traceFile.isSelected();
			LogManager.setLevelAndUpdateIni(logLevel);
			LogManager.setFileLoggingEnabled(logFileEnabled);
		}

		// Printer
		String printer = (String)fPrinter.getSelectedItem();
		Env.setContext(Env.getCtx(), Env.CTXNAME_Printer, printer);
		Ini.setProperty(Ini.P_PRINTER, printer);
		// metas: adding support for an additional label printer
		final String labelPrinter = (String)fLabelPrinter.getSelectedItem();
		Env.setContext(Env.getCtx(), "#" + Ini.P_LABELPRINTER, labelPrinter);
		Ini.setProperty(Ini.P_LABELPRINTER, labelPrinter);

		// Date (remove seconds)
		java.sql.Timestamp ts = fDate.getValue();
		if (ts != null)
			Env.setContext(Env.getCtx(), Env.CTXNAME_Date, ts);
		// Charset
		Charset charset = fCharset.getSelectedItem();
		Ini.setProperty(Ini.P_CHARSET, charset.name());

		// UI
		ValueNamePair laf = plafEditor.getSelectedLook();
		ValueNamePair theme = plafEditor.getSelectedTheme();
		if (laf != null)
		{
			String clazz = laf.getValue();
			String currentLaf = UIManager.getLookAndFeel().getClass().getName();
			if (clazz != null && clazz.length() > 0 && !currentLaf.equals(clazz))
			{
				// laf changed
				AdempierePLAF.setPLAF(laf, theme, true);
				AEnv.updateUI();
			}
			else
			{
				if (UIManager.getLookAndFeel() instanceof MetalLookAndFeel)
				{
					MetalTheme currentTheme = MetalLookAndFeel.getCurrentTheme();
					String themeClass = currentTheme.getClass().getName();
					String sTheme = theme.getValue();
					if (sTheme != null && sTheme.length() > 0 && !sTheme.equals(themeClass))
					{
						ValueNamePair plaf = new ValueNamePair(
								UIManager.getLookAndFeel().getClass().getName(),
								UIManager.getLookAndFeel().getName());
						AdempierePLAF.setPLAF(plaf, theme, true);
						AEnv.updateUI();
					}
				}
			}
		}

		Ini.saveProperties();
		dispose();
	}	// cmd_save

}

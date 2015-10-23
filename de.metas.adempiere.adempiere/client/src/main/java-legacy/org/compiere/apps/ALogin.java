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
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.rmi.AccessException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.MetasFreshTheme;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.compiere.db.CConnectionEditor;
import org.compiere.grid.ed.VDate;
import org.compiere.model.I_AD_Language;
import org.compiere.model.I_AD_System;
import org.compiere.model.MLanguage;
import org.compiere.model.MSystem;
import org.compiere.model.MUser;
import org.compiere.print.CPrinter;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTabbedPane;
import org.compiere.swing.CTextField;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.Login;
import org.compiere.util.Util;
import org.compiere.util.ValueNamePair;

import de.metas.adempiere.model.I_AD_Role;
import de.metas.adempiere.service.IPrinterRoutingBL;

/**
 * Application Login Window
 * 
 * @author Jorg Janke
 * @version $Id: ALogin.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ALogin extends CDialog
		implements ActionListener, ChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7789299589024390163L;

	// services
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	/**
	 * Construct the dialog. Need to call initLogin for dynamic start
	 * 
	 * @param parent parent
	 */
	public ALogin(final Frame parent, final Properties ctx)
	{
		super(parent, "Login", true);	// Modal
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.ctx = ctx;
		this.m_WindowNo = Env.createWindowNo(this);
		
		//
		try
		{
			jbInit();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Failed initializing the login window", e);
		}
		
		// Focus to OK
		this.getRootPane().setDefaultButton(confirmPanel.getOKButton());
		parent.setIconImage(Adempiere.getProductIconSmall());
	}   // ALogin

	private static final String RESOURCE = "org.compiere.apps.ALoginRes";
	private ResourceBundle res = ResourceBundle.getBundle(RESOURCE);

	/** Logger */
	private static final CLogger log = CLogger.getCLogger(ALogin.class);

	private CPanel mainPanel = new CPanel(new BorderLayout());
	private CTabbedPane loginTabPane = new CTabbedPane();
	private CPanel connectionPanel = new CPanel();
	private CLabel hostLabel = new CLabel();
	private CConnectionEditor hostField = new CConnectionEditor();
	private CLabel userLabel = new CLabel();
	private CTextField userTextField = new CTextField();
	private CLabel passwordLabel = new CLabel();
	private JPasswordField passwordField = new JPasswordField();
	private CPanel defaultPanel = new CPanel();
	private CLabel clientLabel = new CLabel();
	private CLabel orgLabel = new CLabel();
	private CLabel dateLabel = new CLabel();
	private VDate dateField = new VDate(DisplayType.Date);
	private CComboBox<KeyNamePair> orgCombo = new CComboBox<>();
	private CComboBox<KeyNamePair> clientCombo = new CComboBox<>();
	private CLabel warehouseLabel = new CLabel();
	private CComboBox<KeyNamePair> warehouseCombo = new CComboBox<>();
	private CLabel printerLabel = new CLabel();
	private CPrinter printerField = new CPrinter();
	private CLabel roleLabel = new CLabel();
	private CComboBox<KeyNamePair> roleCombo = new CComboBox<>();
	private CLabel copy0Label = new CLabel();
	private CLabel copy1Label = new CLabel();
	private GridBagLayout connectionLayout = new GridBagLayout();
	private GridBagLayout defaultPanelLayout = new GridBagLayout();
	private CLabel languageLabel = new CLabel();
	private CComboBox<ValueNamePair> languageCombo = new CComboBox<>(Language.getValueNamePairs());
	private CLabel compileDate = new CLabel();
	private CPanel southPanel = new CPanel();
	private BorderLayout southLayout = new BorderLayout();
	private StatusBar statusBar = new StatusBar();
	private ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.withoutText()
			.build();

	/** Server Connection */
	private CConnection m_cc;
	/** Application User */
	private String m_user;
	/** Application Password */
	private String m_pwd;

	/** Combo Active */
	private boolean m_comboActive = false;
	/** Combo Active */
	private boolean m_okPressed = false;
	/** Connection OK */
	private boolean m_connectionOK = false;
	/** Window No */
	private final int m_WindowNo;
	private final Properties ctx;

	private Login m_login = null;

	private final Properties getCtx()
	{
		return ctx;
	}

	/**************************************************************************
	 * Component initialization
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setName("Login");

		// me16: set some component names to be shown as "technical name" in our UI testing tool's object mapper
		// TODO: consider using reflection to name 'em all
		confirmPanel.setName("Login.confirmPanel");

		loginTabPane.setName("Login.loginTabPane");

		connectionPanel.setName("Login.connectionPanel");
		userTextField.setName("Login.userTextField");
		passwordField.setName("Login.passwordField");

		roleCombo.setName("Login.roleCombo");
		orgCombo.setName("Login.orgCombo");

		defaultPanel.setName("Login.defaultPanel");
		// me16 done

		final CLabel titleLabel = new CLabel();
		titleLabel.setFont(UIManager.getFont(MetasFreshTheme.KEY_Logo_TextFontSmall));
		titleLabel.setForeground(UIManager.getColor(MetasFreshTheme.KEY_Logo_TextColor));
		titleLabel.setRequestFocusEnabled(false);
		titleLabel.setToolTipText(Adempiere.getURL());
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		titleLabel.setIcon(new ImageIcon(Adempiere.getProductLogoLarge()));
		titleLabel.setText(Adempiere.getSubtitle());
		titleLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		//
		final CLabel versionLabel = new CLabel();
		versionLabel.setName("Login.versionLabel");
		versionLabel.setText(Adempiere.getMainVersion());
		versionLabel.setToolTipText(Adempiere.getImplementationVersion());
		versionLabel.setRequestFocusEnabled(false);
		versionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		versionLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		//
		hostLabel.setRequestFocusEnabled(false);
		hostLabel.setLabelFor(hostField);
		hostField.addActionListener(this);
		userLabel.setRequestFocusEnabled(false);
		userLabel.setLabelFor(userTextField);
		passwordLabel.setRequestFocusEnabled(false);
		passwordLabel.setLabelFor(passwordField);
		languageLabel.setLabelFor(languageCombo);
		copy0Label.setFont(UIManager.getFont(MetasFreshTheme.KEY_Logo_TextFontSmall));
		copy0Label.setForeground(UIManager.getColor(MetasFreshTheme.KEY_Logo_TextColor));
		copy0Label.setRequestFocusEnabled(false);
		copy1Label.setRequestFocusEnabled(false);
		roleLabel.setRequestFocusEnabled(false);
		roleLabel.setLabelFor(roleCombo);
		clientLabel.setRequestFocusEnabled(false);
		orgLabel.setRequestFocusEnabled(false);
		dateLabel.setRequestFocusEnabled(false);
		warehouseLabel.setRequestFocusEnabled(false);
		printerLabel.setRequestFocusEnabled(false);
		compileDate.setHorizontalAlignment(SwingConstants.RIGHT);
		compileDate.setHorizontalTextPosition(SwingConstants.RIGHT);
		compileDate.setText(Adempiere.getDateVersion());
		compileDate.setToolTipText(Adempiere.getImplementationVendor());
		southPanel.setLayout(southLayout);
		loginTabPane.addChangeListener(this);

		// ConnectionTab
		connectionPanel.setLayout(connectionLayout);
		//
		hostLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		hostLabel.setText("Host");
		hostLabel.setLabelFor(hostField);
		connectionPanel.add(hostLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		connectionPanel.add(hostField, new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 12), 0, 0));
		userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userLabel.setText("User");
		userLabel.setLabelFor(userTextField);
		connectionPanel.add(userLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		userTextField.setText("System");			// default
		connectionPanel.add(userTextField, new GridBagConstraints(1, 3, 3, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 12), 0, 0));
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setText("Password");
		passwordLabel.setLabelFor(passwordField);
		connectionPanel.add(passwordLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		passwordField.setText("System");			// default
		connectionPanel.add(passwordField, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 12), 0, 0));
		languageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		languageLabel.setText("Language");
		languageLabel.setLabelFor(languageCombo);
		languageCombo.addActionListener(this);
		// @Trifon - begin
		connectionPanel.add(languageLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		connectionPanel.add(languageCombo, new GridBagConstraints(1, 5, 3, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 12), 0, 0));
		// @Trifon - end
		copy0Label.setHorizontalAlignment(SwingConstants.RIGHT);
		connectionPanel.add(copy0Label, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		copy1Label.setText(Adempiere.getCopyright());
		connectionPanel.add(copy1Label, new GridBagConstraints(1, 6, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 12, 12), 0, 0));
		connectionPanel.add(compileDate, new GridBagConstraints(2, 1, 2, 1, 0.0, 0.0
				, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(2, 0, 0, 12), 0, 0));
		connectionPanel.add(titleLabel, new GridBagConstraints(0, 0, 2, 2, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(12, 12, 5, 5), 0, 0));
		connectionPanel.add(versionLabel, new GridBagConstraints(2, 0, 2, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(12, 5, 0, 12), 0, 0));

		loginTabPane.add(connectionPanel, res.getString("Connection"));

		// DefaultTab
		defaultPanel.setLayout(defaultPanelLayout);
		//
		roleLabel.setText("Role");
		roleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		roleLabel.setLabelFor(roleCombo);
		roleCombo.addActionListener(this);
		defaultPanel.add(roleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(12, 12, 5, 5), 0, 0));
		defaultPanel.add(roleCombo, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 0, 5, 12), 0, 0));
		clientLabel.setText("Client");
		clientLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		clientLabel.setLabelFor(clientCombo);
		defaultPanel.add(clientLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		clientCombo.addActionListener(this);
		defaultPanel.add(clientCombo, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 12), 0, 0));
		orgLabel.setText("Organization");
		orgLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		orgLabel.setLabelFor(orgCombo);
		defaultPanel.add(orgLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 5, 5), 0, 0));
		orgCombo.addActionListener(this);
		defaultPanel.add(orgCombo, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 5, 12), 0, 0));
		dateLabel.setText("Date");
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dateLabel.setLabelFor(dateField);
		defaultPanel.add(dateLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		defaultPanel.add(dateField, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 12), 0, 0));
		//
		warehouseLabel.setText("Warehouse");
		warehouseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		warehouseLabel.setLabelFor(warehouseCombo);
		printerLabel.setText("Printer");
		printerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		printerLabel.setLabelFor(printerField);

		// metas: us316: don't show printer field on Login panel
		/*
		 * defaultPanel.add(printerLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0 ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 12, 12, 5), 0, 0));
		 * defaultPanel.add(printerField, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0 ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 12, 12), 0, 0)); /*
		 */

		defaultPanel.add(warehouseLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		defaultPanel.add(warehouseCombo, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 12), 0, 0));

		// @Trifon - begin
		/*
		 * defaultPanel.add(languageLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0 , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 12, 5, 5), 0, 0));
		 * defaultPanel.add(languageCombo, new GridBagConstraints(1, 6, 3, 1, 1.0, 0.0 , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 12), 0, 0));
		 */
		// @Trifon - end
		//
		loginTabPane.add(defaultPanel, res.getString("Defaults"));

		// Help
		/*
		 * helpPanel.setLayout(helpLayout); helpPanel.setPreferredSize(new Dimension (100,100)); helpPanel.add(helpScollPane, BorderLayout.CENTER); loginTabPane.add(helpPanel, "?");
		 */
		//
		this.getContentPane().add(mainPanel);
		mainPanel.add(loginTabPane, BorderLayout.CENTER);
		mainPanel.setName("loginMainPanel");
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		//
		southPanel.add(confirmPanel, BorderLayout.NORTH);
		southPanel.add(statusBar, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);

		final CButton helpBtn = ConfirmPanel.createHelpButton(false);
		helpBtn.setToolTipText(res.getString("Help"));
		helpBtn.addActionListener(this);
		confirmPanel.addComponent(helpBtn);

		statusBar.setStatusDB(null);

	} 	// jbInit

	/**
	 * Set Initial & Ini Parameters Optional Automatic login
	 * 
	 * @return true, if connected & parameters set
	 */
	public boolean initLogin()
	{
		m_cc = CConnection.get();
		DB.setDBTarget(m_cc); // set the connection to DB to have early access to it (for messages, languages etc)
		hostField.setValue(m_cc);

		if (Ini.isPropertyBool(Ini.P_VALIDATE_CONNECTION_ON_STARTUP))
		{
			validateConnection();
		}

		// Application/PWD
		userTextField.setText(Ini.getProperty(Ini.P_UID));
		if (Ini.isPropertyBool(Ini.P_STORE_PWD))
		{
			passwordField.setText(Ini.getProperty(Ini.P_PWD));
		}
		else
		{
			passwordField.setText("");
		}
		
		//
		// fresh_06664 show the language selection combo box only if the System property org.adempiere.client.lang is not set.
		final boolean displayLanguageCombo;
		final String adLanguageToPreselect;		
		final String defaultClientLanguage = System.getProperty(Adempiere.PROPERTY_DefaultClientLanguage);
		if (!Check.isEmpty(defaultClientLanguage, true))
		{
			adLanguageToPreselect = defaultClientLanguage.trim();
			displayLanguageCombo = false;
		}
		else
		{
			adLanguageToPreselect = Ini.getProperty(Ini.P_LANGUAGE);
			displayLanguageCombo = true;
		}
		//
		loadLanguagesFromDatabase(adLanguageToPreselect);
		setLanguageComboVisible(displayLanguageCombo);

		// AutoLogin - assumes that connection is OK
		if (Ini.isPropertyBool(Ini.P_A_LOGIN))
		{
			connectionOK();
			defaultsOK();
			if (m_connectionOK)		// simulate
				m_okPressed = true;
			return m_connectionOK;
		}
		return false;
	}	// initLogin

	/**
	 * Window Events - requestFocus
	 * 
	 * @param e event
	 */
	@Override
	protected void processWindowEvent(WindowEvent e)
	{
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_OPENED)
		{
			this.toFront();
			confirmPanel.getOKButton().requestFocusInWindow();
		}
	}   // processWindowEvent

	private void validateAppServer()
	{
		if (!CConnection.isServerEmbedded())
		{
			m_cc.testAppsServer();
		}
	}

	private void connectToDatabase()
	{
		// Check connection
		DB.setDBTarget(m_cc);

		// direct
		final boolean connectOK = DB.connect();

		if (connectOK)
		{
			MLanguage.setBaseLanguage();
			loadLanguagesFromDatabase(null); // no preselect suggestion
		}
	}

	/**
	 * Load available languages from database (if available) and update the {@link #languageCombo}.
	 * 
	 * @param adLanguageToPreselect AD_Language to preselect or <code>null</code> if no suggestion
	 */
	private void loadLanguagesFromDatabase(final String adLanguageToPreselect)
	{
		if (!DB.isConnected())
		{
			return;
		}

		// Language suggested to be preselected
		ValueNamePair languageToPreselect = null;
		
		// Language which was previously selected on language combo
		final ValueNamePair languagePreviouslySelectedOld = languageCombo.getSelectedItem(); // old value
		ValueNamePair languagePreviouslySelected = null; // the new value, after languages are loaded
		
		// Base language
		ValueNamePair baseLanguage = null;
		
		//
		// Load all available languages
		// and find out which is the language to preselect, language previously selected and base language
		final Vector<ValueNamePair> availableLanguageNames;
		{
			final List<I_AD_Language> availableLanguages = Services.get(ILanguageBL.class).getAvailableLanguages(getCtx());
			availableLanguageNames = new Vector<>(availableLanguages.size());
			for (final I_AD_Language language : availableLanguages)
			{
				final ValueNamePair languageVNP = new ValueNamePair(language.getAD_Language(), language.getName());
				availableLanguageNames.add(languageVNP);
				
				if (adLanguageToPreselect != null && adLanguageToPreselect.equals(languageVNP.getValue()))
				{
					languageToPreselect = languageVNP;
				}
				if (languagePreviouslySelectedOld != null && Check.equals(languageVNP.getValue(), languagePreviouslySelectedOld.getValue()))
				{
					languagePreviouslySelected = languageVNP;
				}
				if (language.isBaseLanguage())
				{
					baseLanguage = languageVNP;
				}
			}
		}

		//
		// Decide which language to preselect
		languageToPreselect = Util.coalesce(languageToPreselect, languagePreviouslySelected, baseLanguage);
		
		//
		// Update language combo's model and preselect the language
		languageCombo.setModel(new DefaultComboBoxModel<>(availableLanguageNames));
		if (languageToPreselect != null)
		{
			languageCombo.setSelectedItem(languageToPreselect);
		}
	}

	/**
	 * Validate Connection
	 */
	private void validateConnection()
	{
		m_connectionOK = false;
		validateAppServer();

		// make sure connecting to new database
		DB.closeTarget();
		connectToDatabase();
		//
		hostField.setDisplay();
	}   // validateConnection

	/*************************************************************************
	 * Exit action performed
	 */
	private void appExit()
	{
		m_connectionOK = false;
		dispose();
	}	// appExit_actionPerformed

	/**
	 * Return true, if logged in
	 * 
	 * @return true if connected
	 */
	public boolean isConnected()
	{
		return m_connectionOK;
	}	// isConnected

	/**
	 * Did the user press OK
	 * 
	 * @return true if user pressed final OK button
	 */
	public boolean isOKpressed()
	{
		return m_okPressed;
	}	// isOKpressed

	/**************************************************************************
	 * Action Event handler
	 * 
	 * @param e event
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		try
		{
			actionPerformed0(e);
		}
		catch (Exception ex)
		{
			statusBar.setStatusLine(ex.getLocalizedMessage(), true);
			ADialog.error(Env.WINDOW_MAIN, this, ex);
		}
	}

	private final void actionPerformed0(final ActionEvent e)
	{
		if (ConfirmPanel.A_OK.equals(e.getActionCommand()))
		{
			if (loginTabPane.getSelectedIndex() == 0)
			{
				connectionOK();		// first ok
				printerField.refresh();
			}
			else
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				m_okPressed = true;
				try
				{
					// Dispose if OK - teo_sarca [ 1674663 ]
					if (!defaultsOK())
						m_okPressed = false;
				}
				finally
				{
					setCursor(Cursor.getDefaultCursor());
				}
			}
		}
		else if (ConfirmPanel.A_CANCEL.equals(e.getActionCommand()))
		{
			appExit();
		}
		//
		else if (e.getSource() == hostField)
		{
			validateConnection();
		}
		else if (e.getSource() == languageCombo)
		{
			languageComboChanged();
		}
		//
		else if (e.getSource() == roleCombo)
		{
			roleComboChanged();
		}
		else if (e.getSource() == clientCombo)
		{
			clientComboChanged();
		}
		else if (e.getSource() == orgCombo)
		{
			orgComboChanged();
		}
		else if (ConfirmPanel.A_HELP.equals(e.getActionCommand()))
		{
			OnlineHelp.openInDefaultBrowser();
		}
	}	// actionPerformed

	/**************************************************************************
	 * Connection OK pressed
	 */
	private void connectionOK()
	{
		log.info("");
		//
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		confirmPanel.getOKButton().setEnabled(false);
		try
		{
			m_connectionOK = tryConnection();

			if (m_connectionOK)
			{
				final Properties ctx = getCtx();

				//
				// Verify Language & Load Msg
				final Language language = Env.getLanguage(ctx);
				if (language == null)
				{
					// Shall not happen because we checked this several times.
					// But in case it happens and it's because somehow the language was not preselected,
					// at least give to user the opportunity so select the language manually
					setLanguageComboVisible(true);
					throw new AdempiereException("@NotFound@ @AD_Language@");
				}
				Env.verifyLanguage(ctx, language);
				Env.setContext(ctx, Env.CTXNAME_AD_Language, language.getAD_Language());
				Services.get(IMsgBL.class).getMsg(ctx, "0"); // trigger messages cache loading

				//
				// Migration
				final I_AD_System system = MSystem.get(ctx);
				if (system.isJustMigrated())
				{
					statusBar.setStatusLine("Running: After Migration ....", true);
					ADialog.info(m_WindowNo, this, "AfterMigration");
					Thread.yield();
					DB.afterMigration(ctx);
				}
				// Set Defaults
				printerField.setValue(Services.get(IPrinterRoutingBL.class).getDefaultPrinterName()); // metas: us316
				// printerField.setValue(Ini.getProperty(Ini.P_PRINTER)); // metas: us316: commented

				//
				//
				// fresh_06009 show warehouse only if ShowWarehouseOnLogin is Y.
				// Also assume 'Y' if we have no connection.
				final boolean showWarehouseOnLogin = m_login == null || m_login.isShowWarehouseOnLogin();
				warehouseLabel.setVisible(showWarehouseOnLogin);
				warehouseCombo.setVisible(showWarehouseOnLogin);

				// Change Tab to Default
				loginTabPane.setSelectedIndex(1);
			}
		}
		finally
		{
			confirmPanel.getOKButton().setEnabled(true);
			setCursor(Cursor.getDefaultCursor());
		}
	}	// connectionOK

	/**
	 * Change of tab <->
	 * 
	 * @param e event
	 */
	@Override
	public void stateChanged(ChangeEvent e)
	{
		if (loginTabPane.getSelectedIndex() == 2)   // allow access to help
			return;

		if (!(String.valueOf(passwordField.getPassword()).equals(m_pwd)
		&& userTextField.getText().equals(m_user)))
			m_connectionOK = false;
		//
		if (m_connectionOK)
			statusBar.setStatusLine(txt_LoggedIn);
		else
		{
			statusBar.setStatusLine(txt_NotConnected, true);
			loginTabPane.setSelectedIndex(0);
		}
		confirmPanel.getOKButton().requestFocus();
	}	// loginTabPane

	/**************************************************************************
	 * Defaults OK pressed
	 * 
	 * @return true if ok
	 */
	private boolean defaultsOK()
	{
		log.info("");

		KeyNamePair org = orgCombo.getSelectedItem();
		if (org == null)
			return false;

		// Set Properties
		Ini.setProperty(Ini.P_CONNECTION, CConnection.get().toStringLong());
		
		ValueNamePair selectedLanguage = languageCombo.getSelectedItem();
		Ini.setProperty(Ini.P_LANGUAGE, selectedLanguage == null ? null : selectedLanguage.getValue());
		
		String error = m_login.validateLogin(org);
		if (error != null && error.length() > 0)
		{
			ADialog.info(m_WindowNo, this, error);
			appExit();
			return false;
		}

		// Load Properties and save Ini values
		statusBar.setStatusLine("Loading Preferences");
		String msg = m_login.loadPreferences(org,
				warehouseCombo.getSelectedItem(),
				dateField.getTimestamp(), printerField.getDisplay());
		if (msg.length() > 0)
			ADialog.info(m_WindowNo, this, msg);

		// Check Apps Server - DB Checked in Menu
		checkVersion();			// exits if conflict

		// Close - we are done
		if (m_connectionOK)
			this.dispose();
		return m_connectionOK;
	}	// defaultsOK

	/**************************************************************************
	 * Try to connect. - Get Connection - Compare User info
	 * 
	 * @return true if connected
	 */
	private boolean tryConnection()
	{
		m_user = userTextField.getText();
		m_pwd = new String(passwordField.getPassword());

		// Establish connection
		if (!DB.isConnected())
		{
			validateConnection();
		}

		if (!DB.isConnected())
		{
			statusBar.setStatusLine(txt_NoDatabase, true);
			hostField.setBackground(AdempierePLAF.getFieldBackground_Error());
			return false;
		}

		// Reference check
		Ini.setProperty(Ini.P_ADEMPIERESYS, "Reference".equalsIgnoreCase(CConnection.get().getDbUid()));

		// Reference check
		Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, "Reference".equalsIgnoreCase(CConnection.get().getDbUid()));

		// Get Roles
		m_login = new Login(getCtx());
		KeyNamePair[] roles = null;
		try
		{
			roles = m_login.getRoles(m_user, m_pwd);
			if (roles == null || roles.length == 0)
			{
				statusBar.setStatusLine(txt_UserPwdError, true);
				userTextField.setBackground(AdempierePLAF.getFieldBackground_Error());
				passwordField.setBackground(AdempierePLAF.getFieldBackground_Error());
				return false;
			}
		}
		catch (Throwable e)
		{
			if (e.getCause() instanceof AccessException)
			{
				statusBar.setStatusLine(txt_UserPwdError, true);
				userTextField.setBackground(AdempierePLAF.getFieldBackground_Error());
				passwordField.setBackground(AdempierePLAF.getFieldBackground_Error());
				return false;
			}
			else
			{
				final Throwable rootCause = CLogger.getRootCause(e);
				log.log(Level.SEVERE, rootCause.getLocalizedMessage(), rootCause);
				statusBar.setStatusLine(rootCause.getLocalizedMessage(), true);
				return false;
			}
		}

		// Delete existing role items
		m_comboActive = true;
		if (roleCombo.getItemCount() > 0)
			roleCombo.removeAllItems();

		// Initial role
		KeyNamePair iniValue = null;
		String iniDefault = Ini.getProperty(Ini.P_ROLE);

		// fill roles
		for (int i = 0; i < roles.length; i++)
		{
			roleCombo.addItem(roles[i]);
			if (roles[i].getName().equals(iniDefault))
				iniValue = roles[i];
		}
		if (iniValue != null)
			roleCombo.setSelectedItem(iniValue);

		// If we have only one role, we can hide the combobox - metas-2009_0021_AP1_G94
		if (roleCombo.getItemCount() == 1)
		{
			roleCombo.setSelectedIndex(0);
			roleLabel.setVisible(false);
			roleCombo.setVisible(false);
		}
		else
		{
			roleLabel.setVisible(true);
			roleCombo.setVisible(true);
		}

		userTextField.setBackground(AdempierePLAF.getFieldBackground_Normal());
		passwordField.setBackground(AdempierePLAF.getFieldBackground_Normal());
		//
		this.setTitle(hostField.getDisplay());
		statusBar.setStatusLine(txt_LoggedIn);
		m_comboActive = false;
		roleComboChanged();

		return true;
	}	// tryConnection

	/**
	 * Role changed - fill Client List
	 */
	private void roleComboChanged()
	{
		final KeyNamePair role = roleCombo.getSelectedItem();
		if (role == null || m_comboActive)
			return;
		log.config(": " + role);
		m_comboActive = true;
		//
		final KeyNamePair[] clients = m_login.getClients(role);
		// delete existing client/org items
		if (clientCombo.getItemCount() > 0)
			clientCombo.removeAllItems();
		if (orgCombo.getItemCount() > 0)
			orgCombo.removeAllItems();
		// No Clients
		if (clients == null || clients.length == 0)
		{
			statusBar.setStatusLine(txt_RoleError, true);
			m_comboActive = false;
			return;
		}
		// initial client
		KeyNamePair iniValue = null;
		String iniDefault = Ini.getProperty(Ini.P_CLIENT);

		// fill clients
		for (int i = 0; i < clients.length; i++)
		{
			clientCombo.addItem(clients[i]);
			if (clients[i].getName().equals(iniDefault))
				iniValue = clients[i];
		}
		// fini
		if (iniValue != null)
			clientCombo.setSelectedItem(iniValue);
		//
		m_comboActive = false;
		clientComboChanged();
	}	// roleComboChanged

	/**
	 * Client changed - fill Org & Warehouse List
	 */
	private void clientComboChanged()
	{
		KeyNamePair client = clientCombo.getSelectedItem();
		if (client == null || m_comboActive)
			return;
		log.config(": " + client);
		m_comboActive = true;

		// @Trifon - Set Proper "#AD_Client_ID", #AD_User_ID and "#SalesRep_ID"
		// https://sourceforge.net/tracker/?func=detail&aid=2957215&group_id=176962&atid=879332
		final Properties ctx = getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, client.getKey());
		MUser user = MUser.get(ctx, userTextField.getText(), new String(passwordField.getPassword()));
		if (user != null)
		{
			Env.setContext(ctx, Env.CTXNAME_AD_User_ID, user.getAD_User_ID());
			Env.setContext(ctx, Env.CTXNAME_SalesRep_ID, user.getAD_User_ID());
		}
		showHideDateField(); // metas
		//
		KeyNamePair[] orgs = m_login.getOrgs(client);
		// delete existing cleint items
		if (orgCombo.getItemCount() > 0)
			orgCombo.removeAllItems();

		// No Orgs
		if (orgs == null || orgs.length == 0)
		{
			statusBar.setStatusLine(txt_RoleError, true);
			m_comboActive = false;
			return;
		}
		// initial client
		KeyNamePair orgValue = null;
		KeyNamePair orgValue2 = null;
		String iniDefault = Ini.getProperty(Ini.P_ORG);

		// fill orgs
		for (int i = 0; i < orgs.length; i++)
		{
			orgCombo.addItem(orgs[i]);
			if (orgs[i].getName().equals(iniDefault))
				orgValue = orgs[i];
			if (orgValue2 == null && orgs[i].getKey() != 0)
				orgValue2 = orgs[i];	// first non-0 org
		}
		// Non-0 Org exists and last login was with 0
		if (orgValue2 != null && orgValue != null && orgValue.getKey() == 0)
			orgValue = orgValue2;
		// Last Org
		if (orgValue != null)
			orgCombo.setSelectedItem(orgValue);
		// Get first Org
		else
			orgValue = orgCombo.getSelectedItem();
		//
		m_comboActive = false;
		orgComboChanged();
	}	// clientComboChanged

	/**
	 * Org changed - fill Warehouse List
	 */
	private void orgComboChanged()
	{
		KeyNamePair org = orgCombo.getSelectedItem();
		if (org == null || m_comboActive)
			return;
		log.config(": " + org);
		m_comboActive = true;
		//
		KeyNamePair[] whs = m_login.getWarehouses(org);
		// Delete existing warehouse items
		if (warehouseCombo.getItemCount() > 0)
			warehouseCombo.removeAllItems();

		// fill warehouses
		if (whs != null)
		{
			// initial warehouse
			KeyNamePair iniValue = null;
			String iniDefault = Ini.getProperty(Ini.P_WAREHOUSE);
			for (int i = 0; i < whs.length; i++)
			{
				warehouseCombo.addItem(whs[i]);
				if (whs[i].getName().equals(iniDefault))
					iniValue = whs[i];
			}
			if (iniValue != null)
				warehouseCombo.setSelectedItem(iniValue);
		}
		m_comboActive = false;
	}	// orgComboChanged

	/**
	 * Check Version
	 * 
	 * @return true if version is OK and false if version could not be checked or is not the same
	 * @see AEnv#getServerVersion
	 */
	private boolean checkVersion()
	{
		boolean retValue = false;
		try
		{
			String version = AEnv.getServerVersion();
			if (Adempiere.getDateVersion().equals(version))
			{
				log.config("Server = Client - " + version);
				retValue = true;
			}
			else if (version != null)
			{
				StringBuffer msg = new StringBuffer(">>\n");
				msg.append(res.getString("VersionConflict")).append("\n")
						.append(res.getString("VersionInfo")).append("\n");
				msg.append(version == null ? "null" : version).append(" <> ")
						.append(Adempiere.getDateVersion()).append("\n");
				msg.append(res.getString("PleaseUpgrade")).append("\n<<");
				JOptionPane.showMessageDialog(null, msg.toString(),
						Adempiere.getName() + " - " + res.getString("VersionConflict"),
						JOptionPane.ERROR_MESSAGE);
				AEnv.exit(1);
			}
		}
		catch (Exception e)
		{
			log.severe("Contact Server failed - "
					+ e.getClass().toString() + ": " + e.getMessage());
		}
		return retValue;
	}   // checkVersion

	/**************************************************************************
	 * Language issues
	 */
	private String	// txt_Connected,
			txt_NotConnected = "Not connected",
			txt_NoDatabase = "No Database",
			txt_UserPwdError = "Invalid user or password",
			txt_RoleError = "No user roles found",
			txt_LoggedIn = "Logged in";
	
	private void setLanguageComboVisible(final boolean visible)
	{
		languageLabel.setVisible(visible);
		languageCombo.setVisible(visible);
	}

	/**
	 * Change Language
	 */
	private void languageComboChanged()
	{
		final ValueNamePair selectedLanguage = languageCombo.getSelectedItem();
		final String selectedADLanguage = selectedLanguage == null ? null : selectedLanguage.getValue();
		// log.info( "Language: " + langName);
		Language language = Language.getLanguage(selectedADLanguage);
		// Language.setLoginLanguage(language); // metas: tsa: 02214: commented out
		Env.setContext(getCtx(), Env.CTXNAME_AD_Language, language.getAD_Language());

		// switching the log in window orientation at once
		if (language.isLeftToRight())
		{
			this.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		}
		else
		{
			this.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		}
		
		// Locales
		final Locale loc = language.getLocale();
		Locale.setDefault(loc);
		this.setLocale(loc);
		res = ResourceBundle.getBundle(RESOURCE, loc);
		//
		this.setTitle(res.getString("Login"));
		hostLabel.setText(res.getString("Host"));
		userLabel.setText(res.getString("User"));
		userLabel.setToolTipText(res.getString("EnterUser"));
		passwordLabel.setText(res.getString("Password"));
		passwordLabel.setToolTipText(res.getString("EnterPassword"));
		languageLabel.setText(res.getString("Language"));
		languageLabel.setToolTipText(res.getString("SelectLanguage"));
		//
		roleLabel.setText(res.getString("Role"));
		clientLabel.setText(res.getString("Client"));
		orgLabel.setText(res.getString("Organization"));
		dateLabel.setText(res.getString("Date"));
		warehouseLabel.setText(res.getString("Warehouse"));
		printerLabel.setText(res.getString("Printer"));
		defaultPanel.setToolTipText(res.getString("Defaults"));
		connectionPanel.setToolTipText(res.getString("Connection"));
		//
		// txt_Connected = res.getString("Connected");
		txt_NotConnected = res.getString("NotConnected");
		txt_NoDatabase = res.getString("DatabaseNotFound");
		txt_UserPwdError = res.getString("UserPwdError");
		txt_RoleError = res.getString("RoleNotFound");
		txt_LoggedIn = res.getString("Authorized");
		//
		loginTabPane.setTitleAt(0, res.getString("Connection"));
		loginTabPane.setTitleAt(1, res.getString("Defaults"));
		confirmPanel.getOKButton().setToolTipText(res.getString("Ok"));
		confirmPanel.getCancelButton().setToolTipText(res.getString("Cancel"));

		// DateField with new format
		dateField.setFormat();
		dateField.setValue(new Timestamp(System.currentTimeMillis()));
		//
		if (m_connectionOK)
		{
			this.setTitle(hostField.getDisplay());
			statusBar.setStatusLine(txt_LoggedIn);
		}
		else
		{
			this.setTitle(res.getString("Login"));
			statusBar.setStatusLine(txt_NotConnected, true);
		}
	}   // languageCombo_actionPerformed

	// metas
	private void showHideDateField()
	{
		final boolean showLoginDate = isShowLoginDate();
		dateLabel.setVisible(showLoginDate);
		dateField.setVisible(showLoginDate);
	}

	private boolean isShowLoginDate()
	{
		final Properties ctx = getCtx();

		// Make sure AD_Client_ID was not set
		if (Check.isEmpty(Env.getContext(ctx, Env.CTXNAME_AD_Client_ID), true))
		{
			return false;
		}

		final boolean allowLoginDateOverride = "Y".equals(Env.getContext(ctx, "#" + I_AD_Role.COLUMNNAME_IsAllowLoginDateOverride));
		if (allowLoginDateOverride)
		{
			return true;
		}

		final int adClientId = Env.getAD_Client_ID(ctx);
		final boolean dateAutoupdate = sysConfigBL.getBooleanValue("LOGINDATE_AUTOUPDATE", false, adClientId);
		if (dateAutoupdate)
		{
			return false;
		}

		return true;
	}

	private boolean disposed = false;

	@Override
	public void dispose()
	{
		super.dispose();

		if (disposed)
		{
			// NOTE: for some reason this method is called twice on login, so we introduced disposed flag to prevent running the code twice
			return;
		}

		Env.clearWinContext(getCtx(), m_WindowNo);
		disposed = true;
	}

}	// ALogin

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
package org.compiere.install;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.logging.Level;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.compiere.Adempiere;
import org.compiere.db.CConnectionAttributes;
import org.compiere.db.Database;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.EMail;
import org.compiere.util.EMailAuthenticator;
import org.compiere.util.Ini;


/**
 *	Configuration Data
 *	
 *  @author Jorg Janke
 *  @version $Id: ConfigurationData.java,v 1.4 2006/07/30 00:57:42 jjanke Exp $
 */
public class ConfigurationData
{
	/**
	 * 	Constructor
	 * 	@param panel UI panel
	 */
	public ConfigurationData (ConfigurationPanel panel)
	{
		super ();
		p_panel = panel;
	}	//	ConfigurationData

	/** UI Panel				*/
	protected ConfigurationPanel	p_panel = null;
	/** Environment Properties	*/
	protected Properties		p_properties = new Properties();
	/**	Adempiere Home			*/
	private File				m_adempiereHome;

	
	/**	Static Logger	*/
	static CLogger	log	= CLogger.getCLogger (ConfigurationData.class);


	/** Properties File	name			*/
	public static final String	ADEMPIERE_ENV_FILE		= "AdempiereEnv.properties";
	
	/** Adempiere Home					*/
	public static final String	ADEMPIERE_HOME 			= "ADEMPIERE_HOME";
	/** 				*/
	public static final String	JAVA_HOME 				= "JAVA_HOME";
	/** 				*/
	public static final String	JAVA_TYPE 				= "ADEMPIERE_JAVA_TYPE";
	/** 				*/
	public static final String	ADEMPIERE_JAVA_OPTIONS 	= "ADEMPIERE_JAVA_OPTIONS";
	/** Default Keystore Password		*/
	public static final String	DEFAULT_KEYSTORE_PASSWORD	= "myPassword";

	/** Default Key Password		*/
	public static final String	DEFAULT_KEY_PASSWORD	= DEFAULT_KEYSTORE_PASSWORD;
	
	/** 				*/
	public static final String	ADEMPIERE_APPS_TYPE		= "ADEMPIERE_APPS_TYPE";
	/** 				*/
	public static final String	ADEMPIERE_APPS_SERVER 	= "ADEMPIERE_APPS_SERVER";
	/** 				*/
	public static final String	ADEMPIERE_APPS_DEPLOY	= "ADEMPIERE_APPS_DEPLOY";
	/** 				*/
	public static final String	ADEMPIERE_JNP_PORT 		= "ADEMPIERE_JNP_PORT";
	/** 				*/
	public static final String	ADEMPIERE_WEB_PORT 		= "ADEMPIERE_WEB_PORT";
	/** 				*/
	public static final String	ADEMPIERE_SSL_PORT 		= "ADEMPIERE_SSL_PORT";
	/** 				*/
	public static final String	ADEMPIERE_WEB_ALIAS 	= "ADEMPIERE_WEB_ALIAS";
	
	/** 				*/
	// task 005047
	public static final String	ADEMPIERE_KEYSTORE_FILENAME 		= "ADEMPIERE_KEYSTORE";
	
	// task 005047
	public static final String	ADEMPIERE_KEYSTORE_FULL_FILENAME 	= "ADEMPIERE_KEYSTORE_FULL_FILENAME";
	
	/** 				*/
	// task 005047
	public static final String	ADEMPIERE_KEYPASS	= "ADEMPIERE_KEYPASS";
	
	/** 				*/
	public static final String	ADEMPIERE_KEYSTOREPASS	= "ADEMPIERE_KEYSTOREPASS";
	/** 				*/
	public static final String	ADEMPIERE_KEYSTORECODEALIAS	= "ADEMPIERE_KEYSTORECODEALIAS";
	/** 				*/
	public static final String	ADEMPIERE_KEYSTOREWEBALIAS	= "ADEMPIERE_KEYSTOREWEBALIAS";
	
	public static final String	ADEMPIERE_CERT_CN	= "ADEMPIERE_CERT_CN";
	
	public static final String	ADEMPIERE_CERT_ORG	= "ADEMPIERE_CERT_ORG";
	
	public static final String	ADEMPIERE_CERT_ORG_UNIT	= "ADEMPIERE_CERT_ORG_UNIT";
	
	public static final String	ADEMPIERE_CERT_LOCATION	= "ADEMPIERE_CERT_LOCATION";
	
	public static final String	ADEMPIERE_CERT_STATE	= "ADEMPIERE_CERT_STATE";
	
	public static final String	ADEMPIERE_CERT_COUNTRY	= "ADEMPIERE_CERT_COUNTRY";

	/** DB Type			*/
	public static final String	ADEMPIERE_DB_TYPE		= "ADEMPIERE_DB_TYPE";
	/** DB Path			*/
	public static final String	ADEMPIERE_DB_PATH		= "ADEMPIERE_DB_PATH";
	/** 				*/
	/** 				*/
	public static final String	ADEMPIERE_DB_SERVER 	= "ADEMPIERE_DB_SERVER";
	/** 				*/
	public static final String	ADEMPIERE_DB_PORT 		= "ADEMPIERE_DB_PORT";
	/** 				*/
	public static final String	ADEMPIERE_DB_NAME 		= "ADEMPIERE_DB_NAME";
	/** 				*/
	public static final String	ADEMPIERE_DB_URL 		= "ADEMPIERE_DB_URL";

	/** 				*/
	public static final String	ADEMPIERE_DB_USER 		= "ADEMPIERE_DB_USER";
	/** 				*/
	public static final String	ADEMPIERE_DB_PASSWORD 	= "ADEMPIERE_DB_PASSWORD";
	/** 				*/
	public static final String	ADEMPIERE_DB_SYSTEM 	= "ADEMPIERE_DB_SYSTEM";

	/** 				*/
	public static final String	ADEMPIERE_MAIL_SERVER 	= "ADEMPIERE_MAIL_SERVER";
	/** 				*/
	public static final String	ADEMPIERE_MAIL_USER 	= "ADEMPIERE_MAIL_USER";
	/** 				*/
	public static final String	ADEMPIERE_MAIL_PASSWORD = "ADEMPIERE_MAIL_PASSWORD";
	/** 				*/
	public static final String	ADEMPIERE_ADMIN_EMAIL	= "ADEMPIERE_ADMIN_EMAIL";
	/** 				*/
	public static final String	ADEMPIERE_MAIL_UPDATED	= "ADEMPIERE_MAIL_UPDATED";

	/** 				*/
	public static final String	ADEMPIERE_FTP_SERVER	= "ADEMPIERE_FTP_SERVER";
	/** 				*/
	public static final String	ADEMPIERE_FTP_USER		= "ADEMPIERE_FTP_USER";
	/** 				*/
	public static final String	ADEMPIERE_FTP_PASSWORD	= "ADEMPIERE_FTP_PASSWORD";
	/** 				*/
	public static final String	ADEMPIERE_FTP_PREFIX	= "ADEMPIERE_FTP_PREFIX";

	/** 				*/
	public static final String	ADEMPIERE_WEBSTORES		= "ADEMPIERE_WEBSTORES";
	
	
	private void updateProperty(String property, String value) {
		if (value == null) value = "";
		String currentValue = (String)p_properties.get(property);
		if (currentValue == null)
			p_properties.put(property, value);
		else if (!currentValue.equals(value))
			p_properties.put(property, value);
	}
	
	/**
	 * 	Load Configuration Data
	 * 	@return true if loaded
	 */
	public boolean load()
	{
		//	Load C:\Adempiere\AdempiereEnv.properties
		String adempiereHome = System.getProperty(ADEMPIERE_HOME);
		if (adempiereHome == null || adempiereHome.length() == 0)
			adempiereHome = System.getProperty("user.dir");
		
		boolean envLoaded = false;
		String fileName = adempiereHome + File.separator + ADEMPIERE_ENV_FILE;
		File env = new File (fileName);
		if (env.exists())
		{
			try
			{
				FileInputStream fis = new FileInputStream(env);
				p_properties.load(fis);
				fis.close();
			}
			catch (Exception e)
			{
				log.warning(e.toString());
			}
			log.info(env.toString());
			if (p_properties.size() > 5)
				envLoaded = true;
			
			Properties loaded = new Properties();
			loaded.putAll(p_properties);
			//
			int javaIndex = setJavaType((String)p_properties.get(JAVA_TYPE));
			initJava(javaIndex);
			if (loaded.containsKey(JAVA_HOME))
				setJavaHome((String)loaded.get(JAVA_HOME));
			//
			setAdempiereHome((String)p_properties.get(ADEMPIERE_HOME));
			// task 05047 begin
			String keyStoreFileName = (String)p_properties.get(ADEMPIERE_KEYSTORE_FILENAME);
			if (keyStoreFileName == null || keyStoreFileName.length() == 0)
			{
				keyStoreFileName = KeyStoreMgt.DEFAULT_KEYSTORE_NAME;
				p_properties.put(ADEMPIERE_KEYSTORE_FILENAME, keyStoreFileName);
			}
			
			// set the default signing passwords, unless they were already defined in ADempiereEnv.properties 
			String keystorePass = (String)p_properties.get(ADEMPIERE_KEYSTOREPASS);
			if (keystorePass == null || keystorePass.length() == 0)
			{
				keystorePass = DEFAULT_KEYSTORE_PASSWORD;
				p_properties.put(ADEMPIERE_KEYSTOREPASS, keystorePass);
			}
			String keyPass = (String)p_properties.get(ADEMPIERE_KEYPASS);
			if (keyPass == null || keyPass.length() == 0)
			{
				keyPass = DEFAULT_KEY_PASSWORD;
				p_properties.put(ADEMPIERE_KEYPASS, keyPass);
			}
			setKeyStore(keyStoreFileName, keystorePass, keyPass);
			// task 05047 end
			
			//
			int appServerIndex = setAppsServerType((String)p_properties.get(ADEMPIERE_APPS_TYPE));
			initAppsServer(appServerIndex);
			if (loaded.containsKey(ADEMPIERE_APPS_SERVER))
				setAppsServer((String)loaded.get(ADEMPIERE_APPS_SERVER));
			if (loaded.containsKey(ADEMPIERE_APPS_DEPLOY))
				setAppsServerDeployDir((String)loaded.get(ADEMPIERE_APPS_DEPLOY));
			if (loaded.containsKey(ADEMPIERE_JNP_PORT))
				setAppsServerJNPPort((String)loaded.get(ADEMPIERE_JNP_PORT));
			if (loaded.containsKey(ADEMPIERE_WEB_PORT))
				setAppsServerWebPort((String)loaded.get(ADEMPIERE_WEB_PORT));
			if (loaded.containsKey(ADEMPIERE_SSL_PORT))
				setAppsServerSSLPort((String)loaded.get(ADEMPIERE_SSL_PORT));
			//
			int dbTypeIndex = setDatabaseType((String)p_properties.get(ADEMPIERE_DB_TYPE));
			initDatabase((String)p_properties.get(ADEMPIERE_DB_NAME), dbTypeIndex);	//	fills Database Options
			if (loaded.containsKey(ADEMPIERE_DB_NAME))
				setDatabaseDiscovered((String)loaded.get(ADEMPIERE_DB_NAME));
			if (loaded.containsKey(ADEMPIERE_DB_SERVER))
				setDatabaseServer((String)loaded.get(ADEMPIERE_DB_SERVER));
			if (loaded.containsKey(ADEMPIERE_DB_PORT))
				setDatabasePort((String)loaded.get(ADEMPIERE_DB_PORT));
			if (loaded.containsKey(ADEMPIERE_DB_NAME))
				setDatabaseName((String)loaded.get(ADEMPIERE_DB_NAME));
			if (loaded.containsKey(ADEMPIERE_DB_USER))
				setDatabaseUser((String)loaded.get(ADEMPIERE_DB_USER));
			if (loaded.containsKey(ADEMPIERE_DB_PASSWORD))
				setDatabasePassword((String)loaded.get(ADEMPIERE_DB_PASSWORD));
			if (loaded.containsKey(ADEMPIERE_DB_SYSTEM))
				setDatabaseSystemPassword((String)loaded.get(ADEMPIERE_DB_SYSTEM));

			if (p_panel != null)
			{
				p_panel.fMailServer.setText((String)p_properties.get(ADEMPIERE_MAIL_SERVER));
				p_panel.fMailUser.setText((String)p_properties.get(ADEMPIERE_MAIL_USER));
				p_panel.fMailPassword.setText((String)p_properties.get(ADEMPIERE_MAIL_PASSWORD));
				p_panel.fAdminEMail.setText((String)p_properties.get(ADEMPIERE_ADMIN_EMAIL));
			}
		}

		InetAddress localhost = null;
		String hostName = "unknown";
		try
		{
			localhost = InetAddress.getLocalHost();
			hostName = localhost.getHostName();
		}
		catch (Exception e)
		{
			log.severe("Cannot get local host name");
		}
		
		//	No environment file found - defaults
	//	envLoaded = false;
		if (!envLoaded)
		{
			log.info("Defaults");
			initJava();
			//
			setAdempiereHome(adempiereHome);
			setKeyStore(KeyStoreMgt.DEFAULT_KEYSTORE_NAME, DEFAULT_KEYSTORE_PASSWORD, DEFAULT_KEY_PASSWORD); // task 05047
			//	AppsServer
			initAppsServer();
			setAppsServer(hostName);
			//	Database Server
			initDatabase("");
			String connectionName = getDatabaseDiscovered();
			if (connectionName != null) {
				setDatabaseName(resolveDatabaseName(connectionName));
			}
			setDatabaseSystemPassword("");
			setDatabaseServer(hostName);
			setDatabaseUser("adempiere");
			setDatabasePassword("adempiere");
			//	Mail Server
			if (p_panel != null)
			{
				p_panel.fMailServer.setText(hostName);
				p_panel.fMailUser.setText("info");
				p_panel.fMailPassword.setText("");
				p_panel.fAdminEMail.setText("info@" + hostName);
			}
			//
		}	//	!envLoaded

		//	Default FTP stuff
		if (!p_properties.containsKey(ADEMPIERE_FTP_SERVER))
		{
			p_properties.setProperty(ADEMPIERE_FTP_SERVER, "localhost");
			p_properties.setProperty(ADEMPIERE_FTP_USER, "anonymous");
			p_properties.setProperty(ADEMPIERE_FTP_PASSWORD, "user@host.com");
			p_properties.setProperty(ADEMPIERE_FTP_PREFIX, "my");
		}
		//	Default Java Options
		if (!p_properties.containsKey(ADEMPIERE_JAVA_OPTIONS))
			p_properties.setProperty(ADEMPIERE_JAVA_OPTIONS, "-Xms64M -Xmx512M");
		//	Web Alias
		if (!p_properties.containsKey(ADEMPIERE_WEB_ALIAS) && localhost != null)
			p_properties.setProperty(ADEMPIERE_WEB_ALIAS, localhost.getCanonicalHostName());
		
		//	(String)p_properties.get(ADEMPIERE_DB_URL)	//	derived
		
		//	Keystore Alias
		if (!p_properties.containsKey(ADEMPIERE_KEYSTORECODEALIAS))
			p_properties.setProperty(ADEMPIERE_KEYSTORECODEALIAS, "adempiere");
		if (!p_properties.containsKey(ADEMPIERE_KEYSTOREWEBALIAS))
			p_properties.setProperty(ADEMPIERE_KEYSTOREWEBALIAS, "adempiere");

		return true;
	}	//	load
	
	
	public String resolveDatabaseName(String connectionName) {
		int index = p_panel.fDatabaseType.getSelectedIndex();
		if (index < 0 || index >= DBTYPE.length)
			log.warning("DatabaseType Index invalid: " + index);
		else if (m_databaseConfig[index] == null)
			log.warning("DatabaseType Config missing: " + DBTYPE[index]);
		else
			return m_databaseConfig[index].getDatabaseName(connectionName);
		return connectionName;
	}

	/**************************************************************************
	 * 	test
	 *	@return true if test ok
	 */
	public boolean test()
	{
		String error = testJava();
		if (error != null)
		{
			log.severe(error);
			return false;
		}
		
		error = testAdempiere();
		if (error != null)
		{
			log.warning(error);
			return false;
		}

		if (p_panel != null)
			p_panel.setStatusBar(p_panel.lAppsServer.getText());
		error = testAppsServer();
		if (error != null)
		{
			log.warning(error);
			return false;
		}
		
		if (p_panel != null)
			p_panel.setStatusBar(p_panel.lDatabaseServer.getText());
		error = testDatabase();
		if (error != null)
		{
			log.warning(error);
			return false;
		}

		if (p_panel != null)
			p_panel.setStatusBar(p_panel.lMailServer.getText());
		error = testMail();
		if (error != null)
		{
			log.warning(error);
			return false;
		}
		
		return true;
	}	//	test
	

	/**
	 * 	Test Adempiere and set AdempiereHome
	 *	@return error message or null if OK
	 */
	private String testAdempiere()
	{
		//	Adempiere Home
		m_adempiereHome = new File (getAdempiereHome());
		boolean pass =m_adempiereHome.exists();
		String error = "Not found: AdempiereHome = " + m_adempiereHome;
		if (p_panel != null)
			p_panel.signalOK(p_panel.okAdempiereHome, "ErrorAdempiereHome", 
					pass, true, error);
		if (!pass)
			return error;
		log.info("OK: AdempiereHome = " + m_adempiereHome);
		p_properties.setProperty(ADEMPIERE_HOME, m_adempiereHome.getAbsolutePath());
		System.setProperty(ADEMPIERE_HOME, m_adempiereHome.getAbsolutePath());
		
		// task 05047 begin of changes: work with the properties set from outside, only fall back to hardcoded strings
		// KeyStore
		final String keyStoreFileName = p_panel != null 
				? new String(p_panel.fKeyStoreFilename.getText())
				: (String)p_properties.get(ADEMPIERE_KEYSTORE_FILENAME);
		
		final String fullKeyStoreFileName = KeyStoreMgt.getKeystoreFileName(m_adempiereHome.getAbsolutePath(), keyStoreFileName);
		p_properties.setProperty(ADEMPIERE_KEYSTORE_FULL_FILENAME, fullKeyStoreFileName);
		
		//	KeyStore Password
		final String keyStorePassword = p_panel != null 
			? new String(p_panel.fKeyStorePass.getPassword())
			: (String)p_properties.get(ADEMPIERE_KEYSTOREPASS);
		pass = keyStorePassword != null && keyStorePassword.length() > 0;
		error = "Invalid Key Store Password = " + keyStorePassword;
		if (p_panel != null)
			p_panel.signalOK(p_panel.okKeyStore, "KeyStorePassword", 
					pass, true, error); 
		if (!pass)
			return error;
		
		final String keyPassword = p_panel != null 
				? new String(p_panel.fKeyPass.getPassword())
				: (String)p_properties.get(ADEMPIERE_KEYPASS);
		pass = keyPassword != null && keyPassword.length() > 0;
		error = "Invalid Key Store Password = " + keyStorePassword;
		if (p_panel != null)
			p_panel.signalOK(p_panel.okKeyStore, "KeyStorePassword", 
					pass, true, error); 
		if (!pass)
			return error;
		
		// if this property is null, then KeyStireMgt will use its default alias
		final String certificateAlias = (String)p_properties.get(ADEMPIERE_KEYSTORECODEALIAS);
		
		p_properties.setProperty(ADEMPIERE_KEYSTOREPASS, keyStorePassword);
		final KeyStoreMgt ks = p_panel != null
			? new KeyStoreMgt (fullKeyStoreFileName, p_panel.fKeyStorePass.getPassword(), certificateAlias, p_panel.fKeyPass.getPassword())
			: new KeyStoreMgt (fullKeyStoreFileName, keyStorePassword.toCharArray(), certificateAlias, keyPassword.toCharArray());
		// task 05047 end of changes
			
		ks.setCommonName(p_properties.getProperty(ADEMPIERE_CERT_CN));
		ks.setOrganization(p_properties.getProperty(ADEMPIERE_CERT_ORG));
		ks.setOrganizationUnit(p_properties.getProperty(ADEMPIERE_CERT_ORG_UNIT));
		ks.setLocation(p_properties.getProperty(ADEMPIERE_CERT_LOCATION));
		ks.setState(p_properties.getProperty(ADEMPIERE_CERT_STATE));
		ks.setCountry(p_properties.getProperty(ADEMPIERE_CERT_COUNTRY));
		error = p_panel != null 
			? ks.verify((JFrame)SwingUtilities.getWindowAncestor(p_panel))
			: ks.verify(null);
		pass = error == null;
		if (p_panel != null)
			p_panel.signalOK(p_panel.okKeyStore, "KeyStorePassword", 
					pass, true, error);
		if (!pass)
			return error;
		log.info("OK: KeyStore = " + fullKeyStoreFileName);
		return null;
	}	//	testAdempiere
	
	
	/**************************************************************************
	 * 	Test (optional) Mail
	 *	@return error message or null, if OK
	 */
	private String testMail()
	{
		//	Mail Server
		String server = p_panel != null
			? p_panel.fMailServer.getText()
			: (String)p_properties.get(ADEMPIERE_MAIL_SERVER);
		boolean pass = server != null && server.length() > 0
			&& server.toLowerCase().indexOf("localhost") == -1 
			&& !server.equals("127.0.0.1");
		String error = "Error Mail Server = " + server;
		InetAddress	mailServer = null;
		try
		{
			if (pass)
				mailServer = InetAddress.getByName(server);
		}
		catch (Exception e)
		{
			error += " - " + e.getMessage();
			pass = false;
		}
		if (p_panel != null)
			p_panel.signalOK(p_panel.okMailServer, "ErrorMailServer",
					pass, true, error);
		if (!pass)
		{
			p_properties.setProperty(ADEMPIERE_MAIL_SERVER, "");
			return error;
		}
		p_properties.setProperty(ADEMPIERE_MAIL_SERVER, mailServer.getHostName());

		//	Mail User
		String mailUser = p_panel != null 
			? p_panel.fMailUser.getText()
			: (String)p_properties.get(ADEMPIERE_MAIL_USER);
		String mailPassword = p_panel != null
			? new String(p_panel.fMailPassword.getPassword())
			: (String)p_properties.get(ADEMPIERE_MAIL_PASSWORD);
	//	m_errorString = "ErrorMailUser";
	//	log.config("Mail User = " + mailUser + "/" + mailPassword);

		//	Mail Address
		String adminEMailString = p_panel != null 
			? p_panel.fAdminEMail.getText()
			: (String)p_properties.get(ADEMPIERE_ADMIN_EMAIL);
		InternetAddress adminEMail = null;
		try
		{
			adminEMail = new InternetAddress (adminEMailString);
		}
		catch (Exception e)
		{
			error = "Not valid: " +  adminEMailString + " - " + e.getMessage();
			pass = false;
		}
		//
		if (pass)
		{
			error = "Not verified EMail = " + adminEMail;
			pass = testMailServer(mailServer, adminEMail, mailUser, mailPassword);
		}
		if (p_panel != null)
			p_panel.signalOK(p_panel.okMailUser, "ErrorMail", 
					pass, false, error);
		if (pass)
		{
			log.info("OK: EMail = " + adminEMail);
			p_properties.setProperty(ADEMPIERE_ADMIN_EMAIL, adminEMail.toString());
			p_properties.setProperty(ADEMPIERE_MAIL_USER, mailUser);
			p_properties.setProperty(ADEMPIERE_MAIL_PASSWORD, mailPassword);
			p_properties.setProperty(ADEMPIERE_MAIL_UPDATED, "No");
		}
		else
		{
			log.warning(error);
			p_properties.setProperty(ADEMPIERE_ADMIN_EMAIL, "");
			p_properties.setProperty(ADEMPIERE_MAIL_USER, "");
			p_properties.setProperty(ADEMPIERE_MAIL_PASSWORD, "");
			p_properties.setProperty(ADEMPIERE_MAIL_UPDATED, "");
		}
		return null;
	}	//	testMail
	
	/**
	 * 	Test Mail
	 * 	@param mailServer mail server
	 * 	@param adminEMail email of admin
	 * 	@param mailUser user ID
	 * 	@param mailPassword password
	 *  @return true of OK
	 */
	private boolean testMailServer(InetAddress	mailServer, InternetAddress adminEMail,
		String mailUser, String mailPassword)
	{
		boolean isGmail = mailServer.getHostName().equalsIgnoreCase("smtp.gmail.com");
		boolean smtpOK = false;
		boolean imapOK = false;
		if (testPort (mailServer, isGmail ? 587 : 25, true))
		{
			log.config("OK: SMTP Server contacted");
			smtpOK = true;
		}
		else
			log.info("SMTP Server NOT available");
		//
		if (testPort (mailServer, isGmail ? 995 : 110, true))
			log.config("OK: POP3 Server contacted");
		else
			log.info("POP3 Server NOT available");
		if (testPort (mailServer, isGmail ? 993 : 143, true))
		{
			log.config("OK: IMAP4 Server contacted");
			imapOK = true;
		}
		else
			log.info("IMAP4 Server NOT available");
		//
		if (!smtpOK)
		{
			String error = "No active Mail Server";
			if (p_panel != null)
				p_panel.signalOK (p_panel.okMailServer, "ErrorMailServer",
						false, false, error);
			log.warning(error);
			return false;
		}
		//
		try
		{
			EMail email = new EMail (new Properties(),
					mailServer.getHostName (),
					adminEMail.toString (), adminEMail.toString(),
					"Adempiere Server Setup Test", 
					"Test: " + getProperties());
			email.createAuthenticator (mailUser, mailPassword);
			if (EMail.SENT_OK.equals (email.send ()))
			{
				log.info("OK: Send Test Email to " + adminEMail);
			}
			else
			{
				log.warning("Could NOT send Email to " + adminEMail);
			}
		}
		catch (Exception ex)
		{
			log.severe(ex.getLocalizedMessage());
			return false;
		}

		//
		if (!imapOK)
			return false;

		//	Test Read Mail Access
		Properties props = new Properties();
		props.put("mail.store.protocol", "smtp");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.host", mailServer.getHostName());
		props.put("mail.user", mailUser);
		props.put("mail.smtp.auth", "true");
		if (isGmail) {
			props.put("impa.smtp.port", "993");
			props.put("mail.store.protocol", "imaps");
		}

		log.config("Connecting to " + mailServer.getHostName());
		//
		Session session = null;
		Store store = null;
		try
		{
			EMailAuthenticator auth = new EMailAuthenticator (mailUser, mailPassword);
			session = Session.getDefaultInstance(props, auth);
			session.setDebug (CLogMgt.isLevelFinest());
			log.config("Session=" + session);
			//	Connect to Store
			store = session.getStore(isGmail ? "imaps" : "imap");
			log.config("Store=" + store);
		}
		catch (NoSuchProviderException nsp)
		{
			log.warning("Mail IMAP Provider - " + nsp.getMessage());
			return false;
		}
		catch (Exception e)
		{
			log.warning("Mail IMAP - " + e.getMessage());
			return false;
		}
		try
		{
			store.connect(mailServer.getHostName(), mailUser, mailPassword);
			log.config("Store - connected");
			Folder folder = store.getDefaultFolder();
			Folder inbox = folder.getFolder("INBOX");
			log.info("OK: Mail Connect to " + inbox.getFullName() + " #Msg=" + inbox.getMessageCount());
			//
			store.close();
		}
		catch (MessagingException mex)
		{
			log.severe("Mail Connect " + mex.getMessage());
			return false;
		}
		return true;
	}	//	testMailServer
	
	
	/**************************************************************************
	 * 	Test Apps Server Port (client perspective)
	 *  @param protocol protocol (http, ..)
	 *  @param server server name
	 *  @param port port
	 *  @param file file name
	 *  @return true if able to connect
	 */
	protected boolean testPort (String protocol, String server, int port, String file)
	{
		System.out.println("testPort[" + protocol + "," + server + ", " + port + ", " + file +"]");
		URL url = null;
		try
		{
			url = new URL (protocol, server, port, file);
		}
		catch (MalformedURLException ex)
		{
			log.severe("No URL for Protocol=" + protocol 
				+ ", Server=" + server
				+ ": " + ex.getMessage());
			return false;
		}
		try
		{
			URLConnection c = url.openConnection();
			Object o = c.getContent();
			if (o == null)
				log.warning("In use=" + url);	//	error
			else
				log.warning("In Use=" + url);	//	error
		}
		catch (Exception ex)
		{
			log.fine("Not used=" + url);	//	ok
			return false;
		}
		return true;
	}	//	testPort

	/**
	 * 	Test Server Port
	 *  @param port port
	 *  @return true if able to create
	 */
	protected boolean testServerPort (int port)
	{
		System.out.println("testServerPort: " + port);
		try
		{
			ServerSocket ss = new ServerSocket (port);
			log.fine(ss.getInetAddress() + ":" + ss.getLocalPort() + " - created");
			ss.close();
		}
		catch (Exception ex)
		{
			log.warning("Port " + port + ": " + ex.getMessage());
			return false;
		}
		return true;
	}	//	testPort


	/**
	 * 	Test Port
	 *  @param host host
	 *  @param port port
	 *  @param shouldBeUsed true if it should be used
	 *  @return true if some server answered on port
	 */
	protected boolean testPort (InetAddress host, int port, boolean shouldBeUsed)
	{
		System.out.println("testPort[" + host.getHostAddress() + ", " + port + "]");
		Socket pingSocket = null;
		try
		{
			pingSocket = new Socket(host, port);
		}
		catch (Exception e)
		{
			if (shouldBeUsed)
				log.warning("Open Socket " + host + ":" + port + " - " + e.getMessage());
			else
				log.fine(host + ":" + port + " - " + e.getMessage());
			return false;
		}
		if (!shouldBeUsed)
			log.warning("Open Socket " + host + ":" + port + " - " + pingSocket);
		
		log.fine(host + ":" + port + " - " + pingSocket);
		if (pingSocket == null)
			return false;
		//	success
		try
		{
			pingSocket.close();
		}
		catch (IOException e)
		{
			log.warning("close socket=" + e.toString());
		}
		return true;
	}	//	testPort

	
	/**************************************************************************
	 * 	Save Settings
	 *	@return true if saved
	 */
	public boolean save()
	{
		//	Add
		p_properties.setProperty("ADEMPIERE_MAIN_VERSION", Adempiere.getMainVersion());
		p_properties.setProperty("ADEMPIERE_DATE_VERSION", Adempiere.getDateVersion());
		p_properties.setProperty("ADEMPIERE_DB_VERSION", Adempiere.getDatabaseVersion());

		log.finest(p_properties.toString());
		
		//	Before we save, load Ini
		Ini.setClient(false);
		String fileName = m_adempiereHome.getAbsolutePath() + File.separator + Ini.ADEMPIERE_PROPERTY_FILE;
		Ini.loadProperties(fileName);

		//	Save Environment
		fileName = m_adempiereHome.getAbsolutePath() + File.separator + ADEMPIERE_ENV_FILE;
		try
		{
			FileOutputStream fos = new FileOutputStream(new File(fileName));
			p_properties.store(fos, ADEMPIERE_ENV_FILE);
			fos.flush();
			fos.close();
		}
		catch (Exception e)
		{
			log.severe("Cannot save Properties to " + fileName + " - " + e.toString());
			if (p_panel != null)
				JOptionPane.showConfirmDialog(p_panel, 
					ConfigurationPanel.res.getString("ErrorSave"), 
					ConfigurationPanel.res.getString("AdempiereServerSetup"),
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			else
				System.err.println(ConfigurationPanel.res.getString("ErrorSave"));
			return false;
		}
		catch (Throwable t)
		{
			log.severe("Cannot save Properties to " + fileName + " - " + t.toString());
			if (p_panel != null)
				JOptionPane.showConfirmDialog(p_panel, 
					ConfigurationPanel.res.getString("ErrorSave"), 
					ConfigurationPanel.res.getString("AdempiereServerSetup"),
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			else
				System.err.println(ConfigurationPanel.res.getString("ErrorSave"));
			return false;
		}
		log.info(fileName);
		return saveIni();
	}	//	save
	
	/**
	 * 	Synchronize and save Connection Info in Ini
	 * 	@return true 
	 */
	private boolean saveIni()
	{
		Ini.setAdempiereHome(m_adempiereHome.getAbsolutePath());

		//	Create Connection
		String dbType = Database.DB_ORACLE;
		if (getDatabaseType().equals(DBTYPE_POSTGRESQL))
			dbType = Database.DB_POSTGRESQL;
		CConnectionAttributes connAttributes = null;
		try
		{
			connAttributes = new CConnectionAttributes();
			connAttributes.setDbType(dbType);
			connAttributes.setDbHost(getDatabaseServer());
			connAttributes.setDbPort(getDatabasePort());
			connAttributes.setDbName(getDatabaseName());
			connAttributes.setDbUid(getDatabaseUser());
			connAttributes.setDbPwd(getDatabasePassword());
			//
			connAttributes.setAppsHost(getAppsServer());
			connAttributes.setAppsPort(getAppsServerJNPPort());
			// cc.setConnectionProfile(CConnection.PROFILE_LAN);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "connection", e);
			return false;
		}
		Ini.setProperty(Ini.P_CONNECTION, connAttributes.toString());
		Ini.saveProperties(false);
		return true;
	}	//	saveIni
	
	
	/**
	 * 	Get Properties
	 *	@return properties
	 */
	Properties getProperties ()
	{
		return p_properties;
	}	//	getProperties
	
	/**
	 * 	Get Adempiere Home
	 *	@return adempiere home
	 */
	public String getAdempiereHome()
	{
		return p_panel != null 
			? p_panel.fAdempiereHome.getText()
			: (String)p_properties.get(ADEMPIERE_HOME);
	}	//	getAdempiereHome

	/**
	 * 	Set Adempiere Home
	 *	@param adempiereHome
	 */
	public void setAdempiereHome (String adempiereHome)
	{
		if (p_panel != null)
			p_panel.fAdempiereHome.setText(adempiereHome);
		else
			updateProperty(ADEMPIERE_HOME, adempiereHome);
	}	//	setAdempiereHome
	
	/**
	 * 	Get Key Store
	 *	@return password
	 */
	public String getKeyStore ()
	{
		char[] pw = p_panel.fKeyStorePass.getPassword();
		if (pw != null)
			return new String(pw);
		return "";
	}	//	getKeyStore

	/**
	 * 	Set Key Store Password
	 *	@param password
	 */
	// task 05047: setting more props
	public void setKeyStore (final String keyStoreFileName, final String keystorePass, final String keyPass)
	{
		if (p_panel != null)
		{
			p_panel.fKeyStoreFilename.setText(keyStoreFileName);
			p_panel.fKeyStorePass.setText(keystorePass);
			p_panel.fKeyPass.setText(keystorePass);
		}
		else
		{
			updateProperty(ADEMPIERE_KEYSTORE_FILENAME, keyStoreFileName);
			updateProperty(ADEMPIERE_KEYSTOREPASS, keystorePass);
			updateProperty(ADEMPIERE_KEYPASS, keyPass);
		}
	}	//	setKeyStore
	
	
	/**************************************************************************
	 * 	Java Settings
	 *************************************************************************/

	/** SUN VM (default)	*/
	private static String	JAVATYPE_SUN = "sun";
	/** Apple VM			*/
	private static String	JAVATYPE_MAC = "mac";
	/** IBM VM				*/
	private static String	JAVATYPE_IBM = "<ibm>";
	/** Open JDK			*/
	private static String	JAVATYPE_OPENJDK = "OpenJDK";
	/** Java VM Types		*/
	static String[]	JAVATYPE = new String[]
		{JAVATYPE_SUN, JAVATYPE_OPENJDK, JAVATYPE_MAC, JAVATYPE_IBM};
	
	/** Virtual machine Configurations	*/
	private Config[] m_javaConfig = new Config[]
	    {new ConfigVMSun(this), new ConfigVMOpenJDK(this), new ConfigVMMac(this), null};

	/**
	 * 	Init Database
	 */
	public void initJava()
	{
		int index = (p_panel != null ? p_panel.fJavaType.getSelectedIndex() : 0);
		initJava(index);
	}	//	initDatabase
	
	private void initJava(int index)
	{
		if (index < 0 || index >= JAVATYPE.length)
			log.warning("JavaType Index invalid: " + index);
		else if (m_javaConfig[index] == null)
		{
			log.warning("JavaType Config missing: " + JAVATYPE[index]);
			if (p_panel != null)
				p_panel.fJavaType.setSelectedIndex(0);
		}
		else
			m_javaConfig[index].init();
	}
	
	/**
	 * 	Test Java
	 *	@return error message or null of OK
	 */
	public String testJava()
	{
		int index = p_panel != null 
			? p_panel.fJavaType.getSelectedIndex()
			: setJavaType((String)p_properties.get(JAVA_TYPE));
		if (index < 0 || index >= JAVATYPE.length)
			return "JavaType Index invalid: " + index;
		else if (m_javaConfig[index] == null)
			return "JavaType Config class missing: " + index;
		return m_javaConfig[index].test();
	}	//	testJava
	
	/**
	 * 	Set Java Type
	 *	@param javaType The javaType to set.
	 */
	public int setJavaType (String javaType)
	{
		int index = -1;
		for (int i = 0; i < JAVATYPE.length; i++)
		{
			if (JAVATYPE[i].equals(javaType))
			{
				index = i;
				break;
			}
		}
		if (index == -1)
		{
			index = 0;
			log.warning("Invalid JavaType=" + javaType);
		}
		if (p_panel != null)
			p_panel.fJavaType.setSelectedIndex(index);
		else
			updateProperty(JAVA_TYPE, javaType);
		
		return index;
	}	//	setJavaType

	/**
	 * @return Returns the javaType.
	 */
	public String getJavaType ()
	{
		if( p_panel != null )
			return (String)p_panel.fJavaType.getSelectedItem();
		else
			return (String)p_properties.get(JAVA_TYPE);
	}

	/**
	 * @return Returns the javaHome.
	 */
	public String getJavaHome ()
	{
		if (p_panel != null)
			return p_panel.fJavaHome.getText();
		else
			return (String)p_properties.get(JAVA_HOME);
	}
	/**
	 * @param javaHome The javaHome to set.
	 */
	public void setJavaHome (String javaHome)
	{
		if (p_panel != null)
			p_panel.fJavaHome.setText(javaHome);
		else
			updateProperty(JAVA_HOME, javaHome);
	}
	
	/**************************************************************************
	 * 	Apps Server Settings
	 *************************************************************************/

	/**	JBoss (default)		*/
	protected static String	APPSTYPE_JBOSS = "jboss";
	/** GlassFish            */
    protected static String APPSTYPE_GLASSFISH = "glassfish";
	/** Application Server Type		*/
	static String[]	APPSTYPE = new String[]
		{ APPSTYPE_JBOSS
		, APPSTYPE_GLASSFISH
		};
	/** Database Configs	*/
	private Config[] m_appsConfig = new Config[]
	    { new ConfigJBoss(this)
	    , new ConfigGlassfish( this )
	    };

	/**
	 * 	Init Apps Server
	 */
	public void initAppsServer()
	{
		int index = (p_panel != null ? p_panel.fAppsType.getSelectedIndex() : 0);
		initAppsServer(index);
	}	//	initAppsServer
	
	private void initAppsServer(int index)
	{
		if (index < 0 || index >= APPSTYPE.length)
			log.warning("AppsServerType Index invalid: " + index);
		else if (m_appsConfig[index] == null)
		{
			log.warning("AppsServerType Config missing: " + APPSTYPE[index]);
			p_panel.fAppsType.setSelectedIndex(0);
		}
		else
			m_appsConfig[index].init();
	}
	
	/**
	 * 	Test Apps Server
	 *	@return error message or null of OK
	 */
	public String testAppsServer()
	{
		int index = p_panel != null 
			? p_panel.fAppsType.getSelectedIndex()
			: setAppsServerType((String)p_properties.get(ADEMPIERE_APPS_TYPE));
		if (index < 0 || index >= APPSTYPE.length)
			return "AppsServerType Index invalid: " + index;
		else if (m_appsConfig[index] == null)
			return "AppsServerType Config class missing: " + index;
		return m_appsConfig[index].test();
	}	//	testAppsServer
	
	
	/**
	 * 	Set Apps Server Type
	 *	@param appsType The appsType to set.
	 */
	public int setAppsServerType (String appsType)
	{
		int index = -1;
		for (int i = 0; i < APPSTYPE.length; i++)
		{
			if (APPSTYPE[i].equals(appsType))
			{
				index = i;
				break;
			}
		}
		if (index == -1)
		{
			index = 0;
			log.warning("Invalid AppsType=" + appsType);
		}
		if (p_panel != null)
			p_panel.fAppsType.setSelectedIndex(index);
		else
			updateProperty(ADEMPIERE_APPS_TYPE, appsType);
			
		return index;
	}	//	setAppsServerType

	/**
	 * 	Get Apps Server Type
	 *	@return Apps Server Type
	 */
	public String getAppsServerType ()
	{
		return p_panel != null
			? (String)p_panel.fAppsType.getSelectedItem()
			: (String)p_properties.get(ADEMPIERE_APPS_TYPE);
	}	//	setDatabaseType

	/**
	 * @return Returns the appsServer.
	 */
	public String getAppsServer ()
	{
		return p_panel != null 
			? p_panel.fAppsServer.getText()
			: (String)p_properties.get(ADEMPIERE_APPS_SERVER);
	}
	/**
	 * @param appsServer The appsServer to set.
	 */
	public void setAppsServer (String appsServer)
	{
		if (p_panel != null)
			p_panel.fAppsServer.setText(appsServer);
		else
			updateProperty(ADEMPIERE_APPS_SERVER, appsServer);
	}
	
	/**
	 * @return Returns the appsServerDeployDir.
	 */
	public String getAppsServerDeployDir ()
	{
		return p_panel != null
			? p_panel.fDeployDir.getText()
			: (String)p_properties.get(ADEMPIERE_APPS_DEPLOY);
	}
	/**
	 * @param appsServerDeployDir The appsServerDeployDir to set.
	 */
	public void setAppsServerDeployDir (String appsServerDeployDir)
	{
		if (p_panel != null)
			p_panel.fDeployDir.setText(appsServerDeployDir);
		else
			updateProperty(ADEMPIERE_APPS_DEPLOY, appsServerDeployDir);
	}
	/**
	 * @param enable if true enable entry
	 */
	public void setAppsServerDeployDir (boolean enable)
	{
		if (p_panel != null)
		{
			p_panel.fDeployDir.setEnabled(enable);
			p_panel.bDeployDir.setEnabled(enable);
		}
	}
	/**
	 * @return Returns the appsServerJNPPort.
	 */
	public int getAppsServerJNPPort ()
	{
		String port = p_panel != null
			? p_panel.fJNPPort.getText()
			: (String)p_properties.get(ADEMPIERE_JNP_PORT);
		try
		{
			return Integer.parseInt(port);
		}
		catch (Exception e)
		{
			setAppsServerJNPPort("0");
		}
		return 0;
	}
	/**
	 * @param appsServerJNPPort The appsServerJNPPort to set.
	 */
	public void setAppsServerJNPPort (String appsServerJNPPort)
	{
		if (p_panel != null)
			p_panel.fJNPPort.setText(appsServerJNPPort);
		else
			updateProperty(ADEMPIERE_JNP_PORT, appsServerJNPPort);
	}

	/**
	 * @param enable if enable JNP entry
	 */
	public void setAppsServerJNPPort (boolean enable)
	{
		if (p_panel != null)
			p_panel.fJNPPort.setEnabled(enable);
	}
	/**
	 * @return Returns the appsServerSSLPort.
	 */
	public int getAppsServerSSLPort ()
	{
		String port = p_panel != null
			? p_panel.fSSLPort.getText()
			: (String)p_properties.get(ADEMPIERE_SSL_PORT);
		try
		{
			return Integer.parseInt(port);
		}
		catch (Exception e)
		{
			setAppsServerSSLPort("0");
		}
		return 0;
	}
	/**
	 * @param appsServerSSLPort The appsServerSSLPort to set.
	 */
	public void setAppsServerSSLPort (String appsServerSSLPort)
	{
		if (p_panel != null)
			p_panel.fSSLPort.setText(appsServerSSLPort);
		else
			updateProperty(ADEMPIERE_SSL_PORT, appsServerSSLPort);
	}
	/**
	 * @param enable if tre enable SSL entry
	 */
	public void setAppsServerSSLPort (boolean enable)
	{
		if (p_panel != null)
			p_panel.fSSLPort.setEnabled(enable);
	}
	/**
	 * @return Returns the appsServerWebPort.
	 */
	public int getAppsServerWebPort ()
	{
		String port = p_panel != null
			? p_panel.fWebPort.getText()
			: (String)p_properties.get(ADEMPIERE_WEB_PORT);
		try
		{
			return Integer.parseInt(port);
		}
		catch (Exception e)
		{
			setAppsServerWebPort("0");
		}
		return 0;
	}
	/**
	 * @param appsServerWebPort The appsServerWebPort to set.
	 */
	public void setAppsServerWebPort (String appsServerWebPort)
	{
		if (p_panel != null)
			p_panel.fWebPort.setText(appsServerWebPort);
		else
			updateProperty(ADEMPIERE_WEB_PORT, appsServerWebPort);
	}
	/**
	 * @param enable if tre enable Web entry
	 */
	public void setAppsServerWebPort (boolean enable)
	{
		if (p_panel != null)
			p_panel.fWebPort.setEnabled(enable);
	}
	
	
	/**************************************************************************
	 * 	Database Settings
	 *************************************************************************/
	
	/** Oracle directory	*/
	private static String	DBTYPE_ORACLE = "oracle";
	/** Oracle XP	*/
	private static String	DBTYPE_ORACLEXE = "oracleXE";
        
	/** PostgreSQL          */
	private static String	DBTYPE_POSTGRESQL = "postgresql";
	
	/** Database Types		*/
	static String[]	DBTYPE = new String[]
	{	DBTYPE_ORACLEXE,
		DBTYPE_ORACLE, 
        //begin e-evolution vpj-cd 02/07/2005 PostgreSQL
        DBTYPE_POSTGRESQL 
    };
	    //end e-evolution vpj-cd 02/07/2005 PostgreSQL
		
	/** Database Configs	*/
	private Config[] m_databaseConfig = new Config[]
	    {
		new ConfigOracle(this,true), 
		new ConfigOracle(this,false), 
		//begin e-evolution vpj-cd 02/07/2005 PostgreSQL
		new ConfigPostgreSQL(this)  
		//		end e-evolution vpj-cd 02/07/2005 PostgreSQL
		};

	/**
	 * 	Init Database
	 * 	@param selected DB
	 */
	public void initDatabase(String selected)
	{
		int index = (p_panel != null ? p_panel.fDatabaseType.getSelectedIndex() : 0);
		initDatabase(selected, index);
	}	//	initDatabase
	
	private void initDatabase(String selected, int index)
	{
		if (index < 0 || index >= DBTYPE.length)
			log.warning("DatabaseType Index invalid: " + index);
		else if (m_databaseConfig[index] == null)
		{
			log.warning("DatabaseType Config missing: " + DBTYPE[index]);
			if (p_panel != null)
				p_panel.fDatabaseType.setSelectedIndex(0);
		}
		else
		{
			m_databaseConfig[index].init();
			
			if (p_panel != null) 
			{
				String[] databases = m_databaseConfig[index].discoverDatabases(selected);
				DefaultComboBoxModel model = new DefaultComboBoxModel(databases);
				p_panel.fDatabaseDiscovered.setModel(model); 
				p_panel.fDatabaseDiscovered.setEnabled(databases.length != 0);
				if (databases.length > 0)
					p_panel.fDatabaseName.setText(databases[0]);
			}
		}
	}
	
	/**
	 * 	Test Database
	 *	@return error message or null of OK
	 */
	public String testDatabase()
	{
		int index = p_panel != null 
			? p_panel.fDatabaseType.getSelectedIndex()
			: setDatabaseType((String)p_properties.get(ADEMPIERE_DB_TYPE));
		if (index < 0 || index >= DBTYPE.length)
			return "DatabaseType Index invalid: " + index;
		else if (m_databaseConfig[index] == null)
			return "DatabaseType Config class missing: " + index;
		return m_databaseConfig[index].test();
	}	//	testDatabase
	
	
	/**
	 * 	Set Database Type
	 *	@param databaseType The databaseType to set.
	 */
	public int setDatabaseType (String databaseType)
	{
		int index = -1;
		for (int i = 0; i < DBTYPE.length; i++)
		{
			if (DBTYPE[i].equals(databaseType))
			{
				index = i;
				break;
			}
		}
		if (index == -1)
		{
			index = 0;
			log.warning("Invalid DatabaseType=" + databaseType);
		}
		if (p_panel != null)
			p_panel.fDatabaseType.setSelectedIndex(index);
		else
			updateProperty(ADEMPIERE_DB_TYPE, databaseType);
		
		return index;
	}	//	setDatabaseType
	
	/**
	 * @return Returns the databaseType.
	 */
	public String getDatabaseType ()
	{
		return p_panel != null
			? (String)p_panel.fDatabaseType.getSelectedItem()
			: (String)p_properties.get(ADEMPIERE_DB_TYPE);
	}
	/**
	 * @return Returns the database Discovered.
	 */
	public String getDatabaseDiscovered ()
	{
		return (String)p_panel.fDatabaseDiscovered.getSelectedItem();
	}
	/**
	 * @param databaseDiscovered The database Discovered to set.
	 */
	public void setDatabaseDiscovered (String databaseDiscovered)
	{
		if (p_panel != null)
			p_panel.fDatabaseDiscovered.setSelectedItem(databaseDiscovered);
	}
	
	/**
	 * @return Returns the databaseName.
	 */
	public String getDatabaseName ()
	{
		return p_panel != null
			? p_panel.fDatabaseName.getText()
			: (String)p_properties.get(ADEMPIERE_DB_NAME);
	}
	/**
	 * @param databaseName The databaseName to set.
	 */
	public void setDatabaseName (String databaseName)
	{
		if (p_panel != null)
			p_panel.fDatabaseName.setText(databaseName);
		else
			updateProperty(ADEMPIERE_DB_NAME, databaseName);
	}
	
	/**
	 * @return Returns the database User Password.
	 */
	public String getDatabasePassword ()
	{
		if (p_panel != null)
		{
			char[] pw = p_panel.fDatabasePassword.getPassword();
			if (pw != null)
				return new String(pw);
			return "";
		}
		else
		{
			String pw = (String)p_properties.get(ADEMPIERE_DB_PASSWORD);
			return (pw != null ? pw : "");
		}
	}
	/**
	 * @param databasePassword The databasePassword to set.
	 */
	public void setDatabasePassword (String databasePassword)
	{
		if (p_panel != null)
			p_panel.fDatabasePassword.setText(databasePassword);
		else
			updateProperty(ADEMPIERE_DB_PASSWORD, databasePassword);
	}
	/**
	 * @return Returns the databasePort.
	 */
	public int getDatabasePort ()
	{
		String port = p_panel != null
			? p_panel.fDatabasePort.getText()
			: (String)p_properties.get(ADEMPIERE_DB_PORT);
		try
		{
			return Integer.parseInt(port);
		}
		catch (Exception e)
		{
			setDatabasePort("0");
		}
		return 0;
	}	//	getDatabasePort
	/**
	 * @param databasePort The databasePort to set.
	 */
	public void setDatabasePort (String databasePort)
	{
		if (p_panel != null)
			p_panel.fDatabasePort.setText(databasePort);
		else
			updateProperty(ADEMPIERE_DB_PORT, databasePort);
	}
	/**
	 * @return Returns the databaseServer.
	 */
	public String getDatabaseServer ()
	{
		return p_panel != null
			? p_panel.fDatabaseServer.getText()
			: (String)p_properties.get(ADEMPIERE_DB_SERVER);
	}
	/**
	 * @param databaseServer The databaseServer to set.
	 */
	public void setDatabaseServer (String databaseServer)
	{
		if (p_panel != null)
			p_panel.fDatabaseServer.setText(databaseServer);
		else
			updateProperty(ADEMPIERE_DB_SERVER, databaseServer);
	}
	/**
	 * @return Returns the databaseSystemPassword.
	 */
	public String getDatabaseSystemPassword ()
	{
		if (p_panel != null)
		{
			char[] pw = p_panel.fSystemPassword.getPassword();
			if (pw != null)
				return new String(pw);
			return "";
		}
		else
		{
			String pw = (String)p_properties.get(ADEMPIERE_DB_SYSTEM);
			return (pw != null ? pw : "");
		}
	}
	/**
	 * @param databaseSystemPassword The databaseSystemPassword to set.
	 */
	public void setDatabaseSystemPassword (String databaseSystemPassword)
	{
		if (p_panel != null)
			p_panel.fSystemPassword.setText(databaseSystemPassword);
		else
			updateProperty(ADEMPIERE_DB_SYSTEM, databaseSystemPassword);
	}
	/**
	 *	@param enable enable Database System Password
	 */
	public void setDatabaseSystemPassword (boolean enable)
	{
		if (p_panel != null)
			p_panel.fSystemPassword.setEnabled(enable);
	}
	/**
	 * @return Returns the databaseUser.
	 */
	public String getDatabaseUser ()
	{
		return p_panel != null
			? p_panel.fDatabaseUser.getText()
			: (String)p_properties.get(ADEMPIERE_DB_USER);
	}
	/**
	 * @param databaseUser The databaseUser to set.
	 */
	public void setDatabaseUser (String databaseUser)
	{
		if (p_panel != null)
			p_panel.fDatabaseUser.setText(databaseUser);
		else
			updateProperty(ADEMPIERE_DB_USER, databaseUser);
	}
	
}	//	ConfigurationData

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
package org.compiere.util;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.plaf.AdempiereLookAndFeel;
import org.adempiere.plaf.MetasFreshTheme;
import org.adempiere.util.Check;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.ModelValidationEngine;
import org.slf4j.Logger;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;

/**
 * Load & Save INI Settings from property file
 * Initiated in Adempiere.startup
 * Settings activated in ALogin.getIni
 *
 * @author Jorg Janke
 * @version $Id$
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>FR [ 1658127 ] Select charset encoding on import
 *         <li>FR [ 2406123 ] Ini.saveProperties fails if target directory does not exist
 */
public final class Ini implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3666529972922769528L;

	/** Property file name */
	public static final String METASFRESH_PROPERTY_FILE = "metasfresh.properties";

	/** Apps User ID */
	public static final String P_UID = "ApplicationUserID";
	private static final String DEFAULT_UID = "";
	/** Apps Password */
	public static final String P_PWD = "ApplicationPassword";
	private static final String DEFAULT_PWD = "";
	/** Store Password */
	public static final String P_STORE_PWD = "StorePassword";
	private static final boolean DEFAULT_STORE_PWD = true;
	/** Trace Level */
	public static final String P_TRACELEVEL = "TraceLevel";
	private static final String DEFAULT_TRACELEVEL = "WARNING";
	/** Trace to File (Y/N). */
	public static final String P_TRACEFILE_ENABLED = "TraceFile";
	private static final boolean DEFAULT_TRACEFILE_ENABLED = false;

	/** Language */
	public static final String P_LANGUAGE = "Language";
	private static final String DEFAULT_LANGUAGE = Language.getName(System.getProperty("user.language") + "_" + System.getProperty("user.country"));
	/** Ini File Name */
	public static final String P_INI = "FileNameINI";
	private static final String DEFAULT_INI = "";
	/** Connection Details */
	public static final String P_CONNECTION = "Connection";
	private static final String DEFAULT_CONNECTION = "";

	/** Look & Feel */
	public static final String P_UI_LOOK = "UILookFeel";

	private static final String DEFAULT_UI_LOOK = AdempiereLookAndFeel.NAME;
	/** UI Theme */

	private static final String DEFAULT_UI_THEME = MetasFreshTheme.NAME;
	/** UI Theme */
	public static final String P_UI_THEME = "UITheme";

	/**
	 * Flat Color UI
	 * public static final String P_UI_FLAT = "UIFlat";
	 * private static final boolean DEFAULT_UI_FLAT = false;
	 */

	/** Auto Save */
	public static final String P_A_COMMIT = "AutoCommit";
	private static final boolean DEFAULT_A_COMMIT = true;
	/** Auto Login */
	public static final String P_A_LOGIN = "AutoLogin";
	private static final boolean DEFAULT_A_LOGIN = false;
	/** Auto New Record */
	public static final String P_A_NEW = "AutoNew";
	private static final boolean DEFAULT_A_NEW = false;
	/** Dictionary Maintenance */
	public static final String P_ADEMPIERESYS = "AdempiereSys";	// Save system records
	private static final boolean DEFAULT_ADEMPIERESYS = false;
	/** Log Migration Script */
	public static final String P_LOGMIGRATIONSCRIPT = "LogMigrationScript";	// Log migration script
	private static final boolean DEFAULT_LOGMIGRATIONSCRIPT = false;
	/** Show Acct Tabs */
	public static final String P_SHOW_ACCT = "ShowAcct";
	private static final boolean DEFAULT_SHOW_ACCT = true;
	/** Show Advanced Tabs */
	public static final String P_SHOW_ADVANCED = "ShowAdvanced";
	private static final boolean DEFAULT_SHOW_ADVANCED = true;
	/** Show Translation Tabs */
	public static final String P_SHOW_TRL = "ShowTrl";
	private static final boolean DEFAULT_SHOW_TRL = false;
	/** Cache Windows */
	public static final String P_CACHE_WINDOW = "CacheWindow";
	private static final boolean DEFAULT_CACHE_WINDOW = true;
	/** Temp Directory */
	public static final String P_TEMP_DIR = "TempDir";
	private static final String DEFAULT_TEMP_DIR = "";
	/** Role */
	public static final String P_ROLE = "Role";
	private static final String DEFAULT_ROLE = "";
	/** Client Name */
	public static final String P_CLIENT = "Client";
	private static final String DEFAULT_CLIENT = "";
	/** Org Name */
	public static final String P_ORG = "Organization";
	private static final String DEFAULT_ORG = "";

	/** Printer Name */
	public static final String P_PRINTER = "Printer";
	private static final String DEFAULT_PRINTER = "";
	// metas: adding support for an additional label printer
	public static final String P_LABELPRINTER = "LabelPrinter";
	private static final String DEFAULT_LABELPRINTER = "";

	// metas: adding support for a report server
	public static final String P_REPORT_PREFIX = "ReportServer";
	private static final String DEFAULT_REPORT_SERVER = "";

	/** Warehouse Name */
	public static final String P_WAREHOUSE = "Warehouse";
	private static final String DEFAULT_WAREHOUSE = "";
	/** Current Date */
	public static final String P_TODAY = "CDate";
	private static final Timestamp DEFAULT_TODAY = new Timestamp(System.currentTimeMillis());
	/** Print Preview */
	public static final String P_PRINTPREVIEW = "PrintPreview";
	private static final boolean DEFAULT_PRINTPREVIEW = false;
	/** Validate connection on startup */
	public static final String P_VALIDATE_CONNECTION_ON_STARTUP = "ValidateConnectionOnStartup";
	private static final boolean DEFAULT_VALIDATE_CONNECTION_ON_STARTUP = false;

	/** Single instance per window id **/
	public static final String P_SINGLE_INSTANCE_PER_WINDOW = "SingleInstancePerWindow";
	public static final boolean DEFAULT_SINGLE_INSTANCE_PER_WINDOW = false;

	/** Open new windows as maximized **/
	public static final String P_OPEN_WINDOW_MAXIMIZED = "OpenWindowMaximized";

	// task 09355: Open everything as maximized from the beginning
	public static final boolean DEFAULT_OPEN_WINDOW_MAXIMIZED = true;
	//
	private static final String P_WARNING = "Warning";
	private static final String DEFAULT_WARNING = "Do_not_change_any_of_the_data_as_they_will_have_undocumented_side_effects.";
	private static final String P_WARNING_de = "WarningD";
	private static final String DEFAULT_WARNING_de = "Einstellungen_nicht_aendern,_da_diese_undokumentierte_Nebenwirkungen_haben.";

	/** Charset */
	public static final String P_CHARSET = "Charset";
	/** Charser Default Value */
	private static final String DEFAULT_CHARSET = Charset.defaultCharset().name();

	/** Load tab fields meta data using background thread **/
	public static final String P_LOAD_TAB_META_DATA_BG = "LoadTabMetaDataBackground";

	public static final String DEFAULT_LOAD_TAB_META_DATA_BG = "N";

	/** Ini PropertyName to default value */
	private static final ImmutableMap<String, String> PROPERTY_DEFAULTS = ImmutableMap.<String, String> builder()
			.put(P_UID, DEFAULT_UID)
			.put(P_PWD, DEFAULT_PWD)
			.put(P_TRACELEVEL, DEFAULT_TRACELEVEL)
			.put(P_TRACEFILE_ENABLED, DisplayType.toBooleanString(DEFAULT_TRACEFILE_ENABLED))
			.put(P_LANGUAGE, DEFAULT_LANGUAGE)
			.put(P_INI, DEFAULT_INI)
			.put(P_CONNECTION, DEFAULT_CONNECTION)
			.put(P_STORE_PWD, DisplayType.toBooleanString(DEFAULT_STORE_PWD))
			.put(P_UI_LOOK, DEFAULT_UI_LOOK)
			.put(P_UI_THEME, DEFAULT_UI_THEME)
			.put(P_A_COMMIT, DisplayType.toBooleanString(DEFAULT_A_COMMIT))
			.put(P_A_LOGIN, DisplayType.toBooleanString(DEFAULT_A_LOGIN))
			.put(P_A_NEW, DisplayType.toBooleanString(DEFAULT_A_NEW))
			.put(P_ADEMPIERESYS, DisplayType.toBooleanString(DEFAULT_ADEMPIERESYS))
			.put(P_LOGMIGRATIONSCRIPT, DisplayType.toBooleanString(DEFAULT_LOGMIGRATIONSCRIPT))
			.put(P_SHOW_ACCT, DisplayType.toBooleanString(DEFAULT_SHOW_ACCT))
			.put(P_SHOW_TRL, DisplayType.toBooleanString(DEFAULT_SHOW_TRL))
			.put(P_SHOW_ADVANCED, DisplayType.toBooleanString(DEFAULT_SHOW_ADVANCED))
			.put(P_CACHE_WINDOW, DisplayType.toBooleanString(DEFAULT_CACHE_WINDOW))
			.put(P_TEMP_DIR, DEFAULT_TEMP_DIR)
			.put(P_ROLE, DEFAULT_ROLE)
			.put(P_CLIENT, DEFAULT_CLIENT)
			.put(P_ORG, DEFAULT_ORG)
			.put(P_WAREHOUSE, DEFAULT_WAREHOUSE)
			.put(P_TODAY, DEFAULT_TODAY.toString())
			.put(P_REPORT_PREFIX, DEFAULT_REPORT_SERVER)
			.put(P_PRINTPREVIEW, DisplayType.toBooleanString(DEFAULT_PRINTPREVIEW))
			.put(P_VALIDATE_CONNECTION_ON_STARTUP, DisplayType.toBooleanString(DEFAULT_VALIDATE_CONNECTION_ON_STARTUP))
			.put(P_SINGLE_INSTANCE_PER_WINDOW, DisplayType.toBooleanString(DEFAULT_SINGLE_INSTANCE_PER_WINDOW))
			.put(P_OPEN_WINDOW_MAXIMIZED, DisplayType.toBooleanString(DEFAULT_OPEN_WINDOW_MAXIMIZED))
			.put(P_WARNING, DEFAULT_WARNING)
			.put(P_WARNING_de, DEFAULT_WARNING_de)
			.put(P_CHARSET, DEFAULT_CHARSET)
			.put(P_LOAD_TAB_META_DATA_BG, DEFAULT_LOAD_TAB_META_DATA_BG)
			.put(P_PRINTER, DEFAULT_PRINTER)
			.put(P_LABELPRINTER, DEFAULT_LABELPRINTER)
			.build();

	/**
	 * List of properties which are available only as a client. In case of client properties and we are in server mode, {@link #setProperty(String, String)} and {@link #getProperty(String)} will
	 * operate on {@link Env#getCtx()} instead.
	 */
	private static final Set<String> PROPERTIES_CLIENT = ImmutableSet.<String> builder()
			.add(P_ADEMPIERESYS)
			.add(P_LOGMIGRATIONSCRIPT)
			.build();

	/** List of property names which shall be skipped from encryption */
	private static final Set<String> PROPERTIES_SKIP_ENCRYPTION = ImmutableSet.<String> builder()
			.add(P_WARNING)
			.add(P_WARNING_de)
			.build();

	/** Container for Properties */
	private static Properties s_prop = new Properties();

	private static String s_propertyFileName = null;

	/** Logger */
	private static final transient Logger log = LogManager.getLogger(Ini.class.getName());

	private Ini()
	{
		super();
	}

	/**
	 * Save INI parameters to disk
	 *
	 */
	public static void saveProperties()
	{
		if (Ini.isClient() && DB.isConnected())
		{
			// Call ModelValidators beforeSaveProperties
			ModelValidationEngine.get().beforeSaveProperties();
		}

		String fileName = getFileName();
		FileOutputStream fos = null;
		try
		{
			File f = new File(fileName);
			f.getParentFile().mkdirs(); // Create all dirs if not exist - teo_sarca FR [ 2406123 ]
			fos = new FileOutputStream(f);
			s_prop.store(fos, "Adempiere");
			fos.flush();
			fos.close();
		}
		catch (Exception e)
		{
			log.error("Cannot save Properties to " + fileName + " - " + e.toString());
			return;
		}
		catch (Throwable t)
		{
			log.error("Cannot save Properties to " + fileName + " - " + t.toString());
			return;
		}
		log.info("Saved properties to {}", fileName);

	}	// save

	/**
	 * Load INI parameters from disk
	 *
	 * @param reload reload
	 */
	public static void loadProperties(boolean reload)
	{
		loadProperties(getFileName());
	}	// loadProperties

	/**
	 * Load INI parameters from filename.
	 * Logger is on default level (INFO)
	 *
	 * @param filename to load
	 * @return true if first time
	 */
	public static boolean loadProperties(String filename)
	{
		boolean loadOK = true;
		boolean firstTime = false;
		s_prop = new Properties();
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(filename);
			s_prop.load(fis);
			fis.close();
		}
		catch (FileNotFoundException e)
		{
			log.warn("{} not found", filename);
			loadOK = false;
		}
		catch (Exception e)
		{
			log.error("Error while loading {}", filename, e);
			loadOK = false;
		}
		catch (Throwable t)
		{
			log.error("Error while loading {}", filename, t);
			loadOK = false;
		}
		if (!loadOK || "".equals(s_prop.getProperty(P_TODAY, "")))
		{
			log.info("Properties file {} is missing. Asking for license approval.", filename);
			firstTime = true;
			if (isShowLicenseDialog())
				if (!IniDialog.accept())
					System.exit(-1);
		}

		checkProperties();

		// Save if not exist or could not be read
		if (!loadOK || firstTime)
		{
			saveProperties();
		}
		s_loaded = true;
		log.info("Loaded {} properties from {}", s_prop.size(), filename);
		s_propertyFileName = filename;

		return firstTime;
	}	// loadProperties

	private static void checkProperties()
	{
		// Check/set properties defaults
		for (Map.Entry<String, String> propertyName2defaultValue : PROPERTY_DEFAULTS.entrySet())
		{
			final String defaultValue = propertyName2defaultValue.getValue();
			if (defaultValue != null && defaultValue.length() > 0)
			{
				final String propertyName = propertyName2defaultValue.getKey();
				checkProperty(propertyName, defaultValue);
			}
		}

		//
		String tempDir = System.getProperty("java.io.tmpdir");
		if (tempDir == null || tempDir.length() <= 1)
			tempDir = getMetasfreshHome();
		if (tempDir == null)
			tempDir = "";
		checkProperty(P_TEMP_DIR, tempDir);

		if (System.getProperty(P_TRACEFILE_ENABLED) != null)
		{
			final boolean traceFileEnabled = Boolean.valueOf(System.getProperty(P_TRACEFILE_ENABLED));
			checkProperty(P_TRACEFILE_ENABLED, DisplayType.toBooleanString(traceFileEnabled));
		}
	}

	/**
	 * Delete Property file
	 */
	public static void deletePropertyFile()
	{
		String fileName = getFileName();
		File file = new File(fileName);
		if (file.exists())
		{
			try
			{
				if (!file.delete())
					file.deleteOnExit();
				s_prop = new Properties();
				log.info("Deleted properties file: {}", fileName);
			}
			catch (Exception e)
			{
				log.warn("Cannot delete properties file: {}", fileName, e);
			}
		}
	}	// deleteProperties

	/**
	 * Load property and set to default, if not existing
	 *
	 * @param key Key
	 * @param defaultValue Default Value
	 * @return Property
	 */
	private static String checkProperty(final String key, final String defaultValue)
	{
		final String result;
		if (PROPERTIES_SKIP_ENCRYPTION.contains(key))
		{
			result = defaultValue;
		}
		else if (!isClient())
		{
			result = s_prop.getProperty(key, SecureInterface.CLEARVALUE_START + defaultValue + SecureInterface.CLEARVALUE_END);
		}
		else
		{
			result = s_prop.getProperty(key, SecureEngine.encrypt(defaultValue));
		}
		s_prop.setProperty(key, result);
		return result;
	}	// checkProperty

	/**
	 * Return File Name of INI file
	 *
	 * <pre>
	 *  Examples:
	 *     C:\WinNT\Profiles\jjanke\Adempiere.properties
	 *      D:\Adempiere\Adempiere.properties
	 *      Adempiere.properties
	 * </pre>
	 *
	 * Can be overwritten by -DPropertyFile=myFile allowing multiple
	 * configurations / property files.
	 *
	 * @return file name
	 */
	private static String getFileName()
	{
		// Try explicitly configured "PropertyFile" property
		if (!Check.isEmpty(System.getProperty("PropertyFile"), true))
		{
			return System.getProperty("PropertyFile");
		}

		// Try explicitly configured "PropertyFile" system environment variable
		if (!Check.isEmpty(System.getenv("PropertyFile"), true))
		{
			return System.getenv("PropertyFile");
		}

		String propertyFileName = getMetasfreshHome();
		if (!propertyFileName.endsWith(File.separator))
		{
			propertyFileName += File.separator;
		}
		propertyFileName += METASFRESH_PROPERTY_FILE;

		return propertyFileName;
	}	// getFileName

	/**************************************************************************
	 * Set Property
	 *
	 * @param key Key
	 * @param value Value
	 */
	public static void setProperty(String key, String value)
	{
		// If it's a client property and we are in server mode, update the context instead of Ini file
		if (!Ini.isClient() && PROPERTIES_CLIENT.contains(key))
		{
			Env.getCtx().setProperty(key, value);
			return;
		}

		// log.trace(key + "=" + value);
		if (s_prop == null)
			s_prop = new Properties();
		if (PROPERTIES_SKIP_ENCRYPTION.contains(key))
		{
			s_prop.setProperty(key, value);
		}
		else if (!isClient())
		{
			s_prop.setProperty(key, SecureInterface.CLEARVALUE_START + value + SecureInterface.CLEARVALUE_END);
		}
		else
		{
			if (value == null)
				s_prop.setProperty(key, "");
			else
			{
				String eValue = SecureEngine.encrypt(value);
				if (eValue == null)
					s_prop.setProperty(key, "");
				else
					s_prop.setProperty(key, eValue);
			}
		}
	}

	/**
	 * Set Property
	 *
	 * @param key Key
	 * @param value Value
	 */
	public static void setProperty(String key, boolean value)
	{
		setProperty(key, DisplayType.toBooleanString(value));
	}   // setProperty

	/**
	 * Set Property
	 *
	 * @param key Key
	 * @param value Value
	 */
	public static void setProperty(String key, int value)
	{
		setProperty(key, String.valueOf(value));
	}   // setProperty

	/**
	 * Get Property
	 *
	 * @param key Key
	 * @return Value
	 */
	public static String getProperty(String key)
	{
		if (key == null)
			return "";

		// If it's a client property and we are in server mode, get value from context instead of Ini file
		if (!Ini.isClient() && PROPERTIES_CLIENT.contains(key))
		{
			final String value = Env.getCtx().getProperty(key);
			return value == null ? "" : value;
		}

		String retStr = s_prop.getProperty(key, "");
		if (retStr == null || retStr.length() == 0)
			return "";
		//
		String value = SecureEngine.decrypt(retStr);
		// log.trace(key + "=" + value);
		if (value == null)
			return "";
		return value;
	}	// getProperty

	/**
	 * Get Property as Boolean
	 *
	 * @param key Key
	 * @return Value
	 */
	public static boolean isPropertyBool(String key)
	{
		return DisplayType.toBoolean(getProperty(key), false);
	}	// getProperty

	/**
	 * Cache Windows
	 *
	 * @return true if windows are cached
	 */
	public static boolean isCacheWindow()
	{
		return isPropertyBool(P_CACHE_WINDOW);
	}	// isCacheWindow

	/**************************************************************************
	 * Get Properties
	 *
	 * @return Ini properties
	 */
	public static Properties getProperties()
	{
		return s_prop;
	}   // getProperties

	/**
	 * toString
	 *
	 * @return String representation
	 */
	public static String getAsString()
	{
		StringBuffer buf = new StringBuffer("Ini[");
		Enumeration<Object> e = s_prop.keys();
		while (e.hasMoreElements())
		{
			String key = (String)e.nextElement();
			buf.append(key).append("=");
			buf.append(getProperty(key)).append("; ");
		}
		buf.append("]");
		return buf.toString();
	}   // toString

	/*************************************************************************/

	public static final String METASFRESH_HOME = "METASFRESH_HOME";
	
	/** System Property Value of ADEMPIERE_HOME. Users should rather set the {@value #METASFRESH_HOME} value */
	@Deprecated
	public static final String ADEMPIERE_HOME = "ADEMPIERE_HOME";
	
	/**
	 * Internal run mode marker. Note that the inital setting is equivalent to the old initialization of <code>s_client = true</code>
	 *
	 * @task 04585
	 */
	private static RunMode s_runMode = RunMode.SWING_CLIENT;

	/** IsClient Internal marker */
	private static boolean s_loaded = false;
	/** Show license dialog for first time **/
	private static boolean s_license_dialog = true;

	/**
	 * Are we running within the swing client?
	 *
	 * @return <code>true</code> if running in the swing client.
	 */
	public static boolean isClient()
	{
		return getRunMode() == RunMode.SWING_CLIENT;
	}   // isClient

	/**
	 * Set Client Mode
	 *
	 * @param client if true, then global run mode is set to {@code SWING_CLIENT}, else to {@code BACKEND}
	 *
	 */
	public static void setClient(boolean client)
	{
		setRunMode(client ? RunMode.SWING_CLIENT : RunMode.BACKEND);
	}   // setClient

	/**
	 *
	 * @return global run mode
	 * @task 04585
	 */
	public static RunMode getRunMode()
	{
		Check.errorIf(s_runMode == null, "RunMode has not yet been set! It is supposed to be set from Adempiere.startup()");
		return s_runMode;
	}

	/**
	 * Set global run mode.
	 *
	 * @param mode
	 * @task 04585
	 */
	public static void setRunMode(RunMode mode)
	{
		s_runMode = mode;
	}

	/**
	 * Set show license dialog for new setup
	 *
	 * @param b
	 */
	public static void setShowLicenseDialog(boolean b)
	{
		s_license_dialog = b;
	}

	/**
	 * Is show license dialog for new setup
	 *
	 * @return boolean
	 */
	public static boolean isShowLicenseDialog()
	{
		return s_license_dialog;
	}

	/**
	 * Are the properties loaded?
	 *
	 * @return true if properties loaded.
	 */
	public static boolean isLoaded()
	{
		return s_loaded;
	}   // isLoaded

	/**
	 * Get Metasfresh home directory.
	 *
	 * @return Metasfresh home directory; never returns <code>null</code>
	 */
	public static String getMetasfreshHome()
	{
		// Try getting the METASFRESH_HOME from JRE defined properties (i.e. via -DADEMPIERE_HOME=....)
		String env = System.getProperty(METASFRESH_HOME);
		if (!Check.isEmpty(env, true))
		{
			return env.trim();
		}
		
		// Try getting the METASFRESH_HOME from environment
		env = System.getenv(METASFRESH_HOME);
		if (!Check.isEmpty(env, true))
		{
			return env.trim();
		}

		// Legacy: Try getting the ADEMPIERE_HOME from environment
		env = System.getenv(ADEMPIERE_HOME);
		if (!Check.isEmpty(env, true))
		{
			return env.trim();
		}

		// Legacy: Try getting the ADEMPIERE_HOME from JRE defined properties (i.e. via -DADEMPIERE_HOME=....)
		env = System.getProperty(ADEMPIERE_HOME);
		if (!Check.isEmpty(env, true))
		{
			return env.trim();
		}

		// If running in client mode, use "USERHOME/.metasfresh" folder.
		if (isClient())
		{
			final String userHomeDir = System.getProperty("user.home");
			env = userHomeDir + File.separator + ".metasfresh";
			return env;
		}

		// Fallback
		if (env == null)
		{
			env = File.separator + "metasfresh";
		}
		
		return env;
	}

	/**
	 * Set Metashfresh home directory
	 *
	 * @param metasfreshHome METASFRESH_HOME
	 */
	public static void setMetasfreshHome(String metasfreshHome)
	{
		if (metasfreshHome != null && metasfreshHome.length() > 0)
		{
			System.setProperty(METASFRESH_HOME, metasfreshHome.trim());
		}
	}

	/**************************************************************************
	 * Get Window Dimension
	 *
	 * @param AD_Window_ID window no
	 * @return dimension or null
	 */
	public static Dimension getWindowDimension(int AD_Window_ID)
	{
		String key = "WindowDim" + AD_Window_ID;
		String value = (String)s_prop.get(key);
		if (value == null || value.length() == 0)
			return null;
		int index = value.indexOf('|');
		if (index == -1)
			return null;
		try
		{
			String w = value.substring(0, index);
			String h = value.substring(index + 1);
			return new Dimension(Integer.parseInt(w), Integer.parseInt(h));
		}
		catch (Exception e)
		{
		}
		return null;
	}	// getWindowDimension

	/**
	 * Set Window Dimension
	 *
	 * @param AD_Window_ID window
	 * @param windowDimension dimension - null to remove
	 */
	public static void setWindowDimension(int AD_Window_ID, Dimension windowDimension)
	{
		String key = "WindowDim" + AD_Window_ID;
		if (windowDimension != null)
		{
			String value = windowDimension.width + "|" + windowDimension.height;
			s_prop.put(key, value);
		}
		else
			s_prop.remove(key);
	}	// setWindowDimension

	/**
	 * Get Window Location
	 *
	 * @param AD_Window_ID window id
	 * @return location or null
	 */
	public static Point getWindowLocation(int AD_Window_ID)
	{
		String key = "WindowLoc" + AD_Window_ID;
		String value = (String)s_prop.get(key);
		if (value == null || value.length() == 0)
			return null;
		int index = value.indexOf('|');
		if (index == -1)
			return null;
		try
		{
			String x = value.substring(0, index);
			String y = value.substring(index + 1);
			return new Point(Integer.parseInt(x), Integer.parseInt(y));
		}
		catch (Exception e)
		{
		}
		return null;
	}	// getWindowLocation

	/**
	 * Set Window Location
	 *
	 * @param AD_Window_ID window
	 * @param windowLocation location - null to remove
	 */
	public static void setWindowLocation(int AD_Window_ID, Point windowLocation)
	{
		String key = "WindowLoc" + AD_Window_ID;
		if (windowLocation != null)
		{
			String value = windowLocation.x + "|" + windowLocation.y;
			s_prop.put(key, value);
		}
		else
			s_prop.remove(key);
	}	// setWindowLocation

	/**
	 * Get Divider Location
	 *
	 * @return location or 400
	 */
	public static int getDividerLocation()
	{
		final int defaultValue = 400;
		String key = "Divider";
		String value = (String)s_prop.get(key);
		if (value == null || value.length() == 0)
			return defaultValue;

		int valueInt = defaultValue;
		try
		{
			valueInt = Integer.parseInt(value);
		}
		catch (Exception e)
		{
		}

		return valueInt <= 0 ? defaultValue : valueInt;
	}	// getDividerLocation

	/**
	 * Set Divider Location
	 *
	 * @param dividerLocation location
	 */
	public static void setDividerLocation(int dividerLocation)
	{
		String key = "Divider";
		String value = String.valueOf(dividerLocation);
		s_prop.put(key, value);
	}	// setDividerLocation

	/**
	 * Get Available Encoding Charsets
	 *
	 * @return array of available encoding charsets
	 * @since 3.1.4
	 */
	public static Charset[] getAvailableCharsets()
	{
		Collection<Charset> col = Charset.availableCharsets().values();
		Charset[] arr = new Charset[col.size()];
		col.toArray(arr);
		return arr;
	}

	/**
	 * Get current charset
	 *
	 * @return current charset
	 * @since 3.1.4
	 */
	public static Charset getCharset()
	{
		String charsetName = getProperty(P_CHARSET);
		if (charsetName == null || charsetName.length() == 0)
			return Charset.defaultCharset();
		try
		{
			return Charset.forName(charsetName);
		}
		catch (Exception e)
		{
		}
		return Charset.defaultCharset();
	}

	public static String getPropertyFileName()
	{
		return s_propertyFileName;
	}

	public static class IsNotSwingClient implements Condition
	{
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata)
		{
			return Ini.getRunMode() != RunMode.SWING_CLIENT;
		}

	}
}	// Ini

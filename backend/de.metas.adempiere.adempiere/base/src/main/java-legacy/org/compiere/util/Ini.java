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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.FileUtil;
import lombok.Setter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.AdempiereLookAndFeel;
import org.adempiere.plaf.MetasFreshTheme;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.ModelValidationEngine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Load & Save INI Settings from property file
 * Initiated in Adempiere.startup
 * Settings activated in ALogin.getIni
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 1658127 ] Select charset encoding on import
 * <li>FR [ 2406123 ] Ini.saveProperties fails if target directory does not exist
 * @version $Id$
 */
public final class Ini
{
	public enum IfMissingMetasfreshProperties
	{
		SHOW_DIALOG, IGNORE
	}

	@Setter
	private static IfMissingMetasfreshProperties ifMissingMetasfreshProperties = IfMissingMetasfreshProperties.SHOW_DIALOG;

	/**
	 * Make sure this happens first, because otherwise, during startup, {@link #getMetasfreshHome()} might be called before this field is initialized
	 * Especially, this supplier needs to be initialized has to be before {@link #DEFAULT_LANGUAGE}.
	 */
	private static final ExtendedMemorizingSupplier<String> METASFRESH_HOME_Supplier = ExtendedMemorizingSupplier.of(Ini::findMetasfreshHome);

	/**
	 * Property file name
	 */
	public static final String METASFRESH_PROPERTY_FILE = "metasfresh.properties";

	/**
	 * Apps User ID
	 */
	public static final String P_UID = "ApplicationUserID";
	private static final String DEFAULT_UID = "";
	/**
	 * Apps Password
	 */
	public static final String P_PWD = "ApplicationPassword";
	private static final String DEFAULT_PWD = "";
	/**
	 * Store Password
	 */
	public static final String P_STORE_PWD = "StorePassword";
	private static final boolean DEFAULT_STORE_PWD = true;
	/**
	 * Trace Level
	 */
	public static final String P_TRACELEVEL = "TraceLevel";
	private static final String DEFAULT_TRACELEVEL = "WARNING";
	/**
	 * Trace to File (Y/N).
	 */
	public static final String P_TRACEFILE_ENABLED = "TraceFile";
	private static final boolean DEFAULT_TRACEFILE_ENABLED = false;

	/**
	 * Language
	 */
	public static final String P_LANGUAGE = "Language";
	private static final String DEFAULT_LANGUAGE = Language.getName(System.getProperty("user.language") + "_" + System.getProperty("user.country"));
	/**
	 * Ini File Name
	 */
	public static final String P_INI = "FileNameINI";
	private static final String DEFAULT_INI = "";
	/**
	 * Connection Details
	 */
	public static final String P_CONNECTION = "Connection";
	private static final String DEFAULT_CONNECTION = "";

	/**
	 * Look & Feel
	 */
	public static final String P_UI_LOOK = "UILookFeel";

	private static final String DEFAULT_UI_LOOK = AdempiereLookAndFeel.NAME;
	/**
	 * UI Theme
	 */

	private static final String DEFAULT_UI_THEME = MetasFreshTheme.NAME;
	/**
	 * UI Theme
	 */
	public static final String P_UI_THEME = "UITheme";

	/**
	 * Auto Save
	 */
	public static final String P_A_COMMIT = "AutoCommit";
	private static final boolean DEFAULT_A_COMMIT = true;
	/**
	 * Auto Login
	 */
	public static final String P_A_LOGIN = "AutoLogin";
	private static final boolean DEFAULT_A_LOGIN = false;
	/**
	 * Auto New Record
	 */
	public static final String P_A_NEW = "AutoNew";
	private static final boolean DEFAULT_A_NEW = false;
	/**
	 * Dictionary Maintenance
	 */
	public static final String P_ADEMPIERESYS = "AdempiereSys";    // Save system records
	private static final boolean DEFAULT_ADEMPIERESYS = false;
	/**
	 * Log Migration Script
	 */
	/**
	 * Show Acct Tabs
	 */
	public static final String P_SHOW_ACCT = "ShowAcct";
	private static final boolean DEFAULT_SHOW_ACCT = true;
	/**
	 * Show Advanced Tabs
	 */
	public static final String P_SHOW_ADVANCED = "ShowAdvanced";
	private static final boolean DEFAULT_SHOW_ADVANCED = true;
	/**
	 * Show Translation Tabs
	 */
	public static final String P_SHOW_TRL = "ShowTrl";
	private static final boolean DEFAULT_SHOW_TRL = false;
	/**
	 * Cache Windows
	 */
	public static final String P_CACHE_WINDOW = "CacheWindow";
	private static final boolean DEFAULT_CACHE_WINDOW = true;
	/**
	 * Temp Directory
	 */
	public static final String P_TEMP_DIR = "TempDir";
	private static final String DEFAULT_TEMP_DIR = "";
	/**
	 * Role
	 */
	public static final String P_ROLE = "Role";
	private static final String DEFAULT_ROLE = "";
	/**
	 * Client Name
	 */
	public static final String P_CLIENT = "Client";
	private static final String DEFAULT_CLIENT = "";
	/**
	 * Org Name
	 */
	public static final String P_ORG = "Organization";
	private static final String DEFAULT_ORG = "";

	/**
	 * Printer Name
	 */
	public static final String P_PRINTER = "Printer";
	private static final String DEFAULT_PRINTER = "";
	// metas: adding support for an additional label printer
	public static final String P_LABELPRINTER = "LabelPrinter";
	private static final String DEFAULT_LABELPRINTER = "";

	// metas: adding support for a report server
	public static final String P_REPORT_PREFIX = "ReportServer";
	private static final String DEFAULT_REPORT_SERVER = "";

	/**
	 * Warehouse Name
	 */
	public static final String P_WAREHOUSE = "Warehouse";
	private static final String DEFAULT_WAREHOUSE = "";
	/**
	 * Current Date
	 */
	public static final String P_TODAY = "CDate";
	private static final Timestamp DEFAULT_TODAY = new Timestamp(System.currentTimeMillis());
	/**
	 * Print Preview
	 */
	public static final String P_PRINTPREVIEW = "PrintPreview";
	private static final boolean DEFAULT_PRINTPREVIEW = false;
	/**
	 * Validate connection on startup
	 */
	public static final String P_VALIDATE_CONNECTION_ON_STARTUP = "ValidateConnectionOnStartup";
	private static final boolean DEFAULT_VALIDATE_CONNECTION_ON_STARTUP = false;

	/**
	 * Single instance per window id
	 **/
	public static final String P_SINGLE_INSTANCE_PER_WINDOW = "SingleInstancePerWindow";
	public static final boolean DEFAULT_SINGLE_INSTANCE_PER_WINDOW = false;

	/**
	 * Open new windows as maximized
	 **/
	public static final String P_OPEN_WINDOW_MAXIMIZED = "OpenWindowMaximized";

	// task 09355: Open everything as maximized from the beginning
	public static final boolean DEFAULT_OPEN_WINDOW_MAXIMIZED = true;
	//
	private static final String P_WARNING = "Warning";
	private static final String DEFAULT_WARNING = "Do_not_change_any_of_the_data_as_they_will_have_undocumented_side_effects.";
	private static final String P_WARNING_de = "WarningD";
	private static final String DEFAULT_WARNING_de = "Einstellungen_nicht_aendern,_da_diese_undokumentierte_Nebenwirkungen_haben.";

	/**
	 * Charset
	 */
	public static final String P_CHARSET = "Charset";
	/**
	 * Charser Default Value
	 */
	private static final String DEFAULT_CHARSET = Charset.defaultCharset().name();

	/**
	 * Load tab fields meta data using background thread
	 **/
	public static final String P_LOAD_TAB_META_DATA_BG = "LoadTabMetaDataBackground";

	public static final String DEFAULT_LOAD_TAB_META_DATA_BG = "N";

	/**
	 * Ini PropertyName to default value
	 */
	private static final ImmutableMap<String, String> PROPERTY_DEFAULTS = ImmutableMap.<String, String>builder()
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
	private static final Set<String> PROPERTIES_CLIENT = ImmutableSet.<String>builder()
			.add(P_ADEMPIERESYS)
			.build();

	/**
	 * List of property names which shall be skipped from encryption
	 */
	private static final Set<String> PROPERTIES_SKIP_ENCRYPTION = ImmutableSet.<String>builder()
			.add(P_WARNING)
			.add(P_WARNING_de)
			.build();

	/**
	 * Container for Properties
	 */
	private static Properties s_prop = new Properties();

	private static String s_propertyFileName = null;

	/**
	 * Logger
	 */
	private static final transient Logger log = LogManager.getLogger(Ini.class.getName());

	private Ini()
	{
		super();
	}

	/**
	 * Save INI parameters to disk
	 */
	public static void saveProperties()
	{
		if (Ini.isSwingClient() && DB.isConnected())
		{
			// Call ModelValidators beforeSaveProperties
			ModelValidationEngine.get().beforeSaveProperties();
		}

		final String fileName = getFileName();
		final File file = new File(fileName).getAbsoluteFile();
		final FileOutputStream fos;
		try
		{
			file.getParentFile().mkdirs(); // Create all dirs if not exist - teo_sarca FR [ 2406123 ]
			fos = new FileOutputStream(file);
			s_prop.store(fos, "metasfresh.properties");
			fos.flush();
			fos.close();
		}
		catch (final Exception e)
		{
			log.error("Cannot save Properties to {}", file, e);
			return;
		}
		log.info("Saved properties to {}", file);

	}    // save

	/**
	 * Load INI parameters from disk
	 */
	public static void loadProperties()
	{
		loadProperties(getFileName());
	}    // loadProperties

	/**
	 * Load INI parameters from filename.
	 * Logger is on default level (INFO)
	 *
	 * @param filename to load
	 * @return true if first time
	 */
	public static boolean loadProperties(final String filename)
	{
		boolean firstTime = false;
		s_prop = new Properties();

		// gh #658: only show the dialog if the file doesn't exist yet.
		// for any other fail, we shall throw an exception because there can be
		// plenty of reasons and we can't just rewrite the file and go on each time.
		final File propertiesFile = new File(filename).getAbsoluteFile();
		boolean propertiesFileExists = propertiesFile.exists();
		if (!propertiesFileExists && IfMissingMetasfreshProperties.SHOW_DIALOG.equals(ifMissingMetasfreshProperties))
		{
			log.info("File {} does not exist. Allow the user to set initial properties", propertiesFile);
			firstTime = true;
			if (isShowLicenseDialog())
			{
				if (!IniDialog.accept())
				{
					System.exit(-1);
				}
			}
			saveProperties();
			propertiesFileExists = true;
		}

		if (propertiesFileExists)
		{
			try (final FileInputStream fis = new FileInputStream(filename))
			{
				s_prop.load(fis);
			}
			catch (final Exception e)
			{
				log.warn(filename + " - " + e.toString());

				// gh #658: for f***'s sake, don't just log a warning. When running in tomcat, this logged warning will go nowhere
				throw AdempiereException.wrapIfNeeded(e);
			}

			checkProperties();

			s_loaded = true;
			log.info("Loaded {} properties from {}", s_prop.size(), propertiesFile);
			s_propertyFileName = propertiesFile.toString();
		}

		return firstTime;
	}    // loadProperties

	private static void checkProperties()
	{
		// Check/set properties defaults
		for (final Map.Entry<String, String> propertyName2defaultValue : PROPERTY_DEFAULTS.entrySet())
		{
			final String defaultValue = propertyName2defaultValue.getValue();
			if (Check.isNotBlank(defaultValue))
			{
				final String propertyName = propertyName2defaultValue.getKey();
				checkProperty(propertyName, defaultValue);
			}
		}

		//
		String tempDir = FileUtil.getTempDir();
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
		final String fileName = getFileName();
		final File file = new File(fileName);
		if (file.exists())
		{
			try
			{
				if (!file.delete())
					file.deleteOnExit();
				s_prop = new Properties();
				log.info("Deleted properties file: {}", fileName);
			}
			catch (final Exception e)
			{
				log.warn("Cannot delete properties file: {}", fileName, e);
			}
		}
	}    // deleteProperties

	/**
	 * Load property and set to default, if not existing
	 *
	 * @param key          Key
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
		else if (!isSwingClient())
		{
			result = s_prop.getProperty(key, SecureInterface.CLEARVALUE_START + defaultValue + SecureInterface.CLEARVALUE_END);
		}
		else
		{
			result = s_prop.getProperty(key, SecureEngine.encrypt(defaultValue));
		}
		s_prop.setProperty(key, result);
		return result;
	}    // checkProperty

	/**
	 * Return File Name of INI file
	 *
	 * <pre>
	 *  Examples:
	 *     C:\WinNT\Profiles\jjanke\Adempiere.properties
	 *      D:\Adempiere\Adempiere.properties
	 *      Adempiere.properties
	 * </pre>
	 * <p>
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
	}    // getFileName

	/**
	 * Set Property
	 *
	 * @param key   can by any key, not only the ones declared in this class. In order to persist it, call {@link #saveProperties()}.
	 *              <p>
	 *              <b>Important:</b> if the given key is included in {@link #PROPERTIES_CLIENT}, but we currently aren't in the client, then the property is set in {@link Env} instead.
	 * @param value Value
	 */
	public static void setProperty(final String key, final String value)
	{
		// If it's a client property and we are in server mode, update the context instead of Ini file
		if (!Ini.isSwingClient() && PROPERTIES_CLIENT.contains(key))
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
		else if (!isSwingClient())
		{
			s_prop.setProperty(key, SecureInterface.CLEARVALUE_START + value + SecureInterface.CLEARVALUE_END);
		}
		else
		{
			if (value == null)
				s_prop.setProperty(key, "");
			else
			{
				final String eValue = SecureEngine.encrypt(value);
				if (eValue == null)
					s_prop.setProperty(key, "");
				else
					s_prop.setProperty(key, eValue);
			}
		}
	}

	/**
	 * Set Property from a boolean value. Delegates to {@link #setProperty(String, String)} with the boolean's string representation.
	 */
	public static void setProperty(final String key, final boolean value)
	{
		setProperty(key, DisplayType.toBooleanString(value));
	}   // setProperty

	/**
	 * Set Property from a int value. Delegates to {@link #setProperty(String, String)} with the int's string representation.
	 */
	public static void setProperty(final String key, final int value)
	{
		setProperty(key, String.valueOf(value));
	}   // setProperty

	public static String getProperty(final String key)
	{
		if (key == null)
		{
			return "";
		}

		// If it's a client property and we are in server mode, get value from context instead of Ini file
		if (!Ini.isSwingClient() && PROPERTIES_CLIENT.contains(key))
		{
			final String value = Env.getCtx().getProperty(key);
			return value == null ? "" : value;
		}

		final String retStr = s_prop.getProperty(key, "");
		if (retStr == null || retStr.isEmpty())
		{
			return "";
		}

		//
		final String value = SecureEngine.decrypt(retStr);
		// log.trace(key + "=" + value);
		if (value == null)
		{
			return "";
		}

		return value;
	}    // getProperty

	/**
	 * Get Property as Boolean
	 */
	public static boolean isPropertyBool(final String key)
	{
		final String valueStr = getProperty(key);
		if (Check.isBlank(valueStr))
		{
			return false;
		}

		return CoalesceUtil.coalesceSuppliersNotNull(
				() -> DisplayType.toBoolean(valueStr, null),
				() -> DisplayType.toBoolean(SecureInterface.CLEARVALUE_START + valueStr + SecureInterface.CLEARVALUE_END, null),
				() -> false
		);
	}    // getProperty

	/**
	 * Cache Windows
	 *
	 * @return true if windows are cached
	 */
	public static boolean isCacheWindow()
	{
		return isPropertyBool(P_CACHE_WINDOW);
	}    // isCacheWindow

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
		final StringBuffer buf = new StringBuffer("Ini[");
		final Enumeration<Object> e = s_prop.keys();
		while (e.hasMoreElements())
		{
			final String key = (String)e.nextElement();
			buf.append(key).append("=");
			buf.append(getProperty(key)).append("; ");
		}
		buf.append("]");
		return buf.toString();
	}   // toString

	/*************************************************************************/

	public static final String METASFRESH_HOME = "METASFRESH_HOME";

	/**
	 * System Property Value of ADEMPIERE_HOME. Users should rather set the {@value #METASFRESH_HOME} value
	 */
	@Deprecated
	public static final String ADEMPIERE_HOME = "ADEMPIERE_HOME";

	/**
	 * Internal run mode marker. Note that the inital setting is equivalent to the old initialization of <code>s_client = true</code>
	 * <p>
	 * task 04585
	 */
	private static RunMode s_runMode = RunMode.SWING_CLIENT;

	/**
	 * IsClient Internal marker
	 */
	private static boolean s_loaded = false;
	/**
	 * Show license dialog for first time
	 **/
	private static boolean s_license_dialog = true;

	/**
	 * Are we running within the swing client?
	 *
	 * @return <code>true</code> if running in the swing client.
	 */
	public static boolean isSwingClient()
	{
		return getRunMode() == RunMode.SWING_CLIENT;
	}   // isClient

	/**
	 * Set Client Mode
	 *
	 * @param client if true, then global run mode is set to {@code SWING_CLIENT}, else to {@code BACKEND}
	 */
	public static void setClient(final boolean client)
	{
		setRunMode(client ? RunMode.SWING_CLIENT : RunMode.BACKEND);
	}   // setClient

	/**
	 * @return global run mode
	 * task 04585
	 */
	public static RunMode getRunMode()
	{
		Check.errorIf(s_runMode == null, "RunMode has not yet been set! It is supposed to be set from Adempiere.startup()");
		return s_runMode;
	}

	/**
	 * Set global run mode.
	 * <p>
	 * task 04585
	 */
	public static void setRunMode(final RunMode mode)
	{
		s_runMode = mode;
	}

	/**
	 * Set show license dialog for new setup
	 */
	public static void setShowLicenseDialog(final boolean b)
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
		return METASFRESH_HOME_Supplier.get();
	}

	/**
	 * Finds {@link #METASFRESH_HOME}. No logging inside this method, because it's run before the logging is actually up an running.
	 *
	 * @return Metasfresh home directory; never returns <code>null</code>
	 */
	private static String findMetasfreshHome()
	{
		// Try getting the METASFRESH_HOME from JRE defined properties (i.e. via -DMETASFRESH_HOME=....)
		{
			final String env = System.getProperty(METASFRESH_HOME);
			if (!Check.isEmpty(env, true))
			{
				final String metasfreshHome = env.trim();
				// log.info("Found METASFRESH_HOME: {} (from system properties variable {})", metasfreshHome, METASFRESH_HOME);
				return metasfreshHome;
			}
		}

		// Try getting the METASFRESH_HOME from environment
		{
			final String env = System.getenv(METASFRESH_HOME);
			if (!Check.isEmpty(env, true))
			{
				// log.info("Found METASFRESH_HOME: {} (from environment variable {})", metasfreshHome, METASFRESH_HOME);
				return env.trim();
			}
		}

		// Legacy: Try getting the ADEMPIERE_HOME from JRE defined properties (i.e. via -DADEMPIERE_HOME=....)
		{
			final String env = System.getProperty(ADEMPIERE_HOME);
			if (!Check.isEmpty(env, true))
			{
				// log.info("Found METASFRESH_HOME: {} (from system property variable {})", metasfreshHome, ADEMPIERE_HOME);
				// log.warn("Property variable {} is deprecated. Please use {} instead.", ADEMPIERE_HOME, METASFRESH_HOME);
				return env.trim();
			}
		}

		// Legacy: Try getting the ADEMPIERE_HOME from environment
		{
			final String env = System.getenv(ADEMPIERE_HOME);
			if (!Check.isEmpty(env, true))
			{
				// log.info("Found METASFRESH_HOME: {} (from environment variable {})", metasfreshHome, ADEMPIERE_HOME);
				// log.warn("System environment variable {} is deprecated. Please use {} instead.", ADEMPIERE_HOME, METASFRESH_HOME);
				return env.trim();
			}
		}

		// If running in client mode, use "USERHOME/.metasfresh" folder.
		final String userHomeDir = System.getProperty("user.home");
		if (!Check.isEmpty(userHomeDir) && new File(userHomeDir).exists())
		{
			// log.info("Found METASFRESH_HOME: {} (fallback, based on user.home)", metasfreshHome);
			return userHomeDir + File.separator + ".metasfresh";
		}

		// Fallback
		{
			// log.info("Found METASFRESH_HOME: {} (fallback)", metasfreshHome);
			return File.separator + "metasfresh";
		}
	}

	/**************************************************************************
	 * Get Window Dimension
	 *
	 * @param AD_Window_ID window no
	 * @return dimension or null
	 */
	@Nullable
	public static Dimension getWindowDimension(final int AD_Window_ID)
	{
		final String key = "WindowDim" + AD_Window_ID;
		final String value = (String)s_prop.get(key);
		if (value == null || value.length() == 0)
			return null;
		final int index = value.indexOf('|');
		if (index == -1)
			return null;
		try
		{
			final String w = value.substring(0, index);
			final String h = value.substring(index + 1);
			return new Dimension(Integer.parseInt(w), Integer.parseInt(h));
		}
		catch (final Exception e)
		{
		}
		return null;
	}    // getWindowDimension

	/**
	 * Set Window Dimension
	 *
	 * @param windowDimension dimension - null to remove
	 */
	public static void setWindowDimension(final int AD_Window_ID, final Dimension windowDimension)
	{
		final String key = "WindowDim" + AD_Window_ID;
		if (windowDimension != null)
		{
			final String value = windowDimension.width + "|" + windowDimension.height;
			s_prop.put(key, value);
		}
		else
			s_prop.remove(key);
	}    // setWindowDimension

	/**
	 * Get Window Location
	 *
	 * @param AD_Window_ID window id
	 * @return location or null
	 */
	@Nullable
	public static Point getWindowLocation(final int AD_Window_ID)
	{
		final String key = "WindowLoc" + AD_Window_ID;
		final String value = (String)s_prop.get(key);
		if (value == null || value.length() == 0)
		{
			return null;
		}
		final int index = value.indexOf('|');
		if (index == -1)
		{
			return null;
		}
		try
		{
			final String x = value.substring(0, index);
			final String y = value.substring(index + 1);
			return new Point(Integer.parseInt(x), Integer.parseInt(y));
		}
		catch (final Exception ignored)
		{
		}
		return null;
	}    // getWindowLocation

	/**
	 * Set Window Location
	 *
	 * @param windowLocation location - null to remove
	 */
	public static void setWindowLocation(final int AD_Window_ID, final Point windowLocation)
	{
		final String key = "WindowLoc" + AD_Window_ID;
		if (windowLocation != null)
		{
			final String value = windowLocation.x + "|" + windowLocation.y;
			s_prop.put(key, value);
		}
		else
			s_prop.remove(key);
	}    // setWindowLocation

	/**
	 * Get Divider Location
	 *
	 * @return location or 400
	 */
	public static int getDividerLocation()
	{
		final int defaultValue = 400;
		final String key = "Divider";
		final String value = (String)s_prop.get(key);
		if (value == null || value.length() == 0)
			return defaultValue;

		int valueInt = defaultValue;
		try
		{
			valueInt = Integer.parseInt(value);
		}
		catch (final Exception ignored)
		{
		}

		return valueInt <= 0 ? defaultValue : valueInt;
	}    // getDividerLocation

	/**
	 * Set Divider Location
	 *
	 * @param dividerLocation location
	 */
	public static void setDividerLocation(final int dividerLocation)
	{
		final String key = "Divider";
		final String value = String.valueOf(dividerLocation);
		s_prop.put(key, value);
	}    // setDividerLocation

	/**
	 * Get Available Encoding Charsets
	 *
	 * @return array of available encoding charsets
	 * @since 3.1.4
	 */
	public static Charset[] getAvailableCharsets()
	{
		final Collection<Charset> col = Charset.availableCharsets().values();
		final Charset[] arr = new Charset[col.size()];
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
		final String charsetName = getProperty(P_CHARSET);
		if (Check.isBlank(charsetName))
		{
			return Charset.defaultCharset();
		}
		try
		{
			return Charset.forName(charsetName);
		}
		catch (final Exception ignored)
		{
		}
		return Charset.defaultCharset();
	}

	public static String getPropertyFileName()
	{
		return s_propertyFileName;
	}

	// public static class IsNotSwingClient implements Condition
	// {
	// @Override
	// public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata)
	// {
	// return Ini.getRunMode() != RunMode.SWING_CLIENT;
	// }
	// }
}    // Ini

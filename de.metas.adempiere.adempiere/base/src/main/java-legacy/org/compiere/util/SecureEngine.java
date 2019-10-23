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

import java.util.Properties;

import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Security Engine
 * 
 * @author Jorg Janke
 * @version $Id: SecureEngine.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class SecureEngine
{
	/**
	 * Initialize Security
	 * 
	 * @param ctx context with METASFRESH_SECURE class name
	 */
	public static void init(final Properties ctx)
	{
		if (s_engine == null)
		{
			String className = ctx.getProperty(SecureInterface.METASFRESH_SECURE);
			s_engine = new SecureEngine(className);
		}
	}	// init

	/**
	 * Initialize/Test Security
	 * 
	 * @param className class name
	 */
	public static void init(String className)
	{
		if (s_engine == null)
			s_engine = new SecureEngine(className);
		else if (className != null && !className.equals(getClassName()))
		{
			String msg = "Requested Security class = " + className
					+ " is not the same as the active class = " + getClassName()
					+ "\nMake sure to set the security class in the start script";
			log.error(msg);
			System.err.println(msg);
			System.exit(10);
		}
	}	// init

	/**
	 * Get Class Name
	 * 
	 * @return class name
	 */
	public static String getClassName()
	{
		if (s_engine == null)
			return null;
		return s_engine.implementation.getClass().getName();
	}	// getClassName

	/**
	 * Convert String to Digest.
	 * JavaScript version see - http://pajhome.org.uk/crypt/md5/index.html
	 *
	 * @param value message
	 * @return HexString of message (length = 32 characters)
	 */
	public static String getDigest(String value)
	{
		if (s_engine == null)
			init(System.getProperties());
		return s_engine.implementation.getDigest(value);
	}	// getDigest

	/**
	 * Encryption.
	 * The methods must recognize clear text values
	 * 
	 * @param value clear value
	 * @return encrypted String
	 */
	public static String encrypt(String value)
	{
		if (value == null || value.length() == 0)
			return value;
		if (s_engine == null)
			init(System.getProperties());
		//
		boolean inQuotes = value.startsWith("'") && value.endsWith("'");
		if (inQuotes)
			value = value.substring(1, value.length() - 1);
		//
		String retValue = s_engine.implementation.encrypt(value);
		if (inQuotes)
			return "'" + retValue + "'";
		return retValue;
	}	// encrypt

	/**
	 * Decryption.
	 * The methods must recognize clear text values
	 * 
	 * @param value encrypted value
	 * @return decrypted String
	 */
	public static String decrypt(String value)
	{
		if (value == null)
			return null;
		if (s_engine == null)
			init(System.getProperties());
		boolean inQuotes = value.startsWith("'") && value.endsWith("'");
		if (inQuotes)
			value = value.substring(1, value.length() - 1);
		String retValue = null;
		if (value.startsWith(SecureInterface.CLEARVALUE_START) && value.endsWith(SecureInterface.CLEARVALUE_END))
			retValue = value.substring(SecureInterface.CLEARVALUE_START.length(), value.length() - SecureInterface.CLEARVALUE_END.length());
		else
			retValue = s_engine.implementation.decrypt(value);
		if (inQuotes)
			return "'" + retValue + "'";
		return retValue;
	}	// decrypt

	/**
	 * Encryption.
	 * The methods must recognize clear values
	 * 
	 * @param value clear value
	 * @return encrypted String
	 */
	public static Object encrypt(Object value)
	{
		if (value instanceof String)
			return encrypt((String)value);
		return value;
	}	// encrypt

	/**
	 * Decryption.
	 * The methods must recognize clear values
	 * 
	 * @param value encrypted value
	 * @return decrypted String
	 */
	public static Object decrypt(Object value)
	{
		if (value instanceof String)
			return decrypt((String)value);
		return value;
	}	// decrypt

	/** Test String */
	private static final String TEST = "This is a 0123456789 .,; -= Test!";
	/** Secure Engine */
	private static SecureEngine s_engine = null;

	/** The real Engine */
	private SecureInterface implementation = null;
	/** Logger */
	private static final Logger log = LogManager.getLogger(SecureEngine.class);

	/**
	 * SecureEngine constructor
	 * 
	 * @param className class name if null defaults to org.compiere.util.Secure
	 */
	private SecureEngine(String className)
	{
		String realClass = className;
		if (realClass == null || realClass.length() == 0)
			realClass = SecureInterface.METASFRESH_SECURE_DEFAULT;
		Exception cause = null;
		try
		{
			final Class<?> clazz = Class.forName(realClass);
			implementation = (SecureInterface)clazz.newInstance();
		}
		catch (Exception e)
		{
			cause = e;
		}
		if (implementation == null)
		{
			String msg = "Could not initialize: " + realClass + " - " + cause.toString()
					+ "\nCheck start script parameter: " + SecureInterface.METASFRESH_SECURE;
			log.error(msg);
			System.err.println(msg);
			System.exit(10);
		}
		// See if it works
		String testE = implementation.encrypt(TEST);
		String testC = implementation.decrypt(testE);
		if (!testC.equals(TEST))
			throw new IllegalStateException(realClass
					+ ": " + TEST
					+ "->" + testE + "->" + testC);
		log.info("{} initialized - {}", realClass, implementation);
	}	// SecureEngine
}

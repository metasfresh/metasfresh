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


/**
 *	Apple Mac Java VM Configuration
 *	
 *  @author Jorg Janke
 *  @version $Id: ConfigVMMac.java,v 1.3 2006/07/30 00:57:42 jjanke Exp $
 */
public class ConfigVMMac extends Config
{
	/**
	 * 	ConfigVMMac
	 * 	@param data configuration
	 */
	public ConfigVMMac (ConfigurationData data)
	{
		super (data);
	}	//	ConfigVMMac
	
	/**
	 * 	Init
	 */
	public void init()
	{
		//	Java Home, e.g. D:\j2sdk1.4.1\jre
		String javaHome = System.getProperty("java.home");
		log.fine(javaHome);
		if (javaHome.endsWith("jre"))
			javaHome = javaHome.substring(0, javaHome.length()-4);
		p_data.setJavaHome(javaHome);
	}	//	init
	
	/**
	 * 	Test
	 *	@return error message or null of OK
	 */
	public String test()
	{
		//	Java Home
		File javaHome = new File (p_data.getJavaHome());
		boolean pass = javaHome.exists();
		String error = "Not found: Java Home";
		if (getPanel() != null)
			signalOK(getPanel().okJavaHome, "ErrorJavaHome",
				pass, true, error);
		if (!pass)
			return error;
		/**	Different VM structure
		File tools = new File (p_data.getJavaHome() 
			+ File.separator + "lib" + File.separator + "tools.jar");
		pass = tools.exists();
		error = "Not found: Java SDK = " + tools;
		signalOK(getPanel().okJavaHome, "ErrorJavaHome",
			pass, true, error);
		if (!pass)
			return error;
		**/

		//
		log.info("OK: JavaHome=" + javaHome.getAbsolutePath());
		setProperty(ConfigurationData.JAVA_HOME, javaHome.getAbsolutePath());
		System.setProperty(ConfigurationData.JAVA_HOME, javaHome.getAbsolutePath());
		
		//	Java Version
		final String VERSION = "1.5.0";
		final String VERSION2 = "1.6.0";
		pass = false;
		String jh = javaHome.getAbsolutePath();
		if (jh.indexOf(VERSION) != -1)	//	file name has version = assuming OK
			pass = true;
		if (!pass && jh.indexOf(VERSION2) != -1)	//
			pass = true;
		String thisJH = System.getProperty("java.home");
		if (thisJH.indexOf(jh) != -1)	//	we are running the version currently
		{
			String thisJV = System.getProperty("java.version");
			pass = thisJV.indexOf(VERSION) != -1;
			if (!pass && thisJV.indexOf(VERSION2) != -1)
				pass = true;
			if (pass)
			  log.info("OK: Version=" + thisJV);
		}
		error = "Wrong Java Version: Should be " + VERSION2;
		if (getPanel() != null)
			signalOK(getPanel().okJavaHome, "ErrorJavaHome",
					pass, true, error);
		if (!pass)
			return error;
		//
		setProperty(ConfigurationData.JAVA_TYPE, p_data.getJavaType());
		
		return null;
	}	//	test

}

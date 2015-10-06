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
package org.compiere.util;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *	Log4J Utilities.
 *	Not intended to be called directly
 *	
 *  @author Jorg Janke
 *  @version $Id: CLogMgtLog4J.java,v 1.2 2006/07/30 00:54:36 jjanke Exp $
 */
final class CLogMgtLog4J
{
	private CLogMgtLog4J()
	{
		super();
	}
	
	/**
	 * 	Initialize Logging
	 * 	@param isClient client
	 */
	protected static void initialize(final boolean isClient)
	{
		if (isClient)
		{
			LogManager.resetConfiguration();
			Logger rootLogger = LogManager.getRootLogger();
			rootLogger.setLevel(s_currentLevelLog4J);
		}
	}	//	initialize

	/** Current Lo4J Level	*/
	private static Level	s_currentLevelLog4J = Level.WARN;

	
	/**
	 * 	Enable/Disable Log4J logging
	 *	@param enableLogging false assumed
	 */
	public static void enable (boolean enableLogging)
	{
		Logger rootLogger = LogManager.getRootLogger(); 
		if (enableLogging)
			rootLogger.setLevel(s_currentLevelLog4J);
		else
		{
			Level level = rootLogger.getLevel(); 
			rootLogger.setLevel(Level.OFF);
			s_currentLevelLog4J = level;
		}
	}	//	enableLog4J

	
}	//	ClientLogMgtLog4J

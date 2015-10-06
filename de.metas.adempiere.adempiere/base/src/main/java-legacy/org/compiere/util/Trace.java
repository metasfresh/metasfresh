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

import java.util.ArrayList;
import java.util.logging.Logger;

import org.compiere.db.CConnectionDialog;

/**
 *  Trace Information
 *
 *  @author     Jorg Janke
 *  @version    $Id: Trace.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class Trace
{
	/**	Logger	*/
	private static CLogger	log	= CLogger.getCLogger (CConnectionDialog.class);
	
	/**
	 * Get Caller Array
	 *
	 * @param caller Optional Throwable/Exception
	 * @param maxNestLevel maximum call nesting level - 0 is all
	 * @return Array of class.method(file:line)
	 */
	public static String[] getCallerClasses (Throwable caller, int maxNestLevel)
	{
		int nestLevel = maxNestLevel;
		if (nestLevel < 1)
			nestLevel = 99;
		//
		ArrayList<String> list = new ArrayList<String>();
		Throwable t = caller;
		if (t == null)
			t = new Throwable();

		StackTraceElement[] elements = t.getStackTrace();
		for (int i = 0; i < elements.length && list.size() <= maxNestLevel; i++)
		{
			String className = elements[i].getClassName();
		//	System.out.println(list.size() + ": " + className);
			if (!(className.startsWith("org.compiere.util.Trace")
				|| className.startsWith("java.lang.Throwable")))
				list.add(className);
		}

		String[] retValue = new String[list.size()];
		list.toArray(retValue);
		return retValue;
	}   //  getCallerClasses

	/**
	 *  Get Caller with nest Level
	 *  @param nestLevel Nesting Level - 0=calling method, 1=previous, ..
	 *  @return class name and line info of nesting level or "" if not exist
	 */
	public static String getCallerClass (int nestLevel)
	{
		String[] array = getCallerClasses (null, nestLevel);
		if (array.length < nestLevel)
			return "";
		return array[nestLevel];
	}   //  getCallerClass

	/**
	 * 	Is the caller Called From the class mentioned
	 *	@param className calling class
	 *	@return the caller was called from className
	 */
	public static boolean isCalledFrom (String className)
	{
		if (className == null || className.length() == 0)
			return false;
		return getCallerClass(1).indexOf(className) != -1;
	}	//	isCalledFrom

	/**
	 *  Print Stack Trace Info (raw) adempiereOnly - first9only
	 */
	public static void printStack()
	{
		printStack (true, true);
	}	//	printStack
	
	/**
	 *  Print Stack Trace Info (raw)
	 */
	public static void printStack (boolean adempiereOnly, boolean first9only)
	{
		Throwable t = new Throwable();
	//	t.printStackTrace();
		int counter = 0;
		StackTraceElement[] elements = t.getStackTrace();
		for (int i = 1; i < elements.length; i++)
		{
			if (elements[i].getClassName().indexOf("util.Trace") != -1)
				continue;
			if (!adempiereOnly
				|| (adempiereOnly && elements[i].getClassName().startsWith("org.compiere"))
				)
			{
				log.fine(i + ": " + elements[i]);
				if (first9only && ++counter > 8)
					break;
			}
		}	
	}   //  printStack
	
}   //  Trace

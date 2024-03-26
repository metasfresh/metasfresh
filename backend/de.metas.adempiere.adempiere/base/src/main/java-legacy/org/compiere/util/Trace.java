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

import lombok.NonNull;
import org.adempiere.ad.dao.impl.QueryStatisticsLogger;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.db.util.AbstractResultSetBlindIterator;
import org.adempiere.sql.impl.StatementsFactory;
import org.adempiere.util.proxy.impl.JavaAssistInterceptor;

import java.util.ArrayList;

/**
 *  Trace Information
 *
 *  @author     Jorg Janke
 *  @version    $Id: Trace.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class Trace
{

	/**
	 * Get Caller Array
	 *
	 * @param caller Optional Throwable/Exception
	 * @param maxNestLevel maximum call nesting level - 0 is all
	 * @return Array of class.method(file:line)
	 */
	private static String[] getCallerClasses (Throwable caller, int maxNestLevel)
	{
		int nestLevel = maxNestLevel;
		if (nestLevel < 1)
		{
			nestLevel = 99;
		}
		//
		ArrayList<String> list = new ArrayList<>();
		Throwable t = caller;
		if (t == null)
		{
			t = new Throwable();
		}

		StackTraceElement[] elements = t.getStackTrace();
		for (int i = 0; i < elements.length && list.size() <= maxNestLevel; i++)
		{
			String className = elements[i].getClassName();
		//	System.out.println(list.size() + ": " + className);
			if (!(className.startsWith("org.compiere.util.Trace")
				|| className.startsWith("java.lang.Throwable")))
			{
				list.add(className);
			}
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
		{
			return "";
		}
		return array[nestLevel];
	}   //  getCallerClass

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
			{
				continue;
			}
			if (!adempiereOnly
				|| (adempiereOnly && elements[i].getClassName().startsWith("org.compiere"))
				)
			{
				if (first9only && ++counter > 8)
				{
					break;
				}
			}
		}	
	}   //  printStack

	public static final String toOneLineStackTraceString()
	{
		return toOneLineStackTraceString(new Exception().getStackTrace());
	}

	public static final String toOneLineStackTraceString(@NonNull final Throwable throwable)
	{
		return toOneLineStackTraceString(throwable.getStackTrace());
	}

	public static final String toOneLineStackTraceString(@NonNull final StackTraceElement[] stacktrace)
	{
		final StringBuilder stackTraceStr = new StringBuilder();
		int ste_Considered = 0;
		boolean ste_lastSkipped = false;
		for (final StackTraceElement ste : stacktrace)
		{
			if (ste_Considered >= 100)
			{
				stackTraceStr.append("...");
				break;
			}

			String classname = ste.getClassName();
			final String methodName = ste.getMethodName();

			// Skip some irrelevant stack trace elements
			if (classname.startsWith("java.") || classname.startsWith("javax.") || classname.startsWith("sun.")
					|| classname.startsWith("com.google.")
					|| classname.startsWith("org.springframework.")
					|| classname.startsWith("org.apache.")
					|| classname.startsWith(QueryStatisticsLogger.class.getPackage().getName())
					|| classname.startsWith(Trace.class.getName())
					//
					|| classname.startsWith(StatementsFactory.class.getPackage().getName())
					|| classname.startsWith(AbstractResultSetBlindIterator.class.getPackage().getName())
					|| classname.startsWith(ITrxManager.class.getPackage().getName())
					|| classname.startsWith(org.adempiere.ad.persistence.TableModelLoader.class.getPackage().getName())
					//
					|| classname.startsWith(JavaAssistInterceptor.class.getPackage().getName())
					|| classname.indexOf("_$$_jvstdca_") >= 0 // javassist proxy
					|| methodName.startsWith("access$")
			//
			)
			{
				ste_lastSkipped = true;
				continue;
			}

			final int lastDot = classname.lastIndexOf(".");
			if (lastDot >= 0)
			{
				classname = classname.substring(lastDot + 1);
			}

			if (ste_lastSkipped || stackTraceStr.length() > 0)
			{
				stackTraceStr.append(ste_lastSkipped ? " <~~~ " : " <- ");
			}

			stackTraceStr.append(classname).append(".").append(methodName);

			final int lineNumber = ste.getLineNumber();
			if (lineNumber > 0)
			{
				stackTraceStr.append(":").append(lineNumber);
			}

			ste_lastSkipped = false;
			ste_Considered++;
		}

		return stackTraceStr.toString();
	}

}   //  Trace

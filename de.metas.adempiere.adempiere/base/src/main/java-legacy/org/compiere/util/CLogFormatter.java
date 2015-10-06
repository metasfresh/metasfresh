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

import java.awt.Toolkit;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.adempiere.util.StringUtils;

/**
 * Adempiere Log Formatter
 *
 * @author Jorg Janke
 * @version $Id: CLogFormatter.java,v 1.2 2006/07/30 00:54:36 jjanke Exp $
 */
final class CLogFormatter extends Formatter
{
	public static final transient CLogFormatter instance = new CLogFormatter();

	/**
	 * Get Formatter
	 *
	 * @return singleton
	 */
	public static final CLogFormatter get()
	{
		return instance;
	}	// get

	/** New Line */
	public static String NL = System.getProperty("line.separator");

	/**************************************************************************
	 * CLogFormatter
	 */
	private CLogFormatter()
	{
		super();
	}	// CLogFormatter

	/** Short Format */
	private boolean m_shortFormat = false;

	/**
	 * Format
	 *
	 * @param record log record
	 * @return formatted string
	 */
	@Override
	public String format(final LogRecord record)
	{
		final StringBuilder sb = new StringBuilder();

		long ms = record.getMillis();
		Timestamp ts = null;
		if (ms == 0)
			ts = new Timestamp(System.currentTimeMillis());
		else
			ts = new Timestamp(ms);
		String tsStr = "";
		try
		{
			tsStr = ts.toString() + "00";
		}
		catch (Exception e)
		{
			System.err.println("CLogFormatter.format: Millis="
					+ ms + " - " + e.toString() + " - " + record.getMessage());
			// 1 5 1 5 2 5
			tsStr = "_________________________";
		}

		/** Time/Error */
		sb.append(tsStr.substring(11, 23));

		if (record.getLevel() == Level.SEVERE)
		{	// 12:12:12.123
			sb.append(" ===========> ");
			if (Ini.isClient())
				Toolkit.getDefaultToolkit().beep();
		}
		else if (record.getLevel() == Level.WARNING)
		{	// 12:12:12.123
			sb.append(" -----------> ");
		}
		else
		{
			int spaces = 11;
			if (record.getLevel() == Level.INFO)
				spaces = 1;
			else if (record.getLevel() == Level.CONFIG)
				spaces = 3;
			else if (record.getLevel() == Level.FINE)
				spaces = 5;
			else if (record.getLevel() == Level.FINER)
				spaces = 7;
			else if (record.getLevel() == Level.FINEST)
				spaces = 9;
			sb.append("                          ".substring(0, spaces));
		}

		/** Class.method **/
		if (!m_shortFormat)
			sb.append(getClassMethod(record)).append(": ");

		/** Message **/
		// metas: use proper message formater
		sb.append(StringUtils.formatMessage(record.getMessage(), record.getParameters()));
		// sb.append(record.getMessage());
		// /** Parameters **/
		// String parameters = getParameters(record);
		// if (parameters.length() > 0)
		// sb.append(" (").append(parameters).append(")");

		/**
		 * Level **
		 * sb.append(" ")
		 * .append(record.getLevel().getLocalizedName());
		 * /** Thread
		 **/
		if (record.getThreadID() != 10)
			sb.append(" [").append(record.getThreadID()).append("]");

		//
		sb.append(NL);
		if (record.getThrown() != null)
			sb.append(getExceptionTrace(record)).append(NL);
		return sb.toString();
	}	// format

	/**
	 * Return the header string for a set of formatted records.
	 * 
	 * @param h The target handler.
	 * @return header string
	 */
	@Override
	public String getHead(final Handler h)
	{
		String className = h.getClass().getName();
		int index = className.lastIndexOf('.');
		if (index != -1)
			className = className.substring(index + 1);
		
		final StringBuilder sb = new StringBuilder()
				.append("*** ")
				.append(new Date())
				.append(" Adempiere Log (").append(className)
				.append(") ***").append(NL);
		return sb.toString();
	}	// getHead

	/**
	 * Return the tail string for a set of formatted records.
	 * 
	 * @param h The target handler.
	 * @return tail string
	 */
	@Override
	public String getTail(final Handler h)
	{
		String className = h.getClass().getName();
		int index = className.lastIndexOf('.');
		if (index != -1)
			className = className.substring(index + 1);
		
		final StringBuilder sb = new StringBuilder()
				.append(NL)
				.append("*** ")
				.append(new Date())
				.append(" Adempiere Log (").append(className)
				.append(") ***").append(NL);
		return sb.toString();
	}	// getTail

	/**
	 * Set Format
	 *
	 * @param shortFormat format
	 */
	public void setShortFormat(boolean shortFormat)
	{
		m_shortFormat = shortFormat;
	}	// setFormat

	/**************************************************************************
	 * Get Class Method from Log Record
	 *
	 * @param record record
	 * @return class.method
	 */
	public static String getClassMethod(LogRecord record)
	{
		StringBuilder sb = new StringBuilder();
		String className = record.getLoggerName();
		if (className == null
				|| className.indexOf("default") != -1	// anonymous logger
				|| className.indexOf("global") != -1)	// global logger
			className = record.getSourceClassName();
		if (className != null)
		{
			int index = className.lastIndexOf('.');
			if (index != -1)
				sb.append(className.substring(index + 1));
			else
				sb.append(className);
		}
		else
			sb.append(record.getLoggerName());
		if (record.getSourceMethodName() != null)
			sb.append(".").append(record.getSourceMethodName());
		String retValue = sb.toString();
		if (retValue.equals("Trace.printStack"))
			return "";
		return retValue;
	}	// getClassMethod

	/**
	 * Get Log Parameters
	 *
	 * @param record log record
	 * @return parameters empty string or parameters
	 */
	public static String getParameters(final LogRecord record)
	{
		final Object[] parameters = record.getParameters();
		if (parameters == null || parameters.length <= 0)
		{
			return "";
		}
		
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parameters.length; i++)
		{
			if (i > 0)
				sb.append(", ");
			sb.append(parameters[i]);
		}
		return sb.toString();
	}	// getParameters

	/**
	 * Get Log Exception
	 *
	 * @param record log record
	 * @return null if exists or string
	 */
	public static String getExceptionTrace(LogRecord record)
	{
		Throwable thrown = record.getThrown();
		if (thrown == null)
			return null;

		StringBuilder sb = new StringBuilder();
		try
		{
			fillExceptionTrace(sb, "", thrown);
		}
		catch (Exception ex)
		{
		}
		return sb.toString();
	}	// getException

	/**
	 * Fill Exception Trace
	 *
	 * @param sb string buffer
	 * @param hdr header
	 * @param thrown thrown
	 */
	private static void fillExceptionTrace(StringBuilder sb, String hdr, Throwable thrown)
	{
		// boolean firstError = hdr.length() == 0;
		sb.append(hdr)
				.append(thrown.toString());
		if (thrown instanceof SQLException)
		{
			final SQLException ex = (SQLException)thrown;
			sb.append("; State=").append(ex.getSQLState())
					.append("; ErrorCode=").append(ex.getErrorCode());
		}
		sb.append(NL);

		final String stackTraceStr = Util.dumpStackTraceToString(thrown);
		sb.append(stackTraceStr).append(NL);

		// //
		// StackTraceElement[] trace = thrown.getStackTrace();
		// boolean adempiereTrace = false;
		// int adempiereTraceNo = 0;
		// for (int i=0; i < trace.length; i++)
		// {
		// adempiereTrace = trace[i].getClassName().startsWith("org.compiere.");
		// if (thrown instanceof ServerException // RMI
		// || adempiereTrace)
		// {
		// if (adempiereTrace)
		// sb.append("\tat ").append(trace[i]).append(NL);
		// }
		// else if (i > 20
		// || (i > 10 && adempiereTraceNo > 8))
		// break;
		// else
		// sb.append("\tat ").append(trace[i]).append(NL);
		// if (adempiereTrace)
		// adempiereTraceNo++;
		// }
		// //
		// Throwable cause = thrown.getCause();
		// if (cause != null)
		// fillExceptionTrace(sb, "caused by: ", cause);
	}	// fillExceptionTrace

}	// CLogFormatter

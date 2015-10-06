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

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * 	Adempiere Console Logger
 *	@author Jorg Janke
 *	@version $Id: CLogConsole.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 */
final class CLogConsole extends Handler
{
	/**
	 * 	Get Console Handler
	 *	@param create create if not exists
	 *	@return console hander or null
	 */
	public static final CLogConsole get (final boolean create)
	{
		if (s_console == null && create)
		{
			synchronized (CLogConsole.class)
			{
				if (s_console == null)
				{
					s_console = new CLogConsole();
				}
			}
		}
		return s_console;
	}	//	get
	
	private static volatile CLogConsole s_console = null;
	
	/**
	 *	Constructor
	 */
	private CLogConsole ()
	{
		super();
		initialize();
	}	// CLogConsole

	/**	Printed header			*/
    private boolean 	m_doneHeader = false;
    /** Normal Writer			*/
    private PrintWriter	m_writerOut = null;
    /** Error Writer			*/
    private PrintWriter	m_writerErr = null;

    /**
     * 	Initialize
     */
    private void initialize()
    {
    //	System.out.println("CLogConsole.initialize");
		//	Set Writers
		String encoding = getEncoding();
		if (encoding != null)
		{
		    try 
		    {
		    	m_writerOut = new PrintWriter(new OutputStreamWriter(System.out, encoding));
		    	m_writerErr = new PrintWriter(new OutputStreamWriter(System.err, encoding));
		    }
		    catch (UnsupportedEncodingException ex) 
		    {
				reportError ("Opening encoded Writers", ex, ErrorManager.OPEN_FAILURE);
		    }
		}
		if (m_writerOut == null)
			m_writerOut = new PrintWriter(System.out);
		if (m_writerErr == null)
			m_writerErr = new PrintWriter(System.err);
		
    	//	Formatting
		setFormatter(CLogFormatter.get());
		//	Default Level
		setLevel(Level.INFO);
		//	Filter
		setFilter(CLogFilter.get());
		//
    }	//	initialize
	
    /**
     * 	Set Encoding
     *	@param encoding encoding
     *	@throws SecurityException
     *	@throws java.io.UnsupportedEncodingException
     */
    @Override
	public void setEncoding (String encoding)
		throws SecurityException, java.io.UnsupportedEncodingException
	{
		super.setEncoding (encoding);
		// Replace the current writer with a writer for the new encoding.
		flush ();
		initialize();
	}	//	setEncoding

    
	/**
	 * 	Set Level
	 *	@see java.util.logging.Handler#setLevel(java.util.logging.Level)
	 *	@param newLevel new Level
	 *	@throws java.lang.SecurityException
	 */
	@Override
	public synchronized void setLevel (Level newLevel)
		throws SecurityException
	{
		if (newLevel == null)
			return;
		super.setLevel (newLevel);
		boolean enableJDBC = newLevel == Level.FINEST;
		if (enableJDBC)
			DriverManager.setLogWriter(m_writerOut);	//	lists Statements
		else
			DriverManager.setLogWriter(null);
	}	//	setLevel
    
	/**
	 *	Publish
	 *	@see java.util.logging.Handler#publish(java.util.logging.LogRecord)
	 *	@param record log record
	 */
	@Override
	public void publish (LogRecord record)
	{
		if (m_writerOut == null)
		{
			return;
		}

		// NOTE (metas-tsa): We are not validating again if we shall log given record because loggers already validated.
		// Also we want to cover following case: the global log level is WARNING, but for one logger we set the log level to FINE (e.g. via JConsole).
		// NOTE2: "isLoggable" is also checking the filters (i.e. org.compiere.util.CLogFilter) which is filtering out java/swing logs but we don't want this (needed this when for example debugging "java.awt.focus.KeyboardFocusManager")
//		if (!isLoggable (record))
//		{
//			return;
//		}
		
		//	Format
		String msg = null;
		try
		{
			msg = getFormatter().format (record);
		}
		catch (Exception ex)
		{
			reportError ("formatting", ex, ErrorManager.FORMAT_FAILURE);
			return;
		}
		//	Output
		try
		{
			if (!m_doneHeader)
			{
				m_writerOut.write (getFormatter().getHead (this));
				m_doneHeader = true;
			}
			if (record.getLevel() == Level.SEVERE 
				|| record.getLevel() == Level.WARNING)
			{
				flush();
				m_writerErr.write (msg);
				flush();
			}
			else
			{
				m_writerOut.write (msg);
				m_writerOut.flush();
			}
		}
		catch (Exception ex)
		{
			reportError ("writing", ex, ErrorManager.WRITE_FAILURE);
		}
	}	// publish

	/**
	 *	Flush
	 *	@see java.util.logging.Handler#flush()
	 */
	@Override
	public void flush ()
	{
		try
		{
			if (m_writerOut != null)
				m_writerOut.flush();
		}
		catch (Exception ex)
		{
			reportError ("flush out", ex, ErrorManager.FLUSH_FAILURE);
		}
		try
		{
			if (m_writerErr != null)
				m_writerErr.flush();
		}
		catch (Exception ex)
		{
			reportError ("flush err", ex, ErrorManager.FLUSH_FAILURE);
		}
	}	// flush

	/**
	 * Close
	 * 
	 * @see java.util.logging.Handler#close()
	 * @throws SecurityException
	 */
	@Override
	public void close () throws SecurityException
	{
		if (m_writerOut == null)
			return;

		//	Write Tail
		try
		{
			if (!m_doneHeader)
				m_writerOut.write (getFormatter().getHead(this));
			//
			m_writerOut.write (getFormatter().getTail(this));
		}
		catch (Exception ex)
		{
			reportError ("tail", ex, ErrorManager.WRITE_FAILURE);
		}
		//
		flush();
		//	Close
		try
		{
			m_writerOut.close();
		}
		catch (Exception ex)
		{
			reportError ("close out", ex, ErrorManager.CLOSE_FAILURE);
		}
		m_writerOut = null;
		try
		{
			m_writerErr.close();
		}
		catch (Exception ex)
		{
			reportError ("close err", ex, ErrorManager.CLOSE_FAILURE);
		}
		m_writerErr = null;
	}	// close
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("CLogConsole[");
		sb.append("Level=").append(getLevel())
			.append ("]");
		return sb.toString ();
	} //	toString

} // CLogConsole

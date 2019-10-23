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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 *  Execute OS Task
 *
 *  @author     Jorg Janke
 *  @version    $Id: Task.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class Task extends Thread
{
	/**
	 *  Create Process with cmd
	 *  @param cmd o/s command
	 */
	public Task (String cmd)
	{
		m_cmd = cmd;
	}   //  Task

	private String          m_cmd;
	private Process         m_child = null;

	private StringBuffer    m_out = new StringBuffer();
	private StringBuffer    m_err = new StringBuffer();
	private Integer    		m_exitValue = 0; //metas: c.ghita@metas.ro 
	private String 			m_errorLog = ""; //metas: c.ghita@metas.ro

	/** The Output Stream of process        */
	private InputStream     m_outStream;
	/** The Error Output Stream of process  */
	private InputStream     m_errStream;
	/** The Input Stream of process         */
	private OutputStream    m_inStream;

	/**	Logger			*/
	private static Logger log = LogManager.getLogger(Task.class);
	
	/** Read Out                            */
	private Thread          m_outReader = new Thread()
	{
		public void run()
		{
			log.debug("outReader");
			try
			{
				int c;
				while ((c = m_outStream.read()) != -1 && !isInterrupted())
				{
			//		System.out.print((char)c);
					m_out.append((char)c);
				}
				m_outStream.close();
			}
			catch (IOException ioe)
			{
				log.error("outReader", ioe);
			}
			log.debug("outReader - done");
		}   //  run
	};   //  m_outReader

	/** Read Out                            */
	private Thread          m_errReader = new Thread()
	{
		public void run()
		{
			log.debug("errReader");
			try
			{
				int c;
				while ((c = m_errStream.read()) != -1 && !isInterrupted())
				{
			//		System.err.print((char)c);
					m_err.append((char)c);
				}
				m_errStream.close();
			}
			catch (IOException ioe)
			{
				log.error("errReader", ioe);
			}
			log.debug("errReader - done");
		}   //  run
	};   //  m_errReader


	/**
	 *  Execute it
	 */
	public void run()
	{
		log.info(m_cmd);
		try
		{
			m_child = Runtime.getRuntime().exec(m_cmd);
			//
			m_outStream = m_child.getInputStream();
			m_errStream = m_child.getErrorStream();
			m_inStream = m_child.getOutputStream();
	
			//
			if (checkInterrupted())
				return;
			m_outReader.start();
			m_errReader.start();
			//
			try
			{
				if (checkInterrupted())
					return;
				m_errReader.join();
				if (checkInterrupted())
					return;
				m_outReader.join();
				if (checkInterrupted())
					return;
				m_exitValue = m_child.waitFor(); //metas: c.ghita@metas.ro
			}
			catch (InterruptedException ie)
			{
				m_errorLog = m_errorLog+""+ie.getMessage(); //metas: c.ghita@metas.ro 
				m_exitValue = -1;//metas: c.ghita@metas.ro
				log.info("(ie) - " + ie);
			}
			//  ExitValue
			try
			{
				if (m_child != null)
				{	
					log.debug("run - ExitValue=" + m_exitValue); //metas: c.ghita@metas.ro : start
				}
			}
			catch (Exception e) 
			{
				m_exitValue = -1; //metas: c.ghita@metas.ro 
			}
			log.info("done");
		}
		catch (IOException ioe)
		{
			m_errorLog = m_errorLog+""+ioe.getMessage(); //metas: c.ghita@metas.ro 
			m_exitValue = -1; //metas: c.ghita@metas.ro 
			log.error("(ioe)", ioe);
		}
	}   //  run

	/**
	 *  Check if interrupted
	 *  @return true if interrupted
	 */
	private boolean checkInterrupted()
	{
		if (isInterrupted())
		{
			log.info("interrupted");
			//  interrupt child processes
			if (m_child != null)
				m_child.destroy();
			m_child = null;
			if (m_outReader != null && m_outReader.isAlive())
				m_outReader.interrupt();
			m_outReader = null;
			if (m_errReader != null && m_errReader.isAlive())
				m_errReader.interrupt();
			m_errReader = null;
			//  close Streams
			if (m_inStream != null)
				try {   m_inStream.close();     } catch (Exception e) {}
			m_inStream = null;
			if (m_outStream != null)
				try {   m_outStream.close();    } catch (Exception e) {}
			m_outStream = null;
			if (m_errStream != null)
				try {   m_errStream.close();    } catch (Exception e) {}
			m_errStream = null;
			//
			return true;
		}
		return false;
	}   //  checkInterrupted

	/**
	 *  Get Out Info
	 *  @return StringBuffer
	 */
	public StringBuffer getOut()
	{
		return m_out;
	}   //  getOut

	/**
	 *  Get Err Info
	 *  @return StringBuffer
	 */
	public StringBuffer getErr()
	{
		return m_err;
	}   //  getErr

	/**
	 *  Get The process input stream - i.e. we output to it
	 *  @return OutputStream
	 */
	public OutputStream getInStream()
	{
		return m_inStream;
	}   //  getInStream

	/*
	 * metas: c.ghita@metas.ro
	 * if the value is 0, means that was a normal termination
	 * get exit value
	 */
	public Integer getExitValue()
	{
		return m_exitValue;
	}
	
	/*
	 * metas: c.ghita@metas.ro
	 * get error log
	 */
	public String getErrorLog()
	{
		return m_errorLog;
	}
}   //  Task

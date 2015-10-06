/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.compiere.util.CLogger;

/**
 *  Execute OS Task
 *
 *  @author Low Heng Sin
 */
public class OSTask extends Thread
{
	/**
	 *  Create Process with cmd
	 *  @param cmd o/s command
	 */
	public OSTask (String cmd)
	{
		m_cmd = cmd;
	}   //  Task

	private String          m_cmd;
	private Process         m_child = null;

	private StringBuffer    m_out = new StringBuffer();
	private StringBuffer    m_err = new StringBuffer();

	/** The Output Stream of process        */
	private InputStream     m_outStream;
	/** The Error Output Stream of process  */
	private InputStream     m_errStream;
	/** The Input Stream of process         */
	private OutputStream    m_inStream;

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(OSTask.class);

	/** Read Out                            */
	private Thread          m_outReader = new Thread()
	{
		public void run()
		{
			log.fine("outReader");
			try
			{
				int c;
				while ((c = m_outStream.read()) != -1 && !isInterrupted())
				{
					m_out.append((char)c);
				}
				m_outStream.close();
			}
			catch (IOException ioe)
			{
				log.log(Level.SEVERE, "outReader", ioe);
			}
			log.fine("outReader - done");
		}   //  run
	};   //  m_outReader

	/** Read Out                            */
	private Thread          m_errReader = new Thread()
	{
		public void run()
		{
			log.fine("errReader");
			try
			{
				int c;
				while ((c = m_errStream.read()) != -1 && !isInterrupted())
				{
					m_err.append((char)c);
				}
				m_errStream.close();
			}
			catch (IOException ioe)
			{
				log.log(Level.SEVERE, "errReader", ioe);
			}
			log.fine("errReader - done");
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

			Integer exitValue = null;
			while(exitValue == null)
			{
				//
				try
				{
					Thread.sleep(500);
					if (checkInterrupted())
						return;
					int i = m_child.exitValue();
					exitValue = new Integer(i);
				}
				catch (Exception ie)
				{
					log.log(Level.INFO, "(ie) - " + ie);
				}
				//  ExitValue
				log.config("done");
			}
		}
		catch (IOException ioe)
		{
			log.log(Level.SEVERE, "(ioe)", ioe);
			m_err.append(ioe.getLocalizedMessage());
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
			log.config("interrupted");
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

}   //  Task

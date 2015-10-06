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
package org.compiere.process;

import java.util.logging.Level;

import org.compiere.model.PO;

/**
 *	Adempiere Service.
 *	Instanciates and Controls the Adempiere Server, 
 *	which actually does the work in separate thread
 *	
 *  @author Jorg Janke
 *  @version $Id: AdempiereService.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 */
public class AdempiereService extends StateEngine
{
	/**
	 * 	Adempiere Service
	 * 	@param processor Processor instance
	 *	@param serverClass server class
	 */
	public AdempiereService (PO processor, Class<?> serverClass)
	{
		super ();
		m_processor = processor;
		m_serverClass = serverClass;
	}	//	AdempiereServer

	/**	Adempiere Server(s)			*/
	private AdempiereServer	m_server = null;
	/**	Adempiere Server	Class		*/
	private Class<?>			m_serverClass = null;
	/** Adempiere Server Processor Instance	*/ 
	private PO				m_processor = null;
	

	/**
	 * 	Get Compier Server
	 *	@return Adempiere Server
	 */
	public AdempiereServer getCompierServer()
	{
		getState();
		return m_server;
	}	//	getAdempiereServer
	
	/**
	 * 	Get/Check State
	 *	@return state
	 */
	public String getState ()
	{
		if (isRunning())
		{
			if (m_server == null || !m_server.isAlive())
				terminate();
		}
		return super.getState ();
	}	//	getState

	/**
	 * 	Start: not started -> running
	 *	@return true if set to running
	 */
	public boolean start()
	{
		if (!super.start())
			return false;
		
		boolean ok = false;
		try
		{
			m_server = (AdempiereServer)m_serverClass.newInstance();
			m_server.setProcessor (m_processor);
			m_server.start();
			ok = true;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "start", e);
			ok = false;
		}
		if (!ok)
			return abort();
		log.info("start - " + ok);
		getState();
		return ok;
	}	//	start

	/**
	 * 	Resume: suspended -> running
	 *	@return true if set to sunning
	 */
	public boolean resume()
	{
		if (!super.resume())
			return false;
		
		boolean ok = false;
		try
		{
			m_server = (AdempiereServer)m_serverClass.newInstance();
			m_server.setProcessor (m_processor);
			m_server.start();
			ok = true;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "resume", e);
			ok = false;
		}
		if (!ok)
			return abort();
		log.info("resume - " + ok);
		getState();
		return ok;
	}	//	resume
	
	
	/**
	 * 	Complete: running -> completed
	 *	@return true if set to completed
	 */
	public boolean complete()
	{
		if (!super.complete())
			return false;
		
		boolean ok = false;
		if (m_server != null && m_server.isAlive())
		{
			try
			{
				m_server.interrupt();
				m_server.join();
				ok = true;
			}
			catch (Exception e)
			{
				return abort();
			}
		}
		log.info("complete - " + ok);
		return ok;
	}	//	complete

	/**
	 * 	Suspend: running -> suspended
	 *	@return true if suspended
	 */
	public boolean suspend()
	{
		if (!super.suspend())
			return false;
		
		boolean ok = false;
		if (m_server != null && m_server.isAlive())
		{
			try
			{
				m_server.interrupt();
				m_server.join();
				ok = true;
			}
			catch (Exception e)
			{
				return abort();
			}
		}
		log.info("suspend - " + ok);
		return ok;
	}	//	suspend
	

	/**
	 * 	Abort: open -> aborted
	 *	@return true if set to aborted
	 */
	public boolean abort()	//	raises CannotStop, NotRunning
	{
		if (super.abort())
		{
			if (m_server != null && m_server.isAlive())
			{
				try
				{
					m_server.interrupt();
				}
				catch (Exception e)
				{
				}
			}
			log.info("abort - done");
			return true;
		}
		return false;
	}	//	abort
	
	/**
	 * 	Terminate (System Error): open -> terminated
	 *	@return true if set to terminated
	 */
	public boolean terminate()
	{
		if (super.terminate())
		{
			if (m_server != null && m_server.isAlive())
			{
				try
				{
					m_server.interrupt();
				}
				catch (Exception e)
				{
				}
			}
			log.info("terminate - done");
			return true;
		}
		return false;
	}	//	terminate
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("AdempiereService[");
		sb.append(getStateInfo())
			.append(" - ").append(m_server);
		sb.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	AdempiereService

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

import java.util.logging.Level;

import javax.swing.SwingUtilities;

import org.compiere.process.ProcessInfo;

/**
 *  ASync Worker for starting methods in classes implementing ASyncProcess
 *
 *  @author     Jorg Janke
 *  @version    $Id: ASyncWorker.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class ASyncWorker extends Thread
{
	/**
	 *  Execute method Synchronously
	 *  @param parent parent
	 *  @param pi process info
	 *  @return result
	 */
	public static ProcessInfo executeSync (ASyncProcess parent, ProcessInfo pi)
	{
		ASyncWorker worker = new ASyncWorker (parent, pi);
		worker.start();
		try
		{
			worker.join();
		}
		catch (InterruptedException e)
		{
			log.log(Level.SEVERE, "executeSync", e);
		}
		return worker.getResult();
	}   //  executeSync

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ASyncWorker.class);
	
	/**
	 *  Constructor
	 *  @param parent Parent Process
	 *  @param pi process info
	 */
	public ASyncWorker (ASyncProcess parent, ProcessInfo pi)
	{
		m_parent = parent;
		m_pi = pi;
	}   //  ASuncWorker

	private ProcessInfo     m_pi;
	private ASyncProcess    m_parent;

	/**
	 *  The Worker Method
	 */
	public void run()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				m_parent.lockUI(m_pi);
			}
		});

		//
		m_parent.executeASync(m_pi);
		//

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				m_parent.unlockUI (m_pi);
			}
		});
	}   //  run

	/**
	 *  Get Result (usually not used as result is returned via unlockUI
	 *  @return result
	 */
	public ProcessInfo getResult()
	{
		return m_pi;
	}   //  getResult

}   //  ASyncWorker

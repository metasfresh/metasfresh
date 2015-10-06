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
package org.adempiere.webui.apps;

import org.adempiere.ad.process.IProcessParameter;
import org.adempiere.webui.component.Window;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MPInstance;
import org.compiere.process.ProcessInfo;
import org.compiere.util.ASyncProcess;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

/**
 * Ported from org.compiere.apps.ProcessCtl
 * @author hengsin
 *
 */
public class WProcessCtl {
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WProcessCtl.class);
	
	/**
	 *	Process Control
	 *  <code>
	 *	- Get Instance ID
	 *	- Get Parameters
	 *	- execute (lock - start process - unlock)
	 *  </code>
	 *  Creates a ProcessCtl instance, which calls
	 *  lockUI and unlockUI if parent is a ASyncProcess
	 *  <br>
	 *
	 *  @param aProcess ASyncProcess & Container
	 *  @param WindowNo window no
	 *  @param pi ProcessInfo process info
	 *  @param trx Transaction
	 */
	public static void process (ASyncProcess aProcess, int WindowNo, ProcessInfo pi, Trx trx)
	{
		log.fine("WindowNo=" + WindowNo + " - " + pi);

		MPInstance instance = null; 
		try 
		{ 
			instance = new MPInstance(Env.getCtx(), pi); 
		} 
		catch (Exception e) 
		{ 
			pi.setThrowable(e); // 03152
			pi.setSummary (e.getLocalizedMessage()); 
			pi.setError (true); 
			log.warning(pi.toString()); 
		} 
		catch (Error e) 
		{ 
			pi.setThrowable(e); // 03152
			pi.setSummary (e.getLocalizedMessage()); 
			pi.setError (true); 
			log.warning(pi.toString()); 
		}
		if (!instance.save())
		{
			pi.setSummary (Msg.getMsg(Env.getCtx(), "ProcessNoInstance"));
			pi.setError (true);
		}
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());

		//	Get Parameters (Dialog)
		ProcessModalDialog para = new ProcessModalDialog(aProcess, WindowNo, pi, false);
		if (para.isValid())
		{
			para.setWidth("500px");
			para.setVisible(true);
			para.setPosition("center");
			para.setAttribute(Window.MODE_KEY, Window.MODE_MODAL);
			AEnv.showWindow(para);
		}
	}	//	execute
	
	/**
	 *	Async Process - Do it all.
	 *  <code>
	 *	- Get Instance ID
	 *	- Get Parameters
	 *	- execute (lock - start process - unlock)
	 *  </code>
	 *  Creates a ProcessCtl instance, which calls
	 *  lockUI and unlockUI if parent is a ASyncProcess
	 *  <br>
	 *	Called from ProcessDialog.actionPerformed
	 *
	 *  @param parent ASyncProcess & Container
	 *  @param WindowNo window no
	 *  @param paraPanel Process Parameter Panel
	 *  @param pi ProcessInfo process info
	 *  @param trx Transaction
	 */
	public static void process(ASyncProcess parent, int WindowNo, IProcessParameter parameter, ProcessInfo pi, Trx trx)
	{
		ProcessCtl.process(parent, WindowNo, parameter, pi, trx);
	}
}

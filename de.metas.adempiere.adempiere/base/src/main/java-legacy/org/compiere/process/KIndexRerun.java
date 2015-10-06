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

import java.math.BigDecimal;
import java.util.logging.Level;

import org.compiere.model.MContainer;
import org.compiere.model.MNewsChannel;
import org.compiere.model.MNewsItem;

/**
 *	Reindex all Content
 *	
 *  @author Yves Sandfort
 *  @version $Id$
 */
public class KIndexRerun extends SvrProcess
{
	/**	WebProject Parameter		*/
	private int		p_CM_WebProject_ID = 0;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("CM_WebProject_ID"))
				p_CM_WebProject_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		// ReIndex Container
		int[] containers = MContainer.getAllIDs("CM_Container","CM_WebProject_ID=" + p_CM_WebProject_ID, get_TrxName());
		for(int i=0;i<containers.length;i++)
		{
			MContainer thisContainer = new MContainer(getCtx(),containers[i], get_TrxName());
			thisContainer.reIndex(false);
		}
		// ReIndex News
		int[] newsChannels = MNewsChannel.getAllIDs("CM_NewsChannel","CM_WebProject_ID=" + p_CM_WebProject_ID, get_TrxName());
		for (int i=0;i<newsChannels.length;i++)
		{
			MNewsChannel thisChannel = new MNewsChannel(getCtx(),newsChannels[i], get_TrxName());
			thisChannel.reIndex(false);
			int[] newsItems = MNewsItem.getAllIDs("CM_NewsItem","CM_NewsChannel_ID=" + newsChannels[i], get_TrxName());
			for (int k=0;k<newsItems.length;k++)
			{
				MNewsItem thisItem = new MNewsItem(getCtx(), newsItems[k], get_TrxName());
				thisItem.reIndex(false);
			}
		}
		return "finished...";
	}	//	doIt

}	//	KIndexRerun

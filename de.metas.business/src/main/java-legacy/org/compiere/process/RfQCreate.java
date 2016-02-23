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

import org.compiere.model.MRfQ;
import org.compiere.model.MRfQResponse;
import org.compiere.model.MRfQTopic;
import org.compiere.model.MRfQTopicSubscriber;

/**
 *	Create RfQ Response from RfQ Topic
 *	
 *  @author Jorg Janke
 *  @version $Id: RfQCreate.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class RfQCreate extends SvrProcess
{
	/**	Send RfQ				*/
	private boolean	p_IsSendRfQ = false;
	/**	RfQ						*/
	private int		p_C_RfQ_ID = 0;
	
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
			else if (name.equals("IsSendRfQ"))
				p_IsSendRfQ = "Y".equals(para[i].getParameter());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_C_RfQ_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (translated text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		MRfQ rfq = new MRfQ (getCtx(), p_C_RfQ_ID, get_TrxName());
		log.info("doIt - " + rfq + ", Send=" + p_IsSendRfQ);
		String error = rfq.checkQuoteTotalAmtOnly();
		if (error != null && error.length() > 0)
			throw new Exception (error);

		int counter = 0;
		int sent = 0;
		int notSent = 0;
		
		//	Get all existing responses
		MRfQResponse[] responses = rfq.getResponses (false, false);
		
		//	Topic
		MRfQTopic topic = new MRfQTopic (getCtx(), rfq.getC_RfQ_Topic_ID(), get_TrxName());
		MRfQTopicSubscriber[] subscribers = topic.getSubscribers();
		for (int i = 0; i < subscribers.length; i++)
		{
			MRfQTopicSubscriber subscriber = subscribers[i];
			boolean skip = false;
			//	existing response
			for (int r = 0; r < responses.length; r++)
			{
				if (subscriber.getC_BPartner_ID() == responses[r].getC_BPartner_ID()
					&& subscriber.getC_BPartner_Location_ID() == responses[r].getC_BPartner_Location_ID())
				{
					skip = true;
					break;
				}
			}
			if (skip)
				continue;
			
			//	Create Response
			MRfQResponse response = new MRfQResponse (rfq, subscriber);
			if (response.get_ID() == 0)	//	no lines
				continue;
			
			counter++;
			if (p_IsSendRfQ)
			{
				if (response.sendRfQ())
					sent++;
				else
					notSent++;
			}
		}	//	for all subscribers

		String retValue = "@Created@ " + counter;
		if (p_IsSendRfQ)
			retValue += " - @IsSendRfQ@=" + sent + " - @Error@=" + notSent;
		return retValue;
	}	//	doIt
	
}	//	RfQCreate

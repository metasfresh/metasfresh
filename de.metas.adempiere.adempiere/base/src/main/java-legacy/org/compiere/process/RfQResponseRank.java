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
import java.util.Arrays;
import java.util.logging.Level;

import org.compiere.model.MRfQ;
import org.compiere.model.MRfQLine;
import org.compiere.model.MRfQLineQty;
import org.compiere.model.MRfQResponse;
import org.compiere.model.MRfQResponseLine;
import org.compiere.model.MRfQResponseLineQty;
import org.compiere.util.Env;

/**
 *	Rank RfQ Responses	
 *	
 *  @author Jorg Janke
 *  @version $Id: RfQResponseRank.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 *  
 *  @author Teo Sarca, teo.sarca@gmail.com
 *  	<li>BF [ 2892595 ] RfQResponseRank - ranking is not good
 *  		https://sourceforge.net/tracker/?func=detail&aid=2892595&group_id=176962&atid=879332
 */
public class RfQResponseRank extends SvrProcess
{
	/**	RfQ 			*/
	private int		p_C_RfQ_ID = 0;

	/**
	 * 	Prepare
	 */
	protected void prepare ()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_C_RfQ_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process.
	 * 	<pre>
	 * 	- ignore 0 or invalid responses
	 * 	- rank among qty
	 * 	- for selected PO qty select winner
	 * 	- if all lines are winner - select that
	 *  </pre> 
	 *	@return message
	 */
	protected String doIt () throws Exception
	{
		MRfQ rfq = new MRfQ (getCtx(), p_C_RfQ_ID, get_TrxName());
		if (rfq.get_ID() == 0)
			throw new IllegalArgumentException("No RfQ found");
		log.info(rfq.toString());
		String error = rfq.checkQuoteTotalAmtOnly();
		if (error != null && error.length() > 0)
			throw new Exception (error);
		
		//	Get Completed, Active Responses
		MRfQResponse[] responses = rfq.getResponses (true, true);
		log.fine("doIt - #Responses=" + responses.length);
		if (responses.length == 0)
			throw new IllegalArgumentException("No completed RfQ Responses found");
		if (responses.length == 1)
		{
			responses[0].setIsSelectedWinner(true);
			responses[0].saveEx();
			return "Only one completed RfQ Response found";
		}
			
		//	Rank
		if (rfq.isQuoteTotalAmtOnly())
			rankResponses(rfq, responses);
		else
			rankLines (rfq, responses);
		return "# " + responses.length;
	}	//	doIt

	
	/**************************************************************************
	 * 	Rank Lines
	 *	@param rfq RfQ 
	 *	@param responses responses
	 */
	@SuppressWarnings("unchecked")
	private void rankLines (MRfQ rfq, MRfQResponse[] responses)
	{
		MRfQLine[] rfqLines = rfq.getLines();
		if (rfqLines.length == 0)
			throw new IllegalArgumentException("No RfQ Lines found");
		
		//	 for all lines
		for (int i = 0; i < rfqLines.length; i++)
		{
			//	RfQ Line
			MRfQLine rfqLine = rfqLines[i];
			if (!rfqLine.isActive())
				continue;
			log.fine("rankLines - " + rfqLine);
			MRfQLineQty[] rfqQtys = rfqLine.getQtys();
			for (int j = 0; j < rfqQtys.length; j++)
			{
				//	RfQ Line Qty
				MRfQLineQty rfqQty = rfqQtys[j];
				if (!rfqQty.isActive() || !rfqQty.isRfQQty())
					continue;
				log.fine("rankLines Qty - " + rfqQty);
				MRfQResponseLineQty[] respQtys = rfqQty.getResponseQtys(false);
				for (int kk = 0; kk < respQtys.length; kk++)
				{
					//	Response Line Qty
					MRfQResponseLineQty respQty = respQtys[kk];
					if (!respQty.isActive() || !respQty.isValidAmt())
					{
						respQty.setRanking(999);
						respQty.save();
						log.fine("  - ignored: " + respQty);
					}
				}	//	for all respones line qtys
				
				//	Rank RfQ Line Qtys
				respQtys = rfqQty.getResponseQtys(false);
				if (respQtys.length == 0)
					log.fine("  - No Qtys with valid Amounts");
				else
				{
					Arrays.sort(respQtys, respQtys[0]);
					int lastRank = 1;		//	multiple rank #1
					BigDecimal lastAmt = Env.ZERO;
					int rank = 0;
					for (int k = 0; k < respQtys.length; k++)
					{
						MRfQResponseLineQty qty = respQtys[k];
						if (!qty.isActive() || qty.getRanking() == 999)
						{
							continue;
						}
						BigDecimal netAmt = qty.getNetAmt();
						if (netAmt == null)
						{
							qty.setRanking(999);
							qty.saveEx();
							log.fine("  - Rank 999: " + qty);
							continue;
						}
						
						if (lastAmt.compareTo(netAmt) != 0)
						{
							lastRank = rank+1;
							lastAmt = qty.getNetAmt();
						}
						qty.setRanking(lastRank);
						log.fine("  - Rank " + lastRank + ": " + qty);
						qty.save();
						//	
						if (rank == 0)	//	Update RfQ
						{
							rfqQty.setBestResponseAmt(qty.getNetAmt());
							rfqQty.save();
						}
						rank++;
					}
				}
			}	//	for all rfq line qtys
		}	//	 for all rfq lines
		
		//	Select Winner based on line ranking
		MRfQResponse winner = null;
		for (int ii = 0; ii < responses.length; ii++)
		{
			MRfQResponse response = responses[ii];
			if (response.isSelectedWinner())
				response.setIsSelectedWinner(false);
			int ranking = 0;
			MRfQResponseLine[] respLines = response.getLines(false);
			for (int jj = 0; jj < respLines.length; jj++)
			{
				//	Response Line
				MRfQResponseLine respLine = respLines[jj];
				if (!respLine.isActive())
					continue;
				if (respLine.isSelectedWinner())
					respLine.setIsSelectedWinner(false);
				MRfQResponseLineQty[] respQtys = respLine.getQtys(false);
				for (int kk = 0; kk < respQtys.length; kk++)
				{
					//	Response Line Qty
					MRfQResponseLineQty respQty = respQtys[kk];
					if (!respQty.isActive())
						continue;
					ranking += respQty.getRanking();
					if (respQty.getRanking() == 1 
						&& respQty.getRfQLineQty().isPurchaseQty())
					{
						respLine.setIsSelectedWinner(true);
						respLine.save();
						break;
					}
				}
			}
			response.setRanking(ranking);
			response.save();
			log.fine("- Response Ranking " + ranking + ": " + response);
			if (!rfq.isQuoteSelectedLines())	//	no total selected winner if not all lines
			{
				if (winner == null && ranking > 0)
					winner = response;
				if (winner != null 
						&& response.getRanking() > 0 
						&& response.getRanking() < winner.getRanking())
					winner = response;
			}
		}
		if (winner != null)
		{
			winner.setIsSelectedWinner(true);
			winner.save();
			log.fine("- Response Winner: " + winner);
		}
	}	//	rankLines

	
	/**************************************************************************
	 * 	Rank Response based on Header
	 *	@param rfq RfQ
	 *	@param responses responses
	 */
	private void rankResponses (MRfQ rfq, MRfQResponse[] responses)
	{
		int ranking = 1;
		//	Responses Ordered by Price
		for (int ii = 0; ii < responses.length; ii++)
		{
			MRfQResponse response = responses[ii];
			if (response.getPrice() != null 
				&& response.getPrice().compareTo(Env.ZERO) > 0)
			{
				if (response.isSelectedWinner() != (ranking == 1))
					response.setIsSelectedWinner(ranking == 1);
				response.setRanking(ranking);
				//
				ranking++;
			}
			else
			{
				response.setRanking(999);
				if (response.isSelectedWinner())
					response.setIsSelectedWinner(false);
			}
			response.save();
			log.fine("rankResponse - " + response);
		}
	}	//	rankResponses
	
}	//	RfQResponseRank

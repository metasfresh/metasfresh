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
package org.compiere.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.print.ReportEngine;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 *	RfQ Response Model	
 *	
 *  @author Jorg Janke
 *  @version $Id: MRfQResponse.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MRfQResponse extends X_C_RfQResponse
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1472377321844135042L;


	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_RfQResponse_ID id
	 *	@param trxName transaction
	 */
	public MRfQResponse (Properties ctx, int C_RfQResponse_ID, String trxName)
	{
		super (ctx, C_RfQResponse_ID, trxName);
		if (C_RfQResponse_ID == 0)
		{
			setIsComplete (false);
			setIsSelectedWinner (false);
			setIsSelfService (false);
			setPrice (Env.ZERO);
			setProcessed(false);
			setProcessing(false);
		}
	}	//	MRfQResponse

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MRfQResponse (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRfQResponse

	/**
	 * 	Parent Constructor
	 *	@param rfq rfq
	 *	@param subscriber subscriber
	 */
	public MRfQResponse (MRfQ rfq, MRfQTopicSubscriber subscriber)
	{
		this (rfq, subscriber, 
			subscriber.getC_BPartner_ID(), 
			subscriber.getC_BPartner_Location_ID(), 
			subscriber.getAD_User_ID());
	}	//	MRfQResponse

	/**
	 * 	Parent Constructor
	 *	@param rfq rfq
	 *	@param partner web response
	 */
	public MRfQResponse (MRfQ rfq, MBPartner partner)
	{
		this (rfq, null, 
			partner.getC_BPartner_ID(), 
			partner.getPrimaryC_BPartner_Location_ID(), 
			partner.getPrimaryAD_User_ID());
	}	//	MRfQResponse
	
	/**
	 * 	Parent Constructor.
	 * 	Automatically saved if lines were created 
	 * 	Saved automatically
	 *	@param rfq rfq
	 *	@param subscriber optional subscriber
	 *	@param C_BPartner_ID bpartner
	 *	@param C_BPartner_Location_ID bpartner location
	 *	@param AD_User_ID bpartner user
	 */
	public MRfQResponse (MRfQ rfq, MRfQTopicSubscriber subscriber,
		int C_BPartner_ID, int C_BPartner_Location_ID, int AD_User_ID)
	{
		this (rfq.getCtx(), 0, rfq.get_TrxName());
		setClientOrg(rfq);
		setC_RfQ_ID(rfq.getC_RfQ_ID());
		setC_Currency_ID (rfq.getC_Currency_ID());
		setName (rfq.getName());
		m_rfq = rfq;
		//	Subscriber info
		setC_BPartner_ID (C_BPartner_ID);
		setC_BPartner_Location_ID (C_BPartner_Location_ID);
		setAD_User_ID(AD_User_ID);
		
		//	Create Lines
		MRfQLine[] lines = rfq.getLines();
		for (int i = 0; i < lines.length; i++)
		{
			if (!lines[i].isActive())
				continue;
			
			//	Product on "Only" list
			if (subscriber != null 
				&& !subscriber.isIncluded(lines[i].getM_Product_ID() ))
				continue;
			//
			if (get_ID() == 0)	//	save Response
				save();

			MRfQResponseLine line = new MRfQResponseLine (this, lines[i]);
			//	line is not saved (dumped) if there are no Qtys 
		}
	}	//	MRfQResponse

	/**	underlying RfQ				*/
	private MRfQ				m_rfq = null;
	/** Lines						*/
	private MRfQResponseLine[]	m_lines = null;
	
	
	/**************************************************************************
	 * 	Get Response Lines
	 * 	@param requery requery
	 *	@return array of Response Lines
	 */
	public MRfQResponseLine[] getLines(boolean requery)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		ArrayList<MRfQResponseLine> list = new ArrayList<MRfQResponseLine>();
		String sql = "SELECT * FROM C_RfQResponseLine "
			+ "WHERE C_RfQResponse_ID=? AND IsActive='Y'";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getC_RfQResponse_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add(new MRfQResponseLine(getCtx(), rs, get_TrxName()));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "getLines", e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		
		m_lines = new MRfQResponseLine[list.size ()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getLines
	
	/**
	 * 	Get Response Lines (no requery)
	 *	@return array of Response Lines
	 */
	public MRfQResponseLine[] getLines ()
	{
		return getLines (false);
	}	//	getLines
	
	
	/**
	 * 	Get RfQ
	 *	@return rfq
	 */
	public MRfQ getRfQ()
	{
		if (m_rfq == null)
			m_rfq = MRfQ.get (getCtx(), getC_RfQ_ID(), get_TrxName());
		return m_rfq;
	}	//	getRfQ
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MRfQResponse[");
		sb.append(get_ID())
			.append(",Complete=").append(isComplete())
			.append(",Winner=").append(isSelectedWinner())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	
	/**************************************************************************
	 * 	Send RfQ
	 *	@return true if RfQ is sent per email.
	 */
	public boolean sendRfQ()
	{
		MUser to = MUser.get(getCtx(), getAD_User_ID());
		if (to.get_ID() == 0 || to.getEMail() == null || to.getEMail().length() == 0)
		{
			log.log(Level.SEVERE, "No User or no EMail - " + to);
			return false;
		}
		MClient client = MClient.get(getCtx());
		//
		String message = getDescription();
		if (message == null || message.length() == 0)
			message = getHelp();
		else if (getHelp() != null)
			message += "\n" + getHelp();
		if (message == null)
			message = getName();
		//
		EMail email = client.createEMail(to.getEMail(), "RfQ: " + getName(), message);
		email.addAttachment(createPDF());
		if (EMail.SENT_OK.equals(email.send()))
		{
			setDateInvited(new Timestamp (System.currentTimeMillis()));
			save();
			return true;
		}
		return false;
	}	//	sendRfQ
	
	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	public File createPDF ()
	{
		return createPDF (null);
	}	//	getPDF
	
	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return File or null
	 */
	public File createPDF (File file)
	{
		ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.RFQ, getC_RfQResponse_ID());
		if (re == null)
			return null;
		return re.getPDF(file);
	}	//	getPDF

	
	/**************************************************************************
	 * 	Check if Response is Complete
	 *	@return null if complere - error message otherwise
	 */
	public String checkComplete()
	{
		if (isComplete())
			setIsComplete(false);
		MRfQ rfq = getRfQ();
		
		//	Is RfQ Total valid
		String error = rfq.checkQuoteTotalAmtOnly();
		if (error != null && error.length() > 0)
			return error;
		
		//	Do we have Total Amount ?
		if (rfq.isQuoteTotalAmt() || rfq.isQuoteTotalAmtOnly())
		{
			BigDecimal amt = getPrice();
			if (amt == null || Env.ZERO.compareTo(amt) >= 0)
				return "No Total Amount";
		}
		
		//	Do we have an amount/qty for all lines
		if (rfq.isQuoteAllLines())
		{
			MRfQResponseLine[] lines = getLines(false);
			for (int i = 0; i < lines.length; i++)
			{
				MRfQResponseLine line = lines[i];
				if (!line.isActive())
					return "Line " + line.getRfQLine().getLine()
						+ ": Not Active";
				boolean validAmt = false;
				MRfQResponseLineQty[] qtys = line.getQtys(false);
				for (int j = 0; j < qtys.length; j++)
				{
					MRfQResponseLineQty qty = qtys[j];
					if (!qty.isActive())
						continue;
					BigDecimal amt = qty.getNetAmt();
					if (amt != null && Env.ZERO.compareTo(amt) < 0)
					{
						validAmt = true;
						break;
					}
				}
				if (!validAmt)
					return "Line " + line.getRfQLine().getLine()
						+ ": No Amount";
			}
		}
		
		//	Do we have an amount for all line qtys
		if (rfq.isQuoteAllQty())
		{
			MRfQResponseLine[] lines = getLines(false);
			for (int i = 0; i < lines.length; i++)
			{
				MRfQResponseLine line = lines[i];
				MRfQResponseLineQty[] qtys = line.getQtys(false);
				for (int j = 0; j < qtys.length; j++)
				{
					MRfQResponseLineQty qty = qtys[j];
					if (!qty.isActive())
						return "Line " + line.getRfQLine().getLine()
						+ " Qty=" + qty.getRfQLineQty().getQty()
						+ ": Not Active";
					BigDecimal amt = qty.getNetAmt();
					if (amt == null || Env.ZERO.compareTo(amt) >= 0)
						return "Line " + line.getRfQLine().getLine()
							+ " Qty=" + qty.getRfQLineQty().getQty()
							+ ": No Amount";
				}
			}
		}
		
		setIsComplete(true);
		return null;
	}	//	checkComplete
	
	/**
	 * 	Is Quote Total Amt Only
	 *	@return true if only Total
	 */
	public boolean isQuoteTotalAmtOnly()
	{
		return getRfQ().isQuoteTotalAmtOnly();
	}	//	isQuoteTotalAmtOnly
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		//	Calculate Complete Date (also used to verify)
		if (getDateWorkStart() != null && getDeliveryDays() != 0)
			setDateWorkComplete (TimeUtil.addDays(getDateWorkStart(), getDeliveryDays()));
		//	Calculate Delivery Days
		else if (getDateWorkStart() != null && getDeliveryDays() == 0 && getDateWorkComplete() != null)
			setDeliveryDays (TimeUtil.getDaysBetween(getDateWorkStart(), getDateWorkComplete()));
		//	Calculate Start Date
		else if (getDateWorkStart() == null && getDeliveryDays() != 0 && getDateWorkComplete() != null)
			setDateWorkStart (TimeUtil.addDays(getDateWorkComplete(), getDeliveryDays() * -1));

		
		return true;
	}	//	beforeSave

}	//	MRfQResponse

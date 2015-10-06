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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;


/**
 *	RfQ Topic Subscriber Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MRfQTopicSubscriber.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MRfQTopicSubscriber extends X_C_RfQ_TopicSubscriber
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7364350828501354344L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_RfQ_TopicSubscriber_ID id
	 *	@param trxName transaction
	 */
	public MRfQTopicSubscriber (Properties ctx, int C_RfQ_TopicSubscriber_ID, String trxName)
	{
		super (ctx, C_RfQ_TopicSubscriber_ID, trxName);
	}	//	MRfQTopic_Subscriber

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MRfQTopicSubscriber (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRfQTopic_Subscriber
	
	/**	Restrictions					*/
	private MRfQTopicSubscriberOnly[] m_restrictions = null;
	
	/**
	 * 	Get Restriction Records
	 *	@param requery requery
	 *	@return arry of onlys
	 */
	public MRfQTopicSubscriberOnly[] getRestrictions (boolean requery)
	{
		if (m_restrictions != null && !requery)
			return m_restrictions;
		
		ArrayList<MRfQTopicSubscriberOnly> list = new ArrayList<MRfQTopicSubscriberOnly>();
		String sql = "SELECT * FROM C_RfQ_TopicSubscriberOnly WHERE C_RfQ_TopicSubscriber_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getC_RfQ_TopicSubscriber_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add(new MRfQTopicSubscriberOnly(getCtx(), rs, get_TrxName()));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
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
		
		m_restrictions = new MRfQTopicSubscriberOnly[list.size ()];
		list.toArray (m_restrictions);
		return m_restrictions;
	}	//	getRestrictions
	
	
	/**
	 * 	Is the product included?
	 *	@param M_Product_ID product
	 *	@return true if no restrictions or included in "positive" only list
	 */
	public boolean isIncluded (int M_Product_ID)
	{
		//	No restrictions
		if (getRestrictions(false).length == 0)
			return true;
		
		for (int i = 0; i < m_restrictions.length; i++)
		{
			MRfQTopicSubscriberOnly restriction = m_restrictions[i];
			if (!restriction.isActive())
				continue;
			//	Product
			if (restriction.getM_Product_ID() == M_Product_ID)
				return true;
			//	Product Category
			if (MProductCategory.isCategory(restriction.getM_Product_Category_ID(), M_Product_ID))
				return true;
		}
		//	must be on "positive" list
		return false;
	}	//	isIncluded
	
}	//	MRfQTopicSubscriber

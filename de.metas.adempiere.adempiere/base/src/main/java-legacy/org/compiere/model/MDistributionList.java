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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Material Distribution List
 *	
 *  @author Jorg Janke
 *  @version $Id: MDistributionList.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MDistributionList extends X_M_DistributionList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1570304450929416071L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_DistributionList_ID id
	 *	@param trxName transaction
	 */
	public MDistributionList (Properties ctx, int M_DistributionList_ID, String trxName)
	{
		super (ctx, M_DistributionList_ID, trxName);
	}	//	MDistributionList

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MDistributionList (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MDistributionList
	
	/**
	 * 	Get Distibution Lines.
	 * 	Add/Correct also Total Ratio
	 *	@return array of lines
	 */
	public MDistributionListLine[] getLines()
	{
		ArrayList<MDistributionListLine> list = new ArrayList<MDistributionListLine>();
		BigDecimal ratioTotal = Env.ZERO;
		//
		String sql = "SELECT * FROM M_DistributionListLine WHERE M_DistributionList_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getM_DistributionList_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MDistributionListLine line = new MDistributionListLine(getCtx(), rs, get_TrxName());
				list.add(line);
				BigDecimal ratio = line.getRatio();
				if (ratio != null)
					ratioTotal = ratioTotal.add(ratio);
			}
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
		//	Update Ratio
		if (ratioTotal.compareTo(getRatioTotal()) != 0)
		{
			log.info("getLines - Set RatioTotal from " + getRatioTotal() + " to " + ratioTotal);
			setRatioTotal(ratioTotal);
			save();
		}
		
		MDistributionListLine[] retValue = new MDistributionListLine[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getLines
	
	
	
}	//	MDistributionList

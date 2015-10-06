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

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Distribution Run Detail
 *	
 *  @author Jorg Janke
 *  @version $Id: MDistributionRunDetail.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MDistributionRunDetail extends X_T_DistributionRunDetail
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8679063565056887913L;

	/**
	 * 	Get Distribution Dun details
	 *	@param ctx context
	 *	@param M_DistributionRun_ID id
	 *	@param orderBP if true ordered by Business Partner otherwise Run Line
	 *	@param trxName transaction
	 *	@return array of details
	 */
	static public MDistributionRunDetail[] get (Properties ctx, int M_DistributionRun_ID, 
		boolean orderBP, String trxName)
	{
		ArrayList<MDistributionRunDetail> list = new ArrayList<MDistributionRunDetail>();
		String sql = "SELECT * FROM T_DistributionRunDetail WHERE M_DistributionRun_ID=? ";
		if (orderBP)
			sql += "ORDER BY C_BPartner_ID, C_BPartner_Location_ID";
		else
			sql += "ORDER BY M_DistributionRunLine_ID";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, M_DistributionRun_ID);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add(new MDistributionRunDetail(ctx, rs, trxName));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, sql, e);
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
		MDistributionRunDetail[] retValue = new MDistributionRunDetail[list.size()];
		list.toArray (retValue);
		return retValue;
	}	//	get
	
	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MDistributionRunDetail.class);
	
	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param T_DistributionRunDetail_ID id
	 *	@param trxName trx
	 */
	public MDistributionRunDetail (Properties ctx, int T_DistributionRunDetail_ID, String trxName)
	{
		super (ctx, T_DistributionRunDetail_ID, trxName);
	}	//	MDistributionRunDetail
	
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MDistributionRunDetail (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	DistributionRunDetail
	
	/**	Precision		*/
	private int	m_precision = 0;
	
	/**
	 * 	Round MinQty & Qty
	 *	@param precision precision (saved)
	 */
	public void round (int precision)
	{
		boolean dirty = false;
		m_precision = precision; 
		BigDecimal min = getMinQty();
		if (min.scale() > m_precision)
		{
			setMinQty(min.setScale(m_precision, BigDecimal.ROUND_HALF_UP));
			dirty = true;
		}
		BigDecimal qty = getQty();
		if (qty.scale() > m_precision)
		{
			setQty(qty.setScale(m_precision, BigDecimal.ROUND_HALF_UP));
			dirty = true;
		}
		if (dirty)
			save();
	}	//	round
	
	/**
	 * 	We can adjust Allocation Qty
	 *	@return true if qty > min
	 */
	public boolean isCanAdjust()
	{
		return (getQty().compareTo(getMinQty()) > 0);
	}	//	isCanAdjust

	/**
	 * 	Get Actual Allocation Qty
	 *	@return the greater of the min/qty
	 */
	public BigDecimal getActualAllocation()
	{
		if (getQty().compareTo(getMinQty()) > 0)
			return getQty();
		else
			return getMinQty();
	}	//	getActualAllocation

	/**
	 * 	Adjust the Quantity maintaining UOM precision
	 * 	@param difference difference
	 * 	@return remaining difference (because under Min or rounding)
	 */
	public BigDecimal adjustQty (BigDecimal difference)
	{
		BigDecimal diff = difference.setScale(m_precision, BigDecimal.ROUND_HALF_UP);
		BigDecimal qty = getQty();
		BigDecimal max = getMinQty().subtract(qty);
		BigDecimal remaining = Env.ZERO;
		if (max.compareTo(diff) > 0)	//	diff+max are negative
		{
			remaining = diff.subtract(max);
			setQty(qty.add(max));
		}
		else
			setQty(qty.add(diff));
		log.fine("Qty=" + qty + ", Min=" + getMinQty() 
			+ ", Max=" + max + ", Diff=" + diff + ", newQty=" + getQty() 
			+ ", Remaining=" + remaining);
		return remaining;
	}	//	adjustQty
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MDistributionRunDetail[")
			.append (get_ID ())
			.append (";M_DistributionListLine_ID=").append (getM_DistributionListLine_ID())
			.append(";Qty=").append(getQty())
			.append(";Ratio=").append(getRatio())
			.append(";MinQty=").append(getMinQty())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	DistributionRunDetail

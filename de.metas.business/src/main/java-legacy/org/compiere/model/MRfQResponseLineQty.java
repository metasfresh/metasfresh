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
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/**
 *	RfQ Response Line Qty
 *	
 *  @author Jorg Janke
 *  @version $Id: MRfQResponseLineQty.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MRfQResponseLineQty extends X_C_RfQResponseLineQty
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4118030805518374853L;

	/**
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param C_RfQResponseLineQty_ID id
	 *	@param trxName transaction
	 */
	public MRfQResponseLineQty (Properties ctx, int C_RfQResponseLineQty_ID, String trxName)
	{
		super (ctx, C_RfQResponseLineQty_ID, trxName);
		if (C_RfQResponseLineQty_ID == 0)
		{
		//	setC_RfQResponseLineQty_ID (0);		//	PK
		//	setC_RfQLineQty_ID (0);
		//	setC_RfQResponseLine_ID (0);
			//
			setPrice (Env.ZERO);
			setDiscount(Env.ZERO);
		}

	}	//	MRfQResponseLineQty

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MRfQResponseLineQty (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRfQResponseLineQty
	
	/**
	 * 	Parent Constructor
	 *	@param line line
	 *	@param qty qty
	 */
	public MRfQResponseLineQty (MRfQResponseLine line, MRfQLineQty qty)
	{
		this (line.getCtx(), 0, line.get_TrxName());
		setClientOrg(line);
		setC_RfQResponseLine_ID (line.getC_RfQResponseLine_ID());
		setC_RfQLineQty_ID (qty.getC_RfQLineQty_ID());
	}	//	MRfQResponseLineQty
	
	
	/**	RfQ Line Qty			*/
	private MRfQLineQty		m_rfqQty = null;
	/**	100						*/
	private static BigDecimal 	ONEHUNDRED = new BigDecimal (100);
	
	/**
	 * 	Get RfQ Line Qty
	 *	@return RfQ Line Qty
	 */
	public MRfQLineQty getRfQLineQty()
	{
		if (m_rfqQty == null)
			m_rfqQty = MRfQLineQty.get (getCtx(), getC_RfQLineQty_ID(), get_TrxName()); 
		return m_rfqQty;
	}	//	getRfQLineQty
	

	/**
	 * 	Is the Amount (price - discount) Valid
	 * 	@return true if valid
	 */
	public boolean isValidAmt()
	{
		BigDecimal price = getPrice();
		if (price == null || Env.ZERO.compareTo(price) == 0)
		{
			log.warning("No Price - " + price);
			return false;
		}
		BigDecimal discount = getDiscount();
		if (discount != null)
		{
			if (discount.abs().compareTo(ONEHUNDRED) > 0)
			{
				log.warning("Discount > 100 - " + discount);
				return false;
			}
		}
		BigDecimal net = getNetAmt();
		if (net == null)
		{
			log.warning("Net is null");
			return false;
		}
		if (net.compareTo(Env.ZERO) <= 0)
		{
			log.warning("Net <= 0 - " + net);
			return false;
		}
		return true;
	}	//	isValidAmt
	
	/**
	 * 	Get Net Amt (price minus discount in %)
	 *	@return net amount or null
	 */
	public BigDecimal getNetAmt()
	{
		BigDecimal price = getPrice();
		if (price == null || Env.ZERO.compareTo(price) == 0)
			return null;
		//	
		BigDecimal discount = getDiscount();
		if (discount == null || Env.ZERO.compareTo(discount) == 0)
			return price;
		//	Calculate
	//	double result = price.doubleValue() * (100.0 - discount.doubleValue()) / 100.0;
		BigDecimal factor = ONEHUNDRED.subtract(discount);
		return price.multiply(factor).divide(ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);  
	}	//	getNetAmt

	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MRfQResponseLineQty[");
		sb.append(get_ID()).append(",Rank=").append(getRanking())
			.append(",Price=").append(getPrice())
			.append(",Discount=").append(getDiscount())
			.append(",Net=").append(getNetAmt())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 *	Compare based on net amount
     *	@param o1 the first object to be compared.
     *	@param o2 the second object to be compared.
     *	@return a negative integer, zero, or a positive integer as the
     * 	       first argument is less than, equal to, or greater than the
     *	       second. 
     *	@throws ClassCastException if the arguments' types prevent them from
     * 	       being compared by this Comparator.
     */
	@Override
	public int compare(Object o1, Object o2)
	{
		if (o1 == null)
			throw new IllegalArgumentException("o1 = null");
		if (o2 == null)
			throw new IllegalArgumentException("o2 = null");
		MRfQResponseLineQty q1 = null;
		MRfQResponseLineQty q2 = null;
		if (o1 instanceof MRfQResponseLineQty)
			q1 = (MRfQResponseLineQty)o1;
		else
			throw new ClassCastException("o1");
		if (o2 instanceof MRfQResponseLineQty)
			q2 = (MRfQResponseLineQty)o2;
		else
			throw new ClassCastException("o2");
		//
		if (!q1.isValidAmt())
			return -99;
		if (!q2.isValidAmt())
			return +99;
   		BigDecimal net1 = q1.getNetAmt();
   		if (net1 == null)
   			return -9;
		BigDecimal net2 = q2.getNetAmt();
   		if (net2 == null)
   			return +9;
		return net1.compareTo(net2);
	}	//	compare

	/**
	 * 	Is Net Amount equal ?
     * 	@param   obj   the reference object with which to compare.
     *	@return true if Net Amount equal
     */
    @Override
	public boolean equals(Object obj)
	{
    	if (obj instanceof MRfQResponseLineQty)
    	{
    		MRfQResponseLineQty cmp = (MRfQResponseLineQty)obj;
    		if (!cmp.isValidAmt() || !isValidAmt())
    			return false;
    		BigDecimal cmpNet = cmp.getNetAmt();
    		if (cmpNet == null)
    			return false;
    		BigDecimal net = cmp.getNetAmt();
    		if (net == null)
    			return false;
    		return cmpNet.compareTo(net) == 0; 
    	}
    	return false;
	}	//	equals
    
    @Override
    public int hashCode()
    {
    	// NOTE: super.hashCode() is accepted. We added this method here to avoid the warning about having equals() but not hashCode()
    	return super.hashCode();
    }

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (!isActive())
			setRanking(999);
		return true;
	}	//	beforeSave

}	//	MRfQResponseLineQty

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
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DB;

/**
 * 	Landed Cost Model
 *  @author Jorg Janke
 *  @version $Id: MLandedCost.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MLandedCost extends X_C_LandedCost
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5645509613930428050L;


	/**
	 *	Get Costs of Invoice Line
	 * 	@param il invoice line
	 *	@return array of landed cost lines
	 */
	public static MLandedCost[] getLandedCosts (MInvoiceLine il)
	{
		ArrayList<MLandedCost> list = new ArrayList<MLandedCost> ();
		String sql = "SELECT * FROM C_LandedCost WHERE C_InvoiceLine_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, il.get_TrxName());
			pstmt.setInt (1, il.getC_InvoiceLine_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MLandedCost (il.getCtx(), rs, il.get_TrxName()));
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
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
		//
		MLandedCost[] retValue = new MLandedCost[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	// getLandedCosts

	/**	Logger	*/
	private static Logger s_log = LogManager.getLogger(MLandedCost.class);

	
	/***************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param C_LandedCost_ID id
	 * @param trxName trx
	 */
	public MLandedCost (Properties ctx, int C_LandedCost_ID, String trxName)
	{
		super (ctx, C_LandedCost_ID, trxName);
		if (C_LandedCost_ID == 0)
		{
		//	setC_InvoiceLine_ID (0);
		//	setM_CostElement_ID (0);
			setLandedCostDistribution (LANDEDCOSTDISTRIBUTION_Quantity);	// Q
		}
	}	//	MLandedCost

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MLandedCost (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MLandedCost
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true if ok
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		//	One Reference
		if (getM_Product_ID() == 0 
			&& getM_InOut_ID() == 0 
			&& getM_InOutLine_ID() == 0)
		{
			throw new AdempiereException("@NotFound@ @M_Product_ID@ | @M_InOut_ID@ | @M_InOutLine_ID@");
		}
		//	No Product if Line entered
		if (getM_InOutLine_ID() != 0 && getM_Product_ID() != 0)
			setM_Product_ID(0);
				
		return true;
	}	//	beforeSave
	
	/**
	 * 	Allocate Costs.
	 * 	Done at Invoice Line Level
	 * 	@return error message or ""
	 */
	public String allocateCosts()
	{
		MInvoiceLine il = new MInvoiceLine (getCtx(), getC_InvoiceLine_ID(), get_TrxName());
		return il.allocateLandedCosts();
	}	//	allocateCosts
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MLandedCost[");
		sb.append (get_ID ())
			.append (",CostDistribution=").append (getLandedCostDistribution())
			.append(",M_CostElement_ID=").append(getM_CostElement_ID());
		if (getM_InOut_ID() != 0)
			sb.append (",M_InOut_ID=").append (getM_InOut_ID());
		if (getM_InOutLine_ID() != 0)
			sb.append (",M_InOutLine_ID=").append (getM_InOutLine_ID());
		if (getM_Product_ID() != 0)
			sb.append (",M_Product_ID=").append (getM_Product_ID());
		sb.append ("]");
		return sb.toString ();
	} //	toString
	
}	//	MLandedCost

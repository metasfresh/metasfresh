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

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.util.Env;

/**
 *	Product Costing Model (old).
 *	deprecated old costing
 *
 *  @author Jorg Janke
 *  @version $Id: MProductCosting.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MProductCosting extends X_M_Product_Costing
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7980130899951986948L;


	/**
	 * 	Get Costing Of Product
	 *	@param ctx context
	 *	@param M_Product_ID product
	 *	@param trxName trx
	 *	@return array of costs
	 */
	public static MProductCosting[] getOfProduct (Properties ctx, int M_Product_ID, String trxName)
	{		
		String whereClause = "M_Product_ID=?";
		
		List<MProductCosting> costs =new Query(ctx, MProductCosting.Table_Name,whereClause, trxName )
		.setParameters(new Object[]{M_Product_ID})
		.list();	
		return costs.toArray(new MProductCosting[costs.size()]);
	}	//	getOfProduct

	/**
	 * 	Get Costing
	 *	@param ctx context
	 *	@param M_Product_ID product
	 *	@param C_AcctSchema_ID as
	 *	@param trxName trx
	 *	@return first product cosnting
	 */
	public static MProductCosting get (Properties ctx, int M_Product_ID, 
		int C_AcctSchema_ID, String trxName)
	{
		String whereClause = "M_Product_ID=? AND C_AcctSchema_ID=?";
		
		return new Query(ctx, MProductCosting.Table_Name,whereClause, trxName )
		.setParameters(new Object[]{M_Product_ID, C_AcctSchema_ID})
		.firstOnly();	
	}	//	get
	
	/**************************************************************************
	 * 	Standard Constructor (odl)
	 *	@param ctx context
	 *	@param ignored (multi key)
	 *	@param trxName transaction
	 */
	public MProductCosting (Properties ctx, int ignored, String trxName)
	{
		super (ctx, ignored, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
		else
		{
		//	setM_Product_ID (0);
		//	setC_AcctSchema_ID (0);
			//
			setCostAverage (Env.ZERO);
			setCostAverageCumAmt (Env.ZERO);
			setCostAverageCumQty (Env.ZERO);
			setCostStandard (Env.ZERO);
			setCostStandardCumAmt (Env.ZERO);
			setCostStandardCumQty (Env.ZERO);
			setCostStandardPOAmt (Env.ZERO);
			setCostStandardPOQty (Env.ZERO);
			setCurrentCostPrice (Env.ZERO);
			setFutureCostPrice (Env.ZERO);
			setPriceLastInv (Env.ZERO);
			setPriceLastPO (Env.ZERO);
			setTotalInvAmt (Env.ZERO);
			setTotalInvQty (Env.ZERO);
		}
	}	//	MProductCosting

	/**
	 * 	Parent Constructor (old)
	 *	@param product parent
	 *	@param C_AcctSchema_ID accounting schema
	 */
	public MProductCosting (MProduct product, int C_AcctSchema_ID)
	{
		super (product.getCtx(), 0, product.get_TrxName());
		setClientOrg(product);
		setM_Product_ID (product.getM_Product_ID());
		setC_AcctSchema_ID (C_AcctSchema_ID);
	}	//	MProductCosting
	
	
	/**
	 * 	Load Constructor (old)
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MProductCosting (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MProductCosting
	
}	//	MProductCosting


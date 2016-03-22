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
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Commission Run
 *	
 *  @author Jorg Janke
 *  @version $Id: MCommissionRun.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MCommissionRun extends X_C_CommissionRun
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3103035295526318283L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_CommissionRun_ID id
	 *	@param trxName transaction
	 */
	public MCommissionRun (Properties ctx, int C_CommissionRun_ID, String trxName)
	{
		super(ctx, C_CommissionRun_ID, trxName);
		if (C_CommissionRun_ID == 0)
		{
		//	setC_Commission_ID (0);
		//	setDocumentNo (null);
		//	setStartDate (new Timestamp(System.currentTimeMillis()));
			setGrandTotal (Env.ZERO);
			setProcessed (false);
		}
	}	//	MCommissionRun

	/**
	 * 	Parent Constructor
	 *	@param commission parent
	 */
	public MCommissionRun (MCommission commission)
	{
		this (commission.getCtx(), 0, commission.get_TrxName());
		setClientOrg (commission);
		setC_Commission_ID (commission.getC_Commission_ID());
	}	//	MCommissionRun

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MCommissionRun(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MCommissionRun

	/**
	 * 	Get Amounts
	 *	@return array of amounts
	 */
	public MCommissionAmt[] getAmts()
	{
		String sql = "SELECT * FROM C_CommissionAmt WHERE C_CommissionRun_ID=?";
		ArrayList<MCommissionAmt> list = new ArrayList<MCommissionAmt>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_CommissionRun_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MCommissionAmt(getCtx(), rs, get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		
		//	Convert
		MCommissionAmt[] retValue = new MCommissionAmt[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getAmts

	/**
	 * 	Update From Amt
	 */
	public void updateFromAmt()
	{
		MCommissionAmt[] amts = getAmts();
		BigDecimal GrandTotal = Env.ZERO;
		for (int i = 0; i < amts.length; i++)
		{
			MCommissionAmt amt = amts[i];
			GrandTotal = GrandTotal.add(amt.getCommissionAmt());
		}
		setGrandTotal(GrandTotal);
	}	//	updateFromAmt
	
}	//	MCommissionRun

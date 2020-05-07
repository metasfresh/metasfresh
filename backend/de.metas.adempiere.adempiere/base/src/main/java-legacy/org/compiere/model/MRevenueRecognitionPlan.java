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
import java.util.Properties;

import org.compiere.util.Env;

/**
 *	Revenue Recognition Plan
 *	
 *  @author Jorg Janke
 *  @version $Id: MRevenueRecognitionPlan.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MRevenueRecognitionPlan extends X_C_RevenueRecognition_Plan
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6748195415080148091L;


	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_RevenueRecognition_Plan_ID id
	 */
	public MRevenueRecognitionPlan (Properties ctx, int C_RevenueRecognition_Plan_ID, String trxName)
	{
		super (ctx, C_RevenueRecognition_Plan_ID, trxName);
		if (C_RevenueRecognition_Plan_ID == 0)
		{
		//	setC_AcctSchema_ID (0);
		//	setC_Currency_ID (0);
		//	setC_InvoiceLine_ID (0);
		//	setC_RevenueRecognition_ID (0);
		//	setC_RevenueRecognition_Plan_ID (0);
		//	setP_Revenue_Acct (0);
		//	setUnEarnedRevenue_Acct (0);
			setTotalAmt (Env.ZERO);
			setRecognizedAmt (Env.ZERO);
		}	
	}	//	MRevenueRecognitionPlan

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public MRevenueRecognitionPlan (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRevenueRecognitionPlan

	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord)
		{
			MRevenueRecognition rr = new MRevenueRecognition(getCtx(), getC_RevenueRecognition_ID(), get_TrxName());
			if (rr.isTimeBased())
			{
				/**	Get InvoiveQty
				SELECT	QtyInvoiced, M_Product_ID 
				  INTO	v_Qty, v_M_Product_ID
				FROM	C_InvoiceLine 
				WHERE 	C_InvoiceLine_ID=:new.C_InvoiceLine_ID;
				--	Insert
				AD_Sequence_Next ('C_ServiceLevel', :new.AD_Client_ID, v_NextNo);
				INSERT INTO C_ServiceLevel
					(C_ServiceLevel_ID, C_RevenueRecognition_Plan_ID,
					AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
					M_Product_ID, Description, ServiceLevelInvoiced, ServiceLevelProvided,
					Processing,Processed)
				VALUES
					(v_NextNo, :new.C_RevenueRecognition_Plan_ID,
					:new.AD_Client_ID,:new.AD_Org_ID,'Y',now(),:new.CreatedBy,now(),:new.UpdatedBy,
					v_M_Product_ID, NULL, v_Qty, 0,
					'N', 'N');
				**/
			}
		}
		return success;
	}	//	afterSave
}	//	MRevenueRecognitionPlan

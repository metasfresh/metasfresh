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

/**
 *	Payment Batch Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MPaymentBatch.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPaymentBatch extends X_C_PaymentBatch
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 779975501904633495L;

	/**
	 * 	Get Payment Batch for PaySelection
	 *	@param ctx context
	 *	@param C_PaySelection_ID id
	 *	@param trxName transaction
	 *	@return payment batch
	 */
	public static MPaymentBatch getForPaySelection (Properties ctx, int C_PaySelection_ID, String trxName)
	{
		MPaySelection ps = new MPaySelection (ctx, C_PaySelection_ID, trxName);
		MPaymentBatch retValue = new MPaymentBatch (ps);
		return retValue;
	}	//	getForPaySelection
	
	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_PaymentBatch_ID id
	 *	@param trxName transaction
	 */
	public MPaymentBatch (Properties ctx, int C_PaymentBatch_ID, String trxName)
	{
		super(ctx, C_PaymentBatch_ID, trxName);
		if (C_PaymentBatch_ID == 0)
		{
		//	setName (null);
			setProcessed (false);
			setProcessing (false);
		}
	}	//	MPaymentBatch

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MPaymentBatch (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MPaymentBatch

	/**
	 * 	New Constructor
	 *	@param ctx context
	 *	@param Name name
	 *	@param trxName trx
	 */
	public MPaymentBatch (Properties ctx, String Name, String trxName)
	{
		this (ctx, 0, trxName);
		setName (Name);
	}	//	MPaymentBatch

	/**
	 * 	Parent Constructor
	 *	@param ps Pay Selection
	 */
	public MPaymentBatch (MPaySelection ps)
	{
		this (ps.getCtx(), 0, ps.get_TrxName());
		setClientOrg(ps);
		setName (ps.getName());
	}	//	MPaymentBatch

}	//	MPaymentBatch

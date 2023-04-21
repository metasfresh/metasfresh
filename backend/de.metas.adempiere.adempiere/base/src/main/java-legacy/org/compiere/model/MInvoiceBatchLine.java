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
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 *	Invoice Batch Line Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MInvoiceBatchLine.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MInvoiceBatchLine extends X_C_InvoiceBatchLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4022629343631759064L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_InvoiceBatchLine_ID id
	 *	@param trxName trx
	 */
	public MInvoiceBatchLine (Properties ctx, int C_InvoiceBatchLine_ID,
		String trxName)
	{
		super (ctx, C_InvoiceBatchLine_ID, trxName);
		if (C_InvoiceBatchLine_ID == 0)
		{
		//	setC_InvoiceBatch_ID (0);
			/**
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Charge_ID (0);
			setC_DocType_ID (0);	// @C_DocType_ID@
			setC_Tax_ID (0);
			setDocumentNo (null);
			setLine (0);	// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM C_InvoiceBatchLine WHERE C_InvoiceBatch_ID=@C_InvoiceBatch_ID@
			**/
			setDateAcct (new Timestamp(System.currentTimeMillis()));	// @DateDoc@
			setDateInvoiced (new Timestamp(System.currentTimeMillis()));	// @DateDoc@
			setIsTaxIncluded (false);
			setLineNetAmt (Env.ZERO);
			setLineTotalAmt (Env.ZERO);
			setPriceEntered (Env.ZERO);
			setQtyEntered (Env.ONE);	// 1
			setTaxAmt (Env.ZERO);
			setProcessed (false);
		}
	}	//	MInvoiceBatchLine

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MInvoiceBatchLine (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MInvoiceBatchLine
	
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		// Amount
		if (getPriceEntered().signum() == 0)
		{
			throw new FillMandatoryException("PriceEntered");
		}
		return true;
	}	//	beforeSave
	
	/**
	 * 	After Save.
	 * 	Update Header
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (success)
		{
			String sql = "UPDATE C_InvoiceBatch h "
				+ "SET DocumentAmt = COALESCE((SELECT SUM(LineTotalAmt) FROM C_InvoiceBatchLine l "
					+ "WHERE h.C_InvoiceBatch_ID=l.C_InvoiceBatch_ID AND l.IsActive='Y'),0) "
				+ "WHERE C_InvoiceBatch_ID=" + getC_InvoiceBatch_ID();
			DB.executeUpdateAndSaveErrorOnFail(sql, get_TrxName());
		}
		return success;
	}	//	afterSave
	
}	//	MInvoiceBatchLine

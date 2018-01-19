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
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailService;
import de.metas.currency.ICurrencyBL;
import de.metas.inout.IInOutBL;
import de.metas.invoice.IMatchInvDAO;

/**
 *	Match Invoice (Receipt<>Invoice) Model.
 *	Accounting:
 *	- Not Invoiced Receipts (relief)
 *	- IPV
 *	
 *  @author Jorg Janke
 *  @version $Id: MMatchInv.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1926113 ] MMatchInv.getNewerDateAcct() should work in trx
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org 
 *			@see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962 
 * @author Bayu Cahya, Sistematika
 * 			<li>BF [ 2240484 ] Re MatchingPO, MMatchPO doesn't contains Invoice info
 * 
 */
public class MMatchInv extends X_M_MatchInv
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3668871839074170205L;

	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_MatchInv_ID id
	 *	@param trxName transaction
	 */
	public MMatchInv (Properties ctx, int M_MatchInv_ID, String trxName)
	{
		super (ctx, M_MatchInv_ID, trxName);
		if (M_MatchInv_ID == 0)
		{
		//	setDateTrx (new Timestamp(System.currentTimeMillis()));
		//	setC_InvoiceLine_ID (0);
		//	setM_InOutLine_ID (0);
		//	setM_Product_ID (0);
			setM_AttributeSetInstance_ID(0);
		//	setQty (Env.ZERO);
			setPosted (false);
			setProcessed (false);
			setProcessing (false);
		}
	}	//	MMatchInv

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MMatchInv (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MMatchInv
	
//	/**
//	 * 	Invoice Line Constructor
//	 *	@param iLine invoice line
//	 *	@param dateTrx optional date
//	 *	@param qty matched quantity
//	 */
//	public MMatchInv (final MInvoiceLine iLine, final Timestamp dateTrx, final BigDecimal qty)
//	{
//		this (iLine.getCtx(), 0, iLine.get_TrxName());
//		
//		Check.assume(iLine.getM_InOutLine_ID() > 0, "Param iLine={} has M_InOutLine_ID > 0", iLine);
//		final I_M_InOutLine inOutLine = iLine.getM_InOutLine();
//		final I_M_InOut inOut = inOutLine.getM_InOut();
//		
//		setClientOrg(iLine);
//		setC_InvoiceLine(iLine);
//		setM_InOutLine(inOutLine);
//		if (dateTrx != null)
//		{
//			setDateTrx (dateTrx);
//		}
//		setM_Product_ID (iLine.getM_Product_ID());
//		setM_AttributeSetInstance_ID(iLine.getM_AttributeSetInstance_ID());
//		setQty (qty);
//		setDocumentNo(inOut.getDocumentNo());
//		setIsSOTrx(inOut.isSOTrx()); // setting the new flag we need since we now use MatchInv with purchase *and* sales invoices.
//		setProcessed(true);		//	auto
//	}	//	MMatchInv

	
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (final boolean newRecord)
	{
		//	Set Trx Date
		if (getDateTrx() == null)
		{
			setDateTrx (SystemTime.asDayTimestamp());
		}
		
		//	Set Acct Date
		if (getDateAcct() == null)
		{
			Timestamp dateAcct = getNewerDateAcct();
			if (dateAcct == null)
				dateAcct = getDateTrx();
			setDateAcct (dateAcct);
		}
		
		if (getM_AttributeSetInstance_ID() <= 0 && getM_InOutLine_ID() > 0)
		{
			final I_M_InOutLine iol = getM_InOutLine();
			setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
		}
		
		return true;
	}	//	beforeSave
	
	/**
	 * 	After Save.
	 * 	Set Order Qty Delivered/Invoiced 
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		// shall not happen (legacy)
		if (!success)
		{
			return false;
		}
		
		if (newRecord)
		{				
			// Elaine 2008/6/20	
			createMatchInvCostDetail();
		}
		
		return success;
	}	//	afterSave
	
	/**
	 * 	Get the later Date Acct from invoice or shipment
	 *	@return date or null
	 */
	private Timestamp getNewerDateAcct()
	{
		String sql = "SELECT i.DateAcct "
			+ "FROM C_InvoiceLine il"
			+ " INNER JOIN C_Invoice i ON (i.C_Invoice_ID=il.C_Invoice_ID) "
			+ "WHERE C_InvoiceLine_ID=?";
		Timestamp invoiceDate = DB.getSQLValueTS(get_TrxName(), sql, getC_InvoiceLine_ID());
		//
		sql = "SELECT io.DateAcct "
			+ "FROM M_InOutLine iol"
			+ " INNER JOIN M_InOut io ON (io.M_InOut_ID=iol.M_InOut_ID) "
			+ "WHERE iol.M_InOutLine_ID=?";
		Timestamp shipDate = DB.getSQLValueTS(get_TrxName(), sql, getM_InOutLine_ID());
		//
		if (invoiceDate == null)
			return shipDate;
		if (shipDate == null)
			return invoiceDate;
		if (invoiceDate.after(shipDate))
			return invoiceDate;
		return shipDate;
	}	//	getNewerDateAcct
	
	
	/**
	 * 	Before Delete
	 *	@return true if acct was deleted
	 */
	@Override
	protected boolean beforeDelete ()
	{
		if (isPosted())
		{
			MPeriod.testPeriodOpen(getCtx(), getDateAcct(), X_C_DocType.DOCBASETYPE_MatchInvoice, getAD_Org_ID());
			setPosted(false);
			Services.get(IFactAcctDAO.class).deleteForDocumentModel(this);
		}
		return true;
	}	//	beforeDelete

	
	/**
	 * 	After Delete
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterDelete (boolean success)
	{
		if (success)
		{
			// AZ Goodwill
			deleteMatchInvCostDetail();
			// end AZ
			
			//	Get Order and decrease invoices
			final I_C_InvoiceLine iLine = getC_InvoiceLine();
			int C_OrderLine_ID = iLine.getC_OrderLine_ID();
			if (C_OrderLine_ID <= 0)
			{
				final I_M_InOutLine ioLine = getM_InOutLine();
				C_OrderLine_ID = ioLine.getC_OrderLine_ID();
			}
			//	No Order Found
			if (C_OrderLine_ID <= 0)
			{
				return success;
			}

			//	Find MatchPO
			final MMatchPO[] mPO = MMatchPO.get(getCtx(), C_OrderLine_ID, getC_InvoiceLine_ID(), get_TrxName());
			for (int i = 0; i < mPO.length; i++)
			{
				if (mPO[i].getM_InOutLine_ID() <= 0)
				{
					mPO[i].delete(true);
				}
				else
				{
					mPO[i].setC_InvoiceLine(null);
					mPO[i].saveEx();
				}
			}
		}
		return success;
	}	//	afterDelete
	

	// Elaine 2008/6/20	
	private void createMatchInvCostDetail()
	{
		if(isSOTrx())
		{
			return; // we extend the use of matchInv to also keep track of the SoTrx side. However, currently we don't need the accounting of that side to work
		}

		final IMatchInvDAO matchInvDAO = Services.get(IMatchInvDAO.class);
		final IInOutBL inOutBL = Services.get(IInOutBL.class);

		final I_C_InvoiceLine invoiceLine = getC_InvoiceLine();
		final BigDecimal qtyInvoiced = invoiceLine.getQtyInvoiced();
		
		// Get Account Schemas to create MCostDetail
		for(final MAcctSchema as : MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID()))
		{
			if (as.isSkipOrg(getAD_Org_ID()))
			{
				continue;
			}
			
			BigDecimal LineNetAmt = invoiceLine.getLineNetAmt();
			BigDecimal multiplier;
			if(qtyInvoiced.signum() != 0) // task 08337: guard against division by zero
			{
				multiplier= getQty()
					.divide(qtyInvoiced, 12, BigDecimal.ROUND_HALF_UP)
					.abs();
			}
			else
			{
				multiplier = BigDecimal.ZERO;
			}
			
			if (multiplier.compareTo(Env.ONE) != 0)
			{
				LineNetAmt = LineNetAmt.multiply(multiplier);
			}
			// Source from Doc_MatchInv.createFacts(MAcctSchema)
			//	Cost Detail Record - data from Expense/IncClearing (CR) record
			// MZ Goodwill
			// Create Cost Detail Matched Invoice using Total Amount and Total Qty based on InvoiceLine
			final List<I_M_MatchInv> matchInvs = matchInvDAO.retrieveForInvoiceLine(invoiceLine);
			BigDecimal tQty = Env.ZERO;
			BigDecimal tAmt = Env.ZERO;
			for (final I_M_MatchInv matchInv : matchInvs)
			{
				if (matchInv.isPosted() && matchInv.getM_MatchInv_ID() != getM_MatchInv_ID())
				{
					tQty = tQty.add(matchInv.getQty());
					final BigDecimal multiplierOfCurrentMatchInv = matchInv.getQty()
						.divide(qtyInvoiced, 12, BigDecimal.ROUND_HALF_UP)
						.abs();
					tAmt = tAmt.add(invoiceLine.getLineNetAmt().multiply(multiplierOfCurrentMatchInv));
				}
			}
			tAmt = tAmt.add(LineNetAmt); //Invoice Price
			
			// 	Different currency
			I_C_Invoice invoice = invoiceLine.getC_Invoice();
			if (as.getC_Currency_ID() != invoice.getC_Currency_ID())
			{
				tAmt = Services.get(ICurrencyBL.class).convert(getCtx(), tAmt, 
					invoice.getC_Currency_ID(), as.getC_Currency_ID(),
					invoice.getDateAcct(), invoice.getC_ConversionType_ID(),
					invoice.getAD_Client_ID(), invoice.getAD_Org_ID());
				if (tAmt == null)
				{
					throw new AdempiereException("AP Invoice not convertible - " + as.getName());
				}
			}			
			
			// set Qty to negative value when MovementType is Vendor Returns
			final I_M_InOutLine receiptLine = getM_InOutLine();
			final I_M_InOut receipt = receiptLine.getM_InOut();
			if (inOutBL.isReturnMovementType(receipt.getMovementType()))
			{
				tQty = tQty.add(getQty().negate()); //	Qty is set to negative value
			}
			else
			{
				tQty = tQty.add(getQty());
			}
			// Set Total Amount and Total Quantity from Matched Invoice 
			Services.get(ICostDetailService.class)
					.createCostDetail(CostDetailCreateRequest.builder()
							.acctSchemaId(as.getC_AcctSchema_ID())
							.orgId(getAD_Org_ID())
							.productId(getM_Product_ID())
							.attributeSetInstanceId(getM_AttributeSetInstance_ID())
							.documentRef(CostingDocumentRef.ofInvoiceLineId(invoiceLine.getC_InvoiceLine_ID()))
							.costElementId(0)
							.amt(tAmt)
							.qty(tQty)
							.description(getDescription())
							.build());
			// end MZ
		}
	}
	
	//
	//AZ Goodwill
	private void deleteMatchInvCostDetail()
	{
		if(isSOTrx())
		{
			return; // task 08529: we extend the use of matchInv to also keep track of the SoTrx side. However, currently we don't need the accounting of that side to work
		}

		final ICostDetailService costDetailService = Services.get(ICostDetailService.class);
		final IInOutBL inOutBL = Services.get(IInOutBL.class);
		
		final I_M_InOut receipt = getM_InOutLine().getM_InOut();
		BigDecimal qty = getQty();
		if (inOutBL.isReturnMovementType(receipt.getMovementType()))
		{
			qty = qty.negate();
		}


		// Get Account Schemas to delete MCostDetail
		for(final MAcctSchema as : MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID()))
		{
			if (as.isSkipOrg(getAD_Org_ID()))
			{
				continue;
			}
			
			final CostDetailQuery costDetailQuery = CostDetailQuery.builder()
					.acctSchemaId(as.getC_AcctSchema_ID())
					.documentRef(CostingDocumentRef.ofInvoiceLineId(getC_InvoiceLine_ID()))
					.attributeSetInstanceId(getM_AttributeSetInstance_ID())
					.build();
			costDetailService.reversePartialQty(costDetailQuery, qty);
		}
	}
}	//	MMatchInv

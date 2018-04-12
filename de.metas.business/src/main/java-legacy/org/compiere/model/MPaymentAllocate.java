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
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * 	Payment Allocate Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MPaymentAllocate.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MPaymentAllocate extends X_C_PaymentAllocate
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2894385378672375131L;

	/**
	 * 	Get active Payment Allocation of Payment
	 *	@param parent payment
	 *	@return array of allocations
	 */
	public static MPaymentAllocate[] get (I_C_Payment parent)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(parent);
		final String trxName = InterfaceWrapperHelper.getTrxName(parent);
		
		final String whereClause = "C_Payment_ID=? AND IsActive=?";
		Query query = MTable.get(ctx, Table_ID).createQuery(whereClause, trxName);
		query.setParameters(new Object[]{parent.getC_Payment_ID(), "Y"});
		List<MPaymentAllocate> list = query.list(MPaymentAllocate.class);
		return list.toArray(new MPaymentAllocate[list.size()]);
	}	//	get
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_PaymentAllocate_ID id
	 *	@param trxName trx
	 */
	public MPaymentAllocate (Properties ctx, int C_PaymentAllocate_ID, String trxName)
	{
		super (ctx, C_PaymentAllocate_ID, trxName);
		if (C_PaymentAllocate_ID == 0)
		{
		//	setC_Payment_ID (0);	//	Parent
		//	setC_Invoice_ID (0);
			setAmount (Env.ZERO);
			setDiscountAmt (Env.ZERO);
			setOverUnderAmt (Env.ZERO);
			setWriteOffAmt (Env.ZERO);
			setInvoiceAmt(Env.ZERO);
		}	
	}	//	MPaymentAllocate

	/**	The Invoice				*/
	private MInvoice	m_invoice = null;
	
	/**
	 * 	Load Cosntructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MPaymentAllocate (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MPaymentAllocate
	
	/**
	 * 	Set C_Invoice_ID
	 *	@param C_Invoice_ID id
	 */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		super.setC_Invoice_ID (C_Invoice_ID);
		m_invoice = null;
	}	//	setC_Invoice_ID
	
	/**
	 * 	Get Invoice
	 *	@return invoice
	 */
	public MInvoice getInvoice()
	{
		if (m_invoice == null && getC_Invoice_ID() != 0)
			m_invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
		return m_invoice;
	}	//	getInvoice
	
	/**
	 * 	Get BPartner of Invoice
	 *	@return bp
	 */
	public int getC_BPartner_ID()
	{
		if (m_invoice == null)
			getInvoice();
		if (m_invoice == null)
			return 0;
		return m_invoice.getC_BPartner_ID();
	}	//	getC_BPartner_ID
	
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		MPayment payment = new MPayment (getCtx(), getC_Payment_ID(), get_TrxName());
		if ((newRecord || is_ValueChanged("C_Invoice_ID"))
			&& (payment.getC_Charge_ID() != 0 
				|| payment.getC_Invoice_ID() != 0 
				|| payment.getC_Order_ID() != 0))
		{
			throw new AdempiereException("@PaymentIsAllocated@");
		}
		
		BigDecimal check = getAmount()
			.add(getDiscountAmt())
			.add(getWriteOffAmt())
			.add(getOverUnderAmt());
		if (check.compareTo(getInvoiceAmt()) != 0)
		{
			throw new AdempiereException("@InvoiceAmt@(" + getInvoiceAmt() + ") <> @Totals@(" + check + ")");
		}
		
		//	Org
		if (newRecord || is_ValueChanged("C_Invoice_ID"))
		{
			getInvoice();
			if (m_invoice != null)
				setAD_Org_ID(m_invoice.getAD_Org_ID());
		}
		
		return true;
	}	//	beforeSave

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		//metas start: cg task us025b
		if (success)
		{
			updateAmounts();
		}
		return success;
		//metas end: cg task us025b
	}

	@Override
	protected boolean afterDelete(boolean success)
	{
		//metas start: cg task us025b
		if (success)
		{
			updateAmounts();
		}
		return success;
		//metas end: cg task us025b
	}
	
	/**
	 * metas cg  task us025b
	 * method for updating amounts in payment 
	 */
	private void updateAmounts()
	{
		String updateSQL = "UPDATE C_Payment "
				+ " set PayAmt = (SELECT SUM(Amount) from C_PaymentAllocate WHERE C_Payment_ID=?), "
				+ " DiscountAmt = (SELECT SUM(DiscountAmt) from C_PaymentAllocate WHERE C_Payment_ID=?), "
				+ " WriteOffAmt = (SELECT SUM(WriteOffAmt) from C_PaymentAllocate WHERE C_Payment_ID=?), "
				+ " OverUnderAmt = (SELECT SUM(OverUnderAmt) from C_PaymentAllocate WHERE C_Payment_ID=?), "
				+ " IsOverUnderPayment = (SELECT CASE WHEN sum(OverUnderAmt)!=0 THEN 'Y' ELSE 'N' END FROM C_PaymentAllocate WHERE C_Payment_ID=?) "
				+ " WHERE C_Payment_ID=?";
		DB.executeUpdate(updateSQL, new Object[] { getC_Payment_ID(), getC_Payment_ID(), getC_Payment_ID(),
				getC_Payment_ID(), getC_Payment_ID(), getC_Payment_ID() },
				false, get_TrxName());
	}
	
}	//	MPaymentAllocate

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

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;


/**
 *	Allocation Line Model
 *
 *  @author Jorg Janke
 *  @version $Id: MAllocationLine.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MAllocationLine extends X_C_AllocationLine
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5532305715886380749L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_AllocationLine_ID id
	 *	@param trxName name
	 */
	public MAllocationLine (Properties ctx, int C_AllocationLine_ID, String trxName)
	{
		super (ctx, C_AllocationLine_ID, trxName);
		if (C_AllocationLine_ID == 0)
		{
		//	setC_AllocationHdr_ID (0);
			setAmount (ZERO);
			setDiscountAmt (ZERO);
			setWriteOffAmt (ZERO);
			setOverUnderAmt(ZERO);
		}
	}	//	MAllocationLine

	/**
	 * 	Load Constructor
	 *	@param ctx ctx
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAllocationLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAllocationLine

	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 */
	public MAllocationLine (MAllocationHdr parent)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setC_AllocationHdr_ID(parent.getC_AllocationHdr_ID());
		m_parent = parent;
		set_TrxName(parent.get_TrxName());
	}	//	MAllocationLine

	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 *	@param Amount amount
	 *	@param DiscountAmt optional discount
	 *	@param WriteOffAmt optional write off
	 *	@param OverUnderAmt over/underpayment
	 */
	public MAllocationLine (MAllocationHdr parent, BigDecimal Amount,
		BigDecimal DiscountAmt, BigDecimal WriteOffAmt, BigDecimal OverUnderAmt)
	{
		this (parent);
		setAmount (Amount);
		setDiscountAmt (DiscountAmt == null ? ZERO : DiscountAmt);
		setWriteOffAmt (WriteOffAmt == null ? ZERO : WriteOffAmt);
		setOverUnderAmt (OverUnderAmt == null ? ZERO : OverUnderAmt);
	}	//	MAllocationLine

	/**	Invoice info			*/
	private MInvoice		m_invoice = null;
	/** Allocation Header		*/
	private MAllocationHdr	m_parent = null;

	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public MAllocationHdr getParent()
	{
		if (m_parent == null)
		{
			m_parent = new MAllocationHdr (getCtx(), getC_AllocationHdr_ID(), get_TrxName());
		}
		return m_parent;
	}	//	getParent

	/**
	 * 	Set Parent
	 *	@param parent parent
	 */
	protected void setParent (MAllocationHdr parent)
	{
		m_parent = parent;
	}	//	setParent

	/**
	 * 	Get Parent Trx Date
	 *	@return date trx
	 */
	@Override
	public Timestamp getDateTrx ()
	{
		return getParent().getDateTrx ();
	}	//	getDateTrx

	/**
	 * 	Set Document Info
	 *	@param C_BPartner_ID partner
	 *	@param C_Order_ID order
	 *	@param C_Invoice_ID invoice
	 */
	public void setDocInfo (int C_BPartner_ID, int C_Order_ID, int C_Invoice_ID)
	{
		setC_BPartner_ID(C_BPartner_ID);
		setC_Invoice_ID(C_Invoice_ID);
	}	//	setDocInfo

	/**
	 * 	Set Payment Info
	 *	@param C_Payment_ID payment
	 *	@param C_CashLine_ID cash line
	 */
	public void setPaymentInfo (int C_Payment_ID, int C_CashLine_ID)
	{
		if (C_Payment_ID != 0)
		{
			setC_Payment_ID(C_Payment_ID);
		}
		if (C_CashLine_ID != 0)
		{
			setC_CashLine_ID(C_CashLine_ID);
		}
	}	//	setPaymentInfo

	/**
	 * 	Get Invoice
	 *	@return invoice or null
	 */
	public MInvoice getInvoice()
	{
		if (m_invoice == null && getC_Invoice_ID() != 0)
		{
			m_invoice = new MInvoice (getCtx(), getC_Invoice_ID(), get_TrxName());
		}
		return m_invoice;
	}	//	getInvoice


	/**************************************************************************
	 * 	Before Save
	 *	@param newRecord
	 *	@return save
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord && getParent().isComplete())
		{
			throw new AdempiereException("@ParentComplete@ @C_AllocationLine_ID@");
		}

		// allow editing the BPartner/Invoice fields (see https://github.com/metasfresh/metasfresh/issues/4326)
//		if (!newRecord
//			&& (is_ValueChanged("C_BPartner_ID") || is_ValueChanged("C_Invoice_ID")))
//		{
//			throw new AdempiereException("Cannot Change Business Partner or Invoice");
//		}

		//	Set BPartner/Order from Invoice
		if (getC_BPartner_ID() == 0 && getInvoice() != null)
		{
			setC_BPartner_ID(getInvoice().getC_BPartner_ID());
		}
		//
		return true;
	}	//	beforeSave


	/**
	 * 	Before Delete
	 *	@return true if reversed
	 */
	@Override
	protected boolean beforeDelete ()
	{
		setIsActive(false);
		processIt(true);
		return true;
	}	//	beforeDelete

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MAllocationLine[");
		sb.append(get_ID());
		if (getC_Payment_ID() != 0)
		{
			sb.append(",C_Payment_ID=").append(getC_Payment_ID());
		}
		if (getC_CashLine_ID() != 0)
		{
			sb.append(",C_CashLine_ID=").append(getC_CashLine_ID());
		}
		if (getC_Invoice_ID() != 0)
		{
			sb.append(",C_Invoice_ID=").append(getC_Invoice_ID());
		}
		if (getC_BPartner_ID() != 0)
		{
			sb.append(",C_BPartner_ID=").append(getC_BPartner_ID());
		}
		sb.append(", Amount=").append(getAmount())
			.append(",Discount=").append(getDiscountAmt())
			.append(",WriteOff=").append(getWriteOffAmt())
			.append(",OverUnder=").append(getOverUnderAmt());
		sb.append ("]");
		return sb.toString ();
	}	//	toString

	/**************************************************************************
	 * 	Process Allocation (does not update line).
	 * 	- Update and Link Invoice/Payment/Cash
	 * 	@param reverse if true allocation is reversed
	 *	@return C_BPartner_ID
	 */
	protected int processIt (boolean reverse)
	{
		log.debug("Reverse=" + reverse + " - " + toString());
		int C_Invoice_ID = getC_Invoice_ID();
		MInvoice invoice = getInvoice();
		if (invoice != null
			&& getC_BPartner_ID() != invoice.getC_BPartner_ID())
		{
			setC_BPartner_ID(invoice.getC_BPartner_ID());
		}
		//
		int C_Payment_ID = getC_Payment_ID();
		int C_CashLine_ID = getC_CashLine_ID();

		//	Update Payment
		if (C_Payment_ID != 0)
		{
			MPayment payment = new MPayment (getCtx(), C_Payment_ID, get_TrxName());

			// NOTE: we shall allow having different invoice and payment bpartners! (task 09105)
			// if (getC_BPartner_ID() != payment.getC_BPartner_ID())
			// log.warn("C_BPartner_ID different - Invoice=" + getC_BPartner_ID() + " - Payment=" + payment.getC_BPartner_ID());

			if (reverse)
			{
				if (!payment.isCashTrx())
				{
					payment.setIsAllocated(false);
					payment.save();
				}
			}
			else
			{
				if (payment.testAllocation())
				{
					payment.save();
				}
			}
		}

		//	Payment - Invoice
		if (C_Payment_ID != 0 && invoice != null)
		{
			//	Link to Invoice
			if (reverse)
			{
				invoice.setC_Payment_ID(0);
				log.debug("C_Payment_ID=" + C_Payment_ID
					+ " Unlinked from C_Invoice_ID=" + C_Invoice_ID);
			}
			else if (invoice.isPaid())
			{
				invoice.setC_Payment_ID(C_Payment_ID);
				log.debug("C_Payment_ID=" + C_Payment_ID
					+ " Linked to C_Invoice_ID=" + C_Invoice_ID);
			}

			//	Link to Order
			String update = "UPDATE C_Order o "
				+ "SET C_Payment_ID="
					+ (reverse ? "NULL " : "(SELECT C_Payment_ID FROM C_Invoice WHERE C_Invoice_ID=" + C_Invoice_ID + ") ")
				+ "WHERE EXISTS (SELECT * FROM C_Invoice i "
					+ "WHERE o.C_Order_ID=i.C_Order_ID AND i.C_Invoice_ID=" + C_Invoice_ID + ")";
			if (DB.executeUpdate(update, get_TrxName()) > 0)
			{
				log.debug("C_Payment_ID=" + C_Payment_ID
					+ (reverse ? " UnLinked from" : " Linked to")
					+ " order of C_Invoice_ID=" + C_Invoice_ID);
			}
		}

		//	Cash - Invoice
		if (C_CashLine_ID != 0 && invoice != null)
		{
			//	Link to Invoice
			if (reverse)
			{
				invoice.setC_CashLine_ID(0);
				log.debug("C_CashLine_ID=" + C_CashLine_ID
					+ " Unlinked from C_Invoice_ID=" + C_Invoice_ID);
			}
			else
			{
				invoice.setC_CashLine_ID(C_CashLine_ID);
				log.debug("C_CashLine_ID=" + C_CashLine_ID
					+ " Linked to C_Invoice_ID=" + C_Invoice_ID);
			}

			//	Link to Order
			String update = "UPDATE C_Order o "
				+ "SET C_CashLine_ID="
					+ (reverse ? "NULL " : "(SELECT C_CashLine_ID FROM C_Invoice WHERE C_Invoice_ID=" + C_Invoice_ID + ") ")
				+ "WHERE EXISTS (SELECT * FROM C_Invoice i "
					+ "WHERE o.C_Order_ID=i.C_Order_ID AND i.C_Invoice_ID=" + C_Invoice_ID + ")";
			if (DB.executeUpdate(update, get_TrxName()) > 0)
			{
				log.debug("C_CashLine_ID=" + C_CashLine_ID
					+ (reverse ? " UnLinked from" : " Linked to")
					+ " order of C_Invoice_ID=" + C_Invoice_ID);
			}
		}

		//	Update Balance / Credit used - Counterpart of MInvoice.completeIt
		if (invoice != null && invoice.testAllocation())
		{
			InterfaceWrapperHelper.save(invoice);
		}

		final int result = getC_BPartner_ID();
		return result;
	}	//	processIt

}	//	MAllocationLine

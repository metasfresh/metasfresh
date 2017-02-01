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
import java.sql.SQLException;
import java.sql.Timestamp;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;

/**
 * Callout for Allocate Payments
 * 
 * @author Jorg Janke
 * @version $Id: CalloutPaymentAllocate.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class CalloutPaymentAllocate extends CalloutEngine
{
	/**
	 * Payment_Invoice.
	 * when Invoice selected
	 * - set InvoiceAmt = invoiceOpen
	 * - DiscountAmt = C_Invoice_Discount (ID, DateTrx)
	 * - Amount = invoiceOpen (ID) - Discount
	 * - WriteOffAmt,OverUnderAmt = 0
	 */
	public String invoice(final ICalloutField calloutField)
	{
		final I_C_PaymentAllocate paymentAlloc = calloutField.getModel(I_C_PaymentAllocate.class);
		final int C_Invoice_ID = paymentAlloc.getC_Invoice_ID();
		if (isCalloutActive()		// assuming it is resetting value
				|| C_Invoice_ID <= 0)
		{
			return NO_ERROR;
		}

		// Check Payment
		// final int C_Payment_ID = paymentAlloc.getC_Payment_ID();
		final I_C_Payment payment = paymentAlloc.getC_Payment();
		if (payment.getC_Charge_ID() > 0 || payment.getC_Invoice_ID() > 0
				|| payment.getC_Order_ID() > 0)
		{
			throw new AdempiereException("@PaymentIsAllocated@");
		}

		paymentAlloc.setDiscountAmt(BigDecimal.ZERO);
		paymentAlloc.setWriteOffAmt(BigDecimal.ZERO);
		paymentAlloc.setOverUnderAmt(BigDecimal.ZERO);

		int C_InvoicePaySchedule_ID = 0;
		if (calloutField.getTabInfoContextAsInt("C_Invoice_ID") == C_Invoice_ID
				&& calloutField.getTabInfoContextAsInt("C_InvoicePaySchedule_ID") > 0)
		{
			C_InvoicePaySchedule_ID = calloutField.getTabInfoContextAsInt("C_InvoicePaySchedule_ID");
		}

		// Payment Date
		final Timestamp ts = paymentAlloc.getC_Payment().getDateTrx();
		//
		final String sql = "SELECT C_BPartner_ID,C_Currency_ID,"		// 1..2
				+ " invoiceOpen(C_Invoice_ID, ?),"					// 3 #1
				+ " invoiceDiscount(C_Invoice_ID,?,?), IsSOTrx "	// 4..5 #2/3
				+ "FROM C_Invoice WHERE C_Invoice_ID=?";			// #4
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, C_InvoicePaySchedule_ID);
			pstmt.setTimestamp(2, ts);
			pstmt.setInt(3, C_InvoicePaySchedule_ID);
			pstmt.setInt(4, C_Invoice_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				// mTab.setValue("C_BPartner_ID", new Integer(rs.getInt(1)));
				// int C_Currency_ID = rs.getInt(2); // Set Invoice Currency
				// mTab.setValue("C_Currency_ID", new Integer(C_Currency_ID));
				//
				BigDecimal InvoiceOpen = rs.getBigDecimal(3);		// Set Invoice OPen Amount
				if (InvoiceOpen == null)
				{
					InvoiceOpen = BigDecimal.ZERO;
				}
				BigDecimal DiscountAmt = rs.getBigDecimal(4);		// Set Discount Amt
				if (DiscountAmt == null)
				{
					DiscountAmt = BigDecimal.ZERO;
				}
				paymentAlloc.setInvoiceAmt(InvoiceOpen);
				paymentAlloc.setAmount(InvoiceOpen.subtract(DiscountAmt));
				paymentAlloc.setDiscountAmt(DiscountAmt);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return NO_ERROR;
	}	// invoice

	/**
	 * Payment_Amounts.
	 * Change of:
	 * - IsOverUnderPayment -> set OverUnderAmt to 0
	 * - C_Currency_ID, C_ConvesionRate_ID -> convert all
	 * - PayAmt, DiscountAmt, WriteOffAmt, OverUnderAmt -> PayAmt
	 * make sure that add up to InvoiceOpenAmt
	 */
	public String amounts(final ICalloutField calloutField)
	{
		if (isCalloutActive()) 		// assuming it is resetting value
		{
			return NO_ERROR;
		}

		final I_C_PaymentAllocate paymentAlloc = calloutField.getModel(I_C_PaymentAllocate.class);

		// No Invoice
		final int C_Invoice_ID = paymentAlloc.getC_Invoice_ID();
		if (C_Invoice_ID <= 0)
		{
			return NO_ERROR;
		}

		// Get Info from Tab
		BigDecimal Amount = paymentAlloc.getAmount();
		final BigDecimal DiscountAmt = paymentAlloc.getDiscountAmt();
		BigDecimal WriteOffAmt = paymentAlloc.getWriteOffAmt();
		final BigDecimal OverUnderAmt = paymentAlloc.getOverUnderAmt();
		final BigDecimal InvoiceAmt = paymentAlloc.getInvoiceAmt();

		// Changed Column
		final String colName = calloutField.getColumnName();
		// PayAmt - calculate write off
		if (I_C_PaymentAllocate.COLUMNNAME_Amount.equals(colName))
		{
			WriteOffAmt = InvoiceAmt.subtract(Amount).subtract(DiscountAmt).subtract(OverUnderAmt);
			paymentAlloc.setWriteOffAmt(WriteOffAmt);
		}
		else    // calculate Amount
		{
			Amount = InvoiceAmt.subtract(DiscountAmt).subtract(WriteOffAmt).subtract(OverUnderAmt);
			paymentAlloc.setAmount(Amount);
		}

		return NO_ERROR;
	}	// amounts

}	// CalloutPaymentAllocate

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
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * 	Callout for Allocate Payments
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutPaymentAllocate.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class CalloutPaymentAllocate extends CalloutEngine
{
	/**
	 *  Payment_Invoice.
	 *  when Invoice selected
	 *  - set InvoiceAmt = invoiceOpen
	 *  	- DiscountAmt = C_Invoice_Discount (ID, DateTrx)
	 *   	- Amount = invoiceOpen (ID) - Discount
	 * 		- WriteOffAmt,OverUnderAmt = 0
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public String invoice (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_Invoice_ID = (Integer)value;
		if (isCalloutActive()		//	assuming it is resetting value
			|| C_Invoice_ID == null || C_Invoice_ID.intValue() == 0)
			return "";

		//	Check Payment
		int C_Payment_ID = Env.getContextAsInt(ctx, WindowNo, "C_Payment_ID");
		MPayment payment = new MPayment (ctx, C_Payment_ID, null);
		if (payment.getC_Charge_ID() != 0 || payment.getC_Invoice_ID() != 0 
			|| payment.getC_Order_ID() != 0)
			return Msg.getMsg(ctx, "PaymentIsAllocated");
		
		mTab.setValue("DiscountAmt", Env.ZERO);
		mTab.setValue("WriteOffAmt", Env.ZERO);
		mTab.setValue("OverUnderAmt", Env.ZERO);

		int C_InvoicePaySchedule_ID = 0;
		if (Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_Invoice_ID") == C_Invoice_ID.intValue()
			&& Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID") != 0)
		{
			C_InvoicePaySchedule_ID = Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID");
		}

		//  Payment Date
		Timestamp ts = Env.getContextAsDate(ctx, WindowNo, "DateTrx");
		//
		String sql = "SELECT C_BPartner_ID,C_Currency_ID,"		//	1..2
			+ " invoiceOpen(C_Invoice_ID, ?),"					//	3		#1
			+ " invoiceDiscount(C_Invoice_ID,?,?), IsSOTrx "	//	4..5	#2/3
			+ "FROM C_Invoice WHERE C_Invoice_ID=?";			//			#4
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_InvoicePaySchedule_ID);
			pstmt.setTimestamp(2, ts);
			pstmt.setInt(3, C_InvoicePaySchedule_ID);
			pstmt.setInt(4, C_Invoice_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
			//	mTab.setValue("C_BPartner_ID", new Integer(rs.getInt(1)));
			//	int C_Currency_ID = rs.getInt(2);					//	Set Invoice Currency
			//	mTab.setValue("C_Currency_ID", new Integer(C_Currency_ID));
				//
				BigDecimal InvoiceOpen = rs.getBigDecimal(3);		//	Set Invoice OPen Amount
				if (InvoiceOpen == null)
					InvoiceOpen = Env.ZERO;
				BigDecimal DiscountAmt = rs.getBigDecimal(4);		//	Set Discount Amt
				if (DiscountAmt == null)
					DiscountAmt = Env.ZERO;
				mTab.setValue("InvoiceAmt", InvoiceOpen);
				mTab.setValue("Amount", InvoiceOpen.subtract(DiscountAmt));
				mTab.setValue("DiscountAmt", DiscountAmt);
				//  reset as dependent fields get reset
				Env.setContext(ctx, WindowNo, "C_Invoice_ID", C_Invoice_ID.toString());
				mTab.setValue("C_Invoice_ID", C_Invoice_ID);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}	//	invoice

	/**
	 *  Payment_Amounts.
	 *	Change of:
	 *		- IsOverUnderPayment -> set OverUnderAmt to 0
	 *		- C_Currency_ID, C_ConvesionRate_ID -> convert all
	 *		- PayAmt, DiscountAmt, WriteOffAmt, OverUnderAmt -> PayAmt
	 *			make sure that add up to InvoiceOpenAmt
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @param oldValue Old Value
	 *  @return null or error message
	 */
	public String amounts (Properties ctx, int WindowNo, GridTab mTab, GridField mField, 
		Object value, Object oldValue)
	{
		if (isCalloutActive())		//	assuming it is resetting value
			return "";
		//	No Invoice
		int C_Invoice_ID = Env.getContextAsInt(ctx, WindowNo, "C_Invoice_ID");
		if (C_Invoice_ID == 0)
			return "";
		//	Get Info from Tab
		BigDecimal Amount = (BigDecimal)mTab.getValue("Amount");
		BigDecimal DiscountAmt = (BigDecimal)mTab.getValue("DiscountAmt");
		BigDecimal WriteOffAmt = (BigDecimal)mTab.getValue("WriteOffAmt");
		BigDecimal OverUnderAmt = (BigDecimal)mTab.getValue("OverUnderAmt");
		BigDecimal InvoiceAmt = (BigDecimal)mTab.getValue("InvoiceAmt");
		log.fine("Amt=" + Amount + ", Discount=" + DiscountAmt
			+ ", WriteOff=" + WriteOffAmt + ", OverUnder=" + OverUnderAmt
			+ ", Invoice=" + InvoiceAmt);

		//	Changed Column
		String colName = mField.getColumnName();
		//  PayAmt - calculate write off
		if (colName.equals("Amount"))
		{
			WriteOffAmt = InvoiceAmt.subtract(Amount).subtract(DiscountAmt).subtract(OverUnderAmt);
			mTab.setValue("WriteOffAmt", WriteOffAmt);
		}
		else    //  calculate Amount
		{
			Amount = InvoiceAmt.subtract(DiscountAmt).subtract(WriteOffAmt).subtract(OverUnderAmt);
			mTab.setValue("Amount", Amount);
		}

		return "";
	}	//	amounts

	
}	//	CalloutPaymentAllocate

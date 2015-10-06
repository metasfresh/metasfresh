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

/**
 *	Cash Book Journal Callouts
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutCashJournal.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class CalloutCashJournal extends CalloutEngine
{
	/**
	 *  Cash Journal Line Invoice.
	 *  when Invoice selected
	 *  - set C_Currency, DiscountAnt, Amount, WriteOffAmt
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String invoice (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())		//	assuming it is resetting value
			return "";

		Integer C_Invoice_ID = (Integer)value;
		if (C_Invoice_ID == null || C_Invoice_ID.intValue() == 0)
		{
			mTab.setValue("C_Currency_ID", null);
			return "";
		}
		
		int C_InvoicePaySchedule_ID = 0;
		if (Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_Invoice_ID") == C_Invoice_ID.intValue ()
			&& Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID") != 0)
			C_InvoicePaySchedule_ID = Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID");

		//  Date
		Timestamp ts = Env.getContextAsDate(ctx, WindowNo, "DateAcct");     //  from C_Cash
		if (ts == null)
			ts = new Timestamp(System.currentTimeMillis());
		//
		String sql = "SELECT C_BPartner_ID, C_Currency_ID,"		//	1..2
			+ "invoiceOpen(C_Invoice_ID, ?), IsSOTrx, "			//	3..4
			+ "invoiceDiscount(C_Invoice_ID,?,?) "              //  5
			+ "FROM C_Invoice WHERE C_Invoice_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt (1, C_InvoicePaySchedule_ID);
			pstmt.setTimestamp (2, ts);
			pstmt.setInt (3, C_InvoicePaySchedule_ID);
			pstmt.setInt(4, C_Invoice_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				mTab.setValue("C_Currency_ID", new Integer(rs.getInt(2)));
				BigDecimal PayAmt = rs.getBigDecimal(3);
				BigDecimal DiscountAmt = rs.getBigDecimal(5);
				boolean isSOTrx = "Y".equals(rs.getString(4));
				if (!isSOTrx)
				{
					PayAmt = PayAmt.negate();
					DiscountAmt = DiscountAmt.negate();
				}
				//
				mTab.setValue("Amount", PayAmt.subtract(DiscountAmt));
				mTab.setValue("DiscountAmt", DiscountAmt);
				mTab.setValue("WriteOffAmt", Env.ZERO);
				Env.setContext(ctx, WindowNo, "InvTotalAmt", PayAmt.toString());
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "invoice", e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}	//	CashJournal_Invoice

	
	/**
	 *  Cash Journal Line Invoice Amounts.
	 *  when DiscountAnt, Amount, WriteOffAmt change
	 *  making sure that add up to InvTotalAmt (created by CashJournal_Invoice)
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String amounts (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		//  Needs to be Invoice
		if (isCalloutActive() || !"I".equals(mTab.getValue("CashType")))
			return "";
		//  Check, if InvTotalAmt exists
		String total = Env.getContext(ctx, WindowNo, "InvTotalAmt");
		if (total == null || total.length() == 0)
			return "";
		BigDecimal InvTotalAmt = new BigDecimal(total);

		BigDecimal PayAmt = (BigDecimal)mTab.getValue("Amount");
		BigDecimal DiscountAmt = (BigDecimal)mTab.getValue("DiscountAmt");
		BigDecimal WriteOffAmt = (BigDecimal)mTab.getValue("WriteOffAmt");
		String colName = mField.getColumnName();
		log.fine(colName + " - Invoice=" + InvTotalAmt
			+ " - Amount=" + PayAmt + ", Discount=" + DiscountAmt + ", WriteOff=" + WriteOffAmt);

		//  Amount - calculate write off
		if (colName.equals("Amount"))
		{
			WriteOffAmt = InvTotalAmt.subtract(PayAmt).subtract(DiscountAmt);
			mTab.setValue("WriteOffAmt", WriteOffAmt);
		}
		else    //  calculate PayAmt
		{
			PayAmt = InvTotalAmt.subtract(DiscountAmt).subtract(WriteOffAmt);
			mTab.setValue("Amount", PayAmt);
		}

		return "";
	}	//	amounts

}	//	CalloutCashJournal

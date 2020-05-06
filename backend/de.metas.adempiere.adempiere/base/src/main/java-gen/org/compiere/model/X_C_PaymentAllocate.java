/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_PaymentAllocate
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_PaymentAllocate extends PO implements I_C_PaymentAllocate, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_PaymentAllocate (Properties ctx, int C_PaymentAllocate_ID, String trxName)
    {
      super (ctx, C_PaymentAllocate_ID, trxName);
      /** if (C_PaymentAllocate_ID == 0)
        {
			setAmount (Env.ZERO);
			setC_Invoice_ID (0);
			setC_PaymentAllocate_ID (0);
			setC_Payment_ID (0);
			setDiscountAmt (Env.ZERO);
			setOverUnderAmt (Env.ZERO);
			setWriteOffAmt (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_PaymentAllocate (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_PaymentAllocate[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_AllocationLine getC_AllocationLine() throws RuntimeException
    {
		return (I_C_AllocationLine)MTable.get(getCtx(), I_C_AllocationLine.Table_Name)
			.getPO(getC_AllocationLine_ID(), get_TrxName());	}

	/** Set Allocation Line.
		@param C_AllocationLine_ID 
		Allocation Line
	  */
	public void setC_AllocationLine_ID (int C_AllocationLine_ID)
	{
		if (C_AllocationLine_ID < 1) 
			set_Value (COLUMNNAME_C_AllocationLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_AllocationLine_ID, Integer.valueOf(C_AllocationLine_ID));
	}

	/** Get Allocation Line.
		@return Allocation Line
	  */
	public int getC_AllocationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AllocationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_Invoice_ID()));
    }

	/** Set Allocate Payment.
		@param C_PaymentAllocate_ID 
		Allocate Payment to Invoices
	  */
	public void setC_PaymentAllocate_ID (int C_PaymentAllocate_ID)
	{
		if (C_PaymentAllocate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaymentAllocate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaymentAllocate_ID, Integer.valueOf(C_PaymentAllocate_ID));
	}

	/** Get Allocate Payment.
		@return Allocate Payment to Invoices
	  */
	public int getC_PaymentAllocate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentAllocate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Payment getC_Payment() throws RuntimeException
    {
		return (I_C_Payment)MTable.get(getCtx(), I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

	/** Set Payment.
		@param C_Payment_ID 
		Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Payment.
		@return Payment identifier
	  */
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Amount.
		@param DiscountAmt 
		Calculated amount of discount
	  */
	public void setDiscountAmt (BigDecimal DiscountAmt)
	{
		set_Value (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

	/** Get Discount Amount.
		@return Calculated amount of discount
	  */
	public BigDecimal getDiscountAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Invoice Amt.
		@param InvoiceAmt Invoice Amt	  */
	public void setInvoiceAmt (BigDecimal InvoiceAmt)
	{
		set_Value (COLUMNNAME_InvoiceAmt, InvoiceAmt);
	}

	/** Get Invoice Amt.
		@return Invoice Amt	  */
	public BigDecimal getInvoiceAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InvoiceAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Over/Under Payment.
		@param OverUnderAmt 
		Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	  */
	public void setOverUnderAmt (BigDecimal OverUnderAmt)
	{
		set_Value (COLUMNNAME_OverUnderAmt, OverUnderAmt);
	}

	/** Get Over/Under Payment.
		@return Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	  */
	public BigDecimal getOverUnderAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OverUnderAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Remaining Amt.
		@param RemainingAmt 
		Remaining Amount
	  */
	public void setRemainingAmt (BigDecimal RemainingAmt)
	{
		throw new IllegalArgumentException ("RemainingAmt is virtual column");	}

	/** Get Remaining Amt.
		@return Remaining Amount
	  */
	public BigDecimal getRemainingAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RemainingAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Write-off Amount.
		@param WriteOffAmt 
		Amount to write-off
	  */
	public void setWriteOffAmt (BigDecimal WriteOffAmt)
	{
		set_Value (COLUMNNAME_WriteOffAmt, WriteOffAmt);
	}

	/** Get Write-off Amount.
		@return Amount to write-off
	  */
	public BigDecimal getWriteOffAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WriteOffAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}
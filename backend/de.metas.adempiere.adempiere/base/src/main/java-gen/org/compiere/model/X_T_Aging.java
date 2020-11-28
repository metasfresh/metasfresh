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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for T_Aging
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_T_Aging extends PO implements I_T_Aging, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_T_Aging (Properties ctx, int T_Aging_ID, String trxName)
    {
      super (ctx, T_Aging_ID, trxName);
      /** if (T_Aging_ID == 0)
        {
			setAD_PInstance_ID (0);
			setC_BPartner_ID (0);
			setC_BP_Group_ID (0);
			setC_Currency_ID (0);
			setDue0 (Env.ZERO);
			setDue0_30 (Env.ZERO);
			setDue0_7 (Env.ZERO);
			setDue1_7 (Env.ZERO);
			setDue31_60 (Env.ZERO);
			setDue31_Plus (Env.ZERO);
			setDue61_90 (Env.ZERO);
			setDue61_Plus (Env.ZERO);
			setDue8_30 (Env.ZERO);
			setDue91_Plus (Env.ZERO);
			setDueAmt (Env.ZERO);
			setDueDate (new Timestamp( System.currentTimeMillis() ));
			setInvoicedAmt (Env.ZERO);
			setIsListInvoices (false);
			setIsSOTrx (false);
			setOpenAmt (Env.ZERO);
			setPastDue1_30 (Env.ZERO);
			setPastDue1_7 (Env.ZERO);
			setPastDue31_60 (Env.ZERO);
			setPastDue31_Plus (Env.ZERO);
			setPastDue61_90 (Env.ZERO);
			setPastDue61_Plus (Env.ZERO);
			setPastDue8_30 (Env.ZERO);
			setPastDue91_Plus (Env.ZERO);
			setPastDueAmt (Env.ZERO);
			setStatementDate (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_T_Aging (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_T_Aging[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_PInstance getAD_PInstance() throws RuntimeException
    {
		return (I_AD_PInstance)MTable.get(getCtx(), I_AD_PInstance.Table_Name)
			.getPO(getAD_PInstance_ID(), get_TrxName());	}

	/** Set Process Instance.
		@param AD_PInstance_ID 
		Instance of the process
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Process Instance.
		@return Instance of the process
	  */
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Activity getC_Activity() throws RuntimeException
    {
		return (I_C_Activity)MTable.get(getCtx(), I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BP_Group getC_BP_Group() throws RuntimeException
    {
		return (I_C_BP_Group)MTable.get(getCtx(), I_C_BP_Group.Table_Name)
			.getPO(getC_BP_Group_ID(), get_TrxName());	}

	/** Set Business Partner Group.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Business Partner Group.
		@return Business Partner Group
	  */
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Campaign getC_Campaign() throws RuntimeException
    {
		return (I_C_Campaign)MTable.get(getCtx(), I_C_Campaign.Table_Name)
			.getPO(getC_Campaign_ID(), get_TrxName());	}

	/** Set Campaign.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Campaign.
		@return Marketing Campaign
	  */
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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

	public I_C_InvoicePaySchedule getC_InvoicePaySchedule() throws RuntimeException
    {
		return (I_C_InvoicePaySchedule)MTable.get(getCtx(), I_C_InvoicePaySchedule.Table_Name)
			.getPO(getC_InvoicePaySchedule_ID(), get_TrxName());	}

	/** Set Invoice Payment Schedule.
		@param C_InvoicePaySchedule_ID 
		Invoice Payment Schedule
	  */
	public void setC_InvoicePaySchedule_ID (int C_InvoicePaySchedule_ID)
	{
		if (C_InvoicePaySchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoicePaySchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoicePaySchedule_ID, Integer.valueOf(C_InvoicePaySchedule_ID));
	}

	/** Get Invoice Payment Schedule.
		@return Invoice Payment Schedule
	  */
	public int getC_InvoicePaySchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoicePaySchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Project getC_Project() throws RuntimeException
    {
		return (I_C_Project)MTable.get(getCtx(), I_C_Project.Table_Name)
			.getPO(getC_Project_ID(), get_TrxName());	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (boolean DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, Boolean.valueOf(DateAcct));
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public boolean isDateAcct () 
	{
		Object oo = get_Value(COLUMNNAME_DateAcct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Days due.
		@param DaysDue 
		Number of days due (negative: due in number of days)
	  */
	public void setDaysDue (int DaysDue)
	{
		set_Value (COLUMNNAME_DaysDue, Integer.valueOf(DaysDue));
	}

	/** Get Days due.
		@return Number of days due (negative: due in number of days)
	  */
	public int getDaysDue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DaysDue);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Due Today.
		@param Due0 Due Today	  */
	public void setDue0 (BigDecimal Due0)
	{
		set_Value (COLUMNNAME_Due0, Due0);
	}

	/** Get Due Today.
		@return Due Today	  */
	public BigDecimal getDue0 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Due0);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Due Today-30.
		@param Due0_30 Due Today-30	  */
	public void setDue0_30 (BigDecimal Due0_30)
	{
		set_Value (COLUMNNAME_Due0_30, Due0_30);
	}

	/** Get Due Today-30.
		@return Due Today-30	  */
	public BigDecimal getDue0_30 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Due0_30);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Due Today-7.
		@param Due0_7 Due Today-7	  */
	public void setDue0_7 (BigDecimal Due0_7)
	{
		set_Value (COLUMNNAME_Due0_7, Due0_7);
	}

	/** Get Due Today-7.
		@return Due Today-7	  */
	public BigDecimal getDue0_7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Due0_7);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Due 1-7.
		@param Due1_7 Due 1-7	  */
	public void setDue1_7 (BigDecimal Due1_7)
	{
		set_Value (COLUMNNAME_Due1_7, Due1_7);
	}

	/** Get Due 1-7.
		@return Due 1-7	  */
	public BigDecimal getDue1_7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Due1_7);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Due 31-60.
		@param Due31_60 Due 31-60	  */
	public void setDue31_60 (BigDecimal Due31_60)
	{
		set_Value (COLUMNNAME_Due31_60, Due31_60);
	}

	/** Get Due 31-60.
		@return Due 31-60	  */
	public BigDecimal getDue31_60 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Due31_60);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Due > 31.
		@param Due31_Plus Due > 31	  */
	public void setDue31_Plus (BigDecimal Due31_Plus)
	{
		set_Value (COLUMNNAME_Due31_Plus, Due31_Plus);
	}

	/** Get Due > 31.
		@return Due > 31	  */
	public BigDecimal getDue31_Plus () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Due31_Plus);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Due 61-90.
		@param Due61_90 Due 61-90	  */
	public void setDue61_90 (BigDecimal Due61_90)
	{
		set_Value (COLUMNNAME_Due61_90, Due61_90);
	}

	/** Get Due 61-90.
		@return Due 61-90	  */
	public BigDecimal getDue61_90 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Due61_90);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Due > 61.
		@param Due61_Plus Due > 61	  */
	public void setDue61_Plus (BigDecimal Due61_Plus)
	{
		set_Value (COLUMNNAME_Due61_Plus, Due61_Plus);
	}

	/** Get Due > 61.
		@return Due > 61	  */
	public BigDecimal getDue61_Plus () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Due61_Plus);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Due 8-30.
		@param Due8_30 Due 8-30	  */
	public void setDue8_30 (BigDecimal Due8_30)
	{
		set_Value (COLUMNNAME_Due8_30, Due8_30);
	}

	/** Get Due 8-30.
		@return Due 8-30	  */
	public BigDecimal getDue8_30 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Due8_30);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Due > 91.
		@param Due91_Plus Due > 91	  */
	public void setDue91_Plus (BigDecimal Due91_Plus)
	{
		set_Value (COLUMNNAME_Due91_Plus, Due91_Plus);
	}

	/** Get Due > 91.
		@return Due > 91	  */
	public BigDecimal getDue91_Plus () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Due91_Plus);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Amount due.
		@param DueAmt 
		Amount of the payment due
	  */
	public void setDueAmt (BigDecimal DueAmt)
	{
		set_Value (COLUMNNAME_DueAmt, DueAmt);
	}

	/** Get Amount due.
		@return Amount of the payment due
	  */
	public BigDecimal getDueAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DueAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set Invoiced Amount.
		@param InvoicedAmt 
		The amount invoiced
	  */
	public void setInvoicedAmt (BigDecimal InvoicedAmt)
	{
		set_Value (COLUMNNAME_InvoicedAmt, InvoicedAmt);
	}

	/** Get Invoiced Amount.
		@return The amount invoiced
	  */
	public BigDecimal getInvoicedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InvoicedAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set List Invoices.
		@param IsListInvoices 
		Include List of Invoices
	  */
	public void setIsListInvoices (boolean IsListInvoices)
	{
		set_Value (COLUMNNAME_IsListInvoices, Boolean.valueOf(IsListInvoices));
	}

	/** Get List Invoices.
		@return Include List of Invoices
	  */
	public boolean isListInvoices () 
	{
		Object oo = get_Value(COLUMNNAME_IsListInvoices);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Open Amount.
		@param OpenAmt 
		Open item amount
	  */
	public void setOpenAmt (BigDecimal OpenAmt)
	{
		set_Value (COLUMNNAME_OpenAmt, OpenAmt);
	}

	/** Get Open Amount.
		@return Open item amount
	  */
	public BigDecimal getOpenAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OpenAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Past Due 1-30.
		@param PastDue1_30 Past Due 1-30	  */
	public void setPastDue1_30 (BigDecimal PastDue1_30)
	{
		set_Value (COLUMNNAME_PastDue1_30, PastDue1_30);
	}

	/** Get Past Due 1-30.
		@return Past Due 1-30	  */
	public BigDecimal getPastDue1_30 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PastDue1_30);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Past Due 1-7.
		@param PastDue1_7 Past Due 1-7	  */
	public void setPastDue1_7 (BigDecimal PastDue1_7)
	{
		set_Value (COLUMNNAME_PastDue1_7, PastDue1_7);
	}

	/** Get Past Due 1-7.
		@return Past Due 1-7	  */
	public BigDecimal getPastDue1_7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PastDue1_7);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Past Due 31-60.
		@param PastDue31_60 Past Due 31-60	  */
	public void setPastDue31_60 (BigDecimal PastDue31_60)
	{
		set_Value (COLUMNNAME_PastDue31_60, PastDue31_60);
	}

	/** Get Past Due 31-60.
		@return Past Due 31-60	  */
	public BigDecimal getPastDue31_60 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PastDue31_60);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Past Due > 31.
		@param PastDue31_Plus Past Due > 31	  */
	public void setPastDue31_Plus (BigDecimal PastDue31_Plus)
	{
		set_Value (COLUMNNAME_PastDue31_Plus, PastDue31_Plus);
	}

	/** Get Past Due > 31.
		@return Past Due > 31	  */
	public BigDecimal getPastDue31_Plus () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PastDue31_Plus);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Past Due 61-90.
		@param PastDue61_90 Past Due 61-90	  */
	public void setPastDue61_90 (BigDecimal PastDue61_90)
	{
		set_Value (COLUMNNAME_PastDue61_90, PastDue61_90);
	}

	/** Get Past Due 61-90.
		@return Past Due 61-90	  */
	public BigDecimal getPastDue61_90 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PastDue61_90);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Past Due > 61.
		@param PastDue61_Plus Past Due > 61	  */
	public void setPastDue61_Plus (BigDecimal PastDue61_Plus)
	{
		set_Value (COLUMNNAME_PastDue61_Plus, PastDue61_Plus);
	}

	/** Get Past Due > 61.
		@return Past Due > 61	  */
	public BigDecimal getPastDue61_Plus () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PastDue61_Plus);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Past Due 8-30.
		@param PastDue8_30 Past Due 8-30	  */
	public void setPastDue8_30 (BigDecimal PastDue8_30)
	{
		set_Value (COLUMNNAME_PastDue8_30, PastDue8_30);
	}

	/** Get Past Due 8-30.
		@return Past Due 8-30	  */
	public BigDecimal getPastDue8_30 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PastDue8_30);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Past Due > 91.
		@param PastDue91_Plus Past Due > 91	  */
	public void setPastDue91_Plus (BigDecimal PastDue91_Plus)
	{
		set_Value (COLUMNNAME_PastDue91_Plus, PastDue91_Plus);
	}

	/** Get Past Due > 91.
		@return Past Due > 91	  */
	public BigDecimal getPastDue91_Plus () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PastDue91_Plus);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Past Due.
		@param PastDueAmt Past Due	  */
	public void setPastDueAmt (BigDecimal PastDueAmt)
	{
		set_Value (COLUMNNAME_PastDueAmt, PastDueAmt);
	}

	/** Get Past Due.
		@return Past Due	  */
	public BigDecimal getPastDueAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PastDueAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Statement date.
		@param StatementDate 
		Date of the statement
	  */
	public void setStatementDate (Timestamp StatementDate)
	{
		set_Value (COLUMNNAME_StatementDate, StatementDate);
	}

	/** Get Statement date.
		@return Date of the statement
	  */
	public Timestamp getStatementDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StatementDate);
	}
}
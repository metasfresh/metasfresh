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

/** Generated Model for C_InvoiceSchedule
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_InvoiceSchedule extends PO implements I_C_InvoiceSchedule, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_InvoiceSchedule (Properties ctx, int C_InvoiceSchedule_ID, String trxName)
    {
      super (ctx, C_InvoiceSchedule_ID, trxName);
      /** if (C_InvoiceSchedule_ID == 0)
        {
			setAmt (Env.ZERO);
			setC_InvoiceSchedule_ID (0);
			setInvoiceDay (0);
// 1
			setInvoiceFrequency (null);
			setInvoiceWeekDay (null);
			setIsAmount (false);
			setIsDefault (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_InvoiceSchedule (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_InvoiceSchedule[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Invoice Schedule.
		@param C_InvoiceSchedule_ID 
		Schedule for generating Invoices
	  */
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID)
	{
		if (C_InvoiceSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceSchedule_ID, Integer.valueOf(C_InvoiceSchedule_ID));
	}

	/** Get Invoice Schedule.
		@return Schedule for generating Invoices
	  */
	public int getC_InvoiceSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Invoice on even weeks.
		@param EvenInvoiceWeek 
		Send invoices on even weeks
	  */
	public void setEvenInvoiceWeek (boolean EvenInvoiceWeek)
	{
		set_Value (COLUMNNAME_EvenInvoiceWeek, Boolean.valueOf(EvenInvoiceWeek));
	}

	/** Get Invoice on even weeks.
		@return Send invoices on even weeks
	  */
	public boolean isEvenInvoiceWeek () 
	{
		Object oo = get_Value(COLUMNNAME_EvenInvoiceWeek);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Invoice Day.
		@param InvoiceDay 
		Day of Invoice Generation
	  */
	public void setInvoiceDay (int InvoiceDay)
	{
		set_Value (COLUMNNAME_InvoiceDay, Integer.valueOf(InvoiceDay));
	}

	/** Get Invoice Day.
		@return Day of Invoice Generation
	  */
	public int getInvoiceDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InvoiceDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Invoice day cut-off.
		@param InvoiceDayCutoff 
		Last day for including shipments
	  */
	public void setInvoiceDayCutoff (int InvoiceDayCutoff)
	{
		set_Value (COLUMNNAME_InvoiceDayCutoff, Integer.valueOf(InvoiceDayCutoff));
	}

	/** Get Invoice day cut-off.
		@return Last day for including shipments
	  */
	public int getInvoiceDayCutoff () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InvoiceDayCutoff);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** InvoiceFrequency AD_Reference_ID=168 */
	public static final int INVOICEFREQUENCY_AD_Reference_ID=168;
	/** Daily = D */
	public static final String INVOICEFREQUENCY_Daily = "D";
	/** Weekly = W */
	public static final String INVOICEFREQUENCY_Weekly = "W";
	/** Monthly = M */
	public static final String INVOICEFREQUENCY_Monthly = "M";
	/** Twice Monthly = T */
	public static final String INVOICEFREQUENCY_TwiceMonthly = "T";
	/** Set Invoice Frequency.
		@param InvoiceFrequency 
		How often invoices will be generated
	  */
	public void setInvoiceFrequency (String InvoiceFrequency)
	{

		set_Value (COLUMNNAME_InvoiceFrequency, InvoiceFrequency);
	}

	/** Get Invoice Frequency.
		@return How often invoices will be generated
	  */
	public String getInvoiceFrequency () 
	{
		return (String)get_Value(COLUMNNAME_InvoiceFrequency);
	}

	/** InvoiceWeekDay AD_Reference_ID=167 */
	public static final int INVOICEWEEKDAY_AD_Reference_ID=167;
	/** Sunday = 7 */
	public static final String INVOICEWEEKDAY_Sunday = "7";
	/** Monday = 1 */
	public static final String INVOICEWEEKDAY_Monday = "1";
	/** Tuesday = 2 */
	public static final String INVOICEWEEKDAY_Tuesday = "2";
	/** Wednesday = 3 */
	public static final String INVOICEWEEKDAY_Wednesday = "3";
	/** Thursday = 4 */
	public static final String INVOICEWEEKDAY_Thursday = "4";
	/** Friday = 5 */
	public static final String INVOICEWEEKDAY_Friday = "5";
	/** Saturday = 6 */
	public static final String INVOICEWEEKDAY_Saturday = "6";
	/** Set Invoice Week Day.
		@param InvoiceWeekDay 
		Day to generate invoices
	  */
	public void setInvoiceWeekDay (String InvoiceWeekDay)
	{

		set_Value (COLUMNNAME_InvoiceWeekDay, InvoiceWeekDay);
	}

	/** Get Invoice Week Day.
		@return Day to generate invoices
	  */
	public String getInvoiceWeekDay () 
	{
		return (String)get_Value(COLUMNNAME_InvoiceWeekDay);
	}

	/** InvoiceWeekDayCutoff AD_Reference_ID=167 */
	public static final int INVOICEWEEKDAYCUTOFF_AD_Reference_ID=167;
	/** Sunday = 7 */
	public static final String INVOICEWEEKDAYCUTOFF_Sunday = "7";
	/** Monday = 1 */
	public static final String INVOICEWEEKDAYCUTOFF_Monday = "1";
	/** Tuesday = 2 */
	public static final String INVOICEWEEKDAYCUTOFF_Tuesday = "2";
	/** Wednesday = 3 */
	public static final String INVOICEWEEKDAYCUTOFF_Wednesday = "3";
	/** Thursday = 4 */
	public static final String INVOICEWEEKDAYCUTOFF_Thursday = "4";
	/** Friday = 5 */
	public static final String INVOICEWEEKDAYCUTOFF_Friday = "5";
	/** Saturday = 6 */
	public static final String INVOICEWEEKDAYCUTOFF_Saturday = "6";
	/** Set Invoice weekday cutoff.
		@param InvoiceWeekDayCutoff 
		Last day in the week for shipments to be included
	  */
	public void setInvoiceWeekDayCutoff (String InvoiceWeekDayCutoff)
	{

		set_Value (COLUMNNAME_InvoiceWeekDayCutoff, InvoiceWeekDayCutoff);
	}

	/** Get Invoice weekday cutoff.
		@return Last day in the week for shipments to be included
	  */
	public String getInvoiceWeekDayCutoff () 
	{
		return (String)get_Value(COLUMNNAME_InvoiceWeekDayCutoff);
	}

	/** Set Amount Limit.
		@param IsAmount 
		Send invoices only if the amount exceeds the limit
	  */
	public void setIsAmount (boolean IsAmount)
	{
		set_Value (COLUMNNAME_IsAmount, Boolean.valueOf(IsAmount));
	}

	/** Get Amount Limit.
		@return Send invoices only if the amount exceeds the limit
	  */
	public boolean isAmount () 
	{
		Object oo = get_Value(COLUMNNAME_IsAmount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }
}
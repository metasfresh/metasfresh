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

/** Generated Model for C_PaySchedule
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_PaySchedule extends PO implements I_C_PaySchedule, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_PaySchedule (Properties ctx, int C_PaySchedule_ID, String trxName)
    {
      super (ctx, C_PaySchedule_ID, trxName);
      /** if (C_PaySchedule_ID == 0)
        {
			setC_PaymentTerm_ID (0);
			setC_PaySchedule_ID (0);
			setDiscount (Env.ZERO);
			setDiscountDays (0);
			setGraceDays (0);
			setIsValid (false);
			setNetDays (0);
			setPercentage (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_PaySchedule (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_PaySchedule[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
    {
		return (I_C_PaymentTerm)MTable.get(getCtx(), I_C_PaymentTerm.Table_Name)
			.getPO(getC_PaymentTerm_ID(), get_TrxName());	}

	/** Set Payment Term.
		@param C_PaymentTerm_ID 
		The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Payment Term.
		@return The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_PaymentTerm_ID()));
    }

	/** Set Payment Schedule.
		@param C_PaySchedule_ID 
		Payment Schedule Template
	  */
	public void setC_PaySchedule_ID (int C_PaySchedule_ID)
	{
		if (C_PaySchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaySchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaySchedule_ID, Integer.valueOf(C_PaySchedule_ID));
	}

	/** Get Payment Schedule.
		@return Payment Schedule Template
	  */
	public int getC_PaySchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaySchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount %.
		@param Discount 
		Discount in percent
	  */
	public void setDiscount (BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Discount %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Discount Days.
		@param DiscountDays 
		Number of days from invoice date to be eligible for discount
	  */
	public void setDiscountDays (int DiscountDays)
	{
		set_Value (COLUMNNAME_DiscountDays, Integer.valueOf(DiscountDays));
	}

	/** Get Discount Days.
		@return Number of days from invoice date to be eligible for discount
	  */
	public int getDiscountDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DiscountDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Grace Days.
		@param GraceDays 
		Days after due date to send first dunning letter
	  */
	public void setGraceDays (int GraceDays)
	{
		set_Value (COLUMNNAME_GraceDays, Integer.valueOf(GraceDays));
	}

	/** Get Grace Days.
		@return Days after due date to send first dunning letter
	  */
	public int getGraceDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GraceDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** NetDay AD_Reference_ID=167 */
	public static final int NETDAY_AD_Reference_ID=167;
	/** Sunday = 7 */
	public static final String NETDAY_Sunday = "7";
	/** Monday = 1 */
	public static final String NETDAY_Monday = "1";
	/** Tuesday = 2 */
	public static final String NETDAY_Tuesday = "2";
	/** Wednesday = 3 */
	public static final String NETDAY_Wednesday = "3";
	/** Thursday = 4 */
	public static final String NETDAY_Thursday = "4";
	/** Friday = 5 */
	public static final String NETDAY_Friday = "5";
	/** Saturday = 6 */
	public static final String NETDAY_Saturday = "6";
	/** Set Net Day.
		@param NetDay 
		Day when payment is due net
	  */
	public void setNetDay (String NetDay)
	{

		set_Value (COLUMNNAME_NetDay, NetDay);
	}

	/** Get Net Day.
		@return Day when payment is due net
	  */
	public String getNetDay () 
	{
		return (String)get_Value(COLUMNNAME_NetDay);
	}

	/** Set Net Days.
		@param NetDays 
		Net Days in which payment is due
	  */
	public void setNetDays (int NetDays)
	{
		set_Value (COLUMNNAME_NetDays, Integer.valueOf(NetDays));
	}

	/** Get Net Days.
		@return Net Days in which payment is due
	  */
	public int getNetDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NetDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Percentage.
		@param Percentage 
		Percent of the entire amount
	  */
	public void setPercentage (BigDecimal Percentage)
	{
		set_Value (COLUMNNAME_Percentage, Percentage);
	}

	/** Get Percentage.
		@return Percent of the entire amount
	  */
	public BigDecimal getPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percentage);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}
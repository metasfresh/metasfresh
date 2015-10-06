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
import org.compiere.util.KeyNamePair;

/** Generated Model for C_UserRemuneration
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_UserRemuneration extends PO implements I_C_UserRemuneration, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_UserRemuneration (Properties ctx, int C_UserRemuneration_ID, String trxName)
    {
      super (ctx, C_UserRemuneration_ID, trxName);
      /** if (C_UserRemuneration_ID == 0)
        {
			setAD_User_ID (0);
			setC_Remuneration_ID (0);
			setC_UserRemuneration_ID (0);
			setGrossRAmt (Env.ZERO);
			setGrossRCost (Env.ZERO);
			setOvertimeAmt (Env.ZERO);
			setOvertimeCost (Env.ZERO);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_UserRemuneration (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_C_UserRemuneration[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_User getAD_User() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_User_ID()));
    }

	public I_C_Remuneration getC_Remuneration() throws RuntimeException
    {
		return (I_C_Remuneration)MTable.get(getCtx(), I_C_Remuneration.Table_Name)
			.getPO(getC_Remuneration_ID(), get_TrxName());	}

	/** Set Remuneration.
		@param C_Remuneration_ID 
		Wage or Salary
	  */
	public void setC_Remuneration_ID (int C_Remuneration_ID)
	{
		if (C_Remuneration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Remuneration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Remuneration_ID, Integer.valueOf(C_Remuneration_ID));
	}

	/** Get Remuneration.
		@return Wage or Salary
	  */
	public int getC_Remuneration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Remuneration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Employee Remuneration.
		@param C_UserRemuneration_ID 
		Employee Wage or Salary Overwrite
	  */
	public void setC_UserRemuneration_ID (int C_UserRemuneration_ID)
	{
		if (C_UserRemuneration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UserRemuneration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UserRemuneration_ID, Integer.valueOf(C_UserRemuneration_ID));
	}

	/** Get Employee Remuneration.
		@return Employee Wage or Salary Overwrite
	  */
	public int getC_UserRemuneration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UserRemuneration_ID);
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

	/** Set Gross Amount.
		@param GrossRAmt 
		Gross Remuneration Amount
	  */
	public void setGrossRAmt (BigDecimal GrossRAmt)
	{
		set_Value (COLUMNNAME_GrossRAmt, GrossRAmt);
	}

	/** Get Gross Amount.
		@return Gross Remuneration Amount
	  */
	public BigDecimal getGrossRAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrossRAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Gross Cost.
		@param GrossRCost 
		Gross Remuneration Costs
	  */
	public void setGrossRCost (BigDecimal GrossRCost)
	{
		set_Value (COLUMNNAME_GrossRCost, GrossRCost);
	}

	/** Get Gross Cost.
		@return Gross Remuneration Costs
	  */
	public BigDecimal getGrossRCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrossRCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Overtime Amount.
		@param OvertimeAmt 
		Hourly Overtime Rate
	  */
	public void setOvertimeAmt (BigDecimal OvertimeAmt)
	{
		set_Value (COLUMNNAME_OvertimeAmt, OvertimeAmt);
	}

	/** Get Overtime Amount.
		@return Hourly Overtime Rate
	  */
	public BigDecimal getOvertimeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OvertimeAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Overtime Cost.
		@param OvertimeCost 
		Hourly Overtime Cost
	  */
	public void setOvertimeCost (BigDecimal OvertimeCost)
	{
		set_Value (COLUMNNAME_OvertimeCost, OvertimeCost);
	}

	/** Get Overtime Cost.
		@return Hourly Overtime Cost
	  */
	public BigDecimal getOvertimeCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OvertimeCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}
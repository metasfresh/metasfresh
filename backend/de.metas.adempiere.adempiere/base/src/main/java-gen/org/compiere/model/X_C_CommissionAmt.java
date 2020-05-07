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

/** Generated Model for C_CommissionAmt
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_CommissionAmt extends PO implements I_C_CommissionAmt, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_CommissionAmt (Properties ctx, int C_CommissionAmt_ID, String trxName)
    {
      super (ctx, C_CommissionAmt_ID, trxName);
      /** if (C_CommissionAmt_ID == 0)
        {
			setActualQty (Env.ZERO);
			setC_CommissionAmt_ID (0);
			setC_CommissionLine_ID (0);
			setC_CommissionRun_ID (0);
			setCommissionAmt (Env.ZERO);
			setConvertedAmt (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_CommissionAmt (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_CommissionAmt[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Actual Quantity.
		@param ActualQty 
		The actual quantity
	  */
	public void setActualQty (BigDecimal ActualQty)
	{
		set_Value (COLUMNNAME_ActualQty, ActualQty);
	}

	/** Get Actual Quantity.
		@return The actual quantity
	  */
	public BigDecimal getActualQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Commission Amount.
		@param C_CommissionAmt_ID 
		Generated Commission Amount 
	  */
	public void setC_CommissionAmt_ID (int C_CommissionAmt_ID)
	{
		if (C_CommissionAmt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CommissionAmt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CommissionAmt_ID, Integer.valueOf(C_CommissionAmt_ID));
	}

	/** Get Commission Amount.
		@return Generated Commission Amount 
	  */
	public int getC_CommissionAmt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CommissionAmt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_CommissionLine getC_CommissionLine() throws RuntimeException
    {
		return (I_C_CommissionLine)MTable.get(getCtx(), I_C_CommissionLine.Table_Name)
			.getPO(getC_CommissionLine_ID(), get_TrxName());	}

	/** Set Commission Line.
		@param C_CommissionLine_ID 
		Commission Line
	  */
	public void setC_CommissionLine_ID (int C_CommissionLine_ID)
	{
		if (C_CommissionLine_ID < 1) 
			set_Value (COLUMNNAME_C_CommissionLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_CommissionLine_ID, Integer.valueOf(C_CommissionLine_ID));
	}

	/** Get Commission Line.
		@return Commission Line
	  */
	public int getC_CommissionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CommissionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_CommissionRun getC_CommissionRun() throws RuntimeException
    {
		return (I_C_CommissionRun)MTable.get(getCtx(), I_C_CommissionRun.Table_Name)
			.getPO(getC_CommissionRun_ID(), get_TrxName());	}

	/** Set Commission Run.
		@param C_CommissionRun_ID 
		Commission Run or Process
	  */
	public void setC_CommissionRun_ID (int C_CommissionRun_ID)
	{
		if (C_CommissionRun_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CommissionRun_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CommissionRun_ID, Integer.valueOf(C_CommissionRun_ID));
	}

	/** Get Commission Run.
		@return Commission Run or Process
	  */
	public int getC_CommissionRun_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CommissionRun_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_CommissionRun_ID()));
    }

	/** Set Commission Amount.
		@param CommissionAmt 
		Commission Amount
	  */
	public void setCommissionAmt (BigDecimal CommissionAmt)
	{
		set_Value (COLUMNNAME_CommissionAmt, CommissionAmt);
	}

	/** Get Commission Amount.
		@return Commission Amount
	  */
	public BigDecimal getCommissionAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Converted Amount.
		@param ConvertedAmt 
		Converted Amount
	  */
	public void setConvertedAmt (BigDecimal ConvertedAmt)
	{
		set_Value (COLUMNNAME_ConvertedAmt, ConvertedAmt);
	}

	/** Get Converted Amount.
		@return Converted Amount
	  */
	public BigDecimal getConvertedAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ConvertedAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}
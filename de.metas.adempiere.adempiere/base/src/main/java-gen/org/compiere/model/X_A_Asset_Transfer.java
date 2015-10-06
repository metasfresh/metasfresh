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

/** Generated Model for A_Asset_Transfer
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_A_Asset_Transfer extends PO implements I_A_Asset_Transfer, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_A_Asset_Transfer (Properties ctx, int A_Asset_Transfer_ID, String trxName)
    {
      super (ctx, A_Asset_Transfer_ID, trxName);
      /** if (A_Asset_Transfer_ID == 0)
        {
			setA_Accumdepreciation_Acct_New (0);
			setA_Asset_Acct_New (0);
			setA_Asset_Transfer_ID (0);
			setA_Depreciation_Acct_New (0);
			setA_Disposal_Loss_New (0);
			setA_Disposal_Revenue_New (0);
			setA_Period_End (0);
// @SQL=SELECT A_Period_End FROM A_Asset_Acct WHERE A_Asset_Acct.A_Asset_Acct_ID=@A_Asset_Acct_ID@
			setA_Period_Start (0);
// @SQL=SELECT A_Period_Start FROM A_Asset_Acct WHERE A_Asset_Acct.A_Asset_Acct_ID=@A_Asset_Acct_ID@
			setA_Split_Percent (Env.ZERO);
// @SQL=SELECT A_Split_Percent FROM A_Asset_Acct WHERE A_Asset_Acct.A_Asset_Acct_ID=@A_Asset_Acct_ID@
			setA_Transfer_Balance (true);
// Y
			setA_Transfer_Balance_IS (false);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
// @Date@
			setProcessed (false);
			setProcessing (false);
        } */
    }

    /** Load Constructor */
    public X_A_Asset_Transfer (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_A_Asset_Transfer[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accumulated Depreciation.
		@param A_Accumdepreciation_Acct Accumulated Depreciation	  */
	public void setA_Accumdepreciation_Acct (int A_Accumdepreciation_Acct)
	{
		set_Value (COLUMNNAME_A_Accumdepreciation_Acct, Integer.valueOf(A_Accumdepreciation_Acct));
	}

	/** Get Accumulated Depreciation.
		@return Accumulated Depreciation	  */
	public int getA_Accumdepreciation_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Accumdepreciation_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_Accumdepreciation_Acct_() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_Accumdepreciation_Acct_New(), get_TrxName());	}

	/** Set New Accum Depreciation Acct.
		@param A_Accumdepreciation_Acct_New New Accum Depreciation Acct	  */
	public void setA_Accumdepreciation_Acct_New (int A_Accumdepreciation_Acct_New)
	{
		set_Value (COLUMNNAME_A_Accumdepreciation_Acct_New, Integer.valueOf(A_Accumdepreciation_Acct_New));
	}

	/** Get New Accum Depreciation Acct.
		@return New Accum Depreciation Acct	  */
	public int getA_Accumdepreciation_Acct_New () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Accumdepreciation_Acct_New);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Old Accum Depreciation Acct.
		@param A_Accumdepreciation_Acct_Str Old Accum Depreciation Acct	  */
	public void setA_Accumdepreciation_Acct_Str (String A_Accumdepreciation_Acct_Str)
	{
		set_Value (COLUMNNAME_A_Accumdepreciation_Acct_Str, A_Accumdepreciation_Acct_Str);
	}

	/** Get Old Accum Depreciation Acct.
		@return Old Accum Depreciation Acct	  */
	public String getA_Accumdepreciation_Acct_Str () 
	{
		return (String)get_Value(COLUMNNAME_A_Accumdepreciation_Acct_Str);
	}

	/** Set Asset Cost Account.
		@param A_Asset_Acct Asset Cost Account	  */
	public void setA_Asset_Acct (int A_Asset_Acct)
	{
		set_Value (COLUMNNAME_A_Asset_Acct, Integer.valueOf(A_Asset_Acct));
	}

	/** Get Asset Cost Account.
		@return Asset Cost Account	  */
	public int getA_Asset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set A_Asset_Acct_ID.
		@param A_Asset_Acct_ID A_Asset_Acct_ID	  */
	public void setA_Asset_Acct_ID (int A_Asset_Acct_ID)
	{
		if (A_Asset_Acct_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Acct_ID, Integer.valueOf(A_Asset_Acct_ID));
	}

	/** Get A_Asset_Acct_ID.
		@return A_Asset_Acct_ID	  */
	public int getA_Asset_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_Asset_Acct_() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_Asset_Acct_New(), get_TrxName());	}

	/** Set New Asset Cost Acct.
		@param A_Asset_Acct_New New Asset Cost Acct	  */
	public void setA_Asset_Acct_New (int A_Asset_Acct_New)
	{
		set_Value (COLUMNNAME_A_Asset_Acct_New, Integer.valueOf(A_Asset_Acct_New));
	}

	/** Get New Asset Cost Acct.
		@return New Asset Cost Acct	  */
	public int getA_Asset_Acct_New () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Acct_New);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Old Asset Cost Acct.
		@param A_Asset_Acct_Str Old Asset Cost Acct	  */
	public void setA_Asset_Acct_Str (String A_Asset_Acct_Str)
	{
		set_Value (COLUMNNAME_A_Asset_Acct_Str, A_Asset_Acct_Str);
	}

	/** Get Old Asset Cost Acct.
		@return Old Asset Cost Acct	  */
	public String getA_Asset_Acct_Str () 
	{
		return (String)get_Value(COLUMNNAME_A_Asset_Acct_Str);
	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set A_Asset_Transfer_ID.
		@param A_Asset_Transfer_ID A_Asset_Transfer_ID	  */
	public void setA_Asset_Transfer_ID (int A_Asset_Transfer_ID)
	{
		if (A_Asset_Transfer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Transfer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Transfer_ID, Integer.valueOf(A_Asset_Transfer_ID));
	}

	/** Get A_Asset_Transfer_ID.
		@return A_Asset_Transfer_ID	  */
	public int getA_Asset_Transfer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Transfer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getA_Asset_Transfer_ID()));
    }

	/** Set Depreciation Expense Account.
		@param A_Depreciation_Acct Depreciation Expense Account	  */
	public void setA_Depreciation_Acct (int A_Depreciation_Acct)
	{
		set_Value (COLUMNNAME_A_Depreciation_Acct, Integer.valueOf(A_Depreciation_Acct));
	}

	/** Get Depreciation Expense Account.
		@return Depreciation Expense Account	  */
	public int getA_Depreciation_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Depreciation_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_Depreciation_Acct_() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_Depreciation_Acct_New(), get_TrxName());	}

	/** Set New Depreciation Exp Acct.
		@param A_Depreciation_Acct_New New Depreciation Exp Acct	  */
	public void setA_Depreciation_Acct_New (int A_Depreciation_Acct_New)
	{
		set_Value (COLUMNNAME_A_Depreciation_Acct_New, Integer.valueOf(A_Depreciation_Acct_New));
	}

	/** Get New Depreciation Exp Acct.
		@return New Depreciation Exp Acct	  */
	public int getA_Depreciation_Acct_New () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Depreciation_Acct_New);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Old Depreciation Exp Acct.
		@param A_Depreciation_Acct_Str Old Depreciation Exp Acct	  */
	public void setA_Depreciation_Acct_Str (String A_Depreciation_Acct_Str)
	{
		set_ValueNoCheck (COLUMNNAME_A_Depreciation_Acct_Str, A_Depreciation_Acct_Str);
	}

	/** Get Old Depreciation Exp Acct.
		@return Old Depreciation Exp Acct	  */
	public String getA_Depreciation_Acct_Str () 
	{
		return (String)get_Value(COLUMNNAME_A_Depreciation_Acct_Str);
	}

	/** Set Loss on Disposal.
		@param A_Disposal_Loss Loss on Disposal	  */
	public void setA_Disposal_Loss (int A_Disposal_Loss)
	{
		set_Value (COLUMNNAME_A_Disposal_Loss, Integer.valueOf(A_Disposal_Loss));
	}

	/** Get Loss on Disposal.
		@return Loss on Disposal	  */
	public int getA_Disposal_Loss () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Disposal_Loss);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_Disposal_Loss_() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_Disposal_Loss_New(), get_TrxName());	}

	/** Set New Disposal Loss.
		@param A_Disposal_Loss_New New Disposal Loss	  */
	public void setA_Disposal_Loss_New (int A_Disposal_Loss_New)
	{
		set_Value (COLUMNNAME_A_Disposal_Loss_New, Integer.valueOf(A_Disposal_Loss_New));
	}

	/** Get New Disposal Loss.
		@return New Disposal Loss	  */
	public int getA_Disposal_Loss_New () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Disposal_Loss_New);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Old Disposal Loss.
		@param A_Disposal_Loss_Str Old Disposal Loss	  */
	public void setA_Disposal_Loss_Str (String A_Disposal_Loss_Str)
	{
		set_ValueNoCheck (COLUMNNAME_A_Disposal_Loss_Str, A_Disposal_Loss_Str);
	}

	/** Get Old Disposal Loss.
		@return Old Disposal Loss	  */
	public String getA_Disposal_Loss_Str () 
	{
		return (String)get_Value(COLUMNNAME_A_Disposal_Loss_Str);
	}

	/** Set Disposal Revenue.
		@param A_Disposal_Revenue Disposal Revenue	  */
	public void setA_Disposal_Revenue (int A_Disposal_Revenue)
	{
		set_Value (COLUMNNAME_A_Disposal_Revenue, Integer.valueOf(A_Disposal_Revenue));
	}

	/** Get Disposal Revenue.
		@return Disposal Revenue	  */
	public int getA_Disposal_Revenue () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Disposal_Revenue);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_Disposal_Revenue_() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_Disposal_Revenue_New(), get_TrxName());	}

	/** Set New Disposal Revenue.
		@param A_Disposal_Revenue_New New Disposal Revenue	  */
	public void setA_Disposal_Revenue_New (int A_Disposal_Revenue_New)
	{
		set_Value (COLUMNNAME_A_Disposal_Revenue_New, Integer.valueOf(A_Disposal_Revenue_New));
	}

	/** Get New Disposal Revenue.
		@return New Disposal Revenue	  */
	public int getA_Disposal_Revenue_New () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Disposal_Revenue_New);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Old Disposal Revenue.
		@param A_Disposal_Revenue_Str Old Disposal Revenue	  */
	public void setA_Disposal_Revenue_Str (String A_Disposal_Revenue_Str)
	{
		set_ValueNoCheck (COLUMNNAME_A_Disposal_Revenue_Str, A_Disposal_Revenue_Str);
	}

	/** Get Old Disposal Revenue.
		@return Old Disposal Revenue	  */
	public String getA_Disposal_Revenue_Str () 
	{
		return (String)get_Value(COLUMNNAME_A_Disposal_Revenue_Str);
	}

	/** Set Period End.
		@param A_Period_End Period End	  */
	public void setA_Period_End (int A_Period_End)
	{
		set_Value (COLUMNNAME_A_Period_End, Integer.valueOf(A_Period_End));
	}

	/** Get Period End.
		@return Period End	  */
	public int getA_Period_End () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Period_End);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Period Start.
		@param A_Period_Start Period Start	  */
	public void setA_Period_Start (int A_Period_Start)
	{
		set_Value (COLUMNNAME_A_Period_Start, Integer.valueOf(A_Period_Start));
	}

	/** Get Period Start.
		@return Period Start	  */
	public int getA_Period_Start () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Period_Start);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Split Percentage.
		@param A_Split_Percent Split Percentage	  */
	public void setA_Split_Percent (BigDecimal A_Split_Percent)
	{
		set_Value (COLUMNNAME_A_Split_Percent, A_Split_Percent);
	}

	/** Get Split Percentage.
		@return Split Percentage	  */
	public BigDecimal getA_Split_Percent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_Split_Percent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Transfer Balance Sheet.
		@param A_Transfer_Balance Transfer Balance Sheet	  */
	public void setA_Transfer_Balance (boolean A_Transfer_Balance)
	{
		set_Value (COLUMNNAME_A_Transfer_Balance, Boolean.valueOf(A_Transfer_Balance));
	}

	/** Get Transfer Balance Sheet.
		@return Transfer Balance Sheet	  */
	public boolean isA_Transfer_Balance () 
	{
		Object oo = get_Value(COLUMNNAME_A_Transfer_Balance);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Transfer Balance IS.
		@param A_Transfer_Balance_IS Transfer Balance IS	  */
	public void setA_Transfer_Balance_IS (boolean A_Transfer_Balance_IS)
	{
		set_Value (COLUMNNAME_A_Transfer_Balance_IS, Boolean.valueOf(A_Transfer_Balance_IS));
	}

	/** Get Transfer Balance IS.
		@return Transfer Balance IS	  */
	public boolean isA_Transfer_Balance_IS () 
	{
		Object oo = get_Value(COLUMNNAME_A_Transfer_Balance_IS);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException
    {
		return (I_C_AcctSchema)MTable.get(getCtx(), I_C_AcctSchema.Table_Name)
			.getPO(getC_AcctSchema_ID(), get_TrxName());	}

	/** Set Accounting Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Accounting Schema.
		@return Rules for accounting
	  */
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Period getC_Period() throws RuntimeException
    {
		return (I_C_Period)MTable.get(getCtx(), I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Period.
		@param C_Period_ID 
		Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** PostingType AD_Reference_ID=125 */
	public static final int POSTINGTYPE_AD_Reference_ID=125;
	/** Actual = A */
	public static final String POSTINGTYPE_Actual = "A";
	/** Budget = B */
	public static final String POSTINGTYPE_Budget = "B";
	/** Commitment = E */
	public static final String POSTINGTYPE_Commitment = "E";
	/** Statistical = S */
	public static final String POSTINGTYPE_Statistical = "S";
	/** Reservation = R */
	public static final String POSTINGTYPE_Reservation = "R";
	/** Set PostingType.
		@param PostingType 
		The type of posted amount for the transaction
	  */
	public void setPostingType (String PostingType)
	{

		set_Value (COLUMNNAME_PostingType, PostingType);
	}

	/** Get PostingType.
		@return The type of posted amount for the transaction
	  */
	public String getPostingType () 
	{
		return (String)get_Value(COLUMNNAME_PostingType);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}
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

/** Generated Model for A_Asset_Change
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_A_Asset_Change extends PO implements I_A_Asset_Change, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_A_Asset_Change (Properties ctx, int A_Asset_Change_ID, String trxName)
    {
      super (ctx, A_Asset_Change_ID, trxName);
      /** if (A_Asset_Change_ID == 0)
        {
			setA_Asset_Change_ID (0);
			setA_Asset_ID (0);
			setChangeType (null);
			setTextDetails (null);
        } */
    }

    /** Load Constructor */
    public X_A_Asset_Change (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_A_Asset_Change[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ValidCombination getA_Accumdepreciation_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_Accumdepreciation_Acct(), get_TrxName());	}

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

	public I_C_ValidCombination getA_Asset_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_Asset_Acct(), get_TrxName());	}

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

	/** Set A_Asset_Addition_ID.
		@param A_Asset_Addition_ID A_Asset_Addition_ID	  */
	public void setA_Asset_Addition_ID (int A_Asset_Addition_ID)
	{
		if (A_Asset_Addition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Addition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Addition_ID, Integer.valueOf(A_Asset_Addition_ID));
	}

	/** Get A_Asset_Addition_ID.
		@return A_Asset_Addition_ID	  */
	public int getA_Asset_Addition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Addition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set A_Asset_Change_ID.
		@param A_Asset_Change_ID A_Asset_Change_ID	  */
	public void setA_Asset_Change_ID (int A_Asset_Change_ID)
	{
		if (A_Asset_Change_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Change_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Change_ID, Integer.valueOf(A_Asset_Change_ID));
	}

	/** Get A_Asset_Change_ID.
		@return A_Asset_Change_ID	  */
	public int getA_Asset_Change_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Change_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getA_Asset_Change_ID()));
    }

	/** Set A_Asset_CreateDate.
		@param A_Asset_CreateDate A_Asset_CreateDate	  */
	public void setA_Asset_CreateDate (Timestamp A_Asset_CreateDate)
	{
		set_Value (COLUMNNAME_A_Asset_CreateDate, A_Asset_CreateDate);
	}

	/** Get A_Asset_CreateDate.
		@return A_Asset_CreateDate	  */
	public Timestamp getA_Asset_CreateDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_A_Asset_CreateDate);
	}

	public I_A_Asset getA_Asset() throws RuntimeException
    {
		return (I_A_Asset)MTable.get(getCtx(), I_A_Asset.Table_Name)
			.getPO(getA_Asset_ID(), get_TrxName());	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
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

	/** Set Asset Retirement.
		@param A_Asset_Retirement_ID 
		Internally used asset is not longer used.
	  */
	public void setA_Asset_Retirement_ID (int A_Asset_Retirement_ID)
	{
		if (A_Asset_Retirement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Retirement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Retirement_ID, Integer.valueOf(A_Asset_Retirement_ID));
	}

	/** Get Asset Retirement.
		@return Internally used asset is not longer used.
	  */
	public int getA_Asset_Retirement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Retirement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set A_Asset_RevalDate.
		@param A_Asset_RevalDate A_Asset_RevalDate	  */
	public void setA_Asset_RevalDate (Timestamp A_Asset_RevalDate)
	{
		set_Value (COLUMNNAME_A_Asset_RevalDate, A_Asset_RevalDate);
	}

	/** Get A_Asset_RevalDate.
		@return A_Asset_RevalDate	  */
	public Timestamp getA_Asset_RevalDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_A_Asset_RevalDate);
	}

	public I_A_Asset_Spread getA_Asset_Spread_T() throws RuntimeException
    {
		return (I_A_Asset_Spread)MTable.get(getCtx(), I_A_Asset_Spread.Table_Name)
			.getPO(getA_Asset_Spread_Type(), get_TrxName());	}

	/** Set A_Asset_Spread_Type.
		@param A_Asset_Spread_Type A_Asset_Spread_Type	  */
	public void setA_Asset_Spread_Type (int A_Asset_Spread_Type)
	{
		set_Value (COLUMNNAME_A_Asset_Spread_Type, Integer.valueOf(A_Asset_Spread_Type));
	}

	/** Get A_Asset_Spread_Type.
		@return A_Asset_Spread_Type	  */
	public int getA_Asset_Spread_Type () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Spread_Type);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getA_Depreciation_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_Depreciation_Acct(), get_TrxName());	}

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

	public I_A_Depreciation_Method getA_Depreciation_Calc_T() throws RuntimeException
    {
		return (I_A_Depreciation_Method)MTable.get(getCtx(), I_A_Depreciation_Method.Table_Name)
			.getPO(getA_Depreciation_Calc_Type(), get_TrxName());	}

	/** Set Depreciation Calculation Type.
		@param A_Depreciation_Calc_Type Depreciation Calculation Type	  */
	public void setA_Depreciation_Calc_Type (int A_Depreciation_Calc_Type)
	{
		set_Value (COLUMNNAME_A_Depreciation_Calc_Type, Integer.valueOf(A_Depreciation_Calc_Type));
	}

	/** Get Depreciation Calculation Type.
		@return Depreciation Calculation Type	  */
	public int getA_Depreciation_Calc_Type () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Depreciation_Calc_Type);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set A_Depreciation_Manual_Amount.
		@param A_Depreciation_Manual_Amount A_Depreciation_Manual_Amount	  */
	public void setA_Depreciation_Manual_Amount (BigDecimal A_Depreciation_Manual_Amount)
	{
		set_Value (COLUMNNAME_A_Depreciation_Manual_Amount, A_Depreciation_Manual_Amount);
	}

	/** Get A_Depreciation_Manual_Amount.
		@return A_Depreciation_Manual_Amount	  */
	public BigDecimal getA_Depreciation_Manual_Amount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_Depreciation_Manual_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** A_Depreciation_Manual_Period AD_Reference_ID=53256 */
	public static final int A_DEPRECIATION_MANUAL_PERIOD_AD_Reference_ID=53256;
	/** Period = PR */
	public static final String A_DEPRECIATION_MANUAL_PERIOD_Period = "PR";
	/** Yearly = YR */
	public static final String A_DEPRECIATION_MANUAL_PERIOD_Yearly = "YR";
	/** Set A_Depreciation_Manual_Period.
		@param A_Depreciation_Manual_Period A_Depreciation_Manual_Period	  */
	public void setA_Depreciation_Manual_Period (String A_Depreciation_Manual_Period)
	{

		set_Value (COLUMNNAME_A_Depreciation_Manual_Period, A_Depreciation_Manual_Period);
	}

	/** Get A_Depreciation_Manual_Period.
		@return A_Depreciation_Manual_Period	  */
	public String getA_Depreciation_Manual_Period () 
	{
		return (String)get_Value(COLUMNNAME_A_Depreciation_Manual_Period);
	}

	public I_A_Depreciation_Table_Header getA_Depreciation_Table_Header() throws RuntimeException
    {
		return (I_A_Depreciation_Table_Header)MTable.get(getCtx(), I_A_Depreciation_Table_Header.Table_Name)
			.getPO(getA_Depreciation_Table_Header_ID(), get_TrxName());	}

	/** Set A_Depreciation_Table_Header_ID.
		@param A_Depreciation_Table_Header_ID A_Depreciation_Table_Header_ID	  */
	public void setA_Depreciation_Table_Header_ID (int A_Depreciation_Table_Header_ID)
	{
		if (A_Depreciation_Table_Header_ID < 1) 
			set_Value (COLUMNNAME_A_Depreciation_Table_Header_ID, null);
		else 
			set_Value (COLUMNNAME_A_Depreciation_Table_Header_ID, Integer.valueOf(A_Depreciation_Table_Header_ID));
	}

	/** Get A_Depreciation_Table_Header_ID.
		@return A_Depreciation_Table_Header_ID	  */
	public int getA_Depreciation_Table_Header_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Depreciation_Table_Header_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set A_Depreciation_Variable_Perc.
		@param A_Depreciation_Variable_Perc A_Depreciation_Variable_Perc	  */
	public void setA_Depreciation_Variable_Perc (BigDecimal A_Depreciation_Variable_Perc)
	{
		set_Value (COLUMNNAME_A_Depreciation_Variable_Perc, A_Depreciation_Variable_Perc);
	}

	/** Get A_Depreciation_Variable_Perc.
		@return A_Depreciation_Variable_Perc	  */
	public BigDecimal getA_Depreciation_Variable_Perc () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_Depreciation_Variable_Perc);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_ValidCombination getA_Disposal_L() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_Disposal_Loss(), get_TrxName());	}

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

	public I_C_ValidCombination getA_Disposal_Reve() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getA_Disposal_Revenue(), get_TrxName());	}

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
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
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

	public I_A_Asset getA_Parent_Asset() throws RuntimeException
    {
		return (I_A_Asset)MTable.get(getCtx(), I_A_Asset.Table_Name)
			.getPO(getA_Parent_Asset_ID(), get_TrxName());	}

	/** Set Asset ID.
		@param A_Parent_Asset_ID Asset ID	  */
	public void setA_Parent_Asset_ID (int A_Parent_Asset_ID)
	{
		if (A_Parent_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Parent_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Parent_Asset_ID, Integer.valueOf(A_Parent_Asset_ID));
	}

	/** Get Asset ID.
		@return Asset ID	  */
	public int getA_Parent_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Parent_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Quantity.
		@param A_QTY_Current Quantity	  */
	public void setA_QTY_Current (BigDecimal A_QTY_Current)
	{
		set_Value (COLUMNNAME_A_QTY_Current, A_QTY_Current);
	}

	/** Get Quantity.
		@return Quantity	  */
	public BigDecimal getA_QTY_Current () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_QTY_Current);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Original Qty.
		@param A_QTY_Original Original Qty	  */
	public void setA_QTY_Original (BigDecimal A_QTY_Original)
	{
		set_Value (COLUMNNAME_A_QTY_Original, A_QTY_Original);
	}

	/** Get Original Qty.
		@return Original Qty	  */
	public BigDecimal getA_QTY_Original () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_QTY_Original);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Revaluation Accumulated Depreciation Offset for Current Year.
		@param A_Reval_Accumdep_Offset_Cur Revaluation Accumulated Depreciation Offset for Current Year	  */
	public void setA_Reval_Accumdep_Offset_Cur (int A_Reval_Accumdep_Offset_Cur)
	{
		set_Value (COLUMNNAME_A_Reval_Accumdep_Offset_Cur, Integer.valueOf(A_Reval_Accumdep_Offset_Cur));
	}

	/** Get Revaluation Accumulated Depreciation Offset for Current Year.
		@return Revaluation Accumulated Depreciation Offset for Current Year	  */
	public int getA_Reval_Accumdep_Offset_Cur () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Reval_Accumdep_Offset_Cur);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Revaluation Accumulated Depreciation Offset for Prior Year.
		@param A_Reval_Accumdep_Offset_Prior Revaluation Accumulated Depreciation Offset for Prior Year	  */
	public void setA_Reval_Accumdep_Offset_Prior (int A_Reval_Accumdep_Offset_Prior)
	{
		set_Value (COLUMNNAME_A_Reval_Accumdep_Offset_Prior, Integer.valueOf(A_Reval_Accumdep_Offset_Prior));
	}

	/** Get Revaluation Accumulated Depreciation Offset for Prior Year.
		@return Revaluation Accumulated Depreciation Offset for Prior Year	  */
	public int getA_Reval_Accumdep_Offset_Prior () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Reval_Accumdep_Offset_Prior);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** A_Reval_Cal_Method AD_Reference_ID=53259 */
	public static final int A_REVAL_CAL_METHOD_AD_Reference_ID=53259;
	/** Default = DFT */
	public static final String A_REVAL_CAL_METHOD_Default = "DFT";
	/** Inception to date = IDF */
	public static final String A_REVAL_CAL_METHOD_InceptionToDate = "IDF";
	/** Year Balances = YBF */
	public static final String A_REVAL_CAL_METHOD_YearBalances = "YBF";
	/** Set Revaluation Calculation Method.
		@param A_Reval_Cal_Method Revaluation Calculation Method	  */
	public void setA_Reval_Cal_Method (String A_Reval_Cal_Method)
	{

		set_Value (COLUMNNAME_A_Reval_Cal_Method, A_Reval_Cal_Method);
	}

	/** Get Revaluation Calculation Method.
		@return Revaluation Calculation Method	  */
	public String getA_Reval_Cal_Method () 
	{
		return (String)get_Value(COLUMNNAME_A_Reval_Cal_Method);
	}

	/** Set Revaluation Cost Offset for Current Year.
		@param A_Reval_Cost_Offset Revaluation Cost Offset for Current Year	  */
	public void setA_Reval_Cost_Offset (int A_Reval_Cost_Offset)
	{
		set_Value (COLUMNNAME_A_Reval_Cost_Offset, Integer.valueOf(A_Reval_Cost_Offset));
	}

	/** Get Revaluation Cost Offset for Current Year.
		@return Revaluation Cost Offset for Current Year	  */
	public int getA_Reval_Cost_Offset () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Reval_Cost_Offset);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Revaluation Cost Offset for Prior Year.
		@param A_Reval_Cost_Offset_Prior Revaluation Cost Offset for Prior Year	  */
	public void setA_Reval_Cost_Offset_Prior (int A_Reval_Cost_Offset_Prior)
	{
		set_Value (COLUMNNAME_A_Reval_Cost_Offset_Prior, Integer.valueOf(A_Reval_Cost_Offset_Prior));
	}

	/** Get Revaluation Cost Offset for Prior Year.
		@return Revaluation Cost Offset for Prior Year	  */
	public int getA_Reval_Cost_Offset_Prior () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Reval_Cost_Offset_Prior);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Revaluation Expense Offs.
		@param A_Reval_Depexp_Offset Revaluation Expense Offs	  */
	public void setA_Reval_Depexp_Offset (int A_Reval_Depexp_Offset)
	{
		set_Value (COLUMNNAME_A_Reval_Depexp_Offset, Integer.valueOf(A_Reval_Depexp_Offset));
	}

	/** Get Revaluation Expense Offs.
		@return Revaluation Expense Offs	  */
	public int getA_Reval_Depexp_Offset () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Reval_Depexp_Offset);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Salvage Value.
		@param A_Salvage_Value Salvage Value	  */
	public void setA_Salvage_Value (BigDecimal A_Salvage_Value)
	{
		set_Value (COLUMNNAME_A_Salvage_Value, A_Salvage_Value);
	}

	/** Get Salvage Value.
		@return Salvage Value	  */
	public BigDecimal getA_Salvage_Value () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_Salvage_Value);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set AssetAccumDepreciationAmt.
		@param AssetAccumDepreciationAmt AssetAccumDepreciationAmt	  */
	public void setAssetAccumDepreciationAmt (BigDecimal AssetAccumDepreciationAmt)
	{
		set_ValueNoCheck (COLUMNNAME_AssetAccumDepreciationAmt, AssetAccumDepreciationAmt);
	}

	/** Get AssetAccumDepreciationAmt.
		@return AssetAccumDepreciationAmt	  */
	public BigDecimal getAssetAccumDepreciationAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AssetAccumDepreciationAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AssetBookValueAmt.
		@param AssetBookValueAmt AssetBookValueAmt	  */
	public void setAssetBookValueAmt (BigDecimal AssetBookValueAmt)
	{
		set_ValueNoCheck (COLUMNNAME_AssetBookValueAmt, AssetBookValueAmt);
	}

	/** Get AssetBookValueAmt.
		@return AssetBookValueAmt	  */
	public BigDecimal getAssetBookValueAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AssetBookValueAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Asset Depreciation Date.
		@param AssetDepreciationDate 
		Date of last depreciation
	  */
	public void setAssetDepreciationDate (Timestamp AssetDepreciationDate)
	{
		set_ValueNoCheck (COLUMNNAME_AssetDepreciationDate, AssetDepreciationDate);
	}

	/** Get Asset Depreciation Date.
		@return Date of last depreciation
	  */
	public Timestamp getAssetDepreciationDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_AssetDepreciationDate);
	}

	/** Set Asset Disposal Date.
		@param AssetDisposalDate 
		Date when the asset is/was disposed
	  */
	public void setAssetDisposalDate (Timestamp AssetDisposalDate)
	{
		set_Value (COLUMNNAME_AssetDisposalDate, AssetDisposalDate);
	}

	/** Get Asset Disposal Date.
		@return Date when the asset is/was disposed
	  */
	public Timestamp getAssetDisposalDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_AssetDisposalDate);
	}

	/** Set Market value Amount.
		@param AssetMarketValueAmt 
		Market value of the asset
	  */
	public void setAssetMarketValueAmt (BigDecimal AssetMarketValueAmt)
	{
		set_ValueNoCheck (COLUMNNAME_AssetMarketValueAmt, AssetMarketValueAmt);
	}

	/** Get Market value Amount.
		@return Market value of the asset
	  */
	public BigDecimal getAssetMarketValueAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AssetMarketValueAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set In Service Date.
		@param AssetServiceDate 
		Date when Asset was put into service
	  */
	public void setAssetServiceDate (Timestamp AssetServiceDate)
	{
		set_ValueNoCheck (COLUMNNAME_AssetServiceDate, AssetServiceDate);
	}

	/** Get In Service Date.
		@return Date when Asset was put into service
	  */
	public Timestamp getAssetServiceDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_AssetServiceDate);
	}

	/** Set Asset value.
		@param AssetValueAmt 
		Book Value of the asset
	  */
	public void setAssetValueAmt (BigDecimal AssetValueAmt)
	{
		set_ValueNoCheck (COLUMNNAME_AssetValueAmt, AssetValueAmt);
	}

	/** Get Asset value.
		@return Book Value of the asset
	  */
	public BigDecimal getAssetValueAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AssetValueAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (I_C_BPartner_Location)MTable.get(getCtx(), I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ChangeAmt.
		@param ChangeAmt ChangeAmt	  */
	public void setChangeAmt (BigDecimal ChangeAmt)
	{
		set_ValueNoCheck (COLUMNNAME_ChangeAmt, ChangeAmt);
	}

	/** Get ChangeAmt.
		@return ChangeAmt	  */
	public BigDecimal getChangeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ChangeAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ChangeDate.
		@param ChangeDate ChangeDate	  */
	public void setChangeDate (Timestamp ChangeDate)
	{
		set_ValueNoCheck (COLUMNNAME_ChangeDate, ChangeDate);
	}

	/** Get ChangeDate.
		@return ChangeDate	  */
	public Timestamp getChangeDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ChangeDate);
	}

	/** ChangeType AD_Reference_ID=53273 */
	public static final int CHANGETYPE_AD_Reference_ID=53273;
	/** Addition = ADD */
	public static final String CHANGETYPE_Addition = "ADD";
	/** Balance = BAL */
	public static final String CHANGETYPE_Balance = "BAL";
	/** Create = CRT */
	public static final String CHANGETYPE_Create = "CRT";
	/** Depreciation = DEP */
	public static final String CHANGETYPE_Depreciation = "DEP";
	/** Disposal = DIS */
	public static final String CHANGETYPE_Disposal = "DIS";
	/** Expense = EXP */
	public static final String CHANGETYPE_Expense = "EXP";
	/** Forecast = FOR */
	public static final String CHANGETYPE_Forecast = "FOR";
	/** Import = IMP */
	public static final String CHANGETYPE_Import = "IMP";
	/** Revaluation = RVL */
	public static final String CHANGETYPE_Revaluation = "RVL";
	/** Setup = SET */
	public static final String CHANGETYPE_Setup = "SET";
	/** Split = SPL */
	public static final String CHANGETYPE_Split = "SPL";
	/** Transfer = TRN */
	public static final String CHANGETYPE_Transfer = "TRN";
	/** Update = UPD */
	public static final String CHANGETYPE_Update = "UPD";
	/** Usage = USE */
	public static final String CHANGETYPE_Usage = "USE";
	/** Set ChangeType.
		@param ChangeType ChangeType	  */
	public void setChangeType (String ChangeType)
	{

		set_ValueNoCheck (COLUMNNAME_ChangeType, ChangeType);
	}

	/** Get ChangeType.
		@return ChangeType	  */
	public String getChangeType () 
	{
		return (String)get_Value(COLUMNNAME_ChangeType);
	}

	public I_C_Location getC_Location() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_A_Depreciation_Convention getConventionT() throws RuntimeException
    {
		return (I_A_Depreciation_Convention)MTable.get(getCtx(), I_A_Depreciation_Convention.Table_Name)
			.getPO(getConventionType(), get_TrxName());	}

	/** Set ConventionType.
		@param ConventionType ConventionType	  */
	public void setConventionType (int ConventionType)
	{
		set_Value (COLUMNNAME_ConventionType, Integer.valueOf(ConventionType));
	}

	/** Get ConventionType.
		@return ConventionType	  */
	public int getConventionType () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ConventionType);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getC_ValidCombination() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getC_ValidCombination_ID(), get_TrxName());	}

	/** Set Combination.
		@param C_ValidCombination_ID 
		Valid Account Combination
	  */
	public void setC_ValidCombination_ID (int C_ValidCombination_ID)
	{
		if (C_ValidCombination_ID < 1) 
			set_Value (COLUMNNAME_C_ValidCombination_ID, null);
		else 
			set_Value (COLUMNNAME_C_ValidCombination_ID, Integer.valueOf(C_ValidCombination_ID));
	}

	/** Get Combination.
		@return Valid Account Combination
	  */
	public int getC_ValidCombination_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ValidCombination_ID);
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

	public I_A_Depreciation getDepreciationT() throws RuntimeException
    {
		return (I_A_Depreciation)MTable.get(getCtx(), I_A_Depreciation.Table_Name)
			.getPO(getDepreciationType(), get_TrxName());	}

	/** Set DepreciationType.
		@param DepreciationType DepreciationType	  */
	public void setDepreciationType (int DepreciationType)
	{
		set_Value (COLUMNNAME_DepreciationType, Integer.valueOf(DepreciationType));
	}

	/** Get DepreciationType.
		@return DepreciationType	  */
	public int getDepreciationType () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DepreciationType);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Depreciate.
		@param IsDepreciated 
		The asset will be depreciated
	  */
	public void setIsDepreciated (boolean IsDepreciated)
	{
		set_Value (COLUMNNAME_IsDepreciated, Boolean.valueOf(IsDepreciated));
	}

	/** Get Depreciate.
		@return The asset will be depreciated
	  */
	public boolean isDepreciated () 
	{
		Object oo = get_Value(COLUMNNAME_IsDepreciated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Disposed.
		@param IsDisposed 
		The asset is disposed
	  */
	public void setIsDisposed (boolean IsDisposed)
	{
		set_Value (COLUMNNAME_IsDisposed, Boolean.valueOf(IsDisposed));
	}

	/** Get Disposed.
		@return The asset is disposed
	  */
	public boolean isDisposed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisposed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Fully depreciated.
		@param IsFullyDepreciated 
		The asset is fully depreciated
	  */
	public void setIsFullyDepreciated (boolean IsFullyDepreciated)
	{
		set_Value (COLUMNNAME_IsFullyDepreciated, Boolean.valueOf(IsFullyDepreciated));
	}

	/** Get Fully depreciated.
		@return The asset is fully depreciated
	  */
	public boolean isFullyDepreciated () 
	{
		Object oo = get_Value(COLUMNNAME_IsFullyDepreciated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set In Possession.
		@param IsInPosession 
		The asset is in the possession of the organization
	  */
	public void setIsInPosession (boolean IsInPosession)
	{
		set_Value (COLUMNNAME_IsInPosession, Boolean.valueOf(IsInPosession));
	}

	/** Get In Possession.
		@return The asset is in the possession of the organization
	  */
	public boolean isInPosession () 
	{
		Object oo = get_Value(COLUMNNAME_IsInPosession);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Owned.
		@param IsOwned 
		The asset is owned by the organization
	  */
	public void setIsOwned (boolean IsOwned)
	{
		set_Value (COLUMNNAME_IsOwned, Boolean.valueOf(IsOwned));
	}

	/** Get Owned.
		@return The asset is owned by the organization
	  */
	public boolean isOwned () 
	{
		Object oo = get_Value(COLUMNNAME_IsOwned);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Life use.
		@param LifeUseUnits 
		Units of use until the asset is not usable anymore
	  */
	public void setLifeUseUnits (int LifeUseUnits)
	{
		set_ValueNoCheck (COLUMNNAME_LifeUseUnits, Integer.valueOf(LifeUseUnits));
	}

	/** Get Life use.
		@return Units of use until the asset is not usable anymore
	  */
	public int getLifeUseUnits () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LifeUseUnits);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lot No.
		@param Lot 
		Lot number (alphanumeric)
	  */
	public void setLot (String Lot)
	{
		set_ValueNoCheck (COLUMNNAME_Lot, Lot);
	}

	/** Get Lot No.
		@return Lot number (alphanumeric)
	  */
	public String getLot () 
	{
		return (String)get_Value(COLUMNNAME_Lot);
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

	/** Set Serial No.
		@param SerNo 
		Product Serial Number 
	  */
	public void setSerNo (String SerNo)
	{
		set_ValueNoCheck (COLUMNNAME_SerNo, SerNo);
	}

	/** Get Serial No.
		@return Product Serial Number 
	  */
	public String getSerNo () 
	{
		return (String)get_Value(COLUMNNAME_SerNo);
	}

	/** Set Details.
		@param TextDetails Details	  */
	public void setTextDetails (String TextDetails)
	{
		set_ValueNoCheck (COLUMNNAME_TextDetails, TextDetails);
	}

	/** Get Details.
		@return Details	  */
	public String getTextDetails () 
	{
		return (String)get_Value(COLUMNNAME_TextDetails);
	}

	/** Set Usable Life - Months.
		@param UseLifeMonths 
		Months of the usable life of the asset
	  */
	public void setUseLifeMonths (int UseLifeMonths)
	{
		set_ValueNoCheck (COLUMNNAME_UseLifeMonths, Integer.valueOf(UseLifeMonths));
	}

	/** Get Usable Life - Months.
		@return Months of the usable life of the asset
	  */
	public int getUseLifeMonths () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UseLifeMonths);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Usable Life - Years.
		@param UseLifeYears 
		Years of the usable life of the asset
	  */
	public void setUseLifeYears (int UseLifeYears)
	{
		set_ValueNoCheck (COLUMNNAME_UseLifeYears, Integer.valueOf(UseLifeYears));
	}

	/** Get Usable Life - Years.
		@return Years of the usable life of the asset
	  */
	public int getUseLifeYears () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UseLifeYears);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Use units.
		@param UseUnits 
		Currently used units of the assets
	  */
	public void setUseUnits (int UseUnits)
	{
		set_Value (COLUMNNAME_UseUnits, Integer.valueOf(UseUnits));
	}

	/** Get Use units.
		@return Currently used units of the assets
	  */
	public int getUseUnits () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UseUnits);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Version No.
		@param VersionNo 
		Version Number
	  */
	public void setVersionNo (String VersionNo)
	{
		set_ValueNoCheck (COLUMNNAME_VersionNo, VersionNo);
	}

	/** Get Version No.
		@return Version Number
	  */
	public String getVersionNo () 
	{
		return (String)get_Value(COLUMNNAME_VersionNo);
	}
}
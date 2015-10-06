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

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Tax_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_Tax_Acct extends PO implements I_C_Tax_Acct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_Tax_Acct (Properties ctx, int C_Tax_Acct_ID, String trxName)
    {
      super (ctx, C_Tax_Acct_ID, trxName);
      /** if (C_Tax_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_Tax_ID (0);
			setT_Credit_Acct (0);
			setT_Due_Acct (0);
			setT_Expense_Acct (0);
			setT_Liability_Acct (0);
			setT_Receivables_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_C_Tax_Acct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_Tax_Acct[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
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

	public I_C_Tax getC_Tax() throws RuntimeException
    {
		return (I_C_Tax)MTable.get(getCtx(), I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getT_Credit_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getT_Credit_Acct(), get_TrxName());	}

	/** Set Tax Credit.
		@param T_Credit_Acct 
		Account for Tax you can reclaim
	  */
	public void setT_Credit_Acct (int T_Credit_Acct)
	{
		set_Value (COLUMNNAME_T_Credit_Acct, Integer.valueOf(T_Credit_Acct));
	}

	/** Get Tax Credit.
		@return Account for Tax you can reclaim
	  */
	public int getT_Credit_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Credit_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getT_Due_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getT_Due_Acct(), get_TrxName());	}

	/** Set Tax Due.
		@param T_Due_Acct 
		Account for Tax you have to pay
	  */
	public void setT_Due_Acct (int T_Due_Acct)
	{
		set_Value (COLUMNNAME_T_Due_Acct, Integer.valueOf(T_Due_Acct));
	}

	/** Get Tax Due.
		@return Account for Tax you have to pay
	  */
	public int getT_Due_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Due_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getT_Expense_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getT_Expense_Acct(), get_TrxName());	}

	/** Set Tax Expense.
		@param T_Expense_Acct 
		Account for paid tax you cannot reclaim
	  */
	public void setT_Expense_Acct (int T_Expense_Acct)
	{
		set_Value (COLUMNNAME_T_Expense_Acct, Integer.valueOf(T_Expense_Acct));
	}

	/** Get Tax Expense.
		@return Account for paid tax you cannot reclaim
	  */
	public int getT_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getT_Liability_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getT_Liability_Acct(), get_TrxName());	}

	/** Set Tax Liability.
		@param T_Liability_Acct 
		Account for Tax declaration liability
	  */
	public void setT_Liability_Acct (int T_Liability_Acct)
	{
		set_Value (COLUMNNAME_T_Liability_Acct, Integer.valueOf(T_Liability_Acct));
	}

	/** Get Tax Liability.
		@return Account for Tax declaration liability
	  */
	public int getT_Liability_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Liability_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getT_Receivables_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getT_Receivables_Acct(), get_TrxName());	}

	/** Set Tax Receivables.
		@param T_Receivables_Acct 
		Account for Tax credit after tax declaration
	  */
	public void setT_Receivables_Acct (int T_Receivables_Acct)
	{
		set_Value (COLUMNNAME_T_Receivables_Acct, Integer.valueOf(T_Receivables_Acct));
	}

	/** Get Tax Receivables.
		@return Account for Tax credit after tax declaration
	  */
	public int getT_Receivables_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Receivables_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
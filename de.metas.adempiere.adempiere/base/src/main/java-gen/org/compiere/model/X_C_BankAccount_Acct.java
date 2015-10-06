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

/** Generated Model for C_BankAccount_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_BankAccount_Acct extends PO implements I_C_BankAccount_Acct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_BankAccount_Acct (Properties ctx, int C_BankAccount_Acct_ID, String trxName)
    {
      super (ctx, C_BankAccount_Acct_ID, trxName);
      /** if (C_BankAccount_Acct_ID == 0)
        {
			setB_Asset_Acct (0);
			setB_Expense_Acct (0);
			setB_InterestExp_Acct (0);
			setB_InterestRev_Acct (0);
			setB_InTransit_Acct (0);
			setB_PaymentSelect_Acct (0);
			setB_RevaluationGain_Acct (0);
			setB_RevaluationLoss_Acct (0);
			setB_SettlementGain_Acct (0);
			setB_SettlementLoss_Acct (0);
			setB_UnallocatedCash_Acct (0);
			setB_Unidentified_Acct (0);
			setC_AcctSchema_ID (0);
			setC_BankAccount_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_BankAccount_Acct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_BankAccount_Acct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ValidCombination getB_Asset_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_Asset_Acct(), get_TrxName());	}

	/** Set Bank Asset.
		@param B_Asset_Acct 
		Bank Asset Account
	  */
	public void setB_Asset_Acct (int B_Asset_Acct)
	{
		set_Value (COLUMNNAME_B_Asset_Acct, Integer.valueOf(B_Asset_Acct));
	}

	/** Get Bank Asset.
		@return Bank Asset Account
	  */
	public int getB_Asset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_Asset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_Expense_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_Expense_Acct(), get_TrxName());	}

	/** Set Bank Expense.
		@param B_Expense_Acct 
		Bank Expense Account
	  */
	public void setB_Expense_Acct (int B_Expense_Acct)
	{
		set_Value (COLUMNNAME_B_Expense_Acct, Integer.valueOf(B_Expense_Acct));
	}

	/** Get Bank Expense.
		@return Bank Expense Account
	  */
	public int getB_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_InterestExp_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_InterestExp_Acct(), get_TrxName());	}

	/** Set Bank Interest Expense.
		@param B_InterestExp_Acct 
		Bank Interest Expense Account
	  */
	public void setB_InterestExp_Acct (int B_InterestExp_Acct)
	{
		set_Value (COLUMNNAME_B_InterestExp_Acct, Integer.valueOf(B_InterestExp_Acct));
	}

	/** Get Bank Interest Expense.
		@return Bank Interest Expense Account
	  */
	public int getB_InterestExp_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_InterestExp_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_InterestRev_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_InterestRev_Acct(), get_TrxName());	}

	/** Set Bank Interest Revenue.
		@param B_InterestRev_Acct 
		Bank Interest Revenue Account
	  */
	public void setB_InterestRev_Acct (int B_InterestRev_Acct)
	{
		set_Value (COLUMNNAME_B_InterestRev_Acct, Integer.valueOf(B_InterestRev_Acct));
	}

	/** Get Bank Interest Revenue.
		@return Bank Interest Revenue Account
	  */
	public int getB_InterestRev_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_InterestRev_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_InTransit_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_InTransit_Acct(), get_TrxName());	}

	/** Set Bank In Transit.
		@param B_InTransit_Acct 
		Bank In Transit Account
	  */
	public void setB_InTransit_Acct (int B_InTransit_Acct)
	{
		set_Value (COLUMNNAME_B_InTransit_Acct, Integer.valueOf(B_InTransit_Acct));
	}

	/** Get Bank In Transit.
		@return Bank In Transit Account
	  */
	public int getB_InTransit_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_InTransit_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_PaymentSelect_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_PaymentSelect_Acct(), get_TrxName());	}

	/** Set Payment Selection.
		@param B_PaymentSelect_Acct 
		AP Payment Selection Clearing Account
	  */
	public void setB_PaymentSelect_Acct (int B_PaymentSelect_Acct)
	{
		set_Value (COLUMNNAME_B_PaymentSelect_Acct, Integer.valueOf(B_PaymentSelect_Acct));
	}

	/** Get Payment Selection.
		@return AP Payment Selection Clearing Account
	  */
	public int getB_PaymentSelect_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_PaymentSelect_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_RevaluationGain_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_RevaluationGain_Acct(), get_TrxName());	}

	/** Set Bank Revaluation Gain.
		@param B_RevaluationGain_Acct 
		Bank Revaluation Gain Account
	  */
	public void setB_RevaluationGain_Acct (int B_RevaluationGain_Acct)
	{
		set_Value (COLUMNNAME_B_RevaluationGain_Acct, Integer.valueOf(B_RevaluationGain_Acct));
	}

	/** Get Bank Revaluation Gain.
		@return Bank Revaluation Gain Account
	  */
	public int getB_RevaluationGain_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_RevaluationGain_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_RevaluationLoss_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_RevaluationLoss_Acct(), get_TrxName());	}

	/** Set Bank Revaluation Loss.
		@param B_RevaluationLoss_Acct 
		Bank Revaluation Loss Account
	  */
	public void setB_RevaluationLoss_Acct (int B_RevaluationLoss_Acct)
	{
		set_Value (COLUMNNAME_B_RevaluationLoss_Acct, Integer.valueOf(B_RevaluationLoss_Acct));
	}

	/** Get Bank Revaluation Loss.
		@return Bank Revaluation Loss Account
	  */
	public int getB_RevaluationLoss_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_RevaluationLoss_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_SettlementGain_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_SettlementGain_Acct(), get_TrxName());	}

	/** Set Bank Settlement Gain.
		@param B_SettlementGain_Acct 
		Bank Settlement Gain Account
	  */
	public void setB_SettlementGain_Acct (int B_SettlementGain_Acct)
	{
		set_Value (COLUMNNAME_B_SettlementGain_Acct, Integer.valueOf(B_SettlementGain_Acct));
	}

	/** Get Bank Settlement Gain.
		@return Bank Settlement Gain Account
	  */
	public int getB_SettlementGain_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_SettlementGain_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_SettlementLoss_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_SettlementLoss_Acct(), get_TrxName());	}

	/** Set Bank Settlement Loss.
		@param B_SettlementLoss_Acct 
		Bank Settlement Loss Account
	  */
	public void setB_SettlementLoss_Acct (int B_SettlementLoss_Acct)
	{
		set_Value (COLUMNNAME_B_SettlementLoss_Acct, Integer.valueOf(B_SettlementLoss_Acct));
	}

	/** Get Bank Settlement Loss.
		@return Bank Settlement Loss Account
	  */
	public int getB_SettlementLoss_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_SettlementLoss_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_UnallocatedCash_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_UnallocatedCash_Acct(), get_TrxName());	}

	/** Set Unallocated Cash.
		@param B_UnallocatedCash_Acct 
		Unallocated Cash Clearing Account
	  */
	public void setB_UnallocatedCash_Acct (int B_UnallocatedCash_Acct)
	{
		set_Value (COLUMNNAME_B_UnallocatedCash_Acct, Integer.valueOf(B_UnallocatedCash_Acct));
	}

	/** Get Unallocated Cash.
		@return Unallocated Cash Clearing Account
	  */
	public int getB_UnallocatedCash_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_UnallocatedCash_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getB_Unidentified_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getB_Unidentified_Acct(), get_TrxName());	}

	/** Set Bank Unidentified Receipts.
		@param B_Unidentified_Acct 
		Bank Unidentified Receipts Account
	  */
	public void setB_Unidentified_Acct (int B_Unidentified_Acct)
	{
		set_Value (COLUMNNAME_B_Unidentified_Acct, Integer.valueOf(B_Unidentified_Acct));
	}

	/** Get Bank Unidentified Receipts.
		@return Bank Unidentified Receipts Account
	  */
	public int getB_Unidentified_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_Unidentified_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (I_C_BankAccount)MTable.get(getCtx(), I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
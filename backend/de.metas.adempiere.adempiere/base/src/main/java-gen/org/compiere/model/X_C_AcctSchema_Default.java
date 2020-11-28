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

/** Generated Model for C_AcctSchema_Default
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_AcctSchema_Default extends org.compiere.model.PO implements I_C_AcctSchema_Default, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 819944387L;

    /** Standard Constructor */
    public X_C_AcctSchema_Default (Properties ctx, int C_AcctSchema_Default_ID, String trxName)
    {
      super (ctx, C_AcctSchema_Default_ID, trxName);
      /** if (C_AcctSchema_Default_ID == 0)
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
			setC_Prepayment_Acct (0);
			setC_Receivable_Acct (0);
			setC_Receivable_Services_Acct (0);
			setCB_Asset_Acct (0);
			setCB_CashTransfer_Acct (0);
			setCB_Differences_Acct (0);
			setCB_Expense_Acct (0);
			setCB_Receipt_Acct (0);
			setCh_Expense_Acct (0);
			setCh_Revenue_Acct (0);
			setE_Expense_Acct (0);
			setE_Prepayment_Acct (0);
			setNotInvoicedReceipts_Acct (0);
			setNotInvoicedReceivables_Acct (0);
			setNotInvoicedRevenue_Acct (0);
			setP_Asset_Acct (0);
			setP_Burden_Acct (0);
			setP_COGS_Acct (0);
			setP_CostAdjustment_Acct (0);
			setP_CostOfProduction_Acct (0);
			setP_Expense_Acct (0);
			setP_FloorStock_Acct (0);
			setP_InventoryClearing_Acct (0);
			setP_InvoicePriceVariance_Acct (0);
			setP_Labor_Acct (0);
			setP_MethodChangeVariance_Acct (0);
			setP_MixVariance_Acct (0);
			setP_OutsideProcessing_Acct (0);
			setP_Overhead_Acct (0);
			setP_PurchasePriceVariance_Acct (0);
			setP_RateVariance_Acct (0);
			setP_Revenue_Acct (0);
			setP_Scrap_Acct (0);
			setP_TradeDiscountGrant_Acct (0);
			setP_TradeDiscountRec_Acct (0);
			setP_UsageVariance_Acct (0);
			setP_WIP_Acct (0);
			setPayDiscount_Exp_Acct (0);
			setPayDiscount_Rev_Acct (0);
			setPJ_Asset_Acct (0);
			setPJ_WIP_Acct (0);
			setRealizedGain_Acct (0);
			setRealizedLoss_Acct (0);
			setT_Credit_Acct (0);
			setT_Due_Acct (0);
			setT_Expense_Acct (0);
			setT_Liability_Acct (0);
			setT_Receivables_Acct (0);
			setUnEarnedRevenue_Acct (0);
			setUnrealizedGain_Acct (0);
			setUnrealizedLoss_Acct (0);
			setV_Liability_Acct (0);
			setV_Liability_Services_Acct (0);
			setV_Prepayment_Acct (0);
			setW_Differences_Acct (0);
			setW_InvActualAdjust_Acct (0);
			setW_Inventory_Acct (0);
			setW_Revaluation_Acct (0);
			setWithholding_Acct (0);
			setWriteOff_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_C_AcctSchema_Default (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_C_ValidCombination getB_Asset_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_Asset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_Asset_A(org.compiere.model.I_C_ValidCombination B_Asset_A)
	{
		set_ValueFromPO(COLUMNNAME_B_Asset_Acct, org.compiere.model.I_C_ValidCombination.class, B_Asset_A);
	}

	/** Set Bank.
		@param B_Asset_Acct 
		Bank Konto
	  */
	@Override
	public void setB_Asset_Acct (int B_Asset_Acct)
	{
		set_Value (COLUMNNAME_B_Asset_Acct, Integer.valueOf(B_Asset_Acct));
	}

	/** Get Bank.
		@return Bank Konto
	  */
	@Override
	public int getB_Asset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_Asset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_Expense_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_Expense_A(org.compiere.model.I_C_ValidCombination B_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_B_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, B_Expense_A);
	}

	/** Set Nebenkosten des Geldverkehrs.
		@param B_Expense_Acct 
		Konto für Nebenkosten des Geldverkehrs
	  */
	@Override
	public void setB_Expense_Acct (int B_Expense_Acct)
	{
		set_Value (COLUMNNAME_B_Expense_Acct, Integer.valueOf(B_Expense_Acct));
	}

	/** Get Nebenkosten des Geldverkehrs.
		@return Konto für Nebenkosten des Geldverkehrs
	  */
	@Override
	public int getB_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_InterestExp_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_InterestExp_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_InterestExp_A(org.compiere.model.I_C_ValidCombination B_InterestExp_A)
	{
		set_ValueFromPO(COLUMNNAME_B_InterestExp_Acct, org.compiere.model.I_C_ValidCombination.class, B_InterestExp_A);
	}

	/** Set Zins Aufwendungen.
		@param B_InterestExp_Acct 
		Konto für Zins Aufwendungen
	  */
	@Override
	public void setB_InterestExp_Acct (int B_InterestExp_Acct)
	{
		set_Value (COLUMNNAME_B_InterestExp_Acct, Integer.valueOf(B_InterestExp_Acct));
	}

	/** Get Zins Aufwendungen.
		@return Konto für Zins Aufwendungen
	  */
	@Override
	public int getB_InterestExp_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_InterestExp_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_InterestRev_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_InterestRev_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_InterestRev_A(org.compiere.model.I_C_ValidCombination B_InterestRev_A)
	{
		set_ValueFromPO(COLUMNNAME_B_InterestRev_Acct, org.compiere.model.I_C_ValidCombination.class, B_InterestRev_A);
	}

	/** Set Zinserträge.
		@param B_InterestRev_Acct 
		Konto für Zinserträge
	  */
	@Override
	public void setB_InterestRev_Acct (int B_InterestRev_Acct)
	{
		set_Value (COLUMNNAME_B_InterestRev_Acct, Integer.valueOf(B_InterestRev_Acct));
	}

	/** Get Zinserträge.
		@return Konto für Zinserträge
	  */
	@Override
	public int getB_InterestRev_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_InterestRev_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_InTransit_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_InTransit_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_InTransit_A(org.compiere.model.I_C_ValidCombination B_InTransit_A)
	{
		set_ValueFromPO(COLUMNNAME_B_InTransit_Acct, org.compiere.model.I_C_ValidCombination.class, B_InTransit_A);
	}

	/** Set Bank In Transit.
		@param B_InTransit_Acct 
		Konto für Bank In Transit
	  */
	@Override
	public void setB_InTransit_Acct (int B_InTransit_Acct)
	{
		set_Value (COLUMNNAME_B_InTransit_Acct, Integer.valueOf(B_InTransit_Acct));
	}

	/** Get Bank In Transit.
		@return Konto für Bank In Transit
	  */
	@Override
	public int getB_InTransit_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_InTransit_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_PaymentSelect_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_PaymentSelect_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_PaymentSelect_A(org.compiere.model.I_C_ValidCombination B_PaymentSelect_A)
	{
		set_ValueFromPO(COLUMNNAME_B_PaymentSelect_Acct, org.compiere.model.I_C_ValidCombination.class, B_PaymentSelect_A);
	}

	/** Set Bezahlung selektiert.
		@param B_PaymentSelect_Acct 
		Konto für selektierte Zahlungen
	  */
	@Override
	public void setB_PaymentSelect_Acct (int B_PaymentSelect_Acct)
	{
		set_Value (COLUMNNAME_B_PaymentSelect_Acct, Integer.valueOf(B_PaymentSelect_Acct));
	}

	/** Get Bezahlung selektiert.
		@return Konto für selektierte Zahlungen
	  */
	@Override
	public int getB_PaymentSelect_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_PaymentSelect_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_RevaluationGain_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_RevaluationGain_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_RevaluationGain_A(org.compiere.model.I_C_ValidCombination B_RevaluationGain_A)
	{
		set_ValueFromPO(COLUMNNAME_B_RevaluationGain_Acct, org.compiere.model.I_C_ValidCombination.class, B_RevaluationGain_A);
	}

	/** Set Erträge aus Kursdifferenzen.
		@param B_RevaluationGain_Acct 
		Konto für Erträge aus Kursdifferenzen
	  */
	@Override
	public void setB_RevaluationGain_Acct (int B_RevaluationGain_Acct)
	{
		set_Value (COLUMNNAME_B_RevaluationGain_Acct, Integer.valueOf(B_RevaluationGain_Acct));
	}

	/** Get Erträge aus Kursdifferenzen.
		@return Konto für Erträge aus Kursdifferenzen
	  */
	@Override
	public int getB_RevaluationGain_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_RevaluationGain_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_RevaluationLoss_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_RevaluationLoss_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_RevaluationLoss_A(org.compiere.model.I_C_ValidCombination B_RevaluationLoss_A)
	{
		set_ValueFromPO(COLUMNNAME_B_RevaluationLoss_Acct, org.compiere.model.I_C_ValidCombination.class, B_RevaluationLoss_A);
	}

	/** Set Währungsverluste.
		@param B_RevaluationLoss_Acct 
		Konto für Währungsverluste
	  */
	@Override
	public void setB_RevaluationLoss_Acct (int B_RevaluationLoss_Acct)
	{
		set_Value (COLUMNNAME_B_RevaluationLoss_Acct, Integer.valueOf(B_RevaluationLoss_Acct));
	}

	/** Get Währungsverluste.
		@return Konto für Währungsverluste
	  */
	@Override
	public int getB_RevaluationLoss_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_RevaluationLoss_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_SettlementGain_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_SettlementGain_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_SettlementGain_A(org.compiere.model.I_C_ValidCombination B_SettlementGain_A)
	{
		set_ValueFromPO(COLUMNNAME_B_SettlementGain_Acct, org.compiere.model.I_C_ValidCombination.class, B_SettlementGain_A);
	}

	/** Set Bank Settlement Gain.
		@param B_SettlementGain_Acct 
		Bank Settlement Gain Account
	  */
	@Override
	public void setB_SettlementGain_Acct (int B_SettlementGain_Acct)
	{
		set_Value (COLUMNNAME_B_SettlementGain_Acct, Integer.valueOf(B_SettlementGain_Acct));
	}

	/** Get Bank Settlement Gain.
		@return Bank Settlement Gain Account
	  */
	@Override
	public int getB_SettlementGain_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_SettlementGain_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_SettlementLoss_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_SettlementLoss_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_SettlementLoss_A(org.compiere.model.I_C_ValidCombination B_SettlementLoss_A)
	{
		set_ValueFromPO(COLUMNNAME_B_SettlementLoss_Acct, org.compiere.model.I_C_ValidCombination.class, B_SettlementLoss_A);
	}

	/** Set Bank Settlement Loss.
		@param B_SettlementLoss_Acct 
		Bank Settlement Loss Account
	  */
	@Override
	public void setB_SettlementLoss_Acct (int B_SettlementLoss_Acct)
	{
		set_Value (COLUMNNAME_B_SettlementLoss_Acct, Integer.valueOf(B_SettlementLoss_Acct));
	}

	/** Get Bank Settlement Loss.
		@return Bank Settlement Loss Account
	  */
	@Override
	public int getB_SettlementLoss_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_SettlementLoss_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_UnallocatedCash_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_UnallocatedCash_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_UnallocatedCash_A(org.compiere.model.I_C_ValidCombination B_UnallocatedCash_A)
	{
		set_ValueFromPO(COLUMNNAME_B_UnallocatedCash_Acct, org.compiere.model.I_C_ValidCombination.class, B_UnallocatedCash_A);
	}

	/** Set Nicht zugeordnete Zahlungen.
		@param B_UnallocatedCash_Acct 
		Konto für nicht zugeordnete Zahlungen
	  */
	@Override
	public void setB_UnallocatedCash_Acct (int B_UnallocatedCash_Acct)
	{
		set_Value (COLUMNNAME_B_UnallocatedCash_Acct, Integer.valueOf(B_UnallocatedCash_Acct));
	}

	/** Get Nicht zugeordnete Zahlungen.
		@return Konto für nicht zugeordnete Zahlungen
	  */
	@Override
	public int getB_UnallocatedCash_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_UnallocatedCash_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_Unidentified_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_B_Unidentified_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_Unidentified_A(org.compiere.model.I_C_ValidCombination B_Unidentified_A)
	{
		set_ValueFromPO(COLUMNNAME_B_Unidentified_Acct, org.compiere.model.I_C_ValidCombination.class, B_Unidentified_A);
	}

	/** Set Bank Unidentified Receipts.
		@param B_Unidentified_Acct 
		Bank Unidentified Receipts Account
	  */
	@Override
	public void setB_Unidentified_Acct (int B_Unidentified_Acct)
	{
		set_Value (COLUMNNAME_B_Unidentified_Acct, Integer.valueOf(B_Unidentified_Acct));
	}

	/** Get Bank Unidentified Receipts.
		@return Bank Unidentified Receipts Account
	  */
	@Override
	public int getB_Unidentified_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_Unidentified_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	/** Set Buchführungs-Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Buchführungs-Schema.
		@return Rules for accounting
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getC_Prepayment_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setC_Prepayment_A(org.compiere.model.I_C_ValidCombination C_Prepayment_A)
	{
		set_ValueFromPO(COLUMNNAME_C_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class, C_Prepayment_A);
	}

	/** Set Erhaltene Anzahlungen.
		@param C_Prepayment_Acct 
		Konto für Erhaltene Anzahlungen
	  */
	@Override
	public void setC_Prepayment_Acct (int C_Prepayment_Acct)
	{
		set_Value (COLUMNNAME_C_Prepayment_Acct, Integer.valueOf(C_Prepayment_Acct));
	}

	/** Get Erhaltene Anzahlungen.
		@return Konto für Erhaltene Anzahlungen
	  */
	@Override
	public int getC_Prepayment_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Prepayment_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getC_Receivable_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Receivable_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setC_Receivable_A(org.compiere.model.I_C_ValidCombination C_Receivable_A)
	{
		set_ValueFromPO(COLUMNNAME_C_Receivable_Acct, org.compiere.model.I_C_ValidCombination.class, C_Receivable_A);
	}

	/** Set Forderungen aus Lieferungen.
		@param C_Receivable_Acct 
		Konto für Forderungen aus Lieferungen (und Leistungen)
	  */
	@Override
	public void setC_Receivable_Acct (int C_Receivable_Acct)
	{
		set_Value (COLUMNNAME_C_Receivable_Acct, Integer.valueOf(C_Receivable_Acct));
	}

	/** Get Forderungen aus Lieferungen.
		@return Konto für Forderungen aus Lieferungen (und Leistungen)
	  */
	@Override
	public int getC_Receivable_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Receivable_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getC_Receivable_Services_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Receivable_Services_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setC_Receivable_Services_A(org.compiere.model.I_C_ValidCombination C_Receivable_Services_A)
	{
		set_ValueFromPO(COLUMNNAME_C_Receivable_Services_Acct, org.compiere.model.I_C_ValidCombination.class, C_Receivable_Services_A);
	}

	/** Set Forderungen aus Dienstleistungen.
		@param C_Receivable_Services_Acct 
		Konto für Forderungen aus Dienstleistungen
	  */
	@Override
	public void setC_Receivable_Services_Acct (int C_Receivable_Services_Acct)
	{
		set_Value (COLUMNNAME_C_Receivable_Services_Acct, Integer.valueOf(C_Receivable_Services_Acct));
	}

	/** Get Forderungen aus Dienstleistungen.
		@return Konto für Forderungen aus Dienstleistungen
	  */
	@Override
	public int getC_Receivable_Services_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Receivable_Services_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCB_Asset_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CB_Asset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCB_Asset_A(org.compiere.model.I_C_ValidCombination CB_Asset_A)
	{
		set_ValueFromPO(COLUMNNAME_CB_Asset_Acct, org.compiere.model.I_C_ValidCombination.class, CB_Asset_A);
	}

	/** Set Kasse.
		@param CB_Asset_Acct 
		Konto für Kasse
	  */
	@Override
	public void setCB_Asset_Acct (int CB_Asset_Acct)
	{
		set_Value (COLUMNNAME_CB_Asset_Acct, Integer.valueOf(CB_Asset_Acct));
	}

	/** Get Kasse.
		@return Konto für Kasse
	  */
	@Override
	public int getCB_Asset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CB_Asset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCB_CashTransfer_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CB_CashTransfer_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCB_CashTransfer_A(org.compiere.model.I_C_ValidCombination CB_CashTransfer_A)
	{
		set_ValueFromPO(COLUMNNAME_CB_CashTransfer_Acct, org.compiere.model.I_C_ValidCombination.class, CB_CashTransfer_A);
	}

	/** Set Geldtransit.
		@param CB_CashTransfer_Acct 
		Konto für Geldtransit
	  */
	@Override
	public void setCB_CashTransfer_Acct (int CB_CashTransfer_Acct)
	{
		set_Value (COLUMNNAME_CB_CashTransfer_Acct, Integer.valueOf(CB_CashTransfer_Acct));
	}

	/** Get Geldtransit.
		@return Konto für Geldtransit
	  */
	@Override
	public int getCB_CashTransfer_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CB_CashTransfer_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCB_Differences_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CB_Differences_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCB_Differences_A(org.compiere.model.I_C_ValidCombination CB_Differences_A)
	{
		set_ValueFromPO(COLUMNNAME_CB_Differences_Acct, org.compiere.model.I_C_ValidCombination.class, CB_Differences_A);
	}

	/** Set Kassendifferenz.
		@param CB_Differences_Acct 
		Konto für Kassendifferenz
	  */
	@Override
	public void setCB_Differences_Acct (int CB_Differences_Acct)
	{
		set_Value (COLUMNNAME_CB_Differences_Acct, Integer.valueOf(CB_Differences_Acct));
	}

	/** Get Kassendifferenz.
		@return Konto für Kassendifferenz
	  */
	@Override
	public int getCB_Differences_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CB_Differences_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCB_Expense_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CB_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCB_Expense_A(org.compiere.model.I_C_ValidCombination CB_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_CB_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, CB_Expense_A);
	}

	/** Set Kasse Aufwand.
		@param CB_Expense_Acct 
		Konto für Kasse Aufwand
	  */
	@Override
	public void setCB_Expense_Acct (int CB_Expense_Acct)
	{
		set_Value (COLUMNNAME_CB_Expense_Acct, Integer.valueOf(CB_Expense_Acct));
	}

	/** Get Kasse Aufwand.
		@return Konto für Kasse Aufwand
	  */
	@Override
	public int getCB_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CB_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCB_Receipt_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CB_Receipt_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCB_Receipt_A(org.compiere.model.I_C_ValidCombination CB_Receipt_A)
	{
		set_ValueFromPO(COLUMNNAME_CB_Receipt_Acct, org.compiere.model.I_C_ValidCombination.class, CB_Receipt_A);
	}

	/** Set Kasse Ertrag.
		@param CB_Receipt_Acct 
		Konto für Kasse Ertrag
	  */
	@Override
	public void setCB_Receipt_Acct (int CB_Receipt_Acct)
	{
		set_Value (COLUMNNAME_CB_Receipt_Acct, Integer.valueOf(CB_Receipt_Acct));
	}

	/** Get Kasse Ertrag.
		@return Konto für Kasse Ertrag
	  */
	@Override
	public int getCB_Receipt_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CB_Receipt_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCh_Expense_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Ch_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCh_Expense_A(org.compiere.model.I_C_ValidCombination Ch_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_Ch_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, Ch_Expense_A);
	}

	/** Set Betriebliche Aufwendungen.
		@param Ch_Expense_Acct 
		Konto für Betriebliche Aufwendungen
	  */
	@Override
	public void setCh_Expense_Acct (int Ch_Expense_Acct)
	{
		set_Value (COLUMNNAME_Ch_Expense_Acct, Integer.valueOf(Ch_Expense_Acct));
	}

	/** Get Betriebliche Aufwendungen.
		@return Konto für Betriebliche Aufwendungen
	  */
	@Override
	public int getCh_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ch_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCh_Revenue_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Ch_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCh_Revenue_A(org.compiere.model.I_C_ValidCombination Ch_Revenue_A)
	{
		set_ValueFromPO(COLUMNNAME_Ch_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class, Ch_Revenue_A);
	}

	/** Set Sonstige Einnahmen.
		@param Ch_Revenue_Acct 
		Konto für Sonstige Einnahmen
	  */
	@Override
	public void setCh_Revenue_Acct (int Ch_Revenue_Acct)
	{
		set_Value (COLUMNNAME_Ch_Revenue_Acct, Integer.valueOf(Ch_Revenue_Acct));
	}

	/** Get Sonstige Einnahmen.
		@return Konto für Sonstige Einnahmen
	  */
	@Override
	public int getCh_Revenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ch_Revenue_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getE_Expense_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_E_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setE_Expense_A(org.compiere.model.I_C_ValidCombination E_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_E_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, E_Expense_A);
	}

	/** Set Löhne und Gehälter.
		@param E_Expense_Acct 
		Konto für Löhne und Gehälter
	  */
	@Override
	public void setE_Expense_Acct (int E_Expense_Acct)
	{
		set_Value (COLUMNNAME_E_Expense_Acct, Integer.valueOf(E_Expense_Acct));
	}

	/** Get Löhne und Gehälter.
		@return Konto für Löhne und Gehälter
	  */
	@Override
	public int getE_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_E_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getE_Prepayment_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_E_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setE_Prepayment_A(org.compiere.model.I_C_ValidCombination E_Prepayment_A)
	{
		set_ValueFromPO(COLUMNNAME_E_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class, E_Prepayment_A);
	}

	/** Set Geleistete Anzahlungen Mitarbeiter.
		@param E_Prepayment_Acct 
		Konto für geleistete Anzahlungen an Mitarbeiter
	  */
	@Override
	public void setE_Prepayment_Acct (int E_Prepayment_Acct)
	{
		set_Value (COLUMNNAME_E_Prepayment_Acct, Integer.valueOf(E_Prepayment_Acct));
	}

	/** Get Geleistete Anzahlungen Mitarbeiter.
		@return Konto für geleistete Anzahlungen an Mitarbeiter
	  */
	@Override
	public int getE_Prepayment_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_E_Prepayment_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getNotInvoicedReceipts_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_NotInvoicedReceipts_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setNotInvoicedReceipts_A(org.compiere.model.I_C_ValidCombination NotInvoicedReceipts_A)
	{
		set_ValueFromPO(COLUMNNAME_NotInvoicedReceipts_Acct, org.compiere.model.I_C_ValidCombination.class, NotInvoicedReceipts_A);
	}

	/** Set Not-invoiced Receipts.
		@param NotInvoicedReceipts_Acct 
		Account for not-invoiced Material Receipts
	  */
	@Override
	public void setNotInvoicedReceipts_Acct (int NotInvoicedReceipts_Acct)
	{
		set_Value (COLUMNNAME_NotInvoicedReceipts_Acct, Integer.valueOf(NotInvoicedReceipts_Acct));
	}

	/** Get Not-invoiced Receipts.
		@return Account for not-invoiced Material Receipts
	  */
	@Override
	public int getNotInvoicedReceipts_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NotInvoicedReceipts_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getNotInvoicedReceivables_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_NotInvoicedReceivables_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setNotInvoicedReceivables_A(org.compiere.model.I_C_ValidCombination NotInvoicedReceivables_A)
	{
		set_ValueFromPO(COLUMNNAME_NotInvoicedReceivables_Acct, org.compiere.model.I_C_ValidCombination.class, NotInvoicedReceivables_A);
	}

	/** Set Unfertige Erzeugnisse.
		@param NotInvoicedReceivables_Acct 
		Konto für unfertige Erzeugnisse
	  */
	@Override
	public void setNotInvoicedReceivables_Acct (int NotInvoicedReceivables_Acct)
	{
		set_Value (COLUMNNAME_NotInvoicedReceivables_Acct, Integer.valueOf(NotInvoicedReceivables_Acct));
	}

	/** Get Unfertige Erzeugnisse.
		@return Konto für unfertige Erzeugnisse
	  */
	@Override
	public int getNotInvoicedReceivables_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NotInvoicedReceivables_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getNotInvoicedRevenue_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_NotInvoicedRevenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setNotInvoicedRevenue_A(org.compiere.model.I_C_ValidCombination NotInvoicedRevenue_A)
	{
		set_ValueFromPO(COLUMNNAME_NotInvoicedRevenue_Acct, org.compiere.model.I_C_ValidCombination.class, NotInvoicedRevenue_A);
	}

	/** Set Nicht abgerechnete Einnahmen.
		@param NotInvoicedRevenue_Acct 
		Konto für nicht abgerechnete Einnahmen
	  */
	@Override
	public void setNotInvoicedRevenue_Acct (int NotInvoicedRevenue_Acct)
	{
		set_Value (COLUMNNAME_NotInvoicedRevenue_Acct, Integer.valueOf(NotInvoicedRevenue_Acct));
	}

	/** Get Nicht abgerechnete Einnahmen.
		@return Konto für nicht abgerechnete Einnahmen
	  */
	@Override
	public int getNotInvoicedRevenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NotInvoicedRevenue_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Asset_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Asset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Asset_A(org.compiere.model.I_C_ValidCombination P_Asset_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Asset_Acct, org.compiere.model.I_C_ValidCombination.class, P_Asset_A);
	}

	/** Set Warenbestand.
		@param P_Asset_Acct 
		Konto für Warenbestand
	  */
	@Override
	public void setP_Asset_Acct (int P_Asset_Acct)
	{
		set_Value (COLUMNNAME_P_Asset_Acct, Integer.valueOf(P_Asset_Acct));
	}

	/** Get Warenbestand.
		@return Konto für Warenbestand
	  */
	@Override
	public int getP_Asset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Asset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Burden_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Burden_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Burden_A(org.compiere.model.I_C_ValidCombination P_Burden_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Burden_Acct, org.compiere.model.I_C_ValidCombination.class, P_Burden_A);
	}

	/** Set Burden.
		@param P_Burden_Acct 
		The Burden account is the account used Manufacturing Order
	  */
	@Override
	public void setP_Burden_Acct (int P_Burden_Acct)
	{
		set_Value (COLUMNNAME_P_Burden_Acct, Integer.valueOf(P_Burden_Acct));
	}

	/** Get Burden.
		@return The Burden account is the account used Manufacturing Order
	  */
	@Override
	public int getP_Burden_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Burden_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_COGS_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_COGS_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_COGS_A(org.compiere.model.I_C_ValidCombination P_COGS_A)
	{
		set_ValueFromPO(COLUMNNAME_P_COGS_Acct, org.compiere.model.I_C_ValidCombination.class, P_COGS_A);
	}

	/** Set Produkt Vertriebsausgaben.
		@param P_COGS_Acct 
		Konto für Produkt Vertriebsausgaben
	  */
	@Override
	public void setP_COGS_Acct (int P_COGS_Acct)
	{
		set_Value (COLUMNNAME_P_COGS_Acct, Integer.valueOf(P_COGS_Acct));
	}

	/** Get Produkt Vertriebsausgaben.
		@return Konto für Produkt Vertriebsausgaben
	  */
	@Override
	public int getP_COGS_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_COGS_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_CostAdjustment_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_CostAdjustment_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_CostAdjustment_A(org.compiere.model.I_C_ValidCombination P_CostAdjustment_A)
	{
		set_ValueFromPO(COLUMNNAME_P_CostAdjustment_Acct, org.compiere.model.I_C_ValidCombination.class, P_CostAdjustment_A);
	}

	/** Set Bezugsnebenkosten.
		@param P_CostAdjustment_Acct 
		Konto für Bezugsnebenkosten
	  */
	@Override
	public void setP_CostAdjustment_Acct (int P_CostAdjustment_Acct)
	{
		set_Value (COLUMNNAME_P_CostAdjustment_Acct, Integer.valueOf(P_CostAdjustment_Acct));
	}

	/** Get Bezugsnebenkosten.
		@return Konto für Bezugsnebenkosten
	  */
	@Override
	public int getP_CostAdjustment_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_CostAdjustment_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_CostOfProduction_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_CostOfProduction_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_CostOfProduction_A(org.compiere.model.I_C_ValidCombination P_CostOfProduction_A)
	{
		set_ValueFromPO(COLUMNNAME_P_CostOfProduction_Acct, org.compiere.model.I_C_ValidCombination.class, P_CostOfProduction_A);
	}

	/** Set Cost Of Production.
		@param P_CostOfProduction_Acct 
		The Cost Of Production account is the account used Manufacturing Order
	  */
	@Override
	public void setP_CostOfProduction_Acct (int P_CostOfProduction_Acct)
	{
		set_Value (COLUMNNAME_P_CostOfProduction_Acct, Integer.valueOf(P_CostOfProduction_Acct));
	}

	/** Get Cost Of Production.
		@return The Cost Of Production account is the account used Manufacturing Order
	  */
	@Override
	public int getP_CostOfProduction_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_CostOfProduction_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Expense_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Expense_A(org.compiere.model.I_C_ValidCombination P_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, P_Expense_A);
	}

	/** Set Produkt Aufwand.
		@param P_Expense_Acct 
		Konto für Produkt Aufwand
	  */
	@Override
	public void setP_Expense_Acct (int P_Expense_Acct)
	{
		set_Value (COLUMNNAME_P_Expense_Acct, Integer.valueOf(P_Expense_Acct));
	}

	/** Get Produkt Aufwand.
		@return Konto für Produkt Aufwand
	  */
	@Override
	public int getP_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_FloorStock_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_FloorStock_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_FloorStock_A(org.compiere.model.I_C_ValidCombination P_FloorStock_A)
	{
		set_ValueFromPO(COLUMNNAME_P_FloorStock_Acct, org.compiere.model.I_C_ValidCombination.class, P_FloorStock_A);
	}

	/** Set Floor Stock.
		@param P_FloorStock_Acct 
		The Floor Stock account is the account used Manufacturing Order
	  */
	@Override
	public void setP_FloorStock_Acct (int P_FloorStock_Acct)
	{
		set_Value (COLUMNNAME_P_FloorStock_Acct, Integer.valueOf(P_FloorStock_Acct));
	}

	/** Get Floor Stock.
		@return The Floor Stock account is the account used Manufacturing Order
	  */
	@Override
	public int getP_FloorStock_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_FloorStock_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_InventoryClearing_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_InventoryClearing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_InventoryClearing_A(org.compiere.model.I_C_ValidCombination P_InventoryClearing_A)
	{
		set_ValueFromPO(COLUMNNAME_P_InventoryClearing_Acct, org.compiere.model.I_C_ValidCombination.class, P_InventoryClearing_A);
	}

	/** Set Inventory Clearing.
		@param P_InventoryClearing_Acct 
		Product Inventory Clearing Account
	  */
	@Override
	public void setP_InventoryClearing_Acct (int P_InventoryClearing_Acct)
	{
		set_Value (COLUMNNAME_P_InventoryClearing_Acct, Integer.valueOf(P_InventoryClearing_Acct));
	}

	/** Get Inventory Clearing.
		@return Product Inventory Clearing Account
	  */
	@Override
	public int getP_InventoryClearing_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_InventoryClearing_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_InvoicePriceVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_InvoicePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_InvoicePriceVariance_A(org.compiere.model.I_C_ValidCombination P_InvoicePriceVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_InvoicePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_InvoicePriceVariance_A);
	}

	/** Set Preisdifferenz Einkauf Rechnung.
		@param P_InvoicePriceVariance_Acct 
		Konto für Preisdifferenz Einkauf Rechnung
	  */
	@Override
	public void setP_InvoicePriceVariance_Acct (int P_InvoicePriceVariance_Acct)
	{
		set_Value (COLUMNNAME_P_InvoicePriceVariance_Acct, Integer.valueOf(P_InvoicePriceVariance_Acct));
	}

	/** Get Preisdifferenz Einkauf Rechnung.
		@return Konto für Preisdifferenz Einkauf Rechnung
	  */
	@Override
	public int getP_InvoicePriceVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_InvoicePriceVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Labor_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Labor_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Labor_A(org.compiere.model.I_C_ValidCombination P_Labor_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Labor_Acct, org.compiere.model.I_C_ValidCombination.class, P_Labor_A);
	}

	/** Set Labor.
		@param P_Labor_Acct 
		The Labor account is the account used Manufacturing Order
	  */
	@Override
	public void setP_Labor_Acct (int P_Labor_Acct)
	{
		set_Value (COLUMNNAME_P_Labor_Acct, Integer.valueOf(P_Labor_Acct));
	}

	/** Get Labor.
		@return The Labor account is the account used Manufacturing Order
	  */
	@Override
	public int getP_Labor_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Labor_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_MethodChangeVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_MethodChangeVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_MethodChangeVariance_A(org.compiere.model.I_C_ValidCombination P_MethodChangeVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_MethodChangeVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_MethodChangeVariance_A);
	}

	/** Set Method Change Variance.
		@param P_MethodChangeVariance_Acct 
		The Method Change Variance account is the account used Manufacturing Order
	  */
	@Override
	public void setP_MethodChangeVariance_Acct (int P_MethodChangeVariance_Acct)
	{
		set_Value (COLUMNNAME_P_MethodChangeVariance_Acct, Integer.valueOf(P_MethodChangeVariance_Acct));
	}

	/** Get Method Change Variance.
		@return The Method Change Variance account is the account used Manufacturing Order
	  */
	@Override
	public int getP_MethodChangeVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_MethodChangeVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_MixVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_MixVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_MixVariance_A(org.compiere.model.I_C_ValidCombination P_MixVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_MixVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_MixVariance_A);
	}

	/** Set Mix Variance.
		@param P_MixVariance_Acct 
		The Mix Variance account is the account used Manufacturing Order
	  */
	@Override
	public void setP_MixVariance_Acct (int P_MixVariance_Acct)
	{
		set_Value (COLUMNNAME_P_MixVariance_Acct, Integer.valueOf(P_MixVariance_Acct));
	}

	/** Get Mix Variance.
		@return The Mix Variance account is the account used Manufacturing Order
	  */
	@Override
	public int getP_MixVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_MixVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_OutsideProcessing_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_OutsideProcessing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_OutsideProcessing_A(org.compiere.model.I_C_ValidCombination P_OutsideProcessing_A)
	{
		set_ValueFromPO(COLUMNNAME_P_OutsideProcessing_Acct, org.compiere.model.I_C_ValidCombination.class, P_OutsideProcessing_A);
	}

	/** Set Outside Processing.
		@param P_OutsideProcessing_Acct 
		The Outside Processing Account is the account used in Manufacturing Order
	  */
	@Override
	public void setP_OutsideProcessing_Acct (int P_OutsideProcessing_Acct)
	{
		set_Value (COLUMNNAME_P_OutsideProcessing_Acct, Integer.valueOf(P_OutsideProcessing_Acct));
	}

	/** Get Outside Processing.
		@return The Outside Processing Account is the account used in Manufacturing Order
	  */
	@Override
	public int getP_OutsideProcessing_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_OutsideProcessing_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Overhead_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Overhead_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Overhead_A(org.compiere.model.I_C_ValidCombination P_Overhead_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Overhead_Acct, org.compiere.model.I_C_ValidCombination.class, P_Overhead_A);
	}

	/** Set Overhead.
		@param P_Overhead_Acct 
		The Overhead account is the account used  in Manufacturing Order 
	  */
	@Override
	public void setP_Overhead_Acct (int P_Overhead_Acct)
	{
		set_Value (COLUMNNAME_P_Overhead_Acct, Integer.valueOf(P_Overhead_Acct));
	}

	/** Get Overhead.
		@return The Overhead account is the account used  in Manufacturing Order 
	  */
	@Override
	public int getP_Overhead_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Overhead_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_PurchasePriceVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_PurchasePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_PurchasePriceVariance_A(org.compiere.model.I_C_ValidCombination P_PurchasePriceVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_PurchasePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_PurchasePriceVariance_A);
	}

	/** Set Preisdifferenz Bestellung.
		@param P_PurchasePriceVariance_Acct 
		Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.
	  */
	@Override
	public void setP_PurchasePriceVariance_Acct (int P_PurchasePriceVariance_Acct)
	{
		set_Value (COLUMNNAME_P_PurchasePriceVariance_Acct, Integer.valueOf(P_PurchasePriceVariance_Acct));
	}

	/** Get Preisdifferenz Bestellung.
		@return Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.
	  */
	@Override
	public int getP_PurchasePriceVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_PurchasePriceVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_RateVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_RateVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_RateVariance_A(org.compiere.model.I_C_ValidCombination P_RateVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_RateVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_RateVariance_A);
	}

	/** Set Rate Variance.
		@param P_RateVariance_Acct 
		The Rate Variance account is the account used Manufacturing Order
	  */
	@Override
	public void setP_RateVariance_Acct (int P_RateVariance_Acct)
	{
		set_Value (COLUMNNAME_P_RateVariance_Acct, Integer.valueOf(P_RateVariance_Acct));
	}

	/** Get Rate Variance.
		@return The Rate Variance account is the account used Manufacturing Order
	  */
	@Override
	public int getP_RateVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_RateVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Revenue_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Revenue_A(org.compiere.model.I_C_ValidCombination P_Revenue_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class, P_Revenue_A);
	}

	/** Set Produkt Ertrag.
		@param P_Revenue_Acct 
		Konto für Produkt Ertrag
	  */
	@Override
	public void setP_Revenue_Acct (int P_Revenue_Acct)
	{
		set_Value (COLUMNNAME_P_Revenue_Acct, Integer.valueOf(P_Revenue_Acct));
	}

	/** Get Produkt Ertrag.
		@return Konto für Produkt Ertrag
	  */
	@Override
	public int getP_Revenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Revenue_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Scrap_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_Scrap_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Scrap_A(org.compiere.model.I_C_ValidCombination P_Scrap_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Scrap_Acct, org.compiere.model.I_C_ValidCombination.class, P_Scrap_A);
	}

	/** Set Scrap.
		@param P_Scrap_Acct 
		The Scrap account is the account used  in Manufacturing Order 
	  */
	@Override
	public void setP_Scrap_Acct (int P_Scrap_Acct)
	{
		set_Value (COLUMNNAME_P_Scrap_Acct, Integer.valueOf(P_Scrap_Acct));
	}

	/** Get Scrap.
		@return The Scrap account is the account used  in Manufacturing Order 
	  */
	@Override
	public int getP_Scrap_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Scrap_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountGrant_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_TradeDiscountGrant_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_TradeDiscountGrant_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountGrant_A)
	{
		set_ValueFromPO(COLUMNNAME_P_TradeDiscountGrant_Acct, org.compiere.model.I_C_ValidCombination.class, P_TradeDiscountGrant_A);
	}

	/** Set Gewährte Rabatte.
		@param P_TradeDiscountGrant_Acct 
		Konto für gewährte Rabatte
	  */
	@Override
	public void setP_TradeDiscountGrant_Acct (int P_TradeDiscountGrant_Acct)
	{
		set_Value (COLUMNNAME_P_TradeDiscountGrant_Acct, Integer.valueOf(P_TradeDiscountGrant_Acct));
	}

	/** Get Gewährte Rabatte.
		@return Konto für gewährte Rabatte
	  */
	@Override
	public int getP_TradeDiscountGrant_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_TradeDiscountGrant_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountRec_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_TradeDiscountRec_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_TradeDiscountRec_A(org.compiere.model.I_C_ValidCombination P_TradeDiscountRec_A)
	{
		set_ValueFromPO(COLUMNNAME_P_TradeDiscountRec_Acct, org.compiere.model.I_C_ValidCombination.class, P_TradeDiscountRec_A);
	}

	/** Set Erhaltene Rabatte.
		@param P_TradeDiscountRec_Acct 
		Konto für erhaltene Rabatte
	  */
	@Override
	public void setP_TradeDiscountRec_Acct (int P_TradeDiscountRec_Acct)
	{
		set_Value (COLUMNNAME_P_TradeDiscountRec_Acct, Integer.valueOf(P_TradeDiscountRec_Acct));
	}

	/** Get Erhaltene Rabatte.
		@return Konto für erhaltene Rabatte
	  */
	@Override
	public int getP_TradeDiscountRec_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_TradeDiscountRec_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_UsageVariance_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_UsageVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_UsageVariance_A(org.compiere.model.I_C_ValidCombination P_UsageVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_UsageVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_UsageVariance_A);
	}

	/** Set Usage Variance.
		@param P_UsageVariance_Acct 
		The Usage Variance account is the account used Manufacturing Order
	  */
	@Override
	public void setP_UsageVariance_Acct (int P_UsageVariance_Acct)
	{
		set_Value (COLUMNNAME_P_UsageVariance_Acct, Integer.valueOf(P_UsageVariance_Acct));
	}

	/** Get Usage Variance.
		@return The Usage Variance account is the account used Manufacturing Order
	  */
	@Override
	public int getP_UsageVariance_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_UsageVariance_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_WIP_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_P_WIP_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_WIP_A(org.compiere.model.I_C_ValidCombination P_WIP_A)
	{
		set_ValueFromPO(COLUMNNAME_P_WIP_Acct, org.compiere.model.I_C_ValidCombination.class, P_WIP_A);
	}

	/** Set Unfertige Leistungen.
		@param P_WIP_Acct 
		Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet
	  */
	@Override
	public void setP_WIP_Acct (int P_WIP_Acct)
	{
		set_Value (COLUMNNAME_P_WIP_Acct, Integer.valueOf(P_WIP_Acct));
	}

	/** Get Unfertige Leistungen.
		@return Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet
	  */
	@Override
	public int getP_WIP_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_WIP_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPayDiscount_Exp_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PayDiscount_Exp_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPayDiscount_Exp_A(org.compiere.model.I_C_ValidCombination PayDiscount_Exp_A)
	{
		set_ValueFromPO(COLUMNNAME_PayDiscount_Exp_Acct, org.compiere.model.I_C_ValidCombination.class, PayDiscount_Exp_A);
	}

	/** Set Gewährte Skonti.
		@param PayDiscount_Exp_Acct 
		Konto für gewährte Skonti
	  */
	@Override
	public void setPayDiscount_Exp_Acct (int PayDiscount_Exp_Acct)
	{
		set_Value (COLUMNNAME_PayDiscount_Exp_Acct, Integer.valueOf(PayDiscount_Exp_Acct));
	}

	/** Get Gewährte Skonti.
		@return Konto für gewährte Skonti
	  */
	@Override
	public int getPayDiscount_Exp_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayDiscount_Exp_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPayDiscount_Rev_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PayDiscount_Rev_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPayDiscount_Rev_A(org.compiere.model.I_C_ValidCombination PayDiscount_Rev_A)
	{
		set_ValueFromPO(COLUMNNAME_PayDiscount_Rev_Acct, org.compiere.model.I_C_ValidCombination.class, PayDiscount_Rev_A);
	}

	/** Set Erhaltene Skonti.
		@param PayDiscount_Rev_Acct 
		Konto für erhaltene Skonti
	  */
	@Override
	public void setPayDiscount_Rev_Acct (int PayDiscount_Rev_Acct)
	{
		set_Value (COLUMNNAME_PayDiscount_Rev_Acct, Integer.valueOf(PayDiscount_Rev_Acct));
	}

	/** Get Erhaltene Skonti.
		@return Konto für erhaltene Skonti
	  */
	@Override
	public int getPayDiscount_Rev_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PayDiscount_Rev_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPJ_Asset_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PJ_Asset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPJ_Asset_A(org.compiere.model.I_C_ValidCombination PJ_Asset_A)
	{
		set_ValueFromPO(COLUMNNAME_PJ_Asset_Acct, org.compiere.model.I_C_ValidCombination.class, PJ_Asset_A);
	}

	/** Set Project Asset.
		@param PJ_Asset_Acct 
		Project Asset Account
	  */
	@Override
	public void setPJ_Asset_Acct (int PJ_Asset_Acct)
	{
		set_Value (COLUMNNAME_PJ_Asset_Acct, Integer.valueOf(PJ_Asset_Acct));
	}

	/** Get Project Asset.
		@return Project Asset Account
	  */
	@Override
	public int getPJ_Asset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PJ_Asset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPJ_WIP_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PJ_WIP_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPJ_WIP_A(org.compiere.model.I_C_ValidCombination PJ_WIP_A)
	{
		set_ValueFromPO(COLUMNNAME_PJ_WIP_Acct, org.compiere.model.I_C_ValidCombination.class, PJ_WIP_A);
	}

	/** Set Unfertige Leistungen.
		@param PJ_WIP_Acct 
		Konto für Unfertige Leistungen
	  */
	@Override
	public void setPJ_WIP_Acct (int PJ_WIP_Acct)
	{
		set_Value (COLUMNNAME_PJ_WIP_Acct, Integer.valueOf(PJ_WIP_Acct));
	}

	/** Get Unfertige Leistungen.
		@return Konto für Unfertige Leistungen
	  */
	@Override
	public int getPJ_WIP_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PJ_WIP_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
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

	@Override
	public org.compiere.model.I_C_ValidCombination getRealizedGain_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setRealizedGain_A(org.compiere.model.I_C_ValidCombination RealizedGain_A)
	{
		set_ValueFromPO(COLUMNNAME_RealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class, RealizedGain_A);
	}

	/** Set Realisierte Währungsgewinne.
		@param RealizedGain_Acct 
		Konto für Realisierte Währungsgewinne
	  */
	@Override
	public void setRealizedGain_Acct (int RealizedGain_Acct)
	{
		set_Value (COLUMNNAME_RealizedGain_Acct, Integer.valueOf(RealizedGain_Acct));
	}

	/** Get Realisierte Währungsgewinne.
		@return Konto für Realisierte Währungsgewinne
	  */
	@Override
	public int getRealizedGain_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RealizedGain_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getRealizedLoss_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setRealizedLoss_A(org.compiere.model.I_C_ValidCombination RealizedLoss_A)
	{
		set_ValueFromPO(COLUMNNAME_RealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class, RealizedLoss_A);
	}

	/** Set Realisierte Währungsverluste.
		@param RealizedLoss_Acct 
		Konto für realisierte Währungsverluste
	  */
	@Override
	public void setRealizedLoss_Acct (int RealizedLoss_Acct)
	{
		set_Value (COLUMNNAME_RealizedLoss_Acct, Integer.valueOf(RealizedLoss_Acct));
	}

	/** Get Realisierte Währungsverluste.
		@return Konto für realisierte Währungsverluste
	  */
	@Override
	public int getRealizedLoss_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RealizedLoss_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Credit_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_T_Credit_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Credit_A(org.compiere.model.I_C_ValidCombination T_Credit_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Credit_Acct, org.compiere.model.I_C_ValidCombination.class, T_Credit_A);
	}

	/** Set Vorsteuer.
		@param T_Credit_Acct 
		Konto für Vorsteuer
	  */
	@Override
	public void setT_Credit_Acct (int T_Credit_Acct)
	{
		set_Value (COLUMNNAME_T_Credit_Acct, Integer.valueOf(T_Credit_Acct));
	}

	/** Get Vorsteuer.
		@return Konto für Vorsteuer
	  */
	@Override
	public int getT_Credit_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Credit_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Due_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_T_Due_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Due_A(org.compiere.model.I_C_ValidCombination T_Due_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Due_Acct, org.compiere.model.I_C_ValidCombination.class, T_Due_A);
	}

	/** Set Geschuldete MwSt..
		@param T_Due_Acct 
		Konto für geschuldete MwSt.
	  */
	@Override
	public void setT_Due_Acct (int T_Due_Acct)
	{
		set_Value (COLUMNNAME_T_Due_Acct, Integer.valueOf(T_Due_Acct));
	}

	/** Get Geschuldete MwSt..
		@return Konto für geschuldete MwSt.
	  */
	@Override
	public int getT_Due_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Due_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Expense_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_T_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Expense_A(org.compiere.model.I_C_ValidCombination T_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, T_Expense_A);
	}

	/** Set Sonstige Steuern.
		@param T_Expense_Acct 
		Account for paid tax you cannot reclaim
	  */
	@Override
	public void setT_Expense_Acct (int T_Expense_Acct)
	{
		set_Value (COLUMNNAME_T_Expense_Acct, Integer.valueOf(T_Expense_Acct));
	}

	/** Get Sonstige Steuern.
		@return Account for paid tax you cannot reclaim
	  */
	@Override
	public int getT_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Liability_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_T_Liability_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Liability_A(org.compiere.model.I_C_ValidCombination T_Liability_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Liability_Acct, org.compiere.model.I_C_ValidCombination.class, T_Liability_A);
	}

	/** Set Verbindlichkeiten aus Steuern.
		@param T_Liability_Acct 
		Konto für Verbindlichkeiten aus Steuern
	  */
	@Override
	public void setT_Liability_Acct (int T_Liability_Acct)
	{
		set_Value (COLUMNNAME_T_Liability_Acct, Integer.valueOf(T_Liability_Acct));
	}

	/** Get Verbindlichkeiten aus Steuern.
		@return Konto für Verbindlichkeiten aus Steuern
	  */
	@Override
	public int getT_Liability_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Liability_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Receivables_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_T_Receivables_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Receivables_A(org.compiere.model.I_C_ValidCombination T_Receivables_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Receivables_Acct, org.compiere.model.I_C_ValidCombination.class, T_Receivables_A);
	}

	/** Set Steuerüberzahlungen.
		@param T_Receivables_Acct 
		Konto für Steuerüberzahlungen
	  */
	@Override
	public void setT_Receivables_Acct (int T_Receivables_Acct)
	{
		set_Value (COLUMNNAME_T_Receivables_Acct, Integer.valueOf(T_Receivables_Acct));
	}

	/** Get Steuerüberzahlungen.
		@return Konto für Steuerüberzahlungen
	  */
	@Override
	public int getT_Receivables_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Receivables_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getUnEarnedRevenue_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_UnEarnedRevenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setUnEarnedRevenue_A(org.compiere.model.I_C_ValidCombination UnEarnedRevenue_A)
	{
		set_ValueFromPO(COLUMNNAME_UnEarnedRevenue_Acct, org.compiere.model.I_C_ValidCombination.class, UnEarnedRevenue_A);
	}

	/** Set Vorausberechnete Einnahmen.
		@param UnEarnedRevenue_Acct 
		Konto für vorausberechnete Einnahmen
	  */
	@Override
	public void setUnEarnedRevenue_Acct (int UnEarnedRevenue_Acct)
	{
		set_Value (COLUMNNAME_UnEarnedRevenue_Acct, Integer.valueOf(UnEarnedRevenue_Acct));
	}

	/** Get Vorausberechnete Einnahmen.
		@return Konto für vorausberechnete Einnahmen
	  */
	@Override
	public int getUnEarnedRevenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UnEarnedRevenue_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getUnrealizedGain_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_UnrealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setUnrealizedGain_A(org.compiere.model.I_C_ValidCombination UnrealizedGain_A)
	{
		set_ValueFromPO(COLUMNNAME_UnrealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class, UnrealizedGain_A);
	}

	/** Set Nicht realisierte Währungsgewinne.
		@param UnrealizedGain_Acct 
		Konto für nicht realisierte Währungsgewinne
	  */
	@Override
	public void setUnrealizedGain_Acct (int UnrealizedGain_Acct)
	{
		set_Value (COLUMNNAME_UnrealizedGain_Acct, Integer.valueOf(UnrealizedGain_Acct));
	}

	/** Get Nicht realisierte Währungsgewinne.
		@return Konto für nicht realisierte Währungsgewinne
	  */
	@Override
	public int getUnrealizedGain_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UnrealizedGain_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getUnrealizedLoss_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_UnrealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setUnrealizedLoss_A(org.compiere.model.I_C_ValidCombination UnrealizedLoss_A)
	{
		set_ValueFromPO(COLUMNNAME_UnrealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class, UnrealizedLoss_A);
	}

	/** Set Nicht realisierte Währungsverluste.
		@param UnrealizedLoss_Acct 
		Konto für nicht realisierte Währungsverluste
	  */
	@Override
	public void setUnrealizedLoss_Acct (int UnrealizedLoss_Acct)
	{
		set_Value (COLUMNNAME_UnrealizedLoss_Acct, Integer.valueOf(UnrealizedLoss_Acct));
	}

	/** Get Nicht realisierte Währungsverluste.
		@return Konto für nicht realisierte Währungsverluste
	  */
	@Override
	public int getUnrealizedLoss_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UnrealizedLoss_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getV_Liability_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_V_Liability_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setV_Liability_A(org.compiere.model.I_C_ValidCombination V_Liability_A)
	{
		set_ValueFromPO(COLUMNNAME_V_Liability_Acct, org.compiere.model.I_C_ValidCombination.class, V_Liability_A);
	}

	/** Set Verbindlichkeiten aus Lieferungen.
		@param V_Liability_Acct 
		Konto für Verbindlichkeiten aus Lieferungen (und Leistungen)
	  */
	@Override
	public void setV_Liability_Acct (int V_Liability_Acct)
	{
		set_Value (COLUMNNAME_V_Liability_Acct, Integer.valueOf(V_Liability_Acct));
	}

	/** Get Verbindlichkeiten aus Lieferungen.
		@return Konto für Verbindlichkeiten aus Lieferungen (und Leistungen)
	  */
	@Override
	public int getV_Liability_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_V_Liability_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getV_Liability_Services_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_V_Liability_Services_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setV_Liability_Services_A(org.compiere.model.I_C_ValidCombination V_Liability_Services_A)
	{
		set_ValueFromPO(COLUMNNAME_V_Liability_Services_Acct, org.compiere.model.I_C_ValidCombination.class, V_Liability_Services_A);
	}

	/** Set Verbindlichkeiten aus Dienstleistungen.
		@param V_Liability_Services_Acct 
		Konto für Verbindlichkeiten aus Dienstleistungen
	  */
	@Override
	public void setV_Liability_Services_Acct (int V_Liability_Services_Acct)
	{
		set_Value (COLUMNNAME_V_Liability_Services_Acct, Integer.valueOf(V_Liability_Services_Acct));
	}

	/** Get Verbindlichkeiten aus Dienstleistungen.
		@return Konto für Verbindlichkeiten aus Dienstleistungen
	  */
	@Override
	public int getV_Liability_Services_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_V_Liability_Services_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getV_Prepayment_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_V_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setV_Prepayment_A(org.compiere.model.I_C_ValidCombination V_Prepayment_A)
	{
		set_ValueFromPO(COLUMNNAME_V_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class, V_Prepayment_A);
	}

	/** Set Geleistete Anzahlungen.
		@param V_Prepayment_Acct 
		Konto für Geleistete Anzahlungen
	  */
	@Override
	public void setV_Prepayment_Acct (int V_Prepayment_Acct)
	{
		set_Value (COLUMNNAME_V_Prepayment_Acct, Integer.valueOf(V_Prepayment_Acct));
	}

	/** Get Geleistete Anzahlungen.
		@return Konto für Geleistete Anzahlungen
	  */
	@Override
	public int getV_Prepayment_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_V_Prepayment_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getW_Differences_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_W_Differences_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setW_Differences_A(org.compiere.model.I_C_ValidCombination W_Differences_A)
	{
		set_ValueFromPO(COLUMNNAME_W_Differences_Acct, org.compiere.model.I_C_ValidCombination.class, W_Differences_A);
	}

	/** Set Lager Bestand Korrektur.
		@param W_Differences_Acct 
		Konto für Differenzen im Lagerbestand (erfasst durch Inventur)
	  */
	@Override
	public void setW_Differences_Acct (int W_Differences_Acct)
	{
		set_Value (COLUMNNAME_W_Differences_Acct, Integer.valueOf(W_Differences_Acct));
	}

	/** Get Lager Bestand Korrektur.
		@return Konto für Differenzen im Lagerbestand (erfasst durch Inventur)
	  */
	@Override
	public int getW_Differences_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Differences_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getW_InvActualAdjust_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_W_InvActualAdjust_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setW_InvActualAdjust_A(org.compiere.model.I_C_ValidCombination W_InvActualAdjust_A)
	{
		set_ValueFromPO(COLUMNNAME_W_InvActualAdjust_Acct, org.compiere.model.I_C_ValidCombination.class, W_InvActualAdjust_A);
	}

	/** Set Lager Wert Korrektur.
		@param W_InvActualAdjust_Acct 
		Konto für Korrekturen am Lager Wert (i.d.R. mit Konto Warenbestand identisch)
	  */
	@Override
	public void setW_InvActualAdjust_Acct (int W_InvActualAdjust_Acct)
	{
		set_Value (COLUMNNAME_W_InvActualAdjust_Acct, Integer.valueOf(W_InvActualAdjust_Acct));
	}

	/** Get Lager Wert Korrektur.
		@return Konto für Korrekturen am Lager Wert (i.d.R. mit Konto Warenbestand identisch)
	  */
	@Override
	public int getW_InvActualAdjust_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_InvActualAdjust_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getW_Inventory_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_W_Inventory_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setW_Inventory_A(org.compiere.model.I_C_ValidCombination W_Inventory_A)
	{
		set_ValueFromPO(COLUMNNAME_W_Inventory_Acct, org.compiere.model.I_C_ValidCombination.class, W_Inventory_A);
	}

	/** Set (Not Used).
		@param W_Inventory_Acct 
		Warehouse Inventory Asset Account - Currently not used
	  */
	@Override
	public void setW_Inventory_Acct (int W_Inventory_Acct)
	{
		set_Value (COLUMNNAME_W_Inventory_Acct, Integer.valueOf(W_Inventory_Acct));
	}

	/** Get (Not Used).
		@return Warehouse Inventory Asset Account - Currently not used
	  */
	@Override
	public int getW_Inventory_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Inventory_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getW_Revaluation_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_W_Revaluation_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setW_Revaluation_A(org.compiere.model.I_C_ValidCombination W_Revaluation_A)
	{
		set_ValueFromPO(COLUMNNAME_W_Revaluation_Acct, org.compiere.model.I_C_ValidCombination.class, W_Revaluation_A);
	}

	/** Set Lager Wert Korrektur Währungsdifferenz.
		@param W_Revaluation_Acct 
		Konto für Lager Wert Korrektur Währungsdifferenz
	  */
	@Override
	public void setW_Revaluation_Acct (int W_Revaluation_Acct)
	{
		set_Value (COLUMNNAME_W_Revaluation_Acct, Integer.valueOf(W_Revaluation_Acct));
	}

	/** Get Lager Wert Korrektur Währungsdifferenz.
		@return Konto für Lager Wert Korrektur Währungsdifferenz
	  */
	@Override
	public int getW_Revaluation_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Revaluation_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getWithholding_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Withholding_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setWithholding_A(org.compiere.model.I_C_ValidCombination Withholding_A)
	{
		set_ValueFromPO(COLUMNNAME_Withholding_Acct, org.compiere.model.I_C_ValidCombination.class, Withholding_A);
	}

	/** Set Einbehalt.
		@param Withholding_Acct 
		Account for Withholdings
	  */
	@Override
	public void setWithholding_Acct (int Withholding_Acct)
	{
		set_Value (COLUMNNAME_Withholding_Acct, Integer.valueOf(Withholding_Acct));
	}

	/** Get Einbehalt.
		@return Account for Withholdings
	  */
	@Override
	public int getWithholding_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Withholding_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getWriteOff_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_WriteOff_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setWriteOff_A(org.compiere.model.I_C_ValidCombination WriteOff_A)
	{
		set_ValueFromPO(COLUMNNAME_WriteOff_Acct, org.compiere.model.I_C_ValidCombination.class, WriteOff_A);
	}

	/** Set Forderungsverluste.
		@param WriteOff_Acct 
		Konto für Forderungsverluste
	  */
	@Override
	public void setWriteOff_Acct (int WriteOff_Acct)
	{
		set_Value (COLUMNNAME_WriteOff_Acct, Integer.valueOf(WriteOff_Acct));
	}

	/** Get Forderungsverluste.
		@return Konto für Forderungsverluste
	  */
	@Override
	public int getWriteOff_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WriteOff_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
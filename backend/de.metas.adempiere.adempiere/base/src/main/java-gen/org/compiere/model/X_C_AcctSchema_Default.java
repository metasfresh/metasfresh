// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_AcctSchema_Default
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_AcctSchema_Default extends org.compiere.model.PO implements I_C_AcctSchema_Default, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1186766514L;

    /** Standard Constructor */
    public X_C_AcctSchema_Default (final Properties ctx, final int C_AcctSchema_Default_ID, @Nullable final String trxName)
    {
      super (ctx, C_AcctSchema_Default_ID, trxName);
    }

    /** Load Constructor */
    public X_C_AcctSchema_Default (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_Asset_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_Asset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_Asset_A(final org.compiere.model.I_C_ValidCombination B_Asset_A)
	{
		set_ValueFromPO(COLUMNNAME_B_Asset_Acct, org.compiere.model.I_C_ValidCombination.class, B_Asset_A);
	}

	@Override
	public void setB_Asset_Acct (final int B_Asset_Acct)
	{
		set_Value (COLUMNNAME_B_Asset_Acct, B_Asset_Acct);
	}

	@Override
	public int getB_Asset_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_Asset_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_Expense_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_Expense_A(final org.compiere.model.I_C_ValidCombination B_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_B_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, B_Expense_A);
	}

	@Override
	public void setB_Expense_Acct (final int B_Expense_Acct)
	{
		set_Value (COLUMNNAME_B_Expense_Acct, B_Expense_Acct);
	}

	@Override
	public int getB_Expense_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_Expense_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_InterestExp_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_InterestExp_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_InterestExp_A(final org.compiere.model.I_C_ValidCombination B_InterestExp_A)
	{
		set_ValueFromPO(COLUMNNAME_B_InterestExp_Acct, org.compiere.model.I_C_ValidCombination.class, B_InterestExp_A);
	}

	@Override
	public void setB_InterestExp_Acct (final int B_InterestExp_Acct)
	{
		set_Value (COLUMNNAME_B_InterestExp_Acct, B_InterestExp_Acct);
	}

	@Override
	public int getB_InterestExp_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_InterestExp_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_InterestRev_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_InterestRev_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_InterestRev_A(final org.compiere.model.I_C_ValidCombination B_InterestRev_A)
	{
		set_ValueFromPO(COLUMNNAME_B_InterestRev_Acct, org.compiere.model.I_C_ValidCombination.class, B_InterestRev_A);
	}

	@Override
	public void setB_InterestRev_Acct (final int B_InterestRev_Acct)
	{
		set_Value (COLUMNNAME_B_InterestRev_Acct, B_InterestRev_Acct);
	}

	@Override
	public int getB_InterestRev_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_InterestRev_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_InTransit_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_InTransit_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_InTransit_A(final org.compiere.model.I_C_ValidCombination B_InTransit_A)
	{
		set_ValueFromPO(COLUMNNAME_B_InTransit_Acct, org.compiere.model.I_C_ValidCombination.class, B_InTransit_A);
	}

	@Override
	public void setB_InTransit_Acct (final int B_InTransit_Acct)
	{
		set_Value (COLUMNNAME_B_InTransit_Acct, B_InTransit_Acct);
	}

	@Override
	public int getB_InTransit_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_InTransit_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_PaymentSelect_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_PaymentSelect_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_PaymentSelect_A(final org.compiere.model.I_C_ValidCombination B_PaymentSelect_A)
	{
		set_ValueFromPO(COLUMNNAME_B_PaymentSelect_Acct, org.compiere.model.I_C_ValidCombination.class, B_PaymentSelect_A);
	}

	@Override
	public void setB_PaymentSelect_Acct (final int B_PaymentSelect_Acct)
	{
		set_Value (COLUMNNAME_B_PaymentSelect_Acct, B_PaymentSelect_Acct);
	}

	@Override
	public int getB_PaymentSelect_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_PaymentSelect_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_RevaluationGain_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_RevaluationGain_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_RevaluationGain_A(final org.compiere.model.I_C_ValidCombination B_RevaluationGain_A)
	{
		set_ValueFromPO(COLUMNNAME_B_RevaluationGain_Acct, org.compiere.model.I_C_ValidCombination.class, B_RevaluationGain_A);
	}

	@Override
	public void setB_RevaluationGain_Acct (final int B_RevaluationGain_Acct)
	{
		set_Value (COLUMNNAME_B_RevaluationGain_Acct, B_RevaluationGain_Acct);
	}

	@Override
	public int getB_RevaluationGain_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_RevaluationGain_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_RevaluationLoss_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_RevaluationLoss_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_RevaluationLoss_A(final org.compiere.model.I_C_ValidCombination B_RevaluationLoss_A)
	{
		set_ValueFromPO(COLUMNNAME_B_RevaluationLoss_Acct, org.compiere.model.I_C_ValidCombination.class, B_RevaluationLoss_A);
	}

	@Override
	public void setB_RevaluationLoss_Acct (final int B_RevaluationLoss_Acct)
	{
		set_Value (COLUMNNAME_B_RevaluationLoss_Acct, B_RevaluationLoss_Acct);
	}

	@Override
	public int getB_RevaluationLoss_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_RevaluationLoss_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_SettlementGain_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_SettlementGain_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_SettlementGain_A(final org.compiere.model.I_C_ValidCombination B_SettlementGain_A)
	{
		set_ValueFromPO(COLUMNNAME_B_SettlementGain_Acct, org.compiere.model.I_C_ValidCombination.class, B_SettlementGain_A);
	}

	@Override
	public void setB_SettlementGain_Acct (final int B_SettlementGain_Acct)
	{
		set_Value (COLUMNNAME_B_SettlementGain_Acct, B_SettlementGain_Acct);
	}

	@Override
	public int getB_SettlementGain_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_SettlementGain_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_SettlementLoss_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_SettlementLoss_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_SettlementLoss_A(final org.compiere.model.I_C_ValidCombination B_SettlementLoss_A)
	{
		set_ValueFromPO(COLUMNNAME_B_SettlementLoss_Acct, org.compiere.model.I_C_ValidCombination.class, B_SettlementLoss_A);
	}

	@Override
	public void setB_SettlementLoss_Acct (final int B_SettlementLoss_Acct)
	{
		set_Value (COLUMNNAME_B_SettlementLoss_Acct, B_SettlementLoss_Acct);
	}

	@Override
	public int getB_SettlementLoss_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_SettlementLoss_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_UnallocatedCash_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_UnallocatedCash_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_UnallocatedCash_A(final org.compiere.model.I_C_ValidCombination B_UnallocatedCash_A)
	{
		set_ValueFromPO(COLUMNNAME_B_UnallocatedCash_Acct, org.compiere.model.I_C_ValidCombination.class, B_UnallocatedCash_A);
	}

	@Override
	public void setB_UnallocatedCash_Acct (final int B_UnallocatedCash_Acct)
	{
		set_Value (COLUMNNAME_B_UnallocatedCash_Acct, B_UnallocatedCash_Acct);
	}

	@Override
	public int getB_UnallocatedCash_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_UnallocatedCash_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getB_Unidentified_A()
	{
		return get_ValueAsPO(COLUMNNAME_B_Unidentified_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setB_Unidentified_A(final org.compiere.model.I_C_ValidCombination B_Unidentified_A)
	{
		set_ValueFromPO(COLUMNNAME_B_Unidentified_Acct, org.compiere.model.I_C_ValidCombination.class, B_Unidentified_A);
	}

	@Override
	public void setB_Unidentified_Acct (final int B_Unidentified_Acct)
	{
		set_Value (COLUMNNAME_B_Unidentified_Acct, B_Unidentified_Acct);
	}

	@Override
	public int getB_Unidentified_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_B_Unidentified_Acct);
	}

	@Override
	public void setC_AcctSchema_Default_ID (final int C_AcctSchema_Default_ID)
	{
		if (C_AcctSchema_Default_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_Default_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_Default_ID, C_AcctSchema_Default_ID);
	}

	@Override
	public int getC_AcctSchema_Default_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_Default_ID);
	}

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema()
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(final org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	@Override
	public void setC_AcctSchema_ID (final int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
	}

	@Override
	public int getC_AcctSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_ID);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCB_Asset_A()
	{
		return get_ValueAsPO(COLUMNNAME_CB_Asset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCB_Asset_A(final org.compiere.model.I_C_ValidCombination CB_Asset_A)
	{
		set_ValueFromPO(COLUMNNAME_CB_Asset_Acct, org.compiere.model.I_C_ValidCombination.class, CB_Asset_A);
	}

	@Override
	public void setCB_Asset_Acct (final int CB_Asset_Acct)
	{
		set_Value (COLUMNNAME_CB_Asset_Acct, CB_Asset_Acct);
	}

	@Override
	public int getCB_Asset_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_CB_Asset_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCB_CashTransfer_A()
	{
		return get_ValueAsPO(COLUMNNAME_CB_CashTransfer_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCB_CashTransfer_A(final org.compiere.model.I_C_ValidCombination CB_CashTransfer_A)
	{
		set_ValueFromPO(COLUMNNAME_CB_CashTransfer_Acct, org.compiere.model.I_C_ValidCombination.class, CB_CashTransfer_A);
	}

	@Override
	public void setCB_CashTransfer_Acct (final int CB_CashTransfer_Acct)
	{
		set_Value (COLUMNNAME_CB_CashTransfer_Acct, CB_CashTransfer_Acct);
	}

	@Override
	public int getCB_CashTransfer_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_CB_CashTransfer_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCB_Differences_A()
	{
		return get_ValueAsPO(COLUMNNAME_CB_Differences_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCB_Differences_A(final org.compiere.model.I_C_ValidCombination CB_Differences_A)
	{
		set_ValueFromPO(COLUMNNAME_CB_Differences_Acct, org.compiere.model.I_C_ValidCombination.class, CB_Differences_A);
	}

	@Override
	public void setCB_Differences_Acct (final int CB_Differences_Acct)
	{
		set_Value (COLUMNNAME_CB_Differences_Acct, CB_Differences_Acct);
	}

	@Override
	public int getCB_Differences_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_CB_Differences_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCB_Expense_A()
	{
		return get_ValueAsPO(COLUMNNAME_CB_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCB_Expense_A(final org.compiere.model.I_C_ValidCombination CB_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_CB_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, CB_Expense_A);
	}

	@Override
	public void setCB_Expense_Acct (final int CB_Expense_Acct)
	{
		set_Value (COLUMNNAME_CB_Expense_Acct, CB_Expense_Acct);
	}

	@Override
	public int getCB_Expense_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_CB_Expense_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCB_Receipt_A()
	{
		return get_ValueAsPO(COLUMNNAME_CB_Receipt_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCB_Receipt_A(final org.compiere.model.I_C_ValidCombination CB_Receipt_A)
	{
		set_ValueFromPO(COLUMNNAME_CB_Receipt_Acct, org.compiere.model.I_C_ValidCombination.class, CB_Receipt_A);
	}

	@Override
	public void setCB_Receipt_Acct (final int CB_Receipt_Acct)
	{
		set_Value (COLUMNNAME_CB_Receipt_Acct, CB_Receipt_Acct);
	}

	@Override
	public int getCB_Receipt_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_CB_Receipt_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCh_Expense_A()
	{
		return get_ValueAsPO(COLUMNNAME_Ch_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCh_Expense_A(final org.compiere.model.I_C_ValidCombination Ch_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_Ch_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, Ch_Expense_A);
	}

	@Override
	public void setCh_Expense_Acct (final int Ch_Expense_Acct)
	{
		set_Value (COLUMNNAME_Ch_Expense_Acct, Ch_Expense_Acct);
	}

	@Override
	public int getCh_Expense_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_Ch_Expense_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCh_Revenue_A()
	{
		return get_ValueAsPO(COLUMNNAME_Ch_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCh_Revenue_A(final org.compiere.model.I_C_ValidCombination Ch_Revenue_A)
	{
		set_ValueFromPO(COLUMNNAME_Ch_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class, Ch_Revenue_A);
	}

	@Override
	public void setCh_Revenue_Acct (final int Ch_Revenue_Acct)
	{
		set_Value (COLUMNNAME_Ch_Revenue_Acct, Ch_Revenue_Acct);
	}

	@Override
	public int getCh_Revenue_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_Ch_Revenue_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getC_Prepayment_A()
	{
		return get_ValueAsPO(COLUMNNAME_C_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setC_Prepayment_A(final org.compiere.model.I_C_ValidCombination C_Prepayment_A)
	{
		set_ValueFromPO(COLUMNNAME_C_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class, C_Prepayment_A);
	}

	@Override
	public void setC_Prepayment_Acct (final int C_Prepayment_Acct)
	{
		set_Value (COLUMNNAME_C_Prepayment_Acct, C_Prepayment_Acct);
	}

	@Override
	public int getC_Prepayment_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Prepayment_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getC_Receivable_A()
	{
		return get_ValueAsPO(COLUMNNAME_C_Receivable_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setC_Receivable_A(final org.compiere.model.I_C_ValidCombination C_Receivable_A)
	{
		set_ValueFromPO(COLUMNNAME_C_Receivable_Acct, org.compiere.model.I_C_ValidCombination.class, C_Receivable_A);
	}

	@Override
	public void setC_Receivable_Acct (final int C_Receivable_Acct)
	{
		set_Value (COLUMNNAME_C_Receivable_Acct, C_Receivable_Acct);
	}

	@Override
	public int getC_Receivable_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Receivable_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getC_Receivable_Services_A()
	{
		return get_ValueAsPO(COLUMNNAME_C_Receivable_Services_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setC_Receivable_Services_A(final org.compiere.model.I_C_ValidCombination C_Receivable_Services_A)
	{
		set_ValueFromPO(COLUMNNAME_C_Receivable_Services_Acct, org.compiere.model.I_C_ValidCombination.class, C_Receivable_Services_A);
	}

	@Override
	public void setC_Receivable_Services_Acct (final int C_Receivable_Services_Acct)
	{
		set_Value (COLUMNNAME_C_Receivable_Services_Acct, C_Receivable_Services_Acct);
	}

	@Override
	public int getC_Receivable_Services_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Receivable_Services_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getE_Expense_A()
	{
		return get_ValueAsPO(COLUMNNAME_E_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setE_Expense_A(final org.compiere.model.I_C_ValidCombination E_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_E_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, E_Expense_A);
	}

	@Override
	public void setE_Expense_Acct (final int E_Expense_Acct)
	{
		set_Value (COLUMNNAME_E_Expense_Acct, E_Expense_Acct);
	}

	@Override
	public int getE_Expense_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_E_Expense_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getE_Prepayment_A()
	{
		return get_ValueAsPO(COLUMNNAME_E_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setE_Prepayment_A(final org.compiere.model.I_C_ValidCombination E_Prepayment_A)
	{
		set_ValueFromPO(COLUMNNAME_E_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class, E_Prepayment_A);
	}

	@Override
	public void setE_Prepayment_Acct (final int E_Prepayment_Acct)
	{
		set_Value (COLUMNNAME_E_Prepayment_Acct, E_Prepayment_Acct);
	}

	@Override
	public int getE_Prepayment_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_E_Prepayment_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getNotInvoicedReceipts_A()
	{
		return get_ValueAsPO(COLUMNNAME_NotInvoicedReceipts_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setNotInvoicedReceipts_A(final org.compiere.model.I_C_ValidCombination NotInvoicedReceipts_A)
	{
		set_ValueFromPO(COLUMNNAME_NotInvoicedReceipts_Acct, org.compiere.model.I_C_ValidCombination.class, NotInvoicedReceipts_A);
	}

	@Override
	public void setNotInvoicedReceipts_Acct (final int NotInvoicedReceipts_Acct)
	{
		set_Value (COLUMNNAME_NotInvoicedReceipts_Acct, NotInvoicedReceipts_Acct);
	}

	@Override
	public int getNotInvoicedReceipts_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_NotInvoicedReceipts_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getNotInvoicedReceivables_A()
	{
		return get_ValueAsPO(COLUMNNAME_NotInvoicedReceivables_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setNotInvoicedReceivables_A(final org.compiere.model.I_C_ValidCombination NotInvoicedReceivables_A)
	{
		set_ValueFromPO(COLUMNNAME_NotInvoicedReceivables_Acct, org.compiere.model.I_C_ValidCombination.class, NotInvoicedReceivables_A);
	}

	@Override
	public void setNotInvoicedReceivables_Acct (final int NotInvoicedReceivables_Acct)
	{
		set_Value (COLUMNNAME_NotInvoicedReceivables_Acct, NotInvoicedReceivables_Acct);
	}

	@Override
	public int getNotInvoicedReceivables_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_NotInvoicedReceivables_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getNotInvoicedRevenue_A()
	{
		return get_ValueAsPO(COLUMNNAME_NotInvoicedRevenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setNotInvoicedRevenue_A(final org.compiere.model.I_C_ValidCombination NotInvoicedRevenue_A)
	{
		set_ValueFromPO(COLUMNNAME_NotInvoicedRevenue_Acct, org.compiere.model.I_C_ValidCombination.class, NotInvoicedRevenue_A);
	}

	@Override
	public void setNotInvoicedRevenue_Acct (final int NotInvoicedRevenue_Acct)
	{
		set_Value (COLUMNNAME_NotInvoicedRevenue_Acct, NotInvoicedRevenue_Acct);
	}

	@Override
	public int getNotInvoicedRevenue_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_NotInvoicedRevenue_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Asset_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Asset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Asset_A(final org.compiere.model.I_C_ValidCombination P_Asset_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Asset_Acct, org.compiere.model.I_C_ValidCombination.class, P_Asset_A);
	}

	@Override
	public void setP_Asset_Acct (final int P_Asset_Acct)
	{
		set_Value (COLUMNNAME_P_Asset_Acct, P_Asset_Acct);
	}

	@Override
	public int getP_Asset_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Asset_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPayBankFee_A()
	{
		return get_ValueAsPO(COLUMNNAME_PayBankFee_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPayBankFee_A(final org.compiere.model.I_C_ValidCombination PayBankFee_A)
	{
		set_ValueFromPO(COLUMNNAME_PayBankFee_Acct, org.compiere.model.I_C_ValidCombination.class, PayBankFee_A);
	}

	@Override
	public void setPayBankFee_Acct (final int PayBankFee_Acct)
	{
		set_Value (COLUMNNAME_PayBankFee_Acct, PayBankFee_Acct);
	}

	@Override
	public int getPayBankFee_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_PayBankFee_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPayDiscount_Exp_A()
	{
		return get_ValueAsPO(COLUMNNAME_PayDiscount_Exp_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPayDiscount_Exp_A(final org.compiere.model.I_C_ValidCombination PayDiscount_Exp_A)
	{
		set_ValueFromPO(COLUMNNAME_PayDiscount_Exp_Acct, org.compiere.model.I_C_ValidCombination.class, PayDiscount_Exp_A);
	}

	@Override
	public void setPayDiscount_Exp_Acct (final int PayDiscount_Exp_Acct)
	{
		set_Value (COLUMNNAME_PayDiscount_Exp_Acct, PayDiscount_Exp_Acct);
	}

	@Override
	public int getPayDiscount_Exp_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_PayDiscount_Exp_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPayDiscount_Rev_A()
	{
		return get_ValueAsPO(COLUMNNAME_PayDiscount_Rev_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPayDiscount_Rev_A(final org.compiere.model.I_C_ValidCombination PayDiscount_Rev_A)
	{
		set_ValueFromPO(COLUMNNAME_PayDiscount_Rev_Acct, org.compiere.model.I_C_ValidCombination.class, PayDiscount_Rev_A);
	}

	@Override
	public void setPayDiscount_Rev_Acct (final int PayDiscount_Rev_Acct)
	{
		set_Value (COLUMNNAME_PayDiscount_Rev_Acct, PayDiscount_Rev_Acct);
	}

	@Override
	public int getPayDiscount_Rev_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_PayDiscount_Rev_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Burden_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Burden_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Burden_A(final org.compiere.model.I_C_ValidCombination P_Burden_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Burden_Acct, org.compiere.model.I_C_ValidCombination.class, P_Burden_A);
	}

	@Override
	public void setP_Burden_Acct (final int P_Burden_Acct)
	{
		set_Value (COLUMNNAME_P_Burden_Acct, P_Burden_Acct);
	}

	@Override
	public int getP_Burden_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Burden_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_COGS_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_COGS_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_COGS_A(final org.compiere.model.I_C_ValidCombination P_COGS_A)
	{
		set_ValueFromPO(COLUMNNAME_P_COGS_Acct, org.compiere.model.I_C_ValidCombination.class, P_COGS_A);
	}

	@Override
	public void setP_COGS_Acct (final int P_COGS_Acct)
	{
		set_Value (COLUMNNAME_P_COGS_Acct, P_COGS_Acct);
	}

	@Override
	public int getP_COGS_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_COGS_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_CostAdjustment_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_CostAdjustment_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_CostAdjustment_A(final org.compiere.model.I_C_ValidCombination P_CostAdjustment_A)
	{
		set_ValueFromPO(COLUMNNAME_P_CostAdjustment_Acct, org.compiere.model.I_C_ValidCombination.class, P_CostAdjustment_A);
	}

	@Override
	public void setP_CostAdjustment_Acct (final int P_CostAdjustment_Acct)
	{
		set_Value (COLUMNNAME_P_CostAdjustment_Acct, P_CostAdjustment_Acct);
	}

	@Override
	public int getP_CostAdjustment_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_CostAdjustment_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_CostClearing_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_CostClearing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_CostClearing_A(final org.compiere.model.I_C_ValidCombination P_CostClearing_A)
	{
		set_ValueFromPO(COLUMNNAME_P_CostClearing_Acct, org.compiere.model.I_C_ValidCombination.class, P_CostClearing_A);
	}

	@Override
	public void setP_CostClearing_Acct (final int P_CostClearing_Acct)
	{
		set_Value (COLUMNNAME_P_CostClearing_Acct, P_CostClearing_Acct);
	}

	@Override
	public int getP_CostClearing_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_CostClearing_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_CostOfProduction_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_CostOfProduction_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_CostOfProduction_A(final org.compiere.model.I_C_ValidCombination P_CostOfProduction_A)
	{
		set_ValueFromPO(COLUMNNAME_P_CostOfProduction_Acct, org.compiere.model.I_C_ValidCombination.class, P_CostOfProduction_A);
	}

	@Override
	public void setP_CostOfProduction_Acct (final int P_CostOfProduction_Acct)
	{
		set_Value (COLUMNNAME_P_CostOfProduction_Acct, P_CostOfProduction_Acct);
	}

	@Override
	public int getP_CostOfProduction_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_CostOfProduction_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Expense_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Expense_A(final org.compiere.model.I_C_ValidCombination P_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, P_Expense_A);
	}

	@Override
	public void setP_Expense_Acct (final int P_Expense_Acct)
	{
		set_Value (COLUMNNAME_P_Expense_Acct, P_Expense_Acct);
	}

	@Override
	public int getP_Expense_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Expense_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_ExternallyOwnedStock_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_ExternallyOwnedStock_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_ExternallyOwnedStock_A(final org.compiere.model.I_C_ValidCombination P_ExternallyOwnedStock_A)
	{
		set_ValueFromPO(COLUMNNAME_P_ExternallyOwnedStock_Acct, org.compiere.model.I_C_ValidCombination.class, P_ExternallyOwnedStock_A);
	}

	@Override
	public void setP_ExternallyOwnedStock_Acct (final int P_ExternallyOwnedStock_Acct)
	{
		set_Value (COLUMNNAME_P_ExternallyOwnedStock_Acct, P_ExternallyOwnedStock_Acct);
	}

	@Override
	public int getP_ExternallyOwnedStock_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_ExternallyOwnedStock_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_FloorStock_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_FloorStock_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_FloorStock_A(final org.compiere.model.I_C_ValidCombination P_FloorStock_A)
	{
		set_ValueFromPO(COLUMNNAME_P_FloorStock_Acct, org.compiere.model.I_C_ValidCombination.class, P_FloorStock_A);
	}

	@Override
	public void setP_FloorStock_Acct (final int P_FloorStock_Acct)
	{
		set_Value (COLUMNNAME_P_FloorStock_Acct, P_FloorStock_Acct);
	}

	@Override
	public int getP_FloorStock_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_FloorStock_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_InventoryClearing_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_InventoryClearing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_InventoryClearing_A(final org.compiere.model.I_C_ValidCombination P_InventoryClearing_A)
	{
		set_ValueFromPO(COLUMNNAME_P_InventoryClearing_Acct, org.compiere.model.I_C_ValidCombination.class, P_InventoryClearing_A);
	}

	@Override
	public void setP_InventoryClearing_Acct (final int P_InventoryClearing_Acct)
	{
		set_Value (COLUMNNAME_P_InventoryClearing_Acct, P_InventoryClearing_Acct);
	}

	@Override
	public int getP_InventoryClearing_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_InventoryClearing_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_InvoicePriceVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_InvoicePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_InvoicePriceVariance_A(final org.compiere.model.I_C_ValidCombination P_InvoicePriceVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_InvoicePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_InvoicePriceVariance_A);
	}

	@Override
	public void setP_InvoicePriceVariance_Acct (final int P_InvoicePriceVariance_Acct)
	{
		set_Value (COLUMNNAME_P_InvoicePriceVariance_Acct, P_InvoicePriceVariance_Acct);
	}

	@Override
	public int getP_InvoicePriceVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_InvoicePriceVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPJ_Asset_A()
	{
		return get_ValueAsPO(COLUMNNAME_PJ_Asset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPJ_Asset_A(final org.compiere.model.I_C_ValidCombination PJ_Asset_A)
	{
		set_ValueFromPO(COLUMNNAME_PJ_Asset_Acct, org.compiere.model.I_C_ValidCombination.class, PJ_Asset_A);
	}

	@Override
	public void setPJ_Asset_Acct (final int PJ_Asset_Acct)
	{
		set_Value (COLUMNNAME_PJ_Asset_Acct, PJ_Asset_Acct);
	}

	@Override
	public int getPJ_Asset_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_PJ_Asset_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPJ_WIP_A()
	{
		return get_ValueAsPO(COLUMNNAME_PJ_WIP_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPJ_WIP_A(final org.compiere.model.I_C_ValidCombination PJ_WIP_A)
	{
		set_ValueFromPO(COLUMNNAME_PJ_WIP_Acct, org.compiere.model.I_C_ValidCombination.class, PJ_WIP_A);
	}

	@Override
	public void setPJ_WIP_Acct (final int PJ_WIP_Acct)
	{
		set_Value (COLUMNNAME_PJ_WIP_Acct, PJ_WIP_Acct);
	}

	@Override
	public int getPJ_WIP_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_PJ_WIP_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Labor_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Labor_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Labor_A(final org.compiere.model.I_C_ValidCombination P_Labor_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Labor_Acct, org.compiere.model.I_C_ValidCombination.class, P_Labor_A);
	}

	@Override
	public void setP_Labor_Acct (final int P_Labor_Acct)
	{
		set_Value (COLUMNNAME_P_Labor_Acct, P_Labor_Acct);
	}

	@Override
	public int getP_Labor_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Labor_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_MethodChangeVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_MethodChangeVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_MethodChangeVariance_A(final org.compiere.model.I_C_ValidCombination P_MethodChangeVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_MethodChangeVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_MethodChangeVariance_A);
	}

	@Override
	public void setP_MethodChangeVariance_Acct (final int P_MethodChangeVariance_Acct)
	{
		set_Value (COLUMNNAME_P_MethodChangeVariance_Acct, P_MethodChangeVariance_Acct);
	}

	@Override
	public int getP_MethodChangeVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_MethodChangeVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_MixVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_MixVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_MixVariance_A(final org.compiere.model.I_C_ValidCombination P_MixVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_MixVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_MixVariance_A);
	}

	@Override
	public void setP_MixVariance_Acct (final int P_MixVariance_Acct)
	{
		set_Value (COLUMNNAME_P_MixVariance_Acct, P_MixVariance_Acct);
	}

	@Override
	public int getP_MixVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_MixVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_OutsideProcessing_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_OutsideProcessing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_OutsideProcessing_A(final org.compiere.model.I_C_ValidCombination P_OutsideProcessing_A)
	{
		set_ValueFromPO(COLUMNNAME_P_OutsideProcessing_Acct, org.compiere.model.I_C_ValidCombination.class, P_OutsideProcessing_A);
	}

	@Override
	public void setP_OutsideProcessing_Acct (final int P_OutsideProcessing_Acct)
	{
		set_Value (COLUMNNAME_P_OutsideProcessing_Acct, P_OutsideProcessing_Acct);
	}

	@Override
	public int getP_OutsideProcessing_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_OutsideProcessing_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Overhead_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Overhead_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Overhead_A(final org.compiere.model.I_C_ValidCombination P_Overhead_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Overhead_Acct, org.compiere.model.I_C_ValidCombination.class, P_Overhead_A);
	}

	@Override
	public void setP_Overhead_Acct (final int P_Overhead_Acct)
	{
		set_Value (COLUMNNAME_P_Overhead_Acct, P_Overhead_Acct);
	}

	@Override
	public int getP_Overhead_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Overhead_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_PurchasePriceVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_PurchasePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_PurchasePriceVariance_A(final org.compiere.model.I_C_ValidCombination P_PurchasePriceVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_PurchasePriceVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_PurchasePriceVariance_A);
	}

	@Override
	public void setP_PurchasePriceVariance_Acct (final int P_PurchasePriceVariance_Acct)
	{
		set_Value (COLUMNNAME_P_PurchasePriceVariance_Acct, P_PurchasePriceVariance_Acct);
	}

	@Override
	public int getP_PurchasePriceVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_PurchasePriceVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_RateVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_RateVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_RateVariance_A(final org.compiere.model.I_C_ValidCombination P_RateVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_RateVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_RateVariance_A);
	}

	@Override
	public void setP_RateVariance_Acct (final int P_RateVariance_Acct)
	{
		set_Value (COLUMNNAME_P_RateVariance_Acct, P_RateVariance_Acct);
	}

	@Override
	public int getP_RateVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_RateVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Revenue_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Revenue_A(final org.compiere.model.I_C_ValidCombination P_Revenue_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class, P_Revenue_A);
	}

	@Override
	public void setP_Revenue_Acct (final int P_Revenue_Acct)
	{
		set_Value (COLUMNNAME_P_Revenue_Acct, P_Revenue_Acct);
	}

	@Override
	public int getP_Revenue_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Revenue_Acct);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_Scrap_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_Scrap_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_Scrap_A(final org.compiere.model.I_C_ValidCombination P_Scrap_A)
	{
		set_ValueFromPO(COLUMNNAME_P_Scrap_Acct, org.compiere.model.I_C_ValidCombination.class, P_Scrap_A);
	}

	@Override
	public void setP_Scrap_Acct (final int P_Scrap_Acct)
	{
		set_Value (COLUMNNAME_P_Scrap_Acct, P_Scrap_Acct);
	}

	@Override
	public int getP_Scrap_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_Scrap_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountGrant_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_TradeDiscountGrant_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_TradeDiscountGrant_A(final org.compiere.model.I_C_ValidCombination P_TradeDiscountGrant_A)
	{
		set_ValueFromPO(COLUMNNAME_P_TradeDiscountGrant_Acct, org.compiere.model.I_C_ValidCombination.class, P_TradeDiscountGrant_A);
	}

	@Override
	public void setP_TradeDiscountGrant_Acct (final int P_TradeDiscountGrant_Acct)
	{
		set_Value (COLUMNNAME_P_TradeDiscountGrant_Acct, P_TradeDiscountGrant_Acct);
	}

	@Override
	public int getP_TradeDiscountGrant_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_TradeDiscountGrant_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_TradeDiscountRec_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_TradeDiscountRec_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_TradeDiscountRec_A(final org.compiere.model.I_C_ValidCombination P_TradeDiscountRec_A)
	{
		set_ValueFromPO(COLUMNNAME_P_TradeDiscountRec_Acct, org.compiere.model.I_C_ValidCombination.class, P_TradeDiscountRec_A);
	}

	@Override
	public void setP_TradeDiscountRec_Acct (final int P_TradeDiscountRec_Acct)
	{
		set_Value (COLUMNNAME_P_TradeDiscountRec_Acct, P_TradeDiscountRec_Acct);
	}

	@Override
	public int getP_TradeDiscountRec_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_TradeDiscountRec_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_UsageVariance_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_UsageVariance_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_UsageVariance_A(final org.compiere.model.I_C_ValidCombination P_UsageVariance_A)
	{
		set_ValueFromPO(COLUMNNAME_P_UsageVariance_Acct, org.compiere.model.I_C_ValidCombination.class, P_UsageVariance_A);
	}

	@Override
	public void setP_UsageVariance_Acct (final int P_UsageVariance_Acct)
	{
		set_Value (COLUMNNAME_P_UsageVariance_Acct, P_UsageVariance_Acct);
	}

	@Override
	public int getP_UsageVariance_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_UsageVariance_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getP_WIP_A()
	{
		return get_ValueAsPO(COLUMNNAME_P_WIP_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setP_WIP_A(final org.compiere.model.I_C_ValidCombination P_WIP_A)
	{
		set_ValueFromPO(COLUMNNAME_P_WIP_Acct, org.compiere.model.I_C_ValidCombination.class, P_WIP_A);
	}

	@Override
	public void setP_WIP_Acct (final int P_WIP_Acct)
	{
		set_Value (COLUMNNAME_P_WIP_Acct, P_WIP_Acct);
	}

	@Override
	public int getP_WIP_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_P_WIP_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getRealizedGain_A()
	{
		return get_ValueAsPO(COLUMNNAME_RealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setRealizedGain_A(final org.compiere.model.I_C_ValidCombination RealizedGain_A)
	{
		set_ValueFromPO(COLUMNNAME_RealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class, RealizedGain_A);
	}

	@Override
	public void setRealizedGain_Acct (final int RealizedGain_Acct)
	{
		set_Value (COLUMNNAME_RealizedGain_Acct, RealizedGain_Acct);
	}

	@Override
	public int getRealizedGain_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_RealizedGain_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getRealizedLoss_A()
	{
		return get_ValueAsPO(COLUMNNAME_RealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setRealizedLoss_A(final org.compiere.model.I_C_ValidCombination RealizedLoss_A)
	{
		set_ValueFromPO(COLUMNNAME_RealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class, RealizedLoss_A);
	}

	@Override
	public void setRealizedLoss_Acct (final int RealizedLoss_Acct)
	{
		set_Value (COLUMNNAME_RealizedLoss_Acct, RealizedLoss_Acct);
	}

	@Override
	public int getRealizedLoss_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_RealizedLoss_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Credit_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Credit_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Credit_A(final org.compiere.model.I_C_ValidCombination T_Credit_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Credit_Acct, org.compiere.model.I_C_ValidCombination.class, T_Credit_A);
	}

	@Override
	public void setT_Credit_Acct (final int T_Credit_Acct)
	{
		set_Value (COLUMNNAME_T_Credit_Acct, T_Credit_Acct);
	}

	@Override
	public int getT_Credit_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_T_Credit_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Due_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Due_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Due_A(final org.compiere.model.I_C_ValidCombination T_Due_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Due_Acct, org.compiere.model.I_C_ValidCombination.class, T_Due_A);
	}

	@Override
	public void setT_Due_Acct (final int T_Due_Acct)
	{
		set_Value (COLUMNNAME_T_Due_Acct, T_Due_Acct);
	}

	@Override
	public int getT_Due_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_T_Due_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Expense_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Expense_A(final org.compiere.model.I_C_ValidCombination T_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, T_Expense_A);
	}

	@Override
	public void setT_Expense_Acct (final int T_Expense_Acct)
	{
		set_Value (COLUMNNAME_T_Expense_Acct, T_Expense_Acct);
	}

	@Override
	public int getT_Expense_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_T_Expense_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Liability_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Liability_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Liability_A(final org.compiere.model.I_C_ValidCombination T_Liability_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Liability_Acct, org.compiere.model.I_C_ValidCombination.class, T_Liability_A);
	}

	@Override
	public void setT_Liability_Acct (final int T_Liability_Acct)
	{
		set_Value (COLUMNNAME_T_Liability_Acct, T_Liability_Acct);
	}

	@Override
	public int getT_Liability_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_T_Liability_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Receivables_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Receivables_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Receivables_A(final org.compiere.model.I_C_ValidCombination T_Receivables_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Receivables_Acct, org.compiere.model.I_C_ValidCombination.class, T_Receivables_A);
	}

	@Override
	public void setT_Receivables_Acct (final int T_Receivables_Acct)
	{
		set_Value (COLUMNNAME_T_Receivables_Acct, T_Receivables_Acct);
	}

	@Override
	public int getT_Receivables_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_T_Receivables_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getUnEarnedRevenue_A()
	{
		return get_ValueAsPO(COLUMNNAME_UnEarnedRevenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setUnEarnedRevenue_A(final org.compiere.model.I_C_ValidCombination UnEarnedRevenue_A)
	{
		set_ValueFromPO(COLUMNNAME_UnEarnedRevenue_Acct, org.compiere.model.I_C_ValidCombination.class, UnEarnedRevenue_A);
	}

	@Override
	public void setUnEarnedRevenue_Acct (final int UnEarnedRevenue_Acct)
	{
		set_Value (COLUMNNAME_UnEarnedRevenue_Acct, UnEarnedRevenue_Acct);
	}

	@Override
	public int getUnEarnedRevenue_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_UnEarnedRevenue_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getUnrealizedGain_A()
	{
		return get_ValueAsPO(COLUMNNAME_UnrealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setUnrealizedGain_A(final org.compiere.model.I_C_ValidCombination UnrealizedGain_A)
	{
		set_ValueFromPO(COLUMNNAME_UnrealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class, UnrealizedGain_A);
	}

	@Override
	public void setUnrealizedGain_Acct (final int UnrealizedGain_Acct)
	{
		set_Value (COLUMNNAME_UnrealizedGain_Acct, UnrealizedGain_Acct);
	}

	@Override
	public int getUnrealizedGain_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_UnrealizedGain_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getUnrealizedLoss_A()
	{
		return get_ValueAsPO(COLUMNNAME_UnrealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setUnrealizedLoss_A(final org.compiere.model.I_C_ValidCombination UnrealizedLoss_A)
	{
		set_ValueFromPO(COLUMNNAME_UnrealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class, UnrealizedLoss_A);
	}

	@Override
	public void setUnrealizedLoss_Acct (final int UnrealizedLoss_Acct)
	{
		set_Value (COLUMNNAME_UnrealizedLoss_Acct, UnrealizedLoss_Acct);
	}

	@Override
	public int getUnrealizedLoss_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_UnrealizedLoss_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getV_Liability_A()
	{
		return get_ValueAsPO(COLUMNNAME_V_Liability_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setV_Liability_A(final org.compiere.model.I_C_ValidCombination V_Liability_A)
	{
		set_ValueFromPO(COLUMNNAME_V_Liability_Acct, org.compiere.model.I_C_ValidCombination.class, V_Liability_A);
	}

	@Override
	public void setV_Liability_Acct (final int V_Liability_Acct)
	{
		set_Value (COLUMNNAME_V_Liability_Acct, V_Liability_Acct);
	}

	@Override
	public int getV_Liability_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_V_Liability_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getV_Liability_Services_A()
	{
		return get_ValueAsPO(COLUMNNAME_V_Liability_Services_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setV_Liability_Services_A(final org.compiere.model.I_C_ValidCombination V_Liability_Services_A)
	{
		set_ValueFromPO(COLUMNNAME_V_Liability_Services_Acct, org.compiere.model.I_C_ValidCombination.class, V_Liability_Services_A);
	}

	@Override
	public void setV_Liability_Services_Acct (final int V_Liability_Services_Acct)
	{
		set_Value (COLUMNNAME_V_Liability_Services_Acct, V_Liability_Services_Acct);
	}

	@Override
	public int getV_Liability_Services_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_V_Liability_Services_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getV_Prepayment_A()
	{
		return get_ValueAsPO(COLUMNNAME_V_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setV_Prepayment_A(final org.compiere.model.I_C_ValidCombination V_Prepayment_A)
	{
		set_ValueFromPO(COLUMNNAME_V_Prepayment_Acct, org.compiere.model.I_C_ValidCombination.class, V_Prepayment_A);
	}

	@Override
	public void setV_Prepayment_Acct (final int V_Prepayment_Acct)
	{
		set_Value (COLUMNNAME_V_Prepayment_Acct, V_Prepayment_Acct);
	}

	@Override
	public int getV_Prepayment_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_V_Prepayment_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getW_Differences_A()
	{
		return get_ValueAsPO(COLUMNNAME_W_Differences_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setW_Differences_A(final org.compiere.model.I_C_ValidCombination W_Differences_A)
	{
		set_ValueFromPO(COLUMNNAME_W_Differences_Acct, org.compiere.model.I_C_ValidCombination.class, W_Differences_A);
	}

	@Override
	public void setW_Differences_Acct (final int W_Differences_Acct)
	{
		set_Value (COLUMNNAME_W_Differences_Acct, W_Differences_Acct);
	}

	@Override
	public int getW_Differences_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_W_Differences_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getW_InvActualAdjust_A()
	{
		return get_ValueAsPO(COLUMNNAME_W_InvActualAdjust_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setW_InvActualAdjust_A(final org.compiere.model.I_C_ValidCombination W_InvActualAdjust_A)
	{
		set_ValueFromPO(COLUMNNAME_W_InvActualAdjust_Acct, org.compiere.model.I_C_ValidCombination.class, W_InvActualAdjust_A);
	}

	@Override
	public void setW_InvActualAdjust_Acct (final int W_InvActualAdjust_Acct)
	{
		set_Value (COLUMNNAME_W_InvActualAdjust_Acct, W_InvActualAdjust_Acct);
	}

	@Override
	public int getW_InvActualAdjust_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_W_InvActualAdjust_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getW_Inventory_A()
	{
		return get_ValueAsPO(COLUMNNAME_W_Inventory_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setW_Inventory_A(final org.compiere.model.I_C_ValidCombination W_Inventory_A)
	{
		set_ValueFromPO(COLUMNNAME_W_Inventory_Acct, org.compiere.model.I_C_ValidCombination.class, W_Inventory_A);
	}

	@Override
	public void setW_Inventory_Acct (final int W_Inventory_Acct)
	{
		set_Value (COLUMNNAME_W_Inventory_Acct, W_Inventory_Acct);
	}

	@Override
	public int getW_Inventory_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_W_Inventory_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getWithholding_A()
	{
		return get_ValueAsPO(COLUMNNAME_Withholding_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setWithholding_A(final org.compiere.model.I_C_ValidCombination Withholding_A)
	{
		set_ValueFromPO(COLUMNNAME_Withholding_Acct, org.compiere.model.I_C_ValidCombination.class, Withholding_A);
	}

	@Override
	public void setWithholding_Acct (final int Withholding_Acct)
	{
		set_Value (COLUMNNAME_Withholding_Acct, Withholding_Acct);
	}

	@Override
	public int getWithholding_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_Withholding_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getW_Revaluation_A()
	{
		return get_ValueAsPO(COLUMNNAME_W_Revaluation_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setW_Revaluation_A(final org.compiere.model.I_C_ValidCombination W_Revaluation_A)
	{
		set_ValueFromPO(COLUMNNAME_W_Revaluation_Acct, org.compiere.model.I_C_ValidCombination.class, W_Revaluation_A);
	}

	@Override
	public void setW_Revaluation_Acct (final int W_Revaluation_Acct)
	{
		set_Value (COLUMNNAME_W_Revaluation_Acct, W_Revaluation_Acct);
	}

	@Override
	public int getW_Revaluation_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_W_Revaluation_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getWriteOff_A()
	{
		return get_ValueAsPO(COLUMNNAME_WriteOff_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setWriteOff_A(final org.compiere.model.I_C_ValidCombination WriteOff_A)
	{
		set_ValueFromPO(COLUMNNAME_WriteOff_Acct, org.compiere.model.I_C_ValidCombination.class, WriteOff_A);
	}

	@Override
	public void setWriteOff_Acct (final int WriteOff_Acct)
	{
		set_Value (COLUMNNAME_WriteOff_Acct, WriteOff_Acct);
	}

	@Override
	public int getWriteOff_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_WriteOff_Acct);
	}
}
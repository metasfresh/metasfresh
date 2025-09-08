// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BP_BankAccount_Acct
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BP_BankAccount_Acct extends org.compiere.model.PO implements I_C_BP_BankAccount_Acct, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1210025809L;

    /** Standard Constructor */
    public X_C_BP_BankAccount_Acct (final Properties ctx, final int C_BP_BankAccount_Acct_ID, @Nullable final String trxName)
    {
      super (ctx, C_BP_BankAccount_Acct_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BP_BankAccount_Acct (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BP_BankAccount_Acct_ID (final int C_BP_BankAccount_Acct_ID)
	{
		if (C_BP_BankAccount_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_Acct_ID, C_BP_BankAccount_Acct_ID);
	}

	@Override
	public int getC_BP_BankAccount_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_Acct_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
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
	public org.compiere.model.I_C_ValidCombination getPayment_WriteOff_A()
	{
		return get_ValueAsPO(COLUMNNAME_Payment_WriteOff_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPayment_WriteOff_A(final org.compiere.model.I_C_ValidCombination Payment_WriteOff_A)
	{
		set_ValueFromPO(COLUMNNAME_Payment_WriteOff_Acct, org.compiere.model.I_C_ValidCombination.class, Payment_WriteOff_A);
	}

	@Override
	public void setPayment_WriteOff_Acct (final int Payment_WriteOff_Acct)
	{
		set_Value (COLUMNNAME_Payment_WriteOff_Acct, Payment_WriteOff_Acct);
	}

	@Override
	public int getPayment_WriteOff_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_Payment_WriteOff_Acct);
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
}
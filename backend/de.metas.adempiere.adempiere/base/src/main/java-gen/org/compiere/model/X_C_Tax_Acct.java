// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Tax_Acct
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Tax_Acct extends org.compiere.model.PO implements I_C_Tax_Acct, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -755187742L;

    /** Standard Constructor */
    public X_C_Tax_Acct (final Properties ctx, final int C_Tax_Acct_ID, @Nullable final String trxName)
    {
      super (ctx, C_Tax_Acct_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Tax_Acct (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Tax_Acct_ID (final int C_Tax_Acct_ID)
	{
		if (C_Tax_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Tax_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_Acct_ID, C_Tax_Acct_ID);
	}

	@Override
	public int getC_Tax_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_Acct_ID);
	}

	@Override
	public void setC_Tax_ID (final int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, C_Tax_ID);
	}

	@Override
	public int getC_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_ID);
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
	public org.compiere.model.I_C_ValidCombination getT_PayDiscount_Exp_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_PayDiscount_Exp_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_PayDiscount_Exp_A(final org.compiere.model.I_C_ValidCombination T_PayDiscount_Exp_A)
	{
		set_ValueFromPO(COLUMNNAME_T_PayDiscount_Exp_Acct, org.compiere.model.I_C_ValidCombination.class, T_PayDiscount_Exp_A);
	}

	@Override
	public void setT_PayDiscount_Exp_Acct (final int T_PayDiscount_Exp_Acct)
	{
		set_Value (COLUMNNAME_T_PayDiscount_Exp_Acct, T_PayDiscount_Exp_Acct);
	}

	@Override
	public int getT_PayDiscount_Exp_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_T_PayDiscount_Exp_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_PayDiscount_Rev_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_PayDiscount_Rev_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_PayDiscount_Rev_A(final org.compiere.model.I_C_ValidCombination T_PayDiscount_Rev_A)
	{
		set_ValueFromPO(COLUMNNAME_T_PayDiscount_Rev_Acct, org.compiere.model.I_C_ValidCombination.class, T_PayDiscount_Rev_A);
	}

	@Override
	public void setT_PayDiscount_Rev_Acct (final int T_PayDiscount_Rev_Acct)
	{
		set_Value (COLUMNNAME_T_PayDiscount_Rev_Acct, T_PayDiscount_Rev_Acct);
	}

	@Override
	public int getT_PayDiscount_Rev_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_T_PayDiscount_Rev_Acct);
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
	public org.compiere.model.I_C_ValidCombination getT_Revenue_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Revenue_A(final org.compiere.model.I_C_ValidCombination T_Revenue_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class, T_Revenue_A);
	}

	@Override
	public void setT_Revenue_Acct (final int T_Revenue_Acct)
	{
		set_Value (COLUMNNAME_T_Revenue_Acct, T_Revenue_Acct);
	}

	@Override
	public int getT_Revenue_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_T_Revenue_Acct);
	}
}
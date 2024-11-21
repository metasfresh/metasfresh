// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_AcctSchema_GL
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_AcctSchema_GL extends org.compiere.model.PO implements I_C_AcctSchema_GL, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 264057558L;

    /** Standard Constructor */
    public X_C_AcctSchema_GL (final Properties ctx, final int C_AcctSchema_GL_ID, @Nullable final String trxName)
    {
      super (ctx, C_AcctSchema_GL_ID, trxName);
    }

    /** Load Constructor */
    public X_C_AcctSchema_GL (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_AcctSchema_GL_ID (final int C_AcctSchema_GL_ID)
	{
		if (C_AcctSchema_GL_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_GL_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_GL_ID, C_AcctSchema_GL_ID);
	}

	@Override
	public int getC_AcctSchema_GL_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_GL_ID);
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
	public org.compiere.model.I_C_ValidCombination getCashRounding_A()
	{
		return get_ValueAsPO(COLUMNNAME_CashRounding_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCashRounding_A(final org.compiere.model.I_C_ValidCombination CashRounding_A)
	{
		set_ValueFromPO(COLUMNNAME_CashRounding_Acct, org.compiere.model.I_C_ValidCombination.class, CashRounding_A);
	}

	@Override
	public void setCashRounding_Acct (final int CashRounding_Acct)
	{
		set_Value (COLUMNNAME_CashRounding_Acct, CashRounding_Acct);
	}

	@Override
	public int getCashRounding_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_CashRounding_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCommitmentOffset_A()
	{
		return get_ValueAsPO(COLUMNNAME_CommitmentOffset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCommitmentOffset_A(final org.compiere.model.I_C_ValidCombination CommitmentOffset_A)
	{
		set_ValueFromPO(COLUMNNAME_CommitmentOffset_Acct, org.compiere.model.I_C_ValidCombination.class, CommitmentOffset_A);
	}

	@Override
	public void setCommitmentOffset_Acct (final int CommitmentOffset_Acct)
	{
		set_Value (COLUMNNAME_CommitmentOffset_Acct, CommitmentOffset_Acct);
	}

	@Override
	public int getCommitmentOffset_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_CommitmentOffset_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCommitmentOffsetSales_A()
	{
		return get_ValueAsPO(COLUMNNAME_CommitmentOffsetSales_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCommitmentOffsetSales_A(final org.compiere.model.I_C_ValidCombination CommitmentOffsetSales_A)
	{
		set_ValueFromPO(COLUMNNAME_CommitmentOffsetSales_Acct, org.compiere.model.I_C_ValidCombination.class, CommitmentOffsetSales_A);
	}

	@Override
	public void setCommitmentOffsetSales_Acct (final int CommitmentOffsetSales_Acct)
	{
		set_Value (COLUMNNAME_CommitmentOffsetSales_Acct, CommitmentOffsetSales_Acct);
	}

	@Override
	public int getCommitmentOffsetSales_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_CommitmentOffsetSales_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCurrencyBalancing_A()
	{
		return get_ValueAsPO(COLUMNNAME_CurrencyBalancing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCurrencyBalancing_A(final org.compiere.model.I_C_ValidCombination CurrencyBalancing_A)
	{
		set_ValueFromPO(COLUMNNAME_CurrencyBalancing_Acct, org.compiere.model.I_C_ValidCombination.class, CurrencyBalancing_A);
	}

	@Override
	public void setCurrencyBalancing_Acct (final int CurrencyBalancing_Acct)
	{
		set_Value (COLUMNNAME_CurrencyBalancing_Acct, CurrencyBalancing_Acct);
	}

	@Override
	public int getCurrencyBalancing_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_CurrencyBalancing_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getIncomeSummary_A()
	{
		return get_ValueAsPO(COLUMNNAME_IncomeSummary_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setIncomeSummary_A(final org.compiere.model.I_C_ValidCombination IncomeSummary_A)
	{
		set_ValueFromPO(COLUMNNAME_IncomeSummary_Acct, org.compiere.model.I_C_ValidCombination.class, IncomeSummary_A);
	}

	@Override
	public void setIncomeSummary_Acct (final int IncomeSummary_Acct)
	{
		set_Value (COLUMNNAME_IncomeSummary_Acct, IncomeSummary_Acct);
	}

	@Override
	public int getIncomeSummary_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_IncomeSummary_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getIntercompanyDueFrom_A()
	{
		return get_ValueAsPO(COLUMNNAME_IntercompanyDueFrom_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setIntercompanyDueFrom_A(final org.compiere.model.I_C_ValidCombination IntercompanyDueFrom_A)
	{
		set_ValueFromPO(COLUMNNAME_IntercompanyDueFrom_Acct, org.compiere.model.I_C_ValidCombination.class, IntercompanyDueFrom_A);
	}

	@Override
	public void setIntercompanyDueFrom_Acct (final int IntercompanyDueFrom_Acct)
	{
		set_Value (COLUMNNAME_IntercompanyDueFrom_Acct, IntercompanyDueFrom_Acct);
	}

	@Override
	public int getIntercompanyDueFrom_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_IntercompanyDueFrom_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getIntercompanyDueTo_A()
	{
		return get_ValueAsPO(COLUMNNAME_IntercompanyDueTo_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setIntercompanyDueTo_A(final org.compiere.model.I_C_ValidCombination IntercompanyDueTo_A)
	{
		set_ValueFromPO(COLUMNNAME_IntercompanyDueTo_Acct, org.compiere.model.I_C_ValidCombination.class, IntercompanyDueTo_A);
	}

	@Override
	public void setIntercompanyDueTo_Acct (final int IntercompanyDueTo_Acct)
	{
		set_Value (COLUMNNAME_IntercompanyDueTo_Acct, IntercompanyDueTo_Acct);
	}

	@Override
	public int getIntercompanyDueTo_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_IntercompanyDueTo_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPPVOffset_A()
	{
		return get_ValueAsPO(COLUMNNAME_PPVOffset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPPVOffset_A(final org.compiere.model.I_C_ValidCombination PPVOffset_A)
	{
		set_ValueFromPO(COLUMNNAME_PPVOffset_Acct, org.compiere.model.I_C_ValidCombination.class, PPVOffset_A);
	}

	@Override
	public void setPPVOffset_Acct (final int PPVOffset_Acct)
	{
		set_Value (COLUMNNAME_PPVOffset_Acct, PPVOffset_Acct);
	}

	@Override
	public int getPPVOffset_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_PPVOffset_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getRetainedEarning_A()
	{
		return get_ValueAsPO(COLUMNNAME_RetainedEarning_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setRetainedEarning_A(final org.compiere.model.I_C_ValidCombination RetainedEarning_A)
	{
		set_ValueFromPO(COLUMNNAME_RetainedEarning_Acct, org.compiere.model.I_C_ValidCombination.class, RetainedEarning_A);
	}

	@Override
	public void setRetainedEarning_Acct (final int RetainedEarning_Acct)
	{
		set_Value (COLUMNNAME_RetainedEarning_Acct, RetainedEarning_Acct);
	}

	@Override
	public int getRetainedEarning_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_RetainedEarning_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getSuspenseBalancing_A()
	{
		return get_ValueAsPO(COLUMNNAME_SuspenseBalancing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setSuspenseBalancing_A(final org.compiere.model.I_C_ValidCombination SuspenseBalancing_A)
	{
		set_ValueFromPO(COLUMNNAME_SuspenseBalancing_Acct, org.compiere.model.I_C_ValidCombination.class, SuspenseBalancing_A);
	}

	@Override
	public void setSuspenseBalancing_Acct (final int SuspenseBalancing_Acct)
	{
		set_Value (COLUMNNAME_SuspenseBalancing_Acct, SuspenseBalancing_Acct);
	}

	@Override
	public int getSuspenseBalancing_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_SuspenseBalancing_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getSuspenseError_A()
	{
		return get_ValueAsPO(COLUMNNAME_SuspenseError_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setSuspenseError_A(final org.compiere.model.I_C_ValidCombination SuspenseError_A)
	{
		set_ValueFromPO(COLUMNNAME_SuspenseError_Acct, org.compiere.model.I_C_ValidCombination.class, SuspenseError_A);
	}

	@Override
	public void setSuspenseError_Acct (final int SuspenseError_Acct)
	{
		set_Value (COLUMNNAME_SuspenseError_Acct, SuspenseError_Acct);
	}

	@Override
	public int getSuspenseError_Acct() 
	{
		return get_ValueAsInt(COLUMNNAME_SuspenseError_Acct);
	}

	@Override
	public void setUseCurrencyBalancing (final boolean UseCurrencyBalancing)
	{
		set_Value (COLUMNNAME_UseCurrencyBalancing, UseCurrencyBalancing);
	}

	@Override
	public boolean isUseCurrencyBalancing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_UseCurrencyBalancing);
	}

	@Override
	public void setUseSuspenseBalancing (final boolean UseSuspenseBalancing)
	{
		set_Value (COLUMNNAME_UseSuspenseBalancing, UseSuspenseBalancing);
	}

	@Override
	public boolean isUseSuspenseBalancing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_UseSuspenseBalancing);
	}

	@Override
	public void setUseSuspenseError (final boolean UseSuspenseError)
	{
		set_Value (COLUMNNAME_UseSuspenseError, UseSuspenseError);
	}

	@Override
	public boolean isUseSuspenseError() 
	{
		return get_ValueAsBoolean(COLUMNNAME_UseSuspenseError);
	}
}
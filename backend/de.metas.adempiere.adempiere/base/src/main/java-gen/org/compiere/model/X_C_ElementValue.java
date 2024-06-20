// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_ElementValue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ElementValue extends org.compiere.model.PO implements I_C_ElementValue, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1059934605L;

    /** Standard Constructor */
    public X_C_ElementValue (final Properties ctx, final int C_ElementValue_ID, @Nullable final String trxName)
    {
      super (ctx, C_ElementValue_ID, trxName);
    }

    /** Load Constructor */
    public X_C_ElementValue (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * AccountSign AD_Reference_ID=118
	 * Reference name: C_ElementValue Account Sign
	 */
	public static final int ACCOUNTSIGN_AD_Reference_ID=118;
	/** Natural = N */
	public static final String ACCOUNTSIGN_Natural = "N";
	/** Debit = D */
	public static final String ACCOUNTSIGN_Debit = "D";
	/** Credit = C */
	public static final String ACCOUNTSIGN_Credit = "C";
	@Override
	public void setAccountSign (final java.lang.String AccountSign)
	{
		set_Value (COLUMNNAME_AccountSign, AccountSign);
	}

	@Override
	public java.lang.String getAccountSign() 
	{
		return get_ValueAsString(COLUMNNAME_AccountSign);
	}

	/** 
	 * AccountType AD_Reference_ID=117
	 * Reference name: C_ElementValue AccountType
	 */
	public static final int ACCOUNTTYPE_AD_Reference_ID=117;
	/** Asset = A */
	public static final String ACCOUNTTYPE_Asset = "A";
	/** Liability = L */
	public static final String ACCOUNTTYPE_Liability = "L";
	/** Revenue = R */
	public static final String ACCOUNTTYPE_Revenue = "R";
	/** Expense = E */
	public static final String ACCOUNTTYPE_Expense = "E";
	/** OwnerSEquity = O */
	public static final String ACCOUNTTYPE_OwnerSEquity = "O";
	/** Memo = M */
	public static final String ACCOUNTTYPE_Memo = "M";
	@Override
	public void setAccountType (final java.lang.String AccountType)
	{
		set_Value (COLUMNNAME_AccountType, AccountType);
	}

	@Override
	public java.lang.String getAccountType() 
	{
		return get_ValueAsString(COLUMNNAME_AccountType);
	}

	@Override
	public void setC_Activity_ID (final int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, C_Activity_ID);
	}

	@Override
	public int getC_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Activity_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setC_Element_ID (final int C_Element_ID)
	{
		if (C_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Element_ID, C_Element_ID);
	}

	@Override
	public int getC_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Element_ID);
	}

	@Override
	public void setC_ElementValue_ID (final int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ElementValue_ID, C_ElementValue_ID);
	}

	@Override
	public int getC_ElementValue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ElementValue_ID);
	}

	@Override
	public void setC_Tax_ID (final int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, C_Tax_ID);
	}

	@Override
	public int getC_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_ID);
	}

	@Override
	public void setDefault_Account (final @Nullable java.lang.String Default_Account)
	{
		set_Value (COLUMNNAME_Default_Account, Default_Account);
	}

	@Override
	public java.lang.String getDefault_Account() 
	{
		return get_ValueAsString(COLUMNNAME_Default_Account);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setIsAutoTaxAccount (final boolean IsAutoTaxAccount)
	{
		set_Value (COLUMNNAME_IsAutoTaxAccount, IsAutoTaxAccount);
	}

	@Override
	public boolean isAutoTaxAccount() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoTaxAccount);
	}

	@Override
	public void setIsBankAccount (final boolean IsBankAccount)
	{
		set_Value (COLUMNNAME_IsBankAccount, IsBankAccount);
	}

	@Override
	public boolean isBankAccount() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBankAccount);
	}

	@Override
	public void setIsDocControlled (final boolean IsDocControlled)
	{
		set_Value (COLUMNNAME_IsDocControlled, IsDocControlled);
	}

	@Override
	public boolean isDocControlled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDocControlled);
	}

	@Override
	public void setIsForeignCurrency (final boolean IsForeignCurrency)
	{
		set_Value (COLUMNNAME_IsForeignCurrency, IsForeignCurrency);
	}

	@Override
	public boolean isForeignCurrency() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsForeignCurrency);
	}

	@Override
	public void setIsMandatoryActivity (final boolean IsMandatoryActivity)
	{
		set_Value (COLUMNNAME_IsMandatoryActivity, IsMandatoryActivity);
	}

	@Override
	public boolean isMandatoryActivity() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMandatoryActivity);
	}

	@Override
	public void setIsSummary (final boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, IsSummary);
	}

	@Override
	public boolean isSummary() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSummary);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getParent()
	{
		return get_ValueAsPO(COLUMNNAME_Parent_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setParent(final org.compiere.model.I_C_ElementValue Parent)
	{
		set_ValueFromPO(COLUMNNAME_Parent_ID, org.compiere.model.I_C_ElementValue.class, Parent);
	}

	@Override
	public void setParent_ID (final int Parent_ID)
	{
		if (Parent_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Parent_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Parent_ID, Parent_ID);
	}

	@Override
	public int getParent_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Parent_ID);
	}

	@Override
	public void setPostActual (final boolean PostActual)
	{
		set_Value (COLUMNNAME_PostActual, PostActual);
	}

	@Override
	public boolean isPostActual() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PostActual);
	}

	@Override
	public void setPostBudget (final boolean PostBudget)
	{
		set_Value (COLUMNNAME_PostBudget, PostBudget);
	}

	@Override
	public boolean isPostBudget() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PostBudget);
	}

	@Override
	public void setPostEncumbrance (final boolean PostEncumbrance)
	{
		set_Value (COLUMNNAME_PostEncumbrance, PostEncumbrance);
	}

	@Override
	public boolean isPostEncumbrance() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PostEncumbrance);
	}

	@Override
	public void setPostStatistical (final boolean PostStatistical)
	{
		set_Value (COLUMNNAME_PostStatistical, PostStatistical);
	}

	@Override
	public boolean isPostStatistical() 
	{
		return get_ValueAsBoolean(COLUMNNAME_PostStatistical);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_ValueNoCheck (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setValidFrom (final @Nullable java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}
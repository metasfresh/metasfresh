// Generated Model - DO NOT CHANGE
package de.metas.acct.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Fact_Acct_UserChange
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Fact_Acct_UserChange extends org.compiere.model.PO implements I_Fact_Acct_UserChange, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -780999996L;

    /** Standard Constructor */
    public X_Fact_Acct_UserChange (final Properties ctx, final int Fact_Acct_UserChange_ID, @Nullable final String trxName)
    {
      super (ctx, Fact_Acct_UserChange_ID, trxName);
    }

    /** Load Constructor */
    public X_Fact_Acct_UserChange (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAccount_ID (final int Account_ID)
	{
		if (Account_ID < 1) 
			set_Value (COLUMNNAME_Account_ID, null);
		else 
			set_Value (COLUMNNAME_Account_ID, Account_ID);
	}

	@Override
	public int getAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Account_ID);
	}

	@Override
	public void setAmount_DC (final BigDecimal Amount_DC)
	{
		set_Value (COLUMNNAME_Amount_DC, Amount_DC);
	}

	@Override
	public BigDecimal getAmount_DC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount_DC);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmount_LC (final BigDecimal Amount_LC)
	{
		set_Value (COLUMNNAME_Amount_LC, Amount_LC);
	}

	@Override
	public BigDecimal getAmount_LC() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount_LC);
		return bd != null ? bd : BigDecimal.ZERO;
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
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
	}

	@Override
	public int getC_AcctSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_ID);
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

	/** 
	 * ChangeType AD_Reference_ID=541813
	 * Reference name: Fact_Acct_UserChange_ChangeType
	 */
	public static final int CHANGETYPE_AD_Reference_ID=541813;
	/** Change = C */
	public static final String CHANGETYPE_Change = "C";
	/** Add = A */
	public static final String CHANGETYPE_Add = "A";
	/** Delete = D */
	public static final String CHANGETYPE_Delete = "D";
	@Override
	public void setChangeType (final java.lang.String ChangeType)
	{
		set_Value (COLUMNNAME_ChangeType, ChangeType);
	}

	@Override
	public java.lang.String getChangeType() 
	{
		return get_ValueAsString(COLUMNNAME_ChangeType);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_OrderSO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderSO(final org.compiere.model.I_C_Order C_OrderSO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderSO_ID, org.compiere.model.I_C_Order.class, C_OrderSO);
	}

	@Override
	public void setC_OrderSO_ID (final int C_OrderSO_ID)
	{
		if (C_OrderSO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderSO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderSO_ID, C_OrderSO_ID);
	}

	@Override
	public int getC_OrderSO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderSO_ID);
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
	public void setDocument_Currency_ID (final int Document_Currency_ID)
	{
		if (Document_Currency_ID < 1) 
			set_Value (COLUMNNAME_Document_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_Document_Currency_ID, Document_Currency_ID);
	}

	@Override
	public int getDocument_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Document_Currency_ID);
	}

	@Override
	public void setFact_Acct_UserChange_ID (final int Fact_Acct_UserChange_ID)
	{
		if (Fact_Acct_UserChange_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Fact_Acct_UserChange_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Fact_Acct_UserChange_ID, Fact_Acct_UserChange_ID);
	}

	@Override
	public int getFact_Acct_UserChange_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Fact_Acct_UserChange_ID);
	}

	@Override
	public void setLocal_Currency_ID (final int Local_Currency_ID)
	{
		if (Local_Currency_ID < 1) 
			set_Value (COLUMNNAME_Local_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_Local_Currency_ID, Local_Currency_ID);
	}

	@Override
	public int getLocal_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Local_Currency_ID);
	}

	@Override
	public void setMatchKey (final @Nullable java.lang.String MatchKey)
	{
		set_Value (COLUMNNAME_MatchKey, MatchKey);
	}

	@Override
	public java.lang.String getMatchKey() 
	{
		return get_ValueAsString(COLUMNNAME_MatchKey);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	/** 
	 * PostingSign AD_Reference_ID=541699
	 * Reference name: PostingSign
	 */
	public static final int POSTINGSIGN_AD_Reference_ID=541699;
	/** DR = D */
	public static final String POSTINGSIGN_DR = "D";
	/** CR = C */
	public static final String POSTINGSIGN_CR = "C";
	@Override
	public void setPostingSign (final java.lang.String PostingSign)
	{
		set_Value (COLUMNNAME_PostingSign, PostingSign);
	}

	@Override
	public java.lang.String getPostingSign() 
	{
		return get_ValueAsString(COLUMNNAME_PostingSign);
	}

	@Override
	public void setUserElementString1 (final @Nullable java.lang.String UserElementString1)
	{
		set_Value (COLUMNNAME_UserElementString1, UserElementString1);
	}

	@Override
	public java.lang.String getUserElementString1() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString1);
	}
}
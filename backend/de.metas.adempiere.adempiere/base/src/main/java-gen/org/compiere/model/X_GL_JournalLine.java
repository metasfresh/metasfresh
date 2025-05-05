// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for GL_JournalLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_GL_JournalLine extends org.compiere.model.PO implements I_GL_JournalLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1927037739L;

    /** Standard Constructor */
    public X_GL_JournalLine (final Properties ctx, final int GL_JournalLine_ID, @Nullable final String trxName)
    {
      super (ctx, GL_JournalLine_ID, trxName);
    }

    /** Load Constructor */
    public X_GL_JournalLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_A_Asset_Group getA_Asset_Group()
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_Group_ID, org.compiere.model.I_A_Asset_Group.class);
	}

	@Override
	public void setA_Asset_Group(final org.compiere.model.I_A_Asset_Group A_Asset_Group)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_Group_ID, org.compiere.model.I_A_Asset_Group.class, A_Asset_Group);
	}

	@Override
	public void setA_Asset_Group_ID (final int A_Asset_Group_ID)
	{
		if (A_Asset_Group_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Group_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Group_ID, A_Asset_Group_ID);
	}

	@Override
	public int getA_Asset_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_A_Asset_Group_ID);
	}

	@Override
	public org.compiere.model.I_A_Asset getA_Asset()
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class);
	}

	@Override
	public void setA_Asset(final org.compiere.model.I_A_Asset A_Asset)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class, A_Asset);
	}

	@Override
	public void setA_Asset_ID (final int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, A_Asset_ID);
	}

	@Override
	public int getA_Asset_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_A_Asset_ID);
	}

	@Override
	public void setA_CreateAsset (final boolean A_CreateAsset)
	{
		set_Value (COLUMNNAME_A_CreateAsset, A_CreateAsset);
	}

	@Override
	public boolean isA_CreateAsset() 
	{
		return get_ValueAsBoolean(COLUMNNAME_A_CreateAsset);
	}

	@Override
	public void setA_Processed (final boolean A_Processed)
	{
		set_Value (COLUMNNAME_A_Processed, A_Processed);
	}

	@Override
	public boolean isA_Processed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_A_Processed);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getAccount_CR()
	{
		return get_ValueAsPO(COLUMNNAME_Account_CR_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setAccount_CR(final org.compiere.model.I_C_ValidCombination Account_CR)
	{
		set_ValueFromPO(COLUMNNAME_Account_CR_ID, org.compiere.model.I_C_ValidCombination.class, Account_CR);
	}

	@Override
	public void setAccount_CR_ID (final int Account_CR_ID)
	{
		if (Account_CR_ID < 1) 
			set_Value (COLUMNNAME_Account_CR_ID, null);
		else 
			set_Value (COLUMNNAME_Account_CR_ID, Account_CR_ID);
	}

	@Override
	public int getAccount_CR_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Account_CR_ID);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getAccount_DR()
	{
		return get_ValueAsPO(COLUMNNAME_Account_DR_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setAccount_DR(final org.compiere.model.I_C_ValidCombination Account_DR)
	{
		set_ValueFromPO(COLUMNNAME_Account_DR_ID, org.compiere.model.I_C_ValidCombination.class, Account_DR);
	}

	@Override
	public void setAccount_DR_ID (final int Account_DR_ID)
	{
		if (Account_DR_ID < 1) 
			set_Value (COLUMNNAME_Account_DR_ID, null);
		else 
			set_Value (COLUMNNAME_Account_DR_ID, Account_DR_ID);
	}

	@Override
	public int getAccount_DR_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Account_DR_ID);
	}

	@Override
	public void setAmtAcctCr (final BigDecimal AmtAcctCr)
	{
		set_ValueNoCheck (COLUMNNAME_AmtAcctCr, AmtAcctCr);
	}

	@Override
	public BigDecimal getAmtAcctCr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtAcctCr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtAcctDr (final BigDecimal AmtAcctDr)
	{
		set_ValueNoCheck (COLUMNNAME_AmtAcctDr, AmtAcctDr);
	}

	@Override
	public BigDecimal getAmtAcctDr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtAcctDr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtAcctGroupCr (final @Nullable BigDecimal AmtAcctGroupCr)
	{
		throw new IllegalArgumentException ("AmtAcctGroupCr is virtual column");	}

	@Override
	public BigDecimal getAmtAcctGroupCr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtAcctGroupCr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtAcctGroupDr (final @Nullable BigDecimal AmtAcctGroupDr)
	{
		throw new IllegalArgumentException ("AmtAcctGroupDr is virtual column");	}

	@Override
	public BigDecimal getAmtAcctGroupDr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtAcctGroupDr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtSourceCr (final BigDecimal AmtSourceCr)
	{
		set_Value (COLUMNNAME_AmtSourceCr, AmtSourceCr);
	}

	@Override
	public BigDecimal getAmtSourceCr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtSourceCr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtSourceDr (final BigDecimal AmtSourceDr)
	{
		set_Value (COLUMNNAME_AmtSourceDr, AmtSourceDr);
	}

	@Override
	public BigDecimal getAmtSourceDr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtSourceDr);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setC_ConversionType_ID (final int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, C_ConversionType_ID);
	}

	@Override
	public int getC_ConversionType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ConversionType_ID);
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
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setCR_AutoTaxAccount (final boolean CR_AutoTaxAccount)
	{
		set_Value (COLUMNNAME_CR_AutoTaxAccount, CR_AutoTaxAccount);
	}

	@Override
	public boolean isCR_AutoTaxAccount() 
	{
		return get_ValueAsBoolean(COLUMNNAME_CR_AutoTaxAccount);
	}

	@Override
	public org.compiere.model.I_C_Order getCR_C_Order()
	{
		return get_ValueAsPO(COLUMNNAME_CR_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setCR_C_Order(final org.compiere.model.I_C_Order CR_C_Order)
	{
		set_ValueFromPO(COLUMNNAME_CR_C_Order_ID, org.compiere.model.I_C_Order.class, CR_C_Order);
	}

	@Override
	public void setCR_C_Order_ID (final int CR_C_Order_ID)
	{
		if (CR_C_Order_ID < 1) 
			set_Value (COLUMNNAME_CR_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_CR_C_Order_ID, CR_C_Order_ID);
	}

	@Override
	public int getCR_C_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CR_C_Order_ID);
	}

	@Override
	public void setCR_M_Product_ID (final int CR_M_Product_ID)
	{
		if (CR_M_Product_ID < 1) 
			set_Value (COLUMNNAME_CR_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_CR_M_Product_ID, CR_M_Product_ID);
	}

	@Override
	public int getCR_M_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CR_M_Product_ID);
	}

	@Override
	public org.compiere.model.I_M_SectionCode getCR_M_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_CR_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setCR_M_SectionCode(final org.compiere.model.I_M_SectionCode CR_M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_CR_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, CR_M_SectionCode);
	}

	@Override
	public void setCR_M_SectionCode_ID (final int CR_M_SectionCode_ID)
	{
		if (CR_M_SectionCode_ID < 1) 
			set_Value (COLUMNNAME_CR_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_CR_M_SectionCode_ID, CR_M_SectionCode_ID);
	}

	@Override
	public int getCR_M_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CR_M_SectionCode_ID);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCR_Tax_Acct()
	{
		return get_ValueAsPO(COLUMNNAME_CR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCR_Tax_Acct(final org.compiere.model.I_C_ValidCombination CR_Tax_Acct)
	{
		set_ValueFromPO(COLUMNNAME_CR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class, CR_Tax_Acct);
	}

	@Override
	public void setCR_Tax_Acct_ID (final int CR_Tax_Acct_ID)
	{
		if (CR_Tax_Acct_ID < 1) 
			set_Value (COLUMNNAME_CR_Tax_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_CR_Tax_Acct_ID, CR_Tax_Acct_ID);
	}

	@Override
	public int getCR_Tax_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CR_Tax_Acct_ID);
	}

	@Override
	public void setCR_Tax_ID (final int CR_Tax_ID)
	{
		if (CR_Tax_ID < 1) 
			set_Value (COLUMNNAME_CR_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_CR_Tax_ID, CR_Tax_ID);
	}

	@Override
	public int getCR_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CR_Tax_ID);
	}

	@Override
	public void setCR_TaxAmt (final @Nullable BigDecimal CR_TaxAmt)
	{
		set_Value (COLUMNNAME_CR_TaxAmt, CR_TaxAmt);
	}

	@Override
	public BigDecimal getCR_TaxAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CR_TaxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCR_TaxBaseAmt (final @Nullable BigDecimal CR_TaxBaseAmt)
	{
		set_Value (COLUMNNAME_CR_TaxBaseAmt, CR_TaxBaseAmt);
	}

	@Override
	public BigDecimal getCR_TaxBaseAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CR_TaxBaseAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCR_TaxTotalAmt (final @Nullable BigDecimal CR_TaxTotalAmt)
	{
		set_Value (COLUMNNAME_CR_TaxTotalAmt, CR_TaxTotalAmt);
	}

	@Override
	public BigDecimal getCR_TaxTotalAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CR_TaxTotalAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCurrencyRate (final BigDecimal CurrencyRate)
	{
		set_ValueNoCheck (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	@Override
	public BigDecimal getCurrencyRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrencyRate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDateAcct (final java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
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
	public void setDR_AutoTaxAccount (final boolean DR_AutoTaxAccount)
	{
		set_Value (COLUMNNAME_DR_AutoTaxAccount, DR_AutoTaxAccount);
	}

	@Override
	public boolean isDR_AutoTaxAccount() 
	{
		return get_ValueAsBoolean(COLUMNNAME_DR_AutoTaxAccount);
	}

	@Override
	public org.compiere.model.I_C_Order getDR_C_Order()
	{
		return get_ValueAsPO(COLUMNNAME_DR_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setDR_C_Order(final org.compiere.model.I_C_Order DR_C_Order)
	{
		set_ValueFromPO(COLUMNNAME_DR_C_Order_ID, org.compiere.model.I_C_Order.class, DR_C_Order);
	}

	@Override
	public void setDR_C_Order_ID (final int DR_C_Order_ID)
	{
		if (DR_C_Order_ID < 1) 
			set_Value (COLUMNNAME_DR_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_DR_C_Order_ID, DR_C_Order_ID);
	}

	@Override
	public int getDR_C_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DR_C_Order_ID);
	}

	@Override
	public void setDR_M_Product_ID (final int DR_M_Product_ID)
	{
		if (DR_M_Product_ID < 1) 
			set_Value (COLUMNNAME_DR_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_DR_M_Product_ID, DR_M_Product_ID);
	}

	@Override
	public int getDR_M_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DR_M_Product_ID);
	}

	@Override
	public org.compiere.model.I_M_SectionCode getDR_M_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_DR_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setDR_M_SectionCode(final org.compiere.model.I_M_SectionCode DR_M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_DR_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, DR_M_SectionCode);
	}

	@Override
	public void setDR_M_SectionCode_ID (final int DR_M_SectionCode_ID)
	{
		if (DR_M_SectionCode_ID < 1) 
			set_Value (COLUMNNAME_DR_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_DR_M_SectionCode_ID, DR_M_SectionCode_ID);
	}

	@Override
	public int getDR_M_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DR_M_SectionCode_ID);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getDR_Tax_Acct()
	{
		return get_ValueAsPO(COLUMNNAME_DR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setDR_Tax_Acct(final org.compiere.model.I_C_ValidCombination DR_Tax_Acct)
	{
		set_ValueFromPO(COLUMNNAME_DR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class, DR_Tax_Acct);
	}

	@Override
	public void setDR_Tax_Acct_ID (final int DR_Tax_Acct_ID)
	{
		if (DR_Tax_Acct_ID < 1) 
			set_Value (COLUMNNAME_DR_Tax_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_DR_Tax_Acct_ID, DR_Tax_Acct_ID);
	}

	@Override
	public int getDR_Tax_Acct_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DR_Tax_Acct_ID);
	}

	@Override
	public void setDR_Tax_ID (final int DR_Tax_ID)
	{
		if (DR_Tax_ID < 1) 
			set_Value (COLUMNNAME_DR_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_DR_Tax_ID, DR_Tax_ID);
	}

	@Override
	public int getDR_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DR_Tax_ID);
	}

	@Override
	public void setDR_TaxAmt (final @Nullable BigDecimal DR_TaxAmt)
	{
		set_Value (COLUMNNAME_DR_TaxAmt, DR_TaxAmt);
	}

	@Override
	public BigDecimal getDR_TaxAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DR_TaxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDR_TaxBaseAmt (final @Nullable BigDecimal DR_TaxBaseAmt)
	{
		set_Value (COLUMNNAME_DR_TaxBaseAmt, DR_TaxBaseAmt);
	}

	@Override
	public BigDecimal getDR_TaxBaseAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DR_TaxBaseAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDR_TaxTotalAmt (final @Nullable BigDecimal DR_TaxTotalAmt)
	{
		set_Value (COLUMNNAME_DR_TaxTotalAmt, DR_TaxTotalAmt);
	}

	@Override
	public BigDecimal getDR_TaxTotalAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DR_TaxTotalAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public org.compiere.model.I_GL_Journal getGL_Journal()
	{
		return get_ValueAsPO(COLUMNNAME_GL_Journal_ID, org.compiere.model.I_GL_Journal.class);
	}

	@Override
	public void setGL_Journal(final org.compiere.model.I_GL_Journal GL_Journal)
	{
		set_ValueFromPO(COLUMNNAME_GL_Journal_ID, org.compiere.model.I_GL_Journal.class, GL_Journal);
	}

	@Override
	public void setGL_Journal_ID (final int GL_Journal_ID)
	{
		if (GL_Journal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_Journal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_Journal_ID, GL_Journal_ID);
	}

	@Override
	public int getGL_Journal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_Journal_ID);
	}

	@Override
	public void setGL_JournalLine_Group (final int GL_JournalLine_Group)
	{
		set_Value (COLUMNNAME_GL_JournalLine_Group, GL_JournalLine_Group);
	}

	@Override
	public int getGL_JournalLine_Group() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_JournalLine_Group);
	}

	@Override
	public void setGL_JournalLine_ID (final int GL_JournalLine_ID)
	{
		if (GL_JournalLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_JournalLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_JournalLine_ID, GL_JournalLine_ID);
	}

	@Override
	public int getGL_JournalLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_JournalLine_ID);
	}

	@Override
	public void setIsAllowAccountCR (final boolean IsAllowAccountCR)
	{
		set_Value (COLUMNNAME_IsAllowAccountCR, IsAllowAccountCR);
	}

	@Override
	public boolean isAllowAccountCR() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowAccountCR);
	}

	@Override
	public void setIsAllowAccountDR (final boolean IsAllowAccountDR)
	{
		set_Value (COLUMNNAME_IsAllowAccountDR, IsAllowAccountDR);
	}

	@Override
	public boolean isAllowAccountDR() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowAccountDR);
	}

	@Override
	public void setIsGenerated (final boolean IsGenerated)
	{
		set_ValueNoCheck (COLUMNNAME_IsGenerated, IsGenerated);
	}

	@Override
	public boolean isGenerated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsGenerated);
	}

	@Override
	public void setIsSplitAcctTrx (final boolean IsSplitAcctTrx)
	{
		set_Value (COLUMNNAME_IsSplitAcctTrx, IsSplitAcctTrx);
	}

	@Override
	public boolean isSplitAcctTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSplitAcctTrx);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
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

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * Type AD_Reference_ID=540534
	 * Reference name: GL_JournalLine_Type
	 */
	public static final int TYPE_AD_Reference_ID=540534;
	/** Normal = N */
	public static final String TYPE_Normal = "N";
	/** Tax = T */
	public static final String TYPE_Tax = "T";
	@Override
	public void setType (final java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}
}
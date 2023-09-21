// Generated Model - DO NOT CHANGE
package de.metas.acct.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for SAP_GLJournalLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_SAP_GLJournalLine extends org.compiere.model.PO implements I_SAP_GLJournalLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2135033865L;

    /** Standard Constructor */
    public X_SAP_GLJournalLine (final Properties ctx, final int SAP_GLJournalLine_ID, @Nullable final String trxName)
    {
      super (ctx, SAP_GLJournalLine_ID, trxName);
    }

    /** Load Constructor */
    public X_SAP_GLJournalLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmount (final BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	@Override
	public BigDecimal getAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmtAcct (final BigDecimal AmtAcct)
	{
		set_Value (COLUMNNAME_AmtAcct, AmtAcct);
	}

	@Override
	public BigDecimal getAmtAcct() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtAcct);
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
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
	public org.compiere.model.I_C_ValidCombination getC_ValidCombination()
	{
		return get_ValueAsPO(COLUMNNAME_C_ValidCombination_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setC_ValidCombination(final org.compiere.model.I_C_ValidCombination C_ValidCombination)
	{
		set_ValueFromPO(COLUMNNAME_C_ValidCombination_ID, org.compiere.model.I_C_ValidCombination.class, C_ValidCombination);
	}

	@Override
	public void setC_ValidCombination_ID (final int C_ValidCombination_ID)
	{
		if (C_ValidCombination_ID < 1) 
			set_Value (COLUMNNAME_C_ValidCombination_ID, null);
		else 
			set_Value (COLUMNNAME_C_ValidCombination_ID, C_ValidCombination_ID);
	}

	@Override
	public int getC_ValidCombination_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ValidCombination_ID);
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
	public void setIsFieldsReadOnlyInUI (final boolean IsFieldsReadOnlyInUI)
	{
		set_Value (COLUMNNAME_IsFieldsReadOnlyInUI, IsFieldsReadOnlyInUI);
	}

	@Override
	public boolean isFieldsReadOnlyInUI() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFieldsReadOnlyInUI);
	}

	@Override
	public void setIsOpenItem (final boolean IsOpenItem)
	{
		set_Value (COLUMNNAME_IsOpenItem, IsOpenItem);
	}

	@Override
	public boolean isOpenItem() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOpenItem);
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

	@Override
	public void setOI_AccountConceptualName (final @Nullable java.lang.String OI_AccountConceptualName)
	{
		set_Value (COLUMNNAME_OI_AccountConceptualName, OI_AccountConceptualName);
	}

	@Override
	public java.lang.String getOI_AccountConceptualName() 
	{
		return get_ValueAsString(COLUMNNAME_OI_AccountConceptualName);
	}

	@Override
	public void setOI_BankStatement_ID (final int OI_BankStatement_ID)
	{
		if (OI_BankStatement_ID < 1) 
			set_Value (COLUMNNAME_OI_BankStatement_ID, null);
		else 
			set_Value (COLUMNNAME_OI_BankStatement_ID, OI_BankStatement_ID);
	}

	@Override
	public int getOI_BankStatement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_OI_BankStatement_ID);
	}

	@Override
	public void setOI_BankStatementLine_ID (final int OI_BankStatementLine_ID)
	{
		if (OI_BankStatementLine_ID < 1) 
			set_Value (COLUMNNAME_OI_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_OI_BankStatementLine_ID, OI_BankStatementLine_ID);
	}

	@Override
	public int getOI_BankStatementLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_OI_BankStatementLine_ID);
	}

	@Override
	public void setOI_BankStatementLine_Ref_ID (final int OI_BankStatementLine_Ref_ID)
	{
		if (OI_BankStatementLine_Ref_ID < 1) 
			set_Value (COLUMNNAME_OI_BankStatementLine_Ref_ID, null);
		else 
			set_Value (COLUMNNAME_OI_BankStatementLine_Ref_ID, OI_BankStatementLine_Ref_ID);
	}

	@Override
	public int getOI_BankStatementLine_Ref_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_OI_BankStatementLine_Ref_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice getOI_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_OI_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setOI_Invoice(final org.compiere.model.I_C_Invoice OI_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_OI_Invoice_ID, org.compiere.model.I_C_Invoice.class, OI_Invoice);
	}

	@Override
	public void setOI_Invoice_ID (final int OI_Invoice_ID)
	{
		if (OI_Invoice_ID < 1) 
			set_Value (COLUMNNAME_OI_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_OI_Invoice_ID, OI_Invoice_ID);
	}

	@Override
	public int getOI_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_OI_Invoice_ID);
	}

	@Override
	public void setOI_Payment_ID (final int OI_Payment_ID)
	{
		if (OI_Payment_ID < 1) 
			set_Value (COLUMNNAME_OI_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_OI_Payment_ID, OI_Payment_ID);
	}

	@Override
	public int getOI_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_OI_Payment_ID);
	}

	/** 
	 * OI_TrxType AD_Reference_ID=541800
	 * Reference name: OI_TrxType
	 */
	public static final int OI_TRXTYPE_AD_Reference_ID=541800;
	/** OpenItem = O */
	public static final String OI_TRXTYPE_OpenItem = "O";
	/** Clearing = C */
	public static final String OI_TRXTYPE_Clearing = "C";
	@Override
	public void setOI_TrxType (final @Nullable java.lang.String OI_TrxType)
	{
		set_Value (COLUMNNAME_OI_TrxType, OI_TrxType);
	}

	@Override
	public java.lang.String getOI_TrxType() 
	{
		return get_ValueAsString(COLUMNNAME_OI_TrxType);
	}

	@Override
	public void setOpenItemKey (final @Nullable java.lang.String OpenItemKey)
	{
		set_Value (COLUMNNAME_OpenItemKey, OpenItemKey);
	}

	@Override
	public java.lang.String getOpenItemKey() 
	{
		return get_ValueAsString(COLUMNNAME_OpenItemKey);
	}

	@Override
	public de.metas.acct.model.I_SAP_GLJournalLine getParent()
	{
		return get_ValueAsPO(COLUMNNAME_Parent_ID, de.metas.acct.model.I_SAP_GLJournalLine.class);
	}

	@Override
	public void setParent(final de.metas.acct.model.I_SAP_GLJournalLine Parent)
	{
		set_ValueFromPO(COLUMNNAME_Parent_ID, de.metas.acct.model.I_SAP_GLJournalLine.class, Parent);
	}

	@Override
	public void setParent_ID (final int Parent_ID)
	{
		if (Parent_ID < 1) 
			set_Value (COLUMNNAME_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_ID, Parent_ID);
	}

	@Override
	public int getParent_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Parent_ID);
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
	public void setSAP_CalculateTax (final boolean SAP_CalculateTax)
	{
		set_Value (COLUMNNAME_SAP_CalculateTax, SAP_CalculateTax);
	}

	@Override
	public boolean isSAP_CalculateTax() 
	{
		return get_ValueAsBoolean(COLUMNNAME_SAP_CalculateTax);
	}

	@Override
	public void setSAP_DetermineTaxBase (final boolean SAP_DetermineTaxBase)
	{
		set_Value (COLUMNNAME_SAP_DetermineTaxBase, SAP_DetermineTaxBase);
	}

	@Override
	public boolean isSAP_DetermineTaxBase() 
	{
		return get_ValueAsBoolean(COLUMNNAME_SAP_DetermineTaxBase);
	}

	@Override
	public de.metas.acct.model.I_SAP_GLJournal getSAP_GLJournal()
	{
		return get_ValueAsPO(COLUMNNAME_SAP_GLJournal_ID, de.metas.acct.model.I_SAP_GLJournal.class);
	}

	@Override
	public void setSAP_GLJournal(final de.metas.acct.model.I_SAP_GLJournal SAP_GLJournal)
	{
		set_ValueFromPO(COLUMNNAME_SAP_GLJournal_ID, de.metas.acct.model.I_SAP_GLJournal.class, SAP_GLJournal);
	}

	@Override
	public void setSAP_GLJournal_ID (final int SAP_GLJournal_ID)
	{
		if (SAP_GLJournal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SAP_GLJournal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SAP_GLJournal_ID, SAP_GLJournal_ID);
	}

	@Override
	public int getSAP_GLJournal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SAP_GLJournal_ID);
	}

	@Override
	public void setSAP_GLJournalLine_ID (final int SAP_GLJournalLine_ID)
	{
		if (SAP_GLJournalLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SAP_GLJournalLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SAP_GLJournalLine_ID, SAP_GLJournalLine_ID);
	}

	@Override
	public int getSAP_GLJournalLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SAP_GLJournalLine_ID);
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

	@Override
	public void setUserElementString2 (final @Nullable java.lang.String UserElementString2)
	{
		set_Value (COLUMNNAME_UserElementString2, UserElementString2);
	}

	@Override
	public java.lang.String getUserElementString2() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString2);
	}

	@Override
	public void setUserElementString3 (final @Nullable java.lang.String UserElementString3)
	{
		set_Value (COLUMNNAME_UserElementString3, UserElementString3);
	}

	@Override
	public java.lang.String getUserElementString3() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString3);
	}

	@Override
	public void setUserElementString4 (final @Nullable java.lang.String UserElementString4)
	{
		set_Value (COLUMNNAME_UserElementString4, UserElementString4);
	}

	@Override
	public java.lang.String getUserElementString4() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString4);
	}

	@Override
	public void setUserElementString5 (final @Nullable java.lang.String UserElementString5)
	{
		set_Value (COLUMNNAME_UserElementString5, UserElementString5);
	}

	@Override
	public java.lang.String getUserElementString5() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString5);
	}

	@Override
	public void setUserElementString6 (final @Nullable java.lang.String UserElementString6)
	{
		set_Value (COLUMNNAME_UserElementString6, UserElementString6);
	}

	@Override
	public java.lang.String getUserElementString6() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString6);
	}

	@Override
	public void setUserElementString7 (final @Nullable java.lang.String UserElementString7)
	{
		set_Value (COLUMNNAME_UserElementString7, UserElementString7);
	}

	@Override
	public java.lang.String getUserElementString7() 
	{
		return get_ValueAsString(COLUMNNAME_UserElementString7);
	}


	@Override
	public void setIsTaxIncluded (final boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, IsTaxIncluded);
	}

	@Override
	public boolean isTaxIncluded()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxIncluded);
	}
}
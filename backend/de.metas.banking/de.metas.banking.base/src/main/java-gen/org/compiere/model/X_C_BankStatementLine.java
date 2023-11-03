// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BankStatementLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BankStatementLine extends org.compiere.model.PO implements I_C_BankStatementLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1043202516L;

    /** Standard Constructor */
    public X_C_BankStatementLine (final Properties ctx, final int C_BankStatementLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_BankStatementLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BankStatementLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBankFeeAmt (final BigDecimal BankFeeAmt)
	{
		set_Value (COLUMNNAME_BankFeeAmt, BankFeeAmt);
	}

	@Override
	public BigDecimal getBankFeeAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_BankFeeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_BankStatement_ID (final int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, C_BankStatement_ID);
	}

	@Override
	public int getC_BankStatement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatement_ID);
	}

	@Override
	public void setC_BankStatementLine_ID (final int C_BankStatementLine_ID)
	{
		if (C_BankStatementLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatementLine_ID, C_BankStatementLine_ID);
	}

	@Override
	public int getC_BankStatementLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_ID);
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
	public void setC_BP_BankAccountTo_ID (final int C_BP_BankAccountTo_ID)
	{
		if (C_BP_BankAccountTo_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccountTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccountTo_ID, C_BP_BankAccountTo_ID);
	}

	@Override
	public int getC_BP_BankAccountTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccountTo_ID);
	}

	@Override
	public void setC_Charge_ID (final int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, C_Charge_ID);
	}

	@Override
	public int getC_Charge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Charge_ID);
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
	public void setChargeAmt (final BigDecimal ChargeAmt)
	{
		set_Value (COLUMNNAME_ChargeAmt, ChargeAmt);
	}

	@Override
	public BigDecimal getChargeAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ChargeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_Payment_ID (final int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, C_Payment_ID);
	}

	@Override
	public int getC_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_ID);
	}

	@Override
	public void setCurrencyRate (final @Nullable BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
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
	public void setDebitorOrCreditorId (final int DebitorOrCreditorId)
	{
		set_Value (COLUMNNAME_DebitorOrCreditorId, DebitorOrCreditorId);
	}

	@Override
	public int getDebitorOrCreditorId() 
	{
		return get_ValueAsInt(COLUMNNAME_DebitorOrCreditorId);
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
	public void setEftAmt (final @Nullable BigDecimal EftAmt)
	{
		set_Value (COLUMNNAME_EftAmt, EftAmt);
	}

	@Override
	public BigDecimal getEftAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_EftAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setEftCheckNo (final @Nullable java.lang.String EftCheckNo)
	{
		set_Value (COLUMNNAME_EftCheckNo, EftCheckNo);
	}

	@Override
	public java.lang.String getEftCheckNo() 
	{
		return get_ValueAsString(COLUMNNAME_EftCheckNo);
	}

	@Override
	public void setEftCurrency (final @Nullable java.lang.String EftCurrency)
	{
		set_Value (COLUMNNAME_EftCurrency, EftCurrency);
	}

	@Override
	public java.lang.String getEftCurrency() 
	{
		return get_ValueAsString(COLUMNNAME_EftCurrency);
	}

	@Override
	public void setEftMemo (final @Nullable java.lang.String EftMemo)
	{
		set_Value (COLUMNNAME_EftMemo, EftMemo);
	}

	@Override
	public java.lang.String getEftMemo() 
	{
		return get_ValueAsString(COLUMNNAME_EftMemo);
	}

	@Override
	public void setEftPayee (final @Nullable java.lang.String EftPayee)
	{
		set_Value (COLUMNNAME_EftPayee, EftPayee);
	}

	@Override
	public java.lang.String getEftPayee() 
	{
		return get_ValueAsString(COLUMNNAME_EftPayee);
	}

	@Override
	public void setEftPayeeAccount (final @Nullable java.lang.String EftPayeeAccount)
	{
		set_Value (COLUMNNAME_EftPayeeAccount, EftPayeeAccount);
	}

	@Override
	public java.lang.String getEftPayeeAccount() 
	{
		return get_ValueAsString(COLUMNNAME_EftPayeeAccount);
	}

	@Override
	public void setEftReference (final @Nullable java.lang.String EftReference)
	{
		set_Value (COLUMNNAME_EftReference, EftReference);
	}

	@Override
	public java.lang.String getEftReference() 
	{
		return get_ValueAsString(COLUMNNAME_EftReference);
	}

	@Override
	public void setEftStatementLineDate (final @Nullable java.sql.Timestamp EftStatementLineDate)
	{
		set_Value (COLUMNNAME_EftStatementLineDate, EftStatementLineDate);
	}

	@Override
	public java.sql.Timestamp getEftStatementLineDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EftStatementLineDate);
	}

	@Override
	public void setEftTrxID (final @Nullable java.lang.String EftTrxID)
	{
		set_Value (COLUMNNAME_EftTrxID, EftTrxID);
	}

	@Override
	public java.lang.String getEftTrxID() 
	{
		return get_ValueAsString(COLUMNNAME_EftTrxID);
	}

	@Override
	public void setEftTrxType (final @Nullable java.lang.String EftTrxType)
	{
		set_Value (COLUMNNAME_EftTrxType, EftTrxType);
	}

	@Override
	public java.lang.String getEftTrxType() 
	{
		return get_ValueAsString(COLUMNNAME_EftTrxType);
	}

	@Override
	public void setEftValutaDate (final @Nullable java.sql.Timestamp EftValutaDate)
	{
		set_Value (COLUMNNAME_EftValutaDate, EftValutaDate);
	}

	@Override
	public java.sql.Timestamp getEftValutaDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EftValutaDate);
	}

	@Override
	public void setImportedBillPartnerIBAN (final @Nullable java.lang.String ImportedBillPartnerIBAN)
	{
		set_Value (COLUMNNAME_ImportedBillPartnerIBAN, ImportedBillPartnerIBAN);
	}

	@Override
	public java.lang.String getImportedBillPartnerIBAN() 
	{
		return get_ValueAsString(COLUMNNAME_ImportedBillPartnerIBAN);
	}

	@Override
	public void setImportedBillPartnerName (final @Nullable java.lang.String ImportedBillPartnerName)
	{
		set_Value (COLUMNNAME_ImportedBillPartnerName, ImportedBillPartnerName);
	}

	@Override
	public java.lang.String getImportedBillPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_ImportedBillPartnerName);
	}

	@Override
	public void setInterestAmt (final BigDecimal InterestAmt)
	{
		set_Value (COLUMNNAME_InterestAmt, InterestAmt);
	}

	@Override
	public BigDecimal getInterestAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InterestAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsManual (final boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, IsManual);
	}

	@Override
	public boolean isManual() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManual);
	}

	@Override
	public void setIsMultiplePayment (final boolean IsMultiplePayment)
	{
		set_Value (COLUMNNAME_IsMultiplePayment, IsMultiplePayment);
	}

	@Override
	public boolean isMultiplePayment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMultiplePayment);
	}

	@Override
	public void setIsMultiplePaymentOrInvoice (final boolean IsMultiplePaymentOrInvoice)
	{
		set_Value (COLUMNNAME_IsMultiplePaymentOrInvoice, IsMultiplePaymentOrInvoice);
	}

	@Override
	public boolean isMultiplePaymentOrInvoice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMultiplePaymentOrInvoice);
	}

	@Override
	public void setIsReconciled (final boolean IsReconciled)
	{
		set_Value (COLUMNNAME_IsReconciled, IsReconciled);
	}

	@Override
	public boolean isReconciled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReconciled);
	}

	@Override
	public void setIsReversal (final boolean IsReversal)
	{
		set_Value (COLUMNNAME_IsReversal, IsReversal);
	}

	@Override
	public boolean isReversal() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReversal);
	}

	@Override
	public void setIsUpdateAmountsFromInvoice (final boolean IsUpdateAmountsFromInvoice)
	{
		set_Value (COLUMNNAME_IsUpdateAmountsFromInvoice, IsUpdateAmountsFromInvoice);
	}

	@Override
	public boolean isUpdateAmountsFromInvoice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUpdateAmountsFromInvoice);
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
	public void setLink_BankStatementLine_ID (final int Link_BankStatementLine_ID)
	{
		if (Link_BankStatementLine_ID < 1) 
			set_Value (COLUMNNAME_Link_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_Link_BankStatementLine_ID, Link_BankStatementLine_ID);
	}

	@Override
	public int getLink_BankStatementLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Link_BankStatementLine_ID);
	}

	@Override
	public void setMemo (final @Nullable java.lang.String Memo)
	{
		set_Value (COLUMNNAME_Memo, Memo);
	}

	@Override
	public java.lang.String getMemo() 
	{
		return get_ValueAsString(COLUMNNAME_Memo);
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
	public void setReconciledBy_SAP_GLJournal_ID (final int ReconciledBy_SAP_GLJournal_ID)
	{
		if (ReconciledBy_SAP_GLJournal_ID < 1) 
			set_Value (COLUMNNAME_ReconciledBy_SAP_GLJournal_ID, null);
		else 
			set_Value (COLUMNNAME_ReconciledBy_SAP_GLJournal_ID, ReconciledBy_SAP_GLJournal_ID);
	}

	@Override
	public int getReconciledBy_SAP_GLJournal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ReconciledBy_SAP_GLJournal_ID);
	}

	@Override
	public void setReconciledBy_SAP_GLJournalLine_ID (final int ReconciledBy_SAP_GLJournalLine_ID)
	{
		if (ReconciledBy_SAP_GLJournalLine_ID < 1) 
			set_Value (COLUMNNAME_ReconciledBy_SAP_GLJournalLine_ID, null);
		else 
			set_Value (COLUMNNAME_ReconciledBy_SAP_GLJournalLine_ID, ReconciledBy_SAP_GLJournalLine_ID);
	}

	@Override
	public int getReconciledBy_SAP_GLJournalLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ReconciledBy_SAP_GLJournalLine_ID);
	}

	@Override
	public void setReferenceNo (final @Nullable java.lang.String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	@Override
	public java.lang.String getReferenceNo() 
	{
		return get_ValueAsString(COLUMNNAME_ReferenceNo);
	}

	@Override
	public void setStatementLineDate (final java.sql.Timestamp StatementLineDate)
	{
		set_Value (COLUMNNAME_StatementLineDate, StatementLineDate);
	}

	@Override
	public java.sql.Timestamp getStatementLineDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_StatementLineDate);
	}

	@Override
	public void setStmtAmt (final BigDecimal StmtAmt)
	{
		set_Value (COLUMNNAME_StmtAmt, StmtAmt);
	}

	@Override
	public BigDecimal getStmtAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_StmtAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTrxAmt (final BigDecimal TrxAmt)
	{
		set_Value (COLUMNNAME_TrxAmt, TrxAmt);
	}

	@Override
	public BigDecimal getTrxAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TrxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setValutaDate (final java.sql.Timestamp ValutaDate)
	{
		set_Value (COLUMNNAME_ValutaDate, ValutaDate);
	}

	@Override
	public java.sql.Timestamp getValutaDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValutaDate);
	}
}
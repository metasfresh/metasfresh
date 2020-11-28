/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BankStatementLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BankStatementLine extends org.compiere.model.PO implements I_C_BankStatementLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 809337976L;

    /** Standard Constructor */
    public X_C_BankStatementLine (Properties ctx, int C_BankStatementLine_ID, String trxName)
    {
      super (ctx, C_BankStatementLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BankStatementLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setBankFeeAmt (java.math.BigDecimal BankFeeAmt)
	{
		set_Value (COLUMNNAME_BankFeeAmt, BankFeeAmt);
	}

	@Override
	public java.math.BigDecimal getBankFeeAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_BankFeeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_BankStatement_ID (int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, Integer.valueOf(C_BankStatement_ID));
	}

	@Override
	public int getC_BankStatement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatement_ID);
	}

	@Override
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID)
	{
		if (C_BankStatementLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatementLine_ID, Integer.valueOf(C_BankStatementLine_ID));
	}

	@Override
	public int getC_BankStatementLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_ID);
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccountTo()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccountTo_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccountTo(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccountTo)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccountTo_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccountTo);
	}

	@Override
	public void setC_BP_BankAccountTo_ID (int C_BP_BankAccountTo_ID)
	{
		if (C_BP_BankAccountTo_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccountTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccountTo_ID, Integer.valueOf(C_BP_BankAccountTo_ID));
	}

	@Override
	public int getC_BP_BankAccountTo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccountTo_ID);
	}

	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	@Override
	public int getC_Charge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Charge_ID);
	}

	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	@Override
	public int getC_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_ID);
	}

	@Override
	public void setChargeAmt (java.math.BigDecimal ChargeAmt)
	{
		set_Value (COLUMNNAME_ChargeAmt, ChargeAmt);
	}

	@Override
	public java.math.BigDecimal getChargeAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ChargeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCurrencyRate (java.math.BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	@Override
	public java.math.BigDecimal getCurrencyRate() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrencyRate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDateAcct (java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setEftAmt (java.math.BigDecimal EftAmt)
	{
		set_Value (COLUMNNAME_EftAmt, EftAmt);
	}

	@Override
	public java.math.BigDecimal getEftAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_EftAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setEftCheckNo (java.lang.String EftCheckNo)
	{
		set_Value (COLUMNNAME_EftCheckNo, EftCheckNo);
	}

	@Override
	public java.lang.String getEftCheckNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftCheckNo);
	}

	@Override
	public void setEftCurrency (java.lang.String EftCurrency)
	{
		set_Value (COLUMNNAME_EftCurrency, EftCurrency);
	}

	@Override
	public java.lang.String getEftCurrency() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftCurrency);
	}

	@Override
	public void setEftMemo (java.lang.String EftMemo)
	{
		set_Value (COLUMNNAME_EftMemo, EftMemo);
	}

	@Override
	public java.lang.String getEftMemo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftMemo);
	}

	@Override
	public void setEftPayee (java.lang.String EftPayee)
	{
		set_Value (COLUMNNAME_EftPayee, EftPayee);
	}

	@Override
	public java.lang.String getEftPayee() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftPayee);
	}

	@Override
	public void setEftPayeeAccount (java.lang.String EftPayeeAccount)
	{
		set_Value (COLUMNNAME_EftPayeeAccount, EftPayeeAccount);
	}

	@Override
	public java.lang.String getEftPayeeAccount() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftPayeeAccount);
	}

	@Override
	public void setEftReference (java.lang.String EftReference)
	{
		set_Value (COLUMNNAME_EftReference, EftReference);
	}

	@Override
	public java.lang.String getEftReference() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftReference);
	}

	@Override
	public void setEftStatementLineDate (java.sql.Timestamp EftStatementLineDate)
	{
		set_Value (COLUMNNAME_EftStatementLineDate, EftStatementLineDate);
	}

	@Override
	public java.sql.Timestamp getEftStatementLineDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EftStatementLineDate);
	}

	@Override
	public void setEftTrxID (java.lang.String EftTrxID)
	{
		set_Value (COLUMNNAME_EftTrxID, EftTrxID);
	}

	@Override
	public java.lang.String getEftTrxID() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftTrxID);
	}

	@Override
	public void setEftTrxType (java.lang.String EftTrxType)
	{
		set_Value (COLUMNNAME_EftTrxType, EftTrxType);
	}

	@Override
	public java.lang.String getEftTrxType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftTrxType);
	}

	@Override
	public void setEftValutaDate (java.sql.Timestamp EftValutaDate)
	{
		set_Value (COLUMNNAME_EftValutaDate, EftValutaDate);
	}

	@Override
	public java.sql.Timestamp getEftValutaDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EftValutaDate);
	}

	@Override
	public void setImportedBillPartnerIBAN (java.lang.String ImportedBillPartnerIBAN)
	{
		set_Value (COLUMNNAME_ImportedBillPartnerIBAN, ImportedBillPartnerIBAN);
	}

	@Override
	public java.lang.String getImportedBillPartnerIBAN() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ImportedBillPartnerIBAN);
	}

	@Override
	public void setImportedBillPartnerName (java.lang.String ImportedBillPartnerName)
	{
		set_Value (COLUMNNAME_ImportedBillPartnerName, ImportedBillPartnerName);
	}

	@Override
	public java.lang.String getImportedBillPartnerName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ImportedBillPartnerName);
	}

	@Override
	public void setInterestAmt (java.math.BigDecimal InterestAmt)
	{
		set_Value (COLUMNNAME_InterestAmt, InterestAmt);
	}

	@Override
	public java.math.BigDecimal getInterestAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InterestAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	@Override
	public boolean isManual() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManual);
	}

	@Override
	public void setIsMultiplePayment (boolean IsMultiplePayment)
	{
		set_Value (COLUMNNAME_IsMultiplePayment, Boolean.valueOf(IsMultiplePayment));
	}

	@Override
	public boolean isMultiplePayment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMultiplePayment);
	}

	@Override
	public void setIsMultiplePaymentOrInvoice (boolean IsMultiplePaymentOrInvoice)
	{
		set_Value (COLUMNNAME_IsMultiplePaymentOrInvoice, Boolean.valueOf(IsMultiplePaymentOrInvoice));
	}

	@Override
	public boolean isMultiplePaymentOrInvoice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMultiplePaymentOrInvoice);
	}

	@Override
	public void setIsReconciled (boolean IsReconciled)
	{
		set_Value (COLUMNNAME_IsReconciled, Boolean.valueOf(IsReconciled));
	}

	@Override
	public boolean isReconciled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReconciled);
	}

	@Override
	public void setIsReversal (boolean IsReversal)
	{
		set_Value (COLUMNNAME_IsReversal, Boolean.valueOf(IsReversal));
	}

	@Override
	public boolean isReversal() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReversal);
	}

	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setLink_BankStatementLine_ID (int Link_BankStatementLine_ID)
	{
		if (Link_BankStatementLine_ID < 1) 
			set_Value (COLUMNNAME_Link_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_Link_BankStatementLine_ID, Integer.valueOf(Link_BankStatementLine_ID));
	}

	@Override
	public int getLink_BankStatementLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Link_BankStatementLine_ID);
	}

	@Override
	public void setMemo (java.lang.String Memo)
	{
		set_Value (COLUMNNAME_Memo, Memo);
	}

	@Override
	public java.lang.String getMemo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Memo);
	}

	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setReferenceNo (java.lang.String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	@Override
	public java.lang.String getReferenceNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReferenceNo);
	}

	@Override
	public void setStatementLineDate (java.sql.Timestamp StatementLineDate)
	{
		set_Value (COLUMNNAME_StatementLineDate, StatementLineDate);
	}

	@Override
	public java.sql.Timestamp getStatementLineDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_StatementLineDate);
	}

	@Override
	public void setStmtAmt (java.math.BigDecimal StmtAmt)
	{
		set_Value (COLUMNNAME_StmtAmt, StmtAmt);
	}

	@Override
	public java.math.BigDecimal getStmtAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_StmtAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTrxAmt (java.math.BigDecimal TrxAmt)
	{
		set_Value (COLUMNNAME_TrxAmt, TrxAmt);
	}

	@Override
	public java.math.BigDecimal getTrxAmt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TrxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setValutaDate (java.sql.Timestamp ValutaDate)
	{
		set_Value (COLUMNNAME_ValutaDate, ValutaDate);
	}

	@Override
	public java.sql.Timestamp getValutaDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValutaDate);
	}
}
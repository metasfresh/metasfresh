// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for I_BankStatement
 *  @author metasfresh (generated) 
 */
public class X_I_BankStatement extends org.compiere.model.PO implements I_I_BankStatement, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -668181063L;

    /** Standard Constructor */
    public X_I_BankStatement (final Properties ctx, final int I_BankStatement_ID, @Nullable final String trxName)
    {
      super (ctx, I_BankStatement_ID, trxName);
    }

    /** Load Constructor */
    public X_I_BankStatement (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	/** 
	 * AmtFormat AD_Reference_ID=541223
	 * Reference name: I_BankStatement_AmtFormat
	 */
	public static final int AMTFORMAT_AD_Reference_ID=541223;
	/** Amount + Indicator = A+I */
	public static final String AMTFORMAT_AmountPlusIndicator = "A+I";
	/** Debit+Credit = D+C */
	public static final String AMTFORMAT_DebitPlusCredit = "D+C";
	/** Straight = S */
	public static final String AMTFORMAT_Straight = "S";
	@Override
	public void setAmtFormat (final java.lang.String AmtFormat)
	{
		set_Value (COLUMNNAME_AmtFormat, AmtFormat);
	}

	@Override
	public java.lang.String getAmtFormat() 
	{
		return get_ValueAsString(COLUMNNAME_AmtFormat);
	}

	@Override
	public void setBankAccountNo (final java.lang.String BankAccountNo)
	{
		set_Value (COLUMNNAME_BankAccountNo, BankAccountNo);
	}

	@Override
	public java.lang.String getBankAccountNo() 
	{
		return get_ValueAsString(COLUMNNAME_BankAccountNo);
	}

	@Override
	public void setBill_BPartner_Name (final java.lang.String Bill_BPartner_Name)
	{
		set_Value (COLUMNNAME_Bill_BPartner_Name, Bill_BPartner_Name);
	}

	@Override
	public java.lang.String getBill_BPartner_Name() 
	{
		return get_ValueAsString(COLUMNNAME_Bill_BPartner_Name);
	}

	@Override
	public void setBPartnerValue (final java.lang.String BPartnerValue)
	{
		set_Value (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	@Override
	public java.lang.String getBPartnerValue() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerValue);
	}

	@Override
	public void setC_BankStatement_ID (final int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatement_ID, C_BankStatement_ID);
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
			set_Value (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, C_BankStatementLine_ID);
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
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(final org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	@Override
	public void setC_DataImport_ID (final int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, C_DataImport_ID);
	}

	@Override
	public int getC_DataImport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class);
	}

	@Override
	public void setC_DataImport_Run(final org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
	}

	@Override
	public void setC_DataImport_Run_ID (final int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, C_DataImport_Run_ID);
	}

	@Override
	public int getC_DataImport_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_Run_ID);
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
	public void setChargeName (final java.lang.String ChargeName)
	{
		set_Value (COLUMNNAME_ChargeName, ChargeName);
	}

	@Override
	public java.lang.String getChargeName() 
	{
		return get_ValueAsString(COLUMNNAME_ChargeName);
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
	public void setCreatePayment (final java.lang.String CreatePayment)
	{
		set_Value (COLUMNNAME_CreatePayment, CreatePayment);
	}

	@Override
	public java.lang.String getCreatePayment() 
	{
		return get_ValueAsString(COLUMNNAME_CreatePayment);
	}

	@Override
	public void setCreditStmtAmt (final BigDecimal CreditStmtAmt)
	{
		set_Value (COLUMNNAME_CreditStmtAmt, CreditStmtAmt);
	}

	@Override
	public BigDecimal getCreditStmtAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CreditStmtAmt);
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
	public void setDebitOrCreditAmt (final BigDecimal DebitOrCreditAmt)
	{
		set_Value (COLUMNNAME_DebitOrCreditAmt, DebitOrCreditAmt);
	}

	@Override
	public BigDecimal getDebitOrCreditAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DebitOrCreditAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * DebitOrCreditIndicator AD_Reference_ID=541226
	 * Reference name: I_BankStatement_DebitOrCreditIndicator
	 */
	public static final int DEBITORCREDITINDICATOR_AD_Reference_ID=541226;
	/** Debit = H */
	public static final String DEBITORCREDITINDICATOR_Debit = "H";
	/** Credit = S */
	public static final String DEBITORCREDITINDICATOR_Credit = "S";
	@Override
	public void setDebitOrCreditIndicator (final java.lang.String DebitOrCreditIndicator)
	{
		set_Value (COLUMNNAME_DebitOrCreditIndicator, DebitOrCreditIndicator);
	}

	@Override
	public java.lang.String getDebitOrCreditIndicator() 
	{
		return get_ValueAsString(COLUMNNAME_DebitOrCreditIndicator);
	}

	@Override
	public void setDebitStmtAmt (final BigDecimal DebitStmtAmt)
	{
		set_Value (COLUMNNAME_DebitStmtAmt, DebitStmtAmt);
	}

	@Override
	public BigDecimal getDebitStmtAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DebitStmtAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setEftAmt (final BigDecimal EftAmt)
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
	public void setEftCheckNo (final java.lang.String EftCheckNo)
	{
		set_Value (COLUMNNAME_EftCheckNo, EftCheckNo);
	}

	@Override
	public java.lang.String getEftCheckNo() 
	{
		return get_ValueAsString(COLUMNNAME_EftCheckNo);
	}

	@Override
	public void setEftCurrency (final java.lang.String EftCurrency)
	{
		set_Value (COLUMNNAME_EftCurrency, EftCurrency);
	}

	@Override
	public java.lang.String getEftCurrency() 
	{
		return get_ValueAsString(COLUMNNAME_EftCurrency);
	}

	@Override
	public void setEftMemo (final java.lang.String EftMemo)
	{
		set_Value (COLUMNNAME_EftMemo, EftMemo);
	}

	@Override
	public java.lang.String getEftMemo() 
	{
		return get_ValueAsString(COLUMNNAME_EftMemo);
	}

	@Override
	public void setEftPayee (final java.lang.String EftPayee)
	{
		set_Value (COLUMNNAME_EftPayee, EftPayee);
	}

	@Override
	public java.lang.String getEftPayee() 
	{
		return get_ValueAsString(COLUMNNAME_EftPayee);
	}

	@Override
	public void setEftPayeeAccount (final java.lang.String EftPayeeAccount)
	{
		set_Value (COLUMNNAME_EftPayeeAccount, EftPayeeAccount);
	}

	@Override
	public java.lang.String getEftPayeeAccount() 
	{
		return get_ValueAsString(COLUMNNAME_EftPayeeAccount);
	}

	@Override
	public void setEftReference (final java.lang.String EftReference)
	{
		set_Value (COLUMNNAME_EftReference, EftReference);
	}

	@Override
	public java.lang.String getEftReference() 
	{
		return get_ValueAsString(COLUMNNAME_EftReference);
	}

	@Override
	public void setEftStatementDate (final java.sql.Timestamp EftStatementDate)
	{
		set_Value (COLUMNNAME_EftStatementDate, EftStatementDate);
	}

	@Override
	public java.sql.Timestamp getEftStatementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EftStatementDate);
	}

	@Override
	public void setEftStatementLineDate (final java.sql.Timestamp EftStatementLineDate)
	{
		set_Value (COLUMNNAME_EftStatementLineDate, EftStatementLineDate);
	}

	@Override
	public java.sql.Timestamp getEftStatementLineDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EftStatementLineDate);
	}

	@Override
	public void setEftStatementReference (final java.lang.String EftStatementReference)
	{
		set_Value (COLUMNNAME_EftStatementReference, EftStatementReference);
	}

	@Override
	public java.lang.String getEftStatementReference() 
	{
		return get_ValueAsString(COLUMNNAME_EftStatementReference);
	}

	@Override
	public void setEftTrxID (final java.lang.String EftTrxID)
	{
		set_Value (COLUMNNAME_EftTrxID, EftTrxID);
	}

	@Override
	public java.lang.String getEftTrxID() 
	{
		return get_ValueAsString(COLUMNNAME_EftTrxID);
	}

	@Override
	public void setEftTrxType (final java.lang.String EftTrxType)
	{
		set_Value (COLUMNNAME_EftTrxType, EftTrxType);
	}

	@Override
	public java.lang.String getEftTrxType() 
	{
		return get_ValueAsString(COLUMNNAME_EftTrxType);
	}

	@Override
	public void setEftValutaDate (final java.sql.Timestamp EftValutaDate)
	{
		set_Value (COLUMNNAME_EftValutaDate, EftValutaDate);
	}

	@Override
	public java.sql.Timestamp getEftValutaDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EftValutaDate);
	}

	@Override
	public void setIBAN (final java.lang.String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	@Override
	public java.lang.String getIBAN() 
	{
		return get_ValueAsString(COLUMNNAME_IBAN);
	}

	@Override
	public void setI_BankStatement_ID (final int I_BankStatement_ID)
	{
		if (I_BankStatement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_BankStatement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_BankStatement_ID, I_BankStatement_ID);
	}

	@Override
	public int getI_BankStatement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_BankStatement_ID);
	}

	@Override
	public void setIBAN_To (final java.lang.String IBAN_To)
	{
		set_Value (COLUMNNAME_IBAN_To, IBAN_To);
	}

	@Override
	public java.lang.String getIBAN_To() 
	{
		return get_ValueAsString(COLUMNNAME_IBAN_To);
	}

	@Override
	public void setI_ErrorMsg (final java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	@Override
	public java.lang.String getI_ErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_I_ErrorMsg);
	}

	@Override
	public void setI_IsImported (final boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	@Override
	public boolean isI_IsImported() 
	{
		return get_ValueAsBoolean(COLUMNNAME_I_IsImported);
	}

	@Override
	public void setI_LineContent (final java.lang.String I_LineContent)
	{
		set_Value (COLUMNNAME_I_LineContent, I_LineContent);
	}

	@Override
	public java.lang.String getI_LineContent() 
	{
		return get_ValueAsString(COLUMNNAME_I_LineContent);
	}

	@Override
	public void setI_LineNo (final int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, I_LineNo);
	}

	@Override
	public int getI_LineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_I_LineNo);
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
	public void setInvoiceDocumentNo (final java.lang.String InvoiceDocumentNo)
	{
		set_Value (COLUMNNAME_InvoiceDocumentNo, InvoiceDocumentNo);
	}

	@Override
	public java.lang.String getInvoiceDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_InvoiceDocumentNo);
	}

	@Override
	public void setISO_Code (final java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
		return get_ValueAsString(COLUMNNAME_ISO_Code);
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
	public void setLineDescription (final java.lang.String LineDescription)
	{
		set_Value (COLUMNNAME_LineDescription, LineDescription);
	}

	@Override
	public java.lang.String getLineDescription() 
	{
		return get_ValueAsString(COLUMNNAME_LineDescription);
	}

	@Override
	public void setLineDescriptionExtra_1 (final java.lang.String LineDescriptionExtra_1)
	{
		set_Value (COLUMNNAME_LineDescriptionExtra_1, LineDescriptionExtra_1);
	}

	@Override
	public java.lang.String getLineDescriptionExtra_1() 
	{
		return get_ValueAsString(COLUMNNAME_LineDescriptionExtra_1);
	}

	@Override
	public void setLineDescriptionExtra_2 (final java.lang.String LineDescriptionExtra_2)
	{
		set_Value (COLUMNNAME_LineDescriptionExtra_2, LineDescriptionExtra_2);
	}

	@Override
	public java.lang.String getLineDescriptionExtra_2() 
	{
		return get_ValueAsString(COLUMNNAME_LineDescriptionExtra_2);
	}

	@Override
	public void setLineDescriptionExtra_3 (final java.lang.String LineDescriptionExtra_3)
	{
		set_Value (COLUMNNAME_LineDescriptionExtra_3, LineDescriptionExtra_3);
	}

	@Override
	public java.lang.String getLineDescriptionExtra_3() 
	{
		return get_ValueAsString(COLUMNNAME_LineDescriptionExtra_3);
	}

	@Override
	public void setLineDescriptionExtra_4 (final java.lang.String LineDescriptionExtra_4)
	{
		set_Value (COLUMNNAME_LineDescriptionExtra_4, LineDescriptionExtra_4);
	}

	@Override
	public java.lang.String getLineDescriptionExtra_4() 
	{
		return get_ValueAsString(COLUMNNAME_LineDescriptionExtra_4);
	}

	@Override
	public void setMatchStatement (final java.lang.String MatchStatement)
	{
		set_Value (COLUMNNAME_MatchStatement, MatchStatement);
	}

	@Override
	public java.lang.String getMatchStatement() 
	{
		return get_ValueAsString(COLUMNNAME_MatchStatement);
	}

	@Override
	public void setMemo (final java.lang.String Memo)
	{
		set_Value (COLUMNNAME_Memo, Memo);
	}

	@Override
	public java.lang.String getMemo() 
	{
		return get_ValueAsString(COLUMNNAME_Memo);
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
	public void setPaymentDocumentNo (final java.lang.String PaymentDocumentNo)
	{
		set_Value (COLUMNNAME_PaymentDocumentNo, PaymentDocumentNo);
	}

	@Override
	public java.lang.String getPaymentDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentDocumentNo);
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
	public void setReferenceNo (final java.lang.String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	@Override
	public java.lang.String getReferenceNo() 
	{
		return get_ValueAsString(COLUMNNAME_ReferenceNo);
	}

	@Override
	public void setRoutingNo (final java.lang.String RoutingNo)
	{
		set_Value (COLUMNNAME_RoutingNo, RoutingNo);
	}

	@Override
	public java.lang.String getRoutingNo() 
	{
		return get_ValueAsString(COLUMNNAME_RoutingNo);
	}

	@Override
	public void setStatementDate (final java.sql.Timestamp StatementDate)
	{
		set_Value (COLUMNNAME_StatementDate, StatementDate);
	}

	@Override
	public java.sql.Timestamp getStatementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_StatementDate);
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

	/** 
	 * TrxType AD_Reference_ID=215
	 * Reference name: C_Payment Trx Type
	 */
	public static final int TRXTYPE_AD_Reference_ID=215;
	/** Sales = S */
	public static final String TRXTYPE_Sales = "S";
	/** DelayedCapture = D */
	public static final String TRXTYPE_DelayedCapture = "D";
	/** CreditPayment = C */
	public static final String TRXTYPE_CreditPayment = "C";
	/** VoiceAuthorization = F */
	public static final String TRXTYPE_VoiceAuthorization = "F";
	/** Authorization = A */
	public static final String TRXTYPE_Authorization = "A";
	/** Void = V */
	public static final String TRXTYPE_Void = "V";
	/** Rückzahlung = R */
	public static final String TRXTYPE_Rueckzahlung = "R";
	@Override
	public void setTrxType (final java.lang.String TrxType)
	{
		set_Value (COLUMNNAME_TrxType, TrxType);
	}

	@Override
	public java.lang.String getTrxType() 
	{
		return get_ValueAsString(COLUMNNAME_TrxType);
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
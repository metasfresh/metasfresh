/** Generated Model - DO NOT CHANGE */
package de.metas.payment.esr.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ESR_ImportLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_ESR_ImportLine extends org.compiere.model.PO implements I_ESR_ImportLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -779950308L;

    /** Standard Constructor */
    public X_ESR_ImportLine (Properties ctx, int ESR_ImportLine_ID, String trxName)
    {
      super (ctx, ESR_ImportLine_ID, trxName);
    }

    /** Load Constructor */
    public X_ESR_ImportLine (Properties ctx, ResultSet rs, String trxName)
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
	public void setAccountingDate (java.sql.Timestamp AccountingDate)
	{
		set_Value (COLUMNNAME_AccountingDate, AccountingDate);
	}

	@Override
	public java.sql.Timestamp getAccountingDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AccountingDate);
	}

	@Override
	public void setAccountNo (java.lang.String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	@Override
	public java.lang.String getAccountNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccountNo);
	}

	@Override
	public void setAmount (java.math.BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	@Override
	public java.math.BigDecimal getAmount() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBPartner_Value (java.lang.String BPartner_Value)
	{
		set_Value (COLUMNNAME_BPartner_Value, BPartner_Value);
	}

	@Override
	public java.lang.String getBPartner_Value() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartner_Value);
	}

	@Override
	public void setC_BankStatement_ID (int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatement_ID, Integer.valueOf(C_BankStatement_ID));
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
			set_Value (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, Integer.valueOf(C_BankStatementLine_ID));
	}

	@Override
	public int getC_BankStatementLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_ID);
	}

	@Override
	public void setC_BankStatementLine_Ref_ID (int C_BankStatementLine_Ref_ID)
	{
		if (C_BankStatementLine_Ref_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, Integer.valueOf(C_BankStatementLine_Ref_ID));
	}

	@Override
	public int getC_BankStatementLine_Ref_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_Ref_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
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
	public void setC_ReferenceNo_ID (int C_ReferenceNo_ID)
	{
		if (C_ReferenceNo_ID < 1) 
			set_Value (COLUMNNAME_C_ReferenceNo_ID, null);
		else 
			set_Value (COLUMNNAME_C_ReferenceNo_ID, Integer.valueOf(C_ReferenceNo_ID));
	}

	@Override
	public int getC_ReferenceNo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ReferenceNo_ID);
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
	public void setESR_Amount_Balance (java.math.BigDecimal ESR_Amount_Balance)
	{
		throw new IllegalArgumentException ("ESR_Amount_Balance is virtual column");	}

	@Override
	public java.math.BigDecimal getESR_Amount_Balance() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ESR_Amount_Balance);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ESR_Document_Status AD_Reference_ID=540375
	 * Reference name: ESR_Document_Statuses
	 */
	public static final int ESR_DOCUMENT_STATUS_AD_Reference_ID=540375;
	/** TotallyMatched = T */
	public static final String ESR_DOCUMENT_STATUS_TotallyMatched = "T";
	/** PartiallyMatched = P */
	public static final String ESR_DOCUMENT_STATUS_PartiallyMatched = "P";
	/** NotMatched = N */
	public static final String ESR_DOCUMENT_STATUS_NotMatched = "N";
	@Override
	public void setESR_Document_Status (java.lang.String ESR_Document_Status)
	{

		set_Value (COLUMNNAME_ESR_Document_Status, ESR_Document_Status);
	}

	@Override
	public java.lang.String getESR_Document_Status() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESR_Document_Status);
	}

	@Override
	public void setESR_DocumentNo (java.lang.String ESR_DocumentNo)
	{
		set_Value (COLUMNNAME_ESR_DocumentNo, ESR_DocumentNo);
	}

	@Override
	public java.lang.String getESR_DocumentNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESR_DocumentNo);
	}

	@Override
	public de.metas.payment.esr.model.I_ESR_Import getESR_Import()
	{
		return get_ValueAsPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class);
	}

	@Override
	public void setESR_Import(de.metas.payment.esr.model.I_ESR_Import ESR_Import)
	{
		set_ValueFromPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class, ESR_Import);
	}

	@Override
	public void setESR_Import_ID (int ESR_Import_ID)
	{
		if (ESR_Import_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, Integer.valueOf(ESR_Import_ID));
	}

	@Override
	public int getESR_Import_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ESR_Import_ID);
	}

	@Override
	public void setESR_ImportLine_ID (int ESR_ImportLine_ID)
	{
		if (ESR_ImportLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_ImportLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_ImportLine_ID, Integer.valueOf(ESR_ImportLine_ID));
	}

	@Override
	public int getESR_ImportLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ESR_ImportLine_ID);
	}

	@Override
	public void setESR_Invoice_Grandtotal (java.math.BigDecimal ESR_Invoice_Grandtotal)
	{
		set_Value (COLUMNNAME_ESR_Invoice_Grandtotal, ESR_Invoice_Grandtotal);
	}

	@Override
	public java.math.BigDecimal getESR_Invoice_Grandtotal() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ESR_Invoice_Grandtotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setESR_Invoice_Openamt (java.math.BigDecimal ESR_Invoice_Openamt)
	{
		set_Value (COLUMNNAME_ESR_Invoice_Openamt, ESR_Invoice_Openamt);
	}

	@Override
	public java.math.BigDecimal getESR_Invoice_Openamt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ESR_Invoice_Openamt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setESR_IsManual_ReferenceNo (boolean ESR_IsManual_ReferenceNo)
	{
		set_Value (COLUMNNAME_ESR_IsManual_ReferenceNo, Boolean.valueOf(ESR_IsManual_ReferenceNo));
	}

	@Override
	public boolean isESR_IsManual_ReferenceNo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_ESR_IsManual_ReferenceNo);
	}

	/** 
	 * ESR_Payment_Action AD_Reference_ID=540386
	 * Reference name: ESR_Payment_Action_List
	 */
	public static final int ESR_PAYMENT_ACTION_AD_Reference_ID=540386;
	/** Money_Was_Transfered_Back_to_Partner = B */
	public static final String ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner = "B";
	/** Allocate_Payment_With_Next_Invoice = N */
	public static final String ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice = "N";
	/** Write_Off_Amount = W */
	public static final String ESR_PAYMENT_ACTION_Write_Off_Amount = "W";
	/** Keep_For_Dunning = D */
	public static final String ESR_PAYMENT_ACTION_Keep_For_Dunning = "D";
	/** Fit_Amounts = F */
	public static final String ESR_PAYMENT_ACTION_Fit_Amounts = "F";
	/** Unable_To_Assign_Income = E */
	public static final String ESR_PAYMENT_ACTION_Unable_To_Assign_Income = "E";
	/** Control_Line = C */
	public static final String ESR_PAYMENT_ACTION_Control_Line = "C";
	/** Allocate_Payment_With_Current_Invoice = A */
	public static final String ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice = "A";
	/** Reverse_Booking = R */
	public static final String ESR_PAYMENT_ACTION_Reverse_Booking = "R";
	/** Discount = T */
	public static final String ESR_PAYMENT_ACTION_Discount = "T";
	@Override
	public void setESR_Payment_Action (java.lang.String ESR_Payment_Action)
	{

		set_Value (COLUMNNAME_ESR_Payment_Action, ESR_Payment_Action);
	}

	@Override
	public java.lang.String getESR_Payment_Action() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESR_Payment_Action);
	}

	@Override
	public void setESRFullReferenceNumber (java.lang.String ESRFullReferenceNumber)
	{
		set_Value (COLUMNNAME_ESRFullReferenceNumber, ESRFullReferenceNumber);
	}

	@Override
	public java.lang.String getESRFullReferenceNumber() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESRFullReferenceNumber);
	}

	@Override
	public void setESRLineText (java.lang.String ESRLineText)
	{
		set_Value (COLUMNNAME_ESRLineText, ESRLineText);
	}

	@Override
	public java.lang.String getESRLineText() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESRLineText);
	}

	@Override
	public void setESRPostParticipantNumber (java.lang.String ESRPostParticipantNumber)
	{
		set_Value (COLUMNNAME_ESRPostParticipantNumber, ESRPostParticipantNumber);
	}

	@Override
	public java.lang.String getESRPostParticipantNumber() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESRPostParticipantNumber);
	}

	@Override
	public void setESRReferenceNumber (java.lang.String ESRReferenceNumber)
	{
		set_Value (COLUMNNAME_ESRReferenceNumber, ESRReferenceNumber);
	}

	@Override
	public java.lang.String getESRReferenceNumber() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESRReferenceNumber);
	}

	@Override
	public void setESRTrxType (java.lang.String ESRTrxType)
	{
		set_Value (COLUMNNAME_ESRTrxType, ESRTrxType);
	}

	@Override
	public java.lang.String getESRTrxType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESRTrxType);
	}

	@Override
	public void setImportErrorMsg (java.lang.String ImportErrorMsg)
	{
		set_Value (COLUMNNAME_ImportErrorMsg, ImportErrorMsg);
	}

	@Override
	public java.lang.String getImportErrorMsg() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ImportErrorMsg);
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
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	@Override
	public boolean isValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsValid);
	}

	@Override
	public void setLineNo (int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, Integer.valueOf(LineNo));
	}

	@Override
	public int getLineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_LineNo);
	}

	@Override
	public void setMatchErrorMsg (java.lang.String MatchErrorMsg)
	{
		set_Value (COLUMNNAME_MatchErrorMsg, MatchErrorMsg);
	}

	@Override
	public java.lang.String getMatchErrorMsg() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MatchErrorMsg);
	}

	@Override
	public void setOrg_ID (int Org_ID)
	{
		if (Org_ID < 1) 
			set_Value (COLUMNNAME_Org_ID, null);
		else 
			set_Value (COLUMNNAME_Org_ID, Integer.valueOf(Org_ID));
	}

	@Override
	public int getOrg_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Org_ID);
	}

	@Override
	public void setPaymentDate (java.sql.Timestamp PaymentDate)
	{
		set_Value (COLUMNNAME_PaymentDate, PaymentDate);
	}

	@Override
	public java.sql.Timestamp getPaymentDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PaymentDate);
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
	public void setSektionNo (java.lang.String SektionNo)
	{
		set_Value (COLUMNNAME_SektionNo, SektionNo);
	}

	@Override
	public java.lang.String getSektionNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SektionNo);
	}
}
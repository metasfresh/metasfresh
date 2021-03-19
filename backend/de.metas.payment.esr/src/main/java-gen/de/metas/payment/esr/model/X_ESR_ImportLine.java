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

	private static final long serialVersionUID = 243864233L;

    /** Standard Constructor */
    public X_ESR_ImportLine (final Properties ctx, final int ESR_ImportLine_ID, final String trxName)
    {
      super (ctx, ESR_ImportLine_ID, trxName);
    }

    /** Load Constructor */
    public X_ESR_ImportLine (final Properties ctx, final ResultSet rs, final String trxName)
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
	public void setAccountingDate (final java.sql.Timestamp AccountingDate)
	{
		set_Value (COLUMNNAME_AccountingDate, AccountingDate);
	}

	@Override
	public java.sql.Timestamp getAccountingDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AccountingDate);
	}

	@Override
	public void setAccountNo (final java.lang.String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	@Override
	public java.lang.String getAccountNo() 
	{
		return get_ValueAsString(COLUMNNAME_AccountNo);
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
	public void setBPartner_Value (final java.lang.String BPartner_Value)
	{
		set_Value (COLUMNNAME_BPartner_Value, BPartner_Value);
	}

	@Override
	public java.lang.String getBPartner_Value() 
	{
		return get_ValueAsString(COLUMNNAME_BPartner_Value);
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
	public void setC_BankStatementLine_Ref_ID (final int C_BankStatementLine_Ref_ID)
	{
		if (C_BankStatementLine_Ref_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, C_BankStatementLine_Ref_ID);
	}

	@Override
	public int getC_BankStatementLine_Ref_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_Ref_ID);
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
	public void setC_ReferenceNo_ID (final int C_ReferenceNo_ID)
	{
		if (C_ReferenceNo_ID < 1) 
			set_Value (COLUMNNAME_C_ReferenceNo_ID, null);
		else 
			set_Value (COLUMNNAME_C_ReferenceNo_ID, C_ReferenceNo_ID);
	}

	@Override
	public int getC_ReferenceNo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ReferenceNo_ID);
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
	public void setESR_Amount_Balance (final BigDecimal ESR_Amount_Balance)
	{
		throw new IllegalArgumentException ("ESR_Amount_Balance is virtual column");	}

	@Override
	public BigDecimal getESR_Amount_Balance() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ESR_Amount_Balance);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setESR_DocumentNo (final java.lang.String ESR_DocumentNo)
	{
		set_Value (COLUMNNAME_ESR_DocumentNo, ESR_DocumentNo);
	}

	@Override
	public java.lang.String getESR_DocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_ESR_DocumentNo);
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
	public void setESR_Document_Status (final java.lang.String ESR_Document_Status)
	{
		set_Value (COLUMNNAME_ESR_Document_Status, ESR_Document_Status);
	}

	@Override
	public java.lang.String getESR_Document_Status() 
	{
		return get_ValueAsString(COLUMNNAME_ESR_Document_Status);
	}

	@Override
	public void setESRFullReferenceNumber (final java.lang.String ESRFullReferenceNumber)
	{
		set_Value (COLUMNNAME_ESRFullReferenceNumber, ESRFullReferenceNumber);
	}

	@Override
	public java.lang.String getESRFullReferenceNumber() 
	{
		return get_ValueAsString(COLUMNNAME_ESRFullReferenceNumber);
	}

	@Override
	public de.metas.payment.esr.model.I_ESR_Import getESR_Import()
	{
		return get_ValueAsPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class);
	}

	@Override
	public void setESR_Import(final de.metas.payment.esr.model.I_ESR_Import ESR_Import)
	{
		set_ValueFromPO(COLUMNNAME_ESR_Import_ID, de.metas.payment.esr.model.I_ESR_Import.class, ESR_Import);
	}

	@Override
	public void setESR_Import_ID (final int ESR_Import_ID)
	{
		if (ESR_Import_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, ESR_Import_ID);
	}

	@Override
	public int getESR_Import_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ESR_Import_ID);
	}

	@Override
	public void setESR_ImportLine_ID (final int ESR_ImportLine_ID)
	{
		if (ESR_ImportLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_ImportLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_ImportLine_ID, ESR_ImportLine_ID);
	}

	@Override
	public int getESR_ImportLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ESR_ImportLine_ID);
	}

	@Override
	public void setESR_Invoice_Grandtotal (final BigDecimal ESR_Invoice_Grandtotal)
	{
		set_Value (COLUMNNAME_ESR_Invoice_Grandtotal, ESR_Invoice_Grandtotal);
	}

	@Override
	public BigDecimal getESR_Invoice_Grandtotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ESR_Invoice_Grandtotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setESR_Invoice_Openamt (final BigDecimal ESR_Invoice_Openamt)
	{
		set_Value (COLUMNNAME_ESR_Invoice_Openamt, ESR_Invoice_Openamt);
	}

	@Override
	public BigDecimal getESR_Invoice_Openamt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ESR_Invoice_Openamt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setESR_IsManual_ReferenceNo (final boolean ESR_IsManual_ReferenceNo)
	{
		set_Value (COLUMNNAME_ESR_IsManual_ReferenceNo, ESR_IsManual_ReferenceNo);
	}

	@Override
	public boolean isESR_IsManual_ReferenceNo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_ESR_IsManual_ReferenceNo);
	}

	@Override
	public void setESRLineText (final java.lang.String ESRLineText)
	{
		set_Value (COLUMNNAME_ESRLineText, ESRLineText);
	}

	@Override
	public java.lang.String getESRLineText() 
	{
		return get_ValueAsString(COLUMNNAME_ESRLineText);
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
	public void setESR_Payment_Action (final java.lang.String ESR_Payment_Action)
	{
		set_Value (COLUMNNAME_ESR_Payment_Action, ESR_Payment_Action);
	}

	@Override
	public java.lang.String getESR_Payment_Action() 
	{
		return get_ValueAsString(COLUMNNAME_ESR_Payment_Action);
	}

	@Override
	public void setESRPostParticipantNumber (final java.lang.String ESRPostParticipantNumber)
	{
		set_Value (COLUMNNAME_ESRPostParticipantNumber, ESRPostParticipantNumber);
	}

	@Override
	public java.lang.String getESRPostParticipantNumber() 
	{
		return get_ValueAsString(COLUMNNAME_ESRPostParticipantNumber);
	}

	@Override
	public void setESRReferenceNumber (final java.lang.String ESRReferenceNumber)
	{
		set_Value (COLUMNNAME_ESRReferenceNumber, ESRReferenceNumber);
	}

	@Override
	public java.lang.String getESRReferenceNumber() 
	{
		return get_ValueAsString(COLUMNNAME_ESRReferenceNumber);
	}

	@Override
	public void setESRTrxType (final java.lang.String ESRTrxType)
	{
		set_Value (COLUMNNAME_ESRTrxType, ESRTrxType);
	}

	@Override
	public java.lang.String getESRTrxType() 
	{
		return get_ValueAsString(COLUMNNAME_ESRTrxType);
	}

	@Override
	public void setImportErrorMsg (final java.lang.String ImportErrorMsg)
	{
		set_Value (COLUMNNAME_ImportErrorMsg, ImportErrorMsg);
	}

	@Override
	public java.lang.String getImportErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_ImportErrorMsg);
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
	public void setIsValid (final boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, IsValid);
	}

	@Override
	public boolean isValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsValid);
	}

	@Override
	public void setLineNo (final int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, LineNo);
	}

	@Override
	public int getLineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_LineNo);
	}

	@Override
	public void setMatchErrorMsg (final java.lang.String MatchErrorMsg)
	{
		set_Value (COLUMNNAME_MatchErrorMsg, MatchErrorMsg);
	}

	@Override
	public java.lang.String getMatchErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_MatchErrorMsg);
	}

	@Override
	public void setOrg_ID (final int Org_ID)
	{
		if (Org_ID < 1) 
			set_Value (COLUMNNAME_Org_ID, null);
		else 
			set_Value (COLUMNNAME_Org_ID, Org_ID);
	}

	@Override
	public int getOrg_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Org_ID);
	}

	@Override
	public void setPaymentDate (final java.sql.Timestamp PaymentDate)
	{
		set_Value (COLUMNNAME_PaymentDate, PaymentDate);
	}

	@Override
	public java.sql.Timestamp getPaymentDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PaymentDate);
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
	public void setSektionNo (final java.lang.String SektionNo)
	{
		set_Value (COLUMNNAME_SektionNo, SektionNo);
	}

	@Override
	public java.lang.String getSektionNo() 
	{
		return get_ValueAsString(COLUMNNAME_SektionNo);
	}

	/** 
	 * Type AD_Reference_ID=541287
	 * Reference name: ESR_Type
	 */
	public static final int TYPE_AD_Reference_ID=541287;
	/** QRR = QRR */
	public static final String TYPE_QRR = "QRR";
	/** ISR Reference = ISR Reference */
	public static final String TYPE_ESR = "ISR Reference";
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
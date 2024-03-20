// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_RemittanceAdvice
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_RemittanceAdvice extends org.compiere.model.PO implements I_C_RemittanceAdvice, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2027386473L;

    /** Standard Constructor */
    public X_C_RemittanceAdvice (final Properties ctx, final int C_RemittanceAdvice_ID, @Nullable final String trxName)
    {
      super (ctx, C_RemittanceAdvice_ID, trxName);
    }

    /** Load Constructor */
    public X_C_RemittanceAdvice (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAdditionalNotes (final @Nullable java.lang.String AdditionalNotes)
	{
		set_Value (COLUMNNAME_AdditionalNotes, AdditionalNotes);
	}

	@Override
	public java.lang.String getAdditionalNotes() 
	{
		return get_ValueAsString(COLUMNNAME_AdditionalNotes);
	}

	@Override
	public void setAnnouncedDateTrx (final @Nullable java.sql.Timestamp AnnouncedDateTrx)
	{
		set_Value (COLUMNNAME_AnnouncedDateTrx, AnnouncedDateTrx);
	}

	@Override
	public java.sql.Timestamp getAnnouncedDateTrx() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_AnnouncedDateTrx);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setC_Payment_Doctype_Target_ID (final int C_Payment_Doctype_Target_ID)
	{
		if (C_Payment_Doctype_Target_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_Doctype_Target_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_Doctype_Target_ID, C_Payment_Doctype_Target_ID);
	}

	@Override
	public int getC_Payment_Doctype_Target_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_Doctype_Target_ID);
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
	public void setC_RemittanceAdvice_ID (final int C_RemittanceAdvice_ID)
	{
		if (C_RemittanceAdvice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RemittanceAdvice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RemittanceAdvice_ID, C_RemittanceAdvice_ID);
	}

	@Override
	public int getC_RemittanceAdvice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_RemittanceAdvice_ID);
	}

	@Override
	public void setCurrenciesReadOnlyFlag (final boolean CurrenciesReadOnlyFlag)
	{
		set_Value (COLUMNNAME_CurrenciesReadOnlyFlag, CurrenciesReadOnlyFlag);
	}

	@Override
	public boolean isCurrenciesReadOnlyFlag() 
	{
		return get_ValueAsBoolean(COLUMNNAME_CurrenciesReadOnlyFlag);
	}

	@Override
	public void setDateDoc (final java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public void setDestination_BP_BankAccount_ID (final int Destination_BP_BankAccount_ID)
	{
		if (Destination_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_Destination_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_Destination_BP_BankAccount_ID, Destination_BP_BankAccount_ID);
	}

	@Override
	public int getDestination_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Destination_BP_BankAccount_ID);
	}

	@Override
	public void setDestintion_BPartner_ID (final int Destintion_BPartner_ID)
	{
		if (Destintion_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Destintion_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Destintion_BPartner_ID, Destintion_BPartner_ID);
	}

	@Override
	public int getDestintion_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Destintion_BPartner_ID);
	}

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	@Override
	public void setDocAction (final java.lang.String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	@Override
	public java.lang.String getDocAction() 
	{
		return get_ValueAsString(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	@Override
	public void setDocStatus (final java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo (final java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setExternalDocumentNo (final @Nullable java.lang.String ExternalDocumentNo)
	{
		set_Value (COLUMNNAME_ExternalDocumentNo, ExternalDocumentNo);
	}

	@Override
	public java.lang.String getExternalDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalDocumentNo);
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
	public void setIsDocumentAcknowledged (final boolean IsDocumentAcknowledged)
	{
		set_Value (COLUMNNAME_IsDocumentAcknowledged, IsDocumentAcknowledged);
	}

	@Override
	public boolean isDocumentAcknowledged() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDocumentAcknowledged);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setPaymentDate (final @Nullable java.sql.Timestamp PaymentDate)
	{
		set_Value (COLUMNNAME_PaymentDate, PaymentDate);
	}

	@Override
	public java.sql.Timestamp getPaymentDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PaymentDate);
	}

	@Override
	public void setPaymentDiscountAmountSum (final @Nullable BigDecimal PaymentDiscountAmountSum)
	{
		set_Value (COLUMNNAME_PaymentDiscountAmountSum, PaymentDiscountAmountSum);
	}

	@Override
	public BigDecimal getPaymentDiscountAmountSum() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PaymentDiscountAmountSum);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setRemittanceAmt (final BigDecimal RemittanceAmt)
	{
		set_Value (COLUMNNAME_RemittanceAmt, RemittanceAmt);
	}

	@Override
	public BigDecimal getRemittanceAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_RemittanceAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRemittanceAmt_Currency_ID (final int RemittanceAmt_Currency_ID)
	{
		if (RemittanceAmt_Currency_ID < 1) 
			set_Value (COLUMNNAME_RemittanceAmt_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_RemittanceAmt_Currency_ID, RemittanceAmt_Currency_ID);
	}

	@Override
	public int getRemittanceAmt_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_RemittanceAmt_Currency_ID);
	}

	@Override
	public void setSendAt (final @Nullable java.sql.Timestamp SendAt)
	{
		set_Value (COLUMNNAME_SendAt, SendAt);
	}

	@Override
	public java.sql.Timestamp getSendAt() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_SendAt);
	}

	@Override
	public void setServiceFeeAmount (final @Nullable BigDecimal ServiceFeeAmount)
	{
		set_Value (COLUMNNAME_ServiceFeeAmount, ServiceFeeAmount);
	}

	@Override
	public BigDecimal getServiceFeeAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ServiceFeeAmount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setServiceFeeAmount_Currency_ID (final int ServiceFeeAmount_Currency_ID)
	{
		if (ServiceFeeAmount_Currency_ID < 1) 
			set_Value (COLUMNNAME_ServiceFeeAmount_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_ServiceFeeAmount_Currency_ID, ServiceFeeAmount_Currency_ID);
	}

	@Override
	public int getServiceFeeAmount_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ServiceFeeAmount_Currency_ID);
	}

	@Override
	public void setServiceFeeExternalInvoiceDocumentNo (final @Nullable java.lang.String ServiceFeeExternalInvoiceDocumentNo)
	{
		set_Value (COLUMNNAME_ServiceFeeExternalInvoiceDocumentNo, ServiceFeeExternalInvoiceDocumentNo);
	}

	@Override
	public java.lang.String getServiceFeeExternalInvoiceDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_ServiceFeeExternalInvoiceDocumentNo);
	}

	@Override
	public void setServiceFeeInvoicedDate (final @Nullable java.sql.Timestamp ServiceFeeInvoicedDate)
	{
		set_Value (COLUMNNAME_ServiceFeeInvoicedDate, ServiceFeeInvoicedDate);
	}

	@Override
	public java.sql.Timestamp getServiceFeeInvoicedDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ServiceFeeInvoicedDate);
	}

	@Override
	public void setSource_BP_BankAccount_ID (final int Source_BP_BankAccount_ID)
	{
		if (Source_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_Source_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_Source_BP_BankAccount_ID, Source_BP_BankAccount_ID);
	}

	@Override
	public int getSource_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Source_BP_BankAccount_ID);
	}

	@Override
	public void setSource_BPartner_ID (final int Source_BPartner_ID)
	{
		if (Source_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Source_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Source_BPartner_ID, Source_BPartner_ID);
	}

	@Override
	public int getSource_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Source_BPartner_ID);
	}
}
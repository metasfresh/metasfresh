// Generated Model - DO NOT CHANGE
package de.metas.document.archive.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Doc_Outbound_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Doc_Outbound_Log extends org.compiere.model.PO implements I_C_Doc_Outbound_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1679925780L;

    /** Standard Constructor */
    public X_C_Doc_Outbound_Log (final Properties ctx, final int C_Doc_Outbound_Log_ID, @Nullable final String trxName)
    {
      super (ctx, C_Doc_Outbound_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Doc_Outbound_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setC_Async_Batch_ID (final int C_Async_Batch_ID)
	{
		if (C_Async_Batch_ID < 1) 
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_ID, C_Async_Batch_ID);
	}

	@Override
	public int getC_Async_Batch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public org.compiere.model.I_C_BP_Group getC_BP_Group()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group(final org.compiere.model.I_C_BP_Group C_BP_Group)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group);
	}

	@Override
	public void setC_BP_Group_ID (final int C_BP_Group_ID)
	{
		throw new IllegalArgumentException ("C_BP_Group_ID is virtual column");	}

	@Override
	public int getC_BP_Group_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Group_ID);
	}

	@Override
	public void setC_Doc_Outbound_Log_ID (final int C_Doc_Outbound_Log_ID)
	{
		if (C_Doc_Outbound_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_ID, C_Doc_Outbound_Log_ID);
	}

	@Override
	public int getC_Doc_Outbound_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_Outbound_Log_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setCurrentEMailAddress (final @Nullable java.lang.String CurrentEMailAddress)
	{
		set_Value (COLUMNNAME_CurrentEMailAddress, CurrentEMailAddress);
	}

	@Override
	public java.lang.String getCurrentEMailAddress() 
	{
		return get_ValueAsString(COLUMNNAME_CurrentEMailAddress);
	}

	@Override
	public void setCurrentEMailRecipient_ID (final int CurrentEMailRecipient_ID)
	{
		if (CurrentEMailRecipient_ID < 1) 
			set_Value (COLUMNNAME_CurrentEMailRecipient_ID, null);
		else 
			set_Value (COLUMNNAME_CurrentEMailRecipient_ID, CurrentEMailRecipient_ID);
	}

	@Override
	public int getCurrentEMailRecipient_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CurrentEMailRecipient_ID);
	}

	@Override
	public void setDateDoc (final @Nullable java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public void setDateLastEMail (final @Nullable java.sql.Timestamp DateLastEMail)
	{
		set_ValueNoCheck (COLUMNNAME_DateLastEMail, DateLastEMail);
	}

	@Override
	public java.sql.Timestamp getDateLastEMail() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateLastEMail);
	}

	@Override
	public void setDateLastPrint (final @Nullable java.sql.Timestamp DateLastPrint)
	{
		set_Value (COLUMNNAME_DateLastPrint, DateLastPrint);
	}

	@Override
	public java.sql.Timestamp getDateLastPrint() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateLastPrint);
	}

	@Override
	public void setDateLastStore (final @Nullable java.sql.Timestamp DateLastStore)
	{
		set_Value (COLUMNNAME_DateLastStore, DateLastStore);
	}

	@Override
	public java.sql.Timestamp getDateLastStore() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateLastStore);
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
	public void setDocStatus (final @Nullable java.lang.String DocStatus)
	{
		set_ValueNoCheck (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	/** 
	 * EDI_ExportStatus AD_Reference_ID=540381
	 * Reference name: EDI_ExportStatus
	 */
	public static final int EDI_EXPORTSTATUS_AD_Reference_ID=540381;
	/** Invalid = I */
	public static final String EDI_EXPORTSTATUS_Invalid = "I";
	/** Pending = P */
	public static final String EDI_EXPORTSTATUS_Pending = "P";
	/** Sent = S */
	public static final String EDI_EXPORTSTATUS_Sent = "S";
	/** SendingStarted = D */
	public static final String EDI_EXPORTSTATUS_SendingStarted = "D";
	/** Error = E */
	public static final String EDI_EXPORTSTATUS_Error = "E";
	/** Enqueued = U */
	public static final String EDI_EXPORTSTATUS_Enqueued = "U";
	/** DontSend = N */
	public static final String EDI_EXPORTSTATUS_DontSend = "N";
	@Override
	public void setEDI_ExportStatus (final @Nullable java.lang.String EDI_ExportStatus)
	{
		set_ValueNoCheck (COLUMNNAME_EDI_ExportStatus, EDI_ExportStatus);
	}

	@Override
	public java.lang.String getEDI_ExportStatus() 
	{
		return get_ValueAsString(COLUMNNAME_EDI_ExportStatus);
	}

	@Override
	public void setEMailCount (final int EMailCount)
	{
		throw new IllegalArgumentException ("EMailCount is virtual column");	}

	@Override
	public int getEMailCount() 
	{
		return get_ValueAsInt(COLUMNNAME_EMailCount);
	}

	@Override
	public void setFileName (final @Nullable java.lang.String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	@Override
	public java.lang.String getFileName() 
	{
		return get_ValueAsString(COLUMNNAME_FileName);
	}

	@Override
	public void setIsEdiEnabled (final boolean IsEdiEnabled)
	{
		throw new IllegalArgumentException ("IsEdiEnabled is virtual column");	}

	@Override
	public boolean isEdiEnabled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEdiEnabled);
	}

	@Override
	public void setIsInvoiceEmailEnabled (final boolean IsInvoiceEmailEnabled)
	{
		set_Value (COLUMNNAME_IsInvoiceEmailEnabled, IsInvoiceEmailEnabled);
	}

	@Override
	public boolean isInvoiceEmailEnabled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoiceEmailEnabled);
	}

	@Override
	public void setPDFCount (final int PDFCount)
	{
		throw new IllegalArgumentException ("PDFCount is virtual column");	}

	@Override
	public int getPDFCount() 
	{
		return get_ValueAsInt(COLUMNNAME_PDFCount);
	}

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		throw new IllegalArgumentException ("POReference is virtual column");	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	/** 
	 * PostFinance_Export_Status AD_Reference_ID=541860
	 * Reference name: PostFinance_ExportStatus
	 */
	public static final int POSTFINANCE_EXPORT_STATUS_AD_Reference_ID=541860;
	/** NotSent = N */
	public static final String POSTFINANCE_EXPORT_STATUS_NotSent = "N";
	/** Error = E */
	public static final String POSTFINANCE_EXPORT_STATUS_Error = "E";
	/** OK = O */
	public static final String POSTFINANCE_EXPORT_STATUS_OK = "O";
	@Override
	public void setPostFinance_Export_Status (final java.lang.String PostFinance_Export_Status)
	{
		set_ValueNoCheck (COLUMNNAME_PostFinance_Export_Status, PostFinance_Export_Status);
	}

	@Override
	public java.lang.String getPostFinance_Export_Status() 
	{
		return get_ValueAsString(COLUMNNAME_PostFinance_Export_Status);
	}

	@Override
	public void setPrintCount (final int PrintCount)
	{
		throw new IllegalArgumentException ("PrintCount is virtual column");	}

	@Override
	public int getPrintCount() 
	{
		return get_ValueAsInt(COLUMNNAME_PrintCount);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setStoreCount (final int StoreCount)
	{
		throw new IllegalArgumentException ("StoreCount is virtual column");	}

	@Override
	public int getStoreCount() 
	{
		return get_ValueAsInt(COLUMNNAME_StoreCount);
	}
}
// Generated Model - DO NOT CHANGE
package de.metas.document.archive.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Doc_Outbound_Log_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Doc_Outbound_Log_Line extends org.compiere.model.PO implements I_C_Doc_Outbound_Log_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1108825301L;

    /** Standard Constructor */
    public X_C_Doc_Outbound_Log_Line (final Properties ctx, final int C_Doc_Outbound_Log_Line_ID, @Nullable final String trxName)
    {
      super (ctx, C_Doc_Outbound_Log_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Doc_Outbound_Log_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * Action AD_Reference_ID=505210
	 * Reference name: C_DocExchange Action
	 */
	public static final int ACTION_AD_Reference_ID=505210;
	/** Print = print */
	public static final String ACTION_Print = "print";
	/** EMail = eMail */
	public static final String ACTION_EMail = "eMail";
	/** PdfExport = pdf */
	public static final String ACTION_PdfExport = "pdf";
	/** AttachmentStored = attachmentStored */
	public static final String ACTION_AttachmentStored = "attachmentStored";
	@Override
	public void setAction (final @Nullable java.lang.String Action)
	{
		set_ValueNoCheck (COLUMNNAME_Action, Action);
	}

	@Override
	public java.lang.String getAction() 
	{
		return get_ValueAsString(COLUMNNAME_Action);
	}

	@Override
	public org.compiere.model.I_AD_Archive getAD_Archive()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class);
	}

	@Override
	public void setAD_Archive(final org.compiere.model.I_AD_Archive AD_Archive)
	{
		set_ValueFromPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class, AD_Archive);
	}

	@Override
	public void setAD_Archive_ID (final int AD_Archive_ID)
	{
		if (AD_Archive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, AD_Archive_ID);
	}

	@Override
	public int getAD_Archive_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Archive_ID);
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
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
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
	public de.metas.document.archive.model.I_C_Doc_Outbound_Log getC_Doc_Outbound_Log()
	{
		return get_ValueAsPO(COLUMNNAME_C_Doc_Outbound_Log_ID, de.metas.document.archive.model.I_C_Doc_Outbound_Log.class);
	}

	@Override
	public void setC_Doc_Outbound_Log(final de.metas.document.archive.model.I_C_Doc_Outbound_Log C_Doc_Outbound_Log)
	{
		set_ValueFromPO(COLUMNNAME_C_Doc_Outbound_Log_ID, de.metas.document.archive.model.I_C_Doc_Outbound_Log.class, C_Doc_Outbound_Log);
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
	public void setC_Doc_Outbound_Log_Line_ID (final int C_Doc_Outbound_Log_Line_ID)
	{
		if (C_Doc_Outbound_Log_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_Line_ID, C_Doc_Outbound_Log_Line_ID);
	}

	@Override
	public int getC_Doc_Outbound_Log_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_Outbound_Log_Line_ID);
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
	public void setCopies (final int Copies)
	{
		set_ValueNoCheck (COLUMNNAME_Copies, Copies);
	}

	@Override
	public int getCopies() 
	{
		return get_ValueAsInt(COLUMNNAME_Copies);
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
		set_Value (COLUMNNAME_DocStatus, DocStatus);
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

	@Override
	public void setEMail_Bcc (final @Nullable java.lang.String EMail_Bcc)
	{
		set_ValueNoCheck (COLUMNNAME_EMail_Bcc, EMail_Bcc);
	}

	@Override
	public java.lang.String getEMail_Bcc() 
	{
		return get_ValueAsString(COLUMNNAME_EMail_Bcc);
	}

	@Override
	public void setEMail_Cc (final @Nullable java.lang.String EMail_Cc)
	{
		set_ValueNoCheck (COLUMNNAME_EMail_Cc, EMail_Cc);
	}

	@Override
	public java.lang.String getEMail_Cc() 
	{
		return get_ValueAsString(COLUMNNAME_EMail_Cc);
	}

	@Override
	public void setEMail_From (final @Nullable java.lang.String EMail_From)
	{
		set_ValueNoCheck (COLUMNNAME_EMail_From, EMail_From);
	}

	@Override
	public java.lang.String getEMail_From() 
	{
		return get_ValueAsString(COLUMNNAME_EMail_From);
	}

	@Override
	public void setEMail_To (final @Nullable java.lang.String EMail_To)
	{
		set_ValueNoCheck (COLUMNNAME_EMail_To, EMail_To);
	}

	@Override
	public java.lang.String getEMail_To() 
	{
		return get_ValueAsString(COLUMNNAME_EMail_To);
	}

	@Override
	public void setPrinterName (final @Nullable java.lang.String PrinterName)
	{
		set_ValueNoCheck (COLUMNNAME_PrinterName, PrinterName);
	}

	@Override
	public java.lang.String getPrinterName() 
	{
		return get_ValueAsString(COLUMNNAME_PrinterName);
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

	/** 
	 * Status AD_Reference_ID=542015
	 * Reference name: OutboundLogLineStatus
	 */
	public static final int STATUS_AD_Reference_ID=542015;
	/** Print_Success = Print_Success */
	public static final String STATUS_Print_Success = "Print_Success";
	/** Print_Failure = Print_Failure */
	public static final String STATUS_Print_Failure = "Print_Failure";
	/** Email_Success = Email_Success */
	public static final String STATUS_Email_Success = "Email_Success";
	/** Email_Failure = Email_Failure */
	public static final String STATUS_Email_Failure = "Email_Failure";
	@Override
	public void setStatus (final @Nullable java.lang.String Status)
	{
		set_ValueNoCheck (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}

	@Override
	public void setStoreURI (final @Nullable java.lang.String StoreURI)
	{
		set_Value (COLUMNNAME_StoreURI, StoreURI);
	}

	@Override
	public java.lang.String getStoreURI() 
	{
		return get_ValueAsString(COLUMNNAME_StoreURI);
	}
}
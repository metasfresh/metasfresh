/** Generated Model - DO NOT CHANGE */
package de.metas.document.archive.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Doc_Outbound_Log_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Doc_Outbound_Log_Line extends org.compiere.model.PO implements I_C_Doc_Outbound_Log_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1349749021L;

    /** Standard Constructor */
    public X_C_Doc_Outbound_Log_Line (Properties ctx, int C_Doc_Outbound_Log_Line_ID, String trxName)
    {
      super (ctx, C_Doc_Outbound_Log_Line_ID, trxName);
      /** if (C_Doc_Outbound_Log_Line_ID == 0)
        {
			setAD_Table_ID (0);
			setC_Doc_Outbound_Log_ID (0);
			setC_Doc_Outbound_Log_Line_ID (0);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Doc_Outbound_Log_Line (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
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
	/** Set Aktion.
		@param Action Aktion	  */
	@Override
	public void setAction (java.lang.String Action)
	{

		set_ValueNoCheck (COLUMNNAME_Action, Action);
	}

	/** Get Aktion.
		@return Aktion	  */
	@Override
	public java.lang.String getAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Action);
	}

	@Override
	public org.compiere.model.I_AD_Archive getAD_Archive()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class);
	}

	@Override
	public void setAD_Archive(org.compiere.model.I_AD_Archive AD_Archive)
	{
		set_ValueFromPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class, AD_Archive);
	}

	/** Set Archiv.
		@param AD_Archive_ID 
		Archiv für Belege und Berichte
	  */
	@Override
	public void setAD_Archive_ID (int AD_Archive_ID)
	{
		if (AD_Archive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, Integer.valueOf(AD_Archive_ID));
	}

	/** Get Archiv.
		@return Archiv für Belege und Berichte
	  */
	@Override
	public int getAD_Archive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Archive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.document.archive.model.I_C_Doc_Outbound_Log getC_Doc_Outbound_Log()
	{
		return get_ValueAsPO(COLUMNNAME_C_Doc_Outbound_Log_ID, de.metas.document.archive.model.I_C_Doc_Outbound_Log.class);
	}

	@Override
	public void setC_Doc_Outbound_Log(de.metas.document.archive.model.I_C_Doc_Outbound_Log C_Doc_Outbound_Log)
	{
		set_ValueFromPO(COLUMNNAME_C_Doc_Outbound_Log_ID, de.metas.document.archive.model.I_C_Doc_Outbound_Log.class, C_Doc_Outbound_Log);
	}

	/** Set C_Doc_Outbound_Log.
		@param C_Doc_Outbound_Log_ID C_Doc_Outbound_Log	  */
	@Override
	public void setC_Doc_Outbound_Log_ID (int C_Doc_Outbound_Log_ID)
	{
		if (C_Doc_Outbound_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_ID, Integer.valueOf(C_Doc_Outbound_Log_ID));
	}

	/** Get C_Doc_Outbound_Log.
		@return C_Doc_Outbound_Log	  */
	@Override
	public int getC_Doc_Outbound_Log_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Doc_Outbound_Log_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Doc_Outbound_Log_Line.
		@param C_Doc_Outbound_Log_Line_ID C_Doc_Outbound_Log_Line	  */
	@Override
	public void setC_Doc_Outbound_Log_Line_ID (int C_Doc_Outbound_Log_Line_ID)
	{
		if (C_Doc_Outbound_Log_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_Line_ID, Integer.valueOf(C_Doc_Outbound_Log_Line_ID));
	}

	/** Get C_Doc_Outbound_Log_Line.
		@return C_Doc_Outbound_Log_Line	  */
	@Override
	public int getC_Doc_Outbound_Log_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Doc_Outbound_Log_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kopien.
		@param Copies 
		Anzahl der zu erstellenden/zu druckenden Exemplare
	  */
	@Override
	public void setCopies (int Copies)
	{
		set_ValueNoCheck (COLUMNNAME_Copies, Integer.valueOf(Copies));
	}

	/** Get Kopien.
		@return Anzahl der zu erstellenden/zu druckenden Exemplare
	  */
	@Override
	public int getCopies () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Copies);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		set_ValueNoCheck (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set EMail Bcc.
		@param EMail_Bcc EMail Bcc	  */
	@Override
	public void setEMail_Bcc (java.lang.String EMail_Bcc)
	{
		set_ValueNoCheck (COLUMNNAME_EMail_Bcc, EMail_Bcc);
	}

	/** Get EMail Bcc.
		@return EMail Bcc	  */
	@Override
	public java.lang.String getEMail_Bcc () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail_Bcc);
	}

	/** Set EMail Cc.
		@param EMail_Cc EMail Cc	  */
	@Override
	public void setEMail_Cc (java.lang.String EMail_Cc)
	{
		set_ValueNoCheck (COLUMNNAME_EMail_Cc, EMail_Cc);
	}

	/** Get EMail Cc.
		@return EMail Cc	  */
	@Override
	public java.lang.String getEMail_Cc () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail_Cc);
	}

	/** Set EMail Absender.
		@param EMail_From 
		Full EMail address used to send requests - e.g. edi@organization.com
	  */
	@Override
	public void setEMail_From (java.lang.String EMail_From)
	{
		set_ValueNoCheck (COLUMNNAME_EMail_From, EMail_From);
	}

	/** Get EMail Absender.
		@return Full EMail address used to send requests - e.g. edi@organization.com
	  */
	@Override
	public java.lang.String getEMail_From () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail_From);
	}

	/** Set EMail Empfänger.
		@param EMail_To 
		EMail address to send requests to - e.g. edi@manufacturer.com 
	  */
	@Override
	public void setEMail_To (java.lang.String EMail_To)
	{
		set_ValueNoCheck (COLUMNNAME_EMail_To, EMail_To);
	}

	/** Get EMail Empfänger.
		@return EMail address to send requests to - e.g. edi@manufacturer.com 
	  */
	@Override
	public java.lang.String getEMail_To () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail_To);
	}

	/** Set Drucker.
		@param PrinterName 
		Name of the Printer
	  */
	@Override
	public void setPrinterName (java.lang.String PrinterName)
	{
		set_ValueNoCheck (COLUMNNAME_PrinterName, PrinterName);
	}

	/** Get Drucker.
		@return Name of the Printer
	  */
	@Override
	public java.lang.String getPrinterName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrinterName);
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	@Override
	public void setStatus (java.lang.String Status)
	{
		set_ValueNoCheck (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	@Override
	public java.lang.String getStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Status);
	}

	/** Set Speicherort.
		@param StoreURI Speicherort	  */
	@Override
	public void setStoreURI (java.lang.String StoreURI)
	{
		set_Value (COLUMNNAME_StoreURI, StoreURI);
	}

	/** Get Speicherort.
		@return Speicherort	  */
	@Override
	public java.lang.String getStoreURI () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StoreURI);
	}
}
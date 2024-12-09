// Generated Model - DO NOT CHANGE
package de.metas.document.archive.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Doc_Outbound_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
<<<<<<< HEAD
public class X_C_Doc_Outbound_Log extends org.compiere.model.PO implements I_C_Doc_Outbound_Log, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1704128096L;

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
		public int getAD_Table_ID ()
		{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
		}

		@Override
	public void setC_Async_Batch_ID (final int C_Async_Batch_ID)
		{
=======
public class X_C_Doc_Outbound_Log extends org.compiere.model.PO implements I_C_Doc_Outbound_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1911580358L;

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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public int getC_BP_Group_ID ()
=======
	public int getC_BP_Group_ID() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Group_ID);
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
	public void setC_Doc_Outbound_Log_ID (final int C_Doc_Outbound_Log_ID)
	{
<<<<<<< HEAD
		if (C_Doc_Outbound_Log_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_ID, null);
		else
=======
		if (C_Doc_Outbound_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_ID, null);
		else 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_ID, C_Doc_Outbound_Log_ID);
	}

	@Override
<<<<<<< HEAD
	public int getC_Doc_Outbound_Log_ID ()
=======
	public int getC_Doc_Outbound_Log_ID() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_Outbound_Log_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
<<<<<<< HEAD
		if (C_DocType_ID < 0)
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else
=======
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
<<<<<<< HEAD
	public int getC_DocType_ID ()
=======
	public int getC_DocType_ID() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setCurrentEMailAddress (final @Nullable String CurrentEMailAddress)
	{
		set_Value (COLUMNNAME_CurrentEMailAddress, CurrentEMailAddress);
	}

	@Override
<<<<<<< HEAD
	public String getCurrentEMailAddress()
=======
	public String getCurrentEMailAddress() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_CurrentEMailAddress);
	}

	@Override
	public void setCurrentEMailRecipient_ID (final int CurrentEMailRecipient_ID)
	{
<<<<<<< HEAD
		if (CurrentEMailRecipient_ID < 1)
			set_Value (COLUMNNAME_CurrentEMailRecipient_ID, null);
		else
=======
		if (CurrentEMailRecipient_ID < 1) 
			set_Value (COLUMNNAME_CurrentEMailRecipient_ID, null);
		else 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_CurrentEMailRecipient_ID, CurrentEMailRecipient_ID);
	}

	@Override
<<<<<<< HEAD
	public int getCurrentEMailRecipient_ID ()
=======
	public int getCurrentEMailRecipient_ID() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_CurrentEMailRecipient_ID);
	}

	@Override
	public void setDateDoc (final @Nullable java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
<<<<<<< HEAD
	public java.sql.Timestamp getDateDoc ()
=======
	public java.sql.Timestamp getDateDoc() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public void setDateLastEMail (final @Nullable java.sql.Timestamp DateLastEMail)
	{
		set_ValueNoCheck (COLUMNNAME_DateLastEMail, DateLastEMail);
	}

	@Override
<<<<<<< HEAD
	public java.sql.Timestamp getDateLastEMail ()
=======
	public java.sql.Timestamp getDateLastEMail() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateLastEMail);
	}

	@Override
	public void setDateLastPrint (final @Nullable java.sql.Timestamp DateLastPrint)
	{
		set_Value (COLUMNNAME_DateLastPrint, DateLastPrint);
	}

	@Override
<<<<<<< HEAD
	public java.sql.Timestamp getDateLastPrint ()
=======
	public java.sql.Timestamp getDateLastPrint() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateLastPrint);
	}

	@Override
	public void setDateLastStore (final @Nullable java.sql.Timestamp DateLastStore)
	{
		set_Value (COLUMNNAME_DateLastStore, DateLastStore);
	}

	@Override
<<<<<<< HEAD
	public java.sql.Timestamp getDateLastStore ()
=======
	public java.sql.Timestamp getDateLastStore() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateLastStore);
	}

<<<<<<< HEAD
	/**
=======
	/** 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
	public void setDocStatus (final @Nullable String DocStatus)
	{
		set_ValueNoCheck (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
<<<<<<< HEAD
	public String getDocStatus()
=======
	public String getDocStatus() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo (final @Nullable String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
<<<<<<< HEAD
	public String getDocumentNo()
=======
	public String getDocumentNo() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
	public void setEDI_ExportStatus (final @Nullable String EDI_ExportStatus)
	{
		set_ValueNoCheck (COLUMNNAME_EDI_ExportStatus, EDI_ExportStatus);
	}

	@Override
<<<<<<< HEAD
	public String getEDI_ExportStatus()
=======
	public String getEDI_ExportStatus() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_EDI_ExportStatus);
	}

	@Override
	public void setEMailCount (final int EMailCount)
	{
		throw new IllegalArgumentException ("EMailCount is virtual column");	}

	@Override
<<<<<<< HEAD
	public int getEMailCount ()
=======
	public int getEMailCount() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_EMailCount);
	}

	@Override
<<<<<<< HEAD
	public void setFileName (final @Nullable java.lang.String FileName)
=======
	public void setFileName (final @Nullable String FileName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	@Override
<<<<<<< HEAD
	public java.lang.String getFileName()
=======
	public String getFileName() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_FileName);
	}

<<<<<<< HEAD
	/** Set Beleg soll per EDI übermittelt werden.
		@param IsEdiEnabled Beleg soll per EDI übermittelt werden	  */
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@Override
	public void setIsEdiEnabled (final boolean IsEdiEnabled)
	{
		throw new IllegalArgumentException ("IsEdiEnabled is virtual column");	}

	@Override
<<<<<<< HEAD
	public boolean isEdiEnabled ()
=======
	public boolean isEdiEnabled() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEdiEnabled);
	}

	@Override
	public void setIsInvoiceEmailEnabled (final boolean IsInvoiceEmailEnabled)
	{
		set_Value (COLUMNNAME_IsInvoiceEmailEnabled, IsInvoiceEmailEnabled);
	}

	@Override
<<<<<<< HEAD
	public boolean isInvoiceEmailEnabled ()
=======
	public boolean isInvoiceEmailEnabled() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoiceEmailEnabled);
	}

	@Override
	public void setPDFCount (final int PDFCount)
	{
		throw new IllegalArgumentException ("PDFCount is virtual column");	}

	@Override
<<<<<<< HEAD
	public int getPDFCount ()
=======
	public int getPDFCount() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_PDFCount);
	}

	@Override
	public void setPOReference (final @Nullable String POReference)
	{
<<<<<<< HEAD
		throw new IllegalArgumentException ("POReference is virtual column");	}

	@Override
	public String getPOReference()
=======
		set_ValueNoCheck (COLUMNNAME_POReference, POReference);
	}

	@Override
	public String getPOReference() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	@Override
	public void setPrintCount (final int PrintCount)
	{
		throw new IllegalArgumentException ("PrintCount is virtual column");	}

	@Override
<<<<<<< HEAD
	public int getPrintCount ()
=======
	public int getPrintCount() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_PrintCount);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
<<<<<<< HEAD
		if (Record_ID < 0)
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else
=======
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
<<<<<<< HEAD
	public int getRecord_ID ()
=======
	public int getRecord_ID() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setStoreCount (final int StoreCount)
	{
		throw new IllegalArgumentException ("StoreCount is virtual column");	}

	@Override
<<<<<<< HEAD
	public int getStoreCount ()
=======
	public int getStoreCount() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_StoreCount);
	}
}
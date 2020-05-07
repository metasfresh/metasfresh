/** Generated Model - DO NOT CHANGE */
package de.metas.document.archive.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Doc_Outbound_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Doc_Outbound_Log extends org.compiere.model.PO implements I_C_Doc_Outbound_Log, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1842671222L;

    /** Standard Constructor */
    public X_C_Doc_Outbound_Log (Properties ctx, int C_Doc_Outbound_Log_ID, String trxName)
    {
      super (ctx, C_Doc_Outbound_Log_ID, trxName);
      /** if (C_Doc_Outbound_Log_ID == 0)
        {
			setAD_Table_ID (0);
			setC_Doc_Outbound_Log_ID (0);
			setIsInvoiceEmailEnabled (true);
// Y
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Doc_Outbound_Log (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
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

	@Override
	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group);
	}

	/** Set Geschäftspartnergruppe.
		@param C_BP_Group_ID 
		Geschäftspartnergruppe
	  */
	@Override
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		throw new IllegalArgumentException ("C_BP_Group_ID is virtual column");	}

	/** Get Geschäftspartnergruppe.
		@return Geschäftspartnergruppe
	  */
	@Override
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
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

	@Override
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
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

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	@Override
	public void setDateDoc (java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	@Override
	public java.sql.Timestamp getDateDoc () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Zuletzt gemailt.
		@param DateLastEMail Zuletzt gemailt	  */
	@Override
	public void setDateLastEMail (java.sql.Timestamp DateLastEMail)
	{
		set_ValueNoCheck (COLUMNNAME_DateLastEMail, DateLastEMail);
	}

	/** Get Zuletzt gemailt.
		@return Zuletzt gemailt	  */
	@Override
	public java.sql.Timestamp getDateLastEMail () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateLastEMail);
	}

	/** Set Zuletzt gedruckt.
		@param DateLastPrint Zuletzt gedruckt	  */
	@Override
	public void setDateLastPrint (java.sql.Timestamp DateLastPrint)
	{
		set_ValueNoCheck (COLUMNNAME_DateLastPrint, DateLastPrint);
	}

	/** Get Zuletzt gedruckt.
		@return Zuletzt gedruckt	  */
	@Override
	public java.sql.Timestamp getDateLastPrint () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateLastPrint);
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

	/** Set Beleg Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Beleg Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Anz. gemailt.
		@param EMailCount Anz. gemailt	  */
	@Override
	public void setEMailCount (int EMailCount)
	{
		throw new IllegalArgumentException ("EMailCount is virtual column");	}

	/** Get Anz. gemailt.
		@return Anz. gemailt	  */
	@Override
	public int getEMailCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EMailCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beleg soll per EDI übermittelt werden.
		@param IsEdiEnabled Beleg soll per EDI übermittelt werden	  */
	@Override
	public void setIsEdiEnabled (boolean IsEdiEnabled)
	{
		throw new IllegalArgumentException ("IsEdiEnabled is virtual column");	}

	/** Get Beleg soll per EDI übermittelt werden.
		@return Beleg soll per EDI übermittelt werden	  */
	@Override
	public boolean isEdiEnabled () 
	{
		Object oo = get_Value(COLUMNNAME_IsEdiEnabled);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Invoice Email Enabled.
		@param IsInvoiceEmailEnabled Invoice Email Enabled	  */
	@Override
	public void setIsInvoiceEmailEnabled (boolean IsInvoiceEmailEnabled)
	{
		set_Value (COLUMNNAME_IsInvoiceEmailEnabled, Boolean.valueOf(IsInvoiceEmailEnabled));
	}

	/** Get Invoice Email Enabled.
		@return Invoice Email Enabled	  */
	@Override
	public boolean isInvoiceEmailEnabled () 
	{
		Object oo = get_Value(COLUMNNAME_IsInvoiceEmailEnabled);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Anz. PDF.
		@param PDFCount Anz. PDF	  */
	@Override
	public void setPDFCount (int PDFCount)
	{
		throw new IllegalArgumentException ("PDFCount is virtual column");	}

	/** Get Anz. PDF.
		@return Anz. PDF	  */
	@Override
	public int getPDFCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PDFCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenz.
		@param POReference 
		Referenz-Nummer des Kunden
	  */
	@Override
	public void setPOReference (java.lang.String POReference)
	{
		throw new IllegalArgumentException ("POReference is virtual column");	}

	/** Get Referenz.
		@return Referenz-Nummer des Kunden
	  */
	@Override
	public java.lang.String getPOReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	/** Set Anz. gedruckt.
		@param PrintCount Anz. gedruckt	  */
	@Override
	public void setPrintCount (int PrintCount)
	{
		throw new IllegalArgumentException ("PrintCount is virtual column");	}

	/** Get Anz. gedruckt.
		@return Anz. gedruckt	  */
	@Override
	public int getPrintCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrintCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}
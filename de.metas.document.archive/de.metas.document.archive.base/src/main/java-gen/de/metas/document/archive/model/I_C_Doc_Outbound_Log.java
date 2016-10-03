package de.metas.document.archive.model;


/** Generated Interface for C_Doc_Outbound_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Doc_Outbound_Log 
{

    /** TableName=C_Doc_Outbound_Log */
    public static final String Table_Name = "C_Doc_Outbound_Log";

    /** AD_Table_ID=540453 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_AD_Client>(I_C_Doc_Outbound_Log.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_AD_Org>(I_C_Doc_Outbound_Log.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_AD_Table>(I_C_Doc_Outbound_Log.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Geschäftspartnergruppe.
	 * Geschäftspartnergruppe
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Geschäftspartnergruppe.
	 * Geschäftspartnergruppe
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group();

	@Deprecated
	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group);

    /** Column definition for C_BP_Group_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_C_BP_Group>(I_C_Doc_Outbound_Log.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_C_BPartner>(I_C_Doc_Outbound_Log.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set C_Doc_Outbound_Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Doc_Outbound_Log_ID (int C_Doc_Outbound_Log_ID);

	/**
	 * Get C_Doc_Outbound_Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Doc_Outbound_Log_ID();

    /** Column definition for C_Doc_Outbound_Log_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_C_Doc_Outbound_Log_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "C_Doc_Outbound_Log_ID", null);
    /** Column name C_Doc_Outbound_Log_ID */
    public static final String COLUMNNAME_C_Doc_Outbound_Log_ID = "C_Doc_Outbound_Log_ID";

	/**
	 * Set Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Belegart oder Verarbeitungsvorgaben
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_C_DocType>(I_C_Doc_Outbound_Log.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_AD_User>(I_C_Doc_Outbound_Log.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateDoc();

    /** Column definition for DateDoc */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_DateDoc = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "DateDoc", null);
    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Zuletzt gemailt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateLastEMail (java.sql.Timestamp DateLastEMail);

	/**
	 * Get Zuletzt gemailt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateLastEMail();

    /** Column definition for DateLastEMail */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_DateLastEMail = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "DateLastEMail", null);
    /** Column name DateLastEMail */
    public static final String COLUMNNAME_DateLastEMail = "DateLastEMail";

	/**
	 * Set Zuletzt gedruckt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateLastPrint (java.sql.Timestamp DateLastPrint);

	/**
	 * Get Zuletzt gedruckt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateLastPrint();

    /** Column definition for DateLastPrint */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_DateLastPrint = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "DateLastPrint", null);
    /** Column name DateLastPrint */
    public static final String COLUMNNAME_DateLastPrint = "DateLastPrint";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Anz. gemailt.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setEMailCount (int EMailCount);

	/**
	 * Get Anz. gemailt.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getEMailCount();

    /** Column definition for EMailCount */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_EMailCount = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "EMailCount", null);
    /** Column name EMailCount */
    public static final String COLUMNNAME_EMailCount = "EMailCount";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Beleg soll per EDI übermittelt werden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsEdiEnabled (boolean IsEdiEnabled);

	/**
	 * Get Beleg soll per EDI übermittelt werden.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isEdiEnabled();

    /** Column definition for IsEdiEnabled */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_IsEdiEnabled = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "IsEdiEnabled", null);
    /** Column name IsEdiEnabled */
    public static final String COLUMNNAME_IsEdiEnabled = "IsEdiEnabled";

	/**
	 * Set Invoice Email Enabled.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInvoiceEmailEnabled (boolean IsInvoiceEmailEnabled);

	/**
	 * Get Invoice Email Enabled.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInvoiceEmailEnabled();

    /** Column definition for IsInvoiceEmailEnabled */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_IsInvoiceEmailEnabled = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "IsInvoiceEmailEnabled", null);
    /** Column name IsInvoiceEmailEnabled */
    public static final String COLUMNNAME_IsInvoiceEmailEnabled = "IsInvoiceEmailEnabled";

	/**
	 * Set Anz. PDF.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setPDFCount (int PDFCount);

	/**
	 * Get Anz. PDF.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getPDFCount();

    /** Column definition for PDFCount */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_PDFCount = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "PDFCount", null);
    /** Column name PDFCount */
    public static final String COLUMNNAME_PDFCount = "PDFCount";

	/**
	 * Set Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setPOReference (java.lang.String POReference);

	/**
	 * Get Referenz.
	 * Referenz-Nummer des Kunden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getPOReference();

    /** Column definition for POReference */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Anz. gedruckt.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setPrintCount (int PrintCount);

	/**
	 * Get Anz. gedruckt.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getPrintCount();

    /** Column definition for PrintCount */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_PrintCount = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "PrintCount", null);
    /** Column name PrintCount */
    public static final String COLUMNNAME_PrintCount = "PrintCount";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, Object>(I_C_Doc_Outbound_Log.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log, org.compiere.model.I_AD_User>(I_C_Doc_Outbound_Log.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}

package de.metas.document.archive.model;


/** Generated Interface for C_Doc_Outbound_Log_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Doc_Outbound_Log_Line 
{

    /** TableName=C_Doc_Outbound_Log_Line */
    public static final String Table_Name = "C_Doc_Outbound_Log_Line";

    /** AD_Table_ID=540454 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Aktion.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAction (java.lang.String Action);

	/**
	 * Get Aktion.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAction();

    /** Column definition for Action */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Action = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "Action", null);
    /** Column name Action */
    public static final String COLUMNNAME_Action = "Action";

	/**
	 * Set Archiv.
	 * Archiv für Belege und Berichte
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Archive_ID (int AD_Archive_ID);

	/**
	 * Get Archiv.
	 * Archiv für Belege und Berichte
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Archive_ID();

	public org.compiere.model.I_AD_Archive getAD_Archive();

	public void setAD_Archive(org.compiere.model.I_AD_Archive AD_Archive);

    /** Column definition for AD_Archive_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_Archive> COLUMN_AD_Archive_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_Archive>(I_C_Doc_Outbound_Log_Line.class, "AD_Archive_ID", org.compiere.model.I_AD_Archive.class);
    /** Column name AD_Archive_ID */
    public static final String COLUMNNAME_AD_Archive_ID = "AD_Archive_ID";

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_Client>(I_C_Doc_Outbound_Log_Line.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_Org>(I_C_Doc_Outbound_Log_Line.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_Table>(I_C_Doc_Outbound_Log_Line.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_User>(I_C_Doc_Outbound_Log_Line.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

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

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_C_BPartner>(I_C_Doc_Outbound_Log_Line.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set C_Doc_Outbound_Log.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Doc_Outbound_Log_ID (int C_Doc_Outbound_Log_ID);

	/**
	 * Get C_Doc_Outbound_Log.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Doc_Outbound_Log_ID();

	public de.metas.document.archive.model.I_C_Doc_Outbound_Log getC_Doc_Outbound_Log();

	public void setC_Doc_Outbound_Log(de.metas.document.archive.model.I_C_Doc_Outbound_Log C_Doc_Outbound_Log);

    /** Column definition for C_Doc_Outbound_Log_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, de.metas.document.archive.model.I_C_Doc_Outbound_Log> COLUMN_C_Doc_Outbound_Log_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, de.metas.document.archive.model.I_C_Doc_Outbound_Log>(I_C_Doc_Outbound_Log_Line.class, "C_Doc_Outbound_Log_ID", de.metas.document.archive.model.I_C_Doc_Outbound_Log.class);
    /** Column name C_Doc_Outbound_Log_ID */
    public static final String COLUMNNAME_C_Doc_Outbound_Log_ID = "C_Doc_Outbound_Log_ID";

	/**
	 * Set C_Doc_Outbound_Log_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Doc_Outbound_Log_Line_ID (int C_Doc_Outbound_Log_Line_ID);

	/**
	 * Get C_Doc_Outbound_Log_Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Doc_Outbound_Log_Line_ID();

    /** Column definition for C_Doc_Outbound_Log_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_C_Doc_Outbound_Log_Line_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "C_Doc_Outbound_Log_Line_ID", null);
    /** Column name C_Doc_Outbound_Log_Line_ID */
    public static final String COLUMNNAME_C_Doc_Outbound_Log_Line_ID = "C_Doc_Outbound_Log_Line_ID";

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

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_C_DocType>(I_C_Doc_Outbound_Log_Line.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Kopien.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCopies (int Copies);

	/**
	 * Get Kopien.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCopies();

    /** Column definition for Copies */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Copies = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "Copies", null);
    /** Column name Copies */
    public static final String COLUMNNAME_Copies = "Copies";

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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_User>(I_C_Doc_Outbound_Log_Line.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set EMail Bcc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail_Bcc (java.lang.String EMail_Bcc);

	/**
	 * Get EMail Bcc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail_Bcc();

    /** Column definition for EMail_Bcc */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_EMail_Bcc = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "EMail_Bcc", null);
    /** Column name EMail_Bcc */
    public static final String COLUMNNAME_EMail_Bcc = "EMail_Bcc";

	/**
	 * Set EMail Cc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail_Cc (java.lang.String EMail_Cc);

	/**
	 * Get EMail Cc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail_Cc();

    /** Column definition for EMail_Cc */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_EMail_Cc = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "EMail_Cc", null);
    /** Column name EMail_Cc */
    public static final String COLUMNNAME_EMail_Cc = "EMail_Cc";

	/**
	 * Set EMail Absender.
	 * Full EMail address used to send requests - e.g. edi@organization.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail_From (java.lang.String EMail_From);

	/**
	 * Get EMail Absender.
	 * Full EMail address used to send requests - e.g. edi@organization.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail_From();

    /** Column definition for EMail_From */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_EMail_From = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "EMail_From", null);
    /** Column name EMail_From */
    public static final String COLUMNNAME_EMail_From = "EMail_From";

	/**
	 * Set EMail Empfänger.
	 * EMail address to send requests to - e.g. edi@manufacturer.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail_To (java.lang.String EMail_To);

	/**
	 * Get EMail Empfänger.
	 * EMail address to send requests to - e.g. edi@manufacturer.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail_To();

    /** Column definition for EMail_To */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_EMail_To = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "EMail_To", null);
    /** Column name EMail_To */
    public static final String COLUMNNAME_EMail_To = "EMail_To";

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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Drucker.
	 * Name of the Printer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPrinterName (java.lang.String PrinterName);

	/**
	 * Get Drucker.
	 * Name of the Printer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPrinterName();

    /** Column definition for PrinterName */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_PrinterName = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "PrinterName", null);
    /** Column name PrinterName */
    public static final String COLUMNNAME_PrinterName = "PrinterName";

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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Status.
	 * Status of the currently running check
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 * Status of the currently running check
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatus();

    /** Column definition for Status */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Status = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "Status", null);
    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/**
	 * Set Speicherort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStoreURI (java.lang.String StoreURI);

	/**
	 * Get Speicherort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStoreURI();

    /** Column definition for StoreURI */
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_StoreURI = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "StoreURI", null);
    /** Column name StoreURI */
    public static final String COLUMNNAME_StoreURI = "StoreURI";

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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, Object>(I_C_Doc_Outbound_Log_Line.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Doc_Outbound_Log_Line, org.compiere.model.I_AD_User>(I_C_Doc_Outbound_Log_Line.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}

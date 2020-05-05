package org.compiere.model;


/** Generated Interface for I_Request
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_Request 
{

    /** TableName=I_Request */
    public static final String Table_Name = "I_Request";

    /** AD_Table_ID=540840 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_AD_Client>(I_I_Request.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_AD_Org>(I_I_Request.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_C_BPartner>(I_I_Request.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_AD_User>(I_I_Request.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Date next action.
	 * Date that this request should be acted on
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateNextAction (java.sql.Timestamp DateNextAction);

	/**
	 * Get Date next action.
	 * Date that this request should be acted on
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateNextAction();

    /** Column definition for DateNextAction */
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_DateNextAction = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "DateNextAction", null);
    /** Column name DateNextAction */
    public static final String COLUMNNAME_DateNextAction = "DateNextAction";

	/**
	 * Set Vorgangsdatum.
	 * Vorgangsdatum
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Vorgangsdatum.
	 * Vorgangsdatum
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateTrx();

    /** Column definition for DateTrx */
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_DateTrx = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "DateTrx", null);
    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

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
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (java.lang.String I_IsImported);

	/**
	 * Get Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Request.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_Request_ID (int I_Request_ID);

	/**
	 * Get Import Request.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_Request_ID();

    /** Column definition for I_Request_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_I_Request_ID = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "I_Request_ID", null);
    /** Column name I_Request_ID */
    public static final String COLUMNNAME_I_Request_ID = "I_Request_ID";

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
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Aufgabe.
	 * Request from a Business Partner or Prospect
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_Request_ID (int R_Request_ID);

	/**
	 * Get Aufgabe.
	 * Request from a Business Partner or Prospect
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_Request_ID();

	public org.compiere.model.I_R_Request getR_Request();

	public void setR_Request(org.compiere.model.I_R_Request R_Request);

    /** Column definition for R_Request_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_R_Request> COLUMN_R_Request_ID = new org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_R_Request>(I_I_Request.class, "R_Request_ID", org.compiere.model.I_R_Request.class);
    /** Column name R_Request_ID */
    public static final String COLUMNNAME_R_Request_ID = "R_Request_ID";

	/**
	 * Set Request Type.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_RequestType_ID (int R_RequestType_ID);

	/**
	 * Get Request Type.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_RequestType_ID();

	public org.compiere.model.I_R_RequestType getR_RequestType();

	public void setR_RequestType(org.compiere.model.I_R_RequestType R_RequestType);

    /** Column definition for R_RequestType_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_R_RequestType> COLUMN_R_RequestType_ID = new org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_R_RequestType>(I_I_Request.class, "R_RequestType_ID", org.compiere.model.I_R_RequestType.class);
    /** Column name R_RequestType_ID */
    public static final String COLUMNNAME_R_RequestType_ID = "R_RequestType_ID";

	/**
	 * Set Status.
	 * Request Status
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_Status_ID (int R_Status_ID);

	/**
	 * Get Status.
	 * Request Status
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_Status_ID();

	public org.compiere.model.I_R_Status getR_Status();

	public void setR_Status(org.compiere.model.I_R_Status R_Status);

    /** Column definition for R_Status_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_R_Status> COLUMN_R_Status_ID = new org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_R_Status>(I_I_Request.class, "R_Status_ID", org.compiere.model.I_R_Status.class);
    /** Column name R_Status_ID */
    public static final String COLUMNNAME_R_Status_ID = "R_Status_ID";

	/**
	 * Set Anfrageart.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestType (java.lang.String RequestType);

	/**
	 * Get Anfrageart.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestType();

    /** Column definition for RequestType */
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_RequestType = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "RequestType", null);
    /** Column name RequestType */
    public static final String COLUMNNAME_RequestType = "RequestType";

	/**
	 * Set Ergebnis.
	 * Result of the action taken
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResult (java.lang.String Result);

	/**
	 * Get Ergebnis.
	 * Result of the action taken
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getResult();

    /** Column definition for Result */
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_Result = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "Result", null);
    /** Column name Result */
    public static final String COLUMNNAME_Result = "Result";

	/**
	 * Set Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatus();

    /** Column definition for Status */
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_Status = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "Status", null);
    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/**
	 * Set Summary.
	 * Textual summary of this request
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSummary (java.lang.String Summary);

	/**
	 * Get Summary.
	 * Textual summary of this request
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSummary();

    /** Column definition for Summary */
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_Summary = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "Summary", null);
    /** Column name Summary */
    public static final String COLUMNNAME_Summary = "Summary";

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
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_Request, org.compiere.model.I_AD_User>(I_I_Request.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_I_Request, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_I_Request, Object>(I_I_Request.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}

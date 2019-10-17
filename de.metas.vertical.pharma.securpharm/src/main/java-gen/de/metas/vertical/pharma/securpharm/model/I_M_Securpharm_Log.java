package de.metas.vertical.pharma.securpharm.model;


/** Generated Interface for M_Securpharm_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Securpharm_Log 
{

    /** TableName=M_Securpharm_Log */
    public static final String Table_Name = "M_Securpharm_Log";

    /** AD_Table_ID=541364 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, org.compiere.model.I_AD_Client>(I_M_Securpharm_Log.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Issue_ID();

	public org.compiere.model.I_AD_Issue getAD_Issue();

	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

    /** Column definition for AD_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, org.compiere.model.I_AD_Issue>(I_M_Securpharm_Log.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, org.compiere.model.I_AD_Org>(I_M_Securpharm_Log.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, org.compiere.model.I_AD_User>(I_M_Securpharm_Log.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsError (boolean IsError);

	/**
	 * Get Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isError();

    /** Column definition for IsError */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Securpharm Aktion Ergebnise.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Securpharm_Action_Result_ID (int M_Securpharm_Action_Result_ID);

	/**
	 * Get Securpharm Aktion Ergebnise.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Securpharm_Action_Result_ID();

	public de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result getM_Securpharm_Action_Result();

	public void setM_Securpharm_Action_Result(de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result M_Securpharm_Action_Result);

    /** Column definition for M_Securpharm_Action_Result_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result> COLUMN_M_Securpharm_Action_Result_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result>(I_M_Securpharm_Log.class, "M_Securpharm_Action_Result_ID", de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result.class);
    /** Column name M_Securpharm_Action_Result_ID */
    public static final String COLUMNNAME_M_Securpharm_Action_Result_ID = "M_Securpharm_Action_Result_ID";

	/**
	 * Set Securpharm-Protokoll.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Securpharm_Log_ID (int M_Securpharm_Log_ID);

	/**
	 * Get Securpharm-Protokoll.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Securpharm_Log_ID();

    /** Column definition for M_Securpharm_Log_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_M_Securpharm_Log_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "M_Securpharm_Log_ID", null);
    /** Column name M_Securpharm_Log_ID */
    public static final String COLUMNNAME_M_Securpharm_Log_ID = "M_Securpharm_Log_ID";

	/**
	 * Set Securpharm Produktdaten Ergebnise.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Securpharm_Productdata_Result_ID (int M_Securpharm_Productdata_Result_ID);

	/**
	 * Get Securpharm Produktdaten Ergebnise.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Securpharm_Productdata_Result_ID();

	public de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result getM_Securpharm_Productdata_Result();

	public void setM_Securpharm_Productdata_Result(de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result M_Securpharm_Productdata_Result);

    /** Column definition for M_Securpharm_Productdata_Result_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result> COLUMN_M_Securpharm_Productdata_Result_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result>(I_M_Securpharm_Log.class, "M_Securpharm_Productdata_Result_ID", de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result.class);
    /** Column name M_Securpharm_Productdata_Result_ID */
    public static final String COLUMNNAME_M_Securpharm_Productdata_Result_ID = "M_Securpharm_Productdata_Result_ID";

	/**
	 * Set Anfrage Ende.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestEndTime (java.sql.Timestamp RequestEndTime);

	/**
	 * Get Anfrage Ende.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getRequestEndTime();

    /** Column definition for RequestEndTime */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_RequestEndTime = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "RequestEndTime", null);
    /** Column name RequestEndTime */
    public static final String COLUMNNAME_RequestEndTime = "RequestEndTime";

	/**
	 * Set Request Method.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestMethod (java.lang.String RequestMethod);

	/**
	 * Get Request Method.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestMethod();

    /** Column definition for RequestMethod */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_RequestMethod = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "RequestMethod", null);
    /** Column name RequestMethod */
    public static final String COLUMNNAME_RequestMethod = "RequestMethod";

	/**
	 * Set Anfrage Start .
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestStartTime (java.sql.Timestamp RequestStartTime);

	/**
	 * Get Anfrage Start .
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getRequestStartTime();

    /** Column definition for RequestStartTime */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_RequestStartTime = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "RequestStartTime", null);
    /** Column name RequestStartTime */
    public static final String COLUMNNAME_RequestStartTime = "RequestStartTime";

	/**
	 * Set Abfrage.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestUrl (java.lang.String RequestUrl);

	/**
	 * Get Abfrage.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestUrl();

    /** Column definition for RequestUrl */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_RequestUrl = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "RequestUrl", null);
    /** Column name RequestUrl */
    public static final String COLUMNNAME_RequestUrl = "RequestUrl";

	/**
	 * Set Antwort .
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResponseCode (int ResponseCode);

	/**
	 * Get Antwort .
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getResponseCode();

    /** Column definition for ResponseCode */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_ResponseCode = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "ResponseCode", null);
    /** Column name ResponseCode */
    public static final String COLUMNNAME_ResponseCode = "ResponseCode";

	/**
	 * Set Antwort-Text.
	 * Anfrage-Antworttext
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResponseText (java.lang.String ResponseText);

	/**
	 * Get Antwort-Text.
	 * Anfrage-Antworttext
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getResponseText();

    /** Column definition for ResponseText */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_ResponseText = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "ResponseText", null);
    /** Column name ResponseText */
    public static final String COLUMNNAME_ResponseText = "ResponseText";

	/**
	 * Set TransaktionsID Client.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTransactionIDClient (java.lang.String TransactionIDClient);

	/**
	 * Get TransaktionsID Client.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTransactionIDClient();

    /** Column definition for TransactionIDClient */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_TransactionIDClient = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "TransactionIDClient", null);
    /** Column name TransactionIDClient */
    public static final String COLUMNNAME_TransactionIDClient = "TransactionIDClient";

	/**
	 * Set TransaktionsID Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTransactionIDServer (java.lang.String TransactionIDServer);

	/**
	 * Get TransaktionsID Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTransactionIDServer();

    /** Column definition for TransactionIDServer */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_TransactionIDServer = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "TransactionIDServer", null);
    /** Column name TransactionIDServer */
    public static final String COLUMNNAME_TransactionIDServer = "TransactionIDServer";

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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, Object>(I_M_Securpharm_Log.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Log, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Log, org.compiere.model.I_AD_User>(I_M_Securpharm_Log.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}

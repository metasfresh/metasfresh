package de.metas.shipper.gateway.dhl.model;


/** Generated Interface for Dhl_ShipmentOrder_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_Dhl_ShipmentOrder_Log 
{

    /** TableName=Dhl_ShipmentOrder_Log */
    public static final String Table_Name = "Dhl_ShipmentOrder_Log";

    /** AD_Table_ID=541426 */
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
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, org.compiere.model.I_AD_Client>(I_Dhl_ShipmentOrder_Log.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, org.compiere.model.I_AD_Issue>(I_Dhl_ShipmentOrder_Log.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, org.compiere.model.I_AD_Org>(I_Dhl_ShipmentOrder_Log.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Config Summary.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setConfigSummary (java.lang.String ConfigSummary);

	/**
	 * Get Config Summary.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getConfigSummary();

    /** Column definition for ConfigSummary */
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_ConfigSummary = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object>(I_Dhl_ShipmentOrder_Log.class, "ConfigSummary", null);
    /** Column name ConfigSummary */
    public static final String COLUMNNAME_ConfigSummary = "ConfigSummary";

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
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object>(I_Dhl_ShipmentOrder_Log.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, org.compiere.model.I_AD_User>(I_Dhl_ShipmentOrder_Log.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Dhl ShipmentOrder Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDhl_ShipmentOrder_Log_ID (int Dhl_ShipmentOrder_Log_ID);

	/**
	 * Get Dhl ShipmentOrder Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDhl_ShipmentOrder_Log_ID();

    /** Column definition for Dhl_ShipmentOrder_Log_ID */
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_Dhl_ShipmentOrder_Log_ID = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object>(I_Dhl_ShipmentOrder_Log.class, "Dhl_ShipmentOrder_Log_ID", null);
    /** Column name Dhl_ShipmentOrder_Log_ID */
    public static final String COLUMNNAME_Dhl_ShipmentOrder_Log_ID = "Dhl_ShipmentOrder_Log_ID";

	/**
	 * Set DHL Shipment Order Request.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDHL_ShipmentOrderRequest_ID (int DHL_ShipmentOrderRequest_ID);

	/**
	 * Get DHL Shipment Order Request.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDHL_ShipmentOrderRequest_ID();

	public de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest getDHL_ShipmentOrderRequest();

	public void setDHL_ShipmentOrderRequest(de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest DHL_ShipmentOrderRequest);

    /** Column definition for DHL_ShipmentOrderRequest_ID */
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest> COLUMN_DHL_ShipmentOrderRequest_ID = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest>(I_Dhl_ShipmentOrder_Log.class, "DHL_ShipmentOrderRequest_ID", de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrderRequest.class);
    /** Column name DHL_ShipmentOrderRequest_ID */
    public static final String COLUMNNAME_DHL_ShipmentOrderRequest_ID = "DHL_ShipmentOrderRequest_ID";

	/**
	 * Set Duration (ms).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDurationMillis (int DurationMillis);

	/**
	 * Get Duration (ms).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDurationMillis();

    /** Column definition for DurationMillis */
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_DurationMillis = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object>(I_Dhl_ShipmentOrder_Log.class, "DurationMillis", null);
    /** Column name DurationMillis */
    public static final String COLUMNNAME_DurationMillis = "DurationMillis";

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
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object>(I_Dhl_ShipmentOrder_Log.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object>(I_Dhl_ShipmentOrder_Log.class, "IsError", null);
    /** Column name IsError */
    public static final String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Request Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestMessage (java.lang.String RequestMessage);

	/**
	 * Get Request Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestMessage();

    /** Column definition for RequestMessage */
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_RequestMessage = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object>(I_Dhl_ShipmentOrder_Log.class, "RequestMessage", null);
    /** Column name RequestMessage */
    public static final String COLUMNNAME_RequestMessage = "RequestMessage";

	/**
	 * Set Response Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResponseMessage (java.lang.String ResponseMessage);

	/**
	 * Get Response Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getResponseMessage();

    /** Column definition for ResponseMessage */
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_ResponseMessage = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object>(I_Dhl_ShipmentOrder_Log.class, "ResponseMessage", null);
    /** Column name ResponseMessage */
    public static final String COLUMNNAME_ResponseMessage = "ResponseMessage";

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
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, Object>(I_Dhl_ShipmentOrder_Log.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_Dhl_ShipmentOrder_Log, org.compiere.model.I_AD_User>(I_Dhl_ShipmentOrder_Log.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}

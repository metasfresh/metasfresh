package de.metas.shipper.gateway.go.model;


/** Generated Interface for GO_DeliveryOrder_Log
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_GO_DeliveryOrder_Log 
{

    /** TableName=GO_DeliveryOrder_Log */
    public static final String Table_Name = "GO_DeliveryOrder_Log";

    /** AD_Table_ID=540896 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Aktion.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAction (java.lang.String Action);

	/**
	 * Get Aktion.
	 * Zeigt die durchzuführende Aktion an
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAction();

    /** Column definition for Action */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object> COLUMN_Action = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object>(I_GO_DeliveryOrder_Log.class, "Action", null);
    /** Column name Action */
    public static final String COLUMNNAME_Action = "Action";

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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, org.compiere.model.I_AD_Client>(I_GO_DeliveryOrder_Log.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, org.compiere.model.I_AD_Issue>(I_GO_DeliveryOrder_Log.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
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

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, org.compiere.model.I_AD_Org>(I_GO_DeliveryOrder_Log.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object>(I_GO_DeliveryOrder_Log.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, org.compiere.model.I_AD_User>(I_GO_DeliveryOrder_Log.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object> COLUMN_DurationMillis = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object>(I_GO_DeliveryOrder_Log.class, "DurationMillis", null);
    /** Column name DurationMillis */
    public static final String COLUMNNAME_DurationMillis = "DurationMillis";

	/**
	 * Set Config Summary.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_ConfigSummary (java.lang.String GO_ConfigSummary);

	/**
	 * Get Config Summary.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGO_ConfigSummary();

    /** Column definition for GO_ConfigSummary */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object> COLUMN_GO_ConfigSummary = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object>(I_GO_DeliveryOrder_Log.class, "GO_ConfigSummary", null);
    /** Column name GO_ConfigSummary */
    public static final String COLUMNNAME_GO_ConfigSummary = "GO_ConfigSummary";

	/**
	 * Set GO Delivery Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliveryOrder_ID (int GO_DeliveryOrder_ID);

	/**
	 * Get GO Delivery Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getGO_DeliveryOrder_ID();

	public de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder getGO_DeliveryOrder();

	public void setGO_DeliveryOrder(de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder GO_DeliveryOrder);

    /** Column definition for GO_DeliveryOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder> COLUMN_GO_DeliveryOrder_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder>(I_GO_DeliveryOrder_Log.class, "GO_DeliveryOrder_ID", de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder.class);
    /** Column name GO_DeliveryOrder_ID */
    public static final String COLUMNNAME_GO_DeliveryOrder_ID = "GO_DeliveryOrder_ID";

	/**
	 * Set GO Delivery Order Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGO_DeliveryOrder_Log_ID (int GO_DeliveryOrder_Log_ID);

	/**
	 * Get GO Delivery Order Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGO_DeliveryOrder_Log_ID();

    /** Column definition for GO_DeliveryOrder_Log_ID */
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object> COLUMN_GO_DeliveryOrder_Log_ID = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object>(I_GO_DeliveryOrder_Log.class, "GO_DeliveryOrder_Log_ID", null);
    /** Column name GO_DeliveryOrder_Log_ID */
    public static final String COLUMNNAME_GO_DeliveryOrder_Log_ID = "GO_DeliveryOrder_Log_ID";

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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object>(I_GO_DeliveryOrder_Log.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object>(I_GO_DeliveryOrder_Log.class, "IsError", null);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object> COLUMN_RequestMessage = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object>(I_GO_DeliveryOrder_Log.class, "RequestMessage", null);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object> COLUMN_ResponseMessage = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object>(I_GO_DeliveryOrder_Log.class, "ResponseMessage", null);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, Object>(I_GO_DeliveryOrder_Log.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_GO_DeliveryOrder_Log, org.compiere.model.I_AD_User>(I_GO_DeliveryOrder_Log.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}

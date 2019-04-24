package de.metas.vertical.pharma.securpharm.model;


/** Generated Interface for M_Securpharm_Action_Result
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Securpharm_Action_Result 
{

    /** TableName=M_Securpharm_Action_Result */
	String Table_Name = "M_Securpharm_Action_Result";

    /** AD_Table_ID=541350 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */
    /** Column definition for Action */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_Action = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "Action", null);
    /** Column name Action */
	String COLUMNNAME_Action = "Action";
    /** Column definition for AD_Client_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_Client>(I_M_Securpharm_Action_Result.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    /** Column definition for AD_Org_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_Org>(I_M_Securpharm_Action_Result.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    /** Column definition for Created */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "Created", null);
    /** Column name Created */
	String COLUMNNAME_Created = "Created";
    /** Column definition for CreatedBy */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_User>(I_M_Securpharm_Action_Result.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
	String COLUMNNAME_CreatedBy = "CreatedBy";
    /** Column definition for IsError */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_IsError = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "IsError", null);
    /** Column name IsError */
	String COLUMNNAME_IsError = "IsError";
    /** Column definition for M_Inventory_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_M_Inventory> COLUMN_M_Inventory_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_M_Inventory>(I_M_Securpharm_Action_Result.class, "M_Inventory_ID", org.compiere.model.I_M_Inventory.class);
    /** Column name M_Inventory_ID */
	String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";
    /** Column definition for M_Securpharm_Action_Result_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_M_Securpharm_Action_Result_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "M_Securpharm_Action_Result_ID", null);
    /** Column name M_Securpharm_Action_Result_ID */
	String COLUMNNAME_M_Securpharm_Action_Result_ID = "M_Securpharm_Action_Result_ID";
    /** Column definition for M_Securpharm_Productdata_Result_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result> COLUMN_M_Securpharm_Productdata_Result_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result>(I_M_Securpharm_Action_Result.class, "M_Securpharm_Productdata_Result_ID", de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result.class);
    /** Column name M_Securpharm_Productdata_Result_ID */
	String COLUMNNAME_M_Securpharm_Productdata_Result_ID = "M_Securpharm_Productdata_Result_ID";
    /** Column definition for RequestEndTime */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_RequestEndTime = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "RequestEndTime", null);
    /** Column name RequestEndTime */
	String COLUMNNAME_RequestEndTime = "RequestEndTime";
    /** Column definition for RequestStartTime */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_RequestStartTime = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "RequestStartTime", null);
    /** Column name RequestStartTime */
	String COLUMNNAME_RequestStartTime = "RequestStartTime";
    /** Column definition for RequestUrl */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_RequestUrl = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "RequestUrl", null);
    /** Column name RequestUrl */
	String COLUMNNAME_RequestUrl = "RequestUrl";
    /** Column definition for TransactionIDClient */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_TransactionIDClient = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "TransactionIDClient", null);
    /** Column name TransactionIDClient */
	String COLUMNNAME_TransactionIDClient = "TransactionIDClient";
    /** Column definition for TransactionIDServer */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_TransactionIDServer = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "TransactionIDServer", null);
    /** Column name TransactionIDServer */
	String COLUMNNAME_TransactionIDServer = "TransactionIDServer";
    /** Column definition for Updated */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, Object>(I_M_Securpharm_Action_Result.class, "Updated", null);
    /** Column name Updated */
	String COLUMNNAME_Updated = "Updated";
    /** Column definition for UpdatedBy */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Action_Result, org.compiere.model.I_AD_User>(I_M_Securpharm_Action_Result.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Get Aktion.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAction();

	/**
	 * Set Aktion.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAction(java.lang.String Action);

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	org.compiere.model.I_AD_Client getAD_Client();

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID(int AD_Org_ID);

	org.compiere.model.I_AD_Org getAD_Org();

	void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	/**
	 * Set Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsError(boolean IsError);

	/**
	 * Get Fehler.
	 * Ein Fehler ist bei der Durchführung aufgetreten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isError();

	/**
	 * Get Inventur.
	 * Parameter für eine physische Inventur
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Inventory_ID();

	/**
	 * Set Inventur.
	 * Parameter für eine physische Inventur
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Inventory_ID(int M_Inventory_ID);

	org.compiere.model.I_M_Inventory getM_Inventory();

	void setM_Inventory(org.compiere.model.I_M_Inventory M_Inventory);

	/**
	 * Get SecurPharm action result.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Securpharm_Action_Result_ID();

	/**
	 * Set SecurPharm action result.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Securpharm_Action_Result_ID(int M_Securpharm_Action_Result_ID);

	/**
	 * Get SecurPharm product data result.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Securpharm_Productdata_Result_ID();

	/**
	 * Set SecurPharm product data result.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Securpharm_Productdata_Result_ID(int M_Securpharm_Productdata_Result_ID);

	de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result getM_Securpharm_Productdata_Result();

	void setM_Securpharm_Productdata_Result(de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result M_Securpharm_Productdata_Result);

	/**
	 * Get Anfrage Ende.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getRequestEndTime();

	/**
	 * Set Anfrage Ende.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRequestEndTime(java.sql.Timestamp RequestEndTime);

	/**
	 * Get Anfrage Start .
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getRequestStartTime();

	/**
	 * Set Anfrage Start .
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRequestStartTime(java.sql.Timestamp RequestStartTime);

	/**
	 * Get Abfrage.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRequestUrl();

	/**
	 * Set Abfrage.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRequestUrl(java.lang.String RequestUrl);

	/**
	 * Get TransaktionsID Client.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTransactionIDClient();

	/**
	 * Set TransaktionsID Client.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTransactionIDClient(java.lang.String TransactionIDClient);

	/**
	 * Get TransaktionsID Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getTransactionIDServer();

	/**
	 * Set TransaktionsID Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTransactionIDServer(java.lang.String TransactionIDServer);

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();
}
